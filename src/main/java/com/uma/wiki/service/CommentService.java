package com.uma.wiki.service;

import com.uma.wiki.dao.CommentRepository;
import com.uma.wiki.dto.CommentCreateDto;
import com.uma.wiki.dto.CommentResponseDto;
import com.uma.wiki.dto.CommentUpdateDTO;
import com.uma.wiki.entity.CommentEntity;
import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.exception.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.uma.wiki.mapper.CommentMapper.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentResponseDto getComment(String commentId) {
        CommentEntity commentEntity = this.commentRepository.findByCommentId(commentId);

        if (commentEntity == null) {
            throw new CommentNotFoundException("Comment with ID " + commentId + " doesn't exist in DB");
        }

        return toCommentResponseDto(commentEntity);
    }

    public void deleteComment(String commentId) {
        CommentEntity commentEntity = this.commentRepository.findByCommentId(commentId);
        if (commentEntity == null) {
            throw new CommentNotFoundException("Comment with ID " + commentId + " doesn't exist in DB");
        }

        this.commentRepository.deleteByCommentId(commentId);
    }

    public CommentResponseDto createComment(CommentCreateDto commentCreateDto) {
        CommentEntity commentEntity = toEntityInCreation(commentCreateDto);
        commentEntity = this.commentRepository.save(commentEntity);

        return toCommentResponseDto(commentEntity);
    }

    public CommentResponseDto updateComment(CommentUpdateDTO commentUpdateDTO) {
        CommentEntity commentEntity = this.commentRepository.findByCommentId(commentUpdateDTO.getCommentId());
        if (commentEntity == null) {
            throw new CommentNotFoundException("Comment with ID " + commentUpdateDTO.getCommentId() + " doesn't exist in DB");
        }
        commentEntity.setContent(commentUpdateDTO.getContent());
        commentEntity.setValoracion(commentUpdateDTO.getValoracion());

        this.commentRepository.save(commentEntity);

        return toCommentResponseDto(commentEntity);
    }

    public List<CommentResponseDto> getCommentsByRating(Integer valoracion) {
        List<CommentEntity> commentEntities = this.commentRepository.findByValoracionGreaterOrEqualThan(valoracion);
        if(commentEntities == null || commentEntities.isEmpty()) {
            throw new CommentNotFoundException("Comments with  rating greater than or equals to " + valoracion + " doesn't exist in DB");
        }
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntities) {
            commentResponseDtos.add(toCommentResponseDto(commentEntity));
        }
        return commentResponseDtos;
    }

    public List<CommentResponseDto> getCommentsByContent(String content) {
        List<CommentEntity> commentEntities = this.commentRepository.findByContentContains(content);
        if(commentEntities == null || commentEntities.isEmpty()) {
            throw new CommentNotFoundException("Comments with  contenido " + content + " doesn't exist in DB");
        }
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntities) {
            commentResponseDtos.add(toCommentResponseDto(commentEntity));
        }
        return commentResponseDtos;
    }



    private void sendNotificationEntryAuthorOfNewComment(UserEntity userEntity) {
        //todo send notification to user
    }
}
