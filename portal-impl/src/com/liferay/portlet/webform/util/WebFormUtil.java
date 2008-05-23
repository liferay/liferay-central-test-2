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

package com.liferay.portlet.webform.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="WebFormUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Daniel Weisser
 * @author Jorge Ferrer
 *
 */
public class WebFormUtil {

	public static String getNewDatabaseTableName(String portletId)
		throws SystemException {

		long formId = CounterLocalServiceUtil.increment(
			WebFormUtil.class.getName());

		return portletId + StringPool.UNDERLINE + formId;
	}


	public static final int MAX_FIELDS = GetterUtil.getInteger(
		PropsUtil.get(PropsUtil.WEB_FORM_PORTLET_MAX_FIELDS));

	public static ExpandoTable addTable(String tableName)
		throws PortalException, SystemException {

	    try {
		    ExpandoTableLocalServiceUtil.deleteTable(
				WebFormUtil.class.getName(), tableName);
	    }
	    catch (NoSuchTableException nste) {
	    }

	    return ExpandoTableLocalServiceUtil.addTable(
		    WebFormUtil.class.getName(), tableName);
	}

	public static ExpandoTable checkTable(
			String tableName, PortletPreferences prefs)
		throws Exception {

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				WebFormUtil.class.getName(), tableName);
		}
		catch (NoSuchTableException nste) {
			expandoTable = addTable(tableName);

			int i = 1;

			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			while ((i == 1) || (Validator.isNotNull(fieldLabel))) {
				ExpandoColumnLocalServiceUtil.addColumn(
					expandoTable.getTableId(), fieldLabel,
					ExpandoColumnConstants.STRING);

				i++;

				fieldLabel = prefs.getValue("fieldLabel" + i, StringPool.BLANK);
			}
		}

		return expandoTable;
	}

	public static int getTableRowsCount(String tableName)
		throws SystemException {

		return ExpandoRowLocalServiceUtil.getRowsCount(
			WebFormUtil.class.getName(), tableName);
	}

	public static String[] split(String s) {
		return split(s, StringPool.COMMA);
	}

	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		s = s.trim();

		if (!s.endsWith(delimiter)) {
			StringMaker sm = new StringMaker();

			sm.append(s);
			sm.append(delimiter);

			s = sm.toString();
		}

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List<String> nodeValues = new ArrayList<String>();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(new String(s.substring(offset, pos)));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return nodeValues.toArray(new String[nodeValues.size()]);
	}

}