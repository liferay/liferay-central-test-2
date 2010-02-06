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

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * <a href="FilterChainImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FilterChainImpl implements FilterChain {

	public FilterChainImpl(
		Portlet portlet, List<? extends PortletFilter> portletFilters) {

		_portlet = portlet;

		if (portletFilters != null) {
			_portletFilters = portletFilters;
		}
		else {
			_portletFilters = Collections.EMPTY_LIST;
		}
	}

	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			ActionFilter portletFilter = (ActionFilter)_portletFilters.get(
				_pos++);

			portletFilter.doFilter(actionRequest, actionResponse, this);
		}
		else {
			_portlet.processAction(actionRequest, actionResponse);
		}
	}

	public void doFilter(EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			EventFilter portletFilter = (EventFilter)_portletFilters.get(
				_pos++);

			portletFilter.doFilter(eventRequest, eventResponse, this);
		}
		else {
			EventPortlet eventPortlet = (EventPortlet)_portlet;

			eventPortlet.processEvent(eventRequest, eventResponse);
		}
	}

	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			RenderFilter portletFilter = (RenderFilter)_portletFilters.get(
				_pos++);

			portletFilter.doFilter(renderRequest, renderResponse, this);
		}
		else {
			_portlet.render(renderRequest, renderResponse);
		}
	}

	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			ResourceFilter portletFilter = (ResourceFilter)_portletFilters.get(
				_pos++);

			portletFilter.doFilter(resourceRequest, resourceResponse, this);
		}
		else {
			ResourceServingPortlet resourceServingPortlet =
				(ResourceServingPortlet)_portlet;

			resourceServingPortlet.serveResource(
				resourceRequest, resourceResponse);
		}
	}

	private Portlet _portlet;
	private List<? extends PortletFilter> _portletFilters;
	private int _pos;

}