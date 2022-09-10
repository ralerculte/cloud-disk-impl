package com.ralerculte.yandex.controllers;

import com.ralerculte.yandex.exceptions.NotFoundException;
import com.ralerculte.yandex.exceptions.ValidationException;
import com.ralerculte.yandex.models.ErrorResponse;
import com.ralerculte.yandex.models.SystemItem;
import com.ralerculte.yandex.models.SystemItemImportRequest;
import com.ralerculte.yandex.services.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class HandlerController {

    private final ItemsService service;

    public HandlerController(ItemsService service) {
        this.service = service;
    }

    @GetMapping("/nodes/{id}")
    public SystemItem get(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping("/imports")
    public void saveOrUpdate(@RequestBody SystemItemImportRequest request, BindingResult bindingResult) {
        service.save(request, bindingResult);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleValidateException(ValidationException e) {
        ErrorResponse response = new ErrorResponse(400, "Validation Failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(404, "Item not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
