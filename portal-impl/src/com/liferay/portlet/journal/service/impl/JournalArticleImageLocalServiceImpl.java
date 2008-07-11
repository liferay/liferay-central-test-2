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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.mirage.custom.MirageServiceFactory;
import com.liferay.portal.mirage.model.JournalImageContent;
import com.liferay.portal.mirage.model.OptionalJournalImageCriteria;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.impl.JournalArticleImageImpl;
import com.liferay.portlet.journal.service.base.JournalArticleImageLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalArticleImageLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Prakash Reddy
 *
 */
public class JournalArticleImageLocalServiceImpl
	extends JournalArticleImageLocalServiceBaseImpl {

	public void addArticleImageId(
			long articleImageId, long groupId, String articleId, double version,
			String elName, String languageId)
		throws PortalException, SystemException {

		try {
			JournalArticleImage journalImage = new JournalArticleImageImpl();
			journalImage.setArticleImageId(articleImageId);
			journalImage.setGroupId(groupId);
			journalImage.setArticleId(articleId);
			journalImage.setVersion(version);
			journalImage.setElName(elName);
			journalImage.setLanguageId(languageId);

			JournalImageContent imageContent =
							new JournalImageContent(journalImage);
			BinaryContentService binaryContentService =
							MirageServiceFactory.getImageContentService();

			binaryContentService.createBinaryContent(imageContent);

		} catch(CMSException ex) {
			_throwException(ex);
		}
	}

	public void deleteArticleImage(long articleImageId) throws SystemException {

		try {
			JournalArticleImage journalImage = new JournalArticleImageImpl();
			journalImage.setArticleImageId(articleImageId);

			JournalImageContent imageContent =
							new JournalImageContent(journalImage);
			OptionalJournalImageCriteria criteria =
							new OptionalJournalImageCriteria();
			criteria.getOptions().put(
					OptionalJournalImageCriteria.FINDER,
					OptionalJournalImageCriteria.REMOVE_BY_ID);
			BinaryContentService binaryContentService =
				MirageServiceFactory.getImageContentService();

			binaryContentService.deleteBinaryContent(imageContent, criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public void deleteArticleImage(
			long groupId, String articleId, double version, String elName,
			String languageId)
		throws SystemException {

		try {
			JournalArticleImage journalImage = new JournalArticleImageImpl();
			journalImage.setGroupId(groupId);
			journalImage.setArticleId(articleId);
			journalImage.setVersion(version);
			journalImage.setElName(elName);
			journalImage.setLanguageId(languageId);

			JournalImageContent imageContent =
							new JournalImageContent(journalImage);
			OptionalJournalImageCriteria criteria =
							new OptionalJournalImageCriteria();
			criteria.getOptions().put(
					OptionalJournalImageCriteria.FINDER,
					OptionalJournalImageCriteria.REMOVE_BY_G_A_V_E_L);
			BinaryContentService binaryContentService =
				MirageServiceFactory.getImageContentService();

			binaryContentService.deleteBinaryContent(imageContent, criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public void deleteImages(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		try {
			OptionalJournalImageCriteria criteria =
						new OptionalJournalImageCriteria();
			criteria.getOptions().put(
					OptionalJournalImageCriteria.GROUP_ID,
					String.valueOf(groupId));
			criteria.getOptions().put(
					OptionalJournalImageCriteria.ARTICLE_ID,
					articleId);
			criteria.getOptions().put(
					OptionalJournalImageCriteria.VERSION,
					String.valueOf(version));

			BinaryContentService binaryContentService =
				MirageServiceFactory.getImageContentService();

			binaryContentService.deleteBinaryContents(criteria);

		} catch(CMSException ex) {
			_throwException(ex);
		}
	}

	public JournalArticleImage getArticleImage(long articleImageId)
		throws PortalException, SystemException {

		JournalArticleImage journalImage = new JournalArticleImageImpl();
		journalImage.setArticleImageId(articleImageId);

		JournalImageContent imageContent =
						new JournalImageContent(journalImage);

		try {
			BinaryContentService binaryContentService =
							MirageServiceFactory.getImageContentService();

			imageContent = (JournalImageContent)
					binaryContentService.getBinaryContent(imageContent);
		} catch(CMSException ex) {
			_throwException(ex);
		}

		return imageContent.getJournalImage();
	}

	public long getArticleImageId(
			long groupId, String articleId, double version, String elName,
			String languageId)
		throws SystemException {

		return getArticleImageId(
			groupId, articleId, version, elName, languageId, false);
	}

	public long getArticleImageId(
			long groupId, String articleId, double version, String elName,
			String languageId, boolean tempImage)
		throws SystemException {

		try {
			JournalArticleImage journalImage = new JournalArticleImageImpl();
			journalImage.setGroupId(groupId);
			journalImage.setArticleId(articleId);
			journalImage.setVersion(version);
			journalImage.setElName(elName);
			journalImage.setLanguageId(languageId);
			journalImage.setTempImage(tempImage);

			JournalImageContent imageContent =
							new JournalImageContent(journalImage);
			BinaryContentService binaryContentService =
				MirageServiceFactory.getImageContentService();

			return binaryContentService.getBinaryContentId(imageContent);

		} catch(CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalArticleImage> getArticleImages(long groupId)
		throws SystemException {

		try {
			OptionalJournalImageCriteria criteria =
						new OptionalJournalImageCriteria();
			criteria.getOptions().put(
					OptionalJournalImageCriteria.GROUP_ID,
					String.valueOf(groupId));

			BinaryContentService binaryContentService =
				MirageServiceFactory.getImageContentService();

			List<BinaryContent> binaryContents =
				binaryContentService.getBinaryContents(criteria);

			return _getImagesFromBinaryContents(binaryContents);

		} catch(CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	private List<JournalArticleImage> _getImagesFromBinaryContents(
		List<BinaryContent> binaryContents) {

		List<JournalArticleImage> images = new ArrayList<JournalArticleImage>();
		for(BinaryContent binaryContent : binaryContents){
			images.add(
				((JournalImageContent) binaryContent).getJournalImage());
		}

		return images;
	}

	private void _throwException(CMSException ex)
		throws PortalException, SystemException {
		Throwable cause = ex.getCause();
		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException) cause;
			} else if (cause instanceof SystemException) {
				throw (SystemException) cause;
			}
		}
	}

}