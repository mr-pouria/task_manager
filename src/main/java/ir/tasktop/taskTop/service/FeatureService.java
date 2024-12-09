package ir.tasktop.taskTop.service;


import ir.tasktop.taskTop.dto.NewFeatureDto;
import ir.tasktop.taskTop.model.Feature;
import ir.tasktop.taskTop.repo.FeatureRepo;
import ir.tasktop.taskTop.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class FeatureService {

    @Autowired
    FeatureRepo featureRepo;

    @Autowired
    ResponseHandler responseHandler;

    public ResponseEntity<?> getFeatures() {
        List<Feature> features = featureRepo.findAll();
        return responseHandler.responseBack(features , null , null , HttpStatus.OK);
    }

    public ResponseEntity<?> releaseNewFeature(NewFeatureDto feature , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseHandler.responseBack(null , null , bindingResult.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (featureRepo.existsByName(feature.getName())) {
            return responseHandler.responseBack(null , null, "این نام قبلا در سیستم ثبت شده است" , HttpStatus.BAD_REQUEST);
        }
        Feature newFeature = new Feature();
        newFeature.setName(feature.getName());
        newFeature.setDescription(feature.getDescription());
        newFeature.setReleaseDate(feature.getReleaseDate());
        featureRepo.save(newFeature);
        return responseHandler.responseBack(null , "رکورد جدید با موفقیت اضافه شد" , null , HttpStatus.CREATED);
    }
}
