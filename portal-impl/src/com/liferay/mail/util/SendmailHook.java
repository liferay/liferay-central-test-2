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

package com.liferay.mail.util;

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ProcessUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.FileReader;

import java.util.List;

/**
 * <a href="SendmailHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SendmailHook implements Hook {

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		try {
			if (emailAddresses != null) {
				String home = PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_HOME);

				File file = new File(home + "/" + userId + "/.forward");

				if (emailAddresses.size() > 0) {
					StringBuilder sb = new StringBuilder();

					for (int i = 0; i < emailAddresses.size(); i++) {
						String emailAddress = emailAddresses.get(i);

						sb.append(emailAddress);
						sb.append("\n");
					}

					FileUtil.write(file, sb.toString());
				}
				else {
					file.delete();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		// Get add user command

		String addUserCmd =
			PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_ADD_USER);

		// Replace userId

		addUserCmd = StringUtil.replace(
			addUserCmd, "%1%", String.valueOf(userId));

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(addUserCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		updatePassword(companyId, userId, password);
		updateEmailAddress(companyId, userId, emailAddress);
	}

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {
	}

	public void deleteEmailAddress(long companyId, long userId) {
		updateEmailAddress(companyId, userId, "");
	}

	public void deleteUser(long companyId, long userId) {
		deleteEmailAddress(companyId, userId);

		// Get delete user command

		String deleteUserCmd =
			PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_DELETE_USER);

		// Replace userId

		deleteUserCmd = StringUtil.replace(
			deleteUserCmd, "%1%", String.valueOf(userId));

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(deleteUserCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		String home = PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_HOME);

		File file = new File(home + "/" + userId + "/.procmailrc");

		if ((blocked == null) || (blocked.size() == 0)) {
			file.delete();

			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("ORGMAIL /var/spool/mail/$LOGNAME\n");
		sb.append("MAILDIR $HOME/\n");
		sb.append("SENDMAIL /usr/smin/sendmail\n");

		for (int i = 0; i < blocked.size(); i++) {
			String emailAddress = blocked.get(i);

			sb.append("\n");
			sb.append(":0\n");
			sb.append("* ^From.*");
			sb.append(emailAddress);
			sb.append("\n");
			sb.append("{\n");
			sb.append(":0\n");
			sb.append("/dev/null\n");
			sb.append("}\n");
		}

		try {
			FileUtil.write(file, sb.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		try {
			String virtusertable =
				PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE);

			FileReader fr = new FileReader(virtusertable);
			UnsyncBufferedReader br = new UnsyncBufferedReader(fr);

			StringBuilder sb = new StringBuilder();

			for (String s = br.readLine(); s != null; s = br.readLine()) {
				if (!s.endsWith(" " + userId)) {
					sb.append(s);
					sb.append('\n');
				}
			}

			if ((emailAddress != null) && (!emailAddress.equals(""))) {
				sb.append(emailAddress);
				sb.append(" ");
				sb.append(userId);
				sb.append('\n');
			}

			br.close();
			fr.close();

			FileUtil.write(virtusertable, sb.toString());

			String virtusertableRefreshCmd =
				PropsUtil.get(
					PropsKeys.MAIL_HOOK_SENDMAIL_VIRTUSERTABLE_REFRESH);

			Runtime rt = Runtime.getRuntime();

			Process p = rt.exec(virtusertableRefreshCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updatePassword(long companyId, long userId, String password) {

		// Get change password command

		String changePasswordCmd =
			PropsUtil.get(PropsKeys.MAIL_HOOK_SENDMAIL_CHANGE_PASSWORD);

		// Replace userId

		changePasswordCmd = StringUtil.replace(
			changePasswordCmd, "%1%", String.valueOf(userId));

		// Replace password

		changePasswordCmd = StringUtil.replace(
			changePasswordCmd, "%2%", password);

		Runtime rt = Runtime.getRuntime();

		try {
			Process p = rt.exec(changePasswordCmd);

			ProcessUtil.close(p);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SendmailHook.class);

}