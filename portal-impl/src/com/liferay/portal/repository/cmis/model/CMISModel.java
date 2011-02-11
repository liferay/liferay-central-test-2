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
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.data.CmisExtensionElement;
import org.apache.chemistry.opencmis.commons.enums.Action;
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
				if (!extension.getName().equals("properties")) {
					continue;
				}

				for (CmisExtensionElement property : extension.getChildren()) {
					Map<String, String> attributes = property.getAttributes();

					String propertyDefinitionId = attributes.get(
						"propertyDefinitionId");

					if (!propertyDefinitionId.equals("cm:description")) {
						continue;
					}

					for (CmisExtensionElement propertyValues :
							property.getChildren()) {

						return propertyValues.getValue();
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	protected boolean containsPermission(CmisObject cmisObject, String actionId)
		throws RepositoryException {

		if (_unsupportedActionKeys.contains(actionId)) {
			return false;
		}

		Action action = _mappedActionKeys.get(actionId);

		if (action == null) {
			throw new RepositoryException(
				"Unexpected permission action " + actionId);
		}

		Set<Action> allowableActions =
			cmisObject.getAllowableActions().getAllowableActions();

		return allowableActions.contains(action);
	}

	private static Set<String> _unsupportedActionKeys = new HashSet<String>();

	private static Map<String, Action> _mappedActionKeys =
		new HashMap<String, Action>();

	private static Log _log = LogFactoryUtil.getLog(CMISModel.class);

	static {
		_unsupportedActionKeys.add(ActionKeys.ADD_DISCUSSION);
		_unsupportedActionKeys.add(ActionKeys.ADD_SHORTCUT);
		_unsupportedActionKeys.add(ActionKeys.DELETE_DISCUSSION);
		_unsupportedActionKeys.add(ActionKeys.PERMISSIONS);
		_unsupportedActionKeys.add(ActionKeys.UPDATE_DISCUSSION);

		_mappedActionKeys.put(
			ActionKeys.ACCESS, Action.CAN_GET_FOLDER_TREE);
		_mappedActionKeys.put(
			ActionKeys.ADD_DOCUMENT, Action.CAN_CREATE_DOCUMENT);
		_mappedActionKeys.put(
			ActionKeys.ADD_FOLDER, Action.CAN_CREATE_FOLDER);
		_mappedActionKeys.put(
			ActionKeys.ADD_SUBFOLDER, Action.CAN_CREATE_FOLDER);
		_mappedActionKeys.put(
			ActionKeys.DELETE, Action.CAN_DELETE_OBJECT);
		_mappedActionKeys.put(
			ActionKeys.UPDATE, Action.CAN_UPDATE_PROPERTIES);
		_mappedActionKeys.put(
			ActionKeys.VIEW, Action.CAN_GET_PROPERTIES);
	}

}