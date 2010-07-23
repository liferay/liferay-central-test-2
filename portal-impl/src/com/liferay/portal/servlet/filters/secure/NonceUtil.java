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

package com.liferay.portal.servlet.filters.secure;

import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class NonceUtil {

	public static String generate(long companyId, String remoteAddr) {
		String nonce = null;

		long timestamp = System.currentTimeMillis();

		String companyKey = null;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

			companyKey = company.getKey();
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid companyId " + companyId, e);
		}

		nonce = DigesterUtil.digestHex(
			Digester.MD5, remoteAddr, Long.toString(timestamp), companyKey);

		_cache.put(timestamp, nonce);

		return nonce;
	}

	public static boolean verify(String nonce) {
		_cleanup();

		return _cache.containsValue(nonce);
	}

	private static void _cleanup() {
		long expired = System.currentTimeMillis() - _NONCE_EXPIRATION;

		List<Long> expiredTimes = new ArrayList<Long>();

		for (long time : _cache.keySet()) {
			if (time <= expired) {
				expiredTimes.add(time);
			}
			else {
				break;
			}
		}

		for (long time : expiredTimes) {
			_cache.remove(time);
		}
	}

	private static final long _NONCE_EXPIRATION = 10 * Time.MINUTE;

	private static Map<Long, String> _cache = Collections.synchronizedMap(
		new LinkedHashMap<Long, String>());

}