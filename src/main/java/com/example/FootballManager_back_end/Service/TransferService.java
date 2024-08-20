package com.example.FootballManager_back_end.Service;
import com.example.FootballManager_back_end.DTO.TransferDTO;
import com.example.FootballManager_back_end.Entity.Transfer;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {
    private TransferRepository transferRepository;
    private ModelMapper modelMapper;

    public TransferDTO transferToTransferDTO(Transfer transfer){
        return modelMapper.map(transfer, TransferDTO.class);
    }
    public Transfer transferDTOToTransfer(TransferDTO transferDTO){
        return modelMapper.map(transferDTO, Transfer.class);
    }
    public TransferDTO createTransfer(TransferDTO transferDTO){
        checkValidations(transferDTO);
        Transfer transfer = transferDTOToTransfer(transferDTO);
        transferRepository.save(transfer);
        return transferToTransferDTO(transfer);
    }
    public List<TransferDTO> getAllTransfers(){
        List<Transfer> transferList = transferRepository.findAll();
        return transferList.stream().map(this::transferToTransferDTO).toList();
    }
    private void checkValidations(TransferDTO newTransferDTO){
        if (newTransferDTO.getFootballPlayer() == null){
            throw new ApiRequestException("Football player can not be null!");
        }
        else if (newTransferDTO.getBuyerTeam() == null){
            throw new ApiRequestException("Buyer team can not be null!");
        }
        else if (newTransferDTO.getSellerTeam() == null){
            throw new ApiRequestException("Seller team can not be null!");
        }
        else if (newTransferDTO.getPrice() <= 0){
            throw new ApiRequestException("Price must be more than 0.");
        }
    }
}
