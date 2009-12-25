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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.FileReader;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <a href="TCKtoJUnitConverter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TCKtoJUnitConverter {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new TCKtoJUnitConverter(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public TCKtoJUnitConverter(String inputFile, String outputDir) {
		try {
			_convert(new File(inputFile), new File(outputDir));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _convert(File inputFile, File outputDir) throws Exception {
		UnsyncBufferedReader br =
			new UnsyncBufferedReader(new FileReader(inputFile));

		String s = StringPool.BLANK;

		while ((s = br.readLine()) != null) {
			if (s.startsWith("Test finished: ")) {
				int x = s.indexOf(StringPool.POUND);
				int y = s.lastIndexOf(StringPool.SLASH, x);

				String className = s.substring(15, y);

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				y = s.indexOf(StringPool.COLON, y);

				if (y == -1) {
					y = s.length();
				}

				className += StringPool.PERIOD + s.substring(x + 1, y);

				String message = s.substring(y + 2);

				_convert(className, message, outputDir);
			}
		}

		br.close();
	}

	private void _convert(String className, String message, File outputDir)
		throws Exception {

		boolean passed = false;

		if (message.startsWith("Passed.")) {
			passed = true;
		}

		String hostname = GetterUtil.getString(
			System.getProperty("env.USERDOMAIN")).toLowerCase();

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");

		sb.append("<testsuite errors=\"");

		if (passed) {
			sb.append("0");
		}
		else {
			sb.append("1");
		}

		sb.append("\" failures=\"");

		if (passed) {
			sb.append("1");
		}
		else {
			sb.append("0");
		}

		sb.append("\" hostname=\"");
		sb.append(hostname);
		sb.append("\" name=\"");
		sb.append(className);
		sb.append("\" tests=\"1\" time=\"0.0\" timestamp=\"");
		sb.append(System.currentTimeMillis());
		sb.append("\">\n");
		sb.append("\t<properties>\n");

		Properties properties = new SortedProperties(System.getProperties());

		Enumeration<String> keys =
			(Enumeration<String>)properties.propertyNames();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			String value = properties.getProperty(key);

			sb.append("\t\t<property name=\"");
			sb.append(HtmlUtil.escape(key));
			sb.append("\" value=\"");
			sb.append(HtmlUtil.escape(value));
			sb.append("\" />\n");
		}

		sb.append("\t</properties>\n");
		sb.append("\t<testcase classname=\"");
		sb.append(className);
		sb.append("\" name=\"test\" time=\"0.0\"");

		if (passed) {
			sb.append(" />\n");
		}
		else {
			String failureMessage = HtmlUtil.escape(message.substring(8));

			sb.append(">\n");
			sb.append("\t\t<failure message=\"");
			sb.append(failureMessage);
			sb.append("\" type=\"junit.framework.AssertionFailedError\">\n");
			sb.append(failureMessage);
			sb.append("\n\t\t</failure>\n");
			sb.append("\t</testcase>\n");
		}

		sb.append("\t<system-out><![CDATA[]]></system-out>\n");
		sb.append("\t<system-err><![CDATA[]]></system-err>\n");
		sb.append("</testsuite>");

		FileUtil.write(
			outputDir + "/TEST-" + className + ".xml", sb.toString());
	}

}