package com.newestworld.scheduler;

import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final ActionTimeoutRepository actionTimeoutRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init()  {
        actionTimeoutRepository.save(new ActionTimeoutEntity(1, System.currentTimeMillis()));
        actionTimeoutRepository.save(new ActionTimeoutEntity(2, System.currentTimeMillis()+1*1000));
        actionTimeoutRepository.save(new ActionTimeoutEntity(3, System.currentTimeMillis()+5*1000));
        actionTimeoutRepository.save(new ActionTimeoutEntity(4, System.currentTimeMillis()+10*1000));

    }

}
