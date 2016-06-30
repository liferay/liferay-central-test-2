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

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Raymond Augé
 */
public class ConjunctionImpl extends CriterionImpl implements Conjunction {

	public ConjunctionImpl(org.hibernate.criterion.Conjunction conjunction) {
		super(conjunction);

		_conjunction = conjunction;
	}

	@Override
	public Junction add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_conjunction.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	public org.hibernate.criterion.Conjunction getWrappedConjunction() {
		return _conjunction;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{_conjunction=");

		if (_conjunction != null) {
			sb.append(_conjunction.toString());
		}
		else {
			sb.append(StringPool.NULL);
		}

		sb.append("}");

		return sb.toString();
	}

	private final org.hibernate.criterion.Conjunction _conjunction;

}