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
 * <a href="JournalTemplateLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalTemplateLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplateLocalService
 * @generated
 */
public class JournalTemplateLocalServiceWrapper
	implements JournalTemplateLocalService {
	public JournalTemplateLocalServiceWrapper(
		JournalTemplateLocalService journalTemplateLocalService) {
		_journalTemplateLocalService = journalTemplateLocalService;
	}

	public com.liferay.portlet.journal.model.JournalTemplate addJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.addJournalTemplate(journalTemplate);
	}

	public com.liferay.portlet.journal.model.JournalTemplate createJournalTemplate(
		long id) {
		return _journalTemplateLocalService.createJournalTemplate(id);
	}

	public void deleteJournalTemplate(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.deleteJournalTemplate(id);
	}

	public void deleteJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException {
		_journalTemplateLocalService.deleteJournalTemplate(journalTemplate);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getJournalTemplate(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getJournalTemplate(id);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getJournalTemplates(
		int start, int end) throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getJournalTemplates(start, end);
	}

	public int getJournalTemplatesCount()
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getJournalTemplatesCount();
	}

	public com.liferay.portlet.journal.model.JournalTemplate updateJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.updateJournalTemplate(journalTemplate);
	}

	public com.liferay.portlet.journal.model.JournalTemplate updateJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.updateJournalTemplate(journalTemplate,
			merge);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, long groupId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.addTemplate(userId, groupId,
			templateId, autoTemplateId, structureId, name, description, xsl,
			formatXsl, langType, cacheable, smallImage, smallImageURL,
			smallFile, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String templateId, boolean autoTemplateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.addTemplate(uuid, userId, groupId,
			templateId, autoTemplateId, structureId, name, description, xsl,
			formatXsl, langType, cacheable, smallImage, smallImageURL,
			smallFile, serviceContext);
	}

	public void addTemplateResources(long groupId, java.lang.String templateId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.addTemplateResources(groupId, templateId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.addTemplateResources(template,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(long groupId, java.lang.String templateId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.addTemplateResources(groupId, templateId,
			communityPermissions, guestPermissions);
	}

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.addTemplateResources(template,
			communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.checkNewLine(groupId, templateId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate copyTemplate(
		long userId, long groupId, java.lang.String oldTemplateId,
		java.lang.String newTemplateId, boolean autoTemplateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.copyTemplate(userId, groupId,
			oldTemplateId, newTemplateId, autoTemplateId);
	}

	public void deleteTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.deleteTemplate(groupId, templateId);
	}

	public void deleteTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.deleteTemplate(template);
	}

	public void deleteTemplates(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalTemplateLocalService.deleteTemplates(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getStructureTemplates(groupId,
			structureId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getStructureTemplates(groupId,
			structureId, start, end);
	}

	public int getStructureTemplatesCount(long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getStructureTemplatesCount(groupId,
			structureId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplate(id);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplate(groupId, templateId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getTemplateBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplateBySmallImageId(smallImageId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates()
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplates();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId) throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplates(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplates(groupId, start, end);
	}

	public int getTemplatesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.getTemplatesCount(groupId);
	}

	public boolean hasTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.hasTemplate(groupId, templateId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long groupId, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.search(companyId, groupId,
			keywords, structureId, structureIdComparator, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.search(companyId, groupId,
			templateId, structureId, structureIdComparator, name, description,
			andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.searchCount(companyId, groupId,
			keywords, structureId, structureIdComparator);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _journalTemplateLocalService.searchCount(companyId, groupId,
			templateId, structureId, structureIdComparator, name, description,
			andOperator);
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
		return _journalTemplateLocalService.updateTemplate(groupId, templateId,
			structureId, name, description, xsl, formatXsl, langType,
			cacheable, smallImage, smallImageURL, smallFile, serviceContext);
	}

	public JournalTemplateLocalService getWrappedJournalTemplateLocalService() {
		return _journalTemplateLocalService;
	}

	private JournalTemplateLocalService _journalTemplateLocalService;
}