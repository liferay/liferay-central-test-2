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

import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class GitWorkingDirectory {

	public static String getGitHubUserName(RemoteConfig remoteConfig)
		throws GitAPIException {

		String remoteURL = getRemoteURL(remoteConfig);

		if (!remoteURL.contains("github.com")) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					remoteConfig.getName(),
					" does not point to a GitHub repository"));
		}

		String userName = null;

		if (remoteURL.startsWith("https://github.com/")) {
			userName = remoteURL.substring("https://github.com/".length());
		}
		else {
			userName = remoteURL.substring("git@github.com:".length());
		}

		return userName.substring(0, userName.indexOf("/"));
	}

	public GitWorkingDirectory(
			String upstreamBranchName, String workingDirectory)
		throws GitAPIException, IOException {

		_upstreamBranchName = upstreamBranchName;

		setWorkingDirectory(workingDirectory);

		waitForIndexLock();

		FileRepositoryBuilder fileRepositoryBuilder =
			new FileRepositoryBuilder();

		fileRepositoryBuilder.setGitDir(_gitDirectory);
		fileRepositoryBuilder.setWorkTree(_workingDirectory);

		_repository = fileRepositoryBuilder.build();

		_git = new Git(_repository);

		_repositoryName = loadRepositoryName();
		_repositoryUsername = loadRepositoryUsername();
	}

	public RemoteConfig addRemote(
			boolean force, String remoteName, String remoteURL)
		throws GitAPIException {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Adding remote ", remoteName, " with url: ", remoteURL));

		RemoteConfig remoteConfig = getRemoteConfig(remoteName);

		if (remoteConfig != null) {
			if (force) {
				removeRemote(remoteConfig);
			}
			else {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Remote ", remoteName, " already exists"));
			}
		}

		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory,
				JenkinsResultsParserUtil.combine(
					"git remote add ", remoteName, " ", remoteURL));
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException("Unable to add remote " + remoteName, e);
		}

		if ((process != null) && (process.exitValue() != 0)) {
			try {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to add remote ", remoteName));
		}

		_remoteConfigs = null;

		return getRemoteConfig(remoteName);
	}

	public void checkoutBranch(String branchName) throws GitAPIException {
		checkoutBranch(branchName, "-f");
	}

	public void checkoutBranch(String branchName, String options)
		throws GitAPIException {

		String currentBranchName = getCurrentBranch();

		List<String> localBranchNames = getLocalBranchNames();

		if (!branchName.contains("/") &&
			!localBranchNames.contains(branchName)) {

			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout ", branchName,
					" because it does not exist"));
		}

		if (currentBranchName.equals(branchName)) {
			System.out.println(branchName + " is already checked out");

			return;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"The current branch is ", currentBranchName,
				". Checking out branch ", branchName, "."));

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		sb.append(branchName);

		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, sb.toString());
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to checkout branch " + branchName, e);
		}

		if ((process != null) && (process.exitValue() != 0)) {
			try {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout branch ", branchName));
		}

		int timeout = 0;

		File headFile = new File(_gitDirectory, "HEAD");

		String expectedContent = null;

		if (!branchName.contains("/")) {
			expectedContent = JenkinsResultsParserUtil.combine(
				"ref: refs/heads/", branchName);
		}
		else {
			int i = branchName.indexOf("/");

			String remoteBranchName = branchName.substring(i + 1);

			String remoteName = branchName.substring(0, i);

			expectedContent = getRemoteBranchSHA(
				remoteBranchName, getRemoteConfig(remoteName));
		}

		while (true) {
			String headContent = null;

			try {
				headContent = JenkinsResultsParserUtil.read(headFile);
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to read file " + headFile.getPath(), ioe);
			}

			headContent = headContent.trim();

			if (headContent.equals(expectedContent)) {
				return;
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"HEAD file content is currently ", headContent,
					" Waiting for branch to be updated"));

			JenkinsResultsParserUtil.sleep(5000);

			timeout++;

			if (timeout >= 59) {
				if (branchName.equals(getCurrentBranch())) {
					return;
				}

				throw new RuntimeException(
					"Unable to checkout branch " + branchName);
			}
		}
	}

	public void clean() throws GitAPIException {
		StoredConfig storedConfig = _repository.getConfig();

		boolean requireForce = storedConfig.getBoolean(
			"clean", "requireForce", true);

		if (requireForce == true) {
			updateConfig("clean", null, "requireForce", false);
		}

		try {
			CleanCommand cleanCommand = _git.clean();

			cleanCommand.setCleanDirectories(true);
			cleanCommand.setIgnore(true);

			System.out.println("Cleaning repository");

			cleanCommand.call();
		}
		finally {
			if (requireForce != false) {
				updateConfig("clean", null, "requireForce", null);
			}
		}
	}

	public void commitFileToCurrentBranch(String fileName, String message)
		throws GitAPIException {

		System.out.println("Committing file to current branch " + fileName);

		stageFileInCurrentBranch(fileName);

		commitStagedFilesToCurrentBranch(message);
	}

	public void commitStagedFilesToCurrentBranch(String message)
		throws GitAPIException {

		System.out.println("Committing staged files to current branch");

		CommitCommand commitCommand = _git.commit();

		commitCommand.setMessage(message);

		commitCommand.call();
	}

	public void createLocalBranch(String branchName) throws GitAPIException {
		createLocalBranch(branchName, false, null);
	}

	public void createLocalBranch(
			String branchName, boolean force, String startPoint)
		throws GitAPIException {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Creating branch ", branchName, " at starting point ",
				startPoint));

		CreateBranchCommand createBranchCommand = _git.branchCreate();

		createBranchCommand.setForce(force);
		createBranchCommand.setName(branchName);

		if (startPoint != null) {
			createBranchCommand.setStartPoint(startPoint);
		}

		createBranchCommand.call();
	}

	public String createPullRequest(
			String body, String pullRequestBranchName, String receiverUserName,
			String title)
		throws IOException {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("base", _upstreamBranchName);
		requestJSONObject.put("body", body);
		requestJSONObject.put(
			"head", receiverUserName + ":" + pullRequestBranchName);
		requestJSONObject.put("title", title);

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", receiverUserName, "/",
			_repositoryName, "/pulls");

		JSONObject responseJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());

		String pullRequestURL = responseJSONObject.getString("html_url");

		System.out.println("Created a pull request at " + pullRequestURL);

		return pullRequestURL;
	}

	public void deleteLocalBranch(String localBranchName)
		throws GitAPIException {

		System.out.println("Deleting local branch " + localBranchName);

		DeleteBranchCommand deleteBranchCommand = _git.branchDelete();

		deleteBranchCommand.setBranchNames(localBranchName);
		deleteBranchCommand.setForce(true);

		deleteBranchCommand.call();
	}

	public void fetch(RefSpec refSpec, RemoteConfig remoteConfig)
		throws GitAPIException {

		FetchCommand fetchCommand = _git.fetch();

		if (refSpec != null) {
			fetchCommand.setRefSpecs(refSpec);
		}
		else {
			fetchCommand.setRefSpecs(remoteConfig.getFetchRefSpecs());
		}

		Writer consoleWriter = new OutputStreamWriter(System.out);

		fetchCommand.setProgressMonitor(new TextProgressMonitor(consoleWriter));

		fetchCommand.setRemote(getRemoteURL(remoteConfig));
		fetchCommand.setTimeout(360);

		int retries = 0;
		long start = 0;

		while (true) {
			try {
				if (refSpec == null) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Fetching from ", getRemoteURL(remoteConfig)));
				}
				else {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Fetching from  ", getRemoteURL(remoteConfig), " ",
							refSpec.toString()));
				}

				start = System.currentTimeMillis();

				fetchCommand.call();

				System.out.println(
					"Fetch completed in " +
						JenkinsResultsParserUtil.toDurationString(
							System.currentTimeMillis() - start));

				return;
			}
			catch (TransportException te) {
				if (retries < 3) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Fetch attempt ", Integer.toString(retries),
							" failed with a transport exception. ",
							te.getMessage(), "\nRetrying."));

					retries++;

					JenkinsResultsParserUtil.sleep(30000);
				}
				else {
					throw te;
				}
			}
		}
	}

	public void fetch(
			String localBranchName, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		RefSpec refSpec = new RefSpec(
			JenkinsResultsParserUtil.combine(
				"refs/heads/", remoteBranchName, ":", localBranchName));

		fetch(refSpec, remoteConfig);
	}

	public List<Ref> getBranchRefs() throws GitAPIException {
		ListBranchCommand listBranchCommand = _git.branchList();

		listBranchCommand.setListMode(ListMode.ALL);

		return listBranchCommand.call();
	}

	public String getCurrentBranch() {
		waitForIndexLock();

		try {
			return _repository.getBranch();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get current branch name from repository", ioe);
		}
	}

	public Git getGit() {
		return _git;
	}

	public File getGitDirectory() {
		return _gitDirectory;
	}

	public String getGitHubBranchSHA(
		String branchName, RemoteConfig remoteConfig) {

		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote ", getRemoteURL(remoteConfig), " ", branchName);

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				command);

			if (process.exitValue() != 0) {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get branch sha for ", branchName, " on ",
						getRemoteURL(remoteConfig)));
			}

			String output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			for (String line : output.split("\n")) {
				if (line.endsWith("refs/heads/" + branchName)) {
					return line.substring(0, line.indexOf("\t"));
				}
			}
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public List<String> getLocalBranchNames() throws GitAPIException {
		List<Ref> allLocalBranchRefs = new ArrayList<>();

		for (Ref branchRef : getBranchRefs()) {
			String branchName = branchRef.getName();

			if (branchName.startsWith("refs/heads")) {
				allLocalBranchRefs.add(branchRef);
			}
		}

		return toShortNameList(allLocalBranchRefs);
	}

	public List<String> getRemoteBranchNames(RemoteConfig remoteConfig)
		throws GitAPIException {

		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		lsRemoteCommand.setHeads(true);
		lsRemoteCommand.setRemote(getRemoteURL(remoteConfig));
		lsRemoteCommand.setTags(false);

		List<String> remoteBranchNames = toShortNameList(
			lsRemoteCommand.call());

		Collections.sort(remoteBranchNames);

		return remoteBranchNames;
	}

	public String getRemoteBranchSHA(
			String branchName, RemoteConfig remoteConfig)
		throws GitAPIException {

		String remoteURL = getRemoteURL(remoteConfig);

		if (remoteURL.contains("git@github.com")) {
			return getGitHubBranchSHA(branchName, remoteConfig);
		}

		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		lsRemoteCommand.setHeads(true);
		lsRemoteCommand.setRemote(remoteURL);
		lsRemoteCommand.setTags(false);

		Collection<Ref> remoteRefs = lsRemoteCommand.call();

		for (Ref remoteRef : remoteRefs) {
			String completeBranchName = "refs/heads/" + branchName;

			if (completeBranchName.equals(remoteRef.getName())) {
				return remoteRef.getObjectId().getName();
			}
		}

		return null;
	}

	public RemoteConfig getRemoteConfig(String remoteName)
		throws GitAPIException {

		List<RemoteConfig> remoteConfigs = getRemoteConfigs();

		for (RemoteConfig remoteConfig : remoteConfigs) {
			if (remoteName.equals(remoteConfig.getName())) {
				return remoteConfig;
			}
		}

		return null;
	}

	public List<RemoteConfig> getRemoteConfigs() throws GitAPIException {
		if (_remoteConfigs != null) {
			return _remoteConfigs;
		}

		try {
			_remoteConfigs = RemoteConfig.getAllRemoteConfigs(
				_repository.getConfig());
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(urise);
		}

		return _remoteConfigs;
	}

	public Set<String> getRemoteNames() throws GitAPIException {
		List<RemoteConfig> remoteConfigs = getRemoteConfigs();

		Set<String> remoteNames = new HashSet<>(remoteConfigs.size());

		for (RemoteConfig remoteConfig : remoteConfigs) {
			remoteNames.add(remoteConfig.getName());
		}

		return remoteNames;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getRepositoryUsername() {
		return _repositoryUsername;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public File getWorkingDirectory() {
		return _workingDirectory;
	}

	public boolean localBranchExists(String branchName) throws GitAPIException {
		List<String> localBranchNames = getLocalBranchNames();

		return localBranchNames.contains(branchName);
	}

	public boolean pushToRemote(RemoteConfig remoteConfig)
		throws GitAPIException {

		return pushToRemote(getCurrentBranch(), remoteConfig);
	}

	public boolean pushToRemote(
			String remoteBranchName, RemoteConfig remoteConfig)
		throws GitAPIException {

		return pushToRemote(getCurrentBranch(), remoteBranchName, remoteConfig);
	}

	public boolean pushToRemote(String remoteBranchName, String remoteURL)
		throws GitAPIException {

		RemoteConfig remoteConfig = null;

		try {
			remoteConfig = addRemote(true, "temp", remoteURL);

			return pushToRemote(remoteBranchName, remoteConfig);
		}
		finally {
			removeRemote(remoteConfig);
		}
	}

	public boolean pushToRemote(
			String localBranchName, String remoteBranchName,
			RemoteConfig remoteConfig)
		throws GitAPIException {

		String remoteURL = getRemoteURL(remoteConfig);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushing ", localBranchName, " to ", remoteURL, " ",
				remoteBranchName));

		PushCommand pushCommand = null;

		synchronized (_git) {
			pushCommand = _git.push();
		}

		String remoteRefName = "refs/heads/" + remoteBranchName;

		RefSpec refSpec = new RefSpec(
			JenkinsResultsParserUtil.combine(
				localBranchName, ":", remoteRefName));

		synchronized (pushCommand) {
			pushCommand.setRefSpecs(refSpec);

			pushCommand.setRemote(remoteURL);

			for (PushResult pushResult : pushCommand.call()) {
				for (RemoteRefUpdate remoteRefUpdate :
						pushResult.getRemoteUpdates()) {

					if ((remoteRefUpdate != null) &&
						(remoteRefUpdate.getStatus() !=
							RemoteRefUpdate.Status.OK)) {

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Unable to push ", localBranchName, " to ",
								getRemoteURL(remoteConfig),
								".\nPush response: ",
								remoteRefUpdate.toString()));

						return false;
					}
				}
			}

			return true;
		}
	}

	public void rebase(
			boolean abortOnFail, String sourceBranchName,
			String targetBranchName)
		throws GitAPIException {

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", sourceBranchName, " ", targetBranchName);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Rebasing ", getCurrentBranch(), " to ", sourceBranchName));

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, getWorkingDirectory(), rebaseCommand);

			if ((process != null) && (process.exitValue() != 0)) {
				try {
					System.out.println(
						JenkinsResultsParserUtil.readInputStream(
							process.getErrorStream()));
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}

				throw new RuntimeException("Unable to rebase");
			}
		}
		catch (Exception e) {
			RepositoryState repositoryState = _repository.getRepositoryState();

			try {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to rebase ", targetBranchName, " to ",
						sourceBranchName, ". Repository is in the ",
						repositoryState.toString(), " state."),
					e);
			}
			finally {
				if (abortOnFail) {
					rebaseAbort();
				}
			}
		}
	}

	public void rebaseAbort() throws GitAPIException {
		if (!_rebaseRepositoryStates.contains(
				_repository.getRepositoryState())) {

			return;
		}

		RebaseCommand rebaseCommand = _git.rebase();

		rebaseCommand.setOperation(RebaseCommand.Operation.ABORT);

		System.out.println(
			"Aborting rebase " + RebaseCommand.Operation.ABORT.toString());

		rebaseCommand.call();
	}

	public boolean remoteExists(String remoteName) throws GitAPIException {
		Set<String> remoteNames = getRemoteNames();

		return remoteNames.contains(remoteName);
	}

	public void removeRemote(RemoteConfig remoteConfig) {
		try {
			if (!remoteExists(remoteConfig.getName())) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to remove remote ", remoteConfig.getName(),
						" because it does not exist"));
			}

			System.out.println("Removing remote " + remoteConfig.getName());

			Process process = null;

			try {
				process = JenkinsResultsParserUtil.executeBashCommands(
					true, _workingDirectory,
					"git remote remove " + remoteConfig.getName());
			}
			catch (InterruptedException | IOException e) {
				throw new RuntimeException(
					"Unable to remove remote " + remoteConfig.getName(), e);
			}

			if ((process != null) && (process.exitValue() != 0)) {
				try {
					System.out.println(
						JenkinsResultsParserUtil.readInputStream(
							process.getErrorStream()));
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}

				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to remove remote", remoteConfig.getName()));
			}

			if (_remoteConfigs.contains(remoteConfig)) {
				_remoteConfigs.remove(remoteConfig);
			}
			else {
				_remoteConfigs = null;
			}
		}
		catch (GitAPIException gapie) {
			gapie.printStackTrace();
		}
	}

	public void removeRemotes(List<RemoteConfig> remoteConfigs) {
		for (RemoteConfig remoteConfig : remoteConfigs) {
			removeRemote(remoteConfig);
		}
	}

	public void reset(String ref, ResetCommand.ResetType resetType)
		throws GitAPIException {

		if ((ref != null) && (ref.equals("head") || ref.equals("HEAD"))) {
			ref = null;
		}

		ResetCommand resetCommand = _git.reset();

		resetCommand.setMode(resetType);

		if (ref != null) {
			resetCommand.setRef(ref);
		}
		else {
			ref = Constants.HEAD;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Resetting ", resetType.toString(), " to ", ref));

		resetCommand.call();
	}

	public void stageFileInCurrentBranch(String fileName)
		throws GitAPIException {

		AddCommand addCommand = _git.add();

		addCommand.addFilepattern(fileName);

		System.out.println("Staging file in current branch " + fileName);

		addCommand.call();
	}

	public void updateConfig(
		String section, String subsection, String name, Object value) {

		StoredConfig storedConfig = _repository.getConfig();

		if (value == null) {
			storedConfig.unset(section, subsection, name);
		}

		if (value instanceof Boolean) {
			storedConfig.setBoolean(section, subsection, name, (Boolean)value);
		}

		if (value instanceof String) {
			storedConfig.setString(section, subsection, name, (String)value);
		}

		try {
			storedConfig.save();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to save configuration change");
		}
	}

	protected static String getRemoteURL(RemoteConfig remoteConfig) {
		List<URIish> uris = remoteConfig.getURIs();

		URIish uri = uris.get(0);

		return uri.toString();
	}

	protected String loadRepositoryName() throws GitAPIException {
		String remoteURL = getRemoteURL(getRemoteConfig("upstream"));

		int x = remoteURL.lastIndexOf("/") + 1;
		int y = remoteURL.indexOf(".git");

		String repositoryName = remoteURL.substring(x, y);

		if (repositoryName.equals("liferay-jenkins-tools-private")) {
			return repositoryName;
		}

		if ((repositoryName.equals("liferay-plugins-ee") ||
			 repositoryName.equals("liferay-portal-ee")) &&
			!_upstreamBranchName.contains("ee-") &&
			!_upstreamBranchName.contains("-private")) {

			repositoryName = repositoryName.replace("-ee", "");
		}

		if (repositoryName.contains("-private") &&
			!_upstreamBranchName.contains("-private")) {

			repositoryName = repositoryName.replace("-private", "");
		}

		return repositoryName;
	}

	protected String loadRepositoryUsername() throws GitAPIException {
		String remoteURL = getRemoteURL(getRemoteConfig("upstream"));

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setWorkingDirectory(String workingDirectory)
		throws GitAPIException, IOException {

		_workingDirectory = new File(workingDirectory);

		if (!_workingDirectory.exists()) {
			throw new FileNotFoundException(
				_workingDirectory.getPath() + " is unavailable");
		}

		_gitDirectory = new File(workingDirectory, ".git");

		if (!_gitDirectory.exists()) {
			throw new FileNotFoundException(
				_gitDirectory.getPath() + " is unavailable");
		}
	}

	protected List<String> toShortNameList(Collection<Ref> refs) {
		List<String> shortNames = new ArrayList<>(refs.size());

		for (Ref ref : refs) {
			String refName = ref.getName();

			shortNames.add(refName.substring(refName.lastIndexOf("/") + 1));
		}

		return shortNames;
	}

	protected void waitForIndexLock() {
		int retries = 0;

		File file = new File(_gitDirectory, "index.lock");

		while (file.exists()) {
			System.out.println("Waiting for index.lock to be cleared.");

			JenkinsResultsParserUtil.sleep(5000);

			retries++;

			if (retries >= 24) {
				file.delete();
			}
		}
	}

	private static final List<RepositoryState> _rebaseRepositoryStates =
		Arrays.asList(
			RepositoryState.REBASING, RepositoryState.REBASING_INTERACTIVE,
			RepositoryState.REBASING_MERGE, RepositoryState.REBASING_REBASING);

	static {
		JschConfigSessionFactory jschConfigSessionFactory =
			new JschConfigSessionFactory() {

				@Override
				protected void configure(Host host, Session session) {
					session.setConfig("StrictHostKeyChecking", "no");
				}

			};

		SshSessionFactory.setInstance(jschConfigSessionFactory);
	}

	private final Git _git;
	private File _gitDirectory;
	private List<RemoteConfig> _remoteConfigs;
	private final Repository _repository;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}