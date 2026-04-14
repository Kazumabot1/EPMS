package com.epms.controller;

import com.epms.dto.*;
import com.epms.entity.Kpi;
import com.epms.service.KpiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    @PostMapping
    public ResponseEntity<ApiResponse<Kpi>> createKpi(@Valid @RequestBody KpiDTO dto,
                                                      @RequestParam Integer createdBy) {
        Kpi kpi = kpiService.createKpi(dto, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("KPI created successfully", kpi));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Kpi>> updateKpi(@PathVariable Integer id,
                                                      @Valid @RequestBody KpiDTO dto,
                                                      @RequestParam Integer updatedBy) {
        Kpi kpi = kpiService.updateKpi(id, dto, updatedBy);
        return ResponseEntity.ok(ApiResponse.success("KPI updated", kpi));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Kpi>> getKpi(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(kpiService.getKpiById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Kpi>>> getAllKpis() {
        return ResponseEntity.ok(ApiResponse.success(kpiService.getAllKpis()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteKpi(@PathVariable Integer id,
                                                       @RequestParam Integer deletedBy) {
        kpiService.deleteKpi(id, deletedBy);
        return ResponseEntity.ok(ApiResponse.success("KPI deleted", null));
    }

    @PostMapping("/{kpiId}/positions/{positionId}")
    public ResponseEntity<ApiResponse<Void>> assignToPosition(
            @PathVariable Integer kpiId,
            @PathVariable Integer positionId,
            @RequestParam Double score,
            @RequestParam(required = false) Double targetValue,
            @RequestParam String assignedBy) {
        kpiService.assignKpiToPosition(kpiId, positionId, score, targetValue, assignedBy);
        return ResponseEntity.ok(ApiResponse.success("KPI assigned to position", null));
    }

    @DeleteMapping("/{kpiId}/positions/{positionId}")
    public ResponseEntity<ApiResponse<Void>> removeFromPosition(
            @PathVariable Integer kpiId,
            @PathVariable Integer positionId) {
        kpiService.removeKpiFromPosition(kpiId, positionId);
        return ResponseEntity.ok(ApiResponse.success("KPI removed from position", null));
    }

    @GetMapping("/{id}/performance")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPerformance(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(kpiService.getKpiPerformanceAcrossPositions(id)));
    }
}