/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.ValueMapper;

/**
 * @author Brian Wing Shun Chan
 */
public class AvailableMappersUtil {

	public static ValueMapper getBlogsEntryIdMapper() {
		return _blogsEntryIdMapper;
	}

	public static void setBlogsEntryIdMapper(ValueMapper blogsEntryIdMapper) {
		_blogsEntryIdMapper = blogsEntryIdMapper;
	}

	public static ValueMapper getBookmarksEntryIdMapper() {
		return _bookmarksEntryIdMapper;
	}

	public static void setBookmarksEntryIdMapper(
		ValueMapper bookmarksEntryIdMapper) {

		_bookmarksEntryIdMapper = bookmarksEntryIdMapper;
	}

	public static ValueMapper getBookmarksFolderIdMapper() {
		return _bookmarksFolderIdMapper;
	}

	public static void setBookmarksFolderIdMapper(
		ValueMapper bookmarksFolderIdMapper) {

		_bookmarksFolderIdMapper = bookmarksFolderIdMapper;
	}

	public static ValueMapper getCalEventIdMapper() {
		return _calEventIdMapper;
	}

	public static void setCalEventIdMapper(ValueMapper calEventIdMapper) {
		_calEventIdMapper = calEventIdMapper;
	}

	public static ValueMapper getCompanyIdMapper() {
		return _companyIdMapper;
	}

	public static void setCompanyIdMapper(ValueMapper companyIdMapper) {
		_companyIdMapper = companyIdMapper;
	}

	public static ValueMapper getContactIdMapper() {
		return _contactIdMapper;
	}

	public static void setContactIdMapper(ValueMapper contactIdMapper) {
		_contactIdMapper = contactIdMapper;
	}

	public static ValueMapper getDLFileEntryIdMapper() {
		return _dlFileEntryIdMapper;
	}

	public static void setDLFileEntryIdMapper(ValueMapper dlFileEntryIdMapper) {
		_dlFileEntryIdMapper = dlFileEntryIdMapper;
	}

	public static ValueMapper getDLFileShortcutIdMapper() {
		return _dlFileShortcutIdMapper;
	}

	public static void setDLFileShortcutIdMapper(
		ValueMapper dlFileShortcutIdMapper) {

		_dlFileShortcutIdMapper = dlFileShortcutIdMapper;
	}

	public static ValueMapper getDLFolderIdMapper() {
		return _dlFolderIdMapper;
	}

	public static void setDLFolderIdMapper(ValueMapper dlFolderIdMapper) {
		_dlFolderIdMapper = dlFolderIdMapper;
	}

	public static ValueMapper getGroupIdMapper() {
		return _groupIdMapper;
	}

	public static void setGroupIdMapper(ValueMapper groupIdMapper) {
		_groupIdMapper = groupIdMapper;
	}

	public static ValueMapper getIGFolderIdMapper() {
		return _igFolderIdMapper;
	}

	public static void setIGFolderIdMapper(ValueMapper igFolderIdMapper) {
		_igFolderIdMapper = igFolderIdMapper;
	}

	public static ValueMapper getIGImageIdMapper() {
		return _igImageIdMapper;
	}

	public static void setIGImageIdMapper(ValueMapper igImageIdMapper) {
		_igImageIdMapper = igImageIdMapper;
	}

	public static ValueMapper getImageIdMapper() {
		return _imageIdMapper;
	}

	public static void setImageIdMapper(ValueMapper imageIdMapper) {
		_imageIdMapper = imageIdMapper;
	}

	public static ValueMapper getJournalArticleIdMapper() {
		return _journalArticleIdMapper;
	}

	public static void setJournalArticleIdMapper(
		ValueMapper journalArticleIdMapper) {

		_journalArticleIdMapper = journalArticleIdMapper;
	}

	public static ValueMapper getJournalStructureIdMapper() {
		return _journalStructureIdMapper;
	}

	public static void setJournalStructureIdMapper(
		ValueMapper journalStructureIdMapper) {

		_journalStructureIdMapper = journalStructureIdMapper;
	}

	public static ValueMapper getJournalTemplateIdMapper() {
		return _journalTemplateIdMapper;
	}

	public static void setJournalTemplateIdMapper(
		ValueMapper journalTemplateIdMapper) {

		_journalTemplateIdMapper = journalTemplateIdMapper;
	}

	public static ValueMapper getLayoutPlidMapper() {
		return _layoutPlidMapper;
	}

	public static void setLayoutPlidMapper(ValueMapper layoutPlidMapper) {
		_layoutPlidMapper = layoutPlidMapper;
	}

	public static ValueMapper getMBCategoryIdMapper() {
		return _mbCategoryIdMapper;
	}

	public static void setMBCategoryIdMapper(ValueMapper mbCategoryIdMapper) {
		_mbCategoryIdMapper = mbCategoryIdMapper;
	}

	public static ValueMapper getMBMessageIdMapper() {
		return _mbMessageIdMapper;
	}

	public static void setMBMessageIdMapper(ValueMapper mbMessageIdMapper) {
		_mbMessageIdMapper = mbMessageIdMapper;
	}

	public static ValueMapper getMBThreadIdMapper() {
		return _mbThreadIdMapper;
	}

	public static void setMBThreadIdMapper(ValueMapper mbThreadIdMapper) {
		_mbThreadIdMapper = mbThreadIdMapper;
	}

	public static ValueMapper getOrganizationIdMapper() {
		return _organizationIdMapper;
	}

	public static void setOrganizationIdMapper(
		ValueMapper organizationIdMapper) {

		_organizationIdMapper = organizationIdMapper;
	}

	public static ValueMapper getPollsQuestionIdMapper() {
		return _pollsQuestionIdMapper;
	}

	public static void setPollsQuestionIdMapper(
		ValueMapper pollsQuestionIdMapper) {

		_pollsQuestionIdMapper = pollsQuestionIdMapper;
	}

	public static ValueMapper getRoleIdMapper() {
		return _roleIdMapper;
	}

	public static void setRoleIdMapper(ValueMapper roleIdMapper) {
		_roleIdMapper = roleIdMapper;
	}

	public static ValueMapper getShoppingCategoryIdMapper() {
		return _shoppingCategoryIdMapper;
	}

	public static void setShoppingCategoryIdMapper(
		ValueMapper shoppingCategoryIdMapper) {

		_shoppingCategoryIdMapper = shoppingCategoryIdMapper;
	}

	public static ValueMapper getShoppingItemIdMapper() {
		return _shoppingItemIdMapper;
	}

	public static void setShoppingItemIdMapper(
		ValueMapper shoppingItemIdMapper) {

		_shoppingItemIdMapper = shoppingItemIdMapper;
	}

	public static ValueMapper getUserGroupIdMapper() {
		return _userGroupIdMapper;
	}

	public static void setUserGroupIdMapper(ValueMapper userGroupIdMapper) {
		_userGroupIdMapper = userGroupIdMapper;
	}

	public static ValueMapper getUserIdMapper() {
		return _userIdMapper;
	}

	public static void setUserIdMapper(ValueMapper userIdMapper) {
		_userIdMapper = userIdMapper;
	}

	public static ValueMapper getWikiNodeIdMapper() {
		return _wikiNodeIdMapper;
	}

	public static void setWikiNodeIdMapper(ValueMapper wikiNodeIdMapper) {
		_wikiNodeIdMapper = wikiNodeIdMapper;
	}

	public static ValueMapper getWikiPageIdMapper() {
		return _wikiPageIdMapper;
	}

	public static void setWikiPageIdMapper(ValueMapper wikiPageIdMapper) {
		_wikiPageIdMapper = wikiPageIdMapper;
	}

	private static ValueMapper _blogsEntryIdMapper;
	private static ValueMapper _bookmarksEntryIdMapper;
	private static ValueMapper _bookmarksFolderIdMapper;
	private static ValueMapper _calEventIdMapper;
	private static ValueMapper _companyIdMapper;
	private static ValueMapper _contactIdMapper;
	private static ValueMapper _dlFileEntryIdMapper;
	private static ValueMapper _dlFileShortcutIdMapper;
	private static ValueMapper _dlFolderIdMapper;
	private static ValueMapper _groupIdMapper;
	private static ValueMapper _igFolderIdMapper;
	private static ValueMapper _igImageIdMapper;
	private static ValueMapper _imageIdMapper;
	private static ValueMapper _journalArticleIdMapper;
	private static ValueMapper _journalStructureIdMapper;
	private static ValueMapper _journalTemplateIdMapper;
	private static ValueMapper _layoutPlidMapper;
	private static ValueMapper _mbCategoryIdMapper;
	private static ValueMapper _mbMessageIdMapper;
	private static ValueMapper _mbThreadIdMapper;
	private static ValueMapper _organizationIdMapper;
	private static ValueMapper _pollsQuestionIdMapper;
	private static ValueMapper _roleIdMapper;
	private static ValueMapper _shoppingCategoryIdMapper;
	private static ValueMapper _shoppingItemIdMapper;
	private static ValueMapper _userGroupIdMapper;
	private static ValueMapper _userIdMapper;
	private static ValueMapper _wikiNodeIdMapper;
	private static ValueMapper _wikiPageIdMapper;

}