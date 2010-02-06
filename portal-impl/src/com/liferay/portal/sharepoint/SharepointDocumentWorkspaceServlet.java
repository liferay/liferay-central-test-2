/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.sharepoint.dws.MemberResponseElement;
import com.liferay.portal.sharepoint.dws.ResponseElement;
import com.liferay.portal.sharepoint.dws.RoleResponseElement;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SharepointDocumentWorkspaceServlet.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 */
public class SharepointDocumentWorkspaceServlet extends HttpServlet {

	protected void doPost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			getDwsMetaDataResponse(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void getDwsMetaDataResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		StringBundler sb = new StringBundler(12);

		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
		sb.append("http://schemas.xmlsoap.org/soap/envelope/\">");
		sb.append("<SOAP-ENV:Header/>");
		sb.append("<SOAP-ENV:Body>");
		sb.append("<GetDwsMetaDataResponse xmlns=\"");
		sb.append("http://schemas.microsoft.com/sharepoint/soap/dws/\">");
		sb.append("<GetDwsMetaDataResult>");

		String results = getResults(request);

		int pos = results.indexOf("\n");

		if (pos != -1) {
			results = results.substring(pos + 1);
		}

		results = results.replaceAll("<", "&lt;");
		results = results.replaceAll(">", "&gt;");

		sb.append(results);

		sb.append("</GetDwsMetaDataResult>");
		sb.append("</GetDwsMetaDataResponse>");
		sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");

		response.setContentType(ContentTypes.TEXT_XML_UTF8);

		ServletResponseUtil.write(response, sb.toString());
	}

	protected String getResults(HttpServletRequest request)
		throws Exception {

		String xml = StringUtil.read(request.getInputStream());

		String documentName = null;

		int beginPos = xml.lastIndexOf("<document>");
		int endPos = xml.lastIndexOf("</document>");

		if (beginPos != -1) {
			documentName = xml.substring(beginPos + 10, endPos);

			documentName = HttpUtil.decodeURL(documentName);
		}

		String path = documentName;

		int pos = documentName.lastIndexOf("sharepoint/");

		if (pos != -1) {
			path = path.substring(pos + 11);
		}

		Group group = GroupServiceUtil.getGroup(
			SharepointUtil.getGroupId(path));

		boolean minimal = false;

		beginPos = xml.lastIndexOf("<minimal>");
		endPos = xml.lastIndexOf("</minimal>");

		if (beginPos != -1) {
			minimal = GetterUtil.getBoolean(
				xml.substring(beginPos + 9, endPos));
		}

		Document doc = SAXReaderUtil.createDocument();

		Element root = doc.addElement("Results");

		String url =
			"http://" + request.getLocalAddr() + ":" + request.getServerPort() +
				"/sharepoint";

		root.addElement("SubscribeUrl").setText(url);
		root.addElement("MtgInstance");
		root.addElement("SettingUrl").setText(url);
		root.addElement("PermsUrl").setText(url);
		root.addElement("UserInfoUrl").setText(url);

		Element rolesEl = root.addElement("Roles");

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			PortalUtil.getCompanyId(request));

		for (Role role : roles) {
			ResponseElement responseElement = new RoleResponseElement(role);

			responseElement.addElement(rolesEl);
		}

		if (!minimal) {
			Element schemaEl = root.addElement("Schema");

			schemaEl.addAttribute("Name", "Documents");
			schemaEl.addAttribute("Url", group.getName());

			Element fieldEl = schemaEl.addElement("Field");

			fieldEl.addAttribute("Name", "FileLeafRef");
			fieldEl.addAttribute("Required", "true");
			fieldEl.addAttribute("Type", "Invalid");

			fieldEl.addElement("Choices");

			fieldEl = schemaEl.addElement("Field");

			fieldEl.addAttribute("Name", "_SourceUrl");
			fieldEl.addAttribute("Required", "false");
			fieldEl.addAttribute("Type", "Text");

			fieldEl.addElement("Choices");

			fieldEl = schemaEl.addElement("Field");

			fieldEl.addAttribute("Name", "_SharedFileIndex");
			fieldEl.addAttribute("Required", "false");
			fieldEl.addAttribute("Type", "Text");

			fieldEl.addElement("Choices");

			fieldEl = schemaEl.addElement("Field");

			fieldEl.addAttribute("Name", "Order");
			fieldEl.addAttribute("Required", "false");
			fieldEl.addAttribute("Type", "Number");

			fieldEl.addElement("Choices");

			fieldEl = schemaEl.addElement("Field");

			fieldEl.addAttribute("Name", "Title");
			fieldEl.addAttribute("Required", "false");
			fieldEl.addAttribute("Type", "Text");

			fieldEl.addElement("Choices");

			Element listInfoEl = root.addElement("ListInfo");

			listInfoEl.addAttribute("Name", "Links");

			listInfoEl.addElement("Moderated").setText(String.valueOf(false));

			Element listPermissionsEl = listInfoEl.addElement(
				"ListPermissions");

			listPermissionsEl.addElement("DeleteListItems");
			listPermissionsEl.addElement("EditListItems");
			listPermissionsEl.addElement("InsertListItems");
			listPermissionsEl.addElement("ManageRoles");
			listPermissionsEl.addElement("ManageWeb");
		}

		Element permissionsEl = root.addElement("Permissions");

		if (!minimal) {
			permissionsEl.addElement("DeleteListItems");
			permissionsEl.addElement("EditListItems");
			permissionsEl.addElement("InsertListItems");
			permissionsEl.addElement("ManageRoles");
			permissionsEl.addElement("ManageWeb");
		}

		root.addElement("HasUniquePerm").setText(String.valueOf(true));
		root.addElement("WorkspaceType").setText("DWS");
		root.addElement("IsADMode").setText(String.valueOf(false));
		root.addElement("DocUrl").setText(documentName);
		root.addElement("Minimal").setText(String.valueOf(true));

		Element resultsEl = root.addElement("Results");

		resultsEl.addElement("Title").setText(group.getName());
		resultsEl.addElement("LastUpdate");

		User user = (User)request.getSession().getAttribute(
			WebKeys.USER);

		ResponseElement responseElement = new MemberResponseElement(
			user, false);

		responseElement.addElement(resultsEl);

		Element membersEl = resultsEl.addElement("Members");

		List<User> users = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		for (User member : users) {
			responseElement = new MemberResponseElement(member, true);

			responseElement.addElement(membersEl);
		}

		if (!minimal) {
			Element assigneesEl = resultsEl.addElement("Assignees");

			for (User member : users) {
				responseElement = new MemberResponseElement(member, true);

				responseElement.addElement(assigneesEl);
			}

			Element listEl = resultsEl.addElement("List");

			listEl.addAttribute("Name", "Documents");

			listEl.addElement("ID");

			String parentFolderPath = path;

			pos = parentFolderPath.lastIndexOf("/");

			if (pos != -1) {
				parentFolderPath = parentFolderPath.substring(0, pos);
			}

			SharepointStorage storage = SharepointUtil.getStorage(
				parentFolderPath);

			SharepointRequest sharepointRequest = new SharepointRequest(
				parentFolderPath);

			storage.addDocumentElements(sharepointRequest, listEl);
		}

		return doc.asXML();
	}

	private static Log _log = LogFactoryUtil.getLog(SharepointServlet.class);

}