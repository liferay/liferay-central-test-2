/**
 * BaseModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.liferay.portal.model;

public abstract class BaseModel  implements java.io.Serializable {
    private boolean modified;

    private boolean _new;

    public BaseModel() {
    }

    public BaseModel(
           boolean modified,
           boolean _new) {
           this.modified = modified;
           this._new = _new;
    }


    /**
     * Gets the modified value for this BaseModel.
     * 
     * @return modified
     */
    public boolean isModified() {
        return modified;
    }


    /**
     * Sets the modified value for this BaseModel.
     * 
     * @param modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }


    /**
     * Gets the _new value for this BaseModel.
     * 
     * @return _new
     */
    public boolean is_new() {
        return _new;
    }


    /**
     * Sets the _new value for this BaseModel.
     * 
     * @param _new
     */
    public void set_new(boolean _new) {
        this._new = _new;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseModel)) return false;
        BaseModel other = (BaseModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.modified == other.isModified() &&
            this._new == other.is_new();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (isModified() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_new() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.portal.liferay.com", "BaseModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modified");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_new");
        elemField.setXmlName(new javax.xml.namespace.QName("", "new"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
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
