package org.example.Utils;

public class xaiUtils {
    public static String SComcFlow(String comcFlow, String MPAN){
        return
                "<CmSendChangeofMeasurementClass> \r\n"
                        + "<marketIdentifier>"+MPAN+"</marketIdentifier> \r\n"
                        + "<transactionDetails> \r\n"
                        + "<changeOfMeasurementClassStatus>"+comcFlow+"</changeOfMeasurementClassStatus> \r\n"
                        + "<date>2023-04-14</date> \r\n"
                        + "<rejectionOfAgentAppointmentCode> </rejectionOfAgentAppointmentCode> \r\n"
                        + "<siteVisitCheckCode> </siteVisitCheckCode> \r\n"
                        + "<additionalInformation> </additionalInformation> \r\n"
                        + "<effectiveFromSettlementDateMSMTD> </effectiveFromSettlementDateMSMTD> \r\n"
                        + "</transactionDetails> \r\n"
                        + "</CmSendChangeofMeasurementClass>";
    }
}
