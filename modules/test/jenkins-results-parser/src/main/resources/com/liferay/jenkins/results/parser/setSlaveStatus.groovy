import hudson.slaves.SlaveComputer;

String nodeNames = "${node.names}";

for (String nodeName : nodeNames.split(",")) {
	Hudson hudson = Hudson.instance;

	Slave slave = hudson.getNode(nodeName.trim());

	SlaveComputer slaveComputer = slave.getComputer();

	try {
		boolean offlineStatus = ${offline.status};

		slaveComputer.setTemporarilyOffline(offlineStatus)
	}
	catch (NullPointerException npe) {
	}
}