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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateGroupId();
		updateMessageClassNameId();
		updateMessageFlagThreadId();
		updateMessagePriority();
	}

	protected void updateGroupId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update MBCategory set threadCount = (select count(*) from ");
		sb.append("MBThread where MBThread.categoryId = ");
		sb.append("MBCategory.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBCategory set messageCount = (select count(*) ");
		sb.append("from MBMessage where MBMessage.categoryId = ");
		sb.append("MBCategory.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBMessage set groupId = (select groupId from ");
		sb.append("MBCategory where MBCategory.categoryId = ");
		sb.append("MBMessage.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBThread set groupId = (select groupId from ");
		sb.append("MBCategory where MBCategory.categoryId = ");
		sb.append("MBThread.categoryId)");

		runSQL(sb.toString());
	}

	protected void updateMessageClassNameId() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("update MBMessage set classNameId = (select classNameId ");
		sb.append("from MBDiscussion where MBDiscussion.threadId = ");
		sb.append("MBMessage.threadId), classPK = (select classPK from ");
		sb.append("MBDiscussion where MBDiscussion.threadId = ");
		sb.append("MBMessage.threadId)");

		runSQL(sb.toString());
	}

	protected void updateMessageFlagThreadId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update MBMessageFlag set threadId = (select threadId from ");
		sb.append("MBMessage where MBMessage.messageId = ");
		sb.append("MBMessageFlag.messageId)");

		runSQL(sb.toString());
	}

	protected void updateMessagePriority() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("update MBMessage set priority = (select priority from ");
		sb.append("MBThread where MBThread.threadId = MBMessage.threadId)");

		runSQL(sb.toString());
	}

}