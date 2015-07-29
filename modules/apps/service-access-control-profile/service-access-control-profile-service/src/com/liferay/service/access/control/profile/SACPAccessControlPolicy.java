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

package com.liferay.service.access.control.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.access.control.AccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.access.control.BaseAccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.profile.ServiceAccessControlProfileThreadLocal;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.service.access.control.profile.configuration.SACPConfiguration;
import com.liferay.service.access.control.profile.constants.SACPConstants;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.SACPEntryLocalService;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = AccessControlPolicy.class)
public class SACPAccessControlPolicy extends BaseAccessControlPolicy {

	@Override
	public void onServiceRemoteAccess(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		List<String> serviceAccessControlProfileNames =
			ServiceAccessControlProfileThreadLocal.
				getActiveServiceAccessControlProfileNames();

		SACPConfiguration sacpConfiguration = null;

		try {
			sacpConfiguration = _settingsFactory.getSettings(
				SACPConfiguration.class,
				new CompanyServiceSettingsLocator(
					CompanyThreadLocal.getCompanyId(),
					SACPConstants.SERVICE_NAME));
		}
		catch (SettingsException se) {
			throw new SecurityException(
				"Unable to determine default service access control profile",
				se);
		}

		if (sacpConfiguration.requireDefaultSACPEntry() ||
			(serviceAccessControlProfileNames == null)) {

			if (serviceAccessControlProfileNames == null) {
				serviceAccessControlProfileNames = new ArrayList<>();

				ServiceAccessControlProfileThreadLocal.
					setActiveServiceAccessControlProfileNames(
						serviceAccessControlProfileNames);
			}

			boolean passwordBasedAuthentication = false;

			AccessControlContext accessControlContext =
				AccessControlUtil.getAccessControlContext();

			if (accessControlContext != null) {
				AuthVerifierResult authVerifierResult =
					accessControlContext.getAuthVerifierResult();

				if (authVerifierResult != null) {
					passwordBasedAuthentication =
						authVerifierResult.isPasswordBasedAuthentication();
				}
			}

			if (passwordBasedAuthentication) {
				serviceAccessControlProfileNames.add(
					sacpConfiguration.defaultUserSACPEntryName());
			}
			else {
				serviceAccessControlProfileNames.add(
					sacpConfiguration.defaultApplicationSACPEntryName());
			}
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		Set<String> allowedServiceSignatures = new HashSet<>();

		for (String name : serviceAccessControlProfileNames) {
			try {
				SACPEntry sacpEntry = _sacpEntryLocalService.getSACPEntry(
					companyId, name);

				allowedServiceSignatures.addAll(
					sacpEntry.getAllowedServiceSignaturesList());
			}
			catch (PortalException pe) {
				throw new SecurityException(pe);
			}
		}

		if (allowedServiceSignatures.contains(StringPool.STAR)) {
			return;
		}

		Class<?> clazz = method.getDeclaringClass();

		String className = clazz.getName();

		if (allowedServiceSignatures.contains(className)) {
			return;
		}

		String methodName = method.getName();

		String classNameAndMethodName = className.concat(
			StringPool.POUND).concat(methodName);

		if (allowedServiceSignatures.contains(classNameAndMethodName)) {
			return;
		}

		for (String allowedService : allowedServiceSignatures) {
			if (matches(className, methodName, allowedService)) {
				return;
			}
		}

		throw new SecurityException(
			"Access denied to " + classNameAndMethodName);
	}

	protected boolean matches(
		String className, String methodName, String allowedServiceSignature) {

		String allowedClassName = null;
		String allowedMethodName = null;

		int index = allowedServiceSignature.indexOf(CharPool.POUND);

		if (index > -1) {
			allowedClassName = allowedServiceSignature.substring(0, index);
			allowedMethodName = allowedServiceSignature.substring(index + 1);
		}
		else {
			allowedClassName = allowedServiceSignature;
		}

		boolean wildcardMatchClass = false;

		if (Validator.isNotNull(allowedClassName) &&
			allowedClassName.endsWith(StringPool.STAR)) {

			allowedClassName = allowedClassName.substring(
				0, allowedClassName.length() - 1);
			wildcardMatchClass = true;
		}

		boolean wildcardMatchMethod = false;

		if (Validator.isNotNull(allowedMethodName) &&
			allowedMethodName.endsWith(StringPool.STAR)) {

			allowedMethodName = allowedMethodName.substring(
				0, allowedMethodName.length() - 1);
			wildcardMatchMethod = true;
		}

		if (Validator.isNotNull(allowedClassName) &&
			Validator.isNotNull(allowedMethodName)) {

			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedClassName)) {
			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedMethodName)) {
			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (wildcardMatchClass && Validator.isNull(allowedClassName)) {
			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setSACPEntryLocalService(
		SACPEntryLocalService sacpEntryLocalService) {

		_sacpEntryLocalService = sacpEntryLocalService;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private SACPEntryLocalService _sacpEntryLocalService;
	private volatile SettingsFactory _settingsFactory;

}