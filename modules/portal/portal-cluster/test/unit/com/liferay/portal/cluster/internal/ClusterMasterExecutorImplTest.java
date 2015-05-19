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

package com.liferay.portal.cluster.internal;

import com.liferay.portal.cluster.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.service.impl.LockLocalServiceImpl;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ClusterMasterExecutorImplTest extends BaseClusterTestCase {

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testClusterMasterTokenClusterEventListener() {

		// Test 1, cluster event listener is invoked when lock is not changed

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		String otherClusterNodeId = mockClusterExecutor.addClusterNode();

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		ClusterEventListener clusterEventListener = clusterEventListeners.get(
			0);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		clusterEventListener.processClusterEvent(null);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, cluster event listener is invoked when lock is changed

		_mockLockLocalService.setLock(otherClusterNodeId);

		clusterEventListener.processClusterEvent(null);

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testClusterMasterTokenTransitionListeners() {

		// Test 1, register cluster master token transition listener

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners =
				ReflectionTestUtil.getFieldValue(
					clusterMasterExecutorImpl,
					"_clusterMasterTokenTransitionListeners");

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		ClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());

		// Test 2, unregister cluster master token transition listener

		clusterMasterExecutorImpl.removeClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		// Test 3, set cluster master token transition listeners

		clusterMasterExecutorImpl.setClusterMasterTokenTransitionListeners(
			Collections.singleton(mockClusterMasterTokenTransitionListener));

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());
	}

	@Test
	public void testDeactivate() {

		// Test 1, destroy when cluster link is enabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		Assert.assertEquals(1, clusterEventListeners.size());
		Assert.assertNotNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl.deactivate();

		Assert.assertTrue(clusterEventListeners.isEmpty());
		Assert.assertNull(_mockLockLocalService.getLock());

		// Test 2, destory when cluster link is disabled

		clusterMasterExecutorImpl = createMasterExecutorImpl(false);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		clusterMasterExecutorImpl.deactivate();

		// Test 3, destory with exception when log is enabled

		clusterMasterExecutorImpl = createMasterExecutorImpl(true);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();

		_mockLockLocalService.setUnlockError(true);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			clusterMasterExecutorImpl.deactivate();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to destroy the cluster master executor",
				logRecord.getMessage());
		}

		// Test 4, destory with exception when log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			clusterMasterExecutorImpl.deactivate();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testExecuteOnMasterDisabled() throws Exception {

		// Test 1, execute without exception when log is eanbled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(false);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		MethodHandler methodHandler = new MethodHandler(
			_TEST_METHOD, timeString);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Executing on the local node because the cluster master " +
					"executor is disabled",
				logRecord.getMessage());
		}

		// Test 2, execute without exception when log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		// Test 3, execute with exception

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			try {
				clusterMasterExecutorImpl.executeOnMaster(null);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable throwable = se.getCause();

				Assert.assertSame(
					NullPointerException.class, throwable.getClass());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"Executing on the local node because the cluster master " +
						"executor is disabled",
					logRecord.getMessage());
			}
		}
	}

	@Test
	public void testExecuteOnMasterEnabled() throws Exception {

		// Test 1, execute without exception

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		NoticeableFuture<String> noticeableFuture =
			clusterMasterExecutorImpl.executeOnMaster(
				new MethodHandler(_TEST_METHOD, timeString));

		Assert.assertSame(timeString, noticeableFuture.get());

		// Test 2, execute with exception

		try {
			clusterMasterExecutorImpl.executeOnMaster(_BAD_METHOD_HANDLER);

			Assert.fail();
		}
		catch (SystemException se) {
			Assert.assertEquals(
				"Unable to execute on master " +
					mockClusterExecutor.getLocalClusterNodeId(),
				se.getMessage());
		}
	}

	@Test
	public void testGetMasterAddressString() {

		// Test 1, master to slave

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		String otherClusterNodeId = mockClusterExecutor.addClusterNode();

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		_mockLockLocalService.setLock(otherClusterNodeId);

		clusterMasterExecutorImpl.getMasterClusterNodeId(true);

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, slave to master

		_mockLockLocalService.setLock(
			mockClusterExecutor.getLocalClusterNodeId());

		clusterMasterExecutorImpl.getMasterClusterNodeId(true);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
	}

	@Test
	public void testGetMasterAddressStringWithException() {

		// Test 1, current owner is not alive

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.INFO)) {

			String otherClusterNodeId = "otherClusterNodeId";

			_mockLockLocalService.setLock(otherClusterNodeId);

			Assert.assertEquals(
				mockClusterExecutor.getLocalClusterNodeId(),
				clusterMasterExecutorImpl.getMasterClusterNodeId(true));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Lock currently held by " + otherClusterNodeId,
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Reattempting to acquire the cluster master lock",
				logRecord.getMessage());
		}

		// Test 2, current owner is null and log is enabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.INFO)) {

			_mockLockLocalService.setLock(null);

			Assert.assertEquals(
				mockClusterExecutor.getLocalClusterNodeId(),
				clusterMasterExecutorImpl.getMasterClusterNodeId(true));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to acquire the cluster master lock",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Reattempting to acquire the cluster master lock",
				logRecord.getMessage());
		}

		// Test 3, current owner is null and log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			_mockLockLocalService.setLock(null);

			Assert.assertEquals(
				mockClusterExecutor.getLocalClusterNodeId(),
				clusterMasterExecutorImpl.getMasterClusterNodeId(true));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testInitialize() {

		// Test 1, initialize when cluster link is disabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(false);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, initialize when cluster link is enabled and lock is null

		Assert.assertNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl = createMasterExecutorImpl(true);

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 3, initialize when cluster link is enabled and lock is not null

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		String otherClusterNodeId = mockClusterExecutor.addClusterNode();

		_mockLockLocalService.setLock(otherClusterNodeId);

		Assert.assertNotNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl = createMasterExecutorImpl(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testNotifyMasterTokenTransitionListeners() {

		// Test 1, notify when master is required

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			createMasterExecutorImpl(true);

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(true);

		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, notify when master is released

		clusterMasterExecutorImpl = createMasterExecutorImpl(true);

		mockClusterMasterTokenTransitionListener =
			new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(false);

		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());
	}

	protected ClusterMasterExecutorImpl createMasterExecutorImpl(
		final boolean enabled) {

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(new ClusterExecutor() {
			@Override
			public void addClusterEventListener(
				ClusterEventListener clusterEventListener) {
			}

			@Override
			public FutureClusterResponses execute(
				ClusterRequest clusterRequest) {

				return null;
			}

			@Override
			public List<ClusterEventListener> getClusterEventListeners() {
				return null;
			}

			@Override
			public List<ClusterNode> getClusterNodes() {
				return null;
			}

			@Override
			public ClusterNode getLocalClusterNode() {
				return null;
			}

			@Override
			public boolean isClusterNodeAlive(String clusterNodeId) {
				return enabled;
			}

			@Override
			public boolean isEnabled() {
				return enabled;
			}

			@Override
			public void removeClusterEventListener(
				ClusterEventListener clusterEventListener) {
			}
		});

		clusterMasterExecutorImpl.setLockLocalService(_mockLockLocalService);

		return clusterMasterExecutorImpl;
	}

	private static final MethodHandler _BAD_METHOD_HANDLER = new MethodHandler(
		new MethodKey());

	private static final MethodKey _TEST_METHOD = new MethodKey(
		TestBean.class, "testMethod1", String.class);

	private final MockLockLocalService _mockLockLocalService =
		new MockLockLocalService();

	private static class MockClusterExecutor extends ClusterExecutorImpl {

		public String addClusterNode() {
			if (!_enabled) {
				return null;
			}

			TestClusterChannel.clearAllMessages();

			MockClusterExecutor newMockClusterExecutor =
				new MockClusterExecutor(true);

			List<Serializable> messages =
				TestClusterChannel.getMulticastMessages();

			handleReceivedClusterRequest((ClusterRequest)messages.get(0));

			return newMockClusterExecutor.getLocalClusterNodeId();
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			if (clusterRequest.getPayload() == _BAD_METHOD_HANDLER) {
				throw new RuntimeException();
			}

			return super.execute(clusterRequest);
		}

		public String getLocalClusterNodeId() {
			ClusterNode clusterNode = getLocalClusterNode();

			return clusterNode.getClusterNodeId();
		}

		@Override
		public boolean isClusterNodeAlive(String clusterNodeId) {
			if (Validator.isNull(clusterNodeId)) {
				throw new NullPointerException();
			}

			return super.isClusterNodeAlive(clusterNodeId);
		}

		@Override
		public boolean isEnabled() {
			return _enabled;
		}

		private MockClusterExecutor(boolean enabled) {
			_enabled = enabled;

			setClusterChannelFactory(new TestClusterChannelFactory());

			clusterExecutorConfiguration = new ClusterExecutorConfiguration() {

				@Override
				public boolean debugEnabled() {
					return false;
				}

			};

			setPortalExecutorManager(new MockPortalExecutorManager());

			setProps(
				new Props() {

					@Override
					public boolean contains(String key) {
						return false;
					}

					@Override
					public String get(String key) {
						return null;
					}

					@Override
					public String get(String key, Filter filter) {
						return null;
					}

					@Override
					public String[] getArray(String key) {
						return null;
					}

					@Override
					public String[] getArray(String key, Filter filter) {
						return null;
					}

					@Override
					public Properties getProperties() {
						return null;
					}

					@Override
					public Properties getProperties(
						String prefix, boolean removePrefix) {

						return null;
					}

				});

			StringBundler sb = new StringBundler();

			sb.append("UDP(bind_addr=localhost;mcast_group_addr=239.255.0.1;");
			sb.append("mcast_port=23301):");
			sb.append("PING(timeout=2000;num_initial_members=20;");
			sb.append("break_on_coord_rsp=true):");
			sb.append("MERGE3(min_interval=10000;max_interval=30000):");
			sb.append("FD_SOCK:FD_ALL:VERIFY_SUSPECT(timeout=1500):");
			sb.append("pbcast.NAKACK2(xmit_interval=1000;");
			sb.append("xmit_table_num_rows=100;xmit_table_msgs_per_row=2000;");
			sb.append("xmit_table_max_compaction_time=30000;");
			sb.append("max_msg_batch_size=500;");
			sb.append("use_mcast_xmit=false;discard_delivered_msgs=true):");
			sb.append("UNICAST2(max_bytes=10M;xmit_table_num_rows=100;");
			sb.append("xmit_table_msgs_per_row=2000;");
			sb.append("xmit_table_max_compaction_time=60000;");
			sb.append("max_msg_batch_size=500):");
			sb.append("pbcast.STABLE(stability_delay=1000;");
			sb.append("desired_avg_gossip=50000;max_bytes=4M):");
			sb.append("pbcast.GMS(join_timeout=3000;print_local_addr=true;");
			sb.append("view_bundling=true):");
			sb.append("UFC(max_credits=2M;min_threshold=0.4):");
			sb.append("MFC(max_credits=2M;min_threshold=0.4):");
			sb.append("FRAG2(frag_size=61440):");
			sb.append("RSVP(resend_interval=2000;timeout=10000)");

			initialize(sb.toString());
		}

		private final boolean _enabled;

	}

	private static class MockClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public boolean isMasterTokenAcquiredNotified() {
			return _masterTokenAcquiredNotified;
		}

		public boolean isMasterTokenReleasedNotified() {
			return _masterTokenReleasedNotified;
		}

		@Override
		public void masterTokenAcquired() {
			_masterTokenAcquiredNotified = true;
		}

		@Override
		public void masterTokenReleased() {
			_masterTokenReleasedNotified = true;
		}

		private boolean _masterTokenAcquiredNotified;
		private boolean _masterTokenReleasedNotified;

	}

	private static class MockLockLocalService extends LockLocalServiceImpl {

		public Lock getLock() {
			return _lock;
		}

		@Override
		public Lock lock(String className, String key, String owner) {
			if (_lock == null) {
				_lock = new LockImpl();

				_lock.setKey(key);
				_lock.setOwner(owner);
			}

			return _lock;
		}

		@Override
		public Lock lock(
			String className, String key, String expectedOwner,
			String updatedOwner) {

			_lock = new LockImpl();

			_lock.setKey(key);
			_lock.setOwner(updatedOwner);

			return _lock;
		}

		public void setLock(String owner) {
			_lock = new LockImpl();

			_lock.setOwner(owner);
		}

		public void setUnlockError(boolean error) {
			_errorOnUnlock = error;
		}

		@Override
		public void unlock(String className, String key, String owner) {
			if (_errorOnUnlock) {
				throw new SystemException();
			}

			_lock = null;
		}

		private boolean _errorOnUnlock;
		private Lock _lock;

	}

}