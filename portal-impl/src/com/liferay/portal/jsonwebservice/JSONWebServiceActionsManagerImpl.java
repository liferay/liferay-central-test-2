/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerImpl
	implements JSONWebServiceActionsManager {

	public JSONWebServiceActionsManagerImpl() {
		_jsonWebServiceActionConfig =
			new SortedArrayList<JSONWebServiceActionConfig>();

		_pathBinarySearch = new PathBinarySearch();
	}

	public List<String[]> dumpMappings() {
		List<String[]> mappings = new ArrayList<String[]>();

		for (JSONWebServiceActionConfig actionConfig :
			_jsonWebServiceActionConfig) {

			String[] parameterNames = actionConfig.getParameterNames();

			Class<?> actionClass = actionConfig.getActionClass();
			Method actionMethod = actionConfig.getActionMethod();

			String methodName = actionMethod.getName();

			methodName += "(";

			for (int i = 0; i < parameterNames.length; i++) {
				if (i != 0) {
					methodName += ", ";
				}

				methodName += parameterNames[i];
			}

			methodName += ")";

			String[] mapping = new String[] {
				actionConfig.getMethod(), actionConfig.getPath(),
				actionClass.getName() + '#' + methodName
			};

			mappings.add(mapping);
		}

		return mappings;
	}

	public JSONWebServiceAction lookup(HttpServletRequest request) {
		String path = GetterUtil.getString(request.getPathInfo());

		String method = GetterUtil.getString(request.getMethod());

		String pathParameters = null;

		JSONRPCRequest jsonRpcRequest = null;

		int pathParametersIndex = _getPathParametersIndex(path);

		if (pathParametersIndex != -1) {
			pathParameters = path.substring(pathParametersIndex);

			path = path.substring(0, pathParametersIndex);
		}
		else {
			if (method.equals(HttpMethods.POST) &&
				!PortalUtil.isMultipartRequest(request)) {

				jsonRpcRequest = new JSONRPCRequest(request);

				if (jsonRpcRequest.isValid()) {
					path += StringPool.SLASH + jsonRpcRequest.getMethod();

					method = null;
				}
				else {
					jsonRpcRequest = null;
				}
			}
		}

		JSONWebServiceActionParameters
			actionParameters = new JSONWebServiceActionParameters();

		actionParameters.collectAll(
			request, pathParameters, jsonRpcRequest);

		String[] parameterNames = actionParameters.getParameterNames();

		int actionConfigIndex = _getJSONWebServiceActionConfigIndex(
			path, method, parameterNames);

		if (actionConfigIndex == -1) {
			throw new RuntimeException(
				"No JSON web service action associated with path " + path +
					" and method " + method);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfig.get(actionConfigIndex);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, actionParameters);
	}

	public void registerJSONWebServiceAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			new JSONWebServiceActionConfig(
				actionClass, actionMethod, path, method);

		_jsonWebServiceActionConfig.add(jsonWebServiceActionConfig);
	}

	private int _countMatchedElements(
		String[] targetArray, String[] subjectArray) {

		int matched = 0;

		for (String target : targetArray) {
			for (String subject : subjectArray) {
				if (subject.equals(target)) {
					matched++;

					break;
				}
			}
		}

		return matched;
	}

	private int _getPathParametersIndex(String path) {
		int index = path.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			index = path.indexOf(CharPool.SLASH, index + 1);
		}

		return index;
	}

	private int _getJSONWebServiceActionConfigIndex(
		String path, String method, String[] parameterNames) {

		int firstIndex = _pathBinarySearch.findFirst(path);

		if (firstIndex < 0) {
			return -1;
		}

		int lastIndex = _pathBinarySearch.findLast(path, firstIndex);

		if (lastIndex < 0) {
			lastIndex = firstIndex;
		}

		int index = -1;

		int max = -1;

		for (int i = firstIndex; i <= lastIndex; i++) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig
				= _jsonWebServiceActionConfig.get(i);

			String actionConfigMethod =
				jsonWebServiceActionConfig.getMethod();

			if (method != null) {
				if ((actionConfigMethod != null) &&
					!actionConfigMethod.equals(method)) {

					continue;
				}
			}

			String[] actionConfigParameterNames =
				jsonWebServiceActionConfig.getParameterNames();

			int count = _countMatchedElements(
				parameterNames, actionConfigParameterNames);

			if (count > max) {
				max = count;

				index = i;
			}
		}

		return index;
	}

	private class PathBinarySearch extends BinarySearch<String> {

		protected int compare(int index, String element) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				_jsonWebServiceActionConfig.get(index);

			String path = jsonWebServiceActionConfig.getPath();

			return path.compareTo(element);
		}

		protected int getLastIndex() {
			return _jsonWebServiceActionConfig.size() - 1;
		}

	}
	private BinarySearch<String> _pathBinarySearch;
	private SortedArrayList<JSONWebServiceActionConfig>
		_jsonWebServiceActionConfig;

}