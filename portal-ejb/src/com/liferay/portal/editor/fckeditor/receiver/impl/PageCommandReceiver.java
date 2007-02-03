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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.io.File;

import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <a href="PageCommandReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 *
 */
public class PageCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument arg) {
		return "0";
	}

	protected String fileUpload(
		CommandArgument arg, String fileName, File file, String extension) {

		return "0";
	}

	protected void getFolders(CommandArgument arg, Node root, Document doc) {
		try {
			_getFolders(arg, root, doc);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected void getFoldersAndFiles(
		CommandArgument arg, Node root, Document doc) {

		try {
			_getFolders(arg, root, doc);
			_getFiles(arg, root, doc);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private Layout _getLayout(String layoutName, Layout layout)
		throws Exception {

		String friendlyURL = layout.getFriendlyURL();

		if (Validator.isNotNull(friendlyURL)) {
			if (layoutName.equals(friendlyURL)) {
				return layout;
			}
		}
		else {
			if (layoutName.equals(layout.getPlid())) {
				return layout;
			}
		}

		List layoutChildren = layout.getChildren();

		if (layoutChildren.size() == 0) {
			return null;
		}
		else {
			for (int i = 0; i < layoutChildren.size(); i++) {
				Layout layoutChild = (Layout)layoutChildren.get(i);

				Layout currentLayout = _getLayout(layoutName, layoutChild);

				if (currentLayout != null) {
					return currentLayout;
				}
			}
		}

		return null;
	}

	private String _getLayoutName(Layout layout) {
		String friendlyURL = layout.getFriendlyURL();

		if (Validator.isNotNull(friendlyURL)) {
			return friendlyURL;
		}
		else {
			return layout.getPlid();
		}
	}

	private String _getLayoutName(String folderName) {
		String layoutName = folderName.substring(
			folderName.lastIndexOf('~') + 1, folderName.length() - 1);

		layoutName = layoutName.replace('>', '/');

		return layoutName;
	}

	private void _getFiles(CommandArgument arg, Node root, Document doc)
		throws Exception {

		if (!arg.getCurrentFolder().equals("/")) {
			Element filesEl = doc.createElement("Files");

			root.appendChild(filesEl);

			Group group = GroupServiceUtil.getGroup(arg.getCompanyId(), arg
					.getCurrentGroupName());

			List layouts = LayoutLocalServiceUtil.getLayouts(
				LayoutImpl.PUBLIC + group.getGroupId(),
				LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (("/" + arg.getCurrentGroupName() + "/").equals(arg
					.getCurrentFolder())) {
				for (int i = 0; i < layouts.size(); i++) {
					Layout layout = (Layout)layouts.get(i);

					Element fileEl = doc.createElement("File");

					filesEl.appendChild(fileEl);

					fileEl.setAttribute("name", _getLayoutName(layout));
					fileEl.setAttribute("desc", _getLayoutName(layout));
					fileEl.setAttribute("size", "");
					fileEl.setAttribute("url", PortalUtil.getLayoutURL(layout,
							arg.getThemeDisplay()));
				}
			}
			else {
				String layoutName = _getLayoutName(arg.getCurrentFolder());

				Layout layout = null;

				for (int i = 0; i < layouts.size(); i++) {
					layout = _getLayout(layoutName, (Layout)layouts.get(i));

					if (layout != null) {
						break;
					}
				}

				if (layout != null) {
					List layoutChildren = layout.getChildren();

					for (int i = 0; i < layoutChildren.size(); i++) {
						layout = (Layout)layoutChildren.get(i);

						Element fileEl = doc.createElement("File");

						filesEl.appendChild(fileEl);

						fileEl.setAttribute("name", _getLayoutName(layout));
						fileEl.setAttribute("desc", _getLayoutName(layout));
						fileEl.setAttribute("size", getSize());
						fileEl.setAttribute(
							"url",
							PortalUtil.getLayoutURL(
								layout, arg.getThemeDisplay()));
					}
				}
			}
		}
	}

	private void _getFolders(CommandArgument arg, Node root, Document doc)
		throws Exception {

		Element foldersEl = doc.createElement("Folders");

		root.appendChild(foldersEl);

		if (arg.getCurrentFolder().equals("/")) {
			LinkedHashMap groupParams = new LinkedHashMap();

			groupParams.put("layoutSet", Boolean.FALSE);

			List groups = GroupLocalServiceUtil.search(
				arg.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < groups.size(); ++i) {
				Group group = (Group)groups.get(i);

				Element folderEl = doc.createElement("Folder");

				foldersEl.appendChild(folderEl);

				folderEl.setAttribute("name", group.getName());
			}
		}
		else {
			Group group = GroupServiceUtil.getGroup(
				arg.getCompanyId(), arg.getCurrentGroupName());

			List layouts = LayoutLocalServiceUtil.getLayouts(
				LayoutImpl.PUBLIC + group.getGroupId(),
				LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (("/" + arg.getCurrentGroupName() + "/").equals(
					arg.getCurrentFolder())) {

				for (int i = 0; i < layouts.size(); i++) {
					Layout layout = (Layout)layouts.get(i);

					Element folderEl = doc.createElement("Folder");

					foldersEl.appendChild(folderEl);

					folderEl.setAttribute(
						"name", "~" + _getLayoutName(layout).replace('/', '>'));
				}
			}
			else {
				String layoutName = _getLayoutName(arg.getCurrentFolder());

				Layout layout = null;

				for (int i = 0; i < layouts.size(); i++) {
					layout = _getLayout(layoutName, (Layout)layouts.get(i));

					if (layout != null) {
						break;
					}
				}

				if (layout != null) {
					List layoutChildren = layout.getChildren();

					for (int i = 0; i < layoutChildren.size(); i++) {
						layout = (Layout)layoutChildren.get(i);

						Element folderEl = doc.createElement("Folder");

						foldersEl.appendChild(folderEl);

						folderEl.setAttribute(
							"name",
							"~" + _getLayoutName(layout).replace('/', '>'));
					}
				}
			}
		}
	}

}