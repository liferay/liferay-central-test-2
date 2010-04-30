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

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

/**
 * <a href="ClusterResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public interface ClusterResponse extends Serializable {

	public Exception getException();

	public Object getResult();

	public String getUuid();

	public boolean hasException();

	public boolean isMulticast();

	public void setException(Exception exception);

	public void setMulticast(boolean multicast);

	public void setResult(Object result);

	public void setUuid(String uuid);

}