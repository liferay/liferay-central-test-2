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

package com.liferay.rss.web.internal.util;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.util.PropsValues;
import com.liferay.rss.web.configuration.RSSWebCacheConfiguration;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSWebCacheItem implements WebCacheItem {

	public RSSWebCacheItem(
		RSSWebCacheConfiguration rssWebCacheConfiguration, String url) {

		_rssWebCacheConfiguration = rssWebCacheConfiguration;
		_url = url;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		SyndFeed feed = null;

		InputStream inputstream = null;

		try {

			// com.liferay.portal.kernel.util.HttpUtil will break the connection
			// if it spends more than 5 seconds looking up a location. However,
			// German umlauts do not get encoded correctly. This may be a bug
			// with commons-httpclient or with how FeedParser uses
			// java.io.Reader.

			// Use http://xml.newsisfree.com/feeds/29/629.xml and
			// http://test.domosoft.com/up/RSS to test if German umlauts show up
			// correctly.

			/*
			Reader reader = new StringReader(
				new String(HttpUtil.URLtoByteArray(_url)));

			channel = FeedParser.parse(builder, reader);
			*/

			SyndFeedInput input = new SyndFeedInput();

			Http.Options options = new Http.Options();

			options.setLocation(_url);
			options.setTimeout(PropsValues.RSS_CONNECTION_TIMEOUT);

			inputstream = HttpUtil.URLtoInputStream(options);

			feed = input.build(new XmlReader(inputstream));
		}
		catch (Exception e) {
			throw new WebCacheException(_url + " " + e.toString(), e);
		}
		finally {
			StreamUtil.cleanUp(inputstream);
		}

		return feed;
	}

	@Override
	public long getRefreshTime() {
		return Time.MINUTE * _rssWebCacheConfiguration.feedTime();
	}

	private final RSSWebCacheConfiguration _rssWebCacheConfiguration;
	private final String _url;

}