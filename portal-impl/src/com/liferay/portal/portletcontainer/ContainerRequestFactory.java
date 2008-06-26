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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletSessionImpl;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLFactory;
import com.sun.portal.container.Container;
import com.sun.portal.container.ContainerException;
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
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ContainerRequestFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class ContainerRequestFactory {

	static List<ChannelState> allowableWindowStates =
		new ArrayList<ChannelState>();
	static List<ChannelMode> allowablePortletWindowModes =
		new ArrayList<ChannelMode>();

	static {
		allowablePortletWindowModes.add(ChannelMode.EDIT);
		allowablePortletWindowModes.add(ChannelMode.HELP);
		allowablePortletWindowModes.add(ChannelMode.VIEW);
		allowablePortletWindowModes.add(new ChannelMode(
			LiferayPortletMode.ABOUT.toString()));
		allowablePortletWindowModes.add(new ChannelMode(
			LiferayPortletMode.CONFIG.toString()));
		allowablePortletWindowModes.add(new ChannelMode(
			LiferayPortletMode.EDIT_DEFAULTS.toString()));
		allowablePortletWindowModes.add(new ChannelMode(
			LiferayPortletMode.PREVIEW.toString()));
		allowablePortletWindowModes.add(new ChannelMode(
			LiferayPortletMode.PRINT.toString()));

		allowableWindowStates.add(ChannelState.MAXIMIZED);
		allowableWindowStates.add(ChannelState.MINIMIZED);
		allowableWindowStates.add(ChannelState.NORMAL);
		allowableWindowStates.add(new ChannelState(
			LiferayWindowState.EXCLUSIVE.toString()));
		allowableWindowStates.add(new ChannelState(
			LiferayWindowState.POP_UP.toString()));
	}

	public static GetMarkupRequest createGetMarkUpRequest(
			HttpServletRequest request, Portlet portletModel,
			WindowState windowState, PortletMode portletMode, String lifecycle,
			long plid, boolean isRemotePortlet)
		throws PortalException, SystemException, ContainerException {

		String portletId = portletModel.getPortletId();
		EntityID portletEntityId = getEntityID(portletModel, portletId);

		ChannelMode currentPortletWindowMode = new ChannelMode(
			portletMode.toString());

		ChannelState currentWindowState = new ChannelState(
			windowState.toString());

		PortletWindowContext portletWindowContext =
			new PortletWindowContextImpl(
				request, portletModel, lifecycle);

		ChannelURLFactory channelURLFactory = getPortletWindowURLFactory(
			request, portletModel, currentPortletWindowMode,
				currentWindowState, plid, isRemotePortlet);

		GetMarkupRequest getMarkupRequest =
			getContainer().createGetMarkUpRequest(
				request, portletEntityId,
					currentWindowState, currentPortletWindowMode,
						portletWindowContext, channelURLFactory);

		populateContainerRequest(getMarkupRequest, portletId, plid);
		// TODO: Need to use pool
		return getMarkupRequest;
	}

	public static ExecuteActionRequest createExecuteActionRequest(
			HttpServletRequest request, Portlet portletModel,
			WindowState windowState, PortletMode portletMode, String lifecycle,
			long plid, boolean isRemotePortlet)
		throws PortalException, SystemException, ContainerException {

		String portletId = portletModel.getPortletId();
		EntityID portletEntityId = getEntityID(portletModel, portletId);

		ChannelMode currentPortletWindowMode = new ChannelMode(
				portletMode.toString());

		ChannelState currentWindowState = new ChannelState(
				windowState.toString());

		PortletWindowContext portletWindowContext =
			new PortletWindowContextImpl(request, portletModel, lifecycle);

		ChannelURLFactory channelURLFactory = getPortletWindowURLFactory(
			request, portletModel, currentPortletWindowMode,
				currentWindowState, plid, isRemotePortlet);

		WindowRequestReader windowRequestReader =
			getWindowRequestReader(isRemotePortlet);

		ChannelState newWindowState =
			windowRequestReader.readNewWindowState(request);

		ChannelMode newPortletWindowMode =
			windowRequestReader.readNewPortletWindowMode(request);

		if (newPortletWindowMode != null) {
			currentPortletWindowMode = newPortletWindowMode;
		}

		if (newWindowState != null) {
			currentWindowState = newWindowState;
		}

		ExecuteActionRequest executeActionRequest =
			getContainer().createExecuteActionRequest(
				request, portletEntityId,
					currentWindowState, currentPortletWindowMode,
						portletWindowContext, channelURLFactory,
							windowRequestReader);

		populateContainerRequest(executeActionRequest, portletId, plid);

		return executeActionRequest;
	}

	public static GetResourceRequest createGetResourceRequest(
			HttpServletRequest request, Portlet portletModel,
			WindowState windowState, PortletMode portletMode, String lifecycle,
			long plid, boolean isRemotePortlet)
		throws PortalException, SystemException, ContainerException {

		String portletId = portletModel.getPortletId();
		EntityID portletEntityId = getEntityID(portletModel, portletId);

		ChannelMode currentPortletWindowMode = new ChannelMode(
			portletMode.toString());

		ChannelState currentWindowState = new ChannelState(
			windowState.toString());

		PortletWindowContext portletWindowContext =
			new PortletWindowContextImpl(request, portletModel, lifecycle);

		ChannelURLFactory channelURLFactory = getPortletWindowURLFactory(
			request, portletModel, currentPortletWindowMode,
				currentWindowState, plid, isRemotePortlet);

		WindowRequestReader windowRequestReader = getWindowRequestReader(
			isRemotePortlet);

		ChannelState newWindowState =
			windowRequestReader.readNewWindowState(request);

		ChannelMode newPortletWindowMode =
			windowRequestReader.readNewPortletWindowMode(request);

		if (newPortletWindowMode != null) {
			currentPortletWindowMode = newPortletWindowMode;
		}

		if (newWindowState != null) {
			currentWindowState = newWindowState;
		}

		GetResourceRequest getResourceRequest =
			getContainer().createGetResourceRequest(
				request, portletEntityId,
					currentWindowState, currentPortletWindowMode,
						portletWindowContext, channelURLFactory,
							windowRequestReader);

		populateContainerRequest(getResourceRequest, portletId, plid);

		return getResourceRequest;
	}

	protected static ChannelURLFactory getPortletWindowURLFactory(
			HttpServletRequest req, Portlet portletModel,
			ChannelMode newPortletWindowMode, ChannelState newWindowState,
			long plid, boolean isRemotePortlet) {
		return new PortletWindowURLFactory(
				req, portletModel, newPortletWindowMode, newWindowState, plid);
	}

	protected static Container getContainer() {
		return ContainerFactory.getContainer(ContainerType.PORTLET_CONTAINER);
	}

	protected static WindowRequestReader getWindowRequestReader(
			boolean isRemotePortlet) {
		return new PortletWindowRequestReader();
	}

	protected static void populateContainerRequest(
			ContainerRequest containerRequest, String portletId, long plid)
		throws ContainerException {

		containerRequest.setAllowableWindowStates(allowableWindowStates);
		containerRequest.setAllowableChannelModes(allowablePortletWindowModes);
		containerRequest .setNamespace(
			PortalUtil.getPortletNamespace(portletId));
		containerRequest.setWindowID(getWindowID(portletId, plid));
		containerRequest.setPortalInfo(ReleaseInfo.getReleaseInfo());
	}

	protected static EntityID getEntityID(
		Portlet portletModel, String portletId) {

		return WindowInvokerUtil.getEntityID(portletModel, portletId);
	}

	private static String getWindowID(String portletId, long plid) {
		StringMaker sm = new StringMaker();

		sm.append(portletId);
		sm.append(PortletSessionImpl.LAYOUT_SEPARATOR);
		sm.append(plid);

		return sm.toString();
	}

}