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
public class JSONWebServiceActionsManagerImpl implements JSONWebServiceActionsManager {

	public JSONWebServiceActionsManagerImpl() {
		_restActionConfig = new SortedArrayList<JSONWebServiceActionConfig>();
		_pathBinarySearch = new PathBinarySearch();
	}

	public List<String[]> dumpMappings() {
		List<String[]> mappings = new ArrayList<String[]>();

		for (JSONWebServiceActionConfig restActionConfig : _restActionConfig) {
			String[] parameterNames = restActionConfig.getParameterNames();

			Class<?> actionClass = restActionConfig.getActionClass();
			Method actionMethod = restActionConfig.getActionMethod();

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
				restActionConfig.getMethod(), restActionConfig.getPath(),
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
			restActionParameters = new JSONWebServiceActionParameters();

		restActionParameters.collectAll(
			request, pathParameters, jsonRpcRequest);

		String[] parameterNames = restActionParameters.getParameterNames();

		int restActionConfigIndex = _getRESTActionConfigIndex(
			path, method, parameterNames);

		if (restActionConfigIndex == -1) {
			throw new RuntimeException(
				"No REST action associated with path " + path +
					" and method " + method);
		}

		JSONWebServiceActionConfig restActionConfig = _restActionConfig.get(
			restActionConfigIndex);

		return new JSONWebServiceActionImpl(restActionConfig, restActionParameters);
	}

	public void registerRESTAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		JSONWebServiceActionConfig restActionConfig =
			new JSONWebServiceActionConfig(actionClass, actionMethod, path, method);

		_restActionConfig.add(restActionConfig);
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

	private int _getRESTActionConfigIndex(
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
			JSONWebServiceActionConfig restActionConfig = _restActionConfig.get(i);

			String restActionConfigMethod = restActionConfig.getMethod();

			if (method != null) {
				if ((restActionConfigMethod != null) &&
					!restActionConfigMethod.equals(method)) {

					continue;
				}
			}

			String[] restActionConfigParameterNames =
				restActionConfig.getParameterNames();

			int count = _countMatchedElements(
				parameterNames, restActionConfigParameterNames);

			if (count > max) {
				max = count;

				index = i;
			}
		}

		return index;
	}

	private class PathBinarySearch extends BinarySearch<String> {

		protected int compare(int index, String element) {
			JSONWebServiceActionConfig restActionConfig = _restActionConfig.get(index);

			String path = restActionConfig.getPath();

			return path.compareTo(element);
		}

		protected int getLastIndex() {
			return _restActionConfig.size() - 1;
		}

	}
	private BinarySearch<String> _pathBinarySearch;
	private SortedArrayList<JSONWebServiceActionConfig> _restActionConfig;

}