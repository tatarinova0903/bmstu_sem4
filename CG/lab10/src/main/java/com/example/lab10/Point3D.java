package com.example.lab10;

public class Point3D {
    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D() {
        this.x = Double.POSITIVE_INFINITY;
        this.y = Double.POSITIVE_INFINITY;
        this.z = Double.POSITIVE_INFINITY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    boolean exists() {
        return !(x == Double.POSITIVE_INFINITY || y == Double.POSITIVE_INFINITY || z == Double.POSITIVE_INFINITY);
    }
}
