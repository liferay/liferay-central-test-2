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
 * <a href="PortalClusterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Util class for cluster.
 * </p>
 * @author Shuyang Zhou
 */
public class PortalClusterUtil {

	/**
	 * Attach the destination cluster member address to the MessageBus message.
	 *
	 * @param message The MessageBus message.
	 * @param portalClusterAddress The destination address.
	 * @return The MessageBus message with the destination address as a
	 *		   property.
	 */
	public static Message setPortalClusterAddress(
		Message message, PortalClusterAddress portalClusterAddress) {
		message.put(PORTAL_CLUSTER_ADDRESS_KEY,portalClusterAddress);
		return message;
	}

	/**
	 * Get the destination cluster member address from the Message message.
	 *
	 * @param message The MessageBus message.
	 * @return The destination cluster member address or null if no destination
	 *		   is attached to this message.
	 */
	public static PortalClusterAddress getPortalClusterAddress(
		Message message) {
		return (PortalClusterAddress) message.get(PORTAL_CLUSTER_ADDRESS_KEY);
	}

	/**
	 * Set a flag to the MessageBus message to indicate it is a forward message.
	 *
	 * @param message The MessageBus message.
	 */
	public static void setForwardMessage(Message message) {
		message.put(PORTAL_CLUSTER_FORWARD_MESSAGE_KEY, true);
	}

	/**
	 * Check the given MessageBus message is forward message or not.
	 *
	 * @param message The MessageBus message.
	 * @return true if the message is forwarded, otherwise false.
	 */
	public static boolean isForwardMessage(Message message) {
		return message.getBoolean(PORTAL_CLUSTER_FORWARD_MESSAGE_KEY);
	}

	/**
	 * Get all cluster members' addresses.
	 * This is a snapshot for the current cluster, the return result should only
	 * be used for monitoring or display. Technically, the result is overdue
	 * immediately after you get it. So you can't do a "check then do" with this
	 * method, it is not safe.
	 *
	 * @return A list of all current existent cluster members' addresses.
	 * @see PortalClusterLink
	 */
	public static List<PortalClusterAddress> getPortalClusterAddresses() {
		return _portalClusterLink.getAddresses();
	}

	/**
	 * Send a MessageBus message to all cluster members with the given priority.
	 *
	 * @param message The MessageBus message to send.
	 * @param messagePriority The priority for the message.
	 * @see PortalClusterLink
	 */
	public static void sendMulticastMessage(
		Message message, PortalClusterMessagePriority priority) {
		_portalClusterLink.sendMulticastMessage(message, priority);
	}

	/**
	 * Send a MessageBus message to a single cluster member base on the
	 * PortalClusterAddress with the given priority.
	 *
	 * @param destinationAddress The cluster member who is going to receive the
	 *		  message
	 * @param message The MessageBus message to send.
	 * @param messagePriority The priority for the message.
	 * @see PortalClusterLink
	 */
	public static void sendUnicastMessage(
		PortalClusterAddress destinationAddress, Message message,
		PortalClusterMessagePriority messagePriority) {
		_portalClusterLink.sendUnicastMessage(
			destinationAddress, message, messagePriority);
	}

	/**
	 * Get the holded PortalClusterLink.
	 *
	 * @return The PortalClusterLink or null if this util has not been
	 *		   initialized yet.
	 */
	public static PortalClusterLink getPortalClusterLink() {
		return _portalClusterLink;
	}

	/**
	 * Create a instance of this util class to set the PortalClusterLink.
	 * This is for Spring.
	 *
	 * @param portalClusterLink The PortalClusterLink
	 */
	public PortalClusterUtil(PortalClusterLink portalClusterLink) {
		_portalClusterLink = portalClusterLink;
	}

	/**
	 * Key for the cluster member address in MessageBus message.
	 */
	public static final String PORTAL_CLUSTER_ADDRESS_KEY =
		"PORTAL_CLUSTER_ADDRESS_KEY";
	/**
	 * Key for the forward MessageBus message flag in MessageBus message.
	 */
	public static final String PORTAL_CLUSTER_FORWARD_MESSAGE_KEY =
		"PORTAL_CLUSTER_FORWARD_MESSAGE_KEY";
	private static PortalClusterLink _portalClusterLink;

}