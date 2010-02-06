/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyServiceBaseImpl;

import java.util.List;

/**
 * <a href="AssetCategoryPropertyServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoryPropertyServiceImpl
	extends AssetCategoryPropertyServiceBaseImpl {

	public AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.addCategoryProperty(
			getUserId(), entryId, key, value);
	}

	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException, SystemException {

		assetCategoryPropertyLocalService.deleteCategoryProperty(
			categoryPropertyId);
	}

	public List<AssetCategoryProperty> getCategoryProperties(long entryId)
		throws SystemException {

		return assetCategoryPropertyLocalService.getCategoryProperties(entryId);
	}

	public List<AssetCategoryProperty> getCategoryPropertyValues(
			long companyId, String key)
		throws SystemException {

		return assetCategoryPropertyLocalService.getCategoryPropertyValues(
			companyId, key);
	}

	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.updateCategoryProperty(
			categoryPropertyId, key, value);
	}

}