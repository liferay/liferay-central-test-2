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
	{"datetime",  "Date & Time",          "320", "136", "http://www.google.com/ig/modules/datetime.xml", ""},
	{"cal",       "Calendar",             "320", "200", "http://mzp.breeze.jp/google/calendar.xml", ""},
	{"ggloss",    "Google Glossary",      "320", "120", "http://base.google.com/base/a/CalebEgg/1016230/1037237637861129854", ""},
	{"upstrack",  "UPS Package Tracking", "280",  "60", "http://www.geocities.com/grimmthething//upsTrack.xml", ""},
	{"imdb",      "Movie Search",         "320",  "60", "http://www.randomstorage.com/googleig/imdb/searchtheimdb_module.xml", ""},
	{"timer",     "Timer",                "320",  "40", "http://cydelic.info/gmodules/timer/timer.xml", ""},
	{"gvideo",    "Google Video",         "320", "280", "http://www.google.com/ig/modules/googlevideo.xml", ""},
	{"pacman",    "PacMan v2.0",          "300", "380", "http://www.schulz.dk/pacman.xml", ""},
	{"gfight",    "Google Fight",         "320", "240", "http://basisforum.org/fight.xml", ""},
	{"pingpong",  "Ping pong",            "150", "240", "http://blog-apart.com/PINGxPONG/gg/gg.xml", ""},
	{"crossword", "Crossword",            "400", "710", "http://www.puzzlerscave.com/crossword.xml", "up_size=normal"},
};

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