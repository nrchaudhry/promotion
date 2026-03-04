package com.cwiztech.promotion.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.PromotionBuyGet;
import com.cwiztech.promotion.model.PromotionProduct;

public interface promotionBuyGetRepository extends JpaRepository<PromotionBuyGet, Long> {
	@Query(value = "select * from tblpromotionbuyget where ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionBuyGet> findActive();

	@Query(value = "select * from tblpromotionbuyget "
			+ "where PROMOTIONBUYGET_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionBuyGet> findByIDs(@Param("ids") List<Integer> ids);
/*
	@Query(value = "select * from tblpromotionproduct "
			+ "where (PROMOTIONPRODCUT_NOTES like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findBySearch(String search);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (PROMOTIONPRODCUT_NOTES like ?1) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findAllBySearch(String search);

*/
	@Query(value = "select * from tblpromotionbuyget " 
			+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
			+ "and (CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END or PRODUCT_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<PromotionBuyGet> findByAdvancedSearch(
		    @Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS,
		    @Param("BUYPRODUCT_ID") Long BUYPRODUCT_ID, @Param("BUYPRODUCT_IDS") List<Integer> BUYPRODUCT_IDS,
            @Param("GETPRODUCT_ID") Long GETPRODUCT_ID, @Param("GETPRODUCT_IDS") List<Integer> GETPRODUCT_IDS);

	@Query(value = "select * from tblpromotionbuyget " 
			+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
			+ "and (CASE WHEN :BUYPRODUCT_ID = 0 THEN BUYPRODUCT_ID=BUYPRODUCT_ID ELSE BUYPRODUCT_ID IN (:BUYPRODUCT_IDS) END or BUYPRODUCT_ID is NULL) "
			+ "and (CASE WHEN :GETPRODUCT_ID = 0 THEN GETPRODUCT_ID=GETPRODUCT_ID ELSE GETPRODUCT_ID IN (:GETPRODUCT_IDS) END or GETPRODUCT_ID is NULL) "
			+ "", nativeQuery = true)
	List<PromotionBuyGet> findAllByAdvancedSearch(   
		    @Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS,
		    @Param("BUYPRODUCT_ID") Long BUYPRODUCT_ID, @Param("BUYPRODUCT_IDS") List<Integer> BUYPRODUCT_IDS,
		    @Param("GETPRODUCT_ID") Long GETPRODUCT_ID, @Param("GETPRODUCT_IDS") List<Integer> GETPRODUCT_IDS

		    );
           
}

