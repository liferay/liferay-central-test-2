/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="PortletDisplay.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletDisplay implements Serializable {

	public PortletDisplay() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		_id = id;
	}

	public String getResourcePK() {
		return _resourcePK;
	}

	public void setResourcePK(String resourcePK) {
		_resourcePK = resourcePK;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public boolean isAccess() {
		return _access;
	}

	public void setAccess(boolean access) {
		_access = access;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getColumnId() {
		return _columnId;
	}

	public void setColumnId(String columnId) {
		_columnId = columnId;
	}

	public int getColumnPos() {
		return _columnPos;
	}

	public void setColumnPos(int columnPos) {
		_columnPos = columnPos;
	}

	public int getColumnCount() {
		return _columnCount;
	}

	public void setColumnCount(int columnCount) {
		_columnCount = columnCount;
	}

	public boolean isStateExclusive() {
		return _stateExclusive;
	}

	public void setStateExclusive(boolean stateExclusive) {
		_stateExclusive = stateExclusive;
	}

	public boolean isStateMax() {
		return _stateMax;
	}

	public void setStateMax(boolean stateMax) {
		_stateMax = stateMax;
	}

	public boolean isStateMin() {
		return _stateMin;
	}

	public void setStateMin(boolean stateMin) {
		_stateMin = stateMin;
	}

	public boolean isStatePopUp() {
		return _statePopUp;
	}

	public void setStatePopUp(boolean statePopUp) {
		_statePopUp = statePopUp;
	}

	public boolean isModeAbout() {
		return _modeAbout;
	}

	public void setModeAbout(boolean modeAbout) {
		_modeAbout = modeAbout;
	}

	public boolean isModeConfig() {
		return _modeConfig;
	}

	public void setModeConfig(boolean modeConfig) {
		_modeConfig = modeConfig;
	}

	public boolean isModeEdit() {
		return _modeEdit;
	}

	public void setModeEdit(boolean modeEdit) {
		_modeEdit = modeEdit;
	}

	public boolean isModeEditDefaults() {
		return _modeEditDefaults;
	}

	public void setModeEditDefaults(boolean modeEditDefaults) {
		_modeEditDefaults = modeEditDefaults;
	}

	public boolean isModeEditGuest() {
		return _modeEditGuest;
	}

	public void setModeEditGuest(boolean modeEditGuest) {
		_modeEditGuest = modeEditGuest;
	}

	public boolean isModeHelp() {
		return _modeHelp;
	}

	public void setModeHelp(boolean modeHelp) {
		_modeHelp = modeHelp;
	}

	public boolean isModePreview() {
		return _modePreview;
	}

	public void setModePreview(boolean modePreview) {
		_modePreview = modePreview;
	}

	public boolean isModePrint() {
		return _modePrint;
	}

	public void setModePrint(boolean modePrint) {
		_modePrint = modePrint;
	}

	public boolean isShowBackIcon() {
		return _showBackIcon;
	}

	public void setShowBackIcon(boolean showBackIcon) {
		_showBackIcon = showBackIcon;
	}

	public boolean isShowCloseIcon() {
		return _showCloseIcon;
	}

	public void setShowCloseIcon(boolean showCloseIcon) {
		_showCloseIcon = showCloseIcon;
	}

	public boolean isShowConfigurationIcon() {
		return _showConfigurationIcon;
	}

	public void setShowConfigurationIcon(boolean showConfigurationIcon) {
		_showConfigurationIcon = showConfigurationIcon;
	}

	public boolean isShowEditIcon() {
		return _showEditIcon;
	}

	public void setShowEditIcon(boolean showEditIcon) {
		_showEditIcon = showEditIcon;
	}

	public boolean isShowEditGuestIcon() {
		return _showEditGuestIcon;
	}

	public void setShowEditGuestIcon(boolean showEditGuestIcon) {
		_showEditGuestIcon = showEditGuestIcon;
	}

	public boolean isShowHelpIcon() {
		return _showHelpIcon;
	}

	public void setShowHelpIcon(boolean showHelpIcon) {
		_showHelpIcon = showHelpIcon;
	}

	public boolean isShowMaxIcon() {
		return _showMaxIcon;
	}

	public void setShowMaxIcon(boolean showMaxIcon) {
		_showMaxIcon = showMaxIcon;
	}

	public boolean isShowMinIcon() {
		return _showMinIcon;
	}

	public void setShowMinIcon(boolean showMinIcon) {
		_showMinIcon = showMinIcon;
	}

	public boolean isShowMoveIcon() {
		return _showMoveIcon;
	}

	public void setShowMoveIcon(boolean showMoveIcon) {
		_showMoveIcon = showMoveIcon;
	}

	public boolean isShowPrintIcon() {
		return _showPrintIcon;
	}

	public void setShowPrintIcon(boolean showPrintIcon) {
		_showPrintIcon = showPrintIcon;
	}

	public String getURLBack() {
		return _urlBack;
	}

	public void setURLBack(String urlBack) {
		_urlBack = urlBack;
	}

	public String getURLClose() {
		return _urlClose;
	}

	public void setURLClose(String urlClose) {
		_urlClose = urlClose;
	}

	public String getURLConfiguration() {
		return _urlConfiguration;
	}

	public void setURLConfiguration(String urlConfiguration) {
		_urlConfiguration = urlConfiguration;
	}

	public String getURLEdit() {
		return _urlEdit;
	}

	public void setURLEdit(String urlEdit) {
		_urlEdit = urlEdit;
	}

	public String getURLEditGuest() {
		return _urlEditGuest;
	}

	public void setURLEditGuest(String urlEditGuest) {
		_urlEditGuest = urlEditGuest;
	}

	public String getURLHelp() {
		return _urlHelp;
	}

	public void setURLHelp(String urlHelp) {
		_urlHelp = urlHelp;
	}

	public String getURLMax() {
		return _urlMax;
	}

	public void setURLMax(String urlMax) {
		_urlMax = urlMax;
	}

	public String getURLMin() {
		return _urlMin;
	}

	public void setURLMin(String urlMin) {
		_urlMin = urlMin;
	}

	public String getURLPrint() {
		return _urlPrint;
	}

	public void setURLPrint(String urlPrint) {
		_urlPrint = urlPrint;
	}

	public boolean isRestoreCurrentView() {
		return _restoreCurrentView;
	}

	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_restoreCurrentView = restoreCurrentView;
	}

	public void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		_id = StringPool.BLANK;
		_resourcePK = null;
		_namespace = null;
		_title = null;
		_access = false;
		_active = false;
		_columnId = null;
		_stateExclusive = false;
		_stateMax = false;
		_stateMin = false;
		_statePopUp = false;
		_modeAbout = false;
		_modeConfig = false;
		_modeEdit = false;
		_modeEditDefaults = false;
		_modeEditGuest = false;
		_modeHelp = false;
		_modePreview = false;
		_modePrint = false;
		_showBackIcon = false;
		_showCloseIcon = false;
		_showConfigurationIcon = false;
		_showEditIcon = false;
		_showEditGuestIcon = false;
		_showHelpIcon = false;
		_showMaxIcon = false;
		_showMinIcon = false;
		_showMoveIcon = false;
		_showPrintIcon = false;
		_urlBack = null;
		_urlClose = null;
		_urlConfiguration = null;
		_urlEdit = null;
		_urlEditGuest = null;
		_urlHelp = null;
		_urlMax = null;
		_urlMin = null;
		_urlPrint = null;
		_restoreCurrentView = false;
	}

	public void copyFrom(PortletDisplay master) {
		_id = master.getId();
		_resourcePK = master.getResourcePK();
		_namespace = master.getNamespace();
		_title = master.getTitle();
		_access = master.isAccess();
		_active = master.isActive();
		_columnId = master.getColumnId();
		_stateExclusive = master.isStateExclusive();
		_stateMax = master.isStateMax();
		_stateMin = master.isStateMin();
		_statePopUp = master.isStatePopUp();
		_modeAbout = master.isModeAbout();
		_modeConfig = master.isModeConfig();
		_modeEdit = master.isModeEdit();
		_modeEditDefaults = master.isModeEditDefaults();
		_modeEditGuest = master.isModeEditGuest();
		_modeHelp = master.isModeHelp();
		_modePreview = master.isModePreview();
		_modePrint = master.isModePrint();
		_showBackIcon = master.isShowBackIcon();
		_showCloseIcon = master.isShowCloseIcon();
		_showConfigurationIcon = master.isShowConfigurationIcon();
		_showEditIcon = master.isShowEditIcon();
		_showEditGuestIcon = master.isShowEditGuestIcon();
		_showHelpIcon = master.isShowHelpIcon();
		_showMaxIcon = master.isShowMaxIcon();
		_showMinIcon = master.isShowMinIcon();
		_showMoveIcon = master.isShowMoveIcon();
		_showPrintIcon = master.isShowPrintIcon();
		_urlBack = master.getURLBack();
		_urlClose = master.getURLClose();
		_urlConfiguration = master.getURLConfiguration();
		_urlEdit = master.getURLEdit();
		_urlEditGuest = master.getURLEditGuest();
		_urlHelp = master.getURLHelp();
		_urlMax = master.getURLMax();
		_urlMin = master.getURLMin();
		_urlPrint = master.getURLPrint();
		_restoreCurrentView = master.isRestoreCurrentView();
	}

	public void copyTo(PortletDisplay slave) {
		slave.setId(_id);
		slave.setResourcePK(_resourcePK);
		slave.setNamespace(_namespace);
		slave.setTitle(_title);
		slave.setAccess(_access);
		slave.setActive(_active);
		slave.setColumnId(_columnId);
		slave.setStateExclusive(_stateExclusive);
		slave.setStateMax(_stateMax);
		slave.setStateMin(_stateMin);
		slave.setStatePopUp(_statePopUp);
		slave.setModeAbout(_modeAbout);
		slave.setModeConfig(_modeConfig);
		slave.setModeEdit(_modeEdit);
		slave.setModeEditDefaults(_modeEditDefaults);
		slave.setModeEditGuest(_modeEditGuest);
		slave.setModeHelp(_modeHelp);
		slave.setModePreview(_modePreview);
		slave.setModePrint(_modePrint);
		slave.setShowBackIcon(_showBackIcon);
		slave.setShowCloseIcon(_showCloseIcon);
		slave.setShowConfigurationIcon(_showConfigurationIcon);
		slave.setShowEditIcon(_showEditIcon);
		slave.setShowEditGuestIcon(_showEditGuestIcon);
		slave.setShowHelpIcon(_showHelpIcon);
		slave.setShowMaxIcon(_showMaxIcon);
		slave.setShowMinIcon(_showMinIcon);
		slave.setShowMoveIcon(_showMoveIcon);
		slave.setShowPrintIcon(_showPrintIcon);
		slave.setURLBack(_urlBack);
		slave.setURLClose(_urlClose);
		slave.setURLConfiguration(_urlConfiguration);
		slave.setURLEdit(_urlEdit);
		slave.setURLEditGuest(_urlEditGuest);
		slave.setURLHelp(_urlHelp);
		slave.setURLMax(_urlMax);
		slave.setURLMin(_urlMin);
		slave.setURLPrint(_urlPrint);
		slave.setRestoreCurrentView(_restoreCurrentView);
	}

	private static Log _log = LogFactoryUtil.getLog(PortletDisplay.class);

	private String _id = StringPool.BLANK;
	private String _resourcePK;
	private String _namespace;
	private String _title;
	private boolean _access;
	private boolean _active;
	private String _columnId;
	private int _columnPos;
	private int _columnCount;
	private boolean _stateExclusive;
	private boolean _stateMax;
	private boolean _stateMin;
	private boolean _statePopUp;
	private boolean _modeAbout;
	private boolean _modeConfig;
	private boolean _modeEdit;
	private boolean _modeEditDefaults;
	private boolean _modeEditGuest;
	private boolean _modeHelp;
	private boolean _modePreview;
	private boolean _modePrint;
	private boolean _showBackIcon;
	private boolean _showCloseIcon;
	private boolean _showConfigurationIcon;
	private boolean _showEditIcon;
	private boolean _showEditGuestIcon;
	private boolean _showHelpIcon;
	private boolean _showMaxIcon;
	private boolean _showMinIcon;
	private boolean _showMoveIcon;
	private boolean _showPrintIcon;
	private String _urlBack;
	private String _urlClose;
	private String _urlConfiguration;
	private String _urlEdit;
	private String _urlEditGuest;
	private String _urlHelp;
	private String _urlMax;
	private String _urlMin;
	private String _urlPrint;
	private boolean _restoreCurrentView;

}