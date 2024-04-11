package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PizzaServer {
    private static List<org.example.Pizza> pizzaLista = new ArrayList<>();

    public static void main(String[] args) {
        pizzaLista.add(new org.example.Pizza("Pepperoni", Arrays.asList("tomato", "cheese", "pepperoni"), 7.50));
        pizzaLista.add(new org.example.Pizza("Vegetariana", Arrays.asList("tomato", "cheese", "mushrooms", "peppers"), 8.00));
        pizzaLista.add(new org.example.Pizza("Marinara", Arrays.asList("tomato", "garlic", "oregano"), 6.00));
        pizzaLista.add(new org.example.Pizza("Quattro Stagioni", Arrays.asList("tomato", "cheese", "mushrooms", "ham", "artichokes", "olives", "oregano"), 9.00));
        pizzaLista.add(new org.example.Pizza("Carbonara", Arrays.asList("cheese", "eggs", "pancetta"), 8.50));
        pizzaLista.add(new org.example.Pizza("Frutti di Mare", Arrays.asList("tomato", "seafood"), 10.00));
        pizzaLista.add(new org.example.Pizza("Quattro Formaggi", Arrays.asList("cheese", "blue cheese", "fontina", "mozzarella", "parmesan"), 9.50));
        pizzaLista.add(new org.example.Pizza("Crudo", Arrays.asList("tomato", "mozzarella", "prosciutto"), 8.00));
        pizzaLista.add(new org.example.Pizza("Napoletana", Arrays.asList("tomato", "mozzarella", "anchovies", "oregano", "olives"), 7.50));
        pizzaLista.add(new org.example.Pizza("Pugliese", Arrays.asList("tomato", "mozzarella", "onions", "olives"), 7.00));
        pizzaLista.add(new org.example.Pizza("Montanara", Arrays.asList("tomato", "mozzarella", "mushrooms", "pepperoni", "stracchino"), 8.50));
        pizzaLista.add(new org.example.Pizza("Capricciosa", Arrays.asList("tomato", "mozzarella", "mushrooms", "artichokes", "cooked ham", "olives"), 9.00));
        pizzaLista.add(new org.example.Pizza("Prosciutto", Arrays.asList("tomato", "mozzarella", "ham"), 7.50));
        pizzaLista.add(new org.example.Pizza("Prosciutto e Funghi", Arrays.asList("tomato", "mozzarella", "ham", "mushrooms"), 8.00));
        pizzaLista.add(new org.example.Pizza("Salsiccia", Arrays.asList("tomato", "mozzarella", "sausage"), 7.50));
        pizzaLista.add(new org.example.Pizza("Speck e Mascarpone", Arrays.asList("tomato", "mozzarella", "speck", "mascarpone"), 9.00));
        pizzaLista.add(new org.example.Pizza("Bufalina", Arrays.asList("tomato", "buffalo mozzarella", "basil"), 8.50));
        pizzaLista.add(new org.example.Pizza("Diavola", Arrays.asList("tomato", "mozzarella", "spicy salami"), 8.00));
        pizzaLista.add(new org.example.Pizza("Ortolana", Arrays.asList("tomato", "mozzarella", "mixed vegetables"), 7.50));
        pizzaLista.add(new org.example.Pizza("Gorgonzola", Arrays.asList("mozzarella", "gorgonzola", "parmesan"), 9.50));
        pizzaLista.add(new org.example.Pizza("Mare e Monti", Arrays.asList("tomato", "mozzarella", "seafood", "mushrooms"), 10.50));
        pizzaLista.add(new org.example.Pizza("Tartufata", Arrays.asList("mozzarella", "truffle sauce", "mushrooms"), 9.00));
        pizzaLista.add(new org.example.Pizza("Salmone", Arrays.asList("mozzarella", "smoked salmon", "arugula"), 10.00));
        pizzaLista.add(new org.example.Pizza("Tonno e Cipolla", Arrays.asList("tomato", "mozzarella", "tuna", "onion"), 8.50));
        pizzaLista.add(new org.example.Pizza("Valtellina", Arrays.asList("mozzarella", "bresaola", "arugula", "parmesan"), 10.00));
        pizzaLista.add(new org.example.Pizza("Piccante", Arrays.asList("tomato", "mozzarella", "spicy sausage", "jalapenos"), 8.50));
        pizzaLista.add(new org.example.Pizza("Pesto", Arrays.asList("mozzarella", "pesto", "sun-dried tomatoes", "pine nuts"), 9.00));
        pizzaLista.add(new org.example.Pizza("Mascarpone", Arrays.asList("tomato", "mozzarella", "mascarpone"), 7.50));
        pizzaLista.add(new org.example.Pizza("Americana", Arrays.asList("tomato", "mozzarella", "french fries", "sausages"), 8.50));
        pizzaLista.add(new org.example.Pizza("Campagnola", Arrays.asList("tomato", "mozzarella", "sausages", "peppers"), 8.00));

        try {
            ServerSocket serverSocket = new ServerSocket(5555);

            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connesso: " + clientSocket);
                Thread thread = new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println("Comando ricevuto dal client: " + inputLine);
                            String response = processCommand(inputLine.trim());
                            out.println(response);
                        }

                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processCommand(String comando) {
        switch (comando) {
            case "with_tomato":
                return getPizzaConIngredienti("tomato");
            case "with_cheese":
                return getPizzaConIngredienti("cheese");
            case "sorted_by_price":
                return ordinaPizzePrezzo();
            default:
                return "Comando non valido";
        }
    }

    private static String getPizzaConIngredienti(String ingredienti) {
        List<org.example.Pizza> filtroPizze = new ArrayList<>();
        for (org.example.Pizza pizza : pizzaLista) {
            if (pizza.ingredienti.contains(ingredienti)) {
                filtroPizze.add(pizza);
            }
        }
        return formatoListaPizze(filtroPizze);
    }

    private static String ordinaPizzePrezzo() {
        List<org.example.Pizza> ordinaPizze = new ArrayList<>(pizzaLista);
        Comparator<Object> Comparator = null;
        ordinaPizze.sort(java.util.Comparator.comparingDouble(pizza -> pizza.prezzo));
        return formatoListaPizze(ordinaPizze);
    }

    private static String formatoListaPizze(List<Pizza> pizzaLista) {
        StringBuilder sb = new StringBuilder();
        for (Pizza pizza : pizzaLista) {
            String nomePizza = pizza.getNome();
            String ingredienti = pizza.getIngredienti().toString();
            double prezzo = pizza.getPrezzo();
            sb.append(String.format("%-20s Ingredienti: %-65s Prezzo: %5.2f%n", nomePizza + ":", ingredienti, prezzo));
        }
        return sb.toString();
    }
}