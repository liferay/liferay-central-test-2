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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <a href="PortalCacheClusterLink.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PortalCacheClusterLink {

	public void afterPropertiesSet() {
		_portalCacheClusterChannels =
			new ArrayList<PortalCacheClusterChannel>(_channelNumber);
		for (int i = 0; i < _channelNumber; i++) {
			_portalCacheClusterChannels.add(
				_portalCacheClusterChannelFactory.
					createPortalCacheClusterChannel());
		}
	}

	public void destroy() {
		for (PortalCacheClusterChannel portalCacheClusterChannel :
			_portalCacheClusterChannels) {
			portalCacheClusterChannel.destroy();
		}
	}

	public long getSubmittedEventNumber() {
		return _submittedEventCounter.get();
	}

	public void sendEvent(PortalCacheClusterEvent event) {
		long count = _submittedEventCounter.getAndIncrement();
		_portalCacheClusterChannels.get((int) (count % _channelNumber)).
			sendEvent(event);
	}

	public void setChannelNumber(int channelNumber) {
		_channelNumber = channelNumber;
	}

	public void setPortalCacheClusterChannelFactory(
		PortalCacheClusterChannelFactory portalCacheClusterChannelFactory) {
		_portalCacheClusterChannelFactory = portalCacheClusterChannelFactory;
	}

	private static final int _DEFAULT_CHANNEL_NUMBER = 10;

	private int _channelNumber = _DEFAULT_CHANNEL_NUMBER;
	private PortalCacheClusterChannelFactory _portalCacheClusterChannelFactory;
	private List<PortalCacheClusterChannel> _portalCacheClusterChannels;
	private AtomicLong _submittedEventCounter = new AtomicLong(0);

}