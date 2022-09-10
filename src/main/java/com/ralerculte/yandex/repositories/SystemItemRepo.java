package com.ralerculte.yandex.repositories;

import com.ralerculte.yandex.models.SystemItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemItemRepo extends JpaRepository<SystemItem, String> {
}
