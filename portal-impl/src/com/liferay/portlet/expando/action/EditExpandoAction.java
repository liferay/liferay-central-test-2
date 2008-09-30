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

package com.liferay.portlet.expando.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.ColumnNameException;
import com.liferay.portlet.expando.ColumnTypeException;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoBridgeImpl;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <a href="EditExpandosAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class EditExpandoAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addExpando(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateExpando(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExpando(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			actionResponse.sendRedirect(redirect);
		}
		catch (Exception e) {
			if (e instanceof ColumnNameException ||
				e instanceof ColumnTypeException ||
				e instanceof DuplicateColumnNameException ||
				e instanceof ValueDataException ||
				e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getColumn(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.expando.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.expando.edit_expando"));
	}

	protected void addExpando(ActionRequest actionRequest) throws Exception {
		String name = ParamUtil.getString(actionRequest, "name");
		int type = ParamUtil.getInteger(actionRequest, "type");

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		ExpandoBridge expandoBridge = new ExpandoBridgeImpl(
			modelResource, resourcePrimKey);

		expandoBridge.addAttribute(name, type);
	}

	protected void deleteExpando(ActionRequest actionRequest) throws Exception {
		long columnId = ParamUtil.getLong(actionRequest, "columnId");

		ExpandoColumnServiceUtil.deleteColumn(columnId);
	}

	public static Object getValue(
		ActionRequest actionRequest, int type, String name) throws Exception {

		Object value = null;

		int numValues = ParamUtil.getInteger(actionRequest, name + "Num");

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			boolean[] values = new boolean[numValues];

			for (int i = 0; i < numValues; i++) {
				values[i] = ParamUtil.getBoolean(actionRequest, name + "_" + i);
			}

			value = values;
		}
		else if (type == ExpandoColumnConstants.DATE) {
			User user = PortalUtil.getUser(actionRequest);

			int valueDateMonth = ParamUtil.getInteger(
				actionRequest, name + "Month");
			int valueDateDay = ParamUtil.getInteger(
				actionRequest, name + "Day");
			int valueDateYear = ParamUtil.getInteger(
				actionRequest, name + "Year");
			int valueDateHour = ParamUtil.getInteger(
				actionRequest, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				actionRequest, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(
				actionRequest, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			value = PortalUtil.getDate(
				valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
				valueDateMinute, user.getTimeZone(),
				new ValueDataException());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			User user = PortalUtil.getUser(actionRequest);

			Date[] values = new Date[numValues];

			for (int i = 0; i < numValues; i++) {
				int valueDateMonth = ParamUtil.getInteger(
					actionRequest, name + "Month" + "_" + i, 1);
				int valueDateDay = ParamUtil.getInteger(
					actionRequest, name + "Day" + "_" + i, 1);
				int valueDateYear = ParamUtil.getInteger(
					actionRequest, name + "Year" + "_" + i, 1970);
				int valueDateHour = ParamUtil.getInteger(
					actionRequest, name + "Hour" + "_" + i);
				int valueDateMinute = ParamUtil.getInteger(
					actionRequest, name + "Minute" + "_" + i);
				int valueDateAmPm = ParamUtil.getInteger(
					actionRequest, name + "AmPm" + "_" + i);

				if (valueDateAmPm == Calendar.PM) {
					valueDateHour += 12;
				}

				values[i] = PortalUtil.getDate(
					valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
					valueDateMinute, user.getTimeZone(),
					new ValueDataException());
			}

			value = values;
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value = ParamUtil.getDouble(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(actionRequest, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(actionRequest, name), "\n");

			value = StringUtil.split(ParamUtil.getString(actionRequest, name));
		}
		else {
			value = ParamUtil.getString(actionRequest, name);
		}

		return value;
	}

	public void updateExpando(ActionRequest actionRequest)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		int type = ParamUtil.getInteger(actionRequest, "type");

		Object defaultValue = getValue(actionRequest, type, "defaultValue");
		Object value = getValue(actionRequest, type, "value");

		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		ExpandoBridge expandoBridge = new ExpandoBridgeImpl(
			modelResource, resourcePrimKey);

		if (Validator.isNotNull(defaultValue)) {
			expandoBridge.setAttributeDefault(name, defaultValue);
		}

		if (Validator.isNotNull(value)) {
			expandoBridge.setAttribute(name, value);
		}
	}

	public static void updateExpandos(
			ExpandoBridge expandoBridge, ActionRequest actionRequest)
		throws Exception {

		Enumeration<String> parameterNames = actionRequest.getParameterNames();

		List<String> expandoAttributes = new ArrayList<String>();

		while (parameterNames.hasMoreElements()) {
			String param = parameterNames.nextElement();

			if (param.indexOf("ExpandoAttributeName(") != -1) {
				expandoAttributes.add(
					ParamUtil.getString(actionRequest, param));
			}
		}

		for (String expandoAttribute : expandoAttributes) {
			int type = expandoBridge.getAttributeType(expandoAttribute);

			Object value = getValue(
				actionRequest, type,
				"ExpandoAttribute(" + expandoAttribute + ")");

			expandoBridge.setAttribute(expandoAttribute, value);
		}
	}

}
