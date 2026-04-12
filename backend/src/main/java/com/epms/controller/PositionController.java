package com.epms.controller;

import com.epms.dto.*;
import com.epms.entity.Position;
import com.epms.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Position>> create(@RequestBody PositionDTO dto,
                                                        @RequestParam String createdBy) {
        Position pos = positionService.createPosition(dto, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Position created", pos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Position>> update(@PathVariable Integer id,
                                                        @RequestBody PositionDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(positionService.updatePosition(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Position>> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(positionService.getPositionById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Position>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(positionService.getAllPositions()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok(ApiResponse.success("Position deleted", null));
    }

    @PostMapping("/{positionId}/kpis/{kpiId}")
    public ResponseEntity<ApiResponse<Void>> assignKpi(
            @PathVariable Integer positionId,
            @PathVariable Integer kpiId,
            @RequestParam Double score,
            @RequestParam(required = false) Double targetValue,
            @RequestParam String assignedBy) {
        positionService.assignKpiToPosition(positionId, kpiId, score, targetValue, assignedBy);
        return ResponseEntity.ok(ApiResponse.success("KPI assigned", null));
    }

    @GetMapping("/{id}/performance")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPerformance(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(positionService.getPositionPerformanceSummary(id)));
    }
}