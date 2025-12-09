package Database;

import DAOS.*;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to test for changes to the database.
 * It calls the structureCheck method of every DAO present in the list.
 * @author Nick Engell
 */
public class DatabaseStructureCheck {
    
    public IStructureCheckable[] daos;
    public String[] procedures;
    public String[] functions;
          
    private void initDAOList() {
        daos = new IStructureCheckable[]{
            // add new DAOs below here
            
            // already established daos
            new AddressAssociationDAO(),
            new AddressDAO(),
            new AddressLookupDAO(),
            new AddressTypeDAO(),
            new AdjustmentApplicationMethodsDAO(),
            new AdjustmentCategoryDAO(),
            new AdjustmentEventDAO(),
            new AdjustmentOrderDAO(),
            new AdjustmentsCSSSchemaDAO(),
            new AdjustmentsDAO(),
            new AdjustmentTypeDAO(),
            new AdvancedDiagnosisCodeDAO(),
            new AdvancedOrderDAO(),
            new AdvancedOrderLogDAO(),
            new AdvancedPrescriptionDAO(),
            new AdvancedResultDAO(),
            new AdvancedTodayDAO(),
            new AOEAnswersDAO(),
            new AOEChoiceDAO(),
            new AOEGroupingTypeDAO(),
            new AOEOrderStatusDAO(),
            new AOEQuestionDAO(),
            new AOETestsDAO(),
            new AssignedTestsDAO(),
            new AssignedWorksheetsDAO(),
            new AttachmentDataDAO(),
            new AttachmentLookupDAO(),
            new AttachmentMetadataDAO(),
            new AttachmentTestLookupDAO(),
            new AttachmentTransmissionLookupDAO(),
            new AttachmentTypeDAO(),
            new AutoOrderTestDAO(),
            new BatchesDAO(),
            new BatchOrdersDAO(),
            //new BillingLogDAO(),
            new BillingPayorDAO(),
            new BillingTestCrossReferenceDAO(),
            new BillTransLogDAO(),
            new CalculationsDAO(),
            new CarbonCopiesDAO(),
            new ChartsDAO(),
            new ChartTypesDAO(),
            new ChartUserLookupDAO(),
            new CityDAO(),
            new ClaimAdjustmentGroupCodesDAO(),
            new ClaimAdjustmentReasonCodesDAO(),
            new ClientBillingAddressLookupDAO(),
            new ClientCopyDAO(),
            new ClientCounselorRelationshipDAO(),
            new ClientCustomRangesDAO(),
            new ClientCustomReflexRulesDAO(),
            new ClientDAO(),
            new ClientDoctorRelationshipDAO(),
            new ClientPropertyDAO(),
            new ClientPropertyLookupDAO(),
            //new ClientXrefDAO(),
            new CommentDAO(),
            new CompletedCallsDAO(),
            new CounselorDAO(),
            new CountryDAO(),
            new CptCodeDAO(),
            new CptDAO(),
            new CptLookupDAO(),
            new CptModifierDAO(),
            new CptModifierLookupDAO(),
            // new CptModifierResultLookupDAO(),
            new CustomRequestDAO(),
            new DepartmentDAO(),
            new DetailCptCodeCommentsDAO(),
            new DetailCptCodeDAO(),
            new DetailCptCodeLogDAO(),
            new DetailCptCommentLogDAO(),
            new DetailCptModifierDAO(),
            new DetailDiagnosisCodeDAO(),
            new DetailImportLogDAO(),
            new DetailInsuranceDAO(),
            new DetailInsuranceItemAdjustmentsDAO(),
            new DetailInsuranceItemsDAO(),
            new DetailInvoiceStatementLookupDAO(),
            new DetaillinesDAO(),
            new DetailOrderDAO(),
            new DetailOrderEventDAO(),
            new DetailOrderHCFADAO(),
            new DetailOrderStatementDetailsDAO(),
            new DetailOrderStatementsDAO(),
            new DiagnosisCodeDAO(),
            new DiagnosisValidityDAO(),
            new DiagnosisValidityLogDAO(),
            new DiagnosisValidityLookupDAO(),
            new DiagnosisValidityStatusDAO(),
            // new DiffLogDAO(),
            new DoctorDAO(),
            new DrugDAO(),
            new EligibilityErrorDAO(),
            new EligibilityRequestDAO(),
            new EligibilityResponseDAO(),
            new EmployeeDepartmentDAO(),
            new EmployeeDepartmentLocationDAO(),
            new EmployeeLocationDAO(),
            new EmployeesDAO(),
            new EmrOrderImportDAO(),
            new EmrOrderLogDAO(),
            new EmrReportDAO(),
            new EmrXrefDAO(),
            new EventDAO(),
            new EventTypeDAO(),
            // new ExampleDAO(),
            new ExtraNormalsDAO(),
            new ExtraNormalsLogDAO(),
            new FeeDAO(),
            new FeeScheduleActionDAO(),
            new FeeScheduleAssignmentDAO(),
            new FeeScheduleCptLookupDAO(),
            new FeeScheduleCustomRulesDAO(),
            new FeeScheduleDAO(),
            new FeeScheduleTestLookupDAO(),
            new GeneticReportDAO(),
            new GroupPoliciesDAO(),
            new InstOrdDAO(),
            new InstResDAO(),
            new InstrumentDAO(),
            new InstXRefDAO(),
            new InsuranceDAO(),
            new InsuranceRulesDAO(),
            new InsuranceSubmissionModeDAO(),
            new InsuranceSubmissionTypeDAO(),
            new InsuranceTypeDAO(),
            new InsuranceXrefDAO(),
            //new InterpOperatorsDAO(),
            //new InterpRulesDAO(),
            //new InterpStepsDAO(),
            //new InterpValuesDAO(),
            //new InterpVariablesDAO(),
            new LabelFormDAO(),
            new LabMasterDAO(),
            new LedgerDAO(),
            new LocationDAO(),
            new LogDAO(), // special
            new LoginLogDAO(),
            new ManufacturerDAO(),
            new MetaboliteMatrixDAO(),
            new MicroAntibioticsDAO(),
            new MicroCommentDAO(),
            new MicroInstrumentCommentDAO(),
            new MicroOrdersDAO(),
            new MicroOrganismsDAO(),
            new MicroResultsDAO(),
            new MicroSitesDAO(),
            new MicroSourcesDAO(),
            new MicroSusceptibilityDAO(),
            new MixedAnswerDAO(),
            new MixedAnswerOptionDAO(),
            new MixedAnswerOptionSetDAO(),
            new MixedAnswerResultDAO(),
            new MixedAnswerTestDAO(),
            new MultichoiceDAO(),
            new OrderCommentDAO(),
            new OrderCptCodeDAO(),
            new OrderCptDiagnosisCodeDAO(),
            new OrderCptModifierDAO(),
            new OrderCptModifierLogDAO(),
            new OrderDAO(),
            new OrderDiagnosisLookupDAO(),
            new OrderedTestDAO(),
            new OrderUpdateQueueDAO(),
            new PanelDAO(),
            new PatientCommentDAO(),
            new PatientDAO(),
            new PatientFaceSheetDAO(),
            new PatientSMSDAO(),
            new PaymentDetailsDAO(),
            new PaymentMethodDAO(),
            new PaymentMethodDetailsDAO(),
            new PaymentMethodFieldsDAO(),
            new PaymentsDAO(),
            new PaymentTypeLookupDAO(),
            new PendingAdvancedOrderDAO(),
            new PendingCallsDAO(),
            new PhlebotomyDAO(),
            new PhlebotomyRedrawReasonDAO(),
            new PlaceOfServiceDAO(),
            new PPSBillingLookupDAO(),
            new PreferenceKeysDAO(),
            new PreferencesDAO(),
            new PreferencesLogDAO(),
            new PreferenceValueMapDAO(),
            new PreferenceValuesDAO(),
            new PrescriptionDAO(),
            new PrintTransmitLogDAO(),
            new ProcedureCodeDAO(),
            new QcControlValuesDAO(),
            new QcCrossRefDAO(),
            new QcInstControlDAO(),
            new QCInstDAO(),
            new QcInstrumentInfoDAO(),
            new QcLotDAO(),
            new QcResultsDAO(),
            new QcResultViolationDAO(),
            new QcRulesDAO(),
            new QcStepsDAO(),
            new QcTestsDAO(),
            new ReferenceLabApprovalLogDAO(),
            new ReferenceLabReportDAO(),
            new ReferenceLabSettingDAO(),
            new RefFileDAO(),
            new RefFlagDAO(),
            new ReflexMultichoiceDAO(),
            new ReflexMultichoiceLogDAO(),
            new ReflexRulesDAO(),
            new RefResDAO(),
            new RefResultDAO(),
            new RefTestDAO(),
            new RegionDAO(),
            new ReleaseOrdersDAO(),
            new ReleaseResultsDAO(),
            new RemarkCategoryDAO(),
            new RemarkDAO(),
            new RemarkTypeDAO(),
            new RemitDetailDAO(),
            new RemitInfoDAO(),
            new RemitInfoLogDAO(),
            new RemittanceAdviceRemarkCodesDAO(),
            new ReportColumnFormatDAO(),
            new ReportedDateDAO(),
            new ReportHeaderDAO(),
            new ReportTypeDAO(),
            new RequisitionDAO(),
            new RescheduleDAO(),
            new ResultDAO(),
            new ResultDetailsDAO(),
            new RetransmitOrdersDAO(),
            new RoutesDAO(),
            //new SalesDAO(),
            new SalesGroupDAO(),
            new SalesGroupLookupDAO(),
            new SalesmenDAO(),
            new ScheduleTypeDAO(),
            new SecGroupAccessDAO(),
            new SequenceDAO(),
            new ServiceTypeDAO(),
            // new SettingDAO(),
            // new SettingLookupDAO(),
            // new SettingTypeDAO(),
            // new SettingValueTypeDAO(),
            new SpecialPriceTestDAO(),
            new SpecialPriceTestLogDAO(),
            new SpeciesDAO(),
            new SpecimenTypeDAO(),
            new StateDAO(),
            new SubGroupLookupDAO(),
            new SubmissionBatchDAO(),
            new SubmissionEventDAO(),
            new SubmissionQueueDAO(),
            new SubmissionStatusDAO(),
            new SubscriberCommentDAO(),
            new SubscriberDAO(),
            new SubstanceDAO(),
            // new SysLogDAO(),
            new SysOpDAO(),
            new TerritoryDAO(),
            new TestcategoryDAO(),
            // new TestCatMapDAO(),
            new TestDAO(),
            new TestLogDAO(),
            new TestRangeTypeDAO(),
            new TestRemarkRangeDAO(),
            new TestXrefDAO(),
            new TransactionImportLogDAO(),
            new TranslationalQueueDAO(),
            new TranslationalXrefDAO(),
            new TransLogDAO(),
            new TransmissionTypeDAO(),
            new TransTypeDAO(),
            new TransTypeLookupDAO(),
            new TubeTypeDAO(),
            new UserDAO(),
            new UserGroupPreferencesDAO(),
            new UserGroupsDAO(),
            new WriteOffsDAO(),
            new XrefsDAO(),
            new ZipCodeDAO(),
                
            // billing daos
            //new Billing.DAOS.AccessionXrefDAO(),
            
            // emr daos
            new EMR.DAOS.EmrApprovalLogDAO(),
            new EMR.DAOS.EmrEventLogDAO(),
            new EMR.DAOS.EmrEventTypeDAO(),
            new EMR.DAOS.EmrFileDAO(),
            new EMR.DAOS.EmrFileLogDAO(),
            new EMR.DAOS.EmrIssueDAO(),
            new EMR.DAOS.EmrIssueTypeDAO(),
            new EMR.DAOS.EmrOrdDiagDAO(),
            new EMR.DAOS.ExtraFieldsDAO(),
            new EMR.DAOS.MissingPrescriptionDAO(),
            new EMR.DAOS.OrderCommentDAO(),
            new EMR.DAOS.OrderDAO(),
            new EMR.DAOS.PatientDAO(),
            new EMR.DAOS.PrescriptionDAO(),
            new EMR.DAOS.ResultDAO(),
            new EMR.DAOS.SubscriberDAO(),
            
            // web daos
            new Web.DAOS.OrderCommentDAO(),
            new Web.DAOS.OrderDAO(),
            new Web.DAOS.OrderDiagnosisLookupDAO(),
            new Web.DAOS.PatientDAO(),
            new Web.DAOS.PrescriptionDAO(),
            new Web.DAOS.ResultDAO(),
            new Web.DAOS.SubscriberDAO(),
            new Web.DAOS.WebLogDAO(),
            new Web.DAOS.WebLogOrderEntryDAO(),
            new Web.DAOS.WebLogOrderReceiptLogDAO(),
            new Web.DAOS.WebLogTypesDAO(),
            new Web.DAOS.WebOrderEntryLogTypeDAO(),
            new Web.DAOS.WebUserDAO(),
            new Web.DAOS.WebUserTypeDAO()
        };
    }
    
    public void initProcedures() {
        this.procedures = new String[]{
            // place stored procedure names below here in the format schema.procedureName
            "css.GenerateAdvancedOrder",
            "css.PhlebotomyMonthlyDraws",
            "css.GetOrderTestContexts",
            "css.CopyFeeSchedule",
            "css.GetLabelData",
            "css.GetCustomLabelData",
            "css.PurgeOrder",
            "css.ReferenceLabInvalidateResults",
            "css.UpdatePanelHeaders",
            "css.UpdateBatteryHeaders",
            "css.UpdateHeaders",
            "css.CustomCalc",
            //"css.CopyReferenceLabCommentToResult",
            "css.BillingIntelligenceReport",
            "css.ReferenceLabLogSearch",
            "css.ReferenceLabResultSearch",
            "css.GetQcResultsForDateRange" // qc test report query
        };
    }
    
    public void initFunctions() {
        this.functions = new String[] {
            // place function names below here in the format schema.functionName
            "css.F_DOB_BETWEEN",
            "css.F_DATE_RANGES_OVERLAP",
            "css.F_CALC_AGE"
        };
    }

    public List<String> run() {
        if(daos == null){
            initDAOList();
        }
        List<String> fails = new ArrayList<>();
        for(IStructureCheckable dao : daos){
            String errorMsg = dao.structureCheck();
            if(errorMsg != null) {
                fails.add(errorMsg);
            }
        }
        fails.addAll(checkProcedures());
        fails.addAll(checkFunctions());
        
        this.daos = null;
        this.procedures = null;
        this.functions = null;
        return fails;
    }
    
    private List<String> checkProcedures() {
        if (functions == null) {
            initProcedures();
        }
        return checkList("procedure", procedures);
    }
    
    private List<String> checkFunctions() {
        if(functions == null) {
            initFunctions();
        }
        return checkList("function", functions);
    }
    
    private List<String> checkList(String type, String[] list) {
        List<String> fails = new ArrayList<>();
        for (String entry : list) {
            String[] pair = entry.split("\\.");
            if (pair.length != 2) {
                fails.add(entry + ": " + StringUtils.capitalize(type) + " " + entry + " not registered correctly");
            }
            else if (!showStatus(type.toUpperCase(), pair[0], pair[1])) {
                fails.add(entry + ": " + StringUtils.capitalize(type) + " " + entry + " doesn't exist");
            }
        }
        return fails;
    }
    
    private boolean showStatus(String type, String schema, String name) {
        try {
            Connection con = Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true);
            Statement stmt = con.createStatement();
            String query = "SHOW " + type + " STATUS WHERE `db` = '" + schema + "' and `name` = '" + name + "'";
            System.out.println("DatabaseStructureCheck::showStatus query: " + query);
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Runs a select all limit 1 query.
     * @param fields A list of fields the table contains.
     * @param table The table name.
     * @param con The connection to run the query on.
     * @return An error message stating what went wrong when running the query, or null if nothing went wrong.
     */
    public static String structureCheck(List<String> fields, String table, Connection con) {
        String query = "SELECT";
        for (int i = 0; i < fields.size(); ++i) {
            query += " `" + fields.get(i) + "`";
            if (i != fields.size() - 1) {
                query += ", ";
            }
        }
        query += " FROM " + table + " LIMIT 1";
        return structureCheck(query, table, con);
    }

    /**
     * Runs a select all limit 1 query.
     * @param query The query string to run.
     * @param table The table name.
     * @param con The connection to run the query on.
     * @return An error message stating what went wrong when running the query, or null if nothing went wrong.
     */
    public static String structureCheck(String query, String table, Connection con) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return null;
        } catch (Exception e) {
            System.out.println("Structure check for table " + table + " has failed.");
            System.out.println(e.toString());
            e.printStackTrace();
            return table + ": " + e.getMessage();
        }
    }
        
}
