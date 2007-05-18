/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

/**
 * <a href="JournalStructurePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface JournalStructurePersistence {
	public com.liferay.portlet.journal.model.JournalStructure create(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK);

	public com.liferay.portlet.journal.model.JournalStructure remove(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure remove(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalStructure update(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalStructure update(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalStructure findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public java.util.List findByC_S(long companyId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_S(long companyId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_S(long companyId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalStructure findByC_S_First(
		long companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure findByC_S_Last(
		long companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public com.liferay.portlet.journal.model.JournalStructure[] findByC_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalStructurePK journalStructurePK,
		long companyId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchStructureException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findAll() throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByC_S(long companyId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByC_S(long companyId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}