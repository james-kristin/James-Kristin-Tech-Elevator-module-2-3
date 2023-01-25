package com.techelevator.service;

import com.techelevator.model.Tax;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.security.Principal;

public interface TaxService {

        BigDecimal getTaxRate(User user);
}
