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

package com.liferay.portlet.wiki.engines.friki;

import com.efsol.friki.ClassicToHTMLFilter;

import java.util.Map;

import javax.portlet.PortletURL;

import org.stringtree.factory.Fetcher;
import org.stringtree.factory.TractFetcher;

/**
 * <a href="NodeFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class NodeFilter extends ClassicToHTMLFilter {

	public NodeFilter(
		Fetcher context, TractFetcher pages, Map<String, String> remoteNames,
		String spec, NodeRepository nodeRepository, PortletURL portletURL,
		long nodeId) {

		super(context, pages, remoteNames, spec);

		_nodeRepository = nodeRepository;
		_portletURL = portletURL;
		_nodeId = nodeId;
	}

	public Map<String, Boolean> getTitles() {
		return _nodeRepository.getTitles();
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public long getNodeId() {
		return _nodeId;
	}

	private NodeRepository _nodeRepository;
	private PortletURL _portletURL;
	private long _nodeId;

}