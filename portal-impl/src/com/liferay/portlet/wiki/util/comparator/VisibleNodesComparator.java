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

package com.liferay.portlet.wiki.util.comparator;

import com.liferay.portlet.wiki.model.WikiNode;

import java.util.Arrays;
import java.util.Comparator;

public class VisibleNodesComparator implements Comparator<WikiNode> {

	public VisibleNodesComparator(String[] visibleNodes) {
		_visibleNodes = visibleNodes;
	}

	public int compare(WikiNode node1, WikiNode node2) {
		int pos1 = Arrays.binarySearch(_visibleNodes, node1.getName());
		int pos2 = Arrays.binarySearch(_visibleNodes, node2.getName());

		if (pos1 < 0) {
			return 1;
		}

		if (pos2 < 0) {
			return -1;
		}

		return pos1 - pos2;
	}

	private String[] _visibleNodes;

}