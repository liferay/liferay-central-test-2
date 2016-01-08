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

package com.liferay.portal.security.sso.token.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.sso.token.constants.LegacyTokenPropsKeys;
import com.liferay.portal.security.sso.token.constants.TokenConstants;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import java.io.IOException;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Brian Greenwald
 */
@RunWith(Arquillian.class)
public class ShibbolethCompanySettingsVerifyProcessTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(
			ShibbolethCompanySettingsVerifyProcessTest.class);

		_bundleContext = bundle.getBundleContext();

		ServiceReference<SettingsFactory> settingsFactoryReference =
			_bundleContext.getServiceReference(SettingsFactory.class);

		_settingsFactory = _bundleContext.getService(settingsFactoryReference);

		ServiceReference<CompanyLocalService> companyLocalServiceReference =
			_bundleContext.getServiceReference(CompanyLocalService.class);

		_companyLocalService = _bundleContext.getService(
			companyLocalServiceReference);

		ServiceReference<PrefsProps> prefsPropsServiceReference =
			_bundleContext.getServiceReference(PrefsProps.class);

		_prefsProps = _bundleContext.getService(prefsPropsServiceReference);

		UnicodeProperties properties = new UnicodeProperties();

		properties.put(LegacyTokenPropsKeys.SHIBBOLETH_AUTH_ENABLED, "true");

		properties.put(
			LegacyTokenPropsKeys.SHIBBOLETH_IMPORT_FROM_LDAP, "true");

		properties.put(
			LegacyTokenPropsKeys.SHIBBOLETH_LOGOUT_URL, "/test/shibboleth/url");

		properties.put(
			LegacyTokenPropsKeys.SHIBBOLETH_USER_HEADER, "testShibboleth");

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			_companyLocalService.updatePreferences(
				company.getCompanyId(), properties);
		}
	}

	@AfterClass
	public static void tearDownClass()
		throws InvalidSyntaxException, IOException {

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			try {
				Settings settings = getSettings(company.getCompanyId());

				settings.getModifiableSettings().reset();
			}
			catch (SettingsException e) {
				throw new IOException(e);
			}
		}
	}

	protected static Settings getSettings(long companyId)
		throws SettingsException {

		Settings settings = _settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(
				companyId, TokenConstants.SERVICE_NAME));

		return settings;
	}

	@Override
	protected void doVerify() throws VerifyException {
		super.doVerify();

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			PortletPreferences portletPreferences = _prefsProps.getPreferences(
				companyId, true);

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
						LegacyTokenPropsKeys.SHIBBOLETH_AUTH_ENABLED,
						StringPool.BLANK)));

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
						LegacyTokenPropsKeys.SHIBBOLETH_IMPORT_FROM_LDAP,
						StringPool.BLANK)));

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
						LegacyTokenPropsKeys.SHIBBOLETH_LOGOUT_URL,
						StringPool.BLANK)));

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(
							LegacyTokenPropsKeys.SHIBBOLETH_USER_HEADER,
							StringPool.BLANK)));

			Settings settings;

			try {
				settings = getSettings(companyId);
			}
			catch (SettingsException e) {
				throw new VerifyException(e);
			}

			Assert.assertNotNull(settings);

			Assert.assertTrue(
				GetterUtil.getBoolean(
					settings.getValue(
						TokenConstants.AUTH_ENABLED, StringPool.FALSE)));

			Assert.assertTrue(
				GetterUtil.getBoolean(
					settings.getValue(
						TokenConstants.IMPORT_FROM_LDAP, StringPool.FALSE)));

			Assert.assertEquals(
				"/test/shibboleth/url",
					settings.getValue(
						TokenConstants.LOGOUT_REDIRECT_URL, StringPool.BLANK));

			Assert.assertEquals(
				"testShibboleth",
					settings.getValue(
						TokenConstants.USER_HEADER, StringPool.BLANK));
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getAllServiceReferences(
					VerifyProcess.class.getName(),
					"(&(objectClass=" + VerifyProcess.class.getName() +
						")(verify.process.name=" + "com.liferay.portal." +
							"security.sso.token.shibboleth))");

			if (ArrayUtil.isEmpty(serviceReferences)) {
				throw new IllegalStateException("Unable to get verify process");
			}

			return (VerifyProcess) _bundleContext.getService(
				serviceReferences[0]);
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException("Unable to get verify process");
		}
	}

	private static BundleContext _bundleContext;
	private static volatile CompanyLocalService _companyLocalService;
	private static volatile PrefsProps _prefsProps;
	private static volatile SettingsFactory _settingsFactory;

}