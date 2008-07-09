/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="StagingTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class StagingTests extends BaseTests {

	public StagingTests() {
		addTestSuite(AddPageTest.class);
		addTestSuite(AddCommunitiesPortletTest.class);
		addTestSuite(FirstNavigateTestPublicCommunityTest.class);
		addTestSuite(AddPublicPortletsTest.class);
		addTestSuite(FirstNavigateTestPrivateCommunityTest.class);
		addTestSuite(AddPrivatePortletsTest.class);
		addTestSuite(ActivateStagingTest.class);
		addTestSuite(NavigateTestCommunityTest.class);
		addTestSuite(AddStagedBlogTest.class);
		addTestSuite(ConfirmNoBlogOnLiveTest.class);
		addTestSuite(PublishBlogToLiveTest.class);
		addTestSuite(ConfirmBlogOnLiveTest.class);
		addTestSuite(AddStagedEventTest.class);
		addTestSuite(ConfirmNoPublicEventTest.class);
		addTestSuite(PublishEventToLiveTest.class);
		addTestSuite(ConfirmEventOnLiveTest.class);
		addTestSuite(AddSecondLiveBlogTest.class);
		addTestSuite(ConfirmNoSecondBlogOnStagedTest.class);
		addTestSuite(ManagePagesCopyFromLiveTest.class);
		addTestSuite(AddThirdStagedBlogTest.class);
		addTestSuite(ConfirmNoThirdBlogOnLiveTest.class);
		addTestSuite(ManagePagesPublishToLiveTest.class);
		addTestSuite(ConfirmThirdBlogOnLiveTest.class);
		addTestSuite(ConfirmElementsNotPresentTest.class);
		addTestSuite(NavigatePrivateTestCommunityTest.class);
		addTestSuite(AddStagedPrivateCalendarTest.class);
		addTestSuite(AddSecondPrivateEventTest.class);
		addTestSuite(ConfirmNoPrivateEventTest.class);
		//addTestSuite(CreateRemoteTestCommunityTest.class);
		//addTestSuite(StoreCommunityIDTest.class);
		//addTestSuite(AssertNoSampleDataOnRemoteTest.class);
		//addTestSuite(RemotePublishStagedPublicPagesTest.class);
		//addTestSuite(VerifyPublicStagedRemotePublishingTest.class);
		//addTestSuite(AddNewPrivateSamplePortletDataTest.class);
		//addTestSuite(PublishNewPrivateSampleDataToLiveTest.class);
		//addTestSuite(VerifyNewPrivateSampleDataTest.class);
		//addTestSuite(RemotePublishStagedPrivatePagesTest.class);
		//addTestSuite(VerifyRemotePublishStagedPrivatePagesTest.class);
	}

}