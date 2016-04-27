/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.lcs.rest;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface LCSClusterNode {

	public boolean getArchived();

	public int getBuildNumber();

	public String getDescription();

	public long getInstallationId();

	public String getKey();

	public long getLcsClusterEntryId();

	public long getLcsClusterNodeId();

	public String getLocation();

	public String getName();

	public String getPortalEdition();

	public boolean isArchived();

	public void setArchived(boolean archived);

	public void setBuildNumber(int buildNumber);

	public void setDescription(String description);

	public void setInstallationId(long installationId);

	public void setKey(String key);

	public void setLcsClusterEntryId(long lcsClusterEntryId);

	public void setLcsClusterNodeId(long lcsClusterNodeId);

	public void setLocation(String location);

	public void setName(String name);

	public void setPortalEdition(String portalEdition);

}