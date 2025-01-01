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

    @Query(value = "SELECT COUNT(id) " +
        "FROM specimen " +
        "where SUBSTRING(specimen.lab_ref_no, 1, LENGTH(:prefix)) = :prefix",
        nativeQuery = true)
    long countByLabRefNoStartingWith(@Param("prefix") String prefix);

    @Query(value = "SELECT COUNT(id) " +
        "FROM specimen " +
        "WHERE SUBSTRING(specimen.lab_ref_no, 1, LENGTH(:prefix)) = :prefix " +
        "AND SUBSTRING(specimen.lab_ref_no, -2) = :suffix",
        nativeQuery = true)
    long countByLabRefNoStartingWithAndEndingWith(@Param("prefix") String prefix, @Param("suffix") String suffix);

    @Query("SELECT s.labRefNo FROM Specimen s " +
        "WHERE s.labRefNo LIKE CONCAT(:prefix, '%') " +
        "ORDER BY s.labRefNo DESC")
    List<String> findMaxLabRefNoStartingWith(@Param("prefix") String prefix, Pageable pageable);

    @Query("SELECT s.labRefNo FROM Specimen s " +
        "WHERE (s.labRefNo LIKE CONCAT(:yearH, '%') " +
        "OR s.labRefNo LIKE CONCAT(:yearHSO, '%') " +
        "OR s.labRefNo LIKE CONCAT(:yearIHSO, '%')) " +
        "ORDER BY s.labRefNo DESC")
    List<String> findMaxLabRefNoForSharedTypes(
        @Param("yearH") String yearH,
        @Param("yearHSO") String yearHSO,
        @Param("yearIHSO") String yearIHSO,
        Pageable pageable
    );
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

}
