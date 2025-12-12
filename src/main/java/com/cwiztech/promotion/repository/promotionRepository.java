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

	@Query(value = "SELECT * FROM TBLPROMOTION "
	        + "WHERE (PROMOTIONSTART_DATE >= CASE WHEN :promotiondate = 0 THEN PROMOTIONSTART_DATE ELSE :promotionstart_DATE END OR PROMOTIONSTART_DATE IS NULL) "
	        + "AND (PROMOTIONEND_DATE <= CASE WHEN :promotiondate = 0 THEN PROMOTIONEND_DATE ELSE :promotionend_DATE END OR PROMOTIONEND_DATE IS NULL) "
	        + "AND (PROMOTIONTYPE_ID = CASE WHEN :promotiontype_ID = 0 THEN PROMOTIONTYPE_ID ELSE :promotiontype_ID END OR PROMOTIONTYPE_ID IS NULL) "
	        + "AND ISACTIVE='Y'", nativeQuery = true)
	List<Promotion> findByAdvancedSearch(
	        @Param("promotiontype_ID") Long promotiontype_ID,
	        @Param("promotionstart_DATE") String promotionstart_DATE,
	        @Param("promotionend_DATE") String promotionend_DATE,
	        @Param("promotiondate") Long promotiondate);


	@Query(value = "SELECT * FROM TBLPROMOTION "
	        + "WHERE (PROMOTIONSTART_DATE >= CASE WHEN :promotiondate = 0 THEN PROMOTIONSTART_DATE ELSE :promotionstart_DATE END OR PROMOTIONSTART_DATE IS NULL) "
	        + "AND (PROMOTIONEND_DATE <= CASE WHEN :promotiondate = 0 THEN PROMOTIONEND_DATE ELSE :promotionend_DATE END OR PROMOTIONEND_DATE IS NULL) "
	        + "AND (PROMOTIONTYPE_ID = CASE WHEN :promotiontype_ID = 0 THEN PROMOTIONTYPE_ID ELSE :promotiontype_ID END OR PROMOTIONTYPE_ID IS NULL)",
	        nativeQuery = true)
	List<Promotion> findAllByAdvancedSearch(
	        @Param("promotiontype_ID") Long promotiontype_ID,
	        @Param("promotionstart_DATE") String promotionstart_DATE,
	        @Param("promotionend_DATE") String promotionend_DATE,
	        @Param("promotiondate") Long promotiondate);

}
