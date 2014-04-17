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

package com.liferay.taglib.ui.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.util.comparator.AssetRendererFactoryTypeNameComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * This class is intended to extract code from tag's JSP file.
 * @author JM Navarro
 */
public class InputAssetLinksTagHelper {

	public InputAssetLinksTagHelper(PageContext pageContext)
		throws PortalException, SystemException {

		super();

		_pageContext = pageContext;
		_request = (HttpServletRequest)pageContext.getRequest();
		_portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_locale = themeDisplay.getLocale();
		_company = themeDisplay.getCompany();
		_user = themeDisplay.getUser();
		_scopeGroup = themeDisplay.getScopeGroup();

		_randomNamespace = PortalUtil.generateRandomKey(
			_request, "taglib_ui_input_asset_links_page") +
			StringPool.UNDERLINE;

		_eventName = _randomNamespace + "selectAsset";

		_assetEntryId = GetterUtil.getLong(
			(String)_request.getAttribute(
				"liferay-ui:input-asset-links:assetEntryId"));

		_stagedLocally =
			_scopeGroup.isStaged() && !_scopeGroup.isStagedRemotely();

		_stagedReferrerPortlet = false;

		if (_stagedLocally) {
			String className = (String)_request.getAttribute(
				"liferay-ui:input-asset-links:className");

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			_stagedReferrerPortlet = _scopeGroup.isStagedPortlet(
				assetRendererFactory.getPortletId());
		}
	}

	public Map<String, Object> getAssetBrowserData(
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("href",
			_getAssetBrowserPortletURL(assetRendererFactory).toString());

		data.put("title",
			LanguageUtil.format(
				_pageContext , "select-x",
				assetRendererFactory.getTypeName(_locale), false));

		data.put("type", assetRendererFactory.getClassName());

		return data;
	}

	public String getAssetBrowserId(AssetRendererFactory assetRendererFactory) {
		return FriendlyURLNormalizerUtil.normalize(
			assetRendererFactory.getTypeName(_locale));
	}

	public String getAssetBrowserMessage(
		AssetRendererFactory assetRendererFactory) {

		return assetRendererFactory.getTypeName(_locale);
	}

	public String getAssetBrowserSrc(
		AssetRendererFactory assetRendererFactory) {

		return assetRendererFactory.getIconPath(_portletRequest);
	}

	public AssetEntry getAssetLinkEntry(AssetLink assetLink)
		throws PortalException, SystemException {

		AssetEntry entry = null;

		if (_assetEntryId > 0 || assetLink.getEntryId1() == _assetEntryId) {
			entry = AssetEntryLocalServiceUtil.getEntry(
				assetLink.getEntryId2());
		}
		else {
			entry = AssetEntryLocalServiceUtil.getEntry(
				assetLink.getEntryId1());
		}

		return entry;
	}

	public List<AssetLink> getAssetLinks()
		throws PortalException, SystemException {

		if (_assetLinks == null) {
			_assetLinks = _createAssetLinks();
		}

		return _assetLinks;
	}

	public List<AssetRendererFactory> getAssetRendererFactories() {
		List<AssetRendererFactory> assetRendererFactories =
			new ArrayList<AssetRendererFactory>();

		for (AssetRendererFactory assetRendererFactory :
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_company.getCompanyId())) {

			if (assetRendererFactory.isLinkable() &&
				assetRendererFactory.isSelectable()) {

				assetRendererFactories.add(assetRendererFactory);
			}
		}

		return ListUtil.sort(
			assetRendererFactories, 
			new AssetRendererFactoryTypeNameComparator(_locale));
	}

	public String getAssetType(AssetEntry entry) {
		AssetRendererFactory assetRendererFactory =
			entry.getAssetRendererFactory();

		return assetRendererFactory.getTypeName(_locale);
	}

	public String getEventName() {
		return _eventName;
	}

	public String getGroupDescriptiveName(AssetEntry entry)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(entry.getGroupId());

		return group.getDescriptiveName(_locale);
	}

	public String getRandomNamespace() {
		return _randomNamespace;
	}

	private static void _addGroupIds(
		List<Long> holder, List<Group> groupsToAdd) {

		for (Group g : groupsToAdd) {
			holder.add(g.getPrimaryKey());
		}
	}

	private List<AssetLink> _createAssetLinks()
		throws PortalException, SystemException {

		List<AssetLink> assetLinks = new ArrayList<AssetLink>();

		String assetLinksSearchContainerPrimaryKeys = ParamUtil.getString(
			_request, "assetLinksSearchContainerPrimaryKeys");

		if (Validator.isNull(assetLinksSearchContainerPrimaryKeys) &&
			SessionErrors.isEmpty(_portletRequest) && _assetEntryId > 0) {

			List<AssetLink> directAssetLinks =
				AssetLinkLocalServiceUtil.getDirectLinks(_assetEntryId);

			for (AssetLink assetLink : directAssetLinks) {
				AssetEntry assetLinkEntry = getAssetLinkEntry(assetLink);

				AssetRendererFactory assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							assetLinkEntry.getClassName());

				if (assetRendererFactory.isActive(_company.getCompanyId())) {
					assetLinks.add(assetLink);
				}
			}
		}
		else {
			String[] assetEntriesPrimaryKeys = StringUtil.split(
				assetLinksSearchContainerPrimaryKeys);

			for (String assetEntryPrimaryKey : assetEntriesPrimaryKeys) {
				long assetEntryPrimaryKeyLong = GetterUtil.getLong(
					assetEntryPrimaryKey);

				AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(
					assetEntryPrimaryKeyLong);

				AssetLink assetLink = AssetLinkLocalServiceUtil.createAssetLink(
					0);

				if (_assetEntryId > 0) {
					assetLink.setEntryId1(_assetEntryId);
				}
				else {
					assetLink.setEntryId1(0);
				}

				assetLink.setEntryId2(assetEntry.getEntryId());

				assetLinks.add(assetLink);
			}
		}

		return assetLinks;
	}

	private PortletURL _getAssetBrowserPortletURL(
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			_company.getCompanyId());

		PortletURL url = PortletURLFactoryUtil.create(
			_request, PortletKeys.ASSET_BROWSER, controlPanelPlid,
			PortletRequest.RENDER_PHASE);

		url.setParameter("struts_action", "/asset_browser/view");
		url.setParameter("eventName", getEventName());
		url.setPortletMode(PortletMode.VIEW);
		url.setWindowState(LiferayWindowState.POP_UP);

		if (_assetEntryId > 0) {
			url.setParameter(
				"refererAssetEntryId", String.valueOf(_assetEntryId));
		}

		long groupId = _scopeGroup.getGroupId();

		if (_stagedLocally) {
			boolean stagedReferencePortlet =
				_scopeGroup.isStagedPortlet(assetRendererFactory.getPortletId());

			if (_stagedReferrerPortlet && !stagedReferencePortlet) {
				groupId = _scopeGroup.getLiveGroupId();
			}
		}

		url.setParameter("groupId", String.valueOf(groupId));
		url.setParameter(
			"selectedGroupIds",
			StringUtil.merge(
				PortalUtil.getSharedContentSiteGroupIds(
					_company.getCompanyId(), groupId, _user.getUserId())
			));
		url.setParameter("typeSelection", assetRendererFactory.getClassName());

		return url;
	}

	private long _assetEntryId;
	private List<AssetLink> _assetLinks;
	private Company _company;
	private String _eventName;
	private Locale _locale;
	private PageContext _pageContext;
	private PortletRequest _portletRequest;
	private String _randomNamespace;
	private HttpServletRequest _request;
	private Group _scopeGroup;
	private boolean _stagedLocally;
	private boolean _stagedReferrerPortlet;
	private User _user;

}