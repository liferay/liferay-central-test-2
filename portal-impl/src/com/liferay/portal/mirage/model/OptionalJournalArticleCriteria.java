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
 * <a href="OptionalJournalArticleCriteria.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Joshna
 *
 */
public class OptionalJournalArticleCriteria implements OptionalCriteria {

	public OptionalJournalArticleCriteria(String finder) {
		_options.put(FINDER, finder);
	}

	public Map<String, String> getOptions() {
		return _options;
	}

	public static final String FIND_BY_G_A_V = "findByG_A_V";
	public static final String FIND_BY_G_S = "findByG_S";
	public static final String FIND_BY_G_T = "findByG_T";
	public static final String FIND_BY_G_S_ORDER = "findByG_S_Order";
	public static final String FIND_BY_G_T_ORDER = "findByG_T_Order";
	public static final String FIND_BY_G_A = "findByG_A";
	public static final String FIND_BY_G_A_A = "findByG_A_A";
	public static final String FIND_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R =
			"findByC_G_A_V_T_D_C_T_S_T_D_A_E_R";
	public static final String FIND_BY_STRUCT_AND_TEMPLATE_IDS =
			"findByStructAndTemplateIds";
	public static final String FIND_BY_PRIMARY_KEY = "findByPrimaryKey";
	public static final String FIND_ALL = "findAllStructures";
	public static final String FIND_BY_GROUP = "findByGroup";
	public static final String FIND_BY_COMPANY_ID = "findByCompanyId";
	public static final String FIND_BY_KEYWORDS = "findByKeywords";
	public static final String FIND_BY_EXPIRATION_DATE = "findByExpirationDate";
	public static final String FIND_BY_REVIEW_DATE = "findByReviewDate";
	public static final String COUNT_BY_KEYWORDS = "countByKeywords";
	public static final String COUNT_BY_G_S = "countByG_S";
	public static final String COUNT_BY_G_T = "countByG_T";
	public static final String COUNT_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R =
			"countByC_G_A_V_T_D_C_T_S_T_D_A_E_R";
	public static final String COUNT_BY_STRUCT_AND_TEMPLATE_IDS =
			"countByStructAndTemplateIds";
	public static final String FIND_BY_GROUP_AND_ORDER = "findByGroupAndOrder";
	public static final String FIND_BY_GROUP_LIMIT = "findByGroupLimit";
	public static final String FIND_BY_SMALL_IMAGE_ID = "findBySmallImageId";
	public static final String FIND_LATEST = "findLatest";
	public static final String FINDER = "finder";
	public static final String RANGE_START = "rangeStart";
	public static final String RANGE_END = "rangeEnd";
	public static final String ORDER_BY_COMPARATOR = "orderByComparator";
	public static final String COMPANY_ID = "companyId";
	public static final String GROUP_ID = "groupId";
	public static final String KEYWORDS = "keywords";
	public static final String STRUCTURE_ID = "structureId";
	public static final String STRUCTURE_IDS = "structureIds";
	public static final String TYPE = "type";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String AND_OPERATOR = "andOperator";
	public static final String DESCRIPTION = "description";
	public static final String TEMPLATE_ID = "templateId";
	public static final String TEMPLATE_IDS = "templateIds";
	public static final String DISPLAY_DATE_GT = "displayDateGT";
	public static final String DISPLAY_DATE_LT = "displayDateLT";
	public static final String EXPIRATION_DATE_GT = "expirationDateGT";
	public static final String EXPIRATION_DATE_LT = "expirationDateLT";
	public static final String REVIEW_DATE_GT = "reviewDateGT";
	public static final String REVIEW_DATE_LT = "reviewDateLT";
	public static final String REVIEW_DATE = "reviewDate";
	public static final String ARTICLE_ID = "articleId";
	public static final String VERSION = "version";
	public static final String SMALL_IMAGE_ID = "smallImageId";
	public static final String APPROVED = "approved";
	public static final String EXPIRED = "expired";
	public static final String NAME = "name";

	private Map<String, String> _options = new HashMap<String, String>();

}