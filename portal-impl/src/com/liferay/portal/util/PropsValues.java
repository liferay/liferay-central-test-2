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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * <a href="PropsValues.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PropsValues {

	public static final String[] ADMIN_DEFAULT_GROUP_NAMES =
		StringUtil.split(
			PropsUtil.get(PropsUtil.ADMIN_DEFAULT_GROUP_NAMES),
			StringPool.NEW_LINE);

	public static final String[] ADMIN_DEFAULT_ROLE_NAMES =
		StringUtil.split(
			PropsUtil.get(PropsUtil.ADMIN_DEFAULT_ROLE_NAMES),
			StringPool.NEW_LINE);

	public static final String[] ADMIN_DEFAULT_USER_GROUP_NAMES =
		StringUtil.split(
			PropsUtil.get(PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES),
			StringPool.NEW_LINE);

	public static final String[] ADMIN_MAIL_HOST_NAMES = StringUtil.split(
		PropsUtil.get(PropsUtil.ADMIN_MAIL_HOST_NAMES),
		StringPool.NEW_LINE);

	public static final String[] ADMIN_RESERVED_EMAIL_ADDRESSES =
		StringUtil.split(
			PropsUtil.get(PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES),
			StringPool.NEW_LINE);

	public static final String[] ADMIN_RESERVED_SCREEN_NAMES = StringUtil.split(
		PropsUtil.get(PropsUtil.ADMIN_RESERVED_SCREEN_NAMES),
		StringPool.NEW_LINE);

	public static final boolean AUTH_FORWARD_BY_LAST_PATH =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_FORWARD_BY_LAST_PATH));

	public static final boolean AUTH_FORWARD_BY_REDIRECT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_FORWARD_BY_REDIRECT));

	public static final boolean AUTH_PIPELINE_ENABLE_LIFERAY_CHECK =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK));

	public static final boolean AUTH_SIMULTANEOUS_LOGINS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_SIMULTANEOUS_LOGINS));

	public static final int AUTO_DEPLOY_BLACKLIST_THRESHOLD =
		GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.AUTO_DEPLOY_BLACKLIST_THRESHOLD));

	public static final String AUTO_DEPLOY_DEPLOY_DIR =
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);

	public static final String AUTO_DEPLOY_DEST_DIR =
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_DEST_DIR);

	public static final boolean AUTO_DEPLOY_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get(PropsUtil.AUTO_DEPLOY_ENABLED));

	public static final int AUTO_DEPLOY_INTERVAL = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_INTERVAL));

	public static final String AUTO_DEPLOY_JBOSS_PREFIX =
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_JBOSS_PREFIX);

	public static final String AUTO_DEPLOY_TOMCAT_CONF_DIR =
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_TOMCAT_CONF_DIR);

	public static final String AUTO_DEPLOY_TOMCAT_LIB_DIR =
		PropsUtil.get(PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR);

	public static final boolean AUTO_DEPLOY_UNPACK_WAR =
		GetterUtil.getBoolean(PropsUtil.get(PropsUtil.AUTO_DEPLOY_UNPACK_WAR));

	public static final String[] AUTO_LOGIN_HOOKS =
		PropsUtil.getArray(PropsUtil.AUTO_LOGIN_HOOKS);

	public static final int CAPTCHA_MAX_CHALLENGES = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.CAPTCHA_MAX_CHALLENGES));

	public static final boolean CAS_AUTH_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.CAS_AUTH_ENABLED));

	public static final boolean COMMONS_POOL_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.COMMONS_POOL_ENABLED));

	public static final boolean COMPANY_SECURITY_AUTH_REQUIRES_HTTPS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS));

	public static final String COMPANY_SECURITY_AUTH_TYPE =
		PropsUtil.get(PropsUtil.COMPANY_SECURITY_AUTH_TYPE);

	public static final boolean COMPANY_SECURITY_AUTO_LOGIN =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_AUTO_LOGIN));

	public static final int COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE),
			CookieKeys.MAX_AGE);

	public static final boolean COMPANY_SECURITY_COMMUNITY_LOGO =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_COMMUNITY_LOGO));

	public static final boolean COMPANY_SECURITY_SEND_PASSWORD =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_SEND_PASSWORD));

	public static final boolean COMPANY_SECURITY_STRANGERS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_STRANGERS));

	public static final boolean COMPANY_SECURITY_STRANGERS_VERIFY =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_STRANGERS_VERIFY));

	public static final boolean COMPANY_SECURITY_STRANGERS_WITH_MX =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.COMPANY_SECURITY_STRANGERS_WITH_MX));

	public static final String DEFAULT_LANDING_PAGE_PATH =
		PropsUtil.get(PropsUtil.DEFAULT_LANDING_PAGE_PATH);

	public static final String DEFAULT_REGULAR_COLOR_SCHEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_REGULAR_COLOR_SCHEME_ID);

	public static final String DEFAULT_REGULAR_THEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_REGULAR_THEME_ID);

	public static final String DEFAULT_USER_LAYOUT_NAME =
		PropsUtil.get(PropsUtil.DEFAULT_USER_LAYOUT_NAME);

	public static final String DEFAULT_USER_LAYOUT_TEMPLATE_ID =
		PropsUtil.get(PropsUtil.DEFAULT_USER_LAYOUT_TEMPLATE_ID);

	public static final String DEFAULT_WAP_COLOR_SCHEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_WAP_COLOR_SCHEME_ID);

	public static final String DEFAULT_WAP_THEME_ID =
		PropsUtil.get(PropsUtil.DEFAULT_WAP_THEME_ID);

	public static final String[] DL_FILE_EXTENSIONS =
		PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);

	public static final int DL_FILE_MAX_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.DL_FILE_MAX_SIZE));

	public static final boolean DL_LAYOUTS_SYNC_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.DL_LAYOUTS_SYNC_ENABLED));

	public static final boolean
		FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY));

	public static final boolean
		FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE));

	public static final boolean
		FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS));

	public static final String[] IG_IMAGE_EXTENSIONS =
		PropsUtil.getArray(PropsUtil.IG_IMAGE_EXTENSIONS);

	public static final int IG_IMAGE_MAX_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.IG_IMAGE_MAX_SIZE));

	public static final int IG_IMAGE_THUMBNAIL_MAX_HEIGHT =
		GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_HEIGHT));

	public static final int IG_IMAGE_THUMBNAIL_MAX_WIDTH =
		GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.IG_IMAGE_THUMBNAIL_MAX_WIDTH));

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

	public static final String
		LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING = PropsUtil.get(
			PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);

	public static final String
		LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING = PropsUtil.get(
			PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

	public static final String LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING =
		PropsUtil.get(PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

	public static final boolean LAYOUT_GUEST_SHOW_MAX_ICON =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_GUEST_SHOW_MAX_ICON));

	public static final boolean LAYOUT_GUEST_SHOW_MIN_ICON =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_GUEST_SHOW_MIN_ICON));

	public static final boolean LAYOUT_PARALLEL_RENDER_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_PARALLEL_RENDER_ENABLE));

	public static final boolean LAYOUT_REMEMBER_REQUEST_WINDOW_STATE_MAXIMIZED =
		GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
			LAYOUT_REMEMBER_REQUEST_WINDOW_STATE_MAXIMIZED));

	public static final boolean LAYOUT_REMEMBER_SESSION_WINDOW_STATE_MAXIMIZED =
		GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
			LAYOUT_REMEMBER_SESSION_WINDOW_STATE_MAXIMIZED));

	public static final boolean LAYOUT_SHOW_PORTLET_ACCESS_DENIED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_ACCESS_DENIED));

	public static final boolean LAYOUT_SHOW_PORTLET_INACTIVE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_INACTIVE));

	public static final boolean LAYOUT_TEMPLATE_CACHE_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LAYOUT_TEMPLATE_CACHE_ENABLED));

	public static final boolean LDAP_AUTH_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LDAP_AUTH_ENABLED));

	public static final boolean LDAP_PASSWORD_POLICY_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LDAP_PASSWORD_POLICY_ENABLED));

	public static final boolean LOCALE_DEFAULT_REQUEST = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LOCALE_DEFAULT_REQUEST));

	public static final String[] LOCALES =
		PropsUtil.getArray(PropsUtil.LOCALES);

	public static final String[] LOGIN_EVENTS_POST =
		PropsUtil.getArray(PropsUtil.LOGIN_EVENTS_POST);

	public static final String[] LOGIN_EVENTS_PRE =
		PropsUtil.getArray(PropsUtil.LOGIN_EVENTS_PRE);

	public static final String LUCENE_DIR = PropsUtil.get(PropsUtil.LUCENE_DIR);

	public static final String LUCENE_FILE_EXTRACTOR =
		PropsUtil.get(PropsUtil.LUCENE_FILE_EXTRACTOR);

	public static final String LUCENE_FILE_EXTRACTOR_REGEXP_STRIP =
		PropsUtil.get(PropsUtil.LUCENE_FILE_EXTRACTOR_REGEXP_STRIP);

	public static final boolean LUCENE_STORE_JDBC_AUTO_CLEAN_UP =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.LUCENE_STORE_JDBC_AUTO_CLEAN_UP));

	public static final String LUCENE_STORE_TYPE = PropsUtil.get(
		PropsUtil.LUCENE_STORE_TYPE);

	public static final boolean
		MY_PLACES_SHOW_COMMUNITY_PRIVATE_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				MY_PLACES_SHOW_COMMUNITY_PRIVATE_SITES_WITH_NO_LAYOUTS));

	public static final boolean
		MY_PLACES_SHOW_COMMUNITY_PUBLIC_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				MY_PLACES_SHOW_COMMUNITY_PUBLIC_SITES_WITH_NO_LAYOUTS));

	public static final boolean
		MY_PLACES_SHOW_ORGANIZATION_PRIVATE_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				MY_PLACES_SHOW_ORGANIZATION_PRIVATE_SITES_WITH_NO_LAYOUTS));

	public static final boolean
		MY_PLACES_SHOW_ORGANIZATION_PUBLIC_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
				MY_PLACES_SHOW_ORGANIZATION_PUBLIC_SITES_WITH_NO_LAYOUTS));

	public static final boolean
		MY_PLACES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.MY_PLACES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS));

	public static final boolean
		MY_PLACES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS =
			GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.MY_PLACES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS));

	public static final long[] OMNIADMIN_USERS = StringUtil.split(
		PropsUtil.get(PropsUtil.OMNIADMIN_USERS), 0L);

	public static final boolean OPEN_ID_AUTH_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.OPEN_ID_AUTH_ENABLED));

	public static final boolean OPEN_SSO_AUTH_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.OPEN_SSO_AUTH_ENABLED));

	public static final String OPEN_SSO_LOGIN_URL =
		PropsUtil.get(PropsUtil.OPEN_SSO_LOGIN_URL);

	public static final String OPEN_SSO_LOGOUT_URL =
		PropsUtil.get(PropsUtil.OPEN_SSO_LOGOUT_URL);

	public static final String OPEN_SSO_SERVICE_URL =
		PropsUtil.get(PropsUtil.OPEN_SSO_SERVICE_URL);

	public static final String OPEN_SSO_SUBJECT_COOKIE_NAME =
		PropsUtil.get(PropsUtil.OPEN_SSO_SUBJECT_COOKIE_NAME);

	public static final boolean OPENOFFICE_SERVER_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.OPENOFFICE_SERVER_ENABLED));

	public static final String PERMISSIONS_CHECKER =
		PropsUtil.get(PropsUtil.PERMISSIONS_CHECKER);

	public static final boolean PLUGIN_NOTIFICATIONS_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.PLUGIN_NOTIFICATIONS_ENABLED));

	public static final String[] PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED =
		StringUtil.split(
			PropsUtil.get(PropsUtil.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED),
			StringPool.NEW_LINE);

	public static final String[] PLUGIN_REPOSITORIES_TRUSTED =
		StringUtil.split(
			PropsUtil.get(PropsUtil.PLUGIN_REPOSITORIES_TRUSTED),
			StringPool.NEW_LINE);

	public static final String[] PLUGIN_REPOSITORIES_UNTRUSTED =
		StringUtil.split(
			PropsUtil.get(PropsUtil.PLUGIN_REPOSITORIES_UNTRUSTED),
			StringPool.NEW_LINE);

	public static final String[] PLUGIN_TYPES =
		PropsUtil.getArray(PropsUtil.PLUGIN_TYPES);

	public static final boolean PORTAL_IMPERSONATION_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.PORTAL_IMPERSONATION_ENABLE));

	public static final boolean PORTAL_JAAS_ENABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE));

	public static final boolean PORTLET_CSS_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.PORTLET_CSS_ENABLED));

	public static final boolean PORTLET_URL_ANCHOR_ENABLE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.PORTLET_URL_ANCHOR_ENABLE));

	public static final boolean PREFERENCE_VALIDATE_ON_STARTUP =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.PREFERENCE_VALIDATE_ON_STARTUP));

	public static final boolean REVERSE_AJAX_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.REVERSE_AJAX_ENABLED));

	public static final String[] SERVLET_SERVICE_EVENTS_POST =
		PropsUtil.getArray(PropsUtil.SERVLET_SERVICE_EVENTS_POST);

	public static final String[] SERVLET_SERVICE_EVENTS_PRE =
		PropsUtil.getArray(PropsUtil.SERVLET_SERVICE_EVENTS_PRE);

	public static final String SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE =
		PropsUtil.get(PropsUtil.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE);

	public static final String SESSION_COOKIE_DOMAIN =
		PropsUtil.get(PropsUtil.SESSION_COOKIE_DOMAIN);

	public static final boolean SESSION_DISABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.SESSION_DISABLED));

	public static final boolean SESSION_ENABLE_PERSISTENT_COOKIES =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES));

	public static final boolean SESSION_ENABLE_PHISHING_PROTECTION =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_ENABLE_PHISHING_PROTECTION));

	public static final boolean SESSION_TEST_COOKIE_SUPPORT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TEST_COOKIE_SUPPORT));

	public static int SESSION_TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.SESSION_TIMEOUT));

	public static final boolean SESSION_TIMEOUT_AUTO_EXTEND =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TIMEOUT_AUTO_EXTEND));

	public static final int SESSION_TIMEOUT_WARNING = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.SESSION_TIMEOUT_WARNING));

	public static final boolean SESSION_TRACKER_FRIENDLY_PATHS_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TRACKER_FRIENDLY_PATHS_ENABLED));

	public static final boolean SESSION_TRACKER_MEMORY_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TRACKER_MEMORY_ENABLED));

	public static final boolean SESSION_TRACKER_PERSISTENCE_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.SESSION_TRACKER_PERSISTENCE_ENABLED));

	public static final boolean SMTP_SERVER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.SMTP_SERVER_ENABLED));

	public static final String SMTP_SERVER_SUBDOMAIN = PropsUtil.get(
		PropsUtil.SMTP_SERVER_SUBDOMAIN);

	public static final String TAGS_ASSET_VALIDATOR = PropsUtil.get(
		PropsUtil.TAGS_ASSET_VALIDATOR);

	public static final boolean TAGS_COMPILER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.TAGS_COMPILER_ENABLED));

	public static final boolean TCK_URL = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.TCK_URL));

	public static final boolean TERMS_OF_USE_REQUIRED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.TERMS_OF_USE_REQUIRED));

	public static final boolean THEME_CSS_FAST_LOAD = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.THEME_CSS_FAST_LOAD));

	public static final boolean USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));

	public static final boolean WEB_SERVER_DISPLAY_NODE = GetterUtil.getBoolean(
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