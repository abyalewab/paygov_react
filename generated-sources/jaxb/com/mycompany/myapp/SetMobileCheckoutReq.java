//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.11 at 07:31:39 AM EAT 
//


package com.mycompany.myapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetMobileCheckoutRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "setMobileCheckoutRequest"
})
@XmlRootElement(name = "SetMobileCheckoutReq", namespace = "urn:ebay:api:PayPalAPI")
public class SetMobileCheckoutReq {

    @XmlElement(name = "SetMobileCheckoutRequest", namespace = "urn:ebay:api:PayPalAPI", required = true)
    protected SetMobileCheckoutRequestType setMobileCheckoutRequest;

    /**
     * Gets the value of the setMobileCheckoutRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SetMobileCheckoutRequestType }
     *     
     */
    public SetMobileCheckoutRequestType getSetMobileCheckoutRequest() {
        return setMobileCheckoutRequest;
    }

    /**
     * Sets the value of the setMobileCheckoutRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetMobileCheckoutRequestType }
     *     
     */
    public void setSetMobileCheckoutRequest(SetMobileCheckoutRequestType value) {
        this.setMobileCheckoutRequest = value;
    }

}
