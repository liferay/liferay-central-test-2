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

package com.liferay.portal.events;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;
import com.liferay.util.Validator;

import java.util.Iterator;

/**
 * <a href="FixImageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FixImageAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {
			_fixImage();
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private void _fixImage() throws PortalException, SystemException {

		// Make sure every image has a type

		Iterator itr = ImageLocalServiceUtil.getImages(0, 1).iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			if (Validator.isNotNull(image.getType())) {
				return;
			}
		}

		itr = ImageLocalServiceUtil.getImages().iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			ImageLocalServiceUtil.updateImage(
				image.getImageId(), image.getTextObj());
		}
	}

}