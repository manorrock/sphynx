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
 * The configuration of the Hue Bridge.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@JsonbPropertyOrder({"name", "dataStoreVersion", "softwareVersion", "apiVersion",
    "macAddress", "bridgeId", "factoryNew", "replacesBridgeId", "modelId", 
    "starterKitId"})
public class HueBridgeBaseConfig {

    /**
     * Stores the API version.
     */
    @JsonbProperty("apiversion")
    private String apiVersion;

    /**
     * Stores the bridge ID.
     */
    @JsonbProperty("bridgeid")
    private String bridgeId;

    /**
     * Stores the data store version.
     */
    @JsonbProperty("datastoreversion")
    private String dataStoreVersion;

    /**
     * Stores the factory new information.
     */
    @JsonbProperty("factorynew")
    private boolean factoryNew;

    /**
     * Stores the MAC address.
     */
    @JsonbProperty("mac")
    private String macAddress;

    /**
     * Stores the model ID.
     */
    @JsonbProperty("modelid")
    private String modelId;

    /**
     * Stores the name.
     */
    private String name;

    /**
     * Stores the replaces bridge ID.
     */
    @JsonbProperty("replacesbridgeid")
    private String replacesBridgeId;

    /**
     * Stores the software version.
     */
    @JsonbProperty("swversion")
    private String softwareVersion;
    
    /**
     * Stores the starter kit ID.
     */
    @JsonbProperty("starterkitid")
    private String starterKitId;

    /**
     * Get the API version.
     *
     * @return the API version.
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Get the bridge ID.
     *
     * @return the bridge ID.
     */
    public String getBridgeId() {
        return bridgeId;
    }

    /**
     * Get the data store version.
     *
     * @return the data store version.
     */
    public String getDataStoreVersion() {
        return dataStoreVersion;
    }

    /**
     * Get the mac address.
     *
     * @return the MAC address.
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Get the model ID.
     *
     * @return the model ID.
     */
    public String getModelId() {
        return modelId;
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
     * Get the replaces Bridge ID.
     *
     * @return the replaces Bridge ID.
     */
    public String getReplacesBridgeId() {
        return replacesBridgeId;
    }

    /**
     * Get the software version.
     *
     * @return the software version.
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Get the starter kid ID.
     * 
     * @return the starter kit ID.
     */
    public String getStarterKitId() {
        return starterKitId;
    }

    /**
     * Is the factory new.
     *
     * @return true if factory new, false otherwise.
     */
    public boolean isFactoryNew() {
        return factoryNew;
    }

    /**
     * Set the API version.
     *
     * @param apiVersion the API version.
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Set the bridge ID.
     *
     * @param bridgeId the bridge ID.
     */
    public void setBridgeId(String bridgeId) {
        this.bridgeId = bridgeId;
    }

    /**
     * Set the data store version.
     *
     * @param dataStoreVersion the data store version.
     */
    public void setDataStoreVersion(String dataStoreVersion) {
        this.dataStoreVersion = dataStoreVersion;
    }

    /**
     * Set the factory new flag.
     *
     * @param factoryNew the factory new flag.
     */
    public void setFactoryNew(boolean factoryNew) {
        this.factoryNew = factoryNew;
    }

    /**
     * Set the MAC address.
     *
     * @param macAddress the MAC address.
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Set the model ID.
     *
     * @param modelId the model ID.
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
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
     * Set the replaces Bridge ID.
     *
     * @param replacesBridgeId the replaces Bridge ID.
     */
    public void setReplacesBridgeId(String replacesBridgeId) {
        this.replacesBridgeId = replacesBridgeId;
    }

    /**
     * Set the software version.
     *
     * @param softwareVersion the software version.
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    /**
     * Set the starter kit ID.
     * 
     * @param starterKitId the starter kit ID.
     */
    public void setStarterKitId(String starterKitId) {
        this.starterKitId = starterKitId;
    }
}
