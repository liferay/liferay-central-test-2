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

import com.liferay.portal.mirage.aop.ContentSearchInvoker;
import com.liferay.portal.mirage.model.MirageJournalContentSearch;
import com.liferay.portal.mirage.util.MirageLoggerUtil;
import com.liferay.portlet.journal.model.JournalContentSearch;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.BinaryContent;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.service.custom.BinaryContentService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ContentSearchServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class ContentSearchServiceImpl implements BinaryContentService {

	public void createBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "createBinaryContent");

		process(binaryContent);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "createBinaryContent");
	}

	public void deleteBinaryContent(
			BinaryContent binaryContent, OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "deleteBinaryContent");

		process(binaryContent);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "deleteBinaryContent");
	}

	public void deleteBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "deleteBinaryContents");

		process(criteria);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "deleteBinaryContents");
	}

	public BinaryContent getBinaryContent(BinaryContent binaryContent) {
		throw new UnsupportedOperationException();
	}

	public long getBinaryContentId(BinaryContent binaryContent) {
		throw new UnsupportedOperationException();
	}

	public List<BinaryContent> getBinaryContents(OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "getBinaryContents");

		process(criteria);

		ContentSearchInvoker contentSearchInvoker =
			(ContentSearchInvoker)criteria;

		List<JournalContentSearch> contentSearches =
			(List<JournalContentSearch>)contentSearchInvoker.getReturnValue();

		List<BinaryContent> binaryContents =
			new ArrayList<BinaryContent>(contentSearches.size());

		for (JournalContentSearch contentSearch : contentSearches) {
			binaryContents.add(new MirageJournalContentSearch(contentSearch));
		}

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "getBinaryContents");

		return binaryContents;
	}

	public void updateBinaryContent(BinaryContent binaryContent)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "updateBinaryContent");

		process(binaryContent);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "updateBinaryContent");
	}

	protected void process(BinaryContent binaryContent) throws CMSException {
		ContentSearchInvoker contentSearchInvoker =
			(ContentSearchInvoker)binaryContent;

		contentSearchInvoker.invoke();
	}

	protected void process(OptionalCriteria criteria) throws CMSException {
		ContentSearchInvoker contentSearchInvoker =
			(ContentSearchInvoker)criteria;

		contentSearchInvoker.invoke();
	}

	private static final String _CLASS_NAME =
		ContentSearchServiceImpl.class.getName();

	private static final Log _log =
		LogFactory.getLog(ContentSearchServiceImpl.class);

}