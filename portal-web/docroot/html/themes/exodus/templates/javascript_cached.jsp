<%
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
%>

<%@ include file="init.jsp" %>

LayoutColumns.highlight = "<%= colorScheme.getBodyBg() %>";

function resizeWallpaper() {
	var bg = _$J.getOne("background-wallpaper");
	var box = _$J.getOne("background-wallpaper-container");

	if (bg && box) {
	    if (!is_safari) {
	    	var scrollHeight = document.body.scrollHeight;
	    	var clientHeight = document.body.clientHeight;

	        box.style.height = (scrollHeight > clientHeight ? scrollHeight : clientHeight) + "px";
	        box.style.width = "100%";
	    }
	    else {
	        box.style.height = document.body.offsetHeight + "px";
	        box.style.width = document.body.offsetWidth + "px";
	    }

		var pageRatio = box.offsetWidth / box.offsetHeight;
		var ratio = 1;

		if (pageRatio > ratio) {
			bg.style.height = "";
			bg.style.width = box.offsetWidth + "px";
		}
		else {
			bg.style.height = box.offsetHeight + "px";
			bg.style.width = "";
		}
	}
}

Event.observe(window, "load", function(){
	resizeWallpaper();
});

Event.observe(window, "resize", function(){
	resizeWallpaper();
});