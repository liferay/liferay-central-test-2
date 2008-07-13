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

package com.liferay.portal.servlet.filters.layoutcache;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.filters.CacheResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="LayoutCacheFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Javier de Ros
 * @author Raymond Augé
 *
 */
public class LayoutCacheFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		LayoutCacheFilter.class + "SKIP_FILTER";

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_pattern = GetterUtil.getInteger(
			filterConfig.getInitParameter("pattern"));

		if ((_pattern != _PATTERN_FRIENDLY) &&
			(_pattern != _PATTERN_LAYOUT) &&
			(_pattern != _PATTERN_RESOURCE)) {

			_log.error("Layout cache pattern is invalid");
		}
	}

	protected String getBrowserType(HttpServletRequest request) {
		if (BrowserSnifferUtil.is_ie_7(request)) {
			return _BROWSER_TYPE_IE_7;
		}
		else if (BrowserSnifferUtil.is_ie(request)) {
			return _BROWSER_TYPE_IE;
		}
		else {
			return _BROWSER_TYPE_OTHER;
		}
	}

	protected String getCacheKey(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		// Url

		sb.append(HttpUtil.getProtocol(request));
		sb.append("://");
		sb.append(request.getServletPath());
		sb.append(request.getPathInfo());
		sb.append(StringPool.QUESTION);
		sb.append(request.getQueryString());

		// Language

		sb.append(StringPool.POUND);
		sb.append(LanguageUtil.getLanguageId(request));

		// Browser type

		sb.append(StringPool.POUND);
		sb.append(getBrowserType(request));

		// Gzip compression

		sb.append(StringPool.POUND);
		sb.append(BrowserSnifferUtil.acceptsGzip(request));

		return sb.toString().trim().toUpperCase();
	}

	protected long getPlid(
		long companyId, String pathInfo, String servletPath, long defaultPlid) {

		if (_pattern == _PATTERN_LAYOUT) {
			return defaultPlid;
		}

		if (Validator.isNull(pathInfo) ||
			!pathInfo.startsWith(StringPool.SLASH)) {

			return 0;
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
			return 0;
		}

		long groupId = 0;
		boolean privateLayout = false;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, friendlyURL);

			groupId = group.getGroupId();

			if (servletPath.startsWith(
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING) ||
				servletPath.startsWith(
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING)) {

				privateLayout = true;
			}
			else if (servletPath.startsWith(
						PropsValues.
							LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

				privateLayout = false;
			}
		}
		catch (NoSuchLayoutException nsle) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsle);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.error(e);
			}

			return 0;
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != pathInfo.length())) {
			friendlyURL = pathInfo.substring(pos, pathInfo.length());
		}

		if (Validator.isNull(friendlyURL)) {
			return 0;
		}

		// If there is no layout path take the first from the group or user

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, privateLayout, friendlyURL);

			return layout.getPlid();
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			return 0;
		}
		catch (Exception e) {
			_log.error(e);

			return 0;
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isCacheable(long companyId, HttpServletRequest request) {
		if (_pattern == _PATTERN_RESOURCE) {
			return true;
		}

		try {
			long plid = getPlid(
				companyId, request.getPathInfo(), request.getServletPath(),
				ParamUtil.getLong(request, "p_l_id"));

			if (plid <= 0) {
				return false;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
				return false;
			}

			UnicodeProperties props = layout.getTypeSettingsProperties();

			for (int i = 0; i < 10; i++) {
				String columnId = "column-" + i;

				String settings = props.getProperty(columnId, StringPool.BLANK);

				String[] portlets = StringUtil.split(settings);

				for (int j = 0; j < portlets.length; j++) {
					String portletId = StringUtil.extractFirst(
						portlets[j], PortletConstants.INSTANCE_SEPARATOR);

					Portlet portlet = PortletLocalServiceUtil.getPortletById(
						companyId, portletId);

					if (!portlet.isLayoutCacheable()) {
						return false;
					}
				}
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	protected boolean isInclude(HttpServletRequest request) {
		String uri = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected boolean isLayout(HttpServletRequest request) {
		if ((_pattern == _PATTERN_FRIENDLY) ||
			(_pattern == _PATTERN_RESOURCE)) {

			return true;
		}
		else {
			String plid = ParamUtil.getString(request, "p_l_id");

			if (Validator.isNotNull(plid)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	protected boolean isPortletRequest(HttpServletRequest request) {
		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return false;
		}
		else {
			return true;
		}
	}

	protected boolean isSignedIn(HttpServletRequest request) {
		long userId = PortalUtil.getUserId(request);
		String remoteUser = request.getRemoteUser();

		if ((userId <= 0) && (remoteUser == null)) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		if (!isPortletRequest(request) && isLayout(request) &&
			!isSignedIn(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			String key = getCacheKey(request);

			long companyId = PortalInstances.getCompanyId(request);

			CacheResponseData data = LayoutCacheUtil.getCacheResponseData(
				companyId, key);

			if (data == null) {
				if (!isCacheable(companyId, request)) {
					if (_log.isDebugEnabled()) {
						_log.debug("Layout is not cacheable " + key);
					}

					processFilter(
						LayoutCacheFilter.class, request, response,
						filterChain);

					return;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Caching layout " + key);
				}

				CacheResponse cacheResponse = new CacheResponse(
					response, StringPool.UTF8);

				processFilter(
					LayoutCacheFilter.class, request, cacheResponse,
					filterChain);

				data = new CacheResponseData(
					cacheResponse.getData(), cacheResponse.getContentType(),
					cacheResponse.getHeaders());

				LastPath lastPath = (LastPath)request.getAttribute(
					WebKeys.LAST_PATH);

				if (lastPath != null) {
					data.setAttribute(WebKeys.LAST_PATH, lastPath);
				}

				if (data.getData().length > 0) {
					LayoutCacheUtil.putCacheResponseData(companyId, key, data);
				}
			}
			else {
				LastPath lastPath = (LastPath)data.getAttribute(
					WebKeys.LAST_PATH);

				if (lastPath != null) {
					HttpSession session = request.getSession();

					session.setAttribute(WebKeys.LAST_PATH, lastPath);
				}
			}

			CacheResponseUtil.write(response, data);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Did not request a layout");
			}

			processFilter(
				LayoutCacheFilter.class, request, response, filterChain);
		}
	}

	private static final int _PATTERN_FRIENDLY = 0;

	private static final int _PATTERN_LAYOUT = 1;

	private static final int _PATTERN_RESOURCE = 2;

	private static final String _BROWSER_TYPE_IE_7 = "ie_7";

	private static final String _BROWSER_TYPE_IE = "ie";

	private static final String _BROWSER_TYPE_OTHER = "other";

	private static Log _log = LogFactoryUtil.getLog(LayoutCacheFilter.class);

	private int _pattern;

}