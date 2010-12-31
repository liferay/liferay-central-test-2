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

package com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment;

import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.addtaskwebcontentcommentassignedtome.AddTaskWebContentCommentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.addtaskwebcontentcommentassignedtomyroles.AddTaskWebContentCommentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.addtaskwebcontentcommentcompleted.AddTaskWebContentCommentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.assertnoworkflowaddtaskcommentassignedtome.AssertNoWorkflowAddTaskCommentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.assertnoworkflowaddtaskcommentassigntomyroles.AssertNoWorkflowAddTaskCommentAssignToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.assertnoworkflowaddtaskcommentcompleted.AssertNoWorkflowAddTaskCommentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.viewtaskwebcontentcommentassignedtome.ViewTaskWebContentCommentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.viewtaskwebcontentcommentassignedtomyroles.ViewTaskWebContentCommentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.workflow.workflowtaskcomment.viewtaskwebcontentcommentcompleted.ViewTaskWebContentCommentCompletedTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskCommentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddTaskWebContentCommentAssignedToMeTests.suite());
		testSuite.addTest(
			AddTaskWebContentCommentAssignedToMyRolesTests.suite());
		testSuite.addTest(AddTaskWebContentCommentCompletedTests.suite());
		testSuite.addTest(
			AssertNoWorkflowAddTaskCommentAssignedToMeTests.suite());
		testSuite.addTest(
			AssertNoWorkflowAddTaskCommentAssignToMyRolesTests.suite());
		testSuite.addTest(AssertNoWorkflowAddTaskCommentCompletedTests.suite());
		testSuite.addTest(ViewTaskWebContentCommentAssignedToMeTests.suite());
		testSuite.addTest(
			ViewTaskWebContentCommentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewTaskWebContentCommentCompletedTests.suite());

		return testSuite;
	}

}