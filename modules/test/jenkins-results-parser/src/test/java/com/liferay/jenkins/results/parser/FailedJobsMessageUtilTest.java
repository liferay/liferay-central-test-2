package com.liferay.jenkins.results.parser;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.tools.ant.Project;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class FailedJobsMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"23-of-46", "2251",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-17",
			false);
		downloadSample(
			"2-of-3888", "3415",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-18",
			false);
		downloadSample(
			"6-of-6", "1287",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19",
			false);
		downloadSample(
			"0-of-23-javac-output-file", "2269",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19",
			true);
	}
	
	@Test
	public void testGetFailedJobsMessage() throws Exception {
		assertSamples();
	}
	
	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
		downloadSampleURL(sampleDir, url, "/logText/progressiveText");
		downloadSampleURL(sampleDir, url, "/testReport/api/json");
		
		generateJavacOutputFile(sampleDir);

		downloadSampleAxisURLs(sampleDir, new File(sampleDir, "/api/json"));
	}

	protected void downloadSample(
			String sampleKey, String buildNumber, String jobName,
			String hostName, boolean generateJavacOutputFile)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/${buildNumber}/";

		urlString = replaceToken(urlString, "buildNumber", buildNumber);
		urlString = replaceToken(urlString, "hostName", hostName);
		urlString = replaceToken(urlString, "jobName", jobName);

		URL url = createURL(urlString);
		
		downloadSample(sampleKey + "-" + jobName, url);
		
		if (!generateJavacOutputFile) {
			File javacOutputFile = 
				new File(dependenciesDir, 
					sampleKey + "-" + jobName + "/" + _JAVAC_OUTPUT_FILE_NAME);
			if (javacOutputFile.exists()) {
				javacOutputFile.delete();
			}
		}

	}

	protected void downloadSampleAxisURLs(File sampleDir, File jobJSONFile)
		throws Exception {

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(jobJSONFile)));

		int number = jobJSONObject.getInt("number");

		JSONArray runsJSONArray = jobJSONObject.getJSONArray("runs");

		for (int i = 0; i < runsJSONArray.length(); i++) {
			JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

			if (number != runJSONObject.getInt("number")) {
				continue;
			}

			URL runURL = createURL(URLDecoder.decode(
				runJSONObject.getString("url"), "UTF-8"));

			File runDir = new File(sampleDir, "run-" + i + "/" + number + "/");

			downloadSampleURL(runDir, runURL, "/api/json");
			downloadSampleURL(runDir, runURL, "/logText/progressiveText");
			downloadSampleURL(runDir, runURL, "/testReport/api/json");

			runJSONObject.put("url", toURLString(runDir));
		}

		write(jobJSONFile, jobJSONObject.toString(4));
	}
	
	@Override
	protected String getMessage(String urlString) throws Exception {
		Project project = _getProject(
			urlString, _urlToFile(urlString).getPath());
		
		FailedJobsMessageUtil.getFailedJobsMessage(project);
		
		return project.getProperty("report.html.content");
	}

	protected void generateJavacOutputFile(File caseDir) throws Exception {
		File javacOutputFile = new File(caseDir, _JAVAC_OUTPUT_FILE_NAME);
		
		int maxLineCount = 4000;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < maxLineCount; i++) {
			sb.append("line: ");
			
			sb.append(i+1);
			
			sb.append(" of " );
			
			sb.append(maxLineCount);
			
			sb.append("\n");
		}
		
		write(javacOutputFile, sb.toString());
		
	}
	
	private File _urlToFile(String urlString) throws Exception {
		if (!urlString.startsWith("file:")) {
			throw new Exception("Only file URLs are allowed.");
		}
		String localURL = JenkinsResultsParserUtil.getLocalURL(urlString);
		
		return new File(localURL.substring("file:".length()));
	}
	
	private Project _getProject(
		String buildURLString, String topLevelSharedDir) {
		
		Project project = new Project();

		project.setProperty("build.url", buildURLString);
		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("repository", "junit-repository");
		project.setProperty("top.level.shared.dir", topLevelSharedDir);
		
		return project;
	}
	
	private static final String _JAVAC_OUTPUT_FILE_NAME = "javac.output.txt";

}
