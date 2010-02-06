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

package com.liferay.portalweb.portlet.assetpublisher.igimage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.addigimage.AddIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.removeigimage.RemoveIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.selectigimage.SelectIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewcountigimage.ViewCountIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimageabstracts.ViewIGImageAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimagedynamicassettypeigimage.ViewIGImageDynamicAssetTypeIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimagefullcontent.ViewIGImageFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimagetable.ViewIGImageTableTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigimagetitlelist.ViewIGImageTitleListTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="IGImageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IGImageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddIGImageTests.suite());
		testSuite.addTest(RemoveIGImageTests.suite());
		testSuite.addTest(SelectIGImageTests.suite());
		testSuite.addTest(ViewCountIGImageTests.suite());
		testSuite.addTest(ViewIGImageAbstractsTests.suite());
		testSuite.addTest(
			ViewIGImageDynamicAssetTypeIGImageTests.suite());
		testSuite.addTest(ViewIGImageFullContentTests.suite());
		testSuite.addTest(ViewIGImageTableTests.suite());
		testSuite.addTest(ViewIGImageTitleListTests.suite());

		return testSuite;
	}

}