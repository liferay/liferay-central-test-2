/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.servlet.FriendlyURLServlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.BrowserSniffer;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Validator;
import com.liferay.util.servlet.Header;
import com.liferay.util.servlet.ServletResponseUtil;

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
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutCacheFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Javier de Ros
 *
 */
public class LayoutCacheFilter implements Filter {

	public static final boolean USE_LAYOUT_CACHE_FILTER = GetterUtil.getBoolean(
		SystemProperties.get(LayoutCacheFilter.class.getName()), true);

	public static final String ENCODING = GetterUtil.getString(
		SystemProperties.get(LayoutCacheFilter.class.getName() + ".encoding"),
		"UTF-8");

	public void init(FilterConfig config) throws ServletException {
		synchronized (FriendlyURLServlet.class) {
			ServletContext ctx = config.getServletContext();

			_companyId = ctx.getInitParameter("company_id");

			_pattern = GetterUtil.getInteger(
				config.getInitParameter("pattern"));

			if ((_pattern != _PATTERN_FRIENDLY) &&
				(_pattern != _PATTERN_LAYOUT) &&
				(_pattern != _PATTERN_RESOURCE)) {

				throw new ServletException("Layout cache pattern is invalid");
			}
		}
	}

	public void doFilter(
			ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			if (USE_LAYOUT_CACHE_FILTER) {
				_log.debug("Layout cache is enabled");
			}
			else {
				_log.debug("Layout cache is disabled");
			}
		}

		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpRes = (HttpServletResponse)res;

		httpReq.setCharacterEncoding(ENCODING);

		if (USE_LAYOUT_CACHE_FILTER && !isPortletRequest(httpReq) &&
			isLayout(httpReq) && !isSignedIn(httpReq) &&
			!isInclude(httpReq) && !isAlreadyFiltered(httpReq)) {

			httpReq.setAttribute(_ALREADY_FILTERED, Boolean.TRUE);

			String key = getCacheKey(httpReq);

			LayoutCacheResponseData data =
				LayoutCacheUtil.getLayoutCacheResponseData(_companyId, key);

			if (data == null) {
				if (!isCacheable(httpReq)) {
					if (_log.isDebugEnabled()) {
						_log.debug("Layout is not cacheable " + key);
					}

					chain.doFilter(req, res);

					return;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Caching layout " + key);
				}

				LayoutCacheResponse layoutCacheResponse =
					new LayoutCacheResponse(httpRes);

				chain.doFilter(req, layoutCacheResponse);

				data = new LayoutCacheResponseData(
					layoutCacheResponse.getData(),
					layoutCacheResponse.getContentType(),
					layoutCacheResponse.getHeaders());

				if (data.getData().length > 0) {
					LayoutCacheUtil.putLayoutCacheResponseData(
						_companyId, key, data);
				}
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
						httpRes.addDateHeader(headerKey, header.getDateValue());
					}
					else if (type == Header.INTEGER_TYPE) {
						httpRes.addIntHeader(headerKey, header.getIntValue());
					}
					else if (type == Header.STRING_TYPE) {
						httpRes.addHeader(headerKey, header.getStringValue());
					}
				}
			}

			httpRes.setContentType(data.getContentType());

			ServletResponseUtil.write(httpRes, data.getData());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Did not request a layout");
			}

			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	protected String getBrowserType(HttpServletRequest req) {
		if (BrowserSniffer.is_ie_7(req)) {
			return _BROWSER_TYPE_IE_7;
		}
		else if (BrowserSniffer.is_ie(req)) {
			return _BROWSER_TYPE_IE;
		}
		else {
			return _BROWSER_TYPE_OTHER;
		}
	}

	protected String getCacheKey(HttpServletRequest req) {
		StringMaker sm = new StringMaker();

		// Url

		sm.append(req.getServletPath());
		sm.append(req.getPathInfo());
		sm.append(StringPool.QUESTION);
		sm.append(req.getQueryString());

		// Language

		sm.append(StringPool.POUND);
		sm.append(LanguageUtil.getLanguageId(req));

		// Browser type

		sm.append(StringPool.POUND);
		sm.append(getBrowserType(req));

		// Gzip compression

		sm.append(StringPool.POUND);
		sm.append(BrowserSniffer.acceptsGzip(req));

		return sm.toString().trim().toUpperCase();
	}

	protected String getPlid(
		String pathInfo, String servletPath, String defaultPlid) {

		if (_pattern == _PATTERN_LAYOUT) {
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

				ownerId = LayoutImpl.PRIVATE + group.getGroupId();
			}
			else if (servletPath.startsWith(
						_LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

				ownerId = LayoutImpl.PUBLIC + group.getGroupId();
			}
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);
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

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				ownerId, friendlyURL);

			return layout.getPlid();
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			return null;
		}
		catch (Exception e) {
			_log.error(e);

			return null;
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest req) {
		if (req.getAttribute(_ALREADY_FILTERED) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isCacheable(HttpServletRequest req) {
		if (_pattern == _PATTERN_RESOURCE) {
			return true;
		}

		try {
			String plid = getPlid(
				req.getPathInfo(), req.getServletPath(),
				ParamUtil.getString(req, "p_l_id"));

			if (plid == null) {
				return false;
			}

			String layoutId = LayoutImpl.getLayoutId(plid);
			String ownerId = LayoutImpl.getOwnerId(plid);

			Layout layout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);

			if (!layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				return false;
			}

			Properties props = layout.getTypeSettingsProperties();

			for (int i = 0; i < 10; i++) {
				String columnId = "column-" + i;

				String settings = props.getProperty(columnId, StringPool.BLANK);

				String[] portlets = StringUtil.split(settings);

				for (int j = 0; j < portlets.length; j++) {
					String portletId = StringUtil.extractFirst(
						portlets[j], PortletImpl.INSTANCE_SEPARATOR);

					Portlet portlet = PortletLocalServiceUtil.getPortletById(
						_companyId, portletId);

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

	protected boolean isInclude(HttpServletRequest req) {
		String uri = (String)req.getAttribute(_INCLUDE);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected boolean isLayout(HttpServletRequest req) {
		if ((_pattern == _PATTERN_FRIENDLY) ||
			(_pattern == _PATTERN_RESOURCE)) {

			return true;
		}
		else {
			String plid = ParamUtil.getString(req, "p_l_id");

			if (Validator.isNotNull(plid)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	protected boolean isPortletRequest(HttpServletRequest req) {
		String portletId = ParamUtil.getString(req, "p_p_id");

		if (Validator.isNull(portletId)) {
			return false;
		}
		else {
			return true;
		}
	}

	protected boolean isSignedIn(HttpServletRequest req) {
		String userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();

		if ((userId == null) && (remoteUser == null)) {
			return false;
		}
		else {
			return true;
		}
	}

	private static final String _INCLUDE = "javax.servlet.include.request_uri";

	private static final String _ALREADY_FILTERED =
		LayoutCacheFilter.class + "_ALREADY_FILTERED";

	private static final String _LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING =
		PropsUtil.get(PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING);

	private static final String _LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING =
		PropsUtil.get(PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

	private static final int _PATTERN_FRIENDLY = 0;

	private static final int _PATTERN_LAYOUT = 1;

	private static final int _PATTERN_RESOURCE = 2;

	private static final String _BROWSER_TYPE_IE_7 = "ie_7";

	private static final String _BROWSER_TYPE_IE = "ie";

	private static final String _BROWSER_TYPE_OTHER = "other";

	private static Log _log = LogFactory.getLog(LayoutCacheFilter.class);

	private String _companyId;
	private int _pattern;

}