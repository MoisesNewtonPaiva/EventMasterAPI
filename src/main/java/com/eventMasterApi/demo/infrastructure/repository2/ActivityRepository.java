package com.eventMasterApi.demo.infrastructure.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventMasterApi.demo.infrastructure.entities.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    public Activity findByName(String title);
}