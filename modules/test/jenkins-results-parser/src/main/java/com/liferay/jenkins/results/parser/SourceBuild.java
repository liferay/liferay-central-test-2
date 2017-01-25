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

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class SourceBuild extends BaseBuild {

	public SourceBuild(String url) {
		super(url);
	}

	public SourceBuild(String url, Build parentBuild) {
		super(url, parentBuild);
	}

	@Override
	public void findDownstreamBuilds() {
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		return null;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		return Collections.emptyList();
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _failureMessageGenerators;
	}

	private static final FailureMessageGenerator[] _failureMessageGenerators = {
		new RebaseFailureMessageGenerator(),
		new SourceFormatFailureMessageGenerator(),

		new GenericFailureMessageGenerator()
	};

}