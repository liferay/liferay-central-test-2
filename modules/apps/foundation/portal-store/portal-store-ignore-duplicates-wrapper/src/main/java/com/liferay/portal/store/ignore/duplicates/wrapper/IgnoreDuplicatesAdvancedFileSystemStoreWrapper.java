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

package com.liferay.portal.store.ignore.duplicates.wrapper;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.store.StoreWrapper;
import com.liferay.portal.store.ignore.duplicates.IgnoreDuplicatesStore;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"store.type=com.liferay.portal.store.file.system.AdvancedFileSystemStore"
	},
	service = StoreWrapper.class
)
public class IgnoreDuplicatesAdvancedFileSystemStoreWrapper
	implements StoreWrapper {

	@Override
	public Store wrap(Store store) {
		return new IgnoreDuplicatesStore(store);
	}

	@Reference(
		target = "(store.type=com.liferay.portal.store.file.system.AdvancedFileSystemStore)"
	)
	private Store _store;

}