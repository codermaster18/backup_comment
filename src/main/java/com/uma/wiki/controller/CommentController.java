package com.uma.wiki.controller;

import com.uma.wiki.dto.*;
import com.uma.wiki.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/comment")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(summary = "Get a comment by ID", description = "Returns a comment based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseWrapper<CommentResponseDto>> getComment(@RequestParam("commentId") String commentId) {
        try {
            CommentResponseDto commentResponseDto = commentService.getComment(commentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>(commentResponseDto, HttpStatus.OK.value(), "Comment retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving comment"));
        }
    }

    @Operation(summary = "Get a list of comment by minimum rating", description = "Returns a list based on its minimum rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/byvaloracion")
    public ResponseEntity<ResponseWrapper<List<CommentResponseDto>>> getCommentByValoracion(@RequestParam("valoracion") Integer valoracion) {
        try {
            List<CommentResponseDto> commentsResponseDto = commentService.getCommentsByRating(valoracion);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>(commentsResponseDto, HttpStatus.OK.value(), "Comments retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving comment"));
        }
    }

    @GetMapping("/bycontent")
    public ResponseEntity<ResponseWrapper<List<CommentResponseDto>>> getCommentByContent(@RequestParam("content") String content) {
        try {
            List<CommentResponseDto> commentsResponseDto = commentService.getCommentsByContent(content);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>(commentsResponseDto, HttpStatus.OK.value(), "Comments retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving comment"));
        }
    }

    @Operation(summary = "Create a new comment", description = "Creates a new comment in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
        public ResponseEntity<ResponseWrapper<CommentResponseDto>> createComment(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        CommentResponseDto newCommentResponseDTO = commentService.createComment(commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(newCommentResponseDTO, HttpStatus.CREATED.value(), "Entry created successfully"));
    }

    @Operation(summary = "Updates an existing comment", description = "This endpoint allows clients to update comments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping
    public ResponseEntity<ResponseWrapper<CommentResponseDto>> updateComment(@Valid @RequestBody CommentUpdateDTO commentUpdateDTO) {
        try {
            CommentResponseDto newCommentResponseDto = commentService.updateComment(commentUpdateDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(newCommentResponseDto, HttpStatus.CREATED.value(), "Comment updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating comment"));
        }
    }

    @Operation(summary = "Delete a comment", description = "This endpoint allows clients to delete a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<ResponseWrapper<String>> deleteComment(@RequestParam("commentId") String idComment) {
        try {
            commentService.deleteComment(idComment);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>("Correctly deleted", HttpStatus.OK.value(), "Comment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting comment"));
        }
    }
}
