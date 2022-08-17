package com.newestworld.scheduler.messaging;

//@Slf4j
////@Component
//@RequiredArgsConstructor
//public class ActionTimeoutProducer implements Supplier<Flux<ActionTimeoutEvent>> {
//
//    private final ActionTimeoutService service;
//
//    @Override
//    public Flux<ActionTimeoutEvent> get() {
//        return Flux.interval(Duration.ofMinutes(1))
//                .map(x -> {
//                    var list = service.findAll(System.currentTimeMillis());
//                    return new ActionTimeoutEvent(list.stream().map(IdReference::getId).collect(Collectors.toList()));
//                }).log();
//    }
//}
