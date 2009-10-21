/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.model.BaseModel;

import java.lang.reflect.Field;


/**
 * <a href="BasePersistenceManagerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 */
public class BasePersistenceManagerImpl implements BasePersistenceManager {

	public <T extends BaseModel<T>> T findByIdentity(ModelIdentity modelIdentity)
		throws NoSuchModelException, SystemException {

		BasePersistence basePersistence = getBasePersistence(modelIdentity);

		return (T) basePersistence.findByPrimaryKey(modelIdentity.getPrimaryKey());
	}
	
	public <T extends BaseModel<T>> ModelIdentity getIdentity(T entity) {
		String baseClassPath =
			extractBaseClassPath(entity.getClass().getName());
		
		String contextName =
			getContextName(baseClassPath, entity.getClass().getClassLoader());
		
		ModelIdentityImpl modelIdentity = new ModelIdentityImpl();

		modelIdentity.setContextName(contextName);
		modelIdentity.setModelClassName(entity.getClass().getName());
		modelIdentity.setPrimaryKey(entity.getPrimaryKeyObj());

		return modelIdentity;
	}

	public <T extends BaseModel<T>> T update(T entity, boolean merge)
		throws SystemException {

		BasePersistence basePersistence = getBasePersistence(entity.getClass());
		
		return (T) basePersistence.update(entity, merge);
	}
	
	protected String extractBaseClassPath(String classPath) {
		int index = classPath.indexOf(".model.impl.");
		return classPath.substring(0, index);
	}

	protected String extractEntityName(String classPath) {
		int index = classPath.indexOf(".model.impl.");
		return classPath.substring(index + 12, classPath.length() - 9);
	}
	
	protected BasePersistence getBasePersistence(Class<?> entityClass) {

		String modelClassName = entityClass.getName();
		String baseClassPath = extractBaseClassPath(modelClassName);
		String entityName = extractEntityName(modelClassName);

		String contextName =
			getContextName(baseClassPath, entityClass.getClassLoader());
		
		StringBuilder persistenceBeanIdBuilder = new StringBuilder();
		persistenceBeanIdBuilder.append(baseClassPath);
		persistenceBeanIdBuilder.append(".service.persistence.");
		persistenceBeanIdBuilder.append(entityName);
		persistenceBeanIdBuilder.append("Persistence.impl");

		String persistenceBeanId = persistenceBeanIdBuilder.toString();
		
		if (contextName == null) {
			return (BasePersistence) PortalBeanLocatorUtil.locate(persistenceBeanId);
		}
		else {
			return (BasePersistence) PortletBeanLocatorUtil.locate(
				contextName, persistenceBeanId);
		}
	}

	protected BasePersistence getBasePersistence(ModelIdentity modelIdentity) {

		String modelClassName = modelIdentity.getModelClassName();
		String baseClassPath = extractBaseClassPath(modelClassName);
		String entityName = extractEntityName(modelClassName);
		String contextName = modelIdentity.getContextName();

		StringBuilder persistenceBeanIdBuilder = new StringBuilder();
		persistenceBeanIdBuilder.append(baseClassPath);
		persistenceBeanIdBuilder.append(".service.persistence.");
		persistenceBeanIdBuilder.append(entityName);
		persistenceBeanIdBuilder.append("Persistence.impl");

		String persistenceBeanId = persistenceBeanIdBuilder.toString();

		if (contextName == null) {
			return (BasePersistence) PortalBeanLocatorUtil.locate(persistenceBeanId);
		}
		else {
			return (BasePersistence) PortletBeanLocatorUtil.locate(
				contextName, persistenceBeanId);
		}
	}
	
	protected String getContextName(
		String baseClassPath, ClassLoader modelClassLoader) {

		StringBuilder className = new StringBuilder();
		className.append(baseClassPath);
		className.append(".service.ClpSerializer");

		try {
			Class<?> clpSerializerClass =
				modelClassLoader.loadClass(className.toString());
			
			Field contextNameField =
				clpSerializerClass.getField("SERVLET_CONTEXT_NAME");
			
			return (String) contextNameField.get(null);
		}
		catch (ClassNotFoundException e) {
			return null;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
				"Cannot retrieve the servlet context name from the ClpSerializer");
		}
	}
	
}
