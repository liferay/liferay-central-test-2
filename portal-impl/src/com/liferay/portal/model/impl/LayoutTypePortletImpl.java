/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.germinus.easyconf.Filter;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.impl.LayoutTemplateLocalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.PwdGenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutTypePortletImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutTypePortletImpl
	extends LayoutTypeImpl implements LayoutTypePortlet {

	public static final String LAYOUT_TEMPLATE_ID = "layout-template-id";

	public static final String STATE_MAX = "state-max";

	public static final String STATE_MAX_PREVIOUS = "state-max-previous";

	public static final String STATE_MIN = "state-min";

	public static final String MODE_ABOUT = "mode-about";

	public static final String MODE_CONFIG = "mode-config";

	public static final String MODE_EDIT = "mode-edit";

	public static final String MODE_EDIT_DEFAULTS = "mode-edit-defaults";

	public static final String MODE_EDIT_GUEST = "mode-edit-guest";

	public static final String MODE_HELP = "mode-help";

	public static final String MODE_PREVIEW = "mode-preview";

	public static final String MODE_PRINT = "mode-print";

	public static String getFullInstanceSeparator() {
		String instanceId = PwdGenerator.getPassword(
			PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3, 4);

		return PortletImpl.INSTANCE_SEPARATOR + instanceId;
	}

	public LayoutTypePortletImpl(LayoutImpl layout) {
		super(layout);
	}

	public LayoutTemplate getLayoutTemplate() {
		LayoutTemplate layoutTemplate =
			LayoutTemplateLocalUtil.getLayoutTemplate(
				getLayoutTemplateId(), false, null);

		if (layoutTemplate == null) {
			layoutTemplate = new LayoutTemplateImpl(
				StringPool.BLANK, StringPool.BLANK);

			List columns = new ArrayList();

			for (int i = 1; i <= 10; i++) {
				columns.add("column-" + i);
			}

			layoutTemplate.setColumns(columns);
		}

		return layoutTemplate;
	}

	public String getLayoutTemplateId() {
		String layoutTemplateId =
			getTypeSettingsProperties().getProperty(LAYOUT_TEMPLATE_ID);

		if (Validator.isNull(layoutTemplateId)) {
			layoutTemplateId = "2_columns_ii";

			getTypeSettingsProperties().setProperty(
				LAYOUT_TEMPLATE_ID, layoutTemplateId);

			_log.warn(
				"Layout template id for layout " + getLayout().getPrimaryKey() +
					" is null, setting it to 2_columns_ii");
		}

		return layoutTemplateId;
	}

	public void setLayoutTemplateId(long userId, String newLayoutTemplateId) {
		setLayoutTemplateId(userId, newLayoutTemplateId, true);
	}

	public void setLayoutTemplateId(
		long userId, String newLayoutTemplateId, boolean checkPermission) {

		if (checkPermission &&
			!PluginSettingLocalServiceUtil.hasPermission(
				userId, newLayoutTemplateId, LayoutTemplateImpl.PLUGIN_TYPE)) {

			return;
		}

		String oldLayoutTemplateId = getLayoutTemplateId();

		getTypeSettingsProperties().setProperty(
			LAYOUT_TEMPLATE_ID, newLayoutTemplateId);

		if (Validator.isNull(oldLayoutTemplateId)) {
			return;
		}

		LayoutTemplate oldLayoutTemplate =
			LayoutTemplateLocalUtil.getLayoutTemplate(
				oldLayoutTemplateId, false, null);

		if (oldLayoutTemplate == null) {
			return;
		}

		LayoutTemplate newLayoutTemplate =
			LayoutTemplateLocalUtil.getLayoutTemplate(
				newLayoutTemplateId, false, null);

		List oldColumns = oldLayoutTemplate.getColumns();
		List newColumns = newLayoutTemplate.getColumns();

		if (newColumns.size() < oldColumns.size()) {
			String lastNewColumnId =
				(String)newColumns.get(newColumns.size() - 1);
			String lastNewColumnValue =
				getTypeSettingsProperties().getProperty(lastNewColumnId);

			for (int i = newColumns.size() - 1; i < oldColumns.size(); i++) {
				String oldColumnId = (String)oldColumns.get(i);
				String oldColumnValue =
					(String)getTypeSettingsProperties().remove(oldColumnId);

				String[] portletIds = StringUtil.split(oldColumnValue);

				for (int j = 0; j < portletIds.length; j++) {
					lastNewColumnValue =
						StringUtil.add(lastNewColumnValue, portletIds[j]);
				}
			}

			getTypeSettingsProperties().setProperty(
				lastNewColumnId, lastNewColumnValue);
		}
	}

	public int getNumOfColumns() {
		return getLayoutTemplate().getColumns().size();
	}

	public List getAllPortlets(String columnId) throws SystemException {
		String columnValue =
			getTypeSettingsProperties().getProperty(columnId);

		String[] portletIds = StringUtil.split(columnValue);

		List portlets = new ArrayList(portletIds.length);

		for (int i = 0; i < portletIds.length; i++) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getLayout().getCompanyId(), portletIds[i]);

			if (portlet != null) {
				portlets.add(portlet);
			}
		}

		List startPortlets = _getStaticPortlets(
			PropsUtil.LAYOUT_STATIC_PORTLETS_START + columnId);

		List endPortlets = _getStaticPortlets(
			PropsUtil.LAYOUT_STATIC_PORTLETS_END + columnId);

		return addStaticPortlets(portlets, startPortlets, endPortlets);
	}

	public List addStaticPortlets(
			List portlets, List startPortlets, List endPortlets)
		throws SystemException {

		// Return the original array of portlets if no static portlets are
		// specified

		if (startPortlets == null) {
			startPortlets = new ArrayList();
		}

		if (endPortlets == null) {
			endPortlets = new ArrayList();
		}

		if ((startPortlets.isEmpty()) && (endPortlets.isEmpty())) {
			return portlets;
		}

		// New array of portlets that contain the static portlets

		List list = new ArrayList(
			portlets.size() + startPortlets.size() + endPortlets.size());

		if (startPortlets != null) {
			list.addAll(startPortlets);
		}

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = (Portlet)portlets.get(i);

			// Add the portlet if and only if it is not also a static portlet

			if (!startPortlets.contains(portlet) &&
				!endPortlets.contains(portlet)) {

				list.add(portlet);
			}
		}

		if (endPortlets != null) {
			list.addAll(endPortlets);
		}

		return list;
	}

	// Modify portlets

	public String addPortletId(long userId, String portletId) {
		return addPortletId(userId, portletId, true);
	}

	public String addPortletId(
		long userId, String portletId, boolean checkPermission) {

		return addPortletId(userId, portletId, null, -1, checkPermission);
	}

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos) {

		return addPortletId(userId, portletId, columnId, columnPos, true);
	}

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos,
		boolean checkPermission) {

		Portlet portlet = null;

		try {
			portlet = PortletLocalServiceUtil.getPortletById(
				getLayout().getCompanyId(), portletId);

			if (checkPermission && !portlet.hasAddPortletPermission(userId)) {
				return null;
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (portlet != null) {
			if (portlet.isSystem()) {
				return null;
			}

			if ((portlet.isInstanceable()) &&
				(PortletImpl.getInstanceId(portlet.getPortletId()) == null)) {

				portletId = portletId + getFullInstanceSeparator();
			}

			if (hasPortletId(portletId)) {
				return null;
			}

			if (columnId == null) {
				LayoutTemplate layoutTemplate = getLayoutTemplate();

				List columns = layoutTemplate.getColumns();

				if (columns.size() > 0) {
					columnId = (String)columns.get(0);
				}
			}

			if (columnId != null) {
				String columnValue =
					getTypeSettingsProperties().getProperty(columnId);

				if (columnPos >= 0) {
					List portletIds =
						ListUtil.fromArray(StringUtil.split(columnValue));

					if (columnPos <= portletIds.size()) {
						portletIds.add(columnPos, portletId);

						columnValue = StringUtil.merge(portletIds);
					}
				}
				else {
					columnValue = StringUtil.add(columnValue, portletId);
				}

				getTypeSettingsProperties().setProperty(columnId, columnValue);
			}

			return portletId;
		}
		else {
			return null;
		}
	}

	public void addPortletIds(
		long userId, String[] portletIds, boolean checkPermission) {

		for (int i = 0; i < portletIds.length; i++) {
			String portletId = portletIds[i];

			addPortletId(userId, portletId, checkPermission);
		}
	}

	public void addPortletIds(
		long userId, String[] portletIds, String columnId,
		boolean checkPermission) {

		for (int i = 0; i < portletIds.length; i++) {
			String portletId = portletIds[i];

			addPortletId(userId, portletId, columnId, -1, checkPermission);
		}
	}

	public List getPortlets() throws SystemException {
		List portletIds = getPortletIds();

		List portlets = new ArrayList(portletIds.size());

		for (int i = 0; i < portletIds.size(); i++) {
			String portletId = (String)portletIds.get(i);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getLayout().getCompanyId(), portletId);

			if (portlet != null) {
				portlets.add(portlet);
			}
		}

		return portlets;
	}

	public List getPortletIds() {
		List portletIds = new ArrayList();

		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = (String)columns.get(i);

			String columnValue =
				getTypeSettingsProperties().getProperty(columnId);

			portletIds.addAll(
				ListUtil.fromArray(StringUtil.split(columnValue)));

		}

		return portletIds;
	}

	public boolean hasPortletId(String portletId) {
		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = (String)columns.get(i);

			if (_hasNonstaticPortletId(columnId, portletId)) {
				return true;
			}

			if (_hasStaticPortletId(columnId, portletId)) {
				return true;
			}
		}

		return false;
	}

	public void movePortletId(
		long userId, String portletId, String columnId, int columnPos) {

		removePortletId(portletId, false);
		addPortletId(userId, portletId, columnId, columnPos);
	}

	public void removePortletId(String portletId) {
		removePortletId(portletId, true);
	}

	public void removePortletId(String portletId, boolean modeAndState) {
		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = (String)columns.get(i);

			String columnValue =
				getTypeSettingsProperties().getProperty(columnId);

			columnValue = StringUtil.remove(columnValue, portletId);

			getTypeSettingsProperties().setProperty(columnId, columnValue);
		}

		if (modeAndState) {
			removeStatesPortletId(portletId);
			removeModesPortletId(portletId);
		}
	}

	public void setPortletIds(String columnId, String portletIds) {
		getTypeSettingsProperties().setProperty(columnId, portletIds);
	}

	// Maximized state

	public String getStateMax() {
		return getTypeSettingsProperties().getProperty(STATE_MAX);
	}

	public void setStateMax(String stateMax) {
		getTypeSettingsProperties().setProperty(STATE_MAX, stateMax);
	}

	public boolean hasStateMax() {
		String[] stateMax = StringUtil.split(getStateMax());

		if (stateMax.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addStateMaxPortletId(String portletId) {
		removeStatesPortletId(portletId);
		//setStateMax(StringUtil.add(getStateMax(), portletId));
		setStateMax(StringUtil.add(StringPool.BLANK, portletId));
	}

	public String getStateMaxPortletId() {
		String[] stateMax = StringUtil.split(getStateMax());

		if (stateMax.length > 0) {
			return stateMax[0];
		}
		else {
			return StringPool.BLANK;
		}
	}

	public boolean hasStateMaxPortletId(String portletId) {
		if (StringUtil.contains(getStateMax(), portletId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void removeStateMaxPortletId(String portletId) {
		setStateMax(StringUtil.remove(getStateMax(), portletId));
	}

	// Maximized state previous

	public String getStateMaxPrevious() {
		return getTypeSettingsProperties().getProperty(STATE_MAX_PREVIOUS);
	}

	public void setStateMaxPrevious(String stateMaxPrevious) {
		getTypeSettingsProperties().setProperty(
			STATE_MAX_PREVIOUS, stateMaxPrevious);
	}

	public void removeStateMaxPrevious() {
		getTypeSettingsProperties().remove(STATE_MAX_PREVIOUS);
	}

	// Minimized state

	public String getStateMin() {
		return getTypeSettingsProperties().getProperty(STATE_MIN);
	}

	public void setStateMin(String stateMin) {
		getTypeSettingsProperties().setProperty(STATE_MIN, stateMin);
	}

	public boolean hasStateMin() {
		String[] stateMin = StringUtil.split(getStateMin());

		if (stateMin.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addStateMinPortletId(String portletId) {
		removeStateMaxPortletId(portletId);
		setStateMin(StringUtil.add(getStateMin(), portletId));
	}

	public boolean hasStateMinPortletId(String portletId) {
		if (StringUtil.contains(getStateMin(), portletId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void removeStateMinPortletId(String portletId) {
		setStateMin(StringUtil.remove(getStateMin(), portletId));
	}

	// Normal state

	public boolean hasStateNormalPortletId(String portletId) {
		if (hasStateMaxPortletId(portletId) ||
			hasStateMinPortletId(portletId)) {

			return false;
		}
		else {
			return true;
		}
	}

	// All states

	public void resetStates() {
		setStateMax(StringPool.BLANK);
		setStateMin(StringPool.BLANK);
	}

	public void removeStatesPortletId(String portletId) {
		removeStateMaxPortletId(portletId);
		removeStateMinPortletId(portletId);
	}

	// About mode

	public String getModeAbout() {
		return getTypeSettingsProperties().getProperty(MODE_ABOUT);
	}

	public void setModeAbout(String modeAbout) {
		getTypeSettingsProperties().setProperty(MODE_ABOUT, modeAbout);
	}

	public void addModeAboutPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeAbout(StringUtil.add(getModeAbout(), portletId));
	}

	public boolean hasModeAboutPortletId(String portletId) {
		return StringUtil.contains(getModeAbout(), portletId);
	}

	public void removeModeAboutPortletId(String portletId) {
		setModeAbout(StringUtil.remove(getModeAbout(), portletId));
	}

	// Config mode

	public String getModeConfig() {
		return getTypeSettingsProperties().getProperty(MODE_CONFIG);
	}

	public void setModeConfig(String modeConfig) {
		getTypeSettingsProperties().setProperty(MODE_CONFIG, modeConfig);
	}

	public void addModeConfigPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeConfig(StringUtil.add(getModeConfig(), portletId));
	}

	public boolean hasModeConfigPortletId(String portletId) {
		return StringUtil.contains(getModeConfig(), portletId);
	}

	public void removeModeConfigPortletId(String portletId) {
		setModeConfig(StringUtil.remove(getModeConfig(), portletId));
	}

	// Edit mode

	public String getModeEdit() {
		return getTypeSettingsProperties().getProperty(MODE_EDIT);
	}

	public void setModeEdit(String modeEdit) {
		getTypeSettingsProperties().setProperty(MODE_EDIT, modeEdit);
	}

	public void addModeEditPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEdit(StringUtil.add(getModeEdit(), portletId));
	}

	public boolean hasModeEditPortletId(String portletId) {
		return StringUtil.contains(getModeEdit(), portletId);
	}

	public void removeModeEditPortletId(String portletId) {
		setModeEdit(StringUtil.remove(getModeEdit(), portletId));
	}

	// Edit defaults mode

	public String getModeEditDefaults() {
		return getTypeSettingsProperties().getProperty(MODE_EDIT_DEFAULTS);
	}

	public void setModeEditDefaults(String modeEditDefaults) {
		getTypeSettingsProperties().setProperty(
			MODE_EDIT_DEFAULTS, modeEditDefaults);
	}

	public void addModeEditDefaultsPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEditDefaults(StringUtil.add(getModeEditDefaults(), portletId));
	}

	public boolean hasModeEditDefaultsPortletId(String portletId) {
		return StringUtil.contains(getModeEditDefaults(), portletId);
	}

	public void removeModeEditDefaultsPortletId(String portletId) {
		setModeEditDefaults(
			StringUtil.remove(getModeEditDefaults(), portletId));
	}

	// Edit guest mode

	public String getModeEditGuest() {
		return getTypeSettingsProperties().getProperty(MODE_EDIT_GUEST);
	}

	public void setModeEditGuest(String modeEditGuest) {
		getTypeSettingsProperties().setProperty(MODE_EDIT_GUEST, modeEditGuest);
	}

	public void addModeEditGuestPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEditGuest(StringUtil.add(getModeEditGuest(), portletId));
	}

	public boolean hasModeEditGuestPortletId(String portletId) {
		return StringUtil.contains(getModeEditGuest(), portletId);
	}

	public void removeModeEditGuestPortletId(String portletId) {
		setModeEditGuest(StringUtil.remove(getModeEditGuest(), portletId));
	}

	// Help mode

	public String getModeHelp() {
		return getTypeSettingsProperties().getProperty(MODE_HELP);
	}

	public void setModeHelp(String modeHelp) {
		getTypeSettingsProperties().setProperty(MODE_HELP, modeHelp);
	}

	public void addModeHelpPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeHelp(StringUtil.add(getModeHelp(), portletId));
	}

	public boolean hasModeHelpPortletId(String portletId) {
		return StringUtil.contains(getModeHelp(), portletId);
	}

	public void removeModeHelpPortletId(String portletId) {
		setModeHelp(StringUtil.remove(getModeHelp(), portletId));
	}

	// Preview mode

	public String getModePreview() {
		return getTypeSettingsProperties().getProperty(MODE_PREVIEW);
	}

	public void setModePreview(String modePreview) {
		getTypeSettingsProperties().setProperty(MODE_PREVIEW, modePreview);
	}

	public void addModePreviewPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModePreview(StringUtil.add(getModePreview(), portletId));
	}

	public boolean hasModePreviewPortletId(String portletId) {
		return StringUtil.contains(getModePreview(), portletId);
	}

	public void removeModePreviewPortletId(String portletId) {
		setModePreview(StringUtil.remove(getModePreview(), portletId));
	}

	// Print mode

	public String getModePrint() {
		return getTypeSettingsProperties().getProperty(MODE_PRINT);
	}

	public void setModePrint(String modePrint) {
		getTypeSettingsProperties().setProperty(MODE_PRINT, modePrint);
	}

	public void addModePrintPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModePrint(StringUtil.add(getModePrint(), portletId));
	}

	public boolean hasModePrintPortletId(String portletId) {
		return StringUtil.contains(getModePrint(), portletId);
	}

	public void removeModePrintPortletId(String portletId) {
		setModePrint(StringUtil.remove(getModePrint(), portletId));
	}

	// View mode

	public boolean hasModeViewPortletId(String portletId) {
		if (hasModeAboutPortletId(portletId) ||
			hasModeConfigPortletId(portletId) ||
			hasModeEditPortletId(portletId) ||
			hasModeEditDefaultsPortletId(portletId) ||
			hasModeEditGuestPortletId(portletId) ||
			hasModeHelpPortletId(portletId) ||
			hasModePreviewPortletId(portletId) ||
			hasModePrintPortletId(portletId)) {

			return false;
		}
		else {
			return true;
		}
	}

	// All modes

	public void resetModes() {
		setModeAbout(StringPool.BLANK);
		setModeConfig(StringPool.BLANK);
		setModeEdit(StringPool.BLANK);
		setModeEditDefaults(StringPool.BLANK);
		setModeEditGuest(StringPool.BLANK);
		setModeHelp(StringPool.BLANK);
		setModePreview(StringPool.BLANK);
		setModePrint(StringPool.BLANK);
	}

	public void removeModesPortletId(String portletId) {
		removeModeAboutPortletId(portletId);
		removeModeConfigPortletId(portletId);
		removeModeEditPortletId(portletId);
		removeModeEditDefaultsPortletId(portletId);
		removeModeEditGuestPortletId(portletId);
		removeModeHelpPortletId(portletId);
		removeModePreviewPortletId(portletId);
		removeModePrintPortletId(portletId);
	}

	// Private methods

	private String[] _getStaticPortletIds(String position) {
		Layout layout = getLayout();

		String selector1 = StringPool.BLANK;

		Group group = layout.getGroup();

		if (group.isUser()) {
			selector1 = "user";
		}
		else if (group.isCommunity()) {
			selector1 = "community";
		}
		else if (group.isOrganization()) {
			selector1 = "organization";
		}

		String selector2 = StringPool.BLANK;

		if (layout.isFirstParent()) {
			selector2 = "firstLayout";
		}

		return PropsUtil.getComponentProperties().getStringArray(
			position, Filter.by(selector1, selector2));
	}

	private List _getStaticPortlets(String position) throws SystemException {
		String[] portletIds = _getStaticPortletIds(position);

		List portlets = new ArrayList();

		for (int i = 0; i < portletIds.length; i++) {
			String portletId = portletIds[i];

			if (_hasNonstaticPortletId(portletId)) {
				continue;
			}

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getLayout().getCompanyId(), portletId);

			if (portlet != null) {
				Portlet staticPortlet = portlet;

				if (portlet.isInstanceable()) {

					// Instanceable portlets do not need to be cloned because
					// they are already cloned. See the method getPortletById in
					// the class PortletLocalServiceImpl and how it references
					// the method getClonedInstance in the class PortletImpl.

				}
				else {
					staticPortlet = (Portlet)staticPortlet.clone();
				}

				staticPortlet.setStatic(true);

				if (position.startsWith("layout.static.portlets.start")) {
					staticPortlet.setStaticStart(true);
				}

				portlets.add(staticPortlet);
			}
		}

		return portlets;
	}

	private boolean _hasNonstaticPortletId(String portletId) {
		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = (String)columns.get(i);

			if (_hasNonstaticPortletId(columnId, portletId)) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasNonstaticPortletId(String columnId, String portletId) {
		String columnValue = getTypeSettingsProperties().getProperty(columnId);

		if (StringUtil.contains(columnValue, portletId)) {
			return true;
		}
		else {
			return false;
		}
	}

	/*private boolean _hasStaticPortletId(String portletId) {
		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = (String)columns.get(i);

			if (_hasStaticPortletId(columnId, portletId)) {
				return true;
			}
		}

		return false;
	}*/

	private boolean _hasStaticPortletId(String columnId, String portletId) {
		String[] staticPortletIdsStart = _getStaticPortletIds(
			PropsUtil.LAYOUT_STATIC_PORTLETS_START + columnId);

		String[] staticPortletIdsEnd = _getStaticPortletIds(
			PropsUtil.LAYOUT_STATIC_PORTLETS_END + columnId);

		String[] staticPortletIds = ArrayUtil.append(
			staticPortletIdsStart, staticPortletIdsEnd);

		for (int i = 0; i < staticPortletIds.length; i++) {
			String staticPortletId = staticPortletIds[i];

			if (staticPortletId.equals(portletId)) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactory.getLog(LayoutTypePortletImpl.class);

}