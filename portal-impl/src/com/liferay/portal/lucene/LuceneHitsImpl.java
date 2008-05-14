/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lucene;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.util.Time;
import com.liferay.util.search.DocumentImpl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;

/**
 * <a href="LuceneHitsImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class LuceneHitsImpl implements Hits {

	public LuceneHitsImpl() {
		_start = System.currentTimeMillis();
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

	public Searcher getSearcher() {
		return _searcher;
	}

	public void setSearcher(Searcher searcher) {
		_searcher = searcher;
	}

	public void closeSearcher() {
		try {
			if (_searcher != null){
				_searcher.close();

				_searcher = null;
			}
		}
		catch (Exception e) {
		}
	}

	public Hits closeSearcher(String keywords, Exception e)
		throws SearchException {

		closeSearcher();

		if (e instanceof BooleanQuery.TooManyClauses ||
			e instanceof ParseException) {

			_log.error("Parsing keywords " + keywords, e);

			return new LuceneHitsImpl();
		}
		else {
			throw new SearchException(e);
		}
	}

	public Document doc(int n) {
		try {
			if ((_docs[n] == null) && (_hits != null)) {
				_docs[n] =_getDocument(_hits.doc(n));
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
		Hits subset = new LuceneHitsImpl();

		if ((begin > - 1) && (begin <= end)) {
			subset.setStart(getStart());

			Document[] subsetDocs = new DocumentImpl[getLength()];
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

	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_length);

		for (int i = 0; i < _length; i++) {
			subset.add(doc(i));
		}

		return subset;
	}

	public void recordHits(
			org.apache.lucene.search.Hits hits, Searcher searcher)
		throws IOException {

		_hits = hits;
		_length = hits.length();
		_docs = new DocumentImpl[_length];
		_scores = new float[_length];
		_searcher = searcher;
	}

	private DocumentImpl _getDocument(
		org.apache.lucene.document.Document oldDoc) {

		DocumentImpl newDoc = new DocumentImpl();

		List<org.apache.lucene.document.Field> fields = oldDoc.getFields();

		for (org.apache.lucene.document.Field field : fields) {
			newDoc.add(
				new Field(
					field.name(), field.stringValue(), field.isTokenized()));
		}

		return newDoc;
	}

	private static Log _log = LogFactory.getLog(LuceneHitsImpl.class);

	private org.apache.lucene.search.Hits _hits;
	private long _start;
	private float _searchTime;
	private Document[] _docs = new DocumentImpl[0];
	private int _length;
	private float[] _scores = new float[0];
	private Searcher _searcher;

}