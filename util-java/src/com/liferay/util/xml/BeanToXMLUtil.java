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

package com.liferay.util.xml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.util.TextFormatter;

import java.lang.reflect.Method;

import java.util.List;

/**
 * <a href="BeanToXMLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 */
public class BeanToXMLUtil {

	public static void addBean(Object obj, Element parentEl) {
		String classNameWithoutPackage = getClassNameWithoutPackage(
			obj.getClass().getName());

		Element el = parentEl.addElement(classNameWithoutPackage);

		addFields(obj, el);
	}

	public static void addBean(Object obj, org.dom4j.Element parentEl) {
		String classNameWithoutPackage = getClassNameWithoutPackage(
			obj.getClass().getName());

		org.dom4j.Element el = parentEl.addElement(classNameWithoutPackage);

		addFields(obj, el);
	}

	public static void addFields(Object obj, Element parentEl) {
		Method[] methods = obj.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];

			if (method.getName().startsWith("get") &&
				!method.getName().equals("getClass")) {

				String memberName = StringUtil.replace(
					method.getName(), "get", StringPool.BLANK);

				memberName = TextFormatter.format(memberName, TextFormatter.I);
				memberName = TextFormatter.format(memberName, TextFormatter.K);

				try {
					Object returnValue = method.invoke(obj, new Object[] {});

					if (returnValue instanceof List<?>) {
						List<Object> list = (List<Object>)returnValue;

						Element listEl = parentEl.addElement(memberName);

						for (int j = 0; j < list.size(); j++) {
							addBean(list.get(j), listEl);
						}
					}
					else {
						DocUtil.add(
							parentEl, memberName, returnValue.toString());
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage());
					}
				}
			}
		}
	}

	/**
	 * @deprecated
	 */
	public static void addFields(Object obj, org.dom4j.Element parentEl) {
		Method[] methods = obj.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];

			if (method.getName().startsWith("get") &&
				!method.getName().equals("getClass")) {

				String memberName = StringUtil.replace(
					method.getName(), "get", StringPool.BLANK);

				memberName = TextFormatter.format(memberName, TextFormatter.I);
				memberName = TextFormatter.format(memberName, TextFormatter.K);

				try {
					Object returnValue = method.invoke(obj, new Object[] {});

					if (returnValue instanceof List<?>) {
						List<Object> list = (List<Object>)returnValue;

						org.dom4j.Element listEl = parentEl.addElement(
							memberName);

						for (int j = 0; j < list.size(); j++) {
							addBean(list.get(j), listEl);
						}
					}
					else {
						DocUtil.add(
							parentEl, memberName, returnValue.toString());
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage());
					}
				}
			}
		}
	}

	public static String getClassNameWithoutPackage(String className) {
		String[] classNameArray = StringUtil.split(
			className, StringPool.PERIOD);

		String classNameWithoutPackage =
			classNameArray[classNameArray.length - 1];

		classNameWithoutPackage = TextFormatter.format(
			classNameWithoutPackage, TextFormatter.I);
		classNameWithoutPackage = TextFormatter.format(
			classNameWithoutPackage, TextFormatter.K);

		return classNameWithoutPackage;
	}

	private static Log _log = LogFactoryUtil.getLog(BeanToXMLUtil.class);

}