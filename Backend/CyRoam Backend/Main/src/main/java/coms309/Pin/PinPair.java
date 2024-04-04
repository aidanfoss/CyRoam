package coms309.Pin;

import coms309.Pin.Pin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.IOException;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class PinPair {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    int id;
    private static Pin pin;
    private boolean isDiscovered;

    public PinPair(Pin pin, boolean isDiscovered) {
        this.pin = pin;
        this.isDiscovered = isDiscovered;
    }

    public PinPair() {

    }

    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered(boolean discovered) {
        isDiscovered = discovered;
    }
}



