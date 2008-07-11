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
import com.liferay.portal.mirage.model.JournalResourceContent;
import com.liferay.portal.mirage.model.OptionalJournalResourceCriteria;
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
 *
 */
public class JournalArticleResourceLocalServiceImpl
	extends JournalArticleResourceLocalServiceBaseImpl {

	public void deleteArticleResource(long groupId, String articleId)
		throws PortalException, SystemException {

		try {
			JournalArticleResource journalResource =
									new JournalArticleResourceImpl();
			journalResource.setGroupId(groupId);
			journalResource.setArticleId(articleId);

			JournalResourceContent resourceContent =
							new JournalResourceContent(journalResource);
			BinaryContentService binaryContentService =
						MirageServiceFactory.getArticleResourceService();

			binaryContentService.deleteBinaryContent(
					resourceContent, new OptionalJournalResourceCriteria());

		}
		catch (CMSException ex) {
			_throwException(ex);
		}
	}

	public JournalArticleResource getArticleResource(
			long articleResourcePrimKey)
		throws PortalException, SystemException {

		JournalArticleResource journalResource =
								new JournalArticleResourceImpl();
		journalResource.setResourcePrimKey(articleResourcePrimKey);

		JournalResourceContent resourceContent =
						new JournalResourceContent(journalResource);

		try {
			BinaryContentService binaryContentService =
				MirageServiceFactory.getArticleResourceService();

			resourceContent = (JournalResourceContent)
					binaryContentService.getBinaryContent(resourceContent);
		} catch(CMSException ex) {
			_throwException(ex);
		}

		return resourceContent.getJournalResource();
	}

	public long getArticleResourcePrimKey(long groupId, String articleId)
		throws SystemException {

		try {
			JournalArticleResource journalResource =
										new JournalArticleResourceImpl();
			journalResource.setGroupId(groupId);
			journalResource.setArticleId(articleId);

			JournalResourceContent resourceContent =
								new JournalResourceContent(journalResource);
			BinaryContentService binaryContentService =
				MirageServiceFactory.getArticleResourceService();

			return binaryContentService.getBinaryContentId(resourceContent);

		} catch(CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalArticleResource> getArticleResources(long groupId)
		throws SystemException {

		try {
			OptionalJournalResourceCriteria criteria =
						new OptionalJournalResourceCriteria();
			criteria.getOptions().put(
					OptionalJournalResourceCriteria.GROUP_ID,
					String.valueOf(groupId));

			BinaryContentService binaryContentService =
				MirageServiceFactory.getArticleResourceService();

			List<BinaryContent> binaryContents =
				binaryContentService.getBinaryContents(criteria);

			return _getResourcesFromBinaryContents(binaryContents);

		} catch(CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	private List<JournalArticleResource> _getResourcesFromBinaryContents(
		List<BinaryContent> binaryContents) {

		List<JournalArticleResource> resources =
									new ArrayList<JournalArticleResource>();
		for(BinaryContent binaryContent : binaryContents){
			resources.add(
				((JournalResourceContent) binaryContent).getJournalResource());
		}

		return resources;
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