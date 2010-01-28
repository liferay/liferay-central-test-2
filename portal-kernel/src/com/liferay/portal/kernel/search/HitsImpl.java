/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="HitsImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class HitsImpl implements Hits {

	public HitsImpl() {
	}

	public Document doc(int n) {
		return _docs[n];
	}

	public Document[] getDocs() {
		return _docs;
	}

	public int getLength() {
		return _length;
	}

	public String[] getQueryTerms() {
		return _queryTerms;
	}

	public float[] getScores() {
		return _scores;
	}

	public float getSearchTime() {
		return _searchTime;
	}

	public String[] getSnippets() {
		return _snippets;
	}

	public long getStart() {
		return _start;
	}

	public float score(int n) {
		return _scores[n];
	}

	public void setDocs(Document[] docs) {
		_docs = docs;
	}

	public void setLength(int length) {
		_length = length;
	}

	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	public void setScores(float[] scores) {
		_scores = scores;
	}

	public void setScores(Float[] scores) {
		float[] primScores = new float[scores.length];

		for (int i = 0; i < scores.length; i++) {
			primScores[i] = scores[i].floatValue();
		}

		setScores(primScores);
	}

	public void setSearchTime(float time) {
		_searchTime = time;
	}

	public void setSnippets(String[] snippets) {
		_snippets = snippets;
	}

	public void setStart(long start) {
		_start = start;
	}

	public String snippet(int n) {
		return _snippets[n];
	}

	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_docs.length);

		for (int i = 0; i < _docs.length; i++) {
			subset.add(_docs[i]);
		}

		return subset;
	}

	private Document[] _docs;
	private int _length;
	private String[] _queryTerms;
	private float[] _scores = new float[0];
	private float _searchTime;
	private String[] _snippets = {};
	private long _start;

}