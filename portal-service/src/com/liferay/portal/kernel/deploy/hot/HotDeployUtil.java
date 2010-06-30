/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <a href="HotDeployUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class HotDeployUtil {

	public static void fireDeployEvent(HotDeployEvent event) {
		_instance._fireDeployEvent(event);
	}

	public static void fireUndeployEvent(HotDeployEvent event) {
		_instance._fireUndeployEvent(event);
	}

	public static void flushPrematureEvents() {
		_instance._flushPrematureEvents();
	}

	public static void registerListener(HotDeployListener listener) {
		_instance._registerListener(listener);
	}

	public static void unregisterListener(HotDeployListener listener) {
		_instance._unregisterListener(listener);
	}

	public static void unregisterListeners() {
		_instance._unregisterListeners();
	}

	private HotDeployUtil() {
		if (_log.isInfoEnabled()) {
			_log.info("Initializing hot deploy manager " + this.hashCode());
		}

		_dependentEvents = new ArrayList<HotDeployEvent>();
		_deployedServletContextNames = new HashSet<String>();
		_listeners = new CopyOnWriteArrayList<HotDeployListener>();
		_prematureEvents = new ArrayList<HotDeployEvent>();
	}

	private void _doFireDeployEvent(HotDeployEvent event) {
		if (_deployedServletContextNames.contains(
				event.getServletContextName())) {

			return;
		}

		boolean hasDependencies = true;

		Set<String> dependentServletContextNames =
			event.getDependentServletContextNames();

		for (String dependentServletContextName :
				dependentServletContextNames) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				hasDependencies = false;

				break;
			}
		}

		if (hasDependencies) {
			if (_dependentEvents.contains(event)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deploy " + event.getServletContextName() +
							" from queue");
				}
			}

			for (HotDeployListener listener : _listeners) {
				try {
					listener.invokeDeploy(event);
				}
				catch (HotDeployException hde) {
					_log.error(hde, hde);
				}
			}

			_deployedServletContextNames.add(event.getServletContextName());

			_dependentEvents.remove(event);

			List<HotDeployEvent> dependentEvents =
				new ArrayList<HotDeployEvent>(_dependentEvents);

			for (HotDeployEvent dependentEvent : dependentEvents) {
				_doFireDeployEvent(dependentEvent);
			}
		}
		else {
			if (!_dependentEvents.contains(event)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Queue " + event.getServletContextName() +
							" for deploy because its dependencies are not " +
								"available");
				}

				_dependentEvents.add(event);
			}
		}
	}

	private void _fireDeployEvent(HotDeployEvent event) {

		// Capture events that are fired before the portal initialized. These
		// events are later fired by flushPrematureEvents.

		if (_prematureEvents != null) {
			_prematureEvents.add(event);

			return;
		}

		// Fire current event

		_doFireDeployEvent(event);
	}

	private void _fireUndeployEvent(HotDeployEvent event) {
		for (HotDeployListener listener : _listeners) {
			try {
				listener.invokeUndeploy(event);
			}
			catch (HotDeployException hde) {
				_log.error(hde, hde);
			}
		}

		_deployedServletContextNames.remove(event.getServletContextName());
	}

	private void _flushPrematureEvents() {
		for (HotDeployEvent event : _prematureEvents) {
			_doFireDeployEvent(event);
		}

		_prematureEvents = null;
	}

	private void _registerListener(HotDeployListener listener) {
		_listeners.add(listener);
	}

	private void _unregisterListener(HotDeployListener listener) {
		_listeners.remove(listener);
	}

	private void _unregisterListeners() {
		_listeners.clear();
	}

	private static Log _log = LogFactoryUtil.getLog(HotDeployUtil.class);

	private static HotDeployUtil _instance = new HotDeployUtil();

	private List<HotDeployEvent> _dependentEvents;
	private Set<String> _deployedServletContextNames;
	private List<HotDeployListener> _listeners;
	private List<HotDeployEvent> _prematureEvents;

}