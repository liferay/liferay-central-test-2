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

package com.liferay.portlet.messageboards.smtp;

import com.liferay.util.ObjectValuePair;
import com.liferay.util.Validator;
import com.liferay.util.GetterUtil;
import com.liferay.util.Html;

import java.util.List;
import java.util.ArrayList;

/**
 * <a href="MBMailMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
class MBMailMessage {
	String _htmlBody;
	String _plainBody;
	List files = new ArrayList();

	public void addFile(String fileName, byte[] data) {
		files.add(new ObjectValuePair(fileName, data));
	}

	public List getFiles() {
		return files;
	}


	public String getHtmlBody() {
		return _htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this._htmlBody = htmlBody;
	}

	public String getPlainBody() {
		return _plainBody;
	}

	public void setPlainBody(String plainBody) {
		this._plainBody = plainBody;
	}

	public String getBody() {
		if (Validator.isNotNull(_plainBody)) {
			return "<PRE>" + GetterUtil.getString(_plainBody) + "</PRE>";
		}
		else if (Validator.isNotNull(_htmlBody)) {
			return Html.stripHtml(_htmlBody);
		} else {
			return "--";
		}
	}
}
