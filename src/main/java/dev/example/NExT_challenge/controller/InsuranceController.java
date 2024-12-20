package dev.example.NExT_challenge.controller;

import dev.example.NExT_challenge.domain.insurance.*;
import dev.example.NExT_challenge.service.InsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/insurance")
@AllArgsConstructor
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;

    @PostMapping("/life")
    public ResponseEntity<Insurance> createInsuranceLife(@RequestBody RequestLifePlanDTO dto) {
        return ResponseEntity.ok(this.insuranceService.createLifePlan(dto));

    }

    @PostMapping("/disability")
    public ResponseEntity<Insurance> createInsuranceDisability(@RequestBody RequestDisabilityPlanDTO dto) {
        return ResponseEntity.ok(this.insuranceService.createDisabilityPlan(dto));
    }

    @PostMapping("/home")
    public ResponseEntity<Insurance> createInsuranceHome(@RequestBody RequestHousePlanDTO dto) {
        return ResponseEntity.ok(this.insuranceService.createHousePlan(dto));
    }

    @PostMapping("/auto")
        public ResponseEntity<Insurance> createInsuranceAuto(@RequestBody InsuranceAutoDTO dto) {
            return ResponseEntity.ok(this.insuranceService.createAutoPlan(dto));
    }
}
