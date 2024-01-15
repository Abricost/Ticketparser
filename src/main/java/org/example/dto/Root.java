package org.example.dto;

import java.util.ArrayList;

public class Root {
    private ArrayList<Ticket> tickets;

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Root{" +
                "tickets=" + tickets +
                '}';
    }
}
