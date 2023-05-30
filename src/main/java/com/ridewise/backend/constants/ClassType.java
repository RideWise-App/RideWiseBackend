package com.ridewise.backend.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ClassType {
    @JsonProperty("Economy")
    ECONOMY,
    @JsonProperty("Standard")
    STANDARD,
    @JsonProperty("Premium")
    PREMIUM
}
