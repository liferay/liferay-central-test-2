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

package com.liferay.portlet.polls.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * <a href="PollsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shepherd Ching
 */
public class PollsUtil {

	public static CategoryDataset getVotesDataset(long questionId)
		throws SystemException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String seriesName = StringPool.BLANK;

		for (PollsChoice choice :
				PollsChoiceLocalServiceUtil.getChoices(questionId)) {

			Integer number = choice.getVotesCount();

			dataset.addValue(number, seriesName, choice.getName());
		}

		return dataset;
	}

	public static boolean hasVoted(HttpServletRequest request, long questionId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn()) {
			try {
				PollsVoteLocalServiceUtil.getVote(
					questionId, themeDisplay.getUserId());
			}
			catch (NoSuchVoteException nsve) {
				return false;
			}

			return true;
		}
		else {
			HttpSession session = request.getSession();

			Boolean hasVoted = (Boolean)session.getAttribute(
				PollsQuestion.class.getName() + "." + questionId);

			if ((hasVoted != null) && (hasVoted.booleanValue())) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static void saveVote(ActionRequest actionRequest, long questionId) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		saveVote(request, questionId);
	}

	public static void saveVote(RenderRequest renderRequest, long questionId) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		saveVote(request, questionId);
	}

	public static void saveVote(HttpServletRequest request, long questionId) {
		HttpSession session = request.getSession();

		session.setAttribute(
			PollsQuestion.class.getName() + "." + questionId, Boolean.TRUE);
	}

}