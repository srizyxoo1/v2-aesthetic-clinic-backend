package com.v2aesthetic.backend.controller;

import com.v2aesthetic.backend.entity.ClinicService;
import com.v2aesthetic.backend.repository.ClinicServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:5173")
public class ClinicServiceController {

    private final ClinicServiceRepository serviceRepository;

    public ClinicServiceController(ClinicServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public ResponseEntity<List<ClinicService>> getAllServices() {
        return ResponseEntity.ok(
                serviceRepository.findAll()
        );
    }

    @PostMapping
    public ResponseEntity<ClinicService> createService(
            @RequestBody ClinicService service) {

        if (service.getActive() == null) {
            service.setActive(true);
        }

        return ResponseEntity.ok(
                serviceRepository.save(service)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicService> updateService(
            @PathVariable Long id,
            @RequestBody ClinicService service) {

        return serviceRepository.findById(id)
                .map(existing -> {

                    existing.setCategory(service.getCategory());
                    existing.setName(service.getName());
                    existing.setDescription(service.getDescription());
                    existing.setPrice(service.getPrice());
                    existing.setDuration(service.getDuration());
                    existing.setImage(service.getImage());
                    existing.setActive(service.getActive());

                    return ResponseEntity.ok(
                            serviceRepository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id) {

        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        serviceRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}