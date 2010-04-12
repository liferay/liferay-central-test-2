/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.taglib.aui;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="ScriptData.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ScriptData {

	public void append(String content, String use) {
		if (Validator.isNull(use)) {
			_rawSB.append(content);
		}
		else {
			_callbackSB.append("(function() {");
			_callbackSB.append(content);
			_callbackSB.append("})();");

			String[] useArray = StringUtil.split(use);

			for (int i = 0; i < useArray.length; i++) {
				_useSet.add(useArray[i]);
			}
		}
	}

	public StringBundler getCallbackSB() {
		return _callbackSB;
	}

	public StringBundler getRawSB() {
		return _rawSB;
	}

	public Set<String> getUseSet() {
		return _useSet;
	}

	private StringBundler _callbackSB = new StringBundler();
	private StringBundler _rawSB = new StringBundler();
	private Set<String> _useSet = new TreeSet<String>();

}