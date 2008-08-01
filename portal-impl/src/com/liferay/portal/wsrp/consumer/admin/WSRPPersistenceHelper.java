/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.wsrp.consumer.admin;

import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.PortalUtil;

import com.sun.portal.wsrp.common.WSRPConfig;
import com.sun.portal.wsrp.common.WSRPLogger;
import com.sun.portal.wsrp.consumer.common.WSRPConsumerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * <a href="WSRPPersistenceHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh T
 *
 */
public class WSRPPersistenceHelper {

	private WSRPPersistenceHelper() {
		try {
			_jaxbContext = JAXBContext.newInstance(
					"com.liferay.portal.wsrp.consumer.admin",
					this.getClass().getClassLoader());

			_marshaller = _jaxbContext.createMarshaller();

			_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			_unmarshaller = _jaxbContext.createUnmarshaller();

			_objectFactory = new ObjectFactory();

		}
		catch (Exception je) {
			_logger.log(Level.SEVERE, je.getMessage(), je);
		}
	}

	public void addWSRPPortlet(Portlet remotePortlet)
			throws WSRPConsumerException {

		WSRPPortlet wsrpPortlet = new WSRPPortlet();
		PortletInfo portletInfo = remotePortlet.getPortletInfo();

		wsrpPortlet.setChannelName(remotePortlet.getPortletId());
		wsrpPortlet.setConsumerId(remotePortlet.getConsumerId());
		wsrpPortlet.setDisplayName(remotePortlet.getDisplayName());
		wsrpPortlet.setPortletHandle(remotePortlet.getRemotePortletHandle());
		wsrpPortlet.setPortletId(remotePortlet.getPortletId());
		wsrpPortlet.setProducerEntityId(remotePortlet.getProducerEntityId());
		wsrpPortlet.setStatus(remotePortlet.isActive());
		wsrpPortlet.setTitle(portletInfo.getTitle());
		wsrpPortlet.setShortTitle(portletInfo.getShortTitle());
		wsrpPortlet.setKeywords(portletInfo.getKeywords());

		Map<String, Set<String>> portletModes = remotePortlet.getPortletModes();
		Set<String> keySet = portletModes.keySet();

		for (String key : keySet) {
			Set<String> modes = portletModes.get(key);
			MimeType mimeType = new MimeType();
			mimeType.setMime(key);
			mimeType.getModes().addAll(modes);
			wsrpPortlet.getMimeTypes().add(mimeType);
		}

		WSRPPortlets rootNode = _getRootNode();

		_updatePortlet(rootNode, wsrpPortlet);
	}

	public void deleteWSRPPortlet(Portlet remotePortlet)
			throws WSRPConsumerException {

		WSRPPortlets rootNode = _getRootNode();

		if (rootNode != null) {
			Iterator iter = rootNode.getWSRPPortlet().iterator();

			while (iter.hasNext()) {
				WSRPPortlet wsrpPortlet = (WSRPPortlet) iter.next();

				if (remotePortlet.getPortletId().
						equals(wsrpPortlet.getChannelName())) {

					iter.remove();
					break;
				}
			}

			_persistWSRPPortlets(rootNode);
		}
	}

	public static synchronized WSRPPersistenceHelper getInstance()
			throws WSRPConsumerException {

		String wsrpStorageDir = WSRPConfig.getWSRPDataDirectory();
		_fileStore = wsrpStorageDir + File.separator + _PORTLET_STORAGE_FILE;

		/* Check for the existence of this file. If the file is absent,
		 * then create a new file, with empty portlets
		 */

		File file = new File(_fileStore);

		if (!file.exists()) {
			FileOutputStream fos = null;

			try {
				file.createNewFile();
			}
			catch (Exception e) {
				_logger.log(Level.SEVERE, e.getMessage(), e);

				throw new WSRPConsumerException("Cannot create default " +
						"WSRP Portlet store", e);
			}
			finally {
				try {
					if (fos != null) {
						fos.close();
					}
				}
				catch (Exception e) {
					// Ignore this.
				}
			}
		}

		return _helper;
	}

	public List<Portlet> getWSRPPortlets() throws WSRPConsumerException {
		WSRPPortlets rootNode = _getRootNode();
		if (rootNode == null || rootNode.getWSRPPortlet().size() == 0) {
			return Collections.EMPTY_LIST;
		}

		List<WSRPPortlet> wsrpPortlets = rootNode.getWSRPPortlet();
		List<Portlet> portlets = new ArrayList<Portlet>();

		for (WSRPPortlet wsrpPortlet : wsrpPortlets) {
			String portletId = PortalUtil.getJsSafePortletId(
					wsrpPortlet.getPortletId());

			Portlet remotePortlet = new PortletImpl(
					CompanyConstants.SYSTEM, portletId);

			remotePortlet.setRemote(true);
			remotePortlet.setProducerEntityId(
					wsrpPortlet.getProducerEntityId());

			remotePortlet.setConsumerId(wsrpPortlet.getConsumerId());
			remotePortlet.setRemotePortletHandle(
					wsrpPortlet.getPortletHandle());

			remotePortlet.setRemotePortletId(wsrpPortlet.getPortletHandle());
			remotePortlet.setPortletId(portletId);
			remotePortlet.setPortletName(portletId);
			remotePortlet.setActive(true);
			remotePortlet.setInstanceable(true);
			remotePortlet.setTimestamp(System.currentTimeMillis());

			PortletInfo portletInfo = new PortletInfo(
					wsrpPortlet.getTitle(),
					wsrpPortlet.getShortTitle(),
					wsrpPortlet.getKeywords());

			remotePortlet.setPortletInfo(portletInfo);

			List<MimeType> mimeTypes = wsrpPortlet.getMimeTypes();

			Map<String, Set<String>> portletModes =
					new HashMap<String, Set<String>>();

			for (MimeType mimeType : mimeTypes) {
				Set<String> modes = new HashSet<String>(mimeType.getModes());
				portletModes.put(mimeType.getMime(), modes);
			}

			remotePortlet.setPortletModes(portletModes);

			portlets.add(remotePortlet);
		}

		return portlets;
	}

	public void updateWSRPPortlet(Portlet remotePortlet)
			throws WSRPConsumerException {

		addWSRPPortlet(remotePortlet);
	}

	private synchronized WSRPPortlets _getRootNode()
			throws WSRPConsumerException {

		synchronized (this) {
			if (_wsrpPortlets == null || !_isDirty) {
				FileInputStream fis = null;
				try {
					File file = new File(_fileStore);
					if (file.length() == 0) {
						return _objectFactory.createWSRPPortlets();
					}
					else {
						fis = new FileInputStream(_fileStore);
						JAXBElement<WSRPPortlets> rootElement =
							(JAXBElement<WSRPPortlets>)
							_unmarshaller.unmarshal(fis);

						_wsrpPortlets = rootElement.getValue();
					}
				}
				catch (Exception e) {
					throw new WSRPConsumerException("Could not initialize " +
							"wsrp portlets jaxb", e);
				}
				finally {
					if (fis != null) {
						try {
							fis.close();
						}
						catch (IOException ex) {
							// Ignore this for now.
						}
					}
				}
				_isDirty = true;
			}
		}

		return _wsrpPortlets;
	}

	private synchronized void _persistWSRPPortlets(WSRPPortlets _wsrpPortlets)
			throws WSRPConsumerException {

		FileOutputStream fos = null;
		try {
			synchronized (this) {
				fos = new FileOutputStream(_fileStore);
				JAXBElement<WSRPPortlets> rootElement =
						_objectFactory.createWSRPPortlets(_wsrpPortlets);

				_marshaller.marshal(rootElement, fos);
				_isDirty = false;
			}
		}
		catch (JAXBException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
			throw new WSRPConsumerException("The wsrp portlets " +
					"could not be updated", e);
		}
		catch (FileNotFoundException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
			throw new WSRPConsumerException("The wsrp portlets could " +
					"not be updated", e);
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				}
				catch (IOException ex) {
					// Ignore this.
				}
			}
		}
	}

	private void _updatePortlet(
			WSRPPortlets _wsrpPortlets,
			WSRPPortlet wsrpPortlet)
		throws WSRPConsumerException {

		if (_wsrpPortlets != null) {
			Iterator iter = _wsrpPortlets.getWSRPPortlet().iterator();

			while (iter.hasNext()) {
				WSRPPortlet remotePortlet = (WSRPPortlet) iter.next();

				if (wsrpPortlet.getChannelName().
						equals(remotePortlet.getChannelName())) {

					iter.remove();
					break;
				}
			}
			_wsrpPortlets.getWSRPPortlet().add(wsrpPortlet);
			_persistWSRPPortlets(_wsrpPortlets);
		}
	}

	private static final String _PORTLET_STORAGE_FILE = "wsrpportlets.xml";

	private static Logger _logger =
			WSRPLogger.getLogger(WSRPPersistenceHelper.class, "logmessages");

	private static ObjectFactory _objectFactory = null;
	private static String _fileStore = null;
	private static WSRPPersistenceHelper _helper = new WSRPPersistenceHelper();

	private Marshaller _marshaller = null;
	private Unmarshaller _unmarshaller = null;
	private JAXBContext _jaxbContext = null;
	private WSRPPortlets _wsrpPortlets = null;
	private boolean _isDirty = false;

}