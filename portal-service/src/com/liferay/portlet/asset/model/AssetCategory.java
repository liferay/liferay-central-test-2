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

package com.liferay.portlet.asset.model;


/**
 * <a href="AssetCategory.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetCategory table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.asset.model.impl.AssetCategoryImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryModel
 * @see       com.liferay.portlet.asset.model.impl.AssetCategoryImpl
 * @see       com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl
 * @generated
 */
public interface AssetCategory extends AssetCategoryModel {
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isRootCategory();
}