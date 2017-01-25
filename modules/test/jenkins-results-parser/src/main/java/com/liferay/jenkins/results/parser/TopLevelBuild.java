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

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	@Override
	public void archive(String archiveName) {
		super.archive(archiveName);

		if (getParentBuild() == null) {
			Properties archiveProperties = new Properties();

			archiveProperties.setProperty(
				"top.level.build.url", replaceBuildURL(getBuildURL()));

			try {
				StringWriter sw = new StringWriter();

				archiveProperties.store(sw, null);

				writeArchiveFile(
					sw.toString(), archiveName + "/archive.properties");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to write archive properties");
			}
		}

		try {
			writeArchiveFile(
				getJenkinsReport(), getArchivePath() + "/jenkins-report.html");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to archive Jenkins report", ioe);
		}
	}

	@Override
	public String getDisplayName() {
		String displayName = super.getDisplayName();

		if (getParentBuild() != null) {
			displayName += "/" + getParameterValue("JENKINS_JOB_VARIANT");
		}

		return displayName;
	}

	@Override
	public Element getGitHubMessageElement() {
		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		if (getParentBuild() == null) {
			return getTopGitHubMessageElement();
		}

		return super.getGitHubMessageElement();
	}

	public Map<String, String> getGitRepositoryDetailsTempMap(
		String repositoryName) {

		String repositoryType = getRepositoryType(repositoryName);

		String tempMapName = "git." + repositoryType + ".properties";

		return getTempMap(tempMapName);
	}

	public String getJenkinsReport() {
		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(getJenkinsReportURL()));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get Jenkins report", ioe);
		}
	}

	public String getJenkinsReportURL() {
		if (fromArchive) {
			return getBuildURL() + "/jenkins-report.html";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://");
		sb.append(getMaster());
		sb.append(".liferay.com/");
		sb.append("userContent/jobs/");
		sb.append(getJobName());
		sb.append("/builds/");
		sb.append(getBuildNumber());
		sb.append("/jenkins-report.html");

		return sb.toString();
	}

	@Override
	public String getStatusReport(int indentSize) {
		String statusReport = super.getStatusReport(indentSize);

		if (getDownstreamBuildCount(null) > 0) {
			while (statusReport.endsWith("\n")) {
				statusReport = statusReport.substring(
					0, statusReport.length() - 1);
			}

			statusReport += " / ";
		}

		return statusReport + "Update took " + _updateDuration +
			" milliseconds.\n";
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		return null;
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;
	}

	protected TopLevelBuild(String url) {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected void archiveJSON() {
		super.archiveJSON();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String repositoryTypes = buildProperties.getProperty(
				"repository.types");

			for (String repositoryType : repositoryTypes.split(",")) {
				try {
					JSONObject gitRepositoryDetailsJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							getGitRepositoryDetailsPropertiesTempMapURL(
								repositoryType));

					Set<?> set = gitRepositoryDetailsJSONObject.keySet();

					if (set.isEmpty()) {
						continue;
					}

					writeArchiveFile(
						gitRepositoryDetailsJSONObject.toString(4),
						getArchivePath() + "/git." + repositoryType +
							".properties.json");
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to create git." + repositoryType +
							".properties.json",
						ioe);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}
	}

	protected Element getBaseBranchDetailsElement() {
		String baseBranchURL =
			"https://github.com/liferay/" + getRepositoryName() + "/tree/" +
				getBranchName();

		String repositoryName = getRepositoryName();

		String repositorySHA = null;

		if (!repositoryName.equals("liferay-jenkins-ee") &&
			repositoryName.endsWith("-ee")) {

			repositorySHA = getRepositorySHA(
				repositoryName.substring(0, repositoryName.length() - 3));
		}
		else {
			repositorySHA = getRepositorySHA(repositoryName);
		}

		String repositoryCommitURL =
			"https://github.com/liferay/" + repositoryName + "/commit/" +
				repositorySHA;

		Element baseBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(baseBranchURL, getBranchName()));

		if (repositorySHA != null) {
			Dom4JUtil.addToElement(
				baseBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ", Dom4JUtil.getNewAnchorElement(
					repositoryCommitURL, repositorySHA));
		}

		return baseBranchDetailsElement;
	}

	protected Element getBuildTimeElement() {
		return Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(getDuration()));
	}

	protected Element getDownstreamGitHubMessageElement() {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewAnchorElement(
				getBuildURL(), null, getDisplayName()));

		if (result.equals("ABORTED")) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));
		}

		if (result.equals("FAILURE")) {
			Element failureMessageElement = getFailureMessageElement();

			if (failureMessageElement != null) {
				messageElement.add(failureMessageElement);
			}
		}

		return messageElement;
	}

	@Override
	protected ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(20);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		if (getParentBuild() == null) {
			return _FAILURE_MESSAGE_GENERATORS;
		}

		return super.getFailureMessageGenerators();
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		int failCount = getDownstreamBuildCount(null) - successCount + 1;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement("h6", null, "Job Results:",
			Dom4JUtil.getNewElement(
				"p", null, Integer.toString(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Jobs", " Job"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				Integer.toString(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Jobs", " Job"),
				" Failed.")));
	}

	protected String getGitRepositoryDetailsPropertiesTempMapURL(
		String repositoryType) {

		if (fromArchive) {
			StringBuilder sb = new StringBuilder();

			sb.append(getBuildURL());
			sb.append("git.");
			sb.append(repositoryType);
			sb.append(".properties.json");

			return sb.toString();
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		sb.append(topLevelBuild.getMaster());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());

		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());

		sb.append("/git.");
		sb.append(repositoryType);
		sb.append(".properties");

		return sb.toString();
	}

	protected Element getJobSummaryListElement() {
		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

		for (Build build : builds) {
			Element jobSummaryListItemElement = Dom4JUtil.getNewElement(
				"li", jobSummaryListElement);

			jobSummaryListItemElement.add(
				build.getGitHubMessageBuildAnchorElement());
		}

		return jobSummaryListElement;
	}

	protected Element getMoreDetailsElement() {
		Element moreDetailsElement = Dom4JUtil.getNewElement(
			"h5", null, "For more details click ",
			Dom4JUtil.getNewAnchorElement(getJenkinsReportURL(), "here"), ".");

		return moreDetailsElement;
	}

	protected Element getResultElement() {
		Element resultElement = Dom4JUtil.getNewElement("h1");

		String result = getResult();

		if (!result.equals("SUCCESS")) {
			resultElement.addText("Some tests FAILED.");
		}
		else {
			resultElement.addText("All tests PASSED.");
		}

		return resultElement;
	}

	@Override
	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);
		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append("start.properties");

		return sb.toString();
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop.properties.json";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);
		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append("stop.properties");

		return sb.toString();
	}

	@Override
	protected String getTempMapURL(String tempMapName) {
		String tempMapURL = super.getTempMapURL(tempMapName);

		if (tempMapURL != null) {
			return tempMapURL;
		}

		Matcher matcher = gitRepositoryTempMapNamePattern.matcher(tempMapName);

		if (matcher.find()) {
			return getGitRepositoryDetailsPropertiesTempMapURL(
				matcher.group("repositoryType"));
		}

		return null;
	}

	protected Element getTopGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement(
			"html", null, getResultElement(), getBuildTimeElement(),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement(),
			Dom4JUtil.getNewElement("h4", null, "Job Summary:"),
			getJobSummaryListElement(), getMoreDetailsElement());

		String result = getResult();

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				rootElement, Dom4JUtil.getNewElement("hr"),
				Dom4JUtil.getNewElement("h4", null, "Failed Jobs:"));

			Element failedJobsOrderedListElement = Dom4JUtil.getNewElement(
				"ol", rootElement,
				Dom4JUtil.getNewElement(
					"li", null, super.getGitHubMessageElement()));

			int failureCount = 1;

			List<Element> failureElements = new ArrayList<>();

			for (Build downstreamBuild : getDownstreamBuilds(null)) {
				String downstreamBuildResult = downstreamBuild.getResult();

				if (downstreamBuildResult.equals("SUCCESS")) {
					continue;
				}

				Element failureElement =
					downstreamBuild.getGitHubMessageElement();

				if (isHighPriorityBuildFailureElement(failureElement)) {
					failureElements.add(0, failureElement);

					continue;
				}

				failureElements.add(downstreamBuild.getGitHubMessageElement());
			}

			for (Element failureElement : failureElements) {
				Element failedJobsListItemElement = Dom4JUtil.getNewElement(
					"li", failedJobsOrderedListElement);

				if (failureCount == 5) {
					failedJobsListItemElement.addText("...");

					break;
				}

				failedJobsListItemElement.add(failureElement);

				failureCount++;
			}

			String jobName = getJobName();

			if (jobName.contains("pullrequest")) {
				StringBuilder sb = new StringBuilder();

				sb.append("https://test-1-1.liferay.com/job/");
				sb.append(jobName.replace("pullrequest", "upstream"));

				try {
					JenkinsResultsParserUtil.toString(
						JenkinsResultsParserUtil.getLocalURL(sb.toString()),
						false, 0, 0, 0);

					Dom4JUtil.addToElement(
						Dom4JUtil.getNewElement("h5", rootElement),
						"For upstream results, click ",
						Dom4JUtil.getNewAnchorElement(sb.toString(), "here"),
						".");
				}
				catch (IOException ioe) {
					System.out.println("No upstream build detected.");
				}
			}
		}

		return rootElement;
	}

	protected String getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if ((upstreamBranchSHA == null) || upstreamBranchSHA.isEmpty()) {
			Map<String, String> startPropertiesTempMap =
				getStartPropertiesTempMap();

			upstreamBranchSHA = startPropertiesTempMap.get(
				"GITHUB_UPSTREAM_BRANCH_SHA");
		}

		return upstreamBranchSHA;
	}

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<repositoryType>.*)\\.properties");

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new PoshiValidationFailureMessageGenerator(),
			new DownstreamFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};

	private long _updateDuration;

}