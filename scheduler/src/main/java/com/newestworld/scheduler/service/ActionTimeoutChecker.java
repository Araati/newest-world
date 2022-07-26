package com.newestworld.scheduler.service;

import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionTimeoutChecker {

    private final ActionTimeoutRepository actionTimeoutRepository;

    @Scheduled(fixedDelay = 1000)
    private void timeoutChecker()   {
        List<ActionTimeoutEntity> actionTimeoutEntityList = actionTimeoutRepository.findAllByTimeoutLessThan(System.currentTimeMillis());

        for(int i = 0; actionTimeoutEntityList.size() > i; i++)
            System.out.println(actionTimeoutEntityList.get(i).getActionId());
    }

}
