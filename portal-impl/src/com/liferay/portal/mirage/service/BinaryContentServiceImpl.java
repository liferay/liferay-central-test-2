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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.portal.mirage.util.MirageLoggerUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BinaryContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public abstract class BinaryContentServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent content) throws CMSException {
		MirageLoggerUtil.enter(_log, _className, "createBinaryContent");

		process(content);

		MirageLoggerUtil.exit(_log, _className, "createBinaryContent");
	}

	public void deleteBinaryContent(
			BinaryContent content, OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _className, "deleteBinaryContent");

		process(content);

		MirageLoggerUtil.exit(_log, _className, "deleteBinaryContent");
	}

	public void deleteBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _className, "deleteBinaryContents");

		process(criteria);

		MirageLoggerUtil.exit(_log, _className, "deleteBinaryContents");
	}

	public BinaryContent getBinaryContent(BinaryContent content)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _className, "getBinaryContent");

		process(content);

		MirageLoggerUtil.exit(_log, _className, "getBinaryContent");

		// The returned value is not being used so return the argument passed

		return content;
	}

	public long getBinaryContentId(BinaryContent content) throws CMSException {
		MirageLoggerUtil.enter(_log, _className, "getBinaryContentId");

		process(content);

		MirageLoggerUtil.exit(_log, _className, "getBinaryContentId");

		// Return the default value

		return 0;
	}

	public List<BinaryContent> getBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _className, "getBinaryContents");

		process(criteria);

		MirageLoggerUtil.exit(_log, _className, "getBinaryContents");

		// Return empty list

		return Collections.emptyList();
	}

	public void updateBinaryContent(BinaryContent content) {
		throw new UnsupportedOperationException();
	}

	protected abstract void process(BinaryContent content) throws CMSException;

	protected abstract void process(OptionalCriteria criteria)
		throws CMSException;

	private static final String _className =
		BinaryContentServiceImpl.class.getName();

	private static final Log _log =
		LogFactory.getLog(BinaryContentServiceImpl.class);

}