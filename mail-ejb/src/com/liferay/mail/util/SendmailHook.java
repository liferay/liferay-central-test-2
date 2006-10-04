/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.mail.util;

import com.liferay.portal.kernel.util.ProcessUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SendmailHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SendmailHook implements Hook {

	public void addForward(
		String userId, List emailAddresses, boolean leaveCopy) {

		try {
			if (emailAddresses != null) {
				String home = PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_HOME);

				File file = new File(home + "/" + userId + "/.forward");

				if (emailAddresses.size() > 0) {
					StringBuffer sb = new StringBuffer();

					for (int i = 0; i < emailAddresses.size(); i++) {
						String emailAddress = (String)emailAddresses.get(i);
						sb.append(emailAddress).append("\n");
					}

					FileUtil.write(file, sb.toString());
				}
				else {
					file.delete();
				}
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}

	public void addUser(
		String userId, String password, String firstName, String middleName,
		String lastName, String emailAddress) {

		// Get add user command

		String addUserCmd =
			PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_ADD_USER);

		// Replace userId

		addUserCmd = StringUtil.replace(addUserCmd, "%1%", userId);

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(addUserCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}

		updatePassword(userId, password);
		updateEmailAddress(userId, emailAddress);
	}

	public void addVacationMessage(
		String userId, String emailAddress, String vacationMessage) {
	}

	public void deleteEmailAddress(String userId) {
		updateEmailAddress(userId, "");
	}

	public void deleteUser(String userId) {
		deleteEmailAddress(userId);

		// Get delete user command

		String deleteUserCmd =
			PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_DELETE_USER);

		// Replace userId

		deleteUserCmd = StringUtil.replace(deleteUserCmd, "%1%", userId);

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(deleteUserCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}

	public void updateBlocked(String userId, List blocked) {
		String home = PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_HOME);

		File file = new File(home + "/" + userId + "/.procmailrc");

		if ((blocked == null) || (blocked.size() == 0)) {
			file.delete();

			return;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("ORGMAIL /var/spool/mail/$LOGNAME\n");
		sb.append("MAILDIR $HOME/\n");
		sb.append("SENDMAIL /usr/sbin/sendmail\n");

		for (int i = 0; i < blocked.size(); i++) {
			String emailAddress = (String)blocked.get(i);

			sb.append("\n");
			sb.append(":0\n");
			sb.append("* ^From.*").append(emailAddress).append("\n");
			sb.append("{\n");
			sb.append(":0\n");
			sb.append("/dev/null\n");
			sb.append("}\n");
		}

		try {
			FileUtil.write(file, sb.toString());
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}

	public void updateEmailAddress(String userId, String emailAddress) {
		try {
			String virtusertable =
				PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE);

			FileReader fr = new FileReader(virtusertable);
			BufferedReader br = new BufferedReader(fr);

			StringBuffer sb = new StringBuffer();

			for (String s = br.readLine(); s != null; s = br.readLine()) {
				if (!s.endsWith(" " + userId)) {
					sb.append(s).append('\n');
				}
			}

			if ((emailAddress != null) && (!emailAddress.equals(""))) {
				sb.append(emailAddress).append(" ").append(userId).append('\n');
			}

			br.close();
			fr.close();

			FileUtil.write(virtusertable, sb.toString());

			String virtusertableRefreshCmd =
				PropsUtil.get(
					PropsUtil.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE_REFRESH);

			Runtime rt = Runtime.getRuntime();

			Process p = rt.exec(virtusertableRefreshCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}

	public void updatePassword(String userId, String password) {

		// Get change password command

		String changePasswordCmd =
			PropsUtil.get(PropsUtil.MAIL_HOOK_SENDMAIL_CHANGE_PASSWORD);

		// Replace userId

		changePasswordCmd =
			StringUtil.replace(changePasswordCmd, "%1%", userId);

		// Replace password

		changePasswordCmd =
			StringUtil.replace(changePasswordCmd, "%2%", password);

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(changePasswordCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}

	private static Log _log = LogFactory.getLog(SendmailHook.class);

}