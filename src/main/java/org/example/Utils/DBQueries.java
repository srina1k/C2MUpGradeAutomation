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
    public static final String IsolateQuote = "update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUOTEX' where BO_STATUS_CD='QUOTE' and CM_OPPORTUNITY_ID != '%s' ";
    public static final String DeIsolateQuote = "update CISADM.CM_OPPORTUNITY set BO_STATUS_CD='QUOTE' where BO_STATUS_CD='QUOTEX' and CM_OPPORTUNITY_ID != '%s' ";

    public static final String fetchContract = "select * from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID = '%s' ";

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
    public static final String pdvCaseCount="SELECT COUNT(*) FROM CI_CASE c WHERE c.CASE_TYPE_CD = 'CM-PDV' AND c.CASE_STATUS_CD IN ('COLL AGY REF','WTNG AGC RSP','RESP RECV','PERP FOR WRN','SENT FOR WRN')\n" +
            "AND c.CASE_COND_FLG = 'OPEN'";
    public static final String pdvlgcount="SELECT COUNT(*) AS RECORD_COUNT\n" +
            "FROM CI_CASE c\n" +
            "WHERE c.CASE_TYPE_CD = 'CM-PDV'\n" +
            "  AND c.CASE_STATUS_CD IN (\n" +
            "      'COLL AGY REF',\n" +
            "      'WTNG AGC RSP',\n" +
            "      'FLWP AGT RSP',\n" +
            "      'RESP RECV',\n" +
            "      'CGH OF TENCY',\n" +
            "      'PYMT RCVD'\n" +
            "  )";
    public static final String wrtlgcount="SELECT COUNT(*) AS RECORD_COUNT\n" +
            "FROM   CI_CASE CC\n" +
            "       INNER JOIN CI_ACCT_PER AP\n" +
            "               ON CC.ACCT_ID = AP.ACCT_ID\n" +
            "       INNER JOIN CI_PER_NAME PN\n" +
            "               ON AP.PER_ID = PN.PER_ID\n" +
            "       INNER JOIN CI_ACCT_CHAR AC\n" +
            "               ON CC.ACCT_ID = AC.ACCT_ID\n" +
            "       INNER JOIN CI_PREM P\n" +
            "               ON AC.CHAR_VAL_FK1 = P.PREM_ID\n" +
            "WHERE  CC.CASE_STATUS_CD IN ('CGH OF TENCY',\n" +
            "                             'PERP FOR WRN',\n" +
            "                             'SENT FOR WRN',\n" +
            "                             'INT DE-ENRG',\n" +
            "                             'EXT DE-ENRG',\n" +
            "                             'DE-ENRG INP',\n" +
            "                             'PYMT RCVD')\n" +
            "AND    AP.MAIN_CUST_SW = 'Y'\n" +
            "AND    CC.CASE_TYPE_CD = 'CM-PDV'\n" +
            "AND    CC.CASE_COND_FLG = 'OPEN'\n";
    public static final String pdvCase = "SELECT c.CASE_ID,(select ch.log_dttm from ci_case_log ch where ch.case_id =C.CASE_ID and ch.CASE_LOG_TYPE_FLG='CASC') as PDV_CASE_CREAT_DTTM, "
            + "c.CASE_STATUS_CD,c.PER_ID,c.ACCT_ID,c.CASE_TYPE_CD,c.CASE_COND_FLG "
            + "FROM CI_CASE c WHERE c.CASE_TYPE_CD = 'CM-PDV' "
            + "AND c.CASE_STATUS_CD IN ('COLL AGY REF','WTNG AGC RSP','RESP RECV','PERP FOR WRN','SENT FOR WRN')  "
            + "AND c.CASE_COND_FLG ='OPEN'";
    public static final String tenderControl="select * from CISADM.ci_tndr_ctl WHERE Tndr_Ctl_id in ('%s')";
    public static final String HocxFile = "select * from CISADM.CI_PEVT_DTL_ST where EXT_SOURCE_ID ='HOCX' and PEVT_STG_ST_FLG = '10' ";
    public static final String PayEventStatus="select * from CISADM.CI_PEVT_DTL_ST where EXT_SOURCE_ID ='HOCX' and PEVT_STG_ST_FLG in ('%s') and EXT_TRANSMIT_ID in ('%s')";
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
    public static final String marketMessage2 = "SELECT * FROM CISADM.CM_OUT_MKTMSG WHERE CM_MKTMSG_ID IN (SELECT CM_MKTMSG_ID FROM CISADM.CM_OUT_MKTMSG_CHAR WHERE CHAR_VAL_FK1 IN (SELECT CM_CONTRACT_ID FROM CISADM.CM_CONTRACT WHERE CM_OPPORTUNITY_ID IN ('%s'))) AND BUS_OBJ_CD = 'CM-D0055Registration'";
    public static final String newSACheckQuery = "select * from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG  "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))) "
            + "and BUS_OBJ_CD='CM-D0055Registration') and SA_STATUS_FLG='10' ";

    public static final String saIsolatePendingStart = "update CISADM.CI_SA set sa_status_flg ='11' where sa_status_flg='10' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) ";

    public static final String saIsolatePendingStop = "update CISADM.CI_SA set sa_status_flg ='31' where sa_status_flg='30' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) ";

    public static final String saDeIsolatePendingStart = "update CISADM.CI_SA set sa_status_flg ='10' where sa_status_flg='11' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) ";

    public static final String saDeIsolatePendingStop = "update CISADM.CI_SA set sa_status_flg ='30' where sa_status_flg='31' "
            + "and SA_ID not in (select SA_ID from CISADM.CI_SA WHERE acct_id in (select ACCT_ID from CISADM.CM_OUT_MKTMSG "
            + "where CM_MKTMSG_ID in(select CM_MKTMSG_ID from CISADM.CM_OUT_MKTMSG_CHAR "
            + "where CHAR_VAL_FK1 in (select CM_CONTRACT_ID from CISADM.CM_CONTRACT where CM_OPPORTUNITY_ID in ('%s'))))) ";
    public static final String isolateOpp = "select * from CISADM.CM_OPPORTUNITY where CM_OPPORTUNITY_ID in ('%s') ";
    public static final String GasServicePoint = "select * from CISADM.CI_SP_GEO where geo_val in ('%s')";
    public static final String GasSupplyQuantity = "select * from ci_sp_char where char_type_cd='CMANUQTY' and sp_id in ('%s')";
    public static final String supplypoint = "select * from ci_sp_char where char_type_cd='CM-SLAPI' and sp_id in ('%s')";
    public static final String updateQuantity = "UPDATE ci_sp_char SET ADHOC_CHAR_VAL = '73150' WHERE char_type_cd = 'CMANUQTY' AND sp_id in ('%s')";
    public static final String DeleteANUQuantity = "Delete from ci_sp_char where char_type_cd='CMANUQTY' and sp_id in ('%s')";
    public static final String DeleteSupplyIndicator = "Delete from ci_sp_char where char_type_cd='CM-SLAPI' and sp_id in ('%s')";
    public static final String InsertQTY = "INSERT INTO ci_sp_char (sp_id, char_type_cd, adhoc_char_val, effdt)" +
            "VALUES ('%s', '%s', '%s', SYSDATE);";
    public static final String InsertClass = "INSERT INTO ci_sp_char (sp_id,char_type_cd,char_val,effdt) VALUES ('%s','%s','%s',SYSDATE)";
    public static final String IsolateContract = "update cm_contract set bo_status_cd='INPROGRESSX' where bo_status_cd='INPROGRESS'  cm_contract_id NOT in ('%s’)";
    public static final String DeIsolateContract = "update cm_contract set bo_status_cd='INPROGRESS' where bo_status_cd='INPROGRESSX'  cm_contract_id NOT in ('%s’)";
    public static final String getSyncReq = "select * from f1_sync_req where BUS_OBJ_CD='CM-QuoteResponseAthena' and BO_STATUS_CD='PENDING' and PK_VALUE1 in ('%s')";
    public static final String getcontractid = "select * from cm_contract where BO_STATUS_CD='INPROGRESS' AND cm_ext_ref_num in ('%s')";
    public static final String CCL_DCL_ID = "select * from ci_dcl where acct_id in ('%s') AND DCL_TYPE_FLG='CCL'";
    public static final String VAT_DCL_ID = "select * from ci_dcl where acct_id in ('%s') AND DCL_TYPE_FLG='VAT'";
    public static final String getservicePointCharacteristics = "select * from ci_sp_char a where a.sp_id in (\n" +
            "SELECT sp_id from ci_sp_geo where geo_val in ('%s') and a.effdt=(SELECT MAX(d.effdt) FROM ci_sp_char d WHERE a.sp_id = d.sp_id AND a.char_type_cd = d.char_type_cd))";
    public static final String checkCancelRebillCount = "select * from ci_case where case_type_cd='CM-CNCL-RBLL' AND ACCT_ID IN ('%s','%s')";
    public static final String IsolateCase = "update CI_CASE SET CASE_STATUS_CD ='MONITORX' WHERE ACCT_ID not IN ('%s','%s') and CASE_STATUS_CD='MONITOR'";
    public static final String DeIsolateCase = "update CI_CASE SET CASE_STATUS_CD ='MONITOR' WHERE ACCT_ID not IN ('%s','%s') and CASE_STATUS_CD='MONITORX'";
    public static final String fetchbill = "SELECT  BILL_ID ,ACCT_ID , ALT_BILL_ID FROM CI_BILL WHERE ACCT_ID IN ('%s','%s')";
    public static final String fetchPostRout = "SELECT NEXT_BATCH_NBR FROM CI_BATCH_CTRL WHERE BATCH_CD = 'POSTROUT'";
    public static final String checkBills = "SELECT * FROM CI_BILL_ROUTING WHERE BATCH_CD = 'POSTROUT' AND BATCH_NBR = '%s' AND BILL_ID IN (SELECT BILL_ID FROM CI_BILL WHERE ACCT_ID IN ('%s','%s'))";
    public static final String identifyUsage = "SELECT /*+parallel(U,80)*/ * " + "FROM D1_USAGE U, D1_USAGE_CHAR UC, D1_US_SP USP, D1_US US, D1_SP_IDENTIFIER SPI " +
            "WHERE U.BO_STATUS_CD IN ('SENT', 'SUB-CORRECT') " + "AND U.D1_USAGE_ID = UC.D1_USAGE_ID " + "AND UC.CHAR_TYPE_CD = 'DM_S2QM' " +
            "AND UC.CHAR_VAL = 'NO' " + "AND U.END_DTTM > ( " + " SELECT ADD_MONTHS(MAX(U2.END_DTTM), -12) " + " FROM D1_USAGE U2, D1_USAGE_CHAR UC2 " +
            " WHERE U2.BO_STATUS_CD IN ('SENT', 'SUB-CORRECT') " + " AND U2.D1_USAGE_ID = UC2.D1_USAGE_ID " + " AND UC2.CHAR_TYPE_CD = 'DM_S2QM' " +
            " AND UC2.CHAR_VAL = 'NO' " + " AND U2.US_ID = U.US_ID " + " AND U2.END_DTTM <= TO_DATE('%s','DD/MON/YY') " + ") " +
            "AND U.END_DTTM <= TO_DATE('%s','DD/MON/YY') " + "AND U.D1_USAGE_ID IN ( " + " SELECT UP.D1_USAGE_ID " +
            "FROM D1_USAGE_PERIOD UP, D1_USAGE_PERIOD_SQ UPS " + " WHERE ( " + " (UP.START_DTTM <= U.START_DTTM AND UP.END_DTTM > U.START_DTTM) " +
            "OR (UP.START_DTTM >= U.START_DTTM AND UP.END_DTTM <= U.END_DTTM) " + " OR (UP.START_DTTM < U.END_DTTM AND UP.END_DTTM >= U.END_DTTM) " + " ) " + " AND UP.D1_USAGE_ID = UPS.D1_USAGE_ID " +
            "AND UP.PERIOD_SEQ_NUM = UPS.PERIOD_SEQ_NUM " + " AND UPS.D1_SQI_CD = 'DMCONSUMAI' " + " AND UPS.D1_UOM_CD = 'KWH' " + ") " +
            "AND U.US_ID = US.US_ID " + "AND US.US_TYPE_CD = 'DM_BILLDET' " + "AND US.US_ID = USP.US_ID " + "AND USP.D1_SP_ID = SPI.D1_SP_ID " +
            "AND SPI.SP_ID_TYPE_FLG = 'DMMI' " + "AND SPI.ID_VALUE NOT LIKE '99%%'";
    public static final String currentUsage="SELECT DISTINCT A.BO_STATUS_CD, B.CHAR_VAL, COUNT(B.CHAR_VAL) FROM D1_USAGE A, D1_USAGE_CHAR B WHERE A.D1_USAGE_ID = B.D1_USAGE_ID \n" +
            "AND B.CHAR_VAL IN('NO', 'YNO', 'YES','DUP') " + "AND A.BO_STATUS_CD IN('SENT', 'SUB-CORRECT') \n" +
            "GROUP BY A.BO_STATUS_CD, B.CHAR_VAL ORDER BY A.BO_STATUS_CD";
    public static final String f1outMsg="select count(*) from f1_outmsg where NT_XID_CD='DM-QMHH' order by cre_dttm desc";
    public static final String f1outMsgdate="select * from f1_outmsg where nt_xid_cd='DM-QMHH' AND TRUNC(Cre_Dttm)=('%s')";
    public static final String MarketMessageIdFromOpportunity="select a.message_parm from cm_opp_log_parm a, cm_opp_log b where \n" +
            "a.cm_opportunity_id=('%s') and a.cm_opportunity_id=b.cm_opportunity_id and b.message_cat_nbr='90002' and b.message_nbr='82004'\n" +
            "and a.seqno=b.seqno and a.parm_seq='1'";
    public static final String PEVTSTGSTFLG="select * from CISADM.CI_PEVT_DTL_ST where EXT_SOURCE_ID ='HOCX' AND EXT_TRANSMIT_ID=('%s')";
}
