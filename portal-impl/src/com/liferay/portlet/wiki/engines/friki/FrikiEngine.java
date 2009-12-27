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