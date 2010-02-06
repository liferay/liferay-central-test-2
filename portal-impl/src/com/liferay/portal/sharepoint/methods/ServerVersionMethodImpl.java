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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.sharepoint.Leaf;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ServerVersionMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class ServerVersionMethodImpl extends BaseMethodImpl {

	public ServerVersionMethodImpl() {
		Tree serverVersionTree = new Tree();

		serverVersionTree.addChild(new Leaf("major ver", "6", true));
		serverVersionTree.addChild(new Leaf("minor ver", "0", true));
		serverVersionTree.addChild(new Leaf("phase ver", "2", true));
		serverVersionTree.addChild(new Leaf("ver incr", "8117", true));

		Property serverVersionProperty = new Property(
			getMethodName(), serverVersionTree);

		_elements.add(serverVersionProperty);
		_elements.add(new Property("source control", "1"));
	}

	public String getMethodName() {
		return _METHOD_NAME;
	}

	protected List<ResponseElement> getElements(
		SharepointRequest sharepointRequest) {

		return _elements;
	}

	private static final String _METHOD_NAME = "server version";

	private List<ResponseElement> _elements = new ArrayList<ResponseElement>();

}