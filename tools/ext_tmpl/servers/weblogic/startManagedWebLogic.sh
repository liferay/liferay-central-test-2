#!/bin/sh
# ****************************************************************************
# This script is used to start a managed WebLogic Server for the domain in the 
# current working directory.  This script reads in the SERVER_NAME and 
# ADMIN_URL as positional parameters, sets the SERVER_NAME variable, then 
# starts the server.
#
# Other variables that startWLS takes are:
#
# WLS_USER       - cleartext user for server startup
# WLS_PW         - cleartext password for server startup
# PRODUCTION_MODE      - Set to true for production mode servers, false for 
#                  development mode
# JAVA_OPTIONS   - Java command-line options for running the server. (These
#                  will be tagged on to the end of the JAVA_VM and MEM_ARGS)
# JAVA_VM        - The java arg specifying the VM to run.  (i.e. -server, 
#                  -hotspot, etc.)
# MEM_ARGS       - The variable to override the standard memory arguments
#                  passed to java
#
# For additional information, refer to the WebLogic Server Administration Guide
# (http://e-docs.bea.com/wls/docs81/ConsoleHelp/startstop.html).
# ****************************************************************************


# set up WL_HOME, the root directory of your WebLogic installation
WL_HOME="C:/Program Files/BEA/weblogic81"

# set up common environment
# Set Production Mode.  When this is set to true, the server starts up in
# production mode.  When set to false, the server starts up in development
# mode.  If it is not set, it will default to false.
PRODUCTION_MODE=""

# Set JAVA_VENDOR to java virtual machine you want to run on server side.
JAVA_VENDOR="BEA"

# Set JAVA_HOME to java virtual machine you want to run on server side.
JAVA_HOME="C:/Program Files/BEA/jrockit81sp2_141_05"

. "${WL_HOME}/common/bin/commEnv.sh"

# Set SERVER_NAME to the name of the server you wish to start up. 
ADMIN_URL=http://localhost:7001
SERVER_NAME=

# Set WLS_USER equal to your system username and WLS_PW equal  
# to your system password for no username and password prompt 
# during server startup.  Both are required to bypass the startup
# prompt.
WLS_USER=
WLS_PW=

# Set JAVA_VM to java virtual machine you want to run on server side.
# JAVA_VM="-jrockit"

# Set JAVA_OPTIONS to the java flags you want to pass to the vm.  If there 
# are more than one, include quotes around them.  For instance: 
# JAVA_OPTIONS="-Dweblogic.attribute=value -Djava.attribute=value"

usage()
{
  echo "Need to set SERVER_NAME and ADMIN_URL environment variables or specify"
  echo "them in command line:"
  echo 'Usage: ./startManagedWebLogic.sh [SERVER_NAME] [ADMIN_URL]'
  echo "for example:"
  echo './startManagedWebLogic.sh managedserver1 http://localhost:7001'
  exit 1
}

# Check for variables SERVER_NAME and ADMIN_URL
# SERVER_NAME and ADMIN_URL must by specified before starting a managed server,
# detailed information can be found at http://e-docs.bea.com/wls/docs81/adminguide/startstop.html.
if [ ${#} = 0 ]; then
  if [ "x${SERVER_NAME}" = "x" -o "x${ADMIN_URL}" = "x" ]; then
    usage
  fi
elif [ ${#} = 1 ]; then
  SERVER_NAME=${1}
  if [ "x${ADMIN_URL}" = "x" ]; then
    usage
  fi
elif [ ${#} = 2 ]; then
  SERVER_NAME=${1}
  ADMIN_URL=${2}
else
    usage
fi

# Start WebLogic server
# Reset number of open file descriptors in the current process
# This function is defined in commEnv.sh
resetFd

CLASSPATH="${WEBLOGIC_CLASSPATH}${CLASSPATHSEP}${POINTBASE_CLASSPATH}${CLASSPATHSEP}${JAVA_HOME}/jre/lib/rt.jar${CLASSPATHSEP}${WL_HOME}/server/lib/webservices.jar${CLASSPATHSEP}${CLASSPATH}"
export CLASSPATH

# Start WebLogic server
echo CLASSPATH="${CLASSPATH}"
echo
echo PATH="${PATH}"
echo
echo "***************************************************"
echo "*  To start WebLogic Server, use a username and   *"
echo "*  password assigned to an admin-level user.  For *"
echo "*  server administration, use the WebLogic Server *"
echo "*  console at http://<hostname>:<port>/console    *"
echo "***************************************************"

"$JAVA_HOME/bin/java" ${JAVA_VM} ${MEM_ARGS} ${JAVA_OPTIONS}     \
  -Dweblogic.Name=${SERVER_NAME}                                 \
  -Dweblogic.management.username=${WLS_USER}                     \
  -Dweblogic.management.password=${WLS_PW}                       \
  -Dweblogic.management.server=${ADMIN_URL}                      \
  -Djava.security.policy="${WL_HOME}/server/lib/weblogic.policy" \
   weblogic.Server 
