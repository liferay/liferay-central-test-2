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

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceParser;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	service = {SoyTemplateContextHelper.class, TemplateContextHelper.class}
)
public class SoyTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Map<String, Object> getHelperUtilities(
		ClassLoader classLoader, boolean restricted) {

		return Collections.emptyMap();
	}

	@Override
	public Set<String> getRestrictedVariables() {
		return Collections.emptySet();
	}

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		contextObjects.put("locale", themeDisplay.getLocale());
		contextObjects.put("themeDisplay", themeDisplay);

		// Custom template context contributors

		for (TemplateContextContributor templateContextContributor :
				_templateContextContributors) {

			templateContextContributor.prepare(contextObjects, request);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		int stateMask = Bundle.ACTIVE | Bundle.RESOLVED;

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask,
			new CapabilityBundleTrackerCustomizer("soy"));

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + TemplateContextContributor.TYPE_GLOBAL + ")",
		unbind = "unregisterTemplateContextContributor"
	)
	protected void registerTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.add(templateContextContributor);
	}

	protected synchronized void unregisterTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.remove(templateContextContributor);
	}

	private final Map<Long, Bundle> _bundleProvidersMap =
		new ConcurrentHashMap<>();
	private BundleTracker<List<BundleCapability>> _bundleTracker;
	private final List<TemplateContextContributor>
		_templateContextContributors = new CopyOnWriteArrayList<>();

	@Reference(
		target = "(lang.type=" + TemplateConstants.LANG_TYPE_SOY + ")",
		unbind = "-"
	)
	private TemplateResourceParser _templateResourceParser;

	private class CapabilityBundleTrackerCustomizer
		implements BundleTrackerCustomizer<List<BundleCapability>> {

		public CapabilityBundleTrackerCustomizer(String namespace) {
			_namespace = namespace;
		}

		@Override
		public List<BundleCapability> addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			List<BundleCapability> bundleCapabilities =
				bundleWiring.getCapabilities(_namespace);

			_bundleProvidersMap.put(bundle.getBundleId(), bundle);

			return bundleCapabilities;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			List<BundleCapability> bundleCapabilities) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			List<BundleCapability> bundleCapabilities) {

			_bundleProvidersMap.remove(bundle.getBundleId());
		}

		private final String _namespace;

	}

}