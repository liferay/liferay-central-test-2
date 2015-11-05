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

package com.liferay.journal.content.web.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.asset.addon.entry.common.ContentMetadataAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.common.ContentMetadataAssetAddonEntryTracker;
import com.liferay.journal.content.asset.addon.entry.common.UserToolAssetAddonEntry;
import com.liferay.journal.content.asset.addon.entry.common.UserToolAssetAddonEntryTracker;
import com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.util.PropertyComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public JournalContentDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_journalContentPortletInstanceConfiguration =
			journalContentPortletInstanceConfiguration;

		if (Validator.isNull(getPortletResource()) && !isShowArticle()) {
			portletRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public void clearCache() throws PortalException {
		String articleId = getArticleId();

		if (Validator.isNotNull(articleId)) {
			JournalContent journalContent =
				(JournalContent) _portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

				journalContent.clearCache(
					getArticleGroupId(), getArticleId(), getDDMTemplateKey());
		}
	}

	public JournalArticle getArticle() {
		if (_article != null) {
			return _article;
		}

		_article = (JournalArticle)_portletRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		if (_article != null) {
			return _article;
		}

		long articleResourcePrimKey = ParamUtil.getLong(
			_portletRequest, "articleResourcePrimKey");

		if (articleResourcePrimKey > 0) {
			_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
				articleResourcePrimKey, WorkflowConstants.STATUS_ANY, true);
		}
		else {
			_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
				getArticleGroupId(), getArticleId(),
				WorkflowConstants.STATUS_ANY);
		}

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		_articleDisplay = (JournalArticleDisplay)_portletRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY);

		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (article.isApproved()) {
			JournalContent journalContent =
				(JournalContent) _portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			_articleDisplay = journalContent.getDisplay(
				article.getGroupId(), article.getArticleId(), null, null,
				themeDisplay.getLanguageId(), 1,
				new PortletRequestModel(_portletRequest, _portletResponse),
				themeDisplay);
		}
		else {
			try {
				_articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						article, null, null, themeDisplay.getLanguageId(), 1,
					new PortletRequestModel(_portletRequest, _portletResponse),
					themeDisplay);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return _articleDisplay;
	}

	public long getArticleGroupId() {
		if (_articleGroupId != null) {
			return _articleGroupId;
		}

		_articleGroupId = ParamUtil.getLong(
			_portletRequest, "groupId",
			_journalContentPortletInstanceConfiguration.groupId());

		if (_articleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_articleGroupId = themeDisplay.getScopeGroupId();
		}

		return _articleGroupId;
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = ParamUtil.getString(
			_portletRequest, "articleId",
			_journalContentPortletInstanceConfiguration.articleId());

		return _articleId;
	}

	public long getAssetEntryId() {
		JournalArticle article = getArticle();

		if (article == null) {
			return 0;
		}

		long classPK = JournalArticleAssetRenderer.getClassPK(article);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), classPK);

		return assetEntry.getEntryId();
	}

	public AssetRenderer<JournalArticle> getAssetRenderer()
		throws PortalException {

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		if (assetRendererFactory == null) {
			return null;
		}

		return assetRendererFactory.getAssetRenderer(
			JournalArticleAssetRenderer.getClassPK(article));
	}

	public DDMStructure getDDMStructure() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		return article.getDDMStructure();
	}

	public DDMTemplate getDDMTemplate() throws PortalException {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((articleDisplay == null) ||
			Validator.isNull(articleDisplay.getDDMTemplateKey())) {

			return null;
		}

		_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			articleDisplay.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class), getDDMTemplateKey(),
			true);

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() throws PortalException {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey",
			_journalContentPortletInstanceConfiguration.ddmTemplateKey());

		if (Validator.isNotNull(_ddmTemplateKey)) {
			return _ddmTemplateKey;
		}

		List<DDMTemplate> ddmTemplates = getDDMTemplates();

		if (!ddmTemplates.isEmpty()) {
			JournalArticle article = getArticle();

			_ddmTemplateKey = article.getDDMTemplateKey();
		}

		return _ddmTemplateKey;
	}

	public List<DDMTemplate> getDDMTemplates() throws PortalException {
		if (_ddmTemplates != null) {
			return _ddmTemplates;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return Collections.emptyList();
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		_ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(
			article.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(), true);

		return _ddmTemplates;
	}

	public List<ContentMetadataAssetAddonEntry>
		getEnabledContentMetadataAssetAddonEntries() {

		List<ContentMetadataAssetAddonEntry> contentMetadataAssetAddonEntries =
			ListUtil.filter(
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntries(),
				new PredicateFilter<ContentMetadataAssetAddonEntry>() {

					@Override
					public boolean filter(
						ContentMetadataAssetAddonEntry
							contentMetadataAssetAddonEntry) {

						return contentMetadataAssetAddonEntry.isEnabled();
					}

				});

		return ListUtil.sort(
			contentMetadataAssetAddonEntries,
			new PropertyComparator("weight", true, false));
	}

	public List<UserToolAssetAddonEntry> getEnabledUserToolAssetAddonEntries() {
		List<UserToolAssetAddonEntry> userToolAssetAddonEntries =
			ListUtil.filter(
				UserToolAssetAddonEntryTracker.getUserToolAssetAddonEntries(),
				new PredicateFilter<UserToolAssetAddonEntry>() {

					@Override
					public boolean filter(
						UserToolAssetAddonEntry userToolAssetAddonEntry) {

						return userToolAssetAddonEntry.isEnabled();
					}

				});

		return ListUtil.sort(
			userToolAssetAddonEntries,
			new PropertyComparator("weight", true, false));
	}

	public JournalArticle getLatestArticle() {
		if (_latestArticle != null) {
			return _latestArticle;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if (articleDisplay == null) {
			return null;
		}

		_latestArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(
			articleDisplay.getGroupId(), articleDisplay.getArticleId(),
			WorkflowConstants.STATUS_ANY);

		return _latestArticle;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_portletRequest, "portletResource");

		return _portletResource;
	}

	public List<ContentMetadataAssetAddonEntry>
		getSelectedContentMetadataAssetAddonEntries() {

		if (_contentMetadataAssetAddonEntries != null) {
			return _contentMetadataAssetAddonEntries;
		}

		_contentMetadataAssetAddonEntries = new ArrayList<>();

		String contentMetadataAssetAddonEntryKeysKeysString =
			_journalContentPortletInstanceConfiguration.
				contentMetadataAssetAddonEntryKeys();

		if (Validator.isNull(contentMetadataAssetAddonEntryKeysKeysString)) {
			return _contentMetadataAssetAddonEntries;
		}

		String[] contentMetadataAssetAddonEntryKeys = StringUtil.split(
			contentMetadataAssetAddonEntryKeysKeysString);

		for (String contentMetadataAssetAddonEntryKey :
				contentMetadataAssetAddonEntryKeys) {

			ContentMetadataAssetAddonEntry contentMetadataAssetAddonEntry =
				ContentMetadataAssetAddonEntryTracker.
					getContentMetadataAssetAddonEntry(
						contentMetadataAssetAddonEntryKey);

			if (contentMetadataAssetAddonEntry != null) {
				_contentMetadataAssetAddonEntries.add(
					contentMetadataAssetAddonEntry);
			}
		}

		_portletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_portletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _contentMetadataAssetAddonEntries;
	}

	public List<UserToolAssetAddonEntry>
		getSelectedUserToolAssetAddonEntries() {

		if (_userToolAssetAddonEntries != null) {
			return _userToolAssetAddonEntries;
		}

		_userToolAssetAddonEntries = new ArrayList<>();

		String userToolAssetAddonEntryKeysString =
			_journalContentPortletInstanceConfiguration.
				userToolAssetAddonEntryKeys();

		if (Validator.isNull(userToolAssetAddonEntryKeysString)) {
			return _userToolAssetAddonEntries;
		}

		String[] userToolAssetAddonEntryKeys = StringUtil.split(
			userToolAssetAddonEntryKeysString);

		for (String userToolAssetAddonEntryKey : userToolAssetAddonEntryKeys) {
			UserToolAssetAddonEntry userToolAssetAddonEntry =
				UserToolAssetAddonEntryTracker.getUserToolAssetAddonEntry(
					userToolAssetAddonEntryKey);

			if (userToolAssetAddonEntry != null) {
				_userToolAssetAddonEntries.add(userToolAssetAddonEntry);
			}
		}

		_portletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, getArticle());
		_portletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY, getArticleDisplay());

		return _userToolAssetAddonEntries;
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		JournalArticle article = getArticle();

		if (article != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_hasViewPermission = JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), article.getGroupId(),
				article.getArticleId(), ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public void incrementViewCounter() throws PortalException {
		JournalArticle article = getArticle();
		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((article == null) || !hasViewPermission() ||
			(articleDisplay == null) || isExpired() ||
			!isEnableViewCountIncrement()) {

			return;
		}

		AssetEntryServiceUtil.incrementViewCounter(
			JournalArticle.class.getName(),
			articleDisplay.getResourcePrimKey());
	}

	public boolean isEnableViewCountIncrement() {
		if (_enableViewCountIncrement != null) {
			return _enableViewCountIncrement;
		}

		if (Validator.isNotNull(
				_journalContentPortletInstanceConfiguration.
					enableViewCountIncrement())) {

			_enableViewCountIncrement = GetterUtil.getBoolean(
				_journalContentPortletInstanceConfiguration.
					enableViewCountIncrement());
		}
		else {
			_enableViewCountIncrement =
				PropsValues.ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED;
		}

		return _enableViewCountIncrement;
	}

	public boolean isExpired() {
		if (_expired != null) {
			return _expired;
		}

		JournalArticle article = getArticle();

		_expired = article.isExpired();

		if (!_expired) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(new Date())) {
				_expired = true;
			}
		}

		return _expired;
	}

	public boolean isPrint() {
		if (_print != null) {
			return _print;
		}

		_print = false;

		String viewMode = ParamUtil.getString(_portletRequest, "viewMode");

		if (viewMode.equals(Constants.PRINT)) {
			_print = true;
		}

		return _print;
	}

	public boolean isShowAddArticleIcon() throws PortalException {
		if (_showAddArticleIcon != null) {
			return _showAddArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_showAddArticleIcon = false;

		if (!isShowSelectArticleIcon()) {
			return _showAddArticleIcon;
		}

		_showAddArticleIcon = JournalPermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ActionKeys.ADD_ARTICLE);

		return _showAddArticleIcon;
	}

	public boolean isShowArticle() throws PortalException {
		if (_showArticle != null) {
			return _showArticle;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			_showArticle = false;

			return _showArticle;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if (articleDisplay == null) {
			_showArticle = false;

			return _showArticle;
		}

		if (!hasViewPermission() || isExpired() || article.isScheduled() ||
			article.isPending()) {

			_showArticle = false;

			return _showArticle;
		}

		_showArticle = true;

		return _showArticle;
	}

	public boolean isShowEditArticleIcon() throws PortalException {
		if (_showEditArticleIcon != null) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		_showEditArticleIcon = false;

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_showEditArticleIcon = JournalArticlePermission.contains(
			themeDisplay.getPermissionChecker(), latestArticle.getGroupId(),
			latestArticle.getArticleId(), ActionKeys.UPDATE);

		return _showEditArticleIcon;
	}

	public boolean isShowEditTemplateIcon() throws PortalException {
		if (_showEditTemplateIcon != null) {
			return _showEditTemplateIcon;
		}

		_showEditTemplateIcon = false;

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return _showEditTemplateIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_showEditTemplateIcon = DDMTemplatePermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ddmTemplate, portletDisplay.getId(), ActionKeys.UPDATE);

		return _showEditTemplateIcon;
	}

	public boolean isShowIconsActions() throws PortalException {
		if (_showIconsActions != null) {
			return _showIconsActions;
		}

		_showIconsActions = false;

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn() || isPrint() || !hasViewPermission()) {
			return _showIconsActions;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			return _showIconsActions;
		}

		Group group = themeDisplay.getSiteGroup();

		if (group.hasLocalOrRemoteStagingGroup()) {
			return _showIconsActions;
		}

		if (isShowAddArticleIcon() || isShowEditArticleIcon() ||
			isShowEditTemplateIcon() || isShowSelectArticleIcon()) {

			_showIconsActions = true;
		}

		return _showIconsActions;
	}

	public boolean isShowSelectArticleIcon() throws PortalException {
		if (_showSelectArticleIcon != null) {
			return _showSelectArticleIcon;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_showSelectArticleIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);

		return _showSelectArticleIcon;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentDisplayContext.class);

	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private Long _articleGroupId;
	private String _articleId;
	private List<ContentMetadataAssetAddonEntry>
		_contentMetadataAssetAddonEntries;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private List<DDMTemplate> _ddmTemplates;
	private Boolean _enableViewCountIncrement;
	private Boolean _expired;
	private Boolean _hasViewPermission;
	private final JournalContentPortletInstanceConfiguration
		_journalContentPortletInstanceConfiguration;
	private JournalArticle _latestArticle;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private Boolean _print;
	private Boolean _showAddArticleIcon;
	private Boolean _showArticle;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showIconsActions;
	private Boolean _showSelectArticleIcon;
	private List<UserToolAssetAddonEntry> _userToolAssetAddonEntries;

}