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
import com.liferay.portlet.asset.model.AssetCategoryVocabulary;
import com.liferay.portlet.asset.service.base.AssetCategoryVocabularyLocalServiceBaseImpl;
import com.liferay.portlet.tags.DuplicateVocabularyException;

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
			throw new DuplicateVocabularyException(
				"A vocabulary with the name " + name + " already exists");
		}

		long vocabularyId = counterLocalService.increment();

		AssetCategoryVocabulary vocabulary =
			assetCategoryVocabularyPersistence.create(vocabularyId);

		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);

		assetCategoryVocabularyPersistence.update(vocabulary, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addCategoryVocabularyResources(
				vocabulary, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addCategoryVocabularyResources(
				vocabulary, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return vocabulary;

	}

	public void addCategoryVocabularyResources(
			AssetCategoryVocabulary vocabulary, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetCategoryVocabulary.class.getName(),
			vocabulary.getCategoryVocabularyId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryVocabularyResources(
			AssetCategoryVocabulary vocabulary, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetCategoryVocabulary.class.getName(),
			vocabulary.getCategoryVocabularyId(), communityPermissions,
			guestPermissions);
	}

	public void deleteCategoryVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetCategoryVocabulary vocabulary =
			assetCategoryVocabularyPersistence.findByPrimaryKey(vocabularyId);

		deleteCategoryVocabulary(vocabulary);
	}

	public void deleteCategoryVocabulary(AssetCategoryVocabulary vocabulary)
		throws PortalException, SystemException {

		// Entries

		assetCategoryLocalService.deleteVocabularyCategories(
			vocabulary.getCategoryVocabularyId());

		// Resources

		resourceLocalService.deleteResource(
			vocabulary.getCompanyId(), AssetCategoryVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			vocabulary.getCategoryVocabularyId());

		assetCategoryVocabularyPersistence.remove(vocabulary);
	}

	public List<AssetCategoryVocabulary> getCompanyVocabularies(long companyId)
		throws SystemException {

		return assetCategoryVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<AssetCategoryVocabulary> getGroupCategoryVocabularies(
			long groupId)
		throws PortalException, SystemException {

		List<AssetCategoryVocabulary> vocabularies =
			assetCategoryVocabularyPersistence.findByGroupId(groupId);

		if (vocabularies.isEmpty()) {
			Group group = groupLocalService.getGroup(groupId);
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(groupId);

			addCategoryVocabulary(
				userLocalService.getDefaultUserId(group.getCompanyId()),
				PropsValues.ASSET_CATEGORIES_VOCABULARY_DEFAULT,
				serviceContext);
		}

		return vocabularies;
	}

	public AssetCategoryVocabulary getGroupCategoryVocabulary(
			long groupId, String name)
		throws PortalException, SystemException {

		return assetCategoryVocabularyPersistence.findByG_N(groupId, name);
	}

	public AssetCategoryVocabulary getCategoryVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return assetCategoryVocabularyPersistence.findByPrimaryKey(
			vocabularyId);
	}

	public AssetCategoryVocabulary updateCategoryVocabulary(
			long vocabularyId, String name)
		throws PortalException, SystemException {

		name = name.trim();

		AssetCategoryVocabulary vocabulary =
			assetCategoryVocabularyPersistence.findByPrimaryKey(vocabularyId);

		if (!vocabulary.getName().equals(name) &&
			hasCategoryVocabulary(vocabulary.getGroupId(), name)) {

			throw new DuplicateVocabularyException(name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);

		assetCategoryVocabularyPersistence.update(vocabulary, false);

		return vocabulary;
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