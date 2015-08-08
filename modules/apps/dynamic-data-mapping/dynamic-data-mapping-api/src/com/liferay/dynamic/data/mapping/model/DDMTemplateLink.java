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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDMTemplateLink service. Represents a row in the &quot;DDMTemplateLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkModel
 * @see com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateLinkImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateLinkModelImpl
 * @generated
 */
@ProviderType
public interface DDMTemplateLink extends DDMTemplateLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate()
		throws com.liferay.portal.kernel.exception.PortalException;
}