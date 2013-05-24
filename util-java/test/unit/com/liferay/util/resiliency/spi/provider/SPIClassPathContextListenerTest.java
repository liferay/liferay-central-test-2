/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util.resiliency.spi.provider;

import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class SPIClassPathContextListenerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() {
		Queue<File> fileQueue = new LinkedList<File>();

		fileQueue.offer(new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME));

		File file = null;

		while ((file = fileQueue.poll()) != null) {
			if (file.isFile()) {
				file.delete();
			}
			else if (file.isDirectory()) {
				File[] files = file.listFiles();

				if (files.length == 0) {
					file.delete();
				}
				else {
					fileQueue.addAll(Arrays.asList(files));
					fileQueue.add(file);
				}
			}
		}
	}

	@Test
	public void testClassPathGeneration() throws IOException {

		// With log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.FINE);

		File spiEmbeddedLibDir = new File(
			_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		spiEmbeddedLibDir.mkdir();

		File jarFile = new File(spiEmbeddedLibDir, "jarfile.jar");

		jarFile.createNewFile();

		File notJarFile = new File(spiEmbeddedLibDir, "notjarfile.zip");

		notJarFile.createNewFile();

		String invalidSPIProviderClassName = "InvalidSPIProvider";

		_mockServletContext.addInitParameter(
			"spiProviderClassName", invalidSPIProviderClassName);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		String spiClassPath =
			jarFile.getAbsolutePath() + File.pathSeparator + _CONTEXT_PATH +
				"/WEB-INF/classes" + File.pathSeparator +
					ClassPathUtil.getGlobalClassPath();

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertEquals(3, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"SPI embedded lib class path " + jarFile.getAbsolutePath() +
				File.pathSeparator, logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals(
			"SPI class path " + spiClassPath, logRecord.getMessage());

		logRecord = logRecords.get(2);

		Assert.assertEquals(
			"Unable to create SPI provider with name InvalidSPIProvider",
			logRecord.getMessage());

		// Without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.OFF);

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertTrue(logRecords.isEmpty());
	}

	@Test
	public void testLoadClassDirectly() throws Exception {

		String jvmClassPath = ClassPathUtil.getJVMClassPath(false);

		URL[] urls = ClassPathUtil.getClassPathURLs(jvmClassPath);

		ClassLoader parentClassLoader = new URLClassLoader(urls, null);

		ClassLoader childClassLoader = new URLClassLoader(
			urls, parentClassLoader);

		Class<?> clazz = SPIClassPathContextListener.loadClassDirectly(
			childClassLoader, TestClass.class.getName());

		Assert.assertNotSame(TestClass.class, clazz);
		Assert.assertEquals(TestClass.class.getName(), clazz.getName());
		Assert.assertSame(childClassLoader, clazz.getClassLoader());

		Method findLoadedClassMethod = ReflectionUtil.getDeclaredMethod(
			ClassLoader.class, "findLoadedClass", String.class);

		Assert.assertSame(
			clazz,
			findLoadedClassMethod.invoke(
				childClassLoader, TestClass.class.getName()));
		Assert.assertNull(
			findLoadedClassMethod.invoke(
				parentClassLoader, TestClass.class.getName()));
		Assert.assertSame(
			clazz,
			SPIClassPathContextListener.loadClassDirectly(
				childClassLoader, TestClass.class.getName()));
	}

	@Test
	public void testMissingSPIEmbeddedLibDir() throws IOException {

		// SPI embedded lib directory does not exist

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_DIR_NAME,
			logRecord.getMessage());

		// SPI embedded lib directory is not a directory

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		File file = new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		file.deleteOnExit();

		file.createNewFile();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));
		}
		finally {
			file.delete();
		}

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_DIR_NAME,
			logRecord.getMessage());
	}

	@Test
	public void testRegistration() {

		// Register

		File embeddedLibDir = new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		embeddedLibDir.mkdir();

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		_mockServletContext.addInitParameter(
			"spiProviderClassName", MockSPIProvider.class.getName());

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNotNull(spiProviderReference.get());

		List<SPIProvider> spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertEquals(1, spiProviders.size());
		Assert.assertSame(spiProviderReference.get(), spiProviders.get(0));

		// Duplicate register

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Duplicate SPI provider " + spiProviderReference.get() +
				" is already registered in servlet context " +
					_mockServletContext.getContextPath(),
			logRecord.getMessage());

		// Unregister

		spiClassPathContextListener.contextDestroyed(
			new ServletContextEvent(_mockServletContext));

		Assert.assertNull(spiProviderReference.get());

		spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertTrue(spiProviders.isEmpty());

		// Duplicate unregister

		spiClassPathContextListener.contextDestroyed(
			new ServletContextEvent(_mockServletContext));

		Assert.assertNull(spiProviderReference.get());

		spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertTrue(spiProviders.isEmpty());

		embeddedLibDir.delete();
	}

	public static class MockSPIProvider implements SPIProvider {

		@Override
		public SPI createSPI(SPIConfiguration spiConfiguration) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName() {
			return MockSPIProvider.class.getName();
		}

		@Override
		public String toString() {
			return MockSPIProvider.class.getName();
		}

	}

	private String _CONTEXT_PATH = System.getProperty("java.io.tmpdir");

	private String _EMBEDDED_LIB_DIR_NAME = "/embeddedLibDir";

	private MockServletContext _mockServletContext = new MockServletContext() {

		{
			addInitParameter("spiEmbeddedLibDir", _EMBEDDED_LIB_DIR_NAME);
		}

		@Override
		public String getRealPath(String path) {
			return _CONTEXT_PATH;
		}

	};

	private class TestClass {
	}

}