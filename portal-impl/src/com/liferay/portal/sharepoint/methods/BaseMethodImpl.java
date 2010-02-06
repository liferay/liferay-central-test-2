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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.List;

/**
 * <a href="BaseMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public abstract class BaseMethodImpl implements Method {

	public String getRootPath(SharepointRequest sharepointRequest) {
		return StringPool.BLANK;
	}

	public void process(SharepointRequest sharepointRequest)
		throws SharepointException {

		try {
			doProcess(sharepointRequest);
		}
		catch (Exception e) {
			throw new SharepointException(e);
		}
	}

	protected abstract List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception;

	protected void doProcess(SharepointRequest sharepointRequest)
		throws Exception {

		ServletResponseUtil.write(
			sharepointRequest.getHttpServletResponse(),
			getResponseBuffer(sharepointRequest).toString());
	}

	protected StringBuilder getResponseBuffer(
			SharepointRequest sharepointRequest)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		SharepointUtil.addTop(sb, getMethodName());

		List<ResponseElement> elements = getElements(sharepointRequest);

		for (ResponseElement element : elements) {
			sb.append(element.parse());
		}

		SharepointUtil.addBottom(sb);

		return sb;
	}

}