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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CacheManagerPeerProviderFactory;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Tina Tian
 */
public class JGroupsCacheManagerPeerProviderFactory
	extends CacheManagerPeerProviderFactory {

	public CacheManagerPeerProvider createCachePeerProvider(
		CacheManager cacheManager, Properties properties) {

		if (_log.isDebugEnabled()) {
			_log.debug("CREATING JGOUPS PEER PROVIDER");
		}

		String clusterName = properties.getProperty("clusterName");

		String channelProperties = properties.getProperty("channelProperties");

		if (channelProperties != null) {
			channelProperties = channelProperties.replaceAll(
				StringPool.SPACE, StringPool.BLANK);

			if (channelProperties.trim().equals(StringPool.BLANK)) {
				channelProperties = null;
			}
		}

		if (channelProperties != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Connect is:" + channelProperties);
			}
		}

		if (clusterName == null) {
			clusterName = _DEFAULT_CLUSTER_NAME;
		}

		return new JGroupManager(cacheManager, channelProperties, clusterName);
	}

	private static final String _DEFAULT_CLUSTER_NAME = "EH-CACHE";

	private static Log _log = LogFactoryUtil.getLog(
		JGroupsCacheManagerPeerProviderFactory.class);

}