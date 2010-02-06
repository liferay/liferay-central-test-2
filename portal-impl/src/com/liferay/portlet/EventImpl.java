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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.Base64;

import java.io.Serializable;

import javax.portlet.Event;

import javax.xml.namespace.QName;

/**
 * <a href="EventImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EventImpl implements Event {

	public EventImpl(String name, QName qName, Serializable value) {
		_name = name;
		_qName = qName;
		_value = value;
	}

	public String getBase64Value() {
		if (_base64Value != null) {
			return _base64Value;
		}
		else {
			_base64Value = Base64.objectToString(_value);

			return _base64Value;
		}
	}

	public String getName() {
		return _name;
	}

	public QName getQName() {
		return _qName;
	}

	public Serializable getValue() {
		return _value;
	}

	private String _base64Value;
	private String _name;
	private QName _qName;
	private Serializable _value;

}