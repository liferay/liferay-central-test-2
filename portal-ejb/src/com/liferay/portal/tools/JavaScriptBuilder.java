/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.JSMin;
import com.liferay.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <a href="JavaScriptBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JavaScriptBuilder {

	public static void main(String[] args) {
		if (args.length == 3) {
			new JavaScriptBuilder(
				args[0], args[1], GetterUtil.getBoolean(args[2]));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public JavaScriptBuilder(String source, String destination,
							 boolean obfuscate) {

		try {
			StringBuffer sb = new StringBuffer();

			String[] sourceArray = StringUtil.split(source);

			for (int i = 0; i < sourceArray.length; i++) {
				sb.append(FileUtil.read("docroot/html/js/" + sourceArray[i]));
				sb.append("\n");
			}

			File tempFile = new File("JavaScriptBuilder.temp");

			FileUtil.write(tempFile, sb.toString());

			if (obfuscate) {
				JSMin jsMin = new JSMin(
					new FileInputStream(tempFile),
					new FileOutputStream(new File(destination)));

				jsMin.jsmin();
			}
			else {
				FileUtil.write(destination, sb.toString());
			}

			tempFile.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}