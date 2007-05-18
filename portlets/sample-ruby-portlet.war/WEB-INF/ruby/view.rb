##
## Ruby Script invoked from the portlet
##
## Implicit global variables:
##  $portletConfig, $portletContext, $renderRequest, $renderResponse, $userInfo

$renderResponse.setContentType "text/html"
out = $renderResponse.getPortletOutputStream

out.println Custom.sayHello

out.println """
This is a sample portlet written in Ruby. It shows how to navigate pages and
how to execute actions. The example action increases a number everytime it's
called and saves it as a preference.
"""

out.println Custom.showNumber($renderRequest)
out.println Custom.navigation