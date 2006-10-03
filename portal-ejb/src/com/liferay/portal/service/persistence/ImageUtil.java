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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="ImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ImageUtil {
	public static final String CLASS_NAME = ImageUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Image"));

	public static com.liferay.portal.model.Image create(
		java.lang.String imageId) {
		return getPersistence().create(imageId);
	}

	public static com.liferay.portal.model.Image remove(
		java.lang.String imageId)
		throws com.liferay.portal.NoSuchImageException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(imageId));
		}

		com.liferay.portal.model.Image image = getPersistence().remove(imageId);

		if (listener != null) {
			listener.onAfterRemove(image);
		}

		return image;
	}

	public static com.liferay.portal.model.Image remove(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(image);
		}

		image = getPersistence().remove(image);

		if (listener != null) {
			listener.onAfterRemove(image);
		}

		return image;
	}

	public static com.liferay.portal.model.Image update(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = image.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(image);
			}
			else {
				listener.onBeforeUpdate(image);
			}
		}

		image = getPersistence().update(image);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(image);
			}
			else {
				listener.onAfterUpdate(image);
			}
		}

		return image;
	}

	public static com.liferay.portal.model.Image update(
		com.liferay.portal.model.Image image, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = image.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(image);
			}
			else {
				listener.onBeforeUpdate(image);
			}
		}

		image = getPersistence().update(image, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(image);
			}
			else {
				listener.onAfterUpdate(image);
			}
		}

		return image;
	}

	public static com.liferay.portal.model.Image findByPrimaryKey(
		java.lang.String imageId)
		throws com.liferay.portal.NoSuchImageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(imageId);
	}

	public static com.liferay.portal.model.Image fetchByPrimaryKey(
		java.lang.String imageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(imageId);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ImagePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ImageUtil util = (ImageUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ImagePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ImageUtil.class);
	private ImagePersistence _persistence;
}