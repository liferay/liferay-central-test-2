/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wiki.web.display.context.logic;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class WikiPortletInstanceSettingsHelper {

	public WikiPortletInstanceSettingsHelper(
		WikiRequestHelper wikiRequestHelper) {

		_wikiRequestHelper = wikiRequestHelper;
	}

	public List<String> getAllNodeNames() throws PortalException {
		if (_allNodeNames == null) {
			_populateNodes();
		}

		return _allNodeNames;
	}

	public List<WikiNode> getAllNodes() throws PortalException {
		if (_allNodes == null) {
			_populateNodes();
		}

		return _allNodes;
	}

	public List<WikiNode> getAllPermittedNodes() throws PortalException {
		if (_allPermittedNodes == null) {
			WikiPortletInstanceSettings wikiPortletInstanceSettings =
				_wikiRequestHelper.getWikiPortletInstanceSettings();

			_allPermittedNodes = WikiUtil.getNodes(
				getAllNodes(), wikiPortletInstanceSettings.hiddenNodes(),
				_wikiRequestHelper.getPermissionChecker());
		}

		return _allPermittedNodes;
	}

	public String[] getVisibleNodeNames() throws PortalException {
		if (_visibleNodeNames == null) {
			_populateNodes();
		}

		return _visibleNodeNames;
	}

	private void _populateNodes() throws PortalException {
		_allNodes = WikiNodeServiceUtil.getNodes(
			_wikiRequestHelper.getScopeGroupId());

		_allNodeNames = WikiUtil.getNodeNames(_allNodes);

		WikiPortletInstanceSettings wikiPortletInstanceSettings =
			_wikiRequestHelper.getWikiPortletInstanceSettings();

		_visibleNodeNames = wikiPortletInstanceSettings.visibleNodes();

		if (ArrayUtil.isNotEmpty(_visibleNodeNames)) {
			_allNodes = WikiUtil.orderNodes(_allNodes, _visibleNodeNames);
		}
		else {
			_visibleNodeNames = _allNodeNames.toArray(
				new String[_allNodeNames.size()]);
		}
	}

	private List<String> _allNodeNames;
	private List<WikiNode> _allNodes;
	private List<WikiNode> _allPermittedNodes;
	private String[] _visibleNodeNames;
	private final WikiRequestHelper _wikiRequestHelper;

}