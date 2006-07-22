/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.mail.util.comparator;

import com.liferay.portlet.mail.model.MailEnvelope;
import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;

import java.util.Comparator;

/**
 * <a href="SubjectComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class SubjectComparator implements Comparator {

	public SubjectComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object arg0, Object arg1) {
		MailEnvelope me0 = (MailEnvelope)arg0;
		MailEnvelope me1 = (MailEnvelope)arg1;

		Long uid0 = new Long(me0.getMsgUID());
		Long uid1 = new Long(me1.getMsgUID());

		String subj0 = _stripPrefixes(me0.getSubject());
		String subj1 = _stripPrefixes(me1.getSubject());

		int comparison = 0;

		if (_asc) {
			comparison = subj0.compareTo(subj1);

			if (comparison == 0) {
				comparison = DateUtil.compareTo(me0.getDate(), me1.getDate());

				if (comparison == 0) {
					comparison = uid0.compareTo(uid1);
				}
			}
		}
		else {
			comparison = subj1.compareTo(subj0);

			if (comparison == 0) {
				comparison = DateUtil.compareTo(me1.getDate(), me0.getDate());

				if (comparison == 0) {
					comparison = uid1.compareTo(uid0);
				}
			}
		}

		return comparison;
	}

	private String _stripPrefixes(String subject) {
		subject = GetterUtil.getString(subject).toLowerCase();

		while (subject.startsWith("re:") || subject.startsWith("re>") ||
			subject.startsWith("fw:") || subject.startsWith("fw>")) {

			subject = subject.substring(3).trim();
		}

		return subject;
	}

	private boolean _asc;

}