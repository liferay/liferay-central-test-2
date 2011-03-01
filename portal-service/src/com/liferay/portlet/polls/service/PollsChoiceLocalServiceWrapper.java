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

package com.liferay.portlet.polls.service;

/**
 * <p>
 * This class is a wrapper for {@link PollsChoiceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoiceLocalService
 * @generated
 */
public class PollsChoiceLocalServiceWrapper implements PollsChoiceLocalService {
	public PollsChoiceLocalServiceWrapper(
		PollsChoiceLocalService pollsChoiceLocalService) {
		_pollsChoiceLocalService = pollsChoiceLocalService;
	}

	/**
	* Adds the polls choice to the database. Also notifies the appropriate model listeners.
	*
	* @param pollsChoice the polls choice to add
	* @return the polls choice that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.polls.model.PollsChoice addPollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.addPollsChoice(pollsChoice);
	}

	/**
	* Creates a new polls choice with the primary key. Does not add the polls choice to the database.
	*
	* @param choiceId the primary key for the new polls choice
	* @return the new polls choice
	*/
	public com.liferay.portlet.polls.model.PollsChoice createPollsChoice(
		long choiceId) {
		return _pollsChoiceLocalService.createPollsChoice(choiceId);
	}

	/**
	* Deletes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param choiceId the primary key of the polls choice to delete
	* @throws PortalException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deletePollsChoice(long choiceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_pollsChoiceLocalService.deletePollsChoice(choiceId);
	}

	/**
	* Deletes the polls choice from the database. Also notifies the appropriate model listeners.
	*
	* @param pollsChoice the polls choice to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deletePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.kernel.exception.SystemException {
		_pollsChoiceLocalService.deletePollsChoice(pollsChoice);
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
		return _pollsChoiceLocalService.dynamicQuery(dynamicQuery);
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
		return _pollsChoiceLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _pollsChoiceLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _pollsChoiceLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the polls choice with the primary key.
	*
	* @param choiceId the primary key of the polls choice to get
	* @return the polls choice
	* @throws PortalException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.polls.model.PollsChoice getPollsChoice(
		long choiceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getPollsChoice(choiceId);
	}

	/**
	* Gets a range of all the polls choices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of polls choices to return
	* @param end the upper bound of the range of polls choices to return (not inclusive)
	* @return the range of polls choices
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getPollsChoices(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getPollsChoices(start, end);
	}

	/**
	* Gets the number of polls choices.
	*
	* @return the number of polls choices
	* @throws SystemException if a system exception occurred
	*/
	public int getPollsChoicesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getPollsChoicesCount();
	}

	/**
	* Updates the polls choice in the database. Also notifies the appropriate model listeners.
	*
	* @param pollsChoice the polls choice to update
	* @return the polls choice that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.updatePollsChoice(pollsChoice);
	}

	/**
	* Updates the polls choice in the database. Also notifies the appropriate model listeners.
	*
	* @param pollsChoice the polls choice to update
	* @param merge whether to merge the polls choice with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the polls choice that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.updatePollsChoice(pollsChoice, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _pollsChoiceLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_pollsChoiceLocalService.setIdentifier(identifier);
	}

	public com.liferay.portlet.polls.model.PollsChoice addChoice(
		long questionId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.addChoice(questionId, name,
			description, serviceContext);
	}

	public com.liferay.portlet.polls.model.PollsChoice getChoice(long choiceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getChoice(choiceId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getChoices(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getChoices(questionId);
	}

	public int getChoicesCount(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.getChoicesCount(questionId);
	}

	public com.liferay.portlet.polls.model.PollsChoice updateChoice(
		long choiceId, long questionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsChoiceLocalService.updateChoice(choiceId, questionId,
			name, description);
	}

	public PollsChoiceLocalService getWrappedPollsChoiceLocalService() {
		return _pollsChoiceLocalService;
	}

	public void setWrappedPollsChoiceLocalService(
		PollsChoiceLocalService pollsChoiceLocalService) {
		_pollsChoiceLocalService = pollsChoiceLocalService;
	}

	private PollsChoiceLocalService _pollsChoiceLocalService;
}