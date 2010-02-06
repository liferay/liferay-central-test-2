/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.lang.reflect.Method;

import java.util.Comparator;

/**
 * <a href="MethodComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class MethodComparator implements Comparator<Method> {

	public int compare(Method method1, Method method2) {
		String name1 = method1.getName();
		String name2 = method2.getName();

		int value = name1.compareTo(name2);

		if (value != 0){
			return value;
		}

		Class<?>[] parameterTypes1 = method1.getParameterTypes();
		Class<?>[] parameterTypes2 = method2.getParameterTypes();

		int index = 0;

		while ((index < parameterTypes1.length) &&
			   (index < parameterTypes2.length)) {

			Class<?> parameterType1 = parameterTypes1[index];
			Class<?> parameterType2 = parameterTypes2[index];

			String parameterTypeName1 = parameterType1.getName();
			String parameterTypeName2 = parameterType2.getName();

			value = parameterTypeName1.compareTo(parameterTypeName2);

			if (value != 0) {
				return value;
			}

			index++;
		}

		if (index < (parameterTypes1.length -1)) {
			return -1;
		}
		else {
			return 1;
		}
	}

}