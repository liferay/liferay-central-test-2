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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.mirage.model.MirageArticleImage;
import com.liferay.portal.mirage.model.MirageArticleImageCriteria;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.journal.DuplicateArticleImageIdException;
import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ArticleImageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 *
 */
public class ArticleImageServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		MirageArticleImage mirageArticleImage =
			(MirageArticleImage)binaryContent;

		JournalArticleImage articleImage = mirageArticleImage.getJournalImage();

		try {
			addArticleImageId(
				articleImage.getArticleImageId(), articleImage.getGroupId(),
				articleImage.getArticleId(), articleImage.getVersion(),
				articleImage.getElName(), articleImage.getLanguageId());
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteBinaryContent(
			BinaryContent binaryContent, OptionalCriteria optionalCriteria)
		throws CMSException {

		MirageArticleImage mirageArticleImage =
			(MirageArticleImage)binaryContent;

		JournalArticleImage articleImage = mirageArticleImage.getJournalImage();

		try {
			MirageArticleImageCriteria criteria =
				(MirageArticleImageCriteria)optionalCriteria;

			String action = criteria.getString(
				MirageArticleImageCriteria.ACTION);

			if (action.equals(MirageArticleImageCriteria.REMOVE_BY_ID)){
				deleteArticleImage(articleImage.getArticleImageId());
			}
			else if (action.equals(MirageArticleImageCriteria.
				REMOVE_BY_G_A_V_E_L)){

				deleteArticleImage(
					articleImage.getGroupId(), articleImage.getArticleId(),
					articleImage.getVersion(), articleImage.getElName(),
					articleImage.getLanguageId());
			}
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteBinaryContents(OptionalCriteria optionalCriteria)
		throws CMSException {

		try {
			MirageArticleImageCriteria criteria =
				(MirageArticleImageCriteria)optionalCriteria;

			long groupId = criteria.getLong(
				MirageArticleImageCriteria.GROUP_ID);

			String articleId = criteria.getString(
				MirageArticleImageCriteria.ARTICLE_ID);

			double version = criteria.getDouble(
				MirageArticleImageCriteria.VERSION);

			deleteImages(groupId, articleId, version);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public BinaryContent getBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		MirageArticleImage mirageArticleImage =
			(MirageArticleImage)binaryContent;

		JournalArticleImage articleImage = mirageArticleImage.getJournalImage();

		try {
			articleImage = getArticleImage(articleImage.getArticleImageId());

			mirageArticleImage.setJournalImage(articleImage);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}

		return mirageArticleImage;
	}

	public long getBinaryContentId(BinaryContent binaryContent)
		throws CMSException {

		MirageArticleImage mirageArticleImage =
			(MirageArticleImage)binaryContent;

		JournalArticleImage articleImage = mirageArticleImage.getJournalImage();

		try {
			return getArticleImageId(
				articleImage.getGroupId(), articleImage.getArticleId(),
				articleImage.getVersion(), articleImage.getElName(),
				articleImage.getLanguageId(), articleImage.isTempImage());
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public List<BinaryContent> getBinaryContents(
			OptionalCriteria optionalCriteria)
		throws CMSException {

		List<BinaryContent> binaryContents = new ArrayList<BinaryContent>();

		try {
			MirageArticleImageCriteria criteria =
				(MirageArticleImageCriteria)optionalCriteria;

			long groupId = criteria.getLong(
				MirageArticleImageCriteria.GROUP_ID);

			List<JournalArticleImage> journalImages = getArticleImages(groupId);

			populateBinaryContents(binaryContents, journalImages);

			return binaryContents;
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateBinaryContent(BinaryContent binaryContent) {
		throw new UnsupportedOperationException();
	}

	protected void addArticleImageId(
			long articleImageId, long groupId, String articleId, double version,
			String elName, String languageId)
		throws PortalException, SystemException {

		if (articleImageId <= 0) {
			return;
		}

		JournalArticleImage articleImage =
			JournalArticleImageUtil.fetchByG_A_V_E_L(
				groupId, articleId, version, elName, languageId);

		if (articleImage == null) {
			articleImage = JournalArticleImageUtil.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(false);

			JournalArticleImageUtil.update(articleImage, false);
		}
		else if (articleImage.getArticleImageId() == articleImageId) {
		}
		else {
			throw new DuplicateArticleImageIdException();
		}
	}

	protected void deleteArticleImage(long articleImageId)
		throws SystemException {

		try {
			JournalArticleImageUtil.remove(articleImageId);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	protected void deleteArticleImage(
			long groupId, String articleId, double version, String elName,
			String languageId)
		throws SystemException {

		try {
			JournalArticleImageUtil.removeByG_A_V_E_L(
				groupId, articleId, version, elName, languageId);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	protected void deleteImages(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		for (JournalArticleImage articleImage :
				JournalArticleImageUtil.findByG_A_V(
					groupId, articleId, version)) {

			ImageLocalServiceUtil.deleteImage(articleImage.getArticleImageId());

			JournalArticleImageUtil.remove(articleImage);
		}
	}

	protected JournalArticleImage getArticleImage(long articleImageId)
		throws PortalException, SystemException {

		return JournalArticleImageUtil.findByPrimaryKey(articleImageId);
	}

	protected long getArticleImageId(
			long groupId, String articleId, double version, String elName,
			String languageId, boolean tempImage)
		throws SystemException {

		JournalArticleImage articleImage =
			JournalArticleImageUtil.fetchByG_A_V_E_L(
				groupId, articleId, version, elName, languageId);

		if (articleImage == null) {
			long articleImageId = CounterLocalServiceUtil.increment();

			articleImage = JournalArticleImageUtil.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(tempImage);

			JournalArticleImageUtil.update(articleImage, false);
		}

		return articleImage.getArticleImageId();
	}

	protected List<JournalArticleImage> getArticleImages(long groupId)
		throws SystemException {

		return JournalArticleImageUtil.findByGroupId(groupId);
	}

	protected void populateBinaryContents(
		List<BinaryContent> binaryContents,
		List<JournalArticleImage> journalImages) {

		if (journalImages == null){
			return;
		}

		for(JournalArticleImage journalImage : journalImages){
			binaryContents.add(new MirageArticleImage(journalImage));
		}
	}

}