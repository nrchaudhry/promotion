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
			+ "where PROMOTION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Promotion> findByIDs(@Param("ids") List<Integer> ids);
	
	
	@Query(value = "select * from tblpromotion "
			+ "where (PROMOTION_ID like ?1 ) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Promotion> findBySearch(String search);
	
	

	 @Query(value = "select * from tblpromotion "
				+ "where PROMOTION_ID like ?1 or  PROMOTION_TITLE like ?1 or  PROMOTION_DESCRIPTION like ?1 ", nativeQuery = true)
		public List<Promotion> findAllBySearch(String search);
	 
	 
	 @Query(value = "select * from tblpromotionproduct " 
				+ "where (CASE WHEN :PROMOTIONTYPE_ID = 0 THEN PROMOTIONTYPE_ID=PROMOTIONTYPE_ID ELSE PROMOTIONTYPE_ID IN (:PROMOTIONTYPE_IDS) END or PROMOTIONTYPE_ID is NULL) "
				+ "and ISACTIVE='Y'", nativeQuery = true)
		List<Promotion> findByAdvancedSearch(
			    @Param("PROMOTIONTYPE_ID") Long PROMOTIONTYPE_ID, @Param("PROMOTIONTYPE_IDS") List<Integer> PROMOTIONTYPE_IDS);
			   

		@Query(value = "select * from tblpromotionproduct " 
				+ "where (CASE WHEN :PROMOTIONTYPE_ID = 0 THEN PROMOTIONTYPE_ID=PROMOTIONTYPE_ID ELSE PROMOTIONTYPE_ID IN (:PROMOTIONTYPE_IDS) END or PROMOTIONTYPE_ID is NULL) "
				+ "", nativeQuery = true)
		List<Promotion> findAllByAdvancedSearch(   
			    @Param("PROMOTIONTYPE_ID") Long PROMOTIONTYPE_ID, @Param("PROMOTIONTYPE_IDS") List<Integer> PROMOTIONTYPE_IDS);
			  
		 @Query(value = "select * from tblpromotion where PROMOTION_ID=?1", nativeQuery = true)
		    public List<Promotion> findByCode(String search);
	
	
	
	
	

	
}
