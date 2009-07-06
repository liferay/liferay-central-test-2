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

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TokenInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TokenInfo implements Serializable {

	public TokenInfo(
			long tokenId, String tokenName, String nodeName, Date startDate,
			Date endDate) {
		this(tokenId, tokenName, nodeName, null, null, startDate, endDate);
	}

	public TokenInfo(long tokenId, String tokenName, String nodeName,
			TokenInfo parent, Date startDate, Date endDate) {
		this(tokenId, tokenName, nodeName, parent, null, startDate, endDate);
	}

	public TokenInfo(long tokenId, String tokenName, String nodeName,
			TokenInfo parent, List<TokenInfo> children, Date startDate,
			Date endDate) {
		_tokenId = tokenId;
		_tokenName = tokenName;
		_nodeName = nodeName;
		_parent = parent;
		_children = children;
		_startDate = startDate;
		_endDate = endDate;
	}

	public boolean addChild(TokenInfo childTokenInfo) {
		return _children.add(childTokenInfo);
	}

	public boolean containsChild(TokenInfo childTokenInfo) {
		return _children.contains(childTokenInfo);
	}

	public List<TokenInfo> getChildren() {
		return _children;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public String getTokenName() {
		return _tokenName;
	}

	public String getNodeName() {
		return _nodeName;
	}

	public TokenInfo getParent() {
		return _parent;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getTokenId() {
		return _tokenId;
	}

	public boolean removeChild(TokenInfo childTokenInfo) {
		return _children.remove(childTokenInfo);
	}

	public void setChildren(List<TokenInfo> children) {
		_children = children;
	}

	public void setParent(TokenInfo parent) {
		_parent = parent;
	}

	@Override
	public String toString() {
		return "TokenInfo[" +
			"id:" + _tokenId +
			", name:" + _tokenName +
			", nodeName:" + _nodeName +
			", children:" + _children +
			", start:" + _startDate +
			", end:" + _endDate + "]";
	}

	private List<TokenInfo> _children = new ArrayList<TokenInfo>();
	private long _tokenId = -1;
	private Date _endDate;
	private String _tokenName;
	private String _nodeName;
	private TokenInfo _parent;
	private Date _startDate;

}