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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.local.LocalProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author Shuyang Zhou
 */
@RunWith(PACLAggregateTest.PACLAggregateTestRunner.class)
public class PACLAggregateTest {

	@Test
	public void testPACLTests() throws Exception {
		LocalProcessExecutor localProcessExecutor = new LocalProcessExecutor();

		try {
			List<Class<?>> classes = scanTestClasses();

			ProcessChannel<Result> processChannel =
				localProcessExecutor.execute(
					createProcessConfig(),
					new PACLTestsProcessCallable(classes));

			Future<Result> future = processChannel.getProcessNoticeableFuture();

			future.get();
		}
		finally {
			localProcessExecutor.destroy();
		}
	}

	public static class PACLAggregateTestRunner extends BlockJUnit4ClassRunner {

		public PACLAggregateTestRunner(Class<?> clazz)
			throws InitializationError {

			super(clazz);
		}

		@Override
		public void run(RunNotifier runNotifier) {
			_runNotifier = runNotifier;

			super.run(runNotifier);
		}

		private static RunNotifier _runNotifier;

	}

	protected ProcessConfig createProcessConfig() {
		Builder builder = new Builder();

		List<String> arguments = new ArrayList<>();

		arguments.add(_JVM_XMX);
		arguments.add(_JVM_MAX_PERM_SIZE);
		arguments.add("-Djava.security.manager");

		URL url = PACLAggregateTest.class.getResource("security.policy");

		arguments.add("-Djava.security.policy=" + url.getFile());

		boolean junitDebug = Boolean.getBoolean("junit.debug");

		if (junitDebug) {
			arguments.add(_JPDA_OPTIONS);
			arguments.add("-Djunit.debug=true");
		}

		builder.setArguments(arguments);
		builder.setBootstrapClassPath(System.getProperty("java.class.path"));
		builder.setReactClassLoader(PACLAggregateTest.class.getClassLoader());

		return builder.build();
	}

	protected List<Class<?>> scanTestClasses() throws ClassNotFoundException {
		URL url = PACLAggregateTest.class.getResource("test");

		File folder = new File(url.getFile());

		File[] files = folder.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (!file.isFile()) {
						return false;
					}

					String fileName = file.getName();

					if (fileName.indexOf('$') != -1) {
						return false;
					}

					return fileName.endsWith(".class");
				}

			});

		Package pkg = PACLAggregateTest.class.getPackage();

		String packageName = pkg.getName();

		packageName = packageName.concat(".test.");

		ClassLoader classLoader = PACLAggregateTest.class.getClassLoader();

		List<Class<?>> classes = new ArrayList<>();

		for (File file : files) {
			String fileName = file.getName();

			classes.add(
				classLoader.loadClass(
					packageName.concat(
						fileName.substring(0, fileName.lastIndexOf('.')))));
		}

		return classes;
	}

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final String _JVM_MAX_PERM_SIZE = "-XX:MaxPermSize=256m";

	private static final String _JVM_XMX = "-Xmx1024m";

	private static class NoticeBridgeRunListener
		extends RunListener implements Serializable {

		@Override
		public void testAssumptionFailure(Failure failure) {
			write("fireTestAssumptionFailed", failure);
		}

		@Override
		public void testFailure(Failure failure) {
			write("fireTestFailure", failure);
		}

		@Override
		public void testFinished(Description description) {
			write("fireTestFinished", description);
		}

		@Override
		public void testIgnored(Description description) {
			write("fireTestIgnored", description);
		}

		@Override
		public void testRunFinished(Result result) {
			write("fireTestRunFinished", result);
		}

		@Override
		public void testRunStarted(Description description) {
			write("fireTestRunStarted", description);
		}

		@Override
		public void testStarted(Description description) {
			write("fireTestStarted", description);
		}

		protected void write(final String methodName, final Object argument) {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			try {
				processOutputStream.writeProcessCallable(
					new ProcessCallable<Serializable>() {

						@Override
						public Serializable call() {
							ReflectionTestUtil.invoke(
								PACLAggregateTestRunner._runNotifier,
								methodName,
								new Class<?>[] {argument.getClass()}, argument);

							return null;
						}

						private static final long serialVersionUID = 1L;

					});
			}
			catch (IOException ioe) {
				ReflectionUtil.throwException(ioe);
			}
		}

		private static final long serialVersionUID = 1L;

	}

	private static class PACLTestsProcessCallable
		implements ProcessCallable<Result> {

		@Override
		public Result call() {
			try {
				JUnitCore junitCore = new JUnitCore();

				junitCore.addListener(new NoticeBridgeRunListener());

				return junitCore.run(
					_classes.toArray(new Class<?>[_classes.size()]));
			}
			finally {
				InitUtil.stopModuleFramework();

				MPIHelperUtil.shutdown();
			}
		}

		@Override
		public String toString() {
			return "PACLTestSuite";
		}

		private PACLTestsProcessCallable(List<Class<?>> classes) {
			_classes = classes;
		}

		private static final long serialVersionUID = 1L;

		private final List<Class<?>> _classes;

	}

}