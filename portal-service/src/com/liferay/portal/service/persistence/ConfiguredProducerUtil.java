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
 * <a href="ConfiguredProducerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfiguredProducerUtil {
	public static com.liferay.portal.model.ConfiguredProducer create(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK) {
		return getPersistence().create(configuredProducerPK);
	}

	public static com.liferay.portal.model.ConfiguredProducer remove(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(configuredProducerPK);
	}

	public static com.liferay.portal.model.ConfiguredProducer remove(
		com.liferay.portal.model.ConfiguredProducer configuredProducer)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(configuredProducer);
	}

	/**
	 * @deprecated Use <code>update(ConfiguredProducer configuredProducer, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.ConfiguredProducer update(
		com.liferay.portal.model.ConfiguredProducer configuredProducer)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(configuredProducer);
	}

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
	public static com.liferay.portal.model.ConfiguredProducer update(
		com.liferay.portal.model.ConfiguredProducer configuredProducer,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(configuredProducer, merge);
	}

	public static com.liferay.portal.model.ConfiguredProducer updateImpl(
		com.liferay.portal.model.ConfiguredProducer configuredProducer,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(configuredProducer, merge);
	}

	public static com.liferay.portal.model.ConfiguredProducer findByPrimaryKey(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(configuredProducerPK);
	}

	public static com.liferay.portal.model.ConfiguredProducer fetchByPrimaryKey(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(configuredProducerPK);
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(portalId, namespace);
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(portalId, namespace, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(portalId, namespace, start, end, obc);
	}

	public static com.liferay.portal.model.ConfiguredProducer findByP_N_First(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByP_N_First(portalId, namespace, obc);
	}

	public static com.liferay.portal.model.ConfiguredProducer findByP_N_Last(
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByP_N_Last(portalId, namespace, obc);
	}

	public static com.liferay.portal.model.ConfiguredProducer[] findByP_N_PrevAndNext(
		com.liferay.portal.service.persistence.ConfiguredProducerPK configuredProducerPK,
		java.lang.String portalId, java.lang.String namespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchConfiguredProducerException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_N_PrevAndNext(configuredProducerPK, portalId,
			namespace, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ConfiguredProducer> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByP_N(java.lang.String portalId,
		java.lang.String namespace) throws com.liferay.portal.SystemException {
		getPersistence().removeByP_N(portalId, namespace);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByP_N(java.lang.String portalId,
		java.lang.String namespace) throws com.liferay.portal.SystemException {
		return getPersistence().countByP_N(portalId, namespace);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static ConfiguredProducerPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ConfiguredProducerPersistence persistence) {
		_persistence = persistence;
	}

	private static ConfiguredProducerPersistence _persistence;
}