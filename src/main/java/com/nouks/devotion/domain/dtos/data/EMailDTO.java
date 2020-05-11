package com.nouks.devotion.domain.dtos.data;

import lombok.Data;

import java.util.List;

@Data
public class EMailDTO {
    List<EmailAddress> emailAddresses;
    String message;
}
