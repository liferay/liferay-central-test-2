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

package com.liferay.knowledge.base.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.knowledge.base.web.configuration.KBSectionPortletInstanceConfiguration",
	localization = "content/Language",
	name = "knowledge.base.section.portlet.instance.configuration.name"
)
public interface KBSectionPortletInstanceConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean showKBArticlesSectionsTitle();

	@Meta.AD(deflt = "general", required = false)
	public String[] kbArticlesSections();

	@Meta.AD(deflt = "title", required = false)
	public String kbArticleDisplayStyle();

	@Meta.AD(deflt = "maximized", required = false)
	public String kbArticleWindowState();

	@Meta.AD(deflt = "modified-date", required = false)
	public String kbArticlesOrderByCol();

	@Meta.AD(deflt = "desc", required = false)
	public String kbArticlesOrderByType();

	@Meta.AD(deflt = "10", required = false)
	public int kbArticlesDelta();

	@Meta.AD(deflt = "true", required = false)
	public boolean showKBArticlesPagination();

	@Meta.AD(deflt = "false", required = false)
	public boolean enableKBArticleDescription();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleRatings();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleKBComments();

	@Meta.AD(deflt = "true", required = false)
	public boolean showKBArticleAttachments();

	@Meta.AD(deflt = "false", required = false)
	public boolean showKBArticleKBComments();

	@Meta.AD(deflt = "thumbs", required = false)
	public String kbArticleRatingsType();

	@Meta.AD(deflt = "true", required = false)
	public boolean showKBArticleAssetEntries();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleAssetLinks();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleViewCountIncrement();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleSubscriptions();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticleHistory();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableKBArticlePrint();

	@Meta.AD(deflt = "false", required = false)
	public boolean enableSocialBookmarks();

	@Meta.AD(deflt = "menu", required = false)
	public String socialBookmarksDisplayStyle();

	@Meta.AD(deflt = "bottom", required = false)
	public String socialBookmarksDisplayPosition();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/social.bookmark.types}",
		required = false
	)
	public String socialBookmarksTypes();

	@Meta.AD(required = false)
	public String[] adminKBArticleSections();
}