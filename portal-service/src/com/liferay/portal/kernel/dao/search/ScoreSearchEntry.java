/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ScoreSearchEntry extends SearchEntry {

	public Object clone() {
		ScoreSearchEntry scoreSearchEntry = new ScoreSearchEntry();

		BeanPropertiesUtil.copyProperties(this, scoreSearchEntry);

		return scoreSearchEntry;
	}

	public float getScore() {
		return _score;
	}

	public void print(PageContext pageContext) throws Exception {
		pageContext.include("/html/taglib/ui/search_iterator/score.jsp");
	}

	public void setScore(float score) {
		_score = score;
	}

	private float _score;

}