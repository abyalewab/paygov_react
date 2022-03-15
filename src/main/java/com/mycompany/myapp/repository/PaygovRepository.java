package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paygov;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Paygov entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaygovRepository extends JpaRepository<Paygov, Long> {}
