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
		selenium.clickAt("//img[@alt='??????? (????????)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("??? ????"));
		selenium.clickAt("//img[@alt='catal\u00e0 (Andorra)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegir p\u00e0gina"));
		selenium.clickAt("//img[@alt='catal\u00e0 (Espanya)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegir p\u00e0gina"));
		selenium.clickAt("//img[@alt='?? (??)']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("????"));
		selenium.clickAt("//img[@alt='?? (??)']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("????"));
		selenium.clickAt("//img[@alt='ce\u0161tina (Cesk\u00e1 republika)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pridej str\u00e1nku"));
		selenium.clickAt("//img[@alt='Nederlands (Nederland)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Pagina toevoegen"));
		selenium.clickAt("//img[@alt='suomi (Suomi)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Lis\u00e4\u00e4 sivu"));
		selenium.clickAt("//img[@alt='fran\u00e7ais (France)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ajouter une page"));
		selenium.clickAt("//img[@alt='Deutsch (Deutschland)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Seite hinzuf\u00fcgen"));
		selenium.clickAt("//img[@alt='e??????? (????da)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("???s???? Se??da?"));
		selenium.clickAt("//img[@alt='magyar (Magyarorsz\u00e1g)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add Page"));
		selenium.clickAt("//img[@alt='italiano (Italia)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Aggiungi Pagina"));
		selenium.clickAt("//img[@alt='??? (??)']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("?????????"));
		selenium.clickAt("//img[@alt='Persian (Iran)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("????? ???? ????"));
		selenium.clickAt("//img[@alt='Ti?ng Vi?t (Vi?t Nam)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Th\u00eam trang"));
		selenium.clickAt("//img[@alt='T\u00fcrk\u00e7e (T\u00fcrkiye)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Sayfa Ekle"));
		selenium.clickAt("//img[@alt='svenska (Sverige)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("L\u00e4gg till sida"));
		selenium.clickAt("//img[@alt='espa\u00f1ol (Espa\u00f1a)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("A\u00f1adir p\u00e1gina"));
		selenium.clickAt("//img[@alt='??????? (??????)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("???????? ????????"));
		selenium.clickAt("//img[@alt='portugu\u00eas (Brasil)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Adicione A P\u00e1gina"));
		selenium.clickAt("//img[@alt='English (United States)']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add Page"));
	}
}