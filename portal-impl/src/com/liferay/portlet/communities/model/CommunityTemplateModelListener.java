/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.model;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.io.FileCacheOutputStream;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.lar.UserIdStrategy;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CommunityTemplateModelListener.java.html"><b><i>View
 * Source</i></b></a>
 * <p/>
 * A ModelListener that listens for creation of communities and attempts to
 * prepopulate the community pages from a template community.
 * <p/>
 * The template community should be a private community and have the appropriate
 * layouts and portlets in its "Private Pages".  This will prevent users from
 * navigating to and using this community.
 * <p/>
 * You may create a separate template for private, open, and protected
 * community. You may also create a default template for all three.  The
 * template community names are:
 * <UL>
 * 	<LI>OPEN_TEMPLATE</LI>
 * 	<LI>PRIVATE_TEMPLATE</LI>
 * 	<LI>RESTRICTED_TEMPLATE>/LI>
 * 	<LI>DEFAULT_TEMPLATE</LI>
 * </UL>
 * <p/>
 * A newly created community will have its layouts preconfigured via: 1) If
 * community is public, the template pages will be imported as the community's
 * public pages 2) If community is private or protected, the template pages will
 * be imported as the community's private pages
 *
 * @author Michael C. Han
 */
public class CommunityTemplateModelListener
	extends BaseModelListener<LayoutSet> {

	public CommunityTemplateModelListener() {
		_templateParameters = getLayoutTemplatesParameters();
	}

	public void onAfterCreate(LayoutSet layoutSet)
		throws ModelListenerException {

		try {
			Group group =
				GroupLocalServiceUtil.getGroup(layoutSet.getGroupId());
			if (!group.isCommunity() ||
				group.getName().contains(_TEMPLATE_POSTFIX)) {
				return;
			}

			Group templateGroup = getTemplateGroup(group);
			if (templateGroup == null) {
				//not template group...
				return;
			}

			Group templateStagingGroup = templateGroup.getStagingGroup();
			if (templateStagingGroup == null) {
				//no staging area for template group where the
				//template layouts should be defined
				return;
			}

			boolean isPrivate = layoutSet.isPrivateLayout();
			FileCacheOutputStream layoutStream =
				LayoutLocalServiceUtil.exportLayoutsAsStream(
					templateStagingGroup.getGroupId(), isPrivate, null,
					_templateParameters, null, null);

			LayoutLocalServiceUtil.importLayouts(
				group.getCreatorUserId(), group.getGroupId(), isPrivate,
				_templateParameters, layoutStream.getFileInputStream());

		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Unble to import layouts for group: " +
					layoutSet.getGroupId(), e);
			}
		}
	}

	private Map<String, String[]> getLayoutTemplatesParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[]{PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[]{
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[]{
				PortletDataHandlerKeys.
					PORTLETS_MERGE_MODE_ADD_TO_BOTTOM});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[]{UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[]{Boolean.FALSE.toString()});

		return parameterMap;
	}

	private Group getTemplateGroup(Group group)
		throws PortalException, SystemException {

		String templateCommunityName;
		switch (group.getType()) {
			case GroupConstants.TYPE_COMMUNITY_OPEN:
				templateCommunityName = _OPEN_TEMPLATE_COMMUNITY_NAME;
				break;
			case GroupConstants.TYPE_COMMUNITY_PRIVATE:
				templateCommunityName = _PRIVATE_TEMPLATE_COMMUNITY_NAME;
				break;
			case GroupConstants.TYPE_COMMUNITY_RESTRICTED:
				templateCommunityName = _RESTRICTED_TEMPLATE_COMMUNITY_NAME;
				break;
			default:
				throw new IllegalArgumentException(
					"Invalid community type: " + group.getType());
		}

		Group templateGroup = null;
		try {
			templateGroup = GroupLocalServiceUtil.getGroup(
				group.getCompanyId(), templateCommunityName);
		}
		catch (NoSuchGroupException e) {
			//check if there's a default template
			try {
				templateGroup =
					GroupLocalServiceUtil.getGroup(
						group.getCompanyId(),
						_DEFAULT_TEMPLATE_COMMUNITY_NAME);
			}
			catch (NoSuchGroupException e1) {
				//nothing to do here...
			}
		}

		return templateGroup;
	}

	private static final String _OPEN_TEMPLATE_COMMUNITY_NAME =
		"OPEN_TEMPLATE";
	private static final String _PRIVATE_TEMPLATE_COMMUNITY_NAME =
		"PRIVATE_TEMPLATE";
	private static final String _RESTRICTED_TEMPLATE_COMMUNITY_NAME =
		"RESTRICTED_TEMPLATE";
	private static final String _DEFAULT_TEMPLATE_COMMUNITY_NAME =
		"DEFAULT_TEMPLATE";
	private static final String _TEMPLATE_POSTFIX = "_TEMPLATE";
	private static final Log _log =
		LogFactory.getLog(CommunityTemplateModelListener.class);
	private Map<String, String[]> _templateParameters;
}
