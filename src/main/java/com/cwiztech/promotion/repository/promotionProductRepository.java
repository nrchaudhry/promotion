package com.cwiztech.promotion.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.PromotionProduct;

public interface promotionProductRepository extends JpaRepository<PromotionProduct, Long> {
	@Query(value = "select * from tblpromotionproduct where ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findActive();
 
	@Query(value = "select * from tblpromotionproduct "
			+ "where PROMOTIONPRODUCT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (PROMOTIONPRODCUT_NOTES like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findBySearch(String search);

	@Query(value = "select * from tblpromotionproduct "
			+ "where (PROMOTIONPRODCUT_NOTES like ?1) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findAllBySearch(String search);

	@Query(value = "select * from tblpromotionproduct " 
			+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
			+ "and (CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END or PRODUCT_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<PromotionProduct> findByAdvancedSearch(
		    @Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS,
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS);

	@Query(value = "select * from tblpromotionproduct " 
			+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
			+ "and (CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END or PRODUCT_ID is NULL) "
			+ "", nativeQuery = true)
	List<PromotionProduct> findAllByAdvancedSearch(   
		    @Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS,
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS);
}


