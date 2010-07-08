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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.distribution.RemoteCacheException;
import net.sf.ehcache.distribution.jgroups.JGroupEventMessage;
import net.sf.ehcache.distribution.jgroups.JGroupManager;

import org.jgroups.Address;

/**
 * This class is used to workaround net.sf.ehcache.distribution.jgroups.
 * JGroupsBootstrapCacheLoader's wrong downcast usage about JGroups' address.
 * No processing logic is changed.
 * <a href="JGroupsBootstrapCacheLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class JGroupsBootstrapCacheLoader
	extends net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoader {

	public JGroupsBootstrapCacheLoader(
		boolean asynchronous, int maximumChunkSize) {
		super(asynchronous, maximumChunkSize);
	}

	public Object clone() throws CloneNotSupportedException {
		return new JGroupsBootstrapCacheLoader(asynchronous,
			maximumChunkSizeBytes);
	}

	public void doLoad(Ehcache cache) throws RemoteCacheException {

		List<JGroupManager> cachePeers = acquireCachePeers(cache);
		if (cachePeers == null || cachePeers.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("Empty list of cache peers for cache " +
					cache.getName() + ". No cache peer to bootstrap from.");
			}

			return;
		}

		JGroupManager jGroupManager = cachePeers.get(0);
		Address localAddress = jGroupManager.getBusLocalAddress();
		if (_log.isInfoEnabled()) {
			_log.info("(" + cache.getName() + ") localAddress: " +
				localAddress);
		}

		List<Address> addresses = buildCachePeerAddressList(cache,
			jGroupManager, localAddress);

		if (addresses == null || addresses.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"This is the first node to start: no cache bootstrap for " +
						cache.getName());
			}
			return;
		}

		Address address = null;
		Random random = new Random();

		while (addresses.size() > 0 &&
			(address == null || cache.getSize() == 0)) {

			int randomPeerNumber = random.nextInt(addresses.size());
			address = addresses.get(randomPeerNumber);
			addresses.remove(randomPeerNumber);
			JGroupEventMessage event =
				new JGroupEventMessage(JGroupEventMessage.ASK_FOR_BOOTSTRAP,
					localAddress, null, cache, cache.getName());
			if (_log.isInfoEnabled()) {
				_log.info("contact " + address + " to boot cache " +
					cache.getName());
			}
			List<JGroupEventMessage> events =
				new ArrayList<JGroupEventMessage>();
			events.add(event);
			try {
				jGroupManager.send(address, events);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					_log.error("InterruptedException", e);
				}
			} catch (RemoteException e1) {
				_log.error("error calling " + address, e1);
			}
		}

		if (cache.getSize() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Cache failed to bootstrap from its peers: " +
					cache.getName());
			}
		} else {
			if (_log.isInfoEnabled()) {
				_log.info("Bootstrap for cache " + cache.getName() +
					" has loaded " + cache.getSize() + " elements");
			}
		}

	}

	private List<Address> buildCachePeerAddressList(
		Ehcache cache, JGroupManager jGroupManager, Address localAddress) {

		List<Address> members = jGroupManager.getBusMembership();
		List<Address> addresses = new ArrayList<Address>();
		for (int i = 0; i < members.size(); i++) {
			Address member = members.get(i);
			if (_log.isInfoEnabled()) {
				_log.info("(" + cache.getName() + ") member " + i + ": "
						+ member + (member.equals(localAddress) ? " ***" : ""));
			}
			if (!member.equals(localAddress)) {
				addresses.add(member);
			}
		}
		return addresses;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JGroupsBootstrapCacheLoader.class);

}