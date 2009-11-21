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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

/**
 * <a href="DynamicQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryImpl implements DynamicQuery {

	public DynamicQueryImpl(DetachedCriteria detachedCriteria) {
		_detachedCriteria = detachedCriteria;
	}

	public DynamicQuery add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_detachedCriteria.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	public DynamicQuery addOrder(Order order) {
		OrderImpl orderImpl = (OrderImpl)order;

		_detachedCriteria.addOrder(orderImpl.getWrappedOrder());

		return this;
	}

	public void compile(Session session) {
		org.hibernate.Session hibernateSession =
			(org.hibernate.Session)session.getWrappedSession();

		_criteria = _detachedCriteria.getExecutableCriteria(hibernateSession);

		if ((_start != null) && (_end != null)) {
			_criteria = _criteria.setFirstResult(_start.intValue());
			_criteria = _criteria.setMaxResults(
				_end.intValue() - _start.intValue());
		}
	}

	public DetachedCriteria getDetachedCriteria() {
		return _detachedCriteria;
	}

	@SuppressWarnings("unchecked")
	public List list() {
		return list(true);
	}

	@SuppressWarnings("unchecked")
	public List list(boolean unmodifiable) {
		List list = _criteria.list();

		if (unmodifiable) {
			return new UnmodifiableList(list);
		}
		else {
			return ListUtil.copy(list);
		}
	}

	public void setLimit(int start, int end) {
		_start = Integer.valueOf(start);
		_end = Integer.valueOf(end);
	}

	public DynamicQuery setProjection(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		_detachedCriteria.setProjection(projectionImpl.getWrappedProjection());

		return this;
	}

	private DetachedCriteria _detachedCriteria;
	private Criteria _criteria;
	private Integer _start;
	private Integer _end;

}