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

package com.liferay.portal.mirage.model;

import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="OptionalJournalTemplateCriteria.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public class OptionalJournalTemplateCriteria implements OptionalCriteria {

	public OptionalJournalTemplateCriteria(String finder) {

		_options.put(FINDER, finder);
	}

	public Map<String, String> getOptions() {

		return _options;
	}

	public static final String COMPANY_ID = "companyId";
	public static final String DESCRIPTION = "description";
	public static final String GROUP_ID = "groupId";
	public static final String KEYWORDS = "keywords";
	public static final String NAME = "name";
	public static final String STRUCTURE_ID = "structureId";
	public static final String STRUCTURE_ID_COMPARATOR =
		"structureIdComparator";
	public static final String TEMPLATE_ID = "templateId";
	public static final String SEARCH = "search";
	public static final String FINDER = "finder";
	public static final String FIND_BY_PRIMARY_KEY = "findByPrimaryKey";
	public static final String FIND_BY_SMALL_IMAGE_ID = "findBySmallImageId";
	public static final String FIND_BY_GROUP_AND_STRUCTURE =
		"findByGroupAndStructure";
	public static final String FIND_BY_GROUP_AND_TEMPLATE_ID =
		"findByGroupAndTemplateId";
	public static final String FIND_BY_GROUP_ID = "findByGroupId";
	public static final String FIND_BY_GROUP_ID_WITH_LIMIT =
		"findByGroupIdWithLimit";
	public static final String FIND_BY_GROUP_AND_STRUCTURE_WITH_LIMIT =
		"findByGroupAndStructureWithLimit";
	public static final String FIND_BY_KEYWORDS = "findByKeywords";
	public static final String FIND_BY_TEMPLATE_ID = "findByTemplateId";
	public static final String RANGE_START = "start";
	public static final String RANGE_END = "end";
	public static final String FIND_ALL = "find";
	public static final String COUNT_BY_GROUP_AND_STRUCTURE =
		"countByGroupAndStructure";
	public static final String COUNT_BY_GROUP_ID = "countByGroupId";
	public static final String COUNT_BY_KEYWORDS = "countByKeywords";
	public static final String SEARCH_COUNT = "searchCount";
	public static final String ARTICLE_COUNT_BY_GROUP_AND_TEMPLATE =
		"articleCountByGroupAndTemplate";

	private Map<String, String> _options = new HashMap<String, String>();

}