package com.cwiztech.promotion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.log.apiRequestLog;
import com.cwiztech.promotion.model.Promotion;
import com.cwiztech.promotion.model.PromotionProduct;
import com.cwiztech.promotion.repository.promotionRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/promotion")
public class promotionController {

	private static final Logger log = LoggerFactory.getLogger(promotionController.class);

	@Autowired
	private promotionRepository promotionrepository;

	
	// will only display the ones who are Active "Y"
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> get(@RequestHeader(value = "Authorization") String headToken,
			@RequestHeader(value = "LimitGrant") String LimitGrant)
					throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion", null, null, headToken);
		
		if (apiRequest.has("error")) {
			return new ResponseEntity<>(apiRequest.toString(), HttpStatus.OK);
		}

		List<Promotion> promotions = promotionrepository.findActive();
		String body = getAPIResponse(promotions, null, null, null, null, apiRequest, false);

		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	// will display every piece of data in database
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Promotion> promotions = promotionrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(promotions, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		Promotion promotion = promotionrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, promotion, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	

	// will give id's in body in form of array and it'll show data of respective id's
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotion/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> promotion_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonpromotions = jsonObj.getJSONArray("promotions");
		for (int i=0; i<jsonpromotions.length(); i++) {
			promotion_IDS.add((Integer) jsonpromotions.get(i));
		}
		List<Promotion> promotions = new ArrayList<Promotion>();
		if (jsonpromotions.length()>0)
			promotions = promotionrepository.findByIDs(promotion_IDS);
		
		return new ResponseEntity(getAPIResponse(promotions, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}
	
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotion/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("promotion_ID", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonPromotions, JSONObject jsonPromotion, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<Promotion> promotions = new ArrayList<Promotion>();
		if (jsonPromotion != null) {
			jsonPromotions = new JSONArray();
			jsonPromotions.put(jsonPromotion);
		}
		
		for (int i=0; i<jsonPromotion.length(); i++) {
			JSONObject jsonObj = jsonPromotions.getJSONObject(i);
			Promotion promotion = new  Promotion();
			long id=0; 
			
			if (jsonObj.has("PROMOTIONPRODCUT_ID")) {
				id = jsonObj.getLong("PROMOTIONPRODCUT_ID");
				if (id!=0) {
					promotion = promotionrepository.findOne(id);
				}
			}
			
			if (id == 0) {
				if (!jsonObj.has("PROMOTION_ID") ) {
					return new ResponseEntity(getAPIResponse(null, null , null, null, "PROMOTION_ID are missing", apiRequest, true), HttpStatus.OK);
				}
			}
			if (jsonObj.has("promotion_ID") && !jsonObj.isNull("promotion_ID"))
				promotion.setPROMOTION_ID(jsonObj.getLong("promotion_ID"));

			if (jsonObj.has("promotion_TITLE") && !jsonObj.isNull("promotion_TITLE"))
				promotion.setPROMOTION_TITLE(jsonObj.getString("promotion_TITLE"));

			if (jsonObj.has("promotion_DESCRIPTION") && !jsonObj.isNull("promotion_DESCRIPTION"))
				promotion.setPROMOTION_DESCRIPTION(jsonObj.getString("promotion_DESCRIPTION"));

			if (jsonObj.has("promotiontype_ID") && !jsonObj.isNull("promotiontype_ID"))
				promotion.setPROMOTIONTYPE_ID(jsonObj.getLong("promotiontype_ID"));

			if (jsonObj.has("discount_PERCENTAGE") && !jsonObj.isNull("discount_PERCENTAGE"))
				promotion.setDISCOUNT_PERCENTAGE(jsonObj.getLong("discount_PERCENTAGE"));

			if (jsonObj.has("promotionstart_DATE") && !jsonObj.isNull("promotionstart_DATE"))
				promotion.setPROMOTIONSTART_DATE(jsonObj.getString("promotionstart_DATE"));

			if (jsonObj.has("promotionend_DATE") && !jsonObj.isNull("promotionend_DATE"))
				promotion.setPROMOTIONEND_DATE(jsonObj.getString("promotionend_DATE"));

			if (jsonObj.has("isActive") && !jsonObj.isNull("isActive"))
				promotion.setISACTIVE(jsonObj.getString("isActive"));

			if (jsonObj.has("modified_BY") && !jsonObj.isNull("modified_BY"))
				promotion.setMODIFIED_BY(jsonObj.getLong("modified_BY"));

            if (jsonObj.has("modified_WHEN") && !jsonObj.isNull("modified_WHEN"))
                promotion.setMODIFIED_WHEN(jsonObj.getString("modified_WHEN"));

			if (jsonObj.has("modified_WORKSTATION") && !jsonObj.isNull("modified_WORKSTATION"))
				promotion.setMODIFIED_WORKSTATION(jsonObj.getString("modified_WORKSTATION"));

			
			
			if (id == 0)
				promotion.setISACTIVE("Y");
			else if (jsonObj.has("isactive") && !jsonObj.isNull("isactive"))
				promotion.setISACTIVE(jsonObj.getString("isactive"));
			
			promotion.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			promotion.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			promotion.setMODIFIED_WHEN(dateFormat1.format(date));

			promotions.add(promotion);
		}
		
	
		ResponseEntity responseentity;
		if (jsonPromotion != null)
			responseentity = new ResponseEntity(getAPIResponse(null, promotions.get(0), null, null, null, apiRequest, true).toString(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(promotions, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
		return responseentity;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		Promotion promotion = promotionrepository.findOne(id);
		promotionrepository.delete(promotion);
		
		return new ResponseEntity(getAPIResponse(null, promotion, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		JSONObject promotion = new JSONObject();
		promotion.put("id", id);
		promotion.put("isactive", "N");
		
		return insertupdateAll(null, promotion, apiRequest);
	}
	

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return BySearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotion/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<Promotion> promotions = ((active == true)
				? promotionrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: promotionrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(promotions, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotion/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		List<Promotion> promotions = new ArrayList<Promotion>();
		JSONObject jsonObj = new JSONObject(data);
        JSONArray searchObject = new JSONArray();

        boolean isWithDetail = true;
        if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
            isWithDetail = jsonObj.getBoolean("iswithdetail");
        }
        jsonObj.put("iswithdetail", false);
        
        long promotiontype_ID=0;
        List<Integer> promotiontype_IDS = new ArrayList<Integer>(); 
      
        promotiontype_IDS.add((int) 0);
        
        if (jsonObj.has("promotiontype_ID") && !jsonObj.isNull("promotiontype_ID") && jsonObj.getLong("promotiontype_ID") != 0) {
        	promotiontype_ID = jsonObj.getLong("promotiontype_ID");
        	promotiontype_IDS.add((int) promotiontype_ID);
        }
        else if (jsonObj.has("promotionType") && !jsonObj.isNull("promotionType") && jsonObj.getLong("promotionType") != 0) {
            if (active == true) {
                searchObject = new JSONArray(ServiceCall.POST("promotionType/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
            } else {
                searchObject = new JSONArray(ServiceCall.POST("promotiontype/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
            }

            promotiontype_ID = searchObject.length();
            for (int i=0; i<searchObject.length(); i++) {
            	promotiontype_IDS.add((int) searchObject.getJSONObject(i).getLong("promotion_ID"));
            }
        }
	
		if (promotiontype_ID != 0) {
		 promotions = ((active == true)
				? promotionrepository.findByAdvancedSearch(promotiontype_ID, promotiontype_IDS)
				: promotionrepository.findAllByAdvancedSearch(promotiontype_ID, promotiontype_IDS));
		}
		return new ResponseEntity(getAPIResponse(promotions, null, null, null, null, apiRequest, isWithDetail).toString(), HttpStatus.OK);
	}
	

	// getAPIrequest function
	String getAPIResponse(List<Promotion> promotions, Promotion promotion, JSONArray Jsonpromotions,
			JSONObject Jsonpromotion, String message, JSONObject apiRequest, boolean isWithDetail)
					throws JSONException, JsonProcessingException, ParseException {

		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse = "Invalid Response";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotion", message).toString();
		} else {
			if (promotion != null && isWithDetail) {
				rtnAPIResponse = mapper.writeValueAsString(promotion);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotions != null && isWithDetail) {
				rtnAPIResponse = mapper.writeValueAsString(promotions);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotion != null && !isWithDetail) {
				rtnAPIResponse = mapper.writeValueAsString(promotion);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotions != null && !isWithDetail) {
				rtnAPIResponse = mapper.writeValueAsString(promotions);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (Jsonpromotions != null) {
				rtnAPIResponse = Jsonpromotions.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	} 
}
