/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import java.io.Serializable;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * <a href="SerializableSessionAttributeListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */

public class SerializableSessionAttributeListener implements HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent event) {
		
		if(!(event.getValue() instanceof Serializable)){
			System.out.println(
						"WARN [com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener] " +
						"An object was added to session " +
						"but it doesn't implement java.io.Serializable.\n" +
						"If your application must be distributable, " +
						"every object stored in session must implement Serializable.\n" +
						"Object's attribute name: " + event.getName() + "\n" +
						"Object's class name: " + event.getValue().getClass());
		}
		
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		this.attributeAdded(event);
	}

}
