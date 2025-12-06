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
			+ "where SALEPromotion_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Promotion> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblpromotion "
			+ "where (SALEPromotion_CODE like ?1 or INVOICE_NUMBER like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Promotion> findBySearch(String search);

	@Query(value = "select * from tblpromotion " +
			"where (SALEPromotion_CODE like ?1 or INVOICE_NUMBER like ?1) " +
			"", nativeQuery = true)

	public List<Promotion> findAllBySearch(String search);
}
