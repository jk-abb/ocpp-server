package com.omb.ocpp.server.iso15118.dto;

import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;

public class OCSPRequestData {

    /**
     * (Required)
     */
    @SerializedName("hashAlgorithm")
    @NotNull
    private OCSPRequestData.HashAlgorithm hashAlgorithm;
    /**
     * (Required)
     */
    @SerializedName("issuerNameHash")
    @Size(max = 128)
    @NotNull
    private String issuerNameHash;
    /**
     * (Required)
     */
    @SerializedName("issuerKeyHash")
    @Size(max = 128)
    @NotNull
    private String issuerKeyHash;
    /**
     * (Required)
     */
    @SerializedName("serialNumber")
    @Size(max = 20)
    @NotNull
    private String serialNumber;
    @SerializedName("responderURL")
    @Size(max = 512)
    private String responderURL;

    /**
     * (Required)
     */
    @SerializedName("hashAlgorithm")
    public Optional<HashAlgorithm> getHashAlgorithm() {
        return Optional.ofNullable(hashAlgorithm);
    }

    /**
     * (Required)
     */
    @SerializedName("hashAlgorithm")
    public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    /**
     * (Required)
     */
    @SerializedName("issuerNameHash")
    public Optional<String> getIssuerNameHash() {
        return Optional.ofNullable(issuerNameHash);
    }

    /**
     * (Required)
     */
    @SerializedName("issuerNameHash")
    public void setIssuerNameHash(String issuerNameHash) {
        this.issuerNameHash = issuerNameHash;
    }

    /**
     * (Required)
     */
    @SerializedName("issuerKeyHash")
    public Optional<String> getIssuerKeyHash() {
        return Optional.ofNullable(issuerKeyHash);
    }

    /**
     * (Required)
     */
    @SerializedName("issuerKeyHash")
    public void setIssuerKeyHash(String issuerKeyHash) {
        this.issuerKeyHash = issuerKeyHash;
    }

    /**
     * (Required)
     */
    @SerializedName("serialNumber")
    public Optional<String> getSerialNumber() {
        return Optional.ofNullable(serialNumber);
    }

    /**
     * (Required)
     */
    @SerializedName("serialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @SerializedName("responderURL")
    public Optional<String> getResponderURL() {
        return Optional.ofNullable(responderURL);
    }

    @SerializedName("responderURL")
    public void setResponderURL(String responderURL) {
        this.responderURL = responderURL;
    }

    public enum HashAlgorithm {
        SHA_256,
        SHA_384,
        SHA_512;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        OCSPRequestData that = (OCSPRequestData) object;
        return hashAlgorithm == that.hashAlgorithm &&
                Objects.equals(issuerNameHash, that.issuerNameHash) &&
                Objects.equals(issuerKeyHash, that.issuerKeyHash) &&
                Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(responderURL, that.responderURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashAlgorithm, issuerNameHash, issuerKeyHash, serialNumber, responderURL);
    }

    @Override
    public String toString() {
        return "OCSPRequestData{" +
                "hashAlgorithm=" + hashAlgorithm +
                ", issuerNameHash='" + issuerNameHash + '\'' +
                ", issuerKeyHash='" + issuerKeyHash + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", responderURL='" + responderURL + '\'' +
                ", additionalProperties=" +
                '}';
    }
}
