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

package com.liferay.portalweb.portlet.language;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="LanguageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LanguageTest extends BaseTestCase {
	public void testLanguage() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u0627\u0644\u0639\u0631\u0628\u064a\u0629 (\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0623\u0636\u0641 \u0635\u0641\u062d\u0629"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='catal\u00e0 (Andorra)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegir p\u00e0gina"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='catal\u00e0 (Espanya)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegir p\u00e0gina"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u4e2d\u6587 (\u4e2d\u56fd)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u589e\u52a0\u9875\u9762"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u4e2d\u6587 (\u53f0\u7063)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u589e\u52a0\u9801\u9762"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u010de\u0161tina (\u010cesk\u00e1 republika)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("P\u0159idej str\u00e1nku"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='Nederlands (Nederland)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pagina toevoegen"));
		selenium.click(RuntimeVariables.replace("//img[@alt='suomi (Suomi)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Lis\u00e4\u00e4 sivu"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='fran\u00e7ais (France)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ajouter une page"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='Deutsch (Deutschland)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Seite hinzuf\u00fcgen"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u03b5\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac (\u0395\u03bb\u03bb\u03ac\u03b4\u03b1)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u03a0\u03c1\u03bf\u03c3\u03b8\u03ae\u03ba\u03b7 \u03a3\u03b5\u03bb\u03af\u03b4\u03b1\u03c2"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='magyar (Magyarorsz\u00e1g)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add Page"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='italiano (Italia)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Aggiungi Pagina"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u65e5\u672c\u8a9e (\u65e5\u672c)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u30da\u30fc\u30b8\u3092\u52a0\u3048\u306a\u3055\u3044"));
		selenium.click(RuntimeVariables.replace("//img[@alt='Persian (Iran)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0627\u0636\u0627\u0641\u0647 \u06a9\u0631\u062f\u0646 \u062c\u062f\u06cc\u062f"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='Ti\u1ebfng Vi\u1ec7t (Vi\u1ec7t Nam)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Th\u00eam trang"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='T\u00fcrk\u00e7e (T\u00fcrkiye)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Sayfa Ekle"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='svenska (Sverige)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("L\u00e4gg till sida"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='espa\u00f1ol (Espa\u00f1a)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("A\u00f1adir p\u00e1gina"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='\u0440\u0443\u0441\u0441\u043a\u0438\u0439 (\u0420\u043e\u0441\u0441\u0438\u044f)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0443"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='portugu\u00eas (Brasil)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Adicione A P\u00e1gina"));
		selenium.click(RuntimeVariables.replace(
				"//img[@alt='English (United States)']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add Page"));
	}
}