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
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.portletcontainer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.PortletURLImpl;

import com.sun.portal.container.ChannelURLType;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="LiferayPortletURLImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class LiferayPortletURLImpl extends PortletURLImpl {

	public LiferayPortletURLImpl(
		HttpServletRequest request, String portletId, WindowState windowState,
		PortletMode portletMode, long plid, String lifecycle) {

		super(request, portletId, plid, lifecycle);

		setLifecycle(getLifecyclePhase(lifecycle));
		setURLType(getChannelURlType(lifecycle));

		try {
			setWindowState(windowState);
		}
		catch (WindowStateException wse1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Exception while setting window state for " + portletId,
						wse1);
			}
			try {
				setWindowState(WindowState.NORMAL);
			}
			catch (WindowStateException wse2) {
			}
		}

		try {
			setPortletMode(portletMode);
		}
		catch (PortletModeException pme1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Exception while setting portlet mode for " + portletId,
						pme1);
			}
			try {
				setPortletMode(PortletMode.VIEW);
			}
			catch (PortletModeException pme2) {
			}
		}
	}

	protected ChannelURLType getChannelURlType(String lifecycle) {
		if (PortletRequest.ACTION_PHASE.equals(lifecycle)) {
			return ChannelURLType.ACTION;
		}
		else if (PortletRequest.RENDER_PHASE.equals(lifecycle)) {
			return ChannelURLType.RENDER;
		}
		else if (PortletRequest.RESOURCE_PHASE.equals(lifecycle)) {
			return ChannelURLType.RESOURCE;
		}
		else {
			return ChannelURLType.RENDER;
		}
	}

	protected String getLifecyclePhase(String lifecycle) {
		if (PortletRequest.RENDER_PHASE.equals(lifecycle)) {

			// Force the portal to call executeAction

			return PortletRequest.ACTION_PHASE;
		}

		return lifecycle;
	}

	private static Log _log =
		LogFactoryUtil.getLog(LiferayPortletURLImpl.class);

}