package com.ism.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.ColumnDefault;
import com.ism.data.enums.TypeCommande;
@Data()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="commande")
@ToString(exclude = "client")
public class Commande extends AbstractEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    public Commande() {
        date=LocalDate.now();
    }

    private double montant;

    @Column(name = "montant_verse")
    private double montantVerse;

    @Column(name = "montant_restant")
    private double montantRestant;
    
    @Enumerated(EnumType.STRING)
    private TypeCommande typeCommande;

    @ColumnDefault(value = "true")
    private boolean archiver;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Details> listeDetails = new ArrayList<>();


    public void setListeDetails(Details details) {
        details.setCommande(this); 
        listeDetails.add(details);    
    }

     
    public void setListeDetails(List<Details> details) {
        this.listeDetails.clear();  // On efface d'abord l'ancienne liste
        if (details != null) {
            for (Details detail : details) {
                detail.setCommande(this);  // Assurez-vous que chaque détail est bien lié à cette dette
                this.listeDetails.add(detail);
            }
        }
    }
}
