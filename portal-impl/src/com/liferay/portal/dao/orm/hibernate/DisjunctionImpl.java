/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Raymond Augé
 */
public class DisjunctionImpl extends CriterionImpl implements Disjunction {

	public DisjunctionImpl(org.hibernate.criterion.Disjunction disjunction) {
		super(disjunction);

		_disjunction = disjunction;
	}

	@Override
	public Junction add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_disjunction.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	public org.hibernate.criterion.Disjunction getWrappedDisjunction() {
		return _disjunction;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{_disjunction=");

		if (_disjunction != null) {
			sb.append(_disjunction.toString());
		}
		else {
			sb.append(StringPool.NULL);
		}

		sb.append("}");

		return sb.toString();
	}

	private final org.hibernate.criterion.Disjunction _disjunction;

}