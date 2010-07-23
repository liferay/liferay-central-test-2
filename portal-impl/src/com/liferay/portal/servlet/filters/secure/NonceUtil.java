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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Alexander Chow
 */
public class NonceUtil {

	public static String generate(long companyId, String remoteAddress) {
		String companyKey = null;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

			companyKey = company.getKey();
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid companyId " + companyId, e);
		}

		long timestamp = System.currentTimeMillis();

		String nonce = DigesterUtil.digestHex(
			Digester.MD5, remoteAddress, String.valueOf(timestamp), companyKey);

		_lock.writeLock().lock();

		try {
			_nonceMap.put(timestamp, nonce);
		}
		finally {
			_lock.writeLock().unlock();
		}

		return nonce;
	}

	public static boolean verify(String nonce) {
		_cleanUp();

		_lock.readLock().lock();

		try {
			return _nonceMap.containsValue(nonce);
		}
		finally {
			_lock.readLock().unlock();
		}
	}

	private static void _cleanUp() {
		long expired = System.currentTimeMillis() - _NONCE_EXPIRATION;

		List<Long> times = new ArrayList<Long>();

		// Find expired nonces

		_lock.readLock().lock();

		try {
			for (long time : _nonceMap.keySet()) {
				if (time <= expired) {
					times.add(time);
				}
				else {
					break;
				}
			}
		}
		finally {
			_lock.readLock().unlock();
		}

		// Purge expired nonces

		_lock.writeLock().lock();

		try {
			for (long time : times) {
				_nonceMap.remove(time);
			}
		}
		finally {
			_lock.writeLock().unlock();
		}
	}

	private static final long _NONCE_EXPIRATION = 10 * Time.MINUTE;

	private static final ReentrantReadWriteLock _lock =
		new ReentrantReadWriteLock();

	private static Map<Long, String> _nonceMap =
		new LinkedHashMap<Long, String>();

}