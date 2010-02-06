/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalTemplateServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalTemplateService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplateService
 * @generated
 */
public class JournalTemplateServiceWrapper implements JournalTemplateService {
	public JournalTemplateServiceWrapper(
		JournalTemplateService journalTemplateService) {
		_journalTemplateService = journalTemplateService;
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long groupId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.addTemplate(groupId, templateId,
			autoTemplateId, structureId, name, description, xsl, formatXsl,
			langType, cacheable, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long groupId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.addTemplate(groupId, templateId,
			autoTemplateId, structureId, name, description, xsl, formatXsl,
			langType, cacheable, smallImage, smallImageURL, smallFile,
			serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalTemplate copyTemplate(
		long groupId, java.lang.String oldTemplateId,
		java.lang.String newTemplateId, boolean autoTemplateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.copyTemplate(groupId, oldTemplateId,
			newTemplateId, autoTemplateId);
	}

	public void deleteTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateService.deleteTemplate(groupId, templateId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.getStructureTemplates(groupId,
			structureId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.getTemplate(groupId, templateId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.updateTemplate(groupId, templateId,
			structureId, name, description, xsl, formatXsl, langType,
			cacheable, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateService.updateTemplate(groupId, templateId,
			structureId, name, description, xsl, formatXsl, langType,
			cacheable, smallImage, smallImageURL, smallFile, serviceContext);
	}

	public JournalTemplateService getWrappedJournalTemplateService() {
		return _journalTemplateService;
	}

	private JournalTemplateService _journalTemplateService;
}