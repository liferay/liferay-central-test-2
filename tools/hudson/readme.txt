Instructions for setup of Hudson CI tools for Liferay
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

[1] Create Hudson working directory:
    Copy [SOURCEDIR]/tools/hudson to /var/hudson (C:\hudson)

[2] Set Environment variable: HUDSON_HOME to Hudson working directory in [1]
    On Unix: export HUDSON_HOME=/var/hudson
    On Windows: Set Environment variable for HUDSON_HOME

[3] Downlaod and install ANT, SVN Client, JDK 1.5

[4] Ensure JDK, SVN, ANT are available on system.
    On Unix: which svn java ant
    On Windows: At command prompt attempt to execute ant, svn, java

[5] Download http://hudson.gotdns.com/latest/hudson.war

[6] Download and install Tomcat 6.x or 5.x
    Configure Tomcat to runon ports 9090 etc. Modify tomcat/conf/server.xml
    This will help if you plan to run the automated test suite.
    Tests for the built liferay bits are to be executed on a different Tomcat instance.
    This will make sure the Tomcat running Hudson will not crash if the tests leak resources.

[7] Auto deploy to Tomcat 6 running on JDK 1.6. Copy hudson.war to tomcat/webapps directory.

[8] Click on http://localhost:9090/hudson to view/manage the build Jobs.
    There will be two jobs: Portal ; Plugins

[9] Access http://localhost:8080/hudson/configure
    Setup the SMTP Mailserver settings
    Other properties on this page might need changes depending on your system.
