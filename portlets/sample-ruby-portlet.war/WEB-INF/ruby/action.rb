##
## Ruby Script invoked from the portlet
##
## Implicit global variables:
##  $portletConfig, $portletContext, $actionRequest, $actionResponse

prefs = $preferences
numStr = prefs.getValue("number", "0")
num = numStr.to_i
num += 1
prefs.setValue("number", num.to_s)
prefs.store