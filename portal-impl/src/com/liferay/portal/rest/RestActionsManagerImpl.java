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

import com.liferay.portal.kernel.rest.RestAction;
import com.liferay.portal.kernel.rest.RestActionsManager;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Spasic
 */
public class RestActionsManagerImpl implements RestActionsManager {

	public RestActionsManagerImpl() {
		_pathChunksBinarySearch = new PathChunksBinarySearch();
		_restActionConfigSets = new SortedArrayList<RestActionConfigSet>();
		_restActionConfigSetsBinarySearch = BinarySearch.forList(
			_restActionConfigSets);
	}

	public RestAction lookup(String path, String method) {

		String[] pathChunks = StringUtil
			.split(path.substring(1), StringPool.SLASH);

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

		RestActionConfigSet restActionConfigSet = _restActionConfigSets.get(
			low);

		RestActionConfig restActionConfig =
			restActionConfigSet.getRestActionConfig(method);

		if (restActionConfig == null) {
			return null;
		}

		String[] restActionConfigSetPathChunks =
			restActionConfigSet.getPathChunks();

		if (restActionConfigSetPathChunks.length != pathChunks.length) {
			return null;
		}

		return new RestActionImpl(restActionConfig, pathChunks);
	}

	public void registerRestAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		RestActionConfig restActionConfig = new RestActionConfig();

		restActionConfig.setActionClass(actionClass);
		restActionConfig.setActionMethod(actionMethod);
		restActionConfig.setPath(path);
		restActionConfig.setMethod(method);

		RestActionConfigSet restActionConfigSet = new RestActionConfigSet();

		restActionConfigSet.setPath(restActionConfig.getPath());

		int index = _restActionConfigSetsBinarySearch.find(restActionConfigSet);

		if (index < 0) {
			_restActionConfigSets.add(restActionConfigSet);
		}
		else {
			restActionConfigSet = _restActionConfigSets.get(index);
		}

		restActionConfigSet.addRestActionConfig(restActionConfig);
	}

	private int _getPathChunkIndex(
		String pathChunk, int depth, int pathMacroIndex, int low, int high) {

		for (int i = low; i <= high; i++) {
			RestActionConfigSet restActionConfigSet = _restActionConfigSets.get(
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
			RestActionConfigSet restActionConfigSet =
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
	private SortedArrayList<RestActionConfigSet> _restActionConfigSets;
	private BinarySearch<RestActionConfigSet> _restActionConfigSetsBinarySearch;

}