/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

	private static Log _log =
		LogFactoryUtil.getLog(RMICacheManagerPeerProviderFactory.class);

}