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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.categories.model.CategoriesEntry;
import com.liferay.portlet.categories.service.base.CategoriesEntryServiceBaseImpl;
import com.liferay.portlet.categories.service.permission.CategoriesEntryPermission;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CategoriesEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 *
 */
public class CategoriesEntryServiceImpl extends CategoriesEntryServiceBaseImpl {

	public CategoriesEntry addEntry(
			long vocabularyId, long parentEntryId, String name, 
			String[] properties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		CategoriesEntryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentEntryId, ActionKeys.ADD_ENTRY);

		return categoriesEntryLocalService.addEntry(
			getUserId(), vocabularyId, parentEntryId, name, properties,
			serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		CategoriesEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		categoriesEntryLocalService.deleteEntry(entryId);
	}

	public List<CategoriesEntry> getEntries(String className, long classPK)
		throws PortalException, SystemException {

		return getEntries(categoriesEntryLocalService.getEntries(className, classPK));
	}

	public CategoriesEntry getEntry(long entryId)
		throws PortalException, SystemException {

		CategoriesEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return categoriesEntryLocalService.getEntry(entryId);
	}

	public List<CategoriesEntry> getGroupVocabularyEntries(long parentEntryId)
		throws PortalException, SystemException {

		return getEntries(
			categoriesEntryLocalService.getChildEntries(parentEntryId));
	}

	public List<CategoriesEntry> getRootVocabularyEntries(long vocabularyId)
		throws PortalException, SystemException {

		return getEntries(
			categoriesEntryLocalService.getRootVocabularyEntries(vocabularyId));
	}

	public JSONArray search(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		return categoriesEntryLocalService.search(
			groupId, name, properties, start, end);
	}

	public CategoriesEntry updateEntry(
			long entryId, long vocabularyId, long parentEntryId, String name,
			String[] properties)
		throws PortalException, SystemException {

		CategoriesEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return categoriesEntryLocalService.updateEntry(
			getUserId(), entryId, vocabularyId, parentEntryId, name,
			properties);
	}

	protected List<CategoriesEntry> getEntries(List<CategoriesEntry> entries)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		entries = ListUtil.copy(entries);

		Iterator<CategoriesEntry> itr = entries.iterator();

		while (itr.hasNext()) {
			CategoriesEntry entry = itr.next();

			if (!CategoriesEntryPermission.contains(
					permissionChecker, entry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return entries;
	}

}