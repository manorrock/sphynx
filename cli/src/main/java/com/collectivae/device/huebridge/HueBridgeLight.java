/*
 * Copyright (c) 2002-2020 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its 
 *      contributors may be used to endorse or promote products derived from
 *      this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.collectivae.device.huebridge;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * A Philips Hue light.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@JsonbPropertyOrder({"state", "softwareUpdate", "type", "name", 
    "manufacturerName", "productName", "capabilities", "softwareConfigId", "productId"})
public class HueBridgeLight {
    
    /**
     * Stores the capabilities.
     */
    @JsonbProperty("capabilities")
    private HueBridgeLightCapabilities capabilities;

    /**
     * Stores the manufacturer name.
     */
    @JsonbProperty("manufacturername")
    private String manufacturerName;

    /**
     * Stores the name.
     */
    @JsonbProperty("name")
    private String name;

    /**
     * Stores the product ID.
     */
    @JsonbProperty("productid")
    private String productId;

    /**
     * Stores the product name.
     */
    @JsonbProperty("productname")
    private String productName;

    /**
     * Stores the software config ID.
     */
    @JsonbProperty("swconfigid")
    private String softwareConfigId;
    
    /**
     * Stores the software update.
     */
    @JsonbProperty("swupdate")
    private HueBridgeLightSoftwareUpdate softwareUpdate;

    /**
     * Stores the state.
     */
    @JsonbProperty("state")
    private HueBridgeLightState state;

    /**
     * Stores the type.
     */
    @JsonbProperty("type")
    private String type;

    /**
     * Get the capabilities.
     * 
     * @return the capabilities.
     */
    public HueBridgeLightCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Get the manufacturer name.
     *
     * @return the manufacturer name.
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the product ID.
     *
     * @return the product ID.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Get the product name.
     *
     * @return product name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Get the software config id.
     *
     * @return the software config id.
     */
    public String getSoftwareConfigId() {
        return softwareConfigId;
    }
    
    /**
     * Get the software update.
     * 
     * @return the software update.
     */
    public HueBridgeLightSoftwareUpdate getSoftwareUpdate() {
        return this.softwareUpdate;
    }

    /**
     * Get the light state.
     *
     * @return the light state.
     */
    public HueBridgeLightState getState() {
        return state;
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the capabilities.
     * 
     * @param capabilities the capabilities.
     */
    public void setCapabilities(HueBridgeLightCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * Set the manufacturer name.
     *
     * @param manufacturerName the manufacturer name.
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Set the product name.
     *
     * @param productName the product name.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Set the software config id.
     *
     * @param softwareConfigId the software config id.
     */
    public void setSoftwareConfigId(String softwareConfigId) {
        this.softwareConfigId = softwareConfigId;
    }

    /**
     * Set the software update.
     * 
     * @param softwareUpdate the software update.
     */
    public void setSoftwareUpdate(HueBridgeLightSoftwareUpdate softwareUpdate) {
        this.softwareUpdate = softwareUpdate;
    }

    /**
     * Set the light state.
     *
     * @param state the state.
     */
    public void setState(HueBridgeLightState state) {
        this.state = state;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
