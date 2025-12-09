package BL;

import BOS.AddressBO;
import DAOS.AddressAssociationDAO;
import DAOS.AddressLookupDAO;
import DAOS.AddressTypeDAO;
import DAOS.CityDAO;
import DAOS.CountryDAO;
import DAOS.DiffLogDAO;
import DAOS.RegionDAO;
import DAOS.StateDAO;
import DAOS.ZipCodeDAO;
import DOS.Address;
import DOS.AddressAssociation;
import DOS.AddressLookup;
import DOS.AddressType;
import DOS.City;
import DOS.Country;
import DOS.Region;
import DOS.State;
import DOS.ZipCode;
import Database.CheckDBConnection;
import Utility.AddressLookupDefinition;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AddressBL
{

    public static enum SearchObjectType
    {

        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }

    /**
     * None are case-sensitive, so MATCHING is just the text value.
     * Warning: "Containing" searches may not use indexes well.
     */
    public static enum SearchType
    {

        STARTING_WITH,
        CONTAINING,
        MATCHING
    }

    
    public static enum AddressFlag
    {
        MAILING,
        PHYSICAL
    }

    /**
     * Attempts to search cities based on the provided text
     *
     * @param searchObjectType
     * @param searchType
     * @param cityNameSearchText
     * @return Collection of City objects; empty list if none found
     * @throws java.sql.SQLException
     */
    public static Collection<City> searchCities(SearchObjectType searchObjectType,
            SearchType searchType, String cityNameSearchText)
            throws IllegalArgumentException, NullPointerException, SQLException
    {

        if (cityNameSearchText == null || cityNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty search text string");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty SearchType argument");
        }

        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty SearchObjectType argument");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        cityNameSearchText = cityNameSearchText.replace("%", "");
        String sql = "SELECT * FROM " + CityDAO.table + " WHERE `name`";
        
        if (searchType.equals(SearchType.MATCHING))
        {
            sql += " = ?";
        }
        else
        {
            sql += " LIKE ?";
            if (searchType.equals(SearchType.CONTAINING)) cityNameSearchText = "%" + cityNameSearchText;
            cityNameSearchText += "%";
        }
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        List<City> cities = new ArrayList<>();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, cityNameSearchText);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            cities.add(CityDAO.ObjectFromResultSet(rs));
        }
        
        return cities;
    }
    
    /**
     * Attempts to search cities based on the provided text
     *
     * @param searchObjectType
     * @param searchType
     * @param stateNameSearchText
     * @return Collection of City objects; empty list if none found
     * @throws java.sql.SQLException
     */
    public static Collection<State> searchStateNames(SearchObjectType searchObjectType,
            SearchType searchType, String stateNameSearchText)
            throws IllegalArgumentException, NullPointerException, SQLException
    {

        if (stateNameSearchText == null || stateNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchStateNames : "
                            + "Received a NULL or empty search text string");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchStateNames : "
                            + "Received a NULL or empty SearchType argument");
        }

        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchStateNames : "
                            + "Received a NULL or empty SearchObjectType argument");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        stateNameSearchText = stateNameSearchText.replace("%", "");
        String sql = "SELECT * FROM " + StateDAO.table + " WHERE `name`";
        
        if (searchType.equals(SearchType.MATCHING))
        {
            sql += " = ?";
        }
        else
        {
            sql += " LIKE ?";
            if (searchType.equals(SearchType.CONTAINING)) stateNameSearchText = "%" + stateNameSearchText;
            stateNameSearchText += "%";
        }
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        List<State> states = new ArrayList<>();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, stateNameSearchText);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            states.add(StateDAO.ObjectFromResultSet(rs));
        }
        
        return states;
    }
    
    
    
    public static Collection<AddressBO> searchStateNames(SearchObjectType searchObjectType,
            SearchType searchType, String stateNameSearchText, City city,
            ZipCode zipCode, Region region, Country country)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (stateNameSearchText == null || stateNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchStateNames : "
                            + "Received a NULL or empty search text string");
        }

        Integer cityId = (city != null && city.getIdCities() != null ? city.getIdCities() : null);
        Integer zipCodeId = (zipCode != null && zipCode.getIdZipCodes() != null ? zipCode.getIdZipCodes() : null);
        Integer countryId = (country != null && country.getIdCountries() != null ? country.getIdCountries() : null);
        Integer regionId = (region != null && region.getIdregions() != null ? region.getIdregions() : null);
        
        return searchAddressAssociations(
                searchObjectType, searchType,
                null, cityId,
                stateNameSearchText, null,
                null, regionId,
                null, zipCodeId,
                null, countryId);
    }
    
    /**
     * This method uses the lookup table to only return cities that are associated
     *  with the supplied state/zip/country. Any of those arguments may be passed
     *  NULL. If NULL, they won't be taken into consideration in the search.
     * 
     *  If the lab does not record address associations, the other search method
     *  should be used
     * @param searchObjectType
     * @param searchType
     * @param cityNameSearchText
     * @param state
     * @param zipCode
     * @param region
     * @param country
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Collection<AddressBO> searchCities(SearchObjectType searchObjectType,
            SearchType searchType, String cityNameSearchText, State state,
            ZipCode zipCode, Region region, Country country)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        
        if (cityNameSearchText == null || cityNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty search text string");
        }
        
        Integer stateId = (state != null && state.getIdStates() != null ? state.getIdStates() : null);
        Integer zipCodeId = (zipCode != null && zipCode.getIdZipCodes() != null ? zipCode.getIdZipCodes() : null);
        Integer countryId = (country != null && country.getIdCountries() != null ? country.getIdCountries() : null);
        Integer regionId = (region != null && region.getIdregions() != null ? region.getIdregions() : null);
        
        return searchAddressAssociations(
                searchObjectType, searchType,
                cityNameSearchText, null,
                null, stateId,
                null, regionId,
                null, zipCodeId,
                null, countryId);
    }
    
    /**
     * This method uses the lookup table to only return cities that are associated
     *  with the supplied state/zip/country. Any of those arguments may be passed
     *  NULL. If NULL, they won't be taken into consideration in the search.
     * 
     *  If the lab does not record address associations, the other search method
     *  should be used
     * @param searchObjectType
     * @param searchType
     * @param regionNameSearchText
     * @param city
     * @param state
     * @param zipCode
     * @param country
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Collection<AddressBO> searchRegions(SearchObjectType searchObjectType,
            SearchType searchType, String regionNameSearchText, City city, State state,
            ZipCode zipCode, Country country)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        
        if (regionNameSearchText == null || regionNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchRegions : "
                            + "Received a NULL or empty search text string");
        }
        
        Integer cityId = (city != null && city.getIdCities() != null ? city.getIdCities() : null);
        Integer stateId = (state != null && state.getIdStates() != null ? state.getIdStates() : null);
        Integer zipCodeId = (zipCode != null && zipCode.getIdZipCodes() != null ? zipCode.getIdZipCodes() : null);
        Integer countryId = (country != null && country.getIdCountries() != null ? country.getIdCountries() : null);
        
        return searchAddressAssociations(
                searchObjectType, searchType,
                null, cityId,
                null, stateId,
                regionNameSearchText, null,
                null, zipCodeId,
                null, countryId);
    }
    
    /**
     * 
     * @param searchObjectType
     * @param searchType
     * @param regionNameSearchText
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<Region> searchRegions(SearchObjectType searchObjectType,
            SearchType searchType, String regionNameSearchText)
            throws IllegalArgumentException, NullPointerException, SQLException
    {

        if (regionNameSearchText == null || regionNameSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchRegions : "
                            + "Received a NULL or empty search text string");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchRegions : "
                            + "Received a NULL or empty SearchType argument");
        }

        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchRegions : "
                            + "Received a NULL or empty SearchObjectType argument");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        regionNameSearchText = regionNameSearchText.replace("%", "");
        String sql = "SELECT * FROM " + RegionDAO.table + " WHERE `name`";
        
        if (searchType.equals(SearchType.MATCHING))
        {
            sql += " = ?";
        }
        else
        {
            sql += " LIKE ?";
            if (searchType.equals(SearchType.CONTAINING)) regionNameSearchText = "%" + regionNameSearchText;
            regionNameSearchText += "%";
        }
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        List<Region> regions = new ArrayList<>();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, regionNameSearchText);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            regions.add(RegionDAO.ObjectFromResultSet(rs));
        }
        
        return regions;
    }    
    
    /**
     * 
     * @param searchObjectType
     * @param searchType
     * @param zipCodeSearchText
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<ZipCode> searchZipCodes(SearchObjectType searchObjectType,
            SearchType searchType, String zipCodeSearchText)
            throws IllegalArgumentException, NullPointerException, SQLException
    {

        if (zipCodeSearchText == null || zipCodeSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchZipCodes : "
                            + "Received a NULL or empty search text string");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchZipCodes : "
                            + "Received a NULL or empty SearchType argument");
        }

        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchZipCodes : "
                            + "Received a NULL or empty SearchObjectType argument");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        zipCodeSearchText = zipCodeSearchText.replace("%", "");
        String sql = "SELECT * FROM " + ZipCodeDAO.table + " WHERE `code`";
        
        if (searchType.equals(SearchType.MATCHING))
        {
            sql += " = ?";
        }
        else
        {
            sql += " LIKE ?";
            if (searchType.equals(SearchType.CONTAINING)) zipCodeSearchText = "%" + zipCodeSearchText;
            zipCodeSearchText += "%";
        }
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        List<ZipCode> zipCodes = new ArrayList<>();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, zipCodeSearchText);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            zipCodes.add(ZipCodeDAO.ObjectFromResultSet(rs));
        }
        
        return zipCodes;
    }
    
    
    /**
     * 
     * @param searchObjectType
     * @param searchType
     * @param zipCodeSearchText
     * @param city
     * @param state
     * @param region
     * @param country
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<AddressBO> searchZipCodes(SearchObjectType searchObjectType,
            SearchType searchType, String zipCodeSearchText,
            City city, State state, Region region, Country country)
            throws IllegalArgumentException, NullPointerException, SQLException    
    {
        if (zipCodeSearchText == null || zipCodeSearchText.isEmpty())
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchZipCodes : "
                            + "Received a NULL or empty search text string");
        }
        
        Integer stateId = (state != null && state.getIdStates() != null ? state.getIdStates() : null);
        Integer cityId = (city != null && city.getIdCities() != null ? city.getIdCities() : null);
        Integer countryId = (country != null && country.getIdCountries() != null ? country.getIdCountries() : null);
        Integer regionId = (region != null && region.getIdregions() != null ? region.getIdregions() : null);
        
        return searchAddressAssociations(
                searchObjectType, searchType,
                null, cityId,
                null, stateId,
                null, regionId,
                zipCodeSearchText, null,
                null, countryId);
    }
    
    /**
     * 
     * Called from within the BL to handle searches of different types (by text/by id)
     * 
     * If a database ID argument is received, the method will use that and
     *  disregard any string passed in for that argument.
     * 
     * NULL string and ID arguments for a single search type (e.g. city) will
     *  exclude that from the search.
     * 
     * @param searchObjectType
     * @param searchType
     * @param city
     * @param cityId
     * @param zipCode
     * @param zipCodeId
     * @param regionStr
     * @param regionId
     * @param state
     * @param stateId
     * @param country
     * @param countryId
     * @return 
     */
    private static Collection<AddressBO> searchAddressAssociations(
            SearchObjectType searchObjectType,
            SearchType searchType,
            String cityStr, Integer cityId,
            String stateStr, Integer stateId,
            String regionStr, Integer regionId,
            String zipCodeStr, Integer zipCodeId,
            String countryStr, Integer countryId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty SearchType argument");
        }

        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL::searchCities : "
                            + "Received a NULL or empty SearchObjectType argument");
        } 
        
        // Returned output:
        Collection<AddressBO> addressBOs = new ArrayList<>();
        
        String sql = "SELECT aa.* FROM addressAssociations aa"
                + " LEFT JOIN cities c ON aa.cityId = c.idCities"
                + " LEFT JOIN states s ON aa.stateId = s.idStates"
                + " LEFT JOIN zipCodes zc ON aa.zipCodeId = zc.idZipCodes"
                + " LEFT JOIN countries co ON aa.countryId = co.idCountries"
                + " LEFT JOIN regions r ON aa.regionId = r.idregions"
                + " WHERE 1=1";
        
        // ---------------------------- City -----------------------------------
        if (cityId != null && cityId > 0)
        {
            sql += " AND aa.cityId = ?";
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND c.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND c.active = 0";
        }
        else if (cityStr != null && cityStr.isEmpty() == false)
        {
            cityStr = cityStr.replace("%", "");
            if (searchType.equals(SearchType.MATCHING))
            {
                sql += " AND c.`name` = ?";
            }
            else
            {
                sql += " AND c.`name` LIKE ?";
                if (searchType.equals(SearchType.CONTAINING))
                {
                    cityStr = "%" + cityStr + "%";
                }
                else
                {
                    cityStr = cityStr + "%"; // handles SearchType.STARTING_WITH
                }
            }
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND c.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND c.active = 0";            
        }
        else
        {
            sql += " AND 1 = ?";
        }
        
        // ---------------------------- State ----------------------------------
        if (stateId != null && stateId > 0)
        {
            sql += " AND aa.stateId = ?";
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND s.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND s.active = 0";
        }
        else if (stateStr != null && stateStr.isEmpty() == false)
        {
            stateStr = stateStr.replace("%", "");
            if (searchType.equals(SearchType.MATCHING))
            {
                sql += " AND s.`name` = ?";
            }
            else
            {
                sql += " AND s.`name` LIKE ?";
                if (searchType.equals(SearchType.CONTAINING))
                {
                    stateStr = "%" + stateStr + "%";
                }
                else
                {
                    stateStr = stateStr + "%"; // handles SearchType.STARTING_WITH
                }
            }
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND s.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND s.active = 0";
        }
        else
        {
            sql += " AND 1 = ?";
        }
        
        // ---------------------------- Region ---------------------------------
        if (regionId != null && regionId > 0)
        {
            sql += " AND aa.regionId = ?";
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND r.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND r.active = 0";
        }
        else if (regionStr != null && regionStr.isEmpty() == false)
        {
            regionStr = regionStr.replace("%", "");
            if (searchType.equals(SearchType.MATCHING))
            {
                sql += " AND r.`name` = ?";
            }
            else
            {
                sql += " AND r.`name` LIKE ?";
                if (searchType.equals(SearchType.CONTAINING))
                {
                    regionStr = "%" + regionStr + "%";
                }
                else
                {
                    regionStr = regionStr + "%"; // handles SearchType.STARTING_WITH
                }
            }
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND r.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND r.active = 0";
        }
        else
        {
            sql += " AND 1 = ?";
        }
        
        // --------------------------- Zip Code --------------------------------
        if (zipCodeId != null && zipCodeId > 0)
        {
            sql += " AND aa.zipCodeId = ?";
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND zc.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND zc.active = 0";
        }
        else if (zipCodeStr != null && zipCodeStr.isEmpty() == false)
        {
            zipCodeStr = zipCodeStr.replace("%", "");
            if (searchType.equals(SearchType.MATCHING))
            {
                sql += " AND zc.`code` = ?";
            }
            else
            {
                sql += " AND zc.`code` LIKE ?";
                if (searchType.equals(SearchType.CONTAINING))
                {
                    zipCodeStr = "%" + zipCodeStr + "%";
                }
                else
                {
                    zipCodeStr = zipCodeStr + "%"; // handles SearchType.STARTING_WITH
                }
            }
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND zc.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND zc.active = 0";
        }
        else
        {
            sql += " AND 1 = ?";
        }
        
        // ---------------------------- Country --------------------------------
        if (countryId != null && countryId > 0)
        {
            sql += " AND aa.countryId = ?";
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND co.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND co.active = 0";
        }
        else if (countryStr != null && countryStr.isEmpty() == false)
        {
            countryStr = countryStr.replace("%", "");
            if (searchType.equals(SearchType.MATCHING))
            {
                sql += " AND co.`name` = ?";
            }
            else
            {
                sql += " AND s.`name` LIKE ?";
                if (searchType.equals(SearchType.CONTAINING))
                {
                    countryStr = "%" + countryStr + "%";
                }
                else
                {
                    countryStr = countryStr + "%"; // handles SearchType.STARTING_WITH
                }
            }
            if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY)) sql += " AND co.active = 1";
            if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY)) sql += " AND co.active = 0";
        }
        else
        {
            sql += " AND 1 = ?";
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        // City
        if (cityId != null && cityId > 0)
        {
            pStmt.setInt(1, cityId);
        }
        else if (cityStr != null && cityStr.isEmpty() == false)
        {
            pStmt.setString(1, cityStr);
        }
        else // Don't include city
        {
            pStmt.setInt(1, 1);
        }
        
        // State
        if (stateId != null && stateId > 0)
        {
            pStmt.setInt(2, stateId);
        }
        else if (stateStr != null && stateStr.isEmpty() == false)
        {
            pStmt.setString(2, stateStr);
        }
        else
        {
            pStmt.setInt(2, 1);
        }
        
        // Region
        if (regionId != null && regionId > 0)
        {
            pStmt.setInt(3, regionId);
        }
        else if (regionStr != null && regionStr.isEmpty() == false)
        {
            pStmt.setString(3, regionStr);
        }
        else
        {
            pStmt.setInt(3, 1);
        }
        
        // Zip Code
        if (zipCodeId != null && zipCodeId > 0)
        {
            pStmt.setInt(4, zipCodeId);
        }
        else if (zipCodeStr != null && zipCodeStr.isEmpty() == false)
        {
            pStmt.setString(4, zipCodeStr);
        }
        else
        {
            pStmt.setInt(4, 1);
        }
        
        // Country
        if (countryId != null && countryId > 0)
        {
            pStmt.setInt(5, countryId);
        }
        else if (countryStr != null && countryStr.isEmpty() == false)
        {
            pStmt.setString(5, countryStr);
        }
        else
        {
            pStmt.setInt(5, 1);
        }
        
        ResultSet rs = pStmt.executeQuery();
        
        // Load the corresponding objects returned from the
        // addressAssociation query
        while (rs.next())
        {
            AddressAssociation addressAssociation = AddressAssociationDAO.ObjectFromResultSet(rs);
            AddressBO addressBO = new AddressBO();
            boolean addToOutput = false;
            
            if (addressAssociation.getCityId() != null)
            {
                City city = CityDAO.get(addressAssociation.getCityId());
                addressBO.setCity(city);
                addToOutput = true;
            }
            
            if (addressAssociation.getStateId() != null)
            {
                State state = StateDAO.get(addressAssociation.getStateId());
                addressBO.setState(state);
                addToOutput = true;
            }
            
            if (addressAssociation.getRegionId() != null)
            {
                Region region = RegionDAO.get(addressAssociation.getRegionId());
                addressBO.setRegion(region);
                addToOutput = true;
            }
            
            if (addressAssociation.getZipCodeId() != null)
            {
                ZipCode zipCode = ZipCodeDAO.get(addressAssociation.getZipCodeId());
                addressBO.setZipCode(zipCode);
                addToOutput = true;
            }
            
            if (addressAssociation.getCountryId() != null)
            {
                Country country = CountryDAO.get(addressAssociation.getCountryId());
                addressBO.setCountry(country);
                addToOutput = true;
            }
            
            if (addToOutput)
            {
                addressBOs.add(addressBO);
            }
        }
        
        return addressBOs;
    }
    
    public static Collection<Country> getCountries(SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL:getCountries: Received a [NULL] SearchObjectType argument.");
        }
       
        String sql = "SELECT * FROM " + CountryDAO.table;
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " WHERE active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " WHERE active = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);        
        
        ResultSet rs = pStmt.executeQuery();
        
        ArrayList<Country> countries = new ArrayList<>();
        while (rs.next())
        {
            countries.add(CountryDAO.ObjectFromResultSet(rs));
        }
        return countries;
    }
    
    public static AddressAssociation getAddressAssociationByCityStateZip(SearchObjectType searchObjectType,
            Integer cityId, Integer stateId, Integer zipCodeId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL:getAddressAssociationByCityStateZip:"
                            + " Received a [NULL] SearchObjectType argument.");            
        }
        
        if (cityId == null || cityId <= 0)
        {
            throw new IllegalArgumentException(
                    "AddressBL:getAddressAssociationByCityStateZip:"
                            + " Received a [NULL] cityId argument."
                            + " All arguments are required.");
        }
        
        if (stateId == null || stateId <= 0)
        {
            throw new IllegalArgumentException(
                    "AddressBL::getAddressAsscoationByCityStateZip:"
                            + " Received a [NULL] stateId argument."
                            + " All arguments are required.");
        }
        
        if (zipCodeId == null || zipCodeId <= 0)
        {
            throw new IllegalArgumentException(
                    "AddressBL::getAddressAsscoationByCityStateZip:"
                            + " Received a [NULL] zipCodeId argument."
                            + " All arguments are required.");
        }        
        
        String sql = "SELECT * FROM " + AddressAssociationDAO.table + " WHERE cityId = ? AND stateId = ? AND zipCodeId = ?";
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);        
        pStmt.setInt(1, cityId);
        pStmt.setInt(2, stateId);
        pStmt.setInt(3, zipCodeId);
        
        ResultSet rs = pStmt.executeQuery();
        
        AddressAssociation addressAssociation = null;
        while (rs.next())
        {
            addressAssociation = AddressAssociationDAO.ObjectFromResultSet(rs);
        }
        return addressAssociation;
    }
    
    /**
     * Return all of the address types that meet the search object type criteria
     * @param searchObjectType
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static Collection<AddressType> getAddressTypes(SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException(
                    "AddressBL:getAddressTypes:"
                            + " Received a [NULL] SearchObjectType argument.");            
        }        
        
        String sql = "SELECT * FROM " + AddressTypeDAO.table;
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " WHERE `active` = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " WHERE `active` = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        ResultSet rs = pStmt.executeQuery();
        
        List<AddressType> addressTypes = new ArrayList<>();
        while (rs.next())
        {
            addressTypes.add(AddressTypeDAO.ObjectFromResultSet(rs));
        }
        return addressTypes;
    }
    
    public static List<AddressLookup> getAddressLookups(
            Integer foreignKeyObjectId,
            AddressLookupDefinition addressLookupDefinition,
            SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (foreignKeyObjectId == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookups:"
                    + " Received a [NULL] or invalid foreign key object Id");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookups:"
                + " Received a [NULL] or invalid AddressLookupDefinition argument");
        }
        
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookups:"
                + " Received a [NULL] or invalid SearchObjectType argument");
        }
     
        String sql = "SELECT * FROM "
                + addressLookupDefinition.tableName
                + " WHERE " + addressLookupDefinition.foreignKeyName + " = ?";
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND `active` = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND `active` = 0";
        }
        
        sql += " ORDER BY assigned DESC";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, foreignKeyObjectId);
        
        ResultSet rs = pStmt.executeQuery();
        
        List<AddressLookup> addressLookups = new ArrayList<>();
        while (rs.next())
        {
            addressLookups.add(AddressLookupDAO.ObjectFromResultSet(rs,addressLookupDefinition));
        }
        
        return addressLookups;
    }
    
    
    public static List<AddressLookup> getAddressLookupsForAddressId(
            Integer addressId,
            AddressLookupDefinition addressLookupDefinition,
            SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (addressId == null || addressId <= 0)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookupsForAddressId:"
                    + " Received a [NULL] or invalid addressId argument");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookupsForAddressId:"
                + " Received a [NULL] AddressLookupDefinition argument");
        }
        
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressLookupsForAddressId:"
                + " Received a [NULL] or invalid SearchObjectType argument");
        }
     
        String sql = "SELECT * FROM "
                + addressLookupDefinition.tableName
                + " WHERE addressId = ?";
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND `active` = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND `active` = 0";
        }
        
        sql += " ORDER BY assigned DESC";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, addressId);
        
        ResultSet rs = pStmt.executeQuery();
        
        List<AddressLookup> addressLookups = new ArrayList<>();
        while (rs.next())
        {
            addressLookups.add(AddressLookupDAO.ObjectFromResultSet(rs,addressLookupDefinition));
        }
        return addressLookups;
    }
    
    /**
     * Checks to see if there are any addresses that match the data of the
     *  argument address and returns the lookup(s). Only searches addresses
     *  of the same type (e.g. Location, Department)
     * 
     *  If the provided Address object has a unique database identifier,
     *   it will not return any lookups associated with that identifier.
     * 
     *  Can be used to inform a user that a change they are making will
     *   affect other objects.
     * 
     * 
     * @param address
     * @param addressLookupDefinition
     * @param searchObjectType
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<AddressLookup> getAddressMatches(
            Address address,
            AddressLookupDefinition addressLookupDefinition,
            SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (address == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressMatches:"
                    + " Received a [NULL] or invalid Address object argument");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressMatches:"
                + " Received a [NULL] AddressLookupDefinition argument");
        }
        
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressMatches:"
                + " Received a [NULL] or invalid SearchObjectType argument");
        }
     
        String sql = "SELECT al.* FROM " + addressLookupDefinition.tableName + " al"
                + " INNER JOIN addresses a ON al.addressId = a.idaddresses"
                // Don't return the argument address as a match to itself
                + " WHERE (NOT a.idAddresses <=> ?)"
                + " AND a.address1 <=> ?"
                + " AND a.address2 <=> ?"
                + " AND a.address3 <=> ?"
                + " AND a.cityId <=> ?"
                + " AND a.stateId <=> ?"
                + " AND a.regionId <=> ?"
                + " AND a.zipCodeId <=> ?"
                + " AND a.countryId <=> ?"
                + " AND a.clia <=> ?"
                + " AND a.cola <=> ?"
                + " AND a.npi <=> ?"
                + " AND a.taxId <=> ?"
                + " AND a.director <=> ?";
        
        if (searchObjectType == SearchObjectType.ACTIVE_ONLY)
        {
            sql += " AND al.active = 1";
        }
        else if (searchObjectType == SearchObjectType.INACTIVE_ONLY)
        {
            sql += " AND al.active = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getIdaddresses());
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress1());
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress2());
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress3());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getCityId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getStateId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getRegionId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getZipCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getCountryId());
        SQLUtil.SafeSetString(pStmt, ++i, address.getClia());
        SQLUtil.SafeSetString(pStmt, ++i, address.getCola());
        SQLUtil.SafeSetString(pStmt, ++i, address.getNpi());
        SQLUtil.SafeSetString(pStmt, ++i, address.getTaxId());
        SQLUtil.SafeSetString(pStmt, ++i, address.getDirector());
        
        ResultSet rs = pStmt.executeQuery();
        
        List<AddressLookup> addressLookups = new ArrayList<>();
        while (rs.next())
        {
            addressLookups.add(AddressLookupDAO.ObjectFromResultSet(rs,addressLookupDefinition));
        }
        return addressLookups;        
    }
    
    /**
     * Gets the address associations for an object
     * @param addressLookupDefinition
     * @param objectId
     * @param searchObjectType
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<AddressLookup> getLookupsForObjectId(
            AddressLookupDefinition addressLookupDefinition,
            Integer objectId,
            SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (objectId == null)
        {
            throw new IllegalArgumentException("AddressBL::getAddressMatches:"
                    + " Received a [NULL] or invalid objectId argument");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::getLookupsForObjectId:"
                + " Received a [NULL] AddressLookupDefinition argument");
        }
        
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException("AddressBL::getLookupsForObjectId:"
                + " Received a [NULL] or invalid SearchObjectType argument");
        }

        String sql = "SELECT * FROM " + addressLookupDefinition.tableName
                + " WHERE " + addressLookupDefinition.foreignKeyName + " = ?";
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        
        if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, objectId);
        ResultSet rs = pStmt.executeQuery();
        
        List<AddressLookup> addressLookups = new ArrayList<>();
        while (rs.next())
        {
            addressLookups.add(AddressLookupDAO.ObjectFromResultSet(rs, addressLookupDefinition));
        }
        
        return addressLookups;
    }
    
    /**
     * Looks at the flags on the argument AddressLookup and un-flags any other
     *  lookups for the object in question. Called when a user assigns an
     *  address to ensure that there is only one assigned address of each
     *  type (mailing/physical).
     * 
     * e.g. if AddressFlag.Physical is passed in, any other physical address 
     *  lookup is set to not in use.
     * 
     * @param excludeAddressLookup
     * @param addressDefinition
     * @param userId
     * @param addressFlags
     * @return List of the address lookups that were affected
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static Collection<AddressLookup> unflagOtherLookups(
            AddressLookup excludeAddressLookup,
            AddressLookupDefinition addressDefinition,
            Integer userId, // The user making this change
            AddressFlag... addressFlags)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        
        // If there aren't any flags being un-set, there's nothing to be done
        if (addressFlags == null || addressFlags.length == 0) return new ArrayList<>();
        
        if (excludeAddressLookup == null)
        {
            throw new IllegalArgumentException("AddressBL::unflagOtherLookups:"
                    + " Received a [NULL] AddressLookup object argument");
        }
        
        if (excludeAddressLookup.getObjectId() == null)
        {
            throw new IllegalArgumentException("AddressBL::unflagOtherLookups:"
                    + " AddressLookup object argument had a [NULL] objectId");
        }
        
        if (addressDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::unflagOtherLookups:"
                    + " Received a [NULL] AddressLookupDefinition argument");
        }
     
        if (userId == null)
        {
            throw new IllegalArgumentException("AddressBL::unflagOtherLookups:"
                    + " Received a [NULL] userId argument");
        }
        
        List<AddressLookup> affectedLookups = new ArrayList<>();
        
        List<AddressFlag> addressFlagList = new ArrayList<>(Arrays.asList(addressFlags));
        
        if (excludeAddressLookup.isAssigned() == false) return affectedLookups;
        
        // We only care about updating active rows (not "deleted")
        List<AddressLookup> lookups = getLookupsForObjectId(
                addressDefinition,
                excludeAddressLookup.getObjectId(),
                SearchObjectType.ACTIVE_ONLY);
        
        HashMap<Integer, AddressType> addressTypeMap = new HashMap<>();
        
        for (AddressLookup lookup : lookups)
        {
            if (excludeAddressLookup.getUniqueDatabaseId() != null &&
                    excludeAddressLookup.getUniqueDatabaseId().equals(lookup.getUniqueDatabaseId()))
            {
                // Skip over the lookup that was passed in
                continue;
            }

            // Lazy-load the necessary address types as we find them
            if (addressTypeMap.containsKey(lookup.getAddressTypeId()) == false)
            {
                AddressType addressType = AddressTypeDAO.get(lookup.getAddressTypeId());
                addressTypeMap.put(addressType.getIdaddressTypes(), addressType);
            }

            AddressType addressType = addressTypeMap.get(lookup.getAddressTypeId());

            if (addressFlagList.contains(AddressFlag.MAILING) && addressType.isMailing()
                    || addressFlagList.contains(AddressFlag.PHYSICAL) && addressType.isPhysical())
            {
                
                // todo: check here. if it's not assigned, don't need to change anything
                
                AddressLookup lookupOld = lookup.copy();
                
                lookup.setAssigned(false);
                
                AddressLookupDAO.update(lookup, addressDefinition);
                
                DiffLogDAO.Insert(
                        addressDefinition.diffLogTableName,
                        addressDefinition.uniqueIdName,
                        lookup.getUniqueDatabaseId(),
                        lookupOld,
                        lookup, "", userId);
                
                affectedLookups.add(lookup);
            }
        }
        return affectedLookups;
    }
    
    
    
    // TODO: need method to determine whether there are any assigned
    //  addresses of a particular type
    /*
    public boolean hasAssignedAddress(AddressLookupDefinition addressLookupDefinition,
            Integer objectId, AddressFlag addressFlag, SearchObjectType searchObjectType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        // If there aren't any flags being un-set, there's nothing to be done
        if (addressFlag == null)
        {
            throw new IllegalArgumentException("AddressBL::hasAssignedAddress:"
                    + " Received a [NULL] AddressFlag argument (mailing/physical)");
        }
        
        if (objectId == null)
        {
            throw new IllegalArgumentException("AddressBL::hasAssignedAddress:"
                    + " Received a [NULL] objectId integer argument");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressBL::hasAssignedAddress:"
                    + " Received a [NULL] AddressLookupDefinition argument");
        }
     
        if (searchObjectType == null)
        {
            throw new IllegalArgumentException("AddressBL::getLookupsForObjectId:"
                + " Received a [NULL] or invalid SearchObjectType argument");
        }
        
        String sql = "SELECT COUNT(*) FROM " + addressLookupDefinition.tableName
                + " al INNER JOIN addressTypes at ON al.addressTypeId = at.idAddressTypes"
                + " WHERE " + addressLookupDefinition.foreignKeyName + " = ?"
                + " AND ";
        
        if (searchObjectType.equals(SearchObjectType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        else if (searchObjectType.equals(SearchObjectType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, objectId);
        ResultSet rs = pStmt.executeQuery();
        
        int count = 0;
        
        if (rs.next())
        {
            count = rs.getInt(1);
        }
        
        return (count > 0);
    }
    */
}
