
package com.cwiztech.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.PromotionProduct;

public interface promotionProductRepository extends JpaRepository<PromotionProduct, Long> {

	@Query(value = "select * from tblPromotionProduct where ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findActive();

	@Query(value = "select * from tblPromotionProduct "
			+ "where PromotionProduct_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblPromotionProduct "
			+ "where (PromotionProduct_DATE like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findBySearch(String search);

	@Query(value = "select * from tblPromotionProduct " +
			"where (PromotionProduct_DATE like ?1) " +
			"", nativeQuery = true)
	public List<PromotionProduct> findAllBySearch(String search);

	@Query(value = "select * from tblPromotionProduct "
			+ "where (CASE WHEN :CUSTOMERSHOP_ID = 0 THEN CUSTOMERSHOP_ID=CUSTOMERSHOP_ID ELSE CUSTOMERSHOP_ID IN (:CUSTOMERSHOP_IDS) END or CUSTOMERSHOP_ID is NULL) "
			+ "and (CASE WHEN :SALEORDERPAYMENT_ID = 0 THEN SALEORDERPAYMENT_ID=SALEORDERPAYMENT_ID ELSE SALEORDERPAYMENT_ID IN (:SALEORDERPAYMENT_IDS) END or SALEORDERPAYMENT_ID is NULL)"			
			+ "and (CASE WHEN :CREDITMEMO_ID = 0 THEN CREDITMEMO_ID=CREDITMEMO_ID ELSE CREDITMEMO_ID IN (:CREDITMEMO_IDS) END or CREDITMEMO_ID is NULL) "			
			+ "and (PromotionProductSTATUS_ID LIKE CASE WHEN :PromotionProductSTATUS_ID = 0 Then PromotionProductSTATUS_ID ELSE :PromotionProductSTATUS_ID END or PromotionProductSTATUS_ID is NULL) "
			+ "and (PromotionProduct_DATE >= CASE WHEN :PromotionProduct_DATE = 0 THEN PromotionProduct_DATE ELSE :PromotionProduct_DATEFROM END or PromotionProduct_DATE is NULL) "
			+ "and (PromotionProduct_DATE <= CASE WHEN :PromotionProduct_DATE = 0 THEN PromotionProduct_DATE ELSE :PromotionProduct_DATETO END or PromotionProduct_DATE is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findByAdvancedSearch(
		    @Param("CUSTOMERSHOP_ID") Long CUSTOMERSHOP_ID, @Param("CUSTOMERSHOP_IDS") List<Integer> CUSTOMERSHOP_IDS,	    
		    @Param("SALEORDERPAYMENT_ID") Long SALEORDERPAYMENT_ID, @Param("SALEORDERPAYMENT_IDS") List<Integer> SALEORDERPAYMENT_IDS,   
		    @Param("CREDITMEMO_ID") Long CREDITMEMO_ID, @Param("CREDITMEMO_IDS") List<Integer> CREDITMEMO_IDS,		    
			@Param("PromotionProductSTATUS_ID") Long PromotionProductSTATUS_ID,
			@Param("PromotionProduct_DATE") Long PromotionProduct_DATE, @Param("PromotionProduct_DATEFROM") String PromotionProduct_DATEFROM, @Param("PromotionProduct_DATETO") String PromotionProduct_DATETO);

	@Query(value = "select * from tblPromotionProduct "
			+ "where (CASE WHEN :CUSTOMERSHOP_ID = 0 THEN CUSTOMERSHOP_ID=CUSTOMERSHOP_ID ELSE CUSTOMERSHOP_ID IN (:CUSTOMERSHOP_IDS) END or CUSTOMERSHOP_ID is NULL) "
			+ "and (CASE WHEN :SALEORDERPAYMENT_ID = 0 THEN SALEORDERPAYMENT_ID=SALEORDERPAYMENT_ID ELSE SALEORDERPAYMENT_ID IN (:SALEORDERPAYMENT_IDS) END or SALEORDERPAYMENT_ID is NULL)"			
			+ "and (CASE WHEN :CREDITMEMO_ID = 0 THEN CREDITMEMO_ID=CREDITMEMO_ID ELSE CREDITMEMO_ID IN (:CREDITMEMO_IDS) END or CREDITMEMO_ID is NULL) "			
			+ "and (PromotionProductSTATUS_ID LIKE CASE WHEN :PromotionProductSTATUS_ID = 0 Then PromotionProductSTATUS_ID ELSE :PromotionProductSTATUS_ID END or PromotionProductSTATUS_ID is NULL) "
			+ "and (PromotionProduct_DATE >= CASE WHEN :PromotionProduct_DATE = 0 THEN PromotionProduct_DATE ELSE :PromotionProduct_DATEFROM END or PromotionProduct_DATE is NULL) "
			+ "and (PromotionProduct_DATE <= CASE WHEN :PromotionProduct_DATE = 0 THEN PromotionProduct_DATE ELSE :PromotionProduct_DATETO END or PromotionProduct_DATE is NULL) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findAllByAdvancedSearch(
		    @Param("CUSTOMERSHOP_ID") Long CUSTOMERSHOP_ID, @Param("CUSTOMERSHOP_IDS") List<Integer> CUSTOMERSHOP_IDS,	    
		    @Param("SALEORDERPAYMENT_ID") Long SALEORDERPAYMENT_ID, @Param("SALEORDERPAYMENT_IDS") List<Integer> SALEORDERPAYMENT_IDS,   
		    @Param("CREDITMEMO_ID") Long CREDITMEMO_ID, @Param("CREDITMEMO_IDS") List<Integer> CREDITMEMO_IDS,		    
			@Param("PromotionProductSTATUS_ID") Long PromotionProductSTATUS_ID,
			@Param("PromotionProduct_DATE") Long PromotionProduct_DATE, @Param("PromotionProduct_DATEFROM") String PromotionProduct_DATEFROM, @Param("PromotionProduct_DATETO") String PromotionProduct_DATETO);

}

