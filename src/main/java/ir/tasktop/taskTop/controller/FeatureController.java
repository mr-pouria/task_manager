package ir.tasktop.taskTop.controller;


import ir.tasktop.taskTop.dto.NewFeatureDto;
import ir.tasktop.taskTop.service.FeatureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class FeatureController {

    @Autowired
    FeatureService featureService;

    @PostMapping("/public/getAllFeatures")
    public ResponseEntity<?> getFeatures() {
        return featureService.getFeatures();
    }

    @PostMapping("/releaseNewFeature")
    public ResponseEntity<?> releaseNewFeature(@Valid @RequestBody NewFeatureDto newFeatureDto , BindingResult bindingResult) {
        return featureService.releaseNewFeature(newFeatureDto , bindingResult);
    }
}
