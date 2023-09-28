package com.lauro.resource.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateTaskDto {
    private String title;
    private String description;
}
