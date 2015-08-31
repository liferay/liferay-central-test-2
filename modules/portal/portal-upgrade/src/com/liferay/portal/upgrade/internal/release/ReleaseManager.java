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

package com.liferay.portal.upgrade.internal.release;

import com.liferay.osgi.service.tracker.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.RunnableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.constants.UpgradeStepConstants;
import com.liferay.portal.upgrade.internal.UpgradeInfo;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Collections;
import java.util.List;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=execute", "osgi.command.function=list",
		"osgi.command.scope=upgrade"
	},
	service = Object.class
)
public class ReleaseManager {

	public void execute(String componentName) throws PortalException {
		List<UpgradeInfo> upgradeInfos = _serviceTrackerMap.getService(
			componentName);

		String version = _getVersion(componentName);

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			version);

		executeUpgradePath(componentName, upgradePath);
	}

	public void execute(String componentName, String to)
		throws PortalException {

		List<UpgradeInfo> upgradeInfos = _serviceTrackerMap.getService(
			componentName);

		String version = _getVersion(componentName);

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		List<UpgradeInfo> upgradePath = releaseGraphManager.getUpgradeInfos(
			version, to);

		executeUpgradePath(componentName, upgradePath);
	}

	public void list() {
		for (String key : _serviceTrackerMap.keySet()) {
			list(key);
		}
	}

	public void list(String componentName) {
		List<UpgradeInfo> upgradeProcesses = _serviceTrackerMap.getService(
			componentName);

		System.out.println(
			"Registered upgrade commands for component " + componentName +
				" (" + _getVersion(componentName) + ")");

		for (UpgradeInfo upgradeProcess : upgradeProcesses) {
			System.out.println("\t" + upgradeProcess);
		}
	}

	@Reference
	public void setOutputStreamTracker(
		OutputStreamContainerFactoryTracker
			outputStreamContainerFactoryTracker) {

		_outputStreamContainerFactoryTracker =
			outputStreamContainerFactoryTracker;
	}

	@Activate
	protected void activate(final BundleContext bundleContext)
		throws InvalidSyntaxException, UpgradeException {

		_logger = new Logger(bundleContext);

		_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
			bundleContext, UpgradeStep.class,
				"(&(" + UpgradeStepConstants.APPLICATION_NAME +
					"=*)(|(database=" +
						UpgradeStepConstants.ALL_DATABASES +
							")(database=" + DBFactoryUtil.getDB().getType() +
								")))",
			new PropertyServiceReferenceMapper<String, UpgradeStep>(
				UpgradeStepConstants.APPLICATION_NAME),
			new UpgradeCustomizer(bundleContext),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<UpgradeStep>(
					UpgradeStepConstants.FROM)));

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected void executeUpgradePath(
		final String componentName, final List<UpgradeInfo> upgradeInfos) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory();

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create("upgrade-" + componentName);

		final OutputStream outputStream =
			outputStreamContainer.getOutputStream();

		RunnableUtil.runWithSwappedSystemOut(
			new Runnable() {

				@Override
				public void run() {
					for (UpgradeInfo upgradeInfo : upgradeInfos) {
						UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

						try {
							upgradeStep.upgrade(new DBProcessContext() {

								@Override
								public DBContext getDBContext() {
									return new DBContext();
								}

								@Override
								public OutputStream getOutputStream() {
									return outputStream;
								}
							});

							_releaseLocalService.updateRelease(
								componentName, upgradeInfo.getToVersionString(),
								upgradeInfo.getFromVersionString());
						}
						catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}

			}, outputStream);

		try {
			outputStream.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		Release release = _releaseLocalService.fetchRelease(componentName);

		if (release != null) {
			_releasePublisher.publish(release);
		}
	}

	@Reference
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference
	protected void setReleasePublisher(ReleasePublisher releasePublisher) {
		_releasePublisher = releasePublisher;
	}

	private String _getVersion(String servletContextName) {
		Release release = _releaseLocalService.fetchRelease(servletContextName);

		if ((release == null) || Validator.isNull(release.getVersion())) {
			return "0.0.0";
		}
		else {
			return release.getVersion();
		}
	}

	private static Logger _logger;

	private OutputStreamContainerFactoryTracker
		_outputStreamContainerFactoryTracker;
	private ReleaseLocalService _releaseLocalService;
	private ReleasePublisher _releasePublisher;
	private ServiceTrackerMap<String, List<UpgradeInfo>> _serviceTrackerMap;

	private static class UpgradeCustomizer
			implements ServiceTrackerCustomizer<UpgradeStep, UpgradeInfo> {

		public UpgradeCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public UpgradeInfo addingService(
			ServiceReference<UpgradeStep> serviceReference) {

			String from = (String)serviceReference.getProperty(
				UpgradeStepConstants.FROM);
			String to = (String)serviceReference.getProperty(
				UpgradeStepConstants.TO);

			UpgradeStep upgradeStepProcess = _bundleContext.getService(
				serviceReference);

			if (upgradeStepProcess == null) {
				_logger.log(
					Logger.LOG_WARNING,
					"Service " + serviceReference + " is registered as " +
						"an upgrade but it is not implementing Upgrade " +
							"interface. Not tracking.");

				return null;
			}

			return new UpgradeInfo(from, to, upgradeStepProcess);
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}