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

package com.liferay.portalweb.plugins.kaleo;

import com.liferay.portalweb.plugins.kaleo.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.plugins.kaleo.mysubmissions.MySubmissionsTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.MyWorkflowTasksTests;
import com.liferay.portalweb.plugins.kaleo.scope.ScopeTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.WebContentTests;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.WebContentDisplayTests;
import com.liferay.portalweb.plugins.kaleo.workflow.WorkflowTests;
import com.liferay.portalweb.plugins.kaleo.workflowconfiguration.WorkflowConfigurationTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AssetPublisherTests.suite());
		testSuite.addTest(MySubmissionsTests.suite());
		testSuite.addTest(MyWorkflowTasksTests.suite());
		testSuite.addTest(ScopeTests.suite());
		testSuite.addTest(WebContentTests.suite());
		testSuite.addTest(WebContentDisplayTests.suite());
		testSuite.addTest(WorkflowTests.suite());
		testSuite.addTest(WorkflowConfigurationTests.suite());

		return testSuite;
	}

}