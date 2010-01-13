/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import com.liferay.util.LocalizationUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("entryId", assetEntryDisplay.getEntryId());
		jsonObj.put("companyId", assetEntryDisplay.getCompanyId());
		jsonObj.put("userId", assetEntryDisplay.getUserId());
		jsonObj.put("userName", assetEntryDisplay.getUserName());
		jsonObj.put("createDate", assetEntryDisplay.getCreateDate());
		jsonObj.put("modifiedDate", assetEntryDisplay.getModifiedDate());
		jsonObj.put("classNameId", assetEntryDisplay.getClassNameId());
		jsonObj.put("className", assetEntryDisplay.getClassName());
		jsonObj.put("classPK", assetEntryDisplay.getClassPK());
		jsonObj.put("portletId", assetEntryDisplay.getPortletId());
		jsonObj.put("portletTitle", assetEntryDisplay.getPortletTitle());
		jsonObj.put("startDate", assetEntryDisplay.getStartDate());
		jsonObj.put("endDate", assetEntryDisplay.getEndDate());
		jsonObj.put("publishDate", assetEntryDisplay.getPublishDate());
		jsonObj.put("expirationDate", assetEntryDisplay.getExpirationDate());
		jsonObj.put("mimeType", assetEntryDisplay.getMimeType());
		jsonObj.put("title", assetEntryDisplay.getTitle());
		jsonObj.put("description", assetEntryDisplay.getDescription());
		jsonObj.put("summary", assetEntryDisplay.getSummary());
		jsonObj.put("url", assetEntryDisplay.getUrl());
		jsonObj.put("height", assetEntryDisplay.getHeight());
		jsonObj.put("width", assetEntryDisplay.getWidth());
		jsonObj.put("priority", assetEntryDisplay.getPriority());
		jsonObj.put("viewCount", assetEntryDisplay.getViewCount());
		jsonObj.put(
			"assetCategoryIds",
			StringUtil.merge(assetEntryDisplay.getCategoryIds()));
		jsonObj.put("assetTagNames", assetEntryDisplay.getTagNames());

		return jsonObj;
	}

	public static JSONObject toJSONObject(AssetEntryType assetEntryType) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("classNameId", assetEntryType.getClassNameId());
		jsonObj.put("className", assetEntryType.getClassName());
		jsonObj.put("portletId", assetEntryType.getPortletId());
		jsonObj.put("portletTitle", assetEntryType.getPortletTitle());

		return jsonObj;
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
					JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

					return jsonObj.toString();
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

				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

				if (e instanceof InvocationTargetException) {
					jsonObj.put("exception", e.getCause().toString());
				}
				else {
					jsonObj.put("exception", e.getMessage());
				}

				return jsonObj.toString();
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
			methodAndParameterTypes =
				new Object[] {method, methodParameterTypes};

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

		JSONObject jsonObj = toJSONObject(assetEntryDisplay);

		return jsonObj.toString();
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

		JSONObject jsonObj = toJSONObject(assetEntryType);

		return jsonObj.toString();
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

			JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

			jsonObj.put("returnValue", returnObj.toString());

			return jsonObj.toString();
		}
		else if (returnObj instanceof BaseModel<?>) {
			String serlializerClassName = getSerializerClassName(returnObj);

			MethodWrapper methodWrapper = new MethodWrapper(
				serlializerClassName, "toJSONObject", returnObj);

			JSONObject jsonObj = (JSONObject)MethodInvoker.invoke(
				methodWrapper, false);

			return jsonObj.toString();
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
			JSONObject jsonObj = (JSONObject)returnObj;

			return jsonObj.toString();
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
			!className.endsWith("LocalServiceUtil")) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONServiceAction.class);

	private Map<String, Object[]> _methodCache =
		new HashMap<String, Object[]>();

}