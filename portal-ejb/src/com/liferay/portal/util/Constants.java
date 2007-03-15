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

/**
 * <a href="Constants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface Constants {

	// Commands used in Action classes

	public static final String CMD = "cmd";

	public static final String ADD = "add";

	public static final String APPROVE = "approve";

	public static final String BAN_USER = "ban_user";

	public static final String CANCEL = "cancel";

	public static final String CHECKOUT = "checkout";

	public static final String DEACTIVATE = "deactivate";

	public static final String DELETE = "delete";

	public static final String EDIT = "edit";

	public static final String EXPIRE = "expire";

	public static final String LOCK = "lock";

	public static final String MAIL_ACCOUNT = "mail_account";

	public static final String MANAGE = "manage";

	public static final String PREVIEW = "preview";

	public static final String PROGRESS_ID = "progressId";

	public static final String REJECT = "reject";

	public static final String RESET = "reset";

	public static final String RESTORE = "restore";

	public static final String REVERT = "revert";

	public static final String SAVE = "save";

	public static final String SEARCH = "search";

	public static final String SEND = "send";

	public static final String SIGNAL = "signal";

	public static final String SUBSCRIBE = "subscribe";

	public static final String UNBAN_USER = "unban_user";

	public static final String UNLOCK = "unlock";

	public static final String UNSUBSCRIBE = "unsubscribe";

	public static final String UPDATE = "update";

	public static final String VIEW = "view";

	// Rreturn values used in Action classes

	public static final String COMMON_ERROR = "/common/error.jsp";

	public static final String COMMON_FORWARD = "/common/forward_js.jsp";

	public static final String COMMON_FORWARD_JSP = "/common/forward_jsp.jsp";

	public static final String COMMON_NULL = "/common/null.jsp";

	public static final String COMMON_REFERER = "/common/referer_js.jsp";

	public static final String COMMON_REFERER_JSP = "/common/referer_jsp.jsp";

	public static final String PORTAL_ERROR = "/portal/error";

	// Content types

	public static final String CONTENT_ID = "Content-ID";

	public static final String MESSAGE_RFC822 = "message/rfc822";

	public static final String MULTIPART_ALTERNATIVE = "multipart/alternative";

	public static final String MULTIPART_FORM_DATA = "multipart/form-data";

	public static final String MULTIPART_MIXED = "multipart/mixed";

	public static final String TEXT_CSS = "text/css";

	public static final String TEXT_HTML = "text/html";

	public static final String TEXT_JAVASCRIPT = "text/javascript";

	public static final String TEXT_PLAIN = "text/plain";

	public static final String TEXT_WML = "text/wml";

	public static final String TEXT_XML = "text/xml";

	// Content directories

	public static final String TEXT_HTML_DIR = "/html";

	public static final String TEXT_WML_DIR = "/wml";

	// Data source

	// Do not set this from a properties file because it will break WebSphere's
	// JAAS authentication. See LEP-494.

	public static final String DATA_SOURCE = "jdbc/LiferayPool";

	// JAAS

	public static final String REALM_NAME = "PortalRealm";

	public static final String JBOSS_LOGIN_MODULE = "client-login";

	// JSF

	public static final String JSF_MYFACES =
		"org.apache.myfaces.portlet.MyFacesGenericPortlet";

	public static final String JSF_SUN =
		"com.sun.faces.portlet.FacesPortlet";

	public static final String LIFERAY_RENDER_KIT_FACTORY =
		"com.liferay.util.jsf.sun.faces.renderkit.LiferayRenderKitFactoryImpl";

	public static final String MYFACES_CONTEXT_FACTORY =
		"com.liferay.util.jsf.apache.myfaces.context.MyFacesContextFactoryImpl";

	// Plugin management

	public static final String DEPLOY_TO_PREFIX = "DEPLOY_TO__";

}