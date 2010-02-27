/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.polls.model.PollsChoice;

import java.util.List;

/**
 * <a href="PollsChoiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoicePersistence
 * @see       PollsChoicePersistenceImpl
 * @generated
 */
public class PollsChoiceUtil {
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
	public static PollsChoice remove(PollsChoice pollsChoice)
		throws SystemException {
		return getPersistence().remove(pollsChoice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PollsChoice update(PollsChoice pollsChoice, boolean merge)
		throws SystemException {
		return getPersistence().update(pollsChoice, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice) {
		getPersistence().cacheResult(pollsChoice);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> pollsChoices) {
		getPersistence().cacheResult(pollsChoices);
	}

	public static com.liferay.portlet.polls.model.PollsChoice create(
		long choiceId) {
		return getPersistence().create(choiceId);
	}

	public static com.liferay.portlet.polls.model.PollsChoice remove(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().remove(choiceId);
	}

	public static com.liferay.portlet.polls.model.PollsChoice updateImpl(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(pollsChoice, merge);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByPrimaryKey(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByPrimaryKey(choiceId);
	}

	public static com.liferay.portlet.polls.model.PollsChoice fetchByPrimaryKey(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice[] findByUuid_PrevAndNext(
		long choiceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUuid_PrevAndNext(choiceId, uuid, obc);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_First(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByQuestionId_First(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_Last(
		long questionId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByQuestionId_Last(questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice[] findByQuestionId_PrevAndNext(
		long choiceId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByQuestionId_PrevAndNext(choiceId, questionId, obc);
	}

	public static com.liferay.portlet.polls.model.PollsChoice findByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByQ_N(questionId, name);
	}

	public static com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_N(questionId, name);
	}

	public static com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_N(questionId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	public static void removeByQ_N(long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		getPersistence().removeByQ_N(questionId, name);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	public static int countByQ_N(long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQ_N(questionId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PollsChoicePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PollsChoicePersistence)PortalBeanLocatorUtil.locate(PollsChoicePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PollsChoicePersistence persistence) {
		_persistence = persistence;
	}

	private static PollsChoicePersistence _persistence;
}