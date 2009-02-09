/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.benchmark.model;

/**
 * <a href="Permission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Permission {

	public Permission(long permissionId, long companyId, String actionId,
					  long resourceId) {
		_permissionId = permissionId;
		_companyId = companyId;
		_actionId = actionId;
		_resourceId = resourceId;
	}

	public long getPermissionId() {
		return _permissionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getActionId() {
		return _actionId;
	}

	public long getResourceId() {
		return _resourceId;
	}

	private long _permissionId;
	private long _companyId;
	private String _actionId;
	private long _resourceId;
}
