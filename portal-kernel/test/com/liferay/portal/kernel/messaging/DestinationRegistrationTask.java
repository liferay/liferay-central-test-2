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

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * <a href="DestinationRegistrationTask.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DestinationRegistrationTask implements Callable<Object> {

	public DestinationRegistrationTask(
		Destination destination, MessageListener[] listeners,
		int taskIterationCount, boolean register) {

		_destination = destination;
		_listeners = listeners;
		_taskIterationCount = taskIterationCount;
		_register = register;
	}

	public Object call() {
		Random random = new Random();

		for (int i = 0; i < _taskIterationCount; i++) {
			MessageListener listener =
				_listeners[random.nextInt(_listeners.length)];

			if (_register) {
				_destination.register(listener);
			}
			else {
				_destination.unregister(listener);
			}
		}

		return null;
	}

	private Destination _destination;
	private MessageListener[] _listeners;
	private boolean _register;
	private int _taskIterationCount;

}