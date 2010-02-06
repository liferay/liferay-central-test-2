/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleThreadLocal;
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
 * <a href="MonitoringPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Karthik Sudarshan
 */
public class MonitoringPortlet implements InvokerPortlet {

	public static boolean isMonitoringPortletActionRequest() {
		return _monitoringPortletActionRequest;
	}

	public static boolean isMonitoringPortletEventRequest() {
		return _monitoringPortletEventRequest;
	}

	public static boolean isMonitoringPortletRenderRequest() {
		return _monitoringPortletRenderRequest;
	}

	public static boolean isMonitoringPortletResourceRequest() {
		return _monitoringPortletResourceRequest;
	}

	public static void setMonitoringPortletActionRequest(
		boolean monitoringPortletActionRequest) {

		_monitoringPortletActionRequest = monitoringPortletActionRequest;
	}

	public static void setMonitoringPortletEventRequest(
		boolean monitoringPortletEventRequest) {

		_monitoringPortletEventRequest = monitoringPortletEventRequest;
	}

	public static void setMonitoringPortletRenderRequest(
		boolean monitoringPortletRenderRequest) {

		_monitoringPortletRenderRequest = monitoringPortletRenderRequest;
	}

	public static void setMonitoringPortletResourceRequest(
		boolean monitoringPortletResourceRequest) {

		_monitoringPortletResourceRequest = monitoringPortletResourceRequest;
	}

	public MonitoringPortlet() {
	}

	public MonitoringPortlet(
		InvokerPortlet invokerPortlet,
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_invokerPortlet = invokerPortlet;
		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		InvokerPortlet invokerPortlet = _invokerPortlet.create(
			portletModel, portlet, portletContext);

		MonitoringPortlet monitoringPortlet = new MonitoringPortlet(
			invokerPortlet, _singleDestinationMessageSender);

		monitoringPortlet.prepare(
			portletModel, portlet, portletConfig, portletContext, facesPortlet,
			strutsPortlet, strutsBridgePortlet);

		return monitoringPortlet;
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		InvokerPortlet invokerPortlet = _invokerPortlet.create(
			portletModel, portlet, portletContext);

		MonitoringPortlet monitoringPortlet = new MonitoringPortlet(
			invokerPortlet, _singleDestinationMessageSender);

		monitoringPortlet.prepare(portletModel, portlet, portletContext);

		return monitoringPortlet;
	}

	public void destroy() {
		_invokerPortlet.destroy();
	}

	public Integer getExpCache() {
		return _invokerPortlet.getExpCache();
	}

	public Portlet getPortlet() {
		return _invokerPortlet.getPortlet();
	}

	public ClassLoader getPortletClassLoader() {
		return _invokerPortlet.getPortletClassLoader();
	}

	public PortletConfig getPortletConfig() {
		return _invokerPortlet.getPortletConfig();
	}

	public PortletContext getPortletContext() {
		return _invokerPortlet.getPortletContext();
	}

	public Portlet getPortletInstance() {
		return _invokerPortlet.getPortletInstance();
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		_invokerPortlet.init(portletConfig);
	}

	public boolean isDestroyable() {
		return _invokerPortlet.isDestroyable();
	}

	public boolean isFacesPortlet() {
		return _invokerPortlet.isFacesPortlet();
	}

	public boolean isStrutsBridgePortlet() {
		return _invokerPortlet.isStrutsBridgePortlet();
	}

	public boolean isStrutsPortlet() {
		return _invokerPortlet.isStrutsPortlet();
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		_invokerPortlet.prepare(
			portletModel, portlet, portletConfig, portletContext, facesPortlet,
			strutsPortlet, strutsBridgePortlet);
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		_invokerPortlet.prepare(portletModel, portlet, portletContext);
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletActionRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.ACTION, actionRequest, actionResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.processAction(actionRequest, actionResponse);

			if (_monitoringPortletActionRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletActionRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletEventRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.EVENT, eventRequest, eventResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.processEvent(eventRequest, eventResponse);

			if (_monitoringPortletEventRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletEventRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletRenderRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.RENDER, renderRequest, renderResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.render(renderRequest, renderResponse);

			if (_monitoringPortletRenderRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletRenderRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		PortletRequestDataSample portletRequestDataSample = null;

		try {
			if (_monitoringPortletResourceRequest) {
				portletRequestDataSample = new PortletRequestDataSample(
					PortletRequestType.RESOURCE, resourceRequest,
					resourceResponse);

				portletRequestDataSample.prepare();
			}

			_invokerPortlet.serveResource(resourceRequest, resourceResponse);

			if (_monitoringPortletResourceRequest) {
				portletRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			_processException(
				_monitoringPortletResourceRequest, portletRequestDataSample, e);
		}
		finally {
			if (portletRequestDataSample != null) {
				_singleDestinationMessageSender.send(portletRequestDataSample);

				DataSampleThreadLocal.addDataSample(portletRequestDataSample);
			}
		}
	}

	public void setInvokerPortlet(InvokerPortlet invokerPortlet) {
		_invokerPortlet = invokerPortlet;
	}

	public void setPortletFilters() throws PortletException {
		_invokerPortlet.setPortletFilters();
	}

	public void setSingleDestinationMessageSender(
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	private void _processException(
			boolean monitoringPortletRequest,
			PortletRequestDataSample portletRequestDataSample, Exception e)
		throws IOException, PortletException {

		if (monitoringPortletRequest) {
			portletRequestDataSample.capture(RequestStatus.ERROR);
		}

		if (e instanceof IOException) {
			throw (IOException)e;
		}
		else if (e instanceof PortletException) {
			throw (PortletException)e;
		}
		else {
			throw new PortletException("Unable to process portlet", e);
		}
	}

	private static boolean _monitoringPortletActionRequest =
		PropsValues.MONITORING_PORTLET_ACTION_REQUEST;
	private static boolean _monitoringPortletEventRequest =
		PropsValues.MONITORING_PORTLET_EVENT_REQUEST;
	private static boolean _monitoringPortletRenderRequest =
		PropsValues.MONITORING_PORTLET_RENDER_REQUEST;
	private static boolean _monitoringPortletResourceRequest =
		PropsValues.MONITORING_PORTLET_RESOURCE_REQUEST;

	private InvokerPortlet _invokerPortlet;
	private SingleDestinationMessageSender _singleDestinationMessageSender;

}