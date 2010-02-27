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
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;

/**
 * <a href="RMICacheManagerPeerProviderFactory.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RMICacheManagerPeerProviderFactory
	extends net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory {

	public RMICacheManagerPeerProviderFactory() {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + this.hashCode());
		}
	}

	public CacheManagerPeerProvider createCachePeerProvider(
		CacheManager cacheManager, Properties properties) {

		String portalPropertyKey = properties.getProperty("portalPropertyKey");

		if (Validator.isNull(portalPropertyKey)) {
			throw new RuntimeException("portalPropertyKey is null");
		}

		String portalPropertiesString = PropsUtil.getProperties().getProperty(
			portalPropertyKey);

		if (_log.isInfoEnabled()) {
			_log.info(
				"portalPropertyKey " + portalPropertyKey + " has value " +
					portalPropertiesString);
		}

		portalPropertiesString = StringUtil.replace(
			portalPropertiesString, StringPool.COMMA, StringPool.NEW_LINE);

		Properties portalProperties = null;

		try {
			portalProperties = PropertiesUtil.load(
				portalPropertiesString);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new RuntimeException(ioe.getMessage());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(PropertiesUtil.list(portalProperties));
		}

		return super.createCachePeerProvider(cacheManager, portalProperties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		RMICacheManagerPeerProviderFactory.class);

}