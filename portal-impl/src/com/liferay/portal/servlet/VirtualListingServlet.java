/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.GroupFriendlyURLComparator;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.util.TextFormatter;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="VirtualListingServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class VirtualListingServlet extends HttpServlet {

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			User user = PortalUtil.getUser(request);

			if (user == null) {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				user = company.getDefaultUser();
 			}

			long userId = user.getUserId();

			PermissionChecker permissionChecker = null;

			PrincipalThreadLocal.setName(userId);

			permissionChecker = PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = HttpUtil.fixPath(request.getPathInfo());
			String fullPath =
				request.getServletPath() + StringPool.SLASH + path;

			String[] pathArray = StringUtil.split(path, StringPool.SLASH);

			StringBundler sb = new StringBundler();

			if (pathArray.length == 0) {
				LinkedHashMap<String, Object> groupParams =
					new LinkedHashMap<String, Object>();

				groupParams.put("usersGroups", new Long(userId));

				List<Group> groups = GroupLocalServiceUtil.search(
					companyId, null, null, groupParams,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				try {
					Group group = GroupLocalServiceUtil.getUserGroup(
						companyId, userId);

					groups.add(group);
				}
				catch (NoSuchGroupException nsge) {
				}

				groups = ListUtil.sort(
					groups, new GroupFriendlyURLComparator());

				appendHtmlHeader(sb, StringPool.SLASH);

				for (Group group : groups) {
					String name = HttpUtil.fixPath(group.getFriendlyURL());

					appendFolderEntry(
						sb, fullPath, name, null, group.getDescription());
				}

				appendHtmlFooter(sb);
			}
			else {
				long groupId = getGroupId(companyId, pathArray[0]);
				long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

				if (pathArray.length > 1) {
					for (int i = 1; i < pathArray.length; i++) {
						String name = pathArray[i];

						try {
							DLFolder folder =
								DLFolderLocalServiceUtil.getFolder(
									groupId, folderId, name);

							folderId = folder.getFolderId();

							DLFolderPermission.check(
								permissionChecker, folder, ActionKeys.VIEW);
						}
						catch (NoSuchFolderException nsfe) {
							if (i != pathArray.length - 1) {
								throw nsfe;
							}

							DLFileEntry fileEntry =
								DLFileEntryLocalServiceUtil.getFileEntryByTitle(
									groupId, folderId, name);

							DLFileEntryPermission.check(
								permissionChecker, fileEntry, ActionKeys.VIEW);

							InputStream is =
								DLFileEntryLocalServiceUtil.getFileAsStream(
									companyId, userId, groupId, folderId,
									fileEntry.getName());

							String contentType = MimeTypesUtil.getContentType(
								fileEntry.getTitle());

							response.setContentType(contentType);
							response.setStatus(HttpServletResponse.SC_OK);

							ServletResponseUtil.write(response, is);

							return;
						}
					}
				}

				DLFileEntry indexFileEntry = getIndexFileEntry(
					permissionChecker, groupId, folderId);

				if (indexFileEntry != null) {
					InputStream is =
						DLFileEntryLocalServiceUtil.getFileAsStream(
							companyId, userId, groupId, folderId,
							indexFileEntry.getName());

					response.setContentType(ContentTypes.TEXT_HTML_UTF8);
					response.setStatus(HttpServletResponse.SC_OK);

					ServletResponseUtil.write(response, is);
				}

				String pathEncoded = StringPool.BLANK;

				for (String pathSegment : pathArray) {
					pathEncoded += StringPool.SLASH +
						HttpUtil.encodeURL(pathSegment, true);
				}

				appendHtmlHeader(sb, pathEncoded);

				List<Object> entries = DLFolderLocalServiceUtil.
					getFoldersAndFileEntriesAndFileShortcuts(
						groupId, folderId, StatusConstants.APPROVED,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				appendFolderEntry(sb, fullPath, "..", null, "");

				for (Object entry : entries) {
					if (entry instanceof DLFolder) {
						DLFolder folder = (DLFolder)entry;

						if (DLFolderPermission.contains(
								permissionChecker, folder, ActionKeys.VIEW)) {

							appendFolderEntry(
								sb, fullPath, folder.getName(),
								folder.getModifiedDate(),
								folder.getDescription());
						}
					}
					else if (entry instanceof DLFileEntry) {
						DLFileEntry fileEntry = (DLFileEntry)entry;

						if (DLFileEntryPermission.contains(
								permissionChecker, fileEntry, ActionKeys.VIEW))
						{

							String size = String.valueOf(fileEntry.getSize());

							if (fileEntry.getSize() > 1024) {
								size = TextFormatter.formatKB(
									fileEntry.getSize(),
									PortalUtil.getLocale(request)) + "k";
							}

							appendFileEntry(
								sb, fullPath, fileEntry.getTitle(),
								fileEntry.getModifiedDate(), size,
								fileEntry.getDescription());
						}
					}
				}

				appendHtmlFooter(sb);
			}

			response.setContentType(ContentTypes.TEXT_HTML_UTF8);
			response.setStatus(HttpServletResponse.SC_OK);

			ServletResponseUtil.write(response, sb.toString());
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected void appendHtmlHeader(StringBundler sb, String path) {
		sb.append("<html>\n<head>\n");
		sb.append("<title>Directory listing for " + path + "</title>\n");
		sb.append("<style type=\"text/css\">\n");
		sb.append("table { font-family: Courier }; td { text-align: left }");
		sb.append("</style>\n");
		sb.append("</head>\n");
		sb.append("<body><h1>Directory listing for " + path + "</h1>\n");
		sb.append("<hr size=\"1\" noshade=\"noshade\" />");
		sb.append(
			"<table width=\"100%\" cellspacing=\"0\" cellpadding=\"5\">\n");
		sb.append("<tr>\n");
		sb.append("<td><font size=\"+1\"><strong>Name</strong></font></td>\n");
		sb.append(
			"<td><font size=\"+1\"><strong>Modified</strong></font></td>\n");
		sb.append("<td><font size=\"+1\"><strong>Size</strong></font></td>\n");
		sb.append(
			"<td><font size=\"+1\"><strong>Description</strong></font></td>\n");
		sb.append("</tr>\n");
	}

	protected void appendFolderEntry(
		StringBundler sb, String path, String name, Date modifiedDate,
		String description) {

		path = getPath(path, name);

		sb.append("<tr>\n");
		sb.append("<td><a href=\"" + path + "/\">" + name + "/</a></td>\n");
		if (modifiedDate == null) {
			sb.append("<td> - </td>\n");
		}
		else {
			sb.append(
				"<td>" + _dateFormat.format(modifiedDate) +
					"</td>\n");
		}
		sb.append("<td> - </td>\n");
		sb.append("<td>" + description + "</td>\n");
		sb.append("</tr>\n");
	}

	protected void appendFileEntry(
		StringBundler sb, String path, String name, Date modifiedDate,
		String size, String description) {

		path = getPath(path, name);

		sb.append("<tr>\n");
		sb.append("<td><a href=\"" + path + "\">" + name + "</a></td>\n");
		sb.append("<td>" + _dateFormat.format(modifiedDate) + "</td>\n");
		sb.append("<td>" + size + "</td>\n");
		sb.append("<td>" + description + "</td>\n");
		sb.append("</tr>\n");
	}

	protected void appendHtmlFooter(StringBundler sb) {
		sb.append("</table>\n");
		sb.append("<hr size=\"1\" noshade=\"noshade\" />\n");
		sb.append("<i>" + ReleaseInfo.getServerInfo() + "</i>\n");
		sb.append("</body>\n");
		sb.append("</html>");
	}

	protected long getGroupId(long companyId, String name) throws Exception {
		try {
			Group group = GroupLocalServiceUtil.getGroup(companyId, name);

			return group.getGroupId();
		}
		catch (NoSuchGroupException nsge) {
		}

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + name);

			return group.getGroupId();
		}
		catch (NoSuchGroupException nsge) {
		}

		User user = UserLocalServiceUtil.getUserByScreenName(
			companyId, name);

		Group group = user.getGroup();

		return group.getGroupId();
	}

	protected DLFileEntry getIndexFileEntry(
			PermissionChecker permissionChecker, long groupId, long folderId)
		throws PortalException, SystemException {

		DLFileEntry indexFileEntry = null;

		try {
			indexFileEntry =
				DLFileEntryLocalServiceUtil.getFileEntryByTitle(
					groupId, folderId, "index.htm");

			DLFileEntryPermission.check(
				permissionChecker, indexFileEntry, ActionKeys.VIEW);
		}
		catch (Exception e) {
			if ((e instanceof NoSuchFileEntryException) ||
				(e instanceof PrincipalException)) {

				try {
					indexFileEntry =
						DLFileEntryLocalServiceUtil.getFileEntryByTitle(
							groupId, folderId, "index.html");

					DLFileEntryPermission.check(
						permissionChecker, indexFileEntry, ActionKeys.VIEW);
				}
				catch (NoSuchFileEntryException nsfee) {
				}
				catch (PrincipalException pe) {
				}
			}
		}

		return indexFileEntry;
	}

	protected String getPath(String path, String name) {
		if (path.endsWith(StringPool.SLASH)) {
			path = path + HttpUtil.encodeURL(name, true);
		}
		else {
			path = path + StringPool.SLASH + HttpUtil.encodeURL(name, true);
		}

		return path;
	}

	private static DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("d MMM yyyy HH:mm z");

}