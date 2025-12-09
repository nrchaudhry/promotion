package com.cwiztech.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.Promotion;


public interface promotionRepository extends JpaRepository<Promotion, Long> {

	
	 @Query(value = "select * from tblpromotion where ISACTIVE='Y'", nativeQuery = true)
	public List<Promotion> findActive();
	
	
	@Query(value = "select * from tblpromotion "
			+ "where Promotion_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Promotion> findByIDs(@Param("ids") List<Integer> ids);
	
	
	@Query(value = "select * from tblpromotion "
			+ "where (Promotion_ID like ?1 or INVOICE_NUMBER like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Promotion> findBySearch(String search);
	
	 @Query(value = "select * from tblpromotion where PROMOTION_ID=?1", nativeQuery = true)
	    public List<Promotion> findByCode(String search);

	 @Query(value = "select * from tblpromotion "
				+ "where PROMOTION_ID like ?1 or  PROMOTION_TITLE like ?1 or  PROMOTION_DESCRIPTION like ?1 ", nativeQuery = true)
		public List<Promotion> findAllBySearch(String search);
	 
	 
	 @Query(value = "select * from tblpromotion " 
				+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
				+ "and ISACTIVE='Y'", nativeQuery = true)
		List<Promotion> findByAdvancedSearch(
	    @Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS);

	 
	 
		@Query(value = "select * from tblpromotion " 
		+ "where (CASE WHEN :PROMOTION_ID = 0 THEN PROMOTION_ID=PROMOTION_ID ELSE PROMOTION_ID IN (:PROMOTION_IDS) END or PROMOTION_ID is NULL) "
		+ "", nativeQuery = true)

		List<Promotion> findAllByAdvancedSearch(   
		@Param("PROMOTION_ID") Long PROMOTION_ID, @Param("PROMOTION_IDS") List<Integer> PROMOTION_IDS);
	
	
	
	
	
	

	
}
