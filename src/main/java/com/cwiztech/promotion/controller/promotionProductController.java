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

import com.cwiztech.promotion.model.PromotionProduct;
import com.cwiztech.promotion.repository.promotionProductRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

@RestController
@CrossOrigin
@RequestMapping("/promotionproduct")
public class promotionProductController<promotionproduct> {
	private static final Logger log = LoggerFactory.getLogger(promotionProductController.class);

	@Autowired
	private promotionProductRepository promotionproductrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionproduct", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionProduct> promotionproducts = promotionproductrepository.findActive();

		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionproduct/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionProduct> promotionproducts = promotionproductrepository.findAll();

		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionproduct/" + id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		PromotionProduct promotionproduct = promotionproductrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, promotionproduct, null, null, null, apiRequest, true), HttpStatus.OK);
	}
	// will give id's in body in form of array and it'll show data of that id's
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionproduct/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> promotionproduct_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonpromotionproducts = jsonObj.getJSONArray("promotionproducts");
		for (int i=0; i<jsonpromotionproducts.length(); i++) {
			promotionproduct_IDS.add((Integer) jsonpromotionproducts.get(i));
		}

		List<PromotionProduct> promotionproducts = new ArrayList<PromotionProduct>();   
		if (jsonpromotionproducts.length()>0)

			promotionproducts = promotionproductrepository.findByIDs(promotionproduct_IDS);


		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionproduct", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity updateAll(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionproduct", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/promotionproduct/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("promotionproduct_ID", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public ResponseEntity insertupdateAll(JSONArray jsonpromotionproducts, JSONObject jsonpromotionproduct, JSONObject apiRequest) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<PromotionProduct> promotionproducts = new ArrayList<PromotionProduct>();
		if (jsonpromotionproduct!= null) {
			jsonpromotionproducts = new JSONArray();
			jsonpromotionproducts.put(jsonpromotionproduct);
		}

		for (int i=0; i<jsonpromotionproduct.length(); i++) {
			JSONObject jsonObj = jsonpromotionproducts.getJSONObject(i);
			PromotionProduct promotionproduct = new  PromotionProduct();
			long id=0; 

			if (jsonObj.has("promotionprodcut_ID")) {
				id = jsonObj.getLong("promotionprodcut_ID");
				if (id!=0) {
					promotionproduct = promotionproductrepository.findOne(id);
				}
			}

			if (id == 0) {
				if (!jsonObj.has("promotionprodcut_ID") && jsonObj.isNull("promotionprodcut_ID")) {
					return new ResponseEntity(getAPIResponse(null, null , null, null, "promotionprodcut_ID are missing", apiRequest, true), HttpStatus.OK);
				}
			}

			if (jsonObj.has("promotion_ID") && !jsonObj.isNull("promotion_ID"))
				promotionproduct.setPROMOTION_ID(jsonObj.getLong("promotion_ID"));

			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
				promotionproduct.setPRODUCT_ID(jsonObj.getLong("product_ID"));
			
			if (jsonObj.has("PRODUCTCATEGORY_ID") && !jsonObj.isNull("PRODUCTCATEGORY_ID"))
				promotionproduct.setPRODUCTCATEGORY_ID(jsonObj.getLong("PRODUCTCATEGORY_ID"));

			if (jsonObj.has("promotionproduct_PRICE") && !jsonObj.isNull("promotionproduct_PRICE"))
				promotionproduct.setPROMOTIONPRODUCT_PRICE(jsonObj.getDouble("promotionproduct_PRICE"));

			if (jsonObj.has("quantity_REQUIRED") && !jsonObj.isNull("quantity_REQUIRED"))
				promotionproduct.setQUANTITY_REQUIRED(jsonObj.getDouble("quantity_REQUIRED"));

			if (jsonObj.has("quantity_BONUS") && !jsonObj.isNull("quantity_BONUS"))
				promotionproduct.setQUANTITY_BONUS(jsonObj.getDouble("quantity_BONUS"));

			if (jsonObj.has("maxpurchase_LIMIT") && !jsonObj.isNull("maxpurchase_LIMIT"))
				promotionproduct.setMAXPURCHASE_LIMIT(jsonObj.getLong("maxpurchase_LIMIT"));


			if (jsonObj.has("promotionproduct_NOTES") && !jsonObj.isNull("promotionproduct_NOTES"))
				promotionproduct.setPROMOTIONPRODUCT_NOTES(jsonObj.getString("promotionproduct_NOTES"));


			if (id == 0)
				promotionproduct.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				promotionproduct.setISACTIVE(jsonObj.getString("isactive"));

			promotionproduct.setMODIFIED_BY(apiRequest.getString("request_ID"));
			promotionproduct.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			promotionproduct.setMODIFIED_WHEN(dateFormat1.format(date));

			promotionproduct = promotionproductrepository.saveAndFlush(promotionproduct);
			promotionproducts.add(promotionproduct);

		}

		ResponseEntity responseentity;
		if (jsonpromotionproduct != null)
			responseentity = new ResponseEntity(getAPIResponse(null, promotionproducts.get(0), null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true), HttpStatus.OK);

		return responseentity;
	}
	
	// we delete the id that we enter  in api 
	// first we find & then we delete
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionproduct/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		PromotionProduct promotionproduct = promotionproductrepository.findOne(id);
		promotionproductrepository.delete(promotionproduct);

		return new ResponseEntity(getAPIResponse(null, promotionproduct, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	//update the list  to remove the given id and make it non-active
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/promotionProduct/remove/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject promotionProduct = new JSONObject();
		promotionProduct.put("PROMOTIONPRODUCT_ID", id);
		promotionProduct.put("isactive", "N");


		return insertupdateAll(null, promotionProduct, apiRequest);
	}

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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionproduct/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		
		// If active == true  ,  Calls findBySearch() → active records only
		// Else  ,   Calls findAllBySearch() → all records

		List<PromotionProduct> promotionproducts = ((active == true)

				? promotionproductrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: promotionproductrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/promotionproduct/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<PromotionProduct> promotionproducts = new ArrayList<PromotionProduct>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

		long promotion_ID=0, product_ID=0,productcategory_ID=0;
		List<Integer> promotion_IDS = new ArrayList<Integer>(); 
		List<Integer> product_IDS = new ArrayList<Integer>(); 
		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 

		promotion_IDS.add((int) 0);
		product_IDS.add((int) 0);
		productcategory_IDS.add((int) 0);

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

		if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID") && jsonObj.getLong("product_ID") != 0) {
			product_ID = jsonObj.getLong("product_ID");
			product_IDS.add((int) product_ID);

		}  else if (jsonObj.has("product") && !jsonObj.isNull("product") && jsonObj.getLong("product") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			product_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				product_IDS.add((int) searchObject.getJSONObject(i).getLong("product_ID"));
			}
		}
		if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && jsonObj.getLong("productcategory_ID") != 0) {
			productcategory_ID = jsonObj.getLong("productcategory_ID");
			productcategory_IDS.add((int) productcategory_ID);

		}  else if (jsonObj.has("productcategory") && !jsonObj.isNull("productcategory") && jsonObj.getLong("productcategory") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			productcategory_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				product_IDS.add((int) searchObject.getJSONObject(i).getLong("productcategory_ID"));
			}
		}
		if (promotion_ID != 0 || product_ID != 0 || productcategory_ID!=0) {
			promotionproducts = ((active == true)
					? promotionproductrepository.findByAdvancedSearch(promotion_ID, promotion_IDS, product_ID, product_IDS,productcategory_ID,productcategory_IDS)
							: promotionproductrepository.findAllByAdvancedSearch(promotion_ID, promotion_IDS, product_ID, product_IDS,productcategory_ID,productcategory_IDS));
		}
		return new ResponseEntity(getAPIResponse(promotionproducts, null, null, null, null, apiRequest, isWithDetail).toString(), HttpStatus.OK);
	}

	//getAPI response Function
	String getAPIResponse(List<PromotionProduct> promotionproducts, PromotionProduct promotionproduct, JSONArray Jsonpromotionproducts, JSONObject Jsonpromotionproduct, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotionproduct", message).toString();
		} else  {
			if ((promotionproducts != null || promotionproduct != null) && isWithDetail == true) {
				if (promotionproduct != null) {
					promotionproducts = new ArrayList<PromotionProduct>();
					promotionproducts.add(promotionproduct);
				}
				if (promotionproducts.size()>0) {
					List<Integer> promotionList = new ArrayList<Integer>();
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> productcategoryList = new ArrayList<Integer>();

					for (int i=0; i<promotionproducts.size(); i++) {
						if (promotionproducts.get(i).getPROMOTION_ID() != null) {
							promotionList.add(Integer.parseInt(promotionproducts.get(i).getPROMOTION_ID().toString()));
						}
						if (promotionproducts.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(promotionproducts.get(i).getPRODUCT_ID().toString()));
						}
						if (promotionproducts.get(i).getPRODUCTCATEGORY_ID() != null) {
							productcategoryList.add(Integer.parseInt(promotionproducts.get(i).getPRODUCTCATEGORY_ID().toString()));
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

					CompletableFuture<JSONArray> productFuture = CompletableFuture.supplyAsync(() -> {
						if (productList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});
					CompletableFuture<JSONArray> productcategoryFuture = CompletableFuture.supplyAsync(() -> {
						if (productcategoryList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});
					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(promotionFuture, productFuture,productcategoryFuture );

					// Block until all are done
					allDone.join();


					JSONArray promotionObject = promotionFuture.get();
					JSONArray productObject = productFuture.get();
					JSONArray productcategoryObject = productcategoryFuture.get();

					for (int i=0; i<promotionproducts.size(); i++) {
						for (int j=0; j<promotionObject.length(); j++) {
							JSONObject promotion = promotionObject.getJSONObject(j);
							if (promotionproducts.get(i).getPROMOTION_ID() != null && promotionproducts.get(i).getPROMOTION_ID() == promotion.getLong("promotion_ID") ) {

								promotionproducts.get(i).setPROMOTION_DETAIL(promotion.toString());
							}
						}
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (promotionproducts.get(i).getPRODUCT_ID() != null && promotionproducts.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {

								promotionproducts.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if (promotionproducts.get(i).getPRODUCTCATEGORY_ID() != null && promotionproducts.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID") ) {
								promotionproducts.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
					}
				}

				if (promotionproduct != null)
					rtnAPIResponse = mapper.writeValueAsString(promotionproducts.get(0));
				else
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
}

