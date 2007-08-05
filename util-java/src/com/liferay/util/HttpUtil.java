/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Iterator;
import java.util.Map;

/**
 * <a href="HttpUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class HttpUtil {

	public static String parameterMapToString(Map parameterMap) {
		return parameterMapToString(parameterMap, true);
	}

	public static String parameterMapToString(
		Map parameterMap, boolean addQuestion) {

		StringMaker sm = new StringMaker();

		if (parameterMap.size() > 0) {
			if (addQuestion) {
				sm.append(StringPool.QUESTION);
			}

			Iterator itr = parameterMap.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String name = (String)entry.getKey();
				Object value = entry.getValue();

				String[] values = null;

				if (value instanceof String[]) {
					values = (String[])entry.getValue();
				}
				else if (value instanceof String) {
				    values = new String[] {(String)value};
				}
				else {
					throw new IllegalArgumentException(
						"Values of type " + value.getClass() + " are not " +
							"supported");
				}

				for (int i = 0; i < values.length; i++) {
					sm.append(name);
					sm.append(StringPool.EQUAL);
					sm.append(Http.encodeURL(values[i]));
					sm.append(StringPool.AMPERSAND);
				}
			}

			sm.deleteCharAt(sm.length() - 1);
		}

		return sm.toString();
	}

}