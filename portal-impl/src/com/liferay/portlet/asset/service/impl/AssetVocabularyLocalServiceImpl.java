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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.DuplicateVocabularyException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="AssetVocabularyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 */
public class AssetVocabularyLocalServiceImpl
	extends AssetVocabularyLocalServiceBaseImpl {

	public AssetVocabulary addVocabulary(
			String uuid, long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Vocabulary

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Locale locale = LocaleUtil.getDefault();
		String name = titleMap.get(locale);

		Date now = new Date();

		if (hasVocabulary(groupId, name)) {
			throw new DuplicateVocabularyException(
				"A category vocabulary with the name " + name +
					" already exists");
		}

		long vocabularyId = counterLocalService.increment();

		AssetVocabulary vocabulary = assetVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setUuid(uuid);
		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);
		vocabulary.setTitleMap(titleMap);
		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);

		assetVocabularyPersistence.update(vocabulary, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addVocabularyResources(
				vocabulary, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addVocabularyResources(
				vocabulary, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return vocabulary;
	}

	public void addVocabularyResources(
			AssetVocabulary vocabulary, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addVocabularyResources(
			AssetVocabulary vocabulary, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), communityPermissions,
			guestPermissions);
	}

	public void deleteVocabulary(AssetVocabulary vocabulary)
		throws PortalException, SystemException {

		// Vocabulary

		assetVocabularyPersistence.remove(vocabulary);

		// Resources

		resourceLocalService.deleteResource(
			vocabulary.getCompanyId(), AssetVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, vocabulary.getVocabularyId());

		// Categories

		assetCategoryLocalService.deleteVocabularyCategories(
			vocabulary.getVocabularyId());
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		deleteVocabulary(vocabulary);
	}

	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws SystemException {

		return assetVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		for (long groupId : groupIds) {
			vocabularies.addAll(getGroupVocabularies(groupId));
		}

		return vocabularies;
	}

	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupId);

		if (vocabularies.isEmpty()) {
			Group group = groupLocalService.getGroup(groupId);

			long defaultUserId = userLocalService.getDefaultUserId(
				group.getCompanyId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(groupId);

			Locale locale = LocaleUtil.getDefault();

			Map<Locale, String> titleMap = new HashMap<Locale, String>();
			titleMap.put(locale, PropsValues.ASSET_VOCABULARY_DEFAULT);

			AssetVocabulary vocabulary =
				assetVocabularyLocalService.addVocabulary(
					null, defaultUserId, titleMap, null, StringPool.BLANK,
					serviceContext);

			vocabularies = ListUtil.copy(vocabularies);

			vocabularies.add(vocabulary);
		}

		return vocabularies;
	}

	public AssetVocabulary getGroupVocabulary(long groupId, String name)
		throws PortalException, SystemException {

		return assetVocabularyPersistence.findByG_N(groupId, name);
	}

	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return assetVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Locale locale = LocaleUtil.getDefault();
		String name = titleMap.get(locale);

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		if (!vocabulary.getName().equals(name) &&
			hasVocabulary(vocabulary.getGroupId(), name)) {

			throw new DuplicateVocabularyException(name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);
		vocabulary.setTitleMap(titleMap);
		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);

		assetVocabularyPersistence.update(vocabulary, false);

		return vocabulary;
	}

	protected boolean hasVocabulary(long groupId, String name)
		throws SystemException {

		if (assetVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

}