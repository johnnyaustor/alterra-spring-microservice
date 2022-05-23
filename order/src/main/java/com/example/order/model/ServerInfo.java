package com.example.order.model;

import lombok.Data;

@Data
public class ServerInfo {
    private String name;
    private Integer port;
    private String contextPath;
}
