/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchUserIdMapperException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.service.persistence.UserIdMapperPK;
import com.liferay.portal.service.persistence.UserIdMapperUtil;
import com.liferay.portal.service.spring.UserIdMapperLocalService;

import java.util.List;

/**
 * <a href="UserIdMapperLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserIdMapperLocalServiceImpl implements UserIdMapperLocalService {

	public void deleteUserIdMappers(String userId) throws SystemException {
		UserIdMapperUtil.removeByUserId(userId);
	}

	public UserIdMapper getUserIdMapper(String userId, String type)
		throws PortalException, SystemException {

		return UserIdMapperUtil.findByPrimaryKey(
			new UserIdMapperPK(userId, type));
	}

	public List getUserIdMappers(String userId) throws SystemException {
		return UserIdMapperUtil.findByUserId(userId);
	}

	public UserIdMapper updateUserIdMapper(
			String userId, String type, String description,
			String externalUserId)
		throws PortalException, SystemException {

		UserIdMapperPK pk = new UserIdMapperPK(userId, type);

		UserIdMapper userIdMapper = null;

		try {
			userIdMapper = UserIdMapperUtil.findByPrimaryKey(pk);
		}
		catch (NoSuchUserIdMapperException uidme) {
			userIdMapper = UserIdMapperUtil.create(pk);
		}

		userIdMapper.setDescription(description);
		userIdMapper.setExternalUserId(externalUserId);

		UserIdMapperUtil.update(userIdMapper);

		return userIdMapper;
	}

}