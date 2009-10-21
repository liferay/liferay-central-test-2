/*
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

package com.liferay.portal.kernel.messaging;

/**
 * <a href="ThrottleAwareDestination.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Destination that delivers a message to a list of message listeners one at a
 * time.
 * </p>
 *
 * @author Michael C. Han
 */
public class ThrottleAwareDestination implements Destination {

    public ThrottleAwareDestination() {
        super();
    }

	public ThrottleAwareDestination(Destination delegateDestination, int maximumQueueSize) {
        _delegateDestination = delegateDestination;
        _maximumQueueSize = maximumQueueSize;
	}

    public void afterPropertiesSet() {
        if (_delegateDestination == null) {
            throw new IllegalStateException("No delegate destination configured");
        }
    }

    public void setDelegateDestination(Destination delegateDestination) {
        _delegateDestination = delegateDestination;
    }

    public void setMaximumQueueSize(int maximumQueueSize) {
        _maximumQueueSize = maximumQueueSize;
    }

    public void addDestinationEventListener(DestinationEventListener destinationEventListener) {
        _delegateDestination.addDestinationEventListener(destinationEventListener);
    }

    public void close() {
        _delegateDestination.close();
    }

    public void close(boolean force) {
        _delegateDestination.close(force);
    }

    public void copyDestinationEventListeners(Destination destination) {
        _delegateDestination.copyDestinationEventListeners(destination);
    }

    public void copyMessageListeners(Destination destination) {
        _delegateDestination.copyMessageListeners(destination);
    }

    public DestinationStatistics getDestinationStatistics() {
        return _delegateDestination.getDestinationStatistics();
    }

    public int getMessageListenerCount() {
        return _delegateDestination.getMessageListenerCount();
    }

    public String getName() {
        return _delegateDestination.getName();
    }

    public int getPendingRequestCount() {
        return _delegateDestination.getPendingRequestCount();
    }

    public boolean isRegistered() {
        return _delegateDestination.isRegistered();
    }

    public void open() {
        _delegateDestination.open();
    }

    public boolean register(MessageListener messageListener) {
        return _delegateDestination.register(messageListener);
    }

    public boolean register(MessageListener messageListener, ClassLoader classloader) {
        return _delegateDestination.register(messageListener, classloader);
    }

    public void removeDestinationEventListener(
            DestinationEventListener destinationEventListener) {
        _delegateDestination.removeDestinationEventListener(destinationEventListener);
    }

    public void removeDestinationEventListeners() {
        _delegateDestination.removeDestinationEventListeners();
    }

    public void send(Message message) {
        if ((_maximumQueueSize > 0) &&
            (getPendingRequestCount() > _maximumQueueSize)) {
            throw new IllegalStateException(
                    getName() + " has exceeded the maximum number of pending " +
                            "requests.  Current size: " + getPendingRequestCount() +
                            " maximum allowed: " + _maximumQueueSize);
        }
        _delegateDestination.send(message);
    }

    public boolean unregister(MessageListener messageListener) {
        return _delegateDestination.unregister(messageListener);
    }

    public void unregisterMessageListeners() {
        _delegateDestination.unregisterMessageListeners();
    }

    private Destination _delegateDestination;    
    private int _maximumQueueSize = -1;

}