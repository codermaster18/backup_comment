package com.uma.wiki.dto;

import com.uma.wiki.entity.EntryEntity;
import com.uma.wiki.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentCreateDto {

    @NotNull
    private String content;

    private Integer valoracion;

    //@NotNull desactivado porque todavía no está modelado
    private UserEntity userEntity;

    //@NotNull desactivado porque todavía no está modelado
    private EntryEntity entryEntity;
}
