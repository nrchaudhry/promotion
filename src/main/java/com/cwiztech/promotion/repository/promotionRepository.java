package com.cwiztech.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.PromotionModel;


public interface promotionRepository extends JpaRepository<PromotionModel, Long> {

	
	@Query(value = "select * from tblpromotion where ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionModel> findActive();

	@Query(value = "select * from tblpromotion "
			+ "where SALEPromotionModel_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionModel> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblpromotion "
			+ "where (SALEPromotionModel_CODE like ?1 or INVOICE_NUMBER like ?1) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionModel> findBySearch(String search);

	@Query(value = "select * from tblpromotion " +
			"where (SALEPromotionModel_CODE like ?1 or INVOICE_NUMBER like ?1) " +
			"", nativeQuery = true)

	public List<PromotionModel> findAllBySearch(String search);
}
