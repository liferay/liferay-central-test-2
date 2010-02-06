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
 * <a href="OpenServiceMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class OpenServiceMethodImpl extends BaseMethodImpl {

	public OpenServiceMethodImpl() {
		Tree metaInfoTree = new Tree();

		metaInfoTree.addChild(new Leaf("vti_casesensitiveurls", "IX|0", false));
		metaInfoTree.addChild(new Leaf("vti_longfilenames", "IX|1", false));
		metaInfoTree.addChild(
			new Leaf("vti_welcomenames", "VX|index.html", false));
		metaInfoTree.addChild(new Leaf("vti_username", "SX|joebloggs", false));
		metaInfoTree.addChild(new Leaf("vti_servertz", "SX|-0700", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolsystem", "SR|lw", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolversion", "SR|V1", false));
		metaInfoTree.addChild(
			new Leaf("vti_doclibwebviewenabled", "IX|0", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolcookie", "SX|fp_internal", false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_sourcecontrolproject", "SX|&#60;STS-based Locking&#62;",
				false));

		Tree serviceTree = new Tree();

		serviceTree.addChild(new Leaf("service_name", "/sharepoint", true));
		serviceTree.addChild(new Leaf("meta_info", metaInfoTree));

		Property serviceProperty = new Property("service", serviceTree);

		_elements.add(serviceProperty);
	}

	public String getMethodName() {
		return _METHOD_NAME;
	}

	protected List<ResponseElement> getElements(
		SharepointRequest sharepointRequest) {

		return _elements;
	}

	private static final String _METHOD_NAME = "open service";

	private List<ResponseElement> _elements = new ArrayList<ResponseElement>();

}