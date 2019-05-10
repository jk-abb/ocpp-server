package com.omb.ocpp.groovy

import com.omb.ocpp.gui.Application
import com.omb.ocpp.server.OcppServerService
import eu.chargetime.ocpp.JSONCommunicator
import eu.chargetime.ocpp.model.SessionInformation
import eu.chargetime.ocpp.model.core.StopTransactionConfirmation
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsConfirmation
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.Instant

class GetDiagnosticsConfirmationSupplier implements ConfirmationSupplier<GetDiagnosticsRequest,
        GetDiagnosticsConfirmation> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetDiagnosticsConfirmationSupplier.class)
    private static final Instant CLASS_LOAD_DATE = Instant.now()
    private static final JSONCommunicator jsonCommunicator = new JSONCommunicator(null)
    private final OcppServerService ocppServerService = Application.APPLICATION.getService(OcppServerService.class)

    @Override
    GetDiagnosticsConfirmation getConfirmation(UUID sessionUuid, GetDiagnosticsRequest request) {
        SessionInformation unknownSession = new SessionInformation.Builder().Identifier("unknown").build()
        SessionInformation sessionInformation = ocppServerService.getSessionInformation(sessionUuid).orElse(unknownSession)

        GetDiagnosticsConfirmation confirmation = new StopTransactionConfirmation()

        LOGGER.debug("Responding to {} from client: {} body: {}", request.getClass().simpleName, sessionInformation
                .identifier, jsonCommunicator.packPayload(confirmation))
        return confirmation
    }

    @Override
    Instant getClassLoadDate(){
        return CLASS_LOAD_DATE;
    }
}
