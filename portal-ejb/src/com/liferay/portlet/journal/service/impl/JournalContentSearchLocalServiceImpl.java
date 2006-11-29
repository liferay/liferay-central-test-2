/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalContentSearchLocalService;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchPK;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalContentSearchLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchLocalServiceImpl
	implements JournalContentSearchLocalService {

	public void checkContentSearches(String companyId)
		throws PortalException, SystemException {

		List layouts = new ArrayList();

		List groups = GroupLocalServiceUtil.search(
			companyId, null, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group)groups.get(i);

			// Private layouts

			String ownerId = LayoutImpl.PRIVATE + group.getGroupId();

			deleteOwnerContentSearches(ownerId);

			layouts.addAll(LayoutLocalServiceUtil.getLayouts(ownerId));

			// Public layouts

			ownerId = LayoutImpl.PUBLIC + group.getGroupId();

			deleteOwnerContentSearches(ownerId);

			layouts.addAll(LayoutLocalServiceUtil.getLayouts(ownerId));
		}

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = (Layout)layouts.get(i);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List portletIds = layoutTypePortlet.getPortletIds();

			for (int j = 0; j < portletIds.size(); j++) {
				String portletId = (String)portletIds.get(j);

				String rootPortletId = PortletImpl.getRootPortletId(portletId);

				if (rootPortletId.equals(PortletKeys.JOURNAL_CONTENT)) {
					try {
						PortletPreferencesPK prefsPK = new PortletPreferencesPK(
							portletId, layout.getLayoutId(),
							layout.getOwnerId());

						PortletPreferences prefs =
							PortletPreferencesLocalServiceUtil.getPreferences(
								layout.getCompanyId(), prefsPK);

						String articleId = prefs.getValue(
							"article-id", StringPool.BLANK);

						if (Validator.isNotNull(articleId)) {
							updateContentSearch(
								portletId, layout.getLayoutId(),
								layout.getOwnerId(), layout.getCompanyId(),
								articleId);
						}
					}
					catch (NoSuchPortletPreferencesException nsppe) {
					}
				}
			}
		}
	}

	public void deleteArticleContentSearches(String companyId, String articleId)
		throws SystemException {

		JournalContentSearchUtil.removeByC_A(companyId, articleId);
	}

	public void deleteLayoutContentSearches(String layoutId, String ownerId)
		throws SystemException {

		JournalContentSearchUtil.removeByL_O(layoutId, ownerId);
	}

	public void deleteOwnerContentSearches(String ownerId)
		throws SystemException {

		JournalContentSearchUtil.removeByOwnerId(ownerId);
	}

	public List getArticleContentSearches(String companyId, String articleId)
		throws SystemException {

		return JournalContentSearchUtil.findByC_A(companyId, articleId);
	}

	public List getLayoutIds(String ownerId, String articleId)
		throws SystemException {

		List layoutIds = new ArrayList();

		Iterator itr = JournalContentSearchUtil.findByO_A(
			ownerId, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch contentSearch =
				(JournalContentSearch)itr.next();

			layoutIds.add(contentSearch.getLayoutId());
		}

		return layoutIds;
	}

	public int getLayoutIdsCount(String ownerId, String articleId)
		throws SystemException {

		return JournalContentSearchUtil.countByO_A(ownerId, articleId);
	}

	public JournalContentSearch updateContentSearch(
			String portletId, String layoutId, String ownerId, String companyId,
			String articleId)
		throws PortalException, SystemException {

		JournalContentSearchPK pk = new JournalContentSearchPK(
			portletId, layoutId, ownerId, articleId);

		JournalContentSearch contentSearch = null;

		try {
			contentSearch = JournalContentSearchUtil.findByPrimaryKey(pk);
		}
		catch (NoSuchContentSearchException nscse) {
			contentSearch = JournalContentSearchUtil.create(pk);
		}

		contentSearch.setCompanyId(companyId);

		JournalContentSearchUtil.update(contentSearch);

		return contentSearch;
	}

	public List updateContentSearch(
			String portletId, String layoutId, String ownerId, String companyId,
			String[] articleIds)
		throws PortalException, SystemException {

		List contentSearches = new ArrayList();

		for (int i = 0; i < articleIds.length; i++) {
			JournalContentSearch contentSearch = updateContentSearch(
				portletId, layoutId, ownerId, companyId, articleIds[i]);

			contentSearches.add(contentSearch);
		}

		return contentSearches;
	}

}