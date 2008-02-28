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

package com.liferay.portlet;

import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.servlet.NamespaceServletRequest;
import com.liferay.portal.struts.StrutsURLEncoder;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.DynamicServletRequest;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="PortletRequestDispatcherImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class PortletRequestDispatcherImpl implements PortletRequestDispatcher {

	public PortletRequestDispatcherImpl(
		RequestDispatcher rd, PortletContextImpl portletCtxImpl) {

		this(rd, portletCtxImpl, null);
	}

	public PortletRequestDispatcherImpl(
		RequestDispatcher rd, PortletContextImpl portletCtxImpl, String path) {

		_rd = rd;
		_portlet = portletCtxImpl.getPortlet();
		_portletCtxImpl = portletCtxImpl;
		_path = path;
	}

	public void forward(PortletRequest req, PortletResponse res)
		throws IllegalStateException, IOException, PortletException {

		HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(res);

		if (httpRes.isCommitted()) {
			throw new IllegalStateException("Response is already committed");
		}

		dispatch(req, res, false, false);
	}

	public void include(PortletRequest req, PortletResponse res)
		throws IOException, PortletException {

		dispatch(req, res, false, true);
	}

	public void include(
			PortletRequest req, PortletResponse res, boolean strutsURLEncoder)
		throws IOException, PortletException {

		dispatch(req, res, strutsURLEncoder, true);
	}

	public void include(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		dispatch(req, res, false, true);
	}

	protected void dispatch(
			PortletRequest req, PortletResponse res, boolean strutsURLEncoder,
			boolean include)
		throws IOException, PortletException {

		try {
			PortletRequestImpl reqImpl = (PortletRequestImpl)req;
			PortletResponseImpl resImpl = PortalUtil.getPortletResponseImpl(
				res);

			HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
			HttpServletResponse httpRes =
				PortalUtil.getHttpServletResponse(res);

			String pathInfo = null;
			String queryString = null;
			String requestURI = null;
			String servletPath = null;

			if (_path != null) {
				/*if (ServerDetector.isJetty()) {
					int pos = _path.indexOf(StringPool.QUESTION);

					if (pos != -1) {
						_path = _path.substring(0, pos);
					}
				}*/

				String pathNoQueryString = _path;

				int pos = _path.indexOf(StringPool.QUESTION);

				if (pos != -1) {
					pathNoQueryString = _path.substring(0, pos);
					queryString = _path.substring(pos + 1, _path.length());

					Map<String, String[]> queryParams =
						new HashMap<String, String[]>();

					String[] queryParamsArray =
						StringUtil.split(queryString, StringPool.AMPERSAND);

					for (int i = 0; i < queryParamsArray.length; i++) {
						String[] nameValuePair = StringUtil.split(
							queryParamsArray[i], StringPool.EQUAL);
						String name = nameValuePair[0];
						String value = nameValuePair[1];

						String[] values = queryParams.get(name);

						if (values == null) {
							queryParams.put(name, new String[] {value});
						}
						else {
							String[] newValues = new String[values.length + 1];

							System.arraycopy(
								values, 0, newValues, 0, values.length);

							newValues[newValues.length - 1] = value;

							queryParams.put(name, newValues);
						}
					}

					DynamicServletRequest dynamicReq = null;

					if (reqImpl.isPrivateRequestAttributes()) {
						String portletNamespace =
							PortalUtil.getPortletNamespace(
								reqImpl.getPortletName());

						dynamicReq = new NamespaceServletRequest(
							httpReq, portletNamespace, portletNamespace);
					}
					else {
						dynamicReq = new DynamicServletRequest(httpReq);
					}

					for (Map.Entry<String, String[]> entry :
							queryParams.entrySet()) {

						String name = entry.getKey();
						String[] values = entry.getValue();

						String[] oldValues =
							dynamicReq.getParameterValues(name);

						if (oldValues == null) {
							dynamicReq.setParameterValues(name, values);
						}
						else {
							String[] newValues = ArrayUtil.append(
								values, oldValues);

							dynamicReq.setParameterValues(name, newValues);
						}
					}

					httpReq = dynamicReq;
				}

				Portlet portlet = reqImpl.getPortlet();

				PortletApp portletApp = portlet.getPortletApp();

				Set<String> servletURLPatterns =
					portletApp.getServletURLPatterns();

				for (String urlPattern : servletURLPatterns) {
					if (urlPattern.endsWith("/*")) {
						pos = urlPattern.indexOf("/*");

						urlPattern = urlPattern.substring(0, pos);

						if (pathNoQueryString.startsWith(urlPattern)) {
							pathInfo = pathNoQueryString.substring(
								urlPattern.length());
							servletPath = urlPattern;

							break;
						}
					}
				}

				if ((pathInfo == null) && (servletPath == null)) {
					pathInfo = StringPool.BLANK;
					servletPath = pathNoQueryString;
				}

				requestURI = req.getContextPath() + pathNoQueryString;
			}

			PortletServletRequest portletServletReq = new PortletServletRequest(
				httpReq, reqImpl, pathInfo, queryString, requestURI,
				servletPath);

			PortletServletResponse portletServletRes =
				new PortletServletResponse(httpRes, resImpl);

			URLEncoder urlEncoder = _portlet.getURLEncoderInstance();

			if (urlEncoder != null) {
				resImpl.setURLEncoder(urlEncoder);
			}
			else if (strutsURLEncoder) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

				URLEncoder strutsURLEncoderObj = new StrutsURLEncoder(
					portletServletReq.getContextPath(),
					themeDisplay.getPathMain(),
					(String)_portletCtxImpl.getAttribute(Globals.SERVLET_KEY),
					resImpl.createRenderURL());

				resImpl.setURLEncoder(strutsURLEncoderObj);
			}

			if (include) {
				_rd.include(portletServletReq, portletServletRes);
			}
			else {
				_rd.forward(portletServletReq, portletServletRes);
			}
		}
		catch (ServletException se) {
			_log.error(se, se);

			throw new PortletException(se);
		}
	}

	private static Log _log =
		LogFactory.getLog(PortletRequestDispatcherImpl.class);

	private RequestDispatcher _rd;
	private Portlet _portlet;
	private PortletContextImpl _portletCtxImpl;
	private String _path;

}