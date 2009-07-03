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

package com.liferay.portal.kernel.messaging;

/**
 * <a href="DestinationNames.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface DestinationNames {

	public static final String CONVERT_PROCESS = "liferay/convert_process";

	/**
	 * This is a special destination. Binding a message listener to this
	 * destination will allow it to listen to messages sent to all destinations.
	 */
	public static final String GLOBAL = "liferay/global";

	public static final String HOT_DEPLOY = "liferay/hot_deploy";

	public static final String IP_GEOCODER = "liferay/ip_geocoder";

	public static final String IP_GEOCODER_RESPONSE =
		"liferay/ip_geocoder/response";

	public static final String LAYOUTS_LOCAL_PUBLISHER =
		"liferay/layouts_local_publisher";

	public static final String LAYOUTS_REMOTE_PUBLISHER =
		"liferay/layouts_remote_publisher";

	public static final String LIVE_USERS = "liferay/live_users";

	public static final String MAIL = "liferay/mail";

	public static final String MAIL_SYNCHRONIZER = "liferay/mail_synchronizer";

	public static final String MESSAGE_BOARDS = "liferay/message_boards";

	public static final String MESSAGE_BOARDS_MAILING_LIST =
		"liferay/message_boards_mailing_list";

	public static final String MESSAGE_BUS_DEFAULT_RESPONSE=
		"liferay/message_bus/default_response";

	public static final String MESSAGE_BUS_MESSAGE_STATUS =
		"liferay/message_bus/message_status";

	public static final String MONITORING = "liferay/monitoring";

	public static final String SAW = "liferay/saw";

	public static final String SCHEDULER = "liferay/scheduler";

	public static final String SCHEDULER_RESPONSE =
		"liferay/scheduler/response";

	public static final String SEARCH_READER = "liferay/search_reader";

	public static final String SEARCH_READER_RESPONSE =
		"liferay/search_reader/response";

	public static final String SEARCH_WRITER = "liferay/search_writer";

	public static final String WIKI = "liferay/wiki";

}