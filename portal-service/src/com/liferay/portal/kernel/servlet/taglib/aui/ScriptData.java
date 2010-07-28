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

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class ScriptData {

	public void append(String content, String use) {
		ObjectValuePair<Object, String> append =
			new ObjectValuePair<Object, String>(content, use);

		_appendList.add(append);

		_calculated = false;
	}

	public void append(StringBundler contentSB, String use) {
		ObjectValuePair<Object, String> append =
			new ObjectValuePair<Object, String>(contentSB, use);

		_appendList.add(append);

		_calculated = false;
	}

	public StringBundler getCallbackSB() {
		calculate();

		return _callbackSB;
	}

	public StringBundler getRawSB() {
		calculate();

		return _rawSB;
	}

	public Set<String> getUseSet() {
		calculate();

		return _useSet;
	}

	public void removeLastEntry() {
		if (!_appendList.isEmpty()) {
			_appendList.remove(_appendList.size() - 1);
		}
	}

	protected void calculate() {
		if (_calculated) {
			return;
		}

		_callbackSB = new StringBundler();
		_rawSB = new StringBundler();
		_useSet = new TreeSet<String>();

		for (ObjectValuePair<Object, String> append : _appendList) {
			Object key = append.getKey();
			String use = append.getValue();

			if (key instanceof StringBundler) {
				StringBundler contentSB = (StringBundler)key;

				if (Validator.isNull(use)) {
					_rawSB.append(contentSB);
				}
				else {
					_callbackSB.append("(function() {");
					_callbackSB.append(contentSB);
					_callbackSB.append("})();");

					String[] useArray = StringUtil.split(use);

					for (int i = 0; i < useArray.length; i++) {
						_useSet.add(useArray[i]);
					}
				}
			}
			else {
				String content = (String)key;

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
		}

		_calculated = true;
	}

	private List<ObjectValuePair<Object, String>> _appendList =
		new ArrayList<ObjectValuePair<Object, String>>();
	private boolean _calculated;
	private StringBundler _callbackSB;
	private StringBundler _rawSB;
	private Set<String> _useSet;

}