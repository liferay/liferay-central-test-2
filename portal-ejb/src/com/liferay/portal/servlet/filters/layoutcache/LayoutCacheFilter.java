/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.servlet.filters.layoutcache;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.service.spring.PortletServiceUtil;
import com.liferay.portal.servlet.FriendlyURLServlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.BrowserSniffer;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Validator;
import com.liferay.util.servlet.Header;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutCacheFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class LayoutCacheFilter implements Filter {

	public static final boolean USE_LAYOUT_CACHE_FILTER = GetterUtil.get(
		SystemProperties.get(LayoutCacheFilter.class.getName()), true);

	public void init(FilterConfig filterConfig) {
		synchronized (FriendlyURLServlet.class) {

			ServletContext ctx = filterConfig.getServletContext();

			_companyId = ctx.getInitParameter("company_id");
			_friendly = GetterUtil.getBoolean(
				filterConfig.getInitParameter("friendly"));
		}
	}

	public void destroy() {
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (USE_LAYOUT_CACHE_FILTER) {
			_log.debug("Layout Cache is enabled");
		}
		else {
			_log.debug("Layout Cache is disabled");
		}

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		String companyId = PortalUtil.getCompanyId(request);

		String plid = _getPlid(
			request.getPathInfo(), request.getServletPath(),
			ParamUtil.getString(request, "p_l_id"));

		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId) && Validator.isNotNull(plid)) {
			if (USE_LAYOUT_CACHE_FILTER && !_isAlreadyFiltered(request) &&
				!_isSignedIn(request) && !_isInclude(request) &&
				_isCacheable(companyId, plid)) {

				request.setAttribute(_ALREADY_FILTERED, Boolean.TRUE);

				String key = getCacheKey(plid, request);

				LayoutCacheResponseData data =
					LayoutCacheUtil.getLayoutCacheResponseData(companyId, key);

				if (data == null) {
					_log.info("Caching layout " + plid);

					LayoutCacheResponse layoutCacheResponse =
						new LayoutCacheResponse(response);

					chain.doFilter(req, layoutCacheResponse);

					data = new LayoutCacheResponseData(
						layoutCacheResponse.getData(),
						layoutCacheResponse.getContentType(),
						layoutCacheResponse.getHeaders());

					LayoutCacheUtil.putLayoutCacheResponseData(
						companyId, key, data);
				}

				Map headers = data.getHeaders();

				Iterator itr = headers.keySet().iterator();

				while (itr.hasNext()) {
					String headerKey = (String)itr.next();

					List headerValues = (List)headers.get(headerKey);

					for (int i = 0; i < headerValues.size(); i++) {
						Header header = (Header)headerValues.get(i);

						int type = header.getType();

						if (type == Header.DATE_TYPE) {
							response.addDateHeader(headerKey,
								header.getDateValue());
						}
						else if (type == Header.INTEGER_TYPE) {
							response.addIntHeader(headerKey,
								header.getIntValue());
						}
						else if (type == Header.STRING_TYPE) {
							response.addHeader(headerKey,
								header.getStringValue());
						}
					}
				}

				byte[] byteArray = data.getData();

				response.setContentLength(byteArray.length);
				response.setContentType(data.getContentType());

				ServletOutputStream out = response.getOutputStream();

				out.write(byteArray, 0, byteArray.length);

				out.flush();
				out.close();
			}
			else {
				_log.debug("Not checking cached layout " + plid);

				chain.doFilter(req, res);
			}
		}
		else {
			_log.debug("Did not request a layout");

			chain.doFilter(req, res);
		}
	}

	protected String getCacheKey(String plid, HttpServletRequest req) {
		String languageId = LanguageUtil.getLanguageId(req);
		boolean isIe = BrowserSniffer.is_ie(req);

		String key = plid + StringPool.POUND + languageId + isIe;

		return key.trim().toUpperCase();
	}

	private boolean _isAlreadyFiltered(HttpServletRequest req) {
		if (req.getAttribute(_ALREADY_FILTERED) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isCacheable(String companyId, String plid) {
		Layout layout = null;

		try {
			String layoutId = Layout.getLayoutId(plid);
			String ownerId = Layout.getOwnerId(plid);

			layout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);

			Properties props = layout.getTypeSettingsProperties();

			for (int i = 0; i < 10; i++) {
				String columnId = "column-" + i;

				String settings = props.getProperty(columnId, StringPool.BLANK);

				String[] portlets = StringUtil.split(settings);

				for (int j = 0; j < portlets.length; j++) {
					String portletId = StringUtil.extractFirst(
						portlets[j], Portlet.INSTANCE_SEPARATOR);

					Portlet portlet = PortletServiceUtil.getPortletById(
						companyId, portletId);

					if (!portlet.isLayoutCacheable()) {
						return false;
					}
				}
			}
		}
		catch(Exception e) {
			return false;
		}

		return true;
	}

	private boolean _isInclude(HttpServletRequest req) {
		String uri = (String)req.getAttribute(_INCLUDE);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	private boolean _isSignedIn(HttpServletRequest req) {
		try {
			User user = PortalUtil.getUser(req);

			if (user != null) {
				return true;
			}
		}
		catch (Exception ex) {
		}

		return false;
	}

	private String _getPlid(
		String pathInfo, String servletPath, String defaultPlid) {

		if (!_friendly) {
			return defaultPlid;
		}

		if (Validator.isNull(pathInfo) ||
			!pathInfo.startsWith(StringPool.SLASH)) {

			return null;
		}

		// Group friendly URL

		String friendlyURL = null;

		int pos = pathInfo.indexOf(StringPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = pathInfo.substring(0, pos);
		}
		else {
			if (pathInfo.length() > 1) {
				friendlyURL = pathInfo.substring(0, pathInfo.length());
			}
		}

		if (Validator.isNull(friendlyURL)) {
			return null;
		}

		String ownerId = null;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				_companyId, friendlyURL);

			if (servletPath.startsWith(
					_LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING)) {

				ownerId = Layout.PRIVATE + group.getGroupId();
			}
			else if (servletPath.startsWith(
						_LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

				ownerId = Layout.PUBLIC + group.getGroupId();
			}
		}
		catch (Exception e) {
			_log.error(e);

			return null;
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != pathInfo.length())) {
			friendlyURL = pathInfo.substring(pos, pathInfo.length());
		}

		if (Validator.isNull(friendlyURL)) {
			return null;
		}

		// If there is no layout path take the first from the group or user

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(ownerId,
				friendlyURL);
		}
		catch (Exception e) {
			_log.error(e);

			return null;
		}

		return layout.getPlid();
	}

	private static final String _INCLUDE = "javax.servlet.include.request_uri";

	private static final String _ALREADY_FILTERED =
		LayoutCacheFilter.class + "_ALREADY_FILTERED";

	private static final String _LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING =
		PropsUtil.get(PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING);

	private static final String _LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING =
		PropsUtil.get(PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

	private static Log _log = LogFactory.getLog(LayoutCacheFilter.class);
	private String _companyId;
	private boolean _friendly;

}