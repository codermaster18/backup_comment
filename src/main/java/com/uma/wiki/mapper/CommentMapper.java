package com.uma.wiki.mapper;

import com.uma.wiki.dto.CommentResponseDto;
import com.uma.wiki.dto.CommentCreateDto;
import com.uma.wiki.entity.CommentEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class    CommentMapper {

    public static CommentResponseDto toCommentResponseDto(CommentEntity commentEntity) {
        if(commentEntity == null) return null;

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentId(commentEntity.getCommentId());
        dto.setCreationDate(commentEntity.getCreationDate());
        dto.setContent(commentEntity.getContent());
        //dto.setUserEntity(commentEntity.getUserEntity());
        //dto.setEntryEntity(commentEntity.getEntryEntity());
        dto.setUserEntity(null);
        dto.setEntryEntity(null);
        dto.setValoracion(commentEntity.getValoracion());
        return dto;

    }

    public static CommentEntity toEntityInCreation(CommentCreateDto commentCreateDto) {
        if(commentCreateDto == null) return null;

        CommentEntity commentEntity = new CommentEntity(
                commentCreateDto.getUserEntity(),
                commentCreateDto.getEntryEntity(),
                commentCreateDto.getValoracion(),
                commentCreateDto.getContent()
        );

        return commentEntity;
    }
}
