package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Root;
import org.example.dto.Ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String jsonFile = "tickets.json";
        String originName = "Владивосток";
        String destinationName = "Тель-Авив";

        TicketService ticketService = new TicketServiceImpl();

        byte[] jsonByte = Files.readAllBytes(Paths.get(jsonFile));
        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(jsonByte, Root.class);
        List<Ticket> tickets = root.getTickets();

        System.out.println("1. Минимальное время полета между городами "+ originName + " и " + destinationName + " для каждого авиаперевозчика:");
        ticketService.getMinTimeForEachCarrier(tickets, originName, destinationName)
                .forEach((k, v) -> System.out.println("\t" + k + ": " + v.toHours() + "ч " + v.toMinutes() % 60 + "мин"));

        System.out.println("\n2. Разница между средней ценой и медианой для полета между городами " + originName + " и " + destinationName + ":");
        System.out.println("\t" + ticketService.getAverageAndMedianPricesDifference(tickets, originName, destinationName));

    }
}