/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;


/**
 * <a href="WebDAVProps.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the WebDAVProps table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.WebDAVPropsImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsModel
 * @see       com.liferay.portal.model.impl.WebDAVPropsImpl
 * @see       com.liferay.portal.model.impl.WebDAVPropsModelImpl
 * @generated
 */
public interface WebDAVProps extends WebDAVPropsModel {
	public java.lang.String getProps();

	public java.util.Set<com.liferay.portal.kernel.util.Tuple> getPropsSet()
		throws java.lang.Exception;

	public java.lang.String getText(java.lang.String name,
		java.lang.String prefix, java.lang.String uri)
		throws java.lang.Exception;

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception;

	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri, java.lang.String text) throws java.lang.Exception;

	public void removeProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception;

	public void store() throws java.lang.Exception;
}