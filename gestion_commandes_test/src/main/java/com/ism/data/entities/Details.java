package com.ism.data.entities;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="detail")

public class Details extends AbstractEntity implements Identifiable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double qteCommande;

    @ManyToOne
    @JoinColumn(name = "commande_id", referencedColumnName = "id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;

    @Override
    public String toString() {
        return "Details{" +
                "id=" + id + 
                ", qteCommande=" + qteCommande +
                ", detteId=" + (commande != null ? commande.getId() : "null") + 
                ", produit=" + (produit != null ? produit.getLibelle() : "null") + 
                '}';
    }
}
