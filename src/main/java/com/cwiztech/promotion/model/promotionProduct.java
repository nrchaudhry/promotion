
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
	public class promotionProduct {
		
	        @Id
	        @GeneratedValue(strategy = GenerationType.IDENTITY)
	        private long PROMOTIONPRODCUT_ID;
	

	        @Column(name ="PROMOTION_ID")
		 	   private long PROMOTION_ID;
		        
		    	@Transient
				private String PROMOTION_DETAIL;
				
	        
	        @Column(name ="PRODUCT_ID")
	 	   private long PRODUCT_ID;
	        
	    	@Transient
			private String PRODUCT_DETAIL;
			
	 	
	        @Column(name ="PROMOTIONPRODCUT_PRICE")
	 	   private String PROMOTIONPRODCUT_PRICE;
	 	
	        @Column(name ="QUANTITY_REQUIRED")
	  	   private Long QUANTITY_REQUIRED;
	      
	        @Column(name ="QUANTITY_BONUS")
		  	   private Long QUANTITY_BONUS;
	        
	        @Column(name ="MAXPURCHASE_LIMIT")
		  	   private Long MAXPURCHASE_LIMIT;
	        
	        @Column(name ="PROMOTIONPRODCUT_NOTES")
		  	   private String PROMOTIONPRODCUT_NOTES;
	        
	        @Column(name ="ISACTIVE")
		  	   private String ISACTIVE;
		  
	        @JsonIgnore
	        @Column(name ="MODIFIED_BY")
	  	   private String MODIFIED_BY;
	        
	        public long getPROMOTIONPRODCUT_ID() {
				return PROMOTIONPRODCUT_ID;
			}

			public void setPROMOTIONPRODCUT_ID(long pROMOTIONPRODCUT_ID) {
				PROMOTIONPRODCUT_ID = pROMOTIONPRODCUT_ID;
			}

			public long getPROMOTION_ID() {
				return PROMOTION_ID;
			}

			public void setPROMOTION_ID(long pROMOTION_ID) {
				PROMOTION_ID = pROMOTION_ID;
			}

			public String getPROMOTION_DETAIL() {
				return PROMOTION_DETAIL;
			}

			public void setPROMOTION_DETAIL(String pROMOTION_DETAIL) {
				PROMOTION_DETAIL = pROMOTION_DETAIL;
			}

			public long getPRODUCT_ID() {
				return PRODUCT_ID;
			}

			public void setPRODUCT_ID(long pRODUCT_ID) {
				PRODUCT_ID = pRODUCT_ID;
			}

			public String getPRODUCT_DETAIL() {
				return PRODUCT_DETAIL;
			}

			public void setPRODUCT_DETAIL(String pRODUCT_DETAIL) {
				PRODUCT_DETAIL = pRODUCT_DETAIL;
			}

			public String getPROMOTIONPRODCUT_PRICE() {
				return PROMOTIONPRODCUT_PRICE;
			}

			public void setPROMOTIONPRODCUT_PRICE(String pROMOTIONPRODCUT_PRICE) {
				PROMOTIONPRODCUT_PRICE = pROMOTIONPRODCUT_PRICE;
			}

			public Long getQUANTITY_REQUIRED() {
				return QUANTITY_REQUIRED;
			}

			public void setQUANTITY_REQUIRED(Long qUANTITY_REQUIRED) {
				QUANTITY_REQUIRED = qUANTITY_REQUIRED;
			}

			public Long getQUANTITY_BONUS() {
				return QUANTITY_BONUS;
			}

			public void setQUANTITY_BONUS(Long qUANTITY_BONUS) {
				QUANTITY_BONUS = qUANTITY_BONUS;
			}

			public Long getMAXPURCHASE_LIMIT() {
				return MAXPURCHASE_LIMIT;
			}

			public void setMAXPURCHASE_LIMIT(Long mAXPURCHASE_LIMIT) {
				MAXPURCHASE_LIMIT = mAXPURCHASE_LIMIT;
			}

			public String getPROMOTIONPRODCUT_NOTES() {
				return PROMOTIONPRODCUT_NOTES;
			}

			public void setPROMOTIONPRODCUT_NOTES(String pROMOTIONPRODCUT_NOTES) {
				PROMOTIONPRODCUT_NOTES = pROMOTIONPRODCUT_NOTES;
			}

			public String getISACTIVE() {
				return ISACTIVE;
			}

			public void setISACTIVE(String iSACTIVE) {
				ISACTIVE = iSACTIVE;
			}

			public String getMODIFIED_BY() {
				return MODIFIED_BY;
			}

			public void setMODIFIED_BY(String mODIFIED_BY) {
				MODIFIED_BY = mODIFIED_BY;
			}

			public String getMODIFIED_WHEN() {
				return MODIFIED_WHEN;
			}

			public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
				MODIFIED_WHEN = mODIFIED_WHEN;
			}

			public String getMODIFIED_WORKSTATION() {
				return MODIFIED_WORKSTATION;
			}

			public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
				MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
			}

			@JsonIgnore
	        @Column(name ="MODIFIED_WHEN")
	  	   private String MODIFIED_WHEN;
	        
	        @JsonIgnore
	        @Column(name ="MODIFIED_WORKSTATION")
	  	   private String MODIFIED_WORKSTATION;

			
			}