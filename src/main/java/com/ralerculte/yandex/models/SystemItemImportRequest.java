package com.ralerculte.yandex.models;

import java.util.List;

public record SystemItemImportRequest(List<SystemItemImport> items, String date) {
}
