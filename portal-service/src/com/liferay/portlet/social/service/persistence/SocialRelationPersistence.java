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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialRelation;

/**
 * <a href="SocialRelationPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationPersistenceImpl
 * @see       SocialRelationUtil
 * @generated
 */
public interface SocialRelationPersistence extends BasePersistence<SocialRelation> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialRelation socialRelation);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialRelation> socialRelations);

	public com.liferay.portlet.social.model.SocialRelation create(
		long relationId);

	public com.liferay.portlet.social.model.SocialRelation remove(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation fetchByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByUuid_PrevAndNext(
		long relationId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByCompanyId_PrevAndNext(
		long relationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByUserId1_First(
		long userId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByUserId1_Last(
		long userId1, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByUserId1_PrevAndNext(
		long relationId, long userId1,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByUserId2_First(
		long userId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByUserId2_Last(
		long userId2, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByUserId2_PrevAndNext(
		long relationId, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByType_First(
		int type, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByType_Last(
		int type, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByType_PrevAndNext(
		long relationId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByC_T_PrevAndNext(
		long relationId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByU1_T_First(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByU1_T_Last(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByU1_T_PrevAndNext(
		long relationId, long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation findByU2_T_First(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByU2_T_Last(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation[] findByU2_T_PrevAndNext(
		long relationId, long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation findByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}