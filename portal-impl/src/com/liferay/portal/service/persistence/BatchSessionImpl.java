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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="BatchSessionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class BatchSessionImpl implements BatchSession {

	public boolean isEnabled() {
		return _enabled.get();
	}

	public void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	public void update(Session session, BaseModel model, boolean merge)
		throws ORMException {

		if (merge) {
			session.merge(model);
		}
		else {
			boolean contains = false;

			if (isEnabled()) {
				Object obj = session.get(
					model.getClass(), model.getPrimaryKeyObj());

				if ((obj != null) && obj.equals(model)) {
					contains = true;
				}
			}

			if (model.isNew()) {
				session.save(model);
			}
			else if (!contains && !session.contains(model)) {
				session.saveOrUpdate(model);
			}
		}

		if (!isEnabled()) {
			session.flush();

			return;
		}

		if (_counter.get() < PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
			_counter.set(_counter.get() + 1);
		}
		else {
			_counter.set(BatchSessionCounter.INITIAL_VALUE);

			session.flush();
			session.clear();
		}
	}

	private BatchSessionCounter _counter = new BatchSessionCounter();
	private BatchSessionEnabled _enabled = new BatchSessionEnabled();

}