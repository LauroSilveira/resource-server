package com.lauro.resource.server.dto;

import lombok.*;

import java.util.List;
public record TasksDto(List<TaskDto> tasks) {
}
