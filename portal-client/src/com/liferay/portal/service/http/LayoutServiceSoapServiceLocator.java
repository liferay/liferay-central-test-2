/**
 * LayoutServiceSoapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.liferay.portal.service.http;

public class LayoutServiceSoapServiceLocator extends org.apache.axis.client.Service implements com.liferay.portal.service.http.LayoutServiceSoapService {

    public LayoutServiceSoapServiceLocator() {
    }


    public LayoutServiceSoapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LayoutServiceSoapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Portal_LayoutService
    private java.lang.String Portal_LayoutService_address = "http://localhost:8080/tunnel/axis/Portal_LayoutService";

    public java.lang.String getPortal_LayoutServiceAddress() {
        return Portal_LayoutService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Portal_LayoutServiceWSDDServiceName = "Portal_LayoutService";

    public java.lang.String getPortal_LayoutServiceWSDDServiceName() {
        return Portal_LayoutServiceWSDDServiceName;
    }

    public void setPortal_LayoutServiceWSDDServiceName(java.lang.String name) {
        Portal_LayoutServiceWSDDServiceName = name;
    }

    public com.liferay.portal.service.http.LayoutServiceSoap getPortal_LayoutService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Portal_LayoutService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPortal_LayoutService(endpoint);
    }

    public com.liferay.portal.service.http.LayoutServiceSoap getPortal_LayoutService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.liferay.portal.service.http.Portal_LayoutServiceSoapBindingStub _stub = new com.liferay.portal.service.http.Portal_LayoutServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPortal_LayoutServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPortal_LayoutServiceEndpointAddress(java.lang.String address) {
        Portal_LayoutService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.liferay.portal.service.http.LayoutServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.liferay.portal.service.http.Portal_LayoutServiceSoapBindingStub _stub = new com.liferay.portal.service.http.Portal_LayoutServiceSoapBindingStub(new java.net.URL(Portal_LayoutService_address), this);
                _stub.setPortName(getPortal_LayoutServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Portal_LayoutService".equals(inputPortName)) {
            return getPortal_LayoutService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:http.service.portal.liferay.com", "LayoutServiceSoapService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:http.service.portal.liferay.com", "Portal_LayoutService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Portal_LayoutService".equals(portName)) {
            setPortal_LayoutServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
