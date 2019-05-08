package com.omb.ocpp.server.handler;

import com.omb.ocpp.groovy.GroovyService;
import com.omb.ocpp.gui.ApplicationContext;
import eu.chargetime.ocpp.JSONCommunicator;
import eu.chargetime.ocpp.feature.profile.ClientFirmwareManagementEventHandler;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsConfirmation;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest;
import eu.chargetime.ocpp.model.firmware.UpdateFirmwareConfirmation;
import eu.chargetime.ocpp.model.firmware.UpdateFirmwareRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmwareManagementEventHandler implements ClientFirmwareManagementEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirmwareManagementEventHandler.class);
    private final JSONCommunicator jsonCommunicator = new JSONCommunicator(null);
    private final GroovyService groovyService = ApplicationContext.INSTANCE.getGroovyService();

    @Override
    public GetDiagnosticsConfirmation handleGetDiagnosticsRequest(GetDiagnosticsRequest request) {
        LOGGER.debug(request.getClass().getSimpleName() + " - " + jsonCommunicator.packPayload(request));
        return groovyService.getConfirmation(null, request);
    }

    @Override
    public UpdateFirmwareConfirmation handleUpdateFirmwareRequest(UpdateFirmwareRequest request) {
        LOGGER.debug(request.getClass().getSimpleName() + " - " + jsonCommunicator.packPayload(request));
        return groovyService.getConfirmation(null, request);
    }
}
