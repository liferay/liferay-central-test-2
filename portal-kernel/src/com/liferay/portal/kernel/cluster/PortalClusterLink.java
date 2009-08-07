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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.messaging.Message;

import java.util.List;

/**
 * <a href="PortalClusterLink.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * PortalClusterLink supports multiple channels. Channel is selected by
 * message's priority(see {@link PortalClusterMessagePriority}). The actual
 * priority -> channel mapping strategy is implementation depended.
 * </p>
 * @author Shuyang Zhou
 */
public interface PortalClusterLink {

	/**
	 * Send a MessageBus message to all cluster members with the given priority.
	 *
	 * @param message The MessageBus message to send.
	 * @param messagePriority The priority for the message.
	 */
	void sendMulticastMessage(
		Message message, PortalClusterMessagePriority messagePriority);

	/**
	 * Send a MessageBus message to a single cluster member base on the
	 * PortalClusterAddress with the given priority.
	 *
	 * @param destinationAddress The cluster member who is going to receive the
	 *		  message
	 * @param message The MessageBus message to send.
	 * @param messagePriority The priority for the message.
	 */
	void sendUnicastMessage(
		PortalClusterAddress destinationAddress, Message message,
		PortalClusterMessagePriority messagePriority);

	/**
	 * Get all cluster members' addresses.
	 * This is a snapshot for the current cluster, the return result should only
	 * be used for monitoring or display. Technically, the result is overdue
	 * immediately after you get it. So you can't do a "check then do" with this
	 * method, it is not safe.
	 *
	 * @return A list of all current existent cluster members' addresses.
	 */
	List<PortalClusterAddress> getAddresses();

}