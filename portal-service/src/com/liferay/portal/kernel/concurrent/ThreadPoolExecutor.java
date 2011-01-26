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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The major difference between this class and
 * {@link java.util.concurrent.ThreadPoolExecutor} is thread schedule strategy.
 * <p>
 * In {@link java.util.concurrent.ThreadPoolExecutor}, when task is submitted,
 * the schedule strategy is :
 * <ol>
 *	<li>
 *		Launch core thread to run the task, if pool size is less then
 *		corePoolSize.
 *	</il>
 *	<li>
 *		Queue up task if queue is not full yet.
 *	</li>
 *	<li>
 *		Launch additional thread to run the task, if queue is full and pool
 *		size is less then maxPoolSize.
 *	</li>
 *	<li>
 *		Reject the task
 *	</li>
 * </ol>
 * <p>
 * This strategy has three problems:
 * <ol>
 *	<li>
 *		Once launched all core threads, the executor will prefer queuing rather
 *		than launching new thread. <br/>
 *		This is only suitable for CPU sensitive tasks. Since launching more
 *		threads can only increase the context switch which will hurt the overall
 *		performance.<br/>
 *		But for IO sensitive tasks which will block very often, this strategy
 *		scales poorly. Since no more thread will be launched unless queue is
 *		full. This will force user to use a limited size queue to let the pool
 *		keeps growing. However a limited size queue will lead to false rejecting
 *		on peak load, which is bad for user experience.
 *	</li>
 *	<li>
 *		There is no FIFO guarantee.<br/>
 *		When the pool size is still under corePoolSize, all arrived task will be
 *		served by launching new core thread. So it is FIFO.<br/>
 *		When the pool size reaches corePoolSize, all arrived task will be queued
 *		up, waiting exist core threads to pick them up. So it is still FIFO.<br>
 *		Once the queue is full, all new arrived task will be served immediately
 *		(bypass the queue) by launching additional thread. This breaks the FIFO
 *		and is totally unfair for those enqueued waiting tasks.
 *	</li>
 *	<li>
 *		Core thread wasting.<br/>
 *		When the pool size is still under corePoolSize, arriving task always
 *		launches new core thread, even the previous launched ones have already
 *		finished the assigned tasks and been in idle waiting for new tasks.
 *	</li>
 * </ol>
 * <p>
 * This class use a difference schedule strategy to address the above three
 * problems.
 * <ol>
 *	<li>
 *		All scheduled tasks have to enqueue first irrelevant with the current
 *		executor state. <br/>
 *		This ensures the FIFO property.
 *  </li>
 *	<li>
 *		After enqueue the arrived task, check if there are spare threads waiting
 *		for tasks. If there is, simply does nothing.<br/>
 *		This fixes the core thread wasting problem.
 *	</li>
 *	<li>
 *		If there is no spare threads waiting, and the pool size is under
 *		corePoolSize, then launch new core thread to poll task from the queue
 *		(not directly hand-over the task to the new thread).<br/>
 *		If the pool size passes corePoolSize, but still under maxPoolSize,
 *		launch additional thread to poll task from the queue
 *		(not directly hand-over the task to the new thread).<br/>
 *		If the pool size reaches the maxPoolSize, no thread will be launched,
 *		simply keeps queuing up.<br/>
 *		If the queue has a limited size, and it has reached its capacity, then
 *		starts rejecting tasks.<br/>
 *		This makes the executor prefer launching new threads than queuing up,
 *		which suitable for IO sensitive tasks, but not CPU sensitive ones.
 *	</li>
 * </ol>
 * The difference between core threads and additional threads is only the
 * timeout feature. Additional thread always try to timeout to help releasing
 * resource. Depends on the configuration, core thread can timeout or not. If
 * allows core thread to timeout, then there is no difference between core
 * thread and additional thread.<br/>
 * In the future, core threads may allow to have a different timeout time than
 * additional threads. I will add it if this is really needed.<br/>
 * <p>
 * Note, this class is not attempting to completely replace
 * {@link java.util.concurrent.ThreadPoolExecutor}. They are suitable for
 * different use cases.<br/>
 * Choose the right one for your case wisely.
 *
 * @author Shuyang Zhou
 */
public class ThreadPoolExecutor extends AbstractExecutorService {

	public ThreadPoolExecutor(int corePoolSize, int maxPoolSize) {
		this(corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS, false,
			Integer.MAX_VALUE, new AbortPolicy(),
			Executors.defaultThreadFactory(), new ThreadPoolHandlerAdapter());
	}

	public ThreadPoolExecutor(
		int corePoolSize, int maxPoolSize,
		long keepAliveTime, TimeUnit timeunit, boolean allowCoreThreadTimeOut,
		int maxQueueSize) {
		this(corePoolSize, maxPoolSize, keepAliveTime, timeunit,
			allowCoreThreadTimeOut, maxQueueSize, new AbortPolicy(),
			Executors.defaultThreadFactory(), new ThreadPoolHandlerAdapter());
	}

	public ThreadPoolExecutor(
		int corePoolSize, int maxPoolSize,
		long keepAliveTime, TimeUnit timeunit, boolean allowCoreThreadTimeOut,
		int maxQueueSize,
		RejectedExecutionHandler rejectedExecutionHandler,
		ThreadFactory threadFactory,
		ThreadPoolHandler threadPoolHandler) {

		if (corePoolSize < 0 || maxPoolSize <= 0 ||
			maxPoolSize < corePoolSize || keepAliveTime < 0 ||
			maxQueueSize <= 0) {
			throw new IllegalArgumentException();
		}

		if (rejectedExecutionHandler == null || threadFactory == null ||
			threadPoolHandler == null) {
			throw new NullPointerException();
		}

		_corePoolSize = corePoolSize;
		_maxPoolSize = maxPoolSize;
		_keepAliveTime = timeunit.toNanos(keepAliveTime);
		_allowCoreThreadTimeOut = allowCoreThreadTimeOut;

		_rejectedExecutionHandler = rejectedExecutionHandler;
		_threadFactory = threadFactory;
		_threadPoolHandler = threadPoolHandler;
		_taskQueue = new TaskQueue<Runnable>(maxQueueSize);
		_workerTaskSet = new HashSet<WorkerTask>(_maxPoolSize);
	}

	/**
	 * Adjust pool to use the new given corePoolSize and maxPoolSize.
	 * As the constructor requires, the newCorePoolSize can not be negative
	 * integer, newMaxPoolSize can not be non-positive integer and
	 * newMaxPoolSize can not be less than newCorePoolSize. If any of these
	 * requirement is violated will throw IllegalArgumentException.
	 * <p>
	 * If newCorePoolSize &lt; corePoolSize, the surplus living threads will
	 * exit on next idle timeout waiting.
	 * <br/>
	 * If newCorePoolSize &gt; corePoolSize, and there is pending task in
	 * _taskQueue, will launch new threads to run the task.
	 * <p>
	 * If newMaxPoolSize &lt; maxPoolSize, the surplus living threads will
	 * exit on next idle timeout waiting.
	 * <br/>
	 * If newMaxPoolSize &>t; maxPoolSize, simply update the value, then on next
	 * add thread requesting, it could launch new thread.
	 *
	 * @param newCorePoolSize The new given corePoolSize
	 * @param newMaxPoolSize The new given maxPoolSize
	 */
	public void adjustPoolSize(int newCorePoolSize, int newMaxPoolSize) {
		if (newCorePoolSize < 0 || newMaxPoolSize <= 0 ||
			newMaxPoolSize < newCorePoolSize) {
			throw new IllegalArgumentException();
		}

		_mainLock.lock();
		try {
			int surplusCoreThreads = _corePoolSize - newCorePoolSize;
			int surplusMaxPoolSize = _maxPoolSize - newMaxPoolSize;
			_corePoolSize = newCorePoolSize;
			_maxPoolSize = newMaxPoolSize;
			if ((surplusCoreThreads > 0 && _poolSize > _corePoolSize) ||
				(surplusMaxPoolSize > 0 && _poolSize > _maxPoolSize)) {
				int interruptCount =
					Math.max(surplusCoreThreads, surplusMaxPoolSize);

				for (WorkerTask workerTask : _workerTaskSet) {
					if (interruptCount > 0) {
						if (workerTask._interruptIfWaiting()) {
							interruptCount--;
						}
					}
					else {
						break;
					}
				}
			}
			else {
				Runnable task = null;
				while (surplusCoreThreads++ < 0 &&
					_poolSize < _corePoolSize &&
					(task = _taskQueue.poll()) != null) {
					_doAddWorkerThread(task);
				}
			}
		}
		finally {
			_mainLock.unlock();
		}
	}

	public boolean awaitTermination(long timeout, TimeUnit timeUnit)
		throws InterruptedException {
		long nanos = timeUnit.toNanos(timeout);

		_mainLock.lock();
		try {
			while (true) {
				if (_runState == _TERMINATED) {
					return true;
				}
				if (nanos <= 0) {
					return false;
				}
				nanos = _terminationCondition.awaitNanos(nanos);
			}
		}
		finally {
			_mainLock.unlock();
		}
	}

	public void execute(Runnable runnable) {
		if (runnable == null) {
			// Quick fail to avoid enter critical section
			throw new NullPointerException();
		}

		boolean[] hasWaiterMarker = new boolean[1];
		// Try to enqueue when executor is in _RUNNING state
		if (_runState == _RUNNING &&
			_taskQueue.offer(runnable, hasWaiterMarker)) {
			// Check concurrent shutdown while enqueuing
			if (_runState != _RUNNING) {
				// Detect a concurrent shutdown, try to rollback enqueued job.
				// If fails to rollback, it means the job is already dispatched,
				// then it is either running, finished or interrupted. They are
				// all legal states
				if (_taskQueue.remove(runnable)) {
					// Successfully rollback
					_rejectedExecutionHandler.rejectedExecution(runnable, this);
				}
				// Stop processing since executor is not in _RUNNING state
				return;
			}
			// Executor is in _RUNNING state and enqueued successfully.
			// Check whether executor has spare threads waiting, if no try to
			// add one.
			if (!hasWaiterMarker[0]) {
				// No spare thread is waiting, try to launch new thread.
				_addWorkerThread();
			}
			return;
		}

		// Executor is not in _RUNNING, or queue is full.
		_rejectedExecutionHandler.rejectedExecution(runnable, this);
	}

	public int getActiveCount() {
		_mainLock.lock();
		try {
			int count = 0;
			for (WorkerTask worker : _workerTaskSet) {
				if (worker._isLocked()) {
					count++;
				}
			}
			return count;
		}
		finally {
			_mainLock.unlock();
		}
	}

	public long getCompletedTaskCount() {
		_mainLock.lock();
		try {
			long count = _completedTaskCount;
			for (WorkerTask worker : _workerTaskSet) {
				count += worker._localCompletedTaskCount;
			}
			return count;
		}
		finally {
			_mainLock.unlock();
		}
	}

	public int getCorePoolSize() {
		return _corePoolSize;
	}

	public long getKeepAliveTime(TimeUnit timeUnit) {
		return timeUnit.convert(_keepAliveTime, TimeUnit.NANOSECONDS);
	}

	public int getLargestPoolSize() {
		return _largestPoolSize;
	}

	public int getMaxPoolSize() {
		return _maxPoolSize;
	}

	public int getPendingTaskCount() {
		return _taskQueue.size();
	}

	public int getPoolSize() {
		return _poolSize;
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return _rejectedExecutionHandler;
	}

	public int getRemainingTaskQueueCapacity() {
		return _taskQueue.remainingCapacity();
	}

	public long getTaskCount() {
		_mainLock.lock();
		try {
			long count = _completedTaskCount;
			for (WorkerTask worker : _workerTaskSet) {
				count += worker._localCompletedTaskCount;
				if (worker._isLocked()) {
					count++;
				}
			}
			return count + _taskQueue.size();
		}
		finally {
			_mainLock.unlock();
		}
	}

	public ThreadFactory getThreadFactory() {
		return _threadFactory;
	}

	public ThreadPoolHandler getThreadPoolHandler() {
		return _threadPoolHandler;
	}

	public boolean isAllowCoreThreadTimeOut() {
		return _allowCoreThreadTimeOut;
	}

	public boolean isShutdown() {
		return _runState != _RUNNING;
	}

	public boolean isTerminated() {
		return _runState == _TERMINATED;
	}

	public boolean isTerminating() {
		return _runState == _STOP;
	}

	public void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeOut) {
		_allowCoreThreadTimeOut = allowCoreThreadTimeOut;
	}

	public void setKeepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
		if (keepAliveTime < 0) {
			throw new IllegalArgumentException();
		}
		_keepAliveTime = timeUnit.toNanos(keepAliveTime);
	}

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {
		if (rejectedExecutionHandler == null) {
			throw new NullPointerException();
		}
		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setThreadFactory(ThreadFactory threadFactory) {
		if (threadFactory == null) {
			throw new NullPointerException();
		}
		_threadFactory = threadFactory;
	}

	public void setThreadPoolHandler(ThreadPoolHandler threadPoolHandler) {
		if (threadPoolHandler == null) {
			throw new NullPointerException();
		}
		_threadPoolHandler = threadPoolHandler;
	}

	public void shutdown() {
		_mainLock.lock();
		try {
			int state = _runState;
			if (state < _SHUTDOWN) {
				_runState = _SHUTDOWN;
			}

			for (WorkerTask worker : _workerTaskSet) {
				worker._interruptIfWaiting();
			}

			_tryTerminate();
		}
		finally {
			_mainLock.unlock();
		}
	}

	public List<Runnable> shutdownNow() {
		_mainLock.lock();
		try {
			int state = _runState;
			if (state < _STOP) {
				_runState = _STOP;
			}

			for (WorkerTask worker : _workerTaskSet) {
				worker._thread.interrupt();
			}

			List<Runnable> tasks = new ArrayList<Runnable>();
			_taskQueue.drainTo(tasks);
			_tryTerminate();
			return tasks;
		}
		finally {
			_mainLock.unlock();
		}
	}

	protected void finalize() {
		shutdown();
	}

	/**
	 * Unit test probe, should not be used other than unit test purpose.
	 * Abusing this method could easily cause deadlock or put executor into an
	 * inconsistent state.
	 */
	protected ReentrantLock getMainLock() {
		return _mainLock;
	}

	/**
	 * Unit test probe, should not be used other than unit test purpose.
	 * Abusing this method could easily cause deadlock or put executor into an
	 * inconsistent state.
	 */
	protected TaskQueue<Runnable> getTaskQueue() {
		return _taskQueue;
	}

	/**
	 * Unit test probe, should not be used other than unit test purpose.
	 * Abusing this method could easily cause deadlock or put executor into an
	 * inconsistent state.
	 */
	protected Set<WorkerTask> getWorkerTaskSet() {
		return _workerTaskSet;
	}

	/**
	 * Try to add a new worker thread. Will success when executor is in _RUNNING
	 * state and has not reached _maxPoolSize yet, or executor is in _SHUTDOWN
	 * state, _poolSize is 0 and _taskQueue is not empty.
	 */
	private void _addWorkerThread() {
		// Reduce volatile read
		int runState = _runState;
		int poolSize = _poolSize;
		if ((runState == _RUNNING && poolSize < _maxPoolSize) ||
			(runState == _SHUTDOWN && poolSize == 0 && !_taskQueue.isEmpty())) {
			// Appears to be eligible for adding, needs recheck with _mainLock.
			_mainLock.lock();
			try {
				runState = _runState;
				poolSize = _poolSize;
				if ((runState == _RUNNING && poolSize < _maxPoolSize) ||
					(runState == _SHUTDOWN && poolSize == 0 &&
						!_taskQueue.isEmpty())) {
					_doAddWorkerThread(_taskQueue.poll());
				}
			}
			finally {
				_mainLock.unlock();
			}
		}
	}

	private void _doAddWorkerThread(Runnable task) {
		WorkerTask worker = new WorkerTask(task);
		_workerTaskSet.add(worker);
		int poolSize = ++_poolSize;
		if (poolSize > _largestPoolSize) {
			_largestPoolSize = poolSize;
		}
		worker._startWork();
	}

	private Runnable _getTask(WorkerTask workerTask, boolean[] cleanUpMarker) {
		while (true) {
			try {
				int state = _runState;
				if (state >= _STOP) {
					// Stop polling task after passing SHUTDOWN state
					// This is a dirty exit, leave the cleanUpMarker unmarked,
					// let the outter code to clean up.
					return null;
				}

				Runnable runnable = null;
				if (state == _SHUTDOWN) {
					// Keep draining the _taskQueue in _SHUTDOWN state
					runnable = _taskQueue.poll();
				}
				else if (_poolSize > _corePoolSize || _allowCoreThreadTimeOut) {
					// Timeout polling
					runnable = _taskQueue.poll(
						_keepAliveTime, TimeUnit.NANOSECONDS);
				}
				else {
					// Infinite wait polling, can be waked up by shutdownNow or
					// adjustPoolSize interruption before elements become
					// available
					runnable = _taskQueue.take();
				}

				if (runnable != null) {
					// Success polling task. A recheck _runState is required in
					// _runTask() to compensate the possible concurrent shutdown
					// after previous _runState checking
					return runnable;
				}

				// Polling failed, check should exit or retry
				_mainLock.lock();
				try {
					if (_runState >= _STOP || // Stop polling in _STOP state
						(_runState >= _SHUTDOWN && _taskQueue.isEmpty()) ||
						// Stop polling, the _taskQueue is Empty
						(_allowCoreThreadTimeOut &&
							(_poolSize > 1 || _taskQueue.isEmpty())) ||
						// Core thread can timeout, and there is more than 1
						// thread exist, or _taskQueue is not empty
						(!_allowCoreThreadTimeOut &&
							_poolSize > _corePoolSize)) {
						// Core thread can't timeout, but there is more than
						// _corePoolSize threads exist.

						// Merge completeTaskCount
						_completedTaskCount +=
							workerTask._localCompletedTaskCount;
						// Remove current worker thread and try to terminate the
						// executor
						_workerTaskSet.remove(workerTask);
						if (--_poolSize == 0) {
							_tryTerminate();
						}
						// mark this exit is already cleaned up.
						cleanUpMarker[0] = true;
						return null;
					}
				}
				finally {
					_mainLock.unlock();
				}
			}
			catch (InterruptedException ie) {
				// retry
			}
		}
	}

	private void _tryTerminate() {
		if (_poolSize == 0) {
			int state = _runState;
			// Transfer to _TERMINATED state when state is _STOP or state is
			// _SHUTDOWN and _taskQueue is empty
			if (state == _STOP
				|| (state == _SHUTDOWN && _taskQueue.isEmpty())) {
				_runState = _TERMINATED;
				_terminationCondition.signalAll();
				_threadPoolHandler.terminated();
				return;
			}

			// state is not _STOP, it could be _RUNNING or _SHUTDOWN with
			// a non-empty _taskQueue. If _taskQueue is not empty, needs to
			// launch a new Thread to drain _taskQueue.
			if (!_taskQueue.isEmpty()) {
				_doAddWorkerThread(_taskQueue.poll());
			}
		}
	}

	/**
	 * Life-cycle states.
	 */
	private static final int _RUNNING = 0;
	private static final int _SHUTDOWN = 1;
	private static final int _STOP = 2;
	private static final int _TERMINATED = 3;

	/**
	 * Guardian lock for updating _runState, _corePoolSize, _maxPoolSize,
	 * _poolSize and _workerTaskSet.
	 */
	private final ReentrantLock _mainLock = new ReentrantLock();
	private final Condition _terminationCondition = _mainLock.newCondition();

	/**
	 * Self maintain states
	 */
	private volatile int _runState;
	private volatile int _poolSize;
	private volatile int _largestPoolSize;
	private long _completedTaskCount;
	private final TaskQueue<Runnable> _taskQueue;
	private final Set<WorkerTask> _workerTaskSet;

	/**
	 * User control states
	 */
	private volatile int _corePoolSize;
	private volatile int _maxPoolSize;
	private volatile long _keepAliveTime;
	private volatile boolean _allowCoreThreadTimeOut;
	private volatile RejectedExecutionHandler _rejectedExecutionHandler;
	private volatile ThreadFactory _threadFactory;
	private volatile ThreadPoolHandler _threadPoolHandler;

	private class WorkerTask
		extends AbstractQueuedSynchronizer implements Runnable {

		public WorkerTask(Runnable task) {
			_task = task;
		}

		public void run() {
			boolean[] cleanUpMarker = new boolean[1];
			try {
				Runnable task = _task;
				do {
					if (task != null) {
						_runTask(task);
						// Release task before calling getTask() to help GC
						task = null;
					}
				}
				while ((task = _getTask(this, cleanUpMarker)) != null);
			}
			finally {
				if (!cleanUpMarker[0]) {
					// Ending worker thread by a dirty exit, needs clean up.
					_mainLock.lock();
					try {
						// Merge the completed task count
						_completedTaskCount += _localCompletedTaskCount;
						_workerTaskSet.remove(this);
						if (--_poolSize == 0) {
							_tryTerminate();
						}
					}
					finally {
						_mainLock.unlock();
					}
				}

				_threadPoolHandler.beforeThreadEnd(_thread);
			}
		}

		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		protected boolean tryAcquire(int unused) {
			return compareAndSetState(0, 1);
		}

		protected boolean tryRelease(int unused) {
			setState(0);
			return true;
		}

		private boolean _interruptIfWaiting() {
			if (!_thread.isInterrupted() && tryAcquire(1)) {
				try {
					_thread.interrupt();
					return true;
				}
				finally {
					_unlock();
				}
			}
			return false;
		}

		private boolean _isLocked() {
			return isHeldExclusively();
		}

		private void _lock() {
			acquire(1);
		}

		private void _runTask(Runnable task) {
			_lock();
			try {
				// Double-check concurrent shutdownNow invoking
				if (_runState < _STOP && Thread.interrupted() && 
					_runState >= _STOP) {
					_thread.interrupt();
				}

				Throwable throwable = null;
				_threadPoolHandler.beforeExecute(_thread, task);
				try {
					task.run();
					_localCompletedTaskCount++;
				}
				catch (RuntimeException x) {
					throwable = x;
					throw x;
				}
				finally {
					_threadPoolHandler.afterExecute(task, throwable);
				}
			}
			finally {
				_unlock();
			}
		}

		private void _startWork() {
			_thread = _threadFactory.newThread(this);

			_threadPoolHandler.beforeThreadStart(_thread);

			_thread.start();
		}

		private void _unlock() {
			release(1);
		}

		private volatile long _localCompletedTaskCount;
		private final Runnable _task;
		private Thread _thread;
	}

}