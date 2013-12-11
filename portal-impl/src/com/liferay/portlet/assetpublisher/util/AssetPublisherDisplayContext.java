/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.util.RSSUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetPublisherDisplayContext {

	public AssetPublisherDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;

		_abstractLength = null;
		_anyAssetType = null;
		_assetLinkBehavior = null;
		_availableClassNameIds = null;
		_classNameIds = null;
		_classTypeIds = null;
		_ddmStructureDisplayFieldValue = null;
		_ddmStructureFieldName = null;
		_ddmStructureFieldValue = null;
		_delta = null;
		_displayStyle = null;
		_displayStyleGroupId = null;
		_enableCommentRatings = null;
		_enableComments = null;
		_enableFlags = null;
		_enablePrint = null;
		_enableRatings = null;
		_enableRelatedAssets = null;
		_enableRSS = null;
		_enableSocialBookmarks = null;
		_enableTagBasedNavigation = null;
		_excludeZeroViewCount = null;
		_extensions = null;
		_groupIds = null;
		_mergeLayoutTags = null;
		_mergeUrlTags = null;
		_metadataFields = null;
		_openOfficeServerEnabled = null;
		_orderByColumn1 = null;
		_orderByColumn2 = null;
		_orderByType1 = null;
		_orderByType2 = null;
		_paginationType = null;
		_rssDelta = null;
		_rssDisplayStyle = null;
		_rssFeedType = null;
		_rssName = null;
		_selectionStyle = null;
		_showAddContentButton = null;
		_showAssetTitle = null;
		_showAvailableLocales = null;
		_showContextLink = null;
		_showMetadataDescriptions = null;
		_showOnlyLayoutAssets = null;
		_socialBookmarksDisplayPosition = null;
		_socialBookmarksDisplayStyle = null;
		_subtypeFieldsFilterEnabled = null;
	}

	public int getAbstractLength() {
		if (_abstractLength == null) {
			_abstractLength = GetterUtil.getInteger(
				_portletPreferences.getValue("abstractLength", null), 200);
		}

		return _abstractLength;
	}

	public String getAssetLinkBehavior() {
		if (_assetLinkBehavior == null) {
			_assetLinkBehavior = GetterUtil.getString(
				_portletPreferences.getValue(
					"assetLinkBehavior", "showFullContent"));
		}

		return _assetLinkBehavior;
	}

	public long[] getAvailableClassNameIds() {
		if (_availableClassNameIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					themeDisplay.getCompanyId(), true);
		}

		return _availableClassNameIds;
	}

	public long[] getClassNameIds() {
		if (_classNameIds == null) {
			_classNameIds = AssetPublisherUtil.getClassNameIds(
				_portletPreferences, getAvailableClassNameIds());
		}

		return _classNameIds;
	}

	public long[] getClassTypeIds() {
		if (_classTypeIds == null) {
			_classTypeIds = GetterUtil.getLongValues(
				_portletPreferences.getValues("classTypeIds", null));
		}

		return _classTypeIds;
	}

	public String getDDMStructureDisplayFieldValue() {
		if (_ddmStructureDisplayFieldValue == null) {
			_ddmStructureDisplayFieldValue = GetterUtil.getString(
				_portletPreferences.getValue(
					"ddmStructureDisplayFieldValue", StringPool.BLANK));
		}

		return _ddmStructureDisplayFieldValue;
	}

	public String getDDMStructureFieldName() {
		if (_ddmStructureFieldName == null) {
			_ddmStructureFieldName = GetterUtil.getString(
				_portletPreferences.getValue(
					"ddmStructureFieldName", StringPool.BLANK));
		}

		return _ddmStructureFieldName;
	}

	public String getDDMStructureFieldValue() {
		if (_ddmStructureFieldValue == null) {
			_ddmStructureFieldValue = _portletPreferences.getValue(
				"ddmStructureFieldValue", StringPool.BLANK);
		}

		return _ddmStructureFieldValue;
	}

	public Integer getDelta() {
		if (_delta == null) {
			_delta = GetterUtil.getInteger(
				_portletPreferences.getValue("delta", null),
				SearchContainer.DEFAULT_DELTA);

			PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

			if (portletConfig != null) {
				String portletName = portletConfig.getPortletName();

				if (portletName.equals(PortletKeys.RECENT_CONTENT)) {
					_delta = PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS;
				}
			}
		}

		return _delta;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = GetterUtil.getString(
				_portletPreferences.getValue(
					"displayStyle",
					PropsValues.ASSET_PUBLISHER_DISPLAY_STYLE_DEFAULT));
		}

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = GetterUtil.getLong(
				_portletPreferences.getValue("displayStyleGroupId", null),
				themeDisplay.getScopeGroupId());
		}

		return _displayStyleGroupId;
	}

	public String[] getExtensions() {
		if (_extensions == null) {
			_extensions = _portletPreferences.getValues(
				"extensions", new String[0]);
		}

		return _extensions;
	}

	public long[] getGroupIds() {
		if (_groupIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_groupIds = AssetPublisherUtil.getGroupIds(
				_portletPreferences, themeDisplay.getScopeGroupId(),
				themeDisplay.getLayout());
		}

		return _groupIds;
	}

	public String[] getMetadataFields() {
		if (_metadataFields == null) {
			_metadataFields = StringUtil.split(
				_portletPreferences.getValue(
					"metadataFields", StringPool.BLANK));
		}

		return _metadataFields;
	}

	public String getOrderByColumn1() {
		if (_orderByColumn1 == null) {
			_orderByColumn1 = GetterUtil.getString(
				_portletPreferences.getValue("orderByColumn1", "modifiedDate"));
		}

		return _orderByColumn1;
	}

	public String getOrderByColumn2() {
		if (_orderByColumn2 == null) {
			_orderByColumn2 = GetterUtil.getString(
				_portletPreferences.getValue("orderByColumn2", "title"));
		}

		return _orderByColumn2;
	}

	public String getOrderByType1() {
		if (_orderByType1 == null) {
			_orderByType1 = GetterUtil.getString(
				_portletPreferences.getValue("orderByType1", "ASC"));
		}

		return _orderByType1;
	}

	public String getOrderByType2() {
		if (_orderByType2 == null) {
			_orderByType2 = GetterUtil.getString(
				_portletPreferences.getValue("orderByType2", "ASC"));
		}

		return _orderByType2;
	}

	public String getPaginationType() {
		if (_paginationType == null) {
			_paginationType = GetterUtil.getString(
				_portletPreferences.getValue("paginationType", "none"));
		}

		return _paginationType;
	}

	public int getRSSDelta() {
		if (_rssDelta == null) {
			_rssDelta = GetterUtil.getInteger(
				_portletPreferences.getValue("rssDelta", StringPool.BLANK),
				SearchContainer.DEFAULT_DELTA);
		}

		return _rssDelta;
	}

	public String getRSSDisplayStyle() {
		if (_rssDisplayStyle == null) {
			_rssDisplayStyle = _portletPreferences.getValue(
				"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		}

		return _rssDisplayStyle;
	}

	public String getRSSFeedType() {
		if (_rssFeedType == null) {
			_rssFeedType = _portletPreferences.getValue(
				"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
		}

		return _rssFeedType;
	}

	public String getRSSName() {
		if (_rssName == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			_rssName = _portletPreferences.getValue(
				"rssName", portletDisplay.getTitle());
		}

		return _rssName;
	}

	public String getSelectionStyle() {
		if (_selectionStyle == null) {
			_selectionStyle = GetterUtil.getString(
				_portletPreferences.getValue("selectionStyle", null),
				"dynamic");
		}

		return _selectionStyle;
	}

	public String getSocialBookmarksDisplayPosition() {
		if (_socialBookmarksDisplayPosition == null) {
			_socialBookmarksDisplayPosition = _portletPreferences.getValue(
				"socialBookmarksDisplayPosition", "bottom");
		}

		return _socialBookmarksDisplayPosition;
	}

	public String getSocialBookmarksDisplayStyle() {
		if (_socialBookmarksDisplayStyle == null) {
			_socialBookmarksDisplayStyle = _portletPreferences.getValue(
				"socialBookmarksDisplayStyle", null);

			if (Validator.isNull(_socialBookmarksDisplayStyle)) {
				String[] socialBookmarksDisplayStyles = PropsUtil.getArray(
					PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

				_socialBookmarksDisplayStyle = socialBookmarksDisplayStyles[0];
			}
		}

		return _socialBookmarksDisplayStyle;
	}

	public Boolean isAnyAssetType() {
		if (_anyAssetType == null) {
			_anyAssetType = GetterUtil.getBoolean(
				_portletPreferences.getValue("anyAssetType", null), true);
		}

		return _anyAssetType;
	}

	public boolean isEnableCommentRatings() {
		if (_enableCommentRatings == null) {
			_enableCommentRatings = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableCommentRatings", null));
		}

		return _enableCommentRatings;
	}

	public boolean isEnableComments() {
		if (_enableComments == null) {
			_enableComments = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableComments", null));
		}

		return _enableComments;
	}

	public boolean isEnableFlags() {
		if (_enableFlags == null) {
			_enableFlags = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableFlags", null));
		}

		return _enableFlags;
	}

	public Boolean isEnablePermissions() {
		if (_enablePermissions == null) {
			if (!PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX) {
				_enablePermissions = false;

				return _enablePermissions;
			}

			PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

			if (portletConfig != null) {
				String portletName = portletConfig.getPortletName();

				if (portletName.equals(PortletKeys.HIGHEST_RATED_ASSETS) ||
					portletName.equals(PortletKeys.MOST_VIEWED_ASSETS)) {

					_enablePermissions = false;

					return _enablePermissions;
				}
			}

			_enablePermissions = GetterUtil.getBoolean(
				_portletPreferences.getValue("enablePermissions", null));
		}

		return _enablePermissions;
	}

	public boolean isEnablePrint() {
		if (_enablePrint == null) {
			_enablePrint = GetterUtil.getBoolean(
				_portletPreferences.getValue("enablePrint", null));
		}

		return _enablePrint;
	}

	public boolean isEnableRatings() {
		if (_enableRatings == null) {
			_enableRatings = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRatings", null));
		}

		return _enableRatings;
	}

	public boolean isEnableRelatedAssets() {
		if (_enableRelatedAssets == null) {
			_enableRelatedAssets = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRelatedAssets", null),
				true);
		}

		return _enableRelatedAssets;
	}

	public boolean isEnableRSS() {
		if (_enableRSS == null) {
			_enableRSS = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRss", null));
		}

		return _enableRSS;
	}

	public boolean isEnableSocialBookmarks() {
		if (_enableSocialBookmarks == null) {
			_enableSocialBookmarks = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableSocialBookmarks", null),
				true);
		}

		return _enableSocialBookmarks;
	}

	public boolean isEnableTagBasedNavigation() {
		if (_enableTagBasedNavigation == null) {
			_enableTagBasedNavigation = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableTagBasedNavigation", null));
		}

		return _enableTagBasedNavigation;
	}

	public boolean isExcludeZeroViewCount() {
		if (_excludeZeroViewCount == null) {
			_excludeZeroViewCount = GetterUtil.getBoolean(
				_portletPreferences.getValue("excludeZeroViewCount", null));
		}

		return _excludeZeroViewCount;
	}

	public boolean isMergeLayoutTags() {
		if (_mergeLayoutTags == null) {
			_mergeLayoutTags = GetterUtil.getBoolean(
				_portletPreferences.getValue("mergeLayoutTags", null), false);
		}

		return _mergeLayoutTags;
	}

	public boolean isMergeUrlTags() {
		if (_mergeUrlTags == null) {
			_mergeUrlTags = GetterUtil.getBoolean(
				_portletPreferences.getValue("mergeUrlTags", null), true);
		}

		return _mergeUrlTags;
	}

	public boolean isOpenOfficeServerEnabled() throws SystemException {
		if (_openOfficeServerEnabled == null) {
			_openOfficeServerEnabled = PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED);
		}

		return _openOfficeServerEnabled;
	}

	public boolean isShowAddContentButton() {
		if (_showAddContentButton == null) {
			_showAddContentButton = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAddContentButton", null),
				true);
		}

		return _showAddContentButton;
	}

	public Boolean isShowAssetTitle() {
		if (_showAssetTitle == null) {
			_showAssetTitle = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAssetTitle", null), true);
		}

		return _showAssetTitle;
	}

	public Boolean isShowAvailableLocales() {
		if (_showAvailableLocales == null) {
			_showAvailableLocales = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAvailableLocales", null));
		}

		return _showAvailableLocales;
	}

	public Boolean isShowContextLink() {
		if (_showContextLink == null) {
			_showContextLink = GetterUtil.getBoolean(
				_portletPreferences.getValue("showContextLink", null), true);
		}

		return _showContextLink;
	}

	public boolean isShowMetadataDescriptions() {
		if (_showMetadataDescriptions == null) {
			_showMetadataDescriptions = GetterUtil.getBoolean(
				_portletPreferences.getValue("showMetadataDescriptions", null),
				true);
		}

		return _showMetadataDescriptions;
	}

	public boolean isShowOnlyLayoutAssets() {
		if (_showOnlyLayoutAssets == null) {
			_showOnlyLayoutAssets = GetterUtil.getBoolean(
				_portletPreferences.getValue("showOnlyLayoutAssets", null));
		}

		return _showOnlyLayoutAssets;
	}

	public boolean isSubtypeFieldsFilterEnabled() {
		if (_subtypeFieldsFilterEnabled == null) {
			_subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
				_portletPreferences.getValue(
					"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));
		}

		return _subtypeFieldsFilterEnabled;
	}

	public void setSelectionStyle(String selectionStyle) {
		_selectionStyle = selectionStyle;
	}

	public void setShowContextLink(Boolean showContextLink) {
		_showContextLink = showContextLink;
	}

	private Integer _abstractLength;
	private Boolean _anyAssetType;
	private String _assetLinkBehavior;
	private long[] _availableClassNameIds;
	private long[] _classNameIds;
	private long[] _classTypeIds;
	private String _ddmStructureDisplayFieldValue;
	private String _ddmStructureFieldName;
	private String _ddmStructureFieldValue;
	private Integer _delta;
	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enableFlags;
	private Boolean _enablePermissions;
	private Boolean _enablePrint;
	private Boolean _enableRatings;
	private Boolean _enableRelatedAssets;
	private Boolean _enableRSS;
	private Boolean _enableSocialBookmarks;
	private Boolean _enableTagBasedNavigation;
	private Boolean _excludeZeroViewCount;
	private String[] _extensions;
	private long[] _groupIds;
	private Boolean _mergeLayoutTags;
	private Boolean _mergeUrlTags;
	private String[] _metadataFields;
	private Boolean _openOfficeServerEnabled;
	private String _orderByColumn1;
	private String _orderByColumn2;
	private String _orderByType1;
	private String _orderByType2;
	private String _paginationType;
	private PortletPreferences _portletPreferences;
	private HttpServletRequest _request;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private String _rssFeedType;
	private String _rssName;
	private String _selectionStyle;
	private Boolean _showAddContentButton;
	private Boolean _showAssetTitle;
	private Boolean _showAvailableLocales;
	private Boolean _showContextLink;
	private Boolean _showMetadataDescriptions;
	private Boolean _showOnlyLayoutAssets;
	private String _socialBookmarksDisplayPosition;
	private String _socialBookmarksDisplayStyle;
	private Boolean _subtypeFieldsFilterEnabled;

}