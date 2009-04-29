/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="PollsChoicePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface PollsChoicePersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice);

	public void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> pollsChoices);

	public void clearCache();

	public com.liferay.portlet.polls.model.PollsChoice create(long choiceId);

	public com.liferay.portlet.polls.model.PollsChoice remove(long choiceId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice remove(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(PollsChoice pollsChoice, boolean merge)</code>.
	 */
	public com.liferay.portlet.polls.model.PollsChoice update(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        pollsChoice the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when pollsChoice is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.polls.model.PollsChoice update(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.polls.model.PollsChoice updateImpl(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.polls.model.PollsChoice findByPrimaryKey(
		long choiceId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice fetchByPrimaryKey(
		long choiceId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.polls.model.PollsChoice findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice[] findByUuid_PrevAndNext(
		long choiceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.polls.model.PollsChoice findByQuestionId_First(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice findByQuestionId_Last(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice[] findByQuestionId_PrevAndNext(
		long choiceId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice findByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByQuestionId(long questionId)
		throws com.liferay.portal.SystemException;

	public void removeByQ_N(long questionId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByQuestionId(long questionId)
		throws com.liferay.portal.SystemException;

	public int countByQ_N(long questionId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}