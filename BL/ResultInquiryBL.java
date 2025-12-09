package BL;

import Database.CheckDBConnection;
import Utility.MultiMap;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @date: May 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: BL
 * @file name: ResultInquiryBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class ResultInquiryBL
{

    public class DrugDetectItem
    {

        Integer idTests;
        Boolean isSubstance;
        Boolean isPrescribed;
        Integer relatedDrug;
        Integer substanceGroup;
        String substance;
        Integer substanceId;
        Integer metaboliteId;
        String resultText;
        Double resultNo;
        Boolean isHigh, isLow, isCIDHigh, isCIDLow;

        public DrugDetectItem(Integer idTests, Boolean isSubstance, Boolean isPrescribed, Integer relatedDrug, Integer substanceGroup, String substance, Integer substanceId, Integer metaboliteId, String resultText, Double resultNo, Boolean isHigh, Boolean isLow, Boolean isCIDHigh, Boolean isCIDLow)
        {
            this.idTests = idTests;
            this.isSubstance = isSubstance;
            this.isPrescribed = isPrescribed;
            this.relatedDrug = relatedDrug;
            this.substanceGroup = substanceGroup;
            this.substance = substance;
            this.substanceId = substanceId;
            this.metaboliteId = metaboliteId;
            this.resultText = resultText;
            this.resultNo = resultNo;
            this.isHigh = isHigh;
            this.isLow = isLow;
            this.isCIDHigh = isCIDHigh;
            this.isCIDLow = isCIDLow;
        }

        public Integer getIdTests()
        {
            return idTests;
        }

        public void setIdTests(Integer idTests)
        {
            this.idTests = idTests;
        }

        public Boolean isIsSubstance()
        {
            return isSubstance;
        }

        public void setIsSubstance(Boolean isSubstance)
        {
            this.isSubstance = isSubstance;
        }

        public Boolean isIsPrescribed()
        {
            return isPrescribed;
        }

        public void setIsPrescribed(Boolean isPrescribed)
        {
            this.isPrescribed = isPrescribed;
        }

        public Integer getRelatedDrug()
        {
            return relatedDrug;
        }

        public void setRelatedDrug(Integer relatedDrug)
        {
            this.relatedDrug = relatedDrug;
        }

        public Integer getSubstanceGroup()
        {
            return substanceGroup;
        }

        public void setSubstanceGroup(Integer substanceGroup)
        {
            this.substanceGroup = substanceGroup;
        }

        public String getSubstance()
        {
            return substance;
        }

        public void setSubstance(String substance)
        {
            this.substance = substance;
        }

        public Integer getSubstanceId()
        {
            return substanceId;
        }

        public void setSubstanceId(Integer substanceId)
        {
            this.substanceId = substanceId;
        }

        public Integer getMetaboliteId()
        {
            return metaboliteId;
        }

        public void setMetaboliteId(Integer metaboliteId)
        {
            this.metaboliteId = metaboliteId;
        }

        public String getResultText()
        {
            return resultText;
        }

        public void setResultText(String resultText)
        {
            this.resultText = resultText;
        }

        public Double getResultNo()
        {
            return resultNo;
        }

        public void setResultNo(Double resultNo)
        {
            this.resultNo = resultNo;
        }

        public Boolean isIsHigh()
        {
            return isHigh;
        }

        public void setIsHigh(Boolean isHigh)
        {
            this.isHigh = isHigh;
        }

        public Boolean isIsLow()
        {
            return isLow;
        }

        public void setIsLow(Boolean isLow)
        {
            this.isLow = isLow;
        }

        public Boolean isIsCIDHigh()
        {
            return isCIDHigh;
        }

        public void setIsCIDHigh(Boolean isCIDHigh)
        {
            this.isCIDHigh = isCIDHigh;
        }

        public Boolean isIsCIDLow()
        {
            return isCIDLow;
        }

        public void setIsCIDLow(Boolean isCIDLow)
        {
            this.isCIDLow = isCIDLow;
        }

    }

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public ResultInquiryBL()
    {

    }

    public MultiMap<Integer, DrugDetectItem> GetDrugDetection(Integer orderID) throws SQLException
    {
        MultiMap<Integer, DrugDetectItem> DetectionGroup = new MultiMap<>();

        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = con.createStatement();

            String query = "SELECT"
                    + " o.idOrders,"
                    + " t.idTests, t.name,"
                    + " if (t.relatedDrug = mm.substance, 1, 0) AS `IsSubstance`,"
                    + " if (pds.idSubstances = mm.substance OR pds.idSubstances = mm.metabolite, 1, 0) AS `IsPrescribed`,"
                    + " t.relatedDrug, pds.idSubstances AS `SubstanceGroup`, pds.substance,"
                    + " mm.substance AS `substanceId`, mm.metabolite AS `metaboliteId`,"
                    + " r.resultText, r.resultNo, r.isHigh, r.isLow, r.isCIDHigh, r.isCIDLow "
                    + " FROM orders o INNER JOIN results r ON o.idOrders = r.orderId"
                    + " INNER JOIN tests t"
                    + " ON t.idTests = r.testId"
                    + " LEFT JOIN panels p"
                    + " ON r.panelId = p.idpanels AND r.testId = p.subtestId"
                    + " LEFT JOIN metaboliteMatrix mm"
                    + " ON t.relatedDrug = mm.metabolite OR t.relatedDrug = mm.substance"
                    + " LEFT JOIN ("
                    + "	 SELECT idPrescriptions, orderId, idSubstances, s.substance, metabolite"
                    + "	 FROM prescriptions p"
                    + "	 INNER JOIN drugs d"
                    + "  ON p.drugId = d.iddrugs"
                    + "	 INNER JOIN substances s"
                    + "  ON d.substance1 = s.idSubstances OR d.substance2 = s.idSubstances OR d.substance3 = s.idSubstances"
                    + "	 INNER JOIN metaboliteMatrix m"
                    + "  ON s.idSubstances = m.substance "
                    + " WHERE orderId = " + orderID + ") pds "
                    + " ON o.idOrders = pds.orderId AND (t.relatedDrug = pds.idSubstances OR mm.metabolite = pds.metabolite)"
                    + " WHERE o.idOrders = " + orderID
                    + " AND (r.dateReported IS NOT NULL OR t.testType = 0)"
                    + " AND (t.header != 1 OR t.headerPrint = 1)"
                    + " AND relatedDrug IS NOT NULL"
                    + " GROUP BY idOrders, testId"
                    + " ORDER BY idOrders, panelId, testType, substanceId, IsSubstance DESC, subtestOrder;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                DrugDetectItem ddi = new DrugDetectItem(
                        rs.getInt("idTests"),
                        rs.getBoolean("IsSubstance"),
                        rs.getBoolean("IsPrescribed"),
                        rs.getInt("relatedDrug"),
                        rs.getInt("SubstanceGroup"),
                        rs.getString("substance"),
                        rs.getInt("substanceId"),
                        rs.getInt("metaboliteId"),
                        rs.getString("resultText"),
                        rs.getDouble("resultNo"),
                        rs.getBoolean("isHigh"),
                        rs.getBoolean("isLow"),
                        rs.getBoolean("isCIDHigh"),
                        rs.getBoolean("isCIDLow"));
                if (ddi.getSubstanceGroup() != null)
                {
                    DetectionGroup.add(ddi.getSubstanceGroup(), ddi);
                }
            }
            rs.close();
            return DetectionGroup;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

}
