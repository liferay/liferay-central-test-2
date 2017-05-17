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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.soy.utils.SoyTemplateUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(service = SoyProviderCapabilityBundleRegister.class)
public class SoyProviderCapabilityBundleRegister {

	public Bundle getBundle(long bundleId) {
		return _bundles.get(bundleId);
	}

	public Collection<Bundle> getBundles() {
		return _bundles.values();
	}

	public Bundle getTemplateBundle(String templateId) {
		long bundleId = SoyTemplateUtil.getBundleId(templateId);

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			Collection<Bundle> bundles = getBundles();

			StringBundler sb = new StringBundler(bundles.size() * 2);

			for (Bundle registredBundle : bundles) {
				sb.append(registredBundle.getSymbolicName());
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			_log.error(String.format("Registred bundles %s", sb.toString()));

			throw new IllegalStateException(
				"There are no bundles providing " + templateId);
		}

		return bundle;
	}

	public void register(Bundle bundle) {
		_bundles.put(bundle.getBundleId(), bundle);
	}

	public void unregister(Bundle bundle) {
		_bundles.remove(bundle.getBundleId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyProviderCapabilityBundleRegister.class);

	private static final Map<Long, Bundle> _bundles = new ConcurrentHashMap<>();

}