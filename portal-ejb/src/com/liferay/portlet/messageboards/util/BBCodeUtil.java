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

package com.liferay.portlet.messageboards.util;

import com.liferay.util.GetterUtil;
import com.liferay.util.Html;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="BBCodeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class BBCodeUtil {

	static Map fontSizes = new HashMap();

	static Map listStyles = new HashMap();

	static {
		fontSizes.put(new Integer(1), "<span style='font-size: 0.7em';>");
		fontSizes.put(new Integer(2), "<span style='font-size: 0.8em';>");
		fontSizes.put(new Integer(3), "<span style='font-size: 0.9em';>");
		fontSizes.put(new Integer(4), "<span style='font-size: 1.0em';>");
		fontSizes.put(new Integer(5), "<span style='font-size: 1.1em';>");
		fontSizes.put(new Integer(6), "<span style='font-size: 1.3em';>");
		fontSizes.put(new Integer(7), "<span style='font-size: 1.5em';>");

		listStyles.put("1", "<ol style='list-style-type: decimal';>");
		listStyles.put("i", "<ol style='list-style-type: lower-roman';>");
		listStyles.put("I", "<ol style='list-style-type: upper-roman';>");
		listStyles.put("a", "<ol style='list-style-type: lower-alpha';>");
		listStyles.put("A", "<ol style='list-style-type: upper-alpha';>");
	}

	public static String getHTML(String bbcode) {
		String html = StringUtil.replace(bbcode, _BBCODE_TAGS, _HTML_TAGS);

		for (int i = 0; i < _EMOTICONS.length; i++) {
			String imgTag = "<img src='" + _EMOTICONS[i][0] + "' />";

			html = StringUtil.replace(html, _EMOTICONS[i][1], imgTag);
		}

		BBCodeTag tag = null;

		StringBuffer sb = null;

		while ((tag = getFirstTag(html, "code")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String code = tag.getElement().replaceAll("\t", "    ");
			String[] lines = Html.escape(code, false).split("\\n");
			int digits = Integer.toString(lines.length + 1).length();

			sb = new StringBuffer(preTag);

			sb.append("<div class='message-board-code'>");

			for (int i = 0; i < lines.length; i++) {
				String index = Integer.toString(i + 1);
				int ld = index.length();

				sb.append("<span class='message-board-code-lines'>");

				for (int j = 0; j < digits - ld; j++) {
					sb.append("&nbsp;");
				}

				lines[i] = StringUtil.replace(lines[i], "   ",
					StringPool.NBSP + StringPool.SPACE + StringPool.NBSP);
				lines[i] = StringUtil.replace(lines[i], "  ",
					StringPool.NBSP + StringPool.SPACE);

				sb.append(index + "</span>");
				sb.append(lines[i]);

				if (index.length() < lines.length) {
					sb.append("<br />");
				}
			}

			sb.append("</div>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "color")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			sb = new StringBuffer(preTag);

			if (tag.hasParameter()) {
				sb.append("<span style='color: ");
				sb.append(tag.getParameter() + ";'>");
				sb.append(tag.getElement() + "</span>");
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "email")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String mailto = GetterUtil.getString(
				tag.getParameter(), tag.getElement().trim());

			sb = new StringBuffer(preTag);

			sb.append("<a href='mailto: " + mailto + "'>");
			sb.append(tag.getElement() + "</a>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "font")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			sb = new StringBuffer(preTag);

			if (tag.hasParameter()) {
				sb.append("<span style='font-family: ");
				sb.append(tag.getParameter() + "';>");
				sb.append(tag.getElement() + "</span>");
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "img")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			sb = new StringBuffer(preTag);

			sb.append("<img src='" + tag.getElement().trim() + "' />");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "list")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String[] items = StringUtil.split(tag.getElement(), "[*]");

			sb = new StringBuffer(preTag);

			if (tag.hasParameter() &&
				listStyles.containsKey(tag.getParameter())) {

				sb.append(listStyles.get(tag.getParameter()));

				for (int i = 0; i < items.length; i++) {
					if (items[i].trim().length() > 0) {
						sb.append("<li>" + items[i].trim() + "</li>");
					}
				}

				sb.append("</ol>");
			}
			else {
				sb.append("<ul style='list-style-type: disc';>");

				for (int i = 0; i < items.length; i++) {
					if (items[i].trim().length() > 0) {
						sb.append("<li>" + items[i].trim() + "</li>");
					}
				}

				sb.append("</ul>");
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "quote")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			sb = new StringBuffer(preTag);

			if (tag.hasParameter()) {
				sb.append("<div class='message-board-quote-title'>");
				sb.append(tag.getParameter() + ":</div>");
			}

			sb.append("<div class='message-board-quote'>");
			sb.append("<div class='message-board-quote-content'>");
			sb.append(tag.getElement());
			sb.append("</div></div>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "size")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			sb = new StringBuffer(preTag);

			if (tag.hasParameter()) {
				Integer size = new Integer(
					GetterUtil.getInteger(tag.getParameter()));

				if (size.intValue() > 7) {
					size = new Integer(7);
				}

				if (fontSizes.containsKey(size)) {
					sb.append(fontSizes.get(size));
					sb.append(tag.getElement() + "</span>");
				}
				else {
					sb.append(tag.getElement());
				}
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "url")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String url = GetterUtil.getString(
				tag.getParameter(), tag.getElement().trim());

			sb = new StringBuffer(preTag);

			sb.append("<a href='" + url + "'>");
			sb.append(tag.getElement() + "</a>");
			sb.append(postTag);

			html = sb.toString();
		}

		html = StringUtil.replace(html, "\n", "<br />");

		return html;
	}

	public static BBCodeTag getFirstTag(String bbcode, String name) {
		BBCodeTag tag = new BBCodeTag();

		String begTag = "[" + name;
		String endTag = "[/" + name + "]";

		String preTag = StringUtil.extractFirst(bbcode, begTag);

		if (preTag == null) {
			return null;
		}

		if (preTag.length() != bbcode.length()) {
			tag.setStartPos(preTag.length());

			String remainder = bbcode.substring(
				preTag.length() + begTag.length());

			int cb = remainder.indexOf("]");
			int end = remainder.indexOf(endTag);

			if (cb > 0 && remainder.startsWith("=")) {
				tag.setParameter(remainder.substring(1, cb));
				tag.setElement(remainder.substring(cb + 1, end));
			}
			else if (cb == 0) {
				tag.setElement(remainder.substring(1, end));
			}
		}

		if (tag.hasElement()) {
			int length =
				begTag.length() + 1 + tag.getElement().length() +
					endTag.length();

			if (tag.hasParameter()) {
				length += 1 + tag.getParameter().length();
			}

			tag.setEndPos(tag.getStartPos() + length);

			return tag;
		}

		return null;
	}

	private static final String[] _BBCODE_TAGS = {
		"[b]", "[/b]", "[i]", "[/i]", "[u]", "[/u]", "[s]", "[/s]",
		"[img]", "[/img]",
		"[left]", "[center]", "[right]", "[indent]",
		"[/left]", "[/center]", "[/right]", "[/indent]"
	};

	private static final String[][] _EMOTICONS = {
		{"@theme_images_path@/emoticons/angry.gif", ">["},
		{"@theme_images_path@/emoticons/bashful.gif", ":bashful:"},
		{"@theme_images_path@/emoticons/big_grin.gif", ":grin:"},
		{"@theme_images_path@/emoticons/blink.gif", ":blink:"},
		{"@theme_images_path@/emoticons/blush.gif", ":*)"},
		{"@theme_images_path@/emoticons/bored.gif", ":bored:"},
		{"@theme_images_path@/emoticons/closed_eyes.gif", "-_-"},
		{"@theme_images_path@/emoticons/cold.gif", ":cold:"},
		{"@theme_images_path@/emoticons/cool.gif", "B)"},
		{"@theme_images_path@/emoticons/darth_vader.gif", ":vader:"},
		{"@theme_images_path@/emoticons/dry.gif", "<_<"},
		{"@theme_images_path@/emoticons/exclamation.gif", ":what:"},
		{"@theme_images_path@/emoticons/girl.gif", ":girl:"},
		{"@theme_images_path@/emoticons/glare.gif", ">_>"},
		{"@theme_images_path@/emoticons/happy.gif", ":)"},
		{"@theme_images_path@/emoticons/huh.gif", ":huh:"},
		{"@theme_images_path@/emoticons/in_love.gif", "<3"},
		{"@theme_images_path@/emoticons/karate_kid.gif", ":kid:"},
		{"@theme_images_path@/emoticons/kiss.gif", ":#"},
		{"@theme_images_path@/emoticons/laugh.gif", ":lol:"},
		{"@theme_images_path@/emoticons/mad.gif", ">("},
		{"@theme_images_path@/emoticons/mellow.gif", ":mellow:"},
		{"@theme_images_path@/emoticons/ninja.gif", ":ph34r:"},
		{"@theme_images_path@/emoticons/oh_my.gif", ":O"},
		{"@theme_images_path@/emoticons/pac_man.gif", ":V"},
		{"@theme_images_path@/emoticons/roll_eyes.gif", ":rolleyes:"},
		{"@theme_images_path@/emoticons/sad.gif", ":("},
		{"@theme_images_path@/emoticons/sleep.gif", ":sleep:"},
		{"@theme_images_path@/emoticons/smile.gif", ":D"},
		{"@theme_images_path@/emoticons/smug.gif", ":smug:"},
		{"@theme_images_path@/emoticons/suspicious.gif", "8o"},
		{"@theme_images_path@/emoticons/tongue.gif", ":P"},
		{"@theme_images_path@/emoticons/unsure.gif", ":unsure:"},
		{"@theme_images_path@/emoticons/wacko.gif", ":wacko:"},
		{"@theme_images_path@/emoticons/wink.gif", ":wink:"},
		{"@theme_images_path@/emoticons/wub.gif", ":wub:"}
	};

	private static final String[] _HTML_TAGS = {
		"<b>", "</b>", "<i>", "</i>", "<u>", "</u>", "<strike>", "</strike>",
		"<img src='", "' />",
		"<span style='text-align: left'>", "<span style='text-align: center'>",
		"<span style='text-align: right'>", "<span style='text-indent: 10px'>",
		"</span>", "</span>", "</span>", "</span>"
	};

}