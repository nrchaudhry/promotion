package com.cwiztech.promotion.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name ="tblpromotionbuyget")
public class PromotionBuyGet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PROMOTIONBUYGET_ID;
	
	@Column(name ="PROMOTION_ID")
	private Long PROMOTION_ID;

	@Transient
	private String PROMOTION_DETAIL;

	@Column(name ="BUYPRODUCT_ID")
	private Long BUYPRODUCT_ID;
	
	@Transient
	private String BUYPRODUCT_DETAIL;
	
	@Column(name ="BUY_QUANTITY")
	private BigDecimal BUY_QUANTITY;

	@Column(name ="GETPRODUCT_ID")
	private Long GETPRODUCT_ID;

	@Transient
	private String GETPRODUCT_DETAIL;

	@Column(name ="GET_QUANTITY")
	private BigDecimal GET_QUANTITY;
	

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

	public long getPROMOTIONBUYGET_ID() {
		return PROMOTIONBUYGET_ID;
	}

	public void setPROMOTIONBUYGET_ID(long pROMOTIONBUYGET_ID) {
		PROMOTIONBUYGET_ID = pROMOTIONBUYGET_ID;
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

	public Long getBUYPRODUCT_ID() {
		return BUYPRODUCT_ID;
	}

	public void setBUYPRODUCT_ID(Long bUYPRODUCT_ID) {
		BUYPRODUCT_ID = bUYPRODUCT_ID;
	}

	public String getBUYPRODUCT_DETAIL() {
		return BUYPRODUCT_DETAIL;
	}

	public void setBUYPRODUCT_DETAIL(String bUYPRODUCT_DETAIL) {
		BUYPRODUCT_DETAIL = bUYPRODUCT_DETAIL;
	}

	public BigDecimal getBUY_QUANTITY() {
		return BUY_QUANTITY;
	}

	public void setBUY_QUANTITY(BigDecimal bUY_QUANTITY) {
		BUY_QUANTITY = bUY_QUANTITY;
	}

	public Long getGETPRODUCT_ID() {
		return GETPRODUCT_ID;
	}

	public void setGETPRODUCT_ID(Long gETPRODUCT_ID) {
		GETPRODUCT_ID = gETPRODUCT_ID;
	}

	public String getGETPRODUCT_DETAIL() {
		return GETPRODUCT_DETAIL;
	}

	public void setGETPRODUCT_DETAIL(String gETPRODUCT_DETAIL) {
		GETPRODUCT_DETAIL = gETPRODUCT_DETAIL;
	}
	
	public BigDecimal getGET_QUANTITY() {
		return GET_QUANTITY;
	}

	public void setGET_QUANTITY(BigDecimal gET_QUANTITY) {
		GET_QUANTITY = gET_QUANTITY;
	}

	String getISACTIVE() {
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
