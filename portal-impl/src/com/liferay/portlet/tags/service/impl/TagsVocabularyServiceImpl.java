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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.service.base.TagsVocabularyServiceBaseImpl;
import com.liferay.portlet.tags.service.permission.TagsPermission;
import com.liferay.portlet.tags.service.permission.TagsVocabularyPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsVocabularyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 *
 */
public class TagsVocabularyServiceImpl extends TagsVocabularyServiceBaseImpl {

	public TagsVocabulary addVocabulary(
			String name, boolean folksonomy, ServiceContext serviceContext)
		throws PortalException, SystemException {

		TagsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_VOCABULARY);

		return tagsVocabularyLocalService.addVocabulary(
			getUserId(), name, folksonomy, serviceContext);
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		TagsVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

		tagsVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	public List<TagsVocabulary> getCompanyVocabularies(
			long companyId, boolean folksonomy)
		throws PortalException, SystemException {

		return getVocabularies(
			tagsVocabularyLocalService.getCompanyVocabularies(
				companyId, folksonomy));
	}

	public List<TagsVocabulary> getGroupVocabularies(
			long groupId, boolean folksonomy)
		throws PortalException, SystemException {

		return getVocabularies(
			tagsVocabularyLocalService.getGroupVocabularies(
				groupId, folksonomy));
	}

	public TagsVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		TagsVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.VIEW);

		return tagsVocabularyLocalService.getVocabulary(vocabularyId);
	}

	public TagsVocabulary updateVocabulary(
			long vocabularyId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		TagsVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.UPDATE);

		return tagsVocabularyLocalService.updateVocabulary(
			vocabularyId, name, folksonomy);
	}

	protected List<TagsVocabulary> getVocabularies(
			List<TagsVocabulary> vocabularies)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		vocabularies = ListUtil.copy(vocabularies);

		Iterator<TagsVocabulary> itr = vocabularies.iterator();

		while (itr.hasNext()) {
			TagsVocabulary vocabulary = itr.next();

			if (!TagsVocabularyPermission.contains(
					permissionChecker, vocabulary, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return vocabularies;
	}

}