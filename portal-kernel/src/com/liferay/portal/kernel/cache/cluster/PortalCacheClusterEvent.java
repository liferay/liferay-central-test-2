/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

/**
 * <a href="PortalCacheClusterEvent.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEvent implements Serializable {

	public PortalCacheClusterEvent(
		String cacheName, Serializable elementKey,
		PortalCacheClusterEventType eventType) {
		_cacheName = cacheName;
		_elementKey = elementKey;
		_eventType = eventType;
	}

	public String getCacheName() {
		return _cacheName;
	}

	public Serializable getElementKey() {
		return _elementKey;
	}

	public PortalCacheClusterEventType getEventType() {
		return _eventType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PortalCacheClusterEvent other = (PortalCacheClusterEvent) obj;
		if ((this._cacheName == null) ?
			(other._cacheName != null) :
			!this._cacheName.equals(other._cacheName)) {
			return false;
		}
		if (this._elementKey != other._elementKey &&
			(this._elementKey == null ||
			!this._elementKey.equals(other._elementKey))) {
			return false;
		}
		if (this._eventType != other._eventType &&
			(this._eventType == null ||
			!this._eventType.equals(other._eventType))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash +
			(this._cacheName != null ? this._cacheName.hashCode() : 0);
		hash = 41 * hash +
			(this._elementKey != null ? this._elementKey.hashCode() : 0);
		hash = 41 * hash +
			(this._eventType != null ? this._eventType.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);
		sb.append(_cacheName);
		sb.append(StringPool.COLON);
		sb.append(_elementKey.toString());
		sb.append(StringPool.COLON);
		sb.append(_eventType.toString());
		return sb.toString();
	}

	private String _cacheName;
	private Serializable _elementKey;
	private PortalCacheClusterEventType _eventType;

}