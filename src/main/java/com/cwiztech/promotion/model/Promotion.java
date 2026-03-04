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
@Table(name="TBLPROMOTION")
public class Promotion {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "PROMOTION_ID")
	private long PROMOTION_ID;
	
	@Column(name = "PROMOTION_TITLE")
	private String PROMOTION_TITLE;
	
	@Column(name = "PROMOTION_DESCRIPTION")
	private String PROMOTION_DESCRIPTION;
	
	@Column(name = "PROMOTIONTYPE_ID")
	private Long PROMOTIONTYPE_ID;
	
	@Transient
	private String PROMOTIONTYPE_DETAIL;
	
	@Column(name = "DISCOUNTTYPE_ID")
	private Long DISCOUNTTYPE_ID;
	
	@Transient
	private String DISCOUNTTYPE_DETAIL;
	
	@Column(name = "DISCOUNT_VALUE")
	private BigDecimal DISCOUNT_VALUE;
	
	@Column(name = "MINORDER_AMOUNT")
	private BigDecimal MINORDER_AMOUNT;
	
	@Column(name = "MAXORDER_AMOUNT")
	private BigDecimal MAXORDER_AMOUNT;
	
	@Column(name = "PROMOTIONSTART_DATE")
	private String PROMOTIONSTART_DATE;
	
	@Column(name = "PROMOTIONEND_DATE")
	private String PROMOTIONEND_DATE;
	
	@Column(name = "COUPON_CODE")
	private String COUPON_CODE;
	
	@Column(name = "ISACTIVE")
	private String ISACTIVE;
	
	@JsonIgnore
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;
	
	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;
	
	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getPROMOTION_ID() {
		return PROMOTION_ID;
	}

	public void setPROMOTION_ID(long pROMOTION_ID) {
		PROMOTION_ID = pROMOTION_ID;
	}

	public String getPROMOTION_TITLE() {
		return PROMOTION_TITLE;
	}

	public void setPROMOTION_TITLE(String pROMOTION_TITLE) {
		PROMOTION_TITLE = pROMOTION_TITLE;
	}

	public String getPROMOTION_DESCRIPTION() {
		return PROMOTION_DESCRIPTION;
	}

	public void setPROMOTION_DESCRIPTION(String pROMOTION_DESCRIPTION) {
		PROMOTION_DESCRIPTION = pROMOTION_DESCRIPTION;
	}

	public Long getPROMOTIONTYPE_ID() {
		return PROMOTIONTYPE_ID;
	}

	public void setPROMOTIONTYPE_ID(Long pROMOTIONTYPE_ID) {
		PROMOTIONTYPE_ID = pROMOTIONTYPE_ID;
	}

	public String getPROMOTIONTYPE_DETAIL() {
		return PROMOTIONTYPE_DETAIL;
	}

	public void setPROMOTIONTYPE_DETAIL(String pROMOTIONTYPE_DETAIL) {
		PROMOTIONTYPE_DETAIL = pROMOTIONTYPE_DETAIL;
	}

	public Long getDISCOUNTTYPE_ID() {
		return DISCOUNTTYPE_ID;
	}

	public void setDISCOUNTTYPE_ID(Long dISCOUNTTYPE_ID) {
		DISCOUNTTYPE_ID = dISCOUNTTYPE_ID;
	}

	public String getDISCOUNTTYPE_DETAIL() {
		return DISCOUNTTYPE_DETAIL;
	}

	public void setDISCOUNTTYPE_DETAIL(String dISCOUNTTYPE_DETAIL) {
		DISCOUNTTYPE_DETAIL = dISCOUNTTYPE_DETAIL;
	}

	public BigDecimal getDISCOUNT_VALUE() {
		return DISCOUNT_VALUE;
	}

	public void setDISCOUNT_VALUE(BigDecimal dISCOUNT_VALUE) {
		DISCOUNT_VALUE = dISCOUNT_VALUE;
	}

	public BigDecimal getMINORDER_AMOUNT() {
		return MINORDER_AMOUNT;
	}

	public void setMINORDER_AMOUNT(BigDecimal mINORDER_AMOUNT) {
		MINORDER_AMOUNT = mINORDER_AMOUNT;
	}

	public BigDecimal getMAXORDER_AMOUNT() {
		return MAXORDER_AMOUNT;
	}

	public void setMAXORDER_AMOUNT(BigDecimal mAXORDER_AMOUNT) {
		MAXORDER_AMOUNT = mAXORDER_AMOUNT;
	}

	public String getPROMOTIONSTART_DATE() {
		return PROMOTIONSTART_DATE;
	}

	public void setPROMOTIONSTART_DATE(String pROMOTIONSTART_DATE) {
		PROMOTIONSTART_DATE = pROMOTIONSTART_DATE;
	}

	public String getPROMOTIONEND_DATE() {
		return PROMOTIONEND_DATE;
	}

	public void setPROMOTIONEND_DATE(String pROMOTIONEND_DATE) {
		PROMOTIONEND_DATE = pROMOTIONEND_DATE;
	}

	public String getCOUPON_CODE() {
		return COUPON_CODE;
	}

	public void setCOUPON_CODE(String cOUPON_CODE) {
		COUPON_CODE = cOUPON_CODE;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}
	
	@JsonIgnore
	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}
	
	@JsonIgnore
	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}
	
	@JsonIgnore
	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

}
