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

package com.liferay.ant.bnd;

import aQute.bnd.differ.Baseline;
import aQute.bnd.differ.Baseline.BundleInfo;
import aQute.bnd.differ.Baseline.Info;
import aQute.bnd.differ.DiffPluginImpl;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.diff.Delta;
import aQute.bnd.service.diff.Diff;
import aQute.bnd.version.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author Raymond Augé
 */
public class BaselineJarTask extends BaseBndTask {

	public void setBndFile(File bndFile) {
		_bndFile = bndFile;
	}

	public void setNewJarFile(File newJarFile) {
		_newJarFile = newJarFile;
	}

	public void setOldJarFile(File oldJarFile) {
		_oldJarFile = oldJarFile;
	}

	@Override
	public void trace(String format, Object... args) {
	}

	@Override
	protected void doBeforeExecute() throws BuildException {
		super.doBeforeExecute();

		File bndRootFile = getBndRootFile();

		File rootDir = bndRootFile.getParentFile();

		if ((_bndFile == null) || !_bndFile.exists() ||
			_bndFile.isDirectory()) {

			if (_bndFile != null) {
				log(
					"File is either missing or is a directory " +
						_bndFile.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("Bnd file is invalid");
		}

		if (_newJarFile == null) {
			throw new BuildException("New jar file is invalid");
		}

		_reportLevel = project.getProperty("baseline.jar.report.level");

		if (_reportLevel == null) {
			_reportLevel = "";
		}

		_reportLevelIsDiff = _reportLevel.equals("diff");
		_reportLevelIsOff = _reportLevel.equals("off");
		_reportLevelIsPersist = _reportLevel.equals("persist");
		_reportLevelIsStandard = _reportLevel.equals("standard");

		if (_reportLevelIsPersist) {
			_reportLevelIsDiff = true;

			File baselineReportsDir = new File(
				rootDir, getBaselineResportsDirName());

			if (!baselineReportsDir.exists() && !baselineReportsDir.mkdir()) {
				throw new BuildException(
					"Unable to create " + baselineReportsDir.getName());
			}

			_logFile = new File(
				baselineReportsDir, _newJarFile.getName() + ".log");

			if (_logFile.exists()) {
				_logFile.delete();
			}
		}

		_reportOnlyDirtyPackages = Boolean.parseBoolean(
			project.getProperty("baseline.jar.report.only.dirty.packages"));
	}

	protected void doDiff(Diff diff, StringBuffer sb) {
		String type = String.valueOf(diff.getType());

		String output = String.format(
			"%s%-3s %-10s %s", sb, getShortDelta(diff.getDelta()),
			type.toLowerCase(), diff.getName());

		log(output, Project.MSG_WARN);

		if (_printWriter != null) {
			_printWriter.println(output);
		}

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}

		sb.deleteCharAt(sb.length() - 1);
	}

	@Override
	protected void doExecute() throws Exception {
		if (_reportLevelIsOff) {
			return;
		}

		BaselineProcessor baselineProcessor = new BaselineProcessor();

		Properties properties = baselineProcessor.getProperties();

		properties.putAll(project.getProperties());
		properties.putAll(getBndFileProperties());

		Jar newJar = new Jar(_newJarFile);

		Jar oldJar = null;

		if (_oldJarFile != null) {
			if (!_oldJarFile.exists() || _oldJarFile.isDirectory() ||
				!_oldJarFile.canRead()) {

				baselineProcessor.warning(
					"Baseline file %s is invalid. Check if it exists, " +
						"is reablable, and is not a directory.",
					_oldJarFile);
			}
			else {
				oldJar = new Jar(_oldJarFile);
			}
		}
		else {
			oldJar = baselineProcessor.getBaselineJar();
		}

		try {
			if (oldJar == null) {
				return;
			}

			Baseline baseline = new Baseline(
				baselineProcessor, new DiffPluginImpl());

			Set<Info> infos = baseline.baseline(newJar, oldJar, null);

			if (infos.isEmpty()) {
				return;
			}

			BundleInfo bundleInfo = baseline.getBundleInfo();

			Info[] infosArray = infos.toArray(new Info[infos.size()]);

			Arrays.sort(
				infosArray, new Comparator<Info>() {

					@Override
					public int compare(Info info1, Info info2) {
						return info1.packageName.compareTo(info2.packageName);
					}

				}
			);

			doHeader(bundleInfo);

			for (Info info : infosArray) {
				String warnings = "-";

				Version newerVersion = info.newerVersion;
				Version suggestedVersion = info.suggestedVersion;

				if (suggestedVersion != null) {
					if (newerVersion.compareTo(suggestedVersion) > 0) {
						warnings = "EXCESSIVE VERSION INCREASE";
					}
					else if (newerVersion.compareTo(suggestedVersion) < 0) {
						warnings = "VERSION INCREASE REQUIRED";
					}
				}

				Diff packageDiff = info.packageDiff;

				Delta delta = packageDiff.getDelta();

				if (delta == Delta.REMOVED) {
					warnings = "PACKAGE REMOVED";
				}
				else if (delta == Delta.UNCHANGED) {
					boolean newVersionSuggested = false;

					if (suggestedVersion.compareTo(newerVersion) > 0) {
						warnings = "VERSION INCREASE SUGGESTED";

						newVersionSuggested = true;
					}
					else if (suggestedVersion.compareTo(newerVersion) < 0) {
						warnings = "EXCESSIVE VERSION INCREASE";

						newVersionSuggested = true;
					}

					if (!newVersionSuggested && !info.mismatch) {
						continue;
					}
				}

				generatePackageInfo(info, warnings);

				if (((_reportLevelIsStandard || _reportOnlyDirtyPackages) &&
					 warnings.equals("-")) ||
					(_reportOnlyDirtyPackages && (delta == Delta.REMOVED))) {

					continue;
				}

				doInfo(bundleInfo, info, warnings);

				if (_reportLevelIsDiff && (delta != Delta.REMOVED)) {
					doPackageDiff(packageDiff);
				}
			}
		}
		finally {
			report(baselineProcessor);

			baselineProcessor.close();
			newJar.close();

			if (oldJar != null) {
				oldJar.close();
			}

			if (_printWriter != null) {
				_printWriter.close();
			}
		}
	}

	protected void doHeader(BundleInfo bundleInfo) {
		if (!bundleInfo.mismatch) {
			return;
		}

		log("[Baseline Report] Mode: " + _reportLevel, Project.MSG_WARN);

		String output =
			"[Baseline Warning] Bundle Version Change Recommended: " +
				bundleInfo.suggestedVersion;

		log(output, Project.MSG_WARN);

		persistLog(output);
	}

	protected void doInfo(BundleInfo bundleInfo, Info info, String warnings) {
		doPackagesHeader(bundleInfo);

		reportLog(
			String.valueOf(info.mismatch ? '*' : ' '), info.packageName,
			String.valueOf(info.packageDiff.getDelta()),
			String.valueOf(info.newerVersion),
			String.valueOf(info.olderVersion),
			String.valueOf(
				(info.suggestedVersion == null) ? "-" : info.suggestedVersion),
			warnings, String.valueOf(info.attributes));
	}

	protected void doPackageDiff(Diff diff) {
		StringBuffer sb = new StringBuffer();

		sb.append("\t");

		for (Diff curDiff : diff.getChildren()) {
			if (curDiff.getDelta() == Delta.UNCHANGED) {
				continue;
			}

			doDiff(curDiff, sb);
		}
	}

	protected void doPackagesHeader(BundleInfo bundleInfo) {
		if (_headerPrinted) {
			return;
		}

		_headerPrinted = true;

		reportLog(
			" ", "PACKAGE_NAME", "DELTA", "CUR_VER", "BASE_VER", "REC_VER",
			"WARNINGS", "ATTRIBUTES");

		reportLog(
			"=", "==================================================",
			"==========", "==========", "==========", "==========",
			"==========", "==========");
	}

	protected void generatePackageInfo(Info info, String warnings)
		throws Exception {

		String sourceDirName = project.getProperty("plugin.source.dir");

		if (sourceDirName == null) {
			sourceDirName = project.getBaseDir() + "/src";
		}

		File sourceDir = new File(sourceDirName);

		if (!sourceDir.exists()) {
			return;
		}

		File packageDir = new File(
			sourceDir, info.packageName.replace('.', File.separatorChar));

		if (!packageDir.exists()) {
			return;
		}

		File packageInfoFile = new File(packageDir, "packageinfo");

		if (packageInfoFile.exists()) {
			return;
		}

		FileOutputStream fileOutputStream = new FileOutputStream(
			packageInfoFile);

		String content = "version " + info.suggestedVersion;

		fileOutputStream.write(content.getBytes());

		fileOutputStream.close();
	}

	protected String getBaselineResportsDirName() {
		if (_baselineResportsDirName != null) {
			return _baselineResportsDirName;
		}

		_baselineResportsDirName = project.getProperty(
			"baseline.jar.reports.dir.name");

		if (_baselineResportsDirName == null) {
			_baselineResportsDirName = _BASELINE_REPORTS_DIR;
		}

		return _baselineResportsDirName;
	}

	protected Map<? extends Object, ? extends Object> getBndFileProperties()
		throws Exception {

		Properties properties = new Properties();

		properties.load(new FileInputStream(_bndFile));

		return properties;
	}

	protected String getShortDelta(Delta delta) {
		if (delta == Delta.ADDED) {
			return "+";
		}
		else if (delta == Delta.CHANGED) {
			return "~";
		}
		else if (delta == Delta.MAJOR) {
			return ">";
		}
		else if (delta == Delta.MICRO) {
			return "0xB5";
		}
		else if (delta == Delta.MINOR) {
			return "<";
		}
		else if (delta == Delta.REMOVED) {
			return "-";
		}

		String deltaString = delta.toString();

		return String.valueOf(deltaString.charAt(0));
	}

	protected void persistLog(String output) {
		if (!_reportLevelIsPersist) {
			return;
		}

		try {
			if (_printWriter == null) {
				_logFile.createNewFile();

				_printWriter = new PrintWriter(_logFile);
			}

			_printWriter.println(output);
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}
	}

	protected void reportLog(
		String string1, String string2, String string3, String string4,
		String string5, String string6, String string7, String string8) {

		String output = String.format(
			"%s %-50s %-10s %-10s %-10s %-10s %-10s", string1, string2, string3,
			string4, string5, string6, string7);

		log(output, Project.MSG_WARN);

		persistLog(output);
	}

	private static final String _BASELINE_REPORTS_DIR = "baseline-reports";

	private String _baselineResportsDirName;
	private File _bndFile;
	private boolean _headerPrinted;
	private File _logFile;
	private File _newJarFile;
	private File _oldJarFile;
	private PrintWriter _printWriter;
	private String _reportLevel;
	private boolean _reportLevelIsDiff;
	private boolean _reportLevelIsOff = true;
	private boolean _reportLevelIsPersist;
	private boolean _reportLevelIsStandard;
	private boolean _reportOnlyDirtyPackages;

}