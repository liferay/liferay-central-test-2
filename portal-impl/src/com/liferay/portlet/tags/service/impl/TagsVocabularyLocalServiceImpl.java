/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.tags.DuplicateVocabularyException;
import com.liferay.portlet.tags.VocabularyNameException;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.service.base.TagsVocabularyLocalServiceBaseImpl;
import com.liferay.portlet.tags.util.TagsUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="TagsVocabularyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alvaro del Castillo
 *
 */
public class TagsVocabularyLocalServiceImpl
	extends TagsVocabularyLocalServiceBaseImpl {

	public TagsVocabulary addVocabulary(long userId, String name)
		throws PortalException, SystemException {

		return addVocabulary(userId, name, false);
	}

	public TagsVocabulary addVocabulary(
			long userId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		name = name.trim().toLowerCase();
		Date now = new Date();

		validate(name);

		if (hasVocabulary(user.getCompanyId(), name)) {
			throw new DuplicateVocabularyException(
				"A vocabulary with the name " + name + " already exists");
		}

		long vocabularyId = counterLocalService.increment();

		TagsVocabulary vocabulary = tagsVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);
		vocabulary.setFolksonomy(folksonomy);

		tagsVocabularyPersistence.update(vocabulary, false);

		return vocabulary;

	}

	public void deleteVocabulary(long userId, long vocabularyId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		tagsEntryLocalService.deleteEntries(user.getCompanyId(), vocabularyId);

		tagsVocabularyPersistence.remove(vocabularyId);
	}

	public List<TagsVocabulary> getVocabularies(long companyId)
		throws SystemException {

		return tagsVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<TagsVocabulary> getVocabularies(
			long companyId, boolean folksonomy)
		throws SystemException {

		return tagsVocabularyPersistence.findByC_F(companyId, folksonomy);
	}

	public TagsVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return tagsVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	public TagsVocabulary getVocabulary(long companyId, String name)
		throws PortalException, SystemException {

		return tagsVocabularyPersistence.findByC_N(companyId, name);
	}

	public TagsVocabulary updateVocabulary(
			long vocabularyId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		name = name.trim().toLowerCase();

		validate(name);

		TagsVocabulary vocabulary = tagsVocabularyPersistence.findByPrimaryKey(
			vocabularyId);

		if (!vocabulary.getName().equals(name) &&
			hasVocabulary(vocabulary.getCompanyId(), name)) {

			throw new DuplicateVocabularyException(name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);
		vocabulary.setFolksonomy(folksonomy);

		tagsVocabularyPersistence.update(vocabulary, false);

		return vocabulary;
	}

	protected boolean hasVocabulary(long companyId, String name)
		throws SystemException {

		if (tagsVocabularyPersistence.countByC_N(companyId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void validate(String name) throws PortalException {
		if (!TagsUtil.isValidWord(name)) {
			throw new VocabularyNameException();
		}
	}

}