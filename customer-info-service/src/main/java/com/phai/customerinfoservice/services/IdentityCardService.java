package com.phai.customerinfoservice.services;

import com.phai.customerinfoservice.constant.Status;
import com.phai.customerinfoservice.exception.AppException;
import com.phai.customerinfoservice.models.IdentityCard;

public interface IdentityCardService {
    IdentityCard updateStatus(IdentityCard identityCard, Status status) throws AppException;
    void checkForExpiredCards() throws AppException;
}
