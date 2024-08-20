package com.example.FootballManager_back_end.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private FootballPlayer footballPlayer;
    @ManyToOne
    @JoinColumn(name = "seller_team_id")
    private FootballTeam sellerTeam;
    @ManyToOne
    @JoinColumn(name = "buyer_team_id")
    private FootballTeam buyerTeam;
    @NotNull
    private int price;
}
