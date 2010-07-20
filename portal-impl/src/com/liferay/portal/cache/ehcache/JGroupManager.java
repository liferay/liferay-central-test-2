/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cluster.BaseReceiver;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.jgroups.JGroupEventMessage;
import net.sf.ehcache.distribution.jgroups.JGroupSerializable;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Tina Tian
 */
public class JGroupManager implements CacheManagerPeerProvider, CachePeer {

	public JGroupManager(
		CacheManager cacheManager, String channelProperties,
		String clusterName) {

		try {
			_channel = new JChannel(channelProperties);

			_channel.setReceiver(new EhcacheJGroupsReceiver());

			_channel.connect(clusterName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Create a new channel with properties " +
						_channel.getProperties());
			}
		}
		catch (Exception ex) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to initialize channels", ex);
			}
		}

		_cacheManager = cacheManager;
	}

	public void dispose() throws CacheException {
		if (_channel != null) {
			_channel.close();
		}
	}

	public Address getBusLocalAddress() {
		return _channel.getAddress();
	}

	public List getBusMembership() {
		return _channel.getView().getMembers();
	}

	public List getElements(List list) throws RemoteException {
		return null;
	}

	public String getGuid() throws RemoteException {
		return null;
	}

	public List getKeys() throws RemoteException {
		return null;
	}

	public String getName() throws RemoteException {
		return null;
	}

	public String getScheme() {
		return _SCHEAM_NAME;
	}

	public long getTimeForClusterToForm() {
		return 0;
	}

	public String getUrl() throws RemoteException {
		return null;
	}

	public String getUrlBase() throws RemoteException {
		return null;
	}

	public Element getQuiet(Serializable srlzbl) throws RemoteException {
		return null;
	}

	public void handleNotification(Serializable argument) {
		if (argument instanceof JGroupSerializable) {
			handleJGroupNotification((JGroupSerializable)argument);
		}
		else if (argument instanceof List) {
			List argumentList = (List)argument;

			for (Object object : argumentList) {
				if (object instanceof JGroupSerializable) {
					handleJGroupNotification((JGroupSerializable)object);
				}
			}
		}
	}

	public void init() {
	}

	public List listRemoteCachePeers(Ehcache cache)
		throws CacheException {

		List<JGroupManager> cachePeers = new ArrayList<JGroupManager>();

		cachePeers.add(this);

		return cachePeers;
	}

	public void put(Element elmnt)
		throws IllegalArgumentException, IllegalStateException,
		RemoteException {
	}

	public void registerPeer(String string) {
	}

	public boolean remove(Serializable srlzbl)
		throws IllegalStateException, RemoteException {
		return false;
	}

	public void removeAll() throws RemoteException, IllegalStateException {
	}

	public void send(List eventMessages) throws RemoteException {
		send(null, eventMessages);
	}

	public void send(Address address, List eventMessages)
		throws RemoteException {

		ArrayList<JGroupSerializable> messages =
			new ArrayList<JGroupSerializable>();

		for (Object eventMessage : eventMessages) {
			if (eventMessage instanceof JGroupEventMessage) {
				JGroupEventMessage jgroupEventMessage =
					(JGroupEventMessage)eventMessage;

				JGroupSerializable finalEventMessage =
					wrapMessage(jgroupEventMessage);

				messages.add(finalEventMessage);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						eventMessage + "is not a JGroupEventMessage type!");
				}
			}
		}

		try {
			_channel.send(address, null, messages);
		}
		catch (Throwable t) {
			throw new RemoteException(t.getMessage());
		}
	}

	public void unregisterPeer(String string) {
	}

	protected void handleJGroupNotification(
		JGroupSerializable jgroupSerializable) {

		String cacheName = jgroupSerializable.getCacheName();
		Serializable key = jgroupSerializable.getKey();
		Serializable value = jgroupSerializable.getValue();
		int event = jgroupSerializable.getEvent();

		Cache cache = _cacheManager.getCache(cacheName);

		if (cache != null) {
			if ((event == JGroupEventMessage.REMOVE) &&
				(cache.getQuiet(key) != null)) {

				cache.remove(key, true);
			}
			else if (event == JGroupEventMessage.PUT) {
				cache.put(new Element(key, value), true);
			}
			else if (event == JGroupEventMessage.REMOVE_ALL) {
				if (_log.isDebugEnabled()) {
					_log.debug("remove all");
				}

				cache.removeAll(true);
			}
		}
	}

	protected JGroupSerializable wrapMessage(JGroupEventMessage eventMessage) {
		Serializable key = eventMessage.getSerializableKey();
		int eventType = eventMessage.getEvent();
		String cacheName = eventMessage.getCacheName();
		Serializable value = null;

		Element element = eventMessage.getElement();

		if (element != null) {
			value = element.getValue();
		}

		return new JGroupSerializable(eventType, key, value, cacheName);
	}

	private class EhcacheJGroupsReceiver extends BaseReceiver {
		public void receive(Message message) {
			Object object = message.getObject();

			if (object == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Message content is null");
				}

				return;
			}

			if (object instanceof Serializable) {
				handleNotification((Serializable)object);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to process message content of type " +
							object.getClass().getName());
				}
			}
		}
	}

	private static final String _SCHEAM_NAME = "JGroups";

	private static Log _log = LogFactoryUtil.getLog(JGroupManager.class);

	private CacheManager _cacheManager;
	private JChannel _channel;

}