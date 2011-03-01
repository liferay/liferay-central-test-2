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

package com.liferay.portlet.calendar.service;

/**
 * <p>
 * This class is a wrapper for {@link CalEventLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventLocalService
 * @generated
 */
public class CalEventLocalServiceWrapper implements CalEventLocalService {
	public CalEventLocalServiceWrapper(
		CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	/**
	* Adds the cal event to the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event to add
	* @return the cal event that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.addCalEvent(calEvent);
	}

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId) {
		return _calEventLocalService.createCalEvent(eventId);
	}

	/**
	* Deletes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event to delete
	* @throws PortalException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCalEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteCalEvent(eventId);
	}

	/**
	* Deletes the cal event from the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteCalEvent(calEvent);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the cal event with the primary key.
	*
	* @param eventId the primary key of the cal event to get
	* @return the cal event
	* @throws PortalException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent getCalEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEvent(eventId);
	}

	/**
	* Gets the cal event with the UUID and group id.
	*
	* @param uuid the UUID of cal event to get
	* @param groupId the group id of the cal event to get
	* @return the cal event
	* @throws PortalException if a cal event with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent getCalEventByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEventByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cal events to return
	* @param end the upper bound of the range of cal events to return (not inclusive)
	* @return the range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEvents(start, end);
	}

	/**
	* Gets the number of cal events.
	*
	* @return the number of cal events
	* @throws SystemException if a system exception occurred
	*/
	public int getCalEventsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCalEventsCount();
	}

	/**
	* Updates the cal event in the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event to update
	* @return the cal event that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateCalEvent(calEvent);
	}

	/**
	* Updates the cal event in the database. Also notifies the appropriate model listeners.
	*
	* @param calEvent the cal event to update
	* @param merge whether to merge the cal event with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the cal event that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateCalEvent(calEvent, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _calEventLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_calEventLocalService.setIdentifier(identifier);
	}

	public com.liferay.portlet.calendar.model.CalEvent addEvent(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String location, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.addEvent(userId, title, description,
			location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(event, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(event, communityPermissions,
			guestPermissions);
	}

	public void addEventResources(long eventId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(eventId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(long eventId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.addEventResources(eventId, communityPermissions,
			guestPermissions);
	}

	public void checkEvents()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.checkEvents();
	}

	public void deleteEvent(com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteEvent(event);
	}

	public void deleteEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteEvent(eventId);
	}

	public void deleteEvents(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.deleteEvents(groupId);
	}

	public java.io.File exportEvent(long userId, long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.exportEvent(userId, eventId);
	}

	public java.io.File exportGroupEvents(long userId, long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.exportGroupEvents(userId, groupId, fileName);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCompanyEvents(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCompanyEvents(companyId, start, end);
	}

	public int getCompanyEventsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getCompanyEventsCount(companyId);
	}

	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvent(eventId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal, type);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, cal, types);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, type, start, end);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String[] types, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEvents(groupId, types, start, end);
	}

	public int getEventsCount(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEventsCount(groupId, type);
	}

	public int getEventsCount(long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getEventsCount(groupId, types);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getNoAssetEvents()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getNoAssetEvents();
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getRepeatingEvents(groupId);
	}

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId, java.util.Calendar cal, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.getRepeatingEvents(groupId, cal, types);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal, type);
	}

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.hasEvents(groupId, cal, types);
	}

	public void importICal4j(long userId, long groupId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.importICal4j(userId, groupId, file);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.calendar.model.CalEvent event,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_calEventLocalService.updateAsset(userId, event, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long userId, long eventId, java.lang.String title,
		java.lang.String description, java.lang.String location,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calEventLocalService.updateEvent(userId, eventId, title,
			description, location, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public CalEventLocalService getWrappedCalEventLocalService() {
		return _calEventLocalService;
	}

	public void setWrappedCalEventLocalService(
		CalEventLocalService calEventLocalService) {
		_calEventLocalService = calEventLocalService;
	}

	private CalEventLocalService _calEventLocalService;
}