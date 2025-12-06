package com.cwiztech.promotion.controller;

import java.text.ParseException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.log.apiRequestLog;
import com.cwiztech.promotion.model.PromotionModel;
import com.cwiztech.promotion.repository.promotionRepository;
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> get(@RequestHeader(value = "Authorization") String headToken,
                                      @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {

        JSONObject apiRequest = AccessToken.checkToken("GET", "/promotion", null, null, headToken);
        if (apiRequest.has("error")) {
            return new ResponseEntity<>(apiRequest.toString(), HttpStatus.OK);
        }

        List<PromotionModel> promotion = promotionrepository.findActive();
        String body = getAPIResponse(promotion, null, null, null, null, apiRequest, true);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    String getAPIResponse(List<PromotionModel> PromotionModels, PromotionModel promotion, JSONArray Jsonpromotions,
                          JSONObject JsonPromotion, String message, JSONObject apiRequest, boolean isWithDetail)
            throws JSONException, JsonProcessingException, ParseException {

        ObjectMapper mapper = new ObjectMapper();
        String rtnAPIResponse = "Invalid Response";

        if (message != null) {
            rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "promotion", message).toString();
        } else {
            if (promotion != null && isWithDetail) {
                rtnAPIResponse = mapper.writeValueAsString(promotion);
                apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (PromotionModels != null && isWithDetail) {
                rtnAPIResponse = mapper.writeValueAsString(PromotionModels);
                apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (promotion != null && !isWithDetail) {
                rtnAPIResponse = mapper.writeValueAsString(promotion);
                apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (PromotionModels != null && !isWithDetail) {
                rtnAPIResponse = mapper.writeValueAsString(PromotionModels);
                apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (Jsonpromotions != null) {
                rtnAPIResponse = Jsonpromotions.toString();
                apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
            }
        }

        return rtnAPIResponse;
    }
}
