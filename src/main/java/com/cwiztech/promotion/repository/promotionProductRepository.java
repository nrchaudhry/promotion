
package com.cwiztech.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.promotionProduct;

public interface promotionProductRepository extends JpaRepository<promotionProduct, Long> {

	@Query(value = "select * from tblpromotionproduct where ISACTIVE='Y'", nativeQuery = true)
	public List<promotionProduct> findActive();

	@Query(value = "select * from tblpromotionproduct "
			+ "where promotionProduct_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<promotionProduct> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (promotionProduct_DATE like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<promotionProduct> findBySearch(String search);

	@Query(value = "select * from tblpromotionproduct " +
			"where (promotionProduct_DATE like ?1) " +
			"", nativeQuery = true)
	public List<promotionProduct> findAllBySearch(String search);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (CASE WHEN :CUSTOMERSHOP_ID = 0 THEN CUSTOMERSHOP_ID=CUSTOMERSHOP_ID ELSE CUSTOMERSHOP_ID IN (:CUSTOMERSHOP_IDS) END or CUSTOMERSHOP_ID is NULL) "
			+ "and (CASE WHEN :SALEORDERPAYMENT_ID = 0 THEN SALEORDERPAYMENT_ID=SALEORDERPAYMENT_ID ELSE SALEORDERPAYMENT_ID IN (:SALEORDERPAYMENT_IDS) END or SALEORDERPAYMENT_ID is NULL)"			
			+ "and (CASE WHEN :CREDITMEMO_ID = 0 THEN CREDITMEMO_ID=CREDITMEMO_ID ELSE CREDITMEMO_ID IN (:CREDITMEMO_IDS) END or CREDITMEMO_ID is NULL) "			
			+ "and (promotionProductSTATUS_ID LIKE CASE WHEN :promotionProductSTATUS_ID = 0 Then promotionProductSTATUS_ID ELSE :promotionProductSTATUS_ID END or promotionProductSTATUS_ID is NULL) "
			+ "and (promotionProduct_DATE >= CASE WHEN :promotionProduct_DATE = 0 THEN promotionProduct_DATE ELSE :promotionProduct_DATEFROM END or promotionProduct_DATE is NULL) "
			+ "and (promotionProduct_DATE <= CASE WHEN :promotionProduct_DATE = 0 THEN promotionProduct_DATE ELSE :promotionProduct_DATETO END or promotionProduct_DATE is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<promotionProduct> findByAdvancedSearch(
		    @Param("CUSTOMERSHOP_ID") Long CUSTOMERSHOP_ID, @Param("CUSTOMERSHOP_IDS") List<Integer> CUSTOMERSHOP_IDS,	    
		    @Param("SALEORDERPAYMENT_ID") Long SALEORDERPAYMENT_ID, @Param("SALEORDERPAYMENT_IDS") List<Integer> SALEORDERPAYMENT_IDS,   
		    @Param("CREDITMEMO_ID") Long CREDITMEMO_ID, @Param("CREDITMEMO_IDS") List<Integer> CREDITMEMO_IDS,		    
			@Param("promotionProductSTATUS_ID") Long promotionProductSTATUS_ID,
			@Param("promotionProduct_DATE") Long promotionProduct_DATE, @Param("promotionProduct_DATEFROM") String promotionProduct_DATEFROM, @Param("promotionProduct_DATETO") String promotionProduct_DATETO);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (CASE WHEN :CUSTOMERSHOP_ID = 0 THEN CUSTOMERSHOP_ID=CUSTOMERSHOP_ID ELSE CUSTOMERSHOP_ID IN (:CUSTOMERSHOP_IDS) END or CUSTOMERSHOP_ID is NULL) "
			+ "and (CASE WHEN :SALEORDERPAYMENT_ID = 0 THEN SALEORDERPAYMENT_ID=SALEORDERPAYMENT_ID ELSE SALEORDERPAYMENT_ID IN (:SALEORDERPAYMENT_IDS) END or SALEORDERPAYMENT_ID is NULL)"			
			+ "and (CASE WHEN :CREDITMEMO_ID = 0 THEN CREDITMEMO_ID=CREDITMEMO_ID ELSE CREDITMEMO_ID IN (:CREDITMEMO_IDS) END or CREDITMEMO_ID is NULL) "			
			+ "and (promotionProductSTATUS_ID LIKE CASE WHEN :promotionProductSTATUS_ID = 0 Then promotionProductSTATUS_ID ELSE :promotionProductSTATUS_ID END or promotionProductSTATUS_ID is NULL) "
			+ "and (promotionProduct_DATE >= CASE WHEN :promotionProduct_DATE = 0 THEN promotionProduct_DATE ELSE :promotionProduct_DATEFROM END or promotionProduct_DATE is NULL) "
			+ "and (promotionProduct_DATE <= CASE WHEN :promotionProduct_DATE = 0 THEN promotionProduct_DATE ELSE :promotionProduct_DATETO END or promotionProduct_DATE is NULL) "
			+ "", nativeQuery = true)
	public List<promotionProduct> findAllByAdvancedSearch(
		    @Param("CUSTOMERSHOP_ID") Long CUSTOMERSHOP_ID, @Param("CUSTOMERSHOP_IDS") List<Integer> CUSTOMERSHOP_IDS,	    
		    @Param("SALEORDERPAYMENT_ID") Long SALEORDERPAYMENT_ID, @Param("SALEORDERPAYMENT_IDS") List<Integer> SALEORDERPAYMENT_IDS,   
		    @Param("CREDITMEMO_ID") Long CREDITMEMO_ID, @Param("CREDITMEMO_IDS") List<Integer> CREDITMEMO_IDS,		    
			@Param("promotionProductSTATUS_ID") Long promotionProductSTATUS_ID,
			@Param("promotionProduct_DATE") Long promotionProduct_DATE, @Param("promotionProduct_DATEFROM") String promotionProduct_DATEFROM, @Param("promotionProduct_DATETO") String promotionProduct_DATETO);

}

