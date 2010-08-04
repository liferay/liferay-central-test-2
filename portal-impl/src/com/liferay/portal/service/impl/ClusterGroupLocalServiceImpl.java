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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.service.base.ClusterGroupLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClusterGroupLocalServiceImpl
	extends ClusterGroupLocalServiceBaseImpl {

	public ClusterGroup addClusterGroup(
			String name, List<String> clusterNodeIds)
		throws SystemException {

		long ClusterGroupId = counterLocalService.increment();
		ClusterGroup clusterGroup = clusterGroupPersistence.create(
			ClusterGroupId);
		clusterGroup.setName(name);

		int size = clusterNodeIds.size();
		StringBundler sb = new StringBundler(size * 2 -1);
		for(int i = 0; i < size; i++) {
			sb.append(clusterNodeIds.get(i));
			if (i < size - 1) {
				sb.append(StringPool.COMMA);
			}
		}

		clusterGroup.setClusterNodeIds(sb.toString());

		return clusterGroupPersistence.update(clusterGroup, false);
	}

	public ClusterGroup addWholeClusterGroup(String name)
		throws SystemException {
		long ClusterGroupId = counterLocalService.increment();
		ClusterGroup clusterGroup = clusterGroupPersistence.create(
			ClusterGroupId);
		clusterGroup.setName(name);
		clusterGroup.setWholeCluster(true);

		return clusterGroupPersistence.update(clusterGroup, false);
	}

}