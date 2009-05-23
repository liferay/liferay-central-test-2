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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.DuplicateCategoryVocabularyException;
import com.liferay.portlet.asset.model.AssetCategoryVocabulary;
import com.liferay.portlet.asset.service.base.AssetCategoryVocabularyLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="AssetCategoryVocabularyLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 *
 */
public class AssetCategoryVocabularyLocalServiceImpl
	extends AssetCategoryVocabularyLocalServiceBaseImpl {

	public AssetCategoryVocabulary addCategoryVocabulary(
			long userId, String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Vocabulary

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		name = name.trim();
		Date now = new Date();

		if (hasCategoryVocabulary(groupId, name)) {
			throw new DuplicateCategoryVocabularyException(
				"A category vocabulary with the name " + name +
					" already exists");
		}

		long categoryVocabularyId = counterLocalService.increment();

		AssetCategoryVocabulary categoryVocabulary =
			assetCategoryVocabularyPersistence.create(categoryVocabularyId);

		categoryVocabulary.setGroupId(groupId);
		categoryVocabulary.setCompanyId(user.getCompanyId());
		categoryVocabulary.setUserId(user.getUserId());
		categoryVocabulary.setUserName(user.getFullName());
		categoryVocabulary.setCreateDate(now);
		categoryVocabulary.setModifiedDate(now);
		categoryVocabulary.setName(name);

		assetCategoryVocabularyPersistence.update(categoryVocabulary, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addCategoryVocabularyResources(
				categoryVocabulary, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addCategoryVocabularyResources(
				categoryVocabulary, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return categoryVocabulary;
	}

	public void addCategoryVocabularyResources(
			AssetCategoryVocabulary categoryVocabulary,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			categoryVocabulary.getCompanyId(), categoryVocabulary.getGroupId(),
			categoryVocabulary.getUserId(),
			AssetCategoryVocabulary.class.getName(),
			categoryVocabulary.getCategoryVocabularyId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryVocabularyResources(
			AssetCategoryVocabulary categoryVocabulary,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			categoryVocabulary.getCompanyId(), categoryVocabulary.getGroupId(),
			categoryVocabulary.getUserId(),
			AssetCategoryVocabulary.class.getName(),
			categoryVocabulary.getCategoryVocabularyId(), communityPermissions,
			guestPermissions);
	}

	public void deleteCategoryVocabulary(
			AssetCategoryVocabulary categoryVocabulary)
		throws PortalException, SystemException {

		// Entries

		assetCategoryLocalService.deleteVocabularyCategories(
			categoryVocabulary.getCategoryVocabularyId());

		// Resources

		resourceLocalService.deleteResource(
			categoryVocabulary.getCompanyId(),
			AssetCategoryVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			categoryVocabulary.getCategoryVocabularyId());

		assetCategoryVocabularyPersistence.remove(categoryVocabulary);
	}

	public void deleteCategoryVocabulary(long categoryVocabularyId)
		throws PortalException, SystemException {

		AssetCategoryVocabulary categoryVocabulary =
			assetCategoryVocabularyPersistence.findByPrimaryKey(
				categoryVocabularyId);

		deleteCategoryVocabulary(categoryVocabulary);
	}

	public AssetCategoryVocabulary getCategoryVocabulary(
			long categoryVocabularyId)
		throws PortalException, SystemException {

		return assetCategoryVocabularyPersistence.findByPrimaryKey(
			categoryVocabularyId);
	}

	public List<AssetCategoryVocabulary> getCompanyCategoryVocabularies(
			long companyId)
		throws SystemException {

		return assetCategoryVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<AssetCategoryVocabulary> getGroupCategoryVocabularies(
			long groupId)
		throws PortalException, SystemException {

		List<AssetCategoryVocabulary> categoryVocabularies =
			assetCategoryVocabularyPersistence.findByGroupId(groupId);

		if (categoryVocabularies.isEmpty()) {
			Group group = groupLocalService.getGroup(groupId);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(groupId);

			addCategoryVocabulary(
				userLocalService.getDefaultUserId(group.getCompanyId()),
				PropsValues.ASSET_CATEGORIES_VOCABULARY_DEFAULT,
				serviceContext);
		}

		return categoryVocabularies;
	}

	public AssetCategoryVocabulary getGroupCategoryVocabulary(
			long groupId, String name)
		throws PortalException, SystemException {

		return assetCategoryVocabularyPersistence.findByG_N(groupId, name);
	}

	public AssetCategoryVocabulary updateCategoryVocabulary(
			long categoryVocabularyId, String name)
		throws PortalException, SystemException {

		name = name.trim();

		AssetCategoryVocabulary categoryVocabulary =
			assetCategoryVocabularyPersistence.findByPrimaryKey(
				categoryVocabularyId);

		if (!categoryVocabulary.getName().equals(name) &&
			hasCategoryVocabulary(categoryVocabulary.getGroupId(), name)) {

			throw new DuplicateCategoryVocabularyException(name);
		}

		categoryVocabulary.setModifiedDate(new Date());
		categoryVocabulary.setName(name);

		assetCategoryVocabularyPersistence.update(categoryVocabulary, false);

		return categoryVocabulary;
	}

	protected boolean hasCategoryVocabulary(long groupId, String name)
		throws SystemException {

		if (assetCategoryVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

}