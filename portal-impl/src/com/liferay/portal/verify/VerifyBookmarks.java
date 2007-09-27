/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.tags.model.impl.TagsAssetImpl;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.dao.hibernate.DynamicQueryInitializerImpl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * <a href="VerifyBookmarks.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class VerifyBookmarks extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying integrity");

		try {
			_verifyBookmarks();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	private void _verifyBookmarks() throws Exception {

		long classNameId = 
			ClassNameLocalServiceUtil.getClassName(
					BookmarksEntry.class.getName()).getClassNameId();

		DetachedCriteria existingTagsAssetEntriesIds = 
			DetachedCriteria.forClass(TagsAssetImpl.class, "ta")
				.add(Property.forName(
						"ta.classNameId").eq(new Long(classNameId)))
				.setProjection(Property.forName("ta.classPK"));

		DetachedCriteria entriesWithMissingTagsAssets = 
			DetachedCriteria.forClass(BookmarksEntryImpl.class, "bm")
			.add(Restrictions.not(
					Subqueries.propertyIn(
							"bm.entryId", existingTagsAssetEntriesIds)));

		DynamicQueryInitializer dqi = 
			new DynamicQueryInitializerImpl(entriesWithMissingTagsAssets);

		try {
			List entries = BookmarksEntryLocalServiceUtil.dynamicQuery(dqi);

			if (_log.isDebugEnabled()) {
				_log.debug("Processing " + entries.size() + " entries");
			}

			for (int i = 0; i < entries.size(); i++) {
				BookmarksEntry entry = (BookmarksEntry)entries.get(i);

				TagsAssetLocalServiceUtil.updateAsset(
						entry.getUserId(), BookmarksEntry.class.getName(),
						entry.getEntryId(), new String[0], null, null, null, null,
						ContentTypes.TEXT_PLAIN, entry.getName(), entry.getComments(),
						entry.getComments(), entry.getUrl(), 0, 0);
			}
		}
		catch (SystemException se) {
			_log.error(se);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("TagsAssets verified for Bookmarks entries");
		}
	}

	private static Log _log = LogFactory.getLog(VerifyBookmarks.class);

}