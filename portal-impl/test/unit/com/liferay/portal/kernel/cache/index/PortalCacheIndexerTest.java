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

package com.liferay.portal.kernel.cache.index;

import com.liferay.portal.cache.test.TestPortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.test.MappedMethodNameCallableInvocationHandler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class PortalCacheIndexerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_portalCache = new TestPortalCache<>(RandomTestUtil.randomString());

		_portalCacheIndexer = new PortalCacheIndexer<>(_portalCache);

		_cacheListener = ReflectionTestUtil.getFieldValue(
			_portalCache, "aggregatedCacheListener");

		_mappedMethodNameCallableInvocationHandler =
			new MappedMethodNameCallableInvocationHandler(
				ReflectionTestUtil.getFieldValue(
					_portalCacheIndexer, "_indexedCacheKeys"),
				true);

		ReflectionTestUtil.setFieldValue(
			_portalCacheIndexer, "_indexedCacheKeys",
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {ConcurrentMap.class},
				_mappedMethodNameCallableInvocationHandler));
	}

	@Test
	public void testAddIndexedCacheKeyConcurrentPutDifferentKeys()
		throws ReflectiveOperationException {

		_mappedMethodNameCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod(
				"putIfAbsent", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() {
					_portalCache.put(_INDEX_1_KEY_1, _VALUE);

					return null;
				}

			});

		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyPutSameKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyWithDifferentIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_2_KEY_3, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyWithSameIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testConstructor() {
		_portalCache = new TestPortalCache<>(RandomTestUtil.randomString());

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer = new PortalCacheIndexer<>(_portalCache);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testDispose() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.unregisterCacheListeners();

		Set<TestIndexedCacheKey> testIndexedCacheKeys =
			_portalCacheIndexer.getIndexedCacheKeys(_INDEX_1_KEY_1.getIndex());

		Assert.assertTrue(testIndexedCacheKeys.isEmpty());
	}

	@Test
	public void testGetIndexedCacheKeysWithIndexKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		Set<TestIndexedCacheKey> testIndexedCacheKeys =
			_portalCacheIndexer.getIndexedCacheKeys(_INDEX_1_KEY_1.getIndex());

		testIndexedCacheKeys.clear();

		assertIndexCacheSynchronization();
	}

	@Test
	public void testGetIndexedCacheKeysWithoutIndexKey() {
		_portalCacheIndexer.getIndexedCacheKeys(_INDEX_1_KEY_1.getIndex());

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryEvicted() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		_cacheListener.notifyEntryEvicted(
			_portalCache, _INDEX_1_KEY_1, _VALUE, 0);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryExpired() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		_cacheListener.notifyEntryExpired(
			_portalCache, _INDEX_1_KEY_1, _VALUE, 0);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryRemoved() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryUpdated() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyRemoveAll() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);
		_portalCache.put(_INDEX_2_KEY_3, _VALUE);

		_portalCache.removeAll();

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyConcurrentPut()
		throws ReflectiveOperationException {

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_mappedMethodNameCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod("remove", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_portalCache.put(_INDEX_1_KEY_2, _VALUE);

					return null;
				}

			});

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyConcurrentRemove()
		throws ReflectiveOperationException {

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_mappedMethodNameCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod("remove", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_portalCacheIndexer.removeIndexedCacheKeys(1L);

					return null;
				}

			});

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeysWithIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer.removeIndexedCacheKeys(_INDEX_1_KEY_1.getIndex());

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeysWithoutIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer.removeIndexedCacheKeys(_INDEX_2_KEY_3.getIndex());

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyWithKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyWithoutKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_2);

		assertIndexCacheSynchronization();
	}

	protected void assertIndexCacheSynchronization() {
		Set<TestIndexedCacheKey> expectedTestIndexedCacheKeys = new HashSet<>(
			_portalCache.getKeys());

		Set<Long> indexes = new HashSet<>();

		for (TestIndexedCacheKey testIndexedCacheKey :
				expectedTestIndexedCacheKeys) {

			indexes.add(testIndexedCacheKey.getIndex());
		}

		Set<TestIndexedCacheKey> actualTestIndexedCacheKeys = new HashSet<>();

		for (Long index : indexes) {
			actualTestIndexedCacheKeys.addAll(
				_portalCacheIndexer.getIndexedCacheKeys(index));
		}

		Assert.assertEquals(
			expectedTestIndexedCacheKeys, actualTestIndexedCacheKeys);
	}

	private static final TestIndexedCacheKey _INDEX_1_KEY_1 =
		new TestIndexedCacheKey(1L, 1L);

	private static final TestIndexedCacheKey _INDEX_1_KEY_2 =
		new TestIndexedCacheKey(1L, 2L);

	private static final TestIndexedCacheKey _INDEX_2_KEY_3 =
		new TestIndexedCacheKey(2L, 3L);

	private static final String _VALUE = "VALUE";

	private CacheListener<TestIndexedCacheKey, String> _cacheListener;
	private MappedMethodNameCallableInvocationHandler
		_mappedMethodNameCallableInvocationHandler;
	private PortalCache<TestIndexedCacheKey, String> _portalCache;
	private PortalCacheIndexer<Long, TestIndexedCacheKey, String>
		_portalCacheIndexer;

}