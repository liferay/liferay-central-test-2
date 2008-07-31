/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.webform.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.webform.service.WebFormLocalServiceUtil;
import com.liferay.portlet.webform.util.WebFormUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportDataAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class ExportDataAction extends PortletAction {

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {
	
		PortletConfigImpl portletConfigImpl = (PortletConfigImpl)portletConfig;

		String portletId = portletConfigImpl.getPortletId();

		PortletPreferences prefs = resourceRequest.getPreferences();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)resourceRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		String databaseTableName = prefs.getValue(
			"databaseTableName", StringPool.BLANK);
		String title = prefs.getValue("title", "no-title");
		boolean uploadToDL = GetterUtil.getBoolean(
			prefs.getValue("uploadToDL", StringPool.BLANK));
		boolean uploadToDisk = GetterUtil.getBoolean(
			prefs.getValue("uploadToDisk", StringPool.BLANK));
		String uploadDiskDir = GetterUtil.getString(
				prefs.getValue("uploadDiskDir", StringPool.BLANK));

		if (uploadToDisk) uploadToDisk = Validator.isNotNull(uploadDiskDir);

		StringBuilder sb = new StringBuilder();
		boolean hasFiles = false;

		List<String> fieldLabels = new ArrayList<String>();
		List<Boolean> fieldFiles = new ArrayList<Boolean>();

		for (int i = 1; i <= WebFormUtil.MAX_FIELDS; i++) {
			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			if (Validator.isNotNull(fieldLabel)) {
				fieldLabels.add(fieldLabel);

				sb.append("\"");
				sb.append(fieldLabel.replaceAll("\"", "\\\""));
				sb.append("\";");

				String fieldType = prefs.getValue("fieldType" + i, null);
				boolean isFile = "file".equals(fieldType);
				fieldFiles.add(Boolean.valueOf(isFile));
				if (isFile) hasFiles = true;
			}
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n");

		ZipWriter zipWriter = null;
		if (hasFiles) zipWriter = new ZipWriter();

		if (Validator.isNotNull(databaseTableName)) {
			List<ExpandoRow> rows = ExpandoRowLocalServiceUtil.getRows(
				WebFormUtil.class.getName(), databaseTableName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (ExpandoRow row : rows) {
				for (int i = 0; i < fieldLabels.size(); i++) {
					String fieldName = fieldLabels.get(i);
					boolean fieldIsFile = fieldFiles.get(i).booleanValue();

					String data = ExpandoValueLocalServiceUtil.getData(
						WebFormUtil.class.getName(), databaseTableName,
						fieldName, row.getClassPK(), StringPool.BLANK);

					if (fieldIsFile && Validator.isNotNull(data)) {
						byte[] bytes = null;

						if (uploadToDL) {
							bytes = WebFormLocalServiceUtil.retrieveUploadedFile(themeDisplay.getCompanyId(), portletId, data);
						}

						if (bytes == null && uploadToDisk) {
							bytes = FileUtil.getBytes(new File(uploadDiskDir + "/" + data));
						}

						if (bytes != null) {
							zipWriter.addEntry(data, bytes);
						}
					}

					data = data.replaceAll("\"", "\\\"");

					sb.append("\"");
					sb.append(data);
					sb.append("\";");
				}

				sb.deleteCharAt(sb.length() - 1);
				sb.append("\n");
			}
		}

		byte[] csvBytes = sb.toString().getBytes();

		String fileName;
		byte[] bytes;
		String contentType;

		if (hasFiles) {
			zipWriter.addEntry("submissions.csv", csvBytes);

			fileName = title + ".zip";
			bytes = zipWriter.finish();
			contentType = ContentTypes.APPLICATION_ZIP;
		}
		else {
			fileName = title + ".csv";
			bytes = csvBytes;
			contentType = ContentTypes.APPLICATION_TEXT;
		}

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

		ServletResponseUtil.sendFile(response, fileName, new ByteArrayInputStream(bytes), contentType);
	}
}
