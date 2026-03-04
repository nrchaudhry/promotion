package com.cwiztech.promotion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name ="tblpromotionproduct")
public class PromotionProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PROMOTIONPRODUCT_ID;
	
	@Column(name ="PROMOTION_ID")
	private Long PROMOTION_ID;

	@Transient
	private String PROMOTION_DETAIL;

	@Column(name ="PRODUCT_ID")
	private Long PRODUCT_ID;

	@Transient
	private String PRODUCT_DETAIL;

	@Column(name ="PRODUCTCATEGORY_ID")
	private Long PRODUCTCATEGORY_ID;

	@Transient
	private String PRODUCTCATEGORY_DETAIL;
	

	@Column(name ="PROMOTIONPRODUCT_PRICE")
	private Double PROMOTIONPRODUCT_PRICE;

	@Column(name ="QUANTITY_REQUIRED")
	private Double QUANTITY_REQUIRED;

	@Column(name ="QUANTITY_BONUS")
	private Double QUANTITY_BONUS;

	@Column(name ="MAXPURCHASE_LIMIT")
	private Long MAXPURCHASE_LIMIT;

	@Column(name ="PROMOTIONPRODUCT_NOTES")
	private String PROMOTIONPRODUCT_NOTES;

	@Column(name ="ISACTIVE")
	private String ISACTIVE;

	@JsonIgnore
	@Column(name ="MODIFIED_BY")
	private String MODIFIED_BY;
	
	@JsonIgnore
	@Column(name ="MODIFIED_WHEN")
	private String MODIFIED_WHEN;
	
	@JsonIgnore
	@Column(name ="MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getPROMOTIONPRODUCT_ID() {
		return PROMOTIONPRODUCT_ID;
	}

	public void setPROMOTIONPRODUCT_ID(long pROMOTIONPRODUCT_ID) {
		PROMOTIONPRODUCT_ID = pROMOTIONPRODUCT_ID;
	}

	public Long getPROMOTION_ID() {
		return PROMOTION_ID;
	}

	public void setPROMOTION_ID(Long pROMOTION_ID) {
		PROMOTION_ID = pROMOTION_ID;
	}

	public String getPROMOTION_DETAIL() {
		return PROMOTION_DETAIL;
	}

	public void setPROMOTION_DETAIL(String pROMOTION_DETAIL) {
		PROMOTION_DETAIL = pROMOTION_DETAIL;
	}

	public Long getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(Long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}


	public String getPRODUCT_DETAIL() {
		return PRODUCT_DETAIL;
	}

	public void setPRODUCT_DETAIL(String pRODUCT_DETAIL) {
		PRODUCT_DETAIL = pRODUCT_DETAIL;
	}

	public Long getPRODUCTCATEGORY_ID() {
		return PRODUCTCATEGORY_ID;
	}

	public void setPRODUCTCATEGORY_ID(Long pRODUCTCATEGORY_ID) {
		PRODUCTCATEGORY_ID = pRODUCTCATEGORY_ID;
	}

	public String getPRODUCTCATEGORY_DETAIL() {
		return PRODUCTCATEGORY_DETAIL;
	}

	public void setPRODUCTCATEGORY_DETAIL(String pRODUCTCATEGORY_DETAIL) {
		PRODUCTCATEGORY_DETAIL = pRODUCTCATEGORY_DETAIL;
	}

	public Double getPROMOTIONPRODUCT_PRICE() {
		return PROMOTIONPRODUCT_PRICE;
	}

	public void setPROMOTIONPRODUCT_PRICE(Double pROMOTIONPRODUCT_PRICE) {
		PROMOTIONPRODUCT_PRICE = pROMOTIONPRODUCT_PRICE;
	}

	public Double getQUANTITY_REQUIRED() {
		return QUANTITY_REQUIRED;
	}

	public void setQUANTITY_REQUIRED(Double qUANTITY_REQUIRED) {
		QUANTITY_REQUIRED = qUANTITY_REQUIRED;
	}

	public Double getQUANTITY_BONUS() {
		return QUANTITY_BONUS;
	}

	public void setQUANTITY_BONUS(Double qUANTITY_BONUS) {
		QUANTITY_BONUS = qUANTITY_BONUS;
	}

	public Long getMAXPURCHASE_LIMIT() {
		return MAXPURCHASE_LIMIT;
	}

	public void setMAXPURCHASE_LIMIT(Long mAXPURCHASE_LIMIT) {
		MAXPURCHASE_LIMIT = mAXPURCHASE_LIMIT;
	}

	public String getPROMOTIONPRODUCT_NOTES() {
		return PROMOTIONPRODUCT_NOTES;
	}

	public void setPROMOTIONPRODUCT_NOTES(String pROMOTIONPRODUCT_NOTES) {
		PROMOTIONPRODUCT_NOTES = pROMOTIONPRODUCT_NOTES;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	@JsonIgnore
	public String getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(String mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	@JsonIgnore
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	@JsonIgnore
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	}
