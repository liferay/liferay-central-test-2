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

<%-- This file can be ovewritten in the ext environment to change the available --%>
<%-- gadgets and border decorations                                             --%>

<%
/**
 * Parameters:
 * 0) Id of the gadget (will be written to the db when selected)
 * 1) Name given to the gadget
 * 2) Recommended width
 * 3) Recommended height
 * 4) URL of the Gadget XML
 * 5) Gadget specific parameters
 */
String[][] gadgets = {
	{"geoiptool", "Geo IP Tool", "290", "290", "http://www.geoiptool.com/GeoIPToolModule.xml", ""},
	{"googlevideo", "Google Video", "320", "280", "http://www.google.com/ig/modules/googlevideo.xml", ""},
	{"upspackagetracking", "UPS Package Tracking", "280", "60", "http://www.geocities.com/grimmthething//upsTrack.xml", ""}
};

// Some other gadget examples:
//	{"datetime",  "Date & Time",          "320", "136", "http://www.google.com/ig/modules/datetime.xml", ""},
//	{"cal",       "Calendar",             "320", "200", "http://mzp.breeze.jp/google/calendar.xml", ""},
//	{"ggloss",    "Google Glossary",      "320", "120", "http://base.google.com/base/a/CalebEgg/1016230/1037237637861129854", ""},
//	{"imdb",      "Movie Search",         "320",  "60", "http://www.randomstorage.com/googleig/imdb/searchtheimdb_module.xml", ""},
//	{"timer",     "Timer",                "320",  "40", "http://cydelic.info/gmodules/timer/timer.xml", ""},
//	{"pacman",    "PacMan v2.0",          "300", "380", "http://www.schulz.dk/pacman.xml", ""},
//	{"gfight",    "Google Fight",         "320", "240", "http://basisforum.org/fight.xml", ""},
//	{"pingpong",  "Ping pong",            "150", "240", "http://blog-apart.com/PINGxPONG/gg/gg.xml", ""},
//	{"crossword", "Crossword",            "400", "710", "http://www.puzzlerscave.com/crossword.xml", "up_size=normal"},

/**
 * Parameters
 * 0) Id of the border (will be written to the db when selected)
 * 1) Name given to the decoration (will be translated if translation is
 *    available for that word or sentence
 * 2) Decoration definition as defined by Google Gadgets API
 */
String[][] borders = {
	{"none",  "none",        ""},
	{"line",  "line",        "#ffffff|3px,1px solid #999999"},
	{"2line", "double line", "#ffffff|1px,1px solid black|1px,1px solid black|0px,1px black"},
	{"3d",    "3d",          "http://gmodules.com/ig/images/"}
};
%>