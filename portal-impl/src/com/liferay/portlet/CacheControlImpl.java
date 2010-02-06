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

package com.liferay.portlet;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;

/**
 * <a href="CacheControlImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Deepak Gothe
 */
public class CacheControlImpl implements CacheControl {

	public CacheControlImpl(
		String eTag, int expirationTime, boolean publicScope,
		boolean useCachedContent, MimeResponseImpl mimeResponseImpl) {

		_eTag = eTag;
		_expirationTime = expirationTime;
		_publicScope = publicScope;
		_useCachedContent = useCachedContent;
		_mimeResponseImpl = mimeResponseImpl;
	}

	public String getETag() {
		return _eTag;
	}

	public boolean isPublicScope() {
		return _publicScope;
	}

	public int getExpirationTime() {
		return _expirationTime;
	}

	public void setETag(String eTag) {
		_eTag = eTag;

		_mimeResponseImpl.setProperty(MimeResponse.ETAG, eTag);
	}

	public void setExpirationTime(int expirationTime) {
		_expirationTime = expirationTime;

		_mimeResponseImpl.setProperty(
			MimeResponse.EXPIRATION_CACHE, String.valueOf(expirationTime));
	}

	public void setPublicScope(boolean publicScope) {
		_publicScope = publicScope;

		_mimeResponseImpl.setProperty(
			MimeResponse.PUBLIC_SCOPE, String.valueOf(publicScope));
	}

	public void setUseCachedContent(boolean useCachedContent) {
		_useCachedContent = useCachedContent;

		_mimeResponseImpl.setProperty(
			MimeResponse.USE_CACHED_CONTENT, String.valueOf(useCachedContent));
	}

	public boolean useCachedContent() {
		return _useCachedContent;
	}

	private String _eTag;
	private int _expirationTime;
	private MimeResponseImpl _mimeResponseImpl;
	private boolean _publicScope;
	private boolean _useCachedContent;

}