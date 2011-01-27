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

package com.liferay.portal.kernel.notifications.event;

import java.util.Comparator;

/**
 * @author Edward Han
 */
public class NotificationEventComparator
	implements Comparator<NotificationEvent> {

	public int compare(
		NotificationEvent notificationEvent1,
		NotificationEvent notificationEvent2) {

		long difference = notificationEvent2.getDeliverBy() -
			notificationEvent1.getDeliverBy();

		int intDifference;

		if (difference > Integer.MAX_VALUE
			|| notificationEvent2.getDeliverBy() == 0) {

			intDifference = Integer.MAX_VALUE;
		}
		else if (difference < Integer.MIN_VALUE
			|| notificationEvent1.getDeliverBy() == 0) {

			intDifference = Integer.MIN_VALUE;
		}
		else {
			intDifference = (int) difference;
		}

		return intDifference;
	}
}