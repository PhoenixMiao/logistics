package com.phoenix.logistics;

/**
 * @author Jyannis
 * @version 1.0 update on 2021/6/30
 */
public class Test extends Car {

    public Test() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public static void main(String[] args) {
        double x = 0;
        System.out.println(g(x));
    }

    static double g(double x){
        return x*x;
    }
}
