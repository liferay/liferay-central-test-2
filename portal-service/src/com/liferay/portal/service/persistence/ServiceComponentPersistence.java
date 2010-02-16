/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ServiceComponent;

/**
 * <a href="ServiceComponentPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentPersistenceImpl
 * @see       ServiceComponentUtil
 * @generated
 */
public interface ServiceComponentPersistence extends BasePersistence<ServiceComponent> {
	public void cacheResult(
		com.liferay.portal.model.ServiceComponent serviceComponent);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ServiceComponent> serviceComponents);

	public com.liferay.portal.model.ServiceComponent create(
		long serviceComponentId);

	public com.liferay.portal.model.ServiceComponent remove(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByBNS_BNU(java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}