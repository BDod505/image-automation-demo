package com.example.demo.image.automation.repository;

import com.example.demo.image.automation.entity.StyleRender;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRenderRepository extends MongoRepository<StyleRender, String> {
}
