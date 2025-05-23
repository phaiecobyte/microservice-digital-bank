package com.phai.customerservice.services;

import com.phai.customerservice.constant.Status;
import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.models.IdentityCard;

public interface IdentityCardService {
    IdentityCard updateStatus(IdentityCard identityCard, Status status) throws AppException;
    void checkForExpiredCards() throws AppException;
}
