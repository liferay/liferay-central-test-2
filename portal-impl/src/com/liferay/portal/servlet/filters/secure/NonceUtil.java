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

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

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

		_nonceQueue.put(new NonceDelayed(nonce));

		return nonce;
	}

	public static boolean verify(String nonce) {
		_cleanUp();

		return _nonceQueue.contains(new NonceDelayed(nonce));
	}

	private static void _cleanUp() {
		while (_nonceQueue.poll() != null);
	}

	private static class NonceDelayed implements Delayed {

		public NonceDelayed(String nonce) {
			if (nonce == null) {
				throw new NullPointerException("Null nonce");
			}
			_nonce = nonce;
			_createdMilliseconds = System.currentTimeMillis();
		}

		public long getDelay(TimeUnit unit) {
			long leftDelayTime = _NONCE_EXPIRATION + _createdMilliseconds -
				System.currentTimeMillis();
			return unit.convert(leftDelayTime, TimeUnit.MILLISECONDS);
		}

		public int compareTo(Delayed o) {
			if (!(o instanceof NonceDelayed)) {
				throw new IllegalArgumentException("Wrong Type : "
					+ o.getClass());
			}
			// Do not cast result into int, it may cause int overflow or
			// underflow
			long result = _createdMilliseconds -
				((NonceDelayed) o)._createdMilliseconds;
			if (result == 0) {
				return 0;
			}
			else if (result > 0) {
				return 1;
			}
			else {
				return -1;
			}
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof NonceDelayed)) {
				return false;
			}

			NonceDelayed nonceDelayed = (NonceDelayed) obj;

			return _nonce.equals(nonceDelayed._nonce);
		}

		public int hashCode() {
			return _nonce.hashCode();
		}

		private final long _createdMilliseconds;
		private final String _nonce;

	}

	private static final long _NONCE_EXPIRATION = 10 * Time.MINUTE;

	private static final DelayQueue<NonceDelayed> _nonceQueue =
		new DelayQueue<NonceDelayed>();

}