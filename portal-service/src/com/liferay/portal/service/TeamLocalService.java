/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for Team. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalServiceUtil
 * @see com.liferay.portal.service.base.TeamLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.TeamLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TeamLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamLocalServiceUtil} to access the team local service. Add custom service methods to {@link com.liferay.portal.service.impl.TeamLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the team to the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.Team addTeam(
		com.liferay.portal.model.Team team);

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link #addTeam(long, long, String,
	String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.Team addTeam(long userId, long groupId,
		java.lang.String name, java.lang.String description)
		throws PortalException;

	public com.liferay.portal.model.Team addTeam(long userId, long groupId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void addUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team);

	public void addUserGroupTeam(long userGroupId, long teamId);

	public void addUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams);

	public void addUserGroupTeams(long userGroupId, long[] teamIds);

	public void addUserTeam(long userId, com.liferay.portal.model.Team team);

	public void addUserTeam(long userId, long teamId);

	public void addUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams);

	public void addUserTeams(long userId, long[] teamIds);

	public void clearUserGroupTeams(long userGroupId);

	public void clearUserTeams(long userId);

	/**
	* Creates a new team with the primary key. Does not add the team to the database.
	*
	* @param teamId the primary key for the new team
	* @return the new team
	*/
	public com.liferay.portal.model.Team createTeam(long teamId);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the team from the database. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was removed
	* @throws PortalException
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.model.Team deleteTeam(
		com.liferay.portal.model.Team team) throws PortalException;

	/**
	* Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param teamId the primary key of the team
	* @return the team that was removed
	* @throws PortalException if a team with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.model.Team deleteTeam(long teamId)
		throws PortalException;

	public void deleteTeams(long groupId) throws PortalException;

	public void deleteUserGroupTeam(long userGroupId,
		com.liferay.portal.model.Team team);

	public void deleteUserGroupTeam(long userGroupId, long teamId);

	public void deleteUserGroupTeams(long userGroupId,
		java.util.List<com.liferay.portal.model.Team> Teams);

	public void deleteUserGroupTeams(long userGroupId, long[] teamIds);

	public void deleteUserTeam(long userId, com.liferay.portal.model.Team team);

	public void deleteUserTeam(long userId, long teamId);

	public void deleteUserTeams(long userId,
		java.util.List<com.liferay.portal.model.Team> Teams);

	public void deleteUserTeams(long userId, long[] teamIds);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team fetchTeam(long groupId,
		java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team fetchTeam(long teamId);

	/**
	* Returns the team matching the UUID and group.
	*
	* @param uuid the team's UUID
	* @param groupId the primary key of the group
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team fetchTeamByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team getTeam(long groupId,
		java.lang.String name) throws PortalException;

	/**
	* Returns the team with the primary key.
	*
	* @param teamId the primary key of the team
	* @return the team
	* @throws PortalException if a team with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team getTeam(long teamId)
		throws PortalException;

	/**
	* Returns the team matching the UUID and group.
	*
	* @param uuid the team's UUID
	* @param groupId the primary key of the group
	* @return the matching team
	* @throws PortalException if a matching team could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team getTeamByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of teams
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getTeams(int start,
		int end);

	/**
	* Returns all the teams matching the UUID and company.
	*
	* @param uuid the UUID of the teams
	* @param companyId the primary key of the company
	* @return the matching teams, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getTeamsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of teams matching the UUID and company.
	*
	* @param uuid the UUID of the teams
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching teams, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getTeamsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> orderByComparator);

	/**
	* Returns the number of teams.
	*
	* @return the number of teams
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTeamsCount();

	/**
	* Returns the userGroupIds of the user groups associated with the team.
	*
	* @param teamId the teamId of the team
	* @return long[] the userGroupIds of user groups associated with the team
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserGroupPrimaryKeys(long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserGroupTeams(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserGroupTeamsCount(long userGroupId);

	/**
	* Returns the userIds of the users associated with the team.
	*
	* @param teamId the teamId of the team
	* @return long[] the userIds of users associated with the team
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserPrimaryKeys(long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserTeamsCount(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupTeam(long userGroupId, long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupTeams(long userGroupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserTeam(long userId, long teamId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserTeams(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> search(long groupId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params);

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void setUserGroupTeams(long userGroupId, long[] teamIds);

	public void setUserTeams(long userId, long[] teamIds);

	/**
	* Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param team the team
	* @return the team that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team);

	public com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws PortalException;
}