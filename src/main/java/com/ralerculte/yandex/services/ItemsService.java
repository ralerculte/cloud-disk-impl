package com.ralerculte.yandex.services;

import com.ralerculte.yandex.exceptions.NotFoundException;
import com.ralerculte.yandex.exceptions.ValidationException;
import com.ralerculte.yandex.models.SystemItem;
import com.ralerculte.yandex.models.SystemItemImport;
import com.ralerculte.yandex.models.SystemItemImportRequest;
import com.ralerculte.yandex.repositories.SystemItemRepo;
import com.ralerculte.yandex.utils.RequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;

@Service
@Transactional
public class ItemsService {

    private final SystemItemRepo repo;
    private final RequestValidator validator;

    public ItemsService(SystemItemRepo repo, RequestValidator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    public SystemItem findById(String id) {
        if (validator.validateId(id)) {
            throw new ValidationException();
        }
        return repo.findById(id).orElseThrow(NotFoundException::new);
    }

    public void save(SystemItemImportRequest request, BindingResult bindingResult) {
        validator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        ZonedDateTime dateTime = ZonedDateTime.parse(request.getUpdateDate());
        for (SystemItemImport itemImport : request.getItems()) {
            SystemItem parent = itemImport.getParentId() == null
                    ? null : repo.findById(itemImport.getParentId()).orElse(null);
            SystemItem item = new SystemItem(
                    itemImport.getId(),
                    itemImport.getUrl(),
                    dateTime,
                    parent,
                    itemImport.getType(),
                    itemImport.getSize()
            );

            if (parent != null) {
                parent.addChildren(item);
            }
            repo.save(item);
        }
    }

    public void delete(String id) {
        if (validator.validateId(id)) {
            throw new ValidationException();
        }
        SystemItem itemToDelete = repo.findById(id).orElseThrow(NotFoundException::new);
        repo.delete(itemToDelete);
    }
}
