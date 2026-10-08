package com.todocodeacademy.BlogCode.controller.web;

import com.todocodeacademy.BlogCode.dto.PostResponseDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Orden de publicaciones para las vistas: lo más nuevo arriba.
 */
final class PostsOrdenados {

    private static final Comparator<PostResponseDTO> RECIENTES_PRIMERO =
            Comparator.comparing(PostResponseDTO::publicationDate,
                            Comparator.nullsLast(Comparator.<LocalDate>reverseOrder()))
                    .thenComparing(PostResponseDTO::id,
                            Comparator.nullsLast(Comparator.<Long>reverseOrder()));

    private PostsOrdenados() {
    }

    static List<PostResponseDTO> recientesPrimero(List<PostResponseDTO> posts) {
        return posts.stream()
                .sorted(RECIENTES_PRIMERO)
                .toList();
    }

}
