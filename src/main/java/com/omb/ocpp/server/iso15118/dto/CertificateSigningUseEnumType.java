package com.omb.ocpp.server.iso15118.dto;

import com.google.gson.annotations.SerializedName;

public enum CertificateSigningUseEnumType {
    @SerializedName("ChargingStationCertificate")
    CHARGING_STATION_CERTIFICATE(),
    @SerializedName("V2GCertificate")
    V2G_CERTIFICATE();
}
