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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.PortalUtil;

import com.sun.portal.wsrp.common.WSRPConfig;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WSRPPersistenceHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 *
 */
public class WSRPPersistenceHelper {

	public static synchronized WSRPPersistenceHelper getInstance() {
		return _instance;
	}

	public void addWSRPPortlet(Portlet portlet) throws WSRPConsumerException {
		WSRPPortlet wsrpPortlet = new WSRPPortlet();

		PortletInfo portletInfo = portlet.getPortletInfo();

		wsrpPortlet.setChannelName(portlet.getPortletId());
		wsrpPortlet.setConsumerId(portlet.getRemoteConsumerId());
		wsrpPortlet.setDisplayName(portlet.getDisplayName());
		wsrpPortlet.setKeywords(portletInfo.getKeywords());
		wsrpPortlet.setPortletHandle(portlet.getRemotePortletHandle());
		wsrpPortlet.setPortletId(portlet.getPortletId());
		wsrpPortlet.setProducerEntityId(portlet.getRemoteProducerEntityId());
		wsrpPortlet.setShortTitle(portletInfo.getShortTitle());
		wsrpPortlet.setStatus(portlet.isActive());
		wsrpPortlet.setTitle(portletInfo.getTitle());

		Map<String, Set<String>> portletModes = portlet.getPortletModes();

		Set<String> mimeTypes = portletModes.keySet();

		for (String mimeType : mimeTypes) {
			Set<String> mimeTypeModes = portletModes.get(mimeType);

			MimeType mimeTypeModel = new MimeType();

			mimeTypeModel.setMime(mimeType);
			mimeTypeModel.getModes().addAll(mimeTypeModes);

			wsrpPortlet.getMimeTypes().add(mimeTypeModel);
		}

		WSRPPortlets rootNode = getRootNode();

		updatePortlet(rootNode, wsrpPortlet);
	}

	public void deleteWSRPPortlet(Portlet portlet)
		throws WSRPConsumerException {

		WSRPPortlets rootNode = getRootNode();

		if (rootNode == null) {
			return;
		}

		Iterator<WSRPPortlet> itr = rootNode.getWSRPPortlet().iterator();

		while (itr.hasNext()) {
			WSRPPortlet wsrpPortlet = itr.next();

			if (portlet.getPortletId().equals(wsrpPortlet.getChannelName())) {
				itr.remove();

				break;
			}
		}

		persistWSRPPortlets(rootNode);
	}

	public List<Portlet> getWSRPPortlets() throws WSRPConsumerException {
		WSRPPortlets rootNode = getRootNode();

		if ((rootNode == null) || (rootNode.getWSRPPortlet().size() == 0)) {
			return Collections.EMPTY_LIST;
		}

		List<Portlet> portlets = new ArrayList<Portlet>();

		List<WSRPPortlet> wsrpPortlets = rootNode.getWSRPPortlet();

		for (WSRPPortlet wsrpPortlet : wsrpPortlets) {
			String portletId = PortalUtil.getJsSafePortletId(
					wsrpPortlet.getPortletId());

			Portlet portlet = new PortletImpl(
				CompanyConstants.SYSTEM, portletId);

			portlet.setPortletId(portletId);
			portlet.setTimestamp(System.currentTimeMillis());
			portlet.setPortletName(portletId);
			portlet.setInstanceable(true);
			portlet.setActive(true);
			portlet.setRemote(true);
			portlet.setRemoteConsumerId(wsrpPortlet.getConsumerId());
			portlet.setRemoteProducerEntityId(
				wsrpPortlet.getProducerEntityId());
			portlet.setRemotePortletHandle(wsrpPortlet.getPortletHandle());
			portlet.setRemotePortletId(wsrpPortlet.getPortletHandle());

			List<MimeType> mimeTypes = wsrpPortlet.getMimeTypes();

			Map<String, Set<String>> portletModes =
				new HashMap<String, Set<String>>();

			for (MimeType mimeType : mimeTypes) {
				Set<String> modes = new HashSet<String>(mimeType.getModes());
				portletModes.put(mimeType.getMime(), modes);
			}

			portlet.setPortletModes(portletModes);

			PortletInfo portletInfo = new PortletInfo(
				wsrpPortlet.getTitle(), wsrpPortlet.getShortTitle(),
				wsrpPortlet.getKeywords());

			portlet.setPortletInfo(portletInfo);

			portlets.add(portlet);
		}

		return portlets;
	}

	public void updateWSRPPortlet(Portlet portlet)
		throws WSRPConsumerException {

		addWSRPPortlet(portlet);
	}

	protected synchronized WSRPPortlets getRootNode()
		throws WSRPConsumerException {

		synchronized (this) {
			if (_wsrpPortlets == null || !_dirty) {
				FileInputStream fis = null;

				try {
					File file = new File(_wsrpFileName);

					if (file.length() == 0) {
						return _objectFactory.createWSRPPortlets();
					}
					else {
						fis = new FileInputStream(_wsrpFileName);

						JAXBElement<WSRPPortlets> rootElement =
							(JAXBElement<WSRPPortlets>)_unmarshaller.unmarshal(
								fis);

						_wsrpPortlets = rootElement.getValue();
					}
				}
				catch (Exception e) {
					throw new WSRPConsumerException(e.getMessage(), e);
				}
				finally {
					if (fis != null) {
						try {
							fis.close();
						}
						catch (IOException ioe) {
						}
					}
				}

				_dirty = true;
			}
		}

		return _wsrpPortlets;
	}

	protected synchronized void persistWSRPPortlets(WSRPPortlets wsrpPortlets)
		throws WSRPConsumerException {

		FileOutputStream fos = null;

		try {
			synchronized (this) {
				fos = new FileOutputStream(_wsrpFileName);

				JAXBElement<WSRPPortlets> rootElement =
					_objectFactory.createWSRPPortlets(wsrpPortlets);

				_marshaller.marshal(rootElement, fos);

				_dirty = false;
			}
		}
		catch (FileNotFoundException fnfe) {
			_log.error(fnfe, fnfe);

			throw new WSRPConsumerException(fnfe.getMessage(), fnfe);
		}
		catch (JAXBException jaxbe) {
			_log.error(jaxbe, jaxbe);

			throw new WSRPConsumerException(jaxbe.getMessage(), jaxbe);
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	protected void updatePortlet(
			WSRPPortlets wsrpPortlets, WSRPPortlet wsrpPortlet)
		throws WSRPConsumerException {

		if (wsrpPortlets == null) {
			return;
		}

		Iterator<WSRPPortlet> itr = wsrpPortlets.getWSRPPortlet().iterator();

		while (itr.hasNext()) {
			WSRPPortlet curWSRPPortlet = itr.next();

			if (curWSRPPortlet.getChannelName().equals(
					wsrpPortlet.getChannelName())) {

				itr.remove();

				break;
			}
		}

		wsrpPortlets.getWSRPPortlet().add(wsrpPortlet);

		persistWSRPPortlets(wsrpPortlets);
	}

	private WSRPPersistenceHelper() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();

			_jaxbContext = JAXBContext.newInstance(
				"com.liferay.portal.wsrp.consumer.admin", classLoader);

			_marshaller = _jaxbContext.createMarshaller();

			_marshaller.setProperty(
				Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			_unmarshaller = _jaxbContext.createUnmarshaller();

			_objectFactory = new ObjectFactory();

			String wsrpDataDir = WSRPConfig.getWSRPDataDirectory();

			FileUtil.mkdirs(wsrpDataDir);

			File wsrpConsumerFile = new File(wsrpDataDir + "/consumer.xml");

			if (!wsrpConsumerFile.exists()) {
				FileUtil.write(
					wsrpConsumerFile,
					StringUtil.read(
						classLoader,
						"com/liferay/portal/wsrp/consumer/data/consumer.xml"));
			}

			_wsrpFileName = wsrpDataDir + "/wsrpportlets.xml";

			File file = new File(_wsrpFileName);

			if (!file.exists()) {
				file.createNewFile();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactory.getLog(WSRPPersistenceHelper.class);

	private static WSRPPersistenceHelper _instance =
		new WSRPPersistenceHelper();

	private JAXBContext _jaxbContext;
	private Marshaller _marshaller;
	private Unmarshaller _unmarshaller;
	private ObjectFactory _objectFactory;
	private String _wsrpFileName;
	private boolean _dirty;
	private WSRPPortlets _wsrpPortlets;

}