/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class BatchSessionImpl implements BatchSession {

	@Override
	public void delete(Session session, BaseModel<?> model)
		throws ORMException {

		if (!session.contains(model)) {
			model = (BaseModel<?>)session.get(
				model.getClass(), model.getPrimaryKeyObj());
		}

		if (model != null) {
			session.delete(model);
		}

		if (!isEnabled()) {
			session.flush();

			return;
		}

		if ((PropsValues.HIBERNATE_JDBC_BATCH_SIZE == 0) ||
			((_counter.get() % PropsValues.HIBERNATE_JDBC_BATCH_SIZE) == 0)) {

			session.flush();
		}

		_counter.set(_counter.get() + 1);
	}

	@Override
	public boolean isEnabled() {
		return _enabled.get();
	}

	@Override
	public void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	@Override
	public void update(Session session, BaseModel<?> model, boolean merge)
		throws ORMException {

		if (merge || model.isCachedModel()) {
			session.merge(model);
		}
		else {
			if (model.isNew()) {
				session.save(model);

				model.setNew(false);
			}
			else {
				session.merge(model);
			}
		}

		if (!isEnabled()) {
			session.flush();

			return;
		}

		if ((PropsValues.HIBERNATE_JDBC_BATCH_SIZE == 0) ||
			((_counter.get() % PropsValues.HIBERNATE_JDBC_BATCH_SIZE) == 0)) {

			session.flush();
		}

		_counter.set(_counter.get() + 1);
	}

	private static final long _INITIAL_COUNTER = 1;

	private static ThreadLocal<Long> _counter = new InitialThreadLocal<Long>(
		BatchSessionImpl.class + "._counter", _INITIAL_COUNTER);
	private static ThreadLocal<Boolean> _enabled =
		new InitialThreadLocal<Boolean>(
			BatchSessionImpl.class + "._enabled", false);

}