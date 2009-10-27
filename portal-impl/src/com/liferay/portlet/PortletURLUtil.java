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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PortletURLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletURLUtil {

	public static PortletURL getCurrent(
		PortletRequest portletRequest, MimeResponse mimeResponse) {

		PortletURL portletURL = mimeResponse.createRenderURL();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();
			String[] values = portletRequest.getParameterValues(param);

			boolean addParam = true;

			// Don't set paramter values that are over 32 kb. See LEP-1755.

			for (int i = 0; i < values.length; i++) {
				if (values[i].length() > _CURRENT_URL_PARAMETER_THRESHOLD) {
					addParam = false;

					break;
				}
			}

			if (addParam) {
				portletURL.setParameter(param, values);
			}
		}

		return portletURL;
	}

	public static PortletURL clone(
			PortletURL portletURL, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(
			liferayPortletURL, liferayPortletURL.getLifecycle(), mimeResponse);
	}

	public static PortletURL clone(
			PortletURL portletURL, String lifecycle, MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		return clone(liferayPortletURL, lifecycle, mimeResponse);
	}

	public static PortletURL clone(
			LiferayPortletURL liferayPortletURL, String lifecycle,
			MimeResponse mimeResponse)
		throws PortletException {

		LiferayPortletURL newURLImpl = null;

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			newURLImpl = (LiferayPortletURL)mimeResponse.createActionURL();
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			newURLImpl = (LiferayPortletURL)mimeResponse.createRenderURL();
		}

		newURLImpl.setPortletId(liferayPortletURL.getPortletId());

		WindowState windowState = liferayPortletURL.getWindowState();

		if (windowState != null) {
			newURLImpl.setWindowState(windowState);
		}

		PortletMode portletMode = liferayPortletURL.getPortletMode();

		if (portletMode != null) {
			newURLImpl.setPortletMode(portletMode);
		}

		newURLImpl.setParameters(liferayPortletURL.getParameterMap());

		return newURLImpl;
	}

	public static String getRefreshURL(
		HttpServletRequest request, ThemeDisplay themeDisplay) {

		Integer columnCount = (Integer)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_COUNT);
		String columnId = (String)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_ID);
		Integer columnPos = (Integer)request.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_POS);
		String currentURL = PortalUtil.getCurrentURL(request);
		String doAsUserId = themeDisplay.getDoAsUserId();
		long plid = themeDisplay.getPlid();
		Portlet portlet = (Portlet)request.getAttribute(
			WebKeys.RENDER_PORTLET);

		String portletId = portlet.getPortletId();

		WindowState windowState = WindowState.NORMAL;

		if (themeDisplay.getLayoutTypePortlet().hasStateMaxPortletId(
				portletId)) {

			windowState = WindowState.MAXIMIZED;
		}
		else if (themeDisplay.getLayoutTypePortlet().hasStateMinPortletId(
				portletId)) {

			windowState = WindowState.MINIMIZED;
		}

		StringBuilder url = new StringBuilder();

		url.append(themeDisplay.getPathMain());
		url.append("/portal/render_portlet");
		url.append("?p_l_id=");
		url.append(plid);
		url.append("&p_p_id=");
		url.append(portletId);
		url.append("&p_p_lifecycle=0&p_p_state=");
		url.append(windowState);
		url.append("&p_p_mode=view&p_p_col_id=");
		url.append(columnId);
		url.append("&p_p_col_pos=");
		url.append(columnPos);
		url.append("&p_p_col_count=");
		url.append(columnCount);

		if (portlet.isStatic()) {
			url.append("&p_p_static=1");

			if (portlet.isStaticStart()) {
				url.append("&p_p_static_start=1");
			}
		}

		if (Validator.isNotNull(doAsUserId)) {
			url.append("&doAsUserId=");
			url.append(HttpUtil.encodeURL(doAsUserId));
		}

		url.append("&currentURL=");
		url.append(HttpUtil.encodeURL(currentURL));

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (ppid.equals(portletId)) {
			Enumeration enu = request.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = (String)enu.nextElement();

				if (!PortalUtil.isReservedParameter(name)) {
					String[] values = request.getParameterValues(name);

					for (int i = 0; i < values.length; i++) {
						url.append(StringPool.AMPERSAND);
						url.append(name);
						url.append(StringPool.EQUAL);
						url.append(HttpUtil.encodeURL(values[i]));
					}
				}
			}

			Map renderParameters = RenderParametersPool.get(
				request, plid, ppid);

			Iterator itr = renderParameters.keySet().iterator();

			while (itr.hasNext()) {
				String name = (String)itr.next();

				String[] values = (String[])renderParameters.get(name);

				for (int i = 0; i < values.length; i++) {
					url.append(StringPool.AMPERSAND);
					url.append(name);
					url.append(StringPool.EQUAL);
					url.append(HttpUtil.encodeURL(values[i]));
				}
			}
		}

		return url.toString();
	}

	private static final int _CURRENT_URL_PARAMETER_THRESHOLD = 32768;

}