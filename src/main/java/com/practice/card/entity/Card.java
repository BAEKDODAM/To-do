package com.practice.card.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practice.member.entity.Member;
import com.practice.todo.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String cardName;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "card")
    private List<Todo> todos;
}
