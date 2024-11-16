package com.swlo.resources;

public abstract class SharedResource {
    public abstract void read(int id);
    public abstract void write(int id);
}