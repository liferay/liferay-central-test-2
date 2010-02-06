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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;

import java.io.Serializable;

import java.util.List;

/**
 * <a href="BasePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BasePersistence<T extends BaseModel<T>> {

	public void clearCache();

	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException;

	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException;

	public ModelListener<T>[] getListeners();

	public void registerListener(ModelListener<T> listener);

	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	public T remove(T model) throws SystemException;

	public void unregisterListener(ModelListener<T> listener);

	public T update(T model, boolean merge) throws SystemException;

}