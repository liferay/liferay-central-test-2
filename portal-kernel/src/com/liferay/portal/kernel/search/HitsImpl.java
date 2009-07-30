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

	public long getStart() {
		return _start;
	}

	public void setStart(long start) {
		_start = start;
	}

	public float getSearchTime() {
		return _searchTime;
	}

	public void setSearchTime(float time) {
		_searchTime = time;
	}

	public String[] getQueryTerms() {
		return _queryTerms;
	}

	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	public Document[] getDocs() {
		return _docs;
	}

	public void setDocs(Document[] docs) {
		_docs = docs;
	}

	public int getLength() {
		return _length;
	}

	public void setLength(int length) {
		_length = length;
	}

	public String[] getSnippets() {
		return _snippets;
	}

	public void setSnippets(String[] snippets) {
		_snippets = snippets;
	}

	public float[] getScores() {
		return _scores;
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

	public Document doc(int n) {
		return _docs[n];
	}

	public String snippet(int n) {
		return _snippets[n];
	}

	public float score(int n) {
		return _scores[n];
	}

	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_docs.length);

		for (int i = 0; i < _docs.length; i++) {
			subset.add(_docs[i]);
		}

		return subset;
	}

	private long _start;
	private float _searchTime;
	private String[] _queryTerms;
	private Document[] _docs;
	private int _length;
	private String[] _snippets;
	private float[] _scores = new float[0];

}