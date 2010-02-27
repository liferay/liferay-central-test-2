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

import com.efsol.friki.PageRepository;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiPage;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.PortletURL;

import org.stringtree.factory.memory.MapStringRepository;

/**
 * <a href="FrikiEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class FrikiEngine implements WikiEngine {

	public FrikiEngine() {
	}

	public String convert(WikiPage page, PortletURL portletURL)
		throws PageContentException {

		try {
			return convert(
				getFilter(portletURL, page.getNodeId()), page.getContent());
		}
		catch (IOException ioe) {
			throw new PageContentException(ioe);
		}
	}

	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		NodeFilter filter = getFilter(page.getNodeId());

		try {
			convert(filter, page.getContent());

			return filter.getTitles();
		}
		catch (IOException ioe) {
			throw new PageContentException(ioe);
		}
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
		_remoteNames = buildRemoteNamesMap(interWikiConfiguration);
	}

	public void setMainConfiguration(String mainConfiguration) {
		_mainConfiguration = mainConfiguration;
	}

	public boolean validate(long nodeId, String newContent) {
		try {
			NodeFilter filter = getFilter(nodeId);

			convert(filter, newContent);

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	protected Map<String, String> buildRemoteNamesMap(String names) {
		Map<String, String> remoteNames = new HashMap<String, String>();

		StringTokenizer st = new StringTokenizer(names, "\n");

		while (st.hasMoreTokens()) {
			String line = st.nextToken().trim();

			int sep = line.indexOf(StringPool.SPACE);

			if (sep > 0) {
				String name = line.substring(0, sep);
				String url = line.substring(sep + 1);

				remoteNames.put(name, url);
			}
		}

		return remoteNames;
	}

	protected String convert(NodeFilter filter, String content)
		throws IOException {

		if (content == null) {
			return StringPool.BLANK;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		filter.filter(new UnsyncStringReader(content), unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	protected NodeFilter getFilter(long nodeId) {
		return getFilter(null, nodeId);
	}

	protected NodeFilter getFilter(PortletURL portletURL, long nodeId) {
		MapStringRepository context = new MapStringRepository();
		NodeRepository nodeRepository = new NodeRepository(nodeId);
		PageRepository pageRepository = new PageRepository(nodeRepository);

		NodeFilter filter = new NodeFilter(
			context, pageRepository, _remoteNames, _mainConfiguration,
			nodeRepository, portletURL, nodeId);

		return filter;
	}

	private String _mainConfiguration;
	private Map<String, String> _remoteNames;

}