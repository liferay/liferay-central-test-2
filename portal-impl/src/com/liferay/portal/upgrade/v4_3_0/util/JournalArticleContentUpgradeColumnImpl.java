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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.IdReplacer;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.PKParser;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticleContentUpgradeColumnImpl
	extends BaseUpgradeColumnImpl {

	public JournalArticleContentUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn groupIdColumn,
		UpgradeColumn articleIdColumn, UpgradeColumn versionColumn,
		UpgradeColumn structureIdColumn, ValueMapper imageIdMapper) {

		super("content");

		_companyIdColumn = companyIdColumn;
		_groupIdColumn = groupIdColumn;
		_articleIdColumn = articleIdColumn;
		_versionColumn = versionColumn;
		_structureIdColumn = structureIdColumn;
		_imageIdMapper = imageIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String content = (String)oldValue;

		content = StringUtil.replace(
			content, Table.SAFE_CHARS[1], Table.SAFE_CHARS[0]);

		/*if (content.indexOf("\\n") != -1) {
			content = StringUtil.replace(
				content,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});
		}*/

		String structureId = (String)_structureIdColumn.getOldValue();

		if (Validator.isNotNull(structureId)) {
			content = formatContent(content);
		}

		content = replaceIds(content);

		content = StringUtil.replace(
			content, Table.SAFE_CHARS[0], Table.SAFE_CHARS[1]);

		return content;
	}

	protected String formatContent(String content) throws Exception {
		String oldCompanyId = (String)_companyIdColumn.getOldValue();
		Long newCompanyId = (Long)_companyIdColumn.getNewValue();
		Long groupId = (Long)_groupIdColumn.getNewValue();
		String articleId = (String)_articleIdColumn.getNewValue();
		Double version = (Double)_versionColumn.getNewValue();

		try {
			Document doc = SAXReaderUtil.read(content);

			Element root = doc.getRootElement();

			format(
				oldCompanyId, newCompanyId.longValue(), groupId.longValue(),
				articleId, version.doubleValue(), root);

			content = JournalUtil.formatXML(doc);
		}
		catch (Exception e) {
			_log.error(
				"Unable to format content for {articleId=" + articleId +
					",version=" + version + "}: " + e.getMessage());
		}

		return content;
	}

	protected void format(
			String oldCompanyId, long newCompanyId, long groupId,
			String articleId, double version, Element root)
		throws Exception {

		Iterator<Element> itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			Element dynamicContent = el.element("dynamic-content");

			String elInstanceId = StringPool.BLANK;
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);
			String elLanguage = StringPool.BLANK;

			if (dynamicContent != null) {
				elLanguage = dynamicContent.attributeValue(
					"language-id", StringPool.BLANK);

				if (!elLanguage.equals(StringPool.BLANK)) {
					elLanguage = "_" + elLanguage;
				}
			}

			if (elType.equals("image") || elType.equals("text")) {
				String oldImageId = dynamicContent.getText();

				if (oldImageId.startsWith(_IMG_ID_PATH) ||
					oldImageId.startsWith("@portal_url@" + _IMG_ID_PATH) ||
					oldImageId.startsWith(
						"http://@portal_url@" + _IMG_ID_PATH) ||
					oldImageId.startsWith(
						"https://@portal_url@" + _IMG_ID_PATH)) {

					int pos = oldImageId.indexOf(_IMG_ID_PATH);

					String preOldImageId = oldImageId.substring(0, pos);

					oldImageId = oldImageId.substring(
						pos + _IMG_ID_PATH.length(), oldImageId.length());

					String newImageId = getNewImageId(oldCompanyId, oldImageId);

					dynamicContent.setText(
						preOldImageId + _IMG_ID_PATH + newImageId);

					if (elType.equals("image")) {
						dynamicContent.addAttribute("id", newImageId);

						long articleImageId = GetterUtil.getLong(newImageId);

						JournalArticleImageLocalServiceUtil.addArticleImageId(
							articleImageId, groupId, articleId, version,
							elInstanceId, elName, elLanguage);
					}
				}
			}

			format(oldCompanyId, newCompanyId, groupId, articleId, version, el);
		}
	}

	protected String getNewImageId(String oldCompanyId, String oldImageId)
		throws Exception {

		int pos = oldImageId.lastIndexOf("&version=");

		oldImageId =
			oldImageId.substring(0, pos) + "." +
				oldImageId.substring(pos + 9, oldImageId.length());

		String newImageId = oldCompanyId + ".journal.article." + oldImageId;

		return String.valueOf(_imageIdMapper.getNewValue(newImageId));
	}

	protected String replaceIds(String content) throws Exception {
		ValueMapper dlFolderIdMapper =
			AvailableMappersUtil.getDLFolderIdMapper();

		content = IdReplacer.replaceLongIds(
			content, "/document_library/get_file?folderId=", dlFolderIdMapper);
		content = IdReplacer.replaceLongIds(
			content,
			"_20_struts_action=%2Fdocument_library%2Fget_file&_20_folderId=",
			dlFolderIdMapper);
		content = IdReplacer.replaceLongIds(
			content,
			"_20_struts_action=%2Fdocument_library%2Fget_file&amp;" +
				"_20_folderId=",
			dlFolderIdMapper);

		ValueMapper imageIdMapper = AvailableMappersUtil.getImageIdMapper();

		ValueMapper newImageIdMapper = ValueMapperFactoryUtil.getValueMapper();

		ValueMapper igImageIdMapper = AvailableMappersUtil.getIGImageIdMapper();

		Iterator<Object> itr = igImageIdMapper.iterator();

		while (itr.hasNext()) {
			String oldValue = (String)itr.next();

			PKParser oldValuePKParser = new PKParser(oldValue);

			String companyId = oldValuePKParser.getString("companyId");
			String oldIGImageId = oldValuePKParser.getString("imageId");

			String oldImageId =
				companyId + ".image_gallery." + oldIGImageId + ".large";

			Long newImageId = (Long)imageIdMapper.getNewValue(oldImageId);

			newImageIdMapper.mapValue(
				new Long(GetterUtil.getLong(oldIGImageId)), newImageId);
		}

		content = IdReplacer.replaceLongIds(
			content, "/image_gallery?img_id=", newImageIdMapper);

		return content;
	}

	private static final String _IMG_ID_PATH =
		"/image/journal/article?img_id=";

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleContentUpgradeColumnImpl.class);

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _groupIdColumn;
	private UpgradeColumn _articleIdColumn;
	private UpgradeColumn _versionColumn;
	private UpgradeColumn _structureIdColumn;
	private ValueMapper _imageIdMapper;

}