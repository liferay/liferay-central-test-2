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

package com.liferay.portlet.journal.model;


/**
 * <a href="JournalTemplate.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalTemplate table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.journal.model.impl.JournalTemplateImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplateModel
 * @see       com.liferay.portlet.journal.model.impl.JournalTemplateImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl
 * @generated
 */
public interface JournalTemplate extends JournalTemplateModel {
	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void setSmallImageType(java.lang.String smallImageType);
}