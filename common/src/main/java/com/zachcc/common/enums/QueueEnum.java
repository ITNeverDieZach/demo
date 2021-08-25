package com.zachcc.common.enums;


import lombok.Getter;

@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("movie.order.direct", "movie.order.cancel", "movie.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("movie.order.direct", "movie.order.cancel.ttl", "movie.order.cancel.ttl"),

    QUEUE_UPLOAD_CANCEL("movie.upload.direct", "movie.upload.cancel", "movie.upload.cancel"),

    QUEUE_TTL_UPLOAD_CANCEL("movie.upload.direct", "movie.upload.cancel.ttl", "movie.upload.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
