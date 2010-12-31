/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetCategoryImpl
	extends AssetCategoryModelImpl implements AssetCategory {

	public AssetCategoryImpl() {
	}

	public List<AssetCategory> getAncestors()
		throws PortalException, SystemException {

		List<AssetCategory> categories = new ArrayList<AssetCategory>();

		AssetCategory category = this;

		while (true) {
			if (!category.isRootCategory()) {
				category = AssetCategoryLocalServiceUtil.getAssetCategory(
					category.getParentCategoryId());

				categories.add(category);
			}
			else {
				break;
			}
		}

		return categories;
	}

	public boolean isRootCategory() {
		if (getParentCategoryId() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}