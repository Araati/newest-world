package com.newestworld.scheduler.service;

import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionTimeoutEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionTimeoutChecker {

    private final ActionTimeoutRepository actionTimeoutRepository;

    private final EventPublisher<ActionTimeoutEventDTO> actionTimeoutPublisher;

    @Scheduled(fixedDelay = 1000)
    private void timeoutChecker()   {
        List<ActionTimeoutEntity> actionTimeoutEntityList = actionTimeoutRepository.findAllByTimeoutLessThan(System.currentTimeMillis());

        if(!actionTimeoutEntityList.isEmpty()) {
            List<Long> actionIdList = new ArrayList<>();
            for (int i = 0; actionTimeoutEntityList.size() > i; i++) {
                actionIdList.add(actionTimeoutEntityList.get(i).getActionId());
            }

            if (!actionIdList.isEmpty()) {
                actionTimeoutPublisher.send(new ActionTimeoutEventDTO(actionIdList));
                actionTimeoutRepository.deleteAll(actionTimeoutEntityList);
            }
        }

    }

}
