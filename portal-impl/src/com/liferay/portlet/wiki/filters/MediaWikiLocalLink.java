package com.liferay.portlet.wiki.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtree.factory.AbstractStringFetcher;
import org.stringtree.factory.Container;
import org.stringtree.factory.Fetcher;
import org.stringtree.juicer.Initialisable;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.Pattern;

import com.efsol.friki.NamePreserver;

public class MediaWikiLocalLink 
	extends AbstractStringFetcher
	implements Initialisable
{
	private Container pages;

	public MediaWikiLocalLink(Container pages)
	{
		this.pages = pages;
	}

	public MediaWikiLocalLink()
	{
		this(null);
	}
	
	public Object getObject(String key)
	{
		String ret = key;
		
		Matcher match = pattern.matcher(key);
		
		if (match.find()) {
			key = match.group(3);
			
			String title = match.group(6);
			
			if (pages.contains(_encodeKey(key.trim()))){
				ret = "\nview{" + title.trim() + "," + _encodeKey(key.trim()) + "}\n";
			}
			else{
				ret = "\nedit{" + title.trim() + "," + _encodeKey(key.trim()) + "}\n";
			}
		}
		
		return ret;
	}

	public void init(Fetcher context)
	{
		pages = (Container)context.getObject("wiki.pages");
	}
	
	private String _encodeKey(String key) {
		return key.replaceAll("\\W", "_");
	}
	
	private static Pattern pattern = Pattern.compile("(^|\\p{Punct}|\\p{Space})(\\[\\s*(((\\p{Lu}\\p{Ll}+)\\s?)+)\\s*\\|\\s*((([\\p{Alpha}\\p{Digit}]+)\\s?)+)\\s*\\])(\\z|\\n|\\p{Punct}|\\p{Space})");
	
	private static Log _log = LogFactory.getLog(MediaWikiLocalLink.class);

}
