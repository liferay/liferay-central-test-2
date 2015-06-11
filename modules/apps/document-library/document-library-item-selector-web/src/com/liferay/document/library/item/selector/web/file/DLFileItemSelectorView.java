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

package com.liferay.document.library.item.selector.web.file;

import com.liferay.document.library.item.selector.web.BaseDLItemSelectorView;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.DefaultItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorView.class)
public class DLFileItemSelectorView
	extends BaseDLItemSelectorView
		<FileItemSelectorCriterion, DefaultItemSelectorReturnType> {

	@Override
	public Class<FileItemSelectorCriterion> getItemSelectorCriterionClass() {
		return FileItemSelectorCriterion.class;
	}

	@Override
	public Set<DefaultItemSelectorReturnType>
		getSupportedItemSelectorReturnTypes() {

		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content/Language", locale);

		return resourceBundle.getString("images");
	}

	private static final Set<DefaultItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableSet(
			SetUtil.fromArray(
				new DefaultItemSelectorReturnType[] {
					DefaultItemSelectorReturnType.FILE_ENTRY,
					DefaultItemSelectorReturnType.URL
				}));

}