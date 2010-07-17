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

package com.liferay.portlet.social.model;

/**
 * <p>
 * This class is a wrapper for {@link SocialRelation}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelation
 * @generated
 */
public class SocialRelationWrapper implements SocialRelation {
	public SocialRelationWrapper(SocialRelation socialRelation) {
		_socialRelation = socialRelation;
	}

	public long getPrimaryKey() {
		return _socialRelation.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialRelation.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _socialRelation.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_socialRelation.setUuid(uuid);
	}

	public long getRelationId() {
		return _socialRelation.getRelationId();
	}

	public void setRelationId(long relationId) {
		_socialRelation.setRelationId(relationId);
	}

	public long getCompanyId() {
		return _socialRelation.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialRelation.setCompanyId(companyId);
	}

	public long getCreateDate() {
		return _socialRelation.getCreateDate();
	}

	public void setCreateDate(long createDate) {
		_socialRelation.setCreateDate(createDate);
	}

	public long getUserId1() {
		return _socialRelation.getUserId1();
	}

	public void setUserId1(long userId1) {
		_socialRelation.setUserId1(userId1);
	}

	public long getUserId2() {
		return _socialRelation.getUserId2();
	}

	public void setUserId2(long userId2) {
		_socialRelation.setUserId2(userId2);
	}

	public int getType() {
		return _socialRelation.getType();
	}

	public void setType(int type) {
		_socialRelation.setType(type);
	}

	public com.liferay.portlet.social.model.SocialRelation toEscapedModel() {
		return _socialRelation.toEscapedModel();
	}

	public boolean isNew() {
		return _socialRelation.isNew();
	}

	public void setNew(boolean n) {
		_socialRelation.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialRelation.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialRelation.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialRelation.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialRelation.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialRelation.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialRelation.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialRelation.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialRelation.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialRelation socialRelation) {
		return _socialRelation.compareTo(socialRelation);
	}

	public int hashCode() {
		return _socialRelation.hashCode();
	}

	public java.lang.String toString() {
		return _socialRelation.toString();
	}

	public java.lang.String toXmlString() {
		return _socialRelation.toXmlString();
	}

	public SocialRelation getWrappedSocialRelation() {
		return _socialRelation;
	}

	private SocialRelation _socialRelation;
}