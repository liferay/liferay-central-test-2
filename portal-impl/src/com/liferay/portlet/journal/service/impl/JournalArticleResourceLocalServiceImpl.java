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
import com.liferay.portal.mirage.model.JournalArticleResourceCriteria;
import com.liferay.portal.mirage.model.MirageJournalArticleResource;
import com.liferay.portal.mirage.service.MirageServiceFactory;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.service.base.JournalArticleResourceLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalArticleResourceLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 *
 */
public class JournalArticleResourceLocalServiceImpl
	extends JournalArticleResourceLocalServiceBaseImpl {

	public void deleteArticleResource(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticleResource journalResource =
			new JournalArticleResourceImpl();

		journalResource.setGroupId(groupId);
		journalResource.setArticleId(articleId);

		MirageJournalArticleResource mirageResource =
			new MirageJournalArticleResource(journalResource);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleResourceService();

		try {
			binaryContentService.deleteBinaryContent(
				mirageResource, new JournalArticleResourceCriteria());
		}
		catch (CMSException cmse) {
			processException(cmse);
		}
	}

	public JournalArticleResource getArticleResource(
			long articleResourcePrimKey)
		throws PortalException, SystemException {

		JournalArticleResource journalResource =
			new JournalArticleResourceImpl();

		journalResource.setResourcePrimKey(articleResourcePrimKey);

		MirageJournalArticleResource mirageResource =
			new MirageJournalArticleResource(journalResource);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleResourceService();

		try {
			mirageResource =
				(MirageJournalArticleResource)binaryContentService.
					getBinaryContent(mirageResource);
		}
		catch(CMSException cmse) {
			processException(cmse);
		}

		return mirageResource.getJournalResource();
	}

	public long getArticleResourcePrimKey(long groupId, String articleId)
		throws SystemException {

		JournalArticleResource journalResource =
			new JournalArticleResourceImpl();

		journalResource.setGroupId(groupId);
		journalResource.setArticleId(articleId);

		MirageJournalArticleResource mirageResource =
			new MirageJournalArticleResource(journalResource);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleResourceService();

		try {
			return binaryContentService.getBinaryContentId(mirageResource);
		}
		catch(CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public List<JournalArticleResource> getArticleResources(long groupId)
		throws SystemException {

		JournalArticleResourceCriteria criteria =
			new JournalArticleResourceCriteria();

		criteria.add(JournalArticleResourceCriteria.GROUP_ID, groupId);

		BinaryContentService binaryContentService =
			MirageServiceFactory.getArticleResourceService();

		try {
			List<BinaryContent> binaryContents =
				binaryContentService.getBinaryContents(criteria);

			return getResources(binaryContents);
		}
		catch(CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	protected List<JournalArticleResource> getResources(
		List<BinaryContent> binaryContents) {

		List<JournalArticleResource> resources =
			new ArrayList<JournalArticleResource>(binaryContents.size());

		for (BinaryContent binaryContent : binaryContents) {
			MirageJournalArticleResource resource =
				(MirageJournalArticleResource) binaryContent;

			resources.add(resource.getJournalResource());
		}

		return resources;
	}

	protected void processException(CMSException cmse)
		throws PortalException, SystemException {

		Throwable cause = cmse.getCause();

		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException)cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException)cause;
			}
		}
	}

}