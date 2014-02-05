/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.cluster;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * @author Shuyang Zhou
 */
public class ClusterReplicationThreadLocal {

	public static boolean isReplicateUpdate() {
		return _replicateUpdateThreadLocal.get();
	}

	public static void setReplicateUpdate(boolean replicateUpdate) {
		_replicateUpdateThreadLocal.set(replicateUpdate);
	}

	private static ThreadLocal<Boolean> _replicateUpdateThreadLocal =
		new InitialThreadLocal<Boolean>(
			ClusterReplicationThreadLocal.class +
				"._replicateUpdateThreadLocal",
			Boolean.TRUE);

}