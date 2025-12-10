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
			+ "where PromotionProductProductProduct_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findByIDs(@Param("ids") List<Integer> ids);

	
	@Query(value = "select * from tblpromotionproduct "
			+ "where (PromotionProductProduct_ID like ?1 or INVOICE_NUMBER like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findBySearch(String search);
	 
	
	 @Query(value = "select * from tblpromotionproduct where PROMOTIONPRODUCT_ID =?1", nativeQuery = true)
	    public List<PromotionProduct> findByCode(String search);

	 @Query(value = "select * from tblpromotionproduct "
				+ "where PROMOTIONPRODUCT_ID like ?1 or  PromotionProductProduct_TITLE like ?1 or  PromotionProductProduct_DESCRIPTION like ?1 ", nativeQuery = true)
		public List<PromotionProduct> findAllBySearch(String search);
	 
	 
	 @Query(value = "select * from tblpromotionproduct " 
				+ "where (CASE WHEN :PromotionProductProductPRODUCT_ID = 0 THEN PromotionProductProductPRODUCT_ID = PromotionProductProductPRODUCT_ID ELSE PromotionProductProductPRODUCT_ID IN (:PROMOTIONPRODUCT_ID) END or PROMOTIONPRODUCT_ID is NULL) "
				+ "and ISACTIVE='Y'", nativeQuery = true)
		List<PromotionProduct> findByAdvancedSearch(
	    @Param("PROMOTIONPRODUCT_ID") Long PROMOTIONPRODUCT_ID, @Param("PROMOTIONPRODUCT_IDS") List<Integer> PROMOTIONPRODUCT_IDS);

	 
	 
		@Query(value = "select * from tblpromotionproduct " 
		+ "where (CASE WHEN :PromotionProductProduct_ID = 0 THEN PROMOTIONPRODUCT_ID = PROMOTIONPRODUCT_ID ELSE PROMOTIONPRODUCT_ID IN (:PROMOTIONPRODUCT_ID) END or PROMOTIONPRODUCT_ID is NULL) "
		+ "", nativeQuery = true)

		List<PromotionProduct> findAllByAdvancedSearch(   
		@Param("PromotionProductProduct_ID") Long PromotionProductProduct_ID, @Param("PromotionProductProduct_IDS") List<Integer> PromotionProductProduct_IDS);
	
	
	
	
	public List<PromotionProduct> findAllByAdvancedSearch(long PromotionProductProductproduct_ID, List<Integer> PromotionProductProductproduct_IDS);

	public List<PromotionProduct> findByAdvancedSearch(long PromotionProductProductproduct_ID, List<Integer> PromotionProductProductproduct_IDS);
}


