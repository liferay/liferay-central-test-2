/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;

import com.liferay.portal.service.persistence.PortletPK;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.CachePortlet;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.UnavailableException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Portlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Portlet extends PortletModel {

	/**
	 * War file separator.
	 */
	public static final String WAR_SEPARATOR = "_WAR_";

	/**
	 * Instance separator.
	 */
	public static final String INSTANCE_SEPARATOR = "_INSTANCE_";

	/**
	 * Layout separator.
	 */
	public static final String LAYOUT_SEPARATOR = "_LAYOUT_";

	/**
	 * Default preferences.
	 */
	public static final String DEFAULT_PREFERENCES = "<portlet-preferences />";

	/**
	 * Gets the root portlet id of the portlet.
	 *
	 * @param		portletId the portlet id of the portlet
	 * @return		the root portlet id of the portlet
	 */
	public static String getRootPortletId(String portletId) {
		int pos = portletId.indexOf(INSTANCE_SEPARATOR);

		if (pos == -1) {
			return portletId;
		}
		else {
			return portletId.substring(0, pos);
		}
	}

	/**
	 * Gets the instance id of the portlet.
	 *
	 * @param		portletId the portlet id of the portlet
	 * @return		the instance id of the portlet
	 */
	public static String getInstanceId(String portletId) {
		int pos = portletId.indexOf(INSTANCE_SEPARATOR);

		if (pos == -1) {
			return null;
		}
		else {
			return portletId.substring(
				pos + INSTANCE_SEPARATOR.length(), portletId.length());
		}
	}

	/**
	 * Constructs a portlet with no parameters.
	 */
	public Portlet() {
	}

	/**
	 * Constructs a portlet with the specified parameters.
	 */
	public Portlet(PortletPK pk) {
		setPrimaryKey(pk);

		setStrutsPath(pk.portletId);
		setActive(true);
		_unlinkedRoles = new HashSet();
		_roleMappers = new LinkedHashMap();
		_initParams = new HashMap();
		_portletModes = new HashMap();
		_supportedLocales = new HashSet();
		_userAttributes = new LinkedHashSet();
		_customUserAttributes = new LinkedHashMap();
	}

	/**
	 * Constructs a portlet with the specified parameters.
	 */
	public Portlet(String portletId, String companyId, String strutsPath,
				   String configurationPath, String portletClass,
				   String indexerClass, String schedulerClass,
				   String portletURLClass, String friendlyURLPluginClass,
				   String defaultPreferences, String prefsValidator,
				   boolean prefsCompanyWide, boolean prefsUniquePerLayout,
				   boolean prefsOwnedByGroup, boolean useDefaultTemplate,
				   boolean showPortletAccessDenied, boolean showPortletInactive,
				   boolean restoreCurrentView, boolean maximizeEdit,
				   boolean maximizeHelp, boolean maximizePrint,
				   boolean layoutCacheable, boolean instanceable,
				   boolean privateRequestAttributes, boolean narrow,
				   String roles, Set unlinkedRoles, Map roleMappers,
				   boolean system, boolean active, boolean include,
				   Map initParams, Integer expCache, Map portletModes,
				   Set supportedLocales, String resourceBundle,
				   PortletInfo portletInfo, Set userAttributes,
				   Map customUserAttributes, boolean warFile) {

		setPortletId(portletId);
		setCompanyId(companyId);
		_strutsPath = strutsPath;
		_configurationPath = configurationPath;
		_portletClass = portletClass;
		_indexerClass = indexerClass;
		_schedulerClass = schedulerClass;
		_portletURLClass = portletURLClass;
		_friendlyURLPluginClass = friendlyURLPluginClass;
		_defaultPreferences = defaultPreferences;
		_prefsValidator = prefsValidator;
		_prefsCompanyWide = prefsCompanyWide;
		_prefsUniquePerLayout = prefsUniquePerLayout;
		_prefsOwnedByGroup = prefsOwnedByGroup;
		_useDefaultTemplate = useDefaultTemplate;
		_showPortletAccessDenied = showPortletAccessDenied;
		_showPortletInactive = showPortletInactive;
		_restoreCurrentView = restoreCurrentView;
		_maximizeEdit = maximizeEdit;
		_maximizeHelp = maximizeHelp;
		_maximizePrint = maximizePrint;
		_layoutCacheable = layoutCacheable;
		_instanceable = instanceable;
		_privateRequestAttributes = privateRequestAttributes;
		setRoles(roles);
		_unlinkedRoles = unlinkedRoles;
		_roleMappers = roleMappers;
		_system = system;
		setActive(active);
		_include = include;
		_initParams = initParams;
		_expCache = expCache;
		_portletModes = portletModes;
		_supportedLocales = supportedLocales;
		_resourceBundle = resourceBundle;
		_portletInfo = portletInfo;
		_userAttributes = userAttributes;
		_customUserAttributes = customUserAttributes;
		_warFile = warFile;

		if (_instanceable) {
			_clonedInstances = new Hashtable();
		}
	}

	/**
	 * Gets the root portlet id of the portlet.
	 *
	 * @return		the root portlet id of the portlet
	 */
	public String getRootPortletId() {
		return getRootPortletId(getPortletId());
	}

	/**
	 * Gets the instance id of the portlet.
	 *
	 * @return		the instance id of the portlet
	 */
	public String getInstanceId() {
		return getInstanceId(getPortletId());
	}

	/**
	 * Gets the struts path of the portlet.
	 *
	 * @return		the struts path of the portlet
	 */
	public String getStrutsPath() {
		return _strutsPath;
	}

	/**
	 * Sets the struts path of the portlet.
	 *
	 * @param		strutsPath the struts path of the portlet
	 */
	public void setStrutsPath(String strutsPath) {
		_strutsPath = strutsPath;
	}

	/**
	 * Gets the configuration path of the portlet.
	 *
	 * @return		the configuration path of the portlet
	 */
	public String getConfigurationPath() {
		return _configurationPath;
	}

	/**
	 * Sets the configuration path of the portlet.
	 *
	 * @param		configurationPath the configuration path of the portlet
	 */
	public void setConfigurationPath(String configurationPath) {
		_configurationPath = configurationPath;
	}

	/**
	 * Gets the name of the portlet class of the portlet.
	 *
	 * @return		the name of the portlet class of the portlet
	 */
	public String getPortletClass() {
		return _portletClass;
	}

	/**
	 * Sets the name of the portlet class of the portlet.
	 *
	 * @param		portletClass the name of the portlet class of the portlet
	 */
	public void setPortletClass(String portletClass) {
		_portletClass = portletClass;
	}

	/**
	 * Gets the name of the indexer class of the portlet.
	 *
	 * @return		the name of the indexer class of the portlet
	 */
	public String getIndexerClass() {
		return _indexerClass;
	}

	/**
	 * Sets the name of the indexer class of the portlet.
	 *
	 * @param		indexerClass the name of the indexer class of the portlet
	 */
	public void setIndexerClass(String indexerClass) {
		_indexerClass = indexerClass;
	}

	/**
	 * Gets the name of the scheduler class of the portlet.
	 *
	 * @return		the name of the scheduler class of the portlet
	 */
	public String getSchedulerClass() {
		return _schedulerClass;
	}

	/**
	 * Sets the name of the scheduler class of the portlet.
	 *
	 * @param		schedulerClass the name of the scheduler class of the
	 *				portlet
	 */
	public void setSchedulerClass(String schedulerClass) {
		_schedulerClass = schedulerClass;
	}

	/**
	 * Gets the name of the portlet URL class of the portlet.
	 *
	 * @return		the name of the portlet URL class of the portlet
	 */
	public String getPortletURLClass() {
		return _portletURLClass;
	}

	/**
	 * Sets the name of the portlet URL class of the portlet.
	 *
	 * @param		portletURLClass the name of the portlet URL class of the
	 *				portlet
	 */
	public void setPortletURLClass(String portletURLClass) {
		_portletURLClass = portletURLClass;
	}

	/**
	 * Gets the name of the friendly URL plugin class of the portlet.
	 *
	 * @return		the name of the friendly URL plugin class of the portlet
	 */
	public String getFriendlyURLPluginClass() {
		return _friendlyURLPluginClass;
	}

	/**
	 * Sets the name of the friendly URL plugin class of the portlet.
	 *
	 * @param		friendlyURLPluginClass the name of the friendly URL plugin
	 *				class of the portlet
	 */
	public void setFriendlyURLPluginClass(String friendlyURLPluginClass) {
		_friendlyURLPluginClass = friendlyURLPluginClass;
	}

	/**
	 * Gets the default preferences of the portlet.
	 *
	 * @return		the default preferences of the portlet
	 */
	public String getDefaultPreferences() {
		if (Validator.isNull(_defaultPreferences)) {
			return DEFAULT_PREFERENCES;
		}
		else {
			return _defaultPreferences;
		}
	}

	/**
	 * Sets the default preferences of the portlet.
	 *
	 * @param		defaultPreferences the default preferences of the portlet
	 */
	public void setDefaultPreferences(String defaultPreferences) {
		_defaultPreferences = defaultPreferences;
	}

	/**
	 * Gets the name of the preferences validator class of the portlet.
	 *
	 * @return		the name of the preferences validator class of the portlet
	 */
	public String getPreferencesValidator() {
		return _prefsValidator;
	}

	/**
	 * Sets the name of the preferences validator class of the portlet.
	 *
	 * @param		prefsValidator the name of the preferences validator class
	 *				of the portlet
	 */
	public void setPreferencesValidator(String prefsValidator) {
		if (prefsValidator != null) {

			// Trim this because XDoclet generates preferences validators with
			// extra white spaces

			_prefsValidator = prefsValidator.trim();
		}
		else {
			_prefsValidator = null;
		}
	}

	/**
	 * Returns true if preferences are shared across the entire company.
	 *
	 * @return		true if preferences are shared across the entire company
	 */
	public boolean getPreferencesCompanyWide() {
		return _prefsCompanyWide;
	}

	/**
	 * Returns true if preferences are shared across the entire company.
	 *
	 * @return		true if preferences are shared across the entire company
	 */
	public boolean isPreferencesCompanyWide() {
		return _prefsCompanyWide;
	}

	/**
	 * Sets to true if preferences are shared across the entire company.
	 *
	 * @param		prefsCompanyWide boolean value for whether preferences
	 *				are shared across the entire company
	 */
	public void setPreferencesCompanyWide(boolean prefsCompanyWide) {
		_prefsCompanyWide = prefsCompanyWide;
	}

	/**
	 * Returns true if preferences are unique per layout.
	 *
	 * @return		true if preferences are unique per layout
	 */
	public boolean getPreferencesUniquePerLayout() {
		return _prefsUniquePerLayout;
	}

	/**
	 * Returns true if preferences are unique per layout.
	 *
	 * @return		true if preferences are unique per layout
	 */
	public boolean isPreferencesUniquePerLayout() {
		return _prefsUniquePerLayout;
	}

	/**
	 * Sets to true if preferences are unique per layout.
	 *
	 * @param		prefsUniquePerLayout boolean value for whether preferences
	 *				are unique per layout
	 */
	public void setPreferencesUniquePerLayout(boolean prefsUniquePerLayout) {
		_prefsUniquePerLayout = prefsUniquePerLayout;
	}

	/**
	 * Returns true if preferences are owned by the group when the portlet is
	 * shown in a group layout. Returns false if preferences are owned by the
	 * user at all times.
	 *
	 * @return		true if preferences are owned by the group when the portlet
	 *				is shown in a group layout; false if preferences are owned
	 *				by the user at all times.
	 */
	public boolean getPreferencesOwnedByGroup() {
		return _prefsOwnedByGroup;
	}

	/**
	 * Returns true if preferences are owned by the group when the portlet is
	 * shown in a group layout. Returns false if preferences are owned by the
	 * user at all times.
	 *
	 * @return		true if preferences are owned by the group when the portlet
	 *				is shown in a group layout; false if preferences are owned
	 *				by the user at all times.
	 */
	public boolean isPreferencesOwnedByGroup() {
		return _prefsOwnedByGroup;
	}

	/**
	 * Sets to true if preferences are owned by the group when the portlet is
	 * shown in a group layout. Sets to false if preferences are owned by the
	 * user at all times.
	 *
	 * @param		prefsOwnedByGroup boolean value for whether preferences are
	 *				owned by the group when the portlet is shown in a group
	 *				layout or preferences are owned by the user at all times
	 */
	public void setPreferencesOwnedByGroup(boolean prefsOwnedByGroup) {
		_prefsOwnedByGroup = prefsOwnedByGroup;
	}

	/**
	 * Returns true if the portlet uses the default template.
	 *
	 * @return		true if the portlet uses the default template
	 */
	public boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	/**
	 * Returns true if the portlet uses the default template.
	 *
	 * @return		true if the portlet uses the default template
	 */
	public boolean isUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	/**
	 * Sets to true if the portlet uses the default template.
	 *
	 * @param		useDefaultTemplate boolean value for whether the portlet
	 *				uses the default template
	 */
	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	/**
	 * Returns true if users are shown that they do not have access to the
	 * portlet.
	 *
	 * @return		true if users are shown that they do not have access to the
	 *				portlet
	 */
	public boolean getShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	/**
	 * Returns true if users are shown that they do not have access to the
	 * portlet.
	 *
	 * @return		true if users are shown that they do not have access to the
	 *				portlet
	 */
	public boolean isShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	/**
	 * Sets to true if users are shown that they do not have access to the
	 * portlet.
	 *
	 * @param		showPortletAccessDenied boolean value for whether users are
	 *				shown that they do not have access to the portlet
	 */
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_showPortletAccessDenied = showPortletAccessDenied;
	}

	/**
	 * Returns true if users are shown that the portlet is inactive.
	 *
	 * @return		true if users are shown that the portlet is inactive
	 */
	public boolean getShowPortletInactive() {
		return _showPortletInactive;
	}

	/**
	 * Returns true if users are shown that the portlet is inactive.
	 *
	 * @return		true if users are shown that the portlet is inactive
	 */
	public boolean isShowPortletInactive() {
		return _showPortletInactive;
	}

	/**
	 * Sets to true if users are shown that the portlet is inactive.
	 *
	 * @param		showPortletInactive boolean value for whether users are
	 *				shown that the portlet is inactive
	 */
	public void setShowPortletInactive(boolean showPortletInactive) {
		_showPortletInactive = showPortletInactive;
	}

	/**
	 * Returns true if the portlet restores to the current view from the
	 * maximized state.
	 *
	 * @return		true if the portlet restores to the current view from the
	 *				maximized state
	 */
	public boolean getRestoreCurrentView() {
		return _restoreCurrentView;
	}

	/**
	 * Returns true if the portlet restores to the current view from the
	 * maximized state.
	 *
	 * @return		true if the portlet restores to the current view from the
	 *				maximized state
	 */
	public boolean isRestoreCurrentView() {
		return _restoreCurrentView;
	}

	/**
	 * Sets to true if the portlet restores to the current view from the
	 * maximized state.
	 *
	 * @param		restoreCurrentView boolean value for whether the portlet
	 *				restores to the current view from the maximized state
	 */
	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_restoreCurrentView = restoreCurrentView;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the edit mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the edit mode
	 */
	public boolean getMaximizeEdit() {
		return _maximizeEdit;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the edit mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the edit mode
	 */
	public boolean isMaximizeEdit() {
		return _maximizeEdit;
	}

	/**
	 * Sets to true if the portlet goes into the maximized state when the user
	 * goes into the edit mode.
	 *
	 * @param		maximizeEdit boolean value for whether the portlet goes into
	 *				the maximized state when the user goes into the edit mode
	 */
	public void setMaximizeEdit(boolean maximizeEdit) {
		_maximizeEdit = maximizeEdit;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the help mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the help mode
	 */
	public boolean getMaximizeHelp() {
		return _maximizeHelp;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the help mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the help mode
	 */
	public boolean isMaximizeHelp() {
		return _maximizeHelp;
	}

	/**
	 * Sets to true if the portlet goes into the maximized state when the user
	 * goes into the help mode.
	 *
	 * @param		maximizeHelp boolean value for whether the portlet goes into
	 *				the maximized state when the user goes into the help mode
	 */
	public void setMaximizeHelp(boolean maximizeHelp) {
		_maximizeHelp = maximizeHelp;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the print mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the print mode
	 */
	public boolean getMaximizePrint() {
		return _maximizePrint;
	}

	/**
	 * Returns true if the portlet goes into the maximized state when the user
	 * goes into the print mode.
	 *
	 * @return		true if the portlet goes into the maximized state when the
	 *				user goes into the print mode
	 */
	public boolean isMaximizePrint() {
		return _maximizePrint;
	}

	/**
	 * Sets to true if the portlet goes into the maximized state when the user
	 * goes into the print mode.
	 *
	 * @param		maximizePrint boolean value for whether the portlet goes into
	 *				the maximized state when the user goes into the print mode
	 */
	public void setMaximizePrint(boolean maximizePrint) {
		_maximizePrint = maximizePrint;
	}

	/**
	 * Returns true to allow the portlet to be cached within the layout.
	 *
	 * @return		layout-cacheable parameter of the portlet
	 */
	public boolean getLayoutCacheable() {
		return _layoutCacheable;
	}

	/**
	 * Returns true to allow the portlet to be cached within the layout.
	 *
	 * @return		layout-cacheable parameter of the portlet
	 */
	public boolean isLayoutCacheable() {
		return _layoutCacheable;
	}

	/**
	 * Sets to true to allow the portlet to be cached within the layout.
	 *
	 * @layoutCacheable	layout-cacheable parameter of the portlet
	 */
	public void setLayoutCacheable(boolean layoutCacheable) {
		_layoutCacheable = layoutCacheable;
	}

	/**
	 * Returns true if the portlet can be added multiple times to a layout.
	 *
	 * @return		true if the portlet can be added multiple times to a layout
	 */
	public boolean getInstanceable() {
		return _instanceable;
	}

	/**
	 * Returns true if the portlet can be added multiple times to a layout.
	 *
	 * @return		true if the portlet can be added multiple times to a layout
	 */
	public boolean isInstanceable() {
		return _instanceable;
	}

	/**
	 * Sets to true if the portlet can be added multiple times to a layout.
	 *
	 * @param		instanceable boolean value for whether the portlet can be
	 *				added multiple times to a layout
	 */
	public void setInstanceable(boolean instanceable) {
		_instanceable = instanceable;
	}

	/**
	 * Returns true if the portlet does not share request attributes with any
	 * other portlet.
	 *
	 * @return		true if the portlet does not share request attributes with
	 *				any other portlet
	 */
	public boolean getPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	/**
	 * Returns true if the portlet does not share request attributes with any
	 * other portlet.
	 *
	 * @return		true if the portlet does not share request attributes with
	 *				any other portlet
	 */
	public boolean isPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	/**
	 * Sets to true if the portlet does not share request attributes with any
	 * other portlet.
	 *
	 * @param		privateRequestAttributes boolean value for whether the
	 *				portlet shares request attributes with any other portlet
	 */
	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_privateRequestAttributes = privateRequestAttributes;
	}

	/**
	 * Sets a string of ordered comma delimited portlet ids.
	 *
	 * @param		roles a string of ordered comma delimited portlet ids
	 */
	public void setRoles(String roles) {
		_rolesArray = StringUtil.split(roles);

		super.setRoles(roles);
	}

	/**
	 * Gets an array of required roles of the portlet.
	 *
	 * @return		an array of required roles of the portlet
	 */
	public String[] getRolesArray() {
		return _rolesArray;
	}

	/**
	 * Sets an array of required roles of the portlet.
	 *
	 * @param		rolesArray an array of required roles of the portlet
	 */
	public void setRolesArray(String[] rolesArray) {
		_rolesArray = rolesArray;

		super.setRoles(StringUtil.merge(rolesArray));
	}

	/**
	 * Gets the unlinked roles of the portlet.
	 *
	 * @return		unlinked roles of the portlet
	 */
	public Set getUnlinkedRoles() {
		return _unlinkedRoles;
	}

	/**
	 * Sets the unlinked roles of the portlet.
	 *
	 * @param		unlinkedRoles the unlinked roles of the portlet
	 */
	public void setUnlinkedRoles(Set unlinkedRoles) {
		_unlinkedRoles = unlinkedRoles;
	}

	/**
	 * Gets the role mappers of the portlet.
	 *
	 * @return		role mappers of the portlet
	 */
	public Map getRoleMappers() {
		return _roleMappers;
	}

	/**
	 * Sets the role mappers of the portlet.
	 *
	 * @param		roleMappers the role mappers of the portlet
	 */
	public void setRoleMappers(Map roleMappers) {
		_roleMappers = roleMappers;
	}

	/**
	 * Link the role names set in portlet.xml with the Liferay roles set in
	 * liferay-portlet.xml.
	 */
	public void linkRoles() {
		List linkedRoles = new ArrayList();

		Iterator itr = _unlinkedRoles.iterator();

		while (itr.hasNext()) {
			String unlinkedRole = (String)itr.next();

			String roleLink = (String)_roleMappers.get(unlinkedRole);

			if (Validator.isNotNull(roleLink)) {
				_log.debug(
					"Linking role for portlet [" + getPortletId() +
						"] with role-name [" + unlinkedRole +
							"] to role-link [" + roleLink + "]");

				linkedRoles.add(roleLink);
			}
			else {
				_log.error(
					"Unable to link role for portlet [" + getPortletId() +
						"] with role-name [" + unlinkedRole +
							"] because role-link is null");
			}
		}

		Collections.sort(linkedRoles);

		setRolesArray((String[])linkedRoles.toArray(new String[0]));
	}

	/**
	 * Returns true if the portlet has a role with the specified name.
	 *
	 * @return		true if the portlet has a role with the specified name
	 */
	public boolean hasRoleWithName(String roleName) {
		for (int i = 0; i < _rolesArray.length; i++) {
			if (_rolesArray[i].equalsIgnoreCase(roleName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true if the user has the permission to add the portlet to a
	 * layout.
	 *
	 * @return		true if the user has the permission to add the portlet to a
	 *				layout
	 */
	public boolean hasAddPortletPermission(String userId) {
		try {
			if (_rolesArray.length == 0) {
				return true;
			}

			if (RoleLocalServiceUtil.hasUserRoles(
					userId, getCompanyId(), _rolesArray)) {

				return true;
			}
			else if (RoleLocalServiceUtil.hasUserRole(
						userId, getCompanyId(), Role.ADMINISTRATOR)) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return false;
	}

	/**
	 * Returns true if the portlet is a system portlet that a user cannot
	 * manually add to their page.
	 *
	 * @return		true if the portlet is a system portlet that a user cannot
	 *				manually add to their page
	 */
	public boolean getSystem() {
		return _system;
	}

	/**
	 * Returns true if the portlet is a system portlet that a user cannot
	 * manually add to their page.
	 *
	 * @return		true if the portlet is a system portlet that a user cannot
	 *				manually add to their page
	 */
	public boolean isSystem() {
		return _system;
	}

	/**
	 * Sets to true if the portlet is a system portlet that a user cannot
	 * manually add to their page.
	 *
	 * @param		system boolean value for whether the portlet is a system
	 *				portlet that a user cannot manually add to their page
	 */
	public void setSystem(boolean system) {
		_system = system;
	}

	/**
	 * Returns true to include the portlet and make it available to be made
	 * active.
	 *
	 * @return		true to include the portlet and make it available to be made
	 * 				active
	 */
	public boolean getInclude() {
		return _include;
	}

	/**
	 * Returns true to include the portlet and make it available to be made
	 * active.
	 *
	 * @return		true to include the portlet and make it available to be made
	 * 				active
	 */
	public boolean isInclude() {
		return _include;
	}

	/**
	 * Sets to true to include the portlet and make it available to be made
	 * active.
	 *
	 * @param		include boolean value for whether to include the portlet and
	 * 				make it available to be made active
	 */
	public void setInclude(boolean include) {
		_include = include;
	}

	/**
	 * Gets the init parameters of the portlet.
	 *
	 * @return		init parameters of the portlet
	 */
	public Map getInitParams() {
		return _initParams;
	}

	/**
	 * Sets the init parameters of the portlet.
	 *
	 * @param		initParams the init parameters of the portlet
	 */
	public void setInitParams(Map initParams) {
		_initParams = initParams;
	}

	/**
	 * Gets expiration cache of the portlet.
	 *
	 * @return		expiration cache of the portlet
	 */
	public Integer getExpCache() {
		return _expCache;
	}

	/**
	 * Sets expiration cache of the portlet.
	 *
	 * @param		expCache expiration cache of the portlet
	 */
	public void setExpCache(Integer expCache) {
		_expCache = expCache;
	}

	/**
	 * Gets the portlet modes of the portlet.
	 *
	 * @return		portlet modes of the portlet
	 */
	public Map getPortletModes() {
		return _portletModes;
	}

	/**
	 * Sets the portlet modes of the portlet.
	 *
	 * @param		portletModes the portlet modes of the portlet
	 */
	public void setPortletModes(Map portletModes) {
		_portletModes = portletModes;
	}

	/**
	 * Returns true if the portlet supports the specified mime type and
	 * portlet mode.
	 *
	 * @return		true if the portlet supports the specified mime type and
	 *				portlet mode
	 */
	public boolean hasPortletMode(String mimeType, PortletMode portletMode) {
		if (portletMode.equals(PortletMode.VIEW)) {
			return true;
		}

		if (mimeType == null) {
			mimeType = Constants.TEXT_HTML;
		}

		Set mimeTypeModes = (Set)_portletModes.get(mimeType);

		if (mimeTypeModes == null) {
			return false;
		}

		return mimeTypeModes.contains(portletMode.toString());
	}

	/**
	 * Gets the supported locales of the portlet.
	 *
	 * @return		supported locales of the portlet
	 */
	public Set getSupportedLocales() {
		return _supportedLocales;
	}

	/**
	 * Sets the supported locales of the portlet.
	 *
	 * @param		supportedLocales the supported locales of the portlet
	 */
	public void setSupportedLocales(Set supportedLocales) {
		_supportedLocales = supportedLocales;
	}

	/**
	 * Gets the resource bundle of the portlet.
	 *
	 * @return		resource bundle of the portlet
	 */
	public String getResourceBundle() {
		return _resourceBundle;
	}

	/**
	 * Sets the resource bundle of the portlet.
	 *
	 * @param		resourceBundle the resource bundle of the portlet
	 */
	public void setResourceBundle(String resourceBundle) {
		_resourceBundle = resourceBundle;
	}

	/**
	 * Gets the portlet info of the portlet.
	 *
	 * @return		portlet info of the portlet
	 */
	public PortletInfo getPortletInfo() {
		return _portletInfo;
	}

	/**
	 * Sets the portlet info of the portlet.
	 *
	 * @param		portletInfo the portlet info of the portlet
	 */
	public void setPortletInfo(PortletInfo portletInfo) {
		_portletInfo = portletInfo;
	}

	/**
	 * Gets the user attributes of the portlet.
	 *
	 * @return		user attributes of the portlet
	 */
	public Set getUserAttributes() {
		return _userAttributes;
	}

	/**
	 * Sets the user attributes of the portlet.
	 *
	 * @param		userAttributes the user attributes of the portlet
	 */
	public void setUserAttributes(Set userAttributes) {
		_userAttributes = userAttributes;
	}

	/**
	 * Gets the custom user attributes of the portlet.
	 *
	 * @return		custom user attributes of the portlet
	 */
	public Map getCustomUserAttributes() {
		return _customUserAttributes;
	}

	/**
	 * Sets the custom user attributes of the portlet.
	 *
	 * @param		customUserAttributes the custom user attributes of the
	 *				portlet
	 */
	public void setCustomUserAttributes(Map customUserAttributes) {
		_customUserAttributes = customUserAttributes;
	}

	/**
	 * Returns true if the portlet is found in a WAR file.
	 *
	 * @return		true if the portlet is found in a WAR file
	 */
	public boolean getWARFile() {
		return _warFile;
	}

	/**
	 * Returns true if the portlet is found in a WAR file.
	 *
	 * @return		true if the portlet is found in a WAR file
	 */
	public boolean isWARFile() {
		return _warFile;
	}

	/**
	 * Sets to true if the portlet is found in a WAR file.
	 *
	 * @param		warFile boolean value for whether the portlet is found in a
	 *				WAR file
	 */
	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	/**
	 * Returns true if the portlet is found in a WAR file.
	 *
	 * @param		portletId the cloned instance portlet id
	 * @return		a cloned instance of the portlet
	 */
	public Portlet getClonedInstance(String portletId) {
		if (_clonedInstances == null) {

			// See LEP-528

			return null;
		}

		Portlet clonedInstance = (Portlet)_clonedInstances.get(portletId);

		if (clonedInstance == null) {
			clonedInstance = (Portlet)clone();

			clonedInstance.setPortletId(portletId);

			// Disable caching of cloned instances until we can figure out how
			// to elegantly refresh the cache when the portlet is dynamically
			// updated by the user. For example, the user might change the
			// portlet from narrow to wide. Cloned instances that are cached
			// would not see the new change. We can then also cache static
			// portlet instances.

			//_clonedInstances.put(portletId, clonedInstance);
		}

		return clonedInstance;
	}

	/**
	 * Returns true if the portlet is a static portlet that is cannot be moved.
	 *
	 * @return		true if the portlet is a static portlet that is cannot be
	 *				moved
	 */
	public boolean getStatic() {
		return _staticPortlet;
	}

	/**
	 * Returns true if the portlet is a static portlet that is cannot be moved.
	 *
	 * @return		true if the portlet is a static portlet that is cannot be
	 *				moved
	 */
	public boolean isStatic() {
		return _staticPortlet;
	}

	/**
	 * Sets to true if the portlet is a static portlet that is cannot be moved.
	 *
	 * @param		staticPortlet boolean value for whether the portlet is a
	 *				static portlet that cannot be moved
	 */
	public void setStatic(boolean staticPortlet) {
		_staticPortlet = staticPortlet;
	}

	/**
	 * Returns true if the portlet is a static portlet at the start of a list of
	 * portlets.
	 *
	 * @return		true if the portlet is a static portlet at the start of a
	 *				list of portlets
	 */
	public boolean getStaticStart() {
		return _staticPortletStart;
	}

	/**
	 * Returns true if the portlet is a static portlet at the start of a list of
	 * portlets.
	 *
	 * @return		true if the portlet is a static portlet at the start of a
	 *				list of portlets
	 */
	public boolean isStaticStart() {
		return _staticPortletStart;
	}

	/**
	 * Sets to true if the portlet is a static portlet at the start of a list of
	 * portlets.
	 *
	 * @param		staticPortletStart boolean value for whether the portlet is
	 *				a static portlet at the start of a list of portlets
	 */
	public void setStaticStart(boolean staticPortletStart) {
		_staticPortletStart = staticPortletStart;
	}

	/**
	 * Returns true if the portlet is a static portlet at the end of a list of
	 * portlets.
	 *
	 * @return		true if the portlet is a static portlet at the end of a
	 *				list of portlets
	 */
	public boolean getStaticEnd() {
		return !_staticPortletStart;
	}

	/**
	 * Returns true if the portlet is a static portlet at the end of a list of
	 * portlets.
	 *
	 * @return		true if the portlet is a static portlet at the end of a
	 *				list of portlets
	 */
	public boolean isStaticEnd() {
		return !_staticPortletStart;
	}

	/**
	 * Initialize the portlet instance.
	 */
	public CachePortlet init(PortletConfig portletConfig)
		throws PortletException {

		return init(portletConfig, null);
	}

	/**
	 * Initialize the portlet instance.
	 */
	public CachePortlet init(
			PortletConfig portletConfig, javax.portlet.Portlet portletInstance)
		throws PortletException {

		CachePortlet cachePortlet = null;

		try {
			if (portletInstance == null) {
				portletInstance = (javax.portlet.Portlet)
					Class.forName(getPortletClass()).newInstance();
			}

			cachePortlet = new CachePortlet(
				portletInstance, portletConfig.getPortletContext(),
				getExpCache());

			cachePortlet.init(portletConfig);
		}
		catch (ClassNotFoundException cnofe) {
			throw new UnavailableException(cnofe.getMessage());
		}
		catch (InstantiationException ie) {
			throw new UnavailableException(ie.getMessage());
		}
		catch (IllegalAccessException iae) {
			throw new UnavailableException(iae.getMessage());
		}

		return cachePortlet;
	}

	/**
	 * Creates and returns a copy of this object.
	 *
     * @return		a copy of this object
	 */
	public Object clone() {
		return new Portlet(
			getPortletId(), getCompanyId(), getStrutsPath(),
			getConfigurationPath(), getPortletClass(), getIndexerClass(),
			getSchedulerClass(), getPortletURLClass(),
			getFriendlyURLPluginClass(), getDefaultPreferences(),
			getPreferencesValidator(), isPreferencesCompanyWide(),
			isPreferencesUniquePerLayout(), isPreferencesOwnedByGroup(),
			isUseDefaultTemplate(), isShowPortletAccessDenied(),
			isShowPortletInactive(), isRestoreCurrentView(), isMaximizeEdit(),
			isMaximizeHelp(), isMaximizePrint(), isLayoutCacheable(),
			isInstanceable(), isPrivateRequestAttributes(), isNarrow(),
			getRoles(), getUnlinkedRoles(), getRoleMappers(), isSystem(),
			isActive(), isInclude(), getInitParams(), getExpCache(),
			getPortletModes(), getSupportedLocales(), getResourceBundle(),
			getPortletInfo(), getUserAttributes(), getCustomUserAttributes(),
			isWARFile());
	}

	/**
	 * Compares this portlet to the specified object.
	 *
     * @param		obj the object to compare this portlet against
     * @return		the value 0 if the argument portlet is equal to this
     *				portlet; a value less than -1 if this portlet is less than
     *				the portlet argument; and 1 if this portlet is greater than
     *				the portlet argument
	 */
	public int compareTo(Object obj) {
		Portlet portlet = (Portlet)obj;

		return getPortletId().compareTo(portlet.getPortletId());
	}

	/**
	 * Log instance for this class.
	 */
	private static Log _log = LogFactory.getLog(Portlet.class);

	/**
	 * The struts path of the portlet.
	 */
	private String _strutsPath;

	/**
	 * The configuration path of the portlet.
	 */
	private String _configurationPath;

	/**
	 * The name of the portlet class of the portlet.
	 */
	private String _portletClass;

	/**
	 * The name of the indexer class of the portlet.
	 */
	private String _indexerClass;

	/**
	 * The name of the scheduler class of the portlet.
	 */
	private String _schedulerClass;

	/**
	 * The name of the portlet URL class of the portlet.
	 */
	private String _portletURLClass;

	/**
	 * The name of the friendly URL plugin class of the portlet.
	 */
	private String _friendlyURLPluginClass;

	/**
	 * The default preferences of the portlet.
	 */
	private String _defaultPreferences;

	/**
	 * The name of the preferences validator class of the portlet.
	 */
	private String _prefsValidator;

	/**
	 * True if preferences are shared across the entire company.
	 */
	private boolean _prefsCompanyWide;

	/**
	 * True if preferences are unique per layout.
	 */
	private boolean _prefsUniquePerLayout = true;

	/**
	 * True if preferences are owned by the group when the portlet is shown in a
	 * group layout. False if preferences are owned by the user at all times.
	 */
	private boolean _prefsOwnedByGroup = true;

	/**
	 * True if the portlet uses the default template.
     */
	private boolean _useDefaultTemplate = true;

	/**
	 * True if users are shown that they do not have access to the portlet.
     */
	private boolean _showPortletAccessDenied = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_ACCESS_DENIED));

	/**
	 * True if users are shown that the portlet is inactive.
     */
	private boolean _showPortletInactive = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAYOUT_SHOW_PORTLET_INACTIVE));

	/**
	 * True if the portlet restores to the current view from the maximized
	 * state.
     */
	private boolean _restoreCurrentView = true;

	/**
	 * True if the portlet goes into the maximized state when the user goes into
	 * the edit mode.
     */
	private boolean _maximizeEdit;

	/**
	 * True if the portlet goes into the maximized state when the user goes into
	 * the help mode.
     */
	private boolean _maximizeHelp;

	/**
	 * True if the portlet goes into the maximized state when the user goes into
	 * the print mode.
     */
	private boolean _maximizePrint;

	/**
	 * False since most portlets should not be layout cacheable.
	 */
	private boolean _layoutCacheable = false;

	/**
	 * True if the portlet can be added multiple times to a layout.
     */
	private boolean _instanceable;

	/**
	 * True if the portlet does not share request attributes with any other
	 * portlet.
     */
	private boolean _privateRequestAttributes = true;

	/**
	 * An array of required roles of the portlet.
	 */
	private String[] _rolesArray;

	/**
	 * The unlinked roles of the portlet.
	 */
	private Set _unlinkedRoles;

	/**
	 * The role mappers of the portlet.
	 */
	private Map _roleMappers;

	/**
	 * True if the portlet is a system portlet that a user cannot manually add
	 * to their page.
	 */
	private boolean _system;

	/**
	 * True to include the portlet and make it available to be made active.
	 */
	private boolean _include = true;

	/**
	 * The init parameters of the portlet.
	 */
	private Map _initParams;

	/**
	 * The expiration cache of the portlet.
	 */
	private Integer _expCache;

	/**
	 * The portlet modes of the portlet.
	 */
	private Map _portletModes;

	/**
	 * The supported locales of the portlet.
	 */
	private Set _supportedLocales;

	/**
	 * The resource bundle of the portlet.
	 */
	private String _resourceBundle;

	/**
	 * The portlet info of the portlet.
	 */
	private PortletInfo _portletInfo;

	/**
	 * The user attributes of the portlet.
	 */
	private Set _userAttributes;

	/**
	 * The custom user attributes of the portlet.
	 */
	private Map _customUserAttributes;

	/**
	 * True if the portlet is found in a WAR file.
	 */
	private boolean _warFile;

	/**
	 * The cloned instances of the portlet.
	 */
	private Map _clonedInstances;

	/**
	 * True if the portlet is a static portlet that is cannot be moved.
	 */
	private boolean _staticPortlet;

	/**
	 * True if the portlet is a static portlet at the start of a list of
	 * portlets.
	 */
	private boolean _staticPortletStart;

}