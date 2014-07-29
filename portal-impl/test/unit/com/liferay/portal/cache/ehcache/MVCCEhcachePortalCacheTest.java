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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.cluster.ClusterReplicationThreadLocal;
import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.LowLevelCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.Serializable;

import java.util.concurrent.Semaphore;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class MVCCEhcachePortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		_portalCache = new MemoryPortalCache<String, MVCCModel>(
			_CACHE_NAME, 16);

		_mvccEhcachePortalCache = new MVCCEhcachePortalCache<String, MVCCModel>(
			(LowLevelCache<String, MVCCModel>)_portalCache);

		_testCacheListener = new ThreadLocalAwareCacheListener();

		_portalCache.registerCacheListener(_testCacheListener);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testForHiddenBridge() {
		@SuppressWarnings("rawtypes")
		MVCCEhcachePortalCache mvccEhcachePortalCache =
			new MVCCEhcachePortalCache(new MemoryPortalCache(_CACHE_NAME, 16));

		Serializable key = _KEY_1;
		Object value = new MockMVCCModel(_VERSION_1);

		mvccEhcachePortalCache.put(key, value);
		mvccEhcachePortalCache.put(key, value, 10);
		mvccEhcachePortalCache.putQuiet(key, value);
		mvccEhcachePortalCache.putQuiet(key, value, 10);
	}

	@AdviseWith(adviceClasses = {MemoryPortalCacheAdvice.class})
	@Test
	public void testMVCCCacheWithAdvice() throws Exception {

		// Concurrent put 1

		MemoryPortalCacheAdvice.block();

		Thread thread1 = new Thread() {

			@Override
			public void run() {
				_mvccEhcachePortalCache.put(
					_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread1.start();

		MemoryPortalCacheAdvice.waitUntilBlock(1);

		Thread thread2 = new Thread() {

			@Override
			public void run() {
				_mvccEhcachePortalCache.put(
					_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread2.start();

		MemoryPortalCacheAdvice.waitUntilBlock(2);

		MemoryPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_1, _mvccEhcachePortalCache.get(_KEY_1));

		_testCacheListener.assertPut(_KEY_1, new MockMVCCModel(_VERSION_1));
		_testCacheListener.assertActionsCount(1);
		_testCacheListener.reset();

		// Concurrent put 2

		MemoryPortalCacheAdvice.block();

		thread1 = new Thread() {

			@Override
			public void run() {
				_mvccEhcachePortalCache.put(
					_KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread1.start();

		MemoryPortalCacheAdvice.waitUntilBlock(1);

		thread2 = new Thread() {

			@Override
			public void run() {
				_mvccEhcachePortalCache.put(
					_KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread2.start();

		MemoryPortalCacheAdvice.waitUntilBlock(2);

		MemoryPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_2, _mvccEhcachePortalCache.get(_KEY_1));

		_testCacheListener.assertUpdated(_KEY_1, new MockMVCCModel(_VERSION_2));
		_testCacheListener.assertActionsCount(1);
		_testCacheListener.reset();

		// Put with exception

		MemoryPortalCacheAdvice.setException();

		try {
			_mvccEhcachePortalCache.put(_KEY_2, new MockMVCCModel(_VERSION_1));

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertNull(_mvccEhcachePortalCache.get(_KEY_2));

			_testCacheListener.assertActionsCount(0);
		}
	}

	@Test
	public void testMVCCCacheWithoutTTL() {
		doTestMVCCCache(false);
	}

	@Test
	public void testMVCCCacheWithTTL() {
		doTestMVCCCache(true);
	}

	@Aspect
	public static class MemoryPortalCacheAdvice {

		public static void block() {
			_semaphore = new Semaphore(0);
		}

		public static void setException() {
			_exception = true;
		}

		public static void unblock(int permits) {
			Semaphore semaphore = _semaphore;

			_semaphore = null;

			semaphore.release(permits);
		}

		public static void waitUntilBlock(int threadCount) {
			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				while (semaphore.getQueueLength() < threadCount);
			}
		}

		@Around(
			"execution(protected * com.liferay.portal.cache.memory." +
				"MemoryPortalCache.doPutIfAbsent(..))")
		public Object doPutIfAbsent(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			if (_exception) {
				throw new Exception();
			}

			return proceedingJoinPoint.proceed();
		}

		@Around(
			"execution(protected * com.liferay.portal.cache.memory." +
				"MemoryPortalCache.doReplace(..))")
		public Object doReplace(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			if (_exception) {
				throw new Exception();
			}

			return proceedingJoinPoint.proceed();
		}

		private static volatile boolean _exception;
		private static volatile Semaphore _semaphore;

	}

	protected void doTestMVCCCache(final boolean timeToLive) {

		// Put 1

		if (timeToLive) {
			_mvccEhcachePortalCache.put(
				_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_mvccEhcachePortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_assertVersion(_VERSION_1, _mvccEhcachePortalCache.get(_KEY_1));

		_testCacheListener.assertPut(_KEY_1, new MockMVCCModel(_VERSION_1));
		_testCacheListener.assertActionsCount(1);
		_testCacheListener.reset();

		// Put 2

		if (timeToLive) {
			_mvccEhcachePortalCache.put(
				_KEY_1, new MockMVCCModel(_VERSION_0), 10);
		}
		else {
			_mvccEhcachePortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_0));
		}

		_assertVersion(_VERSION_1, _mvccEhcachePortalCache.get(_KEY_1));

		_testCacheListener.assertActionsCount(0);

		// Put 3

		if (timeToLive) {
			_mvccEhcachePortalCache.put(
				_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_mvccEhcachePortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_assertVersion(_VERSION_2, _mvccEhcachePortalCache.get(_KEY_1));

		_testCacheListener.assertUpdated(_KEY_1, new MockMVCCModel(_VERSION_2));
		_testCacheListener.assertActionsCount(1);
		_testCacheListener.reset();

		// Putquiet

		if (timeToLive) {
			_mvccEhcachePortalCache.putQuiet(
				_KEY_2, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_mvccEhcachePortalCache.putQuiet(
				_KEY_2, new MockMVCCModel(_VERSION_1));
		}

		_assertVersion(_VERSION_1, _mvccEhcachePortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
	}

	private void _assertVersion(long version, MVCCModel mvccModel) {
		Assert.assertEquals(version, mvccModel.getMvccVersion());
	}

	private static final String _CACHE_NAME = "CACHE_NAME";

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final long _VERSION_0 = 0;

	private static final long _VERSION_1 = 1;

	private static final long _VERSION_2 = 2;

	private MVCCEhcachePortalCache<String, MVCCModel> _mvccEhcachePortalCache;
	private PortalCache<String, MVCCModel> _portalCache;
	private TestCacheListener<String, MVCCModel> _testCacheListener;

	private static class MockMVCCModel implements MVCCModel {

		public MockMVCCModel(long version) {
			_version = version;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof MockMVCCModel)) {
				return false;
			}

			MockMVCCModel mockMVCCModel = (MockMVCCModel)object;

			return _version == mockMVCCModel._version;
		}

		@Override
		public long getMvccVersion() {
			return _version;
		}

		@Override
		public int hashCode() {
			return (int)_version;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
			_version = mvccVersion;
		}

		private long _version;

	}

	private static class ThreadLocalAwareCacheListener
		extends TestCacheListener<String, MVCCModel> {

		@Override
		public void notifyEntryEvicted(
			PortalCache<String, MVCCModel> portalCache, String key,
			MVCCModel value) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyEntryEvicted(portalCache, key, value);
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<String, MVCCModel> portalCache, String key,
			MVCCModel value) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyEntryExpired(portalCache, key, value);
		}

		@Override
		public void notifyEntryPut(
			PortalCache<String, MVCCModel> portalCache, String key,
			MVCCModel value) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyEntryPut(portalCache, key, value);
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<String, MVCCModel> portalCache, String key,
			MVCCModel value) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyEntryRemoved(portalCache, key, value);
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<String, MVCCModel> portalCache, String key,
			MVCCModel value) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyEntryUpdated(portalCache, key, value);
		}

		@Override
		public void notifyRemoveAll(
			PortalCache<String, MVCCModel> portalCache) {

			if (!ClusterReplicationThreadLocal.isReplicate()) {
				return;
			}

			super.notifyRemoveAll(portalCache);
		}

	}

}