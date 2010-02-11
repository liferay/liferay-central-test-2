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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.util.List;

/**
 * <a href="VerifyMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VerifyMessageBoards extends VerifyProcess {

	protected void doVerify() throws Exception {
		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getMBCategories(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + categories.size() +
					" categories for statistics accuracy");
		}

		for (MBCategory category : categories) {
			int threadCount = MBThreadLocalServiceUtil.getCategoryThreadsCount(
				category.getGroupId(), category.getCategoryId(),
				StatusConstants.APPROVED);
			int messageCount =
				MBMessageLocalServiceUtil.getCategoryMessagesCount(
					category.getGroupId(), category.getCategoryId(),
					StatusConstants.APPROVED);

			if ((category.getThreadCount() != threadCount) ||
				(category.getMessageCount() != messageCount)) {

				category.setThreadCount(threadCount);
				category.setMessageCount(messageCount);

				MBCategoryLocalServiceUtil.updateMBCategory(category);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Statistics verified for categories");
		}

		List<MBThread> threads = MBThreadLocalServiceUtil.getMBThreads(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + threads.size() +
					" threads for statistics accuracy");
		}

		for (MBThread thread : threads) {
			int messageCount = MBMessageLocalServiceUtil.getThreadMessagesCount(
				thread.getThreadId(), StatusConstants.APPROVED);

			if (thread.getMessageCount() != messageCount) {
				thread.setMessageCount(messageCount);

				MBThreadLocalServiceUtil.updateMBThread(thread);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Statistics verified for threads");
		}

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getNoAssetMessages();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + messages.size() + " messages with no asset");
		}

		for (MBMessage message : messages) {
			try {
				MBMessageLocalServiceUtil.updateAsset(
					message.getUserId(), message, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for message " +
							message.getMessageId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for messages");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyMessageBoards.class);

}