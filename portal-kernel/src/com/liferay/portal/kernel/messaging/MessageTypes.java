/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 *
 */

package com.liferay.portal.kernel.messaging;

/**
 * <a href="MessageTypes.java.html"><b><i>View Source</i></b></a>
 *
 * Constant definition for the types of messages sent by the bus.
 *
 * @author Michael C. Han
 */
public class MessageTypes {
	public static final String LIVE_USER_MESSAGE = "msg.type.live.users";
	public static final String MB_NOTIFICATION_MESSAGE = "msg.type.mb.notification";
	public static final String WIKI_NOTIFICATION_MESSAGE = "msg.type.wiki.notification";
	public static final String RUON_MESSAGE = "msg.type.ruon";
	public static final String INDEX_READER_MESSAGE = "msg.type.index.reader";
	public static final String SCHEDULER_MESSAGE = "msg.type.scheduler";
	public static final String MAIL_MESSAGE = "msg.type.mail";
}
