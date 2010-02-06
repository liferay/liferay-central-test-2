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
 * <a href="TreeNode.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class TreeNode<T extends Comparable<T>> {

	public TreeNode(T value) {
		this(value, null);
	}

	public TreeNode(T value, TreeNode<T> parentNode) {
		this(value, parentNode, new ArrayList<TreeNode<T>>());
	}

	public TreeNode(
		T value, TreeNode<T> parentNode, List<TreeNode<T>> childNodes) {

		_value = value;
		_parentNode = parentNode;
		_childNodes = childNodes;
	}

	public TreeNode<T> addChildNode(T value) {
		TreeNode<T> childNode = new TreeNode<T>(value, this);

		_childNodes.add(childNode);

		return childNode;
	}

	public List<TreeNode<T>> getChildNodes() {
		return _childNodes;
	}

	public List<T> getChildValues() {
		List<T> values = new ArrayList<T>(_childNodes.size());

		for (TreeNode<T> childNode : _childNodes) {
			values.add(childNode.getValue());
		}

		return values;
	}

	public TreeNode<T> getParentNode() {
		return _parentNode;
	}

	public T getValue() {
		return _value;
	}

	public boolean isRootNode() {
		if (_parentNode == null) {
			return true;
		}
		else {
			return false;
		}
	}

	private final List<TreeNode<T>> _childNodes;
	private final TreeNode<T> _parentNode;
	private final T _value;

}