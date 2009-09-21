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

package com.liferay.portal.kernel.workflow.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="WorkflowDefinitionQueryContextBuilder.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowDefinitionQueryContextBuilder {

	public static OrderBy ORDER_BY_ID_ASC =
		new OrderBy("id", OrderBy.OrderByType.ASC);
	public static OrderBy ORDER_BY_ID_DESC =
		new OrderBy("id", OrderBy.OrderByType.DESC);
	public static OrderBy ORDER_BY_NAME_ASC =
		new OrderBy("name", OrderBy.OrderByType.ASC);
	public static OrderBy ORDER_BY_NAME_DESC =
		new OrderBy("name", OrderBy.OrderByType.DESC);
	public static OrderBy ORDER_BY_VERSION_ASC =
		new OrderBy("version", OrderBy.OrderByType.ASC);
	public static OrderBy ORDER_BY_VERSION_DESC =
		new OrderBy("version", OrderBy.OrderByType.DESC);

	public WorkflowDefinitionQueryContextBuilder(OrderBy... orderBys) {
		_orderBys.addAll(Arrays.asList(orderBys));
	}

	public QueryContext build() {
		return new QueryContext(
			_start, _end, _orderBys.toArray(new OrderBy[_orderBys.size()]));
	}

	public WorkflowDefinitionQueryContextBuilder orderByIdAsc() {
		_orderBys.add(ORDER_BY_ID_ASC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder orderByIdDesc() {
		_orderBys.add(ORDER_BY_ID_DESC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder orderByNameAsc() {
		_orderBys.add(ORDER_BY_NAME_ASC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder orderByNameDesc() {
		_orderBys.add(ORDER_BY_NAME_DESC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder orderByVersionAsc() {
		_orderBys.add(ORDER_BY_VERSION_ASC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder orderByVersionDesc() {
		_orderBys.add(ORDER_BY_VERSION_DESC);
		return this;
	}

	public WorkflowDefinitionQueryContextBuilder range(int start, int end) {
		_start = start;
		_end = end;
		return this;
	}

	private int _end = -1;
	private List<OrderBy> _orderBys = new ArrayList<OrderBy>();
	private int _start = -1;

}