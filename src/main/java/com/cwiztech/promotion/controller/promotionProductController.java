package com.cwiztech.promotion.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.cwiztech.log.apiRequestLog;
import com.cwiztech.promotion.model.PromotionProduct;
import com.cwiztech.promotion.repository.promotionProductRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

@RestController
@CrossOrigin
@RequestMapping("/promotionproduct")
public class promotionProductController {
	private static final Logger log = LoggerFactory.getLogger(promotionProductController.class);

	private static final JSONTokener data = null;

	@Autowired
	private promotionProductRepository promotionproductrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionProduct> promotionproducts = promotionproductrepository.findActive();

		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true), HttpStatus.OK);

	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct/" + id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		PromotionProduct promotionproduct = promotionproductrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, promotionproduct, null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct/remove/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject promotionProduct = new JSONObject();
		promotionProduct.put("customer_ID", id);
		promotionProduct.put("isactive", "N");

		return insertupdateAll(null, promotionProduct, apiRequest);
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionProduct", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}
	 

	String getAPIResponse(List<PromotionProduct> promotionproducts, PromotionProduct promotionproduct, JSONArray Jsonpromotionproducts, JSONObject Jsonpromotionproduct, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";
		
		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotionproduct", message).toString();
		} else {
			if (promotionproduct != null && isWithDetail == true) {
				
				rtnAPIResponse = mapper.writeValueAsString(promotionproduct);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				
			} else if (promotionproducts != null && isWithDetail == true) {
				if (promotionproducts.size()>0) {
				}
				
				rtnAPIResponse = mapper.writeValueAsString(promotionproducts);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
            
	         } else if (promotionproduct != null && isWithDetail == false) {
					rtnAPIResponse = mapper.writeValueAsString(promotionproduct);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

	        } else if (promotionproducts != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(promotionproducts);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
	                
			} else if (Jsonpromotionproducts != null) {
				rtnAPIResponse = Jsonpromotionproducts.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (Jsonpromotionproduct != null) {
				rtnAPIResponse = mapper.writeValueAsString(Jsonpromotionproduct.toString());
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			}
		}
		
		return rtnAPIResponse;
	

	
	}
	
	List<PromotionProduct> promotionProducts = new ArrayList<PromotionProduct>();
	JSONObject jsonObj = new JSONObject(data);

	JSONArray searchObject = new JSONArray();
	List<Integer> customershop_IDS = new ArrayList<Integer>();
	List<Integer> saleorderpayment_IDS = new ArrayList<Integer>(); 
	List<Integer> creditmemo_IDS = new ArrayList<Integer>(); 

	customershop_IDS . add((int) 0);
	saleorderpayment_IDS.add((int) 0);
	creditmemo_IDS.add((int) 0);

	long customershop_ID = 0, saleorderpayment_ID = 0, creditmemo_ID = 0, promotionProductstatus_ID = 0, promotionProductdate = 0;
	String promotionProduct_DATEFROM="", promotionProduct_DATETO="";

	boolean isWithDetail = true;
	if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
		isWithDetail = jsonObj.getBoolean("iswithdetail");
	}
	jsonObj.put("iswithdetail", false);

	if (jsonObj.has("customershop_ID") && !jsonObj.isNull("customershop_ID") && jsonObj.getLong("customershop_ID") != 0) {
		customershop_ID = jsonObj.getLong("customershop_ID");
		customershop_IDS.add((int) customershop_ID);
	} else if (jsonObj.has("customershop") && !jsonObj.isNull("customershop") && jsonObj.getLong("customershop") != 0) {
		String headToken;
		if (isWithDetail == true) {
			searchObject = new JSONArray(ServiceCall.POST("customershop/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
		} else {
			searchObject = new JSONArray(ServiceCall.POST("customershop/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
		}

		customershop_ID = searchObject.length();
		for (int i=0; i<searchObject.length(); i++) {
			customershop_IDS.add((int) searchObject.getJSONObject(i).getLong("customershop_ID"));
		}
	}

	if (jsonObj.has("saleorderpayment_ID") && !jsonObj.isNull("saleorderpayment_ID") && jsonObj.getLong("saleorderpayment_ID") != 0) {
		saleorderpayment_ID = jsonObj.getLong("saleorderpayment_ID");
		saleorderpayment_IDS.add((int) saleorderpayment_ID);
	} else if (jsonObj.has("saleorderpayment") && !jsonObj.isNull("saleorderpayment") && jsonObj.getLong("saleorderpayment") != 0) {
		String headToken;
		if (isWithDetail == true) {
			searchObject = new JSONArray(ServiceCall.POST("saleorderpayment/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
		} else {
			searchObject = new JSONArray(ServiceCall.POST("saleorderpayment/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
		}

		saleorderpayment_ID = searchObject.length();
		for (int i=0; i<searchObject.length(); i++) {
			saleorderpayment_IDS.add((int) searchObject.getJSONObject(i).getLong("saleorderpayment_ID"));
		}
	}

	if (jsonObj.has("creditmemo_ID") && !jsonObj.isNull("creditmemo_ID") && jsonObj.getLong("creditmemo_ID") != 0) {
		creditmemo_ID = jsonObj.getLong("creditmemo_ID");
		creditmemo_IDS.add((int) creditmemo_ID);
	} else if (jsonObj.has("creditmemo") && !jsonObj.isNull("creditmemo") && jsonObj.getLong("creditmemo") != 0) {
		String headToken;
		if (isWithDetail == true) {
			searchObject = new JSONArray(ServiceCall.POST("creditmemo/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
		} else {
			searchObject = new JSONArray(ServiceCall.POST("creditmemo/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
		}

		creditmemo_ID = searchObject.length();
		for (int i=0; i<searchObject.length(); i++) {
			creditmemo_IDS.add((int) searchObject.getJSONObject(i).getLong("creditmemo_ID"));
		}
	}

	if (jsonObj.has("promotionProductstatus_ID") && !jsonObj.isNull("promotionProductstatus_ID"))
		promotionProductstatus_ID = jsonObj.getLong("promotionProductstatus_ID");
	
	else if (jsonObj.has("promotionProductstatus_CODE") && !jsonObj.isNull("promotionProductstatus_CODE")) {
		JSONObject promotionProductstatus = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'promotionProductSTATUS', code: "+jsonObj.getString("promotionProductstatus_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
		if (promotionProductstatus != null)
			promotionProductstatus_ID = promotionProductstatus.getLong("id");
	}

	if (jsonObj.has("promotionProduct_DATE") && !jsonObj.isNull("promotionProduct_DATE")) {
		promotionProductdate = 1;
		promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATE");
		promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATE");
	} else if (jsonObj.has("promotionProduct_DATEFROM") && !jsonObj.isNull("promotionProduct_DATEFROM") && jsonObj.has("promotionProduct_DATETO") && !jsonObj.isNull("promotionProduct_DATETO")) {
		promotionProductdate = 1;
		promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATEFROM");
		promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATETO");
	} else if (jsonObj.has("promotionProduct_DATEFROM") && !jsonObj.isNull("promotionProduct_DATEFROM")) {
		promotionProductdate = 1;
		promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATEFROM");
		promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATEFROM");
	} else if (jsonObj.has("promotionProduct_DATETO") && !jsonObj.isNull("promotionProduct_DATETO")) {
		promotionProductdate = 1;
		promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATETO");
		promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATETO");
	}

  }
}
/*

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> customer_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonCustomers = jsonObj.getJSONArray("promotionProducts");
		for (int i=0; i<jsonCustomers.length(); i++) {
			customer_IDS.add((Integer) jsonCustomers.get(i));
		}
		List<promotionProduct> promotionProducts = promotionProductRepository.findByIDs(customer_IDS);

		return new ResponseEntity(getAPIResponse(promotionProducts, null, null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionProduct/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("customer_ID", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity updateAll(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionProduct", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public ResponseEntity insertupdateAll(JSONArray jsonCustomers, JSONObject jsonpromotionProduct, JSONObject apiRequest) throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<PromotionProduct> promotionProducts = new ArrayList<PromotionProduct>();
		if (jsonpromotionProduct != null) {
			jsonCustomers = new JSONArray();
			jsonCustomers.put(jsonpromotionProduct);
		}

	oducts.add(promotionProduct);
		}
		
		adddded
		
		ResponseEntity responseentity;
		if (jsonpromotionProduct != null)
			responseentity = new ResponseEntity(getAPIResponse(null, promotionProducts.get(0), null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(promotionProducts, null, null, null, null, apiRequest, true), HttpStatus.OK);
		return responsee	for (int i=0; i<jsonCustomers.length(); i++) {
			JSONObject jsonObj = jsonCustomers.getJSONObject(i);
			PromotionProduct promotionProduct = new  PromotionProduct();
			long id=0; 
			
			if (jsonObj.has("promotionProduct_ID")) {
				id = jsonObj.getLong("promotionProduct_ID");
				if (id!=0) {
					promotionProduct = promotionProductRepository.findOne(id);
				}
			}

			if (id == 0) {
				if (!jsonObj.has("customershop_ID") && jsonObj.isNull("customershop_ID")) {
					return new ResponseEntity(getAPIResponse(null, null , null, null, "customershop_ID are missing", apiRequest, true), HttpStatus.OK);
				}
			}

			if (jsonObj.has("promotionProduct_DATE") && !jsonObj.isNull("promotionProduct_DATE"))
				promotionProduct.setpromotionProduct_DATE(jsonObj.getString("promotionProduct_DATE"));

			if (jsonObj.has("customershop_ID") && !jsonObj.isNull("customershop_ID"))
				promotionProduct.setCUSTOMERSHOP_ID(jsonObj.getLong("customershop_ID"));

			if (jsonObj.has("saleorderpayment_ID") && !jsonObj.isNull("saleorderpayment_ID"))
				promotionProduct.setSALEORDERPAYMENT_ID(jsonObj.getLong("saleorderpayment_ID"));

			if (jsonObj.has("creditmemo_ID") && !jsonObj.isNull("creditmemo_ID"))
				promotionProduct.setCREDITMEMO_ID(jsonObj.getLong("creditmemo_ID"));

			if (jsonObj.has("promotionProductstatus_ID") && !jsonObj.isNull("promotionProductstatus_ID"))
				promotionProduct.setpromotionProductSTATUS_ID(jsonObj.getLong("promotionProductstatus_ID"));
			else if (jsonObj.has("promotionProductstatus_CODE") && !jsonObj.isNull("promotionProductstatus_CODE")) {
				JSONObject promotionProductstatus = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'promotionProductSTATUS', code: "+jsonObj.getString("promotionProductstatus_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
				if (promotionProductstatus != null)
					promotionProduct.setpromotionProductSTATUS_ID(promotionProductstatus.getLong("id"));
			}

			if (id == 0)
				promotionProduct.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				promotionProduct.setISACTIVE(jsonObj.getString("isactive"));

			promotionProduct.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			promotionProduct.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			promotionProduct.setMODIFIED_WHEN(dateFormat1.format(date));

			promotionProduct = promotionProductRepository.saveAndFlush(promotionProduct);
			promotionPrntity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("DELETE", "/promotionProduct/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		promotionProduct promotionProduct = promotionProductRepository.findOne(id);
		promotionProductRepository.delete(promotionProduct);

		return new ResponseEntity(getAPIResponse(null, promotionProduct, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JSONException, ParseException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct/remove/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject promotionProduct = new JSONObject();
		promotionProduct.put("customer_ID", id);
		promotionProduct.put("isactive", "N");

		return insertupdateAll(null, promotionProduct, apiRequest);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return BySearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return BySearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionProduct/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<promotionProduct> promotionProducts = ((active == true)
				? promotionProductRepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: promotionProductRepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(promotionProducts, null, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return ByAdvancedSearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return ByAdvancedSearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionProduct/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<promotionProduct> promotionProducts = new ArrayList<promotionProduct>();
		JSONObject jsonObj = new JSONObject(data);

		JSONArray searchObject = new JSONArray();
		List<Integer> customershop_IDS = new ArrayList<Integer>();
		List<Integer> saleorderpayment_IDS = new ArrayList<Integer>(); 
		List<Integer> creditmemo_IDS = new ArrayList<Integer>(); 

		customershop_IDS.add((int) 0);
		saleorderpayment_IDS.add((int) 0);
		creditmemo_IDS.add((int) 0);

		long customershop_ID = 0, saleorderpayment_ID = 0, creditmemo_ID = 0, promotionProductstatus_ID = 0, promotionProductdate = 0;
		String promotionProduct_DATEFROM="", promotionProduct_DATETO="";

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

		if (jsonObj.has("customershop_ID") && !jsonObj.isNull("customershop_ID") && jsonObj.getLong("customershop_ID") != 0) {
			customershop_ID = jsonObj.getLong("customershop_ID");
			customershop_IDS.add((int) customershop_ID);
		} else if (jsonObj.has("customershop") && !jsonObj.isNull("customershop") && jsonObj.getLong("customershop") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("customershop/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("customershop/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			customershop_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				customershop_IDS.add((int) searchObject.getJSONObject(i).getLong("customershop_ID"));
			}
		}

		if (jsonObj.has("saleorderpayment_ID") && !jsonObj.isNull("saleorderpayment_ID") && jsonObj.getLong("saleorderpayment_ID") != 0) {
			saleorderpayment_ID = jsonObj.getLong("saleorderpayment_ID");
			saleorderpayment_IDS.add((int) saleorderpayment_ID);
		} else if (jsonObj.has("saleorderpayment") && !jsonObj.isNull("saleorderpayment") && jsonObj.getLong("saleorderpayment") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("saleorderpayment/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("saleorderpayment/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			saleorderpayment_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				saleorderpayment_IDS.add((int) searchObject.getJSONObject(i).getLong("saleorderpayment_ID"));
			}
		}

		if (jsonObj.has("creditmemo_ID") && !jsonObj.isNull("creditmemo_ID") && jsonObj.getLong("creditmemo_ID") != 0) {
			creditmemo_ID = jsonObj.getLong("creditmemo_ID");
			creditmemo_IDS.add((int) creditmemo_ID);
		} else if (jsonObj.has("creditmemo") && !jsonObj.isNull("creditmemo") && jsonObj.getLong("creditmemo") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("creditmemo/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("creditmemo/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			creditmemo_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				creditmemo_IDS.add((int) searchObject.getJSONObject(i).getLong("creditmemo_ID"));
			}
		}

		if (jsonObj.has("promotionProductstatus_ID") && !jsonObj.isNull("promotionProductstatus_ID"))
			promotionProductstatus_ID = jsonObj.getLong("promotionProductstatus_ID");
		else if (jsonObj.has("promotionProductstatus_CODE") && !jsonObj.isNull("promotionProductstatus_CODE")) {
			JSONObject promotionProductstatus = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'promotionProductSTATUS', code: "+jsonObj.getString("promotionProductstatus_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
			if (promotionProductstatus != null)
				promotionProductstatus_ID = promotionProductstatus.getLong("id");
		}

		if (jsonObj.has("promotionProduct_DATE") && !jsonObj.isNull("promotionProduct_DATE")) {
			promotionProductdate = 1;
			promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATE");
			promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATE");
		} else if (jsonObj.has("promotionProduct_DATEFROM") && !jsonObj.isNull("promotionProduct_DATEFROM") && jsonObj.has("promotionProduct_DATETO") && !jsonObj.isNull("promotionProduct_DATETO")) {
			promotionProductdate = 1;
			promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATEFROM");
			promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATETO");
		} else if (jsonObj.has("promotionProduct_DATEFROM") && !jsonObj.isNull("promotionProduct_DATEFROM")) {
			promotionProductdate = 1;
			promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATEFROM");
			promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATEFROM");
		} else if (jsonObj.has("promotionProduct_DATETO") && !jsonObj.isNull("promotionProduct_DATETO")) {
			promotionProductdate = 1;
			promotionProduct_DATEFROM = jsonObj.getString("promotionProduct_DATETO");
			promotionProduct_DATETO = jsonObj.getString("promotionProduct_DATETO");
		}

		if (customershop_ID != 0 || saleorderpayment_ID != 0 || creditmemo_ID != 0 || promotionProductstatus_ID != 0 || promotionProductdate != 0) {
			promotionProducts = ((active == true)
					? promotionProductRepository.findByAdvancedSearch(customershop_ID, customershop_IDS, saleorderpayment_ID, saleorderpayment_IDS, 
							creditmemo_ID, creditmemo_IDS, promotionProductstatus_ID, promotionProductdate, promotionProduct_DATEFROM, promotionProduct_DATETO)
							: promotionProductRepository.findAllByAdvancedSearch(customershop_ID, customershop_IDS, saleorderpayment_ID, saleorderpayment_IDS, creditmemo_ID, creditmemo_IDS, promotionProductstatus_ID, promotionProductdate, promotionProduct_DATEFROM, promotionProduct_DATETO));
		}
		return new ResponseEntity(getAPIResponse(promotionProducts, null, null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<promotionProduct> promotionProducts, promotionProduct promotionProduct, JSONArray jsonCustomers, JSONObject jsonpromotionProduct, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotionProduct", message).toString();
		} else {
			if (promotionProduct != null && isWithDetail == true) {
				List<Integer> lookupList = new ArrayList<Integer>();
				if (promotionProduct.getpromotionProductSTATUS_ID() != null) {
					lookupList.add(Integer.parseInt(promotionProduct.getpromotionProductSTATUS_ID().toString()));
				}
				
		        CompletableFuture<JSONArray> lookupFuture = CompletableFuture.supplyAsync(() -> {
					try {
						return new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+lookupList+"}", apiRequest.getString("access_TOKEN"), true));
					} catch (JSONException | JsonProcessingException | ParseException e) {
						e.printStackTrace();
					    return new JSONArray();
					}
				});
		        
		        CompletableFuture<JSONObject> customershopFuture = CompletableFuture.supplyAsync(() -> {
						try {
							return new JSONObject(ServiceCall.GET("customershop/"+promotionProduct.getCUSTOMERSHOP_ID(), apiRequest.getString("access_TOKEN"), false));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
						    return new JSONObject();
						}
				});
				
		        CompletableFuture<JSONObject> saleorderpaymentFuture = CompletableFuture.supplyAsync(() -> {
						try {
							return new JSONObject(ServiceCall.GET("saleorderpayment/"+promotionProduct.getSALEORDERPAYMENT_ID(), apiRequest.getString("access_TOKEN"), false));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
						    return new JSONObject();
						}
				});
				
		        CompletableFuture<JSONObject> creditmemoFuture = CompletableFuture.supplyAsync(() -> {
						try {
							return new JSONObject(ServiceCall.GET("creditmemo/"+promotionProduct.getCREDITMEMO_ID(), apiRequest.getString("access_TOKEN"), false));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
						    return new JSONObject();
						}
				});
				
		        // Wait until all futures complete
		        CompletableFuture<Void> allDone =
		                CompletableFuture.allOf(customershopFuture, saleorderpaymentFuture, creditmemoFuture, lookupFuture);

		        // Block until all are done
		        allDone.join();

				promotionProduct.setCUSTOMERSHOP_DETAIL(customershopFuture.get().toString());
				promotionProduct.setSALEORDERPAYMENT_DETAIL(saleorderpaymentFuture.get().toString());
				promotionProduct.setCREDITMEMO_DETAIL(creditmemoFuture.get().toString());

				JSONArray lookupObject = lookupFuture.get();	
				for (int j=0; j<lookupObject.length(); j++) {
					JSONObject lookup = lookupObject.getJSONObject(j);
					if (promotionProduct.getpromotionProductSTATUS_ID() != null && promotionProduct.getpromotionProductSTATUS_ID() == lookup.getLong("id") ) {
						promotionProduct.setpromotionProductSTATUS_DETAIL(lookup.toString());
					}
				}	

				rtnAPIResponse = mapper.writeValueAsString(promotionProduct);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotionProducts != null && isWithDetail == true) {
				if (promotionProducts.size()>0) {
					List<Integer> saleorderpaymentList = new ArrayList<Integer>();
					List<Integer> customershopList = new ArrayList<Integer>();
					List<Integer> creditmemoList = new ArrayList<Integer>();
					List<Integer> lookupList = new ArrayList<Integer>();

					for (int i=0; i<promotionProducts.size(); i++) {
						if (promotionProducts.get(i).getCUSTOMERSHOP_ID() != null) {
							customershopList.add(Integer.parseInt(promotionProducts.get(i).getCUSTOMERSHOP_ID().toString()));
						}
						if (promotionProducts.get(i).getSALEORDERPAYMENT_ID() != null) {
							saleorderpaymentList.add(Integer.parseInt(promotionProducts.get(i).getSALEORDERPAYMENT_ID().toString()));
						}
						if (promotionProducts.get(i).getCREDITMEMO_ID() != null) {
							creditmemoList.add(Integer.parseInt(promotionProducts.get(i).getCREDITMEMO_ID().toString()));
						}
						if (promotionProducts.get(i).getpromotionProductSTATUS_ID() != null) {
							lookupList.add(Integer.parseInt(promotionProducts.get(i).getpromotionProductSTATUS_ID().toString()));
						}
						if (promotionProducts.get(i).getpromotionProductSTATUS_ID() != null) {
							lookupList.add(Integer.parseInt(promotionProducts.get(i).getpromotionProductSTATUS_ID().toString()));
						}
					}

			        CompletableFuture<JSONArray> lookupFuture = CompletableFuture.supplyAsync(() -> {
						try {
							return new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+lookupList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
						    return new JSONArray();
						}
					});
			        
			        CompletableFuture<JSONArray> customershopFuture = CompletableFuture.supplyAsync(() -> {
							try {
								return new JSONArray(ServiceCall.POST("customershop/ids", "{customershops: "+customershopList+"}", apiRequest.getString("access_TOKEN"), false));
							} catch (JSONException | JsonProcessingException | ParseException e) {
								e.printStackTrace();
							    return new JSONArray();
							}
					});
					
			        CompletableFuture<JSONArray> saleorderpaymentFuture = CompletableFuture.supplyAsync(() -> {
							try {
								return new JSONArray(ServiceCall.POST("saleorderpayment/ids", "{companies: "+saleorderpaymentList+"}", apiRequest.getString("access_TOKEN"), false));
							} catch (JSONException | JsonProcessingException | ParseException e) {
								e.printStackTrace();
							    return new JSONArray();
							}
					});
					
			        CompletableFuture<JSONArray> creditmemoFuture = CompletableFuture.supplyAsync(() -> {
							try {
								return new JSONArray(ServiceCall.POST("creditmemo/ids", "{creditmemos: "+creditmemoList+"}", apiRequest.getString("access_TOKEN"), false));
							} catch (JSONException | JsonProcessingException | ParseException e) {
								e.printStackTrace();
							    return new JSONArray();
							}
					});
					
			        // Wait until all futures complete
			        CompletableFuture<Void> allDone =
			                CompletableFuture.allOf(customershopFuture, saleorderpaymentFuture, creditmemoFuture, lookupFuture);

			        // Block until all are done
			        allDone.join();

					JSONArray customershopObject = customershopFuture.get();
					JSONArray saleorderpaymentObject = saleorderpaymentFuture.get();
					JSONArray creditmemoObject = creditmemoFuture.get();
					JSONArray lookups = lookupFuture.get();

					for (int i=0; i<promotionProducts.size(); i++) {
						for (int j=0; j<customershopObject.length(); j++) {
							JSONObject customershop = customershopObject.getJSONObject(j);
							if (promotionProducts.get(i).getCUSTOMERSHOP_ID() != null && promotionProducts.get(i).getCUSTOMERSHOP_ID() == customershop.getLong("customershop_ID") ) {
								promotionProducts.get(i).setCUSTOMERSHOP_DETAIL(customershop.toString());
							}
						}
						for (int j=0; j<saleorderpaymentObject.length(); j++) {
							JSONObject saleorderpayment = saleorderpaymentObject.getJSONObject(j);
							if (promotionProducts.get(i).getSALEORDERPAYMENT_ID() != null && promotionProducts.get(i).getSALEORDERPAYMENT_ID() == saleorderpayment.getLong("saleorderpayment_ID") ) {
								promotionProducts.get(i).setSALEORDERPAYMENT_DETAIL(saleorderpayment.toString());
							}
						}

						for (int j=0; j<creditmemoObject.length(); j++) {
							JSONObject creditmemo = creditmemoObject.getJSONObject(j);
							if (promotionProducts.get(i).getCREDITMEMO_ID() != null && promotionProducts.get(i).getCREDITMEMO_ID() == creditmemo.getLong("creditmemo_ID") ) {
								promotionProducts.get(i).setCREDITMEMO_DETAIL(creditmemo.toString());
							}
						}
						for (int j=0; j<lookups.length(); j++) {
							if (promotionProducts.get(i).getpromotionProductSTATUS_ID() != null && promotionProducts.get(i).getpromotionProductSTATUS_ID() == lookups.getJSONObject(j).getLong("id") ) {
								promotionProducts.get(i).setpromotionProductSTATUS_DETAIL(lookups.getJSONObject(j).toString());
							}
						}
					}
				}
				
				rtnAPIResponse = mapper.writeValueAsString(promotionProducts);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotionProduct != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(promotionProduct);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotionProducts != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(promotionProducts);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonCustomers != null) {
				rtnAPIResponse = jsonCustomers.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonpromotionProduct != null) {
				rtnAPIResponse = jsonpromotionProduct.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
}*/