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
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Spasic
 */
public class RESTActionsManagerImpl implements RESTActionsManager {

	public RESTActionsManagerImpl() {
		_pathChunksBinarySearch = new PathChunksBinarySearch();
		_restActionConfigSets = new SortedArrayList<RESTActionConfigSet>();
		_restActionConfigSetsBinarySearch = BinarySearch.forList(
			_restActionConfigSets);
	}

	public List<String[]> dumpMappings() {
		List<String[]> mappings = new ArrayList<String[]>();

		for (RESTActionConfigSet restActionConfigSet : _restActionConfigSets) {
			List<RESTActionConfig> restActionConfigs =
				restActionConfigSet.getActionConfigs();

			for (RESTActionConfig restActionConfig : restActionConfigs) {
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
		}

		return mappings;
	}

	public RESTAction lookup(String path, String method) {
		String[] pathChunks = StringUtil.split(
			path.substring(1), StringPool.SLASH);

		int low = 0;
		int high = _restActionConfigSets.size() - 1;

		int pathMacroIndex = 0;

		for (int depth = 0; depth < pathChunks.length; depth++) {
			String pathChunk = pathChunks[depth];

			_pathChunksBinarySearch._depth = depth;

			int nextLow = _pathChunksBinarySearch.findFirst(
				pathChunk, low, high);

			if (nextLow < 0) {
				int pathChunkndex = _getPathChunkIndex(
					pathChunk, depth, pathMacroIndex, low, high);

				if (pathChunkndex == -1) {
					low = nextLow;

					break;
				}
				else {
					pathMacroIndex++;

					low = pathChunkndex;
					high = pathChunkndex;
				}
			}
			else {
				low = nextLow;

				if (high > low) {
					high = _pathChunksBinarySearch.findLast(
						pathChunk, low, high);
				}
			}
		}

		if (low < 0) {
			return null;
		}

		RESTActionConfigSet restActionConfigSet = _restActionConfigSets.get(
			low);

		RESTActionConfig restActionConfig =
			restActionConfigSet.getRESTActionConfig(method);

		if (restActionConfig == null) {
			return null;
		}

		String[] restActionConfigSetPathChunks =
			restActionConfigSet.getPathChunks();

		if (restActionConfigSetPathChunks.length != pathChunks.length) {
			return null;
		}

		return new RESTActionImpl(restActionConfig, pathChunks);
	}

	public void registerRESTAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		RESTActionConfig restActionConfig = new RESTActionConfig();

		restActionConfig.setActionClass(actionClass);
		restActionConfig.setActionMethod(actionMethod);
		restActionConfig.setPath(path);
		restActionConfig.setMethod(method);

		RESTActionConfigSet restActionConfigSet = new RESTActionConfigSet();

		restActionConfigSet.setPath(restActionConfig.getPath());

		int index = _restActionConfigSetsBinarySearch.find(restActionConfigSet);

		if (index < 0) {
			_restActionConfigSets.add(restActionConfigSet);
		}
		else {
			restActionConfigSet = _restActionConfigSets.get(index);
		}

		restActionConfigSet.addRESTActionConfig(restActionConfig);
	}

	private int _getPathChunkIndex(
		String pathChunk, int depth, int pathMacroIndex, int low, int high) {

		for (int i = low; i <= high; i++) {
			RESTActionConfigSet restActionConfigSet = _restActionConfigSets.get(
				i);

			PathMacro[] pathMacros = restActionConfigSet.getPathMacros();

			if (pathMacroIndex >= pathMacros.length) {
				continue;
			}

			PathMacro pathMacro = pathMacros[pathMacroIndex];

			if (pathMacro.getIndex() != depth) {
				continue;
			}

			if (!pathChunk.startsWith(pathMacro.getOffsetLeft())) {
				continue;
			}

			if (!pathChunk.endsWith(pathMacro.getOffsetRight())) {
				continue;
			}

			if (pathMacro.getPattern() != null) {
				String offsetLeft = pathMacro.getOffsetLeft();
				String offsetRight = pathMacro.getOffsetRight();

				String value = pathChunk.substring(
					offsetLeft.length(),
					pathChunk.length() - offsetRight.length());

				Pattern pattern = pathMacro.getPattern();

				Matcher matcher = pattern.matcher(value);

				if (!matcher.matches()) {
					continue;
				}
			}

			return i;
		}

		return -1;
	}

	private class PathChunksBinarySearch extends BinarySearch<String> {

		protected int compare(int index, String element) {
			String pathChunk = get(index, _depth);

			return pathChunk.compareTo(element);
		}

		protected String get(int index, int depth) {
			RESTActionConfigSet restActionConfigSet =
				_restActionConfigSets.get(index);

			String[] pathChunks = restActionConfigSet.getPathChunks();

			return pathChunks[depth];
		}

		protected int getLastIndex() {
			return _restActionConfigSets.size() - 1;
		}

		private int _depth;

	}

	private PathChunksBinarySearch _pathChunksBinarySearch;
	private SortedArrayList<RESTActionConfigSet> _restActionConfigSets;
	private BinarySearch<RESTActionConfigSet> _restActionConfigSetsBinarySearch;

}