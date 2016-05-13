package com.example.newto.korsarze.bluetooth;

import java.util.Observable;
import java.util.Stack;

/**
 * Created by Kamil Szuba on 2016-05-08.
 */
public class ObservableStack extends Observable {


    private final Stack<Integer> normalStack;


    public ObservableStack() {
        this.normalStack = new Stack<>();

    }

    public void push(int value) {
        normalStack.push(value);
        notifyObservers();
    }

    public int getTopByte() {
        if (!normalStack.isEmpty())
            return normalStack.pop();
        else return 0;

    }


}
