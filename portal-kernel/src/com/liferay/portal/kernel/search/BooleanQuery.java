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

package com.liferay.portal.kernel.search;

import java.util.List;

/**
 * <a href="BooleanQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BooleanQuery extends Query {

	public void add(Query query, BooleanClauseOccur occur)
		throws ParseException;

	public void addExactTerm(String field, boolean value);

	public void addExactTerm(String field, Boolean value);

	public void addExactTerm(String field, double value);

	public void addExactTerm(String field, Double value);

	public void addExactTerm(String field, int value);

	public void addExactTerm(String field, Integer value);

	public void addExactTerm(String field, long value);

	public void addExactTerm(String field, Long value);

	public void addExactTerm(String field, short value);

	public void addExactTerm(String field, Short value);

	public void addExactTerm(String field, String value);

	public void addRequiredTerm(String field, boolean value);

	public void addRequiredTerm(String field, Boolean value);

	public void addRequiredTerm(String field, double value);

	public void addRequiredTerm(String field, Double value);

	public void addRequiredTerm(String field, int value);

	public void addRequiredTerm(String field, Integer value);

	public void addRequiredTerm(String field, long value);

	public void addRequiredTerm(String field, Long value);

	public void addRequiredTerm(String field, short value);

	public void addRequiredTerm(String field, Short value);

	public void addRequiredTerm(String field, String value);

	public void addRequiredTerm(String field, String value, boolean like);

	public void addTerm(String field, long value) throws ParseException;

	public void addTerm(String field, String value) throws ParseException;

	public void addTerm(String field, String value, boolean like)
		throws ParseException;

	public List<BooleanClause> clauses();

}