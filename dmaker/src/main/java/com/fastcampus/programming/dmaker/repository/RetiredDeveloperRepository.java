package com.fastcampus.programming.dmaker.repository;

import com.fastcampus.programming.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
