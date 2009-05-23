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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategoryVocabulary;
import com.liferay.portlet.asset.service.base.AssetCategoryVocabularyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryVocabularyPermission;
import com.liferay.portlet.asset.service.permission.AssetPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetCategoryVocabularyServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 *
 */
public class AssetCategoryVocabularyServiceImpl
	extends AssetCategoryVocabularyServiceBaseImpl {

	public AssetCategoryVocabulary addCategoryVocabulary(
			String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_VOCABULARY);

		return assetCategoryVocabularyLocalService.addCategoryVocabulary(
			getUserId(), name, serviceContext);
	}

	public void deleteCategoryVocabulary(long categoryVocabularyId)
		throws PortalException, SystemException {

		AssetCategoryVocabularyPermission.check(
			getPermissionChecker(), categoryVocabularyId, ActionKeys.DELETE);

		assetCategoryVocabularyLocalService.deleteCategoryVocabulary(
			categoryVocabularyId);
	}

	public List<AssetCategoryVocabulary> getCompanyCategoryVocabularies(
			long companyId)
		throws PortalException, SystemException {

		return getCategoryVocabularies(
			assetCategoryVocabularyLocalService.getCompanyCategoryVocabularies(
				companyId));
	}

	public List<AssetCategoryVocabulary> getGroupCategoryVocabularies(
			long groupId)
		throws PortalException, SystemException {

		return getCategoryVocabularies(
			assetCategoryVocabularyLocalService.getGroupCategoryVocabularies(
				groupId));
	}

	public AssetCategoryVocabulary getVocabulary(long categoryVocabularyId)
		throws PortalException, SystemException {

		AssetCategoryVocabularyPermission.check(
			getPermissionChecker(), categoryVocabularyId, ActionKeys.VIEW);

		return assetCategoryVocabularyLocalService.getCategoryVocabulary(
			categoryVocabularyId);
	}

	public AssetCategoryVocabulary updateCategoryVocabulary(
			long categoryVocabularyId, String name)
		throws PortalException, SystemException {

		AssetCategoryVocabularyPermission.check(
			getPermissionChecker(), categoryVocabularyId, ActionKeys.UPDATE);

		return assetCategoryVocabularyLocalService.updateCategoryVocabulary(
			categoryVocabularyId, name);
	}

	protected List<AssetCategoryVocabulary> getCategoryVocabularies(
			List<AssetCategoryVocabulary> categoryVocabularies)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		categoryVocabularies = ListUtil.copy(categoryVocabularies);

		Iterator<AssetCategoryVocabulary> itr = categoryVocabularies.iterator();

		while (itr.hasNext()) {
			AssetCategoryVocabulary categoryVocabulary = itr.next();

			if (!AssetCategoryVocabularyPermission.contains(
					permissionChecker, categoryVocabulary, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return categoryVocabularies;
	}

}