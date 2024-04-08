package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository.CafeteriaRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CafeteriaQueryServiceImpl implements CafeteriaQueryService{
    private final CafeteriaRepository cafeteriaRepository;
    @Override
    public Cafeteria findById(Long id) {
        Cafeteria findedCafeteria = cafeteriaRepository.
                findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.CAFETERIA_NOT_FOUND));
        return findedCafeteria;
    }

    @Override
    public List<Cafeteria> findAll() {
        return cafeteriaRepository.findAll();
    }
}
