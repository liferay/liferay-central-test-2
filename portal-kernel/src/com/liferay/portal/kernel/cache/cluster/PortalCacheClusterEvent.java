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

package com.liferay.portal.kernel.cache.cluster;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * <a href="PortalCacheClusterEvent.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEvent implements Serializable {

	public PortalCacheClusterEvent(
		String cacheName, Serializable elementKey,
		PortalCacheClusterEventType portalCacheClusterEventType) {

		_cacheName = cacheName;
		_elementKey = elementKey;
		_portalCacheClusterEventType = portalCacheClusterEventType;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof PortalCacheClusterEvent)) {
			return false;
		}

		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)obj;

		if (Validator.equals(_cacheName, portalCacheClusterEvent._cacheName) &&
			Validator.equals(
				_elementKey, portalCacheClusterEvent._elementKey) &&
			Validator.equals(
				_portalCacheClusterEventType,
				portalCacheClusterEvent._portalCacheClusterEventType)) {

			return true;
		}

		return true;
	}

	public String getCacheName() {
		return _cacheName;
	}

	public Serializable getElementKey() {
		return _elementKey;
	}

	public PortalCacheClusterEventType getEventType() {
		return _portalCacheClusterEventType;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(_cacheName);
		sb.append(StringPool.COLON);
		sb.append(_elementKey.toString());
		sb.append(StringPool.COLON);
		sb.append(_portalCacheClusterEventType.toString());

		return sb.toString();
	}

	private String _cacheName;
	private Serializable _elementKey;
	private PortalCacheClusterEventType _portalCacheClusterEventType;

}