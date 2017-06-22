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

package com.liferay.portal.servlet.filters.cache.validator;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class RequestParameterCacheValidatorManager {

	public static String getCacheFileName(
		final HttpServletRequest request, String cacheName, Log log) {

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(cacheName);

		cacheKeyGenerator.append(HttpUtil.getProtocol(request.isSecure()));
		cacheKeyGenerator.append(StringPool.UNDERLINE);
		cacheKeyGenerator.append(request.getRequestURI());

		Enumeration<String> parameterNamesEnumeration =
			request.getParameterNames();

		List<String> parameterNames = ListUtil.fromEnumeration(
			parameterNamesEnumeration);

		Collections.sort(parameterNames);

		for (String parameterName : _serviceTrackerMap.keySet()) {
			for (PredicateFilter<HttpServletRequest> predicateFilter :
					_serviceTrackerMap.getService(parameterName)) {

				if (predicateFilter == null) {
					continue;
				}

				try {
					boolean valid = predicateFilter.filter(request);

					if (valid) {
						cacheKeyGenerator.append(StringPool.UNDERLINE);
						cacheKeyGenerator.append(parameterName);
						cacheKeyGenerator.append(StringPool.UNDERLINE);
						cacheKeyGenerator.append(
							request.getParameter(parameterName));
					}
					else {
						if (log.isDebugEnabled()) {
							StringBundler sb = new StringBundler(5);

							sb.append("Parameter value ");
							sb.append(request.getParameter(parameterName));
							sb.append(" for parameter ");
							sb.append(parameterName);
							sb.append(" has been discarded for cache key");

							log.debug(sb.toString());
						}
					}
				}
				catch (Exception e) {
					if (log.isInfoEnabled()) {
						log.info(
							"Error validating request parameter " +
								parameterName + " for cache",
							e);
					}
				}
			}
		}

		String generatedValue = String.valueOf(cacheKeyGenerator.finish());

		return _sterilizeFileName(generatedValue);
	}

	private static String _sterilizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, new char[] {CharPool.SLASH, CharPool.BACK_SLASH},
			new char[] {CharPool.UNDERLINE, CharPool.UNDERLINE});
	}

	private static final
		ServiceTrackerMap<String, List<PredicateFilter>> _serviceTrackerMap =
			ServiceTrackerCollections.openMultiValueMap(
				PredicateFilter.class, "filter.request.parameter");

}