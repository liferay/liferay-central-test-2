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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.velocity.LiferayResourceCacheUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

/**
 * <a href="JournalTemplateListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Raymond Augé
 */
public class JournalTemplateListener
	extends BaseModelListener<JournalTemplate> {

	public void onAfterRemove(JournalTemplate template) {
		clearCache(template);
	}

	public void onAfterUpdate(JournalTemplate template) {
		clearCache(template);
	}

	protected void clearCache(JournalTemplate template) {

		// Journal content

		JournalContentUtil.clearCache();

		// Layout cache

		CacheUtil.clearCache(template.getCompanyId());

		// Velocity cache

		LiferayResourceCacheUtil.clear();

		VelocityEngineUtil.flushTemplate(
			template.getCompanyId() + template.getGroupId() +
				template.getTemplateId());
	}

}