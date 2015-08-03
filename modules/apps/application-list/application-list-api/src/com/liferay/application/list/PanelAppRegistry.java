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

package com.liferay.application.list;

import com.liferay.application.list.util.PanelCategoryServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PanelAppRegistry.class)
public class PanelAppRegistry {

	public List<PanelApp> getPanelApps(PanelCategory parentPanelCategory) {
		List<PanelApp> panelApps = _serviceTrackerMap.getService(
			parentPanelCategory.getKey());

		if (panelApps == null) {
			return Collections.emptyList();
		}

		return panelApps;
	}

	public List<PanelApp> getPanelApps(
		PanelCategory parentPanelCategory,
		final PermissionChecker permissionChecker, final Group group) {

		List<PanelApp> panelApps = getPanelApps(parentPanelCategory);

		if (panelApps.isEmpty()) {
			return panelApps;
		}

		return ListUtil.filter(
			panelApps,
			new PredicateFilter<PanelApp>() {

				@Override
				public boolean filter(PanelApp panelApp) {
					try {
						return panelApp.hasAccessPermission(
							permissionChecker, group);
					}
					catch (PortalException e) {
						_log.error(e);
					}

					return false;
				}

			});
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
			bundleContext, PanelApp.class, "(panel.category.key=*)",
			new PanelCategoryServiceReferenceMapper(),
			new ServiceRankingPropertyServiceReferenceComparator());

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelAppRegistry.class);

	private ServiceTrackerMap<String, List<PanelApp>> _serviceTrackerMap;

	private static class ServiceRankingPropertyServiceReferenceComparator
		extends PropertyServiceReferenceComparator<PanelApp> {

		public ServiceRankingPropertyServiceReferenceComparator() {
			super("service.ranking");
		}

		@Override
		public int compare(
			ServiceReference<PanelApp> serviceReference1,
			ServiceReference<PanelApp> serviceReference2) {

			return -(super.compare(serviceReference1, serviceReference2));
		}

	}

}