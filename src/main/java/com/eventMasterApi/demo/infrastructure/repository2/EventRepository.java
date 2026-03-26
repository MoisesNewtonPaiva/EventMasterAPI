package com.eventMasterApi.demo.infrastructure.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventMasterApi.demo.infrastructure.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
    
    public List<Event> findByNameIgnoreCase(String name);
    public boolean existsByNameIgnoreCase(String name);
}
