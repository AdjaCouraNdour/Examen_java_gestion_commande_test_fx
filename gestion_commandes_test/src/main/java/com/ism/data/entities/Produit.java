package com.ism.data.entities;

import java.util.ArrayList;
import java.util.List;

import com.ism.data.enums.EtatProduit;

import lombok.Data;
import lombok.EqualsAndHashCode;
// import lombok.ToString;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false, of={"reference","libelle"} )
@Entity
@Table(name="Produit")
// @ToString(exclude = "listeDetails")

public class Produit extends AbstractEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PrePersist
    public void generateReference() {
        if (this.id == 0) {
            return;
        }
        this.reference = String.format("A%06d", this.id);
    } 

    @Column(length = 10,unique = true)
    private String reference;
    
    @Column(length = 10,unique = true)
    private String libelle;

    private int prix;
    private double qteStock;

    @Enumerated(EnumType.STRING)
    private EtatProduit etatProduit;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Details> listeDetails = new ArrayList<>();

    
    public void setListeDetails(Details details) {
        this.qteStock=this.qteStock-details.getQteCommande();
        if(this.qteStock==0){
            this.etatProduit=EtatProduit.Indisponible;
        }else{
            this.etatProduit=EtatProduit.Disponible;  
        }
        listeDetails.add(details);

    }
    public void setQteStock(double qteStock) {
        this.qteStock = qteStock;
        if (this.qteStock == 0) {
            this.etatProduit = EtatProduit.Indisponible;
        } else {
            this.etatProduit = EtatProduit.Disponible;
        }    
    }

    
    public void removeDetails(Details details) {
        this.qteStock += details.getQteCommande();
        if (this.qteStock > 0) {
            this.etatProduit = EtatProduit.Disponible;
        } else {
            this.etatProduit = EtatProduit.Indisponible;
        }
        listeDetails.remove(details);
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id + 
                ", reference='" + reference + 
                ", libelle='" + libelle + 
                ", prix=" + prix +
                ", qteStock=" + qteStock +
                ", etatProduit=" + etatProduit +
                '}';
    }



}
