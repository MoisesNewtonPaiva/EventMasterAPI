package com.eventMasterApi.demo.infrastructure.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventMasterApi.demo.infrastructure.entities.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    public boolean existsByTitleContainingIgnoreCase(String title);

    public List<Activity> findByTitleContainingIgnoreCase(String title);
}