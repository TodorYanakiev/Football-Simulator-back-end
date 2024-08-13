package com.example.FootballManager_back_end.Controller;
import com.example.FootballManager_back_end.DTO.TransferDTO;
import com.example.FootballManager_back_end.Service.TransferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vi/transfers")
public class TransferController {
    private TransferService transferService;
    @PostMapping
    public ResponseEntity<TransferDTO> createTransfer(@Valid @RequestBody TransferDTO transferDTO){
        TransferDTO newTransferDTO = transferService.createTransfer(transferDTO);
        return ResponseEntity.status(201).body(newTransferDTO);
    }
    @GetMapping
    public ResponseEntity<List<TransferDTO>> getAllTransfers(){
        List<TransferDTO> transferDTOList = transferService.getAllTransfers();
        return ResponseEntity.ok(transferDTOList);
    }
}
