/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
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
import com.liferay.portlet.PortletConfigFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;

/**
 * <a href="PortletLister.java.html"><b><i>View Source</i></b></a>
 *
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

		for (int i = 0; itr.hasNext(); i++) {
			PortletCategory portletCategory = itr.next();

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
		}
	}

	private void _iteratePortlets(
			PortletCategory portletCategory, Set<String> portletIds,
			int parentNodeId, int depth)
		throws SystemException {

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
				else if (!portlet.hasAddPortletPermission()) {
				}
				else {
					portlets.add(portlet);
				}

				PortletApp portletApp = portlet.getPortletApp();

				if (portletApp.isWARFile() &&
					Validator.isNull(externalPortletCategory)) {

					PortletConfig portletConfig = PortletConfigFactory.create(
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