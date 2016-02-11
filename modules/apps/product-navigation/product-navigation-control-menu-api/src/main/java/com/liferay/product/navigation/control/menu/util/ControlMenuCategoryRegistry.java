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

package com.liferay.product.navigation.control.menu.util;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = ControlMenuCategoryRegistry.class)
public class ControlMenuCategoryRegistry {

	public List<ProductNavigationControlMenuCategory> getControlMenuCategories(
		String controlMenuCategoryKey) {

		List<ProductNavigationControlMenuCategory> controlMenuCategories =
			_controlMenuCategoryServiceTrackerMap.getService(
				controlMenuCategoryKey);

		if (controlMenuCategories == null) {
			return Collections.emptyList();
		}

		return new ArrayList<>(controlMenuCategories);
	}

	public List<ProductNavigationControlMenuCategory> getControlMenuCategories(
		String controlMenuCategoryKey, final HttpServletRequest request) {

		List<ProductNavigationControlMenuCategory> controlMenuCategories =
			getControlMenuCategories(controlMenuCategoryKey);

		if (controlMenuCategories.isEmpty()) {
			return controlMenuCategories;
		}

		return ListUtil.filter(
			controlMenuCategories,
			new PredicateFilter<ProductNavigationControlMenuCategory>() {

				@Override
				public boolean filter(
					ProductNavigationControlMenuCategory controlMenuCategory) {

					try {
						if (!controlMenuCategory.hasAccessPermission(request)) {
							return false;
						}

						List<ProductNavigationControlMenuEntry>
							controlMenuEntries =
								_controlMenuEntryRegistry.getControlMenuEntries(
									controlMenuCategory, request);

						if (controlMenuEntries.isEmpty()) {
							return false;
						}

						return true;
					}
					catch (PortalException pe) {
						_log.error(pe, pe);
					}

					return false;
				}

			});
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_controlMenuCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ProductNavigationControlMenuCategory.class,
				"(control.menu.category.key=*)",
				new ControlMenuCategoryServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator("service.ranking")));
	}

	@Deactivate
	protected void deactivate() {
		_controlMenuCategoryServiceTrackerMap.close();
	}

	@Reference(unbind = "-")
	protected void setControlMenuEntryRegistry(
		ControlMenuEntryRegistry controlMenuEntryRegistry) {

		_controlMenuEntryRegistry = controlMenuEntryRegistry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ControlMenuCategoryRegistry.class);

	private ServiceTrackerMap
		<String, List<ProductNavigationControlMenuCategory>>
			_controlMenuCategoryServiceTrackerMap;
	private ControlMenuEntryRegistry _controlMenuEntryRegistry;

}