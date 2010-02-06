/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="BooleanQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BooleanQueryImpl implements BooleanQuery {

	public BooleanQueryImpl() {
		_booleanQuery = new org.apache.lucene.search.BooleanQuery();
	}

	public void add(Query query, BooleanClauseOccur occur)
		throws ParseException {

		try {
			_booleanQuery.add(
				QueryTranslator.translate(query),
				BooleanClauseOccurTranslator.translate(occur));
		}
		catch (org.apache.lucene.queryParser.ParseException pe) {
			throw new ParseException(pe.getMessage());
		}
	}

	public void addExactTerm(String field, boolean value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, Boolean value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, double value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, Double value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, int value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, Integer value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, long value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, Long value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, short value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, Short value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addExactTerm(String field, String value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, boolean value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, Boolean value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, double value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, Double value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, int value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, Integer value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, long value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, Long value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, short value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, Short value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, String value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	public void addRequiredTerm(String field, String value, boolean like) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value, like);
	}

	public void addTerm(String field, long value) throws ParseException {
		try {
			LuceneHelperUtil.addTerm(_booleanQuery, field, value);
		}
		catch (org.apache.lucene.queryParser.ParseException pe) {
			throw new ParseException(pe.getMessage());
		}
	}

	public void addTerm(String field, String value) throws ParseException {
		try {
			LuceneHelperUtil.addTerm(_booleanQuery, field, value);
		}
		catch (org.apache.lucene.queryParser.ParseException pe) {
			throw new ParseException(pe.getMessage());
		}
	}

	public void addTerm(String field, String value, boolean like)
		throws ParseException {

		try {
			LuceneHelperUtil.addTerm(_booleanQuery, field, value, like);
		}
		catch (org.apache.lucene.queryParser.ParseException pe) {
			throw new ParseException(pe.getMessage());
		}
	}

	public List<BooleanClause> clauses() {
		List<org.apache.lucene.search.BooleanClause> luceneClauses =
			_booleanQuery.clauses();

		List<BooleanClause> clauses = new ArrayList<BooleanClause>(
			luceneClauses.size());

		for (int i = 0; i < luceneClauses.size(); i++) {
			clauses.add(new BooleanClauseImpl(luceneClauses.get(i)));
		}

		return clauses;
	}

	public org.apache.lucene.search.BooleanQuery getBooleanQuery() {
		return _booleanQuery;
	}

	public String toString() {
		return _booleanQuery.toString();
	}

	private org.apache.lucene.search.BooleanQuery _booleanQuery;

}