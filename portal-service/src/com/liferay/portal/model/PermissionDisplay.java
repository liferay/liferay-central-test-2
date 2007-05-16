/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

/**
 * <a href="PermissionDisplay.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionDisplay implements Comparable, Serializable {

	public PermissionDisplay(Permission permission, Resource resource,
							 String resourceName, String resourceNameParam,
							 String resourceLabel, String actionId,
							 String actionLabel) {

		_permission = permission;
		_resource = resource;
		_resourceName = resourceName;
		_resourceNameParam = resourceNameParam;
		_resourceLabel = resourceLabel;
		_actionId = actionId;
		_actionLabel = actionLabel;
	}

	public Permission getPermission() {
		return _permission;
	}

	public Resource getResource() {
		return _resource;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getResourceNameParam() {
		return _resourceNameParam;
	}

	public String getResourceLabel() {
		return _resourceLabel;
	}

	public String getActionId() {
		return _actionId;
	}

	public String getActionLabel() {
		return _actionLabel;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PermissionDisplay permissionDisplay = (PermissionDisplay)obj;

		int value = getResourceLabel().compareTo(
			permissionDisplay.getResourceLabel());

		if (value == 0) {
			value = getActionLabel().compareTo(
				permissionDisplay.getActionLabel());
		}

		return value;
	}

	private Permission _permission;
	private Resource _resource;
	private String _resourceName;
	private String _resourceNameParam;
	private String _resourceLabel;
	private String _actionId;
	private String _actionLabel;

}