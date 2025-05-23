package com.phai.customerservice.services.impl;

import com.phai.customerservice.constant.Status;
import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.models.IdentityCard;
import com.phai.customerservice.repositories.IdentityCardRepository;
import com.phai.customerservice.services.IdentityCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class IdentityCardServiceImpl implements IdentityCardService {
    private final IdentityCardRepository repository;

    @Transactional
    @Override
    public IdentityCard updateStatus(IdentityCard identityCard, Status status) throws AppException {
        identityCard.setStatus(status.toString());
        return repository.save(identityCard);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Override
    public void checkForExpiredCards() {
        log.info("Starting scheduled check for expired identity cards");
        LocalDate today = LocalDate.now();

        List<IdentityCard> activeIdentityCard = repository.findByStatusAndExpiryDateBefore(Status.ACT.toString(), today);
        if (activeIdentityCard.isEmpty()) {
            log.info("No expired identity card found");
            return;
        }

        log.info("found {} expired identity cards", activeIdentityCard.size());
        for (IdentityCard idcard : activeIdentityCard) {
            idcard.setStatus(Status.IAC.toString());
            repository.save(idcard);
            log.info("Marked identity card {} as inactive due to expiration on {}", idcard.getIdNumber(), idcard.getExpiryDate());
        }
        log.info("Completed processing expired identity cards");
    }
}
