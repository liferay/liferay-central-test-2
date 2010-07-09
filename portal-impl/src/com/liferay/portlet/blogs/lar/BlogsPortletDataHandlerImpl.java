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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;

import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class BlogsPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _categories, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _categories, _comments, _ratings, _tags, _wordpress
		};
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				BlogsPortletDataHandlerImpl.class, "deleteData")) {

			BlogsEntryLocalServiceUtil.deleteEntries(context.getScopeGroupId());
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.blogs", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("blogs-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		List<BlogsEntry> entries = BlogsEntryUtil.findByGroupId(
			context.getScopeGroupId());

		for (BlogsEntry entry : entries) {
			exportEntry(context, rootElement, entry);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.blogs", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		for (Element entryElement : rootElement.elements("entry")) {
			String path = entryElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			BlogsEntry entry = (BlogsEntry)context.getZipEntryAsObject(path);

			importEntry(context, entry);
		}

		if (context.getBooleanParameter(_NAMESPACE, "wordpress")) {
			WordPressImporter.importData(context);
		}

		return null;
	}

	protected void exportEntry(
			PortletDataContext context, Element root, BlogsEntry entry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (entry.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		String path = getEntryPath(context, entry);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element entryElement = root.addElement("entry");

		entryElement.addAttribute("path", path);

		context.addPermissions(BlogsEntry.class, entry.getEntryId());

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			context.addAssetCategories(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addAssetTags(BlogsEntry.class, entry.getEntryId());
		}

		entry.setUserUuid(entry.getUserUuid());

		context.addZipEntry(path, entry);
	}

	protected String getEntryPath(
		PortletDataContext context, BlogsEntry entry) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEntry(PortletDataContext context, BlogsEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = entry.isAllowPingbacks();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());
		int status = entry.getStatus();

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				BlogsEntry.class, entry.getEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(entry.getCreateDate());
		serviceContext.setModifiedDate(entry.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if (status != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		BlogsEntry importedEntry = null;

		if (context.isDataStrategyMirror()) {
			BlogsEntry existingEntry = BlogsEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BlogsEntryLocalServiceUtil.addEntry(
					userId, entry.getTitle(), entry.getContent(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, allowPingbacks,
					allowTrackbacks, trackbacks, serviceContext);
			}
			else {
				importedEntry = BlogsEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(), entry.getTitle(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					allowPingbacks, allowTrackbacks, trackbacks,
					serviceContext);
			}
		}
		else {
			importedEntry = BlogsEntryLocalServiceUtil.addEntry(
				userId, entry.getTitle(), entry.getContent(), displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				serviceContext);
		}

		context.importPermissions(
			BlogsEntry.class, entry.getEntryId(), importedEntry.getEntryId());

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				BlogsEntry.class, entry.getEntryId(),
				importedEntry.getEntryId(), context.getScopeGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				BlogsEntry.class, entry.getEntryId(),
				importedEntry.getEntryId());
		}
	}

	private static final String _NAMESPACE = "blogs";

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static PortletDataHandlerBoolean _wordpress =
		new PortletDataHandlerBoolean(_NAMESPACE, "wordpress");

}