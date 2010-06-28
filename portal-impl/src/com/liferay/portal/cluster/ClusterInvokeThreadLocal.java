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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * <a href="ClusterInvokeThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterInvokeThreadLocal {

	public static boolean isClusterInvoke() {
		return _clusterInvoke.get();
	}

	public static void setClusterInvoke(boolean clusterInvoke) {
		_clusterInvoke.set(clusterInvoke);
	}

	private static ThreadLocal<Boolean> _clusterInvoke =
		new InitialThreadLocal<Boolean>(
			ClusterInvokeThreadLocal.class.getName(), Boolean.FALSE);

}