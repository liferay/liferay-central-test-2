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

package com.liferay.portal.workflow.kaleo.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for KaleoTaskFormInstance. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstanceLocalServiceUtil
 * @see com.liferay.portal.workflow.kaleo.service.base.KaleoTaskFormInstanceLocalServiceBaseImpl
 * @see com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskFormInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface KaleoTaskFormInstanceLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoTaskFormInstanceLocalServiceUtil} to access the kaleo task form instance local service. Add custom service methods to {@link com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskFormInstanceLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Adds the kaleo task form instance to the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstance the kaleo task form instance
	* @return the kaleo task form instance that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public KaleoTaskFormInstance addKaleoTaskFormInstance(
		KaleoTaskFormInstance kaleoTaskFormInstance);

	public KaleoTaskFormInstance addKaleoTaskFormInstance(long groupId,
		long kaleoTaskFormId, java.lang.String formValues,
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new kaleo task form instance with the primary key. Does not add the kaleo task form instance to the database.
	*
	* @param kaleoTaskFormInstanceId the primary key for the new kaleo task form instance
	* @return the new kaleo task form instance
	*/
	public KaleoTaskFormInstance createKaleoTaskFormInstance(
		long kaleoTaskFormInstanceId);

	/**
	* Deletes the kaleo task form instance from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstance the kaleo task form instance
	* @return the kaleo task form instance that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public KaleoTaskFormInstance deleteKaleoTaskFormInstance(
		KaleoTaskFormInstance kaleoTaskFormInstance);

	/**
	* Deletes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance that was removed
	* @throws PortalException if a kaleo task form instance with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public KaleoTaskFormInstance deleteKaleoTaskFormInstance(
		long kaleoTaskFormInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoTaskFormInstance fetchKaleoTaskFormInstance(
		long kaleoTaskFormInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoTaskFormInstance fetchKaleoTaskFormKaleoTaskFormInstance(
		long kaleoTaskFormId);

	/**
	* Returns the kaleo task form instance with the primary key.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance
	* @throws PortalException if a kaleo task form instance with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoTaskFormInstance getKaleoTaskFormInstance(
		long kaleoTaskFormInstanceId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoTaskFormInstance getKaleoTaskFormKaleoTaskFormInstance(
		long kaleoTaskFormId) throws PortalException;

	/**
	* Updates the kaleo task form instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstance the kaleo task form instance
	* @return the kaleo task form instance that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public KaleoTaskFormInstance updateKaleoTaskFormInstance(
		KaleoTaskFormInstance kaleoTaskFormInstance);

	public int countKaleoTaskFormInstanceByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns the number of kaleo task form instances.
	*
	* @return the number of kaleo task form instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKaleoTaskFormInstancesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of kaleo task form instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoTaskFormInstance> getKaleoTaskFormInstances(int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoTaskFormInstance> getKaleoTaskKaleoTaskFormInstances(
		long kaleoTaskId);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void deleteCompanyKaleoTaskFormInstances(long companyId);

	public void deleteKaleoDefinitionKaleoTaskFormInstances(
		long kaleoDefinitionId);

	public void deleteKaleoInstanceKaleoTaskFormInstances(long kaleoInstanceId);
}