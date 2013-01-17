/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

/**
 * @author Miguel Pastor
 */
public class SynchronousDestinationExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		_setSync(_previousSync);
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		_previousSync = ProxyModeThreadLocal.isForceSync();

		Class<?> testClass = testContext.getClazz();

		_sync = AnnotationLocator.locate(testClass, Sync.class);

		_setSync(true);
	}

	private void _setSync(boolean sync) {
		if (_sync != null) {
			ProxyModeThreadLocal.setForceSync(sync);
		}
	}

	private boolean _previousSync;

	private Sync _sync;

}