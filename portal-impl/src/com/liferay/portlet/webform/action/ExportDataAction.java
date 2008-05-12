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

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.webform.util.WebFormUtil;
import com.liferay.util.dao.hibernate.QueryUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

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

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req);

		String databaseTableName = prefs.getValue(
			"databaseTableName", StringPool.BLANK);
		String title = prefs.getValue("title", "no-title");

        StringMaker sm = new StringMaker();

		List<String> fieldLabels = new ArrayList<String>();

		for (int i = 1; i <= WebFormUtil.MAX_FIELDS; i++) {
            String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			if (Validator.isNotNull(fieldLabel)) {
				fieldLabels.add(fieldLabel);

				sm.append("\"");
				sm.append(fieldLabel.replaceAll("\"", "\\\""));
				sm.append("\";");
			}
        }

        sm.deleteCharAt(sm.length() - 1);
        sm.append("\n");

		if (Validator.isNotNull(databaseTableName)) {
			List<ExpandoRow> rows = ExpandoRowLocalServiceUtil.getRows(
				WebFormUtil.class.getName(), databaseTableName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (ExpandoRow row : rows) {
				for (String fieldName : fieldLabels) {
					String data = ExpandoValueLocalServiceUtil.getData(
						WebFormUtil.class.getName(), databaseTableName,
						fieldName, row.getClassPK(), StringPool.BLANK);

					data = data.replaceAll("\"", "\\\"");

					sm.append("\"");
					sm.append(data);
					sm.append("\";");
				}

				sm.deleteCharAt(sm.length() - 1);
				sm.append("\n");
			}
		}

		HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(res);
		String fileName = title + ".csv";
		InputStream is = new ByteArrayInputStream(sm.toString().getBytes());
		String contentType = ContentTypes.APPLICATION_TEXT;

		ServletResponseUtil.sendFile(httpRes, fileName, is, contentType);

		setForward(req, ActionConstants.COMMON_NULL);
	}

}