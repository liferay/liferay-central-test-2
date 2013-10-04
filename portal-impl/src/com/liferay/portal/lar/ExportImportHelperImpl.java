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

package com.liferay.portal.lar;

import com.liferay.portal.LARFileException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelper;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.SystemEventActionableDynamicQuery;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.apache.xerces.parsers.SAXParser;

import org.xml.sax.InputSource;

/**
 * @author Zsolt Berentey
 * @author Levente Hudák
 * @author Julio Camarero
 */
public class ExportImportHelperImpl implements ExportImportHelper {

	@Override
	public Calendar getCalendar(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int dateMonth = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(portletRequest, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(
			portletRequest, paramPrefix + "AmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = themeDisplay.getLocale();
			timeZone = themeDisplay.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

		calendar.set(Calendar.MONTH, dateMonth);
		calendar.set(Calendar.DATE, dateDay);
		calendar.set(Calendar.YEAR, dateYear);
		calendar.set(Calendar.HOUR_OF_DAY, dateHour);
		calendar.set(Calendar.MINUTE, dateMinute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	@Override
	public DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId)
		throws Exception {

		Date startDate = null;
		Date endDate = null;

		String range = ParamUtil.getString(portletRequest, "range");

		if (range.equals("dateRange")) {
			Calendar startCalendar = getCalendar(
				portletRequest, "startDate", true);

			startDate = startCalendar.getTime();

			Calendar endCalendar = getCalendar(portletRequest, "endDate", true);

			endDate = endCalendar.getTime();
		}
		else if (range.equals("fromLastPublishDate")) {
			if (Validator.isNotNull(portletId) && (plid > 0)) {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				PortletPreferences preferences =
					PortletPreferencesFactoryUtil.getPortletSetup(
						layout, portletId, StringPool.BLANK);

				long lastPublishDate = GetterUtil.getLong(
					preferences.getValue(
						"last-publish-date", StringPool.BLANK));

				if (lastPublishDate > 0) {
					endDate = new Date();

					startDate = new Date(lastPublishDate);
				}
			}
			else {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				long lastPublishDate = GetterUtil.getLong(
					layoutSet.getSettingsProperty("last-publish-date"));

				if (lastPublishDate > 0) {
					endDate = new Date();

					startDate = new Date(lastPublishDate);
				}
			}
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(portletRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		return new DateRange(startDate, endDate);
	}

	@Override
	public Layout getExportableLayout(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			return layout;
		}

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isLayout()) {
			layout = LayoutLocalServiceUtil.getLayout(scopeGroup.getClassPK());
		}
		else if (!scopeGroup.isCompany()) {
			long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(
				themeDisplay.getSiteGroupId());

			if (defaultPlid > 0) {
				layout = LayoutLocalServiceUtil.getLayout(defaultPlid);
			}
		}

		return layout;
	}

	@Override
	public String getExportableRootPortletId(long companyId, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			return null;
		}

		return PortletConstants.getRootPortletId(portletId);
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap(PortletRequest portletRequest)
		throws Exception {

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

		String layoutIdsJSON = ParamUtil.getString(portletRequest, "layoutIds");

		if (Validator.isNull(layoutIdsJSON)) {
			return layoutIdMap;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutIdsJSON);

		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long plid = jsonObject.getLong("plid");
			boolean includeChildren = jsonObject.getBoolean("includeChildren");

			layoutIdMap.put(plid, includeChildren);
		}

		return layoutIdMap;
	}

	@Override
	public long[] getLayoutIds(List<Layout> layouts) {
		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = layouts.get(i);

			layoutIds[i] = layout.getLayoutId();
		}

		return layoutIds;
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, parameterMap,
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		final ManifestSummary manifestSummary = new ManifestSummary();

		SAXParser saxParser = new SAXParser();

		ElementHandler elementHandler = new ElementHandler(
			new ManifestSummaryElementProcessor(group, manifestSummary),
			new String[] {"header", "portlet", "staged-model"});

		saxParser.setContentHandler(elementHandler);

		InputStream is = portletDataContext.getZipEntryAsInputStream(
			"/manifest.xml");

		if (is == null) {
			throw new LARFileException("manifest.xml is not in the LAR");
		}

		String manifestXMLContent = StringUtil.read(is);

		saxParser.parse(new InputSource(new StringReader(manifestXMLContent)));

		return manifestSummary;
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		File file = FileUtil.createTempFile("lar");
		InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(
			userId, fileEntry.getFileEntryId(), fileEntry.getVersion(), false);

		ManifestSummary manifestSummary = null;

		try {
			FileUtil.write(file, inputStream);

			manifestSummary = getManifestSummary(
				userId, groupId, parameterMap, file);
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			FileUtil.delete(file);
		}

		return manifestSummary;
	}

	@Override
	public long getModelDeletionCount(
			final PortletDataContext portletDataContext,
			final StagedModelType stagedModelType)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new SystemEventActionableDynamicQuery() {

			protected void addCreateDateProperty(DynamicQuery dynamicQuery) {
				if (!portletDataContext.hasDateRange()) {
					return;
				}

				Property createDateProperty = PropertyFactoryUtil.forName(
					"createDate");

				Date startDate = portletDataContext.getStartDate();

				dynamicQuery.add(createDateProperty.ge(startDate));

				Date endDate = portletDataContext.getEndDate();

				dynamicQuery.add(createDateProperty.le(endDate));
			}

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				Property groupIdProperty = PropertyFactoryUtil.forName(
					"groupId");

				disjunction.add(groupIdProperty.eq(0L));
				disjunction.add(
					groupIdProperty.eq(portletDataContext.getScopeGroupId()));

				dynamicQuery.add(disjunction);

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(
					classNameIdProperty.eq(stagedModelType.getClassNameId()));

				if (stagedModelType.getReferrerClassNameId() >= 0) {
					Property referrerClassNameIdProperty =
						PropertyFactoryUtil.forName("referrerClassNameId");

					dynamicQuery.add(
						referrerClassNameIdProperty.eq(
							stagedModelType.getReferrerClassNameId()));
				}

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(
					typeProperty.eq(SystemEventConstants.TYPE_DELETE));

				addCreateDateProperty(dynamicQuery);
			}

			@Override
			protected void performAction(Object object) {
			}

		};

		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		return actionableDynamicQuery.performCount();
	}

	@Override
	public FileEntry getTempFileEntry(
			long groupId, long userId, String folderName)
		throws PortalException, SystemException {

		String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(
			groupId, folderName);

		if (tempFileEntryNames.length == 0) {
			return null;
		}

		return TempFileUtil.getTempFile(
			groupId, userId, tempFileEntryNames[0], folderName);
	}

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		content = ExportImportHelperUtil.replaceExportLayoutReferences(
			portletDataContext, content, exportReferencedContent);
		content = ExportImportHelperUtil.replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);

		content = ExportImportHelperUtil.replaceExportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);

		Element groupElement = entityElement.getParent();

		String groupElementName = groupElement.getName();

		if (!groupElementName.equals(JournalArticle.class.getSimpleName())) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String contextPath = PortalUtil.getPathContext();

		String[] patterns = {
			contextPath.concat("/c/document_library/get_file?"),
			contextPath.concat("/documents/"),
			contextPath.concat("/image/image_gallery?")
		};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> dlReferenceParameters =
				getDLReferenceParameters(
					portletDataContext, content,
					beginPos + contextPath.length(), endPos);

			FileEntry fileEntry = getFileEntry(
				portletDataContext, dlReferenceParameters);

			if (fileEntry == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(dlReferenceParameters, "endPos");

			try {
				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, entityStagedModel, entityElement,
						fileEntry, FileEntry.class,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					portletDataContext.addReferenceElement(
						entityStagedModel, entityElement, fileEntry,
						FileEntry.class,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}

				String path = ExportImportPathUtil.getModelPath(fileEntry);

				sb.replace(beginPos, endPos, "[$dl-reference=" + path + "$]");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		StringBuilder sb = new StringBuilder(content);

		String[] patterns = {"href=", "[["};

		int beginPos = -1;
		int endPos = content.length();
		int offset = 0;

		while (true) {
			if (beginPos > -1) {
				endPos = beginPos - 1;
			}

			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			if (content.startsWith("href=", beginPos)) {
				offset = 5;

				char c = content.charAt(beginPos + offset);

				if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
					offset++;
				}
			}
			else if (content.charAt(beginPos) == CharPool.OPEN_BRACKET) {
				offset = 2;
			}

			endPos = StringUtil.indexOfAny(
				content, _LAYOUT_REFERENCE_STOP_CHARS, beginPos + offset,
				endPos);

			if (endPos == -1) {
				continue;
			}

			String url = content.substring(beginPos + offset, endPos);

			StringBundler urlSB = new StringBundler(5);

			try {
				url = replaceExportHostname(portletDataContext, url, urlSB);

				if (!url.startsWith(StringPool.SLASH)) {
					continue;
				}

				String pathContext = PortalUtil.getPathContext();

				if (pathContext.length() > 1) {
					if (!url.startsWith(pathContext)) {
						continue;
					}

					urlSB.append(DATA_HANDLER_PATH_CONTEXT);

					url = url.substring(pathContext.length());
				}

				if (!url.startsWith(StringPool.SLASH)) {
					continue;
				}

				int pos = url.indexOf(StringPool.SLASH, 1);

				String localePath = StringPool.BLANK;

				Locale locale = null;

				if (pos != -1) {
					localePath = url.substring(0, pos);

					locale = LocaleUtil.fromLanguageId(
						localePath.substring(1), true, false);
				}

				if (locale != null) {
					String urlWithoutLocale = url.substring(
						localePath.length());

					if (urlWithoutLocale.startsWith(
							_PRIVATE_GROUP_SERVLET_MAPPING) ||
						urlWithoutLocale.startsWith(
							_PRIVATE_USER_SERVLET_MAPPING) ||
						urlWithoutLocale.startsWith(
							_PUBLIC_GROUP_SERVLET_MAPPING)) {

						urlSB.append(localePath);

						url = urlWithoutLocale;
					}
				}

				if (url.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING)) {
					urlSB.append(DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING);

					url = url.substring(
						_PRIVATE_GROUP_SERVLET_MAPPING.length() - 1);
				}
				else if (url.startsWith(_PRIVATE_USER_SERVLET_MAPPING)) {
					urlSB.append(DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING);

					url = url.substring(
						_PRIVATE_USER_SERVLET_MAPPING.length() - 1);
				}
				else if (url.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING)) {
					urlSB.append(DATA_HANDLER_PUBLIC_SERVLET_MAPPING);

					url = url.substring(
						_PUBLIC_GROUP_SERVLET_MAPPING.length() - 1);
				}
				else {
					String urlSBString = urlSB.toString();

					LayoutSet layoutSet = null;

					if (urlSBString.contains(
							DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL) ||
						urlSBString.contains(
							DATA_HANDLER_PUBLIC_LAYOUT_SET_URL)) {

						layoutSet = group.getPublicLayoutSet();
					}
					else if (urlSBString.contains(
								DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL) ||
							 urlSBString.contains(
								DATA_HANDLER_PRIVATE_LAYOUT_SET_URL)) {

						layoutSet = group.getPrivateLayoutSet();
					}

					if (layoutSet == null) {
						continue;
					}

					boolean privateLayout = layoutSet.isPrivateLayout();

					LayoutFriendlyURL layoutFriendlyUrl =
						LayoutFriendlyURLLocalServiceUtil.
							fetchFirstLayoutFriendlyURL(
								group.getGroupId(), privateLayout, url);

					if (layoutFriendlyUrl == null) {
						continue;
					}

					if (privateLayout) {
						if (group.isUser()) {
							urlSB.append(
								DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING);
						}
						else {
							urlSB.append(
								DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING);
						}
					}
					else {
						urlSB.append(DATA_HANDLER_PUBLIC_SERVLET_MAPPING);
					}

					urlSB.append(DATA_HANDLER_GROUP_FRIENDLY_URL);

					continue;
				}

				String groupFriendlyURL = group.getFriendlyURL();

				if (url.equals(groupFriendlyURL) ||
					url.startsWith(groupFriendlyURL + StringPool.SLASH)) {

					urlSB.append(DATA_HANDLER_GROUP_FRIENDLY_URL);

					url = url.substring(groupFriendlyURL.length());
				}
			}
			finally {
				if (urlSB.length() > 0) {
					urlSB.append(url);

					url = urlSB.toString();
				}

				sb.replace(beginPos + offset, endPos, url);
			}
		}

		return sb.toString();
	}

	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long layoutId = GetterUtil.getLong(matcher.group(1));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutId);

				String oldLinkToLayout = matcher.group(0);

				StringBundler sb = new StringBundler(5);

				sb.append(type);
				sb.append(StringPool.AT);
				sb.append(layout.getUuid());
				sb.append(StringPool.AT);
				sb.append(layout.getFriendlyURL());

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type, sb.toString());

				oldLinksToLayout.add(oldLinkToLayout);
				newLinksToLayout.add(newLinkToLayout);

				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, entityStagedModel, layout,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					portletDataContext.addReferenceElement(
						entityStagedModel, entityElement, layout,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message =
						"Unable to get layout with ID " + layoutId +
							" in group " + portletDataContext.getScopeGroupId();

					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
					else {
						_log.debug(message, e);
					}
				}
			}
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		content = ExportImportHelperUtil.replaceImportLayoutReferences(
			portletDataContext, content, importReferencedContent);
		content = ExportImportHelperUtil.replaceImportLinksToLayouts(
			portletDataContext, content, importReferencedContent);

		content = ExportImportHelperUtil.replaceImportDLReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);

		return content;
	}

	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				entityElement, FileEntry.class,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

		for (Element referenceDataElement : referenceDataElements) {
			String fileEntryUUID = referenceDataElement.attributeValue("uuid");

			if (fileEntryUUID == null) {
				continue;
			}

			String path = referenceDataElement.attributeValue("path");

			if (!content.contains("[$dl-reference=" + path + "$]")) {
				continue;
			}

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fileEntry);

			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntry.class);

			long importedFileEntryId = MapUtil.getLong(
				fileEntryIds, fileEntry.getFileEntryId(),
				fileEntry.getFileEntryId());

			FileEntry importedFileEntry = null;

			try {
				importedFileEntry = DLAppLocalServiceUtil.getFileEntry(
					importedFileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to reference " + path);
				}

				continue;
			}

			String url = DLUtil.getPreviewURL(
				importedFileEntry, importedFileEntry.getFileVersion(), null,
				StringPool.BLANK, false, false);

			content = StringUtil.replace(
				content, "[$dl-reference=" + path + "$]", url);
		}

		return content;
	}

	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		String companyPortalURL = StringPool.BLANK;
		String privateLayoutSetPortalURL = StringPool.BLANK;
		String publicLayoutSetPortalURL = StringPool.BLANK;

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		LayoutSet privateLayoutSet = group.getPrivateLayoutSet();
		LayoutSet publicLayoutSet = group.getPublicLayoutSet();

		int portalPort = PortalUtil.getPortalPort(false);

		if (portalPort != -1) {
			if (Validator.isNotNull(company.getVirtualHostname())) {
				companyPortalURL = PortalUtil.getPortalURL(
					company.getVirtualHostname(), portalPort, false);
			}

			if (Validator.isNotNull(privateLayoutSet.getVirtualHostname())) {
				privateLayoutSetPortalURL = PortalUtil.getPortalURL(
					privateLayoutSet.getVirtualHostname(), portalPort, false);
			}

			if (Validator.isNotNull(publicLayoutSet.getVirtualHostname())) {
				publicLayoutSetPortalURL = PortalUtil.getPortalURL(
					publicLayoutSet.getVirtualHostname(), portalPort, false);
			}
		}

		int securePortalPort = PortalUtil.getPortalPort(true);

		String companySecurePortalURL = StringPool.BLANK;
		String privateLayoutSetSecurePortalURL = StringPool.BLANK;
		String publicLayoutSetSecurePortalURL = StringPool.BLANK;

		if (securePortalPort != -1) {
			if (Validator.isNotNull(company.getVirtualHostname())) {
				companySecurePortalURL = PortalUtil.getPortalURL(
					company.getVirtualHostname(), securePortalPort, true);
			}

			if (Validator.isNotNull(privateLayoutSet.getVirtualHostname())) {
				privateLayoutSetSecurePortalURL = PortalUtil.getPortalURL(
					privateLayoutSet.getVirtualHostname(), securePortalPort,
					true);
			}

			if (Validator.isNotNull(publicLayoutSet.getVirtualHostname())) {
				publicLayoutSetSecurePortalURL = PortalUtil.getPortalURL(
					publicLayoutSet.getVirtualHostname(), securePortalPort,
					true);
			}
		}

		content = StringUtil.replace(
			content, DATA_HANDLER_COMPANY_SECURE_URL, companySecurePortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_COMPANY_URL, companyPortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_GROUP_FRIENDLY_URL, group.getFriendlyURL());
		content = StringUtil.replace(
			content, DATA_HANDLER_PATH_CONTEXT, PortalUtil.getPathContext());
		content = StringUtil.replace(
			content, DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
		content = StringUtil.replace(
			content, DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL,
			privateLayoutSetSecurePortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_PRIVATE_LAYOUT_SET_URL,
			privateLayoutSetPortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);
		content = StringUtil.replace(
			content, DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL,
			publicLayoutSetSecurePortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_PUBLIC_LAYOUT_SET_URL,
			publicLayoutSetPortalURL);
		content = StringUtil.replace(
			content, DATA_HANDLER_PUBLIC_SERVLET_MAPPING,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

		return content;
	}

	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long oldLayoutId = GetterUtil.getLong(matcher.group(1));

			long newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String layoutUuid = matcher.group(3);
			String friendlyURL = matcher.group(4);

			try {
				Layout layout =
					LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
						layoutUuid, portletDataContext.getScopeGroupId(),
						privateLayout);

				if (layout == null) {
					layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
						portletDataContext.getScopeGroupId(), privateLayout,
						friendlyURL);
				}

				if (layout == null) {
					layout = LayoutLocalServiceUtil.fetchLayout(
						portletDataContext.getScopeGroupId(), privateLayout,
						oldLayoutId);
				}

				if (layout == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(9);

						sb.append("Unable to get layout with UUID ");
						sb.append(layoutUuid);
						sb.append(", friendly URL ");
						sb.append(friendlyURL);
						sb.append(", or ");
						sb.append("layoutId ");
						sb.append(oldLayoutId);
						sb.append(" in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newLayoutId = layout.getLayoutId();
				}
			}
			catch (SystemException se) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message =
						"Unable to get layout in group " +
							portletDataContext.getScopeGroupId();

					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
					else {
						_log.debug(message, se);
					}
				}
			}

			String oldLinkToLayout = matcher.group(0);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.AT);
			sb.append(layoutUuid);
			sb.append(StringPool.AT);
			sb.append(friendlyURL);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {sb.toString(), String.valueOf(oldLayoutId)},
				new String[] {StringPool.BLANK, String.valueOf(newLayoutId)});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	@Override
	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className,
			Element rootElement)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			String newValue = oldValue;

			String[] primaryKeys = StringUtil.split(oldValue);

			for (String primaryKey : primaryKeys) {
				if (!Validator.isNumber(primaryKey)) {
					break;
				}

				long primaryKeyLong = GetterUtil.getLong(primaryKey);

				String uuid = null;

				if (className.equals(AssetCategory.class.getName())) {
					AssetCategory assetCategory =
						AssetCategoryLocalServiceUtil.fetchCategory(
							primaryKeyLong);

					if (assetCategory != null) {
						uuid = assetCategory.getUuid();

						portletDataContext.addReferenceElement(
							portlet, rootElement, assetCategory,
							AssetCategory.class,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
					}
				}
				else if (className.equals(AssetVocabulary.class.getName())) {
					AssetVocabulary assetVocabulary =
						AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
							primaryKeyLong);

					if (assetVocabulary != null) {
						uuid = assetVocabulary.getUuid();

						portletDataContext.addReferenceElement(
							portlet, rootElement, assetVocabulary,
							AssetVocabulary.class,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
					}
				}
				else if (className.equals(DDMStructure.class.getName())) {
					DDMStructure ddmStructure =
						DDMStructureLocalServiceUtil.fetchStructure(
							primaryKeyLong);

					if (ddmStructure != null) {
						uuid = ddmStructure.getUuid();

						portletDataContext.addReferenceElement(
							portlet, rootElement, ddmStructure,
							DDMStructure.class,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
					}
				}
				else if (className.equals(DLFileEntryType.class.getName())) {
					DLFileEntryType dlFileEntryType =
						DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(
							primaryKeyLong);

					if (dlFileEntryType != null) {
						uuid = dlFileEntryType.getUuid();

						portletDataContext.addReferenceElement(
							portlet, rootElement, dlFileEntryType,
							DLFileEntryType.class,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
					}
				}
				else if (className.equals(Organization.class.getName())) {
					Organization organization =
						OrganizationLocalServiceUtil.fetchOrganization(
							primaryKeyLong);

					if (organization != null) {
						uuid = organization.getUuid();

						portletDataContext.addReferenceElement(
							portlet, rootElement, organization,
							Organization.class,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
					}
				}

				if (Validator.isNull(uuid)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get UUID for class " + className +
								" with primary key " + primaryKeyLong);
					}

					continue;
				}

				newValue = StringUtil.replace(newValue, primaryKey, uuid);
			}

			newValues[i] = newValue;
		}

		portletPreferences.setValues(key, newValues);
	}

	@Override
	public void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, Class<?> clazz,
			long companyGroupId)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(clazz);

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			String newValue = oldValue;

			String[] uuids = StringUtil.split(oldValue);

			for (String uuid : uuids) {
				Long newPrimaryKey = null;

				if (Validator.isNumber(uuid)) {
					long oldPrimaryKey = GetterUtil.getLong(uuid);

					newPrimaryKey = MapUtil.getLong(
						primaryKeys, oldPrimaryKey, oldPrimaryKey);
				}
				else {
					String className = clazz.getName();

					if (className.equals(AssetCategory.class.getName())) {
						AssetCategory assetCategory =
							AssetCategoryUtil.fetchByUUID_G(
								uuid, portletDataContext.getScopeGroupId());

						if (assetCategory == null) {
							assetCategory = AssetCategoryUtil.fetchByUUID_G(
								uuid, companyGroupId);
						}

						if (assetCategory != null) {
							newPrimaryKey = assetCategory.getCategoryId();
						}
					}
					else if (className.equals(
								AssetVocabulary.class.getName())) {

						AssetVocabulary assetVocabulary =
							AssetVocabularyUtil.fetchByUUID_G(
								uuid, portletDataContext.getScopeGroupId());

						if (assetVocabulary == null) {
							assetVocabulary = AssetVocabularyUtil.fetchByUUID_G(
								uuid, companyGroupId);
						}

						if (assetVocabulary != null) {
							newPrimaryKey = assetVocabulary.getVocabularyId();
						}
					}
					else if (className.equals(DDMStructure.class.getName())) {
						DDMStructure ddmStructure =
							DDMStructureUtil.fetchByUUID_G(
								uuid, portletDataContext.getScopeGroupId());

						if (ddmStructure == null) {
							ddmStructure = DDMStructureUtil.fetchByUUID_G(
								uuid, companyGroupId);
						}

						if (ddmStructure != null) {
							newPrimaryKey = ddmStructure.getStructureId();
						}
					}
					else if (className.equals(
								DLFileEntryType.class.getName())) {

						DLFileEntryType dlFileEntryType =
							DLFileEntryTypeUtil.fetchByUUID_G(
								uuid, portletDataContext.getScopeGroupId());

						if (dlFileEntryType == null) {
							dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
								uuid, companyGroupId);
						}

						if (dlFileEntryType != null) {
							newPrimaryKey =
								dlFileEntryType.getFileEntryTypeId();
						}
					}
					else if (className.equals(Organization.class.getName())) {
						Organization organization =
							OrganizationUtil.fetchByUuid_C_First(
								uuid, portletDataContext.getCompanyId(), null);

						if (organization != null) {
							newPrimaryKey = organization.getOrganizationId();
						}
					}
				}

				if (Validator.isNull(newPrimaryKey)) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(8);

						sb.append("Unable to get primary key for ");
						sb.append(clazz);
						sb.append(" with UUID ");
						sb.append(uuid);
						sb.append(" in company group ");
						sb.append(companyGroupId);
						sb.append(" or in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newValue = StringUtil.replace(
						newValue, uuid, newPrimaryKey.toString());
				}
			}

			newValues[i] = newValue;
		}

		portletPreferences.setValues(key, newValues);
	}

	@Override
	public MissingReferences validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		final MissingReferences missingReferences = new MissingReferences();

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		final PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, parameterMap,
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		SAXParser saxParser = new SAXParser();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					MissingReference missingReference =
						validateMissingReference(portletDataContext, element);

					if (missingReference != null) {
						missingReferences.add(missingReference);
					}
				}

			},
			new String[] {"missing-reference"});

		saxParser.setContentHandler(elementHandler);

		saxParser.parse(
			new InputSource(
				portletDataContext.getZipEntryAsInputStream("/manifest.xml")));

		return missingReferences;
	}

	@Override
	public void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		Element rootElement = document.getRootElement();

		Element manifestSummaryElement = rootElement.addElement(
			"manifest-summary");

		for (String manifestSummaryKey :
				manifestSummary.getManifestSummaryKeys()) {

			Element element = manifestSummaryElement.addElement("staged-model");

			element.addAttribute("manifest-summary-key", manifestSummaryKey);

			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				manifestSummaryKey);

			if (modelAdditionCount > 0) {
				element.addAttribute(
					"addition-count", String.valueOf(modelAdditionCount));
			}

			long modelDeletionCount = manifestSummary.getModelDeletionCount(
				manifestSummaryKey);

			if (modelDeletionCount > 0) {
				element.addAttribute(
					"deletion-count", String.valueOf(modelDeletionCount));
			}
		}
	}

	protected Map<String, String[]> getDLReferenceParameters(
		PortletDataContext portletDataContext, String content, int beginPos,
		int endPos) {

		boolean legacyURL = true;
		char[] stopChars = _DL_REFERENCE_LEGACY_STOP_CHARS;

		if (content.startsWith("/documents/", beginPos)) {
			legacyURL = false;
			stopChars = _DL_REFERENCE_STOP_CHARS;
		}

		endPos = StringUtil.indexOfAny(content, stopChars, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		Map<String, String[]> map = new HashMap<String, String[]>();

		String dlReference = content.substring(beginPos, endPos);

		while (dlReference.contains(StringPool.AMPERSAND_ENCODED)) {
			dlReference = dlReference.replace(
				StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		if (!legacyURL) {
			String[] pathArray = dlReference.split(StringPool.SLASH);

			map.put("groupId", new String[] {pathArray[2]});

			if (pathArray.length == 4) {
				map.put("uuid", new String[] {pathArray[3]});
			}
			else if (pathArray.length == 5) {
				map.put("folderId", new String[] {pathArray[3]});
				map.put(
					"title", new String[] {HttpUtil.decodeURL(pathArray[4])});
			}
			else if (pathArray.length > 5) {
				map.put("uuid", new String[] {pathArray[5]});
			}
		}
		else {
			dlReference = dlReference.substring(
				dlReference.indexOf(CharPool.QUESTION) + 1);

			map = HttpUtil.parameterMapFromString(dlReference);

			if (map.containsKey("img_id")) {
				map.put("image_id", map.get("img_id"));
			}
			else if (map.containsKey("i_id")) {
				map.put("image_id", map.get("i_id"));
			}
		}

		map.put("endPos", new String[] {String.valueOf(endPos)});

		String groupIdString = MapUtil.getString(map, "groupId");

		if (groupIdString.equals("@group_id@")) {
			groupIdString = String.valueOf(
				portletDataContext.getScopeGroupId());

			map.put("groupId", new String[] {groupIdString});
		}

		return map;
	}

	protected FileEntry getFileEntry(
		PortletDataContext portletDataContext, Map<String, String[]> map) {

		if (map == null) {
			return null;
		}

		FileEntry fileEntry = null;

		try {
			String uuid = MapUtil.getString(map, "uuid");
			long groupId = MapUtil.getLong(map, "groupId");

			if (Validator.isNotNull(uuid)) {
				fileEntry =
					DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
						uuid, groupId);
			}
			else {
				if (map.containsKey("folderId")) {
					long folderId = MapUtil.getLong(map, "folderId");
					String name = MapUtil.getString(map, "name");
					String title = MapUtil.getString(map, "title");

					if (Validator.isNotNull(title)) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							groupId, folderId, title);
					}
					else {
						DLFileEntry dlFileEntry =
							DLFileEntryLocalServiceUtil.fetchFileEntryByName(
								groupId, folderId, name);

						if (dlFileEntry != null) {
							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								dlFileEntry.getFileEntryId());
						}
					}
				}
				else if (map.containsKey("image_id")) {
					DLFileEntry dlFileEntry =
						DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(
							MapUtil.getLong(map, "image_id"));

					if (dlFileEntry != null) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							dlFileEntry.getFileEntryId());
					}
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return fileEntry;
	}

	protected UserIdStrategy getUserIdStrategy(
			long userId, String userIdStrategy)
		throws Exception {

		User user = UserLocalServiceUtil.getUserById(userId);

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected String replaceExportHostname(
			PortletDataContext portletDataContext, String url,
			StringBundler urlSB)
		throws PortalException, SystemException {

		if (!HttpUtil.hasProtocol(url)) {
			return url;
		}

		boolean secure = HttpUtil.isSecure(url);

		int portalPort = PortalUtil.getPortalPort(secure);

		if (portalPort == -1) {
			return url;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		LayoutSet publicLayoutSet = group.getPublicLayoutSet();

		String publicLayoutSetVirtualHostname =
			publicLayoutSet.getVirtualHostname();

		String portalUrl = StringPool.BLANK;

		if (Validator.isNotNull(publicLayoutSetVirtualHostname)) {
			portalUrl = PortalUtil.getPortalURL(
				publicLayoutSetVirtualHostname, portalPort, secure);

			if (url.startsWith(portalUrl)) {
				if (secure) {
					urlSB.append(DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL);
				}
				else {
					urlSB.append(DATA_HANDLER_PUBLIC_LAYOUT_SET_URL);
				}

				return url.substring(portalUrl.length());
			}
		}

		LayoutSet privateLayoutSet = group.getPrivateLayoutSet();

		String privateLayoutSetVirtualHostname =
			privateLayoutSet.getVirtualHostname();

		if (Validator.isNotNull(privateLayoutSetVirtualHostname)) {
			portalUrl = PortalUtil.getPortalURL(
				privateLayoutSetVirtualHostname, portalPort, secure);

			if (url.startsWith(portalUrl)) {
				if (secure) {
					urlSB.append(DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL);
				}
				else {
					urlSB.append(DATA_HANDLER_PRIVATE_LAYOUT_SET_URL);
				}

				return url.substring(portalUrl.length());
			}
		}

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		String companyVirtualHostname = company.getVirtualHostname();

		if (Validator.isNotNull(companyVirtualHostname)) {
			portalUrl = PortalUtil.getPortalURL(
				companyVirtualHostname, portalPort, secure);

			if (url.startsWith(portalUrl)) {
				if (secure) {
					urlSB.append(DATA_HANDLER_COMPANY_SECURE_URL);
				}
				else {
					urlSB.append(DATA_HANDLER_COMPANY_URL);
				}

				return url.substring(portalUrl.length());
			}
		}

		portalUrl = PortalUtil.getPortalURL("localhost", portalPort, secure);

		if (url.startsWith(portalUrl)) {
			return url.substring(portalUrl.length());
		}

		return url;
	}

	protected MissingReference validateMissingReference(
		PortletDataContext portletDataContext, Element element) {

		String className = element.attributeValue("class-name");

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		if (!stagedModelDataHandler.validateReference(
				portletDataContext, element.getParent(), element)) {

			return new MissingReference(element);
		}

		return null;
	}

	private static final char[] _DL_REFERENCE_LEGACY_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _DL_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _LAYOUT_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
			StringPool.SLASH;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
			StringPool.SLASH;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
			StringPool.SLASH;

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportHelperImpl.class);

	private Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");
	private Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

	private class ManifestSummaryElementProcessor implements ElementProcessor {

		public ManifestSummaryElementProcessor(
			Group group, ManifestSummary manifestSummary) {

			_group = group;
			_manifestSummary = manifestSummary;
		}

		@Override
		public void processElement(Element element) {
			String elementName = element.getName();

			if (elementName.equals("header")) {
				String exportDateString = element.attributeValue("export-date");

				Date exportDate = GetterUtil.getDate(
					exportDateString,
					DateFormatFactoryUtil.getSimpleDateFormat(
						Time.RFC822_FORMAT));

				_manifestSummary.setExportDate(exportDate);
			}
			else if (elementName.equals("portlet")) {
				String portletId = element.attributeValue("portlet-id");

				Portlet portlet = null;

				try {
					portlet = PortletLocalServiceUtil.getPortletById(
						_group.getCompanyId(), portletId);
				}
				catch (Exception e) {
					return;
				}

				PortletDataHandler portletDataHandler =
					portlet.getPortletDataHandlerInstance();

				String[] configurationPortletOptions = StringUtil.split(
					element.attributeValue("portlet-configuration"));

				PortletDataHandlerControl[] portletDataHandlerControls =
					portletDataHandler.getImportConfigurationControls(
						configurationPortletOptions);

				if (ArrayUtil.isNotEmpty(portletDataHandlerControls)) {
					_manifestSummary.addConfigurationPortlet(
						portlet, configurationPortletOptions);
				}

				if (!(portletDataHandler instanceof
						DefaultConfigurationPortletDataHandler) &&
					GetterUtil.getBoolean(
						element.attributeValue("portlet-data"))) {

					_manifestSummary.addDataPortlet(portlet);
				}
			}
			else if (elementName.equals("staged-model")) {
				String manifestSummaryKey = element.attributeValue(
					"manifest-summary-key");

				long modelAdditionCount = GetterUtil.getLong(
					element.attributeValue("addition-count"));

				_manifestSummary.addModelAdditionCount(
					manifestSummaryKey, modelAdditionCount);

				long modelDeletionCount = GetterUtil.getLong(
					element.attributeValue("deletion-count"));

				_manifestSummary.addModelDeletionCount(
					manifestSummaryKey, modelDeletionCount);
			}
		}

		private Group _group;
		private ManifestSummary _manifestSummary;

	}

}