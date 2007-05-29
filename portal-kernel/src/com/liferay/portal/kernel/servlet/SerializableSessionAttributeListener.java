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
		
		if(!isSerializable(event.getValue().getClass())){
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
	
	private boolean isSerializable(Class clazz){
		
		Class superclass = clazz.getSuperclass();
		
		if(superclass != null && isSerializable(superclass)){
			return true;
		}
		
		Class[] interfaces = clazz.getInterfaces();
		
		for (int i = 0; i < interfaces.length; i++) {
			Class interfac = interfaces[i];
			
			if(interfac.getName().equals("java.io.Serializable")){
				return true;
			}
		}
		
		return false;
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		this.attributeAdded(event);
	}

	/*public static void main(String[] args) {
		SerializableSessionAttributeListener test = new SerializableSessionAttributeListener();
		System.out.println(test.isSerializable(Long.class)); 
        System.out.println(test.isSerializable(LinkedHashMap.class));
		System.out.println(test.isSerializable(PortletSessionTracker.class));
	}*/

}
