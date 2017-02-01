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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContextContributor;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderParameterSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service = {
		SelectDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class SelectDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"multiple",
			ddmFormField.isMultiple() ? "multiple" : StringPool.BLANK);
		parameters.put(
			"options", getOptions(ddmFormField, ddmFormFieldRenderingContext));

		Map<String, String> stringsMap = new HashMap<>();

		ResourceBundle resourceBundle = getResourceBundle(
			ddmFormFieldRenderingContext.getLocale());

		stringsMap.put(
			"chooseAnOption",
			LanguageUtil.get(resourceBundle, "choose-an-option"));

		parameters.put("strings", stringsMap);
		parameters.put(
			"value", getValue(ddmFormField, ddmFormFieldRenderingContext));

		return parameters;
	}

	protected void addDDMDataProviderContextParameters(
		HttpServletRequest request,
		DDMDataProviderContext ddmDataProviderContext,
		List<DDMDataProviderContextContributor>
			ddmDataProviderContextContributors) {

		for (DDMDataProviderContextContributor
				ddmDataProviderContextContributor :
					ddmDataProviderContextContributors) {

			Map<String, String> parameters =
				ddmDataProviderContextContributor.getParameters(request);

			if (parameters == null) {
				continue;
			}

			ddmDataProviderContext.addParameters(parameters);
		}
	}

	protected DDMDataProviderContext createDDMDataProviderContext(
			String ddmDataProviderInstanceId, DDMDataProvider ddmDataProvider,
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.getDataProviderInstance(
				Long.valueOf(ddmDataProviderInstanceId));

		ddmDataProvider = ddmDataProviderTracker.getDDMDataProvider(
			ddmDataProviderInstance.getType());

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues = ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, ddmDataProviderInstance.getDefinition());

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		List<DDMDataProviderContextContributor>
			ddmDataProviderContextContributors =
				ddmDataProviderTracker.getDDMDataProviderContextContributors(
					ddmDataProviderInstance.getType());

		addDDMDataProviderContextParameters(
			ddmFormFieldRenderingContext.getHttpServletRequest(),
			ddmDataProviderContext, ddmDataProviderContextContributors);

		return ddmDataProviderContext;
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		List<Map<String, String>> keyValuePairs =
			(List<Map<String, String>>)
				ddmFormFieldRenderingContext.getProperty("options");

		if (keyValuePairs.isEmpty()) {
			return ddmFormField.getDDMFormFieldOptions();
		}

		for (Map<String, String> keyValuePair : keyValuePairs) {
			ddmFormFieldOptions.addOptionLabel(
				keyValuePair.get("value"),
				ddmFormFieldRenderingContext.getLocale(),
				keyValuePair.get("label"));
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldOptions createDDMFormFieldOptionsFromDataProvider(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(
			ddmFormFieldRenderingContext.getLocale());

		try {
			String ddmDataProviderInstanceId = GetterUtil.getString(
				ddmFormField.getProperty("ddmDataProviderInstanceId"));

			DDMDataProvider ddmDataProvider =
				ddmDataProviderTracker.getDDMDataProviderByInstanceId(
					ddmDataProviderInstanceId);

			DDMDataProviderContext ddmDataProviderContext = null;

			if (ddmDataProvider != null) {
				ddmDataProviderContext = new DDMDataProviderContext(null);
			}
			else {
				DDMDataProviderInstance ddmDataProviderInstance =
					ddmDataProviderInstanceService.getDataProviderInstance(
						Long.valueOf(ddmDataProviderInstanceId));

				ddmDataProvider = ddmDataProviderTracker.getDDMDataProvider(
					ddmDataProviderInstance.getType());

				ddmDataProviderContext = createDDMDataProviderContext(
					ddmDataProviderInstanceId, ddmDataProvider, ddmFormField,
					ddmFormFieldRenderingContext);
			}

			ddmDataProviderContext.setHttpServletRequest(
				ddmFormFieldRenderingContext.getHttpServletRequest());

			DDMDataProviderResponse ddmDataProviderResponse =
				executeDDMDataProvider(ddmDataProvider, ddmDataProviderContext);

			String ddmDataProviderOutput = GetterUtil.getString(
				ddmFormField.getProperty("ddmDataProviderOutput"));

			if (Validator.isNotNull(ddmDataProviderOutput)) {
				DDMDataProviderOutputParametersSettings outputParameterSetting =
					getDDMDataProviderOutputParametersSetting(
						ddmDataProviderOutput, ddmDataProvider,
						ddmDataProviderContext);

				String[] paths = StringUtil.split(
					outputParameterSetting.outputParameterPath(),
					CharPool.SEMICOLON);

				String key = paths[0];

				String value = key;

				if (paths.length > 1) {
					value = paths[1];
				}

				for (Map<Object, Object> map :
						ddmDataProviderResponse.getData()) {

					ddmFormFieldOptions.addOptionLabel(
						String.valueOf(map.get(value)),
						ddmFormFieldRenderingContext.getLocale(),
						String.valueOf(map.get(key)));
				}
			}
			else {
				for (Map<Object, Object> map :
						ddmDataProviderResponse.getData()) {

					for (Entry<Object, Object> entry : map.entrySet()) {
						ddmFormFieldOptions.addOptionLabel(
							String.valueOf(entry.getValue()),
							ddmFormFieldRenderingContext.getLocale(),
							String.valueOf(entry.getKey()));
					}
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return ddmFormFieldOptions;
	}

	protected DDMDataProviderResponse executeDDMDataProvider(
			DDMDataProvider ddmDataProvider,
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(ddmDataProviderContext);

		return ddmDataProvider.getData(ddmDataProviderRequest);
	}

	protected DDMDataProviderOutputParametersSettings
		getDDMDataProviderOutputParametersSetting(
			String ddmDataProviderOutput, DDMDataProvider ddmDataProvider,
			DDMDataProviderContext ddmDataProviderContext) {

		DDMDataProviderParameterSettings dataProviderParameterizedSettings =
			(DDMDataProviderParameterSettings)
				ddmDataProviderContext.getSettingsInstance(
					ddmDataProvider.getSettings());

		for (DDMDataProviderOutputParametersSettings outputParameterSettings :
				dataProviderParameterizedSettings.outputParameters()) {

			if (ddmDataProviderOutput.equals(
					outputParameterSettings.outputParameterName())) {

				return outputParameterSettings;
			}
		}

		return null;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		if (Objects.equals(dataSourceType, "data-provider")) {
			return createDDMFormFieldOptionsFromDataProvider(
				ddmFormField, ddmFormFieldRenderingContext);
		}
		else {
			return createDDMFormFieldOptions(
				ddmFormField, ddmFormFieldRenderingContext);
		}
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		SelectDDMFormFieldContextHelper selectDDMFormFieldContextHelper =
			new SelectDDMFormFieldContextHelper(
				jsonFactory,
				getDDMFormFieldOptions(
					ddmFormField, ddmFormFieldRenderingContext),
				ddmFormFieldRenderingContext.getValue(),
				ddmFormField.getPredefinedValue(),
				ddmFormFieldRenderingContext.getLocale());

		return selectDDMFormFieldContextHelper.getOptions();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	protected List<String> getValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		SelectDDMFormFieldContextHelper selectDDMFormFieldContextHelper =
			new SelectDDMFormFieldContextHelper(
				jsonFactory,
				getDDMFormFieldOptions(
					ddmFormField, ddmFormFieldRenderingContext),
				ddmFormFieldRenderingContext.getValue(),
				ddmFormField.getPredefinedValue(),
				ddmFormFieldRenderingContext.getLocale());

		String[] valuesStringArray =
			selectDDMFormFieldContextHelper.toStringArray(
				ddmFormFieldRenderingContext.getValue());

		return ListUtil.toList(valuesStringArray);
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldTemplateContextContributor.class);

	@Reference
	private DDMDataProviderTracker ddmDataProviderTracker;

	@Reference
	private DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

}