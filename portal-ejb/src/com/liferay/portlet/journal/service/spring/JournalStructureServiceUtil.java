/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.journal.service.spring;

/**
 * <a href="JournalStructureServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureServiceUtil {
	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String structureId, boolean autoStructureId,
		java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureService journalStructureService = JournalStructureServiceFactory.getService();

			return journalStructureService.addStructure(structureId,
				autoStructureId, plid, name, description, xsd,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteStructure(java.lang.String companyId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureService journalStructureService = JournalStructureServiceFactory.getService();
			journalStructureService.deleteStructure(companyId, structureId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.journal.model.JournalStructure getStructure(
		java.lang.String companyId, java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureService journalStructureService = JournalStructureServiceFactory.getService();

			return journalStructureService.getStructure(companyId, structureId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateStructure(
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureService journalStructureService = JournalStructureServiceFactory.getService();

			return journalStructureService.updateStructure(structureId, name,
				description, xsd);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}