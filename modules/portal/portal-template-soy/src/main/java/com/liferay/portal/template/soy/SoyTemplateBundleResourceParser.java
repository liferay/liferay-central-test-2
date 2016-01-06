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

package com.liferay.portal.template.soy;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.URLResourceParser;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Marcellus Tavares
 */
public class SoyTemplateBundleResourceParser extends URLResourceParser {

	public SoyTemplateBundleResourceParser() {
		Bundle bundle = FrameworkUtil. getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		int stateMask = Bundle.ACTIVE | Bundle.RESOLVED;

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask,
			new CapabilityBundleTrackerCustomizer("soy"));

		_bundleTracker.open();
	}

	@Override
	public URL getURL(String templateId) {
		int pos = templateId.indexOf(TemplateConstants.BUNDLE_SEPARATOR);

		if (pos == -1) {
			throw new IllegalArgumentException(
				String.format(
					"The templateId \"%s\" does not map to a Soy template",
					templateId));
		}

		String capabilityPrefix = templateId.substring(0, pos);

		Bundle bundle = _bundleProvidersMap.get(capabilityPrefix);

		if (bundle == null) {
			throw new IllegalStateException(
				"There are no bundles providing " + capabilityPrefix);
		}

		String templateName = templateId.substring(
			pos + TemplateConstants.BUNDLE_SEPARATOR.length());

		return bundle.getResource(templateName);
	}

	@Override
	protected void finalize() throws Throwable {
		_bundleTracker.close();
	}

	protected String getCapabilityPrefix(BundleCapability bundleCapability) {
		Map<String, Object> attributes = bundleCapability.getAttributes();

		StringBundler sb = new StringBundler(3);

		sb.append(attributes.get("type"));
		sb.append(StringPool.UNDERLINE);
		sb.append(attributes.get("version"));

		return sb.toString();
	}

	private final Map<String, Bundle> _bundleProvidersMap =
		new ConcurrentHashMap<>();
	private final BundleTracker<Bundle> _bundleTracker;

	private class CapabilityBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Bundle> {

		public CapabilityBundleTrackerCustomizer(String namespace) {
			_namespace = namespace;
		}

		@Override
		public Bundle addingBundle(Bundle bundle, BundleEvent event) {
			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			List<BundleCapability> capabilities = bundleWiring.getCapabilities(
				_namespace);

			for (BundleCapability bundleCapability : capabilities) {
				String providerBundleKey = getCapabilityPrefix(
					bundleCapability);

				_bundleProvidersMap.put(providerBundleKey, bundle);
			}

			return bundle;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent event, Bundle object) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent event, Bundle object) {

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			List<BundleCapability> capabilities = bundleWiring.getCapabilities(
				_namespace);

			for (BundleCapability bundleCapability : capabilities) {
				String providerBundleKey = getCapabilityPrefix(
					bundleCapability);

				_bundleProvidersMap.remove(providerBundleKey);
			}
		}

		private final String _namespace;

	}

}