package org.example;

import org.example.dto.Ticket;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public interface TicketService {

    HashMap<String, Duration> getMinTimeForEachCarrier(List<Ticket> tickets, String originName, String destinationName) throws IOException;

    Double getAverageAndMedianPricesDifference(List<Ticket> tickets, String originName, String destinationName) throws IOException;

}
