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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.UserIdMapper;

/**
 * <a href="UserIdMapperPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapperPersistenceImpl
 * @see       UserIdMapperUtil
 * @generated
 */
public interface UserIdMapperPersistence extends BasePersistence<UserIdMapper> {
	public void cacheResult(com.liferay.portal.model.UserIdMapper userIdMapper);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserIdMapper> userIdMappers);

	public com.liferay.portal.model.UserIdMapper create(long userIdMapperId);

	public com.liferay.portal.model.UserIdMapper remove(long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper findByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper fetchByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper[] findByUserId_PrevAndNext(
		long userIdMapperId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper findByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_E(java.lang.String type,
		java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_E(java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}