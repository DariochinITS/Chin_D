package org.example;

import java.util.List;

class Pizza {
    String nome;
    List<String> ingredienti;
    double prezzo;

    public Pizza(String nome, List<String> ingredienti, double prezzo) {
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return nome + ": " + "Ingredienti: " + ingredienti;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getIngredienti() {
        return ingredienti;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
