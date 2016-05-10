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

package com.liferay.portal.workflow.kaleo.runtime.internal.messaging;

import com.liferay.portal.kernel.concurrent.CallerRunsPolicy;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.kernel.workflow.messaging.DefaultWorkflowDestinationEventListener;
import com.liferay.portal.workflow.kaleo.runtime.internal.timer.messaging.TimerMessageListener;
import com.liferay.portal.workflow.kaleo.runtime.messaging.DestinationNames;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = KaleoWorkflowMessagingConfigurator.class)
public class KaleoWorkflowMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		registerKaleoGraphWalkerDestination();

		registerWorkflowDefinitionLinkDestination();

		wireKaleoWorkflowMessageListeners();

		registerWorkflowTimerDestination();

		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			_timerMessageListener);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", DestinationNames.WORKFLOW_TIMER);

		_schedulerEventMessageListenerServiceRegistration =
			_bundleContext.registerService(
				MessageListener.class, schedulerEventMessageListenerWrapper,
				properties);
	}

	@Deactivate
	protected void deactivate() {

		for (Map.Entry<String, MessageListener> entry:
				_proxyMessageListeners.entrySet()) {

			_messageBus.unregisterMessageListener(
				entry.getKey(), entry.getValue());
		}

		if (!_serviceRegistrations.isEmpty()) {
			for (ServiceRegistration<Destination> serviceRegistration :
					_serviceRegistrations.values()) {

				Destination destination = _bundleContext.getService(
					serviceRegistration.getReference());

				serviceRegistration.unregister();

				destination.destroy();
			}

			_serviceRegistrations.clear();
		}

		if (_schedulerEventMessageListenerServiceRegistration != null) {
			_schedulerEventMessageListenerServiceRegistration.unregister();

			_schedulerEventMessageListenerServiceRegistration = null;
		}

		_bundleContext = null;
	}

	protected void registerDestination(
		DestinationConfiguration kaleoGraphWalkerDestinationConfiguration) {

		Destination destination = _destinationFactory.createDestination(
			kaleoGraphWalkerDestinationConfiguration);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());

		ServiceRegistration<Destination> serviceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, properties);

		_serviceRegistrations.put(destination.getName(), serviceRegistration);
	}

	protected void registerKaleoGraphWalkerDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.KALEO_GRAPH_WALKER);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the graph walker's task queue is at " +
									"its maximum capacity");
					}

					super.rejectedExecution(runnable, threadPoolExecutor);
				}

			};

		destinationConfiguration.setRejectedExecutionHandler(
			rejectedExecutionHandler);

		registerDestination(destinationConfiguration);
	}

	protected MessageListener registerProxyMessageListener(
		Object manager, String destinationName) {

		ProxyMessageListener proxyMessageListener = new ProxyMessageListener();

		proxyMessageListener.setManager(manager);
		proxyMessageListener.setMessageBus(_messageBus);

		_messageBus.registerMessageListener(
			destinationName, proxyMessageListener);

		_proxyMessageListeners.put(destinationName, proxyMessageListener);

		return proxyMessageListener;
	}

	protected void registerWorkflowDefinitionLinkDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				DestinationNames.WORKFLOW_DEFINITION_LINK);

		registerDestination(destinationConfiguration);
	}

	protected void registerWorkflowTimerDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.WORKFLOW_TIMER);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the workflow timer task queue is at " +
									"its maximum capacity");
					}

					super.rejectedExecution(runnable, threadPoolExecutor);
				}

			};

		destinationConfiguration.setRejectedExecutionHandler(
			rejectedExecutionHandler);

		registerDestination(destinationConfiguration);
	}

	protected void wireKaleoWorkflowMessageListeners() {
		DefaultWorkflowDestinationEventListener
			defaultWorkflowDestinationEventListener =
				new DefaultWorkflowDestinationEventListener();

		defaultWorkflowDestinationEventListener.setWorkflowEngineName(
			"Liferay Kaleo Workflow Engine");

		MessageListener workflowComparatorMessageListener =
			registerProxyMessageListener(
				_workflowComparatorFactory,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_COMPARATOR);

		defaultWorkflowDestinationEventListener.
			setWorkflowComparatorFactoryListener(
				workflowComparatorMessageListener);

		MessageListener workflowDefinitionManagerProxyMessageListener =
			registerProxyMessageListener(
				_workflowDefinitionManager,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_DEFINITION);

		defaultWorkflowDestinationEventListener.
			setWorkflowDefinitionManagerListener(
				workflowDefinitionManagerProxyMessageListener);

		MessageListener workflowEngineManagerProxyMessageListener =
			registerProxyMessageListener(
				_workflowEngineManager,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_ENGINE);

		defaultWorkflowDestinationEventListener.
			setWorkflowEngineManagerListener(
				workflowEngineManagerProxyMessageListener);

		MessageListener workflowInstanceManagerProxyMessageListener =
			registerProxyMessageListener(
				_workflowInstanceManager,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_INSTANCE);

		defaultWorkflowDestinationEventListener.
			setWorkflowInstanceManagerListener(
				workflowInstanceManagerProxyMessageListener);

		MessageListener workflowLogManagerProxyMessageListener =
			registerProxyMessageListener(
				_workflowLogManagerk,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_LOG);

		defaultWorkflowDestinationEventListener.setWorkflowLogManagerListener(
			workflowLogManagerProxyMessageListener);

		MessageListener workflowTaskManagerProxyMessageListener =
			registerProxyMessageListener(
				_workflowTaskManager,
				com.liferay.portal.kernel.messaging.DestinationNames.
					WORKFLOW_TASK);

		defaultWorkflowDestinationEventListener.setWorkflowTaskManagerListener(
			workflowTaskManagerProxyMessageListener);

		_workflowEngineDestination.addDestinationEventListener(
			defaultWorkflowDestinationEventListener);
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoWorkflowMessagingConfigurator.class);

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private MessageBus _messageBus;

	private ServiceRegistration<MessageListener>
		_schedulerEventMessageListenerServiceRegistration;
	private final Map<String, ServiceRegistration<Destination>>
		_serviceRegistrations = new HashMap<>();

	@Reference
	private TimerMessageListener _timerMessageListener;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference(
		target = "(destination.name=" + com.liferay.portal.kernel.messaging.DestinationNames.WORKFLOW_ENGINE + ")"
	)
	private Destination _workflowEngineDestination;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowEngineManager _workflowEngineManager;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowLogManager _workflowLogManagerk;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowTaskManager _workflowTaskManager;

	private Map<String, MessageListener> _proxyMessageListeners =
		new HashMap<>();

}