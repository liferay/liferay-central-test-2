/**
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
 */

package com.liferay.portal.mirage.aop;

import com.liferay.portal.mirage.service.MirageServiceFactory;

import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="JournalFeedLocalServiceInterceptor.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalFeedLocalServiceInterceptor extends MirageInterceptor {

	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();

		if (methodName.equals("addFeed") || methodName.equals("deleteFeed") ||
			methodName.equals("getFeed") || methodName.equals("updateFeed")) {

			ContentFeedInvoker contentFeedInvoker = new ContentFeedInvoker(
				invocation);

			ContentFeedService contentFeedService =
				MirageServiceFactory.getContentFeedService();

			if (methodName.equals("addFeed")) {
				contentFeedService.createContentFeed(contentFeedInvoker);
			}
			else if (methodName.equals("deleteFeed")) {
				contentFeedService.deleteContentFeed(contentFeedInvoker);
			}
			else if (methodName.equals("getFeed")) {
				contentFeedService.getContentFeed(contentFeedInvoker, null);
			}
			else if (methodName.equals("updateFeed")) {
				contentFeedService.updateContentFeed(contentFeedInvoker, null);
			}

			return contentFeedInvoker.getReturnValue();
		}
		else if (methodName.equals("getFeeds") ||
				 methodName.equals("getFeedsCount") ||
				 methodName.equals("search") ||
				 methodName.equals("searchCount")) {

			SearchCriteriaInvoker searchCriteriaInvoker =
				new SearchCriteriaInvoker(invocation);

			ContentFeedService contentFeedService =
				MirageServiceFactory.getContentFeedService();

			if (methodName.equals("getFeeds") || methodName.equals("search")) {
				contentFeedService.searchContentFeeds(searchCriteriaInvoker);
			}
			else if (methodName.equals("getFeedsCount") ||
					 methodName.equals("searchCount")) {

				contentFeedService.getContentFeedSearchCount(
					searchCriteriaInvoker);
			}

			return searchCriteriaInvoker.getReturnValue();
		}
		else {
			return invocation.proceed();
		}
	}

}