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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderMetadataFactory;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration",
	service = {ManagedServiceFactory.class, OpenIdConnectProviderRegistry.class}
)
public class OpenIdConnectProviderRegistryImpl
	implements OpenIdConnectProviderRegistry, ManagedServiceFactory {

	@Override
	public void deleted(String factoryPid) {
		removeOpenConnectIdProvider(factoryPid);
	}

	@Override
	public String getName() {
		return "OpenId Connect Provider Factory";
	}

	@Override
	public OpenIdConnectProvider getOpenIdConnectProvider(String name) {
		return _openIdConnectProvidersPerName.get(name);
	}

	@Override
	public Collection<String> getOpenIdConnectProviderNames() {
		if (_openIdConnectProvidersPerName.isEmpty()) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableCollection(
			_openIdConnectProvidersPerName.keySet());
	}

	@Override
	public void updated(String factoryPid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		OpenIdConnectProviderConfiguration openIdConnectProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenIdConnectProviderConfiguration.class, properties);

		synchronized (_openIdConnectProvidersPerFactory) {
			OpenIdConnectProvider openIdConnectProvider =
				createOpenIdConnectProvider(openIdConnectProviderConfiguration);

			removeOpenConnectIdProvider(factoryPid);

			addOpenConnectIdConnectProvider(factoryPid, openIdConnectProvider);
		}
	}

	protected void addOpenConnectIdConnectProvider(
		String factoryPid, OpenIdConnectProvider openIdConnectProvider) {

		synchronized (_openIdConnectProvidersPerFactory) {
			_openIdConnectProvidersPerFactory.put(
				factoryPid, openIdConnectProvider);

			_openIdConnectProvidersPerName.put(
				openIdConnectProvider.getName(), openIdConnectProvider);
		}
	}

	protected OpenIdConnectProvider createOpenIdConnectProvider(
			OpenIdConnectProviderConfiguration
				openIdConnectProviderConfiguration)
		throws ConfigurationException {

		String providerName = openIdConnectProviderConfiguration.providerName();
		String discoveryEndPoint =
			openIdConnectProviderConfiguration.discoveryEndPoint();

		OpenIdConnectProviderMetadataFactory
			openIdConnectProviderMetadataFactory;

		try {
			if (Validator.isNotNull(discoveryEndPoint)) {
				long discoveryEndPointCacheInMillis =
					openIdConnectProviderConfiguration.
						discoveryEndPointCacheInMillis();

				URL discoveryEndPointURL = new URL(discoveryEndPoint);

				openIdConnectProviderMetadataFactory =
					new OpenIdConnectProviderMetadataFactoryImpl(
						providerName, discoveryEndPointURL,
						discoveryEndPointCacheInMillis);
			}
			else {
				String issuerURL =
					openIdConnectProviderConfiguration.issuerURL();
				String[] subjectTypes =
					openIdConnectProviderConfiguration.subjectTypes();
				String jwks = openIdConnectProviderConfiguration.jwksURI();
				String authorizationEndPoint =
					openIdConnectProviderConfiguration.authorizationEndPoint();
				String tokenEndPoint =
					openIdConnectProviderConfiguration.tokenEndPoint();
				String userInfoEndPoint =
					openIdConnectProviderConfiguration.userInfoEndPoint();

				openIdConnectProviderMetadataFactory =
					new OpenIdConnectProviderMetadataFactoryImpl(
						providerName, issuerURL, subjectTypes, jwks,
						authorizationEndPoint, tokenEndPoint, userInfoEndPoint);
			}
		}
		catch (Exception e) {
			throw new ConfigurationException(
				null,
				"Unable to instantiate Provider Metadata Factory for " +
					openIdConnectProviderConfiguration.providerName() +
						". Invalid configuration",
				e);
		}

		String clientId =
			openIdConnectProviderConfiguration.openIdConnectClientId();
		String clientSecret =
			openIdConnectProviderConfiguration.openIdConnectClientSecret();

		OpenIdConnectProvider openIdConnectProvider = new OpenIdConnectProvider(
			providerName, clientId, clientSecret,
			openIdConnectProviderMetadataFactory);

		return openIdConnectProvider;
	}

	protected void removeOpenConnectIdProvider(String factoryPid) {
		synchronized (_openIdConnectProvidersPerFactory) {
			OpenIdConnectProvider openIdConnectProvider =
				_openIdConnectProvidersPerFactory.remove(factoryPid);

			if (openIdConnectProvider != null) {
				_openIdConnectProvidersPerName.remove(
					openIdConnectProvider.getName());
			}
		}
	}

	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerFactory = new ConcurrentHashMap<>();
	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerName = new ConcurrentHashMap<>();

}