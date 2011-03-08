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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Igor Spasic
 */
public class RestActionConfigSet implements Comparable<RestActionConfigSet> {

	public boolean addRestActionConfig(RestActionConfig restActionConfig) {
		if (!_path.equals(restActionConfig.getPath())) {
			return false;
		}

		restActionConfig.setRestActionConfigSet(this);

		int index = _getRestActionConfigIndex(restActionConfig.getMethod());

		if (index == -1) {
			if (restActionConfig.getMethod() == null) {
				_restActionConfigs.add(restActionConfig);
			}
			else {
				_restActionConfigs.add(0, restActionConfig);
			}

			return false;
		}
		else {
			_restActionConfigs.set(index, restActionConfig);

			return true;
		}
	}

	public int compareTo(RestActionConfigSet restActionConfigSet) {
		return _path.compareTo(restActionConfigSet._path);
	}

	public String getPath() {
		return _path;
	}

	public String[] getPathChunks() {
		return _pathChunks;
	}

	public PathMacro[] getPathMacros() {
		return _pathMacros;
	}

	public RestActionConfig getRestActionConfig(String method) {
		int index = _getRestActionConfigIndex(method);

		if (index == -1) {
			return null;
		}

		return _restActionConfigs.get(index);
	}

	public void setPath(String path) {
		_path = path;

		_pathChunks = StringUtil.split(path.substring(1), StringPool.SLASH);

		_pathMacros = _resolvePathMacros(_pathChunks);
	}

	private int[] _getPathChunkIndexes(
		String pathChunk, String leftBoundary, String rightBoundary) {

		int index = pathChunk.indexOf(leftBoundary, 0);

		if (index == -1) {
			return null;
		}

		int[] indexes = new int[4];

		indexes[0] = index;

		index += leftBoundary.length();

		indexes[1] = index;

		index = pathChunk.indexOf(rightBoundary, index);

		if (index == -1) {
			return null;
		}

		indexes[2] = index;
		indexes[3] = index + rightBoundary.length();

		return indexes;
	}

	private int _getRestActionConfigIndex(String method) {
		for (int i = 0; i < _restActionConfigs.size(); i++) {
			RestActionConfig restActionConfig = _restActionConfigs.get(i);

			String restActionConfigMethod = restActionConfig.getMethod();

			if ((restActionConfigMethod == null) ||
				restActionConfigMethod.equals(method)) {

				return i;
			}
		}

		return -1;
	}

	private PathMacro[] _resolvePathMacros(String[] pathChunks) {
		List<PathMacro> pathMacros = new ArrayList<PathMacro>(
			pathChunks.length);

		for (int i = 0; i < pathChunks.length; i++) {
			String pathChunk = pathChunks[i];

			int[] indexes = _getPathChunkIndexes(
				pathChunk, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE);

			if (indexes == null) {
				continue;
			}

			PathMacro pathMacro = new PathMacro();

			pathMacro.setIndex(i);

			String name = pathChunk.substring(indexes[1], indexes[2]);

			int index = name.indexOf(CharPool.COLON);

			if (index != -1) {
				String patternString = name.substring(index + 1);

				Pattern pattern = Pattern.compile(patternString);

				pathMacro.setPattern(pattern);

				name = name.substring(0, index);
			}

			pathMacro.setName(name);

			String offsetLeft = StringPool.BLANK;

			if (indexes[0] != 0) {
				offsetLeft = pathChunk.substring(0, indexes[0]);
			}

			pathMacro.setOffsetLeft(offsetLeft);

			String offsetRight = StringPool.BLANK;

			if (indexes[3] == pathChunk.length()) {
				offsetRight = pathChunk.substring(indexes[3]);
			}

			pathMacro.setOffsetRight(offsetRight);

			pathMacros.add(pathMacro);
		}

		if (pathMacros.isEmpty()) {
			return null;
		}

		return pathMacros.toArray(new PathMacro[pathMacros.size()]);
	}

	private String _path;
	private String[] _pathChunks;
	private PathMacro[] _pathMacros;
	private List<RestActionConfig> _restActionConfigs =
		new ArrayList<RestActionConfig>(10);

}