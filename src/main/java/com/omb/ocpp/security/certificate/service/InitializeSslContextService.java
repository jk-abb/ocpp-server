package com.omb.ocpp.security.certificate.service;

import com.omb.ocpp.security.certificate.api.KeystoreApi;
import com.omb.ocpp.security.certificate.config.KeystoreCertificateConfig;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.util.Objects;
import java.util.UUID;

public class InitializeSslContextService {

    private final KeystoreApi keystoreApi;
    private final UUID keystoreUUID;

    public InitializeSslContextService(KeystoreApi keystoreApi, UUID keystoreUUID) {
        this.keystoreApi = Objects.requireNonNull(keystoreApi);
        this.keystoreUUID = Objects.requireNonNull(keystoreUUID);
    }

    public SSLContext execute() throws Exception {

        KeystoreCertificateConfig keyStoreConfig = keystoreApi.getKeystoreCertificateConfig(keystoreUUID);

        SSLContext context = SSLContext.getInstance(keyStoreConfig.getKeystoreProtocol());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");

        KeyStore keyStore = keystoreApi.getKeyStores(keyStoreConfig.getUuid());
        keyManagerFactory.init(keyStore, keyStoreConfig.getKeystorePassword().toCharArray());

        KeyStore trustStore = keystoreApi.getTrustStore();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
        tmf.init(trustStore);

        context.init(keyManagerFactory.getKeyManagers(), tmf.getTrustManagers(), null);

        return context;
    }
}
