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

package com.liferay.portal.test.randomizerbumpers;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class TikaSafeRandomizerBumperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, AspectJNewEnvTestRule.INSTANCE);

	@Test
	public void testAcceptAny() {
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			TikaSafeRandomizerBumper.INSTANCE;

		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, true, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, true, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.OFF);

		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, true, Level.INFO);
		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, true, Level.INFO);
		doAccept(
			tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.INFO);
	}

	@Test
	public void testAcceptExe() {
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			new TikaSafeRandomizerBumper("application/x-msdownload");

		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, true, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, false, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.OFF);

		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, true, Level.INFO);
		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, false, Level.INFO);
		doAccept(
			tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.INFO);
	}

	@Test
	public void testAcceptText() {
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			new TikaSafeRandomizerBumper(ContentTypes.TEXT_PLAIN);

		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, true, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, false, Level.OFF);
		doAccept(tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.OFF);

		doAccept(tikaSafeRandomizerBumper, _TEXT_BYTE_ARRAY, true, Level.INFO);
		doAccept(tikaSafeRandomizerBumper, _EXE_BYTE_ARRAY, false, Level.INFO);
		doAccept(
			tikaSafeRandomizerBumper, _BROKEN_EXE_BYTES, false, Level.INFO);
	}

	@AdviseWith(adviceClasses = {ReflectionTestUtilAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testExceptionInInitializerError()
		throws ClassNotFoundException {

		try {
			Class.forName(TikaSafeRandomizerBumper.class.getName());

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Throwable cause = eiie.getCause();

			Assert.assertTrue(cause instanceof RuntimeException);

			Assert.assertSame(
				ReflectionTestUtilAdvice._MESSAGE, cause.getMessage());
		}
	}

	@Aspect
	public static class ReflectionTestUtilAdvice {

		@Around(
			"execution(public static T " +
				"com.liferay.portal.kernel.test.ReflectionTestUtil." +
					"getFieldValue(java.lang.Class<?>, java.lang.String))"
		)
		public <T> T getFieldValue() {
			throw new RuntimeException(_MESSAGE);
		}

		private static final String _MESSAGE = "This is a test.";

	}

	protected void doAccept(
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper, byte[] byteArray,
		boolean accept, Level level) {

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					TikaSafeRandomizerBumper.class.getName(), level)) {

			if (accept) {
				Assert.assertTrue(tikaSafeRandomizerBumper.accept(byteArray));

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				if (level == Level.INFO) {
					Assert.assertEquals(1, logRecords.size());

					LogRecord logRecord = logRecords.get(0);

					Assert.assertEquals(
						"Accepted: " +
							TikaSafeRandomizerBumper.byteArrayToString(
								byteArray),
						logRecord.getMessage());
				}
				else {
					Assert.assertTrue(logRecords.isEmpty());
				}
			}
			else {
				Assert.assertFalse(tikaSafeRandomizerBumper.accept(byteArray));

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertTrue(logRecords.isEmpty());
			}
		}
	}

	private static final byte[] _BROKEN_EXE_BYTES = "MZ5gFGQt".getBytes();

	// http://www.phreedom.org/research/tinype

	private static final byte[] _EXE_BYTE_ARRAY = new byte[] {
		77, 90, 0, 0, 80, 69, 0, 0, 76, 1, 1, 0, 106, 42, 88, -61, 0, 0, 0, 0,
		0, 0, 0, 0, 4, 0, 3, 1, 11, 1, 8, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0,
		12, 0, 0, 0, 4, 0, 0, 0, 12, 0, 0, 0, 0, 0, 64, 0, 4, 0, 0, 0, 4, 0, 0,
		0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 100, 0,
		0, 0, 0, 0, 0, 0, 2
	};

	private static final byte[] _TEXT_BYTE_ARRAY = "WHz5WKch".getBytes();

}