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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * <a href="PortletBag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface PortletBag extends Cloneable {

	public Object clone();

	public ConfigurationAction getConfigurationActionInstance();

	public FriendlyURLMapper getFriendlyURLMapperInstance();

	public Indexer getIndexerInstance();

	public OpenSearch getOpenSearchInstance();

	public PollerProcessor getPollerProcessorInstance();

	public MessageListener getPopMessageListenerInstance();

	public Portlet getPortletInstance();

	public PortletLayoutListener getPortletLayoutListenerInstance();

	public String getPortletName();

	public PreferencesValidator getPreferencesValidatorInstance();

	public ResourceBundle getResourceBundle(Locale locale);

	public Map<String, ResourceBundle> getResourceBundles();

	public ServletContext getServletContext();

	public URLEncoder getURLEncoderInstance();

	public List<WorkflowHandler> getWorkflowHandlerInstances();

	public void setPortletInstance(Portlet portletInstance);

	public void setPortletName(String portletName);

}