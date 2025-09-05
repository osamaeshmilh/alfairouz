package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.Receipt;
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.repository.ReceiptRepository;
import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.service.dto.ReceiptDTO;
import ly.alfairouz.lab.service.mapper.ReceiptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Receipt}.
 */
@Service
@Transactional
public class ReceiptService {

    private final Logger log = LoggerFactory.getLogger(ReceiptService.class);

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    private final SpecimenRepository specimenRepository; // Add this


    public ReceiptService(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper, SpecimenRepository specimenRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
        this.specimenRepository = specimenRepository;
    }

    public ReceiptDTO save(ReceiptDTO receiptDTO) {
        log.debug("Request to save Receipt : {}", receiptDTO);
        Receipt receipt = receiptMapper.toEntity(receiptDTO);
        receipt = receiptRepository.save(receipt);

        // Update specimen paid amounts
        updateSpecimenPaidAmounts(receipt.getSpecimen().getId());

        return receiptMapper.toDto(receipt);
    }

    public ReceiptDTO update(ReceiptDTO receiptDTO) {
        log.debug("Request to update Receipt : {}", receiptDTO);
        Receipt receipt = receiptMapper.toEntity(receiptDTO);
        receipt = receiptRepository.save(receipt);

        // Update specimen paid amounts
        updateSpecimenPaidAmounts(receipt.getSpecimen().getId());

        return receiptMapper.toDto(receipt);
    }

    public Optional<ReceiptDTO> partialUpdate(ReceiptDTO receiptDTO) {
        log.debug("Request to partially update Receipt : {}", receiptDTO);

        return receiptRepository
            .findById(receiptDTO.getId())
            .map(existingReceipt -> {
                Long specimenId = existingReceipt.getSpecimen().getId();
                receiptMapper.partialUpdate(existingReceipt, receiptDTO);

                Receipt saved = receiptRepository.save(existingReceipt);
                // Update specimen paid amounts
                updateSpecimenPaidAmounts(specimenId);

                return saved;
            })
            .map(receiptMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Receipt : {}", id);

        // Get specimen ID before deleting receipt
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isPresent()) {
            Long specimenId = receipt.get().getSpecimen().getId();
            receiptRepository.deleteById(id);

            // Update specimen paid amounts after deletion
            updateSpecimenPaidAmounts(specimenId);
        }
    }

    /**
     * Recalculates and updates the specimen's paid and notPaid amounts based on all receipts
     */
    private void updateSpecimenPaidAmounts(Long specimenId) {
        log.debug("Updating specimen paid amounts for specimen ID: {}", specimenId);

        Optional<Specimen> specimenOpt = specimenRepository.findById(specimenId);
        if (specimenOpt.isPresent()) {
            Specimen specimen = specimenOpt.get();

            // Calculate total paid from all receipts (including refunds as negative amounts)
            Double totalPaid = receiptRepository.findBySpecimenId(specimenId)
                .stream()
                .mapToDouble(receipt -> receipt.getPaid() != null ? receipt.getPaid() : 0.0)
                .sum();

            // Calculate notPaid (remaining balance)
            Double price = specimen.getPrice() != null ? specimen.getPrice() : 0.0;
            Double notPaid = price - totalPaid;

            // Update specimen
            specimen.setPaid(totalPaid.floatValue());
            specimen.setNotPaid(notPaid.floatValue());

            specimenRepository.save(specimen);

            log.debug("Updated specimen {} - Price: {}, Paid: {}, NotPaid: {}",
                specimenId, price, totalPaid, notPaid);
        }
    }

    /**
     * Get all the receipts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Receipts");
        return receiptRepository.findAll(pageable).map(receiptMapper::toDto);
    }

    /**
     * Get all the receipts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ReceiptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return receiptRepository.findAllWithEagerRelationships(pageable).map(receiptMapper::toDto);
    }

    /**
     * Get one receipt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReceiptDTO> findOne(Long id) {
        log.debug("Request to get Receipt : {}", id);
        return receiptRepository.findOneWithEagerRelationships(id).map(receiptMapper::toDto);
    }

}
