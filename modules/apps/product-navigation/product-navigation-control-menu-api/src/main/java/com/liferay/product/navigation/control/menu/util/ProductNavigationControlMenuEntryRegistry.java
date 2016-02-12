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

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true, service = ProductNavigationControlMenuEntryRegistry.class
)
public class ProductNavigationControlMenuEntryRegistry {

	public List<ProductNavigationControlMenuEntry> getControlMenuEntries(
		ProductNavigationControlMenuCategory controlMenuCategory) {

		List<ProductNavigationControlMenuEntry> controlMenuEntries =
			_serviceTrackerMap.getService(controlMenuCategory.getKey());

		if (controlMenuEntries == null) {
			return Collections.emptyList();
		}

		return controlMenuEntries;
	}

	public List<ProductNavigationControlMenuEntry> getControlMenuEntries(
		ProductNavigationControlMenuCategory controlMenuCategory,
		final HttpServletRequest request) {

		List<ProductNavigationControlMenuEntry> controlMenuEntries =
			getControlMenuEntries(controlMenuCategory);

		if (controlMenuEntries.isEmpty()) {
			return controlMenuEntries;
		}

		return ListUtil.filter(
			controlMenuEntries,
			new PredicateFilter<ProductNavigationControlMenuEntry>() {

				@Override
				public boolean filter(
					ProductNavigationControlMenuEntry controlMenuEntry) {

					try {
						return controlMenuEntry.isShow(request);
					}
					catch (PortalException pe) {
						_log.error(pe, pe);
					}

					return false;
				}

			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ProductNavigationControlMenuEntry.class,
			"(product.navigation.control.menu.category.key=*)",
			new ProductNavigationControlMenuEntryServiceReferenceMapper(),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator("service.ranking")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductNavigationControlMenuEntryRegistry.class);

	private ServiceTrackerMap<String, List<ProductNavigationControlMenuEntry>>
		_serviceTrackerMap;

}