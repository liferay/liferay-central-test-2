/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.util.test.MailServiceTestUtil;

import org.junit.runner.Description;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 * @author Shuyang Zhou
 */
public class SynchronousMailTestRule extends SynchronousDestinationTestRule {

	public static final SynchronousMailTestRule INSTANCE =
		new SynchronousMailTestRule();

	@Override
	protected void afterClass(
		Description description, SyncHandler syncHandler) {

		syncHandler.restorePreviousSync();

		MailServiceTestUtil.stop();
	}

	@Override
	protected void afterMethod(
		Description description, SyncHandler syncHandler) {

		super.afterMethod(description, syncHandler);

		MailServiceTestUtil.clearMessages();
	}

	@Override
	protected SyncHandler beforeClass(Description description) {
		MailServiceTestUtil.start();

		SyncHandler syncHandler = new SyncHandler();

		syncHandler.replaceDestination(DestinationNames.MAIL);

		return syncHandler;
	}

	private SynchronousMailTestRule() {
	}

}