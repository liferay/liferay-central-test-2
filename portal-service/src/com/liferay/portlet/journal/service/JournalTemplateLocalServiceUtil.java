/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String userId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String plid,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.addTemplate(userId, templateId,
			autoTemplateId, plid, structureId, name, description, xsl,
			formatXsl, langType, smallImage, smallImageURL, smallFile,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String userId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String plid,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.addTemplate(userId, templateId,
			autoTemplateId, plid, structureId, name, description, xsl,
			formatXsl, langType, smallImage, smallImageURL, smallFile,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String userId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String plid,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.addTemplate(userId, templateId,
			autoTemplateId, plid, structureId, name, description, xsl,
			formatXsl, langType, smallImage, smallImageURL, smallFile,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addTemplateResources(java.lang.String companyId,
		java.lang.String templateId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.addTemplateResources(companyId, templateId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.addTemplateResources(template,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addTemplateResources(java.lang.String companyId,
		java.lang.String templateId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.addTemplateResources(companyId, templateId,
			communityPermissions, guestPermissions);
	}

	public static void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.addTemplateResources(template,
			communityPermissions, guestPermissions);
	}

	public static void checkNewLine(java.lang.String companyId,
		java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.checkNewLine(companyId, templateId);
	}

	public static void deleteTemplate(java.lang.String companyId,
		java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.deleteTemplate(companyId, templateId);
	}

	public static void deleteTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();
		journalTemplateLocalService.deleteTemplate(template);
	}

	public static java.util.List getStructureTemplates(
		java.lang.String companyId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getStructureTemplates(companyId,
			structureId);
	}

	public static java.util.List getStructureTemplates(
		java.lang.String companyId, java.lang.String structureId, int begin,
		int end) throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getStructureTemplates(companyId,
			structureId, begin, end);
	}

	public static int getStructureTemplatesCount(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getStructureTemplatesCount(companyId,
			structureId);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		java.lang.String companyId, java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getTemplate(companyId, templateId);
	}

	public static java.util.List getTemplates(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getTemplates(groupId);
	}

	public static java.util.List getTemplates(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getTemplates(groupId, begin, end);
	}

	public static int getTemplatesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.getTemplatesCount(groupId);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String templateId, java.lang.String groupId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.search(companyId, templateId,
			groupId, structureId, structureIdComparator, name, description,
			andOperator, begin, end, obc);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String templateId, java.lang.String groupId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.searchCount(companyId, templateId,
			groupId, structureId, structureIdComparator, name, description,
			andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		java.lang.String companyId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalService journalTemplateLocalService = JournalTemplateLocalServiceFactory.getService();

		return journalTemplateLocalService.updateTemplate(companyId,
			templateId, structureId, name, description, xsl, formatXsl,
			langType, smallImage, smallImageURL, smallFile);
	}
}