/*
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

package com.liferay.portlet;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.ThreadLocalDataSampleCache;
import com.liferay.portal.monitoring.statistics.portlet.PortletRequestDataSample;
import com.liferay.portal.monitoring.statistics.portlet.PortletRequestType;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * <a href="InstrumentedInvokerPortletImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Karthik Sudarshan
 */
public class InstrumentedInvokerPortletImpl implements InvokerPortlet {

	public static boolean isMonitorActionActive() {
		return _monitorActionActive;
	}

	public static boolean isMonitorEventActive() {
		return _monitorEventActive;
	}

	public static boolean isMonitorRenderActive() {
		return _monitorRenderActive;
	}

	public static boolean isMonitorResourceActive() {
		return _monitorResourceActive;
	}

	public static void setMonitorActionActive(boolean monitorActionActive) {
		_monitorActionActive = monitorActionActive;
	}

	public static void setMonitorEventActive(boolean monitorEventActive) {
		_monitorEventActive = monitorEventActive;
	}

	public static void setMonitorRenderActive(boolean monitorRenderActive) {
		_monitorRenderActive = monitorRenderActive;
	}

	public static void setMonitorResourceActive(boolean monitorResourceActive) {
		_monitorResourceActive = monitorResourceActive;
	}

	public InstrumentedInvokerPortletImpl(
			InvokerPortlet delegate,
			SingleDestinationMessageSender messageSender) {

		_delegate = delegate;

		_messageSender = messageSender;

		_monitorActionActive = PropsValues.MONITORING_PORTLET_ACTION_ENABLED;
		_monitorEventActive = PropsValues.MONITORING_PORTLET_EVENT_ENABLED;
		_monitorRenderActive = PropsValues.MONITORING_PORTLET_RENDER_ENABLED;
		_monitorResourceActive = PropsValues.MONITORING_PORTLET_RESOURCE_ENABLED;
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		InvokerPortlet delegateClone =
			_delegate.create(portletModel, portlet, portletContext);

		InstrumentedInvokerPortletImpl clone =
			new InstrumentedInvokerPortletImpl(delegateClone, _messageSender);

		clone.prepare(portletModel, portlet, portletContext);

		return clone;
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		InvokerPortlet delegateClone =
			_delegate.create(portletModel, portlet, portletContext);

		InstrumentedInvokerPortletImpl clone =
			new InstrumentedInvokerPortletImpl(delegateClone, _messageSender);

		clone.prepare(
			portletModel, portlet, portletConfig, portletContext,
			facesPortlet, strutsPortlet, strutsBridgePortlet);

		return clone;
	}

	public void destroy() {
		_delegate.destroy();
	}

	public Integer getExpCache() {
		return _delegate.getExpCache();
	}

	public ClassLoader getPortletClassLoader() {
		return _delegate.getPortletClassLoader();
	}

	public PortletConfig getPortletConfig() {
		return _delegate.getPortletConfig();
	}

	public PortletContext getPortletContext() {
		return _delegate.getPortletContext();
	}

	public Portlet getPortletInstance() {
		return _delegate.getPortletInstance();
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		_delegate.init(portletConfig);
	}

	public boolean isDestroyable() {
		return _delegate.isDestroyable();
	}

	public boolean isFacesPortlet() {
		return _delegate.isFacesPortlet();
	}

	public boolean isStrutsBridgePortlet() {
		return _delegate.isStrutsBridgePortlet();
	}

	public boolean isStrutsPortlet() {
		return _delegate.isStrutsPortlet();
	}

	public void prepare(
		com.liferay.portal.model.Portlet portletModel, Portlet portlet,
		PortletContext portletContext)
		throws PortletException {
		_delegate.prepare(
			portletModel, portlet, portletContext);
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		_delegate.prepare(
			portletModel, portlet, portletConfig, portletContext,
			facesPortlet, strutsPortlet, strutsBridgePortlet);
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException, IOException {

		final boolean activateMonitoring = isMonitorActionActive();

		PortletRequestDataSample portletRequestSample = null;

		if (activateMonitoring) {
			portletRequestSample = new PortletRequestDataSample(
				PortletRequestType.ACTION,
				(LiferayPortletRequest) actionRequest,
				(LiferayPortletResponse) actionResponse);

			portletRequestSample.prepare();
		}

		try {
			_delegate.processAction(actionRequest, actionResponse);

			if (activateMonitoring) {
				portletRequestSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(activateMonitoring, portletRequestSample, e);
		}
		finally {
			if (portletRequestSample != null) {
				Message message = new Message();
				message.setPayload(portletRequestSample);
				_messageSender.send(message);
				ThreadLocalDataSampleCache.add(portletRequestSample);
			}
		}
	}

	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws PortletException, IOException {

		final boolean activateMonitoring = isMonitorEventActive();

		PortletRequestDataSample portletRequestSample = null;

		if (activateMonitoring) {
			portletRequestSample = new PortletRequestDataSample(
				PortletRequestType.EVENT,
				(LiferayPortletRequest) eventRequest,
				(LiferayPortletResponse) eventResponse);

			portletRequestSample.prepare();
		}

		try {
			_delegate.processEvent(eventRequest, eventResponse);

			if (activateMonitoring) {
				portletRequestSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(activateMonitoring, portletRequestSample, e);
		}
		finally {
			if (portletRequestSample != null) {
				Message message = new Message();
				message.setPayload(portletRequestSample);
				_messageSender.send(message);
				ThreadLocalDataSampleCache.add(portletRequestSample);
			}
		}

	}

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException, IOException {

		final boolean activateMonitoring = isMonitorRenderActive();

		PortletRequestDataSample portletRequestSample = null;

		if (activateMonitoring) {
			portletRequestSample = new PortletRequestDataSample(
				PortletRequestType.EVENT,
				(LiferayPortletRequest) renderRequest,
				(LiferayPortletResponse) renderResponse);

			portletRequestSample.prepare();
		}

		try {
			_delegate.render(renderRequest, renderResponse);

			if (activateMonitoring) {
				portletRequestSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(activateMonitoring, portletRequestSample, e);
		}
		finally {
			if (portletRequestSample != null) {
				Message message = new Message();
				message.setPayload(portletRequestSample);
				_messageSender.send(message);
				ThreadLocalDataSampleCache.add(portletRequestSample);
			}
		}

	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		final boolean activateMonitoring = isMonitorResourceActive();

		PortletRequestDataSample portletRequestSample = null;

		if (activateMonitoring) {
			portletRequestSample = new PortletRequestDataSample(
				PortletRequestType.EVENT,
				(LiferayPortletRequest) resourceRequest,
				(LiferayPortletResponse) resourceResponse);

			portletRequestSample.prepare();
		}

		try {
			_delegate.serveResource(resourceRequest, resourceResponse);

			if (activateMonitoring) {
				portletRequestSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(activateMonitoring, portletRequestSample, e);
		}
		finally {
			if (portletRequestSample != null) {
				Message message = new Message();
				message.setPayload(portletRequestSample);
				_messageSender.send(message);
				ThreadLocalDataSampleCache.add(portletRequestSample);
			}
		}

	}

	private void _processException(
			boolean activateMonitoring,
			PortletRequestDataSample portletRequestSample, Exception e)
		throws IOException, PortletException {

		if (activateMonitoring) {
			portletRequestSample.capture(RequestStatus.ERROR);
		}

		if (e instanceof IOException) {
			throw (IOException) e;
		}
		else if (e instanceof PortletException) {
			throw (PortletException) e;
		}
		else {
			throw new PortletException("Unable to process portlet", e);
		}
	}

	public void setPortletFilters() throws PortletException {
		_delegate.setPortletFilters();
	}

	private static boolean _monitorActionActive =
		PropsValues.MONITORING_PORTLET_ACTION_ENABLED;
	private static boolean _monitorEventActive =
		PropsValues.MONITORING_PORTLET_EVENT_ENABLED;
	private static boolean _monitorRenderActive =
		PropsValues.MONITORING_PORTLET_RENDER_ENABLED;
	private static boolean _monitorResourceActive =
		PropsValues.MONITORING_PORTLET_RESOURCE_ENABLED;

	private InvokerPortlet _delegate;
	private SingleDestinationMessageSender _messageSender;
}
