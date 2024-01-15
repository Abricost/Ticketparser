package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Root;
import org.example.dto.Ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TicketServiceImpl implements TicketService{

    @Override
    public HashMap<String, Duration> getMinTimeForEachCarrier(List<Ticket> tickets, String originName, String destinationName) throws IOException {

        HashMap<String, Duration> carriersTime = new HashMap<>();

        for (Ticket ticket : tickets) {
            if (ticket.getOrigin_name().equals(originName) && ticket.getDestination_name().equals(destinationName)) {
                String departureDate = ticket.getDeparture_date();
                String departureTime = ticket.getDeparture_time();
                String arrivalDate = ticket.getArrival_date();
                String arrivalTime = ticket.getArrival_time();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");    // 12.05.18 16:20
                LocalDateTime departureDateTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
                LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDate + " " + arrivalTime, formatter);
                Duration duration = Duration.between(departureDateTime, arrivalDateTime);

                if (!carriersTime.containsKey(ticket.getCarrier())) {
                    carriersTime.put(ticket.getCarrier(), duration);
                } else {
                    Duration prevDuration = carriersTime.get(ticket.getCarrier());
                    if (prevDuration.compareTo(duration) > 0) carriersTime.replace(ticket.getCarrier(), duration);
                }
            }
        }
        return carriersTime;
    }

    @Override
    public Double getAverageAndMedianPricesDifference(List<Ticket> tickets, String originName, String destinationName) throws IOException {

        List<Double> prices = new ArrayList<>();

        double priceTotal = 0.0;
        for (Ticket ticket : tickets) {
            if (ticket.getOrigin_name().equals(originName) && ticket.getDestination_name().equals(destinationName)) {
                prices.add(ticket.getPrice());
                priceTotal = priceTotal + ticket.getPrice();
            }
        }

        double priceAVG = (prices.size() == 0) ? 0 : priceTotal / prices.size();
        double priceMedian = findMedian(prices);
        return priceAVG - priceMedian;
    }

    private double findMedian(List<Double> prices) {
        Collections.sort(prices);
        int size = prices.size();
        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }

}
