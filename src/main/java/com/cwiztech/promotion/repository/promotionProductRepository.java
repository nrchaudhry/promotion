package com.cwiztech.promotion.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.promotion.model.PromotionProduct;
import com.cwiztech.promotion.model.Promotion;

public interface promotionProductRepository extends JpaRepository<PromotionProduct, Long> {

	@Query(value = "select * from TBLPromotionProduct where EMPLOYEE_ID=?1", nativeQuery = true)
	public List<Promotion> findAllByEmployee(long eid);

	@Query(value = "select * from TBLPromotionProduct where ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findActive();

	@Query(value = "select * from TBLPromotionProduct where PromotionProduct_CODE=''", nativeQuery = true)
	public List<PromotionProduct> findCode();

	@Query(value = "select * from TBLPromotionProduct where MODIFIED_WORKSTATION='U'", nativeQuery = true)
	public List<PromotionProduct> findNewCode();

	@Query(value = "select * from TBLPromotionProduct where EMPLOYEE_ID=?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findActiveByEmployee(long eid);

	@Query(value = "select * from TBLPromotionProduct where NETSUITE_ID=?1", nativeQuery = true)
	PromotionProduct findByNetSuiteID(Long id);
	
	@Query(value = "select * from TBLPromotionProduct where NETSUITE_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findByNullNetSuiteID();
	
	@Query(value = "select * from TBLPromotionProduct where QUICKBOOK_ID=?1", nativeQuery = true)
	public PromotionProduct findByQuickBookID(String id);
	
	@Query(value = "select * from TBLPromotionProduct where QUICKBOOK_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findByNullQuickBookID();
	
	@Query(value = "select * from TBLPromotionProduct where SAGE_ID=?1", nativeQuery = true)
	public PromotionProduct findBySageID(String id);
	
	@Query(value = "select * from TBLPromotionProduct where SAGE_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findByNullSageID();
	
	@Query(value = "select * from TBLPromotionProduct where PromotionProduct_CODE=?1", nativeQuery = true)
	public PromotionProduct findByCode(String code);
	
	@Query(value = "select * from TBLPromotionProduct where NETSUITE_ID=1", nativeQuery = true)
	public List<PromotionProduct> findByNetsuiteCode();
	
	@Query(value = "select PromotionProduct_CODE from TBLPromotionProduct where LEFT(PromotionProduct_CODE, 1)=?1 order by PromotionProduct_CODE desc limit 1", nativeQuery = true)
	public String GenerateNewCode(char name);
	
	@Query(value = "select * from TBLPromotionProduct "
			+ "where PromotionProduct_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPromotionProduct "
			+ "where PromotionProduct_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findByNotInIDs(@Param("ids") List<Integer> ids);
	
	@Query(value = "select * from TBLPromotionProduct "
			+ "where SAGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findBySageIDs(@Param("ids") List<String> ids);

//	@Query(value = "Select SUBSTRING(PromotionProduct_NEWCODE, 2, LEN(PromotionProduct_NEWCODE)-1) + 1 from TBLPromotionProduct"
//			+ " where PromotionProduct_NEWCODE IN(select top 1 PromotionProduct_NEWCODE from TBLPromotionProduct where LEFT(PromotionProduct_NEWCODE, 1)=?1 order by PromotionProduct_NEWCODE desc)", nativeQuery = true)
//	public int GenerateNewCode(char name);

	@Query(value = "select * from TBLPromotionProduct where PromotionProduct_NEWCODE=?1", nativeQuery = true)
	public PromotionProduct findByNewCode(String code);

	@Query(value = "select * from TBLPromotionProduct "
			+ "where PromotionProduct_CODE like ?1 or PromotionProduct_NAME like ?1 "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<PromotionProduct> findBySearch(String search);

	@Query(value = "select * from TBLPromotionProduct "
			+ "where PromotionProduct_CODE like ?1 or PromotionProduct_NAME like ?1 "
			+ "", nativeQuery = true)
	public List<PromotionProduct> findAllBySearch(String search);
	
	@Query(value = "select * from TBLPromotionProduct "
			+ "where (CASE WHEN :PERSON_ID = 0 THEN PERSON_ID=PERSON_ID ELSE PERSON_ID IN (:PERSON_IDS) END or PERSON_ID is NULL) "
			+ "and (CASE WHEN :COMPANY_ID = 0 THEN COMPANY_ID=COMPANY_ID ELSE COMPANY_ID IN (:COMPANY_IDS) END or COMPANY_ID is NULL) "
			+ "and (PromotionProductCATEGORY_ID LIKE CASE WHEN :PromotionProductCATEGORY_ID = 0 Then PromotionProductCATEGORY_ID ELSE :PromotionProductCATEGORY_ID END or PromotionProductCATEGORY_ID is NULL) "
			+ "and (PromotionProductSTATUS_ID LIKE CASE WHEN :PromotionProductSTATUS_ID = 0 Then PromotionProductSTATUS_ID ELSE :PromotionProductSTATUS_ID END or PromotionProductSTATUS_ID is NULL) "
			+ "and (CASE WHEN :LOCATION_ID = 0 THEN LOCATION_ID=LOCATION_ID ELSE LOCATION_ID IN (:LOCATION_IDS) END or LOCATION_ID is NULL) "
			+ "and (BUSINESSTYPE_ID LIKE CASE WHEN :BUSINESSTYPE_ID = 0 Then BUSINESSTYPE_ID ELSE :BUSINESSTYPE_ID END or BUSINESSTYPE_ID is NULL) "
			+ "and (BUSINESSMARKETNICHE_ID LIKE CASE WHEN :BUSINESSMARKETNICHE_ID = 0 Then BUSINESSMARKETNICHE_ID ELSE :BUSINESSMARKETNICHE_ID END or BUSINESSMARKETNICHE_ID is NULL) "
			+ "and (INVOICETYPE_ID LIKE CASE WHEN :INVOICETYPE_ID = 0 Then INVOICETYPE_ID ELSE :INVOICETYPE_ID END or INVOICETYPE_ID is NULL) "
			+ "and (CASE WHEN :EMPLOYEE_ID = 0 THEN EMPLOYEE_ID=EMPLOYEE_ID ELSE EMPLOYEE_ID IN (:EMPLOYEE_IDS) END or EMPLOYEE_ID is NULL) "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN :PRICELEVEL_ID = 0 Then PRICELEVEL_ID ELSE :PRICELEVEL_ID END or PRICELEVEL_ID is NULL) "
			+ "and (TERMS_ID LIKE CASE WHEN :TERMS_ID = 0 Then TERMS_ID ELSE :TERMS_ID END or TERMS_ID is NULL) "
			+ "and (CREDITTERMS_ID LIKE CASE WHEN :CREDITTERMS_ID = 0 Then CREDITTERMS_ID ELSE :CREDITTERMS_ID END or CREDITTERMS_ID is NULL) "
			+ "and (HOLD_ID LIKE CASE WHEN :HOLD_ID = 0 Then HOLD_ID ELSE :HOLD_ID END or HOLD_ID is NULL) "
			+ "and (CASE WHEN :TAXCODE_ID = 0 THEN TAXCODE_ID=TAXCODE_ID ELSE TAXCODE_ID IN (:TAXCODE_IDS) END or TAXCODE_ID is NULL) "
			+ "and (CURRENCY_ID LIKE CASE WHEN :CURRENCY_ID = 0 Then CURRENCY_ID ELSE :CURRENCY_ID END or CURRENCY_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<PromotionProduct> findByAdvancedSearch(
		    @Param("PERSON_ID") Long PERSON_ID, @Param("PERSON_IDS") List<Integer> PERSON_IDS,
		    @Param("COMPANY_ID") Long COMPANY_ID, @Param("COMPANY_IDS") List<Integer> COMPANY_IDS,
		    @Param("PromotionProductCATEGORY_ID") Long PromotionProductCATEGORY_ID,
		    @Param("PromotionProductSTATUS_ID") Long PromotionProductSTATUS_ID,
		    @Param("LOCATION_ID") Long LOCATION_ID, @Param("LOCATION_IDS") List<Integer> LOCATION_IDS,
		    @Param("BUSINESSTYPE_ID") Long BUSINESSTYPE_ID,    
			@Param("BUSINESSMARKETNICHE_ID") Long BUSINESSMARKETNICHE_ID,
		    @Param("INVOICETYPE_ID") Long INVOICETYPE_ID,
		    @Param("EMPLOYEE_ID") Long EMPLOYEE_ID, @Param("EMPLOYEE_IDS") List<Integer> EMPLOYEE_IDS,
		    @Param("PRICELEVEL_ID") Long PRICELEVEL_ID,
		    @Param("TERMS_ID") Long TERMS_ID,
		    @Param("CREDITTERMS_ID") Long CREDITTERMS_ID,
		    @Param("HOLD_ID") Long HOLD_ID,
		    @Param("TAXCODE_ID") Long TAXCODE_ID, @Param("TAXCODE_IDS") List<Integer> TAXCODE_IDS,
			@Param("CURRENCY_ID") Long PERSONSTATUS_ID);

	@Query(value = "select * from TBLPromotionProduct "
			+ "where (CASE WHEN :PERSON_ID = 0 THEN PERSON_ID=PERSON_ID ELSE PERSON_ID IN (:PERSON_IDS) END or PERSON_ID is NULL) "
			+ "and (CASE WHEN :COMPANY_ID = 0 THEN COMPANY_ID=COMPANY_ID ELSE COMPANY_ID IN (:COMPANY_IDS) END or COMPANY_ID is NULL) "
			+ "and (PromotionProductCATEGORY_ID LIKE CASE WHEN :PromotionProductCATEGORY_ID = 0 Then PromotionProductCATEGORY_ID ELSE :PromotionProductCATEGORY_ID END or PromotionProductCATEGORY_ID is NULL) "
			+ "and (PromotionProductSTATUS_ID LIKE CASE WHEN :PromotionProductSTATUS_ID = 0 Then PromotionProductSTATUS_ID ELSE :PromotionProductSTATUS_ID END or PromotionProductSTATUS_ID is NULL) "
			+ "and (CASE WHEN :LOCATION_ID = 0 THEN LOCATION_ID=LOCATION_ID ELSE LOCATION_ID IN (:LOCATION_IDS) END or LOCATION_ID is NULL) "
			+ "and (BUSINESSTYPE_ID LIKE CASE WHEN :BUSINESSTYPE_ID = 0 Then BUSINESSTYPE_ID ELSE :BUSINESSTYPE_ID END or BUSINESSTYPE_ID is NULL) "
			+ "and (BUSINESSMARKETNICHE_ID LIKE CASE WHEN :BUSINESSMARKETNICHE_ID = 0 Then BUSINESSMARKETNICHE_ID ELSE :BUSINESSMARKETNICHE_ID END or BUSINESSMARKETNICHE_ID is NULL) "
			+ "and (INVOICETYPE_ID LIKE CASE WHEN :INVOICETYPE_ID = 0 Then INVOICETYPE_ID ELSE :INVOICETYPE_ID END or INVOICETYPE_ID is NULL) "
			+ "and (CASE WHEN :EMPLOYEE_ID = 0 THEN EMPLOYEE_ID=EMPLOYEE_ID ELSE EMPLOYEE_ID IN (:EMPLOYEE_IDS) END or EMPLOYEE_ID is NULL) "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN :PRICELEVEL_ID = 0 Then PRICELEVEL_ID ELSE :PRICELEVEL_ID END or PRICELEVEL_ID is NULL) "
			+ "and (TERMS_ID LIKE CASE WHEN :TERMS_ID = 0 Then TERMS_ID ELSE :TERMS_ID END or TERMS_ID is NULL) "
			+ "and (CREDITTERMS_ID LIKE CASE WHEN :CREDITTERMS_ID = 0 Then CREDITTERMS_ID ELSE :CREDITTERMS_ID END or CREDITTERMS_ID is NULL) "
			+ "and (HOLD_ID LIKE CASE WHEN :HOLD_ID = 0 Then HOLD_ID ELSE :HOLD_ID END or HOLD_ID is NULL) "
			+ "and (CASE WHEN :TAXCODE_ID = 0 THEN TAXCODE_ID=TAXCODE_ID ELSE TAXCODE_ID IN (:TAXCODE_IDS) END or TAXCODE_ID is NULL) "
			+ "and (CURRENCY_ID LIKE CASE WHEN :CURRENCY_ID = 0 Then CURRENCY_ID ELSE :CURRENCY_ID END or CURRENCY_ID is NULL) "
			+ "", nativeQuery = true)
	List<PromotionProduct> findAllByAdvancedSearch(
		    @Param("PERSON_ID") Long PERSON_ID, @Param("PERSON_IDS") List<Integer> PERSON_IDS,
		    @Param("COMPANY_ID") Long COMPANY_ID, @Param("COMPANY_IDS") List<Integer> COMPANY_IDS,
		    @Param("PromotionProductCATEGORY_ID") Long PromotionProductCATEGORY_ID,
		    @Param("PromotionProductSTATUS_ID") Long PromotionProductSTATUS_ID,
		    @Param("LOCATION_ID") Long LOCATION_ID, @Param("LOCATION_IDS") List<Integer> LOCATION_IDS,
		    @Param("BUSINESSTYPE_ID") Long BUSINESSTYPE_ID,    
			@Param("BUSINESSMARKETNICHE_ID") Long BUSINESSMARKETNICHE_ID,
		    @Param("INVOICETYPE_ID") Long INVOICETYPE_ID,
		    @Param("EMPLOYEE_ID") Long EMPLOYEE_ID, @Param("EMPLOYEE_IDS") List<Integer> EMPLOYEE_IDS,
		    @Param("PRICELEVEL_ID") Long PRICELEVEL_ID,
		    @Param("TERMS_ID") Long TERMS_ID,
		    @Param("CREDITTERMS_ID") Long CREDITTERMS_ID,
		    @Param("HOLD_ID") Long HOLD_ID,
		    @Param("TAXCODE_ID") Long TAXCODE_ID, @Param("TAXCODE_IDS") List<Integer> TAXCODE_IDS,
			@Param("CURRENCY_ID") Long PERSONSTATUS_ID);

	public List<PromotionProduct> findAllByAdvancedSearch(long promotionproduct_ID, List<Integer> promotionproduct_IDS);

	public List<PromotionProduct> findByAdvancedSearch(long promotionproduct_ID, List<Integer> promotionproduct_IDS);
}


