##
## Example of accessing classes from the Java SDK
## It also shows a simple example of invoking Liferay's services
##
require 'java'
include_class 'java.util.TreeSet'
include_class 'com.liferay.portal.service.OrganizationServiceUtil'

$renderResponse.setContentType "text/html"
out = $renderResponse.getPortletOutputStream

out.println "<h3>Using the JDK</h3>"
out.println "The following list has been generated with java.util.TreeSet:<br>"
set = TreeSet.new
set.add "foo"
set.add "Bar"
set.add "baz"
set.each do |v|
  out.println "value: #{v}"
end

out.println "<br>"

out.println "<h3>Invoking Liferay's services</h3>"
userId = $userInfo['liferay.user.id'].to_i

organizations = OrganizationServiceUtil.getUserOrganizations(userId)

out.println "The user belongs to #{organizations.size} organizations"

out.println Custom.navigation
