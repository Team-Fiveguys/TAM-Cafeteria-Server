package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Congestion;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository.CafeteriaRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeteriaCommandServiceImpl implements CafeteriaCommandService {
    private final CafeteriaRepository cafeteriaRepository;

    @Override
    public Cafeteria enroll(Cafeteria cafeteria) {
        boolean exists = cafeteriaRepository.existsByName(cafeteria.getName());
        if (exists){
            throw new GeneralException(ErrorStatus.CAFETERIA_NAME_DUPLICATE);
        }
        return cafeteriaRepository.save(cafeteria);
    }

    @Override
    public Congestion setCongestion(Cafeteria cafeteria, Congestion congestion) {
        cafeteria.setCongestion(congestion);
        return cafeteria.getCongestion();
    }
}
