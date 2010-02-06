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

package com.liferay.portal.kernel.messaging;

import java.util.Collection;

/**
 * <a href="MessageBus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface MessageBus {

	public void addDestination(Destination destination);

	public void addDestinationEventListener(
		DestinationEventListener destinationEventListener);

	public void addDestinationEventListener(
		String destinationName,
		DestinationEventListener destinationEventListener);

	public Destination getDestination(String destinationName);

	public int getDestinationCount();

	public Collection<String> getDestinationNames();

	public Collection<Destination> getDestinations();

	public boolean hasDestination(String destinationName);

	public boolean hasMessageListener(String destinationName);

	public boolean registerMessageListener(
		String destinationName, MessageListener messageListener);

	public Destination removeDestination(String destinationName);

	public void removeDestinationEventListener(
		DestinationEventListener destinationEventListener);

	public void removeDestinationEventListener(
		String destinationName,
		DestinationEventListener destinationEventListener);

	public void replace(Destination destination);

	public void sendMessage(String destinationName, Message message);

	public void shutdown();

	public void shutdown(boolean force);

	public boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener);

}