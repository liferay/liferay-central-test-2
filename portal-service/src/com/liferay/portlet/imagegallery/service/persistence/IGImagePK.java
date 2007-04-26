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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="IGImagePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImagePK implements Comparable, Serializable {
	public String companyId;
	public String imageId;

	public IGImagePK() {
	}

	public IGImagePK(String companyId, String imageId) {
		this.companyId = companyId;
		this.imageId = imageId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		IGImagePK pk = (IGImagePK)obj;
		int value = 0;
		value = companyId.compareTo(pk.companyId);

		if (value != 0) {
			return value;
		}

		value = imageId.compareTo(pk.imageId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		IGImagePK pk = null;

		try {
			pk = (IGImagePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((companyId.equals(pk.companyId)) && (imageId.equals(pk.imageId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(companyId) + String.valueOf(imageId)).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();
		sm.append(StringPool.OPEN_CURLY_BRACE);
		sm.append("companyId");
		sm.append(StringPool.EQUAL);
		sm.append(companyId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("imageId");
		sm.append(StringPool.EQUAL);
		sm.append(imageId);
		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}