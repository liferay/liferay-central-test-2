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

package com.liferay.portlet.shopping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.shopping.util.ShoppingConstants;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
public class ShoppingSettings {

	public static final String[] ALL_KEYS = {
		"alternativeShipping", "ccTypes", "currencyId", "emailFromAddress",
		"emailFromName", "emailOrderConfirmationBody",
		"emailOrderConfirmationSubject", "emailOrderShippingBody",
		"emailOrderShippingSubject", "insurance", "insuranceFormula",
		"minOrder", "paypalEmailAddress", "shipping", "shippingFormula",
		"taxRate", "taxState", "emailOrderConfirmationEnabled",
		"emailOrderShippingEnabled"
	};

	public static final String CC_NONE = "none";

	public static final String[] CC_TYPES =
		{"visa", "mastercard", "discover", "amex"};

	public static final String[] CURRENCY_IDS;

	public static final double[] INSURANCE_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	public static final double[] SHIPPING_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	static {
		String[] ids = null;

		try {
			Set<String> set = new TreeSet<>();

			Locale[] locales = Locale.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
				Locale locale = locales[i];

				if (locale.getCountry().length() == 2) {
					Currency currency = Currency.getInstance(locale);

					String currencyId = currency.getCurrencyCode();

					set.add(currencyId);
				}
			}

			ids = set.toArray(new String[set.size()]);
		}
		catch (Exception e) {
			ids = new String[] {"USD", "CAD", "EUR", "GBP", "JPY"};
		}
		finally {
			CURRENCY_IDS = ids;
		}
	}

	public static ShoppingSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, ShoppingConstants.SERVICE_NAME);

		return new ShoppingSettings(settings);
	}

	public static ShoppingSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, ShoppingConstants.SERVICE_NAME);

		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new ShoppingSettings(parameterMapSettings);
	}

	public ShoppingSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String[][] getAlternativeShipping() {
		String value = _typedSettings.getValue("alternativeShipping", null);

		if (value == null) {
			return new String[0][0];
		}

		String[] array = StringUtil.split("alternativeShipping", "[$_ARRAY_$]");

		String[][] alternativeShipping = new String[array.length][0];

		for (int i = 0; i < array.length; i++) {
			alternativeShipping[i] = StringUtil.split(array[i]);
		}

		return alternativeShipping;
	}

	public String getAlternativeShippingName(int altShipping) {
		String altShippingName = StringPool.BLANK;

		try {
			altShippingName = getAlternativeShipping()[0][altShipping];
		}
		catch (Exception e) {
		}

		return altShippingName;
	}

	public String[] getCcTypes() {
		String[] ccTypes = _typedSettings.getValues("ccTypes");

		if ((ccTypes.length == 1) && ccTypes[0].equals(CC_NONE)) {
			return StringPool.EMPTY_ARRAY;
		}

		return ccTypes;
	}

	public String getCurrencyId() {
		return _typedSettings.getValue("currencyId", "USD");
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailOrderConfirmationBody() {
		LocalizedValuesMap emailOrderConfirmationBody =
			_typedSettings.getLocalizedValuesMap("emailOrderConfirmationBody");

		return emailOrderConfirmationBody;
	}

	public String getEmailOrderConfirmationBodyXml() {
		LocalizedValuesMap emailOrderConfirmationBodyMap =
			getEmailOrderConfirmationBody();

		return emailOrderConfirmationBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailOrderConfirmationSubject() {
		LocalizedValuesMap emailOrderConfirmationSubject =
			_typedSettings.getLocalizedValuesMap(
				"emailOrderConfirmationSubject");

		return emailOrderConfirmationSubject;
	}

	public String getEmailOrderConfirmationSubjectXml() {
		LocalizedValuesMap emailOrderConfirmationSubjectMap =
			getEmailOrderConfirmationSubject();

		return emailOrderConfirmationSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailOrderShippingBody() {
		return _typedSettings.getLocalizedValuesMap("emailOrderShippingBody");
	}

	public String getEmailOrderShippingBodyXml() {
		LocalizedValuesMap emailOrderShippingBodyMap =
			getEmailOrderShippingBody();

		return emailOrderShippingBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailOrderShippingSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailOrderShippingSubject");
	}

	public String getEmailOrderShippingSubjectXml() {
		LocalizedValuesMap emailOrderShippingSubjectMap =
			getEmailOrderShippingSubject();

		return emailOrderShippingSubjectMap.getLocalizationXml();
	}

	public String[] getInsurance() {
		return _typedSettings.getValues("insurance");
	}

	public String getInsuranceFormula() {
		return _typedSettings.getValue("insuranceFormula");
	}

	public double getMinOrder() {
		return _typedSettings.getDoubleValue("minOrder");
	}

	public String getPayPalEmailAddress() {
		return _typedSettings.getValue("paypalEmailAddress");
	}

	public String[] getShipping() {
		return _typedSettings.getValues("shipping");
	}

	public String getShippingFormula() {
		return _typedSettings.getValue("shippingFormula");
	}

	public double getTaxRate() {
		return _typedSettings.getDoubleValue("taxRate");
	}

	public String getTaxState() {
		return _typedSettings.getValue("taxState");
	}

	public boolean isEmailOrderConfirmationEnabled() {
		return _typedSettings.getBooleanValue("emailOrderConfirmationEnabled");
	}

	public boolean isEmailOrderShippingEnabled() {
		return _typedSettings.getBooleanValue("emailOrderShippingEnabled");
	}

	public boolean useAlternativeShipping() {
		String[][] alternativeShipping = getAlternativeShipping();

		try {
			for (int i = 0; i < 10; i++) {
				if (Validator.isNotNull(alternativeShipping[0][i]) &&
					Validator.isNotNull(alternativeShipping[1][i])) {

					return true;
				}
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean usePayPal() {
		return Validator.isNotNull(getPayPalEmailAddress());
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add("ccTypes", PropsKeys.SHOPPING_CREDIT_CARD_TYPES);
		fallbackKeys.add("currencyId", PropsKeys.SHOPPING_CURRENCY_ID);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.SHOPPING_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.SHOPPING_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"emailOrderConfirmationBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY);
		fallbackKeys.add(
			"emailOrderConfirmationEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED);
		fallbackKeys.add(
			"emailOrderConfirmationSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT);
		fallbackKeys.add(
			"emailOrderShippingBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY);
		fallbackKeys.add(
			"emailOrderShippingEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED);
		fallbackKeys.add(
			"emailOrderShippingSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT);
		fallbackKeys.add("insurance", PropsKeys.SHOPPING_INSURANCE);
		fallbackKeys.add(
			"insuranceFormula", PropsKeys.SHOPPING_INSURANCE_FORMULA);
		fallbackKeys.add("minOrder", PropsKeys.SHOPPING_MIN_ORDER);
		fallbackKeys.add(
			"paypalEmailAddress", PropsKeys.SHOPPING_PAYPAL_EMAIL_ADDRESS);
		fallbackKeys.add("shipping", PropsKeys.SHOPPING_SHIPPING);
		fallbackKeys.add(
			"shippingFormula", PropsKeys.SHOPPING_SHIPPING_FORMULA);
		fallbackKeys.add("taxRate", PropsKeys.SHOPPING_TAX_RATE);
		fallbackKeys.add("taxState", PropsKeys.SHOPPING_TAX_STATE);

		return fallbackKeys;
	}

	private static final String[] _MULTI_VALUED_KEYS = {
		"ccTypes", "insurance", "shipping"
	};

	private static final ResourceManager _resourceManager =
		new ClassLoaderResourceManager(ShoppingSettings.class.getClassLoader());

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			ShoppingConstants.SERVICE_NAME, _getFallbackKeys(),
			_MULTI_VALUED_KEYS, _resourceManager);
	}

	private final TypedSettings _typedSettings;

}