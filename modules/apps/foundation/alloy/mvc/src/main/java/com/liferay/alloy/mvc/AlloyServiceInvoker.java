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

package com.liferay.alloy.mvc;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.TextFormatter;

import java.lang.reflect.Method;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AlloyServiceInvoker {

	public AlloyServiceInvoker(String className) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		int pos = className.indexOf(".model.");

		String simpleClassName = className.substring(pos + 7);

		String serviceClassName =
			className.substring(0, pos) + ".service." + simpleClassName +
				"LocalServiceUtil";

		try {
			Class<?> serviceClass = classLoader.loadClass(serviceClassName);
			Class<?> modelClass = classLoader.loadClass(className);

			addModelMethod = serviceClass.getMethod(
				"add" + simpleClassName, new Class[] {modelClass});

			createModelMethod = serviceClass.getMethod(
				"create" + simpleClassName, new Class[] {long.class});
			deleteModelMethod = serviceClass.getMethod(
				"delete" + simpleClassName, new Class[] {long.class});
			dynamicQueryCountMethod1 = serviceClass.getMethod(
				"dynamicQueryCount", new Class[] {DynamicQuery.class});
			dynamicQueryCountMethod2 = serviceClass.getMethod(
				"dynamicQueryCount",
				new Class[] {DynamicQuery.class, Projection.class});
			dynamicQueryMethod1 = serviceClass.getMethod(
				"dynamicQuery", new Class[0]);
			dynamicQueryMethod2 = serviceClass.getMethod(
				"dynamicQuery", new Class[] {DynamicQuery.class});
			dynamicQueryMethod3 = serviceClass.getMethod(
				"dynamicQuery",
				new Class[] {DynamicQuery.class, int.class, int.class});
			dynamicQueryMethod4 = serviceClass.getMethod(
				"dynamicQuery",
				new Class[] {
					DynamicQuery.class, int.class, int.class,
					OrderByComparator.class
				});
			fetchModelMethod = serviceClass.getMethod(
				"fetch" + simpleClassName, new Class[] {long.class});
			getModelMethod = serviceClass.getMethod(
				"get" + simpleClassName, new Class[] {long.class});
			getModelsCountMethod = serviceClass.getMethod(
				"get" + TextFormatter.formatPlural(simpleClassName) + "Count",
				new Class[0]);
			getModelsMethod = serviceClass.getMethod(
				"get" + TextFormatter.formatPlural(simpleClassName),
				new Class[] {int.class, int.class});
			updateModelMethod = serviceClass.getMethod(
				"update" + simpleClassName, new Class[] {modelClass});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public BaseModel addModel(BaseModel baseModel) throws Exception {
		return (BaseModel<?>)addModelMethod.invoke(false, baseModel);
	}

	public DynamicQuery buildDynamicQuery() throws Exception {
		return (DynamicQuery)dynamicQueryMethod1.invoke(false);
	}

	public DynamicQuery buildDynamicQuery(Object[] properties)
		throws Exception {

		if ((properties.length == 0) || ((properties.length % 2) != 0)) {
			throw new IllegalArgumentException(
				"Properties length is not an even number");
		}

		DynamicQuery dynamicQuery = buildDynamicQuery();

		for (int i = 0; i < properties.length; i += 2) {
			String propertyName = String.valueOf(properties[i]);

			Property property = PropertyFactoryUtil.forName(propertyName);

			Object propertyValue = properties[i + 1];

			dynamicQuery.add(property.eq(propertyValue));
		}

		return dynamicQuery;
	}

	public BaseModel createModel(long id) throws Exception {
		return (BaseModel<?>)createModelMethod.invoke(false, id);
	}

	public BaseModel<?> deleteModel(BaseModel<?> baseModel) throws Exception {
		return (BaseModel<?>)deleteModelMethod.invoke(
			false, baseModel.getPrimaryKeyObj());
	}

	public BaseModel<?> deleteModel(long classPK) throws Exception {
		return (BaseModel<?>)deleteModelMethod.invoke(false, classPK);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #executeDynamicQuery(DynamicQuery)}
	 */
	@Deprecated
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery) throws Exception {
		return executeDynamicQuery(dynamicQuery);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #executeDynamicQueryCount(DynamicQuery)}
	 */
	@Deprecated
	public long dynamicQueryCount(DynamicQuery dynamicQuery) throws Exception {
		return executeDynamicQueryCount(dynamicQuery);
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(DynamicQuery dynamicQuery)
		throws Exception {

		return (List)dynamicQueryMethod2.invoke(false, dynamicQuery);
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws Exception {

		return (List)dynamicQueryMethod3.invoke(
			false, dynamicQuery, start, end);
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<?> obc)
		throws Exception {

		return (List)dynamicQueryMethod4.invoke(
			false, dynamicQuery, start, end, obc);
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(Object[] properties) throws Exception {
		return executeDynamicQuery(buildDynamicQuery(properties));
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(Object[] properties, int start, int end)
		throws Exception {

		return executeDynamicQuery(buildDynamicQuery(properties), start, end);
	}

	@SuppressWarnings("rawtypes")
	public List executeDynamicQuery(
			Object[] properties, int start, int end, OrderByComparator<?> obc)
		throws Exception {

		return executeDynamicQuery(
			buildDynamicQuery(properties), start, end, obc);
	}

	public long executeDynamicQueryCount(DynamicQuery dynamicQuery)
		throws Exception {

		return (Long)dynamicQueryCountMethod1.invoke(false, dynamicQuery);
	}

	public long executeDynamicQueryCount(
			DynamicQuery dynamicQuery, Projection projection)
		throws Exception {

		return (Long)dynamicQueryCountMethod2.invoke(
			false, dynamicQuery, projection);
	}

	public long executeDynamicQueryCount(Object[] properties) throws Exception {
		return executeDynamicQueryCount(buildDynamicQuery(properties));
	}

	public BaseModel<?> fetchModel(long classPK) throws Exception {
		return (BaseModel<?>)fetchModelMethod.invoke(false, classPK);
	}

	public BaseModel<?> getModel(long classPK) throws Exception {
		return (BaseModel<?>)getModelMethod.invoke(false, classPK);
	}

	@SuppressWarnings("rawtypes")
	public List getModels(int start, int end) throws Exception {
		return (List)getModelsMethod.invoke(false, start, end);
	}

	public int getModelsCount() throws Exception {
		return (Integer)getModelsCountMethod.invoke(false);
	}

	public BaseModel updateModel(BaseModel baseModel) throws Exception {
		return (BaseModel<?>)updateModelMethod.invoke(false, baseModel);
	}

	protected Method addModelMethod;
	protected Method createModelMethod;
	protected Method deleteModelMethod;
	protected Method dynamicQueryCountMethod1;
	protected Method dynamicQueryCountMethod2;
	protected Method dynamicQueryMethod1;
	protected Method dynamicQueryMethod2;
	protected Method dynamicQueryMethod3;
	protected Method dynamicQueryMethod4;
	protected Method fetchModelMethod;
	protected Method getModelMethod;
	protected Method getModelsCountMethod;
	protected Method getModelsMethod;
	protected Method updateModelMethod;

}