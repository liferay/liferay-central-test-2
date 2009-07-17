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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BooleanQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class BooleanQueryImpl implements BooleanQuery {

	public void add(Query query, BooleanClauseOccur occur)
		throws ParseException {

		if (occur.equals(BooleanClauseOccur.MUST)) {
			_clauses.add(new BooleanClauseImpl(query, occur, false, true));
		}
		else if (occur.equals(BooleanClauseOccur.SHOULD)) {
			_clauses.add(new BooleanClauseImpl(query, occur, false, false));
		}
		else if (occur.equals(BooleanClauseOccur.MUST_NOT)) {
			_clauses.add(new BooleanClauseImpl(query, occur, true, true));
		}

		throw new ParseException("Invalid occur clause " + occur);
	}

	public void addExactTerm(String field, boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, int value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Integer value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, String value) {
		TermQueryImpl termQuery = new TermQueryImpl(
			new QueryTerm(field, String.valueOf(value)));

		try {
			add(termQuery, BooleanClauseOccur.SHOULD);
		}
		catch (ParseException pe) {
			throw new IllegalStateException("Bad query implementation", pe);
		}
	}

	public void addRequiredTerm(String field, boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, int value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Integer value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, String value) {
		addRequiredTerm(field, value, false);
	}

	public void addRequiredTerm(String field, String value, boolean like) {
		Query query = null;

		if (like) {
			query = new WildCardQueryImpl(
				new QueryTerm(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTerm(field, String.valueOf(value)));
		}

		try {
			add(query , BooleanClauseOccur.MUST);
		}
		catch (ParseException pe) {
			throw new IllegalStateException("Bad query implementation", pe);
		}
	}

	public void addTerm(String field, long value) throws ParseException {
		addTerm(field, String.valueOf(value), false);
	}

	public void addTerm(String field, String value) throws ParseException {
		addTerm(field, value, false);
	}

	public void addTerm(String field, String value, boolean like)
		throws ParseException {

		Query query = null;

		if (like) {
			query = new WildCardQueryImpl(
				new QueryTerm(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTerm(field, String.valueOf(value)));
		}

		add(query , BooleanClauseOccur.SHOULD);
	}

	public List<BooleanClause> clauses() {
		return Collections.unmodifiableList(_clauses);
	}

	private List<BooleanClause> _clauses = new ArrayList<BooleanClause>();

}