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

package com.liferay.portal.wsrp.producer.registry;

import com.liferay.portal.SystemException;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.portletcontainer.WindowInvokerUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;

import com.sun.portal.container.EntityID;
import com.sun.portal.container.service.EventHolder;
import com.sun.portal.container.service.PortletDescriptorHolder;
import com.sun.portal.container.service.PortletDescriptorHolderFactory;
import com.sun.portal.container.service.PublicRenderParameterHolder;
import com.sun.portal.wsrp.common.WSRPUtility;
import com.sun.portal.wsrp.common.stubs.v2.ItemDescription;
import com.sun.portal.wsrp.common.stubs.v2.LocalizedString;
import com.sun.portal.wsrp.common.stubs.v2.MarkupType;
import com.sun.portal.wsrp.common.stubs.v2.ParameterDescription;
import com.sun.portal.wsrp.common.stubs.v2.PropertyList;
import com.sun.portal.wsrp.common.stubs.v2.UserContext;
import com.sun.portal.wsrp.producer.ProducerException;
import com.sun.portal.wsrp.producer.driver.PortletRegistry;
import com.sun.portal.wsrp.producer.driver.ResourceName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * <a href="PortletRepositoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Manish Gupta
 * @author Nithya Subramanian
 */

public class PortletRepositoryImpl implements PortletRegistry, ResourceName {

	public PortletRepositoryImpl() throws ProducerException{
		if (_portlets == null) {
			_portlets = PortletLocalServiceUtil.getPortlets();
		}
		try {
			_portletDescriptionHolder =
					PortletDescriptorHolderFactory.getPortletDescriptorHolder();
		}
		catch(Exception ex) {
			throw new ProducerException(ex);
		}
	}

	public PortletRepositoryImpl(String portalId, String orgDN)
		throws ProducerException {

		this();
	}

	public void cloneChannel(
			UserContext uc, String newName, String existingChannel,
				String regHandle) throws ProducerException {
		//Not implemented
	}

	public Set<String> getAvailablePortlets() throws ProducerException {
		if (_portlets == null) {
			_portlets = PortletLocalServiceUtil.getPortlets();
		}

		Set<String> availablePortlets = new HashSet<String>();

		for (Portlet portlet : _portlets) {
			String portletId = portlet.getPortletId();

			if (portlet.getPortletApp().isWARFile()) {
				availablePortlets.add(portletId);
			}
		}

		return availablePortlets;
	}

	public List<ItemDescription> getCustomModeDescriptions()
		throws ProducerException {

		return Collections.EMPTY_LIST;
	}

	public List<ItemDescription> getCustomWindowStateDescriptions()
		throws ProducerException {

		return Collections.EMPTY_LIST;
	}

	public Boolean getDefaultSecureMarkup(String portletName)
		throws ProducerException {

		return Boolean.FALSE;
	}

	public List<LocalizedString> getDescription(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		return Collections.EMPTY_LIST;
	}

	public List<LocalizedString> getDisplayName(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		List<LocalizedString> displayNames = new ArrayList<LocalizedString>();
		String displayName = null;

		try {
			displayName = _getPortlet(portletName).getDisplayName();
		}
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}

		if (displayName != null) {
			String resourceNamePrefix =
				portletName + SEPARATOR + DISPLAY_NAME + SEPARATOR;

			LocalizedString localizedString = new LocalizedString();
			localizedString.setResourceName(resourceNamePrefix + displayName);
			localizedString.setValue(displayName);
			localizedString.setLang(
					WSRPUtility.toXMLLang(getDefaultLocale().toString()));
			displayNames.add(localizedString);
		}

		return displayNames;
	}

	public Locale getDefaultLocale() throws ProducerException {
		return Locale.getDefault();
	}

	public List<EventHolder> getHandledEvents(String portletName, String locale)
		throws ProducerException {

		EntityID entityID = getPortletEntityID(portletName);

		List<EventHolder> eventHolders =
			_portletDescriptionHolder.getSupportedProcessingEventHolders(
					entityID);

		return eventHolders;
	}

	public List<LocalizedString> getKeywords(
			String portletName, Set<String> desiredLocales)
				throws ProducerException {

		String keywords = null;

		try {
			keywords = _getPortlet(portletName).getPortletInfo().getKeywords();
		}
		catch (Exception ex) {
			throw new ProducerException(ex);
		}

		if (keywords == null) {
			return Collections.EMPTY_LIST;
		}

		List<LocalizedString> result = new ArrayList<LocalizedString>();

		String resourceName =
				portletName + SEPARATOR + KEYWORDS + SEPARATOR +  keywords;

		LocalizedString localizedString = new LocalizedString();

		localizedString.setResourceName(resourceName);
		localizedString.setValue(keywords);
		localizedString.setLang(
				WSRPUtility.toXMLLang(getDefaultLocale().toString()));

		result.add(localizedString);

		return result;
	}

	public List<MarkupType> getMarkupTypesSet(String portletName)
		throws ProducerException {

		Map<String, Set<String>> portletModesMap = null;

		try {
			portletModesMap = _getPortlet(portletName).getPortletModes();
		}
		catch (Exception ex) {
			throw new ProducerException(ex);
		}

		List markupTypes = new ArrayList<MarkupType>();
		MarkupType markupType = null;

		for (String mimeType : portletModesMap.keySet()) {
			markupType = new MarkupType();

			markupType.getWindowStates().addAll(_states);
			markupType.getModes().addAll(_modes);
			markupType.setMimeType(mimeType);

			markupTypes.add(markupType);
		}

		return markupTypes;
	}

	public List<ParameterDescription> getNavigationalPublicParameters(
		String portletName, String locale) throws ProducerException {

		List<ParameterDescription> publicRenderParamList =
				new ArrayList<ParameterDescription>();

		EntityID entityId = getPortletEntityID(portletName);

		List<PublicRenderParameterHolder> publicRenderParams =
				_portletDescriptionHolder.
					getSupportedPublicRenderParameterHolders( entityId, null);

		for (PublicRenderParameterHolder paramHolder : publicRenderParams) {

			ParameterDescription parameterDescription =
					new ParameterDescription();

			parameterDescription.setIdentifier(paramHolder.getIdentifier());
			parameterDescription.getNames().add(paramHolder.getQName());
			parameterDescription.getNames().addAll(paramHolder.getAliases());

			publicRenderParamList.add(parameterDescription);
		}

		return publicRenderParamList;
	}

	public PortletPreferences getPortletPreferences(
		String portletName) throws ProducerException {

		return null;
	}

	public EntityID getPortletEntityID(String portletName)
		throws ProducerException {

		try {
			return WindowInvokerUtil.getEntityID(_getPortlet(portletName));
		}
		catch(Exception e) {
			throw new ProducerException(e);
		}
	}

	public Map getPortletResourceMap(
		String portletName) throws ProducerException {

		return null;
	}

	public List<EventHolder> getPublishedEvents(
		String portletName, String locale) throws ProducerException {

		EntityID entityID = getPortletEntityID(portletName);

		List<EventHolder> publishedEvents =
				_portletDescriptionHolder.getSupportedPublishingEventHolders(
					entityID);

		return publishedEvents;
	}

	public List<LocalizedString> getShortTitle(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		String sTitle = null;

		try {
			sTitle = _getPortlet(portletName).getPortletInfo().getShortTitle();
		}
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}

		List<LocalizedString> shortTitles = new ArrayList<LocalizedString>();
		String resourceNamePrefix =
				portletName + SEPARATOR + SHORT_TITLE + SEPARATOR;

		LocalizedString localizedString = new LocalizedString();
		localizedString.setResourceName(resourceNamePrefix + sTitle);
		localizedString.setValue(sTitle);
		localizedString.setLang(
				WSRPUtility.toXMLLang(getDefaultLocale().toString()));

		shortTitles.add(localizedString);

		return shortTitles;
	}

	public List<LocalizedString> getTitle(String portletName,
		Set<String> desiredLocales) throws ProducerException {

		String title = null;
		try {
			title = _getPortlet(portletName).getPortletInfo().getTitle();
		}
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}

		List<LocalizedString> titles = new ArrayList<LocalizedString>();

		String resourceNamePrefix = portletName + SEPARATOR + TITLE + SEPARATOR;

		LocalizedString localizedString = new LocalizedString();
		localizedString.setResourceName(resourceNamePrefix + title);
		localizedString.setLang(
				WSRPUtility.toXMLLang(getDefaultLocale().toString()));

		localizedString.setValue(title);

		titles.add(localizedString);
		return titles;
	}

	public List<ItemDescription> getUserCategories(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		Map<String,String> roles = null;
		try {
			roles = _getPortlet(portletName).getRoleMappers();
		}
		catch (Exception ex) {
			throw new ProducerException(ex);
		}
		if (roles == null || roles.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<ItemDescription> userCategories = new ArrayList<ItemDescription>();
		String resourcePrefix =
				portletName + SEPARATOR + ROLE_DESCRIPTION + SEPARATOR;

		int i = 0;
		ItemDescription itemDescription = null;
		LocalizedString localizedString = null;

		for(String key : roles.keySet()){
			localizedString = new LocalizedString();
			localizedString.setLang(
					WSRPUtility.toXMLLang(getDefaultLocale().toString()));

			localizedString.setResourceName(
					resourcePrefix + key + SEPARATOR + i);

			localizedString.setValue(roles.get(key));

			itemDescription = new ItemDescription();
			itemDescription.setDescription(localizedString);
			itemDescription.setItemName(key);

			userCategories.add(itemDescription);
			i++;
		}
		return userCategories;
	}

	public List<ItemDescription> getUserProfileItems(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		return Collections.EMPTY_LIST;
	}

	public boolean isCloneSupported() {
		return false;
	}

	public void removeChannel(String regHandle, String portletName)
		throws ProducerException {
		//Not implemented
	}

	public void setPortletProperties(
		UserContext uc, PropertyList pList, String portletHandle,
			String regHandle) throws ProducerException {

		throw new UnsupportedOperationException("Not yet supported.");
	}

	private Portlet _getPortlet(String portletName) throws SystemException{

		return PortletLocalServiceUtil.getPortletById(
					CompanyConstants.SYSTEM, portletName);
	}

	private List<Portlet> _portlets = null;

	private PortletDescriptorHolder _portletDescriptionHolder = null;

	private static final List<String> _modes =
		Arrays.asList(new String[]{"wsrp:view", "wsrp:help", "wsrp:edit"});

	private static final List<String> _states =
		Arrays.asList(
			new String[]{"wsrp:maximized", "wsrp:minimized", "wsrp:normal"});

}