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

package com.liferay.portal.mirage.custom;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.mirage.model.JournalImageContent;
import com.liferay.portal.mirage.model.OptionalJournalImageCriteria;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.ImageLocalServiceFactory;
import com.liferay.portlet.journal.DuplicateArticleImageIdException;
import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ImageContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class ImageContentServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		JournalImageContent journalImageContent =
			(JournalImageContent) binaryContent;
		JournalArticleImage journalImage =
			journalImageContent.getJournalImage();
		try {
			_addArticleImageId(
					journalImage.getArticleImageId(),
						journalImage.getGroupId(),
							journalImage.getArticleId(),
								journalImage.getVersion(),
									journalImage.getElName(),
										journalImage.getLanguageId());
		} catch (PortalException ex) {
			throw new CMSException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public void deleteBinaryContent(
			BinaryContent binaryContent, OptionalCriteria criteria)
		throws CMSException {

		JournalImageContent journalImageContent =
			(JournalImageContent) binaryContent;
		JournalArticleImage journalImage =
			journalImageContent.getJournalImage();

		try {
			OptionalJournalImageCriteria journalCriteria =
				(OptionalJournalImageCriteria)criteria;

			String finder = _getValue(
								journalCriteria,
									OptionalJournalImageCriteria.FINDER);

			if (OptionalJournalImageCriteria.REMOVE_BY_ID.equals(finder)){

				_deleteArticleImage(journalImage.getArticleImageId());

			} else if (OptionalJournalImageCriteria.REMOVE_BY_G_A_V_E_L
							.equals(finder)){

				_deleteArticleImage(
						journalImage.getGroupId(),
							journalImage.getArticleId(),
								journalImage.getVersion(),
									journalImage.getElName(),
										journalImage.getLanguageId());

			}
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public void deleteBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		try {
			OptionalJournalImageCriteria journalCriteria =
						(OptionalJournalImageCriteria)criteria;

			String groupId = _getValue(
								journalCriteria,
									OptionalJournalImageCriteria.GROUP_ID);

			String articleId = _getValue(
								journalCriteria,
									OptionalJournalImageCriteria.ARTICLE_ID);

			String version = _getValue(
								journalCriteria,
									OptionalJournalImageCriteria.VERSION);

			_deleteImages(
					Long.parseLong(groupId),
						articleId,
							Double.parseDouble(version));

		} catch (PortalException ex) {
			throw new CMSException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public BinaryContent getBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		JournalImageContent journalImageContent =
			(JournalImageContent) binaryContent;
		JournalArticleImage journalImage =
			journalImageContent.getJournalImage();

		try {
			journalImage = _getArticleImage(journalImage.getArticleImageId());
			journalImageContent.setJournalImage(journalImage);
		} catch (PortalException ex) {
			throw new CMSException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return journalImageContent;
	}

	public long getBinaryContentId(BinaryContent binaryContent)
		throws CMSException {

		JournalImageContent journalImageContent =
			(JournalImageContent) binaryContent;
		JournalArticleImage journalImage =
			journalImageContent.getJournalImage();

		try {
			long result;
			result = _getArticleImageId(
						journalImage.getGroupId(),
							journalImage.getArticleId(),
								journalImage.getVersion(),
									journalImage.getElName(),
										journalImage.getLanguageId(),
											journalImage.isTempImage());
			return result;
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public List<BinaryContent> getBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		List<BinaryContent> binaryContents = new ArrayList<BinaryContent>();

		try {
			OptionalJournalImageCriteria journalCriteria =
				(OptionalJournalImageCriteria)criteria;

			String groupId = _getValue(
								journalCriteria,
									OptionalJournalImageCriteria.GROUP_ID);
			List<JournalArticleImage> journalImages =
						_getArticleImages(Long.parseLong(groupId));
			_populateBinaryContents(binaryContents, journalImages);

			return binaryContents;
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public void updateBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	private void _addArticleImageId(
			long articleImageId, long groupId, String articleId, double version,
			String elName, String languageId)
		throws PortalException, SystemException {

		if (articleImageId <= 0) {
			return;
		}

		JournalArticleImage articleImage =
			_journalArticleImagePersistence.fetchByG_A_V_E_L(
				groupId, articleId, version, elName, languageId);

		if (articleImage == null) {
			articleImage = _journalArticleImagePersistence.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(false);

			_journalArticleImagePersistence.update(articleImage, false);
		}
		else if (articleImage.getArticleImageId() == articleImageId) {
		}
		else {
			throw new DuplicateArticleImageIdException();
		}
	}

	private void _deleteArticleImage(long articleImageId)
		throws SystemException {
		try {
			_journalArticleImagePersistence.remove(articleImageId);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	private void _deleteArticleImage(
			long groupId, String articleId, double version, String elName,
			String languageId)
		throws SystemException {

		try {
			_journalArticleImagePersistence.removeByG_A_V_E_L(
				groupId, articleId, version, elName, languageId);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	private void _deleteImages(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		for (JournalArticleImage articleImage :
				_journalArticleImagePersistence.findByG_A_V(
					groupId, articleId, version)) {

			_imageLocalService.deleteImage(articleImage.getArticleImageId());

			_journalArticleImagePersistence.remove(articleImage);
		}
	}

	private JournalArticleImage _getArticleImage(long articleImageId)
		throws PortalException, SystemException {

		return _journalArticleImagePersistence.findByPrimaryKey(articleImageId);
	}

	private long _getArticleImageId(
			long groupId, String articleId, double version, String elName,
			String languageId, boolean tempImage)
		throws SystemException {

		JournalArticleImage articleImage =
				_journalArticleImagePersistence.fetchByG_A_V_E_L(
					groupId, articleId, version, elName, languageId);

		if (articleImage == null) {
			long articleImageId = _counterLocalService.increment();

			articleImage = _journalArticleImagePersistence.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(tempImage);

			_journalArticleImagePersistence.update(articleImage, false);
		}

		return articleImage.getArticleImageId();
	}

	private List<JournalArticleImage> _getArticleImages(long groupId)
		throws SystemException {

		return _journalArticleImagePersistence.findByGroupId(groupId);
	}

	private String _getValue(
			OptionalJournalImageCriteria criteria, String key) {

		return criteria.getOptions().get(key);
	}

	private void _populateBinaryContents(
		List<BinaryContent> binaryContents,
		List<JournalArticleImage> journalImages) {

		if (journalImages == null){
			return;
		}

		for(JournalArticleImage journalImage : journalImages){
			binaryContents.add(new JournalImageContent(journalImage));
		}
	}

	private CounterLocalService _counterLocalService =
		CounterLocalServiceFactory.getImpl();

	private ImageLocalService _imageLocalService =
		ImageLocalServiceFactory.getImpl();

	private JournalArticleImagePersistence _journalArticleImagePersistence =
		JournalArticleImageUtil.getPersistence();

}