/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.job.Scheduler;
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

	public Scheduler getSchedulerInstance();

	public ServletContext getServletContext();

	public URLEncoder getURLEncoderInstance();

	public List<WorkflowHandler> getWorkflowHandlerInstances();

	public void setPortletInstance(Portlet portletInstance);

	public void setPortletName(String portletName);

}