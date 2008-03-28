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

package com.liferay.portlet.news.util;

import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portlet.news.model.Feed;
import com.liferay.portlet.news.model.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * <a href="NewsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class NewsUtil {

	public static Map<String, Set<Feed>> getCategoryMap() {
		return _instance._categoryMap;
	}

	public static Set<String> getCategorySet() {
		return _instance._categorySet;
	}

	public static Map<String, Feed> getFeedMap() {
		return _instance._feedMap;
	}

	public static Set<Feed> getFeedSet() {
		return _instance._feedSet;
	}

	public static News getNews(String xml) {
		Feed feed = getFeedMap().get(xml);

		if (feed == null) {
			return null;
		}
		else {
			WebCacheItem wci = new NewsWebCacheItem();

			return (News)WebCachePoolUtil.get(
				feed.getShortName() + ";" + xml, wci);
		}
	}

	public static List<News> getNews(PortletPreferences prefs) {
		List<News> news = new ArrayList<News>();

		for (Feed feed : getSelFeeds(prefs)) {
			news.add(getNews(feed.getFeedURL()));
		}

		return news;
	}

	public static Map<String, List<Feed>> getSelCategories(Set<Feed> selFeeds) {
		Map<String, List<Feed>> selCategories =
			new HashMap<String, List<Feed>>();

		for (Feed feed : selFeeds) {
			String categoryName = feed.getCategoryName();

			if (selCategories.containsKey(categoryName)) {
				List<Feed> feedList = selCategories.get(categoryName);

				feedList.add(feed);
			}
			else {
				List<Feed> feedList = new ArrayList<Feed>();

				feedList.add(feed);

				selCategories.put(categoryName, feedList);
			}
		}

		return selCategories;
	}

	public static Set<Feed> getSelFeeds(PortletPreferences prefs) {
		Map<String, Feed> feedMap = getFeedMap();

		Set<Feed> selFeeds = new LinkedHashSet<Feed>();

		String[] selFeedsArray = prefs.getValues("sel-feeds", new String[0]);

		for (String selFeed : selFeedsArray) {
			Feed feed = feedMap.get(selFeed);

			selFeeds.add(feed);
		}

		return selFeeds;
	}

	public static String[] getSelFeeds(Set<Feed> selFeeds) {
		List<String> list = new ArrayList<String>();

		for (Feed feed : selFeeds) {
			list.add(feed.getFeedURL());
		}

		return list.toArray(new String[list.size()]);
	}

	private NewsUtil() {
		try {
			WebCacheItem wci = new CategoryWebCacheItem();

			Object[] objArray = (Object[])WebCachePoolUtil.get(
				"http://w.moreover.com/categories/category_list.tsv2", wci);

			_categoryMap = (Map<String, Set<Feed>>)objArray[0];
			_categorySet = (Set<String>)objArray[1];
			_feedMap = (Map<String, Feed>)objArray[2];
			_feedSet = (Set<Feed>)objArray[3];
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static NewsUtil _instance = new NewsUtil();

	private Map<String, Set<Feed>> _categoryMap;
	private Set<String> _categorySet;
	private Map<String, Feed> _feedMap;
	private Set<Feed> _feedSet;

}