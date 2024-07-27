package io.javabrains.tinder_ai_backend.profiles;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile,String> {
    @Aggregation( pipeline = {"{$sample: { size: 1} }"})
    Profile getRandomProfile();
}
