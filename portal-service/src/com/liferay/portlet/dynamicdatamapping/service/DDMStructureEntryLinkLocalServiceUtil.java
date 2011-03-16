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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m structure entry link local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLinkLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryLinkLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkLocalServiceImpl
 * @generated
 */
public class DDMStructureEntryLinkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m structure entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to add
	* @return the d d m structure entry link that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMStructureEntryLink(ddmStructureEntryLink);
	}

	/**
	* Creates a new d d m structure entry link with the primary key. Does not add the d d m structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new d d m structure entry link
	* @return the new d d m structure entry link
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink createDDMStructureEntryLink(
		long structureEntryLinkId) {
		return getService().createDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Deletes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to delete
	* @throws PortalException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Deletes the d d m structure entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMStructureEntryLink(ddmStructureEntryLink);
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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
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
	* Gets the d d m structure entry link with the primary key.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to get
	* @return the d d m structure entry link
	* @throws PortalException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getDDMStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Gets a range of all the d d m structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> getDDMStructureEntryLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntryLinks(start, end);
	}

	/**
	* Gets the number of d d m structure entry links.
	*
	* @return the number of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMStructureEntryLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntryLinksCount();
	}

	/**
	* Updates the d d m structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to update
	* @return the d d m structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMStructureEntryLink(ddmStructureEntryLink);
	}

	/**
	* Updates the d d m structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to update
	* @param merge whether to merge the d d m structure entry link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateDDMStructureEntryLink(ddmStructureEntryLink, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addStructureEntryLink(
		java.lang.String structureKey, java.lang.String className,
		long classPK, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructureEntryLink(structureKey, className, classPK,
			serviceContext);
	}

	public static void deleteStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink structureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntryLink(structureEntryLink);
	}

	public static void deleteStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntryLink(structureEntryLinkId);
	}

	public static void deleteStructureEntryLink(java.lang.String structureKey,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntryLink(structureKey, className, classPK);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntryLink(structureEntryLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		java.lang.String structureKey, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructureEntryLink(structureKey, className, classPK);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> getStructureEntryLinks(
		java.lang.String structureKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntryLinks(structureKey, start, end);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateStructureEntryLink(
		long structureEntryLinkId, java.lang.String structureKey, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructureEntryLink(structureEntryLinkId,
			structureKey, groupId, className, classPK);
	}

	public static DDMStructureEntryLinkLocalService getService() {
		if (_service == null) {
			_service = (DDMStructureEntryLinkLocalService)PortalBeanLocatorUtil.locate(DDMStructureEntryLinkLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureEntryLinkLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureEntryLinkLocalService.class);
		}

		return _service;
	}

	public void setService(DDMStructureEntryLinkLocalService service) {
		MethodCache.remove(DDMStructureEntryLinkLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureEntryLinkLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureEntryLinkLocalService.class);
	}

	private static DDMStructureEntryLinkLocalService _service;
}