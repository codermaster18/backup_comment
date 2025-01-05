package com.uma.wiki.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class CommentEntity {

    @MongoId
    @Getter
    @Builder.Default
    private ObjectId id = new ObjectId();

    @NotNull
    @Getter
    private String commentId;

    //@NotNull
    @Getter
    private UserEntity userEntity;

    //@NotNull
    @Getter
    private EntryEntity entryEntity;

    @Getter
    private Integer valoracion;

    private String content;

    @Getter
    private LocalDateTime creationDate;

    public CommentEntity(String commentId, UserEntity userEntity, EntryEntity entryEntity, Integer valoracion, String content) {
        this.id = new ObjectId();
        this.commentId = validateCommentId(commentId) ? commentId : null;
        this.creationDate = LocalDateTime.now();
        this.userEntity = userEntity;
        this.entryEntity = entryEntity;
        this.valoracion = valoracion;
        this.content = content;
    }

    public CommentEntity(UserEntity userEntity, EntryEntity entryEntity, Integer valoracion, String content) {
        this.id = new ObjectId();
        this.commentId = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.userEntity = userEntity;
        this.entryEntity = entryEntity;
        this.valoracion = valoracion;
        this.content = content;
    }



    private boolean validateCommentId(String commentId) {
        try {
            UUID.fromString(commentId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("commentId must be a valid UUID");
        }

        return true;
    }
}
