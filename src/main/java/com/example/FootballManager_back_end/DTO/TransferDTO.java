package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Entity.FootballPlayer;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private FootballPlayer footballPlayer;
    private FootballTeam sellerTeam;
    private FootballTeam buyerTeam;
    private int price;
}
