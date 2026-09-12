
package com.v2aesthetic.backend.controller;

import com.v2aesthetic.backend.entity.Doctor;
import com.v2aesthetic.backend.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctorDetails
    ) {
        return doctorRepository.findById(id)
                .map(doctor -> {

                    doctor.setName(doctorDetails.getName());
                    doctor.setSpecialization(doctorDetails.getSpecialization());
                    doctor.setExperience(doctorDetails.getExperience());
                    doctor.setConsultationFee(
                            doctorDetails.getConsultationFee()
                    );
                    doctor.setPhone(doctorDetails.getPhone());
                    doctor.setDescription(doctorDetails.getDescription());
                    doctor.setImage(doctorDetails.getImage());
                    doctor.setActive(doctorDetails.isActive());

                    return ResponseEntity.ok(
                            doctorRepository.save(doctor)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {

        if (!doctorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        doctorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}