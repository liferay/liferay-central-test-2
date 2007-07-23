package com.liferay.portlet.wiki.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtree.factory.AbstractStringFetcher;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.Pattern;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.util.StringUtil;

public class CodeBlock extends AbstractStringFetcher {
	
	public Object getObject(String content) {
		
		StringMaker sm = new StringMaker();
		
		Matcher matcher = pattern.matcher(content);
		
		boolean found = matcher.find();
		int groupCount = matcher.groupCount();
		
		if (found && groupCount >= 4) {
			sm.append("<div class=\"wiki-code\">");
			
			String[] lines = matcher.group(2).split("\n");
			
			for (int i = 0; i < lines.length; i++) {
				if (i != 0) {
					sm.append("<br/>");
				}

				String translation = StringUtil.replace(
						lines[i],
						new String[] {
							"\\s",
							"<",
							">",
							"=",
							"\"",
							"'",
							"\t"
						},
						new String[] {
							"&nbsp;",
							"&lt;",
							"&gt;",
							"&#x003D;",
							"&#0034;",
							"&#0039;",
							"&nbsp;&#8594;&nbsp;"
						});
				
				int padlength = String.valueOf(lines.length).length() - 
					String.valueOf(i + 1).length();
				String padding = "";
				
				for (int j = 0; j < padlength; j++) {
					padding += "&#0149;";
				}
					
				sm.append("<span class=\"wiki-code-lines\">");
				sm.append(padding + (i + 1));
				sm.append("</span>");
				
				sm.append(translation);
			}
			
			sm.append("</div>");
			
			content = sm.toString();
		}
		
		return content;
	}
	
	private static Pattern pattern = Pattern.compile("(\\[code\\])((.|\n)*?)(\\[/code\\])");
	
	private static Log _log = LogFactory.getLog(CodeBlock.class);

}
