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

package com.liferay.portlet.asset.util;

import com.liferay.portlet.asset.model.AssetCategoryConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsHelperTest {

	@Test
	public void testHasClassNameIdAll() {
		_vocabularySettingsHelper = getSettingProperties(
			true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(_vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertTrue(_vocabularySettingsHelper.hasClassNameId(2));
	}

	@Test
	public void testHasClassNameIdClassName() {
		_vocabularySettingsHelper = getSettingProperties(true, 1, true);

		Assert.assertTrue(_vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertFalse(_vocabularySettingsHelper.hasClassNameId(2));
	}

	@Test
	public void testIsClassNameIdRequiredAllIsNotRequired() {
		_vocabularySettingsHelper = getSettingProperties(
			true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(2));
	}

	@Test
	public void testIsClassNameIdRequiredAllIsRequired() {
		_vocabularySettingsHelper = getSettingProperties(
			true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(_vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertTrue(_vocabularySettingsHelper.isClassNameIdRequired(2));
	}

	@Test
	public void testIsClassNameIdRequiredClassNameIsNotRequired() {
		_vocabularySettingsHelper = getSettingProperties(true, 1, false);

		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(2));
	}

	@Test
	public void testIsClassNameIdRequiredClassNameIsRequired() {
		_vocabularySettingsHelper = getSettingProperties(true, 1, true);

		Assert.assertTrue(_vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(2));
	}

	@Test
	public void testIsClassNameIdRequiredOnOverwrite() {
		_vocabularySettingsHelper = getSettingProperties(true, 1, true);

		_vocabularySettingsHelper.setClassNameIds(new long[]{1}, new boolean[]{false});

		Assert.assertFalse(_vocabularySettingsHelper.isClassNameIdRequired(1));
	}

	@Test
	public void testIsMultiValuedFalse() {
		_vocabularySettingsHelper = getSettingProperties(false, 1, true);

		Assert.assertFalse(_vocabularySettingsHelper.isMultiValued());
	}

	@Test
	public void testIsMultiValuedTrue() {
		_vocabularySettingsHelper = getSettingProperties(true, 1, true);

		Assert.assertTrue(_vocabularySettingsHelper.isMultiValued());
	}

	protected AssetVocabularySettingsHelper getSettingProperties(
		boolean multiValued, long classNameId, boolean required) {

		long[] classNameIds = new long[] {classNameId};
		boolean[] requireds = new boolean[] {required};

		return getVocabularySettingsHelper(multiValued, classNameIds, requireds);
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		boolean multiValued, long[] classNameIds, boolean[] requireds) {

		AssetVocabularySettingsHelper settingsProperties =
			new AssetVocabularySettingsHelper();

		settingsProperties.setClassNameIds(classNameIds, requireds);
		settingsProperties.setMultiValued(multiValued);

		return settingsProperties;
	}

	private AssetVocabularySettingsHelper _vocabularySettingsHelper;

}