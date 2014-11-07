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

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.local.agent.LocalFabricAgent;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentStub;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.rpc.ChannelThreadLocal;
import com.liferay.portal.fabric.netty.rpc.RPCUtil;
import com.liferay.portal.fabric.netty.rpc.SyncProcessRPCCallable;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.BaseFutureListener;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFutureConverter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerExecutionChannelHandler
	extends SimpleChannelInboundHandler<NettyFabricWorkerConfig<Serializable>> {

	public NettyFabricWorkerExecutionChannelHandler(
		Repository repository, ProcessExecutor processExecutor,
		long executionTimeout) {

		if (repository == null) {
			throw new NullPointerException("Repository is null");
		}

		if (processExecutor == null) {
			throw new NullPointerException("Process executor is null");
		}

		_repository = repository;
		_fabricAgent = new LocalFabricAgent(processExecutor);
		_executionTimeout = executionTimeout;
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable throwable) {

		final Channel channel = channelHandlerContext.channel();

		_log.error("Closing " + channel + " due to:", throwable);

		ChannelFuture channelFuture = channel.close();

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (_log.isInfoEnabled()) {
						_log.info(channel + " is closed");
					}
				}

			});
	}

	@Override
	protected void channelRead0(
		ChannelHandlerContext channelHandlerContext,
		NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

		NoticeableFuture<LoadedResources> noticeableFuture = loadResources(
			nettyFabricWorkerConfig);

		noticeableFuture.addFutureListener(
			new PostLoadResourcesFutureListener(
				channelHandlerContext, nettyFabricWorkerConfig));
	}

	protected NoticeableFuture<LoadedResources> loadResources(
		NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

		Map<Path, Path> mergedResources = new HashMap<Path, Path>();

		ProcessConfig processConfig =
			nettyFabricWorkerConfig.getProcessConfig();

		final Map<Path, Path> bootstrapResources =
			new LinkedHashMap<Path, Path>();

		for (String pathString :
				StringUtil.split(
					processConfig.getBootstrapClassPath(),
					File.pathSeparatorChar)) {

			bootstrapResources.put(Paths.get(pathString), null);
		}

		mergedResources.putAll(bootstrapResources);

		final Map<Path, Path> runtimeResources =
			new LinkedHashMap<Path, Path>();

		for (String pathString :
				StringUtil.split(
					processConfig.getRuntimeClassPath(),
					File.pathSeparatorChar)) {

			runtimeResources.put(Paths.get(pathString), null);
		}

		mergedResources.putAll(runtimeResources);

		final Map<Path, Path> inputResources =
			nettyFabricWorkerConfig.getInputResourceMap();

		mergedResources.putAll(inputResources);

		return new NoticeableFutureConverter<LoadedResources, Map<Path, Path>>(
			_repository.getFiles(mergedResources, false)) {

			@Override
			protected LoadedResources convert(Map<Path, Path> mergedResources)
				throws IOException {

				Map<Path, Path> loadedInputResources =
					new HashMap<Path, Path>();

				List<Path> missedInputResources = new ArrayList<Path>();

				for (Path path : inputResources.keySet()) {
					Path loadedInputResource = mergedResources.get(path);

					if (loadedInputResource == null) {
						missedInputResources.add(path);
					}
					else {
						loadedInputResources.put(path, loadedInputResource);
					}
				}

				if (!missedInputResources.isEmpty()) {
					throw new IOException(
						"Unable to get input resources :" +
							missedInputResources);
				}

				List<Path> loadedBootstrapResources = new ArrayList<Path>();

				List<Path> missedBootstrapResources = new ArrayList<Path>();

				for (Path path : bootstrapResources.keySet()) {
					Path loadedBootstrapResource = mergedResources.get(path);

					if (loadedBootstrapResource == null) {
						missedBootstrapResources.add(path);
					}
					else {
						loadedBootstrapResources.add(loadedBootstrapResource);
					}
				}

				if (!missedBootstrapResources.isEmpty() &&
					_log.isWarnEnabled()) {

					_log.warn(
						"Incomplete bootstrap classpath loaded, missed :" +
							missedBootstrapResources);
				}

				List<Path> loadedRuntimeResources = new ArrayList<Path>();

				List<Path> missedRuntimeResources = new ArrayList<Path>();

				for (Path path : runtimeResources.keySet()) {
					Path loadedRuntimeResource = mergedResources.get(path);

					if (loadedRuntimeResource == null) {
						missedRuntimeResources.add(path);
					}
					else {
						loadedRuntimeResources.add(loadedRuntimeResource);
					}
				}

				if (!missedRuntimeResources.isEmpty() && _log.isWarnEnabled()) {
					_log.warn(
						"Incomplete runtime classpath loaded, missed :" +
							missedRuntimeResources);
				}

				return new LoadedResources(
					loadedInputResources,
					StringUtil.merge(
						loadedBootstrapResources, File.pathSeparator),
					StringUtil.merge(
						loadedRuntimeResources, File.pathSeparator));
			}
		};
	}

	protected void sendResult(
		Channel channel, long fabricWorkerId, Serializable result,
		Throwable t) {

		final FabricWorkerResultProcessCallable
			fabricWorkerResultProcessCallable =
				new FabricWorkerResultProcessCallable(
					fabricWorkerId, result, t);

		NoticeableFuture<Serializable> noticeableFuture = RPCUtil.execute(
			channel,
			new SyncProcessRPCCallable<Serializable>(
				fabricWorkerResultProcessCallable));

		NettyUtil.scheduleCancellation(
			channel, noticeableFuture, _executionTimeout);

		noticeableFuture.addFutureListener(
			new BaseFutureListener<Serializable>() {

				@Override
				public void completeWithException(
					Future<Serializable> future, Throwable throwable) {

					_log.error(
						"Unable to send back fabric worker result " +
							fabricWorkerResultProcessCallable,
						throwable);
				}

			});
	}

	protected static class FabricAgentFinishStartupProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			Channel channel = ChannelThreadLocal.getChannel();

			NettyFabricAgentStub nettyStubFabricAgent =
				NettyChannelAttributes.getNettyFabricAgentStub(channel);

			if (nettyStubFabricAgent == null) {
				throw new ProcessException(
					"Unable to locate fabric agent on channel " + channel);
			}

			nettyStubFabricAgent.finsihStartup(_id);

			return null;
		}

		protected FabricAgentFinishStartupProcessCallable(long id) {
			_id = id;
		}

		private static final long serialVersionUID = 1L;

		private final long _id;

	}

	protected static class FabricWorkerResultProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			Channel channel = ChannelThreadLocal.getChannel();

			NettyFabricAgentStub nettyStubFabricAgent =
				NettyChannelAttributes.getNettyFabricAgentStub(channel);

			if (nettyStubFabricAgent == null) {
				throw new ProcessException(
					"Unable to locate fabric agent on channel " + channel);
			}

			NettyFabricWorkerStub<Serializable> nettyStubFabricWorker =
				(NettyFabricWorkerStub<Serializable>)
					nettyStubFabricAgent.takeNettyStubFabricWorker(_id);

			if (nettyStubFabricWorker == null) {
				throw new ProcessException(
					"Unable to locate fabric worker on channel " + channel +
						", with fabric worker id " + _id);
			}

			if (_throwable != null) {
				nettyStubFabricWorker.setException(_throwable);
			}
			else {
				nettyStubFabricWorker.setResult(_result);
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(7);

			sb.append("{id=");
			sb.append(_id);
			sb.append(", result=");
			sb.append(_result);
			sb.append(", throwable=");
			sb.append(_throwable);
			sb.append("}");

			return sb.toString();
		}

		protected FabricWorkerResultProcessCallable(
			long id, Serializable result, Throwable throwable) {

			_id = id;
			_result = result;
			_throwable = throwable;
		}

		private static final long serialVersionUID = 1L;

		private final long _id;
		private final Serializable _result;
		private final Throwable _throwable;

	}

	protected static class LoadedResources {

		public LoadedResources(
			Map<Path, Path> inputResources, String bootstrapClassPath,
			String runtimeClassPath) {

			_inputResources = inputResources;
			_bootstrapClassPath = bootstrapClassPath;
			_runtimeClassPath = runtimeClassPath;
		}

		public Map<Path, Path> getInputResources() {
			return _inputResources;
		}

		public ProcessConfig toProcessConfig(ProcessConfig processConfig) {
			Builder builder = new Builder();

			builder.setArguments(processConfig.getArguments());
			builder.setBootstrapClassPath(_bootstrapClassPath);
			builder.setJavaExecutable(processConfig.getJavaExecutable());
			builder.setRuntimeClassPath(_runtimeClassPath);

			return builder.build();
		}

		private final String _bootstrapClassPath;
		private final Map<Path, Path> _inputResources;
		private final String _runtimeClassPath;
	}

	protected class PostFabricWorkerExecutionFutureListener
		implements GenericFutureListener
			<io.netty.util.concurrent.Future<FabricWorker<Serializable>>> {

		public PostFabricWorkerExecutionFutureListener(
			Channel channel, LoadedResources loadedResources,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

			_channel = channel;
			_loadedResources = loadedResources;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
		}

		@Override
		public void operationComplete(
				io.netty.util.concurrent.Future<FabricWorker<Serializable>>
					future)
			throws Exception {

			Throwable throwable = future.cause();

			if (throwable != null) {
				sendResult(
					_channel, _nettyFabricWorkerConfig.getId(), null,
					throwable);

				return;
			}

			FabricWorker<Serializable> fabricWorker = future.get();

			NettyChannelAttributes.putFabricWorker(
				_channel, _nettyFabricWorkerConfig.getId(), fabricWorker);

			NoticeableFuture<Serializable> noticeableFuture = RPCUtil.execute(
				_channel,
				new SyncProcessRPCCallable<Serializable>(
					new FabricAgentFinishStartupProcessCallable(
						_nettyFabricWorkerConfig.getId())));

			NettyUtil.scheduleCancellation(
				_channel, noticeableFuture, _executionTimeout);

			noticeableFuture.addFutureListener(
				new BaseFutureListener<Serializable>() {

					@Override
					public void completeWithException(
						Future<Serializable> future, Throwable throwable) {

						_log.error(
							"Unable to finish fabric worker startup",
							throwable);
					}

				});

			NoticeableFuture<Serializable> processNoticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			processNoticeableFuture.addFutureListener(
				new PostFabricWorkerFinishFutureListener(
					_channel, _nettyFabricWorkerConfig, _loadedResources));
		}

		private final Channel _channel;
		private final LoadedResources _loadedResources;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;
	}

	protected class PostFabricWorkerFinishFutureListener
		implements FutureListener<Serializable> {

		public PostFabricWorkerFinishFutureListener(
			Channel channel,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig,
			LoadedResources loadedResources) {

			_channel = channel;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
			_loadedResources = loadedResources;
		}

		@Override
		public void complete(Future<Serializable> future) {
			Map<Path, Path> inputResources =
				_loadedResources.getInputResources();

			for (Path path : inputResources.values()) {
				FileHelperUtil.delete(true, path);
			}

			try {
				sendResult(
					_channel, _nettyFabricWorkerConfig.getId(), future.get(),
					null);
			}
			catch (Throwable t) {
				if (t instanceof ExecutionException) {
					t = t.getCause();
				}

				sendResult(_channel, _nettyFabricWorkerConfig.getId(), null, t);
			}
		}

		private final Channel _channel;
		private final LoadedResources _loadedResources;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;

	}

	protected class PostLoadResourcesFutureListener
		extends BaseFutureListener<LoadedResources> {

		public PostLoadResourcesFutureListener(
			ChannelHandlerContext channelHandlerContext,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

			_channelHandlerContext = channelHandlerContext;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
		}

		@Override
		public void completeWithException(
			Future<LoadedResources> future, Throwable throwable) {

			sendResult(
				_channelHandlerContext.channel(),
				_nettyFabricWorkerConfig.getId(), null, throwable);
		}

		@Override
		public void completeWithResult(
			Future<LoadedResources> loadResourcesFuture,
			final LoadedResources loadedResources) {

			EventExecutor eventExecutor = _channelHandlerContext.executor();

			io.netty.util.concurrent.Future<FabricWorker<Serializable>> future =
				eventExecutor.submit(
					new Callable<FabricWorker<Serializable>>() {

						@Override
						public FabricWorker<Serializable> call()
							throws ProcessException {

							ProcessConfig processConfig =
								_nettyFabricWorkerConfig.getProcessConfig();

							return _fabricAgent.execute(
								loadedResources.toProcessConfig(processConfig),
								_nettyFabricWorkerConfig.getProcessCallable());
						}

					});

			future.addListener(
				new PostFabricWorkerExecutionFutureListener(
					_channelHandlerContext.channel(), loadedResources,
					_nettyFabricWorkerConfig));
		}

		private final ChannelHandlerContext _channelHandlerContext;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricWorkerExecutionChannelHandler.class);

	private final long _executionTimeout;
	private final FabricAgent _fabricAgent;
	private final Repository _repository;

}