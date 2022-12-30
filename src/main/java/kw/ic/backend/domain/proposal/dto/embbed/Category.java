package kw.ic.backend.domain.proposal.dto.embbed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Category {
    @JsonProperty("menuName")
    MENU_NAME,
    @JsonProperty("price")
    PRICE,
    @JsonProperty("openTime")
    OPEN_TIME,
    @JsonProperty("closeTime")
    CLOSE_TIME;

    @JsonCreator
    public static Category from(String val) {
        for (Category category : Category.values()) {
            if (category.name().equals(val)) {
                return category;
            }
        }
        return null;
    }
}
