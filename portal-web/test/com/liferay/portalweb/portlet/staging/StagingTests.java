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

package com.liferay.portalweb.portlet.staging;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="StagingTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class StagingTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddCommunitiesPortletTest.class);
		testSuite.addTestSuite(FirstNavigateTestPublicCommunityTest.class);
		testSuite.addTestSuite(AddPublicPortletsTest.class);
		testSuite.addTestSuite(FirstNavigateTestPrivateCommunityTest.class);
		testSuite.addTestSuite(AddPrivatePortletsTest.class);
		testSuite.addTestSuite(ActivateStagingTest.class);
		testSuite.addTestSuite(NavigateTestCommunityTest.class);
		testSuite.addTestSuite(AddStagedBlogTest.class);
		testSuite.addTestSuite(ConfirmNoBlogOnLiveTest.class);
		testSuite.addTestSuite(PublishBlogToLiveTest.class);
		testSuite.addTestSuite(ConfirmBlogOnLiveTest.class);
		testSuite.addTestSuite(AddStagedEventTest.class);
		testSuite.addTestSuite(ConfirmNoPublicEventTest.class);
		testSuite.addTestSuite(PublishEventToLiveTest.class);
		testSuite.addTestSuite(ConfirmEventOnLiveTest.class);
		testSuite.addTestSuite(AddSecondLiveBlogTest.class);
		testSuite.addTestSuite(ConfirmNoSecondBlogOnStagedTest.class);
		testSuite.addTestSuite(ManagePagesCopyFromLiveTest.class);
		testSuite.addTestSuite(AddThirdStagedBlogTest.class);
		testSuite.addTestSuite(ConfirmNoThirdBlogOnLiveTest.class);
		testSuite.addTestSuite(ManagePagesPublishToLiveTest.class);
		testSuite.addTestSuite(ConfirmThirdBlogOnLiveTest.class);
		testSuite.addTestSuite(ConfirmElementsNotPresentTest.class);
		testSuite.addTestSuite(NavigatePrivateTestCommunityTest.class);
		testSuite.addTestSuite(AddStagedPrivateCalendarTest.class);
		testSuite.addTestSuite(AddSecondPrivateEventTest.class);
		testSuite.addTestSuite(ConfirmNoPrivateEventTest.class);
		//testSuite.addTestSuite(CreateRemoteTestCommunityTest.class);
		//testSuite.addTestSuite(StoreCommunityIDTest.class);
		//testSuite.addTestSuite(AssertNoSampleDataOnRemoteTest.class);
		//testSuite.addTestSuite(RemotePublishStagedPublicPagesTest.class);
		//testSuite.addTestSuite(VerifyPublicStagedRemotePublishingTest.class);
		//testSuite.addTestSuite(AddNewPrivateSamplePortletDataTest.class);
		//testSuite.addTestSuite(PublishNewPrivateSampleDataToLiveTest.class);
		//testSuite.addTestSuite(VerifyNewPrivateSampleDataTest.class);
		//testSuite.addTestSuite(RemotePublishStagedPrivatePagesTest.class);
		//testSuite.addTestSuite(VerifyRemotePublishStagedPrivatePagesTest.class
		testSuite.addTestSuite(DeletePageTest.class);

		return testSuite;
	}

}