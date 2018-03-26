package com.solutions.roartek.placeme.Helper;

import android.util.Log;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Component.AppConfigure;
import com.solutions.roartek.placeme.DTO.StatisticsDTO;
import com.solutions.roartek.placeme.Domain.Entity_College;
import com.solutions.roartek.placeme.Domain.Entity_Company;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.Domain.Entity_Staff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Created by raghav.aneja on 11-12-2016.
 */
public class JsonDecodeHelper {

    String jsonResponse;

    public JsonDecodeHelper(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public Object decodeCollegeInfo()
    {
        Entity_College collegeOBj=null;
        try {
            JSONArray responseArray = new JSONArray(jsonResponse);
            JSONObject jsonCollegeObj = responseArray.getJSONObject(0);
            if (jsonCollegeObj != null) {
                collegeOBj=new Entity_College();
                collegeOBj.setCollegeCode(jsonCollegeObj.getString("collegeCode"));
                collegeOBj.setCollegeName(jsonCollegeObj.getString("collegeName"));
                collegeOBj.setCollegeShortName(jsonCollegeObj.getString("collegeShortName"));
                collegeOBj.setDBName(jsonCollegeObj.getString("DBName"));
                collegeOBj.setDBUser(jsonCollegeObj.getString("DBUser"));
                collegeOBj.setDBPassword(jsonCollegeObj.getString("DBPassword"));
                collegeOBj.setDBServer(jsonCollegeObj.getString("DBServer"));
                collegeOBj.setProductExpiryDate(Utility.convertStringToDate(jsonCollegeObj.getString("productExpiryDate")));
            }
        }
        catch (Exception e)
        {
            Log.e("decodeBatchDegResponse",e.getMessage());
        }

        return collegeOBj;
    }

    public Object decodeLoginResponse()
    {
        Entity_Staff staffObj=null;
        try
        {
            staffObj=new Entity_Staff();
            AppConfigure appConfigure=new AppConfigure();

            JSONArray responseArray=new JSONArray(jsonResponse);
            if(responseArray!=null || responseArray.length()>0)
            {
                JSONObject jsonStaff=responseArray.getJSONObject(0);

                staffObj.setStaffID(jsonStaff.getLong("userId"));
                staffObj.setStaffName(jsonStaff.getString("staffName"));
                staffObj.setPassword(jsonStaff.getString("password"));
                staffObj.setEmail(jsonStaff.getString("email"));
                staffObj.setContact(jsonStaff.getString("contact"));
                staffObj.setCreatedOn(Utility.convertStringToDate(jsonStaff.getString("createdOn")));
                staffObj.setLastModifiedOn(jsonStaff.getString("lastModified"));
                staffObj.setIsAccountActive(Utility.getIntEquivalentBoolean(jsonStaff.getInt("account_active")));

                appConfigure.setCurrentBatch(jsonStaff.getString("batch"));
                appConfigure.setCurrentDegree(jsonStaff.getString("degree"));

                staffObj.setAppConfigure(appConfigure);
            }
        }
       catch (Exception e)
       {
           Log.e("decodeStatsResponse", e.getMessage());
       }
        return staffObj;
    }

    public Object decodeCUDResponse()
    {
        int status=0;
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if(jsonObject!=null)
                status = jsonObject.getInt("status");
        }
        catch (Exception e)
        {
            Log.e("decodeStatsResponse", e.getMessage());
        }
        return status;
    }

    public Object decodeViewCompanyResponse()
    {
        Entity_Company entity_company;
        List<Entity_Company> compantList=null;
        try
        {
            compantList=new ArrayList<>();
            JSONArray responseArray=new JSONArray(jsonResponse);
            for(int i=0;i<responseArray.length();i++) {
                JSONObject companyObj = responseArray.getJSONObject(i);
                if (companyObj != null) {
                    entity_company = new Entity_Company();
                    entity_company.setCompID(companyObj.getLong("compId"));
                    entity_company.setCompDetailID(companyObj.getLong("compDetailsId"));
                    entity_company.setStaffID(companyObj.getLong("staffId"));
                    entity_company.setCompName(companyObj.getString("compName"));
                    entity_company.setHost(companyObj.getString("host"));
                    entity_company.setStatus(companyObj.getInt("status"));
                    entity_company.setType(companyObj.getInt("type"));
                    entity_company.setDOR(companyObj.getString("dor"));

                    compantList.add(entity_company);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("decodeCompanyResponse", e.getMessage());
        }
        return compantList;

    }

    public Object decodeStatsResponse()
    {
        StatisticsDTO statisticsDTO=null;
         try {
             statisticsDTO=new StatisticsDTO();

            JSONArray responseArray=new JSONArray(jsonResponse);
            JSONObject overAllOuterObj=responseArray.getJSONObject(0);
            JSONObject entityOuterObj=responseArray.getJSONObject(1);

            JSONArray overAllArray=overAllOuterObj.getJSONArray("overall");
            for (int i = 0; i < overAllArray.length(); i++) {
                JSONObject overAllObj=overAllArray.getJSONObject(i);
                statisticsDTO.setPlacedCount((Integer) overAllObj.get("placedCount"));
                statisticsDTO.setNonPlacedCount((Integer) overAllObj.get("nonPlacedCount"));
                statisticsDTO.setConfirmedCompCount((Integer) overAllObj.get("confirmedCompanyCount"));
                statisticsDTO.setUpcomingCompCount((Integer) overAllObj.get("upcomingCompanyCount"));
            }

            JSONArray entityArray=entityOuterObj.getJSONArray("Entity");
            JSONObject entityObj=entityArray.getJSONObject(0);

            statisticsDTO.setStudentBranchMap((validateJsonObject(entityObj.get("branchWise")))? null:getTreeMap(entityObj.getJSONArray("branchWise")));
            statisticsDTO.setStudentCompanyMap((validateJsonObject(entityObj.get("companyWise")))? null:getTreeMap(entityObj.getJSONArray("companyWise")));
            statisticsDTO.setMonthCompanyMap((validateJsonObject(entityObj.get("monthlyWiseComp")))? null:getTreeMap(entityObj.getJSONArray("monthlyWiseComp")));
            statisticsDTO.setMonthStudentMap((validateJsonObject(entityObj.get("monthlyWisePlaced")))? new TreeMap<String, Integer>():getTreeMap(entityObj.getJSONArray("monthlyWisePlaced")));
            statisticsDTO.setYearCompanyMap((validateJsonObject(entityObj.get("yearWiseCompany")))? new TreeMap<String, Integer>():getTreeMap(entityObj.getJSONArray("yearWiseCompany")));
            statisticsDTO.setYearStudentMap((validateJsonObject(entityObj.get("yearWisePlaced"))) ? null : getTreeMap(entityObj.getJSONArray("yearWisePlaced")));

        } catch (Exception e) {
             Log.e("decodeStatsResponse", e.getMessage());
         }
        return  statisticsDTO;
    }

    private boolean validateJsonObject(Object response)
    {
        return response.toString().equals("null");
    }

    private Map<String,Integer> getTreeMap(JSONArray mainArray)
    {
        Map<String, Integer> tempMap = null;
        try {
            if(mainArray !=null && mainArray.length()>0)
            {
                tempMap = new TreeMap<>();
                for (int i = 0; i < mainArray.length(); i++) {
                    JSONObject currentObj = mainArray.getJSONObject(i);
                    if(!validateJsonObject(currentObj.get("mKey")))
                         tempMap.put(currentObj.get("mKey").toString(), currentObj.getInt("mValue"));
                }
            }
        } catch (Exception e) {
            Log.e("getTreeMap()", e.getMessage());
        }
        return tempMap;
    }

    public Object decodeShortlistResponse()
    {
        Map<String,Object> shortlistMap=null;
        Map<String,Integer> dropDownMap;
        StringBuffer emailBuffer;
        StringBuffer smsBuffer;

        try {
            shortlistMap=new HashMap<>();
            dropDownMap=new TreeMap<>();
            emailBuffer=new StringBuffer();
            smsBuffer=new StringBuffer();
            JSONArray responseArray=new JSONArray(jsonResponse);
            int totalCount=responseArray.length();
            dropDownMap.put("ALL", totalCount);

            for(int i=0;i<responseArray.length();i++)
            {
                JSONObject studentObj=responseArray.getJSONObject(i);
                if(studentObj!=null)
                {
                    emailBuffer.append(studentObj.getString("email"))
                            .append(",");
                    smsBuffer.append(studentObj.getString("contact"))
                            .append(",");

                    String branch=studentObj.getString("branch");
                    int currentCount=  dropDownMap.containsKey(branch)?dropDownMap.get(branch):0;
                    dropDownMap.put(branch, currentCount+1);
                }
            }
            smsBuffer.replace(smsBuffer.length()-1, smsBuffer.length(), "");
            emailBuffer.replace(emailBuffer.length()-1, emailBuffer.length(), "");
            shortlistMap.put("SMS", smsBuffer.toString());
            shortlistMap.put("EMAIL", emailBuffer.toString());
            shortlistMap.put("DROPDOWN", dropDownMap);
        } catch (JSONException e) {
            Log.e("decodeShortlistResp() ",e.getMessage());
        }
        return shortlistMap;
    }

    public Object decodeNotificationLoad()
    {
        Map<Long,Entity_Notification> notificationMap=null;
        Entity_Notification notificationObj;
        try {
            JSONArray responseArray = new JSONArray(jsonResponse);
            JSONObject allNotificationsObj = responseArray.getJSONObject(0);
            JSONArray allNotificationsArray = allNotificationsObj.getJSONArray("allNotifications");
            if (allNotificationsArray != null && allNotificationsArray.length() > 0) {
                notificationMap = new LinkedHashMap<>();
                for (int i = 0; i < allNotificationsArray.length(); i++) {
                    JSONObject notificationChild = allNotificationsArray.getJSONObject(i);
                    notificationObj = new Entity_Notification();
                    notificationObj.setNotificationId(notificationChild.getLong("notificationId"));
                    notificationObj.setStaffName(notificationChild.getString("staffName"));
                    notificationObj.setCriteriaJSON(notificationChild.getString("criteria"));
                    notificationObj.setEligibleStudentMapJSON(notificationChild.getString("studentMap"));
                    notificationObj.setPlacementInfoJSON(notificationChild.getString("placementInfo"));
                    notificationObj.setNotificationDate(notificationChild.getString("notificationDate"));

                    notificationMap.put(notificationObj.getNotificationId(), notificationObj);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("decodeNotificationLoad",e.getMessage());
        }
        return notificationMap;
    }

    public Object decodeCriteriaFormResponse()
    {
        Map<String,Object> criteriaFormMap=null;
        List<String> branchList=null;
        Map<String,Entity_Company> companyMap=null;
        try
        {
           JSONArray responseArray=new JSONArray(jsonResponse);
           if(responseArray.length()>0) {
               criteriaFormMap=new TreeMap<>();
               JSONObject branchListObj = responseArray.getJSONObject(0);
               JSONObject companyDetailsListObj = responseArray.getJSONObject(1);

               if (branchListObj != null) {
                   JSONArray branchArray = branchListObj.getJSONArray("Branches");
                   branchList = new ArrayList<>();
                   for (int i = 0; i < branchArray.length(); i++) {
                       JSONObject branchObj = branchArray.getJSONObject(i);
                       if (branchObj != null)
                           branchList.add(branchObj.getString("branch"));
                   }
                   criteriaFormMap.put("BranchList",branchList);
               }

               if(companyDetailsListObj!=null)
               {
                   JSONArray companyArray = companyDetailsListObj.getJSONArray("Companies");
                   companyMap=new TreeMap<>();
                   for (int i = 0; i < companyArray.length(); i++) {
                       JSONObject companyObj = companyArray.getJSONObject(i);
                       if (companyObj != null) {
                           companyMap.put(companyObj.getString("companyName"),new Entity_Company(companyObj.getLong("detailsID"),companyObj.getString("companyName"),
                                   companyObj.getString("DOR")));
                       }
                   }
                   criteriaFormMap.put("CompanyMapJSON",Utility.convertObjectToJSON(companyMap));
               }

           }

        }catch (Exception e)
        {
            Log.e("decodCriteriaResponse()",e.getMessage());
        }

        return  criteriaFormMap;
    }

    public Object decodeBatchDegreeResponse()
    {
        Map<String,List<String>> batchdegreeMap=null;
        List degreeList;
        String batch;
        try {
            JSONArray responseArray = new JSONArray(jsonResponse);
            batchdegreeMap = new TreeMap<>();

            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject batchDegreeObj = responseArray.getJSONObject(i);
                if (batchDegreeObj != null) {
                    batch = batchDegreeObj.getString("BATCH");
                    degreeList = batchdegreeMap.containsKey(batch) ? batchdegreeMap.get(batch) : new ArrayList<>();
                    degreeList.add(batchDegreeObj.getString("DEGREE"));
                    batchdegreeMap.put(batch, degreeList);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("decodeBatchDegResponse",e.getMessage());
        }

        return  batchdegreeMap;
    }


}
