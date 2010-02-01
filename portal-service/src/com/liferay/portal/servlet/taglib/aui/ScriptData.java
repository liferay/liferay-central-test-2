/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
			_callbackSB.append("(function() {" + content + "})();");

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