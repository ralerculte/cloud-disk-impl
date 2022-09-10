package com.ralerculte.yandex.utils;

import com.ralerculte.yandex.models.SystemItem;
import com.ralerculte.yandex.models.SystemItemImport;
import com.ralerculte.yandex.models.SystemItemImportRequest;
import com.ralerculte.yandex.models.SystemItemType;
import com.ralerculte.yandex.repositories.SystemItemRepo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
public class RequestValidator implements Validator {
    private final SystemItemRepo repo;

    public RequestValidator(SystemItemRepo repo) {
        this.repo = repo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SystemItemImportRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SystemItemImportRequest request = (SystemItemImportRequest) target;

        if (!validateDate(request.date())) {
            errors.rejectValue("date","400", "Validation Failed");
            return;
        }

        for (SystemItemImport item : request.items()) {
            SystemItem parent = repo.findById(item.getParentId()).orElse(null);
            if (!validateItem(item, parent)) {
                errors.rejectValue("items","400", "Validation Failed");
                return;
            }
        }
    }

    public boolean validateId(String id) {
        return !checkId(id);
    }

    private boolean checkId(String id) {
        return id != null && id.matches("элемент_\\d_\\d");
    }

    private boolean validateItem(SystemItemImport item, SystemItem parent) {
        SystemItem s = repo.findById(item.getId()).orElse(null);
        if (s != null && item.getType() != s.getType()) {
            return false;
        }

        if (parent != null && parent.getType().equals(SystemItemType.FILE)) {
            return false;
        }

        if (item.getUrl() != null && item.getUrl().length() > 255) {
            return false;
        }

        if (item.getType() == null) {
            return false;
        }

        if (item.getType().equals(SystemItemType.FILE) && item.getSize() <= 0) {
            return false;
        }

        if (item.getType().equals(SystemItemType.FOLDER)
                && (item.getSize() != null || item.getUrl() != null)) {
            return false;
        }
        return checkId(item.getId());
    }

    private boolean validateDate(String date) {
        if (date == null) {
            return false;
        }

        try {
            LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
