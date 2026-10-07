package com.todocodeacademy.BlogCode.mapper;

import com.todocodeacademy.BlogCode.dto.PostRequestDTO;
import com.todocodeacademy.BlogCode.dto.PostResponseDTO;
import com.todocodeacademy.BlogCode.model.Author;
import com.todocodeacademy.BlogCode.model.Post;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PostMapper {

    // authorId and publicationDate are resolved by the service
    public Post toEntity(PostRequestDTO requestDTO) {
        return Post.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
                .build();
    }

    public PostResponseDTO toResponse(Post post) {
        Author author = post.getAuthor();
        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .publicationDate(post.getPublicationDate())
                .authorId(author != null ? author.getId() : null)
                .authorFullName(buildFullName(author))
                .build();
    }

    private String buildFullName(Author author) {
        if (author == null) {
            return null;
        }
        String fullName = Stream.of(author.getName(), author.getLastName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
        return fullName.isEmpty() ? null : fullName;
    }
}
