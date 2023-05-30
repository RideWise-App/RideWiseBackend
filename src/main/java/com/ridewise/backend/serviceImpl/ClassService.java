package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.constants.ClassType;
import com.ridewise.backend.entity.Class;
import com.ridewise.backend.repository.ClassRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassService {
    private final ClassRepository repository;

    public void addClass(Class newClass) {
        repository.save(newClass);
    }

    public List<Class> findAll() {
        return repository.findAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    private void resetClasses() {
        if (repository.findAll().size() < 3) {
            Class economyClass = Class.builder().classType(ClassType.ECONOMY).rate(5.00).build();
            Class standardClass = Class.builder().classType(ClassType.STANDARD).rate(10.00).build();
            Class premiumClass = Class.builder().classType(ClassType.PREMIUM).rate(15.00).build();
            repository.deleteAll();
            repository.save(economyClass);
            repository.save(standardClass);
            repository.save(premiumClass);
        }
    }
}
