package com.eventMasterApi.demo.infrastructure.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventMasterApi.demo.infrastructure.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
    
}
