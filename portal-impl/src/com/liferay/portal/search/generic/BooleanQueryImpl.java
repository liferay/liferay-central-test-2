/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BaseBooleanQueryImpl;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanClauseOccurImpl;
import com.liferay.portal.kernel.search.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BooleanQueryImpl extends BaseBooleanQueryImpl {

	public void add(Query query, BooleanClauseOccur booleanClauseOccur) {
		_clauses.add(new BooleanClauseImpl(query, booleanClauseOccur));
	}

	public void add(Query query, String occur) {
		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		add(query, booleanClauseOccur);
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
			new QueryTermImpl(field, String.valueOf(value)));

		add(termQuery, BooleanClauseOccur.SHOULD);
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
			query = new WildcardQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}

		add(query , BooleanClauseOccur.MUST);
	}

	public void addTerm(String field, long value) {
		addTerm(field, String.valueOf(value), false);
	}

	public void addTerm(String field, String value) {
		addTerm(field, value, false);
	}

	public void addTerm(String field, String value, boolean like) {
		Query query = null;

		if (like) {
			query = new WildcardQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}

		add(query , BooleanClauseOccur.SHOULD);
	}

	public List<BooleanClause> clauses() {
		return Collections.unmodifiableList(_clauses);
	}

	private List<BooleanClause> _clauses = new ArrayList<BooleanClause>();

}