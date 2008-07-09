/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="SeleneseToJavaBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SeleneseToJavaBuilder {

	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			new SeleneseToJavaBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public SeleneseToJavaBuilder(String basedir) throws Exception {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setIncludes(new String[] {"**\\*.html"});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		for (int i = 0; i < files.length; i++) {

			// I would have preferred to use XlateHtmlSeleneseToJava, but it
			// is horribly out of sync with Selenium IDE and generates incorrect
			// code.

			/*File file = new File(basedir + "/" + files[i]);

			String input = StringUtil.replace(file.toString(), "\\", "/");

			XlateHtmlSeleneseToJava.main(
				new String[] {
					"test", "-silent", input
				}
			);*/

			translate(basedir, files[i]);
		}
	}

	protected String fixParam(String param) {
		StringBuilder sb = new StringBuilder();

		char[] array = param.toCharArray();

		for (int i = 0; i < array.length; ++i) {
			char c = array[i];

			if (c == CharPool.BACK_SLASH) {
				sb.append("\\\\");
			}
			else if (c == CharPool.QUOTE) {
				sb.append("\\\"");
			}
			else if (Character.isWhitespace(c)) {
				sb.append(c);
			}
			else if ((c < 0x0020) || (c > 0x007e)) {
				sb.append("\\u");
				sb.append(UnicodeFormatter.charToHex(c));
			}
			else {
				sb.append(c);
			}
		}

		return StringUtil.replace(
			sb.toString(), _FIX_PARAM_OLD_SUBS, _FIX_PARAM_NEW_SUBS);
	}

	protected String[] getParams(String step) throws Exception {
		String[] params = new String[3];

		int x = 0;
		int y = 0;

		for (int i = 0; i < 3; i++) {
			x = step.indexOf("<td>", x) + 4;
			y = step.indexOf("\n", x);
			y = step.lastIndexOf("</td>", y);

			params[i] =	step.substring(x, y);
		}

		return params;
	}

	protected void translate(String basedir, String file) throws Exception {
		file = StringUtil.replace(
			file, StringPool.BACK_SLASH, StringPool.SLASH);

		int x = file.lastIndexOf(StringPool.SLASH);
		int y = file.indexOf(StringPool.PERIOD);

		String testPackagePath = StringUtil.replace(
			file.substring(0, x), StringPool.SLASH, StringPool.PERIOD);
		String testName = file.substring(x + 1, y);
		String testMethodName =
			"test" + testName.substring(0, testName.length() - 4);
		String testFileName = basedir + "/" + file.substring(0, y) + ".java";

		StringBuilder sb = new StringBuilder();

		sb.append("package " + testPackagePath + ";\n\n");

		sb.append("import com.liferay.portal.kernel.util.StringPool;\n");
		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n\n");

		sb.append("public class " + testName + " extends BaseTestCase {");

		sb.append("public void " + testMethodName + "() throws Exception {");

		String xml = _fileUtil.read(basedir + "/" + file);

		if ((xml.indexOf("<title>" + testName + "</title>") == -1) ||
			(xml.indexOf("colspan=\"3\">" + testName + "</td>") == -1)) {

			System.out.println(testName + " has an invalid test name");
		}

		x = xml.indexOf("<tbody>");
		y = xml.indexOf("</tbody>");

		xml = xml.substring(x, y + 8);

		x = 0;
		y = 0;

		while (true) {
			x = xml.indexOf("<tr>", x);
			y = xml.indexOf("\n</tr>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			x += 6;
			y++;

			String step = xml.substring(x, y);

			String[] params = getParams(step);

			String param1 = params[0];
			String param2 = fixParam(params[1]);
			String param3 = fixParam(params[2]);

			if (param1.equals("assertConfirmation")) {
				param2 = StringUtil.replace(param2, "?", "[\\\\s\\\\S]");

				sb.append("assertTrue(selenium.getConfirmation().matches(\"^");
				sb.append(param2);
				sb.append("$\"));");
			}
			else if (param1.equals("assertElementPresent") ||
					 param1.equals("assertElementNotPresent")) {

				if (param1.equals("assertElementPresent")) {
					sb.append("assertTrue");
				}
				else if (param1.equals("assertElementNotPresent")) {
					sb.append("assertFalse");
				}

				sb.append("(selenium.isElementPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("click") || param1.equals("mouseDown") ||
					 param1.equals("mouseUp") || param1.equals("open") ||
					 param1.equals("selectFrame") ||
					 param1.equals("selectWindow")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append("(\"");
				sb.append(param2);
				sb.append("\");");
			}
			else if (param1.equals("clickAndWait")) {
				sb.append("selenium.click(\"");
				sb.append(param2);
				sb.append("\");");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
			}
			else if (param1.equals("close")) {
				sb.append("selenium.");
				sb.append(param1);
				sb.append("();");
			}
			else if (param1.equals("pause")) {
				sb.append("Thread.sleep(");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("addSelection") || param1.equals("select") ||
					 param1.equals("type") || param1.equals("typeKeys") ||
					 param1.equals("waitForPopUp")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append("(\"");
				sb.append(param2);
				sb.append("\", \"");
				sb.append(param3);
				sb.append("\");");
			}
			else if (param1.equals("selectAndWait")) {
				sb.append("selenium.select(\"");
				sb.append(param2);
				sb.append("\", \"");
				sb.append(param3);
				sb.append("\");");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
			}
			else if (param1.equals("storeText")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getText(\"");
				sb.append(param2);
				sb.append("\");");
			}
			else if (param1.equals("verifyElementPresent") ||
					 param1.equals("verifyElementNotPresent")) {

				if (param1.equals("verifyElementPresent")) {
					sb.append("verifyTrue");
				}
				else if (param1.equals("verifyElementNotPresent")) {
					sb.append("verifyFalse");
				}

				sb.append("(selenium.isElementPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("verifyTextPresent") ||
					 param1.equals("verifyTextNotPresent")) {

				if (param1.equals("verifyTextPresent")) {
					sb.append("verifyTrue");
				}
				else if (param1.equals("verifyTextNotPresent")) {
					sb.append("verifyFalse");
				}

				sb.append("(selenium.isTextPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("verifyTitle")) {
				sb.append("verifyEquals(\"");
				sb.append(param2);
				sb.append("\", selenium.getTitle());");
			}
			else if (param1.equals("waitForElementNotPresent") ||
					 param1.equals("waitForElementPresent") ||
					 param1.equals("waitForTextNotPresent") ||
					 param1.equals("waitForTextPresent")) {

				sb.append("for (int second = 0;; second++) {");
				sb.append("if (second >= 60) {");
				sb.append("fail(\"timeout\");");
				sb.append("}");

				sb.append("try {");
				sb.append("if (");

				if (param1.equals("waitForElementNotPresent") ||
					param1.equals("waitForTextNotPresent")) {

					sb.append("!");
				}

				sb.append("selenium.");

				if (param1.equals("waitForElementNotPresent") ||
					param1.equals("waitForElementPresent")) {

					sb.append("isElementPresent");
				}
				else if (param1.equals("waitForTextNotPresent") ||
						 param1.equals("waitForTextPresent")) {

					sb.append("isTextPresent");
				}

				sb.append("(\"");
				sb.append(param2);
				sb.append("\")) {");
				sb.append("break;");
				sb.append("}");
				sb.append("}");
				sb.append("catch (Exception e) {");
				sb.append("}");

				sb.append("Thread.sleep(1000);");
				sb.append("}");
			}
			else if (param1.equals("waitForTable")) {
				sb.append("for (int second = 0;; second++) {");
				sb.append("if (second >= 60) {");
				sb.append("fail(\"timeout\");");
				sb.append("}");

				sb.append("try {");
				sb.append("if (StringPool.BLANK.equals(selenium.getTable(\"");
				sb.append(param2);
				sb.append("\"))) {");
				sb.append("break;");
				sb.append("}");
				sb.append("}");
				sb.append("catch (Exception e) {");
				sb.append("}");

				sb.append("Thread.sleep(1000);");
				sb.append("}");
			}
			else {
				System.out.println(param1 + " was not translated");
			}
		}

		sb.append("}");
		sb.append("}");

		String content = sb.toString();

		ServiceBuilder.writeFile(new File(testFileName), content);
	}

	private static final String[] _FIX_PARAM_OLD_SUBS = new String[] {
		"\\\\n", "<br />"
	};

	private static final String[] _FIX_PARAM_NEW_SUBS = new String[] {
		"\\n", "\\n"
	};

	private static FileImpl _fileUtil = new FileImpl();

}