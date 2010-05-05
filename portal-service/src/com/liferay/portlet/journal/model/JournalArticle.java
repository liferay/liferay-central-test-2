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
 * <a href="JournalArticle.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalArticle table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.journal.model.impl.JournalArticleImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleModel
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleModelImpl
 * @generated
 */
public interface JournalArticle extends JournalArticleModel {
	public java.lang.String[] getAvailableLocales();

	public java.lang.String getContentByLocale(java.lang.String languageId);

	public java.lang.String getDefaultLocale();

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isApproved();

	public boolean isDraft();

	public boolean isExpired();

	public boolean isPending();

	public boolean isTemplateDriven();

	public void setSmallImageType(java.lang.String smallImageType);
}