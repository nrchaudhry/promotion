package com.cwiztech.promotion.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cwiztech.promotion.model.PromotionBuyGet;
import com.cwiztech.promotion.model.PromotionProduct;
import com.cwiztech.promotion.repository.promotionBuyGetRepository;
import com.cwiztech.promotion.repository.promotionProductRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

@RestController
@CrossOrigin
@RequestMapping("/promotionbuyget")
public class promotionBuyGetController<promotionbutget> {
	private static final Logger log = LoggerFactory.getLogger(promotionBuyGetController.class);

	@Autowired
	private promotionBuyGetRepository promotionbuygetrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionproduct", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionBuyGet> promotionbuygets = promotionbuygetrepository.findActive();

		return new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionbuyget/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionBuyGet> promotionbuygets = promotionbuygetrepository.findAll();

		return new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionbuyget/" + id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		PromotionBuyGet promotionbuyget = promotionbuygetrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, promotionbuyget, null, null, null, apiRequest, true), HttpStatus.OK);
	}
	// will give id's in body in form of array and it'll show data of that id's
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionbuyget/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> PROMOTIONBUYGET_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonpromotionbuygets = jsonObj.getJSONArray("promotionbuygets");
		for (int i=0; i<jsonpromotionbuygets.length(); i++) {
			PROMOTIONBUYGET_IDS.add((Integer) jsonpromotionbuygets.get(i));
		}

		List<PromotionBuyGet> promotionbuygets = new ArrayList<PromotionBuyGet>();   
		if (jsonpromotionbuygets.length()>0)

			promotionbuygets = promotionbuygetrepository.findByIDs(PROMOTIONBUYGET_IDS);


		return new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionbuyget", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity updateAll(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionbuyget", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionbuyget/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("PROMOTIONBUYGET_ID", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonpromotionbuygets, JSONObject jsonpromotionbuyget, JSONObject apiRequest) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<PromotionBuyGet> promotionbuygets = new ArrayList<PromotionBuyGet>();
		if (jsonpromotionbuyget!= null) {
			jsonpromotionbuygets = new JSONArray();
			jsonpromotionbuygets.put(jsonpromotionbuyget);
		}

		for (int i=0; i<jsonpromotionbuyget.length(); i++) {
			JSONObject jsonObj = jsonpromotionbuygets.getJSONObject(i);
			PromotionBuyGet promotionbuyget = new  PromotionBuyGet();
			long id=0; 

			if (jsonObj.has("PROMOTIONBUYGET_ID")) {
				id = jsonObj.getLong("PROMOTIONBUYGET_ID");
				if (id!=0) {
					promotionbuyget = promotionbuygetrepository.findOne(id);
				}
			}

			if (id == 0) {
				if (!jsonObj.has("PROMOTIONBUYGET_ID") && jsonObj.isNull("PROMOTIONBUYGET_ID")) {
					return new ResponseEntity(getAPIResponse(null, null , null, null, "PROMOTIONBUYGET_ID are missing", apiRequest, true), HttpStatus.OK);
				}
			}

			if (jsonObj.has("promotion_ID") && !jsonObj.isNull("promotion_ID"))
				promotionbuyget.setPROMOTION_ID(jsonObj.getLong("promotion_ID"));

			if (jsonObj.has("buyproduct_ID") && !jsonObj.isNull("buyproduct_ID"))
				promotionbuyget.setBUYPRODUCT_ID(jsonObj.getLong("buyproduct_ID"));

			if (jsonObj.has("buy_QUANTITY") && !jsonObj.isNull("buy_QUANTITY"))
				promotionbuyget.setBUY_QUANTITY(BigDecimal.valueOf(jsonObj.getDouble("buy_QUANTITY")));
			else if (id == 0)
				promotionbuyget.setBUY_QUANTITY(BigDecimal.valueOf(0.0));

			if (jsonObj.has("getproduct_ID") && !jsonObj.isNull("getproduct_ID"))
				promotionbuyget.setGETPRODUCT_ID(jsonObj.getLong("getproduct_ID"));
			
			if (jsonObj.has("GET_QUANTITY") && !jsonObj.isNull("GET_QUANTITY"))
				promotionbuyget.setGET_QUANTITY(BigDecimal.valueOf(jsonObj.getDouble("GET_QUANTITY")));
			else if (id == 0)
				promotionbuyget.setBUY_QUANTITY(BigDecimal.valueOf(0.0));

			if (id == 0)
				promotionbuyget.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				promotionbuyget.setISACTIVE(jsonObj.getString("isactive"));

			promotionbuyget.setMODIFIED_BY(apiRequest.getString("request_ID"));
			promotionbuyget.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			promotionbuyget.setMODIFIED_WHEN(dateFormat1.format(date));

			promotionbuyget = promotionbuygetrepository.saveAndFlush(promotionbuyget);
			promotionbuygets.add(promotionbuyget);

		}

		ResponseEntity responseentity;
		if (jsonpromotionbuyget != null)
			responseentity = new ResponseEntity(getAPIResponse(null, promotionbuygets.get(0), null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	// we delete the id that we enter  in api 
	// first we find & then we delete
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionbuyget/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		PromotionBuyGet promotionbuyget = promotionbuygetrepository.findOne(id);
		promotionbuygetrepository.delete(promotionbuyget);

		return new ResponseEntity(getAPIResponse(null, promotionbuyget, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	//update the list  to remove the given id and make it non-active
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionbuyget/remove/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject promotionBuyGet= new JSONObject();
		promotionBuyGet.put("PROMOTIONBUYGET_ID", id);
		promotionBuyGet.put("isactive", "N");


		return insertupdateAll(null, promotionBuyGet, apiRequest);
	}

	/*
	// Calls a common method BySearch()
	// true means → fetch only active records
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		return BySearch(data, true, headToken, LimitGrant);
	}

	// Calls same logic as /search
	// false means: (active + inactive records )
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		return BySearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionbuyget/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		
		// If active == true  ,  Calls findBySearch() → active records only
		// Else  ,   Calls findAllBySearch() → all records

		List<PromotionBuyGet> promotionbuygets = ((active == true)

				? promotionbuygetrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: promotionbuygetrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}
*/
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionproduct/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionBuyGet> promotionbuygets = new ArrayList<PromotionBuyGet>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

		long promotion_ID=0, buyproduct_ID=0,getproduct_ID=0;
		List<Integer> promotion_IDS = new ArrayList<Integer>(); 
		List<Integer> buyproduct_IDS = new ArrayList<Integer>(); 
		List<Integer> getproduct_IDS = new ArrayList<Integer>(); 

		promotion_IDS.add((int) 0);
		buyproduct_IDS.add((int) 0);
		getproduct_IDS.add((int) 0);

		if (jsonObj.has("promotion_ID") && !jsonObj.isNull("promotion_ID") && jsonObj.getLong("promotion_ID") != 0) {
			promotion_ID = jsonObj.getLong("promotion_ID");
			promotion_IDS.add((int) promotion_ID);

		}  else if (jsonObj.has("promotion") && !jsonObj.isNull("promotion") && jsonObj.getLong("promotion") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("promotion/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("promotion/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			promotion_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				promotion_IDS.add((int) searchObject.getJSONObject(i).getLong("promotion_ID"));
			}
		}

		if (jsonObj.has("buyproduct_ID") && !jsonObj.isNull("buyproduct_ID") && jsonObj.getLong("buyproduct_ID") != 0) {
			buyproduct_ID = jsonObj.getLong("buyproduct_ID");
			buyproduct_IDS.add((int) buyproduct_ID);

		}  else if (jsonObj.has("buyproduct") && !jsonObj.isNull("buyproduct") && jsonObj.getLong("buyproduct") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			buyproduct_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				buyproduct_IDS.add((int) searchObject.getJSONObject(i).getLong("buyproduct_ID"));
			}
		}
		if (jsonObj.has("getproduct_ID") && !jsonObj.isNull("getproduct_ID") && jsonObj.getLong("getproduct_ID") != 0) {
			getproduct_ID = jsonObj.getLong("product_ID");
			getproduct_IDS.add((int) getproduct_ID);

		}  else if (jsonObj.has("getproduct") && !jsonObj.isNull("getproduct") && jsonObj.getLong("getproduct") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			getproduct_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				getproduct_IDS.add((int) searchObject.getJSONObject(i).getLong("getproduct_ID"));
			}
		}
		
		
		if (promotion_ID != 0 || buyproduct_ID != 0 || getproduct_ID != 0) {
			promotionbuygets = ((active == true)
					? promotionbuygetrepository.findByAdvancedSearch(promotion_ID, promotion_IDS, buyproduct_ID, buyproduct_IDS, getproduct_ID, getproduct_IDS)
							: promotionbuygetrepository.findAllByAdvancedSearch(promotion_ID, promotion_IDS, buyproduct_ID, buyproduct_IDS, getproduct_ID, getproduct_IDS));
		}
		return new ResponseEntity(getAPIResponse(promotionbuygets, null, null, null, null, apiRequest, isWithDetail).toString(), HttpStatus.OK);
	}

	//getAPI response Function
	String getAPIResponse(List<PromotionBuyGet> promotionbuygets, PromotionBuyGet promotionbuyget, JSONArray Jsonpromotionbuygets, JSONObject Jsonpromotionbuyget, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotionproduct", message).toString();
		} else  {
			if ((promotionbuygets != null || promotionbuyget != null) && isWithDetail == true) {
				if (promotionbuyget != null) {
					promotionbuygets= new ArrayList<PromotionBuyGet>();
					promotionbuygets.add(promotionbuyget);
				}
				if (promotionbuygets.size()>0) {
					List<Integer> promotionList = new ArrayList<Integer>();
					List<Integer> buyproductList = new ArrayList<Integer>();
					List<Integer> getproductList = new ArrayList<Integer>();


					for (int i=0; i<promotionbuygets.size(); i++) {
						if (promotionbuygets.get(i).getPROMOTION_ID() != null) {
							promotionList.add(Integer.parseInt(promotionbuygets.get(i).getPROMOTION_ID().toString()));
						}
						if (promotionbuygets.get(i).getBUYPRODUCT_ID() != null) {
							buyproductList.add(Integer.parseInt(promotionbuygets.get(i).getBUYPRODUCT_ID().toString()));
						}
						if (promotionbuygets.get(i).getGETPRODUCT_ID() != null) {
							getproductList.add(Integer.parseInt(promotionbuygets.get(i).getGETPRODUCT_ID().toString()));
						}
					}

					CompletableFuture<JSONArray> promotionFuture = CompletableFuture.supplyAsync(() -> {
						if (promotionList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("promotion/ids", "{promotions: "+promotionList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					CompletableFuture<JSONArray> buyproductFuture = CompletableFuture.supplyAsync(() -> {
						if (buyproductList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("buyproduct/ids", "{buyproducts: "+buyproductList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});
					CompletableFuture<JSONArray> getproductFuture = CompletableFuture.supplyAsync(() -> {
						if (getproductList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("getproduct/ids", "{getproducts: "+getproductList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});
					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(promotionFuture,buyproductFuture,getproductFuture);

					// Block until all are done
					allDone.join();

					JSONArray  promotionObject = promotionFuture.get();
					JSONArray buyproductObject = buyproductFuture.get();
					JSONArray getproductObject = getproductFuture.get();

					for (int i=0; i<promotionbuygets.size(); i++) {
						for (int j=0; j<promotionObject.length(); j++) {
							JSONObject promotion = promotionObject.getJSONObject(j);
							if (promotionbuygets.get(i).getPROMOTION_ID() != null && promotionbuygets.get(i).getPROMOTION_ID() == promotion.getLong("PROMOTION_ID") ) {
								promotionbuygets.get(i).setPROMOTION_DETAIL(promotion.toString());
							}
						}
						for (int j=0; j<buyproductObject.length(); j++) {
							JSONObject buyproduct = buyproductObject.getJSONObject(j);
							if (promotionbuygets.get(i).getBUYPRODUCT_ID() != null && promotionbuygets.get(i).getBUYPRODUCT_ID() == buyproduct.getLong("BUYPRODUCT_ID") ) {
								promotionbuygets.get(i).setBUYPRODUCT_DETAIL(buyproduct.toString());
							}
						}
						for (int j=0; j<getproductObject.length(); j++) {
							JSONObject getproduct = getproductObject.getJSONObject(j);
							if (promotionbuygets.get(i).getGETPRODUCT_ID() != null && promotionbuygets.get(i).getGETPRODUCT_ID() == getproduct.getLong("GETPRODUCT_ID") ) {
								promotionbuygets.get(i).setGETPRODUCT_DETAIL(getproduct.toString());
							}
						}
					}
				}

				if (promotionbuyget != null)
					rtnAPIResponse = mapper.writeValueAsString(promotionbuygets.get(0));
				else
					rtnAPIResponse = mapper.writeValueAsString(promotionbuygets);

				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotionbuyget != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(promotionbuyget);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (promotionbuygets != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(promotionbuygets);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (Jsonpromotionbuygets != null) {
				rtnAPIResponse = Jsonpromotionbuygets.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (Jsonpromotionbuyget != null) {
				rtnAPIResponse = mapper.writeValueAsString(Jsonpromotionbuyget.toString());
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
}

