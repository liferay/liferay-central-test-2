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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.categories.model.CategoriesVocabulary;
import com.liferay.portlet.categories.service.base.CategoriesVocabularyServiceBaseImpl;
import com.liferay.portlet.categories.service.permission.CategoriesPermission;
import com.liferay.portlet.categories.service.permission.CategoriesVocabularyPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CategoriesVocabularyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 *
 */
public class CategoriesVocabularyServiceImpl
	extends CategoriesVocabularyServiceBaseImpl {

	public CategoriesVocabulary addVocabulary(
			String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		CategoriesPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_VOCABULARY);

		return categoriesVocabularyLocalService.addVocabulary(
			getUserId(), name, serviceContext);
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		CategoriesVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

		categoriesVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	public List<CategoriesVocabulary> getCompanyVocabularies(long companyId)
		throws PortalException, SystemException {

		return getVocabularies(
			categoriesVocabularyLocalService.getCompanyVocabularies(companyId));
	}

	public List<CategoriesVocabulary> getGroupVocabularies(
			long groupId, boolean folksonomy)
		throws PortalException, SystemException {

		return getVocabularies(
			categoriesVocabularyLocalService.getGroupVocabularies(groupId));
	}

	public CategoriesVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		CategoriesVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.VIEW);

		return categoriesVocabularyLocalService.getVocabulary(vocabularyId);
	}

	public CategoriesVocabulary updateVocabulary(long vocabularyId, String name)
		throws PortalException, SystemException {

		CategoriesVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.UPDATE);

		return categoriesVocabularyLocalService.updateVocabulary(
			vocabularyId, name);
	}

	protected List<CategoriesVocabulary> getVocabularies(
			List<CategoriesVocabulary> vocabularies)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		vocabularies = ListUtil.copy(vocabularies);

		Iterator<CategoriesVocabulary> itr = vocabularies.iterator();

		while (itr.hasNext()) {
			CategoriesVocabulary vocabulary = itr.next();

			if (!CategoriesVocabularyPermission.contains(
					permissionChecker, vocabulary, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return vocabularies;
	}

}