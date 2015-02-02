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

package com.liferay.arquillian.extension;

import com.liferay.arquillian.extension.internal.event.LiferayEventTestRunnerAdapter;
import com.liferay.arquillian.extension.internal.instanceproducer.ExtensionInstanceProducer;
import com.liferay.arquillian.extension.internal.log.LogAssertionExecutorInArquillian;
import com.liferay.arquillian.extension.internal.log.LogAssertionObserver;
import com.liferay.portal.kernel.test.rule.executor.ClearThreadLocalExecutor;
import com.liferay.portal.kernel.test.rule.executor.DeleteAfterTestRunExecutor;
import com.liferay.portal.kernel.test.rule.executor.DeleteAfterTestRunExecutorImpl;
import com.liferay.portal.kernel.test.rule.executor.InitTestLiferayContextExecutor;
import com.liferay.portal.kernel.test.rule.executor.UniqueStringRandomizerBumperExecutor;
import com.liferay.portal.test.rule.executor.ClearThreadLocalExecutorImpl;
import com.liferay.portal.test.rule.executor.InitTestLiferayContextExecutorImpl;
import com.liferay.portal.test.rule.executor.LogAssertionExecutor;
import com.liferay.portal.test.rule.executor.UniqueStringRandomizerBumperExecutorImpl;

import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * @author Cristina González Castellano
 */
public class LiferayTestScenarioExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
		extensionBuilder.observer(ExtensionInstanceProducer.class);
		extensionBuilder.observer(LiferayEventTestRunnerAdapter.class);
		extensionBuilder.observer(LogAssertionObserver.class);

		extensionBuilder.service(
			ClearThreadLocalExecutor.class, ClearThreadLocalExecutorImpl.class);
		extensionBuilder.service(
			DeleteAfterTestRunExecutor.class,
			DeleteAfterTestRunExecutorImpl.class);
		extensionBuilder.service(
			InitTestLiferayContextExecutor.class,
			InitTestLiferayContextExecutorImpl.class);
		extensionBuilder.service(
			LogAssertionExecutor.class, LogAssertionExecutorInArquillian.class);
		extensionBuilder.service(
			UniqueStringRandomizerBumperExecutor.class,
			UniqueStringRandomizerBumperExecutorImpl.class);
	}

}