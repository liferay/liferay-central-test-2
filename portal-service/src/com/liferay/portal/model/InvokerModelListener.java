/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.ModelListenerException;

/**
 * <a href="InvokerModelListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InvokerModelListener<T> implements ModelListener<T> {

	public InvokerModelListener(
		ModelListener<T> modelListener, ClassLoader classLoader) {

		_modelListener = modelListener;
		_classLoader = classLoader;
	}

	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onAfterAddAssociation(
				classPK, associationClassName, associationClassPK);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterCreate(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onAfterCreate(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterRemove(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onAfterRemove(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onAfterRemoveAssociation(
				classPK, associationClassName, associationClassPK);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onAfterUpdate(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onAfterUpdate(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onBeforeAddAssociation(
				classPK, associationClassName, associationClassPK);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeCreate(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onBeforeCreate(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeRemove(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onBeforeRemove(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onBeforeRemoveAssociation(
				classPK, associationClassName, associationClassPK);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void onBeforeUpdate(T model) throws ModelListenerException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_modelListener.onBeforeUpdate(model);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private ModelListener<T> _modelListener;
	private ClassLoader _classLoader;

}