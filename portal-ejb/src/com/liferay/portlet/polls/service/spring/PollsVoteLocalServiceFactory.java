/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.spring;

import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

/**
 * <a href="PollsVoteLocalServiceFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsVoteLocalServiceFactory {
	public static final String CLASS_NAME = PollsVoteLocalServiceFactory.class.getName();
	public static final String TRANSACTION_CLASS_NAME = PollsVoteLocalService.class.getName() +
		".transaction";

	public static PollsVoteLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();
		PollsVoteLocalServiceFactory factory = (PollsVoteLocalServiceFactory)ctx.getBean(CLASS_NAME);

		return factory._service;
	}

	public void setService(PollsVoteLocalService service) {
		_service = service;
	}

	public static PollsVoteLocalService getTxImpl() {
		ApplicationContext ctx = SpringUtil.getContext();
		PollsVoteLocalService service = (PollsVoteLocalService)ctx.getBean(TRANSACTION_CLASS_NAME);

		return service;
	}

	private PollsVoteLocalService _service;
}