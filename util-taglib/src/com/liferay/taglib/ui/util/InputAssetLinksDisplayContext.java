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

package com.liferay.taglib.ui.util;

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
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
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
 * @author Jos� Manuel Navarro
 */
public class InputAssetLinksDisplayContext {

	public InputAssetLinksDisplayContext(PageContext pageContext) {
		_pageContext = pageContext;

		_request = (HttpServletRequest)pageContext.getRequest();

		_portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_company = themeDisplay.getCompany();
		_locale = themeDisplay.getLocale();
		_scopeGroup = themeDisplay.getScopeGroup();
		_user = themeDisplay.getUser();

		_assetEntryId = GetterUtil.getLong(
			(String)_request.getAttribute(
				"liferay-ui:input-asset-links:assetEntryId"));
	}

	public AssetEntry getAssetLinkEntry(AssetLink assetLink)
		throws PortalException, SystemException {

		if ((_assetEntryId > 0) || (assetLink.getEntryId1() == _assetEntryId)) {
			return AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
		}

		return AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
	}

	public List<AssetLink> getAssetLinks()
		throws PortalException, SystemException {

		if (_assetLinks == null) {
			_assetLinks = _createAssetLinks();
		}

		return _assetLinks;
	}

	public int getAssetLinksCount() throws PortalException, SystemException {
		List<AssetLink> assetLinks = getAssetLinks();

		return assetLinks.size();
	}

	public List<AssetRendererFactory> getAssetRendererFactories() {
		List<AssetRendererFactory> assetRendererFactories = ListUtil.filter(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_company.getCompanyId()),
				new PredicateFilter<AssetRendererFactory>() {

					@Override
					public boolean filter(
						AssetRendererFactory assetRendererFactory) {

						if (assetRendererFactory.isLinkable() &&
							assetRendererFactory.isSelectable()) {

							return true;
						}

						return false;
					}

				});

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
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = _randomNamespace + "selectAsset";

		return _eventName;
	}

	public String getGroupDescriptiveName(AssetEntry assetEntry)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());

		return group.getDescriptiveName(_locale);
	}

	public String getRandomNamespace() {
		if (_randomNamespace != null) {
			return _randomNamespace;
		}

		_randomNamespace = PortalUtil.generateRandomKey(
			_request, "taglib_ui_input_asset_links_page") +
			StringPool.UNDERLINE;

		return _randomNamespace;
	}

	public List<Map<String, Object>> getSelectorEntries() throws Exception {
		List<Map<String, Object>> selectorEntries =
			new ArrayList<Map<String, Object>>();

		for (AssetRendererFactory assetRendererFactory :
				getAssetRendererFactories()) {

			if (assetRendererFactory.isSupportsClassTypes()) {
				selectorEntries.addAll(
					_getSelectorEntries(assetRendererFactory));
			}
			else {
				Map<String, Object> selectorEntry =
					new HashMap<String, Object>();

				selectorEntry.put(
					"data", _geSelectorEntryData(assetRendererFactory));
				selectorEntry.put(
					"id", _getSelectorEntryId(assetRendererFactory));
				selectorEntry.put(
					"message", _getSelectorEntryMessage(assetRendererFactory));
				selectorEntry.put(
					"src", _getSelectorEntrySrc(assetRendererFactory));

				selectorEntries.add(selectorEntry);
			}
		}

		return selectorEntries;
	}

	private List<AssetLink> _createAssetLinks()
		throws PortalException, SystemException {

		List<AssetLink> assetLinks = new ArrayList<AssetLink>();

		String assetLinksSearchContainerPrimaryKeys = ParamUtil.getString(
			_request, "assetLinksSearchContainerPrimaryKeys");

		if (Validator.isNull(assetLinksSearchContainerPrimaryKeys) &&
			SessionErrors.isEmpty(_portletRequest) && (_assetEntryId > 0)) {

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

	private Map<String, Object> _geSelectorEntryData(
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		Map<String, Object> selectorEntryData = new HashMap<String, Object>();

		selectorEntryData.put(
			"href",
			_getAssetBrowserPortletURL(assetRendererFactory).toString());

		String typeName = assetRendererFactory.getTypeName(_locale);

		selectorEntryData.put(
			"title", LanguageUtil.format(
				_pageContext , "select-x", typeName, false));

		selectorEntryData.put("type", assetRendererFactory.getClassName());

		return selectorEntryData;
	}

	private long _getAssetBrowserGroupId(
		AssetRendererFactory assetRendererFactory) {

		long groupId = _scopeGroup.getGroupId();

		if (_isStagedLocally()) {
			boolean stagedReferencePortlet =
				_scopeGroup.isStagedPortlet(
					assetRendererFactory.getPortletId());

			if (_isStagedReferrerPortlet() && !stagedReferencePortlet) {
				groupId = _scopeGroup.getLiveGroupId();
			}
		}

		return groupId;
	}

	private PortletURL _getAssetBrowserPortletURL(
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			_company.getCompanyId());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_request, PortletKeys.ASSET_BROWSER, controlPanelPlid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/asset_browser/view");
		portletURL.setParameter("eventName", getEventName());

		if (_assetEntryId > 0) {
			portletURL.setParameter(
				"refererAssetEntryId", String.valueOf(_assetEntryId));
		}

		long groupId = _getAssetBrowserGroupId(assetRendererFactory);

		portletURL.setParameter("groupId", String.valueOf(groupId));
		portletURL.setParameter(
			"selectedGroupIds",
			StringUtil.merge(
				PortalUtil.getSharedContentSiteGroupIds(
					_company.getCompanyId(), groupId, _user.getUserId())
			));

		portletURL.setParameter(
			"typeSelection", assetRendererFactory.getClassName());

		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	private List<Map<String, Object>> _getSelectorEntries(
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		long groupId = _getAssetBrowserGroupId(assetRendererFactory);

		Map<Long, String> classTypes =
			assetRendererFactory.getClassTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), _locale);

		if (classTypes.isEmpty()) {
			return null;
		}

		List<Map<String, Object>> selectorEntries =
			new ArrayList<Map<String, Object>>();

		for (Map.Entry<Long, String> assetAvailableClassType :
				classTypes.entrySet()) {

			Map<String, Object> selectorEntry = new HashMap<String, Object>();

			selectorEntry.put(
				"data",
				_getSelectorEntryData(
					assetRendererFactory, assetAvailableClassType));
			selectorEntry.put(
				"id",
				_getSelectorEntryId(
					assetRendererFactory, assetAvailableClassType));
			selectorEntry.put(
				"message", _getSelectorEntryMessage(assetAvailableClassType));
			selectorEntry.put(
				"src", _getSelectorEntrySrc(assetRendererFactory));

			selectorEntries.add(selectorEntry);
		}

		return selectorEntries;
	}

	private Map<String, Object> _getSelectorEntryData(
			AssetRendererFactory assetRendererFactory,
			Map.Entry<Long, String> assetAvailableClassType)
		throws Exception {

		Map<String, Object> selectorEntryData = new HashMap<String, Object>();

		PortletURL portletURL = _getAssetBrowserPortletURL(
			assetRendererFactory);

		portletURL.setParameter(
			"subtypeSelectionId",
			String.valueOf(assetAvailableClassType.getKey()));

		selectorEntryData.put("href", portletURL.toString());

		String title =
			LanguageUtil.format(
				_pageContext, "select-x", assetAvailableClassType.getValue(),
				false);

		selectorEntryData.put("title", title);

		selectorEntryData.put("type", assetAvailableClassType.getValue());

		return selectorEntryData;
	}

	private String _getSelectorEntryId(
		AssetRendererFactory assetRendererFactory) {

		return FriendlyURLNormalizerUtil.normalize(
			assetRendererFactory.getTypeName(_locale));
	}

	private String _getSelectorEntryId(
		AssetRendererFactory assetRendererFactory,
		Map.Entry<Long, String> assetAvailableClassType) {

		StringBundler sb = new StringBundler(2);

		sb.append(_getAssetBrowserGroupId(assetRendererFactory));
		sb.append(
			FriendlyURLNormalizerUtil.normalize(
				assetAvailableClassType.getValue()));

		return sb.toString();
	}

	private String _getSelectorEntryMessage(
		AssetRendererFactory assetRendererFactory) {

		return assetRendererFactory.getTypeName(_locale);
	}

	private String _getSelectorEntryMessage(
		Map.Entry<Long, String> assetAvailableClassType) {

		return assetAvailableClassType.getValue();
	}

	private String _getSelectorEntrySrc(
		AssetRendererFactory assetRendererFactory) {

		return assetRendererFactory.getIconPath(_portletRequest);
	}

	private boolean _isStagedLocally() {
		if (_stagedLocally != null) {
			return _stagedLocally;
		}

		if (_scopeGroup.isStaged() && !_scopeGroup.isStagedRemotely()) {
			_stagedLocally = true;
		}
		else {
			_stagedLocally = false;
		}

		return _stagedLocally;
	}

	private boolean _isStagedReferrerPortlet() {
		if (_stagedReferrerPortlet != null) {
			return _stagedReferrerPortlet;
		}

		if (_isStagedLocally()) {
			String className = (String)_request.getAttribute(
				"liferay-ui:input-asset-links:className");

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			_stagedReferrerPortlet = _scopeGroup.isStagedPortlet(
				assetRendererFactory.getPortletId());
		}
		else {
			_stagedReferrerPortlet = false;
		}

		return _stagedReferrerPortlet;
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
	private Boolean _stagedLocally;
	private Boolean _stagedReferrerPortlet;
	private User _user;

}