package com.omb.ocpp.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omb.ocpp.groovy.GroovyService;
import com.omb.ocpp.gui.Application;
import com.omb.ocpp.security.certificate.api.KeystoreApi;
import com.omb.ocpp.security.certificate.config.KeystoreCertificateConfig;
import com.omb.ocpp.security.certificate.service.TrustStoreService;
import com.omb.ocpp.server.OcppServerService;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;
import eu.chargetime.ocpp.model.core.ChangeConfigurationRequest;
import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.core.GetConfigurationRequest;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest;
import eu.chargetime.ocpp.model.core.ResetRequest;
import eu.chargetime.ocpp.model.core.UnlockConnectorRequest;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileRequest;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAPI.class);
    private final OcppServerService ocppServerService = Application.APPLICATION.getService(OcppServerService.class);
    private final GroovyService groovyService = Application.APPLICATION.getService(GroovyService.class);
    private final KeystoreApi keystoreApi = Application.APPLICATION.getService(KeystoreApi.class);
    private final TrustStoreService trustStoreService = Application.APPLICATION.getService(TrustStoreService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("send-reset-request")
    public Response sendResetRequest(ResetRequest resetRequest) {
        return sendRequest(resetRequest);
    }

    @POST
    @Path("send-get-diagnostics")
    public Response sendGetDiagnostics(GetDiagnosticsRequest getDiagnosticsRequest) {
        return sendRequest(getDiagnosticsRequest);
    }

    @POST
    @Path("send-change-availability-request")
    public Response sendChangeAvailabilityRequest(ChangeAvailabilityRequest changeAvailabilityRequest) {
        return sendRequest(changeAvailabilityRequest);
    }

    @POST
    @Path("send-change-configuration-request")
    public Response sendChangeConfigurationRequest(ChangeConfigurationRequest changeConfigurationRequest) {
        return sendRequest(changeConfigurationRequest);
    }

    @POST
    @Path("send-clear-cache-request")
    public Response sendClearCacheRequest(ClearCacheRequest clearCacheRequest) {
        return sendRequest(clearCacheRequest);
    }

    @POST
    @Path("send-data-transfer-request")
    public Response sendDataTransferRequest(DataTransferRequest dataTransferRequest) {
        return sendRequest(dataTransferRequest);
    }

    @POST
    @Path("send-get-configuration-request")
    public Response sendGetConfigurationRequest(GetConfigurationRequest getConfigurationRequest) {
        return sendRequest(getConfigurationRequest);
    }

    @POST
    @Path("send-remote-start-transaction-request")
    public Response sendRemoteStartTransactionRequest(RemoteStartTransactionRequest remoteStartTransactionRequest) {
        return sendRequest(remoteStartTransactionRequest);
    }

    @POST
    @Path("send-remote-stop-transaction-request")
    public Response sendRemoteStopTransactionRequest(RemoteStopTransactionRequest remoteStopTransactionRequest) {
        return sendRequest(remoteStopTransactionRequest);
    }


    @POST
    @Path("send-unlock-connector-request")
    public Response sendUnlockConnectorRequest(UnlockConnectorRequest unlockConnectorRequest) {
        return sendRequest(unlockConnectorRequest);
    }

    @POST
    @Path("send-diagnostics-status-notification-request")
    public Response sendDiagnosticsStatusNotificationRequest(DiagnosticsStatusNotificationRequest diagnosticsStatusNotificationRequest) {
        return sendRequest(diagnosticsStatusNotificationRequest);
    }

    @POST
    @Path("send-firmware-status-notification-request")
    public Response sendFirmwareStatusNotificationRequest(FirmwareStatusNotificationRequest firmwareStatusNotificationRequest) {
        return sendRequest(firmwareStatusNotificationRequest);
    }

    @POST
    @Path("send-set-charging-profile-request")
    public Response sendSetChargingProfileRequest(SetChargingProfileRequest setChargingProfileRequest) {
        return sendRequest(setChargingProfileRequest);
    }

    @POST
    @Path("send-clear-charging-profile-request")
    public Response sendClearChargingProfileRequest(ClearChargingProfileRequest clearChargingProfileRequest) {
        return sendRequest(clearChargingProfileRequest);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("upload-confirmation-supplier")
    public Response uploadConfirmationSupplier(@FormDataParam("file") InputStream uploadedInputStream,
                                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
        if (uploadedInputStream == null || fileDetail == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            groovyService.uploadGroovyScript(uploadedInputStream, fileDetail.getFileName());
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Could not upload confirmation supplier", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("upload-client-cert")
    public Response uploadClientCertificate(@FormDataParam("file") InputStream uploadedInputStream,
                                            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        if (uploadedInputStream == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            trustStoreService.addClientCertificate(uploadedInputStream);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Could not upload client certificate", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @DELETE
    @Path("delete-client-cert")
    public Response deleteClientCertificate(@QueryParam("alias") String alias) {
        if (alias == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            trustStoreService.deleteClientCertificate(alias);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Could not delete client certificate", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("list-trust-store-aliases")
    public Response listClientCertificate() {
        Optional<List<String>> aliases = trustStoreService.listAliases();
        if (aliases.isPresent()) {
            return Response.ok(aliases.get()).build();
        } else {
            LOGGER.error("Could not list trust store aliases");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Could not list trust store aliases").build();
        }
    }

    @GET
    @Path("download-server-cert")
    public Response downloadServerCertificate(@QueryParam("uuid") String uuid) {
        try {
            String certificate = keystoreApi.getServerCertificatePem(UUID.fromString(uuid));
            StreamingOutput fileStream = output -> {
                output.write(certificate.getBytes());
                output.flush();
            };
            return Response
                    .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-disposition", "attachment; filename = server.pem")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    String.format("Server certificate does not exist, error: %s", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("delete-server-cert")
    public Response deleteServerCertificate(@QueryParam("uuid") String uuid) {
        try {
            keystoreApi.deleteKeystoreCertificate(UUID.fromString(uuid));
            return Response
                    .ok()
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    String.format("Could not delete certificate, error: %s", e.getMessage())).build();
        }
    }

    @POST
    @Path("generate-server-cert")
    public Response generateServerCertificate() {
        try {
            KeystoreCertificateConfig keystoreCertificateConfig = keystoreApi.createKeystoreCertificate();
            return Response
                    .ok(gson.toJson(keystoreCertificateConfig))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    String.format("Could not delete certificate, error: %s", e.getMessage())).build();
        }
    }

    @GET
    @Path("get-keystore-config")
    public Response getKeyStoreConfig() {
        try {
            List<KeystoreCertificateConfig> configList =
                    keystoreApi.getKeystoreConfigRegistry().getKeystoreCertificatesConfig();
            return Response
                    .ok(gson.toJson(configList))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    String.format("Server certificate does not exist, error: %s", e.getMessage())).build();
        }
    }

    private Response sendRequest(Request request) {
        try {
            ocppServerService.sendToAll(request);
            return Response.ok().build();
        } catch (NotConnectedException | OccurenceConstraintException | UnsupportedFeatureException e) {
            LOGGER.error("Could not send request", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }
}
