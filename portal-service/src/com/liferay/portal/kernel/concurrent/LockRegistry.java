/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class LockRegistry {

	public static Lock allocateLock(String groupName, String key) {
		ConcurrentHashMap<String, Lock> lockGroup = _lockGroupMap.get(
			groupName);

		if (lockGroup == null) {
			lockGroup = new ConcurrentHashMap<String, Lock>();

			ConcurrentHashMap<String, Lock> oldLockGroup =
				_lockGroupMap.putIfAbsent(groupName, lockGroup);

			if (oldLockGroup != null) {
				lockGroup = oldLockGroup;
			}
		}

		Lock lock = lockGroup.get(key);

		if (lock == null) {
			lock = new ReentrantLock();

			Lock oldLock = lockGroup.putIfAbsent(key, lock);

			if (oldLock != null) {
				lock = oldLock;
			}
		}

		return lock;
	}

	public static void freeAllLock() {
		freeAllLock(false);
	}

	public static void freeAllLock(boolean unlock) {
		if (unlock == true) {
			for (Map<String, Lock> lockGroup : _lockGroupMap.values()) {
				for (Lock lock : lockGroup.values()) {
					lock.unlock();
				}
			}
		}

		_lockGroupMap.clear();
	}

	public static Map<String, Lock> freeLock(String groupName) {
		return freeLock(groupName, false);
	}

	public static Map<String, Lock> freeLock(String groupName, boolean unlock) {
		Map<String, Lock> lockGroup = _lockGroupMap.remove(groupName);

		if (lockGroup == null) {
			return null;
		}

		if (unlock == true) {
			for (Lock lock : lockGroup.values()) {
				lock.unlock();
			}
		}

		return lockGroup;
	}

	public static Lock freeLock(String groupName, String key) {
		return freeLock(groupName, key, false);
	}

	public static Lock freeLock(String groupName, String key, boolean unlock) {
		Map<String, Lock> lockGroup = _lockGroupMap.get(groupName);

		if (lockGroup == null) {
			return null;
		}

		Lock lock = lockGroup.remove(key);

		if (lock == null) {
			return null;
		}

		if (unlock) {
			lock.unlock();
		}

		return lock;
	}

	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Lock>>
		_lockGroupMap =
			new ConcurrentHashMap<String, ConcurrentHashMap<String, Lock>>();

}