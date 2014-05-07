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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.NestedSetsTreeNodeModel;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public abstract class NestedSetsTreeManager<T extends NestedSetsTreeNodeModel> {

	public long countAncestors(T t) throws SystemException {
		return doCountAncestors(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public long countDescendants(T t) throws SystemException {
		return doCountDescendants(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public void delete(T t) throws SystemException {
		long nestedSetsTreeNodeLeft = t.getNestedSetsTreeNodeLeft();
		long nestedSetsTreeNodeRight = t.getNestedSetsTreeNodeRight();

		doUpdate(
			t.getNestedSetsTreeNodeScopeId(), -1, nestedSetsTreeNodeLeft, false,
			nestedSetsTreeNodeRight, false, null);
		doUpdate(t.getNestedSetsTreeNodeScopeId(), true, -2, nestedSetsTreeNodeRight, false);
		doUpdate(t.getNestedSetsTreeNodeScopeId(), false, -2, nestedSetsTreeNodeRight, false);
	}

	public List<T> getAncestors(T t) throws SystemException {
		return doGetAncestors(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public List<T> getDescendants(T t) throws SystemException {
		return doGetDescendants(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public void insert(T t, T parent) throws SystemException {
		if (parent == null) {
			long maxNestedSetsTreeNodeRight = getMaxNestedSetsTreeNodeRight(
				t.getNestedSetsTreeNodeScopeId());

			t.setNestedSetsTreeNodeLeft(maxNestedSetsTreeNodeRight);
			t.setNestedSetsTreeNodeRight(maxNestedSetsTreeNodeRight + 1);
		}
		else {
			long nestedSetsTreeNodeRight = parent.getNestedSetsTreeNodeRight();

			doUpdate(t.getNestedSetsTreeNodeScopeId(), true, 2, nestedSetsTreeNodeRight, true);

			doUpdate(t.getNestedSetsTreeNodeScopeId(), false, 2, nestedSetsTreeNodeRight, true);

			t.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeRight);
			t.setNestedSetsTreeNodeRight(nestedSetsTreeNodeRight + 1);
		}
	}

	public void move(T t, T oldParent, T newParent) throws SystemException {
		if (Validator.equals(oldParent, newParent)) {
			return;
		}

		long nestedSetsTreeNodeLeft = t.getNestedSetsTreeNodeLeft();
		long nestedSetsTreeNodeRight = t.getNestedSetsTreeNodeRight();

		List<T> childrenList = doGetDescendants(
			t.getNestedSetsTreeNodeScopeId(), nestedSetsTreeNodeLeft, nestedSetsTreeNodeRight);

		long newParentNestedSetsTreeNodeRight = 0;

		if (newParent == null) {
			newParentNestedSetsTreeNodeRight = getMaxNestedSetsTreeNodeRight(
				t.getNestedSetsTreeNodeScopeId());
		}
		else {
			newParentNestedSetsTreeNodeRight = newParent.getNestedSetsTreeNodeRight();
		}

		long delta = 0;

		if (nestedSetsTreeNodeRight < newParentNestedSetsTreeNodeRight) {
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(),
				-(nestedSetsTreeNodeRight - nestedSetsTreeNodeLeft + 1), nestedSetsTreeNodeRight, false,
				newParentNestedSetsTreeNodeRight, false, null);

			delta = newParentNestedSetsTreeNodeRight - nestedSetsTreeNodeRight - 1;

			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), delta, nestedSetsTreeNodeLeft, true,
				nestedSetsTreeNodeRight, true, childrenList);
		}
		else {
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), nestedSetsTreeNodeRight - nestedSetsTreeNodeLeft + 1,
				newParentNestedSetsTreeNodeRight, true, nestedSetsTreeNodeLeft, false, null);

			delta = newParentNestedSetsTreeNodeRight - nestedSetsTreeNodeLeft;

			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), delta, nestedSetsTreeNodeLeft, true,
				nestedSetsTreeNodeRight, true, childrenList);
		}

		t.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeLeft + delta);
		t.setNestedSetsTreeNodeRight(nestedSetsTreeNodeRight + delta);
	}

	protected abstract long doCountAncestors(
			long scopeId, long nestedSetsTreeNodeLeft, long nestedSetsTreeNodeRight)
		throws SystemException;

	protected abstract long doCountDescendants(
			long scopeId, long nestedSetsTreeNodeLeft, long nestedSetsTreeNodeRight)
		throws SystemException;

	protected abstract List<T> doGetAncestors(
			long scopeId, long nestedSetsTreeNodeLeft, long nestedSetsTreeNodeRight)
		throws SystemException;

	protected abstract List<T> doGetDescendants(
			long scopeId, long nestedSetsTreeNodeLeft, long nestedSetsTreeNodeRight)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, boolean leftOrRight, long delta, long limit,
			boolean inclusive)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, long delta, long start, boolean startIncluside,
			long end, boolean endInclusive, List<T> inList)
		throws SystemException;

	protected abstract long getMaxNestedSetsTreeNodeRight(long scopeId)
		throws SystemException;

}