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

package com.liferay.portlet.categories.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.tags.DuplicateVocabularyException;
import com.liferay.portlet.categories.model.CategoriesVocabulary;
import com.liferay.portlet.categories.service.base.CategoriesVocabularyLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="CategoriesVocabularyLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 *
 */
public class CategoriesVocabularyLocalServiceImpl
	extends CategoriesVocabularyLocalServiceBaseImpl {

	public CategoriesVocabulary addVocabulary(
			long userId, String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Vocabulary

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		name = name.trim();
		Date now = new Date();

		if (hasVocabulary(groupId, name)) {
			throw new DuplicateVocabularyException(
				"A vocabulary with the name " + name + " already exists");
		}

		long vocabularyId = counterLocalService.increment();

		CategoriesVocabulary vocabulary = categoriesVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);

		categoriesVocabularyPersistence.update(vocabulary, false);

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
			CategoriesVocabulary vocabulary, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), CategoriesVocabulary.class.getName(),
			vocabulary.getVocabularyId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addVocabularyResources(
			CategoriesVocabulary vocabulary, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), CategoriesVocabulary.class.getName(),
			vocabulary.getVocabularyId(), communityPermissions,
			guestPermissions);
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		CategoriesVocabulary vocabulary = categoriesVocabularyPersistence.findByPrimaryKey(
			vocabularyId);

		deleteVocabulary(vocabulary);
	}

	public void deleteVocabulary(CategoriesVocabulary vocabulary)
		throws PortalException, SystemException {

		// Entries

		categoriesEntryLocalService.deleteVocabularyEntries(
			vocabulary.getVocabularyId());

		// Resources

		resourceLocalService.deleteResource(
			vocabulary.getCompanyId(), CategoriesVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, vocabulary.getVocabularyId());

		categoriesVocabularyPersistence.remove(vocabulary);
	}

	public List<CategoriesVocabulary> getCompanyVocabularies(long companyId)
		throws SystemException {

		return categoriesVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<CategoriesVocabulary> getGroupVocabularies(long groupId)
		throws PortalException, SystemException {

		List<CategoriesVocabulary> vocabularies =
			categoriesVocabularyPersistence.findByGroupId(groupId);

		if (vocabularies.isEmpty()) {
			Group group = groupLocalService.getGroup(groupId);
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(groupId);

			addVocabulary(
				userLocalService.getDefaultUserId(group.getCompanyId()), 
				PropsValues.CATEGORIES_VOCABULARY_DEFAULT, serviceContext);
		}
		return vocabularies;
	}

	public CategoriesVocabulary getGroupVocabulary(long groupId, String name)
		throws PortalException, SystemException {

		return categoriesVocabularyPersistence.findByG_N(groupId, name);
	}

	public CategoriesVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return categoriesVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	public CategoriesVocabulary updateVocabulary(long vocabularyId, String name)
		throws PortalException, SystemException {

		name = name.trim();

		CategoriesVocabulary vocabulary =
			categoriesVocabularyPersistence.findByPrimaryKey(vocabularyId);

		if (!vocabulary.getName().equals(name) &&
			hasVocabulary(vocabulary.getGroupId(), name)) {

			throw new DuplicateVocabularyException(name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);

		categoriesVocabularyPersistence.update(vocabulary, false);

		return vocabulary;
	}

	protected boolean hasVocabulary(long groupId, String name)
		throws SystemException {

		if (categoriesVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

}