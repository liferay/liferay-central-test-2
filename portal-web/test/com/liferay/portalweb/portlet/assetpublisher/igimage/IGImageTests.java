/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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