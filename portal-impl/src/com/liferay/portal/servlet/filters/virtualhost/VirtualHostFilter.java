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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.servlet.AbsoluteRedirectsResponse;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="VirtualHostFilter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This filter is used to provide virtual host functionality. However, this
 * filter is still required even if you do not use virtual hosting because it
 * sets the company id in the request so that subsequent calls in the thread
 * have the company id properly set. This filter must also always be the first
 * filter in the list of filters.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VirtualHostFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
	}

	protected boolean isValidFriendlyURL(String friendlyURL) {
		friendlyURL = friendlyURL.toLowerCase();

		if (PortalInstances.isVirtualHostsIgnorePath(friendlyURL) ||
			friendlyURL.startsWith(
				PortalUtil.getPathFriendlyURLPrivateGroup() +
					StringPool.SLASH) ||
			friendlyURL.startsWith(
				PortalUtil.getPathFriendlyURLPublic() + StringPool.SLASH) ||
			friendlyURL.startsWith(
				PortalUtil.getPathFriendlyURLPrivateUser() +
					StringPool.SLASH) ||
			friendlyURL.startsWith(_PATH_C) ||
			friendlyURL.startsWith(_PATH_DELEGATE) ||
			friendlyURL.startsWith(_PATH_DISPLAY_CHART) ||
			friendlyURL.startsWith(_PATH_DOCUMENT) ||
			friendlyURL.startsWith(_PATH_FACEBOOK) ||
			friendlyURL.startsWith(_PATH_GOOGLE_GADGET) ||
			friendlyURL.startsWith(_PATH_HTML) ||
			friendlyURL.startsWith(_PATH_IMAGE) ||
			friendlyURL.startsWith(_PATH_LANGUAGE) ||
			friendlyURL.startsWith(_PATH_NETVIBES) ||
			friendlyURL.startsWith(_PATH_PBHS) ||
			friendlyURL.startsWith(_PATH_POLLER) ||
			friendlyURL.startsWith(_PATH_SHAREPOINT) ||
			friendlyURL.startsWith(_PATH_SITEMAP_XML) ||
			friendlyURL.startsWith(_PATH_SOFTWARE_CATALOG) ||
			friendlyURL.startsWith(_PATH_VTI) ||
			friendlyURL.startsWith(_PATH_WAP) ||
			friendlyURL.startsWith(_PATH_WIDGET)) {

			return false;
		}

		int code = LayoutImpl.validateFriendlyURL(friendlyURL);

		if ((code > -1) &&
			(code != LayoutFriendlyURLException.ENDS_WITH_SLASH)) {

			return false;
		}

		return true;
	}

	protected boolean isValidRequestURL(StringBuffer requestURL) {
		if (requestURL == null) {
			return false;
		}

		String url = requestURL.toString();

		for (String extension : PropsValues.VIRTUAL_HOSTS_IGNORE_EXTENSIONS) {
			if (url.endsWith(extension)) {
				return false;
			}
		}

		return true;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setCharacterEncoding(StringPool.UTF8);
		//response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		// Make sure all redirects issued by the portal are absolute

		response = new AbsoluteRedirectsResponse(request, response);

		// Company id needs to always be called here so that it's properly set
		// in subsequent calls

		long companyId = PortalInstances.getCompanyId(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Company id " + companyId);
		}

		PortalUtil.getCurrentCompleteURL(request);
		PortalUtil.getCurrentURL(request);

		HttpSession session = request.getSession();

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		if (httpsInitial == null) {
			httpsInitial = Boolean.valueOf(request.isSecure());

			session.setAttribute(WebKeys.HTTPS_INITIAL, httpsInitial);

			if (_log.isDebugEnabled()) {
				_log.debug("Setting httpsInitial to " + httpsInitial);
			}
		}

		if (!isFilterEnabled()) {
			processFilter(
				VirtualHostFilter.class, request, response, filterChain);

			return;
		}

		StringBuffer requestURL = request.getRequestURL();

		if (_log.isDebugEnabled()) {
			_log.debug("Received " + requestURL);
		}

		if (!isValidRequestURL(requestURL)) {
			processFilter(
				VirtualHostFilter.class, request, response, filterChain);

			return;
		}

		String contextPath = PortalUtil.getPathContext();

		String friendlyURL = request.getRequestURI();

		if ((Validator.isNotNull(contextPath)) &&
			(friendlyURL.indexOf(contextPath) != -1)) {

			friendlyURL = friendlyURL.substring(contextPath.length());
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String i18nLanguageId = null;

		Set<String> languageIds = I18nServlet.getLanguageIds();

		for (String languageId : languageIds) {
			if (friendlyURL.startsWith(languageId)) {
				int pos = friendlyURL.indexOf(StringPool.SLASH, 1);

				if (pos == -1) {
					continue;
				}

				i18nLanguageId = friendlyURL.substring(0, pos);
				friendlyURL = friendlyURL.substring(pos);

				break;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		if (!friendlyURL.equals(StringPool.SLASH) &&
			!isValidFriendlyURL(friendlyURL)) {

			_log.debug("Friendly URL is not valid");

			processFilter(
				VirtualHostFilter.class, request, response, filterChain);

			return;
		}

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (_log.isDebugEnabled()) {
			_log.debug("Layout set " + layoutSet);
		}

		if (layoutSet != null) {
			try {
				LastPath lastPath = new LastPath(
					contextPath, friendlyURL, request.getParameterMap());

				request.setAttribute(WebKeys.LAST_PATH, lastPath);

				StringBuilder prefix = new StringBuilder();

				Group group = GroupLocalServiceUtil.getGroup(
					layoutSet.getGroupId());

				if (layoutSet.isPrivateLayout()) {
					if (group.isUser()) {
						prefix.append(_PRIVATE_USER_SERVLET_MAPPING);
					}
					else {
						prefix.append(_PRIVATE_GROUP_SERVLET_MAPPING);
					}
				}
				else {
					prefix.append(_PUBLIC_GROUP_SERVLET_MAPPING);
				}

				prefix.append(group.getFriendlyURL());

				StringBuilder forwardURL = new StringBuilder();

				if (i18nLanguageId != null) {
					forwardURL.append(i18nLanguageId);
				}

				if (friendlyURL.startsWith(
						PropsValues.WIDGET_SERVLET_MAPPING)) {

					forwardURL.append(PropsValues.WIDGET_SERVLET_MAPPING);

					friendlyURL = StringUtil.replaceFirst(
						friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING,
						StringPool.BLANK);
				}

				long plid = PortalUtil.getPlidFromFriendlyURL(
					companyId, friendlyURL);

				if (plid <= 0) {
					forwardURL.append(prefix);
				}

				forwardURL.append(friendlyURL);

				if (_log.isDebugEnabled()) {
					_log.debug("Forward to " + forwardURL);
				}

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher(forwardURL.toString());

				requestDispatcher.forward(request, response);

				return;
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		processFilter(VirtualHostFilter.class, request, response, filterChain);
	}

	private static final String _PATH_C = "/c/";

	private static final String _PATH_DELEGATE = "/delegate/";

	private static final String _PATH_DISPLAY_CHART = "/display_chart/";

	private static final String _PATH_DOCUMENT = "/document/";

	private static final String _PATH_FACEBOOK = "/facebook/";

	private static final String _PATH_GOOGLE_GADGET = "/google_gadget/";

	private static final String _PATH_HTML = "/html/";

	private static final String _PATH_IMAGE = "/image/";

	private static final String _PATH_LANGUAGE = "/language/";

	private static final String _PATH_NETVIBES = "/netvibes/";

	private static final String _PATH_PBHS = "/pbhs/";

	private static final String _PATH_POLLER = "/poller/";

	private static final String _PATH_SHAREPOINT = "/sharepoint/";

	private static final String _PATH_SITEMAP_XML = "/sitemap.xml";

	private static final String _PATH_SOFTWARE_CATALOG = "/software_catalog/";

	private static final String _PATH_VTI = "/_vti_";

	private static final String _PATH_WAP = "/wap/";

	private static final String _PATH_WIDGET = "/widget/";

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static Log _log = LogFactoryUtil.getLog(VirtualHostFilter.class);

	private ServletContext _servletContext;

}