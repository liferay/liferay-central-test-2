/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.util.lucene;

import com.liferay.util.Time;

import java.io.IOException;
import java.io.Serializable;

import org.apache.lucene.document.Document;

/**
 * <a href="Hits.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Hits implements Serializable {

	public Hits() {
		_start = System.currentTimeMillis();
	}

	public void recordHits(org.apache.lucene.search.Hits hits)
		throws IOException {

		_hits = hits;
		_length = hits.length();
		_docs = new Document[_length];
		_scores = new float[_length];
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
		try {
			if ((_docs[n] == null) && (_hits != null)) {
				_docs[n] = _hits.doc(n);
			}
		}
		catch(IOException ioe) {
		}

		return _docs[n];
	}

	public float score(int n) {
		try {
			if ((_scores[n] == 0) && (_hits != null)) {
				_scores[n] = _hits.score(n);
			}
		}
		catch (IOException ioe) {
		}

		return _scores[n];
	}

	public Hits subset(int begin, int end) {
		Hits subset = new Hits();

		if ((begin > - 1) && (begin <= end)) {
			subset.setStart(getStart());

			Document[] subsetDocs = new Document[getLength()];
			float[] subsetScores = new float[getLength()];

			int j = 0;

			for (int i = begin; (i < end) && (i < getLength()); i++, j++) {
				subsetDocs[j] = doc(i);
				subsetScores[j] = score(i);
			}

			subset.setLength(j);

			subset.setDocs(subsetDocs);
			subset.setScores(subsetScores);

			_searchTime =
				(float)(System.currentTimeMillis() - _start) / Time.SECOND;

			subset.setSearchTime(getSearchTime());
		}

		return subset;
	}

	private org.apache.lucene.search.Hits _hits;
	private long _start;
	private float _searchTime;
	private Document[] _docs = new Document[0];
	private int _length;
	private float[] _scores = new float[0];

}