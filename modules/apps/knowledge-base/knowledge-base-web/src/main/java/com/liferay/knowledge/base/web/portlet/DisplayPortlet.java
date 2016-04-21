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

package com.liferay.knowledge.base.web.portlet;

import com.liferay.knowledge.base.constants.ActionKeys;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.PortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBCommentService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.knowledge.base.web.constants.WebKeys;
import com.liferay.knowledge.base.web.selector.KBArticleSelection;
import com.liferay.knowledge.base.web.selector.KBArticleSelector;
import com.liferay.knowledge.base.web.selector.KBArticleSelectorFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=knowledge-base-portlet knowledge-base-portlet-display",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.header-portlet-css=/admin/css/common.css,/display/css/main.css",
		"com.liferay.portlet.icon=/icons/display.png",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Knowledge Base Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/display/",
		"javax.portlet.init-param.view-template=/display/view.jsp",
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-display-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DisplayPortlet extends BaseKBPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			KBArticleSelection kbArticleSelection = getKBArticle(renderRequest);

			KBArticle kbArticle = kbArticleSelection.getKBArticle();

			int status = getStatus(renderRequest, kbArticle);

			renderRequest.setAttribute(WebKeys.KNOWLEDGE_BASE_STATUS, status);

			if ((kbArticle != null) && (kbArticle.getStatus() != status)) {
				kbArticle = _kbArticleLocalService.fetchLatestKBArticle(
					kbArticle.getResourcePrimKey(), status);
			}

			renderRequest.setAttribute(
				WebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);
			renderRequest.setAttribute(
				WebKeys.KNOWLEDGE_BASE_EXACT_MATCH,
				kbArticleSelection.isExactMatch());
			renderRequest.setAttribute(
				WebKeys.KNOWLEDGE_BASE_SEARCH_KEYWORDS,
				kbArticleSelection.getKeywords());

			if (!kbArticleSelection.isExactMatch()) {
				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(renderResponse);

				response.setStatus(404);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				SessionMessages.add(
					renderRequest,
					PortalUtil.getPortletId(renderRequest) +
						SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	public void updateRootKBFolderId(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		long kbFolderId = ParamUtil.getLong(actionRequest, "rootKBFolderId");

		if (kbFolderId == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		KBFolder kbFolder = _kbFolderService.getKBFolder(kbFolderId);

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(
				PortalUtil.getLiferayPortletRequest(actionRequest));

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String contentRootPrefix = GetterUtil.getString(
			portletPreferences.getValue("contentRootPrefix", null));

		String previousPreferredKBFolderURLTitle =
			KnowledgeBaseUtil.getPreferredKBFolderURLTitle(
				portalPreferences, contentRootPrefix);

		KnowledgeBaseUtil.setPreferredKBFolderURLTitle(
			portalPreferences, contentRootPrefix, kbFolder.getUrlTitle());

		String urlTitle = ParamUtil.getString(actionRequest, "urlTitle");

		KBArticle kbArticle = null;

		if (Validator.isNotNull(urlTitle)) {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				kbFolder.getGroupId(), kbFolder.getUrlTitle(), urlTitle);

			if ((kbArticle == null) &&
				Validator.isNull(previousPreferredKBFolderURLTitle)) {

				kbArticle = findClosestMatchingKBArticle(
					kbFolder.getGroupId(), previousPreferredKBFolderURLTitle,
					kbFolder.getKbFolderId(), urlTitle);
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((kbArticle != null) &&
			!KBArticlePermission.contains(
				themeDisplay.getPermissionChecker(), kbArticle,
				ActionKeys.VIEW)) {

			kbArticle = null;
		}

		PortletURL redirectURL = _portletURLFactory.create(
			actionRequest, PortletKeys.KNOWLEDGE_BASE_DISPLAY,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		redirectURL.setParameter("kbFolderUrlTitle", kbFolder.getUrlTitle());

		if (kbArticle != null) {
			redirectURL.setParameter("urlTitle", kbArticle.getUrlTitle());
		}

		actionResponse.sendRedirect(redirectURL.toString());
	}

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (actionName.equals("deleteKBArticle") ||
			actionName.equals("updateKBComment") ||
			actionName.equals("updateRootKBFolderId")) {

			return;
		}

		super.addSuccessMessage(actionRequest, actionResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchArticleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchCommentException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchSubscriptionException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected KBArticle findClosestMatchingKBArticle(
			long groupId, String oldKBFolderURLTitle, long newKBFolderId,
			String urlTitle)
		throws PortalException {

		KBArticle oldKBArticle =
			_kbArticleLocalService.fetchKBArticleByUrlTitle(
				groupId, oldKBFolderURLTitle, urlTitle);

		KBArticle kbArticle = null;

		while ((kbArticle == null) && (oldKBArticle != null)) {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				groupId, newKBFolderId, oldKBArticle.getUrlTitle());

			if (kbArticle == null) {
				oldKBArticle = oldKBArticle.getParentKBArticle();
			}
		}

		if (kbArticle == null) {
			List<KBArticle> kbArticles = _kbArticleLocalService.getKBArticles(
				groupId, newKBFolderId, WorkflowConstants.STATUS_APPROVED, 0, 1,
				new KBArticlePriorityComparator(true));

			if (!kbArticles.isEmpty()) {
				kbArticle = kbArticles.get(0);
			}
		}

		return kbArticle;
	}

	@Override
	protected Http getHttp() {
		return _http;
	}

	@Override
	protected JSONFactory getJSONFactory() {
		return _jsonFactory;
	}

	protected KBArticleSelection getKBArticle(RenderRequest renderRequest)
		throws PortalException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (mvcPath.endsWith("/edit_article.jsp") ||
			mvcPath.endsWith("/history.jsp") ||
			mvcPath.endsWith("/print_article.jsp")) {

			long resourcePrimKey = ParamUtil.getLong(
				renderRequest, "resourcePrimKey");

			if (resourcePrimKey == 0) {
				return new KBArticleSelection(null, false);
			}

			KBArticle latestKBArticle =
				_kbArticleLocalService.getLatestKBArticle(
					resourcePrimKey, WorkflowConstants.STATUS_ANY);

			return new KBArticleSelection(latestKBArticle, true);
		}

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		long kbFolderClassNameId = _classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourcePrimKey = GetterUtil.getLong(
			portletPreferences.getValue("resourcePrimKey", null));
		long parentResourceClassNameId = GetterUtil.getLong(
			portletPreferences.getValue("resourceClassNameId", null),
			kbFolderClassNameId);

		KBArticleSelector kbArticleSelector =
			KBArticleSelectorFactoryUtil.getKBArticleSelector(
				parentResourceClassNameId);

		String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

		String preferredKBFolderURLTitle = getPreferredKBFolderUrlTitle(
			renderRequest, portletPreferences);

		if (Validator.isNotNull(urlTitle)) {
			String kbFolderUrlTitle = ParamUtil.getString(
				renderRequest, "kbFolderUrlTitle");

			return kbArticleSelector.findByUrlTitle(
				PortalUtil.getScopeGroupId(renderRequest),
				preferredKBFolderURLTitle, parentResourcePrimKey,
				kbFolderUrlTitle, urlTitle);
		}

		long resourcePrimKey = ParamUtil.getLong(
			renderRequest, "resourcePrimKey",
			KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY);

		return kbArticleSelector.findByResourcePrimKey(
			PortalUtil.getScopeGroupId(renderRequest),
			preferredKBFolderURLTitle, parentResourcePrimKey, resourcePrimKey);
	}

	@Override
	protected KBArticleService getKBArticleService() {
		return _kbArticleService;
	}

	@Override
	protected KBCommentLocalService getKBCommentLocalService() {
		return _kbCommentLocalService;
	}

	@Override
	protected KBCommentService getKBCommentService() {
		return _kbCommentService;
	}

	@Override
	protected KBFolderService getKBFolderService() {
		return _kbFolderService;
	}

	@Override
	protected Portal getPortal() {
		return _portal;
	}

	protected String getPreferredKBFolderUrlTitle(
			RenderRequest renderRequest, PortletPreferences portletPreferences)
		throws PortalException {

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(renderRequest);

		String contentRootPrefix = GetterUtil.getString(
			portletPreferences.getValue("contentRootPrefix", null));

		return KnowledgeBaseUtil.getPreferredKBFolderURLTitle(
			portalPreferences, contentRootPrefix);
	}

	protected int getStatus(RenderRequest renderRequest, KBArticle kbArticle)
		throws Exception {

		if (kbArticle == null) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (KBArticlePermission.contains(
				themeDisplay.getPermissionChecker(), kbArticle,
				ActionKeys.UPDATE)) {

			return ParamUtil.getInteger(
				renderRequest, "status", WorkflowConstants.STATUS_ANY);
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setHttp(Http http) {
		_http = http;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBArticleService(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentLocalService(
		KBCommentLocalService kbCommentLocalService) {

		_kbCommentLocalService = kbCommentLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentService(KBCommentService kbCommentService) {
		_kbCommentService = kbCommentService;
	}

	@Reference(unbind = "-")
	protected void setKBFolderService(KBFolderService kbFolderService) {
		_kbFolderService = kbFolderService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesFactory(
		PortletPreferencesFactory portletPreferencesFactory) {

		_portletPreferencesFactory = portletPreferencesFactory;
	}

	@Reference(unbind = "-")
	protected void setPortletURLFactory(PortletURLFactory portletURLFactory) {
		_portletURLFactory = portletURLFactory;
	}

	private ClassNameLocalService _classNameLocalService;
	private Http _http;
	private JSONFactory _jsonFactory;
	private KBArticleLocalService _kbArticleLocalService;
	private KBArticleService _kbArticleService;
	private KBCommentLocalService _kbCommentLocalService;
	private KBCommentService _kbCommentService;
	private KBFolderService _kbFolderService;
	private Portal _portal;
	private PortletPreferencesFactory _portletPreferencesFactory;
	private PortletURLFactory _portletURLFactory;

}