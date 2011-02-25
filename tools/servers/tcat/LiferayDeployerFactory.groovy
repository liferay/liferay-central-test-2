package com.mulesoft.tcat.script;

import com.mulesoft.common.agent.file.FileService;
import com.mulesoft.common.remoting.RemoteContext;

import com.mulesoft.tcat.DeployerFactory;
import com.mulesoft.tcat.DeploymentVersion;
import com.mulesoft.tcat.Server;

import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployer.DeployableMonitor;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.deployer.DeployerType;
import org.codehaus.cargo.util.log.Logger;

import org.springframework.context.ApplicationContext;

/**
 * This script replaces the TCat Deployer and DeployerFactory with ones
 * customized LiferayDeployer and LiferayDeployerFactory.
 *
 * Liferay's WARs require some additional preprocessing before they can be
 * properly placed into Tomcat's webapps directory.  This script intercepts
 * Liferay WAR files and places them into Liferay's hot deploy directory
 * located at ${CATALINA_HOME}/deploy.  Liferay will then complete the
 * deployment.
 *
 */
public class LiferayDeployer implements Deployer {
    Deployer _wrappedDeployer;
    FileService _fileService;
    Server _server;
    Logger _logger;
    
    public void setLogger(Logger logger) {
        _logger = logger;
        _wrappedDeployer.setLogger(logger);
    }
    
    public Logger getLogger() {
        return _logger;
    }
    
    public void deploy(Deployable deployable) {
		def deployableFileName = deployable.getFile();

        if (deployableFileName.contains("-ext") ||
			deployableFileName.contains("-hook") ||
			deployableFileName.contains("-layouttpl") ||
			deployableFileName.contains("-portlet") ||
			deployableFileName.contains("-theme") ||
			deployableFileName.contains("-web")) {

			println("Deploying Liferay module: " + deployableFileName);

            RemoteContext.setServerId(_server.id);

            def input = new FileInputStream(deployableFileName);
            def file = new File(deployableFileName);
            def name = file.name;

            name = name.substring(0, name.lastIndexOf(".war") + 4);

			_fileService.upload("/deploy/" + name, input);

			println(
				"Completed deployment Liferay module: " + deployableFileName);
        }
		else {
            _wrappedDeployer.deploy(deployable);
        }
    }
    
    public void deploy(Deployable deployable, DeployableMonitor monitor) {
        def input = new FileInputStream(deployable.getFile());

        RemoteContext.setServerId(_server.id);

        _fileService.upload("/deploy", input);
    }
    
    public void undeploy(Deployable deployable) {
        _wrappedDeployer.undeploy(deployable);
    }
    
    public void undeploy(Deployable deployable, DeployableMonitor monitor) {
        _wrappedDeployer.undeploy(deployable, monitor);
    }
    
    public void redeploy(Deployable deployable) {
        _wrappedDeployer.redeploy(deployable);
    }
    
    public void start(Deployable deployable) {
        _wrappedDeployer.start(deployable);
    }
    
    public void stop(Deployable deployable) {
        _wrappedDeployer.stop(deployable);
    }

    public DeployerType getType() {
        return _wrappedDeployer.getType();
    }
}

/**
 * Listens for deployments which happen and then adds in a context.xml
 * from /Configuration/Contexts/[server-name].xml.
 */
public class LiferayDeployerFactory implements DeployerFactory {
    def applicationContext;
    def defaultDeployerFactory;
    
    public LiferayDeployerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.defaultDeployerFactory = applicationContext.getBean(
			"deployerFactory");
    }
    
    public Deployer getDeployer(
		DeploymentVersion deploymentVersion, Server server) {

		Deployer wrappedDeployer = defaultDeployerFactory.getDeployer(
			deploymentVersion, server)
        
        return new LiferayDeployer(
			_fileService: applicationContext.getBean("tomcatFileService"),
			server: server, _wrappedDeployer: wrappedDeployer);
    }
        
}

def factory = new LiferayDeployerFactory(applicationContext);

applicationContext.getBean(
	"deploymentActionManager").setDeployerFactory(factory);
