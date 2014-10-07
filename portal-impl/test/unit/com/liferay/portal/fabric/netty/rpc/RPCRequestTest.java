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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.channels.ClosedChannelException;

import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCRequestTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testExecuteWithAsyncException() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(null, false, _throwable, null));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, false, null, _throwable);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithCancellation() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(null, true, null, null));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, true, null, null);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithResult() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(null, false, null, _result));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, false, _result, null);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithSyncException() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(_throwable, false, null, null));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, false, null, _throwable);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testSendRPCResponseCancelled() {
		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addLast(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.cancel(true);
				}

			});

		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(null, false, null, _result));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, true, null, null);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			RPCRequest.class.getName(), Level.SEVERE);

		try {
			rpcRequest.sendRPCResponse(_embeddedChannel, rpcResponse);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Cancelled on sending RPC response: " + rpcResponse,
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testSendRPCResponseFailed() {
		_embeddedChannel.close();

		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestRPCCallable(null, false, null, _result));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, true, null, null);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			RPCRequest.class.getName(), Level.SEVERE);

		try {
			rpcRequest.sendRPCResponse(_embeddedChannel, rpcResponse);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to send RPC response: " + rpcResponse,
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertTrue(throwable instanceof ClosedChannelException);
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testToString() {
		RPCCallable<String> rpcCallable = new TestRPCCallable(
			null, true, null, null);

		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, rpcCallable);

		Assert.assertEquals(
			"{id=" + _id + ", rpcCallable=" + rpcCallable.toString() + "}",
			rpcRequest.toString());
	}

	protected void doTestExecute(
		RPCRequest<String> rpcRequest, RPCResponse<String> rpcResponse) {

		rpcRequest.execute(_embeddedChannel);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, messages.size());

		Object message = messages.poll();

		Assert.assertTrue(message instanceof RPCResponse);
		Assert.assertEquals(rpcResponse.toString(), message.toString());
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();
	private final long _id = System.currentTimeMillis();
	private final String _result = "This is the result.";
	private final Throwable _throwable = new Throwable(
		"This is the throwable.");

	private static class TestRPCCallable implements RPCCallable<String> {

		public TestRPCCallable(
			Throwable syncThrowable, boolean cancel, Throwable asyncThrowable,
			String result) {

			_syncThrowable = syncThrowable;
			_cancel = cancel;
			_asyncThrowable = asyncThrowable;
			_result = result;
		}

		@Override
		public NoticeableFuture<String> call() throws Throwable {
			if (_syncThrowable != null) {
				throw _syncThrowable;
			}

			DefaultNoticeableFuture<String> defaultNoticeableFuture =
				new DefaultNoticeableFuture<String>();

			if (_cancel) {
				defaultNoticeableFuture.cancel(true);
			}
			else if (_asyncThrowable != null) {
				defaultNoticeableFuture.setException(_asyncThrowable);
			}
			else {
				defaultNoticeableFuture.set(_result);
			}

			return defaultNoticeableFuture;
		}

		private static final long serialVersionUID = 1L;

		private final Throwable _asyncThrowable;
		private final boolean _cancel;
		private final String _result;
		private final Throwable _syncThrowable;

	}

}