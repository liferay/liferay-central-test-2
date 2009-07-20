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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;

public class MBMessageDisplayImpl implements MBMessageDisplay {

	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, MBThread previousThread, MBThread nextThread,
		String threadView) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		if (!threadView.equals(MBThreadImpl.THREAD_VIEW_FLAT)) {
			_treeWalker = new MBTreeWalkerImpl(message);
		}

		_previousThread = previousThread;
		_nextThread = nextThread;
		_threadView = threadView;
	}

	public MBMessage getMessage() {
		return _message;
	}

	public MBMessage getParentMessage() {
		return _parentMessage;
	}

	public MBCategory getCategory() {
		return _category;
	}

	public MBThread getThread() {
		return _thread;
	}

	public MBTreeWalker getTreeWalker() {
		return _treeWalker;
	}

	public MBThread getPreviousThread() {
		return _previousThread;
	}

	public MBThread getNextThread() {
		return _nextThread;
	}

	public String getThreadView() {
		return _threadView;
	}

	private MBMessage _message;
	private MBMessage _parentMessage;
	private MBCategory _category;
	private MBThread _thread;
	private MBTreeWalker _treeWalker;
	private MBThread _previousThread;
	private MBThread _nextThread;
	private String _threadView;

}