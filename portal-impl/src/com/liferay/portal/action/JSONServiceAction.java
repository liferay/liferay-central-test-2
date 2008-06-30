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

package com.liferay.portal.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portlet.tags.model.TagsAssetDisplay;
import com.liferay.portlet.tags.model.TagsAssetType;
import com.liferay.util.JSONUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="JSONServiceAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JSONServiceAction extends JSONAction {

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

				if (args[i] == null) {
					return null;
				}
			}

			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Invoking class " + classObj + " on method " +
							method.getName() + " with args " + args);
				}

				Object returnObj = method.invoke(classObj, args);

				if (returnObj != null) {
					if (returnObj instanceof JSONArray) {
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

						JSONObject jsonObj = new JSONObject();

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
					JSONObject jsonObj = new JSONObject();

					return jsonObj.toString();
				}
			}
			catch (InvocationTargetException ite) {
				JSONObject jsonObj = new JSONObject();

				jsonObj.put("exception", ite.getCause().toString());

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

		if (parameterTypeName.equals("boolean") ||
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
		else if (parameterTypeName.equals(String.class.getName())) {
			return ParamUtil.getString(request, parameter);
		}
		else if (parameterTypeName.equals("[Ljava.lang.String;")) {
			return StringUtil.split(ParamUtil.getString(request, parameter));
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

		return null;
	}

	protected String getReturnValue(TagsAssetDisplay assetDisplay)
		throws Exception {

		JSONObject jsonObj = toJSONObject(assetDisplay);

		return jsonObj.toString();
	}

	protected String getReturnValue(TagsAssetDisplay[] assetDisplays)
		throws Exception {

		JSONArray jsonArray = new JSONArray();

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

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < assetTypes.length; i++) {
			TagsAssetType assetType = assetTypes[i];

			jsonArray.put(toJSONObject(assetType));
		}

		return jsonArray.toString();
	}

	public static JSONObject toJSONObject(TagsAssetDisplay assetDisplay) {
		JSONObject jsonObj = new JSONObject();

		JSONUtil.put(jsonObj, "assetId", assetDisplay.getAssetId());
		JSONUtil.put(jsonObj, "companyId", assetDisplay.getCompanyId());
		JSONUtil.put(jsonObj, "userId", assetDisplay.getUserId());
		JSONUtil.put(jsonObj, "userName", assetDisplay.getUserName());
		JSONUtil.put(jsonObj, "createDate", assetDisplay.getCreateDate());
		JSONUtil.put(jsonObj, "modifiedDate", assetDisplay.getModifiedDate());
		JSONUtil.put(jsonObj, "classNameId", assetDisplay.getClassNameId());
		JSONUtil.put(jsonObj, "className", assetDisplay.getClassName());
		JSONUtil.put(jsonObj, "classPK", assetDisplay.getClassPK());
		JSONUtil.put(jsonObj, "portletId", assetDisplay.getPortletId());
		JSONUtil.put(jsonObj, "portletTitle", assetDisplay.getPortletTitle());
		JSONUtil.put(jsonObj, "startDate", assetDisplay.getStartDate());
		JSONUtil.put(jsonObj, "endDate", assetDisplay.getEndDate());
		JSONUtil.put(jsonObj, "publishDate", assetDisplay.getPublishDate());
		JSONUtil.put(
			jsonObj, "expirationDate", assetDisplay.getExpirationDate());
		JSONUtil.put(jsonObj, "mimeType", assetDisplay.getMimeType());
		JSONUtil.put(jsonObj, "title", assetDisplay.getTitle());
		JSONUtil.put(jsonObj, "description", assetDisplay.getDescription());
		JSONUtil.put(jsonObj, "summary", assetDisplay.getSummary());
		JSONUtil.put(jsonObj, "url", assetDisplay.getUrl());
		JSONUtil.put(jsonObj, "height", assetDisplay.getHeight());
		JSONUtil.put(jsonObj, "width", assetDisplay.getWidth());
		JSONUtil.put(jsonObj, "priority", assetDisplay.getPriority());
		JSONUtil.put(jsonObj, "viewCount", assetDisplay.getViewCount());
		JSONUtil.put(jsonObj, "tagsEntries", assetDisplay.getTagsEntries());

		return jsonObj;
	}

	public static JSONObject toJSONObject(TagsAssetType assetType) {
		JSONObject jsonObj = new JSONObject();

		JSONUtil.put(jsonObj, "classNameId", assetType.getClassNameId());
		JSONUtil.put(jsonObj, "className", assetType.getClassName());
		JSONUtil.put(jsonObj, "portletId", assetType.getPortletId());
		JSONUtil.put(jsonObj, "portletTitle", assetType.getPortletTitle());

		return jsonObj;
	}

	private static Log _log = LogFactory.getLog(JSONServiceAction.class);

	private Map<String, Object[]> _methodCache =
		new HashMap<String, Object[]>();

}