package com.ridewise.backend.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClassType {
    @JsonProperty("Economy")
    ECONOMY,
    @JsonProperty("Standard")
    STANDARD,
    @JsonProperty("Premium")
    PREMIUM
}
