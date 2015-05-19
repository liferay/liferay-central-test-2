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

package com.liferay.portal.settings;

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.FallbackSettings;
import com.liferay.portal.kernel.settings.PortalSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.portal.kernel.settings.definition.SettingsIdMapping;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.settings.util.ConfigurationPidUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = SettingsFactory.class)
@DoPrivileged
public class SettingsFactoryImpl implements SettingsFactory {

	public SettingsFactoryImpl() {
		registerSettingsMetadata(PortalSettings.class, null, null);
	}

	@Override
	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		try {
			PortletItem portletItem = null;

			portletItem = getPortletItem(groupId, portletId, name);

			return new ArchivedSettingsImpl(portletItem);
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	@Override
	public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		List<ArchivedSettings> archivedSettingsList = new ArrayList<>();

		List<PortletItem> portletItems =
			PortletItemLocalServiceUtil.getPortletItems(
				groupId, portletId,
				com.liferay.portal.model.PortletPreferences.class.getName());

		for (PortletItem portletItem : portletItems) {
			archivedSettingsList.add(new ArchivedSettingsImpl(portletItem));
		}

		return archivedSettingsList;
	}

	@Override
	public Settings getServerSettings(String settingsId) {
		Settings portalPropertiesSettings =
			_settingsLocatorHelper.getPortalPropertiesSettings();

		return _settingsLocatorHelper.getConfigurationBeanSettings(
			settingsId, portalPropertiesSettings);
	}

	@Override
	public <T> T getSettings(Class<T> clazz, SettingsLocator settingsLocator)
		throws SettingsException {

		Settings settings = getSettings(settingsLocator);

		Class<?> settingsOverrideClass = getOverrideClass(clazz);

		try {
			TypedSettings typedSettings = new TypedSettings(settings);

			Object settingsOverrideInstance = null;

			if (settingsOverrideClass != null) {
				Constructor<?> constructor =
					settingsOverrideClass.getConstructor(TypedSettings.class);

				settingsOverrideInstance = constructor.newInstance(
					typedSettings);
			}

			SettingsInvocationHandler<T> settingsInvocationHandler =
				new SettingsInvocationHandler<>(
					clazz, settingsOverrideInstance, typedSettings);

			return settingsInvocationHandler.createProxy();
		}
		catch (NoSuchMethodException | InvocationTargetException |
				InstantiationException | IllegalAccessException e) {

			throw new SettingsException(
				"Unable to load settings of type " + clazz.getName(), e);
		}
	}

	@Override
	public Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		Settings settings = settingsLocator.getSettings();

		return applyFallbackKeys(settingsLocator.getSettingsId(), settings);
	}

	@Override
	public SettingsDescriptor getSettingsDescriptor(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		return _settingsDescriptors.get(settingsId);
	}

	@Override
	public void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		SettingsDescriptor settingsDescriptor = new AnnotatedSettingsDescriptor(
			settingsClass);

		Settings.Config settingsConfig = settingsClass.getAnnotation(
			Settings.Config.class);

		for (String settingsId : settingsConfig.settingsIds()) {
			register(settingsId, settingsDescriptor, fallbackKeys);
		}
	}

	protected Settings applyFallbackKeys(String settingsId, Settings settings) {
		if (settings instanceof FallbackKeys) {
			return settings;
		}

		settingsId = PortletConstants.getRootPortletId(settingsId);

		FallbackKeys fallbackKeys = _fallbackKeysMap.get(settingsId);

		if (fallbackKeys != null) {
			settings = new FallbackSettings(settings, fallbackKeys);
		}

		return settings;
	}

	protected long getCompanyId(long groupId) throws SettingsException {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return group.getCompanyId();
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	protected <T> Class<?> getOverrideClass(Class<T> clazz) {
		Settings.OverrideClass overrideClass = clazz.getAnnotation(
			Settings.OverrideClass.class);

		if (overrideClass == null) {
			return null;
		}

		if (overrideClass.value() == Object.class) {
			return null;
		}

		return overrideClass.value();
	}

	protected PortletItem getPortletItem(
			long groupId, String portletId, String name)
		throws PortalException {

		PortletItem portletItem = null;

		try {
			portletItem = PortletItemLocalServiceUtil.getPortletItem(
				groupId, name, portletId, PortletPreferences.class.getName());
		}
		catch (NoSuchPortletItemException nspie) {
			long userId = PrincipalThreadLocal.getUserId();

			portletItem = PortletItemLocalServiceUtil.updatePortletItem(
				userId, groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	protected void register(
		String settingsId, SettingsDescriptor settingsDescriptor,
		FallbackKeys fallbackKeys) {

		_settingsDescriptors.put(settingsId, settingsDescriptor);

		if (fallbackKeys != null) {
			_fallbackKeysMap.put(settingsId, fallbackKeys);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		Class<?> configurationBeanClass =
			configurationBeanDeclaration.getConfigurationBeanClass();

		String settingsId = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);

		ConfigurationBeanClassSettingsDescriptor
			configurationBeanClassSettingsDescriptor =
				new ConfigurationBeanClassSettingsDescriptor(
					configurationBeanClass);

		register(settingsId, configurationBeanClassSettingsDescriptor, null);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setSettingsIdMapping(SettingsIdMapping settingsIdMapping) {
		String settingsId = settingsIdMapping.getSettingsId();

		Class<?> configurationBeanClass =
			settingsIdMapping.getConfigurationBeanClass();

		ConfigurationBeanClassSettingsDescriptor
			configurationBeanClassSettingsDescriptor =
				new ConfigurationBeanClassSettingsDescriptor(
					configurationBeanClass);

		register(settingsId, configurationBeanClassSettingsDescriptor, null);
	}

	@Reference(unbind = "-")
	protected void setSettingsLocatorHelper(
		SettingsLocatorHelper settingsLocatorHelper) {

		_settingsLocatorHelper = settingsLocatorHelper;
	}

	protected void unregister(String settingsId) {
		_fallbackKeysMap.remove(settingsId);

		_settingsDescriptors.remove(settingsId);
	}

	protected void unsetConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		Class<?> configurationBeanClass =
			configurationBeanDeclaration.getConfigurationBeanClass();

		String settingsId = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);

		unregister(settingsId);
	}

	protected void unsetSettingsIdMapping(SettingsIdMapping settingsIdMapping) {
		unregister(settingsIdMapping.getSettingsId());
	}

	private final ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<>();
	private final Map<String, SettingsDescriptor> _settingsDescriptors =
		new ConcurrentHashMap<>();
	private SettingsLocatorHelper _settingsLocatorHelper;

}