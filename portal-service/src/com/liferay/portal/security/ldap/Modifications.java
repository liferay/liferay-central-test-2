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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * <a href="Modifications.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public class Modifications {

	public static Modifications getInstance() {
		return new Modifications();
	}

	public ModificationItem addItem(
		int modificationOp, String id, String value) {

		BasicAttribute basicAttribute = new BasicAttribute(id);

		if (Validator.isNotNull(value)) {
			basicAttribute.add(value);
		}

		ModificationItem item = new ModificationItem(
			modificationOp, basicAttribute);

		_items.add(item);

		return item;
	}

	public ModificationItem addItem(String id, String value) {
		return addItem(DirContext.REPLACE_ATTRIBUTE, id, value);
	}

	public ModificationItem[] getItems() {
		return _items.toArray(new ModificationItem[_items.size()]);
	}

	private Modifications() {
	}

	private List<ModificationItem> _items = new ArrayList<ModificationItem>();

}