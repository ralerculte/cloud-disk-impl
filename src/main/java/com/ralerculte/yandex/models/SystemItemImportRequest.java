package com.ralerculte.yandex.models;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

public class SystemItemImportRequest {
    private List<SystemItemImport> items;
    String updateDate;

    public SystemItemImportRequest(List<SystemItemImport> items, String updateDate) {
        this.items = items;
        this.updateDate = updateDate;
    }

    public List<SystemItemImport> getItems() {
        return items;
    }

    public void setItems(List<SystemItemImport> items) {
        this.items = items;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<SystemItemImport> getSortedList() {
        items.sort(Comparator.comparing(SystemItemImport::getId));
        return items;
    }
}
