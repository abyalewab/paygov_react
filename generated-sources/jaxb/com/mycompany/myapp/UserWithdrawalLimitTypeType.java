//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.11 at 07:31:39 AM EAT 
//


package com.mycompany.myapp;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserWithdrawalLimitTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserWithdrawalLimitTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Limited"/>
 *     &lt;enumeration value="Unlimited"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserWithdrawalLimitTypeType")
@XmlEnum
public enum UserWithdrawalLimitTypeType {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Limited")
    LIMITED("Limited"),
    @XmlEnumValue("Unlimited")
    UNLIMITED("Unlimited");
    private final String value;

    UserWithdrawalLimitTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserWithdrawalLimitTypeType fromValue(String v) {
        for (UserWithdrawalLimitTypeType c: UserWithdrawalLimitTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
