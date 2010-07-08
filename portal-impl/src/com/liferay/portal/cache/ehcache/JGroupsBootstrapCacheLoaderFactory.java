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

package com.liferay.portal.cache.ehcache;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

/**
 * Return instance of
 * com.liferay.portal.cache.ehcache.JGroupsBootstrapCacheLoader, rather than
 * net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoader to workaround
 * the Address downcast bug.
 * <a href="JGroupsBootstrapCacheLoaderFactory.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 */
public class JGroupsBootstrapCacheLoaderFactory
	extends net.sf.ehcache.distribution.jgroups.
		JGroupsBootstrapCacheLoaderFactory {

	public BootstrapCacheLoader createBootstrapCacheLoader(
		Properties properties) {
		boolean bootstrapAsynchronously =
			extractAndValidateBootstrapAsynchronously(properties);
		int maximumChunkSizeBytes = extractMaximumChunkSizeBytes(properties);
		return new JGroupsBootstrapCacheLoader(
			bootstrapAsynchronously, maximumChunkSizeBytes);
	}

}