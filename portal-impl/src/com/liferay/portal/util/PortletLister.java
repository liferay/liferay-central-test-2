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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.comparator.PortletCategoryComparator;
import com.liferay.portal.util.comparator.PortletTitleComparator;
import com.liferay.portlet.PortletConfigFactoryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 */
public class PortletLister {

	public TreeView getTreeView(
			LayoutTypePortlet layoutTypePortlet, String rootNodeName, User user,
			ServletContext servletContext)
		throws PortalException, SystemException {

		_layoutTypePortlet = layoutTypePortlet;
		_user = user;
		_servletContext = servletContext;
		_nodeId = 1;

		_list = new ArrayList<TreeNodeView>();

		TreeNodeView rootNodeView = new TreeNodeView(_nodeId);

		rootNodeView.setName(rootNodeName);

		_list.add(rootNodeView);

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			String.valueOf(user.getCompanyId()), WebKeys.PORTLET_CATEGORY);

		List<PortletCategory> categories = ListUtil.fromCollection(
			portletCategory.getCategories());

		_iterateCategories(categories, _nodeId, 0);

		return new TreeView(_list, _depth);
	}

	public boolean isIncludeInstanceablePortlets() {
		return _includeInstanceablePortlets;
	}

	public void setIncludeInstanceablePortlets(
		boolean includeInstanceablePortlets) {

		_includeInstanceablePortlets = includeInstanceablePortlets;
	}

	private void _iterateCategories(
			List<PortletCategory> categories, long parentId, int depth)
		throws PortalException, SystemException {

		categories = ListUtil.sort(
			categories, new PortletCategoryComparator(_user.getLocale()));

		Iterator<PortletCategory> itr = categories.iterator();

		for (int i = 0; itr.hasNext();) {
			PortletCategory portletCategory = itr.next();

			if (portletCategory.isHidden()) {
				continue;
			}

			if (i == 0) {
				depth++;

				if (depth > _depth) {
					_depth = depth;
				}
			}

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);

			if ((i + 1) == categories.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			nodeView.setName(
				LanguageUtil.get(_user.getLocale(), portletCategory.getName()));
			nodeView.setParentId(parentId);

			_list.add(nodeView);

			List<PortletCategory> subCategories = ListUtil.fromCollection(
				portletCategory.getCategories());

			_iterateCategories(subCategories, _nodeId, depth);

			_iteratePortlets(
				portletCategory, portletCategory.getPortletIds(), _nodeId,
				depth + 1);

			i++;
		}
	}

	private void _iteratePortlets(
			PortletCategory portletCategory, Set<String> portletIds,
			int parentNodeId, int depth)
		throws PortalException, SystemException {

		List<Portlet> portlets = new ArrayList<Portlet>();

		Iterator<String> portletIdsItr = portletIds.iterator();

		String externalPortletCategory = null;

		while (portletIdsItr.hasNext()) {
			String portletId = portletIdsItr.next();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive()) {
				}
				else if (portlet.isInstanceable() &&
						 !_includeInstanceablePortlets) {
				}
				else if (!portlet.isInstanceable() &&
						 _layoutTypePortlet.hasPortletId(
							portlet.getPortletId())) {

					portlets.add(portlet);
				}
				else if (!portlet.hasAddPortletPermission(_user.getUserId())) {
				}
				else {
					portlets.add(portlet);
				}

				PortletApp portletApp = portlet.getPortletApp();

				if (portletApp.isWARFile() &&
					Validator.isNull(externalPortletCategory)) {

					PortletConfig portletConfig =
						PortletConfigFactoryUtil.create(
							portlet, _servletContext);

					ResourceBundle resourceBundle =
						portletConfig.getResourceBundle(_user.getLocale());

					try {
						externalPortletCategory = resourceBundle.getString(
							portletCategory.getName());
					}
					catch (MissingResourceException mre) {
					}
				}
			}
		}

		portlets = ListUtil.sort(
			portlets, new PortletTitleComparator(_user.getLocale()));

		Iterator<Portlet> portletsItr = portlets.iterator();

		for (int i = 0; portletsItr.hasNext(); i++) {
			Portlet portlet = portletsItr.next();

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);

			if ((i + 1) == portlets.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			nodeView.setName(PortalUtil.getPortletTitle(portlet, _user));
			nodeView.setObjId(portlet.getRootPortletId());
			nodeView.setParentId(parentNodeId);

			_list.add(nodeView);
		}
	}

	private LayoutTypePortlet _layoutTypePortlet;
	private User _user;
	private ServletContext _servletContext;
	private int _nodeId;
	private List<TreeNodeView> _list;
	private int _depth;
	private boolean _includeInstanceablePortlets;

}