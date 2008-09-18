/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="SearchContainerTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerTag extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = getServletRequest();

			PortletRequest portletRequest = (PortletRequest)
				request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			PortletResponse portletResponse = (PortletResponse)
				request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

			if (_iteratorURL == null) {
				_iteratorURL =
					((RenderResponse)portletResponse).createRenderURL();
			}

			if (_searchContainer == null) {
				_searchContainer = new SearchContainer(
					portletRequest, _displayTerms, _searchTerms, getCurParam(),
					getDelta(), _iteratorURL, _headerNames,
					_emptyResultsMessage);
			}

			if (_orderableHeaders != null) {
				_searchContainer.setOrderableHeaders(_orderableHeaders);
			}

			if (Validator.isNotNull(_orderByCol)) {
				_searchContainer.setOrderByCol(_orderByCol);
			}

			if (Validator.isNotNull(_orderByType)) {
				_searchContainer.setOrderByType(_orderByType);
			}

			_searchContainer.setHover(_hover);

			if (_orderByComparator != null) {
				_searchContainer.setOrderByComparator(_orderByComparator);
			}

			if (_rowChecker != null) {
				_searchContainer.setRowChecker(_rowChecker);
			}

			pageContext.setAttribute(getVar(), _searchContainer);

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		_curParam = null;
		_delta = 0;
		_displayTerms = null;
		_emptyResultsMessage = null;
		_hasResults = false;
		_headerNames = null;
		_hover = false;
		_iteratorURL = null;
		_orderableHeaders = null;
		_orderByCol = null;
		_orderByComparator = null;
		_orderByType = null;
		_rowChecker = null;
		_searchContainer = null;
		_searchTerms = null;
		_var = null;

		return EVAL_PAGE;
	}

	public String getCurParam() {
		if (Validator.isNull(_curParam)) {
			return SearchContainer.DEFAULT_CUR_PARAM;
		}
		else {
			return _curParam;
		}
	}

	public void setCurParam(String curParam) {
		_curParam = curParam;
	}

	public int getDelta() {
		if (_delta <= 0) {
			return SearchContainer.DEFAULT_DELTA;
		}
		else {
			return _delta;
		}
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public DisplayTerms getDisplayTerms() {
		return _displayTerms;
	}

	public void setDisplayTerms(DisplayTerms displayTerms) {
		_displayTerms = displayTerms;
	}

	public String getEmptyResultsMessage() {
		return _emptyResultsMessage;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public boolean getHasResults() {
		return _hasResults;
	}

	public void setHasResults(boolean hasResults) {
		_hasResults = hasResults;
	}

	public List<String> getHeaderNames() {
		return _headerNames;
	}

	public void setHeaderNames(List<String> headerNames) {
		_headerNames = headerNames;
	}

	public boolean isHover() {
		return _hover;
	}

	public void setHover(boolean hover) {
		_hover = hover;
	}

	public PortletURL getIteratorURL() {
		return _iteratorURL;
	}

	public void setIteratorURL(PortletURL iteratorURL) {
		_iteratorURL = iteratorURL;
	}

	public Map<String, String> getOrderableHeaders() {
		return _orderableHeaders;
	}

	public void setOrderableHeaders(Map<String, String> orderableHeaders) {
		_orderableHeaders = orderableHeaders;
	}

	public String getOrderByCol() {
		return _orderByCol;
	}

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	public OrderByComparator getOrderByComparator() {
		return _orderByComparator;
	}

	public void setOrderByComparator(OrderByComparator orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public RowChecker getRowChecker() {
		return _rowChecker;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public SearchContainer<?> getSearchContainer() {
		return _searchContainer;
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public DisplayTerms getSearchTerms() {
		return _searchTerms;
	}

	public void setSearchTerms(DisplayTerms searchTerms) {
		_searchTerms = searchTerms;
	}

	public String getVar() {
		if (Validator.isNull(_var)) {
			return SEARCH_CONTAINER_VAR;
		}
		else {
			return _var;
		}
	}

	public void setVar(String var) {
		_var = var;
	}

	public static final String SEARCH_CONTAINER_VAR = "searchContainer";

	private String _curParam;
	private int _delta = 0;
	private DisplayTerms _displayTerms;
	private String _emptyResultsMessage;
	private boolean _hasResults;
	private List<String> _headerNames;
	private boolean _hover = false;
	private PortletURL _iteratorURL;
	private OrderByComparator _orderByComparator;
	private Map<String, String> _orderableHeaders;
	private String _orderByCol;
	private String _orderByType;
	private RowChecker _rowChecker;
	private SearchContainer<?> _searchContainer;
	private DisplayTerms _searchTerms;
	private String _var;

}
