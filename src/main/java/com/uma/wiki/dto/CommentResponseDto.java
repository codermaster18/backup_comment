package com.uma.wiki.dto;

import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.entity.EntryEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private String commentId;

    private String content;

    private Integer valoracion;

    private LocalDateTime creationDate;

    //@NotNull desactivado porque todavía no está modelado
    private UserEntity userEntity;

    //@NotNull desactivado porque todavía no está modelado
    private EntryEntity entryEntity; // Relación con la entrada (Entry) en la que se comenta

}

