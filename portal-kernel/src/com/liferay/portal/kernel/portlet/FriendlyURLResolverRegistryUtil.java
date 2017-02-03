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

package com.liferay.portal.kernel.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Garcia
 * @author Raymond Augé
 */
@ProviderType
public class FriendlyURLResolverRegistryUtil {

	public static FriendlyURLResolver getFriendlyURLResolver(
		String urlSeparator) {

		return _serviceTrackerMap.getService(urlSeparator);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getFriendlyURLResolversAsCollection()}
	 */
	@Deprecated
	public static List<FriendlyURLResolver> getFriendlyURLResolvers() {
		return new ArrayList<>(getFriendlyURLResolversAsCollection());
	}

	public static Collection<FriendlyURLResolver>
		getFriendlyURLResolversAsCollection() {

		List<FriendlyURLResolver> list = new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			FriendlyURLResolver friendlyURLResolver =
				_serviceTrackerMap.getService(key);

			if (friendlyURLResolver != null) {
				list.add(friendlyURLResolver);
			}
		}

		return list;
	}

	public static String[] getURLSeparators() {
		Set<String> urlSeparators = _serviceTrackerMap.keySet();

		return urlSeparators.toArray(new String[urlSeparators.size()]);
	}

	public static void register(FriendlyURLResolver friendlyURLResolver) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			registry.registerService(
				FriendlyURLResolver.class, friendlyURLResolver);

		_serviceRegistrations.put(friendlyURLResolver, serviceRegistration);
	}

	public static void unregister(FriendlyURLResolver friendlyURLResolver) {
		ServiceRegistration<FriendlyURLResolver> serviceRegistration =
			_serviceRegistrations.remove(friendlyURLResolver);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ServiceRegistrationMap<FriendlyURLResolver>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();

	private static final ServiceTrackerMap<String, FriendlyURLResolver>
		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			FriendlyURLResolver.class, null,
			new ServiceReferenceMapper<String, FriendlyURLResolver>() {

				@Override
				public void map(
					ServiceReference<FriendlyURLResolver> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					FriendlyURLResolver friendlyURLResolver =
						registry.getService(serviceReference);

					emitter.emit(friendlyURLResolver.getURLSeparator());

					registry.ungetService(serviceReference);
				}

			});

}