/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserUtil;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="BlogsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class BlogsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_BLOGS_DATA, _enableExport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("blogs-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Entries

			List entries = BlogsEntryUtil.findByGroupId(context.getGroupId());

			Iterator itr = entries.iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				if (context.addPrimaryKey(
						BlogsEntry.class, new Long(entry.getEntryId()))) {

					itr.remove();
				}
				else {
					context.addTagsEntries(
						BlogsEntry.class, entry.getPrimaryKeyObj());
				}
			}

			String xml = xStream.toXML(entries);

			Element el = root.addElement("blog-entries");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Stats users

			List statsUsers = BlogsStatsUserUtil.findByGroupId(
				context.getGroupId());

			itr = statsUsers.iterator();

			while (itr.hasNext()) {
				BlogsStatsUser statsUser = (BlogsStatsUser)itr.next();

				if (context.addPrimaryKey(
						BlogsStatsUser.class, new Long(
							statsUser.getStatsUserId()))) {

					itr.remove();
				}
			}

			xml = xStream.toXML(statsUsers);

			el = root.addElement("blog-stats-users");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_BLOGS_DATA, _enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Entries

			Element el = root.element("blog-entries").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List entries = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = entries.iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				String[] tagsEntries = context.getTagsEntries(
					BlogsEntry.class, entry.getPrimaryKeyObj());

				BlogsEntry existingEntry = BlogsEntryUtil.fetchByPrimaryKey(
					entry.getPrimaryKey());

				if ((existingEntry == null) ||
					(existingEntry.getGroupId() != context.getGroupId())) {

					long plid = context.getPlid();

					ThemeDisplay themeDisplay = null;
					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					Date displayDate = entry.getDisplayDate();

					BlogsEntryLocalServiceUtil.addEntry(
						entry.getUserId(), plid, entry.getCategoryId(),
						entry.getTitle(), entry.getContent(),
						displayDate.getMonth(), displayDate.getDay(),
						displayDate.getYear(), displayDate.getHours(),
						displayDate.getMinutes(), themeDisplay, tagsEntries,
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					TagsAssetLocalServiceUtil.updateAsset(
							entry.getUserId(), BlogsEntry.class.getName(),
							entry.getPrimaryKey(), tagsEntries);

					BlogsEntryUtil.update(entry, true);
				}
			}

			// Stats users

			el = root.element("blog-stats-users").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List statsUsers = (List) xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = statsUsers.iterator();

			while (itr.hasNext()) {
				BlogsStatsUser statsUser = (BlogsStatsUser)itr.next();

				BlogsStatsUser existingStatsUser =
					BlogsStatsUserUtil.fetchByPrimaryKey(
						statsUser.getPrimaryKey());

				if ((existingStatsUser == null) ||
					(existingStatsUser.getGroupId() != context.getGroupId())) {

					long groupId = context.getGroupId();

					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					BlogsStatsUserLocalServiceUtil.updateStatsUser(
						groupId, statsUser.getUserId(),
						statsUser.getLastPostDate());
				}
				else {
					BlogsStatsUserUtil.update(statsUser, true);
				}
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static final String _EXPORT_BLOGS_DATA =
		"export-" + PortletKeys.BLOGS + "-data";

	private static final String _IMPORT_BLOGS_DATA =
		"import-" + PortletKeys.BLOGS + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_BLOGS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_BLOGS_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(BlogsPortletDataHandlerImpl.class);

}