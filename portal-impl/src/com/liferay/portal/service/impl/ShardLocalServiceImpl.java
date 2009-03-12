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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.base.ShardLocalServiceBaseImpl;

/**
 * <a href="ShardLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShardLocalServiceImpl extends ShardLocalServiceBaseImpl {

	public void addCompany(long companyId, String shardName)
		throws SystemException {

		long classNameId =
			classNameLocalService.getClassNameId(Company.class);

		long shardId = counterLocalService.increment();

		Shard shard = shardPersistence.create(shardId);

		shard.setClassPK(companyId);
		shard.setClassNameId(classNameId);
		shard.setJdbcName(shardName);

		shardPersistence.update(shard, false);
	}

	public String getShardNameByCompanyId(long companyId)
		throws PortalException, SystemException {

		long classNameId =
			classNameLocalService.getClassNameId(Company.class);

		Shard shard = shardPersistence.findByC_C(classNameId, companyId);

		return shard.getJdbcName();
	}

}