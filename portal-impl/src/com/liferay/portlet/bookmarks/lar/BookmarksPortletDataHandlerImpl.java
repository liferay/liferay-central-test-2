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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.ArrayList;
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
 * <a href="BookmarksPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * Provides the Bookmarks portlet export and import functionality, which is to
 * clone all bookmark entries associated with the layout's group. Upon import,
 * new instances of the corresponding bookmark entries are created or updated.
 * The author of the newly created objects is assumed to be the same one as the
 * original author and that an account for it exists and has the same id.
 * </p>
 *
 * @author JorgeFerrer
 */
public class BookmarksPortletDataHandlerImpl implements PortletDataHandler {

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
			parameterMap, _EXPORT_BOOKMARKS_DATA,
			_enableExport.getDefaultState());

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

			Element root = doc.addElement("bookmarks-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Folders

			List folders = BookmarksFolderUtil.findByGroupId(
				context.getGroupId());

			List entries = new ArrayList();

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				BookmarksFolder folder = (BookmarksFolder)itr.next();

				if (context.addPrimaryKey(
						BookmarksFolder.class,
						new Long(folder.getFolderId()))) {

					itr.remove();
				}
				else {
					List folderEntries = BookmarksEntryUtil.findByFolderId(
						folder.getFolderId());

					entries.addAll(folderEntries);
				}
			}

			String xml = xStream.toXML(folders);

			Element el = root.addElement("bookmark-folders");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Entries

			itr = entries.iterator();

			while (itr.hasNext()) {
				BookmarksEntry entry = (BookmarksEntry)itr.next();

				if (context.addPrimaryKey(
						BookmarksFolder.class, new Long(entry.getEntryId()))) {

					itr.remove();
				}
			}

			xml = xStream.toXML(entries);

			el = root.addElement("bookmark-entries");

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
			parameterMap, _IMPORT_BOOKMARKS_DATA,
			_enableImport.getDefaultState());

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

			// Folders

			Element el = root.element("bookmark-folders").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List folders = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				BookmarksFolder folder = (BookmarksFolder)itr.next();

				folder.setGroupId(context.getGroupId());

				if (BookmarksFolderUtil.fetchByPrimaryKey(
						folder.getPrimaryKey()) == null) {

					long plid = context.getPlid();
					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					BookmarksFolderLocalServiceUtil.addFolder(
						folder.getUserId(), plid, folder.getFolderId(),
						folder.getName(), folder.getDescription(),
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					BookmarksFolderUtil.update(folder, true);
				}
			}

			// Entries

			el = root.element("bookmark-entries").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List entries = (List) xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = entries.iterator();

			while (itr.hasNext()) {
				BookmarksEntry entry = (BookmarksEntry)itr.next();

				if (BookmarksEntryUtil.fetchByPrimaryKey(
						entry.getPrimaryKey()) == null) {

					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					BookmarksEntryLocalServiceUtil.addEntry(
						entry.getUserId(), entry.getFolderId(), entry.getName(),
						entry.getUrl(), entry.getComments(), new String[0],
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					BookmarksEntryUtil.update(entry, true);
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

	protected String getPrimaryKey(long groupId, String key) {
		StringMaker sm = new StringMaker();

		sm.append(groupId);
		sm.append(StringPool.POUND);
		sm.append(key);

		return sm.toString();
	}

	private static final String _EXPORT_BOOKMARKS_DATA =
		"export-" + PortletKeys.BOOKMARKS + "-data";

	private static final String _IMPORT_BOOKMARKS_DATA =
		"import-" + PortletKeys.BOOKMARKS + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_BOOKMARKS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_BOOKMARKS_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(BookmarksPortletDataHandlerImpl.class);

}