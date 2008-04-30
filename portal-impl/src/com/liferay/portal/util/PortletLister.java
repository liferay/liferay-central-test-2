/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.comparator.PortletCategoryComparator;
import com.liferay.portal.util.comparator.PortletTitleComparator;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;

/**
 * <a href="PortletLister.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PortletLister {

	public TreeView getTreeView(
			Company company, String rootNodeName, User user,
			LayoutTypePortlet layoutTypePortlet, ServletContext application,
			Locale locale)
		throws PortalException, SystemException {

		_locale = locale;
		_nodeId = 1;
		_user = user;
		_layoutTypePortlet = layoutTypePortlet;
		_application = application;

		_list = new ArrayList<TreeNodeView>();

		TreeNodeView rootNodeView = new TreeNodeView(_nodeId);

		rootNodeView.setName(rootNodeName);

		_list.add(rootNodeView);

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			String.valueOf(company.getCompanyId()), WebKeys.PORTLET_CATEGORY);

		List categories =
			ListUtil.fromCollection(portletCategory.getCategories());

		_iterateCategories(company, categories, _nodeId, 0);

		return new TreeView(_list, _depth);
	}

	private void _iterateCategories(
			Company company, List categories, long parentId, int depth)
		throws PortalException, SystemException {

		Collections.sort(
			categories,
			new PortletCategoryComparator(company.getCompanyId(), _locale));

		Iterator itr = categories.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			PortletCategory portletCategory = (PortletCategory)itr.next();

			if (i == 0) {
				depth++;

				if (depth > _depth) {
					_depth = depth;
				}
			}

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);
			nodeView.setName(
				LanguageUtil.get(_locale, portletCategory.getName()));
			nodeView.setParentId(parentId);

			if ((i + 1) == categories.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			_list.add(nodeView);

			List subCategories =
				ListUtil.fromCollection(portletCategory.getCategories());

			_iterateCategories(company, subCategories, _nodeId, depth);

			_iteratePortlets(
				portletCategory.getPortletIds(), portletCategory, _nodeId,
				depth + 1);
		}
	}

	private void _iteratePortlets(
			Set<String> portletIds, PortletCategory portletCategory,
			int parentNodeId, int depth)
		throws SystemException {

		List portlets = new ArrayList();

		Iterator itr = portletIds.iterator();

		String externalPortletCategory = null;

		while (itr.hasNext()) {
			String portletId = (String)itr.next();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive()) {
				}
				else if (
					!portlet.isInstanceable() &&
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
					PortletConfig curPortletConfig =
						PortletConfigFactory.create(portlet, _application);

					ResourceBundle resourceBundle =
						curPortletConfig.getResourceBundle(_locale);

					try {
						externalPortletCategory =
							resourceBundle.getString(portletCategory.getName());
					}
					catch (MissingResourceException mre) {
					}
				}
			}
		}

		Collections.sort(
			portlets, new PortletTitleComparator(_application, _locale));

		itr = portlets.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			Portlet portlet = (Portlet)itr.next();

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);
			nodeView.setName(
				PortalUtil.getPortletTitle(portlet, _application, _locale));
			nodeView.setParentId(parentNodeId);
			nodeView.setObjId(portlet.getRootPortletId());

			if ((i + 1) == portlets.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			_list.add(nodeView);
		}
	}

	private Locale _locale;
	private int _nodeId;
	private List<TreeNodeView> _list;
	private int _depth;

	private User _user;
	private LayoutTypePortlet _layoutTypePortlet;
	private ServletContext _application;

}