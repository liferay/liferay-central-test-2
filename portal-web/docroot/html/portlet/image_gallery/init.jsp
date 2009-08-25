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
<%@ page import="com.liferay.portal.webdav.WebDAVUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagServiceUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.DuplicateFolderNameException" %>
<%@ page import="com.liferay.portlet.imagegallery.DuplicateImageNameException" %>
<%@ page import="com.liferay.portlet.imagegallery.FolderNameException" %>
<%@ page import="com.liferay.portlet.imagegallery.ImageNameException" %>
<%@ page import="com.liferay.portlet.imagegallery.ImageSizeException" %>
<%@ page import="com.liferay.portlet.imagegallery.NoSuchFolderException" %>
<%@ page import="com.liferay.portlet.imagegallery.NoSuchImageException" %>
<%@ page import="com.liferay.portlet.imagegallery.model.IGFolder" %>
<%@ page import="com.liferay.portlet.imagegallery.model.IGImage" %>
<%@ page import="com.liferay.portlet.imagegallery.model.impl.IGFolderImpl" %>
<%@ page import="com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.service.permission.IGFolderPermission" %>
<%@ page import="com.liferay.portlet.imagegallery.service.permission.IGImagePermission" %>
<%@ page import="com.liferay.portlet.imagegallery.util.IGUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.webdav.IGWebDAVStorageImpl" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
else if (layout.getGroup().getName().equals(GroupConstants.CONTROL_PANEL)) {
	preferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), scopeGroupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, PortletKeys.IMAGE_GALLERY, null);
}

long rootFolderId = PrefsParamUtil.getLong(preferences, request, "rootFolderId", IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

if (rootFolderId == IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
	IGFolder dynamicRootFolder = null;

	int count = IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

	if (count > 1) {
		List<IGFolder> folders = IGFolderLocalServiceUtil.getFolders(scopeGroupId, IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(IGFolder.class.getName(), renderRequest);

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		dynamicRootFolder = IGFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), IGFolderImpl.DEFAULT_PARENT_FOLDER_ID, LanguageUtil.get(pageContext, "image-home"), StringPool.BLANK, serviceContext);

		long dynamicRootFolderId = dynamicRootFolder.getFolderId();

		for (IGFolder folder : folders) {
			IGFolderLocalServiceUtil.updateFolder(folder.getFolderId(), dynamicRootFolderId, folder.getName(), folder.getDescription(), false, serviceContext);
		}
	}
	else if (count == 1) {
		List<IGFolder> folders = IGFolderLocalServiceUtil.getFolders(scopeGroupId, IGFolderImpl.DEFAULT_PARENT_FOLDER_ID, 0, 1);

		dynamicRootFolder = folders.get(0);
	}
	else {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(IGFolder.class.getName(), renderRequest);

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		dynamicRootFolder = IGFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), IGFolderImpl.DEFAULT_PARENT_FOLDER_ID, LanguageUtil.get(pageContext, "image-home"), StringPool.BLANK, serviceContext);
	}

	rootFolderId = dynamicRootFolder.getFolderId();

	preferences.setValue("rootFolderId", String.valueOf(rootFolderId));

	preferences.store();
}
%>