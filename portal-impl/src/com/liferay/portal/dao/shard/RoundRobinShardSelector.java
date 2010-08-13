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

package com.liferay.portal.dao.shard;

import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

/**
 * @author Alexander Chow
 */
public class RoundRobinShardSelector implements ShardSelector {

	public String getShardName(
		String scope, String shardName, Map<String, String> params) {

		if (scope.equals(ShardSelector.COMPANY_SCOPE)) {
			int instances = PortalInstances.getCompanyIds().length;
			int shards = PropsValues.SHARD_AVAILABLE_NAMES.length;

			return PropsValues.SHARD_AVAILABLE_NAMES[instances % shards];
		}
		else {
			return PropsValues.SHARD_DEFAULT_NAME;
		}
	}

}