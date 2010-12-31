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

package com.liferay.portalweb.plugins.kaleo.webcontent;

import com.liferay.portalweb.plugins.kaleo.webcontent.addwebcontentnoworkflow.AddWebContentNoWorkflowTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.addwebcontentsingleapprover.AddWebContentSingleApproverTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.addwebcontentsingleapproverdraft.AddWebContentSingleApproverDraftTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.approvewebcontentactions.ApproveWebContentActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.approvewebcontentresubmitactions.ApproveWebContentResubmitActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.approvewebcontentresubmitrmenu.ApproveWebContentResubmitRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.approvewebcontentrmenu.ApproveWebContentRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentactions.AssignToMeWebContentActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentdetails.AssignToMeWebContentDetailsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentresubmitactions.AssignToMeWebContentResubmitActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentresubmitdetails.AssignToMeWebContentResubmitDetailsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentresubmitrmenu.AssignToMeWebContentResubmitRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.assigntomewebcontentrmenu.AssignToMeWebContentRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.deletewebcontentassignedtomeactions.DeleteWebContentAssignedToMeActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.deletewebcontentassignedtomyrolesactions.DeleteWebContentAssignedToMyRolesActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.deletewebcontentcompletedactions.DeleteWebContentCompletedActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.deletewebcontentcompletededitedlist.DeleteWebContentCompletedEditedListTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.editwebcontentassignedtomeactions.EditWebContentAssignedToMeActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.editwebcontentassignedtomyrolesactions.EditWebContentAssignedToMyRolesActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.editwebcontentcompletedactions.EditWebContentCompletedActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.editwebcontentcompleteddraftactions.EditWebContentCompletedDraftActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.rejectwebcontentactions.RejectWebContentActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.rejectwebcontentrmenu.RejectWebContentRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.resubmitwebcontentactions.ResubmitWebContentActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.resubmitwebcontentrmenu.ResubmitWebContentRMenuTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.submitforpublicationwebcontentdraft.SubmitForPublicationWebContentDraftTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.updateduedatewebcontentassignedtomeactions.UpdateDueDateWebContentAssignedToMeActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.updateduedatewebcontentassignedtomyrolesactions.UpdateDueDateWebContentAssignedToMyRolesActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.updateduedatewebcontentcompletedactions.UpdateDueDateWebContentCompletedActionsTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.viewwebcontenttaskactivitiesassignedtome.ViewWebContentTaskActivitiesAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.viewwebcontenttaskactivitiesassignedtomyroles.ViewWebContentTaskActivitiesAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.webcontent.viewwebcontenttaskactivitiescompleted.ViewWebContentTaskActivitiesCompletedTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentNoWorkflowTests.suite());
		testSuite.addTest(AddWebContentSingleApproverTests.suite());
		testSuite.addTest(AddWebContentSingleApproverDraftTests.suite());
		testSuite.addTest(ApproveWebContentActionsTests.suite());
		testSuite.addTest(ApproveWebContentResubmitActionsTests.suite());
		testSuite.addTest(ApproveWebContentResubmitRMenuTests.suite());
		testSuite.addTest(ApproveWebContentRMenuTests.suite());
		testSuite.addTest(AssignToMeWebContentActionsTests.suite());
		testSuite.addTest(AssignToMeWebContentDetailsTests.suite());
		testSuite.addTest(AssignToMeWebContentResubmitActionsTests.suite());
		testSuite.addTest(AssignToMeWebContentResubmitDetailsTests.suite());
		testSuite.addTest(AssignToMeWebContentResubmitRMenuTests.suite());
		testSuite.addTest(AssignToMeWebContentRMenuTests.suite());
		testSuite.addTest(DeleteWebContentAssignedToMeActionsTests.suite());
		testSuite.addTest(
			DeleteWebContentAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(DeleteWebContentCompletedEditedListTests.suite());
		testSuite.addTest(EditWebContentAssignedToMeActionsTests.suite());
		testSuite.addTest(EditWebContentAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(EditWebContentCompletedActionsTests.suite());
		testSuite.addTest(EditWebContentCompletedDraftActionsTests.suite());
		testSuite.addTest(DeleteWebContentCompletedActionsTests.suite());
		testSuite.addTest(RejectWebContentActionsTests.suite());
		testSuite.addTest(RejectWebContentRMenuTests.suite());
		testSuite.addTest(ResubmitWebContentActionsTests.suite());
		testSuite.addTest(ResubmitWebContentRMenuTests.suite());
		testSuite.addTest(SubmitForPublicationWebContentDraftTests.suite());
		testSuite.addTest(
			UpdateDueDateWebContentAssignedToMeActionsTests.suite());
		testSuite.addTest(
			UpdateDueDateWebContentAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(UpdateDueDateWebContentCompletedActionsTests.suite());
		testSuite.addTest(
			ViewWebContentTaskActivitiesAssignedToMeTests.suite());
		testSuite.addTest(
			ViewWebContentTaskActivitiesAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewWebContentTaskActivitiesCompletedTests.suite());

		return testSuite;
	}

}