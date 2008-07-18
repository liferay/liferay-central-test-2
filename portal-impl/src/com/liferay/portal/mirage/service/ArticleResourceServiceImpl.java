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
import com.liferay.portal.mirage.model.JournalArticleResourceCriteria;
import com.liferay.portal.mirage.model.MirageJournalArticleResource;
import com.liferay.portlet.journal.model.JournalArticleResource;
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
 * @author Karthik Sudarshan
 *
 */
public class ArticleResourceServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent binaryContent) {
		throw new UnsupportedOperationException();
	}

	public void deleteBinaryContent(
			BinaryContent binaryContent, OptionalCriteria criteria)
		throws CMSException {

		try {
			MirageJournalArticleResource journalResourceContent =
				(MirageJournalArticleResource) binaryContent;

			JournalArticleResource journalResource =
				journalResourceContent.getJournalResource();

			JournalArticleResourceUtil.removeByG_A(
				journalResource.getGroupId(), journalResource.getArticleId());
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteBinaryContents(OptionalCriteria criteria) {
		throw new UnsupportedOperationException();
	}

	public BinaryContent getBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		try {
			MirageJournalArticleResource journalResourceContent =
				(MirageJournalArticleResource) binaryContent;

			JournalArticleResource journalResource =
				journalResourceContent.getJournalResource();

			journalResource = JournalArticleResourceUtil.findByPrimaryKey(
				journalResource.getResourcePrimKey());

			journalResourceContent.setJournalResource(journalResource);

			return journalResourceContent;
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public long getBinaryContentId(BinaryContent binaryContent)
		throws CMSException {

		try {
			MirageJournalArticleResource journalResourceContent =
				(MirageJournalArticleResource) binaryContent;

			JournalArticleResource journalResource =
				journalResourceContent.getJournalResource();

			long groupId = journalResource.getGroupId();
			String articleId = journalResource.getArticleId();

			JournalArticleResource articleResource =
				JournalArticleResourceUtil.fetchByG_A(groupId, articleId);

			if (articleResource == null) {
				long articleResourcePrimKey =
					CounterLocalServiceUtil.increment();

				articleResource = JournalArticleResourceUtil.create(
					articleResourcePrimKey);

				articleResource.setGroupId(groupId);
				articleResource.setArticleId(articleId);

				JournalArticleResourceUtil.update(articleResource, false);
			}

			return articleResource.getResourcePrimKey();
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public List<BinaryContent> getBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		try {
			List<BinaryContent> binaryContents = null;

			JournalArticleResourceCriteria journalCriteria =
				(JournalArticleResourceCriteria)criteria;

			long groupId = journalCriteria.get(
				JournalArticleResourceCriteria.GROUP_ID);

			List<JournalArticleResource> journalResources =
				JournalArticleResourceUtil.findByGroupId(groupId);

			if (journalResources == null) {
				return new ArrayList<BinaryContent>();
			}

			binaryContents = new ArrayList<BinaryContent>(
				journalResources.size());

			for(JournalArticleResource journalResource : journalResources){
				binaryContents.add(
					new MirageJournalArticleResource(journalResource));
			}

			return binaryContents;
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateBinaryContent(BinaryContent binaryContent) {
		throw new UnsupportedOperationException();
	}

}