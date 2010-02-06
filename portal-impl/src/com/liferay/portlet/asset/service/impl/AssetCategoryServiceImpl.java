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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.base.AssetCategoryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="AssetCategoryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 */
public class AssetCategoryServiceImpl extends AssetCategoryServiceBaseImpl {

	public AssetCategory addCategory(
			long parentCategoryId, Map<Locale, String> titleMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return assetCategoryLocalService.addCategory(
			null, getUserId(), parentCategoryId, titleMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.DELETE);

		assetCategoryLocalService.deleteCategory(categoryId);
	}

	public List<AssetCategory> getCategories(String className, long classPK)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getCategories(className, classPK));
	}

	public AssetCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.VIEW);

		return assetCategoryLocalService.getCategory(categoryId);
	}

	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getChildCategories(parentCategoryId));
	}

	public List<AssetCategory> getVocabularyCategories(long vocabularyId)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getVocabularyCategories(vocabularyId));
	}

	public List<AssetCategory> getVocabularyRootCategories(long vocabularyId)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getVocabularyRootCategories(
				vocabularyId));
	}

	public JSONArray search(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws SystemException {

		return assetCategoryLocalService.search(
			groupId, name, categoryProperties, start, end);
	}

	public AssetCategory updateCategory(
			long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.UPDATE);

		return assetCategoryLocalService.updateCategory(
			getUserId(), categoryId, parentCategoryId, titleMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	protected List<AssetCategory> filterCategories(
			List<AssetCategory> categories)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		categories = ListUtil.copy(categories);

		Iterator<AssetCategory> itr = categories.iterator();

		while (itr.hasNext()) {
			AssetCategory category = itr.next();

			if (!AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return categories;
	}

}