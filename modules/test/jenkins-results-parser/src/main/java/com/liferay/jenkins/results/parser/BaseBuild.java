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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public abstract class BaseBuild implements Build {

	@Override
	public void addDownstreamBuilds(String... urls) {
		for (String url : urls) {
			try {
				url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.decode(url));
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + url, uee);
			}

			if (!hasBuildURL(url)) {
				downstreamBuilds.add(BuildFactory.newBuild(url, this));
			}
		}
	}

	@Override
	public void archive(final String archiveName) {
		if (!_status.equals("completed")) {
			throw new RuntimeException("Invalid build status: " + _status);
		}

		this.archiveName = archiveName;

		File archiveDir = new File(getArchivePath());

		if (archiveDir.exists()) {
			archiveDir.delete();
		}

		if (downstreamBuilds != null) {
			ExecutorService executorService = getExecutorService();

			for (final Build downstreamBuild : downstreamBuilds) {
				if (executorService != null) {
					Runnable runnable = new Runnable() {

						@Override
						public void run() {
							downstreamBuild.archive(archiveName);
						}

					};

					executorService.execute(runnable);
				}
				else {
					downstreamBuild.archive(archiveName);
				}
			}

			if (executorService != null) {
				executorService.shutdown();

				while (!executorService.isTerminated()) {
					JenkinsResultsParserUtil.sleep(100);
				}
			}
		}

		try {
			writeArchiveFile(
				Long.toString(System.currentTimeMillis()),
				getArchivePath() + "/archive-marker");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to to write archive-marker file", ioe);
		}

		archiveConsoleLog();
		archiveJSON();
	}

	@Override
	public String getAppServer() {
		return null;
	}

	@Override
	public String getArchivePath() {
		StringBuilder sb = new StringBuilder(archiveName);

		if (!archiveName.endsWith("/")) {
			sb.append("/");
		}

		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());

		return sb.toString();
	}

	@Override
	public List<String> getBadBuildURLs() {
		List<String> badBuildURLs = new ArrayList<>();

		String jobURL = getJobURL();

		for (Integer badBuildNumber : badBuildNumbers) {
			StringBuilder sb = new StringBuilder();

			sb.append(jobURL);
			sb.append("/");
			sb.append(badBuildNumber);
			sb.append("/");

			badBuildURLs.add(sb.toString());
		}

		return badBuildURLs;
	}

	@Override
	public String getBranchName() {
		return branchName;
	}

	@Override
	public String getBrowser() {
		return null;
	}

	@Override
	public JSONObject getBuildJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				getBuildURL() + "api/json", false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON object", ioe);
		}
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public String getBuildURL() {
		try {
			String jobURL = getJobURL();

			if ((jobURL == null) || (_buildNumber == -1)) {
				return null;
			}

			if (fromArchive) {
				return jobURL + "/" + _buildNumber + "/";
			}

			jobURL = JenkinsResultsParserUtil.decode(jobURL);

			return JenkinsResultsParserUtil.encode(
				jobURL + "/" + _buildNumber + "/");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getBuildURLRegex() {
		StringBuffer sb = new StringBuffer();

		sb.append("http[s]*:\\/\\/");
		sb.append(JenkinsResultsParserUtil.getRegexLiteral(getMaster()));
		sb.append("[^\\/]*");
		sb.append("[\\/]+job[\\/]+");

		String jobNameRegexLiteral = JenkinsResultsParserUtil.getRegexLiteral(
			getJobName());

		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\(", "(\\(|%28)");
		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\)", "(\\)|%29)");

		sb.append(jobNameRegexLiteral);

		sb.append("[\\/]+");
		sb.append(getBuildNumber());
		sb.append("[\\/]*");

		String buildURLRegex = sb.toString();

		return buildURLRegex;
	}

	@Override
	public String getConsoleText() {
		String status = getStatus();

		if (status.equals("running")) {
			JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
				new JenkinsConsoleTextLoader(getBuildURL());

			return jenkinsConsoleTextLoader.getConsoleText();
		}

		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "/consoleText"),
				false);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDatabase() {
		return null;
	}

	@Override
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getJobName());

		String jobVariant = getParameterValue("JOB_VARIANT");

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append("/");
			sb.append(jobVariant);
		}

		return sb.toString();
	}

	@Override
	public int getDownstreamBuildCount(String status) {
		List<Build> downstreamBuilds = getDownstreamBuilds(status);

		return downstreamBuilds.size();
	}

	@Override
	public List<Build> getDownstreamBuilds(String status) {
		if (status == null) {
			return downstreamBuilds;
		}

		List<Build> filteredDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			if (status.equals(downstreamBuild.getStatus())) {
				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public long getDuration() {
		JSONObject buildJSONObject = getBuildJSONObject("duration,timestamp");

		long duration = buildJSONObject.getLong("duration");

		if (duration == 0) {
			long timestamp = buildJSONObject.getLong("timestamp");

			duration = System.currentTimeMillis() - timestamp;
		}

		return duration;
	}

	@Override
	public String getInvocationURL() {
		String jobURL = getJobURL();

		if (jobURL == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(jobURL);

		sb.append("/buildWithParameters?");

		Map<String, String> parameters = getParameters();

		parameters.put("token", "raen3Aib");

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			String value = parameter.getValue();

			if ((value != null) && !value.isEmpty()) {
				sb.append(parameter.getKey());
				sb.append("=");
				sb.append(parameter.getValue());
				sb.append("&");
			}
		}

		sb.deleteCharAt(sb.length() - 1);

		try {
			return JenkinsResultsParserUtil.encode(sb.toString());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getJDK() {
		return null;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobURL() {
		if ((master == null) || (jobName == null)) {
			return null;
		}

		if (fromArchive) {
			return "${dependencies.url}/" + archiveName + "/" + master + "/" +
				jobName;
		}

		try {
			return JenkinsResultsParserUtil.encode(
				"http://" + master + "/job/" + jobName);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getJobVariant() {
		String batchName = getParameterValue("JOB_VARIANT");

		if ((batchName == null) || batchName.isEmpty()) {
			batchName = getParameterValue("JENKINS_JOB_VARIANT");
		}

		return batchName;
	}

	@Override
	public String getMaster() {
		return master;
	}

	@Override
	public String getOperatingSystem() {
		return null;
	}

	@Override
	public Map<String, String> getParameters() {
		return new HashMap<>(_parameters);
	}

	@Override
	public String getParameterValue(String name) {
		return _parameters.get(name);
	}

	@Override
	public Build getParentBuild() {
		return _parentBuild;
	}

	@Override
	public String getRepositoryName() {
		if (repositoryName == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("repository[");

			TopLevelBuild topLevelBuild = getTopLevelBuild();

			sb.append(topLevelBuild.getJobName());

			sb.append("]");

			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build.properties", ioe);
			}

			repositoryName = buildProperties.getProperty(sb.toString());

			if (repositoryName == null) {
				throw new RuntimeException(
					"Unable to get repository name for job " +
						topLevelBuild.getJobName());
			}
		}

		return repositoryName;
	}

	@Override
	public String getResult() {
		String buildURL = getBuildURL();

		if ((result == null) && (buildURL != null)) {
			try {
				JSONObject resultJSONObject = getBuildJSONObject("result");

				result = resultJSONObject.optString("result");

				if (result.equals("")) {
					result = null;
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}

	@Override
	public Map<String, String> getStartPropertiesTempMap() {
		return getTempMap("start.properties");
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public long getStatusAge() {
		return System.currentTimeMillis() - statusModifiedTime;
	}

	@Override
	public String getStatusReport() {
		return getStatusReport(0);
	}

	@Override
	public String getStatusReport(int indentSize) {
		StringBuffer indentStringBuffer = new StringBuffer();

		for (int i = 0; i < indentSize; i++) {
			indentStringBuffer.append(" ");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(indentStringBuffer);
		sb.append("Build \"");
		sb.append(jobName);
		sb.append("\"");

		String status = getStatus();

		if (status.equals("completed")) {
			sb.append(" completed at ");
			sb.append(getBuildURL());
			sb.append(". ");
			sb.append(getResult());

			return sb.toString();
		}

		if (status.equals("missing")) {
			sb.append(" is missing ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("queued")) {
			sb.append(" is queued at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("running")) {
			sb.append(" running at ");
			sb.append(getBuildURL());
			sb.append(".\n");

			if (getDownstreamBuildCount(null) > 0) {
				sb.append("\n");

				for (Build downstreamBuild : getDownstreamBuilds("running")) {
					sb.append(downstreamBuild.getStatusReport(indentSize + 4));
				}

				sb.append("\n");
				sb.append(indentStringBuffer);
				sb.append(getStatusSummary());
				sb.append("\n");
			}

			return sb.toString();
		}

		if (status.equals("starting")) {
			sb.append(" invoked at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		throw new RuntimeException("Unknown status: " + status);
	}

	@Override
	public String getStatusSummary() {
		StringBuilder sb = new StringBuilder();

		sb.append(getDownstreamBuildCount("starting"));
		sb.append(" Starting  ");
		sb.append("/ ");

		sb.append(getDownstreamBuildCount("missing"));
		sb.append(" Missing  ");
		sb.append("/ ");

		sb.append(getDownstreamBuildCount("queued"));
		sb.append(" Queued  ");
		sb.append("/ ");

		sb.append(getDownstreamBuildCount("running"));
		sb.append(" Running  ");
		sb.append("/ ");

		sb.append(getDownstreamBuildCount("completed"));
		sb.append(" Completed  ");
		sb.append("/ ");

		sb.append(getDownstreamBuildCount(null));
		sb.append(" Total ");

		return sb.toString();
	}

	@Override
	public Map<String, String> getStopPropertiesTempMap() {
		return getTempMap("stop.properties");
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "testReport/api/json"),
				false);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get test report JSON object", ioe);
		}
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		List<TestResult> testResults = new ArrayList<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			testResults.addAll(downstreamBuild.getTestResults(testStatus));
		}

		return testResults;
	}

	@Override
	public TopLevelBuild getTopLevelBuild() {
		Build topLevelBuild = this;

		while ((topLevelBuild != null) &&
		 !(topLevelBuild instanceof TopLevelBuild)) {

			topLevelBuild = topLevelBuild.getParentBuild();
		}

		return (TopLevelBuild)topLevelBuild;
	}

	@Override
	public boolean hasBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		buildURL = JenkinsResultsParserUtil.getLocalURL(buildURL);

		String thisBuildURL = getBuildURL();

		if ((thisBuildURL != null) && thisBuildURL.equals(buildURL)) {
			return true;
		}

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild.hasBuildURL(buildURL)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void reinvoke() {
		String hostName = JenkinsResultsParserUtil.getHostName("");

		Build parentBuild = getParentBuild();

		String parentBuildStatus = parentBuild.getStatus();

		if (!parentBuildStatus.equals("running")) {
			System.out.println(
				"Parent build is no longer running. Reinvocation has been " +
					"aborted.");

			return;
		}

		if (!hostName.startsWith("cloud-10-0")) {
			System.out.println("A build may not be reinvoked by " + hostName);

			return;
		}

		String invocationURL = getInvocationURL();

		try {
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(invocationURL));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println(getReinvokedMessage());

		reset();
	}

	@Override
	public String replaceBuildURL(String text) {
		if ((text == null) || text.isEmpty()) {
			return text;
		}

		if (downstreamBuilds != null) {
			for (Build downstreamBuild : downstreamBuilds) {
				Build downstreamBaseBuild = downstreamBuild;

				text = downstreamBaseBuild.replaceBuildURL(text);
			}
		}

		text = text.replaceAll(
			getBuildURLRegex(),
			Matcher.quoteReplacement(
				"${dependencies.url}/" + getArchivePath()));

		Build parentBuild = getParentBuild();

		while (parentBuild != null) {
			text = text.replaceAll(
				parentBuild.getBuildURLRegex(),
				Matcher.quoteReplacement(
					"${dependencies.url}/" + parentBuild.getArchivePath()));

			parentBuild = parentBuild.getParentBuild();
		}

		return text;
	}

	@Override
	public void update() {
		String status = getStatus();

		if (!status.equals("completed")) {
			try {
				if (status.equals("missing") || status.equals("queued") ||
					status.equals("starting")) {

					JSONObject runningBuildJSONObject =
						getRunningBuildJSONObject();

					if (runningBuildJSONObject != null) {
						setBuildNumber(runningBuildJSONObject.getInt("number"));
					}
					else {
						JSONObject queueItemJSONObject =
							getQueueItemJSONObject();

						if (status.equals("starting") &&
							(queueItemJSONObject != null)) {

							setStatus("queued");
						}
						else if (status.equals("queued") &&
								 (queueItemJSONObject == null)) {

							setStatus("missing");
						}
					}
				}

				status = getStatus();

				if (downstreamBuilds != null) {
					ExecutorService executorService = getExecutorService();

					for (final Build downstreamBuild : downstreamBuilds) {
						if (executorService != null) {
							Runnable runnable = new Runnable() {

								@Override
								public void run() {
									downstreamBuild.update();
								}

							};

							executorService.execute(runnable);
						}
						else {
							downstreamBuild.update();
						}
					}

					if (executorService != null) {
						executorService.shutdown();

						while (!executorService.isTerminated()) {
							JenkinsResultsParserUtil.sleep(100);
						}
					}

					String result = getResult();

					if ((downstreamBuilds.size() ==
							getDownstreamBuildCount("completed")) &&
						(result != null)) {

						setStatus("completed");
					}
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}

			findDownstreamBuilds();
		}
	}

	protected BaseBuild(String url) {
		this(url, null);
	}

	protected BaseBuild(String url, Build parentBuild) {
		_parentBuild = parentBuild;

		if (url.contains("buildWithParameters")) {
			setInvocationURL(url);
		}
		else {
			setBuildURL(url);
		}

		update();
	}

	protected void archiveConsoleLog() {
		downloadSampleURL(
			getArchivePath(), true, getBuildURL(), "/consoleText");
	}

	protected void archiveJSON() {
		downloadSampleURL(getArchivePath(), true, getBuildURL(), "api/json");
		downloadSampleURL(
			getArchivePath(), false, getBuildURL(), "testReport/api/json");

		if (!getStartPropertiesTempMap().isEmpty()) {
			try {
				JSONObject startPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStartPropertiesTempMapURL());

				writeArchiveFile(
					startPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/start.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create start.properties.json", ioe);
			}
		}

		if (!getStopPropertiesTempMap().isEmpty()) {
			try {
				JSONObject stopPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStopPropertiesTempMapURL());

				writeArchiveFile(
					stopPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/stop.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create stop.properties.json", ioe);
			}
		}
	}

	protected void checkForReinvocation() {
		TopLevelBuild topLevelBuild = (TopLevelBuild)getTopLevelBuild();

		if ((topLevelBuild == null) || topLevelBuild.fromArchive) {
			return;
		}

		String consoleText = topLevelBuild.getConsoleText();

		if (consoleText.contains(getReinvokedMessage())) {
			reset();

			update();
		}
	}

	protected void downloadSampleURL(
		String path, boolean required, String url, String urlSuffix) {

		String urlString = url + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		urlSuffix = JenkinsResultsParserUtil.fixFileName(urlSuffix);

		String content = null;

		try {
			content = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
			if (required) {
				throw new RuntimeException(
					"Unable to download sample " + urlString, ioe);
			}
			else {
				return;
			}
		}

		try {
			writeArchiveFile(content, path + "/" + urlSuffix);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to write file", ioe);
		}
	}

	protected void findDownstreamBuilds() {
		List<String> foundDownstreamBuildURLs = new ArrayList<>(
			findDownstreamBuildsInConsoleText());

		JSONObject buildJSONObject;

		try {
			buildJSONObject = getBuildJSONObject("runs[number,url]");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if ((buildJSONObject != null) && buildJSONObject.has("runs")) {
			JSONArray runsJSONArray = buildJSONObject.getJSONArray("runs");

			if (runsJSONArray != null) {
				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

					if (runJSONObject.getInt("number") == _buildNumber) {
						String url = runJSONObject.getString("url");

						if (!hasBuildURL(url) &&
							!foundDownstreamBuildURLs.contains(url)) {

							foundDownstreamBuildURLs.add(url);
						}
					}
				}
			}
		}

		addDownstreamBuilds(
			foundDownstreamBuildURLs.toArray(
				new String[foundDownstreamBuildURLs.size()]));
	}

	protected List<String> findDownstreamBuildsInConsoleText() {
		List<String> foundDownstreamBuildURLs = new ArrayList<>();

		if (getBuildURL() != null) {
			String consoleText = getConsoleText();

			Matcher downstreamBuildURLMatcher =
				downstreamBuildURLPattern.matcher(
					consoleText.substring(_consoleReadCursor));

			_consoleReadCursor = consoleText.length();

			while (downstreamBuildURLMatcher.find()) {
				String url = downstreamBuildURLMatcher.group("url");

				String reinvocationMarker = url + " restarted at ";

				int reinvocationIndex = consoleText.indexOf(reinvocationMarker);

				if (reinvocationIndex != -1) {
					url = consoleText.substring(
						reinvocationIndex + reinvocationMarker.length(),
						consoleText.indexOf(
							".\n",
							reinvocationIndex + reinvocationMarker.length()));
				}

				if (!foundDownstreamBuildURLs.contains(url)) {
					foundDownstreamBuildURLs.add(url);
				}
			}
		}

		return foundDownstreamBuildURLs;
	}

	protected JSONObject getBuildJSONObject(String tree) {
		if (getBuildURL() == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(JenkinsResultsParserUtil.getLocalURL(getBuildURL()));
		sb.append("/api/json?pretty");

		if (tree != null) {
			sb.append("&tree=");
			sb.append(tree);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(sb.toString(), false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}
	}

	protected String getBuildMessage() {
		if (jobName != null) {
			String status = getStatus();

			StringBuilder sb = new StringBuilder();

			sb.append("Build \"");
			sb.append(jobName);
			sb.append("\"");

			if (status.equals("completed")) {
				sb.append(" completed at ");
				sb.append(getBuildURL());
				sb.append(". ");
				sb.append(getResult());

				return sb.toString();
			}

			if (status.equals("missing")) {
				sb.append(" is missing ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("queued")) {
				sb.append(" is queued at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("running")) {
				if (badBuildNumbers.size() > 0) {
					sb.append(" ");

					List<String> badBuildURLs = getBadBuildURLs();

					sb.append(badBuildURLs.get(badBuildNumbers.size() - 1));

					sb.append(" restarted at ");
				}
				else {
					sb.append(" started at ");
				}

				sb.append(getBuildURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("starting")) {
				sb.append(" invoked at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			throw new RuntimeException("Unknown status: " + status);
		}

		return "";
	}

	protected JSONArray getBuildsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			getJobURL() + "/api/json?tree=builds[actions[parameters" +
				"[name,type,value]],building,duration,number,result,url]",
			false);

		return jsonObject.getJSONArray("builds");
	}

	protected ExecutorService getExecutorService() {
		return null;
	}

	protected Set<String> getJobParameterNames() {
		JSONObject jsonObject;

		try {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				getJobURL() + "/api/json?tree=actions[parameterDefinitions" +
					"[name,type,value]]");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}

		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		JSONObject firstActionJSONObject = actionsJSONArray.getJSONObject(0);

		JSONArray parameterDefinitionsJSONArray =
			firstActionJSONObject.getJSONArray("parameterDefinitions");

		Set<String> parameterNames = new HashSet<>(
			parameterDefinitionsJSONArray.length());

		for (int i = 0; i < parameterDefinitionsJSONArray.length(); i++) {
			JSONObject parameterDefinitionJSONObject =
				parameterDefinitionsJSONArray.getJSONObject(i);

			String type = parameterDefinitionJSONObject.getString("type");

			if (type.equals("StringParameterDefinition")) {
				parameterNames.add(
					parameterDefinitionJSONObject.getString("name"));
			}
		}

		return parameterNames;
	}

	protected Map<String, String> getParameters(JSONArray jsonArray)
		throws Exception {

		Map<String, String> parameters = new HashMap<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.opt("value") instanceof String) {
				String name = jsonObject.getString("name");
				String value = jsonObject.getString("value");

				if (!value.isEmpty()) {
					parameters.put(name, value);
				}
			}
		}

		return parameters;
	}

	protected Map<String, String> getParameters(JSONObject buildJSONObject)
		throws Exception {

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			return new HashMap<>();
		}

		JSONObject jsonObject = actionsJSONArray.getJSONObject(0);

		if (jsonObject.has("parameters")) {
			JSONArray parametersJSONArray = jsonObject.getJSONArray(
				"parameters");

			return getParameters(parametersJSONArray);
		}

		return new HashMap<>();
	}

	protected JSONObject getQueueItemJSONObject() throws Exception {
		JSONArray queueItemsJSONArray = getQueueItemsJSONArray();

		for (int i = 0; i < queueItemsJSONArray.length(); i++) {
			JSONObject queueItemJSONObject = queueItemsJSONArray.getJSONObject(
				i);

			JSONObject taskJSONObject = queueItemJSONObject.getJSONObject(
				"task");

			String queueItemName = taskJSONObject.getString("name");

			if (!queueItemName.equals(jobName)) {
				continue;
			}

			if (_parameters.equals(getParameters(queueItemJSONObject))) {
				return queueItemJSONObject;
			}
		}

		return null;
	}

	protected JSONArray getQueueItemsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"http://" + master +
				"/queue/api/json?tree=items[actions[parameters" +
					"[name,value]],task[name,url]]",
			false);

		return jsonObject.getJSONArray("items");
	}

	protected String getReinvokedMessage() {
		StringBuffer sb = new StringBuffer();

		sb.append("Reinvoked: ");
		sb.append(getBuildURL());
		sb.append(" at ");
		sb.append(getInvocationURL());

		return sb.toString();
	}

	protected String getRepositoryType(String repositoryName) {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			StringBuilder sb = new StringBuilder();

			sb.append("repository.type[");
			sb.append(repositoryName);
			sb.append("]");

			String repositoryType = buildProperties.getProperty(sb.toString());

			if ((repositoryType == null) || repositoryType.isEmpty()) {
				throw new RuntimeException(
					"Unable to find repository type for repository " +
						repositoryName);
			}

			return repositoryType;
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties");
		}
	}

	protected JSONObject getRunningBuildJSONObject() throws Exception {
		JSONArray buildsJSONArray = getBuildsJSONArray();

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			Map<String, String> parameters = getParameters();

			if (parameters.equals(getParameters(buildJSONObject)) &&
				!badBuildNumbers.contains(buildJSONObject.getInt("number"))) {

				return buildJSONObject;
			}
		}

		return null;
	}

	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		return getParameterValue("JSON_MAP_URL");
	}

	protected String getStopPropertiesTempMapURL() {
		return null;
	}

	protected Map<String, String> getTempMap(String tempMapName) {
		JSONObject tempMapJSONObject = null;

		String tempMapURL = getTempMapURL(tempMapName);

		if (tempMapURL == null) {
			return Collections.emptyMap();
		}

		try {
			tempMapJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(tempMapURL), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
		}

		if ((tempMapJSONObject == null) ||
			!tempMapJSONObject.has("properties")) {

			return Collections.emptyMap();
		}

		JSONArray propertiesJSONArray = tempMapJSONObject.getJSONArray(
			"properties");

		Map<String, String> tempMap = new HashMap<>(
			propertiesJSONArray.length());

		for (int i = 0; i < propertiesJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertiesJSONArray.getJSONObject(
				i);

			String key = propertyJSONObject.getString("name");
			String value = propertyJSONObject.optString("value");

			if ((value != null) && !value.isEmpty()) {
				tempMap.put(key, value);
			}
		}

		return tempMap;
	}

	protected String getTempMapURL(String tempMapName) {
		if (tempMapName.equals("start.properties")) {
			return getStartPropertiesTempMapURL();
		}

		if (tempMapName.equals("stop.properties")) {
			return getStopPropertiesTempMapURL();
		}

		return null;
	}

	protected boolean isParentBuildRoot() {
		if (_parentBuild == null) {
			return false;
		}

		if ((_parentBuild.getParentBuild() == null) &&
			(_parentBuild instanceof TopLevelBuild)) {

			return true;
		}

		return false;
	}

	protected void loadParametersFromBuildJSONObject() {
		if (getBuildURL() == null) {
			return;
		}

		JSONObject buildJSONObject = getBuildJSONObject(
			"actions[parameters[*]]");

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			_parameters = new HashMap<>(0);

			return;
		}

		JSONObject actionJSONObject = actionsJSONArray.getJSONObject(0);

		if (actionJSONObject.has("parameters")) {
			JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
				"parameters");

			_parameters = new HashMap<>(parametersJSONArray.length());

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				Object value = parameterJSONObject.opt("value");

				if (value instanceof String) {
					if (!value.toString().isEmpty()) {
						_parameters.put(
							parameterJSONObject.getString("name"),
							value.toString());
					}
				}
			}

			return;
		}

		_parameters = Collections.emptyMap();
	}

	protected void loadParametersFromQueryString(String queryString) {
		Set<String> jobParameterNames = getJobParameterNames();

		for (String parameter : queryString.split("&")) {
			String[] nameValueArray = parameter.split("=");

			if ((nameValueArray.length == 2) &&
				jobParameterNames.contains(nameValueArray[0])) {

				_parameters.put(nameValueArray[0], nameValueArray[1]);
			}
		}
	}

	protected void reset() {
		result = null;

		badBuildNumbers.add(getBuildNumber());

		setBuildNumber(-1);

		downstreamBuilds.clear();

		_consoleReadCursor = 0;

		setStatus("starting");
	}

	protected void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;

		setStatus("running");

		if (_buildNumber != -1) {
			checkForReinvocation();
		}
	}

	protected void setBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(
				"Unable to decode " + buildURL, uee);
		}

		try {
			String archiveMarkerContent = JenkinsResultsParserUtil.toString(
				buildURL + "/archive-marker", false, 0, 0, 0);

			if ((archiveMarkerContent != null) &&
				!archiveMarkerContent.isEmpty()) {

				fromArchive = true;
			}
			else {
				fromArchive = false;
			}
		}
		catch (IOException ioe) {
			fromArchive = false;
		}

		Matcher matcher = buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			matcher = archiveBuildURLPattern.matcher(buildURL);

			if (!matcher.find()) {
				throw new IllegalArgumentException(
					"Invalid build URL " + buildURL);
			}

			archiveName = matcher.group("archiveName");
		}

		setJobName(matcher.group("jobName"));
		master = matcher.group("master");

		_buildNumber = Integer.parseInt(matcher.group("buildNumber"));

		loadParametersFromBuildJSONObject();

		_consoleReadCursor = 0;

		setStatus("running");

		checkForReinvocation();
	}

	protected void setInvocationURL(String invocationURL) {
		if (getBuildURL() == null) {
			try {
				invocationURL = JenkinsResultsParserUtil.decode(invocationURL);
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + invocationURL, uee);
			}

			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				invocationURL);

			if (!invocationURLMatcher.find()) {
				throw new RuntimeException("Invalid invocation URL");
			}

			setJobName(invocationURLMatcher.group("jobName"));
			master = invocationURLMatcher.group("master");

			loadParametersFromQueryString(invocationURL);

			setStatus("starting");
		}
	}

	protected void setJobName(String jobName) {
		this.jobName = jobName;

		Matcher matcher = jobNamePattern.matcher(jobName);

		if (matcher.find()) {
			branchName = matcher.group("branchName");

			return;
		}

		branchName = "master";
	}

	protected void setStatus(String status) {
		if (((status == null) && (_status != null)) ||
			!status.equals(_status)) {

			_status = status;

			statusModifiedTime = System.currentTimeMillis();

			if (isParentBuildRoot()) {
				System.out.println(getBuildMessage());
			}
		}
	}

	protected void writeArchiveFile(String content, String path)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append(
			JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE.substring(
				"file:".length()));
		sb.append("/");
		sb.append(path);

		JenkinsResultsParserUtil.write(sb.toString(), replaceBuildURL(content));
	}

	protected static final Pattern archiveBuildURLPattern = Pattern.compile(
		"($\\{dependencies\\.url\\}|file:|http://).*/(?<archiveName>[^/]+)/" +
			"(?<master>[^/]+)/+(?<jobName>[^/]+).*/(?<buildNumber>\\d+)/?");
	protected static final Pattern buildURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>" +
			"\\d+)/?");
	protected static final Pattern downstreamBuildURLPattern = Pattern.compile(
		"[\\'\\\"].*[\\'\\\"] started at (?<url>.+)\\.");
	protected static final Pattern invocationURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/" +
			"buildWithParameters\\?(?<queryString>.*)");
	protected static final Pattern jobNamePattern = Pattern.compile(
		"(?<baseJob>[^\\(]+)\\((?<branchName>[^\\)]+)\\)");
	protected static final String tempMapBaseURL =
		"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/map/";

	protected String archiveName;
	protected List<Integer> badBuildNumbers = new ArrayList<>();
	protected String branchName;
	protected List<Build> downstreamBuilds = new ArrayList<>();
	protected boolean fromArchive;
	protected String jobName;
	protected String master;
	protected String repositoryName;
	protected String result;
	protected long statusModifiedTime;

	private int _buildNumber = -1;
	private int _consoleReadCursor;
	private Map<String, String> _parameters = new HashMap<>();
	private final Build _parentBuild;
	private String _status;

}