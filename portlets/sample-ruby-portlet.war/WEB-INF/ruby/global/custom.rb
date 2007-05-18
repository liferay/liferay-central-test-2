##
## Place your shared methods here!!
##
## Implicit global variables:
##  $portletConfig, $portletContext

module Custom
	def Custom.navigation
	   """
	   <hr />
	   Navigation:
	   #{LiferayPortlet.renderLink("Home", "/WEB-INF/ruby/view.rb")} -
	   #{LiferayPortlet.renderLink("User info", "/WEB-INF/ruby/info.rb", {'type' => 'user'})} -
	   #{LiferayPortlet.renderLink("Portlet info", "/WEB-INF/ruby/info.rb", {'type' => 'portlet'})} -
	   #{LiferayPortlet.renderLink("Java invocation example", "/WEB-INF/ruby/java.rb")}
	   """
	end

	def Custom.showNumber(req)
       prefs = req.getPreferences();
       num = prefs.getValue("number", "0")
	   """
	   <hr />
	   Number: #{num} (#{LiferayPortlet.actionLink("Increase number")})
	   <br />
	   """
	end

	def Custom.sayHello
	   """
	   <h2>Hello Ruby Portlet users!</h2>
	   <br />
	   """
	end

end