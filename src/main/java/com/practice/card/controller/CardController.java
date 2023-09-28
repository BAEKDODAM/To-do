package com.practice.card.controller;

import com.practice.auth.jwt.JwtTokenizer;
import com.practice.card.entity.Card;
import com.practice.card.mapper.CardMapper;
import com.practice.card.service.CardService;
import com.practice.card.tdo.CardDto;
import com.practice.response.MultiResponseDto;
import com.practice.response.SingleResponseDto;
import com.practice.todo.entity.Todo;
import com.practice.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card")
@Validated
@Slf4j
public class CardController {
    private final static String CARD_DEFAULT_URL = "/cards";
    private final CardService cardService;
    private final CardMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public CardController(CardService cardService, CardMapper mapper, JwtTokenizer jwtTokenizer) {
        this.cardService = cardService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping
    public ResponseEntity postCard(@RequestHeader("Authorization") String token,
                                   @Valid @RequestBody CardDto.Post requestBody){
        long memberId = jwtTokenizer.getMemberId(token);
        Card createdCard = cardService.createCard(mapper.cardPostDtoToCard(requestBody), memberId);
        URI location = UriCreator.createUri(CARD_DEFAULT_URL, createdCard.getCardId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity patchCard(@RequestHeader("Authorization") String token,
                                    @PathVariable("cardId") @Positive long cardId,
                                    @Valid @RequestBody CardDto.Post requestBody) {
        long memberId = jwtTokenizer.getMemberId(token);
        Card updateCard = cardService.updateCard(mapper.cardPostDtoToCard(requestBody), memberId, cardId);
        return ResponseEntity.ok(mapper.cardToCardResponseDto(updateCard));
    }

    @GetMapping
    public ResponseEntity getCards(@RequestHeader("Authorization") String token){
        long memberId = jwtTokenizer.getMemberId(token);
        List<Card> cards = cardService.findCardsByMemberId(memberId);
        List<Card> cardsWithTodos = cards.stream()
                .map(card -> {
                    Card cardWithTodos = cardService.findCardWithTodos(card.getCardId());
                    return cardWithTodos;
                }).collect(Collectors.toList());
        List<CardDto.Response> cardResponses = mapper.cardsToCardResponseDtos(cardsWithTodos);
        return  new ResponseEntity<>(
                new SingleResponseDto<>(cardResponses), HttpStatus.OK
        );
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity deleteCard(@RequestHeader("Authorization") String token,
                                     @PathVariable("cardId") @Positive long cardId) {
        long memberId = jwtTokenizer.getMemberId(token);
        cardService.deleteCard(memberId, cardId);
        return ResponseEntity.noContent().build();
    }
}
