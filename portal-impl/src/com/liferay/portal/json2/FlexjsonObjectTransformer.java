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

package com.liferay.portal.json2;

import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.json2.JSONBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import flexjson.PathExpression;

import flexjson.transformer.ObjectTransformer;

import java.util.List;

import jodd.bean.BeanUtil;

/**
 * @author Igor Spasic
 */
public class FlexjsonObjectTransformer extends ObjectTransformer implements
	JSONTransformer {

	@Override
	public void transform(Object object) {

		Class type = resolveClass(object);

		JSONBuilder jsonBuilder = _JSON_BUILDER_MANAGER.lookupBuilder(type);

		if (jsonBuilder != null) {
			getContext().write(jsonBuilder.toJSONString(object));
			return;
		}

		_pathExpressions = (List<PathExpression>)
			BeanUtil.getDeclaredProperty(getContext(), "pathExpressions");

		String path = _getPath();

		String[] includes = _JSON_INCLUDES_MANAGER.lookupIncludes(type);
		_include(path, includes);

		String[] excludes = _JSON_INCLUDES_MANAGER.lookupExcludes(type);
		_exclude(path, excludes);

		super.transform(object);
	}

	private void _exclude(String path, String... names) {
		for (String name : names) {
			_pathExpressions.add(new PathExpression(path + name, false));
		}
	}

	private String _getPath() {

		List<String> pathList = getContext().getPath().getPath();

		int pathSize = pathList.size();

		if (pathSize == 0) {
			return StringPool.BLANK;
		}

		StringBundler path = new StringBundler(pathSize * 2);

		for (String pathElement : pathList) {
			path.append(pathElement);
			path.append('.');
		}

		return path.toString();
	}

	private void _include(String path, String... names) {
		for (String name : names) {
			_pathExpressions.add(0, new PathExpression(path + name, true));
		}
	}

	private static final JSONBuilderManager
		_JSON_BUILDER_MANAGER = new JSONBuilderManager();

	private static final JSONIncludesManager
		_JSON_INCLUDES_MANAGER = new JSONIncludesManager();

	private List<PathExpression> _pathExpressions;

}