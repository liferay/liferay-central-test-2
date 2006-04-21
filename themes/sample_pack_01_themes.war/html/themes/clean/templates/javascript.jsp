<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%
String ua = request.getHeader( "User-Agent" );
boolean isFirefox = ( ua != null && ua.indexOf( "Firefox/" ) != -1 );
boolean isMSIE = ( ua != null && ua.indexOf( "MSIE" ) != -1 );
response.setHeader( "Vary", "User-Agent" );

if( !(isMSIE) ){ %>
	function displayMenu(elem1,elem2,elem3) {
		if(document.getElementById) {
			var i;
			var j;
			var k;

			if (i = document.getElementById(elem1)) {
				i.style.display = (i.style.display == 'block') ? 'none' : 'block';
			}

			if (j = document.getElementById(elem2)) {
				j.style.display = 'none';
			}

			if ( k = document.getElementById(elem3)) {
				k.style.display = 'none';
			}
		}
	}
<% } %>

<% if( isMSIE ){ %>
	function displayMenu(elem1,elem2,elem3) {
		if(document.getElementById) {
			var i;
			var j;
			var k;
			var l = document.getElementById('iframe_hack');

			if (j = document.getElementById(elem2)) {
				j.style.display = 'none';
			}

			if ( k = document.getElementById(elem3)) {
				k.style.display = 'none';

			}

			if (i = document.getElementById(elem1)) {
				i.style.display = (i.style.display == 'block') ? 'none' : 'block';
				if(i.style.display == 'block') {
					l.style.height = document.getElementById("startMenu").offsetHeight;
				}
				else {
					l.style.height = '50px';
				}
			}
		}
	}
<% } %>

function displayStartMenu() {
	if(document.getElementById) {
		var i = document.getElementById('iframe_hack');
		var j = document.getElementById('startMenu');
		i.style.display = (i.style.display == 'block') ? 'none' : 'block';
		j.style.display = (j.style.display == 'block') ? 'none' : 'block';
	}
}