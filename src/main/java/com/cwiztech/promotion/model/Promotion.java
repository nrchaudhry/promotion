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
@Table(name="tblpromotion")
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
	private long PROMOTIONTYPE_ID;
	
	@Transient
	private String PROMOTIONTYPE_DETAIL;
	
	
	@Column(name = "DISCOUNT_PERCENTAGE")
	private long DISCOUNT_PERCENTAGE;
	
	
	@Column(name = "PROMOTIONSTART_DATE")
	private String PROMOTIONSTART_DATE;
	
	
	@Column(name = "PROMOTIONEND_DATE")
	private String PROMOTIONEND_DATE;
	
	
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

	public long getPROMOTIONTYPE_ID() {
		return PROMOTIONTYPE_ID;
	}

	public void setPROMOTIONTYPE_ID(long pROMOTIONTYPE_ID) {
		PROMOTIONTYPE_ID = pROMOTIONTYPE_ID;
	}

	public String getPROMOTIONTYPE_DETAIL() {
		return PROMOTIONTYPE_DETAIL;
	}

	public void setPROMOTIONTYPE_DETAIL(String pROMOTIONTYPE_DETAIL) {
		PROMOTIONTYPE_DETAIL = pROMOTIONTYPE_DETAIL;
	}

	public long getDISCOUNT_PERCENTAGE() {
		return DISCOUNT_PERCENTAGE;
	}

	public void setDISCOUNT_PERCENTAGE(long dISCOUNT_PERCENTAGE) {
		DISCOUNT_PERCENTAGE = dISCOUNT_PERCENTAGE;
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

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	@JsonIgnore
	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
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
