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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletResourceBundles;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.social.model.SocialEquityActionMapping;
import com.liferay.util.UniqueList;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Daeyoung Song
 */
public class ResourceActionsUtil {

	public static final String ACTION_NAME_PREFIX = "action.";

	public static final String MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	public static final String[] ORGANIZATION_MODEL_RESOURCES = {
		Organization.class.getName(), PasswordPolicy.class.getName(),
		User.class.getName()
	};

	public static final String[] PORTAL_MODEL_RESOURCES = {
		ExpandoColumn.class.getName(), Organization.class.getName(),
		PasswordPolicy.class.getName(), Role.class.getName(),
		User.class.getName(), UserGroup.class.getName()
	};

	public static void checkAction(String name, String actionId)
		throws NoSuchResourceActionException {

		List<String> resourceActions = getResourceActions(name);

		if (!resourceActions.contains(actionId)) {
			throw new NoSuchResourceActionException(
				name.concat(StringPool.POUND).concat(actionId));
		}
	}

	public static String getAction(Locale locale, String action) {
		String key = ACTION_NAME_PREFIX + action;

		String value = LanguageUtil.get(locale, key, null);

		if ((value == null) || (value.equals(key))) {
			value = PortletResourceBundles.getString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	public static String getAction(PageContext pageContext, String action) {
		String key = ACTION_NAME_PREFIX + action;

		String value = LanguageUtil.get(pageContext, key, null);

		if ((value == null) || (value.equals(key))) {
			value = PortletResourceBundles.getString(pageContext, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	public static List<String> getActions(List<Permission> permissions) {
		List<String> actions = new UniqueList<String>();

		for (Permission permission : permissions) {
			actions.add(permission.getActionId());
		}

		return actions;
	}

	public static List<String> getActionsNames(
		PageContext pageContext, List<String> actions) {

		List<String> uniqueList = new UniqueList<String>();

		for (String action : actions) {
			uniqueList.add(getAction(pageContext, action));
		}

		List<String> list = new ArrayList<String>();

		list.addAll(uniqueList);

		return list;
	}

	public static List<String> getActionsNames(
		PageContext pageContext, String name, long actionIds) {

		try {
			List<ResourceAction> resourceActions =
				ResourceActionLocalServiceUtil.getResourceActions(name);

			List<String> actions = new ArrayList<String>();

			for (ResourceAction resourceAction : resourceActions) {
				long bitwiseValue = resourceAction.getBitwiseValue();

				if ((actionIds & bitwiseValue) == bitwiseValue) {
					actions.add(resourceAction.getActionId());
				}
			}

			return getActionsNames(pageContext, actions);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	public static List<String> getModelNames() {
		return _instance._getModelNames();
	}

	public static List<String> getModelPortletResources(String name) {
		return _instance._getModelPortletResources(name);
	}

	public static String getModelResource(Locale locale, String name) {
		String key = MODEL_RESOURCE_NAME_PREFIX + name;

		String value = LanguageUtil.get(locale, key, null);

		if ((value == null) || (value.equals(key))) {
			value = PortletResourceBundles.getString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	public static String getModelResource(
		PageContext pageContext, String name) {

		String key = MODEL_RESOURCE_NAME_PREFIX + name;

		String value = LanguageUtil.get(pageContext, key, null);

		if ((value == null) || (value.equals(key))) {
			value = PortletResourceBundles.getString(pageContext, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	public static List<String> getModelResourceActions(String name) {
		return _instance._getModelResourceActions(name);
	}

	public static List<String> getModelResourceCommunityDefaultActions(
		String name) {

		return _instance._getModelResourceCommunityDefaultActions(name);
	}

	public static List<String> getModelResourceGuestDefaultActions(
		String name) {

		return _instance._getModelResourceGuestDefaultActions(name);
	}

	public static List<String> getModelResourceGuestUnsupportedActions(
		String name) {

		return _instance._getModelResourceGuestUnsupportedActions(name);
	}

	public static List<String> getModelResourceOwnerDefaultActions(
		String name) {

		return _instance._getModelResourceOwnerDefaultActions(name);
	}

	public static String getPortletBaseResource(String portletName) {
		return _instance._getPortletBaseResource(portletName);
	}

	public static List<String> getPortletModelResources(String portletName) {
		return _instance._getPortletModelResources(portletName);
	}

	public static List<String> getPortletNames() {
		return _instance._getPortletNames();
	}

	public static List<String> getPortletResourceActions(String name) {
		return _instance._getPortletResourceActions(name);
	}

	public static List<String> getPortletResourceCommunityDefaultActions(
		String name) {

		return _instance._getPortletResourceCommunityDefaultActions(name);
	}

	public static List<String> getPortletResourceGuestDefaultActions(
		String name) {

		return _instance._getPortletResourceGuestDefaultActions(name);
	}

	public static List<String> getPortletResourceGuestUnsupportedActions(
		String name) {

		return _instance._getPortletResourceGuestUnsupportedActions(name);
	}

	public static List<String> getPortletResourceLayoutManagerActions(
		String name) {

		return _instance._getPortletResourceLayoutManagerActions(name);
	}

	public static List<String> getResourceActions(String name) {
		if (name.contains(StringPool.PERIOD)) {
			return getModelResourceActions(name);
		}
		else {
			return getPortletResourceActions(name);
		}
	}

	public static List<String> getResourceActions(
		String portletResource, String modelResource) {

		List<String> actions = null;

		if (Validator.isNull(modelResource)) {
			actions = getPortletResourceActions(portletResource);
		}
		else {
			actions = getModelResourceActions(modelResource);
		}

		return actions;
	}

	public static List<String> getResourceCommunityDefaultActions(String name) {
		if (name.contains(StringPool.PERIOD)) {
			return getModelResourceCommunityDefaultActions(name);
		}
		else {
			return getPortletResourceCommunityDefaultActions(name);
		}
	}

	public static List<String> getResourceGuestUnsupportedActions(
		String portletResource, String modelResource) {

		List<String> actions = null;

		if (Validator.isNull(modelResource)) {
			actions =
				getPortletResourceGuestUnsupportedActions(portletResource);
		}
		else {
			actions = getModelResourceGuestUnsupportedActions(modelResource);
		}

		return actions;
	}

	public static List<Role> getRoles(
			long companyId, Group group, String modelResource, int[] roleTypes)
		throws SystemException {

		List<Role> allRoles = RoleLocalServiceUtil.getRoles(companyId);

		if (roleTypes == null) {
			roleTypes = _instance._getRoleTypes(
				companyId, group, modelResource);
		}

		List<Role> roles = new ArrayList<Role>();

		for (int roleType : roleTypes) {
			for (Role role : allRoles) {
				if (role.getType() == roleType) {
					roles.add(role);
				}
			}
		}

		return roles;
	}

	public static SocialEquityActionMapping getSocialEquityActionMapping(
		String name, String actionId) {

		return _instance._getSocialEquityActionMapping(name, actionId);
	}

	public static List<SocialEquityActionMapping> getSocialEquityActionMappings(
		String name) {

		return _instance._getSocialEquityActionMappings(name);
	}

	public static String[] getSocialEquityClassNames() {
		return _instance._getSocialEquityClassNames();
	}

	public static boolean hasModelResourceActions(String name) {
		return _instance._hasModelResourceActions(name);
	}

	public static void init() {
		_instance._init();
	}

	public static boolean isOrganizationModelResource(String modelResource) {
		return _instance._isOrganizationModelResource(modelResource);
	}

	public static boolean isPortalModelResource(String modelResource) {
		return _instance._isPortalModelResource(modelResource);
	}

	public static void read(
			String servletContextName, ClassLoader classLoader, String source)
		throws Exception {

		_instance._read(servletContextName, classLoader, source);
	}

	public static void read(String servletContextName, InputStream inputStream)
		throws Exception {

		_instance._read(servletContextName, inputStream);
	}

	private ResourceActionsUtil() {
		_organizationModelResources = new HashSet<String>();

		for (String resource : ORGANIZATION_MODEL_RESOURCES) {
			_organizationModelResources.add(resource);
		}

		_portalModelResources = new HashSet<String>();

		for (String resource : PORTAL_MODEL_RESOURCES) {
			_portalModelResources.add(resource);
		}

		_portletModelResources = new HashMap<String, Set<String>>();
		_portletResourceActions = new HashMap<String, List<String>>();
		_portletResourceCommunityDefaultActions =
			new HashMap<String, List<String>>();
		_portletResourceGuestDefaultActions =
			new HashMap<String, List<String>>();
		_portletResourceGuestUnsupportedActions =
			new HashMap<String, List<String>>();
		_portletResourceLayoutManagerActions =
			new HashMap<String, List<String>>();
		_modelPortletResources = new HashMap<String, Set<String>>();
		_modelResourceActions = new HashMap<String, List<String>>();
		_modelResourceCommunityDefaultActions =
			new HashMap<String, List<String>>();
		_modelResourceGuestDefaultActions =
			new HashMap<String, List<String>>();
		_modelResourceGuestUnsupportedActions =
			new HashMap<String, List<String>>();
		_modelResourceOwnerDefaultActions =
			new HashMap<String, List<String>>();
		_socialEquityActionMappings =
			new HashMap<String, Map<String, SocialEquityActionMapping>>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			for (String config : PropsValues.RESOURCE_ACTIONS_CONFIGS) {
				_read(null, classLoader, config);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _checkGuestUnsupportedActions(
		List<String> guestUnsupportedActions,
		List<String> guestDefaultActions) {

		// Guest default actions cannot reference guest unsupported actions

		Iterator<String> itr = guestDefaultActions.iterator();

		while (itr.hasNext()) {
			String actionId = itr.next();

			if (guestUnsupportedActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	private void _checkModelActions(List<String> actions) {
		if (!actions.contains(ActionKeys.PERMISSIONS)) {
			actions.add(ActionKeys.PERMISSIONS);
		}
	}

	private void _checkPortletActions(List<String> actions) {
		if (!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			!actions.contains(ActionKeys.ADD_TO_PAGE)) {

			actions.add(ActionKeys.ADD_TO_PAGE);
		}

		if (!actions.contains(ActionKeys.CONFIGURATION)) {
			actions.add(ActionKeys.CONFIGURATION);
		}

		if (!actions.contains(ActionKeys.VIEW)) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private void _checkPortletCommunityDefaultActions(List<String> actions) {
		if (actions.size() == 0) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private void _checkPortletGuestDefaultActions(List<String> actions) {
		if (actions.size() == 0) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private void _checkPortletLayoutManagerActions(List<String> actions) {
		if (!actions.contains(ActionKeys.CONFIGURATION)) {
			actions.add(ActionKeys.CONFIGURATION);
		}

		if (!actions.contains(ActionKeys.PREFERENCES)) {
			actions.add(ActionKeys.PREFERENCES);
		}

		if (!actions.contains(ActionKeys.VIEW)) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private List<String> _getActions(
		Map<String, List<String>> actionsMap, String name) {

		List<String> actions = actionsMap.get(name);

		if (actions == null) {
			actions = new UniqueList<String>();

			actionsMap.put(name, actions);
		}

		return actions;
	}

	private List<String> _getModelNames() {
		return ListUtil.fromCollection(_modelPortletResources.keySet());
	}

	private List<String> _getModelPortletResources(String name) {
		Set<String> resources = _modelPortletResources.get(name);

		if (resources == null) {
			return new UniqueList<String>();
		}
		else {
			return Collections.list(Collections.enumeration(resources));
		}
	}

	private List<String> _getModelResourceActions(String name) {
		return _getActions(_modelResourceActions, name);
	}

	private List<String> _getModelResourceCommunityDefaultActions(
		String name) {

		return _getActions(_modelResourceCommunityDefaultActions, name);
	}

	private List<String> _getModelResourceGuestDefaultActions(String name) {
		return _getActions(_modelResourceGuestDefaultActions, name);
	}

	private List<String> _getModelResourceGuestUnsupportedActions(String name) {
		return _getActions(_modelResourceGuestUnsupportedActions, name);
	}

	private List<String> _getModelResourceOwnerDefaultActions(String name) {
		return _getActions(_modelResourceOwnerDefaultActions, name);
	}

	private Element _getPermissionsChildElement(
		Element parentElement, String childElementName) {

		Element permissionsElement = parentElement.element("permissions");

		if (permissionsElement != null) {
			return permissionsElement.element(childElementName);
		}
		else {
			return parentElement.element(childElementName);
		}
	}

	private String _getPortletBaseResource(String portletName) {
		List<String> modelNames = _getPortletModelResources(portletName);

		for (String modelName : modelNames) {
			if (!modelName.contains(".model.")) {
				return modelName;
			}
		}

		return null;
	}

	private List<String> _getPortletMimeTypeActions(String name) {
		List<String> actions = new UniqueList<String>();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(name);

		if (portlet != null) {
			Map<String, Set<String>> portletModes = portlet.getPortletModes();

			Set<String> mimeTypePortletModes = portletModes.get(
				ContentTypes.TEXT_HTML);

			if (mimeTypePortletModes != null) {
				for (String actionId : mimeTypePortletModes) {
					if (actionId.equalsIgnoreCase("edit")) {
						actions.add(ActionKeys.PREFERENCES);
					}
					else if (actionId.equalsIgnoreCase("edit_guest")) {
						actions.add(ActionKeys.GUEST_PREFERENCES);
					}
					else {
						actions.add(actionId.toUpperCase());
					}
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to obtain resource actions for unknown portlet " +
						name);
			}
		}

		return actions;
	}

	private List<String> _getPortletModelResources(String portletName) {
		portletName = PortletConstants.getRootPortletId(portletName);

		Set<String> resources = _portletModelResources.get(portletName);

		if (resources == null) {
			return new UniqueList<String>();
		}
		else {
			return Collections.list(Collections.enumeration(resources));
		}
	}

	private List<String> _getPortletNames() {
		return ListUtil.fromCollection(_portletModelResources.keySet());
	}

	private List<String> _getPortletResourceActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		List<String> actions = _getActions(_portletResourceActions, name);

		if (actions.size() == 0) {
			synchronized (this) {
				actions = _getPortletMimeTypeActions(name);

				if (!name.equals(PortletKeys.PORTAL)) {
					_checkPortletActions(actions);
				}

				List<String> communityDefaultActions =
					_portletResourceCommunityDefaultActions.get(name);

				if (communityDefaultActions == null) {
					communityDefaultActions = new UniqueList<String>();

					_portletResourceCommunityDefaultActions.put(
						name, communityDefaultActions);

					_checkPortletCommunityDefaultActions(
						communityDefaultActions);
				}

				List<String> guestDefaultActions =
					_portletResourceGuestDefaultActions.get(name);

				if (guestDefaultActions == null) {
					guestDefaultActions = new UniqueList<String>();

					_portletResourceGuestDefaultActions.put(
						name, guestDefaultActions);

					_checkPortletGuestDefaultActions(guestDefaultActions);
				}

				List<String> layoutManagerActions =
					_portletResourceLayoutManagerActions.get(name);

				if (layoutManagerActions == null) {
					layoutManagerActions = new UniqueList<String>();

					_portletResourceLayoutManagerActions.put(
						name, layoutManagerActions);

					_checkPortletLayoutManagerActions(layoutManagerActions);
				}
			}
		}

		return actions;
	}

	private List<String> _getPortletResourceCommunityDefaultActions(
		String name) {

		// This method should always be called only after
		// _getPortletResourceActions has been called at least once to
		// populate the default community actions. Check to make sure this is
		// the case. However, if it is not, that means the methods
		// _getPortletResourceGuestDefaultActions and
		// _getPortletResourceGuestDefaultActions may not work either.

		name = PortletConstants.getRootPortletId(name);

		return _getActions(_portletResourceCommunityDefaultActions, name);
	}

	private List<String> _getPortletResourceGuestDefaultActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		return _getActions(_portletResourceGuestDefaultActions, name);
	}

	private List<String> _getPortletResourceGuestUnsupportedActions(
		String name) {

		name = PortletConstants.getRootPortletId(name);

		return _getActions(_portletResourceGuestUnsupportedActions, name);
	}

	private List<String> _getPortletResourceLayoutManagerActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		List<String> actions = _getActions(
			_portletResourceLayoutManagerActions, name);

		// This check can never return an empty list. If the list is empty, it
		// means that the portlet does not have an explicit resource-actions
		// configuration file and should therefore be handled as if it has
		// defaults of CONFIGURATION, PREFERENCES, and VIEW.

		if (actions.size() < 1) {
			actions.add(ActionKeys.CONFIGURATION);
			actions.add(ActionKeys.PREFERENCES);
			actions.add(ActionKeys.VIEW);
		}

		return actions;
	}

	private int[] _getRoleTypes (
		long companyId, Group group, String modelResource) {

		int[] types = {
			RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_COMMUNITY
		};

		if (isPortalModelResource(modelResource)) {
			if (modelResource.equals(Organization.class.getName()) ||
				modelResource.equals(User.class.getName())) {

				types = new int[] {
					RoleConstants.TYPE_REGULAR,
					RoleConstants.TYPE_ORGANIZATION
				};
			}
			else {
				types = new int[] {RoleConstants.TYPE_REGULAR};
			}
		}
		else {
			if (group != null) {
				if (group.isOrganization()) {
					types = new int[] {
						RoleConstants.TYPE_REGULAR,
						RoleConstants.TYPE_ORGANIZATION
					};
				}
				else if (group.isUser()) {
					types = new int[] {RoleConstants.TYPE_REGULAR};
				}
			}
		}

		return types;
	}

	private SocialEquityActionMapping _getSocialEquityActionMapping(
		String name, String actionId) {

		Map<String, SocialEquityActionMapping> socialEquityActionMappings =
			_socialEquityActionMappings.get(name);

		if (socialEquityActionMappings == null) {
			return null;
		}

		return socialEquityActionMappings.get(actionId);
	}

	private List<SocialEquityActionMapping> _getSocialEquityActionMappings(
		String name) {

		Map<String, SocialEquityActionMapping> socialEquityActionMappings =
			_socialEquityActionMappings.get(name);

		if (socialEquityActionMappings == null) {
			return Collections.emptyList();
		}

		List<SocialEquityActionMapping> socialEquityActionMappingList =
			new ArrayList<SocialEquityActionMapping>();

		for (Map.Entry<String, SocialEquityActionMapping> entry :
				socialEquityActionMappings.entrySet()) {

			socialEquityActionMappingList.add(entry.getValue());
		}

		return socialEquityActionMappingList;
	}

	private String[] _getSocialEquityClassNames() {
		Set<String> classNames = _socialEquityActionMappings.keySet();

		return classNames.toArray(new String[classNames.size()]);
	}

	private boolean _hasModelResourceActions(String name) {
		List<String> actions = _modelResourceActions.get(name);

		if ((actions != null) && !actions.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _init() {
	}

	private boolean _isOrganizationModelResource(String modelResource) {
		if (_organizationModelResources.contains(modelResource)) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isPortalModelResource(String modelResource) {
		if (_portalModelResources.contains(modelResource)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _read(
			String servletContextName, ClassLoader classLoader, String source)
		throws Exception {

		InputStream inputStream = classLoader.getResourceAsStream(source);

		if (inputStream == null) {
			if (_log.isWarnEnabled() && !source.endsWith("-ext.xml")) {
				_log.warn("Cannot load " + source);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		Document document = SAXReaderUtil.read(inputStream);

		Element rootElement = document.getRootElement();

		for (Element resourceElement : rootElement.elements("resource")) {
			String file = resourceElement.attributeValue("file").trim();

			_read(servletContextName, classLoader, file);

			String extFile = StringUtil.replace(file, ".xml", "-ext.xml");

			_read(servletContextName, classLoader, extFile);
		}

		_read(servletContextName, document);
	}

	private void _read(String servletContextName, Document document)
		throws Exception {

		Element rootElement = document.getRootElement();

		if (PropsValues.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES) {
			for (Element portletResourceElement :
					rootElement.elements("portlet-resource")) {

				_readPortletResource(
					servletContextName, portletResourceElement);
			}
		}

		for (Element modelResourceElement :
				rootElement.elements("model-resource")) {

			_readModelResource(servletContextName, modelResourceElement);
		}
	}

	private void _read(String servletContextName, InputStream inputStream)
		throws Exception {

		Document document = SAXReaderUtil.read(inputStream);

		_read(servletContextName, document);
	}

	private void _readActionKeys(Element parentElement, List<String> actions) {
		for (Element actionKeyElement : parentElement.elements("action-key")) {
			String actionKey = actionKeyElement.getTextTrim();

			if (Validator.isNull(actionKey)) {
				continue;
			}

			actions.add(actionKey);
		}
	}

	private void _readCommunityDefaultActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> communityDefaultActions = _getActions(actionsMap, name);

		Element communityDefaultsElement = _getPermissionsChildElement(
			parentElement, "community-defaults");

		_readActionKeys(communityDefaultsElement, communityDefaultActions);
	}

	private List<String> _readGuestDefaultActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> guestDefaultActions = _getActions(actionsMap, name);

		Element guestDefaultsElement = _getPermissionsChildElement(
			parentElement, "guest-defaults");

		_readActionKeys(guestDefaultsElement, guestDefaultActions);

		return guestDefaultActions;
	}

	private void _readGuestUnsupportedActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name, List<String> guestDefaultActions) {

		List<String> guestUnsupportedActions = _getActions(actionsMap, name);

		Element guestUnsupportedElement = _getPermissionsChildElement(
			parentElement, "guest-unsupported");

		_readActionKeys(guestUnsupportedElement, guestUnsupportedActions);

		_checkGuestUnsupportedActions(
			guestUnsupportedActions, guestDefaultActions);
	}

	private void _readLayoutManagerActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name, List<String> supportsActions) {

		List<String> layoutManagerActions = _getActions(actionsMap, name);

		Element layoutManagerElement = _getPermissionsChildElement(
			parentElement, "layout-manager");

		if (layoutManagerElement != null) {
			_readActionKeys(layoutManagerElement, layoutManagerActions);
		}
		else {
			layoutManagerActions.addAll(supportsActions);
		}
	}

	private void _readModelResource(
		String servletContextName, Element modelResourceElement) {

		String name = modelResourceElement.elementTextTrim("model-name");

		Element portletRefElement = modelResourceElement.element("portlet-ref");

		for (Element portletNameElement :
				portletRefElement.elements("portlet-name")) {

			String portletName = portletNameElement.getTextTrim();

			if (servletContextName != null) {
				portletName =
					portletName.concat(PortletConstants.WAR_SEPARATOR).concat(
						servletContextName);
			}

			portletName = PortalUtil.getJsSafePortletId(portletName);

			// Reference for a portlet to child models

			Set<String> modelResources = _portletModelResources.get(
				portletName);

			if (modelResources == null) {
				modelResources = new HashSet<String>();

				_portletModelResources.put(portletName, modelResources);
			}

			modelResources.add(name);

			// Reference for a model to parent portlets

			Set<String> portletResources = _modelPortletResources.get(name);

			if (portletResources == null) {
				portletResources = new HashSet<String>();

				_modelPortletResources.put(name, portletResources);
			}

			portletResources.add(portletName);
		}

		List<String> supportsActions = _readSupportsActions(
			modelResourceElement, _modelResourceActions, name);

		_checkModelActions(supportsActions);

		_readCommunityDefaultActions(
			modelResourceElement, _modelResourceCommunityDefaultActions,  name);

		List<String> guestDefaultActions = _readGuestDefaultActions(
			modelResourceElement, _modelResourceGuestDefaultActions, name);

		_readGuestUnsupportedActions(
			modelResourceElement, _modelResourceGuestUnsupportedActions, name,
			guestDefaultActions);

		_readOwnerDefaultActions(
			modelResourceElement, _modelResourceOwnerDefaultActions, name);

		_readSocialEquity(modelResourceElement, name);
	}

	private void _readOwnerDefaultActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> ownerDefaultActions = _getActions(actionsMap, name);

		Element ownerDefaultsElement = _getPermissionsChildElement(
			parentElement, "owner-defaults");

		if (ownerDefaultsElement == null) {
			return;
		}

		_readActionKeys(ownerDefaultsElement, ownerDefaultActions);
	}

	private void _readPortletResource(
		String servletContextName, Element portletResourceElement) {

		String name = portletResourceElement.elementTextTrim("portlet-name");

		if (servletContextName != null) {
			name = name.concat(PortletConstants.WAR_SEPARATOR).concat(
				servletContextName);
		}

		name = PortalUtil.getJsSafePortletId(name);

		List<String> supportsActions = _readSupportsActions(
			portletResourceElement, _portletResourceActions, name);

		supportsActions.addAll(_getPortletMimeTypeActions(name));

		if (!name.equals(PortletKeys.PORTAL)) {
			_checkPortletActions(supportsActions);
		}

		_readCommunityDefaultActions(
			portletResourceElement, _portletResourceCommunityDefaultActions,
			name);

		List<String> guestDefaultActions = _readGuestDefaultActions(
			portletResourceElement, _portletResourceGuestDefaultActions, name);

		_readGuestUnsupportedActions(
			portletResourceElement, _portletResourceGuestUnsupportedActions,
			name, guestDefaultActions);

		_readLayoutManagerActions(
			portletResourceElement, _portletResourceLayoutManagerActions, name,
			supportsActions);
	}

	private void _readSocialEquity(Element parentElement, String name) {
		Element socialEquityElement = parentElement.element("social-equity");

		if (socialEquityElement == null) {
			return;
		}

		for (Element socialEquityMappingElement :
				socialEquityElement.elements("social-equity-mapping")) {

			_readSocialEquityMapping(socialEquityMappingElement, name);
		}
	}

	private void _readSocialEquityMapping(
		Element socialEquityMappingElement, String name) {

		Element actionKeyElement =
			socialEquityMappingElement.element("action-key");

		if (actionKeyElement == null) {
			return;
		}

		String actionKey = actionKeyElement.getTextTrim();

		if (Validator.isNull(actionKey)) {
			return;
		}

		int informationDailyLimit = GetterUtil.getInteger(
			socialEquityMappingElement.elementText("information-daily-limit"));
		int informationLifespan = GetterUtil.getInteger(
			socialEquityMappingElement.elementText("information-lifespan"));
		int informationValue = GetterUtil.getInteger(
			socialEquityMappingElement.elementText("information-value"));
		int participationDailyLimit = GetterUtil.getInteger(
			socialEquityMappingElement.elementText(
				"participation-daily-limit"));
		int participationLifespan = GetterUtil.getInteger(
			socialEquityMappingElement.elementText("participation-lifespan"));
		int participationValue = GetterUtil.getInteger(
			socialEquityMappingElement.elementText("participation-value"));
		boolean unique = GetterUtil.getBoolean(
			actionKeyElement.attributeValue("unique"));

		SocialEquityActionMapping socialEquityActionMapping =
			new SocialEquityActionMapping();

		socialEquityActionMapping.setActionId(actionKey);
		socialEquityActionMapping.setClassName(name);
		socialEquityActionMapping.setInformationDailyLimit(
			informationDailyLimit);
		socialEquityActionMapping.setInformationLifespan(informationLifespan);
		socialEquityActionMapping.setInformationValue(informationValue);
		socialEquityActionMapping.setParticipationDailyLimit(
			participationDailyLimit);
		socialEquityActionMapping.setParticipationLifespan(
			participationLifespan);
		socialEquityActionMapping.setParticipationValue(participationValue);
		socialEquityActionMapping.setUnique(unique);

		Map<String, SocialEquityActionMapping> socialEquityActionMappings =
			_socialEquityActionMappings.get(name);

		if (socialEquityActionMappings == null) {
			socialEquityActionMappings =
				new HashMap<String, SocialEquityActionMapping>();

			_socialEquityActionMappings.put(name, socialEquityActionMappings);
		}

		socialEquityActionMappings.put(actionKey, socialEquityActionMapping);
	}

	private List<String> _readSupportsActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> supportsActions = _getActions(actionsMap, name);

		Element supportsElement = _getPermissionsChildElement(
			parentElement, "supports");

		_readActionKeys(supportsElement, supportsActions);

		return supportsActions;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceActionsUtil.class);

	private static ResourceActionsUtil _instance = new ResourceActionsUtil();

	private Map<String, Set<String>> _modelPortletResources;
	private Map<String, List<String>> _modelResourceActions;
	private Map<String, List<String>> _modelResourceCommunityDefaultActions;
	private Map<String, List<String>> _modelResourceGuestDefaultActions;
	private Map<String, List<String>> _modelResourceGuestUnsupportedActions;
	private Map<String, List<String>> _modelResourceOwnerDefaultActions;
	private Set<String> _organizationModelResources;
	private Set<String> _portalModelResources;
	private Map<String, Set<String>> _portletModelResources;
	private Map<String, List<String>> _portletResourceActions;
	private Map<String, List<String>> _portletResourceCommunityDefaultActions;
	private Map<String, List<String>> _portletResourceGuestDefaultActions;
	private Map<String, List<String>> _portletResourceGuestUnsupportedActions;
	private Map<String, List<String>> _portletResourceLayoutManagerActions;
	private Map<String, Map<String, SocialEquityActionMapping>>
		_socialEquityActionMappings;

}