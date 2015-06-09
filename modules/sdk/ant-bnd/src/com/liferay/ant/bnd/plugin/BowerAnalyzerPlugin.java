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

package com.liferay.ant.bnd.plugin;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.version.Version;

import aQute.lib.json.Decoder;
import aQute.lib.json.JSONCodec;

import java.io.InputStream;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raymond Augé
 */
public class BowerAnalyzerPlugin implements AnalyzerPlugin {

	public static final String BOWER_JSON = "bower.json";

	public static final String DEPENDENCIES = "dependencies";

	public static final String OSGI_WEBRESOURCE = "osgi.webresource";

	public static final String VERSION =
		"((\\d{1,9})(\\.([\\dx\\*]{1,9})(\\.([\\dx\\*]{1,9})" +
			"([\\.-]([-_\\da-zA-Z]+))?)?)?)";

	public static final String VERSION_INCLUSIVE_RANGE =
		VERSION + "\\s*-\\s*" + VERSION;

	public static final Pattern VERSION_INCLUSIVE_RANGE_PATTERN =
		Pattern.compile(VERSION_INCLUSIVE_RANGE);

	public static final String VERSION_NAMED =
		"(?<major>\\d{1,9})(\\.(?<minor>[\\dx\\*]{1,9})" +
			"(\\.(?<micro>[\\dx\\*]{1,9})" +
				"([\\.-](?<qualifier>[-_\\da-zA-Z]+))?)?)?";

	public static final Pattern VERSION_NAMED_PATTERN = Pattern.compile(
		VERSION_NAMED);

	public static final Pattern VERSION_PATTERN = Pattern.compile(VERSION);

	public static final String VERSION_PREFIX_RANGE = "(<|<=|>|>=|=|~|\\^|v)";

	public static final Pattern VERSION_PREFIX_RANGE_PATTERN = Pattern.compile(
		VERSION_PREFIX_RANGE + VERSION);

	public static final String VERSION_RANGE =
		"(" + VERSION_PREFIX_RANGE + VERSION + ")\\s+(" + VERSION_PREFIX_RANGE +
			VERSION + ")";

	public static final Pattern VERSION_RANGE_PATTERN = Pattern.compile(
		VERSION_RANGE);

	public static final String WEB_CONTEXTPATH = "Web-ContextPath";

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Jar jar = analyzer.getJar();

		Resource bowerJSONResource = jar.getResource(BOWER_JSON);

		if (bowerJSONResource == null) {
			return false;
		}

		BowerModule bowerModule = processBowerJsonResource(
			analyzer, bowerJSONResource);

		processDepedencies(analyzer, bowerModule);

		return false;
	}

	public static class BowerModule {

		public Map<String, String> dependencies;
		public String name;
		public Map<String, String> runtime;
		public String version;

	}

	protected void appendInclusive(
		StringBuilder sb, String group1, String group2) {

		sb.append("(&(version>=");

		Matcher matcher = VERSION_NAMED_PATTERN.matcher(group1);

		matcher.matches();

		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		sb.append(toVersion(major, minor, micro, qualifier));

		matcher = VERSION_NAMED_PATTERN.matcher(group2);

		matcher.matches();

		major = matcher.group("major");
		minor = matcher.group("minor");
		micro = matcher.group("micro");
		qualifier = matcher.group("qualifier");

		if (minor == null) {
			major = Integer.parseInt(major) + 1 + "";

			sb.append(")(!(version>=");
			sb.append(major);
			sb.append(".0.0)");
		}
		else if (micro == null) {
			sb.append(")(version<=");
			sb.append(major);
			sb.append(".");
			sb.append(Integer.parseInt(minor) + 1);
			sb.append(".0");
		}
		else {
			sb.append(")(version<=");
			sb.append(toVersion(major, minor, micro, qualifier));
		}

		sb.append("))");
	}

	protected void appendPrefixRange(
		StringBuilder sb, String prefix, String version) {

		Matcher matcher = VERSION_NAMED_PATTERN.matcher(version);

		matcher.matches();

		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		if (prefix.equals("v") || prefix.equals("=")) {
			sb.append("(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals("<")) {
			sb.append("(!(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append("))");
		}
		else if (prefix.equals("<=")) {
			sb.append("(version<=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals(">")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")))");
		}
		else if (prefix.equals(">=")) {
			sb.append("(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals("~")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");

			if (minor != null) {
				sb.append(major);
				sb.append(".");
				sb.append(Integer.parseInt(minor) + 1);
				sb.append(".0");
			}
			else {
				sb.append(Integer.parseInt(major) + 1);
				sb.append(".0.0");
			}

			sb.append(")))");
		}
		else if (prefix.equals("^")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");

			if (!major.equals("0") || minor.equalsIgnoreCase("x") ||
				minor.equals("*")) {

				sb.append(Integer.parseInt(major) + 1);
				sb.append(".0.0");
			}
			else if (!minor.equals("0") || (micro == null) ||
					 micro.equalsIgnoreCase("x") || micro.equals("*")) {

				sb.append("0.");
				sb.append(Integer.parseInt(desugar(minor)) + 1);
				sb.append(".0");
			}
			else {
				sb.append("0.0.");
				sb.append(Integer.parseInt(desugar(micro)) + 1);
			}

			sb.append(")))");
		}
	}

	protected void appendRange(StringBuilder sb, String group1, String group2) {
		sb.append("(&");

		Matcher matcher = VERSION_PREFIX_RANGE_PATTERN.matcher(group1);

		matcher.matches();

		appendPrefixRange(sb, matcher.group(1), matcher.group(2));

		matcher = VERSION_PREFIX_RANGE_PATTERN.matcher(group2);

		matcher.matches();

		appendPrefixRange(sb, matcher.group(1), matcher.group(2));

		sb.append(")");
	}

	protected void appendVersion(StringBuilder sb, Matcher matcher) {
		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");
			sb.append(Integer.parseInt(major) + 1);
			sb.append(".0.0)))");
		}
		else if ((micro == null) || micro.equalsIgnoreCase("x") ||
				 micro.equals("*")) {

			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");
			sb.append(major);
			sb.append(".");
			sb.append(Integer.parseInt(minor) + 1);
			sb.append(".0)))");
		}
		else {
			sb.append("(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
	}

	protected String desugar(String minor) {
		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			return "0";
		}

		return minor;
	}

	protected BowerModule getBowerModule(InputStream inputStream)
		throws Exception {

		JSONCodec jsonCodec = new JSONCodec();

		Decoder decoder = jsonCodec.dec();

		decoder = decoder.from(inputStream);

		return decoder.get(BowerModule.class);
	}

	protected String getBowerVersionFilter(String version) {
		StringBuilder sb = new StringBuilder();

		String[] comparatorSets = version.split("\\|\\|");

		// Comparator sets are OR'd together

		if (comparatorSets.length > 1) {
			sb.append("(|");
		}

		for (String comparatorSet : comparatorSets) {
			comparatorSet = comparatorSet.trim();

			if ((comparatorSet.length() == 0) || comparatorSet.equals("*")) {
				comparatorSet = ">=0";
			}

			Matcher inclusiveMatcher = VERSION_INCLUSIVE_RANGE_PATTERN.matcher(
				comparatorSet);
			Matcher rangeMatcher = VERSION_RANGE_PATTERN.matcher(comparatorSet);
			Matcher prefixRangeMatcher = VERSION_PREFIX_RANGE_PATTERN.matcher(
				comparatorSet);
			Matcher versionMatcher = VERSION_NAMED_PATTERN.matcher(
				comparatorSet);

			if (inclusiveMatcher.matches()) {
				appendInclusive(
					sb, inclusiveMatcher.group(1), inclusiveMatcher.group(9));
			}
			else if (rangeMatcher.matches()) {
				appendRange(sb, rangeMatcher.group(1), rangeMatcher.group(11));
			}
			else if (prefixRangeMatcher.matches()) {
				appendPrefixRange(
					sb, prefixRangeMatcher.group(1),
					prefixRangeMatcher.group(2));
			}
			else if (versionMatcher.matches()) {
				appendVersion(sb, versionMatcher);
			}
		}

		if (comparatorSets.length > 1) {
			sb.append(")");
		}

		return sb.toString();
	}

	protected BowerModule processBowerJsonResource(
			Analyzer analyzer, Resource bowerJSONResource)
		throws Exception {

		BowerModule bowerModule = getBowerModule(
			bowerJSONResource.openInputStream());

		String bundleVersion = analyzer.getBundleVersion();

		if (bundleVersion == null) {
			Version version = null;

			try {
				version = new Version(bowerModule.version);
			}
			catch (IllegalArgumentException iae) {
				String sanitizedQualifier = bowerModule.version.replaceAll(
					"[^-_\\da-zA-Z]", "");

				version = new Version("0.0.0." + sanitizedQualifier);
			}

			analyzer.setBundleVersion(version.toString());
		}

		String bowerName = bowerModule.name;
		String webContextPath = analyzer.getProperty(WEB_CONTEXTPATH);

		if ((webContextPath == null) && (bowerName != null)) {
			if (bowerName.indexOf('/') == 0) {
				bowerName = bowerName.substring(1);
			}

			analyzer.setProperty(
				WEB_CONTEXTPATH,
				'/' + bowerName + "-" + analyzer.getBundleVersion());
		}

		Parameters parameters = new Parameters();

		Attrs attrs = new Attrs();

		attrs.put(OSGI_WEBRESOURCE, bowerName);
		attrs.put(
			Constants.VERSION_ATTRIBUTE + ":Version",
			analyzer.getBundleVersion());

		parameters.add(OSGI_WEBRESOURCE, attrs);

		setCapabilities(analyzer, Constants.PROVIDE_CAPABILITY, parameters);

		return bowerModule;
	}

	protected void processDepedencies(
			Analyzer analyzer, BowerModule bowerModule)
		throws Exception {

		if (bowerModule.runtime == null) {
			return;
		}

		Parameters parameters = new Parameters();

		for (Entry<String, String> entry : bowerModule.runtime.entrySet()) {
			StringBuilder sb = new StringBuilder();

			sb.append("(&(");
			sb.append(OSGI_WEBRESOURCE);
			sb.append("=");

			String name = entry.getKey();

			sb.append(name);

			sb.append(")");

			String version = entry.getValue();

			sb.append(getBowerVersionFilter(version));

			sb.append(")");

			Attrs attrs = new Attrs();

			attrs.put(Constants.FILTER_DIRECTIVE, sb.toString());

			parameters.add(OSGI_WEBRESOURCE, attrs);
		}

		setCapabilities(analyzer, Constants.REQUIRE_CAPABILITY, parameters);
	}

	protected void setCapabilities(
		Analyzer analyzer, String capabilityType, Parameters parameters) {

		if (parameters.isEmpty()) {
			return;
		}

		Parameters headerParameters = new Parameters(
			analyzer.getProperty(capabilityType));

		if (!headerParameters.isEmpty()) {
			parameters.mergeWith(headerParameters, false);
		}

		analyzer.setProperty(capabilityType, parameters.toString());
	}

	protected String toVersion(
		String major, String minor, String micro, String qualifier) {

		StringBuilder sb = new StringBuilder();

		sb.append(major);

		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			sb.append(".0");
		}
		else {
			sb.append(".");
			sb.append(minor);
		}

		if ((micro == null) || micro.equalsIgnoreCase("x") ||
			micro.equals("*")) {

			sb.append(".0");
		}
		else {
			sb.append(".");
			sb.append(micro);
		}

		if (qualifier == null) {
			sb.append("");
		}
		else {
			sb.append(".");
			sb.append(qualifier);
		}

		return sb.toString();
	}

}