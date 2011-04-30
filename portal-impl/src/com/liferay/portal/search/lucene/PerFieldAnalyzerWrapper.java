/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import java.io.IOException;
import java.io.Reader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Fieldable;

/**
 * @author Raymond Augé
 */
public class PerFieldAnalyzerWrapper
	extends org.apache.lucene.analysis.PerFieldAnalyzerWrapper {

	public PerFieldAnalyzerWrapper(
		Analyzer defaultAnalyzer, Map<String,Analyzer> analyzerMap) {

		super(defaultAnalyzer, analyzerMap);

		_defaultAnalyzer = defaultAnalyzer;
		_analyzerMap = analyzerMap;
	}

	public void addAnalyzer(String fieldName, Analyzer analyzer) {
		super.addAnalyzer(fieldName, analyzer);

		_analyzerMap.put(fieldName, analyzer);
	}

	public TokenStream tokenStream(String fieldName, Reader reader) {
		Analyzer analyzer = _findAnalyzer(fieldName);

		return analyzer.tokenStream(fieldName, reader);
	}

	public TokenStream reusableTokenStream(String fieldName, Reader reader)
		throws IOException {

		Analyzer analyzer = _findAnalyzer(fieldName);

		return analyzer.reusableTokenStream(fieldName, reader);
	}

	public int getPositionIncrementGap(String fieldName) {
		Analyzer analyzer = _findAnalyzer(fieldName);

		return analyzer.getPositionIncrementGap(fieldName);
	}

	public int getOffsetGap(Fieldable field) {
		Analyzer analyzer = _findAnalyzer(field.name());

		return analyzer.getOffsetGap(field);
	}

	private Analyzer _findAnalyzer(String fieldName) {
		Analyzer analyzer = _analyzerMap.get(fieldName);

		if (analyzer == null) {
			for (String key : _analyzerMap.keySet()) {
				if (Pattern.matches(key, fieldName)) {
					analyzer = _analyzerMap.get(key);

					break;
				}
			}
		}

		if (analyzer != null) {
			return analyzer;
		}

		return _defaultAnalyzer;
	}

	private Map<String,Analyzer> _analyzerMap = new HashMap<String,Analyzer>();
	private Analyzer _defaultAnalyzer;

}