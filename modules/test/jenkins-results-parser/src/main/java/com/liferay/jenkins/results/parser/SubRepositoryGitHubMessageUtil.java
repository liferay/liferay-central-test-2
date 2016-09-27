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

import org.apache.tools.ant.Project;

/**
 * @author Leslie Wong
 */
public class SubrepositoryGitHubMessageUtil {

	public static void getGitHubMessage(Project project) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("<h1>");
		sb.append(project.getProperty("top.level.result.message"));
		sb.append("</h1>");

		sb.append("<p>Build Time: ");
		sb.append(project.getProperty("top.level.build.time"));
		sb.append("</p>");

		String rebaseBranchGitCommit = project.getProperty(
			"rebase.branch.git.commit");

		if (!rebaseBranchGitCommit.equals("")) {
			sb.append("<h4>Base Branch:</h4>");
			sb.append("<p>Branch Name: ");
			sb.append("<a href=\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/tree/");
			sb.append(project.getProperty("branch.name"));
			sb.append("\">");
			sb.append(project.getProperty("branch.name"));
			sb.append("</a><br />");
			sb.append("Branch GIT ID: <a href=\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/commit/");
			sb.append(rebaseBranchGitCommit);
			sb.append("\">");
			sb.append(rebaseBranchGitCommit);
			sb.append("</a></p>");
		}

		String buildURL = project.getProperty("build.url");

		String progressiveText = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"),
			false);

		String[] consoleSnippets = progressiveText.split(
			"Executing subrepository task ");

		if (consoleSnippets.length > 1) {
			sb.append("<h4>Task Summary:</h4>");
			sb.append("<ul>");

			for (String consoleSnippet : consoleSnippets) {
				sb.append("<li><strong><a href=\"");
				sb.append(project.getProperty("top.level.shared.dir.url"));
				sb.append("/");

				String taskName = consoleSnippet.substring(
					0, consoleSnippet.indexOf("\n"));

				JenkinsResultsParserUtil.write(
					new File(
						project.getProperty("top.level.shared.dir") + "/" +
							taskName + ".log"),
					consoleSnippet);

				sb.append(taskName);
				sb.append(".log");
				sb.append("\">");
				sb.append(taskName);
				sb.append("</a></strong> ");
				sb.append("- ");

				SubrepositoryTask subrepositoryTask = _getSubrepositoryTask(
					buildURL, consoleSnippet);

				sb.append(_getResult(subrepositoryTask));
				sb.append("</li>");
			}

			sb.append("</ul>");
		}
		else {
			sb.append(FailureMessageUtil.getFailureMessage(project, buildURL));
		}

		sb.append("<h5>For more details click <a href=\"");
		sb.append(buildURL);
		sb.append("\">here</a>.</h5>");

		project.setProperty("report.html.content", sb.toString());
	}

	private static String _getResult(SubrepositoryTask subrepositoryTask)
		throws Exception {

		String result = subrepositoryTask.getResult();

		if (result.equals("SUCCESS")) {
			return " :white_check_mark:";
		}

		if (result.equals("ABORTED")) {
			return " :no_entry:" + subrepositoryTask.getGitHubMessage();
		}

		if (result.equals("FAILURE")) {
			return " :x:" + subrepositoryTask.getGitHubMessage();
		}

		return "";
	}

	private static SubrepositoryTask _getSubrepositoryTask(
			String buildURL, String console)
		throws Exception {

		if (console.contains(
				"A report with all the test results can be found at " +
					"test-results/html/index.html")) {

			return new SubrepositoryTaskReport(buildURL);
		}
		else {
			return new SubrepositoryTaskNoReport(console);
		}
	}

}