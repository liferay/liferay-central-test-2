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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.editor.fckeditor.receiver.CommandReceiver;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.upload.LiferayFileItemFactory;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public abstract class BaseCommandReceiver implements CommandReceiver {

	public void createFolder(
		CommandArgument argument, HttpServletRequest request,
		HttpServletResponse response) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, argument.getCommand(), argument.getType(),
			argument.getCurrentFolder(), StringPool.BLANK);

		Element errorEl = doc.createElement("Error");

		root.appendChild(errorEl);

		String returnValue = "0";

		try {
			returnValue = createFolder(argument);
		}
		catch (FCKException fcke) {
			Throwable cause = fcke.getCause();

			returnValue = "110";

			if (cause != null) {
				String causeString = GetterUtil.getString(cause.toString());

				if (causeString.indexOf("DuplicateFolderNameException") != -1) {
					returnValue = "101";
				}
				else if (causeString.indexOf("FolderNameException") != -1) {
					returnValue = "102";
				}
				else if (causeString.indexOf("NoSuchGroupException") != -1) {
					returnValue = "103";
				}
				else {
					throw fcke;
				}
			}
		}

		errorEl.setAttribute("number", returnValue);

		_writeDocument(doc, response);
	}

	public void getFolders(
		CommandArgument argument, HttpServletRequest request,
		HttpServletResponse response) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, argument.getCommand(), argument.getType(),
			argument.getCurrentFolder(), getPath(argument));

		getFolders(argument, doc, root);

		_writeDocument(doc, response);
	}

	public void getFoldersAndFiles(
		CommandArgument argument, HttpServletRequest request,
		HttpServletResponse response) {

		Document doc = _createDocument();

		Node root = _createRoot(
			doc, argument.getCommand(), argument.getType(),
			argument.getCurrentFolder(), getPath(argument));

		getFoldersAndFiles(argument, doc, root);

		_writeDocument(doc, response);
	}

	public void fileUpload(
		CommandArgument argument, HttpServletRequest request,
		HttpServletResponse response) {

		ServletFileUpload upload = new ServletFileUpload(
			new LiferayFileItemFactory(
				UploadServletRequestImpl.DEFAULT_TEMP_DIR));

		List<FileItem> items = null;

		try {
			items = upload.parseRequest(request);
		}
		catch (FileUploadException fue) {
			throw new FCKException(fue);
		}

		Map<String, Object> fields = new HashMap<String, Object>();

		for (FileItem item : items) {
			if (item.isFormField()) {
				fields.put(item.getFieldName(), item.getString());
			}
			else {
				fields.put(item.getFieldName(), item);
			}
		}

		DiskFileItem fileItem = (DiskFileItem)fields.get("NewFile");

		String fileName = StringUtil.replace(fileItem.getName(), "\\", "/");
		String[] fileNameArray = StringUtil.split(fileName, "/");
		fileName = fileNameArray[fileNameArray.length - 1];

		String extension = _getExtension(fileName);

		String returnValue = null;

		try {
			returnValue = fileUpload(
				argument, fileName, fileItem.getStoreLocation(), extension);
		}
		catch (FCKException fcke) {
			Throwable cause = fcke.getCause();

			returnValue = "203";

			if (cause != null) {
				String causeString = GetterUtil.getString(cause.toString());

				if ((causeString.indexOf("NoSuchFolderException") != -1) ||
					(causeString.indexOf("NoSuchGroupException") != -1)) {

					returnValue = "204";
				}
				else if (causeString.indexOf("ImageNameException") != -1) {
					returnValue = "205";
				}
				else if (causeString.indexOf("FileNameException") != -1) {
					returnValue = "206";
				}
				else if (causeString.indexOf("PrincipalException") != -1) {
					returnValue = "207";
				}
				else {
					throw fcke;
				}
			}

			_writeUploadResponse(returnValue, response);
		}

		_writeUploadResponse(returnValue, response);
	}

	protected abstract String createFolder(CommandArgument argument);

	protected abstract String fileUpload(
		CommandArgument argument, String fileName, File file, String extension);

	protected abstract void getFolders(
		CommandArgument argument, Document doc, Node root);

	protected abstract void getFoldersAndFiles(
		CommandArgument argument, Document doc, Node root);

	protected void getRootFolders(
			CommandArgument argument, Document doc, Element foldersEl)
		throws Exception {

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", new Long(argument.getUserId()));

		List<Group> groups = GroupLocalServiceUtil.search(
			argument.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		List<Organization> userOrgs =
			OrganizationLocalServiceUtil.getUserOrganizations(
				argument.getUserId(), true);

		for (Organization organization : userOrgs) {
			groups.add(0, organization.getGroup());
		}

		if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			Group userGroup = GroupLocalServiceUtil.getUserGroup(
				argument.getCompanyId(), argument.getUserId());

			groups.add(0, userGroup);
		}

		for (Group group : groups) {
			Element folderEl = doc.createElement("Folder");

			foldersEl.appendChild(folderEl);

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				folderEl.setAttribute(
					"name",
					stagingGroup.getGroupId() + " - " +
						HtmlUtil.escape(stagingGroup.getDescriptiveName()));
			}
			else {
				folderEl.setAttribute(
					"name",
					group.getGroupId() + " - " +
						HtmlUtil.escape(group.getDescriptiveName()));
			}
		}
	}

	protected String getPath(CommandArgument argument) {
		return StringPool.BLANK;
	}

	protected String getSize() {
		return getSize(0);
	}

	protected String getSize(long size) {
		return String.valueOf(Math.ceil(size / 1000));
	}

	private Document _createDocument() {
		try {
			Document doc = null;

			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = null;

			builder = factory.newDocumentBuilder();

			doc = builder.newDocument();

			return doc;
		}
		catch (ParserConfigurationException pce) {
			throw new FCKException(pce);
		}
	}

	private Node _createRoot(
		Document doc, String commandStr, String typeStr, String currentPath,
		String currentUrl) {

		Element root = doc.createElement("Connector");

		doc.appendChild(root);

		root.setAttribute("command", commandStr);
		root.setAttribute("resourceType", typeStr);

		Element el = doc.createElement("CurrentFolder");

		root.appendChild(el);

		el.setAttribute("path", currentPath);
		el.setAttribute("url", currentUrl);

		return root;
	}

	private String _getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private void _writeDocument(Document doc, HttpServletResponse response) {
		try {
			doc.getDocumentElement().normalize();

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);

			if (_log.isDebugEnabled()) {
				StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();

			StreamResult result = new StreamResult(out);

			transformer.transform(source, result);

			out.flush();
			out.close();
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private void _writeUploadResponse(
		String returnValue, HttpServletResponse response) {

		try {
			StringBundler sb = new StringBundler(7);

			String newName = StringPool.BLANK;

			sb.append("<script type=\"text/javascript\">");
			sb.append("window.parent.frames['frmUpload'].OnUploadCompleted(");
			sb.append(returnValue);
			sb.append(",'");
			sb.append(newName);
			sb.append("');");
			sb.append("</script>");

			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;

			out = response.getWriter();

			out.print(sb.toString());

			out.flush();
			out.close();
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BaseCommandReceiver.class);

}