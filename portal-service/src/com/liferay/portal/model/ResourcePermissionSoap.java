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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ResourcePermissionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.ResourcePermissionServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ResourcePermissionServiceSoap
 *
 */
public class ResourcePermissionSoap implements Serializable {
	public static ResourcePermissionSoap toSoapModel(ResourcePermission model) {
		ResourcePermissionSoap soapModel = new ResourcePermissionSoap();

		soapModel.setResourcePermissionId(model.getResourcePermissionId());
		soapModel.setResourceId(model.getResourceId());
		soapModel.setRoleId(model.getRoleId());
		soapModel.setActionIds(model.getActionIds());

		return soapModel;
	}

	public static ResourcePermissionSoap[] toSoapModels(
		List<ResourcePermission> models) {
		List<ResourcePermissionSoap> soapModels = new ArrayList<ResourcePermissionSoap>(models.size());

		for (ResourcePermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourcePermissionSoap[soapModels.size()]);
	}

	public ResourcePermissionSoap() {
	}

	public long getPrimaryKey() {
		return _resourcePermissionId;
	}

	public void setPrimaryKey(long pk) {
		setResourcePermissionId(pk);
	}

	public long getResourcePermissionId() {
		return _resourcePermissionId;
	}

	public void setResourcePermissionId(long resourcePermissionId) {
		_resourcePermissionId = resourcePermissionId;
	}

	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		_resourceId = resourceId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public long getActionIds() {
		return _actionIds;
	}

	public void setActionIds(long actionIds) {
		_actionIds = actionIds;
	}

	private long _resourcePermissionId;
	private long _resourceId;
	private long _roleId;
	private long _actionIds;
}