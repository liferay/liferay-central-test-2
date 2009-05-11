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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portlet.tags.model.TagsAssetDisplay;
import com.liferay.portlet.tags.model.TagsAssetType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 *
 */
public class JSONServiceAction extends JSONAction {

	public static JSONObject toJSONObject(TagsAssetDisplay assetDisplay) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("assetId", assetDisplay.getAssetId());
		jsonObj.put("companyId", assetDisplay.getCompanyId());
		jsonObj.put("userId", assetDisplay.getUserId());
		jsonObj.put("userName", assetDisplay.getUserName());
		jsonObj.put("createDate", assetDisplay.getCreateDate());
		jsonObj.put("modifiedDate", assetDisplay.getModifiedDate());
		jsonObj.put("classNameId", assetDisplay.getClassNameId());
		jsonObj.put("className", assetDisplay.getClassName());
		jsonObj.put("classPK", assetDisplay.getClassPK());
		jsonObj.put("portletId", assetDisplay.getPortletId());
		jsonObj.put("portletTitle", assetDisplay.getPortletTitle());
		jsonObj.put("startDate", assetDisplay.getStartDate());
		jsonObj.put("endDate", assetDisplay.getEndDate());
		jsonObj.put("publishDate", assetDisplay.getPublishDate());
		jsonObj.put("expirationDate", assetDisplay.getExpirationDate());
		jsonObj.put("mimeType", assetDisplay.getMimeType());
		jsonObj.put("title", assetDisplay.getTitle());
		jsonObj.put("description", assetDisplay.getDescription());
		jsonObj.put("summary", assetDisplay.getSummary());
		jsonObj.put("url", assetDisplay.getUrl());
		jsonObj.put("height", assetDisplay.getHeight());
		jsonObj.put("width", assetDisplay.getWidth());
		jsonObj.put("priority", assetDisplay.getPriority());
		jsonObj.put("viewCount", assetDisplay.getViewCount());
		jsonObj.put("tagsEntries", assetDisplay.getTagsEntries());

		return jsonObj;
	}

	public static JSONObject toJSONObject(TagsAssetType assetType) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("classNameId", assetType.getClassNameId());
		jsonObj.put("className", assetType.getClassName());
		jsonObj.put("portletId", assetType.getPortletId());
		jsonObj.put("portletTitle", assetType.getPortletTitle());

		return jsonObj;
	}

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String className = ParamUtil.getString(request, "serviceClassName");
		String methodName = ParamUtil.getString(request, "serviceMethodName");
		String[] serviceParameters = StringUtil.split(
			ParamUtil.getString(request, "serviceParameters"));
		String[] serviceParameterTypes = StringUtil.split(
			ParamUtil.getString(request, "serviceParameterTypes"));

		if (!isValidRequest(request)) {
			return null;
		}

		Class<?> classObj = Class.forName(className);

		Object[] methodAndParameterTypes = getMethodAndParameterTypes(
			classObj, methodName, serviceParameters, serviceParameterTypes);

		if (methodAndParameterTypes != null) {
			Method method = (Method)methodAndParameterTypes[0];
			Class<?>[] parameterTypes = (Class[])methodAndParameterTypes[1];
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
							method.getName() + " with args " + args);
				}

				Object returnObj = method.invoke(classObj, args);

				if (returnObj != null) {
					if (returnObj instanceof BaseModel) {
						String serlializerClassName = getSerializerClassName(
							returnObj);

						MethodWrapper methodWrapper = new MethodWrapper(
							serlializerClassName, "toJSONObject", returnObj);

						JSONObject jsonObj = (JSONObject)MethodInvoker.invoke(
							methodWrapper, false);

						return jsonObj.toString();
					}
					else if (returnObj instanceof List) {
						JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

						List<Object> returnList = (List<Object>)returnObj;

						if (!returnList.isEmpty()) {
							Object returnItem0 = returnList.get(0);

							String serlializerClassName =
								getSerializerClassName(returnItem0);

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
					else if (returnObj instanceof Boolean ||
							 returnObj instanceof Double ||
							 returnObj instanceof Integer ||
							 returnObj instanceof Long ||
							 returnObj instanceof Short ||
							 returnObj instanceof String) {

						JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

						jsonObj.put("returnValue", returnObj.toString());

						return jsonObj.toString();
					}
					else {
						String returnValue = getReturnValue(returnObj);

						if (returnValue == null) {
							_log.error(
								"Unsupported return type for class " +
									classObj + " and method " + methodName);
						}

						return returnValue;
					}
				}
				else {
					JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

					return jsonObj.toString();
				}
			}
			catch (Exception e) {
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
			String parameter, Class<?> parameterType)
		throws Exception {

		String parameterTypeName = parameterType.getName();

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
		else if (parameterTypeName.equals("[Ljava.lang.String;")) {
			return StringUtil.split(value);
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
		Class<?>[] methodParameterTypes = null;

		Method[] methods = classObj.getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method curMethod = methods[i];

			if (curMethod.getName().equals(methodName)) {
				Class<?>[] curParameterTypes = curMethod.getParameterTypes();

				if (curParameterTypes.length == parameters.length) {
					if ((parameterTypes.length > 0) &&
						(parameterTypes.length == curParameterTypes.length)) {

						boolean match = true;

						for (int j = 0; j < parameterTypes.length; j++) {
							String t1 = parameterTypes[j];
							String t2 = curParameterTypes[j].getName();

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

	protected String getReturnValue(Object returnObj) throws Exception {
		if (returnObj instanceof TagsAssetDisplay) {
			return getReturnValue((TagsAssetDisplay)returnObj);
		}
		else if (returnObj instanceof TagsAssetDisplay[]) {
			return getReturnValue((TagsAssetDisplay[])returnObj);
		}
		else if (returnObj instanceof TagsAssetType) {
			return getReturnValue((TagsAssetType)returnObj);
		}
		else if (returnObj instanceof TagsAssetType[]) {
			return getReturnValue((TagsAssetType[])returnObj);
		}
		else {
			return JSONFactoryUtil.serialize(returnObj);
		}
	}

	protected String getReturnValue(TagsAssetDisplay assetDisplay)
		throws Exception {

		JSONObject jsonObj = toJSONObject(assetDisplay);

		return jsonObj.toString();
	}

	protected String getReturnValue(TagsAssetDisplay[] assetDisplays)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < assetDisplays.length; i++) {
			TagsAssetDisplay assetDisplay = assetDisplays[i];

			jsonArray.put(toJSONObject(assetDisplay));
		}

		return jsonArray.toString();
	}

	protected String getReturnValue(TagsAssetType assetType)
		throws Exception {

		JSONObject jsonObj = toJSONObject(assetType);

		return jsonObj.toString();
	}

	protected String getReturnValue(TagsAssetType[] assetTypes)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < assetTypes.length; i++) {
			TagsAssetType assetType = assetTypes[i];

			jsonArray.put(toJSONObject(assetType));
		}

		return jsonArray.toString();
	}

	protected String getSerializerClassName(Object obj) {
		String serlializerClassName = StringUtil.replace(
			obj.getClass().getName(),
			new String[] {".model.impl.", "Impl"},
			new String[] {".service.http.", "JSONSerializer"});

		return serlializerClassName;
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