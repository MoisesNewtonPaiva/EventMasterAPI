package com.eventMasterApi.demo.infrastructure.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventMasterApi.demo.DTO.ActivityDTO;
import com.eventMasterApi.demo.infrastructure.entities.Activity;
import com.eventMasterApi.demo.infrastructure.repository2.ActivityRepository;

import jakarta.transaction.Transactional;



@Service
public class ActivityService {
    
    @Autowired
    private ActivityRepository repository;

    ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(
            activity.getId(),
            activity.getTitle(),
            activity.getDescription(),
            activity.getDate(),
            null, // Assuming participantIds is not being set here
            activity.getEvent()
        );
    }

    @Transactional
    public ResponseEntity<ActivityDTO> insertActivity(ActivityDTO dto) {
        
        // Validações antes de qualquer operação no banco
        var existingActivity = repository.findByTitleIgnoreCase(dto.title());

        if (existingActivity != null) {
            return ResponseEntity.badRequest().build();
        }

        if (dto.date().isBefore(java.time.Instant.now())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Activity activity = new Activity();
            activity.setTitle(dto.title());
            activity.setDescription(dto.description());
            activity.setDate(dto.date());
            activity.setEvent(dto.event());
            
            Activity savedActivity = repository.save(activity);
            return ResponseEntity.ok(convertToDTO(savedActivity));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

   public List<ActivityDTO> findAllActivities() {
        return repository.findAll().stream().map(activity -> new ActivityDTO(
            activity.getId(),
            activity.getTitle(),
            activity.getDescription(),
            activity.getDate(),
            null, // Assuming participantIds is not being set here
            activity.getEvent()
        )).toList();
   }

   public Optional<ActivityDTO> findActivityById(Long id) {
        return repository.findById(id).map(activity -> new ActivityDTO(
            activity.getId(),
            activity.getTitle(),
            activity.getDescription(),
            activity.getDate(),
            null, // Assuming participantIds is not being set here
            activity.getEvent()
        ));
   }

   public ResponseEntity<ActivityDTO> updateActivityDTO(Long id, ActivityDTO dto) {
        Optional<Activity> existingActivityOpt = repository.findById(id);

        if (existingActivityOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Activity existingActivity = existingActivityOpt.get();

        if (!existingActivity.getTitle().equalsIgnoreCase(dto.title()) && repository.existsByTitleIgnoreCase(dto.title())) {
            return ResponseEntity.badRequest().build();
        }

        if (dto.date().isBefore(java.time.Instant.now())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            existingActivity.setTitle(dto.title());
            existingActivity.setDescription(dto.description());
            existingActivity.setDate(dto.date());
            existingActivity.setEvent(dto.event());

            Activity updatedActivity = repository.save(existingActivity);
            return ResponseEntity.ok(convertToDTO(updatedActivity));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
   }

   public String deleteActivity(Long id) {
        Optional<Activity> existingActivityOpt = repository.findById(id);

        if (existingActivityOpt.isEmpty()) {
            return "Activity not found.";
        }

        try {
            repository.deleteById(id);
            return "Activity deleted successfully.";
        } catch (Exception e) {
            return "Error deleting activity: " + e.getMessage();
        }
   }

}