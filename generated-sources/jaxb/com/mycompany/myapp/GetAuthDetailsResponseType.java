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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAuthDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAuthDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetAuthDetailsResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAuthDetailsResponseType", namespace = "urn:ebay:api:PayPalAPI", propOrder = {
    "getAuthDetailsResponseDetails"
})
public class GetAuthDetailsResponseType
    extends AbstractResponseType
{

    @XmlElement(name = "GetAuthDetailsResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetAuthDetailsResponseDetailsType getAuthDetailsResponseDetails;

    /**
     * Gets the value of the getAuthDetailsResponseDetails property.
     * 
     * @return
     *     possible object is
     *     {@link GetAuthDetailsResponseDetailsType }
     *     
     */
    public GetAuthDetailsResponseDetailsType getGetAuthDetailsResponseDetails() {
        return getAuthDetailsResponseDetails;
    }

    /**
     * Sets the value of the getAuthDetailsResponseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAuthDetailsResponseDetailsType }
     *     
     */
    public void setGetAuthDetailsResponseDetails(GetAuthDetailsResponseDetailsType value) {
        this.getAuthDetailsResponseDetails = value;
    }

}
