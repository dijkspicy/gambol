package com.dijkspicy.gambol;

import com.dijkspicy.gambol.model.Request;

import java.util.LinkedList;
import java.util.Queue;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public enum RequestQueue {
    ins;

    private final Queue<Request> requestQueue = new LinkedList<>();

    public Queue<Request> getRequestQueue() {
        return requestQueue;
    }
}
