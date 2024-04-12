package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository.CafeteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeteriaCommandServiceImpl implements CafeteriaCommandService {
    private final CafeteriaRepository cafeteriaRepository;

    @Override
    public Cafeteria enroll(Cafeteria cafeteria) {
        return cafeteriaRepository.save(cafeteria);
    }
}
