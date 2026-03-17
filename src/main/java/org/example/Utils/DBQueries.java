package org.example.Utils;

public class DBQueries {
    public static final String OppCreation = "select * from CISADM.CM_OPPORTUNITY where CM_OPPORTUNITY_ID = '%s' ";
    public static final String StagingData = "select * from CISADM.CM_UP_OPP_STAG where CM_FILE_NAME like '%Data_S7_RT.csv%'";
    public static final String UpdateStagingData = "update CISADM.CM_UP_OPP_STAG set CM_STATUS='PENDINGX' where CM_STATUS='PENDING' and CM_FILE_NAME not like '%Data_S7_RT.csv%'";
    public static final String IsolateOpportunity = "update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUALIFIEDX' where BO_STATUS_CD='QUALIFIED' and CM_OPPORTUNITY_ID != '%s' ";
    public static final String DeisolateOpportunity = "update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUALIFIED' where BO_STATUS_CD='QUALIFIEDX' and CM_OPPORTUNITY_ID != '%s' ";

    //Late Payment
    public static final String ODPROCID = "select * from CISADM.CI_OD_PROC where od_status_flg='10' and cre_dttm <= '%s' ORDER by cre_dttm desc";
    public static final String OPP_FOR_PER_AT_SP = "select * from CISADM.CM_OPP_FOR_PER_AT_SP where CM_OPP_FOR_PER_ID IN  \n" +
            "\n" +
            "(select CM_OPP_FOR_PER_ID from CM_OPP_FOR_PER where CM_OPPORTUNITY_ID IN (select CM_OPPORTUNITY_ID from CM_OPPORTUNITY where CM_FRAMEWORK_ID='%s'))";

    public static final String syncReq = "SELECT * FROM CISADM.F1_SYNC_REQ WHERE PK_VALUE1='%s' and BUS_OBJ_CD='CM-QuoteResponseSync'";
    public static final String IsolateQuote ="update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUOTEX' where BO_STATUS_CD='QUOTE' and CM_OPPORTUNITY_ID != '%s' ";
    public static final String DeIsolateQuote ="update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUOTE' where BO_STATUS_CD='QUOTEX' and CM_OPPORTUNITY_ID != '%s' ";

    public static final String fetchContract ="select * from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID = '%s' ";

    public static final String marketMessage = "select * from CISADM.CM_OUT_MKTMSG where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))) ";

    public static final String InMktMessage1 = "select * from CM_IN_MKTMSG A , CISADM.CM_IN_MKTMSG_CHAR B where A.CM_MKTID IN "
            + "(select CM_MKTID from CISADM.CM_OUT_MKTMSG where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in ('%s'))) and A.BUS_OBJ_CD='CM-SCOMCInboundMarketMessage' "
            + "and A.CM_MKTMSG_ID=B.CM_MKTMSG_ID and B.CHAR_VAL='1' ";

    public static final String InMktMessage2 = "select * from CM_IN_MKTMSG A , CISADM.CM_IN_MKTMSG_CHAR B where A.CM_MKTID IN "
            + "(select CM_MKTID from CISADM.CM_OUT_MKTMSG where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in ('%s'))) and A.BUS_OBJ_CD='CM-SCOMCInboundMarketMessage' "
            + "and A.CM_MKTMSG_ID=B.CM_MKTMSG_ID and B.CHAR_VAL='2' ";

    public static final String InMktMessage4 = "select * from CM_IN_MKTMSG A , CISADM.CM_IN_MKTMSG_CHAR B where A.CM_MKTID IN "
            + "(select CM_MKTID from CISADM.CM_OUT_MKTMSG where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in ('%s'))) and A.BUS_OBJ_CD='CM-SCOMCInboundMarketMessage' "
            + "and A.CM_MKTMSG_ID=B.CM_MKTMSG_ID and B.CHAR_VAL='4' ";

    public static final String SACheckQuery = "select * from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG  "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))";
    public static final String billStop = "select * from ci_acct where acct_id in ('%s')";
    public static final String billStop1 = "select * from CI_ACCT_CHAR where CHAR_TYPE_CD in ('CM-BSTOP') and ACCT_ID in ('%s')";

}
