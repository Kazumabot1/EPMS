package com.epms.controller;

import com.epms.dto.*;
import com.epms.entity.KpiPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kpi-positions")
@RequiredArgsConstructor
public class KpiPositionController {

    private final KpiPositionService kpiPositionService;

    @PostMapping
    public ResponseEntity<ApiResponse<KpiPosition>> assign(@RequestBody KpiPositionDTO dto,
                                                           @RequestParam String assignedBy) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.assignKpiToPosition(dto, assignedBy)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KpiPosition>> update(@PathVariable Integer id,
                                                           @RequestBody KpiPositionDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.updateKpiPosition(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KpiPosition>> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.getById(id)));
    }

    @GetMapping("/kpi/{kpiId}")
    public ResponseEntity<ApiResponse<List<KpiPosition>>> getByKpi(@PathVariable Integer kpiId) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.getAssignmentsForKpi(kpiId)));
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<ApiResponse<List<KpiPosition>>> getByPosition(@PathVariable Integer positionId) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.getAssignmentsForPosition(positionId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        // Assuming a method exists to delete by id; add if needed
        kpiPositionService.changeStatus(id, "INACTIVE"); // soft delete via status
        return ResponseEntity.ok(ApiResponse.success("Assignment removed", null));
    }

    @GetMapping("/position/{positionId}/summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSummary(@PathVariable Integer positionId) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.getPerformanceSummaryForPosition(positionId)));
    }

    @GetMapping("/kpi/{kpiId}/top")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopPerformers(
            @PathVariable Integer kpiId,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(ApiResponse.success(kpiPositionService.getTopPerformersForKpi(kpiId, limit)));
    }
}