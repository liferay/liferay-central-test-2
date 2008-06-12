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

package com.liferay.portal.mirage.custom;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

/**
 * <a href="WorkflowServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class WorkflowServiceImpl implements WorkflowService {

	public void submitContentToWorkflow(String contentUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateWorkflowCommentsAccepted(String contentUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateWorkflowComplete(String contentUUID)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateWorkflowContentRejected(
			String contentUUID, String comments)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

	public void updateWorkflowStatus(String contentUUID, String status)
		throws CMSException {

		throw new UnsupportedOperationException();
	}

}