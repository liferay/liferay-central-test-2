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

package com.liferay.portal.portletcontainer;

import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletSessionImpl;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLFactory;
import com.sun.portal.container.Container;
import com.sun.portal.container.ContainerFactory;
import com.sun.portal.container.ContainerRequest;
import com.sun.portal.container.ContainerType;
import com.sun.portal.container.EntityID;
import com.sun.portal.container.ExecuteActionRequest;
import com.sun.portal.container.GetMarkupRequest;
import com.sun.portal.container.GetResourceRequest;
import com.sun.portal.container.PortletWindowContext;
import com.sun.portal.container.WindowRequestReader;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ContainerRequestFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 *
 */
public class ContainerRequestFactory {

	static List<ChannelMode> portletModes = new ArrayList<ChannelMode>();
	static List<ChannelState> windowStates = new ArrayList<ChannelState>();

	static {
		portletModes.add(ChannelMode.EDIT);
		portletModes.add(ChannelMode.HELP);
		portletModes.add(ChannelMode.VIEW);
		portletModes.add(
			new ChannelMode(LiferayPortletMode.ABOUT.toString()));
		portletModes.add(
			new ChannelMode(LiferayPortletMode.CONFIG.toString()));
		portletModes.add(
			new ChannelMode(LiferayPortletMode.EDIT_DEFAULTS.toString()));
		portletModes.add(
			new ChannelMode(LiferayPortletMode.PREVIEW.toString()));
		portletModes.add(
			new ChannelMode(LiferayPortletMode.PRINT.toString()));

		windowStates.add(ChannelState.MAXIMIZED);
		windowStates.add(ChannelState.MINIMIZED);
		windowStates.add(ChannelState.NORMAL);
		windowStates.add(
			new ChannelState(LiferayWindowState.EXCLUSIVE.toString()));
		windowStates.add(
			new ChannelState(LiferayWindowState.POP_UP.toString()));
	}

	public static ExecuteActionRequest createExecuteActionRequest(
			HttpServletRequest request, Portlet portlet,
			WindowState windowState, PortletMode portletMode,
			long plid, boolean facesPortlet, boolean remotePortlet)
		throws Exception {

		return (ExecuteActionRequest)_createContainerRequest(
			request, portlet, windowState, portletMode, plid,
			facesPortlet, remotePortlet, PortletRequest.ACTION_PHASE);
	}

	public static GetMarkupRequest createGetMarkUpRequest(
			HttpServletRequest request, Portlet portlet,
			WindowState windowState, PortletMode portletMode,
			long plid, boolean facesPortlet, boolean remotePortlet)
		throws Exception {

		return (GetMarkupRequest)_createContainerRequest(
			request, portlet, windowState, portletMode, plid,
			facesPortlet, remotePortlet, PortletRequest.RENDER_PHASE);
	}

	public static GetResourceRequest createGetResourceRequest(
			HttpServletRequest request, Portlet portlet,
			WindowState windowState, PortletMode portletMode,
			long plid, boolean facesPortlet, boolean remotePortlet)
		throws Exception {

		return (GetResourceRequest)_createContainerRequest(
			request, portlet, windowState, portletMode, plid,
			facesPortlet, remotePortlet, PortletRequest.RESOURCE_PHASE);
	}

	private static ContainerRequest _createContainerRequest(
			HttpServletRequest request, Portlet portlet,
			WindowState windowState, PortletMode portletMode,
			long plid, boolean facesPortlet, boolean remotePortlet,
			String lifecycle)
		throws Exception {

		EntityID entityID = WindowInvokerUtil.getEntityID(portlet);

		ChannelState channelWindowState = new ChannelState(
			windowState.toString());

		ChannelMode channelPortletMode = new ChannelMode(
			portletMode.toString());

		PortletWindowContext portletWindowContext =
			new PortletWindowContextImpl(request, portlet, lifecycle);

		ChannelURLFactory channelURLFactory = null;

		if (!remotePortlet) {
			channelURLFactory = new PortletWindowURLFactory(
				request, portlet, channelWindowState, channelPortletMode, plid);
		}

		WindowRequestReader windowRequestReader = null;

		if (lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			if (!remotePortlet) {
				windowRequestReader = new PortletWindowRequestReader(
					facesPortlet);
			}

			ChannelState newWindowState =
				windowRequestReader.readNewWindowState(request);

			if (newWindowState != null) {
				channelWindowState = newWindowState;
			}

			ChannelMode newPortletWindowMode =
				windowRequestReader.readNewPortletWindowMode(request);

			if (newPortletWindowMode != null) {
				channelPortletMode = newPortletWindowMode;
			}
		}

		Container container = ContainerFactory.getContainer(
			ContainerType.PORTLET_CONTAINER);

		ContainerRequest containerRequest = null;

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			containerRequest = container.createExecuteActionRequest(
				request, entityID, channelWindowState, channelPortletMode,
				portletWindowContext, channelURLFactory, windowRequestReader);
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			containerRequest = container.createGetMarkUpRequest(
				request, entityID, channelWindowState, channelPortletMode,
				portletWindowContext, channelURLFactory);
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			containerRequest = container.createGetResourceRequest(
				request, entityID, channelWindowState, channelPortletMode,
				portletWindowContext, channelURLFactory, windowRequestReader);
		}

		if (containerRequest != null) {
			String namespace = PortalUtil.getPortletNamespace(
				portlet.getPortletId());

			StringBuilder windowID = new StringBuilder();

			windowID.append(portlet.getPortletId());
			windowID.append(PortletSessionImpl.LAYOUT_SEPARATOR);
			windowID.append(plid);

			containerRequest.setAllowableWindowStates(windowStates);
			containerRequest.setAllowableChannelModes(portletModes);
			containerRequest.setNamespace(namespace);
			containerRequest.setPortalInfo(ReleaseInfo.getReleaseInfo());
			containerRequest.setWindowID(windowID.toString());
		}

		return containerRequest;
	}

}