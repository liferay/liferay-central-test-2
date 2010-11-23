from net.grinder.script.Grinder import grinder
from net.grinder.script import Test
from net.grinder.plugin.http import HTTPRequest, HTTPPluginControl
from HTTPClient import NVPair
from java.lang import System

test1 = Test(1, "Request resource")
request1 = test1.wrap(HTTPRequest())
authenticationTest = Test(2, "POST to j_security_check")

class TestRunner:
	def __call__(self):
		result = request1.GET("http://localhost:8080/c/portal/login?cmd=already-registered&login=test@liferay.com&password=test")
		result = request1.GET("http://localhost:8080/c/portal/login?cmd=already-registered&login=test@liferay.com&password=test")
		result = request1.GET("http://localhost:8080/c/portal/login?cmd=already-registered&login=test@liferay.com&password=test")

		writeToFile(result.text);

		authenticationFormData = ( NVPair("j_username", "test@liferay.com"), NVPair("j_password", "test"),)

		request = authenticationTest.wrap(HTTPRequest(url="%s/j_security_check" % result.originalURI))
		request.POST(authenticationFormData)

		writeToFile(result.text);

def writeToFile(text):
	varTime = System.currentTimeMillis()
	filename = grinder.getFilenameFactory().createFilename("page", "-%d.html" % varTime)
	file = open(filename, "w")
	print >> file, text
	file.close()