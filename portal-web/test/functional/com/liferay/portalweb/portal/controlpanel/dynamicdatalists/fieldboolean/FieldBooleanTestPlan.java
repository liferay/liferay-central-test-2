/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.addfieldboolean.AddFieldBooleanTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.deletefieldboolean.DeleteFieldBooleanTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleanfieldlabel.EditFieldBooleanFieldLabelTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleanname.EditFieldBooleanNameTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleanpredefinedvalue.EditFieldBooleanPredefinedValueTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleanshowlabel.EditFieldBooleanShowLabelTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleantip.EditFieldBooleanTipTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FieldBooleanTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFieldBooleanTests.suite());
		testSuite.addTest(DeleteFieldBooleanTests.suite());
		testSuite.addTest(EditFieldBooleanFieldLabelTests.suite());
		testSuite.addTest(EditFieldBooleanNameTests.suite());
		testSuite.addTest(EditFieldBooleanPredefinedValueTests.suite());
		testSuite.addTest(EditFieldBooleanShowLabelTests.suite());
		testSuite.addTest(EditFieldBooleanTipTests.suite());

		return testSuite;
	}

}