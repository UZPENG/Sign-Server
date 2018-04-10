package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/10.
 */
public class Probability {
    private Double x;
    private Double y;

    public Probability(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
