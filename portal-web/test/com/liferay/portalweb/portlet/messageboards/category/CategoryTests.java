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

package com.liferay.portalweb.portlet.messageboards.category;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategory.AddCategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategorymultiple.AddCategoryMultipleTests;
import com.liferay.portalweb.portlet.messageboards.category.addcategorynamenull.AddCategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategory.AddSubcategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategorymultiple.AddSubcategoryMultipleTests;
import com.liferay.portalweb.portlet.messageboards.category.addsubcategorynamenull.AddSubcategoryNameNullTests;
import com.liferay.portalweb.portlet.messageboards.category.deletecategory.DeleteCategoryTests;
import com.liferay.portalweb.portlet.messageboards.category.editcategory.EditCategoryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="CategoryTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CategoryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryTests.suite());
		testSuite.addTest(AddCategoryMultipleTests.suite());
		testSuite.addTest(AddCategoryNameNullTests.suite());
		testSuite.addTest(AddSubcategoryTests.suite());
		testSuite.addTest(AddSubcategoryMultipleTests.suite());
		testSuite.addTest(AddSubcategoryNameNullTests.suite());
		testSuite.addTest(DeleteCategoryTests.suite());
		testSuite.addTest(EditCategoryTests.suite());

		return testSuite;
	}

}