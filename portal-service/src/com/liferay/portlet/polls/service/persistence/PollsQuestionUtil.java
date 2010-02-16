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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.polls.model.PollsQuestion;

import java.util.List;

/**
 * <a href="PollsQuestionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestionPersistence
 * @see       PollsQuestionPersistenceImpl
 * @generated
 */
public class PollsQuestionUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PollsQuestion remove(PollsQuestion pollsQuestion)
		throws SystemException {
		return getPersistence().remove(pollsQuestion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PollsQuestion update(PollsQuestion pollsQuestion,
		boolean merge) throws SystemException {
		return getPersistence().update(pollsQuestion, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion) {
		getPersistence().cacheResult(pollsQuestion);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsQuestion> pollsQuestions) {
		getPersistence().cacheResult(pollsQuestions);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion create(
		long questionId) {
		return getPersistence().create(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion remove(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().remove(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion updateImpl(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(pollsQuestion, merge);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByPrimaryKey(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByPrimaryKey(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion fetchByPrimaryKey(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(questionId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion[] findByUuid_PrevAndNext(
		long questionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByUuid_PrevAndNext(questionId, uuid, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsQuestion[] findByGroupId_PrevAndNext(
		long questionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(questionId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsQuestion> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchQuestionException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PollsQuestionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PollsQuestionPersistence)PortalBeanLocatorUtil.locate(PollsQuestionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PollsQuestionPersistence persistence) {
		_persistence = persistence;
	}

	private static PollsQuestionPersistence _persistence;
}