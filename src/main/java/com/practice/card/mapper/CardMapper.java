package com.practice.card.mapper;

import com.practice.card.entity.Card;
import com.practice.card.tdo.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CardMapper {
    Card cardPostDtoToCard(CardDto.Post cardPostDto);
    Card cardPatchDtoToCard(CardDto.Patch cardPatchDto);
    CardDto.Response cardToCardResponseDto(Card card);
    List<CardDto.Response> cardsToCardResponseDtos(List<Card> cards);

}
