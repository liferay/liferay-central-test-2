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
import com.liferay.portal.mirage.model.MirageArticleImage;
import com.liferay.portal.mirage.model.MirageArticleImageCriteria;
import com.liferay.portal.mirage.service.MirageServiceFactory;
import com.liferay.portal.mirage.util.ExceptionTranslator;
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
 * @author Karthik Sudarshan
 *
 */
public class JournalArticleImageLocalServiceImpl
	extends JournalArticleImageLocalServiceBaseImpl {

	public void addArticleImageId(
			long articleImageId, long groupId, String articleId, double version,
			String elName, String languageId)
		throws PortalException, SystemException {

		JournalArticleImage articleImage = new JournalArticleImageImpl();

		articleImage.setArticleImageId(articleImageId);
		articleImage.setGroupId(groupId);
		articleImage.setArticleId(articleId);
		articleImage.setVersion(version);
		articleImage.setElName(elName);
		articleImage.setLanguageId(languageId);

		MirageArticleImage mirageArticleImage =
			new MirageArticleImage(articleImage);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			binaryContentService.createBinaryContent(mirageArticleImage);
		}
		catch(CMSException cmse) {
			ExceptionTranslator.translate(cmse);
		}
	}

	public void deleteArticleImage(long articleImageId) throws SystemException {
		JournalArticleImage articleImage = new JournalArticleImageImpl();

		articleImage.setArticleImageId(articleImageId);

		MirageArticleImage mirageArticleImage =
			new MirageArticleImage(articleImage);

		MirageArticleImageCriteria criteria = new MirageArticleImageCriteria();

		criteria.setValue(
			MirageArticleImageCriteria.ACTION,
			MirageArticleImageCriteria.REMOVE_BY_ID);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			binaryContentService.deleteBinaryContent(
				mirageArticleImage, criteria);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public void deleteArticleImage(
			long groupId, String articleId, double version, String elName,
			String languageId)
		throws SystemException {

		JournalArticleImage articleImage = new JournalArticleImageImpl();

		articleImage.setGroupId(groupId);
		articleImage.setArticleId(articleId);
		articleImage.setVersion(version);
		articleImage.setElName(elName);
		articleImage.setLanguageId(languageId);

		MirageArticleImage mirageArticleImage =
			new MirageArticleImage(articleImage);

		MirageArticleImageCriteria criteria = new MirageArticleImageCriteria();

		criteria.setValue(
			MirageArticleImageCriteria.ACTION,
			MirageArticleImageCriteria.REMOVE_BY_G_A_V_E_L);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			binaryContentService.deleteBinaryContent(
				mirageArticleImage, criteria);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public void deleteImages(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		MirageArticleImageCriteria criteria = new MirageArticleImageCriteria();

		criteria.setValue(MirageArticleImageCriteria.GROUP_ID, groupId);
		criteria.setValue(MirageArticleImageCriteria.ARTICLE_ID, articleId);
		criteria.setValue(MirageArticleImageCriteria.VERSION, version);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			binaryContentService.deleteBinaryContents(criteria);
		}
		catch(CMSException cmse) {
			ExceptionTranslator.translate(cmse);
		}
	}

	public JournalArticleImage getArticleImage(long articleImageId)
		throws PortalException, SystemException {

		JournalArticleImage articleImage = new JournalArticleImageImpl();

		articleImage.setArticleImageId(articleImageId);

		MirageArticleImage mirageArticleImage =
			new MirageArticleImage(articleImage);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			mirageArticleImage = (MirageArticleImage)
				binaryContentService.getBinaryContent(mirageArticleImage);
		}
		catch(CMSException cmse) {
			ExceptionTranslator.translate(cmse);
		}

		return mirageArticleImage.getJournalImage();
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

		JournalArticleImage articleImage = new JournalArticleImageImpl();

		articleImage.setGroupId(groupId);
		articleImage.setArticleId(articleId);
		articleImage.setVersion(version);
		articleImage.setElName(elName);
		articleImage.setLanguageId(languageId);
		articleImage.setTempImage(tempImage);

		MirageArticleImage mirageArticleImage =
			new MirageArticleImage(articleImage);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			return binaryContentService.getBinaryContentId(mirageArticleImage);
		}
		catch(CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public List<JournalArticleImage> getArticleImages(long groupId)
		throws SystemException {

		MirageArticleImageCriteria criteria = new MirageArticleImageCriteria();

		criteria.setValue(MirageArticleImageCriteria.GROUP_ID, groupId);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleImageService();

		try {
			List<BinaryContent> binaryContents =
				binaryContentService.getBinaryContents(criteria);

			return getImages(binaryContents);
		}
		catch(CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	protected List<JournalArticleImage> getImages(
		List<BinaryContent> binaryContents) {

		List<JournalArticleImage> images = new ArrayList<JournalArticleImage>();

		for(BinaryContent binaryContent : binaryContents){
			MirageArticleImage mirageArticleImage =
				(MirageArticleImage)binaryContent;

			images.add(mirageArticleImage.getJournalImage());
		}

		return images;
	}

}