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

package com.liferay.portal.virtuallisting;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.GroupFriendlyURLComparator;
import com.liferay.portal.velocity.VelocityUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.util.TextFormatter;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.InputStream;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="VirtualListingServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class VirtualListingServlet extends HttpServlet {

	protected Tuple buildEntry(
		String path, String name, Date modifiedDate, String size,
		String description) {

		path = getPath(path, name);

		String date = StringPool.DASH;

		if (modifiedDate != null) {
			date = _dateFormat.format(modifiedDate);
		}

		if (size == null) {
			size = StringPool.DASH;
		}

		if (description == null) {
			description = StringPool.BLANK;
		}

		return new Tuple(path, name, date, size, description);
	}

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			User user = PortalUtil.getUser(request);

			if (user == null) {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				user = company.getDefaultUser();
 			}

			long userId = user.getUserId();

			PrincipalThreadLocal.setName(userId);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = HttpUtil.fixPath(request.getPathInfo());
			String fullPath =
				request.getServletPath() + StringPool.SLASH + path;
			String[] pathArray = StringUtil.split(path, StringPool.SLASH);

			if (pathArray.length == 0) {
				sendGroupListing(response, companyId, userId, fullPath);
			}
			else {
				sendDocumentLibraryListing(
					request, response, companyId, userId, fullPath, pathArray);
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected String encodePath(String[] pathArray) {
		String pathEncoded = StringPool.BLANK;

		for (String pathSegment : pathArray) {
			pathEncoded +=
				StringPool.SLASH + HttpUtil.encodeURL(pathSegment, true);
		}

		return pathEncoded + StringPool.SLASH;
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

		User user = UserLocalServiceUtil.getUserByScreenName(companyId, name);

		Group group = user.getGroup();

		return group.getGroupId();
	}

	protected String getPath(String path, String name) {
		if (name.endsWith(StringPool.SLASH)) {
			name = HttpUtil.fixPath(name, false, true);

			return getPath(path, name) + StringPool.SLASH;
		}

		if (path.endsWith(StringPool.SLASH)) {
			path = path + HttpUtil.encodeURL(name, true);
		}
		else {
			path = path + StringPool.SLASH + HttpUtil.encodeURL(name, true);
		}

		return path;
	}

	protected void sendDocumentLibraryListing(
			HttpServletRequest request, HttpServletResponse response,
			long companyId, long userId, String fullPath, String[] pathArray)
		throws Exception {

		long groupId = getGroupId(companyId, pathArray[0]);
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (int i = 1; i < pathArray.length; i++) {
			String name = pathArray[i];

			try {
				DLFolder folder = DLFolderServiceUtil.getFolder(
					groupId, folderId, name);

				folderId = folder.getFolderId();
			}
			catch (NoSuchFolderException nsfe) {
				if (i != pathArray.length - 1) {
					throw nsfe;
				}

				String title = name;

				sendFile(
					response, companyId, userId, groupId, folderId, title);

				return;
			}
		}

		try {
			sendFile(
				response, companyId, userId, groupId, folderId, "index.html");

			return;
		}
		catch (Exception e) {
			if ((e instanceof NoSuchFileEntryException) ||
				(e instanceof PrincipalException)) {

				try {
					sendFile(
						response, companyId, userId, groupId, folderId,
						"index.htm");

					return;
				}
				catch (NoSuchFileEntryException nsfee) {
				}
				catch (PrincipalException pe) {
				}
			}
			else {
				throw e;
			}
		}

		List<Tuple> entries = new ArrayList<Tuple>();

		entries.add(buildEntry(fullPath, "../", null, null, null));

		List<DLFolder> folders = DLFolderServiceUtil.getFolders(
			groupId, folderId);

		for (DLFolder folder : folders) {
			Tuple entry = buildEntry(
				fullPath, folder.getName() + StringPool.SLASH,
				folder.getModifiedDate(), null, folder.getDescription());

			entries.add(entry);
		}

		List<DLFileEntry> fileEntries = DLFileEntryServiceUtil.getFileEntries(
			groupId, folderId);

		for (DLFileEntry fileEntry : fileEntries) {
			String size = String.valueOf(fileEntry.getSize());

			if (fileEntry.getSize() > 1024) {
				size = TextFormatter.formatKB(
					fileEntry.getSize(), PortalUtil.getLocale(request)) + "k";
			}

			Tuple entry = buildEntry(
				fullPath, fileEntry.getTitle(), fileEntry.getModifiedDate(),
				size, fileEntry.getDescription());

			entries.add(entry);
		}

		String directoryPath = HttpUtil.encodePath(
			StringPool.SLASH + StringUtil.merge(pathArray, StringPool.SLASH));

		sendHTML(response, directoryPath, entries);
	}

	protected void sendFile(
			HttpServletResponse response, long companyId, long userId,
			long groupId, long folderId, String title)
		throws Exception {

		DLFileEntry fileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
			groupId, folderId, title);

		String contentType = MimeTypesUtil.getContentType(fileEntry.getTitle());

		InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(
			companyId, userId, groupId, folderId, fileEntry.getName());

		response.setContentType(contentType);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, inputStream);
	}

	protected void sendGroupListing(
			HttpServletResponse response, long companyId, long userId,
			String fullPath)
		throws Exception {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", new Long(userId));

		List<Group> groups = GroupLocalServiceUtil.search(
			companyId, null, null, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		try {
			Group group = GroupLocalServiceUtil.getUserGroup(companyId, userId);

			groups.add(group);
		}
		catch (NoSuchGroupException nsge) {
		}

		groups = ListUtil.sort(groups, new GroupFriendlyURLComparator());

		List<Tuple> entries = new ArrayList<Tuple>();

		for (Group group : groups) {
			String name = HttpUtil.fixPath(group.getFriendlyURL());

			Tuple entry = buildEntry(
				fullPath, name + StringPool.SLASH, null, null,
				group.getDescription());

			entries.add(entry);
		}

		sendHTML(response, StringPool.SLASH, entries);
	}

	protected void sendHTML(
			HttpServletResponse response, String directoryPath,
			List<Tuple> entries)
		throws Exception {

		String template = ContentUtil.get(_TEMPLATE_PATH);

		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("directoryPath", directoryPath);
		variables.put("entries", entries);
		variables.put("serverInfo", ReleaseInfo.getServerInfo());

		String html = VelocityUtil.evaluate(template, variables);

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, html);
	}

	private static final String _DATE_FORMAT_PATTERN = "d MMM yyyy HH:mm z";

	private static final String _TEMPLATE_PATH =
		"com/liferay/portal/virtuallisting/dependencies/virtual_listing.vm";

	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT_PATTERN);

}