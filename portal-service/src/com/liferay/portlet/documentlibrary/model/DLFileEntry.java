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

package com.liferay.portlet.documentlibrary.model;


/**
 * <a href="DLFileEntry.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the DLFileEntry table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryModel
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl
 * @generated
 */
public interface DLFileEntry extends DLFileEntryModel {
	public java.lang.String getExtraSettings();

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties();

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder();

	public com.liferay.portal.model.Lock getLock();

	public java.lang.String getLuceneProperties();

	public long getRepositoryId();

	public boolean hasLock(long userId);

	public boolean isLocked();

	public void setExtraSettings(java.lang.String extraSettings);

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties);
}