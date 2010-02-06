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

package com.liferay.portal.model;


/**
 * <a href="UserIdMapperSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserIdMapper}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapper
 * @generated
 */
public class UserIdMapperWrapper implements UserIdMapper {
	public UserIdMapperWrapper(UserIdMapper userIdMapper) {
		_userIdMapper = userIdMapper;
	}

	public long getPrimaryKey() {
		return _userIdMapper.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userIdMapper.setPrimaryKey(pk);
	}

	public long getUserIdMapperId() {
		return _userIdMapper.getUserIdMapperId();
	}

	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapper.setUserIdMapperId(userIdMapperId);
	}

	public long getUserId() {
		return _userIdMapper.getUserId();
	}

	public void setUserId(long userId) {
		_userIdMapper.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _userIdMapper.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_userIdMapper.setUserUuid(userUuid);
	}

	public java.lang.String getType() {
		return _userIdMapper.getType();
	}

	public void setType(java.lang.String type) {
		_userIdMapper.setType(type);
	}

	public java.lang.String getDescription() {
		return _userIdMapper.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_userIdMapper.setDescription(description);
	}

	public java.lang.String getExternalUserId() {
		return _userIdMapper.getExternalUserId();
	}

	public void setExternalUserId(java.lang.String externalUserId) {
		_userIdMapper.setExternalUserId(externalUserId);
	}

	public com.liferay.portal.model.UserIdMapper toEscapedModel() {
		return _userIdMapper.toEscapedModel();
	}

	public boolean isNew() {
		return _userIdMapper.isNew();
	}

	public boolean setNew(boolean n) {
		return _userIdMapper.setNew(n);
	}

	public boolean isCachedModel() {
		return _userIdMapper.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userIdMapper.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userIdMapper.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userIdMapper.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userIdMapper.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userIdMapper.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userIdMapper.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userIdMapper.clone();
	}

	public int compareTo(com.liferay.portal.model.UserIdMapper userIdMapper) {
		return _userIdMapper.compareTo(userIdMapper);
	}

	public int hashCode() {
		return _userIdMapper.hashCode();
	}

	public java.lang.String toString() {
		return _userIdMapper.toString();
	}

	public java.lang.String toXmlString() {
		return _userIdMapper.toXmlString();
	}

	public UserIdMapper getWrappedUserIdMapper() {
		return _userIdMapper;
	}

	private UserIdMapper _userIdMapper;
}