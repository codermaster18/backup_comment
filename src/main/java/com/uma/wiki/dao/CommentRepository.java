package com.uma.wiki.dao;

import com.uma.wiki.entity.CommentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId> {
    CommentEntity findByCommentId(String commentId);
    
    void deleteByCommentId(String commentId);

    @Query("{ 'content' : { $regex: ?0, $options: 'i' } }")
    List<CommentEntity> findByContentContains(@Param("content") String content);

    @Query("{ 'valoracion' : { $gte: ?0 } }")
    List<CommentEntity> findByValoracionGreaterOrEqualThan(@Param("valoracion") Integer valoracion);

    //List<CommentEntity> findByEntryEntityIdOrderByCreationDateDesc (String entryId);
    //List<CommentEntity> findByUserEntityIdOrderByCreationDateDesc (String userEntityId);
}
