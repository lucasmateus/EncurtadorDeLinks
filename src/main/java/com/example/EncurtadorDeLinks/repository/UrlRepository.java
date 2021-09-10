package com.example.EncurtadorDeLinks.repository;

import com.example.EncurtadorDeLinks.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

}
