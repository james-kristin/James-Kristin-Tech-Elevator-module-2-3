package com.techelevator.service;

import com.techelevator.model.Tax;
import com.techelevator.model.User;
import org.apache.tomcat.util.descriptor.tld.TldRuleSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class RestTaxService implements TaxService{
    public String Url =  "https://teapi.netlify.app/api/statetax?state=";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public BigDecimal getTaxRate(User user) {
        String stateCode = user.getStateCode();
        String newUrl = Url + stateCode;
        Tax taxObject = restTemplate.getForObject(newUrl, Tax.class);
        BigDecimal taxPercent = taxObject.getSalesTax();
        BigDecimal taxRate = taxPercent.divide(new BigDecimal(100));
        return taxRate;
    }



}
