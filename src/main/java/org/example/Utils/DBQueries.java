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
    public static final String overDue = "select * from CI_OD_PROC_LOG where TRUNC(LOG_DTTM) > = '30-MAR-26' ORDER BY LOG_DTTM";
    public static final String pdvCase = "SELECT c.CASE_ID,(select ch.log_dttm from ci_case_log ch where ch.case_id =C.CASE_ID and ch.CASE_LOG_TYPE_FLG='CASC') as PDV_CASE_CREAT_DTTM, "
            + "c.CASE_STATUS_CD,c.PER_ID,c.ACCT_ID,c.CASE_TYPE_CD,c.CASE_COND_FLG "
            + "FROM CI_CASE c WHERE c.CASE_TYPE_CD = 'CM-PDV' "
            + "AND c.CASE_STATUS_CD IN ('COLL AGY REF','WTNG AGC RSP','RESP RECV','PERP FOR WRN','SENT FOR WRN')  "
            + "AND c.CASE_COND_FLG ='OPEN'";
    public static final String HocxFile = "select * from CISADM.CI_PEVT_DTL_ST where EXT_SOURCE_ID ='HOCX' and PEVT_STG_ST_FLG = '10' ";
    public static final String REMPFile = "select * from CISADM.CI_PEVT_DTL_ST where EXT_SOURCE_ID ='REMP' and PEVT_STG_ST_FLG = '10' ";
    public static final String paymentEvent = "SELECT * from CI_PAY_EVENT";
    public static final String ommIsolate = "UPDATE CM_OUT_MKTMSG SET BO_STATUS_CD='PENDINGX' where BO_STATUS_CD='PENDING' and BUS_OBJ_CD='CM-D0055Registration' "
            + "and CM_MKTMSG_ID not in (select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))) "
            + "and BUS_OBJ_CD='CM-D0055Registration' ";
    //COT P2
    public static final String marketMessage1 = "SELECT * FROM CISADM.CM_OUT_MKTMSG WHERE CM_MKTMSG_ID IN ( SELECT CM_MKTMSG_ID FROM CISADM.CM_OUT_MKTMSG_CHAR WHERE CHAR_VAL_FK1= '%s')";
    public static final String ServiceAgreement = "select * from CI_SA WHERE ACCT_ID IN ( SELECT ACCT_ID FROM CM_OUT_MKTMSG WHERE CM_MKTMSG_ID IN (SELECT CM_MKTMSG_ID FROM CM_OUT_MKTMSG_CHAR WHERE CHAR_VAL_FK1 IN ('%s'))) AND  SA_STATUS_FLG= '10'";
    public static final String ommDeIsolate = "UPDATE CM_OUT_MKTMSG SET BO_STATUS_CD='PENDING' where BO_STATUS_CD='PENDINGX' and BUS_OBJ_CD='CM-D0055Registration' "
            + "and CM_MKTMSG_ID not in (select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))) "
            + "and BUS_OBJ_CD='CM-D0055Registration' ";


    public static final String newSACheckQuery = "select * from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG  "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))) "
            + "and BUS_OBJ_CD='CM-D0055Registration') and SA_STATUS_FLG='10' ";

    public static final String saIsolatePendingStart = "update CISADM.CI_SA set sa_status_flg ='11' where sa_status_flg='10' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) " ;

    public static final String saIsolatePendingStop = "update CISADM.CI_SA set sa_status_flg ='31' where sa_status_flg='30' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) " ;

    public static final String saDeIsolatePendingStart = "update CISADM.CI_SA set sa_status_flg ='10' where sa_status_flg='11' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) " ;

    public static final String saDeIsolatePendingStop = "update CISADM.CI_SA set sa_status_flg ='30' where sa_status_flg='31' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) " ;
}
