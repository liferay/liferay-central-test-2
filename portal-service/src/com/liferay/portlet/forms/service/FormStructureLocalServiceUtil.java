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

package com.liferay.portlet.forms.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the form structure local service. This utility wraps {@link com.liferay.portlet.forms.service.impl.FormStructureLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructureLocalService
 * @see com.liferay.portlet.forms.service.base.FormStructureLocalServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormStructureLocalServiceImpl
 * @generated
 */
public class FormStructureLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormStructureLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the form structure to the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to add
	* @return the form structure that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructure addFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addFormStructure(formStructure);
	}

	/**
	* Creates a new form structure with the primary key. Does not add the form structure to the database.
	*
	* @param id the primary key for the new form structure
	* @return the new form structure
	*/
	public static com.liferay.portlet.forms.model.FormStructure createFormStructure(
		long id) {
		return getService().createFormStructure(id);
	}

	/**
	* Deletes the form structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the form structure to delete
	* @throws PortalException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFormStructure(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFormStructure(id);
	}

	/**
	* Deletes the form structure from the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFormStructure(formStructure);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the form structure with the primary key.
	*
	* @param id the primary key of the form structure to get
	* @return the form structure
	* @throws PortalException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructure(id);
	}

	/**
	* Gets the form structure with the UUID and group id.
	*
	* @param uuid the UUID of form structure to get
	* @param groupId the group id of the form structure to get
	* @return the form structure
	* @throws PortalException if a form structure with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructure getFormStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructureByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the form structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of form structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructures(start, end);
	}

	/**
	* Gets the number of form structures.
	*
	* @return the number of form structures
	* @throws SystemException if a system exception occurred
	*/
	public static int getFormStructuresCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructuresCount();
	}

	/**
	* Updates the form structure in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to update
	* @return the form structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFormStructure(formStructure);
	}

	/**
	* Updates the form structure in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to update
	* @param merge whether to merge the form structure with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the form structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFormStructure(formStructure, merge);
	}

	public static com.liferay.portlet.forms.model.FormStructure addFormStructure(
		long userId, long groupId, java.lang.String formStructureId,
		boolean autoFormStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFormStructure(userId, groupId, formStructureId,
			autoFormStructureId, name, description, xsd, serviceContext);
	}

	public static void addFormStructureResources(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFormStructureResources(formStructure, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFormStructureResources(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFormStructureResources(formStructure, communityPermissions,
			guestPermissions);
	}

	public static void deleteFormStructure(long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFormStructure(groupId, formStructureId);
	}

	public static void deleteFormStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFormStructures(groupId);
	}

	public static com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByG_F(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructure(groupId, formStructureId);
	}

	public static java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructures();
	}

	public static java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructures(groupId);
	}

	public static java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructures(groupId, start, end);
	}

	public static int getFormStructuresCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructuresCount(groupId);
	}

	public static com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFormStructure(groupId, formStructureId, name,
			description, xsd, serviceContext);
	}

	public static FormStructureLocalService getService() {
		if (_service == null) {
			_service = (FormStructureLocalService)PortalBeanLocatorUtil.locate(FormStructureLocalService.class.getName());

			ReferenceRegistry.registerReference(FormStructureLocalServiceUtil.class,
				"_service");
			MethodCache.remove(FormStructureLocalService.class);
		}

		return _service;
	}

	public void setService(FormStructureLocalService service) {
		MethodCache.remove(FormStructureLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(FormStructureLocalServiceUtil.class,
			"_service");
		MethodCache.remove(FormStructureLocalService.class);
	}

	private static FormStructureLocalService _service;
}