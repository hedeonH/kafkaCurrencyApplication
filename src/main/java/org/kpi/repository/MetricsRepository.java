package org.kpi.repository;

import org.kpi.model.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<SensorEntity, Integer> {
}
