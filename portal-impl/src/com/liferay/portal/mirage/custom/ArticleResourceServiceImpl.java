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
import com.liferay.portal.mirage.model.JournalResourceContent;
import com.liferay.portal.mirage.model.OptionalJournalResourceCriteria;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourceUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ArticleResourceServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class ArticleResourceServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void deleteBinaryContent(
			BinaryContent binaryContent, OptionalCriteria criteria)
		throws CMSException {

		JournalResourceContent journalResourceContent =
							(JournalResourceContent) binaryContent;
		JournalArticleResource journalResource =
						journalResourceContent.getJournalResource();

		try {
			_deleteArticleResource(
				journalResource.getGroupId(), journalResource.getArticleId());
		} catch (PortalException ex) {
			throw new CMSException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public void deleteBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public BinaryContent getBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		JournalResourceContent journalResourceContent =
			(JournalResourceContent) binaryContent;
		JournalArticleResource journalResource =
			journalResourceContent.getJournalResource();

		try {
			journalResource = _getArticleResource(
									journalResource.getResourcePrimKey());
			journalResourceContent.setJournalResource(journalResource);
		} catch (PortalException ex) {
			throw new CMSException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
		return journalResourceContent;
	}

	public long getBinaryContentId(BinaryContent binaryContent)
		throws CMSException {

		JournalResourceContent journalResourceContent =
			(JournalResourceContent) binaryContent;
		JournalArticleResource journalResource =
			journalResourceContent.getJournalResource();

		try {
			long result = _getArticleResourcePrimKey(
								journalResource.getGroupId(),
									journalResource.getArticleId());
			return result;
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public List<BinaryContent> getBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		List<BinaryContent> binaryContents = new ArrayList<BinaryContent>();

		try {
			OptionalJournalResourceCriteria journalCriteria =
				(OptionalJournalResourceCriteria)criteria;

			String groupId = _getValue(
								journalCriteria,
									OptionalJournalResourceCriteria.GROUP_ID);
			List<JournalArticleResource> journalResources =
				_getArticleResources(Long.parseLong(groupId));
			_populateBinaryContents(binaryContents, journalResources);

			return binaryContents;
		} catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	public void updateBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	private void _deleteArticleResource(long groupId, String articleId)
		throws PortalException, SystemException {

		_resourcePersistence.removeByG_A(groupId, articleId);
	}

	private JournalArticleResource _getArticleResource(
			long articleResourcePrimKey)
		throws PortalException, SystemException {

		return _resourcePersistence.findByPrimaryKey(
			articleResourcePrimKey);
	}

	private long _getArticleResourcePrimKey(long groupId, String articleId)
		throws SystemException {

		JournalArticleResource articleResource =
			_resourcePersistence.fetchByG_A(groupId, articleId);

		if (articleResource == null) {
			long articleResourcePrimKey = _counterLocalService.increment();

			articleResource = _resourcePersistence.create(
				articleResourcePrimKey);

			articleResource.setGroupId(groupId);
			articleResource.setArticleId(articleId);

			_resourcePersistence.update(articleResource, false);
		}

		return articleResource.getResourcePrimKey();
	}

	private List<JournalArticleResource> _getArticleResources(long groupId)
		throws SystemException {

		return _resourcePersistence.findByGroupId(groupId);
	}

	private String _getValue(
		OptionalJournalResourceCriteria criteria, String key) {

		return criteria.getOptions().get(key);
	}

	private void _populateBinaryContents(
		List<BinaryContent> binaryContents,
		List<JournalArticleResource> journalResources) {

		if (journalResources == null){
			return;
		}

		for(JournalArticleResource journalResource : journalResources){
			binaryContents.add(new JournalResourceContent(journalResource));
		}
	}

	private CounterLocalService _counterLocalService =
		CounterLocalServiceFactory.getImpl();

	private JournalArticleResourcePersistence _resourcePersistence =
		JournalArticleResourceUtil.getPersistence();

}