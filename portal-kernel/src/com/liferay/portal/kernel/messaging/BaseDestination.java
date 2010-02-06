/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ConcurrentHashSet;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <a href="BaseDestination.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public abstract class BaseDestination implements Destination {

	public BaseDestination() {
	}

	/**
	 * @deprecated
	 */
	public BaseDestination(String name) {
		this(name, _WORKERS_CORE_SIZE, _WORKERS_MAX_SIZE);
	}

	/**
	 * @deprecated
	 */
	public BaseDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		_name = name;
		_workersCoreSize = workersCoreSize;
		_workersMaxSize = workersMaxSize;

		open();
	}

	public void addDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.add(destinationEventListener);
	}

	public void afterPropertiesSet() {
		if (Validator.isNull(_name)) {
			throw new IllegalArgumentException("Name is null");
		}

		open();
	}

	public synchronized void close() {
		close(false);
	}

	public synchronized void close(boolean force) {
		doClose(force);
	}

	public void copyDestinationEventListeners(Destination destination) {
		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destination.addDestinationEventListener(
				destinationEventListener);
		}
	}

	public void copyMessageListeners(Destination destination) {
		for (MessageListener messageListener : _messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	public DestinationStatistics getDestinationStatistics() {
		DestinationStatistics destinationStatistics =
			new DestinationStatistics();

		destinationStatistics.setActiveThreadCount(
			_threadPoolExecutor.getActiveCount());
		destinationStatistics.setCurrentThreadCount(
			_threadPoolExecutor.getPoolSize());
		destinationStatistics.setLargestThreadCount(
			_threadPoolExecutor.getLargestPoolSize());
		destinationStatistics.setMaxThreadPoolSize(
			_threadPoolExecutor.getMaximumPoolSize());
		destinationStatistics.setMinThreadPoolSize(
			_threadPoolExecutor.getCorePoolSize());
		destinationStatistics.setPendingMessageCount(
			_threadPoolExecutor.getQueue().size());
		destinationStatistics.setSentMessageCount(
			_threadPoolExecutor.getCompletedTaskCount());

		return destinationStatistics;
	}

	public int getMaximumQueueSize() {
		return _maximumQueueSize;
	}

	public int getMessageListenerCount() {
		return _messageListeners.size();
	}

	public String getName() {
		return _name;
	}

	public int getWorkersCoreSize() {
		return _workersCoreSize;
	}

	public int getWorkersMaxSize() {
		return _workersMaxSize;
	}

	public boolean isRegistered() {
		if (getMessageListenerCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public synchronized void open() {
		doOpen();
	}

	public boolean register(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return registerMessageListener(invokerMessageListener);
	}

	public boolean register(
		MessageListener messageListener, ClassLoader classloader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classloader);

		return registerMessageListener(invokerMessageListener);
	}

	public void removeDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.remove(destinationEventListener);
	}

	public void removeDestinationEventListeners() {
		_destinationEventListeners.clear();
	}

	public void send(Message message) {
		if (_messageListeners.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No message listeners for destination " + getName());
			}

			return;
		}

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		if (threadPoolExecutor.isShutdown()) {
			throw new IllegalStateException(
				"Destination " + getName() + " is shutdown and cannot " +
					"receive more messages");
		}

		if ((_maximumQueueSize > -1) &&
			(threadPoolExecutor.getQueue().size() > _maximumQueueSize)) {

			throw new IllegalStateException(
				threadPoolExecutor.getQueue().size() +
					" messages exceeds the maximum queue size of " +
						_maximumQueueSize);
		}

		dispatch(_messageListeners, message);
	}

	public void setMaximumQueueSize(int maximumQueueSize) {
		_maximumQueueSize = maximumQueueSize;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setWorkersCoreSize(int workersCoreSize) {
		_workersCoreSize = workersCoreSize;
	}

	public void setWorkersMaxSize(int workersMaxSize) {
		_workersMaxSize = workersMaxSize;
	}

	public boolean unregister(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return unregisterMessageListener(invokerMessageListener);
	}

	public boolean unregister(
		MessageListener messageListener, ClassLoader classloader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classloader);

		return unregisterMessageListener(invokerMessageListener);
	}

	public void unregisterMessageListeners() {
		for (MessageListener messageListener : _messageListeners) {
			unregisterMessageListener((InvokerMessageListener)messageListener);
		}
	}

	protected abstract void dispatch(
		Set<MessageListener> messageListeners, Message message);

	protected void doClose(boolean force) {
		if (!_threadPoolExecutor.isShutdown() &&
			!_threadPoolExecutor.isTerminating()) {

			if (!force) {
				_threadPoolExecutor.shutdown();
			}
			else {
				List<Runnable> pendingTasks = _threadPoolExecutor.shutdownNow();

				if (_log.isInfoEnabled()) {
					_log.info(
						"The following " + pendingTasks.size() + " tasks " +
							"were not executed due to shutown: " +
								pendingTasks);
				}
			}
		}
	}

	protected void doOpen() {
		if ((_threadPoolExecutor == null) || _threadPoolExecutor.isShutdown()) {
			_threadPoolExecutor = new ThreadPoolExecutor(
				_workersCoreSize, _workersMaxSize, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new NamedThreadFactory(getName(), Thread.NORM_PRIORITY));
		}
	}

	protected void fireMessageListenerRegisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destinationEventListener.messageListenerRegistered(
				getName(), messageListener);
		}
	}

	protected void fireMessageListenerUnregisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener listener : _destinationEventListeners) {
			listener.messageListenerUnregistered(getName(), messageListener);
		}
	}

	protected ThreadPoolExecutor getThreadPoolExecutor() {
		return _threadPoolExecutor;
	}

	protected boolean registerMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean registered = _messageListeners.add(invokerMessageListener);

		if (registered) {
			fireMessageListenerRegisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return registered;
	}

	protected boolean unregisterMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean unregistered = _messageListeners.remove(invokerMessageListener);

		if (unregistered) {
			fireMessageListenerUnregisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return unregistered;
	}

	private static final int _WORKERS_CORE_SIZE = 2;

	private static final int _WORKERS_MAX_SIZE = 5;

	private static Log _log = LogFactoryUtil.getLog(BaseDestination.class);

	private Set<DestinationEventListener> _destinationEventListeners =
		new ConcurrentHashSet<DestinationEventListener>();
	private int _maximumQueueSize = -1;
	private Set<MessageListener> _messageListeners =
		new ConcurrentHashSet<MessageListener>();
	private String _name = StringPool.BLANK;
	private ThreadPoolExecutor _threadPoolExecutor;
	private int _workersCoreSize = _WORKERS_CORE_SIZE;
	private int _workersMaxSize = _WORKERS_MAX_SIZE;

}