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
			+ "where (PROMOTION_TITLE like ?1 or  PROMOTION_DESCRIPTION like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Promotion> findBySearch(String search);

	@Query(value = "select * from tblpromotion "
			+ "where PROMOTION_TITLE like ?1 or  PROMOTION_DESCRIPTION like ?1 ", nativeQuery = true)
	public List<Promotion> findAllBySearch(String search);

	@Query(value = "SELECT * FROM TBLPROMOTION "
	        + "where (PROMOTIONTYPE_ID = CASE WHEN :promotiontype_ID = 0 THEN PROMOTIONTYPE_ID ELSE :promotiontype_ID END OR PROMOTIONTYPE_ID IS NULL) "
			+ "and (PROMOTIONSTART_DATE >= CASE WHEN :PROMOTIONSTART_DATE = 0 THEN PROMOTIONSTART_DATE ELSE :PROMOTIONSTART_DATEFROM END or PROMOTIONSTART_DATE is NULL) "
			+ "and (PROMOTIONSTART_DATE <= CASE WHEN :PROMOTIONSTART_DATE = 0 THEN PROMOTIONSTART_DATE ELSE :PROMOTIONSTART_DATETO END or PROMOTIONSTART_DATE is NULL) "
			+ "and (PROMOTIONEND_DATE >= CASE WHEN :PROMOTIONEND_DATE = 0 THEN PROMOTIONEND_DATE ELSE :PROMOTIONEND_DATEFROM END or PROMOTIONEND_DATE is NULL) "
			+ "and (PROMOTIONEND_DATE <= CASE WHEN :PROMOTIONEND_DATE = 0 THEN PROMOTIONEND_DATE ELSE :PROMOTIONEND_DATETO END or PROMOTIONEND_DATE is NULL) "
	        + "and ISACTIVE='Y'", nativeQuery = true)
	List<Promotion> findByAdvancedSearch(
	        @Param("promotiontype_ID") Long promotiontype_ID,
			@Param("PROMOTIONSTART_DATE") Long PROMOTIONSTART_DATE, @Param("PROMOTIONSTART_DATEFROM") String PROMOTIONSTART_DATEFROM, @Param("PROMOTIONSTART_DATETO") String PROMOTIONSTART_DATETO,
			@Param("PROMOTIONEND_DATE") Long PROMOTIONEND_DATE, @Param("PROMOTIONEND_DATEFROM") String PROMOTIONEND_DATEFROM, @Param("PROMOTIONEND_DATETO") String PROMOTIONEND_DATETO);


	@Query(value = "SELECT * FROM TBLPROMOTION "
	        + "where (PROMOTIONTYPE_ID = CASE WHEN :promotiontype_ID = 0 THEN PROMOTIONTYPE_ID ELSE :promotiontype_ID END OR PROMOTIONTYPE_ID IS NULL) "
			+ "and (PROMOTIONSTART_DATE >= CASE WHEN :PROMOTIONSTART_DATE = 0 THEN PROMOTIONSTART_DATE ELSE :PROMOTIONSTART_DATEFROM END or PROMOTIONSTART_DATE is NULL) "
			+ "and (PROMOTIONSTART_DATE <= CASE WHEN :PROMOTIONSTART_DATE = 0 THEN PROMOTIONSTART_DATE ELSE :PROMOTIONSTART_DATETO END or PROMOTIONSTART_DATE is NULL) "
			+ "and (PROMOTIONEND_DATE >= CASE WHEN :PROMOTIONEND_DATE = 0 THEN PROMOTIONEND_DATE ELSE :PROMOTIONEND_DATEFROM END or PROMOTIONEND_DATE is NULL) "
			+ "and (PROMOTIONEND_DATE <= CASE WHEN :PROMOTIONEND_DATE = 0 THEN PROMOTIONEND_DATE ELSE :PROMOTIONEND_DATETO END or PROMOTIONEND_DATE is NULL) "
	        + "", nativeQuery = true)
	List<Promotion> findAllByAdvancedSearch(
	        @Param("promotiontype_ID") Long promotiontype_ID,
			@Param("PROMOTIONSTART_DATE") Long PROMOTIONSTART_DATE, @Param("PROMOTIONSTART_DATEFROM") String PROMOTIONSTART_DATEFROM, @Param("PROMOTIONSTART_DATETO") String PROMOTIONSTART_DATETO,
			@Param("PROMOTIONEND_DATE") Long PROMOTIONEND_DATE, @Param("PROMOTIONEND_DATEFROM") String PROMOTIONEND_DATEFROM, @Param("PROMOTIONEND_DATETO") String PROMOTIONEND_DATETO);
}
