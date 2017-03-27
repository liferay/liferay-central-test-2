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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteConfig;

/**
 * @author Peter Yoo
 */
public class LocalGitSyncUtil {

	public static void deleteCacheBranch(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException {

		List<RemoteConfig> localGitRemoteConfigs = null;

		try {
			localGitRemoteConfigs = getLocalGitRemoteConfigs(
				gitWorkingDirectory);

			deleteCacheBranch(
				getCacheBranchName(
					receiverUsername, senderUsername, senderBranchSha,
					upstreamBranchSha),
				gitWorkingDirectory, localGitRemoteConfigs);
		}
		finally {
			if (localGitRemoteConfigs != null) {
				gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
			}
		}
	}

	public static List<RemoteConfig> getLocalGitRemoteConfigs(
			GitWorkingDirectory gitWorkingDirectory)
		throws GitAPIException {

		List<String> localGitRemoteURLs = getLocalGitRemoteURLs(
			gitWorkingDirectory);

		List<RemoteConfig> localGitRemoteConfigs = new ArrayList<>(
			localGitRemoteURLs.size());

		for (String localGitRemoteURL : localGitRemoteURLs) {
			String url = localGitRemoteURL.replace(
				"${username}", gitWorkingDirectory.getRepositoryUsername());

			url = url.replace(
				"${repository-name}", gitWorkingDirectory.getRepositoryName());

			String localGitRemoteName =
				"local-git-remote-" +
					localGitRemoteURLs.indexOf(localGitRemoteURL);

			RemoteConfig remoteConfig = gitWorkingDirectory.getRemoteConfig(
				localGitRemoteName);

			if ((remoteConfig == null) ||
				!url.equals(GitWorkingDirectory.getRemoteURL(remoteConfig))) {

				remoteConfig = gitWorkingDirectory.addRemote(
					true, localGitRemoteName, url);
			}

			localGitRemoteConfigs.add(remoteConfig);
		}

		return localGitRemoteConfigs;
	}

	public static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException, IOException {

		return synchronizeToLocalGit(
			gitWorkingDirectory, receiverUsername, 0, senderBranchName,
			senderUsername, senderBranchSha, upstreamBranchName,
			upstreamBranchSha);
	}

	protected static void cacheBranch(
			GitWorkingDirectory gitWorkingDirectory, String localBranchName,
			RemoteConfig remoteConfig, long timestamp)
		throws GitAPIException {

		gitWorkingDirectory.pushToRemote(remoteConfig);

		gitWorkingDirectory.pushToRemote(
			JenkinsResultsParserUtil.combine(
				localBranchName, "-", Long.toString(timestamp)),
			remoteConfig);
	}

	protected static void cacheBranches(
			final GitWorkingDirectory gitWorkingDirectory,
			final String localBranchName,
			List<RemoteConfig> localGitRemoteConfigs,
			final String upstreamBranchName, final String upstreamUsername)
		throws GitAPIException {

		final long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							cacheBranch(
								gitWorkingDirectory, localBranchName,
								localGitRemoteConfig, start);

							if (upstreamUsername.equals("liferay")) {
								gitWorkingDirectory.pushToRemote(
									upstreamBranchName, upstreamBranchName,
									localGitRemoteConfig);
							}
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(30, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Cache branches pushed up in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void copyUpstreamRefsToHeads(
			GitWorkingDirectory gitWorkingDirectory)
		throws IOException {

		File gitDir = gitWorkingDirectory.getGitDirectory();

		File headsDir = new File(gitDir, "refs/heads");
		File upstreamDir = new File(gitDir, "refs/remotes/upstream-temp");

		for (File file : upstreamDir.listFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Copying ", headsDir.getPath(), " to ",
					upstreamDir.getPath()));
			JenkinsResultsParserUtil.copy(
				file, new File(headsDir, file.getName()));
		}
	}

	protected static void deleteCacheBranch(
			final String cacheBranchName,
			final GitWorkingDirectory gitWorkingDirectory,
			List<RemoteConfig> localGitRemoteConfigs)
		throws GitAPIException {

		long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							deleteRemoteCacheBranch(
								cacheBranchName, gitWorkingDirectory,
								localGitRemoteConfig);
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Expired cache branches deleted in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void deleteExpiredCacheBranches(
			final GitWorkingDirectory gitWorkingDirectory,
			List<RemoteConfig> localGitRemoteConfigs)
		throws GitAPIException {

		final long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(
			localGitRemoteConfigs.size());

		for (final RemoteConfig localGitRemoteConfig : localGitRemoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							deleteExpiredCacheBranches(
								gitWorkingDirectory, localGitRemoteConfig,
								start);
						}
						catch (GitAPIException gapie) {
							throw new RuntimeException(gapie);
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Expired cache branches deleted in " +
				JenkinsResultsParserUtil.toDurationString(duration));
	}

	protected static void deleteExpiredCacheBranches(
			GitWorkingDirectory gitWorkingDirectory, RemoteConfig remoteConfig,
			long timestamp)
		throws GitAPIException {

		int branchCount = 0;
		int deleteCount = 0;
		long oldestBranchAge = Long.MIN_VALUE;

		List<String> remoteRepositoryBranchNames = null;

		try {
			remoteRepositoryBranchNames =
				gitWorkingDirectory.getRemoteBranchNames(remoteConfig);
		}
		catch (GitAPIException gapie) {
			gapie.printStackTrace();

			System.out.println(
				"Unable to get remote repository branch names from " +
					GitWorkingDirectory.getRemoteURL(remoteConfig));

			return;
		}

		for (String remoteBranchName : remoteRepositoryBranchNames) {
			Matcher matcher = _cacheTimestampBranchPattern.matcher(
				remoteBranchName);

			if (matcher.matches()) {
				branchCount++;

				long remoteBranchTimestamp = Long.parseLong(
					matcher.group("timestamp"));

				long branchAge = timestamp - remoteBranchTimestamp;

				if (branchAge > _BRANCH_EXPIRE_AGE_MILLIS) {
					try {
						deleteRemoteRepositoryCacheBranch(
							gitWorkingDirectory, matcher.group("name"),
							remoteConfig);
						deleteRemoteRepositoryCacheBranch(
							gitWorkingDirectory, remoteBranchName,
							remoteConfig);

						deleteCount++;
					}
					catch (GitAPIException gapie) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Unable to delete cache branch ",
								remoteBranchName, " from ",
								GitWorkingDirectory.getRemoteURL(
									remoteConfig)));

						gapie.printStackTrace();
					}
				}
				else {
					oldestBranchAge = Math.max(oldestBranchAge, branchAge);
				}
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Found ", Integer.toString(branchCount), " cache branches on ",
				GitWorkingDirectory.getRemoteURL(remoteConfig), " ",
				Integer.toString(deleteCount), " were deleted. ",
				Integer.toString(branchCount - deleteCount),
				" remain. The oldest branch is ",
				JenkinsResultsParserUtil.toDurationString(oldestBranchAge),
				" old."));
	}

	protected static void deleteLocalCacheBranches(
			String excludeBranchName, GitWorkingDirectory gitWorkingDirectory)
		throws GitAPIException {

		for (String localBranchName :
				gitWorkingDirectory.getLocalBranchNames()) {

			if (localBranchName.matches(_cacheBranchRegex) &&
				!localBranchName.equals(excludeBranchName)) {

				gitWorkingDirectory.deleteLocalBranch(localBranchName);
			}
		}
	}

	protected static void deleteRemoteCacheBranch(
			String cacheBranchName, GitWorkingDirectory gitWorkingDirectory,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		for (String remoteBranchName :
				gitWorkingDirectory.getRemoteBranchNames(remoteConfig)) {

			if (!remoteBranchName.startsWith(cacheBranchName)) {
				continue;
			}

			try {
				deleteRemoteRepositoryCacheBranch(
					gitWorkingDirectory, remoteBranchName, remoteConfig);
			}
			catch (GitAPIException gapie) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to delete cache branch ", remoteBranchName,
						" from ",
						GitWorkingDirectory.getRemoteURL(remoteConfig)));

				gapie.printStackTrace();
			}
		}
	}

	protected static void deleteRemoteRepositoryCacheBranch(
			GitWorkingDirectory gitWorkingDirectory, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		if (gitWorkingDirectory.pushToRemote(
				"", remoteBranchName, remoteConfig)) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Deleted ", remoteBranchName, " from ",
					GitWorkingDirectory.getRemoteURL(remoteConfig)));
		}

		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteBranchName, " from ",
					GitWorkingDirectory.getRemoteURL(remoteConfig)));
		}
	}

	protected static String getCacheBranchName(
		String receiverUsername, String senderUsername, String senderSHA,
		String upstreamSHA) {

		return JenkinsResultsParserUtil.combine(
			"cache-", receiverUsername, "-", upstreamSHA, "-", senderUsername,
			"-", senderSHA);
	}

	protected static String getGitHubRemoteURL(
		String repositoryName, String userName) {

		return JenkinsResultsParserUtil.combine(
			"git@github.com:", userName, "/", repositoryName, ".git");
	}

	protected static List<String> getLocalGitRemoteURLs(
		GitWorkingDirectory gitWorkingDirectory) {

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties");
		}

		String gitCacheHostnamesPropertyValue = buildProperties.getProperty(
			"github.cache.hostnames");

		String[] gitCacheHostnames = gitCacheHostnamesPropertyValue.split(",");

		List<String> localGitRemoteURLs = new ArrayList<>(
			gitCacheHostnames.length);

		for (String gitCacheHostname : gitCacheHostnames) {
			if (gitCacheHostname.startsWith("file:") ||
				gitCacheHostname.startsWith("http:")) {

				localGitRemoteURLs.add(gitCacheHostname);

				continue;
			}

			localGitRemoteURLs.add(
				JenkinsResultsParserUtil.combine(
					"git@", gitCacheHostname, ":",
					gitWorkingDirectory.getRepositoryUsername(), "/",
					gitWorkingDirectory.getRepositoryName(), ".git"));
		}

		return localGitRemoteURLs;
	}

	protected static RemoteConfig getRandomRemoteConfig(
		List<RemoteConfig> remoteConfigs) {

		return remoteConfigs.get(
			JenkinsResultsParserUtil.getRandomValue(
				0, remoteConfigs.size() - 1));
	}

	protected static List<String> getRemoteCacheBranchNames(
			GitWorkingDirectory gitWorkingDirectory, RemoteConfig remoteConfig)
		throws GitAPIException {

		List<String> remoteCacheBranchNames = new ArrayList<>();

		List<String> remoteBranchNames =
			gitWorkingDirectory.getRemoteBranchNames(remoteConfig);

		for (String remoteBranchName : remoteBranchNames) {
			if (remoteBranchName.matches(_cacheBranchRegex)) {
				if (hasTimestampBranch(remoteBranchName, remoteBranchNames)) {
					remoteCacheBranchNames.add(remoteBranchName);
				}
				else {
					deleteRemoteCacheBranch(
						remoteBranchName, gitWorkingDirectory, remoteConfig);
				}
			}
		}

		return remoteCacheBranchNames;
	}

	protected static boolean hasTimestampBranch(
		String cacheBranchName, List<String> remoteBranchNames) {

		for (String remoteBranchName : remoteBranchNames) {
			Matcher matcher = _cacheTimestampBranchPattern.matcher(
				remoteBranchName);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	protected static Map<RemoteConfig, Boolean> pushToAllRemotes(
			final GitWorkingDirectory gitWorkingDirectory,
			final String localBranchName, final String remoteBranchName,
			List<RemoteConfig> remoteConfigs)
		throws GitAPIException {

		final long start = System.currentTimeMillis();

		final Map<RemoteConfig, Boolean> resultsMap =
			Collections.synchronizedMap(
				new HashMap<RemoteConfig, Boolean>(remoteConfigs.size()));

		ExecutorService executorService = Executors.newFixedThreadPool(
			remoteConfigs.size());

		for (final RemoteConfig remoteConfig : remoteConfigs) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						try {
							resultsMap.put(
								remoteConfig,
								gitWorkingDirectory.pushToRemote(
									localBranchName, remoteBranchName,
									remoteConfig));
						}
						catch (GitAPIException gapie) {
							System.out.println(
								JenkinsResultsParserUtil.combine(
									"Unable to push ", localBranchName, " to ",
									GitWorkingDirectory.getRemoteURL(
										remoteConfig),
									":", remoteBranchName));
							gapie.printStackTrace();
						}
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(30, TimeUnit.MINUTES);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushed ", localBranchName, " to ", remoteBranchName, " on ",
				Integer.toString(remoteConfigs.size()), " git nodes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));

		return resultsMap;
	}

	protected static String synchronizeToLocalGit(
			GitWorkingDirectory gitWorkingDirectory, String receiverUsername,
			int retryCount, String senderBranchName, String senderUsername,
			String senderBranchSha, String upstreamBranchName,
			String upstreamBranchSha)
		throws GitAPIException, IOException {

		long start = System.currentTimeMillis();

		String originalBranchName = gitWorkingDirectory.getCurrentBranch();

		RemoteConfig senderRemoteConfig = null;
		RemoteConfig upstreamRemoteConfig = null;

		try {
			senderRemoteConfig = gitWorkingDirectory.addRemote(
				true, "sender-temp",
				getGitHubRemoteURL(
					gitWorkingDirectory.getRepositoryName(), senderUsername));
			upstreamRemoteConfig = gitWorkingDirectory.getRemoteConfig(
				"upstream");

			boolean pullRequest = !upstreamBranchSha.equals(senderBranchSha);

			String cacheBranchName = getCacheBranchName(
				receiverUsername, senderUsername, senderBranchSha,
				upstreamBranchSha);

			List<RemoteConfig> localGitRemoteConfigs = null;

			try {
				localGitRemoteConfigs = getLocalGitRemoteConfigs(
					gitWorkingDirectory);

				deleteLocalCacheBranches(cacheBranchName, gitWorkingDirectory);

				deleteExpiredCacheBranches(
					gitWorkingDirectory, localGitRemoteConfigs);

				int randomIndex = JenkinsResultsParserUtil.getRandomValue(
					0, localGitRemoteConfigs.size() - 1);

				List<String> remoteCacheBranchNames = getRemoteCacheBranchNames(
					gitWorkingDirectory,
					localGitRemoteConfigs.get(randomIndex));

				if (remoteCacheBranchNames.contains(cacheBranchName)) {
					System.out.println(
						"Cache branch " + cacheBranchName + " already exists.");

					updateCacheBranchTimestamp(
						cacheBranchName, gitWorkingDirectory,
						localGitRemoteConfigs);

					return cacheBranchName;
				}

				gitWorkingDirectory.rebaseAbort();

				gitWorkingDirectory.clean();

				gitWorkingDirectory.reset(null, ResetType.HARD);

				gitWorkingDirectory.fetch(null, upstreamRemoteConfig);

				gitWorkingDirectory.checkoutBranch(
					JenkinsResultsParserUtil.combine(
						upstreamRemoteConfig.getName(), "/",
						upstreamBranchName),
					"-f");

				gitWorkingDirectory.deleteLocalBranch(upstreamBranchName);

				gitWorkingDirectory.checkoutBranch(upstreamBranchName, "-b");

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, null);

				gitWorkingDirectory.fetch(
					cacheBranchName, senderBranchName, senderRemoteConfig);

				gitWorkingDirectory.createLocalBranch(
					cacheBranchName, true, senderBranchSha);

				if (pullRequest) {
					if (!gitWorkingDirectory.rebase(
							true, cacheBranchName, upstreamBranchSha)) {

						throw new RuntimeException("Rebase failed.");
					}
				}

				gitWorkingDirectory.checkoutBranch(cacheBranchName);

				cacheBranches(
					gitWorkingDirectory, cacheBranchName, localGitRemoteConfigs,
					upstreamBranchName, "liferay");

				return cacheBranchName;
			}
			catch (Exception e) {
				if (retryCount == 1) {
					throw e;
				}

				try {
					deleteCacheBranch(
						gitWorkingDirectory, receiverUsername, senderBranchName,
						senderUsername, senderBranchSha, upstreamBranchName,
						upstreamBranchSha);
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}

				localGitRemoteConfigs = null;
				senderRemoteConfig = null;

				System.out.println(
					"Synchronization with local-git failed. Retrying.");

				return synchronizeToLocalGit(
					gitWorkingDirectory, receiverUsername, retryCount + 1,
					senderBranchName, senderUsername, senderBranchSha,
					upstreamBranchName, upstreamBranchSha);
			}
			finally {
				if (localGitRemoteConfigs != null) {
					gitWorkingDirectory.removeRemotes(localGitRemoteConfigs);
				}

				if (gitWorkingDirectory.localBranchExists(originalBranchName)) {
					gitWorkingDirectory.checkoutBranch(originalBranchName);
				}
				else {
					gitWorkingDirectory.checkoutBranch(
						gitWorkingDirectory.getUpstreamBranchName());
				}

				gitWorkingDirectory.deleteLocalBranch(cacheBranchName);
			}
		}
		finally {
			if (senderRemoteConfig != null) {
				gitWorkingDirectory.removeRemote(senderRemoteConfig);
			}

			System.out.println(
				"Synchronization with local Git completed in " +
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start));
		}
	}

	protected static void updateCacheBranchTimestamp(
			final String cacheBranchName,
			final GitWorkingDirectory gitWorkingDirectory,
			List<RemoteConfig> localGitRemoteConfigs)
		throws GitAPIException {

		long start = System.currentTimeMillis();

		RemoteConfig localGitRemoteConfig = getRandomRemoteConfig(
			localGitRemoteConfigs);

		try {
			List<String> remoteCacheBranchNames = getRemoteCacheBranchNames(
				gitWorkingDirectory, localGitRemoteConfig);

			boolean updated = false;

			for (String remoteCacheBranchName : remoteCacheBranchNames) {
				Matcher matcher = _cacheTimestampBranchPattern.matcher(
					remoteCacheBranchName);

				if (remoteCacheBranchName.contains(cacheBranchName) &&
					matcher.matches()) {

					if (updated) {
						pushToAllRemotes(
							gitWorkingDirectory, "", remoteCacheBranchName,
							localGitRemoteConfigs);

						continue;
					}

					long currentTimestamp = System.currentTimeMillis();
					long existingTimestamp = Long.parseLong(
						matcher.group("timestamp"));

					if ((currentTimestamp - existingTimestamp) <
							(1000 * 60 * 60 * 24)) {

						return;
					}

					String newTimestampBranchName =
						JenkinsResultsParserUtil.combine(
							cacheBranchName, "-",
							Long.toString(currentTimestamp));

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"\nUpdating cache branch timestamp ", " from ",
							remoteCacheBranchName, "to ",
							newTimestampBranchName));

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Updating existing timestamp ", "branch name: ",
							remoteCacheBranchName, " to ",
							newTimestampBranchName));

					String currentBranch =
						gitWorkingDirectory.getCurrentBranch();

					gitWorkingDirectory.createLocalBranch(
						newTimestampBranchName);

					gitWorkingDirectory.fetch(
						newTimestampBranchName, remoteCacheBranchName,
						localGitRemoteConfig);

					gitWorkingDirectory.createLocalBranch(
						newTimestampBranchName, true,
						gitWorkingDirectory.getRemoteBranchSha(
							remoteCacheBranchName, localGitRemoteConfig));

					try {
						pushToAllRemotes(
							gitWorkingDirectory, newTimestampBranchName,
							newTimestampBranchName, localGitRemoteConfigs);
						pushToAllRemotes(
							gitWorkingDirectory, "", remoteCacheBranchName,
							localGitRemoteConfigs);

						updated = true;
					}
					finally {
						gitWorkingDirectory.checkoutBranch(currentBranch);

						gitWorkingDirectory.deleteLocalBranch(
							newTimestampBranchName);
					}
				}
			}
		}
		catch (GitAPIException gapie) {
			throw new RuntimeException(gapie);
		}

		System.out.println(
			"Cache branch timestamp updated in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));
	}

	private static final long _BRANCH_EXPIRE_AGE_MILLIS =
		1000 * 60 * 60 * 24 * 2;

	private static final String _cacheBranchRegex = ".*cache-.+-.+-.+-[^-]+";
	private static final Pattern _cacheTimestampBranchPattern = Pattern.compile(
		"(?<name>cache-.*)-(?<timestamp>\\d+)");

}