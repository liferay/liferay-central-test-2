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

package com.liferay.portlet.wiki.model;


/**
 * <a href="WikiPage.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the WikiPage table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.wiki.model.impl.WikiPageImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageModel
 * @see       com.liferay.portlet.wiki.model.impl.WikiPageImpl
 * @see       com.liferay.portlet.wiki.model.impl.WikiPageModelImpl
 * @generated
 */
public interface WikiPage extends WikiPageModel {
	public java.lang.String getAttachmentsDir();

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages();

	public com.liferay.portlet.wiki.model.WikiNode getNode();

	public com.liferay.portlet.wiki.model.WikiPage getParentPage();

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages();

	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage();

	public boolean isApproved();

	public void setAttachmentsDir(java.lang.String attachmentsDir);
}