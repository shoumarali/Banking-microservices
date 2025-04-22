package com.ashoumar.card_service.controller;

import com.ashoumar.card_service.constants.CardsConstants;
import com.ashoumar.card_service.dto.CardsDto;
import com.ashoumar.card_service.dto.ResponseDto;
import com.ashoumar.card_service.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class CardController {

    private ICardsService iCardsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber) {
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber
    ){
        boolean isCardDeleted = iCardsService.deleteCard(mobileNumber);

        if(isCardDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }
}
