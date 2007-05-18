##
## Utility methods for portlets
##
## Implicit global variables:
##  $portletConfig, $portletContext

module LiferayPortlet
	def LiferayPortlet.showPortletDetails(req)
	   """
	   <hr>
	   Information about this portlet:
	   <ul>
	   <li>Portlet Name: #{$portletConfig.portletName} </li>
	   <li>Parameters: #{req.getParameterMap} </li>
	   <li>Preferences: #{req.getPreferences.getMap} </li>
	   </ul>
	   """
	end

	def LiferayPortlet.showUserDetails(userInfo)
	   """
	   <hr>
	   Information about the current user:
	   <ul>
	   <li>First Name: #{userInfo['user.name.given']} </li>
	   <li>Last Name: #{userInfo['user.name.family']} </li>
	   <li>Email address: #{userInfo['user.home-info.online.email']} </li>
	   <li>Liferay UserId: #{userInfo['liferay.user.id']} </li>
	   </ul>
	   """
	end

	def LiferayPortlet.renderLink(text, target=nil, params=nil)
	   link renderUrl(target, params), text
	end

	def LiferayPortlet.renderUrl(target=nil, params=nil)
	   portletUrl = $renderResponse.createRenderURL
	   if target
		  portletUrl.setParameter "rubyFile", target
	   end

	   url portletUrl, params
	end

	def LiferayPortlet.actionLink(text, params=nil)
	   link actionUrl(params), text
	end

	def LiferayPortlet.actionUrl(params=nil)
	   portletUrl = $renderResponse.createActionURL
	   url portletUrl, params
	end

	def LiferayPortlet.url(portletUrl, params=nil)
	   if params
		  params.each{ |key, value|
			 portletUrl.setParameter(key, value)
		  }
	   end

	   portletUrl.toString
	end

	def LiferayPortlet.link(url, text)
	   "<a href='" + url + "'>" + text + "</a>"
	end
end