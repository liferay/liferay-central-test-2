/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.data.CmisExtensionElement;
import org.apache.chemistry.opencmis.commons.enums.ExtensionLevel;

/**
 * @author Alexander Chow
 */
public abstract class CMISModel {

	public String getDescription(CmisObject cmisObject) {
		try {
			List<CmisExtensionElement> extensions = cmisObject.getExtensions(
				ExtensionLevel.PROPERTIES).get(0).getChildren();

			for (CmisExtensionElement extension : extensions) {
				if (extension.getName().equals("properties")) {
					for (CmisExtensionElement property :
							extension.getChildren()) {

						String id = property.getAttributes().get(
							"propertyDefinitionId");

						if (id.equals("cm:description")) {
							for (CmisExtensionElement propertyValues :
									property.getChildren()) {

								return propertyValues.getValue();
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISModel.class);

}