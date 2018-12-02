package com.chrispassold.weatherapp.storage.pojo;

import java.io.Serializable;

public class Sys implements Serializable {

    private Integer population;

    /**
     *
     * @return
     * The population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     *
     * @param population
     * The population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

}
