package com.example.news.mapper;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsMapper {

    @Mapping(target = "newsId", ignore = true)
    News newsPostDtoToNews(NewsDto.Post post);
}
