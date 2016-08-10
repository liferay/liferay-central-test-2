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

package com.liferay.dynamic.data.mapping.type.radio;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldTypeSettingsTest;
import com.liferay.dynamic.data.mapping.type.radio.internal.RadioDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class RadioDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTest {

	@Test
	public void testCreateRadioDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			RadioDDMFormFieldTypeSettings.class);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();
		Assert.assertEquals(1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> ddmFormRuleActions = ddmFormRule.getActions();

		Assert.assertArrayEquals(
			new String[] {"set(fieldAt(\"validation\",0),\"visible\",false)"},
			ddmFormRuleActions.toArray());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField inlineDDMFormField = ddmFormFieldsMap.get("inline");

		Assert.assertNotNull(inlineDDMFormField);
		Assert.assertNotNull(inlineDDMFormField.getLabel());
		Assert.assertEquals(
			"true", inlineDDMFormField.getProperty("showAsSwitcher"));

		DDMFormField optionsDDMFormField = ddmFormFieldsMap.get("options");

		Assert.assertNotNull(optionsDDMFormField);
		Assert.assertEquals("ddm-options", optionsDDMFormField.getDataType());
		Assert.assertNotNull(optionsDDMFormField.getLabel());
		Assert.assertTrue(optionsDDMFormField.isRequired());
		Assert.assertEquals("options", optionsDDMFormField.getType());

		DDMFormField validationDDMFormField = ddmFormFieldsMap.get(
			"validation");

		Assert.assertNotNull(validationDDMFormField);
		Assert.assertEquals(
			"FALSE", validationDDMFormField.getVisibilityExpression());
	}

}