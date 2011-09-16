/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.social.RelationUserIdException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.base.SocialRelationLocalServiceBaseImpl;

import java.util.List;

/**
 * The social relation local service. This service provides methods to handle
 * uni- or bidirectional relations between users.
 *
 * <p>
 * Relations between users can be uni- or bidirectional. The type of relation
 * is identified by an integer constant. Possible relations are <i>co-worker,
 * friend, romantic partner, sibling, spouse, child, enemy, follower, parent,
 * subordinate, supervisor</i>.
 * </p>
 *
 * <p>
 * The two users participating in the relation are designated as user1 and
 * user2. In case of unidirectional relations User1 should always be the
 * subject of the relation. You can use the following English sentence to find
 * out which user to use as user1 and user2:
 * </p>
 *
 * <p>
 * User1 is <i>&lt;relation&gt;</i> of user2 (e.g. User1 is parent of user2;
 * User1 is supervisor of user2)
 * </p>
 *
 * <p>
 * For bidirectional relations, the service automatically generates the inverse
 * relation.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRelationLocalServiceImpl
	extends SocialRelationLocalServiceBaseImpl {

	/**
	 * Adds a new social relation between two users to the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return the social relation
	 * @throws PortalException if the users could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation addRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		if (userId1 == userId2) {
			throw new RelationUserIdException();
		}

		User user1 = userPersistence.findByPrimaryKey(userId1);
		User user2 = userPersistence.findByPrimaryKey(userId2);

		if (user1.getCompanyId() != user2.getCompanyId()) {
			throw new RelationUserIdException();
		}

		SocialRelation relation = socialRelationPersistence.fetchByU1_U2_T(
			userId1, userId2, type);

		if (relation == null) {
			long relationId = counterLocalService.increment();

			relation = socialRelationPersistence.create(relationId);

			relation.setCompanyId(user1.getCompanyId());
			relation.setCreateDate(System.currentTimeMillis());
			relation.setUserId1(userId1);
			relation.setUserId2(userId2);
			relation.setType(type);

			socialRelationPersistence.update(relation, false);
		}

		if (SocialRelationConstants.isTypeBi(type)) {
			SocialRelation biRelation =
				socialRelationPersistence.fetchByU1_U2_T(
					userId2, userId1, type);

			if (biRelation == null) {
				long biRelationId = counterLocalService.increment();

				biRelation = socialRelationPersistence.create(biRelationId);

				biRelation.setCompanyId(user1.getCompanyId());
				biRelation.setCreateDate(System.currentTimeMillis());
				biRelation.setUserId1(userId2);
				biRelation.setUserId2(userId1);
				biRelation.setType(type);

				socialRelationPersistence.update(biRelation, false);
			}
		}

		return relation;
	}

	/**
	 * Remove the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  relationId the primary key of the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelation(long relationId)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByPrimaryKey(
			relationId);

		deleteRelation(relation);
	}

	/**
	 * Remove the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);

		deleteRelation(relation);
	}

	/**
	 * Remove the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  relation the relation to be removed
	 * @throws PortalException if the inverse relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelation(SocialRelation relation)
		throws PortalException, SystemException {

		socialRelationPersistence.remove(relation);

		if (SocialRelationConstants.isTypeBi(relation.getType())) {
			SocialRelation biRelation = socialRelationPersistence.findByU1_U2_T(
				relation.getUserId2(), relation.getUserId1(),
				relation.getType());

			socialRelationPersistence.remove(biRelation);
		}
	}

	/**
	 * Removes all relations from the database the user is part of.
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelations(long userId) throws SystemException {
		socialRelationPersistence.removeByUserId1(userId);
		socialRelationPersistence.removeByUserId2(userId);
	}

	/**
	 * Removes all relations between user1 and user2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @throws PortalException if the inverse relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelations(long userId1, long userId2)
		throws PortalException, SystemException {

		List<SocialRelation> relations = socialRelationPersistence.findByU1_U2(
			userId1, userId2);

		for (SocialRelation relation : relations) {
			deleteRelation(relation);
		}
	}

	/**
	 * Returns a range of all the inverse relations of the given type for the
	 * user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the
	 * full result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  type the type of the relation
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> getInverseRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU2_T(userId, type, start, end);
	}

	/**
	 * Returns the number of inverse relations of the given type for the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  type the type of the relation
	 * @return the number of relations
	 * @throws SystemException if a system exception occurred
	 */
	public int getInverseRelationsCount(long userId, int type)
		throws SystemException {

		return socialRelationPersistence.countByU2_T(userId, type);
	}

	/**
	 * Returns the relation identified by its primary key.
	 *
	 * @param  relationId the primary key of the relation
	 * @return Returns the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation getRelation(long relationId)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByPrimaryKey(relationId);
	}

	/**
	 * Returns the relation with the given type between user1 and user2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return Returns the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation getRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);
	}

	/**
	 * Returns a range of all the relations with the given type where the user
	 * is the subject of the relation.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the
	 * full result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  type the type of the relation
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> getRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_T(userId, type, start, end);
	}

	/**
	 * Returns a range of all the relations between user1 and user2.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the
	 * full result set.
	 * </p>
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialRelation> getRelations(
			long userId1, long userId2, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_U2(
			userId1, userId2, start, end);
	}

	/**
	 * Returns the number of relations with the given type where the user is
	 * the subject of the relation.
	 *
	 * @param  userId the primary key of the user
	 * @param  type the type of the relation
	 * @return the number of relations
	 * @throws SystemException if a system exception occurred
	 */
	public int getRelationsCount(long userId, int type) throws SystemException {
		return socialRelationPersistence.countByU1_T(userId, type);
	}

	/**
	 * Returns the number of relations between user1 and user2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @return the number of relations
	 * @throws SystemException if a system exception occurred
	 */
	public int getRelationsCount(long userId1, long userId2)
		throws SystemException {

		return socialRelationPersistence.countByU1_U2(userId1, userId2);
	}

	/**
	 * Returns <code>true</code> if a relation with the given type exists
	 * between user1 and user2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return <code>true</code> if the relation exists; <code>false</code>
	 *         otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasRelation(long userId1, long userId2, int type)
		throws SystemException {

		SocialRelation relation = socialRelationPersistence.fetchByU1_U2_T(
			userId1, userId2, type);

		if (relation == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Returns <code>true</code> if user1 and user2 can be in a relation with
	 * the given type.
	 *
	 * <p>
	 * This method returns <code>false</code> when user1 and user2 are the
	 * same, or when any of the users is the default user. It also returns
	 * <code>false</code> if a relation with the given type already exists
	 * between the two users.
	 * </p>
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return <code>true</code> if the two users can be in the given relation;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRelatable(long userId1, long userId2, int type)
		throws SystemException {

		if (userId1 == userId2) {
			return false;
		}

		User user1 = userPersistence.fetchByPrimaryKey(userId1);

		if ((user1 == null) || user1.isDefaultUser()) {
			return false;
		}

		User user2 = userPersistence.fetchByPrimaryKey(userId2);

		if ((user2 == null) || user2.isDefaultUser()) {
			return false;
		}

		return !hasRelation(userId1, userId2, type);
	}

}