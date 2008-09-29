/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="ConfiguredProducerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ConfiguredProducerPersistence {
	public com.liferay.portal.model.ConfiguredProducer create(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK);

	public com.liferay.portal.model.ConfiguredProducer remove(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer remove(
		com.liferay.portal.model.ConfiguredProducer configuredProducer)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ConfiguredProducer configuredProducer, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ConfiguredProducer update(
		com.liferay.portal.model.ConfiguredProducer configuredProducer)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        configuredProducer the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when configuredProducer is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ConfiguredProducer update(
		com.liferay.portal.model.ConfiguredProducer configuredProducer,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer updateImpl(
		com.liferay.portal.model.ConfiguredProducer configuredProducer,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer findByPrimaryKey(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer fetchByPrimaryKey(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer findByP_N_First(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer findByP_N_Last(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ConfiguredProducer[] findByP_N_PrevAndNext(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK,
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByP_N(java.lang.String portalId,
		java.lang.String namespace) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByP_N(java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}