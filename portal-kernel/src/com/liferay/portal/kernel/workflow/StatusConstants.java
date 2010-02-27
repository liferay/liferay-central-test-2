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

package com.liferay.portal.kernel.workflow;

/**
 * <a href="StatusConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class StatusConstants {

	public static final int ANY = -1;

	public static final int APPROVED = 0;

	public static final int DENIED = 4;

	public static final int DRAFT = 2;

	public static final int EXPIRED = 3;

	public static final String LABEL_ANY = "any";

	public static final String LABEL_APPROVED = "approved";

	public static final String LABEL_DENIED = "denied";

	public static final String LABEL_DRAFT = "draft";

	public static final String LABEL_EXPIRED = "expired";

	public static final String LABEL_PENDING = "pending";

	public static final int PENDING = 1;

	public static int fromLabel(String status) {
		if (status.equals(LABEL_ANY)) {
			return ANY;
		}
		else if (status.equals(LABEL_APPROVED)) {
			return APPROVED;
		}
		else if (status.equals(LABEL_DENIED)) {
			return DENIED;
		}
		else if (status.equals(LABEL_DRAFT)) {
			return DRAFT;
		}
		else if (status.equals(LABEL_EXPIRED)) {
			return EXPIRED;
		}
		else if (status.equals(LABEL_PENDING)) {
			return PENDING;
		}
		else {
			return ANY;
		}
	}

	public static String toLabel(int status) {
		if (status == ANY) {
			return LABEL_ANY;
		}
		else if (status == APPROVED) {
			return LABEL_APPROVED;
		}
		else if (status == DENIED) {
			return LABEL_DENIED;
		}
		else if (status == DRAFT) {
			return LABEL_DRAFT;
		}
		else if (status == EXPIRED) {
			return LABEL_EXPIRED;
		}
		else if (status == PENDING) {
			return LABEL_PENDING;
		}
		else {
			return LABEL_ANY;
		}
	}

}