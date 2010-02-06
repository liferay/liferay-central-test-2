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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalTemplate;

/**
 * <a href="JournalTemplatePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplatePersistenceImpl
 * @see       JournalTemplateUtil
 * @generated
 */
public interface JournalTemplatePersistence extends BasePersistence<JournalTemplate> {
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate);

	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalTemplate> journalTemplates);

	public com.liferay.portlet.journal.model.JournalTemplate create(long id);

	public com.liferay.portlet.journal.model.JournalTemplate remove(long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchByPrimaryKey(
		long id) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_First(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_Last(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate[] findByTemplateId_PrevAndNext(
		long id, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.SystemException;

	public void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public void removeByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.SystemException;

	public int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException;

	public int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException;

	public int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}