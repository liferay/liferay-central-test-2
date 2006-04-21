<%
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
%><%--

--%><%@ include file="/html/common/init.jsp" %><%--

--%><%@ page import="com.liferay.portal.DuplicateUserEmailAddressException" %><%--
--%><%@ page import="com.liferay.portal.UserEmailAddressException" %><%--
--%><%@ page import="com.liferay.portlet.addressbook.util.ABUtil" %><%--
--%><%@ page import="com.liferay.portlet.mail.MailMessageException" %><%--
--%><%@ page import="com.liferay.portlet.mail.action.RegisterAction" %><%--
--%><%@ page import="com.liferay.portlet.mail.model.Attachment" %><%--
--%><%@ page import="com.liferay.portlet.mail.model.Content" %><%--
--%><%@ page import="com.liferay.portlet.mail.model.SearchResult" %><%--
--%><%@ page import="com.liferay.portlet.mail.util.*" %><%--

--%><%@ page import="javax.mail.BodyPart" %><%--
--%><%@ page import="javax.mail.Flags" %><%--
--%><%@ page import="javax.mail.Folder" %><%--
--%><%@ page import="javax.mail.Message" %><%--
--%><%@ page import="javax.mail.Multipart" %><%--
--%><%@ page import="javax.mail.Part" %><%--
--%><%@ page import="javax.mail.SendFailedException" %><%--

--%><%@ page import="javax.mail.internet.AddressException" %><%--
--%><%@ page import="javax.mail.internet.InternetAddress" %><%--
--%><%@ page import="javax.mail.internet.MimeMessage" %><%--

--%><portlet:defineObjects /><%--

--%><%
PortletPreferences prefs = renderRequest.getPreferences();

String forwardAddress = prefs.getValue("forward-address", StringPool.BLANK);
String signature = JS.decodeURIComponent(prefs.getValue("signature", StringPool.BLANK));
String vacationMessage = JS.decodeURIComponent(prefs.getValue("vacation-message", StringPool.BLANK));
String colOrder = prefs.getValue("col-order", StringPool.BLANK);
String orderByCol = prefs.getValue("order-by-col", StringPool.BLANK);
String orderByType = prefs.getValue("order-by-type", StringPool.BLANK);
int messagesPerPortlet = GetterUtil.getInteger(prefs.getValue("messages-per-portlet", StringPool.BLANK));
int messagesPerPage = GetterUtil.getInteger(prefs.getValue("messages-per-page", StringPool.BLANK));
int messageHeaders = GetterUtil.getInteger(prefs.getValue("message-headers", StringPool.BLANK));
int messageRecipientsLimit = GetterUtil.getInteger(prefs.getValue("message-recipients-limit", "10"));
boolean includeOriginal = GetterUtil.getBoolean(prefs.getValue("include-original", StringPool.BLANK));
int originalTextIndicator = GetterUtil.getInteger(prefs.getValue("original-text-indicator", StringPool.BLANK));
String replyToAddress = prefs.getValue("reply-to-address", StringPool.BLANK);
int newMailNotification = GetterUtil.getInteger(prefs.getValue("new-mail-notification", StringPool.BLANK));
String[] extraFolders = prefs.getValues("folder-names", new String[0]);

// Hack to ensure folder names do not have '

boolean modifyFolderNames = false;

for (int i = 0; i < extraFolders.length; i++) {
	if ((extraFolders[i].indexOf("\'") != -1) || (extraFolders[i].indexOf(".") != -1)) {
		modifyFolderNames = true;

		extraFolders[i] = StringUtil.replace(extraFolders[i], "\'", "_");
		extraFolders[i] = StringUtil.replace(extraFolders[i], ".", "");
	}
}

if (modifyFolderNames) {
	prefs.setValues("folder-names", extraFolders);

	PortalUtil.storePreferences(prefs);
}

// Check if the user is accessing the portlet for the first time for this session

if (Validator.isNull(forwardAddress) && user.hasCompanyMx()) {
	Boolean firstTime = (Boolean)session.getAttribute(renderResponse.getNamespace() + "_firstTime");

	if (firstTime == null || firstTime.booleanValue() == true) {
		int junkMailFolderWarningSize = GetterUtil.getInteger(PropsUtil.get(PropsUtil.MAIL_JUNK_MAIL_WARNING_SIZE));
		int trashFolderWarningSize = GetterUtil.getInteger(PropsUtil.get(PropsUtil.MAIL_TRASH_WARNING_SIZE));

		PortletURL viewFoldersURL = renderResponse.createRenderURL();

		viewFoldersURL.setWindowState(WindowState.MAXIMIZED);

		viewFoldersURL.setParameter("struts_action", "/mail/view_folders");

		if (junkMailFolderWarningSize > 0) {
			Folder junkMailFolder = MailUtil.getFolder(request, MailUtil.MAIL_JUNK_NAME);
			int junkMailFolderSize = MailUtil.getSize(junkMailFolder.getMessages());

			if (junkMailFolderSize > junkMailFolderWarningSize) {
%>

				<script type="text/javascript">
					if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "your-junk-mail-folder-is-taking-up-a-lot-of-space") %>")) {
						self.location = "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/empty_folder" /><portlet:param name="redirect" value="<%= viewFoldersURL.toString() %>" /><portlet:param name="folder_name" value="junk-mail" /></portlet:actionURL>";
					}
				</script>

<%
			}
			else if (trashFolderWarningSize > 0) {
				Folder trashFolder = MailUtil.getFolder(request, MailUtil.MAIL_TRASH_NAME);
				int trashFolderSize = MailUtil.getSize(trashFolder.getMessages());

				if (trashFolderSize > trashFolderWarningSize) {
%>

					<script type="text/javascript">
						if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "your-trash-folder-is-taking-up-a-lot-of-space") %>")) {
							self.location = "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/empty_folder" /><portlet:param name="redirect" value="<%= viewFoldersURL.toString() %>" /><portlet:param name="folder_name" value="trash" /></portlet:actionURL>";
						}
					</script>

<%
				}
			}
		}
	}
}

// You must set this everytime or else register won't work

session.setAttribute(renderResponse.getNamespace() + "_firstTime", Boolean.FALSE);
%>