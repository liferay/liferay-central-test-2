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
 * <a href="JournalTemplateServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateServiceUtil {
	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String templateId, boolean autoTemplateId,
		java.lang.String plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalTemplateService journalTemplateService = JournalTemplateServiceFactory.getService();

		return journalTemplateService.addTemplate(templateId, autoTemplateId,
			plid, structureId, name, description, xsl, formatXsl, langType,
			smallImage, smallImageURL, smallFile, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String templateId, boolean autoTemplateId,
		java.lang.String plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalTemplateService journalTemplateService = JournalTemplateServiceFactory.getService();

		return journalTemplateService.addTemplate(templateId, autoTemplateId,
			plid, structureId, name, description, xsl, formatXsl, langType,
			smallImage, smallImageURL, smallFile, communityPermissions,
			guestPermissions);
	}

	public static void deleteTemplate(java.lang.String companyId,
		java.lang.String groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalTemplateService journalTemplateService = JournalTemplateServiceFactory.getService();
		journalTemplateService.deleteTemplate(companyId, groupId, templateId);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalTemplateService journalTemplateService = JournalTemplateServiceFactory.getService();

		return journalTemplateService.getTemplate(companyId, groupId, templateId);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		java.lang.String groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalTemplateService journalTemplateService = JournalTemplateServiceFactory.getService();

		return journalTemplateService.updateTemplate(groupId, templateId,
			structureId, name, description, xsl, formatXsl, langType,
			smallImage, smallImageURL, smallFile);
	}
}