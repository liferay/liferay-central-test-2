/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.File;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class PageCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument arg) {
		return "0";
	}

	protected String fileUpload(
		CommandArgument arg, String fileName, File file, String extension) {

		return "0";
	}

	protected void getFolders(CommandArgument arg, Document doc, Node root) {
		try {
			_getFolders(arg, doc, root);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected void getFoldersAndFiles(
		CommandArgument arg, Document doc, Node root) {

		try {
			_getFolders(arg, doc, root);
			_getFiles(arg, doc, root);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private Layout _getLayout(String layoutName, Layout layout)
		throws Exception {

		String friendlyURL = layout.getFriendlyURL();

		if (layoutName.equals(friendlyURL)) {
			return layout;
		}

		List<Layout> layoutChildren = layout.getChildren();

		if (layoutChildren.size() == 0) {
			return null;
		}
		else {
			for (Layout layoutChild : layoutChildren) {
				Layout currentLayout = _getLayout(layoutName, layoutChild);

				if (currentLayout != null) {
					return currentLayout;
				}
			}
		}

		return null;
	}

	private String _getLayoutName(Layout layout) {
		return layout.getFriendlyURL();
	}

	private String _getLayoutName(String folderName) {
		String layoutName = folderName.substring(
			folderName.lastIndexOf('~') + 1, folderName.length() - 1);

		layoutName = layoutName.replace('>', '/');

		return layoutName;
	}

	private void _getFiles(CommandArgument arg, Document doc, Node root)
		throws Exception {

		if (arg.getCurrentFolder().equals(StringPool.SLASH)) {
			return;
		}

		Element filesEl = doc.createElement("Files");

		root.appendChild(filesEl);

		Group group = arg.getCurrentGroup();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (("/" + arg.getCurrentGroupName() + "/").equals(
				arg.getCurrentFolder())) {

			for (Layout layout : layouts) {
				Element fileEl = doc.createElement("File");

				filesEl.appendChild(fileEl);

				fileEl.setAttribute("name", _getLayoutName(layout));
				fileEl.setAttribute("desc", _getLayoutName(layout));
				fileEl.setAttribute("size", StringPool.BLANK);
				fileEl.setAttribute(
					"url",
					PortalUtil.getLayoutURL(
						layout,arg.getThemeDisplay(), false));
			}
		}
		else {
			String layoutName = _getLayoutName(arg.getCurrentFolder());

			Layout layout = null;

			for (int i = 0; i < layouts.size(); i++) {
				layout = _getLayout(layoutName, layouts.get(i));

				if (layout != null) {
					break;
				}
			}

			if (layout == null) {
				return;
			}

			List<Layout> layoutChildren = layout.getChildren();

			for (int i = 0; i < layoutChildren.size(); i++) {
				layout = layoutChildren.get(i);

				Element fileEl = doc.createElement("File");

				filesEl.appendChild(fileEl);

				fileEl.setAttribute("name", _getLayoutName(layout));
				fileEl.setAttribute("desc", _getLayoutName(layout));
				fileEl.setAttribute("size", getSize());
				fileEl.setAttribute(
					"url",
					PortalUtil.getLayoutURL(
						layout, arg.getThemeDisplay(), false));
			}
		}
	}

	private void _getFolders(CommandArgument arg, Document doc, Node root)
		throws Exception {

		Element foldersEl = doc.createElement("Folders");

		root.appendChild(foldersEl);

		if (arg.getCurrentFolder().equals(StringPool.SLASH)) {
			getRootFolders(arg, doc, foldersEl);
		}
		else {
			Group group = arg.getCurrentGroup();

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (("/" + arg.getCurrentGroupName() + "/").equals(
					arg.getCurrentFolder())) {

				for (Layout layout : layouts) {
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
					layout = _getLayout(layoutName, layouts.get(i));

					if (layout != null) {
						break;
					}
				}

				if (layout != null) {
					List<Layout> layoutChildren = layout.getChildren();

					for (int i = 0; i < layoutChildren.size(); i++) {
						layout = layoutChildren.get(i);

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