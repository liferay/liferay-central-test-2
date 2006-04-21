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
 * <a href="JournalStructureLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String userId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.addStructure(userId,
				structureId, autoStructureId, plid, name, description, xsd,
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

	public static void addStructureResources(java.lang.String companyId,
		java.lang.String structureId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();
			journalStructureLocalService.addStructureResources(companyId,
				structureId, addCommunityPermissions, addGuestPermissions);
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

	public static void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();
			journalStructureLocalService.addStructureResources(structure,
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

	public static void checkNewLine(java.lang.String companyId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();
			journalStructureLocalService.checkNewLine(companyId, structureId);
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
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();
			journalStructureLocalService.deleteStructure(companyId, structureId);
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

	public static void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();
			journalStructureLocalService.deleteStructure(structure);
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
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.getStructure(companyId,
				structureId);
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

	public static java.util.List getStructures(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.getStructures(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getStructures(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.getStructures(groupId, begin,
				end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getStructuresCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.getStructuresCount(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.search(companyId, structureId,
				groupId, name, description, andOperator, begin, end, obc);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String structureId, java.lang.String groupId,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.searchCount(companyId,
				structureId, groupId, name, description, andOperator);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateStructure(
		java.lang.String companyId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

			return journalStructureLocalService.updateStructure(companyId,
				structureId, name, description, xsd);
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