<%
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
%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portlet.bookmarks.EntryURLException" %>
<%@ page import="com.liferay.portlet.bookmarks.FolderNameException" %>
<%@ page import="com.liferay.portlet.bookmarks.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.bookmarks.NoSuchFolderException" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksEntry" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksFolder" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksFolderConstants" %>
<%@ page import="com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.util.BookmarksUtil" %>

<%
PortalPreferences portalPrefs = PortletPreferencesFactoryUtil.getPortalPreferences(request);

DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);

PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
else if (layout.getGroup().getName().equals(GroupConstants.CONTROL_PANEL)) {
	preferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), scopeGroupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, PortletKeys.BOOKMARKS, null);
}

long rootFolderId = PrefsParamUtil.getLong(preferences, request, "rootFolderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if (rootFolderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	BookmarksFolder dynamicRootFolder = null;

	int count = BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

	if (count > 1) {
		List<BookmarksFolder> folders = BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(BookmarksFolder.class.getName(), renderRequest);

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		dynamicRootFolder = BookmarksFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, LanguageUtil.get(pageContext, "bookmarks-home"), StringPool.BLANK, serviceContext);

		long dynamicRootFolderId = dynamicRootFolder.getFolderId();

		for (BookmarksFolder folder : folders) {
			BookmarksFolderLocalServiceUtil.updateFolder(folder.getFolderId(), dynamicRootFolderId, folder.getName(), folder.getDescription(), false, serviceContext);
		}
	}
	else if (count == 1) {
		List<BookmarksFolder> folders = BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, 0, 1);

		dynamicRootFolder = folders.get(0);
	}
	else {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(BookmarksFolder.class.getName(), renderRequest);

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		dynamicRootFolder = BookmarksFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, LanguageUtil.get(pageContext, "bookmarks-home"), StringPool.BLANK, serviceContext);
	}

	rootFolderId = dynamicRootFolder.getFolderId();

	preferences.setValue("rootFolderId", String.valueOf(rootFolderId));

	preferences.store();
}
%>