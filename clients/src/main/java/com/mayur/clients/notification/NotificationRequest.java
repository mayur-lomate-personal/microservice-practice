package com.mayur.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class NotificationRequest {
    private int toCustomerId;
    private String toCustomerName;
    private String message;
}
