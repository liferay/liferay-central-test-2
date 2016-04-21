/*
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

package com.liferay.knowledge.base.web.social;

import com.liferay.knowledge.base.constants.ActionKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBCommentLocalServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateLocalServiceUtil;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.knowledge.base.service.permission.KBTemplatePermission;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getEntryTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		String title = StringPool.BLANK;

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

			title = kbArticle.getTitle();
		}
		else if (className.equals(KBComment.class.getName())) {
			KBComment kbComment = KBCommentLocalServiceUtil.getKBComment(
				activity.getClassPK());

			String kbCommentClassName = kbComment.getClassName();

			if (kbCommentClassName.equals(KBArticle.class.getName())) {
				KBArticle kbArticle =
					KBArticleLocalServiceUtil.getLatestKBArticle(
						kbComment.getClassPK(),
						WorkflowConstants.STATUS_APPROVED);

				title = kbArticle.getTitle();
			}
			else if (kbCommentClassName.equals(KBTemplate.class.getName())) {
				KBTemplate kbTemplate =
					KBTemplateLocalServiceUtil.getKBTemplate(
						kbComment.getClassPK());

				title = kbTemplate.getTitle();
			}
		}
		else if (className.equals(KBTemplate.class.getName())) {
			KBTemplate kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
				activity.getClassPK());

			title = kbTemplate.getTitle();
		}

		return getJSONValue(activity.getExtraData(), "title", title);
	}

	@Override
	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

			return KnowledgeBaseUtil.getKBArticleURL(
				serviceContext.getPlid(), kbArticle.getResourcePrimKey(),
				kbArticle.getStatus(), serviceContext.getPortalURL(), false);
		}
		else if (className.equals(KBComment.class.getName())) {
			KBComment kbComment = KBCommentLocalServiceUtil.getKBComment(
				activity.getClassPK());

			String kbCommentClassName = kbComment.getClassName();

			if (kbCommentClassName.equals(KBArticle.class.getName())) {
				KBArticle kbArticle =
					KBArticleLocalServiceUtil.getLatestKBArticle(
						activity.getClassPK(),
						WorkflowConstants.STATUS_APPROVED);

				return KnowledgeBaseUtil.getKBArticleURL(
					serviceContext.getPlid(), kbArticle.getResourcePrimKey(),
					kbArticle.getStatus(), serviceContext.getPortalURL(),
					false);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			if (activity.getType() == KBActivityKeys.ADD_KB_ARTICLE) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-add-kb-article";
				}
				else {
					return "activity-knowledge-base-admin-add-kb-article-in";
				}
			}
			else if (activity.getType() == KBActivityKeys.MOVE_KB_ARTICLE) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-move-kb-article";
				}
				else {
					return "activity-knowledge-base-admin-move-kb-article-in";
				}
			}
			else if (activity.getType() == KBActivityKeys.UPDATE_KB_ARTICLE) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-update-kb-article";
				}
				else {
					return "activity-knowledge-base-admin-update-kb-article-in";
				}
			}
		}
		else if (className.equals(KBComment.class.getName())) {
			if (activity.getType() == KBActivityKeys.ADD_KB_COMMENT) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-add-kb-comment";
				}
				else {
					return "activity-knowledge-base-admin-add-kb-comment-in";
				}
			}
			else if (activity.getType() == KBActivityKeys.UPDATE_KB_COMMENT) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-update-kb-comment";
				}
				else {
					return "activity-knowledge-base-admin-update-kb-comment-in";
				}
			}
		}
		else if (className.equals(KBTemplate.class.getName())) {
			if (activity.getType() == KBActivityKeys.ADD_KB_TEMPLATE) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-add-kb-template";
				}
				else {
					return "activity-knowledge-base-admin-add-kb-template-in";
				}
			}
			else if (activity.getType() == KBActivityKeys.UPDATE_KB_TEMPLATE) {
				if (Validator.isNull(groupName)) {
					return "activity-knowledge-base-admin-update-kb-template";
				}
				else {
					return
						"activity-knowledge-base-admin-update-kb-template-in";
				}
			}
		}

		return StringPool.BLANK;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

			return KBArticlePermission.contains(
				permissionChecker, kbArticle, ActionKeys.VIEW);
		}
		else if (className.equals(KBComment.class.getName())) {
			return true;
		}
		else if (className.equals(KBTemplate.class.getName())) {
			KBTemplate kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
				activity.getClassPK());

			return KBTemplatePermission.contains(
				permissionChecker, kbTemplate, ActionKeys.VIEW);
		}

		return false;
	}

	private static final String[] _CLASS_NAMES = {
		KBArticle.class.getName(), KBComment.class.getName(),
		KBTemplate.class.getName()
	};

	private final ResourceBundleLoader _resourceBundleLoader =
		new ClassResourceBundleLoader(
			"content.Language", KBActivityInterpreter.class);

}