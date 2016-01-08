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

package com.liferay.portal.security.antisamy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.BaseSanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.Map;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class AntiSamySanitizerImpl extends BaseSanitizer {

	public AntiSamySanitizerImpl(URL url) {
		try (InputStream inputstream = url.openStream()) {
			_policy = Policy.getInstance(inputstream);
		}
		catch (Exception e) {
			throw new IllegalStateException("Unable to initialize policy", e);
		}
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Sanitizing " + className + "#" + classPK);
		}

		if (s == null) {
			return null;
		}

		if (Validator.isNull(contentType) ||
			!contentType.equals(ContentTypes.TEXT_HTML)) {

			return s;
		}

		try {
			AntiSamy antiSamy = new AntiSamy();

			CleanResults cleanResults = antiSamy.scan(s, _policy);

			return cleanResults.getCleanHTML();
		}
		catch (Exception e) {
			_log.error("Unable to sanitize input", e);

			throw new SanitizerException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntiSamySanitizerImpl.class);

	private final Policy _policy;

}