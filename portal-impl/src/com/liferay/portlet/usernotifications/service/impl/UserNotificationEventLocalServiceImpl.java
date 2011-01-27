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

package com.liferay.portlet.usernotifications.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.usernotifications.model.UserNotificationEvent;
import com.liferay.portlet.usernotifications.service.base.UserNotificationEventLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The implementation of the user notification event local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.usernotifications.service.UserNotificationEventLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.usernotifications.service.base.UserNotificationEventLocalServiceBaseImpl
 * @see com.liferay.portlet.usernotifications.service.UserNotificationEventLocalServiceUtil
 */
public class UserNotificationEventLocalServiceImpl
	extends UserNotificationEventLocalServiceBaseImpl {

    public UserNotificationEvent addUserNotificationEvent(
			long companyId, long userId, String type,
            long timestamp, long deliverBy, String data,
			ServiceContext serviceContext)
        throws SystemException, PortalException {

        UserNotificationEvent userNotificationEvent =
			createUserNotificationEvent(counterLocalService.increment());

        userNotificationEvent.setUuid(serviceContext.getUuid());
        userNotificationEvent.setCompanyId(companyId);
        userNotificationEvent.setUserId(userId);
        userNotificationEvent.setType(type);
        userNotificationEvent.setTimestamp(timestamp);
        userNotificationEvent.setDeliverBy(deliverBy);
        userNotificationEvent.setPayload(data);

        return userNotificationEventPersistence.update(
			userNotificationEvent, false);
    }

	public UserNotificationEvent addUserNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws SystemException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(notificationEvent.getUuid());

		return addUserNotificationEvent(
			companyId, userId, notificationEvent.getType(),
			notificationEvent.getTimestamp(), notificationEvent.getDeliverBy(),
			notificationEvent.getPayload().toString(), serviceContext);
	}

	public Collection<UserNotificationEvent> addUserNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws SystemException, PortalException {

		Collection<UserNotificationEvent> userNotificationEvents =
			new ArrayList<UserNotificationEvent>(notificationEvents.size());

		for (NotificationEvent notificationEvent : notificationEvents) {
			UserNotificationEvent userNotificationEvent =
				addUserNotificationEvent(companyId, userId, notificationEvent);

			userNotificationEvents.add(userNotificationEvent);
		}

		return userNotificationEvents;
	}

    public void deleteUserNotificationEvent(String uuid)
        throws SystemException, PortalException {

		userNotificationEventPersistence.removeByUuid(uuid);
    }

	public void deleteUserNotificationEvents(Collection<String> uuids)
		throws SystemException, PortalException {

		for (String uuid : uuids) {
			deleteUserNotificationEvent(uuid);
		}
	}

    public Collection<UserNotificationEvent> getUserNotificationEvents(
			long companyId, long userId)
		throws SystemException {

        return userNotificationEventPersistence.findByCompanyIdUserId(
			companyId, userId);
    }
}