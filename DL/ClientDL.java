package DL;

/**
 * @date:
 * Mar
 * 15,
 * 2012
 * @author:
 * Keith
 * Maggio
 *
 * @project:
 * CSSLibrary
 * @package:
 * DL
 * @file
 * name:
 * OrderDL.java
 *
 * @Description:
 *
 * Computer
 * Service
 * &
 * Support,
 * Inc.
 * All
 * Rights
 * Reserved
 */
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import DOS.Clients;
import DAOS.ClientDAO;
import EPOS.ClientLine;
import Utility.Convert;

public class ClientDL
{

    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
                                                      ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;

        ClientDAO rd = new ClientDAO();

        Clients client;
        ClientLine line;
        String[] values;
        int x = 0;


        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);

        while ((values = reader.readNext()) != null)
        {
            try
            {
                line = new ClientLine();
                x = 0;
                count++;

                //line 0
                /*line.setAccession(Integer.parseInt(values[x]));
                 x++;
                 line.setClientID(Integer.parseInt(values[x]));
                 x++;
                 line.setDoctorID(Integer.parseInt(values[x]));
                 x++;
                 line.setLocationID(Integer.parseInt(values[x]));
                 x++;
                 line.setOrderDate(Convert.ToSQLDate(values[x], "dd/MM/yyyy_HHmm"));
                 x++;
                 line.setSpecimenDate(Convert.ToSQLDate(values[x], "dd/MM/yyyy_HHmm"));
                 x++;
                 line.setPatientID(Integer.parseInt(values[x]));
                 x++;
                 line.setSpeciesID(Integer.parseInt(values[x]));
                 */

                line.setClientNumber(Integer.parseInt(values[x]));
                ++x;
                line.setClientName(values[x]);
                ++x;
                line.setSortSequence(values[x]);
                ++x;
                line.setExternalId(values[x]);
                ++x;
                line.setLicenseNumber(values[x]);
                ++x;
                line.setClientUPIN(values[x]);
                ++x;
                line.setClientOtherNumber(values[x]);
                ++x;
                line.setClientNPINumber(values[x]);
                ++x;
                line.setExtendedClientName(values[x]);
                ++x;
                line.setAddress1(values[x]);
                ++x;
                line.setAddress2(values[x]);
                ++x;
                line.setCityStateZip(values[x]);
                ++x;
                line.setClientCountry(values[x]);
                ++x;
                line.setLocality(values[x]);
                ++x;
                line.setOtherName(values[x]);
                ++x;
                line.setOtherClientName(values[x]);
                ++x;
                line.setOtherAddress(values[x]);
                ++x;
                line.setOtherCityStateZip(values[x]);
                ++x;
                line.setOtherCountry(values[x]);
                ++x;
                line.setOtherLocality(values[x]);
                ++x;
                line.setContactName(values[x]);
                ++x;
                line.setPhone(values[x]);
                ++x;
                line.setFAX(values[x]);
                ++x;
                line.setTransmit(values[x]);
                ++x;
                line.setBalanceForward(Float.parseFloat(values[x]));
                ++x;
                line.setCurrentBalance(Float.parseFloat(values[x]));
                ++x;
                line.setThirtyDayBalance(Float.parseFloat(values[x]));
                ++x;
                line.setSixtyDayBalance(Float.parseFloat(values[x]));
                ++x;
                line.setNinetyDayBalance(Float.parseFloat(values[x]));
                ++x;
                line.setOverNinetyDayBalance(Float.parseFloat(values[x]));
                ++x;
                int Tests[] = new int[8];
                float Amnt[] = new float[8];
                //TODO ClientMonth Test and Amount
                Tests[0] = Integer.parseInt(values[x]);
                ++x;
                Amnt[0] = Float.parseFloat(values[x]);
                ++x;
                Tests[1] = Integer.parseInt(values[x]);
                ++x;
                Amnt[1] = Float.parseFloat(values[x]);
                ++x;
                Tests[2] = Integer.parseInt(values[x]);
                ++x;
                Amnt[2] = Float.parseFloat(values[x]);
                ++x;
                Tests[3] = Integer.parseInt(values[x]);
                ++x;
                Amnt[3] = Float.parseFloat(values[x]);
                ++x;
                Tests[4] = Integer.parseInt(values[x]);
                ++x;
                Amnt[4] = Float.parseFloat(values[x]);
                ++x;
                Tests[5] = Integer.parseInt(values[x]);
                ++x;
                Amnt[5] = Float.parseFloat(values[x]);
                ++x;
                Tests[6] = Integer.parseInt(values[x]);
                ++x;
                Amnt[6] = Float.parseFloat(values[x]);
                ++x;
                Tests[7] = Integer.parseInt(values[x]);
                ++x;
                Amnt[7] = Float.parseFloat(values[x]);
                ++x;
                line.setMonthTests(Tests);
                line.setMonthAmounts(Amnt);
                //TODO ClientYear Test and Amount
                Tests[0] = Integer.parseInt(values[x]);
                ++x;
                Amnt[0] = Float.parseFloat(values[x]);
                ++x;
                Tests[1] = Integer.parseInt(values[x]);
                ++x;
                Amnt[1] = Float.parseFloat(values[x]);
                ++x;
                Tests[2] = Integer.parseInt(values[x]);
                ++x;
                Amnt[2] = Float.parseFloat(values[x]);
                ++x;
                Tests[3] = Integer.parseInt(values[x]);
                ++x;
                Amnt[3] = Float.parseFloat(values[x]);
                ++x;
                Tests[4] = Integer.parseInt(values[x]);
                ++x;
                Amnt[4] = Float.parseFloat(values[x]);
                ++x;
                Tests[5] = Integer.parseInt(values[x]);
                ++x;
                Amnt[5] = Float.parseFloat(values[x]);
                ++x;
                Tests[6] = Integer.parseInt(values[x]);
                ++x;
                Amnt[6] = Float.parseFloat(values[x]);
                ++x;
                Tests[7] = Integer.parseInt(values[x]);
                ++x;
                Amnt[7] = Float.parseFloat(values[x]);
                ++x;
                line.setYearTests(Tests);
                line.setYearAmounts(Amnt);
                //TODO Clientlife Test and Amount
                Tests[0] = Integer.parseInt(values[x]);
                ++x;
                Amnt[0] = Float.parseFloat(values[x]);
                ++x;
                Tests[1] = Integer.parseInt(values[x]);
                ++x;
                Amnt[1] = Float.parseFloat(values[x]);
                ++x;
                Tests[2] = Integer.parseInt(values[x]);
                ++x;
                Amnt[2] = Float.parseFloat(values[x]);
                ++x;
                Tests[3] = Integer.parseInt(values[x]);
                ++x;
                Amnt[3] = Float.parseFloat(values[x]);
                ++x;
                Tests[4] = Integer.parseInt(values[x]);
                ++x;
                Amnt[4] = Float.parseFloat(values[x]);
                ++x;
                Tests[5] = Integer.parseInt(values[x]);
                ++x;
                Amnt[5] = Float.parseFloat(values[x]);
                ++x;
                Tests[6] = Integer.parseInt(values[x]);
                ++x;
                Amnt[6] = Float.parseFloat(values[x]);
                ++x;
                Tests[7] = Integer.parseInt(values[x]);
                ++x;
                Amnt[7] = Float.parseFloat(values[x]);
                ++x;
                line.setLifeTests(Tests);
                line.setLifeAmounts(Amnt);
                if (values[x].isEmpty() == false)
                {
                    line.setDiscountType(values[x].charAt(0));
                }
                else
                {
                    line.setDiscountType(' ');
                }
                ++x;
                line.setInHouseDiscount(Integer.parseInt(values[x]));
                ++x;
                line.setVolumeForDiscount(Float.parseFloat(values[x]));
                ++x;
                line.setReferenceLabDiscount(Integer.parseInt(values[x]));
                ++x;
                int CustAutoTests[] = new int[6];
                //TODO CustomTests
                CustAutoTests[0] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[1] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[2] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[3] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[4] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[5] = Integer.parseInt(values[x]);
                ++x;
                line.setCustomTests(CustAutoTests);
                //TODO AutoTests
                CustAutoTests[0] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[1] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[2] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[3] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[4] = Integer.parseInt(values[x]);
                ++x;
                CustAutoTests[5] = Integer.parseInt(values[x]);
                ++x;
                line.setAutoTests(CustAutoTests);

                if ("00/00/0000".equals(values[x]) == false && "".equals(values[x]) == false)
                {
                    line.setAuthorizedStartDate(Convert.ToDate(values[x], "MM/dd/yyyy"));
                }
                ++x;
                if ("00/00/0000".equals(values[x]) == false && "".equals(values[x]) == false)
                {
                    line.setAuthorizedEndDate(Convert.ToDate(values[x], "MM/dd/yyyy"));
                }
                ++x;
                //TODO Auth. Tests
                int AuthTests[] = new int[16];
                AuthTests[0] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[1] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[2] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[3] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[4] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[5] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[6] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[7] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[8] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[9] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[10] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[11] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[12] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[13] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[14] = Integer.parseInt(values[x]);
                ++x;
                AuthTests[15] = Integer.parseInt(values[x]);
                ++x;
                line.setAuthorizedTests(AuthTests);
                if (values[x].isEmpty() == false)
                {
                    line.setClientFeeSchedule(values[x].charAt(0));
                }
                else
                {
                    line.setClientFeeSchedule(' ');
                }
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setClientTransmission(values[x].charAt(0));
                }
                else
                {
                    line.setClientTransmission(' ');
                }
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setStatisticType(values[x].charAt(0));
                }
                else
                {
                    line.setStatisticType(' ');
                }
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setBillingType(values[x].charAt(0));
                }
                else
                {
                    line.setBillingType(' ');
                }
                ++x;
                line.setCurrency(values[x]);
                ++x;
                line.setClientStop(Integer.parseInt(values[x]));
                ++x;
                line.setClientPickedTime(Integer.parseInt(values[x]));
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setSaturdayPickup(values[x].charAt(0));
                }
                else
                {
                    line.setSaturdayPickup(' ');
                }
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setResultPrint(values[x].charAt(0));
                }
                else
                {
                    line.setResultPrint(' ');
                }
                ++x;
                line.setSalesmanCode(Integer.parseInt(values[x]));
                ++x;
                line.setNumPerDegreeDifference(Integer.parseInt(values[x]));
                ++x;
                //TODO Deg Diff
                int DegDiff[] = new int[9];
                DegDiff[0] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[1] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[2] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[3] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[4] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[5] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[6] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[7] = Integer.parseInt(values[x]);
                ++x;
                DegDiff[8] = Integer.parseInt(values[x]);
                ++x;
                line.setPerForDegreeDiff(DegDiff);
                line.setClientCopies(Integer.parseInt(values[x]));
                ++x;
                line.setRoute(Integer.parseInt(values[x]));
                ++x;
                line.setPickupDays(values[x]);
                ++x;
                line.setLocation(Integer.parseInt(values[x]));
                ++x;
                if (values[x].isEmpty() == false)
                {
                    line.setHL7Enabled(values[x].charAt(0));
                }
                else
                {
                    line.setHL7Enabled(' ');
                }
                ++x;
                line.setPassword(values[x]);
                ++x;
                line.setDaysOpen(values[x]);
                ++x;
                line.setFreezerTime(Integer.parseInt(values[x]));
                ++x;
                line.setWebFlag(Integer.parseInt(values[x]));
                ++x;
                line.setClientComment(values[x]);
                ++x;
                line.setCreditTerms(Integer.parseInt(values[x]));
                ++x;
                line.setEmail1(values[x]);
                ++x;
                line.setEmail2(values[x]);
                ++x;
                int ResultRpt[] = new int[3];
                ResultRpt[0] = Integer.parseInt(values[x]);
                ++x;
                ResultRpt[1] = Integer.parseInt(values[x]);
                ++x;
                ResultRpt[2] = Integer.parseInt(values[x]);
                ++x;
                line.setResultReport(ResultRpt);

                try
                {
                    client = EPO2DO(line);
                    //if(rd.GetClient(client.getClientNo()).getIdClients() == 0)
                    if (!rd.ClientExists(client.getClientNo()))
                    {
                        rd.InsertClient(client);
                        inserts++;
                    }
                    else
                    {
                        client.setIdClients(rd.GetClientId(client.getClientNo()));
                        rd.UpdateClient(client);
                        ++update;
                    }
                }
                catch (Exception ex1)
                {
                    System.out.println(ex1.toString() + " CLIENT [" + line.getClientNumber() + "]");
                    failures++;
                }

            }
            catch (Exception ex)
            {
                System.out.println(ex.toString() + " Value[" + count + ", " + x + "]");
                unreadableLines++;
            }
        }

    }

    //Converts the Remark EPO to the Remark DO
    public Clients EPO2DO(ClientLine line)
    {
        try
        {
            Clients client = new Clients();

            client.setClientNo(line.getClientNumber());
            client.setClientName(line.getClientName());
            client.setClientStreet(line.getAddress1());
            client.setClientStreet2(line.getAddress2());
            if (line.getCityStateZip().isEmpty() == false)
            {
                /*String City;
                 String State;
                 String Zip;
                 String CSZ = line.getCityStateZip();
                 int CityIndex = CSZ.indexOf(',');
                 if(CityIndex != -1)
                 {
                 City = CSZ.substring(0, CityIndex + 1);
                 int StateIndex = CSZ.lastIndexOf(',');
                 if(StateIndex != -1)
                 {
                 State = CSZ.substring(CityIndex, StateIndex);
                 Zip = CSZ.substring(StateIndex);
                 client.setClientCity(City);
                 client.setClientState(State);
                 client.setClientZip(Zip);
                 }
                 else
                 {
                 // OK, no commas. Try for a space?
                 StateIndex = CSZ.lastIndexOf(' ');
                 if(StateIndex != -1)
                 {
                 State = CSZ.substring(CityIndex, StateIndex);
                 Zip = CSZ.substring(StateIndex);
                 client.setClientCity(City);
                 client.setClientState(State);
                 client.setClientZip(Zip);
                 }
                 else
                 {
                 State = CSZ.substring(CityIndex);
                 client.setClientCity(City);
                 client.setClientState(State);
                 client.setClientZip(null);
                 }
                 }
                 }
                 else
                 {
                 client.setClientCity(CSZ);
                 client.setClientState(null);
                 client.setClientZip(null);
                
                 }*/
                String CSZ = line.getCityStateZip();

                String tokens[] = CSZ.split("(,)\\s*|\\s(?=\\d)");

                if (tokens.length == 3)
                {
                    client.setClientCity(tokens[0]);
                    if (tokens[1].length() > 2)
                    {
                        String statetok[] = tokens[1].split(" ");
                        String State;
                        if (statetok.length != 2)
                        {
                            State = tokens[1].substring(0, 2);
                        }
                        else
                        {
                            char abbrev[] = new char[2];
                            abbrev[0] = statetok[0].charAt(0);
                            abbrev[1] = statetok[1].charAt(0);
                            State = new String(abbrev);
                        }
                        client.setClientState(State);
                    }
                    else
                    {
                        client.setClientState(tokens[1]);
                    }
                    if (tokens[2].length() > 12)
                    {
                        client.setClientZip(tokens[2].substring(0, 12));
                    }
                    else
                    {
                        client.setClientZip(tokens[2]);
                    }
                }
                else
                {
                    client.setClientCity(null);
                    client.setClientState(null);
                    client.setClientZip(null);
                }

            }
            else
            {
                client.setClientCity(null);
                client.setClientState(null);
                client.setClientZip(null);
            }
            client.setPhoneNo(line.getPhone());
            client.setFaxNo(line.getFAX());
            client.setTransmitNo(line.getTransmit());
            if (line.getLicenseNumber().isEmpty() == false)
            {
                client.setLicenseNo(line.getLicenseNumber());
            }
            else
            {
                client.setLicenseNo(null);
            }
            client.setUpin(line.getClientUPIN());
            client.setOtherId(line.getClientOtherNumber());
            client.setNpi(line.getClientNPINumber());
            if(line.getWebFlag() != 0)
                client.setWebEnabled(true);
            else
                client.setWebEnabled(false);
            client.setResultCopies(null);
            //client.setFeeSchedule(line.getClientFeeSchedule());
            client.setFeeSchedule(null);
            client.setRoute(line.getRoute());
            client.setStopNo(String.valueOf(line.getClientStop()));
            //TODO client.setPickupTime(0);
            client.setSalesmen(line.getSalesmanCode());
            client.setLocation(line.getLocation());
            client.setResReport1(String.valueOf(line.getResultReport()[0]));
            client.setResReport2(String.valueOf(line.getResultReport()[1]));
            client.setResReport3(String.valueOf(line.getResultReport()[2]));
            client.setBillType(line.getBillingType());
            client.setResPrint(line.getResultPrint());
            client.setTransType(line.getClientTransmission());
            client.setStatCode(line.getStatisticType());
            client.setContact1(line.getContactName());
            client.setContact2("");
            if(line.getHL7Enabled() != 0)
                client.setHl7Enabled(true);
            else
                client.setHl7Enabled(false);
            
            client.setCliComment(line.getClientComment().getBytes());
            if (line.getPassword().isEmpty() == false)
            {
                client.setPassword(line.getPassword());
            }
            else
            {
                client.setPassword(null);
            }
            client.setClientType(0);
            client.setProcedureset(null);
            client.setPercentDiscount(null);
            client.setDiscountVolume(null);
            client.setReferenceDiscount(null);
            client.setDefaultReportType(null);
            client.setBillingId(null);
            client.setTransmitEMRPdf(false);
            client.setPdfToOBX(false);

            return client;
        }
        catch (Exception e)
        {
            System.out.println("EPO2DO Failure: " + line.getClientNumber() + " - " + e.toString());
            return null;
        }

    }
}
