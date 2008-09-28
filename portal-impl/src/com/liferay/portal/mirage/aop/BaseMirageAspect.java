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

import com.sun.portal.cms.mirage.service.custom.BinaryContentService;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;
import com.sun.portal.cms.mirage.service.custom.ContentService;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="BaseMirageAspect.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public abstract class BaseMirageAspect {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		try {
			return doInvoke(proceedingJoinPoint);
		}
		catch (Throwable t) {
			Throwable cause = t.getCause();

			if (cause != null) {
				throw cause;
			}
			else {
				throw t;
			}
		}
	}

	protected abstract Object doInvoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable;

	@Resource(name = "com.liferay.portal.mirage.ArticleImageService")
	protected BinaryContentService articleImageService;

	@Resource(name = "com.liferay.portal.mirage.ArticleResourceService")
	protected BinaryContentService articleResourceService;

	@Resource(name = "com.liferay.portal.mirage.ContentFeedService")
	protected ContentFeedService contentFeedService;

	@Resource(name = "com.liferay.portal.mirage.ContentSearchService")
	protected BinaryContentService contentSearchService;

	@Resource(name = "com.liferay.portal.mirage.ContentService")
	protected ContentService contentService;

	@Resource(name = "com.liferay.portal.mirage.ContentTypeService")
	protected ContentTypeService contentTypeService;

	@Resource(name = "com.liferay.portal.mirage.WorkflowService")
	protected WorkflowService workflowService;

}