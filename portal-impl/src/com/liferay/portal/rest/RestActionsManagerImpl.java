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

import com.liferay.portal.kernel.rest.RestActionsManager;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.SortedArrayList;
import jodd.util.ReflectUtil;

import java.lang.reflect.Method;

public class RestActionsManagerImpl implements
	RestActionsManager<RestActionConfig> {

	public RestActionsManagerImpl() {
		_list = new SortedArrayList<RestActionConfigSet>();
		_listMatch = new ActionPathChunksBinarySearch();
		_listBS = BinarySearch.forList(_list);
	}

	public RestActionConfig lookup(String[] pathChunks, String method) {

		int low = 0;
		int high = _list.size() - 1;
		int macroNdx = 0;

		for (int deep = 0; deep < pathChunks.length; deep++) {
			String chunk = pathChunks[deep];
			_listMatch.deep = deep;

			int nextLow = _listMatch.findFirst(chunk, low, high);

			if (nextLow < 0) {
				int matched = _matchChunk(chunk, deep, macroNdx, low, high);
				if (matched == -1) {
					low = nextLow;
					break;
				}
				else {
					macroNdx++;
					low = matched;
					high = matched;
				}
			}
			else {
				low = nextLow;
				if (high > low) {
					high = _listMatch.findLast(chunk, low, high);
				}
			}
		}
		if (low < 0) {
			return null;
		}

		RestActionConfigSet set = _list.get(low);
		RestActionConfig cfg = set.lookup(method);

		if (cfg == null) {
			return null;
		}
		if (set.getActionPathChunks().length != pathChunks.length) {
			return null;
		}
		return cfg;
	}


	public Object[] prepareParameters(
		String[] pathChunks, RestActionConfig config) {

		String[] parameterNames = config.getParameterNames();

		Class[] parameterTypes = config.getParameterTypes();

		PathMacro[] macros = config.getActionPathMacros();

		Object[] parameters = new Object[parameterNames.length];

		for (PathMacro macro : macros) {
			String macroName = macro.getName();

			int macroIndex = macro.getIndex();

			if (macroIndex >= pathChunks.length) {
				continue;
			}

			String value = pathChunks[macroIndex];

			for (int i = 0; i < parameterNames.length; i++) {

				String parameterName = parameterNames[i];

				Class destinationType = parameterTypes[i];

				if (parameterName.equals(macroName)) {

					Object paramValue =
						ReflectUtil.castType(value, destinationType);

					parameters[i] = paramValue;
				}
			}
		}

		return parameters;
	}

	public void registerRestAction(
		Class actionClass, Method actionMethod, String path, String method) {

		RestActionConfig cfg =
			new RestActionConfig(actionClass, actionMethod, path, method);

		RestActionConfigSet set = new RestActionConfigSet(cfg.getActionPath());

		int ndx = _listBS.find(set);

		if (ndx < 0) {
			_list.add(set);
		}
		else {
			set = _list.get(ndx);
		}

		set.add(cfg);
	}

	private int _matchChunk(
		String chunk, int chunkNdx, int macroNdx, int low, int high) {

		for (int i = low; i <= high; i++) {
			RestActionConfigSet set = _list.get(i);

			if (macroNdx >= set.getActionPathMacros().length) {
				continue;
			}
			PathMacro macro = set.getActionPathMacros()[macroNdx];
			if (macro.getIndex() != chunkNdx) {
				continue;
			}

			if (chunk.startsWith(macro.getOffsetLeft()) == false) {
				continue;
			}

			if (chunk.endsWith(macro.getOffsetRight()) == false) {
				continue;
			}

			if (macro.getRegexPattern() != null) {
				String value = chunk.substring(
					macro.getOffsetLeft().length(),
					chunk.length() - macro.getOffsetRight().length());

				if (macro.getRegexPattern().matcher(value).matches() == false) {
					continue;
				}
			}

			return i;
		}
		return -1;
	}


	private class ActionPathChunksBinarySearch extends BinarySearch<String> {

		@Override
		protected int compare(int index, String element) {
			return get(index, deep).compareTo(element);
		}

		protected String get(int index, int deep) {
			return _list.get(index).getActionPathChunks()[deep];
		}

		@Override
		protected int getLastIndex() {
			return _list.size() - 1;
		}

		protected int deep;
	}

	private final SortedArrayList<RestActionConfigSet> _list;

	private final BinarySearch<RestActionConfigSet> _listBS;

	private final ActionPathChunksBinarySearch _listMatch;

}
