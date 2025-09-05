package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.Specimen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Specimen entity.
 */
@Repository
public interface SpecimenRepository extends JpaRepository<Specimen, Long>, JpaSpecificationExecutor<Specimen> {
    default Optional<Specimen> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Specimen> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Specimen> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct specimen from Specimen specimen left join fetch specimen.patient left join fetch specimen.biopsy left join fetch specimen.cytology left join fetch specimen.organ left join fetch specimen.specimenType left join fetch specimen.size left join fetch specimen.referringCenter left join fetch specimen.grossingDoctor left join fetch specimen.referringDoctor left join fetch specimen.pathologistDoctor left join fetch specimen.operatorEmployee left join fetch specimen.correctorEmployee",
        countQuery = "select count(distinct specimen) from Specimen specimen"
    )
    Page<Specimen> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct specimen from Specimen specimen left join fetch specimen.patient left join fetch specimen.biopsy left join fetch specimen.cytology left join fetch specimen.organ left join fetch specimen.specimenType left join fetch specimen.size left join fetch specimen.referringCenter left join fetch specimen.grossingDoctor left join fetch specimen.referringDoctor left join fetch specimen.pathologistDoctor left join fetch specimen.operatorEmployee left join fetch specimen.correctorEmployee"
    )
    List<Specimen> findAllWithToOneRelationships();

    @Query(
        "select specimen from Specimen specimen left join fetch specimen.patient left join fetch specimen.biopsy left join fetch specimen.cytology left join fetch specimen.organ left join fetch specimen.specimenType left join fetch specimen.size left join fetch specimen.referringCenter left join fetch specimen.grossingDoctor left join fetch specimen.referringDoctor left join fetch specimen.pathologistDoctor left join fetch specimen.operatorEmployee left join fetch specimen.correctorEmployee where specimen.id =:id"
    )
    Optional<Specimen> findOneWithToOneRelationships(@Param("id") Long id);

    //TODO:: ignore case
    @Query(
        "select specimen from Specimen specimen left join fetch specimen.patient left join fetch specimen.biopsy left join fetch specimen.cytology left join fetch specimen.organ left join fetch specimen.specimenType left join fetch specimen.size left join fetch specimen.referringCenter left join fetch specimen.grossingDoctor left join fetch specimen.referringDoctor left join fetch specimen.pathologistDoctor left join fetch specimen.operatorEmployee left join fetch specimen.correctorEmployee where specimen.labQr =:labQr"
    )
    Optional<Specimen> findOneByLabQrWithToOneRelationships(@Param("labQr") String labQr);

    @Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(lab_ref_no, LENGTH(:prefix) + 1, 5) AS UNSIGNED)), 0) " +
        "FROM specimen " +
        "WHERE lab_ref_no LIKE CONCAT(:prefix, '%') " +
        "AND lab_ref_no LIKE CONCAT('%-', :year)",
        nativeQuery = true)
    long getMaxNumberByTypeAndYear(@Param("prefix") String prefix, @Param("year") String year);

    Optional<Specimen> findByLabRefNo(String labRefNo);

}
