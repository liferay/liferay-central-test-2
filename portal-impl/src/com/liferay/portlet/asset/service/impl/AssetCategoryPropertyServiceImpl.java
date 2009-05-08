/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 *
 */
public class AssetCategoryPropertyServiceImpl
	extends AssetCategoryPropertyServiceBaseImpl {

	public AssetCategoryProperty addProperty(
			long entryId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.addProperty(
			getUserId(), entryId, key, value);
	}

	public void deleteProperty(long propertyId)
		throws PortalException, SystemException {

		assetCategoryPropertyLocalService.deleteProperty(propertyId);
	}

	public List<AssetCategoryProperty> getProperties(long entryId)
		throws SystemException {

		return assetCategoryPropertyLocalService.getProperties(entryId);
	}

	public List<AssetCategoryProperty> getPropertyValues(
			long companyId, String key)
		throws SystemException {

		return assetCategoryPropertyLocalService.getPropertyValues(
			companyId, key);
	}

	public AssetCategoryProperty updateProperty(
			long propertyId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.updateProperty(
			propertyId, key, value);
	}

}