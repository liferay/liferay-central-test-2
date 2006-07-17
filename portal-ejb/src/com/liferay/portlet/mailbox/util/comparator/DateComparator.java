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
package com.liferay.portlet.mailbox.util.comparator;

import java.util.Comparator;

import com.liferay.portlet.mailbox.util.MailEnvelope;
import com.liferay.util.DateUtil;

/**
 * <a href="DateComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class DateComparator implements Comparator {

	public DateComparator(boolean asc) {
		_asc = asc;
	}
	
	public int compare(Object arg0, Object arg1) {
		MailEnvelope me0 = (MailEnvelope)arg0;
		MailEnvelope me1 = (MailEnvelope)arg1;
		
		if (_asc) {
			return DateUtil.compareTo(me0.getDate(), me1.getDate());			
		}
		else {
			return DateUtil.compareTo(me1.getDate(), me0.getDate());			
		}
	}
	
	private boolean _asc;

}