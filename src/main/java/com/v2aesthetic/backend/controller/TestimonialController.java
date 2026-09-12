package com.v2aesthetic.backend.controller;

import com.v2aesthetic.backend.entity.Testimonial;
import com.v2aesthetic.backend.repository.TestimonialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@CrossOrigin(origins = "http://localhost:5173")
public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @GetMapping
    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Testimonial> getTestimonialById(@PathVariable Long id) {
        return testimonialRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Testimonial> createTestimonial(
            @RequestBody Testimonial testimonial
    ) {
        Testimonial saved = testimonialRepository.save(testimonial);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testimonial> updateTestimonial(
            @PathVariable Long id,
            @RequestBody Testimonial details
    ) {
        return testimonialRepository.findById(id)
                .map(testimonial -> {

                    testimonial.setCustomerName(details.getCustomerName());
                    testimonial.setService(details.getService());
                    testimonial.setReview(details.getReview());
                    testimonial.setRating(details.getRating());
                    testimonial.setGoogleReviewLink(details.getGoogleReviewLink());
                    testimonial.setImage(details.getImage());
                    testimonial.setActive(details.isActive());

                    return ResponseEntity.ok(
                            testimonialRepository.save(testimonial)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {

        if (!testimonialRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        testimonialRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}