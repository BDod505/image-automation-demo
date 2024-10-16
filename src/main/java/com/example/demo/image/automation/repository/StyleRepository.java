package com.example.demo.image.automation.repository;

import com.example.demo.image.automation.entity.Style;
import com.example.demo.image.automation.models.StyleDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends MongoRepository<Style,String> {
}
