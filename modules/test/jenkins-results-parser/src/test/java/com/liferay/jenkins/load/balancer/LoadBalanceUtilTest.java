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

package com.liferay.jenkins.load.balancer;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

import org.apache.tools.ant.Project;
import org.junit.Before;
import org.junit.Test;

import com.liferay.jenkins.load.balancer.LoadBalanceUtil;
import com.liferay.jenkins.results.parser.BaseJenkinsResultsParserTestCase;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

/**
 * @author Peter Yoo
 */
public class LoadBalanceUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample("test-1", null);
		downloadSample("test-2", null);
		//downloadSample("test-3", null);
	}
	
	public LoadBalanceUtilTest() {
		dependenciesDir = new File(
			"src/test/resources/com/liferay/load/balance/dependencies/" +
				getSimpleClassName());
	}
	
	@Test
	public void testGetMostAvailableMasterURL() throws Exception {
		assertSamples();
	}
	
	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		Project project = getDownloadProject(sampleDir.getName());
		int maxHostNames = 
			LoadBalanceUtil.calculateMaxHostNames(project, sampleDir.getName());
		for (int i = 1; i <= maxHostNames; i++) {
						
			downloadSampleURL(
				new File(sampleDir, sampleDir.getName() + "-" + i), 
				createURL(
					project.getProperty(
						"jenkins.local.url[" + sampleDir.getName() + "-" + i +
						"]")),
				"/computer/api/json?pretty&tree=computer[idle]");
		}
	}
	
	@Override
	protected String getMessage(String urlString) throws Exception {
		File sampleDir = new File(urlString.substring("file:".length()));

		Project project = getTestProject(sampleDir.getName());

		return LoadBalanceUtil.getMostAvailableMasterURL(project);
	}
	
	protected static Project getDownloadProject(String baseInvocationHostName) {
		Project project = new Project();

		project.setProperty(
			"base.invocation.url", "http://" + baseInvocationHostName +
			".liferay.com");
		project.setProperty("jenkins.shared.dir", "mnt/mfs-ssd1-10.10/jenkins");
		project.setProperty("jenkins.local.url[test-1-1]", "http://test-1-1");
		project.setProperty("jenkins.local.url[test-1-2]", "http://test-1-2");
		project.setProperty("jenkins.local.url[test-1-3]", "http://test-1-3");
		project.setProperty("jenkins.local.url[test-1-4]", "http://test-1-4");
		project.setProperty("jenkins.local.url[test-1-5]", "http://test-1-5");
		project.setProperty("jenkins.local.url[test-1-6]", "http://test-1-6");
		project.setProperty("jenkins.local.url[test-1-7]", "http://test-1-7");
		project.setProperty("jenkins.local.url[test-1-8]", "http://test-1-8");
		project.setProperty("jenkins.local.url[test-1-9]", "http://test-1-9");
		project.setProperty("jenkins.local.url[test-1-10]", "http://test-1-10");
		project.setProperty("jenkins.local.url[test-1-11]", "http://test-1-11");
		project.setProperty("jenkins.local.url[test-1-12]", "http://test-1-12");
		project.setProperty("jenkins.local.url[test-1-13]", "http://test-1-13");
		project.setProperty("jenkins.local.url[test-1-14]", "http://test-1-14");
		project.setProperty("jenkins.local.url[test-1-15]", "http://test-1-15");
		project.setProperty("jenkins.local.url[test-1-16]", "http://test-1-16");
		project.setProperty("jenkins.local.url[test-1-17]", "http://test-1-17");
		project.setProperty("jenkins.local.url[test-1-18]", "http://test-1-18");
		project.setProperty("jenkins.local.url[test-1-19]", "http://test-1-19");
		project.setProperty("jenkins.local.url[test-1-20]", "http://test-1-20");
		project.setProperty("jenkins.local.url[test-2-1]", "http://test-2-1");
		project.setProperty("jenkins.local.url[test-3-1]", "http://test-3-1");
		project.setProperty("jenkins.local.url[test-3-2]", "http://test-3-2");
		project.setProperty("jenkins.local.url[test-3-3]", "http://test-3-3");
		
		return project;
	}
	
	protected static Project getTestProject(String baseInvocationHostName) {
		Project project = getDownloadProject(baseInvocationHostName);
		Hashtable<String, Object> propertiesTable = project.getProperties();
		for (String key : propertiesTable.keySet()) {
			if (key.equals("base.invocation.url")) {
				continue;
			}
			String value = (String) propertiesTable.get(key);
			value.replace("http://", "file:" + System.getProperty("user.dir") + "/");
			propertiesTable.put(key, value);
		}
		return project;
	}

}