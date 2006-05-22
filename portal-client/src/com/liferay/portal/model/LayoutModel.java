/**
 * LayoutModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.liferay.portal.model;

public class LayoutModel  extends com.liferay.portal.model.BaseModel  implements java.io.Serializable {
    private java.lang.String colorSchemeId;

    private java.lang.String companyId;

    private java.lang.String friendlyURL;

    private boolean hidden;

    private java.lang.String layoutId;

    private java.lang.String name;

    private java.lang.String ownerId;

    private java.lang.String parentLayoutId;

    private com.liferay.portal.service.persistence.LayoutPK primaryKey;

    private int priority;

    private java.lang.String themeId;

    private java.lang.String type;

    private java.lang.String typeSettings;

    public LayoutModel() {
    }

    public LayoutModel(
           boolean modified,
           boolean _new,
           java.lang.String colorSchemeId,
           java.lang.String companyId,
           java.lang.String friendlyURL,
           boolean hidden,
           java.lang.String layoutId,
           java.lang.String name,
           java.lang.String ownerId,
           java.lang.String parentLayoutId,
           com.liferay.portal.service.persistence.LayoutPK primaryKey,
           int priority,
           java.lang.String themeId,
           java.lang.String type,
           java.lang.String typeSettings) {
        super(
            modified,
            _new);
        this.colorSchemeId = colorSchemeId;
        this.companyId = companyId;
        this.friendlyURL = friendlyURL;
        this.hidden = hidden;
        this.layoutId = layoutId;
        this.name = name;
        this.ownerId = ownerId;
        this.parentLayoutId = parentLayoutId;
        this.primaryKey = primaryKey;
        this.priority = priority;
        this.themeId = themeId;
        this.type = type;
        this.typeSettings = typeSettings;
    }


    /**
     * Gets the colorSchemeId value for this LayoutModel.
     * 
     * @return colorSchemeId
     */
    public java.lang.String getColorSchemeId() {
        return colorSchemeId;
    }


    /**
     * Sets the colorSchemeId value for this LayoutModel.
     * 
     * @param colorSchemeId
     */
    public void setColorSchemeId(java.lang.String colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }


    /**
     * Gets the companyId value for this LayoutModel.
     * 
     * @return companyId
     */
    public java.lang.String getCompanyId() {
        return companyId;
    }


    /**
     * Sets the companyId value for this LayoutModel.
     * 
     * @param companyId
     */
    public void setCompanyId(java.lang.String companyId) {
        this.companyId = companyId;
    }


    /**
     * Gets the friendlyURL value for this LayoutModel.
     * 
     * @return friendlyURL
     */
    public java.lang.String getFriendlyURL() {
        return friendlyURL;
    }


    /**
     * Sets the friendlyURL value for this LayoutModel.
     * 
     * @param friendlyURL
     */
    public void setFriendlyURL(java.lang.String friendlyURL) {
        this.friendlyURL = friendlyURL;
    }


    /**
     * Gets the hidden value for this LayoutModel.
     * 
     * @return hidden
     */
    public boolean isHidden() {
        return hidden;
    }


    /**
     * Sets the hidden value for this LayoutModel.
     * 
     * @param hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }


    /**
     * Gets the layoutId value for this LayoutModel.
     * 
     * @return layoutId
     */
    public java.lang.String getLayoutId() {
        return layoutId;
    }


    /**
     * Sets the layoutId value for this LayoutModel.
     * 
     * @param layoutId
     */
    public void setLayoutId(java.lang.String layoutId) {
        this.layoutId = layoutId;
    }


    /**
     * Gets the name value for this LayoutModel.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this LayoutModel.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the ownerId value for this LayoutModel.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this LayoutModel.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the parentLayoutId value for this LayoutModel.
     * 
     * @return parentLayoutId
     */
    public java.lang.String getParentLayoutId() {
        return parentLayoutId;
    }


    /**
     * Sets the parentLayoutId value for this LayoutModel.
     * 
     * @param parentLayoutId
     */
    public void setParentLayoutId(java.lang.String parentLayoutId) {
        this.parentLayoutId = parentLayoutId;
    }


    /**
     * Gets the primaryKey value for this LayoutModel.
     * 
     * @return primaryKey
     */
    public com.liferay.portal.service.persistence.LayoutPK getPrimaryKey() {
        return primaryKey;
    }


    /**
     * Sets the primaryKey value for this LayoutModel.
     * 
     * @param primaryKey
     */
    public void setPrimaryKey(com.liferay.portal.service.persistence.LayoutPK primaryKey) {
        this.primaryKey = primaryKey;
    }


    /**
     * Gets the priority value for this LayoutModel.
     * 
     * @return priority
     */
    public int getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this LayoutModel.
     * 
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }


    /**
     * Gets the themeId value for this LayoutModel.
     * 
     * @return themeId
     */
    public java.lang.String getThemeId() {
        return themeId;
    }


    /**
     * Sets the themeId value for this LayoutModel.
     * 
     * @param themeId
     */
    public void setThemeId(java.lang.String themeId) {
        this.themeId = themeId;
    }


    /**
     * Gets the type value for this LayoutModel.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this LayoutModel.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the typeSettings value for this LayoutModel.
     * 
     * @return typeSettings
     */
    public java.lang.String getTypeSettings() {
        return typeSettings;
    }


    /**
     * Sets the typeSettings value for this LayoutModel.
     * 
     * @param typeSettings
     */
    public void setTypeSettings(java.lang.String typeSettings) {
        this.typeSettings = typeSettings;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LayoutModel)) return false;
        LayoutModel other = (LayoutModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.colorSchemeId==null && other.getColorSchemeId()==null) || 
             (this.colorSchemeId!=null &&
              this.colorSchemeId.equals(other.getColorSchemeId()))) &&
            ((this.companyId==null && other.getCompanyId()==null) || 
             (this.companyId!=null &&
              this.companyId.equals(other.getCompanyId()))) &&
            ((this.friendlyURL==null && other.getFriendlyURL()==null) || 
             (this.friendlyURL!=null &&
              this.friendlyURL.equals(other.getFriendlyURL()))) &&
            this.hidden == other.isHidden() &&
            ((this.layoutId==null && other.getLayoutId()==null) || 
             (this.layoutId!=null &&
              this.layoutId.equals(other.getLayoutId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.ownerId==null && other.getOwnerId()==null) || 
             (this.ownerId!=null &&
              this.ownerId.equals(other.getOwnerId()))) &&
            ((this.parentLayoutId==null && other.getParentLayoutId()==null) || 
             (this.parentLayoutId!=null &&
              this.parentLayoutId.equals(other.getParentLayoutId()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            this.priority == other.getPriority() &&
            ((this.themeId==null && other.getThemeId()==null) || 
             (this.themeId!=null &&
              this.themeId.equals(other.getThemeId()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.typeSettings==null && other.getTypeSettings()==null) || 
             (this.typeSettings!=null &&
              this.typeSettings.equals(other.getTypeSettings())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getColorSchemeId() != null) {
            _hashCode += getColorSchemeId().hashCode();
        }
        if (getCompanyId() != null) {
            _hashCode += getCompanyId().hashCode();
        }
        if (getFriendlyURL() != null) {
            _hashCode += getFriendlyURL().hashCode();
        }
        _hashCode += (isHidden() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getLayoutId() != null) {
            _hashCode += getLayoutId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOwnerId() != null) {
            _hashCode += getOwnerId().hashCode();
        }
        if (getParentLayoutId() != null) {
            _hashCode += getParentLayoutId().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        _hashCode += getPriority();
        if (getThemeId() != null) {
            _hashCode += getThemeId().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getTypeSettings() != null) {
            _hashCode += getTypeSettings().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LayoutModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.portal.liferay.com", "LayoutModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colorSchemeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colorSchemeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendlyURL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friendlyURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hidden");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hidden"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layoutId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "layoutId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ownerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ownerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentLayoutId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentLayoutId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://persistence.service.portal.liferay.com", "LayoutPK"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("themeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "themeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeSettings");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typeSettings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
