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

import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Calendar;

/**
 * <a href="MBMessageFlagImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagImpl
	extends MBMessageFlagModelImpl implements MBMessageFlag {

	public static final int ANSWER_FLAG = 3;

	public static final int READ_FLAG = 1;

	public static final int QUESTION_FLAG = 2;

	public MBMessageFlagImpl() {
	}

	public boolean isRead(MBMessage message) {
		Calendar lastRead = Calendar.getInstance();

		lastRead.setTime(getModifiedDate());
		lastRead.add(Calendar.SECOND, _MARGIN_SECONDS);

		if ((DateUtil.compareTo(
				lastRead.getTime(), message.getModifiedDate()) == -1)) {
			return false;
		}

		return true;
	}

	private static final int _MARGIN_SECONDS = 2;

}