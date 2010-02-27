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

package com.liferay.portlet.reverendfun.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.util.InitUtil;
import com.liferay.portlet.reverendfun.util.ReverendFunWebCacheItem;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="ReverendFunBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReverendFunBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		new ReverendFunBuilder();
	}

	public ReverendFunBuilder() {
		try {
			File file = new File(
				"../portal-impl/src/com/liferay/portlet/reverendfun/" +
					"dependencies/dates.txt");

			String[] dates = StringUtil.split(FileUtil.read(file), "\n");

			WebCacheItem wci = new ReverendFunWebCacheItem(dates[0]);

			Set<String> moreDates = (Set<String>)wci.convert(StringPool.BLANK);

			if (moreDates.size() > 0) {
				StringBundler sb = new StringBundler();

				Set<String> datesSet = new HashSet<String>();

				for (String date : moreDates) {
					datesSet.add(date);

					sb.append(date);
					sb.append("\n");
				}

				for (int i = 0; i < dates.length; i++) {
					String date = dates[i];

					if (!datesSet.contains(date)) {
						datesSet.add(date);

						sb.append(date);

						if ((i + 1) < dates.length) {
							sb.append("\n");
						}
					}
				}

				FileUtil.write(file, sb.toString(), true);
			}
		}
		catch (Exception e) {
		}
	}

}