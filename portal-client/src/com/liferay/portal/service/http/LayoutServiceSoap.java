/**
 * LayoutServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.liferay.portal.service.http;

public interface LayoutServiceSoap extends java.rmi.Remote {
    public com.liferay.portal.model.LayoutModel addLayout(java.lang.String groupId, boolean privateLayout, java.lang.String parentLayoutId, java.lang.String name, java.lang.String type, boolean hidden, java.lang.String friendlyURL) throws java.rmi.RemoteException;
    public void deleteLayout(java.lang.String layoutId, java.lang.String ownerId) throws java.rmi.RemoteException;
    public com.liferay.portal.model.LayoutModel[] getLayouts(java.lang.String companyId, java.lang.String portletId, java.lang.String prefsKey, java.lang.String prefsValue) throws java.rmi.RemoteException;
    public void setLayouts(java.lang.String ownerId, java.lang.String parentLayoutId, java.lang.String[] layoutIds) throws java.rmi.RemoteException;
    public com.liferay.portal.model.LayoutModel updateLayout(java.lang.String layoutId, java.lang.String ownerId, java.lang.String typeSettings) throws java.rmi.RemoteException;
    public com.liferay.portal.model.LayoutModel updateLayout(java.lang.String layoutId, java.lang.String ownerId, java.lang.String parentLayoutId, java.lang.String name, java.lang.String languageId, java.lang.String type, boolean hidden, java.lang.String friendlyURL) throws java.rmi.RemoteException;
    public com.liferay.portal.model.LayoutModel updateLookAndFeel(java.lang.String layoutId, java.lang.String ownerId, java.lang.String themeId, java.lang.String colorSchemeId) throws java.rmi.RemoteException;
}
