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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * <a href="PropsValues.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PropsValues {

	public static boolean AUTH_FORWARD_BY_LAST_PATH = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.AUTH_FORWARD_BY_LAST_PATH));

	public static boolean AUTH_FORWARD_BY_REDIRECT = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.AUTH_FORWARD_BY_REDIRECT));

	public static boolean AUTH_SIMULTANEOUS_LOGINS = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.AUTH_SIMULTANEOUS_LOGINS));

	public static final String[] AUTO_LOGIN_HOOKS = PropsUtil.getArray(
		PropsUtil.AUTO_LOGIN_HOOKS);

	public static boolean COMPANY_SECURITY_AUTH_REQUIRES_HTTPS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS));

	public static final String DEFAULT_REGULAR_COLOR_SCHEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_REGULAR_THEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_REGULAR_THEME_ID);

	public static final String DEFAULT_WAP_COLOR_SCHEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_WAP_COLOR_SCHEME_ID);

	public static final String DEFAULT_WAP_THEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_WAP_THEME_ID);

	public static final boolean JABBER_XMPP_SERVER_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.JABBER_XMPP_SERVER_ENABLED));

	public static final boolean JAVASCRIPT_FAST_LOAD = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.JAVASCRIPT_FAST_LOAD));

	public static final boolean JAVASCRIPT_LOG_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.JAVASCRIPT_LOG_ENABLED));

	public static final boolean LAST_MODIFIED_CHECK = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAST_MODIFIED_CHECK));

	public static final boolean LAYOUT_DEFAULT_P_L_RESET =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_DEFAULT_P_L_RESET));

	public static final boolean LAYOUT_GUEST_SHOW_MAX_ICON =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_GUEST_SHOW_MAX_ICON));

	public static final boolean LAYOUT_GUEST_SHOW_MIN_ICON =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_GUEST_SHOW_MIN_ICON));

	public static final boolean LAYOUT_PARALLEL_RENDER_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_PARALLEL_RENDER_ENABLE));

	public static boolean LAYOUT_SHOW_PORTLET_ACCESS_DENIED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_ACCESS_DENIED));

	public static boolean LAYOUT_SHOW_PORTLET_INACTIVE = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_INACTIVE));

	public static boolean LAYOUT_TEMPLATE_CACHE_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAYOUT_TEMPLATE_CACHE_ENABLED));

	public static final String[] LOGIN_EVENTS_POST = PropsUtil.getArray(
		PropsUtil.LOGIN_EVENTS_POST);

	public static final String[] LOGIN_EVENTS_PRE = PropsUtil.getArray(
		PropsUtil.LOGIN_EVENTS_PRE);

	public static long[] OMNIADMIN_USERS = StringUtil.split(
		PropsUtil.get(PropsUtil.OMNIADMIN_USERS), 0L);

	public static final boolean PORTAL_IMPERSONATION_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.PORTAL_IMPERSONATION_ENABLE));

	public static final boolean PORTAL_JAAS_ENABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE));

	public static final boolean PORTLET_CSS_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTLET_CSS_ENABLED));

	public static boolean PORTLET_URL_ANCHOR_ENABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTLET_URL_ANCHOR_ENABLE));

	public static final boolean REVERSE_AJAX_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.REVERSE_AJAX_ENABLED));

	public static final String[] SERVLET_SERVICE_EVENTS_POST =
		PropsUtil.getArray(PropsUtil.SERVLET_SERVICE_EVENTS_POST);

	public static final String[] SERVLET_SERVICE_EVENTS_PRE =
		PropsUtil.getArray(PropsUtil.SERVLET_SERVICE_EVENTS_PRE);

	public static final String SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE =
		PropsUtil.get(PropsUtil.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE);

	public static boolean SESSION_DISABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.SESSION_DISABLED));

	public static boolean SESSION_ENABLE_PERSISTENT_COOKIES =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES));

	public static boolean SESSION_TEST_COOKIE_SUPPORT = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.SESSION_TEST_COOKIE_SUPPORT));

	public static boolean SESSION_TRACKER_FRIENDLY_PATHS_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TRACKER_FRIENDLY_PATHS_ENABLED));

	public static boolean TAGS_COMPILER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.TAGS_COMPILER_ENABLED));

	public static boolean TERMS_OF_USE_REQUIRED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.TERMS_OF_USE_REQUIRED));

	public static boolean WEB_SERVER_DISPLAY_NODE = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.WEB_SERVER_DISPLAY_NODE));

	public static final String WEB_SERVER_HOST = PropsUtil.get(
		PropsUtil.WEB_SERVER_HOST);

	public static final int WEB_SERVER_HTTP_PORT = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.WEB_SERVER_HTTP_PORT), -1);

	public static final int WEB_SERVER_HTTPS_PORT = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.WEB_SERVER_HTTPS_PORT), -1);

	public static final String WEB_SERVER_PROTOCOL = PropsUtil.get(
		PropsUtil.WEB_SERVER_PROTOCOL);

}