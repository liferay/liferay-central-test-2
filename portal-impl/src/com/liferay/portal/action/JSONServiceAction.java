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

package com.liferay.portal.action;

import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portlet.asset.model.AssetEntryDisplay;
import com.liferay.portlet.asset.model.AssetEntryType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="JSONServiceAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Karthik Sudarshan
 * @author Julio Camarero
 */
public class JSONServiceAction extends JSONAction {

	public static JSONObject toJSONObject(AssetEntryDisplay assetEntryDisplay) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("entryId", assetEntryDisplay.getEntryId());
		jsonObject.put("companyId", assetEntryDisplay.getCompanyId());
		jsonObject.put("userId", assetEntryDisplay.getUserId());
		jsonObject.put("userName", assetEntryDisplay.getUserName());
		jsonObject.put("createDate", assetEntryDisplay.getCreateDate());
		jsonObject.put("modifiedDate", assetEntryDisplay.getModifiedDate());
		jsonObject.put("classNameId", assetEntryDisplay.getClassNameId());
		jsonObject.put("className", assetEntryDisplay.getClassName());
		jsonObject.put("classPK", assetEntryDisplay.getClassPK());
		jsonObject.put("portletId", assetEntryDisplay.getPortletId());
		jsonObject.put("portletTitle", assetEntryDisplay.getPortletTitle());
		jsonObject.put("startDate", assetEntryDisplay.getStartDate());
		jsonObject.put("endDate", assetEntryDisplay.getEndDate());
		jsonObject.put("publishDate", assetEntryDisplay.getPublishDate());
		jsonObject.put("expirationDate", assetEntryDisplay.getExpirationDate());
		jsonObject.put("mimeType", assetEntryDisplay.getMimeType());
		jsonObject.put("title", assetEntryDisplay.getTitle());
		jsonObject.put("description", assetEntryDisplay.getDescription());
		jsonObject.put("summary", assetEntryDisplay.getSummary());
		jsonObject.put("url", assetEntryDisplay.getUrl());
		jsonObject.put("height", assetEntryDisplay.getHeight());
		jsonObject.put("width", assetEntryDisplay.getWidth());
		jsonObject.put("priority", assetEntryDisplay.getPriority());
		jsonObject.put("viewCount", assetEntryDisplay.getViewCount());
		jsonObject.put(
			"assetCategoryIds",
			StringUtil.merge(assetEntryDisplay.getCategoryIds()));
		jsonObject.put("assetTagNames", assetEntryDisplay.getTagNames());

		return jsonObject;
	}

	public static JSONObject toJSONObject(AssetEntryType assetEntryType) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("classNameId", assetEntryType.getClassNameId());
		jsonObject.put("className", assetEntryType.getClassName());
		jsonObject.put("portletId", assetEntryType.getPortletId());
		jsonObject.put("portletTitle", assetEntryType.getPortletTitle());

		return jsonObject;
	}

	public JSONServiceAction() {
		_invalidClassNames.add(DLLocalServiceUtil.class.getName());
		_invalidClassNames.add(DLServiceUtil.class.getName());
		_invalidClassNames.add(MailServiceUtil.class.getName());
	}

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String className = ParamUtil.getString(request, "serviceClassName");
		String methodName = ParamUtil.getString(request, "serviceMethodName");
		String[] serviceParameters = getStringArrayFromJSON(
			request, "serviceParameters");
		String[] serviceParameterTypes = getStringArrayFromJSON(
			request, "serviceParameterTypes");

		if (!isValidRequest(request)) {
			return null;
		}

		Class<?> classObj = Class.forName(className);

		Object[] methodAndParameterTypes = getMethodAndParameterTypes(
			classObj, methodName, serviceParameters, serviceParameterTypes);

		if (methodAndParameterTypes != null) {
			Method method = (Method)methodAndParameterTypes[0];
			Type[] parameterTypes = (Type[])methodAndParameterTypes[1];
			Object[] args = new Object[serviceParameters.length];

			for (int i = 0; i < serviceParameters.length; i++) {
				args[i] = getArgValue(
					request, classObj, methodName, serviceParameters[i],
					parameterTypes[i]);
			}

			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Invoking class " + classObj + " on method " +
							method.getName() + " with args " +
								Arrays.toString(args));
				}

				Object returnObj = method.invoke(classObj, args);

				if (returnObj != null) {
					return getReturnValue(returnObj);
				}
				else {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					return jsonObject.toString();
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Invoked class " + classObj + " on method " +
							method.getName() + " with args " +
								Arrays.toString(args),
						e);
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				if (e instanceof InvocationTargetException) {
					jsonObject.put("exception", e.getCause().toString());
				}
				else {
					jsonObject.put("exception", e.getMessage());
				}

				return jsonObject.toString();
			}
		}

		return null;
	}

	protected Object getArgValue(
			HttpServletRequest request, Class<?> classObj, String methodName,
			String parameter, Type parameterType)
		throws Exception {

		String parameterTypeName = getTypeName(parameterType);

		String value = ParamUtil.getString(request, parameter);

		if (Validator.isNull(value) &&
			!parameterTypeName.equals("[Ljava.lang.String;")) {

			return null;
		}
		else if (parameterTypeName.equals("boolean") ||
				 parameterTypeName.equals(Boolean.class.getName())) {

			return Boolean.valueOf(ParamUtil.getBoolean(request, parameter));
		}
		else if (parameterTypeName.equals("double") ||
				 parameterTypeName.equals(Double.class.getName())) {

			return new Double(ParamUtil.getDouble(request, parameter));
		}
		else if (parameterTypeName.equals("int") ||
				 parameterTypeName.equals(Integer.class.getName())) {

			return new Integer(ParamUtil.getInteger(request, parameter));
		}
		else if (parameterTypeName.equals("long") ||
				 parameterTypeName.equals(Long.class.getName())) {

			return new Long(ParamUtil.getLong(request, parameter));
		}
		else if (parameterTypeName.equals("short") ||
				 parameterTypeName.equals(Short.class.getName())) {

			return new Short(ParamUtil.getShort(request, parameter));
		}
		else if (parameterTypeName.equals(Date.class.getName())) {
			return new Date(ParamUtil.getLong(request, parameter));
		}
		else if (parameterTypeName.equals(ServiceContext.class.getName())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			jsonObject.put("javaClass", ServiceContext.class.getName());

			return ServiceContextUtil.deserialize(jsonObject);
		}
		else if (parameterTypeName.equals(String.class.getName())) {
			return value;
		}
		else if (parameterTypeName.equals("[Z")) {
			return ParamUtil.getBooleanValues(request, parameter);
		}
		else if (parameterTypeName.equals("[D")) {
			return ParamUtil.getDoubleValues(request, parameter);
		}
		else if (parameterTypeName.equals("[F")) {
			return ParamUtil.getFloatValues(request, parameter);
		}
		else if (parameterTypeName.equals("[I")) {
			return ParamUtil.getIntegerValues(request, parameter);
		}
		else if (parameterTypeName.equals("[J")) {
			return ParamUtil.getLongValues(request, parameter);
		}
		else if (parameterTypeName.equals("[S")) {
			return ParamUtil.getShortValues(request, parameter);
		}
		else if (parameterTypeName.equals("[Ljava.lang.String;")) {
			return StringUtil.split(value);
		}
		else if (parameterTypeName.equals("[[Z")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				boolean[][] doubleArray =
					new boolean[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getBoolean(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new boolean[0][0];
			}
		}
		else if (parameterTypeName.equals("[[D")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				double[][] doubleArray =
					new double[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getDouble(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new double[0][0];
			}
		}
		else if (parameterTypeName.equals("[[F")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				float[][] doubleArray =
					new float[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getFloat(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new float[0][0];
			}
		}
		else if (parameterTypeName.equals("[[I")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				int[][] doubleArray =
					new int[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getInteger(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new int[0][0];
			}
		}
		else if (parameterTypeName.equals("[[J")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				long[][] doubleArray =
					new long[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getLong(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new long[0][0];
			}
		}
		else if (parameterTypeName.equals("[[S")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				short[][] doubleArray =
					new short[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getShort(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new short[0][0];
			}
		}
		else if (parameterTypeName.equals("[[Ljava.lang.String")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				String[][] doubleArray =
					new String[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					doubleArray[i] = StringUtil.split(values[i]);
				}

				return doubleArray;
			}
			else {
				return new String[0][0];
			}
		}
		else if (parameterTypeName.equals(
			"java.util.Map<java.util.Locale, java.lang.String>")) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			return LocalizationUtil.deserialize(jsonObject);
		}
		else {
			_log.error(
				"Unsupported parameter type for class " + classObj +
					", method " + methodName + ", parameter " + parameter +
						", and type " + parameterTypeName);

			return null;
		}
	}

	protected Object[] getMethodAndParameterTypes(
			Class<?> classObj, String methodName, String[] parameters,
			String[] parameterTypes)
		throws Exception {

		String parameterNames = StringUtil.merge(parameters);

		String key =
			classObj.getName() + "_METHOD_NAME_" + methodName +
				"_PARAMETERS_" + parameterNames;

		Object[] methodAndParameterTypes = _methodCache.get(key);

		if (methodAndParameterTypes != null) {
			return methodAndParameterTypes;
		}

		Method method = null;
		Type[] methodParameterTypes = null;

		Method[] methods = classObj.getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method curMethod = methods[i];

			if (curMethod.getName().equals(methodName)) {
				Type[] curParameterTypes = curMethod.getGenericParameterTypes();

				if (curParameterTypes.length == parameters.length) {
					if ((parameterTypes.length > 0) &&
						(parameterTypes.length == curParameterTypes.length)) {

						boolean match = true;

						for (int j = 0; j < parameterTypes.length; j++) {
							String t1 = parameterTypes[j];
							String t2 = getTypeName(curParameterTypes[j]);

							if (!t1.equals(t2)) {
								match = false;
							}
						}

						if (match) {
							method = curMethod;
							methodParameterTypes = curParameterTypes;

							break;
						}
					}
					else if (method != null) {
						_log.error(
							"Obscure method name for class " + classObj +
								", method " + methodName + ", and parameters " +
									parameterNames);

						return null;
					}
					else {
						method = curMethod;
						methodParameterTypes = curParameterTypes;
					}
				}
			}
		}

		if (method != null) {
			methodAndParameterTypes = new Object[] {
				method, methodParameterTypes
			};

			_methodCache.put(key, methodAndParameterTypes);

			return methodAndParameterTypes;
		}
		else {
			_log.error(
				"No method found for class " + classObj + ", method " +
					methodName + ", and parameters " + parameterNames);

			return null;
		}
	}

	protected String getReturnValue(AssetEntryDisplay assetEntryDisplay)
		throws Exception {

		JSONObject jsonObject = toJSONObject(assetEntryDisplay);

		return jsonObject.toString();
	}

	protected String getReturnValue(AssetEntryDisplay[] assetEntryDisplays)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < assetEntryDisplays.length; i++) {
			AssetEntryDisplay assetEntryDisplay = assetEntryDisplays[i];

			jsonArray.put(toJSONObject(assetEntryDisplay));
		}

		return jsonArray.toString();
	}

	protected String getReturnValue(AssetEntryType assetEntryType)
		throws Exception {

		JSONObject jsonObject = toJSONObject(assetEntryType);

		return jsonObject.toString();
	}

	protected String getReturnValue(AssetEntryType[] assetEntryTypes)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < assetEntryTypes.length; i++) {
			AssetEntryType assetEntryType = assetEntryTypes[i];

			jsonArray.put(toJSONObject(assetEntryType));
		}

		return jsonArray.toString();
	}

	protected String getReturnValue(Object returnObj) throws Exception {
		if ((returnObj instanceof Boolean) || (returnObj instanceof Double) ||
			(returnObj instanceof Integer) || (returnObj instanceof Long) ||
			(returnObj instanceof Short) || (returnObj instanceof String)) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("returnValue", returnObj.toString());

			return jsonObject.toString();
		}
		else if (returnObj instanceof BaseModel<?>) {
			String serlializerClassName = getSerializerClassName(returnObj);

			MethodWrapper methodWrapper = new MethodWrapper(
				serlializerClassName, "toJSONObject", returnObj);

			JSONObject jsonObject = (JSONObject)MethodInvoker.invoke(
				methodWrapper, false);

			return jsonObject.toString();
		}
		else if (returnObj instanceof BaseModel<?>[]) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			BaseModel<?>[] returnArray = (BaseModel[])returnObj;

			if (returnArray.length > 0) {
				BaseModel<?> returnItem0 = returnArray[0];

				String serializerClassName = getSerializerClassName(
					returnItem0);

				MethodWrapper methodWrapper = new MethodWrapper(
					serializerClassName, "toJSONArray", returnObj);

				jsonArray = (JSONArray)MethodInvoker.invoke(
					methodWrapper, false);
			}

			return jsonArray.toString();
		}
		else if (returnObj instanceof BaseModel<?>[][]) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			BaseModel<?>[][] returnArray = (BaseModel<?>[][])returnObj;

			if ((returnArray.length > 0) &&
				(returnArray[0].length > 0)) {

				BaseModel<?> returnItem0 = returnArray[0][0];

				String serializerClassName = getSerializerClassName(
					returnItem0);

				MethodWrapper methodWrapper = new MethodWrapper(
					serializerClassName, "toJSONArray", returnObj);

				jsonArray = (JSONArray)MethodInvoker.invoke(
					methodWrapper, false);
			}

			return jsonArray.toString();
		}
		else if (returnObj instanceof List<?>) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			List<Object> returnList = (List<Object>)returnObj;

			if (!returnList.isEmpty()) {
				Object returnItem0 = returnList.get(0);

				String serlializerClassName = getSerializerClassName(
					returnItem0);

				MethodWrapper methodWrapper = new MethodWrapper(
					serlializerClassName, "toJSONArray", returnObj);

				jsonArray = (JSONArray)MethodInvoker.invoke(
					methodWrapper, false);
			}

			return jsonArray.toString();
		}
		else if (returnObj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)returnObj;

			return jsonArray.toString();
		}
		else if (returnObj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)returnObj;

			return jsonObject.toString();
		}
		else if (returnObj instanceof AssetEntryDisplay) {
			return getReturnValue((AssetEntryDisplay)returnObj);
		}
		else if (returnObj instanceof AssetEntryDisplay[]) {
			return getReturnValue((AssetEntryDisplay[])returnObj);
		}
		else if (returnObj instanceof AssetEntryType) {
			return getReturnValue((AssetEntryType)returnObj);
		}
		else if (returnObj instanceof AssetEntryType[]) {
			return getReturnValue((AssetEntryType[])returnObj);
		}
		else {
			return JSONFactoryUtil.serialize(returnObj);
		}
	}

	protected String getSerializerClassName(Object obj) {
		String serlializerClassName = StringUtil.replace(
			obj.getClass().getName(),
			new String[] {".model.impl.", "Impl"},
			new String[] {".service.http.", "JSONSerializer"});

		return serlializerClassName;
	}

	protected String[] getStringArrayFromJSON(
			HttpServletRequest request, String param)
		throws JSONException {

		String json = ParamUtil.getString(request, param, "[]");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		return ArrayUtil.toStringArray(jsonArray);
	}

	protected String getTypeName(Type type) {
		String name = type.toString();

		int pos = name.indexOf("class ");

		if (pos != -1) {
			name = name.substring("class ".length());
		}
		else {
			if (name.equals("boolean[]")) {
				name = "[Z";
			}
			else if (name.equals("double[]")) {
				name = "[D";
			}
			else if (name.equals("float[]")) {
				name = "[F";
			}
			else if (name.equals("int[]")) {
				name = "[I";
			}
			else if (name.equals("long[]")) {
				name = "[J";
			}
			else if (name.equals("short[]")) {
				name = "[S";
			}
		}

		return name;
	}

	protected boolean isValidRequest(HttpServletRequest request) {
		String className = ParamUtil.getString(request, "serviceClassName");

		if (className.contains(".service.") &&
			className.endsWith("ServiceUtil") &&
			!className.endsWith("LocalServiceUtil") &&
			!_invalidClassNames.contains(className)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONServiceAction.class);

	private Set<String> _invalidClassNames = new HashSet<String>();
	private Map<String, Object[]> _methodCache =
		new HashMap<String, Object[]>();

}