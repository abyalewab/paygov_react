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
 * <p>Java class for BillingCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BillingCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="MerchantInitiatedBilling"/>
 *     &lt;enumeration value="RecurringPayments"/>
 *     &lt;enumeration value="MerchantInitiatedBillingSingleAgreement"/>
 *     &lt;enumeration value="ChannelInitiatedBilling"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BillingCodeType")
@XmlEnum
public enum BillingCodeType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("MerchantInitiatedBilling")
    MERCHANT_INITIATED_BILLING("MerchantInitiatedBilling"),
    @XmlEnumValue("RecurringPayments")
    RECURRING_PAYMENTS("RecurringPayments"),
    @XmlEnumValue("MerchantInitiatedBillingSingleAgreement")
    MERCHANT_INITIATED_BILLING_SINGLE_AGREEMENT("MerchantInitiatedBillingSingleAgreement"),
    @XmlEnumValue("ChannelInitiatedBilling")
    CHANNEL_INITIATED_BILLING("ChannelInitiatedBilling");
    private final String value;

    BillingCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillingCodeType fromValue(String v) {
        for (BillingCodeType c: BillingCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
