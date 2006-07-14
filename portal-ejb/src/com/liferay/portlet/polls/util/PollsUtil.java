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

package com.liferay.portlet.polls.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.spring.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.spring.PollsVoteLocalServiceUtil;
import com.liferay.util.StringPool;

import java.util.Iterator;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * <a href="PollsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Shepherd Ching
 *
 */
public class PollsUtil {

	public static CategoryDataset getVotesDataset(String questionId)
		throws SystemException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String seriesName = StringPool.BLANK;

		Iterator itr = PollsChoiceLocalServiceUtil.getChoices(
			questionId).iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();

			Integer number = new Integer(
				PollsVoteLocalServiceUtil.getVotesCount(
					questionId, choice.getChoiceId()));

			dataset.addValue(number, seriesName, choice.getChoiceId());
		}

		return dataset;
	}

	public static boolean hasVoted(HttpServletRequest req, String questionId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

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
			HttpSession ses = req.getSession();

			Boolean hasVoted = (Boolean)ses.getAttribute(
				PollsQuestion.class.getName() + "." + questionId);

			if ((hasVoted != null) && (hasVoted.booleanValue())) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static void saveVote(ActionRequest req, String questionId) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		saveVote(reqImpl.getHttpServletRequest(), questionId);
	}

	public static void saveVote(RenderRequest req, String questionId) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		saveVote(reqImpl.getHttpServletRequest(), questionId);
	}

	public static void saveVote(HttpServletRequest req, String questionId) {
		HttpSession ses = req.getSession();

		ses.setAttribute(
			PollsQuestion.class.getName() + "." + questionId, Boolean.TRUE);
	}

}