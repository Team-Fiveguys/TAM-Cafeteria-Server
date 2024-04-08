package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;

import java.util.List;

public interface CafeteriaQueryService {

    public Cafeteria findById(Long id);

    public List findAll();
}
