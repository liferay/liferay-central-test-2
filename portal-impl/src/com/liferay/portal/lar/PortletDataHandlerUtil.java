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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eric Min
 */
public class PortletDataHandlerUtil {

	public static String exportDLFileEntries(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileEntriesElement, Element fileRanksElement,
			Element entityElement, String content, boolean checkDateRange)
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

		int beginPos = content.length();
		int currentLocation = -1;

		while (true) {
			currentLocation = content.lastIndexOf(
				"/c/document_library/get_file?", beginPos);

			if (currentLocation == -1) {
				currentLocation = content.lastIndexOf("/documents/", beginPos);
			}

			if (currentLocation == -1) {
				return sb.toString();
			}

			beginPos = currentLocation;

			int endPos1 = content.indexOf(CharPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(CharPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(CharPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(CharPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(CharPool.SPACE, beginPos);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if ((beginPos == -1) || (endPos == -1)) {
				break;
			}

			try {
				String oldParameters = content.substring(beginPos, endPos);

				while (oldParameters.contains(StringPool.AMPERSAND_ENCODED)) {
					oldParameters = oldParameters.replace(
						StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
				}

				Map<String, String> map = new HashMap<String, String>();

				if (oldParameters.startsWith("/documents/")) {
					String[] pathArray = oldParameters.split(StringPool.SLASH);

					map.put("groupId", pathArray[2]);

					if (pathArray.length == 4) {
						map.put("uuid", pathArray[3]);
					}
					else if (pathArray.length > 4) {
						map.put("folderId", pathArray[3]);
						map.put("name", pathArray[4]);
					}
				}
				else {
					oldParameters = oldParameters.substring(
						oldParameters.indexOf(CharPool.QUESTION) + 1);

					map = MapUtil.toLinkedHashMap(
						oldParameters.split(StringPool.AMPERSAND),
						StringPool.EQUAL);
				}

				FileEntry fileEntry = null;

				String uuid = map.get("uuid");

				if (uuid != null) {
					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = portletDataContext.getScopeGroupId();
					}

					fileEntry =
						DLAppLocalServiceUtil.getFileEntryByUuidAndRepositoryId(
							uuid, groupId);
				}
				else {
					String folderIdString = map.get("folderId");

					if (folderIdString != null) {
						long folderId = GetterUtil.getLong(folderIdString);
						String name = map.get("name");

						String groupIdString = map.get("groupId");

						long groupId = GetterUtil.getLong(groupIdString);

						if (groupIdString.equals("@group_id@")) {
							groupId = portletDataContext.getScopeGroupId();
						}

						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							groupId, folderId, name);
					}
				}

				if (fileEntry == null) {
					beginPos--;

					continue;
				}

				String path = DLPortletDataHandlerImpl.getFileEntryPath(
					portletDataContext, fileEntry);

				Element dlReferenceElement = entityElement.addElement(
					"dl-reference");

				dlReferenceElement.addAttribute("path", path);

				DLPortletDataHandlerImpl.exportFileEntry(
					portletDataContext, foldersElement, fileEntriesElement,
					fileRanksElement, fileEntry, checkDateRange);

				String dlReference = "[$dl-reference=" + path + "$]";

				sb.replace(beginPos, endPos, dlReference);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			beginPos--;
		}

		return sb.toString();
	}

	public static String exportIGImages(
			PortletDataContext portletDataContext, Element foldersElement,
			Element imagesElement, Element entityElement, String content,
			boolean checkDateRange)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.IMAGE_GALLERY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		int beginPos = content.length();

		while (true) {
			beginPos = content.lastIndexOf("/image/image_gallery?", beginPos);

			if (beginPos == -1) {
				return sb.toString();
			}

			int endPos1 = content.indexOf(CharPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(CharPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(CharPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(CharPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(CharPool.SPACE, beginPos);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if ((beginPos == -1) || (endPos == -1)) {
				break;
			}

			try {
				String oldParameters = content.substring(beginPos, endPos);

				oldParameters = oldParameters.substring(
					oldParameters.indexOf(StringPool.QUESTION) + 1);

				while (oldParameters.contains(StringPool.AMPERSAND_ENCODED)) {
					oldParameters = oldParameters.replace(
						StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
				}

				Map<String, String> map = MapUtil.toLinkedHashMap(
					oldParameters.split(StringPool.AMPERSAND),
					StringPool.EQUAL);

				IGImage image = null;

				if (map.containsKey("uuid")) {
					String uuid = map.get("uuid");

					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = portletDataContext.getScopeGroupId();
					}

					image = IGImageLocalServiceUtil.getImageByUuidAndGroupId(
						uuid, groupId);
				}
				else if (map.containsKey("image_id") ||
						 map.containsKey("img_id") ||
						 map.containsKey("i_id")) {

					long imageId = GetterUtil.getLong(map.get("image_id"));

					if (imageId <= 0) {
						imageId = GetterUtil.getLong(map.get("img_id"));

						if (imageId <= 0) {
							imageId = GetterUtil.getLong(map.get("i_id"));
						}
					}

					try {
						image = IGImageLocalServiceUtil.getImageByLargeImageId(
							imageId);
					}
					catch (Exception e) {
						image = IGImageLocalServiceUtil.getImageBySmallImageId(
							imageId);
					}
				}

				if (image == null) {
					beginPos--;

					continue;
				}

				String path = IGPortletDataHandlerImpl.getImagePath(
					portletDataContext, image);

				Element igReferenceElement = entityElement.addElement(
					"ig-reference");

				igReferenceElement.addAttribute("path", path);

				IGPortletDataHandlerImpl.exportImage(
					portletDataContext, foldersElement, imagesElement, image,
					checkDateRange);

				String igReference = "[$ig-reference=" + path + "$]";

				sb.replace(beginPos, endPos, igReference);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			beginPos--;
		}

		return sb.toString();
	}

	public static String exportLayoutFriendlyURLs(
		PortletDataContext portletDataContext, String content) {

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(
				portletDataContext.getScopeGroupId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		String href = "href=";

		int beginPos = content.length();

		while (true) {
			int hrefLength = href.length();

			beginPos = content.lastIndexOf(href, beginPos);

			if (beginPos == -1) {
				break;
			}

			char c = content.charAt(beginPos + hrefLength);

			if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
				hrefLength++;
			}

			int endPos1 = content.indexOf(
				CharPool.APOSTROPHE, beginPos + hrefLength);
			int endPos2 = content.indexOf(
				CharPool.CLOSE_BRACKET, beginPos + hrefLength);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos + hrefLength);
			int endPos4 = content.indexOf(
				CharPool.LESS_THAN, beginPos + hrefLength);
			int endPos5 = content.indexOf(
				CharPool.QUOTE, beginPos + hrefLength);
			int endPos6 = content.indexOf(
				CharPool.SPACE, beginPos + hrefLength);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if (endPos == -1) {
				beginPos--;

				continue;
			}

			String url = content.substring(beginPos + hrefLength, endPos);

			if (!url.startsWith(friendlyURLPrivateGroupPath) &&
				!url.startsWith(friendlyURLPrivateUserPath) &&
				!url.startsWith(friendlyURLPublicPath)) {

				beginPos--;

				continue;
			}

			int beginGroupPos = content.indexOf(
				CharPool.SLASH, beginPos + hrefLength + 1);

			if (beginGroupPos == -1) {
				beginPos--;

				continue;
			}

			int endGroupPos = content.indexOf(
				CharPool.SLASH, beginGroupPos + 1);

			if (endGroupPos == -1) {
				beginPos--;

				continue;
			}

			String groupFriendlyURL = content.substring(
				beginGroupPos, endGroupPos);

			if (groupFriendlyURL.equals(group.getFriendlyURL())) {
				sb.replace(
					beginGroupPos, endGroupPos,
					"@data_handler_group_friendly_url@");
			}

			beginPos--;
		}

		return sb.toString();
	}


	public static String exportLinksToLayout(
			PortletDataContext portletDataContext, String content)
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

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type,
					type.concat(StringPool.AT.concat(layout.getFriendlyURL())));

				oldLinksToLayout.add(oldLinkToLayout);
				newLinksToLayout.add(newLinkToLayout);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout with id " + layoutId +
							" in group " + portletDataContext.getScopeGroupId(),
						e);
				}
			}
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	public static String importDLFileEntries(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		List<Element> dlReferenceElements = parentElement.elements(
			"dl-reference");

		for (Element dlReferenceElement : dlReferenceElements) {
			String dlReferencePath = dlReferenceElement.attributeValue("path");

			DLFileEntry fileEntry = null;

			try {
				fileEntry = (DLFileEntry)portletDataContext.getZipEntryAsObject(
					dlReferencePath);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (fileEntry == null) {
				continue;
			}

			long fileEntryId = MapUtil.getLong(
				fileEntryPKs, fileEntry.getFileEntryId(),
				fileEntry.getFileEntryId());

			fileEntry = DLFileEntryUtil.fetchByPrimaryKey(fileEntryId);

			if (fileEntry == null) {
				continue;
			}

			String dlReference = "[$dl-reference=" + dlReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/documents/");
			sb.append(portletDataContext.getScopeGroupId());
			sb.append(StringPool.SLASH);
			sb.append(fileEntry.getFolderId());
			sb.append(StringPool.SLASH);
			sb.append(
				HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())));

			content = StringUtil.replace(content, dlReference, sb.toString());
		}

		return content;
	}

	public static String importIGImages(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		Map<Long, Long> imagePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				IGImage.class);

		List<Element> igReferenceElements = parentElement.elements(
			"ig-reference");

		for (Element igReferenceElement : igReferenceElements) {
			String igReferencePath = igReferenceElement.attributeValue("path");

			IGImage image = null;

			try {
				image = (IGImage)portletDataContext.getZipEntryAsObject(
					igReferencePath);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (image == null) {
				continue;
			}

			long imageId = MapUtil.getLong(
				imagePKs, image.getImageId(), image.getImageId());

			image = IGImageUtil.fetchByPrimaryKey(imageId);

			if (image == null) {
				continue;
			}

			String igReference = "[$ig-reference=" + igReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/image/image_gallery?uuid=");
			sb.append(image.getUuid());
			sb.append("&groupId=");
			sb.append(portletDataContext.getScopeGroupId());
			sb.append("&t=");
			sb.append(System.currentTimeMillis());

			content = StringUtil.replace(content, igReference, sb.toString());
		}

		return content;
	}

	public static String importLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			String oldLayoutId = matcher.group(1);

			String newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String friendlyURL = matcher.group(3);

			try {
				Layout layout = LayoutUtil.fetchByG_P_F(
					portletDataContext.getScopeGroupId(), privateLayout,
					friendlyURL);

				newLayoutId = String.valueOf(layout.getLayoutId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout with friendly URL " +
							friendlyURL + " in group " +
								portletDataContext.getScopeGroupId(),
						e);
				}
			}

			String oldLinkToLayout = matcher.group(0);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {oldLayoutId, StringPool.AT.concat(friendlyURL)},
				new String[] {newLayoutId, StringPool.BLANK});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	private static Log _log = LogFactoryUtil.getLog(
			PortletDataHandlerUtil.class);

	private static Pattern _exportLinksToLayoutPattern = Pattern.compile(
	"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");

	private static Pattern _importLinksToLayoutPattern = Pattern.compile(
	"\\[([0-9]+)@(public|private\\-[a-z]*)@([^\\]]*)\\]");

}