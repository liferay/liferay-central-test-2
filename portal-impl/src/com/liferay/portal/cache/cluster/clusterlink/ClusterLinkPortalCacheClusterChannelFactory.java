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

package com.liferay.portal.cache.cluster.clusterlink;

import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterChannel;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterChannelFactory;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterException;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="ClusterLinkPortalCacheClusterChannelFactory.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterChannelFactory
	implements PortalCacheClusterChannelFactory {

	public ClusterLinkPortalCacheClusterChannelFactory(
		String destination, List<Priority> priorities) {

		_counter = new AtomicInteger(0);
		_destination = destination;
		_priorities = priorities;

		Collections.sort(priorities);
	}

	public PortalCacheClusterChannel createPortalCacheClusterChannel()
		throws PortalCacheClusterException {

		int count = _counter.getAndIncrement();

		if (count >= _priorities.size()) {
			throw new IllegalStateException(
				"Cannot create more than " + _priorities.size() + " channels");
		}

		return new ClusterLinkPortalCacheClusterChannel(
			_destination, _priorities.get(count));
	}

	private AtomicInteger _counter;
	private String _destination;
	private List<Priority> _priorities;

}