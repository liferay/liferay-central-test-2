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

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.webform.util.WebFormUtil;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportDataAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class ExportDataAction extends PortletAction {
	
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		PortletConfigImpl configImpl = (PortletConfigImpl)config;

		String portletId = configImpl.getPortletId();

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req, portletId);

		String databaseTableName =
			prefs.getValue("databaseTableName", StringPool.BLANK);
		String title = prefs.getValue("title", "no-title");

        StringMaker csvData = new StringMaker();

		List<String> fieldLabels = new ArrayList<String>();

		for (int i = 1; i <= WebFormUtil.MAX_FIELDS; i++) {
            String fieldLabel =
	            prefs.getValue("fieldLabel" + i, StringPool.BLANK);

			if (Validator.isNotNull(fieldLabel)) {
				fieldLabels.add(fieldLabel);

				csvData.append("\"");
				csvData.append(fieldLabel.replaceAll("\"", "\\\""));
				csvData.append("\";");
			}
        }
        
        csvData.deleteCharAt(csvData.length() - 1);
        csvData.append("\n");

		if (Validator.isNotNull(databaseTableName)) {
			List<ExpandoRow> rows = ExpandoRowLocalServiceUtil.getRows(
				WebFormUtil.class.getName(), databaseTableName, -1, -1);

			for (ExpandoRow row: rows) {
				for (String fieldName: fieldLabels) {
					String data = ExpandoValueLocalServiceUtil.getData(
						WebFormUtil.class.getName(), databaseTableName,
						fieldName, row.getClassPK(), "");

					data = data.replaceAll("\"", "\\\"");

					csvData.append("\"");
					csvData.append(data);
					csvData.append("\";");
				}

				csvData.deleteCharAt(csvData.length() - 1);
				csvData.append("\n");
			}
		}
		
		res.setContentType("application/text");

		String fileName = title + "_form-data.csv";

		res.setProperty(
			"Content-disposition", "attachment;filename=" + fileName);

		OutputStream out = res.getPortletOutputStream();

		try {
			out.write(csvData.toString().getBytes());
		}
		finally {
			out.close();
		}
		
		return null;
	}
	
}
