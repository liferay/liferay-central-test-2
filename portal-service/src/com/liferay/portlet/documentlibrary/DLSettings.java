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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Adolfo Pérez
 */
public class DLSettings {

	public static final String[] MULTI_VALUED_KEYS = {};

	public static FallbackKeys getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFileEntryAddedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
		fallbackKeys.add(
			"emailFileEntryAddedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED);
		fallbackKeys.add(
			"emailFileEntryAddedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailFileEntryUpdatedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
		fallbackKeys.add(
			"emailFileEntryUpdatedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailFileEntryUpdatedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.DL_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.DL_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		fallbackKeys.add("enableRatings", PropsKeys.DL_RATINGS_ENABLED);
		fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.DL_RELATED_ASSETS_ENABLED);
		fallbackKeys.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add("entryColumns", PropsKeys.DL_ENTRY_COLUMNS);
		fallbackKeys.add("fileEntryColumns", PropsKeys.DL_FILE_ENTRY_COLUMNS);
		fallbackKeys.add("folderColumns", PropsKeys.DL_FOLDER_COLUMNS);
		fallbackKeys.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"fileEntriesPerPage",
			PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		fallbackKeys.add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);

		return fallbackKeys;
	}

	public DLSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	public String getEmailFileEntryAddedBodyXml() {
		LocalizedValuesMap emailFileEntryAddedBody =
			getEmailFileEntryAddedBody();

		return emailFileEntryAddedBody.getLocalizationXml();
	}

	public boolean getEmailFileEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryAddedSubject");
	}

	public String getEmailFileEntryAddedSubjectXml() {
		LocalizedValuesMap emailFileEntryAddedSubject =
			getEmailFileEntryAddedSubject();

		return emailFileEntryAddedSubject.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedBody");
	}

	public String getEmailFileEntryUpdatedBodyXml() {
		LocalizedValuesMap emailFileEntryUpdatedBody =
			getEmailFileEntryUpdatedBody();

		return emailFileEntryUpdatedBody.getLocalizationXml();
	}

	public boolean getEmailFileEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedSubject");
	}

	public String getEmailFileEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailFileEntryUpdatedSubject =
			getEmailFileEntryUpdatedSubject();

		return emailFileEntryUpdatedSubject.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	private TypedSettings _typedSettings;

}