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

import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RestActionConfigSet implements Comparable<RestActionConfigSet> {

	public RestActionConfigSet(String actionPath) {
		this._actionPath = actionPath;

		this._actionPathChunks = RestUtil.splitPath(actionPath);

		this._actionPathMacros = _resolveMacros(_actionPathChunks);
	}


	public boolean add(RestActionConfig cfg) {
		if (cfg.getActionPath().equals(this._actionPath) == false) {
			return false;	//todo throw an exception on misconfiguration
		}

		cfg.setActionConfigSet(this);

		int ndx = lookupIndex(cfg.getMethod());
		if (ndx == -1) {
			if (cfg.getMethod() == null) {
				_configs.add(cfg);
			}
			else {
				_configs.add(0, cfg);
			}
			return false;
		}
		else {
			_configs.set(ndx, cfg);
			return true;
		}
	}

	public int compareTo(RestActionConfigSet set) {
		return this._actionPath.compareTo(set._actionPath);
	}

	public String getActionPath() {
		return _actionPath;
	}

	public String[] getActionPathChunks() {
		return _actionPathChunks;
	}

	public PathMacro[] getActionPathMacros() {
		return _actionPathMacros;
	}

	public RestActionConfig lookup(String method) {
		int ndx = lookupIndex(method);
		if (ndx == -1) {
			return null;
		}
		return _configs.get(ndx);
	}

	protected int lookupIndex(String method) {

		for (int i = 0; i < _configs.size(); i++) {
			RestActionConfig config = _configs.get(i);

			if (config.getMethod() == null) {
				return i;
			}
			if (config.getMethod().equals(method)) {
				return i;
			}
		}
		return -1;
	}

	private static int[] _indexOfRegion(
		String string, String leftBoundary, String rightBoundary, int offset) {

		int ndx = offset;
		int[] res = new int[4];

		ndx = string.indexOf(leftBoundary, ndx);
		if (ndx == -1) {
			return null;
		}

		res[0] = ndx;
		ndx += leftBoundary.length();
		res[1] = ndx;

		ndx = string.indexOf(rightBoundary, ndx);
		if (ndx == -1) {
			return null;
		}

		res[2] = ndx;
		res[3] = ndx + rightBoundary.length();
		return res;
	}

	private PathMacro[] _resolveMacros(String[] chunks) {
		List<PathMacro> list = new ArrayList<PathMacro>(chunks.length);

		for (int i = 0; i < chunks.length; i++) {
			String chunk = chunks[i];

			int[] ndx = _indexOfRegion(
				chunk,
				StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE,
				0);

			if (ndx != null) {
				PathMacro macro = new PathMacro();

				String name = chunk.substring(ndx[1], ndx[2]);

				int colonNdx = name.indexOf(':');
				if (colonNdx != -1) {
					String pattern = name.substring(colonNdx + 1);
					macro.setRegexPattern(Pattern.compile(pattern));
					name = name.substring(0, colonNdx);
				}

				macro.setName(name);
				macro.setIndex(i);

				macro.setOffsetLeft(
					ndx[0] == 0 ?
						StringPool.BLANK : chunk.substring(0, ndx[0]));

				macro.setRight(ndx[3] == chunk.length() ?
					StringPool.BLANK : chunk.substring(ndx[3]));

				list.add(macro);
			}
		}

		if (list.isEmpty()) {
			return null;
		}

		return list.toArray(new PathMacro[list.size()]);
	}

	private List<RestActionConfig> _configs =
		new ArrayList<RestActionConfig>(10);

	private final String _actionPath;

	private final String[] _actionPathChunks;

	private final PathMacro[] _actionPathMacros;

}
