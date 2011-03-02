/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * <a href="RevisionConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class LayoutRevisionConstants {

	public static final long DEFAULT_PARENT_LAYOUT_REVISION_ID = 0;

	public static String encodeKey(long layoutSetBranchId, long plid) {
		StringBundler sb = new StringBundler(5);

		sb.append("LAYOUT_SET_BRANCH_ID_");
		sb.append(layoutSetBranchId);
		sb.append("_PLID_");
		sb.append(plid);
		sb.append("_LAYOUT_REVISION_ID");

		return sb.toString();
	}

}