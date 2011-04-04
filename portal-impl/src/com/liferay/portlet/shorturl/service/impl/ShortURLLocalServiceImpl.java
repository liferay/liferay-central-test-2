/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shorturl.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.shorturl.model.ShortURL;
import com.liferay.portlet.shorturl.service.base.ShortURLLocalServiceBaseImpl;

/**
 * The implementation of the short u r l local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.shorturl.service.ShortURLLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.shorturl.service.base.ShortURLLocalServiceBaseImpl
 * @see com.liferay.portlet.shorturl.service.ShortURLLocalServiceUtil
 */
public class ShortURLLocalServiceImpl extends ShortURLLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.shorturl.service.ShortURLLocalServiceUtil} to access the short u r l local service.
	 */
	public ShortURL addCustomURL(String url, String path) 
		throws SystemException {
		
		return addShortURL(url, path, null);
	}
	
	public ShortURL addShortURL(String url) throws SystemException {
		return addShortURL(url, null);
	}

	public ShortURL addShortURL(String url, String description) 
		throws SystemException {
		
		return addShortURL(url, null, description);
	}
	
	public ShortURL addShortURL(String url, String hash, String description)
		throws SystemException {

		ShortURL shortURL = shortURLPersistence.fetchByOriginalURL(url);

		if (shortURL == null) {
			shortURL = shortURLPersistence.create(
				counterLocalService.increment(ShortURL.class.getName()));
		}

		long shortURLId = shortURL.getShortURLId();

		shortURL.setOriginalURL(url);
		
		if (Validator.isNull(hash)) {
			shortURL.setHash(generateHash(shortURLId));
		}
		else {
			shortURL.setHash(hash);
		}
		
		shortURL.setDescriptor(generateDescriptor(description));

		return shortURLPersistence.update(shortURL, false);
	}

	public String getURLByHash(String hash) throws SystemException {
		ShortURL shortURL = shortURLPersistence.fetchByHash(hash);
		
		if (shortURL != null) {
			return shortURL.getOriginalURL();
		}

		return null;
	}

	public String getURLByURI(String uri) throws SystemException {
		return getURLByHash(getHashFromURI(uri));
	}
	
	protected String getHashFromURI(String uri) {
		int index = uri.indexOf(_PATH_SHORT_URL);

		if (index > -1) {
			index += _PATH_SHORT_URL.length();
			
			int end = uri.indexOf("/", index + 1);
			
			if (end == -1) {
				return uri.substring(index);
			}
			else {
				return uri.substring(index, end);
			}
		}

		return null;
	}
	
	protected String generateDescriptor(String description) {
		if (Validator.isNull(description)) {
			return "";
		}

		String descriptor = 
			HtmlUtil.escapeURL(description.replaceAll(" ", "-")); 
		
		if (!descriptor.startsWith("/")) {
			return "/" + descriptor;
		}
		else {
			return descriptor; 
		}
	}

	protected String generateHash(long key) {
		return "/" + Long.toString(key, Character.MAX_RADIX);
	}

	private static final String _PATH_SHORT_URL = "/u";
}