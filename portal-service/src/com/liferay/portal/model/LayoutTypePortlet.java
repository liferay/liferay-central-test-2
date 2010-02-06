/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;

import java.util.List;

/**
 * <a href="LayoutTypePortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface LayoutTypePortlet extends LayoutType {

	public void addModeAboutPortletId(String portletId);

	public void addModeConfigPortletId(String portletId);

	public void addModeEditDefaultsPortletId(String portletId);

	public void addModeEditGuestPortletId(String portletId);

	public void addModeEditPortletId(String portletId);

	public void addModeHelpPortletId(String portletId);

	public void addModePreviewPortletId(String portletId);

	public void addModePrintPortletId(String portletId);

	public String addPortletId(long userId, String portletId);

	public String addPortletId(
		long userId, String portletId, boolean checkPermission);

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos);

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos,
		boolean checkPermission);

	public void addPortletIds(
		long userId, String[] portletIds, boolean checkPermission);

	public void addPortletIds(
		long userId, String[] portletIds, String columnId,
		boolean checkPermission);

	public void addStateMaxPortletId(String portletId);

	public void addStateMinPortletId(String portletId);

	public List<Portlet> addStaticPortlets(
			List<Portlet> portlets, List<Portlet> startPortlets,
			List<Portlet> endPortlets)
		throws SystemException;

	public List<Portlet> getAllPortlets() throws SystemException;

	public List<Portlet> getAllPortlets(String columnId) throws SystemException;

	public LayoutTemplate getLayoutTemplate();

	public String getLayoutTemplateId();

	public String getModeAbout();

	public String getModeConfig();

	public String getModeEdit();

	public String getModeEditDefaults();

	public String getModeEditGuest();

	public String getModeHelp();

	public String getModePreview();

	public String getModePrint();

	public int getNumOfColumns();

	public List<String> getPortletIds();

	public List<Portlet> getPortlets() throws SystemException;

	public String getStateMax();

	public String getStateMaxPortletId();

	public String getStateMin();

	public boolean hasModeAboutPortletId(String portletId);

	public boolean hasModeConfigPortletId(String portletId);

	public boolean hasModeEditDefaultsPortletId(String portletId);

	public boolean hasModeEditGuestPortletId(String portletId);

	public boolean hasModeEditPortletId(String portletId);

	public boolean hasModeHelpPortletId(String portletId);

	public boolean hasModePreviewPortletId(String portletId);

	public boolean hasModePrintPortletId(String portletId);

	public boolean hasModeViewPortletId(String portletId);

	public boolean hasPortletId(String portletId);

	public boolean hasStateMax();

	public boolean hasStateMaxPortletId(String portletId);

	public boolean hasStateMin();

	public boolean hasStateMinPortletId(String portletId);

	public boolean hasStateNormalPortletId(String portletId);

	public void movePortletId(
		long userId, String portletId, String columnId, int columnPos);

	public void removeModeAboutPortletId(String portletId);

	public void removeModeConfigPortletId(String portletId);

	public void removeModeEditDefaultsPortletId(String portletId);

	public void removeModeEditGuestPortletId(String portletId);

	public void removeModeEditPortletId(String portletId);

	public void removeModeHelpPortletId(String portletId);

	public void removeModePreviewPortletId(String portletId);

	public void removeModePrintPortletId(String portletId);

	public void removeModesPortletId(String portletId);

	public void removeNestedColumns(String portletId);

	public void removePortletId(long userId, String portletId);

	public void removePortletId(
		long userId, String portletId, boolean modeAndState);

	public void removeStateMaxPortletId(String portletId);

	public void removeStateMinPortletId(String portletId);

	public void removeStatesPortletId(String portletId);

	public void reorganizePortlets(
		List<String> newColumns, List<String> oldColumns);

	public void resetModes();

	public void resetStates();

	public void setLayoutTemplateId(long userId, String newLayoutTemplateId);

	public void setLayoutTemplateId(
		long userId, String newLayoutTemplateId, boolean checkPermission);

	public void setModeAbout(String modeAbout);

	public void setModeConfig(String modeConfig);

	public void setModeEdit(String modeEdit);

	public void setModeEditDefaults(String modeEditDefaults);

	public void setModeEditGuest(String modeEditGuest);

	public void setModeHelp(String modeHelp);

	public void setModePreview(String modePreview);

	public void setModePrint(String modePrint);

	public void setPortletIds(String columnId, String portletIds);

	public void setStateMax(String stateMax);

	public void setStateMin(String stateMin);

}