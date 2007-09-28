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

import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.tags.model.impl.TagsAssetImpl;
import com.liferay.util.dao.hibernate.DynamicQueryInitializerImpl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * <a href="VerifyDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class VerifyDocumentLibrary extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying integrity");

		try {
			verifyDocumentLibrary();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	protected void verifyDocumentLibrary() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());

		DetachedCriteria entriesWithTagsAssets = DetachedCriteria.forClass(
			TagsAssetImpl.class, "tagsAsset");

		entriesWithTagsAssets = entriesWithTagsAssets.add(
			Property.forName("tagsAsset.classNameId").eq(
				new Long(classNameId)));

		entriesWithTagsAssets = entriesWithTagsAssets.setProjection(
			Property.forName("tagsAsset.classPK"));

		DetachedCriteria entriesWithoutTagsAssets = DetachedCriteria.forClass(
			DLFileEntryImpl.class, "dlFileEntry");

		entriesWithoutTagsAssets = entriesWithoutTagsAssets.add(
			Restrictions.not(
				Subqueries.propertyIn(
					"dlFileEntry.fileEntryId", entriesWithTagsAssets)));

		DynamicQueryInitializer dqi = new DynamicQueryInitializerImpl(
			entriesWithoutTagsAssets);

		List fileEntries = DLFileEntryLocalServiceUtil.dynamicQuery(dqi);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + fileEntries.size() +
					" file entries with no tags assets");
		}

		for (int i = 0; i < fileEntries.size(); i++) {
			DLFileEntry fileEntry = (DLFileEntry)fileEntries.get(i);

			DLFileEntryLocalServiceUtil.updateTagsAsset(
				fileEntry, new String[0]);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Tags assets verified for file entries");
		}
	}

	private static Log _log = LogFactory.getLog(VerifyDocumentLibrary.class);

}