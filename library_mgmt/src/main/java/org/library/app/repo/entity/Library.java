package org.library.app.repo.entity;

import java.util.List;

public class Library {
    private List<Rack> racks;
    private Integer availableRacks;

    public Library() {
    }

    public Integer getAvailableRacks() {
        return availableRacks;
    }

    public void setAvailableRacks(Integer availableRacks) {
        this.availableRacks = availableRacks;
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }
}
