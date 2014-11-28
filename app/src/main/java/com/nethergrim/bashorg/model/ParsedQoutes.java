package com.nethergrim.bashorg.model;

import java.util.List;

/**
 * Created by nethergrim on 28.11.2014.
 */
public class ParsedQoutes {

    private List<Long> ids;
    private List<String> texts;
    private List<String> dates;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
