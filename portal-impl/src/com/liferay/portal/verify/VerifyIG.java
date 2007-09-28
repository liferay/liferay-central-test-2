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
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
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
 * <a href="VerifyIG.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class VerifyIG extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying integrity");

		try {
			_verifyIG();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	private void _verifyIG() throws Exception {

		long classNameId = 
			ClassNameLocalServiceUtil.getClassName(
					IGImage.class.getName()).getClassNameId();

		DetachedCriteria existingTagsAssetEntriesIds = 
			DetachedCriteria.forClass(TagsAssetImpl.class, "ta")
				.add(Property.forName(
						"ta.classNameId").eq(new Long(classNameId)))
				.setProjection(Property.forName("ta.classPK"));

		DetachedCriteria entriesWithMissingTagsAssets = 
			DetachedCriteria.forClass(IGImageImpl.class, "igi")
			.add(Restrictions.not(
					Subqueries.propertyIn(
							"igi.imageId", existingTagsAssetEntriesIds)));

		DynamicQueryInitializer dqi = 
			new DynamicQueryInitializerImpl(entriesWithMissingTagsAssets);

		try {
			List entries = IGImageLocalServiceUtil.dynamicQuery(dqi);

			if (_log.isDebugEnabled()) {
				_log.debug("Processing " + entries.size() + " entries");
			}

			for (int i = 0; i < entries.size(); i++) {
				IGImage image = (IGImage)entries.get(i);

				Image largeImage = 
					ImageLocalUtil.getImage(image.getLargeImageId());

				TagsAssetLocalServiceUtil.updateAsset(
					image.getUserId(), IGImage.class.getName(),
					image.getImageId(), new String[0], null, null, null, null, 
					largeImage.getType(), image.getDescription(),
					image.getDescription(), image.getDescription(), null, 
					largeImage.getHeight(), largeImage.getWidth());
			}
		}
		catch (SystemException se) {			
			_log.error(se);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("TagsAssets verified for IG Image entries");
		}
	}

	private static Log _log = LogFactory.getLog(VerifyIG.class);

}