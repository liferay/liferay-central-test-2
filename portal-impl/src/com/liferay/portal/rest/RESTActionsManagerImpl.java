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

package com.liferay.portal.rest;

import com.liferay.portal.kernel.rest.RESTAction;
import com.liferay.portal.kernel.rest.RESTActionsManager;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SortedArrayList;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class RESTActionsManagerImpl implements RESTActionsManager {

	public RESTActionsManagerImpl() {
		_restActionConfig = new SortedArrayList<RESTActionConfig>();
		_pathBinarySearch = new PathBinarySearch();
	}

	public List<String[]> dumpMappings() {
		List<String[]> mappings = new ArrayList<String[]>();

		for (RESTActionConfig restActionConfig : _restActionConfig) {

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

	public RESTAction lookup(HttpServletRequest request) {

		String path = GetterUtil.getString(request.getPathInfo());

		String method = GetterUtil.getString(request.getMethod());

		String pathParameters = null;

		int pathParametersIndex = _indexOfPathParameters(path);

		if (pathParametersIndex != -1) {
			pathParameters = path.substring(pathParametersIndex);

			path = path.substring(0, pathParametersIndex);
		}

		RESTActionParameters restActionParameters = new RESTActionParameters();

		restActionParameters.collectAll(request, pathParameters);

		String parameterNames[] = restActionParameters.extractParameterNames();

		int index = _lookup(path, method, parameterNames);

		if (index == -1) {
			throw new RuntimeException(
				"No REST action associated with path " + path +
					" and method " + method);
		}

		RESTActionConfig restActionConfig = _restActionConfig.get(index);

		return new RESTActionImpl(restActionConfig, restActionParameters);
	}

	public void registerRESTAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		RESTActionConfig restActionConfig =
			new RESTActionConfig(actionClass, actionMethod, path, method);

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

	private int _indexOfPathParameters(String path) {

		int index = path.indexOf('/', 1);

		index = path.indexOf('/', index + 1);

		return index;
	}

	private int _lookup(String path, String method, String[] parameterNames) {

		int firstIndex = _pathBinarySearch.findFirst(path);

		if (firstIndex == -1) {
			return -1;
		}

		int lastIndex = _pathBinarySearch.findLast(path, firstIndex);

		if (lastIndex == -1) {
			lastIndex = firstIndex;
		}

		int found = -1;

		int max = -1;

		for (int i = firstIndex; i <= lastIndex; i++) {
			RESTActionConfig config = _restActionConfig.get(i);

			if (config.getMethod() != null &&
				!config.getMethod().equals(method)) {

				continue;
			}

			String[] methodParameterNames = config.getParameterNames();

			int matched =
				_countMatchedElements(parameterNames, methodParameterNames);

			if (matched > max) {
				max = matched;

				found = i;
			}
		}
		return found;

	}

	private class PathBinarySearch extends BinarySearch<String> {

		protected int compare(int index, String element) {
			RESTActionConfig rac = _restActionConfig.get(index);

			return rac.getPath().compareTo(element);
		}

		protected int getLastIndex() {
			return _restActionConfig.size() - 1;
		}

	}
	private BinarySearch<String> _pathBinarySearch;

	private SortedArrayList<RESTActionConfig> _restActionConfig;

}