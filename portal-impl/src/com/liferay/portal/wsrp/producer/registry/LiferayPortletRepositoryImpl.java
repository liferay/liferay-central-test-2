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

import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.portletcontainer.WindowInvokerUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.SystemException;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;


public class LiferayPortletRepositoryImpl implements 
		PortletRegistry, ResourceName {

	public LiferayPortletRepositoryImpl() throws ProducerException{
		if (portletsList == null) {
			portletsList = PortletLocalServiceUtil.getPortlets();
		}
		try{
			portletDescHolder = 
					PortletDescriptorHolderFactory.getPortletDescriptorHolder();
		}catch(Exception ex){
			throw new ProducerException(ex);
		}
	}

	public LiferayPortletRepositoryImpl(String portalId, String orgDN)
	 throws ProducerException {
		this();
	}

	public void cloneChannel(
		UserContext uc, String newName, String existingChannel,
			String regHandle) throws ProducerException {
		//Not implemented
	}
	
	public Set<String> getAvailablePortlets() throws ProducerException {
		if (portletsList == null) {
			portletsList = PortletLocalServiceUtil.getPortlets();
		}
		
		Set<String> portletIDSet = new HashSet<String>();
		for (Portlet portlet : portletsList) {
			String id = portlet.getPortletId();
			if (portlet.getPortletApp().isWARFile()) {
				portletIDSet.add(id);
			}
		}
		return portletIDSet;
	}

	public List<ItemDescription> getCustomModeDescriptions()
	 	throws ProducerException {
		//TODO:
		return new ArrayList<ItemDescription>();
	}
	
	public List<ItemDescription> getCustomWindowStateDescriptions()
		throws ProducerException {
		//TODO:to get Description from Liferay
		return new ArrayList<ItemDescription>();
	}
	
	public Boolean getDefaultSecureMarkup(String portletName)
		throws ProducerException {
		return Boolean.FALSE;
	}	
	
	public List<LocalizedString> getDescription(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {
		//TODO: Change Liferay Portlet model file to provide description
		return new ArrayList<LocalizedString>();
	}

	public List<LocalizedString> getDisplayName(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		List<LocalizedString> result = new ArrayList<LocalizedString>();
		LocalizedString ls = null;
		String displayName = null;
		String resourceName = 
				portletName + SEPARATOR + DISPLAY_NAME + SEPARATOR;

		try {
			displayName = getPortlet(portletName).getDisplayName();
		} 
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}
		if (displayName != null) {
			ls = new LocalizedString();
			ls.setResourceName(resourceName + displayName);
			ls.setValue(displayName);
			//TODO: get the locale after getLocales is implemented in LR
			ls.setLang(WSRPUtility.toXMLLang(getDefaultLocale().toString()));
			result.add(ls);
		}
		return result;
	}
		
	public Locale getDefaultLocale() throws ProducerException {
		return Locale.getDefault();
	}

	public List<EventHolder> getHandledEvents(String portletName, String locale) 
			throws ProducerException {
				
		EntityID eid = getPortletEntityID(portletName);

		List<EventHolder> eventHolders =
                portletDescHolder.getSupportedProcessingEventHolders(eid);

		return eventHolders;
	}
	
	public List<LocalizedString> getKeywords(
		String portletName, Set<String> desiredLocales)
		 	throws ProducerException {

		List<LocalizedString> result = new ArrayList<LocalizedString>();

		String keywords = null;
		String resourcePrefix = portletName + SEPARATOR + KEYWORDS + SEPARATOR;
		String resourceName = null;
		LocalizedString ls = null;
		int i = 0;

		try {
			keywords = getPortlet(portletName).getPortletInfo().getKeywords();
		} 
		catch (Exception ex) {
			throw new ProducerException(ex);
		}
		//TODO: Fix this resourceName
		if (keywords != null) {
			resourceName = resourcePrefix + keywords + SEPARATOR + i;
			ls = new LocalizedString();
			ls.setResourceName(resourceName);
			ls.setValue(keywords);
			//TODO: get the locale after getLocales is implemented in LR
			ls.setLang(WSRPUtility.toXMLLang(getDefaultLocale().toString()));
			result.add(ls);
			i++;
		}

		if (result.size() == 0) {
			return null;
		}
		return result;
	}		
	
	public List<MarkupType> getMarkupTypesSet(String portletName)
		throws ProducerException {

		List returnValue = new ArrayList<MarkupType>();
		MarkupType markupType = null;

		Map<String, Set<String>> portletModesMap;
		try {
			portletModesMap = getPortlet(portletName).getPortletModes();
		} 
		catch (Exception ex) {
			throw new ProducerException(ex);
		}
		
		for (String strMarkupType : portletModesMap.keySet()) {
			markupType = new MarkupType();

			List w_states = markupType.getWindowStates();
			List modes = markupType.getModes();
			modes.addAll(Arrays.asList(pc_modes));
			w_states.addAll(Arrays.asList(pc_states));
			markupType.setMimeType(strMarkupType);
			returnValue.add(markupType);
		}

		return returnValue;
	}
	
	public List<ParameterDescription> getNavigationalPublicParameters(
		String portletName, String locale) throws ProducerException {
		
		List<ParameterDescription> prpList = 
				new ArrayList<ParameterDescription>();
		
		EntityID entityId = getPortletEntityID(portletName);
			
		List<PublicRenderParameterHolder> pdHolders = 
				portletDescHolder.getSupportedPublicRenderParameterHolders(
					entityId, null);
		
		for (PublicRenderParameterHolder paramHolder : pdHolders) {

			ParameterDescription pd = new ParameterDescription();

			pd.setIdentifier(paramHolder.getIdentifier());
			pd.getNames().add(paramHolder.getQName());
			pd.getNames().addAll(paramHolder.getAliases());

			prpList.add(pd);
		}
		
		return prpList;
	}
	
	public PortletPreferences getPortletPreferences(
			String portletName) throws ProducerException {
		return null;
	}
	
	private Portlet getPortlet(String portletName) throws SystemException{
		return PortletLocalServiceUtil.getPortletById(
						CompanyConstants.SYSTEM, portletName);
	}
	
	public EntityID getPortletEntityID(String portletName) 
			throws ProducerException {
		
		try {		
			return WindowInvokerUtil.getEntityID(getPortlet(portletName));
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

		EntityID eid = getPortletEntityID(portletName);
		
		List<EventHolder> publishedEvents = 
				portletDescHolder.getSupportedPublishingEventHolders(eid);
				
		return publishedEvents;
	}

	public List<LocalizedString> getShortTitle(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {

		List<LocalizedString> result = new ArrayList<LocalizedString>();
		LocalizedString ls = null;
		String sTitle = null;
		String resourceName = portletName + SEPARATOR + SHORT_TITLE + SEPARATOR;

		try {
			sTitle = getPortlet(portletName).getPortletInfo().getShortTitle();
		} 
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}
		ls = new LocalizedString();
		ls.setResourceName(resourceName + sTitle);
		ls.setValue(sTitle);
		//TODO: get the locale after getLocales is implemented in LR
		ls.setLang(WSRPUtility.toXMLLang(getDefaultLocale().toString()));
		result.add(ls);

		return result;
	}
	
	public List<LocalizedString> getTitle(String portletName,
		Set<String> desiredLocales) throws ProducerException {

		List<LocalizedString> result = new ArrayList<LocalizedString>();
		LocalizedString ls = null;
		String title = null;
		String resourceName = portletName + SEPARATOR + TITLE + SEPARATOR;

		try {
			title = getPortlet(portletName).getPortletInfo().getTitle();
		} 
		catch (SystemException sException) {
			throw new ProducerException(sException);
		}		
		
		ls = new LocalizedString();
		ls.setResourceName(resourceName + title);
		//TODO: get the locale after getLocales is implemented in LR
		ls.setLang(WSRPUtility.toXMLLang(getDefaultLocale().toString()));
		ls.setValue(title);
		result.add(ls);
		
		return result;
	}
	
	public List<ItemDescription> getUserCategories(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {
		Map roles = null;
		try {
			roles = getPortlet(portletName).getRoleMappers();
		} 
		catch (Exception ex) {
			throw new ProducerException(ex);
		}
		if (roles == null || roles.size() == 0) {
			return null;
		}

		ItemDescription desc = null;
		LocalizedString ls = null;
		String key = null;
		List<ItemDescription> result = new ArrayList<ItemDescription>();
		String resourcePrefix = 
				portletName + SEPARATOR + ROLE_DESCRIPTION + SEPARATOR;
		
		Iterator it = roles.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			key = it.next().toString();
			ls = new LocalizedString();
			ls.setLang(WSRPUtility.toXMLLang(getDefaultLocale().toString()));
			ls.setResourceName(resourcePrefix + key);
			ls.setValue(roles.get(key).toString());
			desc = new ItemDescription();
			desc.setDescription(ls);
			desc.setItemName(key);
			result.add(desc);
			i++;
		}
		return result;
	}
	
	public List<ItemDescription> getUserProfileItems(
		String portletName, Set<String> desiredLocales)
			throws ProducerException {
		//TODO:to get Description from Liferay
		return new ArrayList<ItemDescription>();
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
		throw new UnsupportedOperationException("Not supported yet.");
	}
		
	
	private List<Portlet> portletsList = null;

	private PortletDescriptorHolder portletDescHolder = null;
	private static final String[] pc_modes = 
			new String[]{"wsrp:view", "wsrp:help", "wsrp:edit"};
	
	private static final String[] pc_states = 
			new String[]{"wsrp:maximized", "wsrp:minimized", "wsrp:normal"};

}