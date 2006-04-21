
The applications directory provides a quick way to deploy applications
in a development server.  When the WebLogic Server instance is running
in development mode, it automatically deploys any applications or
modules that you place in this directory.

The file (or files) you place in this directory can be:

* A J2EE application
* EAR file A WAR, EJB JAR, RAR, or CAR archived module
* An exploded archive directory for either an application 
  or a module

To automatically deploy an application:

* Start the WebLogic Server domain in development mode.
* Place the exploded directory structure or archive file 
  in this directory. 

When you use the auto deployment feature, the server automatically
adds an entry for your application or module to the domain's
config.xml file.  The config.xml entry ensures that the application is
automatically deployed at server startup, even if you later change
from development mode to production mode.

The applications directory is automatically created when you install
the WebLogic Server sample domains, or when you use the Configuration
Wizard to create your own domains. WebLogic Server domains start up in
development mode by default. You can set startup mode for a domain by
using the instructions below.


STARTING THE SERVER IN DEVELOPMENT MODE

The startup mode applies to all WebLogic Server instances in a domain.
You can use any of the following methods to set the domain startup
mode:

1) Edit the installed start script for the Administration Server (or
   standalone server).  The start script has a file suffix of either
   .cmd (for Windows) or .sh (for Unix), and is placed in the domain
   directory you specify.

   Use a text editor to edit the following line in the start script:

   set STARTMODE=

   To use production mode, set the value to TRUE. To use development 
   mode and auto deployment, set the value to FALSE or leave it blank.

2) Start the server from the command line with the
   -ProductionModeEnabled flag set to false to use development mode.
   For example:

   % java -ms200m -mx200m -classpath $CLASSPATH
	   -Dweblogic.Name=myserver
 	   -Dweblogic.ProductionModeEnabled=false
	   -Dweblogic.management.username=myUserName
	   -Dweblogic.management.password=myPassword
	   weblogic.Server

3) Set the domain startup mode using the Administration Console.  The
   Configuration->General page for the domain provides a selection box
   to to enable or disable production mode.  See the Administration
   Console online help for more information about setting this
   attribute.

For more details about starting and stopping WebLogic Server 
instances, see the Administration Console Online Help at
http://e-docs.bea.com/wls/docs81/ConsoleHelp/startstop.html.

