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

package com.liferay.wiki.web.settings.definition;

import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.wiki.settings.WikiGroupServiceSettingsExtra;

/**
 * @author Iván Zaera
 */
public class WikiGroupServiceSettingsExtraImpl
	implements WikiGroupServiceSettingsExtra {

	public WikiGroupServiceSettingsExtraImpl(TypedSettings typedSettings) {
		_typedSettings = typedSettings;
	}

	@Override
	public String emailPageAddedBodyXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailPageAddedBody"),
			"emailPageAddedBody");
	}

	@Override
	public String emailPageAddedSubjectXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailPageAddedSubject"),
			"emailPageAddedSubject");
	}

	@Override
	public String emailPageUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailPageUpdatedBody"),
			"emailPageUpdatedBody");
	}

	@Override
	public String emailPageUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject"),
			"emailPageUpdatedSubject");
	}

	private final TypedSettings _typedSettings;

}