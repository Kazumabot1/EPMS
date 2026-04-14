package com.epms.service;

import com.epms.dto.KpiDTO;
import com.epms.entity.*;
import com.epms.entity.KpiVersionHistory.ChangeType;
import com.epms.exception.DuplicateResourceException;
import com.epms.exception.ResourceNotFoundException;
import com.epms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class KpiService {

    @Autowired private KpiRepository kpiRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PositionRepository positionRepository;
    @Autowired private KpiPositionRepository kpiPositionRepository;
    @Autowired private KpiVersionHistoryRepository versionHistoryRepository;

    public Kpi createKpi(KpiDTO dto, Integer createdByUserId) {
        if (kpiRepository.existsByTitleAndCreatedByUser_Id(dto.getTitle(), createdByUserId)) {
            throw new DuplicateResourceException("KPI with this title already exists for this user");
        }
        User creator = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Kpi kpi = new Kpi();
        kpi.setTitle(dto.getTitle());
        kpi.setKpiCategory(dto.getKpiCategory());
        kpi.setTarget(dto.getTarget());
        kpi.setUnit(dto.getUnit());
        kpi.setWeight(dto.getWeight());
        kpi.setCreatedByUser(creator);
        kpi.setCreatedBy(creator.getFullName());
        kpi.setCreatedAt(new Date());
        kpi.setVersion(1);

        Kpi saved = kpiRepository.save(kpi);

        // Save version history
        KpiVersionHistory history = new KpiVersionHistory();
        history.setKpi(saved);
        history.setKpiColumnName("title");
        history.setNewValue(saved.getTitle());
        history.setChangeType(ChangeType.CREATED);
        history.setModifiedByUser(creator);
        history.setModifiedBy(creator.getFullName());
        history.setChangedAt(new Date());
        history.setChangedReason("Initial creation");
        history.setVersionNumber(1);
        versionHistoryRepository.save(history);

        return saved;
    }

    public Kpi updateKpi(Integer id, KpiDTO dto, Integer updatedByUserId) {
        Kpi kpi = kpiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found"));
        User updater = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update fields and create version history entries
        if (dto.getTitle() != null && !dto.getTitle().equals(kpi.getTitle())) {
            saveVersionHistory(kpi, "title", kpi.getTitle(), dto.getTitle(), ChangeType.UPDATED, updater);
            kpi.setTitle(dto.getTitle());
        }
        if (dto.getKpiCategory() != null && !dto.getKpiCategory().equals(kpi.getKpiCategory())) {
            saveVersionHistory(kpi, "kpiCategory", kpi.getKpiCategory(), dto.getKpiCategory(), ChangeType.CATEGORY_CHANGED, updater);
            kpi.setKpiCategory(dto.getKpiCategory());
        }
        if (dto.getTarget() != null && !dto.getTarget().equals(kpi.getTarget())) {
            saveVersionHistory(kpi, "target", String.valueOf(kpi.getTarget()), String.valueOf(dto.getTarget()), ChangeType.TARGET_MODIFIED, updater);
            kpi.setTarget(dto.getTarget());
        }
        if (dto.getUnit() != null && !dto.getUnit().equals(kpi.getUnit())) {
            saveVersionHistory(kpi, "unit", kpi.getUnit(), dto.getUnit(), ChangeType.UPDATED, updater);
            kpi.setUnit(dto.getUnit());
        }
        if (dto.getWeight() != null && !dto.getWeight().equals(kpi.getWeight())) {
            saveVersionHistory(kpi, "weight", String.valueOf(kpi.getWeight()), String.valueOf(dto.getWeight()), ChangeType.WEIGHT_MODIFIED, updater);
            kpi.setWeight(dto.getWeight());
        }

        kpi.setUpdatedByUser(updater);
        kpi.setUpdatedAt(new Date());
        kpi.setVersion(kpi.getVersion() + 1);
        return kpiRepository.save(kpi);
    }

    public Kpi getKpiById(Integer id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found"));
    }

    public List<Kpi> getAllKpis() {
        return kpiRepository.findAll();
    }

    public void deleteKpi(Integer id, Integer deletedByUserId) {
        Kpi kpi = getKpiById(id);
        User deleter = userRepository.findById(deletedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        saveVersionHistory(kpi, "ALL", kpi.getTitle(), null, ChangeType.DELETED, deleter);
        kpiRepository.delete(kpi);
    }

    public void assignKpiToPosition(Integer kpiId, Integer positionId, Double score, Double targetValue, String assignedBy) {
        Kpi kpi = getKpiById(kpiId);
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
        if (kpiPositionRepository.findByKpi_IdAndPosition_Id(kpiId, positionId).isPresent()) {
            throw new DuplicateResourceException("KPI already assigned to this position");
        }
        KpiPosition kp = new KpiPosition();
        kp.setKpi(kpi);
        kp.setPosition(position);
        kp.setScore(score);
        kp.setTargetValue(targetValue);
        kp.setAssignedDate(new Date());
        kp.setAssignedBy(assignedBy);
        kpiPositionRepository.save(kp);
    }

    // Private helper
    private void saveVersionHistory(Kpi kpi, String column, String oldVal, String newVal, ChangeType type, User user) {
        KpiVersionHistory history = new KpiVersionHistory();
        history.setKpi(kpi);
        history.setKpiColumnName(column);
        history.setOldValue(oldVal);
        history.setNewValue(newVal);
        history.setChangeType(type);
        history.setModifiedByUser(user);
        history.setModifiedBy(user.getFullName());
        history.setChangedAt(new Date());
        history.setVersionNumber(kpi.getVersion() + (type == ChangeType.CREATED ? 0 : 1));
        versionHistoryRepository.save(history);
    }
}