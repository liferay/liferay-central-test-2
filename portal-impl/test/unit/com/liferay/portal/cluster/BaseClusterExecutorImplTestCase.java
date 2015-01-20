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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterResponseCallback;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.View;

import org.junit.Assert;

/**
 * @author Tina Tian
 */
public abstract class BaseClusterExecutorImplTestCase
	extends BaseClusterTestCase {

	public static final String SERIALIZABLE_RETRUN_VALUE =
		"This is test method return value";

	@Aspect
	public static class EnableClusterExecutorDebugAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"CLUSTER_EXECUTOR_DEBUG_ENABLED)")
		public Object enableClusterExecutorDebug(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableLiveUsersAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.LIVE_USERS_ENABLED)")
		public Object enableLiveUsers(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class SetBadPortalInetSocketAddressAdvice {

		public static final String BAD_ADDRESS = "bad address";

		public static void setPort(int port) {
			_port = port;
		}

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"PORTAL_INSTANCE_HTTP_INET_SOCKET_ADDRESS)")
		public Object setPortalInetSocketAddress(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			String address = BAD_ADDRESS;

			if (_port != null) {
				address = address.concat(StringPool.COLON).concat(
					_port.toString());
			}

			return proceedingJoinPoint.proceed(new Object[] {address});
		}

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"PORTAL_INSTANCE_HTTPS_INET_SOCKET_ADDRESS)")
		public Object setSecurePortalInetSocketAddress(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			String address = BAD_ADDRESS;

			if (_port != null) {
				address = address.concat(StringPool.COLON).concat(
					_port.toString());
			}

			return proceedingJoinPoint.proceed(new Object[] {address});
		}

		private static Integer _port;

	}

	@Aspect
	public static class SetPortalInetSocketAddressAdvice {

		public static final String PORTAL_ADDRESS = "127.0.0.1";

		public static final int PORTAL_PORT = 80;

		public static final String SECURE_PORTAL_ADDRESS = "127.0.1.1";

		public static final int SECURE_PORTAL_PORT = 81;

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"PORTAL_INSTANCE_HTTP_INET_SOCKET_ADDRESS)")
		public Object setPortalInetSocketAddress(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(
				new Object[] {PORTAL_ADDRESS + StringPool.COLON + PORTAL_PORT});
		}

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"PORTAL_INSTANCE_HTTPS_INET_SOCKET_ADDRESS)")
		public Object setSecurePortalInetSocketAddress(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(
				new Object[] {
					SECURE_PORTAL_ADDRESS + StringPool.COLON +
						SECURE_PORTAL_PORT
					});
		}

	}

	@Aspect
	public static class SetWebServerProtocolAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.WEB_SERVER_PROTOCOL)")
		public Object setWebServerProtocol(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {"https"});
		}

	}

	protected void assertClusterEvent(
			ClusterEvent clusterEvent,
			ClusterEventType exceptedClusterEventType,
			ClusterNode... expectedClusterNodes)
		throws Exception {

		Assert.assertEquals(
			exceptedClusterEventType, clusterEvent.getClusterEventType());

		List<ClusterNode> clusterNodes = clusterEvent.getClusterNodes();

		Assert.assertEquals(expectedClusterNodes.length, clusterNodes.size());

		for (ClusterNode expectedClusterNode : expectedClusterNodes) {
			Assert.assertTrue(clusterNodes.contains(expectedClusterNode));
		}
	}

	protected void assertFutureClusterResponsesWithException(
			FutureClusterResponses futureClusterResponses, String expectedUUID,
			Address expectedAddress, String expectedExceptionMessage)
		throws InterruptedException {

		ClusterNodeResponses clusterNodeResponses =
			futureClusterResponses.get();

		ClusterNodeResponse clusterNodeResponse =
			clusterNodeResponses.getClusterResponse(expectedAddress);

		Exception exception = clusterNodeResponse.getException();

		Assert.assertEquals(1, clusterNodeResponses.size());
		Assert.assertEquals(expectedUUID, clusterNodeResponse.getUuid());
		Assert.assertEquals(expectedAddress, clusterNodeResponse.getAddress());
		Assert.assertTrue(clusterNodeResponse.hasException());

		if (expectedExceptionMessage != null) {
			String exceptionMessage = exception.getMessage();

			if (exception instanceof InvocationTargetException) {
				InvocationTargetException invocationTargetException =
					(InvocationTargetException)exception;

				Throwable throwable =
					invocationTargetException.getTargetException();

				exceptionMessage = throwable.getMessage();
			}

			Assert.assertEquals(expectedExceptionMessage, exceptionMessage);
		}

		try {
			clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			Assert.assertEquals(exception, e);
		}
	}

	protected void assertFutureClusterResponsesWithoutException(
			ClusterNodeResponses clusterNodeResponses, String expectedUUID,
			Object exceptedResult, Address expectedAddress)
		throws Exception {

		List<Address> expectedAddresses = new ArrayList<>();

		expectedAddresses.add(expectedAddress);

		assertFutureClusterResponsesWithoutException(
			clusterNodeResponses, expectedUUID, exceptedResult,
			expectedAddresses);
	}

	protected void assertFutureClusterResponsesWithoutException(
			ClusterNodeResponses clusterNodeResponses, String expectedUUID,
			Object exceptedResult, List<Address> expectedAddresses)
		throws Exception {

		Assert.assertEquals(
			expectedAddresses.size(), clusterNodeResponses.size());

		for (Address expectedAddress : expectedAddresses) {
			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(expectedAddress);

			Assert.assertEquals(expectedUUID, clusterNodeResponse.getUuid());
			Assert.assertEquals(
				expectedAddress, clusterNodeResponse.getAddress());
			Assert.assertEquals(
				exceptedResult, clusterNodeResponse.getResult());
			Assert.assertFalse(clusterNodeResponse.hasException());
			Assert.assertNull(clusterNodeResponse.getException());
		}
	}

	protected ClusterExecutorImpl getClusterExecutorImpl() {
		_initialize();

		ClusterExecutorImpl clusterExecutorImpl = new ClusterExecutorImpl();

		clusterExecutorImpl.afterPropertiesSet();

		clusterExecutorImpl.initialize();

		return clusterExecutorImpl;
	}

	protected void updateView(
		ClusterExecutorImpl localClusterExecutorImpl,
		ClusterExecutorImpl... remoteClusterExecutorImpls) {

		final JChannel localJChannel =
			localClusterExecutorImpl.getControlChannel();

		org.jgroups.Address jAddress = localJChannel.getAddress();

		List<org.jgroups.Address> jAddresses = new ArrayList<>();

		jAddresses.add(jAddress);

		for (ClusterExecutorImpl clusterExecutorImpl :
				remoteClusterExecutorImpls) {

			JChannel jChannel = clusterExecutorImpl.getControlChannel();

			jAddresses.add(jChannel.getAddress());
		}

		final View view = new View(
			jAddress, System.currentTimeMillis(), jAddresses);

		new Thread() {

			@Override
			public void run() {
				Receiver receiver = localJChannel.getReceiver();

				receiver.viewAccepted(view);
			}

		}.start();
	}

	protected static final String BEAN_IDENTIFIER = "test.bean";

	protected static final String SERVLET_CONTEXT_NAME =
		"TestServletContextName";

	protected static MethodKey testMethod1MethodKey = new MethodKey(
		TestBean.class, "testMethod1", String.class);
	protected static MethodKey testMethod2MethodKey = new MethodKey(
		TestBean.class, "testMethod2");
	protected static MethodKey testMethod3MethodKey = new MethodKey(
		TestBean.class, "testMethod3", String.class);

	protected class MockClusterEventListener implements ClusterEventListener {

		@Override
		public void processClusterEvent(ClusterEvent clusterEvent) {
			try {
				ClusterEventType clusterEventType =
					clusterEvent.getClusterEventType();

				if (clusterEventType.equals(ClusterEventType.DEPART)) {
					_departMessageExchanger.exchange(clusterEvent);
				}
				else if (clusterEventType.equals(ClusterEventType.JOIN)) {
					_joinMessageExchanger.exchange(clusterEvent);
				}
			}
			catch (InterruptedException ie) {
			}
		}

		public ClusterEvent waitDepartMessage() throws Exception {
			try {
				return _departMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public ClusterEvent waitJoinMessage() throws Exception {
			try {
				return _joinMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		private final Exchanger<ClusterEvent> _departMessageExchanger =
			new Exchanger<>();
		private final Exchanger<ClusterEvent> _joinMessageExchanger =
			new Exchanger<>();

	}

	protected class MockClusterResponseCallback
		implements ClusterResponseCallback {

		@Override
		public void callback(BlockingQueue<ClusterNodeResponse> blockingQueue) {
			try {
				_messageExchanger.exchange(blockingQueue);
			}
			catch (Exception e) {
			}
		}

		public BlockingQueue<ClusterNodeResponse> waitMessage()
			throws Exception {

			try {
				return _messageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		private final Exchanger<BlockingQueue<ClusterNodeResponse>>
			_messageExchanger = new Exchanger<>();

	}

	protected class MockPortalExecutorManager implements PortalExecutorManager {

		@Override
		public <T> NoticeableFuture<T> execute(
			String name, Callable<T> callable) {

			return _threadPoolExecutor.submit(callable);
		}

		@Override
		public <T> T execute(
				String name, Callable<T> callable, long timeout,
				TimeUnit timeUnit)
			throws ExecutionException, InterruptedException, TimeoutException {

			Future<T> future = _threadPoolExecutor.submit(callable);

			return future.get(timeout, timeUnit);
		}

		@Override
		public ThreadPoolExecutor getPortalExecutor(String name) {
			return _threadPoolExecutor;
		}

		@Override
		public ThreadPoolExecutor getPortalExecutor(
			String name, boolean createIfAbsent) {

			return _threadPoolExecutor;
		}

		@Override
		public ThreadPoolExecutor registerPortalExecutor(
			String name, ThreadPoolExecutor threadPoolExecutor) {

			return _threadPoolExecutor;
		}

		@Override
		public void shutdown() {
			shutdown(false);
		}

		@Override
		public void shutdown(boolean interrupt) {
			if (interrupt) {
				_threadPoolExecutor.shutdownNow();
			}
			else {
				_threadPoolExecutor.shutdown();
			}
		}

		@Override
		public void shutdown(String name) {
			shutdown(name, false);
		}

		@Override
		public void shutdown(String name, boolean interrupt) {
			if (interrupt) {
				_threadPoolExecutor.shutdownNow();
			}
			else {
				_threadPoolExecutor.shutdown();
			}
		}

		private final ThreadPoolExecutor _threadPoolExecutor =
			new ThreadPoolExecutor(10, 10);

	}

	private void _initialize() {
		if (_initialized) {
			return;
		}

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		PropsUtil.setProps(new PropsImpl());

		PortalExecutorManagerUtil portalExecutorManagerUtil =
			new PortalExecutorManagerUtil();

		portalExecutorManagerUtil.setPortalExecutorManager(
			new MockPortalExecutorManager());

		_initialized = true;
	}

	private boolean _initialized;

}