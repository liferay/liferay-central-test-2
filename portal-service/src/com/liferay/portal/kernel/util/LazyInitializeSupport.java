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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Shuyang Zhou
 */
public class LazyInitializeSupport implements LazyInitializable {

	public static LazyInitializeSupport create(String groupName) {
		LazyInitializeSupport lazyInitializeSupport =
			new LazyInitializeSupport();

		Set<LazyInitializeSupport> group = _registerMap.get(groupName);
		if (group == null) {
			group = new CopyOnWriteArraySet<LazyInitializeSupport>();
			Set<LazyInitializeSupport> oldGroup =
				_registerMap.putIfAbsent(groupName, group);
			if (oldGroup != null) {
				group = oldGroup;
			}
		}
		group.add(lazyInitializeSupport);

		return lazyInitializeSupport;
	}

	public static void finish(String groupName) {
		Set<LazyInitializeSupport> group = _registerMap.remove(groupName);
		for(LazyInitializeSupport lazyInitializeSupport : group) {
			lazyInitializeSupport.finishInitialize();
		}
	}

	public void finishInitialize() {
		_competeLatch.done();
	}

	public boolean isInitializing() {
		return _competeLatch.isLocked();
	}

	private LazyInitializeSupport() {
		_competeLatch = new CompeteLatch();
		_competeLatch.compete();
	}

	private static final ConcurrentMap<String, Set<LazyInitializeSupport>>
		_registerMap =
			new ConcurrentHashMap<String, Set<LazyInitializeSupport>>();

	private final CompeteLatch _competeLatch;

}