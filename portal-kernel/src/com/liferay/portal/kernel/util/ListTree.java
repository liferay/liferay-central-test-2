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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ListTree.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ListTree<T extends Comparable<T>> {

	public ListTree() {
		this(null);
	}

	public ListTree(T value) {
		_rootNode = new TreeNode<T>(value);
	}

	public List<TreeNode<T>> getChildNodes(TreeNode<T> node) {
		List<TreeNode<T>> nodes = new ArrayList<TreeNode<T>>();

		getChildNodes(node, nodes);

		return nodes;
	}

	public TreeNode<T> getRootNode() {
		return _rootNode;
	}

	protected void getChildNodes(TreeNode<T> node, List<TreeNode<T>> nodes) {
		List<TreeNode<T>> childNodes = node.getChildNodes();

		nodes.addAll(childNodes);

		for (TreeNode<T> childNode : childNodes) {
			getChildNodes(childNode, nodes);
		}
	}

	private final TreeNode<T> _rootNode;

}