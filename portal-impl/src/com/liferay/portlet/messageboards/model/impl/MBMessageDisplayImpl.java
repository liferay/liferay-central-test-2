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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.model.MBTreeWalker;

/**
 * <a href="MBMessageDisplayImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBMessageDisplayImpl implements MBMessageDisplay {

	public MBMessageDisplayImpl(
		MBMessage message, MBMessage parentMessage, MBCategory category,
		MBThread thread, MBThread previousThread, MBThread nextThread,
		int status, String threadView) {

		_message = message;
		_parentMessage = parentMessage;
		_category = category;
		_thread = thread;

		if (!threadView.equals(MBThreadConstants.THREAD_VIEW_FLAT)) {
			_treeWalker = new MBTreeWalkerImpl(message, status);
		}

		_previousThread = previousThread;
		_nextThread = nextThread;
		_threadView = threadView;
	}

	public MBCategory getCategory() {
		return _category;
	}

	public MBMessage getMessage() {
		return _message;
	}

	public MBThread getNextThread() {
		return _nextThread;
	}

	public MBMessage getParentMessage() {
		return _parentMessage;
	}

	public MBThread getPreviousThread() {
		return _previousThread;
	}

	public MBThread getThread() {
		return _thread;
	}

	public String getThreadView() {
		return _threadView;
	}

	public MBTreeWalker getTreeWalker() {
		return _treeWalker;
	}

	private MBCategory _category;
	private MBMessage _message;
	private MBThread _nextThread;
	private MBMessage _parentMessage;
	private MBThread _previousThread;
	private MBThread _thread;
	private String _threadView;
	private MBTreeWalker _treeWalker;

}