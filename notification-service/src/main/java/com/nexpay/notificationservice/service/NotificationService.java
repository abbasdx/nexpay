package com.nexpay.notificationservice.service;

import com.nexpay.notificationservice.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(Notification notification);
    List<Notification> getNotificationsByUserId(Long userId);
}
