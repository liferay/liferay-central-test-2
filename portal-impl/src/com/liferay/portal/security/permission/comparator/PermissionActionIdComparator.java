/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission.comparator;

import com.liferay.portal.model.Permission;

import java.io.Serializable;

import java.util.Comparator;

/**
 * <a href="PermissionActionIdComparator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionActionIdComparator
	implements Comparator<Object>, Serializable {

	public int compare(Object obj1, Object obj2) {
		String actionId1 = null;

		if (obj1 instanceof String) {
			actionId1 = (String)obj1;
		}
		else {
			Permission permission1 = (Permission)obj1;

			actionId1 = permission1.getActionId();
		}

		String actionId2 = null;

		if (obj2 instanceof String) {
			actionId2 = (String)obj2;
		}
		else {
			Permission permission2 = (Permission)obj2;

			actionId2 = permission2.getActionId();
		}

		return actionId1.compareTo(actionId2);
	}

}