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

package com.liferay.jenkins.results.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tools.ant.Project;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadSlaveDependency(
			"generic-1", "0,label_exp=!master", "129",
			"test-portal-acceptance-pullrequest-batch(master)", "test-4-1");
		_downloadSlaveDependency(
			"rebase-1", null, "267",
			"test-portal-acceptance-pullrequest-source(ee-6.2.x)", "test-1-1");
		_downloadSlaveDependency(
			"plugin-compile-1", "9,label_exp=!master", "233",
			"test-portal-acceptance-pullrequest-batch(ee-6.2.x)", "test-1-20");
	}

	public FailureMessageUtilTest() {
		_project = _initProject();
	}

	@Test
	public void testGetFailureMessage() throws Exception {
		File[] files = _testDependenciesDir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				assertSample(_project, file);
			}
		}
	}
	
	protected String toExternalForm(File file) throws Exception {
		URI uri = file.toURI();
		
		URL url = uri.toURL();
		
		return url.toExternalForm();
	}

	protected void writeExpectedResults(Project project, File sampleDir)
		throws Exception {
		
		String failureMessage = FailureMessageUtil.getFailureMessage(
			project, toExternalForm(sampleDir));

		File file = new File(sampleDir, "expected_results.html");

		_write(file, failureMessage);
	}

	protected void assertSample(Project project, File caseDir)
		throws Exception {

		System.out.print("Asserting sample " + caseDir.getName() + ": ");

		File expectedResultsFile = new File(
			caseDir.getPath(), "expected_results.html");

		String expectedResults = _read(expectedResultsFile);

		String url = caseDir.toURI().toURL().toExternalForm();
		String results = FailureMessageUtil.getFailureMessage(project, url);

		boolean value = results.equals(expectedResults);

		if (!value) {
			System.out.println(" FAILED");
			System.out.println("\nActual results: \n" + results);
			System.out.println("\nExpected results: \n" + expectedResults);
		}
		else {
			System.out.println(" PASSED");
		}

		Assert.assertTrue(value);
	}

	private static URL _createURL(String urlString) throws Exception {
		URL url = new URL(urlString);

		return _encode(url);
	}

	private static void _deleteFile(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		}
		else {
			File[] files = file.listFiles();

			for (File childFile : files) {
				_deleteFile(childFile);
			}

			file.delete();
		}
	}

	private static void _downloadSlaveDependency(
			String caseDescription, String axisVariable, String buildNumber,
			String jobName, String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/" +
				"/${buildNumber}/";

		String slaveDependencyIdentifier =
			caseDescription + "_" + jobName + "_" + hostName + "_" +
				buildNumber;

		if (axisVariable != null) {
			slaveDependencyIdentifier += "_" + axisVariable;

			urlString =
				"https://${hostName}.liferay.com/job/${jobName}/" +
					"AXIS_VARIABLE=${axis}/${buildNumber}/";

			urlString = _replaceToken(urlString, "axis", axisVariable);
		}

		urlString = _replaceToken(urlString, "buildNumber", buildNumber);
		urlString = _replaceToken(urlString, "hostName", hostName);
		urlString = _replaceToken(urlString, "jobName", jobName);

		URL url = _createURL(urlString);

		_downloadSlaveDependency(slaveDependencyIdentifier, url);
	}

	private static void _downloadSlaveDependency(
			String slaveDependencyIdentifier, URL slaveDependencyURL)
		throws Exception {

		System.out.println(
			"downloading slave results: " + slaveDependencyIdentifier);

		String slaveDependencyRootPath =
			_testDependenciesDir.getPath() + "/" + slaveDependencyIdentifier;

		String slaveLogTextPath = slaveDependencyRootPath + "/logText";
		String slaveApiPath = slaveDependencyRootPath + "/api";

		File slaveLogTextDir = new File(slaveLogTextPath);
		File slaveApiDir = new File(slaveApiPath);

		if (slaveLogTextDir.exists()) {
			return;
		}

		slaveLogTextDir.mkdirs();
		slaveApiDir.mkdirs();

		try {
			String jsonURL = slaveDependencyURL.toString() + "/api/json";

			System.out.println(" downloading json from:" + jsonURL);

			String jsonString = JenkinsResultsParserUtil.toString(jsonURL);

			File jsonFile = new File(slaveApiDir.getPath() + "/json");

			_write(jsonFile, jsonString);

			System.out.println(
				" wrote file: " + jsonFile.getPath() + " size; " +
					jsonFile.length());

			String consoleURL =
				slaveDependencyURL.toString() + "/logText/progressiveText";

			System.out.println(" downloading console from:" + consoleURL);

			String console = JenkinsResultsParserUtil.toString(consoleURL);

			File consoleFile = new File(
				slaveLogTextDir.getPath() + "/progressiveText");

			_write(consoleFile, console);

			System.out.println(
				" wrote file: " + consoleFile.getPath() + " size; " +
					consoleFile.length());

			FailureMessageUtilTest failureMessageUtilTest =
				new FailureMessageUtilTest();

			failureMessageUtilTest.writeExpectedResults(
				failureMessageUtilTest._project,
				new File(slaveDependencyRootPath));
		}
		catch (IOException ioe) {
			_deleteFile(slaveLogTextDir);

			_deleteFile(slaveApiDir);

			throw ioe;
		}
	}

	private static URL _encode(URL url) throws Exception {
		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		return new URL(uri.toASCIIString());
	}

	private static Project _initProject() {
		Project project = new Project();

		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("repository", "junit-repository");

		return project;
	}

	private static String _read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	private static String _replaceToken(
		String string, String token, String value) {

		if (string == null) {
			return string;
		}

		return string.replace("${" + token + "}", value);
	}

	private static void _write(File file, String content) throws Exception {
		Files.write(Paths.get(file.toURI()), content.getBytes());
	}

	private static final String _EXPECTED_RESULTS_FILE_PATH =
		"expected-results/FailureMessageUtilTest.html";

	private static final File _testDependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies/" +
		"FailureMessageUtilTest");

	private final Project _project;

}