package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.BlockWithdraw;
import ly.alfairouz.lab.repository.BlockWithdrawRepository;
import ly.alfairouz.lab.service.dto.BlockWithdrawDTO;
import ly.alfairouz.lab.service.mapper.BlockWithdrawMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BlockWithdraw}.
 */
@Service
@Transactional
public class BlockWithdrawService {

    private final Logger log = LoggerFactory.getLogger(BlockWithdrawService.class);

    private final BlockWithdrawRepository blockWithdrawRepository;

    private final BlockWithdrawMapper blockWithdrawMapper;

    public BlockWithdrawService(BlockWithdrawRepository blockWithdrawRepository, BlockWithdrawMapper blockWithdrawMapper) {
        this.blockWithdrawRepository = blockWithdrawRepository;
        this.blockWithdrawMapper = blockWithdrawMapper;
    }

    /**
     * Save a blockWithdraw.
     *
     * @param blockWithdrawDTO the entity to save.
     * @return the persisted entity.
     */
    public BlockWithdrawDTO save(BlockWithdrawDTO blockWithdrawDTO) {
        log.debug("Request to save BlockWithdraw : {}", blockWithdrawDTO);
        BlockWithdraw blockWithdraw = blockWithdrawMapper.toEntity(blockWithdrawDTO);
        blockWithdraw = blockWithdrawRepository.save(blockWithdraw);
        return blockWithdrawMapper.toDto(blockWithdraw);
    }

    /**
     * Update a blockWithdraw.
     *
     * @param blockWithdrawDTO the entity to save.
     * @return the persisted entity.
     */
    public BlockWithdrawDTO update(BlockWithdrawDTO blockWithdrawDTO) {
        log.debug("Request to save BlockWithdraw : {}", blockWithdrawDTO);
        BlockWithdraw blockWithdraw = blockWithdrawMapper.toEntity(blockWithdrawDTO);
        blockWithdraw = blockWithdrawRepository.save(blockWithdraw);
        return blockWithdrawMapper.toDto(blockWithdraw);
    }

    /**
     * Partially update a blockWithdraw.
     *
     * @param blockWithdrawDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BlockWithdrawDTO> partialUpdate(BlockWithdrawDTO blockWithdrawDTO) {
        log.debug("Request to partially update BlockWithdraw : {}", blockWithdrawDTO);

        return blockWithdrawRepository
            .findById(blockWithdrawDTO.getId())
            .map(existingBlockWithdraw -> {
                blockWithdrawMapper.partialUpdate(existingBlockWithdraw, blockWithdrawDTO);

                return existingBlockWithdraw;
            })
            .map(blockWithdrawRepository::save)
            .map(blockWithdrawMapper::toDto);
    }

    /**
     * Get all the blockWithdraws.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BlockWithdrawDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BlockWithdraws");
        return blockWithdrawRepository.findAll(pageable).map(blockWithdrawMapper::toDto);
    }

    /**
     * Get all the blockWithdraws with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BlockWithdrawDTO> findAllWithEagerRelationships(Pageable pageable) {
        return blockWithdrawRepository.findAllWithEagerRelationships(pageable).map(blockWithdrawMapper::toDto);
    }

    /**
     * Get one blockWithdraw by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BlockWithdrawDTO> findOne(Long id) {
        log.debug("Request to get BlockWithdraw : {}", id);
        return blockWithdrawRepository.findOneWithEagerRelationships(id).map(blockWithdrawMapper::toDto);
    }

    /**
     * Delete the blockWithdraw by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BlockWithdraw : {}", id);
        blockWithdrawRepository.deleteById(id);
    }
}
