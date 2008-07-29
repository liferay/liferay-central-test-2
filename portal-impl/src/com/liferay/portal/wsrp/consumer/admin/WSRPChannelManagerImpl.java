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

import com.liferay.portal.SystemException;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.sun.portal.wsrp.common.WSRPConfig;
import com.sun.portal.wsrp.common.stubs.v2.LocalizedString;
import com.sun.portal.wsrp.common.stubs.v2.MarkupType;
import com.sun.portal.wsrp.common.stubs.v2.PortletDescription;
import com.sun.portal.wsrp.consumer.admin.mbeans.WSRPChannelManagerMBean;
import com.sun.portal.wsrp.consumer.common.WSRPConsumerException;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntity;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntityManager;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntityManagerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WSRPChannelManagerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh T
 *
 */

/*
 * An implementation of the OpenPortal WSRP Channel Administration Mbean.
 * Responsible for creation of WSRP creation/deletion/ modifucation of
 * WSRP Remote Portlets
 */
public class WSRPChannelManagerImpl implements WSRPChannelManagerMBean {

	/**
	 * Create a new window/channel for a remote portlet.
	 *
	 * @param		consumerName Consumer/Organization name.
	 * @param		channelName Name for the remote portlet window.
	 * @param		prodEntityId Configured producer Id
	 * @param		portletId Remote portlet Id
	 */
	public void createWSRPChannel(
			String channelName,
			String consumerId,
			String producerEntityId,
			String portletHandle) {

		try {
			String portletId = PortalUtil.getJsSafePortletId(channelName);
			Portlet remotePortletModel =
					new PortletImpl(CompanyConstants.SYSTEM, portletId);

			remotePortletModel.setRemote(true);
			remotePortletModel.setProducerEntityId(producerEntityId);
			remotePortletModel.setConsumerId(consumerId);
			remotePortletModel.setRemotePortletHandle(portletHandle);
			remotePortletModel.setRemotePortletId(channelName);
			remotePortletModel.setPortletId(portletId);
			remotePortletModel.setPortletName(portletId);
			remotePortletModel.setActive(true);
			remotePortletModel.setTimestamp(System.currentTimeMillis());

			_addPortletInfo(
					remotePortletModel,
					producerEntityId,
					portletHandle);

			_persistRemotePortlet(remotePortletModel);

			PortletLocalServiceUtil.deployRemotePortlet(remotePortletModel);

		} catch (Exception ex) {
			_log.error(ex);
		}
	}

	/**
	 * Provides a list of existing remote portlet channels
	 * @return a List of existing remote channels
	 */
	public List<String> listWSRPChannels() {
		List<String> remotePortlets = new ArrayList<String>();
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		for (Portlet portlet : portlets) {
			if (portlet.isRemote()) {
				remotePortlets.add(portlet.getPortletId());
			}
		}

		return remotePortlets;
	}

	/**
	 * Removes a channel/window for remote portlet.
	 * @param channelName Name for the remote portlet window.
	 */
	public void removeWSRPChannel(String channelName) {
		try {
			Portlet remotePortlet = PortletLocalServiceUtil.
					getPortletById(CompanyConstants.SYSTEM,
					channelName);

			if (!remotePortlet.isRemote()) {
				return;
			}

			PortletLocalServiceUtil.deletePortlet(remotePortlet);
			PortletLocalServiceUtil.destroyPortlet(remotePortlet);

			WSRPPersistenceHelper persistenceHelper =
					WSRPPersistenceHelper.getInstance();

			persistenceHelper.deleteWSRPPortlet(remotePortlet);
		}
		catch (WSRPConsumerException wcex) {
			_log.error(wcex);
		}
		catch (SystemException ex) {
			_log.error(ex);
		}
	}

	/**
	 * Removes a numbers of channel/window for remote portlet.
	 * @param channelNames List of channel names.
	 */
	public void removeWSRPChannels(List<String> channelNames) {
		for (String channelName : channelNames) {
			removeWSRPChannel(channelName);
		}
	}

	private void _addPortletInfo(
			Portlet remotePortlet,
			String producerEntityId,
			String portletHandle)
		throws WSRPConsumerException {

		ProducerEntityManagerFactory pemFactory =
				ProducerEntityManagerFactory.getInstance();

		String portalId = WSRPConfig.getPortalId();

		ProducerEntityManager producerEntityManager =
				pemFactory.getProducerEntityManager(portalId, null);

		ProducerEntity producerEntity =
				producerEntityManager.getProducerEntity(producerEntityId);

		PortletDescription portletDescription =
				producerEntity.getPortletDescription(portletHandle);

		List<MarkupType> markupTypes = portletDescription.getMarkupTypes();
		Map<String, Set<String>> portletModes =
				new HashMap<String, Set<String>>();

		for (MarkupType markupType : markupTypes) {
			Set<String> mimeModes = _getPortalModes(markupType.getModes());
			portletModes.put(markupType.getMimeType(), mimeModes);
		}

		remotePortlet.setPortletModes(portletModes);

		String title = portletDescription.getTitle().getValue();
		if (title == null) {
			title = portletHandle;
		}

		String shortTitle = portletDescription.getShortTitle().getValue();
		if (shortTitle == null) {
			shortTitle = portletHandle;
		}

		List<LocalizedString> keywords = portletDescription.getKeywords();
		String keyword = null;
		if (keywords != null && keywords.size() >= 1) {
			LocalizedString element = keywords.get(0);
			if (element != null) {
				keyword = element.getValue();
			}
		}
		if (keyword == null) {
			keyword = _DEFAULT_KEYWORD;
		}

		LocalizedString displayName = portletDescription.getDisplayName();
		if (displayName != null) {
			String display = displayName.getValue();
			if (display == null) {
				remotePortlet.setDisplayName(portletHandle);
			}
			else {
				remotePortlet.setDisplayName(display);
			}
		}

		PortletInfo portletInfo = new PortletInfo(title, shortTitle, keyword);
		remotePortlet.setPortletInfo(portletInfo);
	}

	private Set<String> _getPortalModes(List<String> modes) {
		Set<String> portalModes = new HashSet<String>();

		//view mode is present by default- Even if its not there
		//assume that view mode exists

		portalModes.add(_VIEW_MODE);

		for (String mode : modes) {
			if (mode.equals(_WSRP_EDIT)) {
				portalModes.add(_EDIT_MODE);
			} else if (mode.equals(_WSRP_VIEW)) {
				portalModes.add(_VIEW_MODE);
			} else if (mode.equals(_WSRP_HELP)) {
				portalModes.add(_HELP_MODE);
			}// Ignore rest of the modes
		}

		return portalModes;
	}

	private void _persistRemotePortlet(Portlet remotePortlet)
			throws WSRPConsumerException {
		WSRPPersistenceHelper persistenceHelper =
				WSRPPersistenceHelper.getInstance();

		persistenceHelper.addWSRPPortlet(remotePortlet);
	}

	private static final String _DEFAULT_KEYWORD = "WSRP";
	private static final String _EDIT_MODE = "edit";
	private static final String _HELP_MODE = "help";
	private static final String _VIEW_MODE = "view";
	private static final String _WSRP_EDIT = "wsrp:edit";
	private static final String _WSRP_VIEW = "wsrp:view";
	private static final String _WSRP_HELP = "wsrp:help";

	private static Log _log = LogFactory.getLog(WSRPChannelManagerImpl.class);

}