package com.ism.data.enums;

public enum TypeCommande {
   Solde,nonSolde;

   public static TypeCommande getTypeDette(String value) {
         for (TypeCommande typeDette : TypeCommande.values()) {
            if (typeDette.name().compareToIgnoreCase(value) == 0) {
               return typeDette;
            }
         }
         return null;
   }

   public static TypeCommande getTypeDetteId (int id) {
         for (TypeCommande typeDette : TypeCommande.values()) {
            if (typeDette.ordinal() == (id - 1)) {
               return typeDette;
            }
         }
         return null;
   }

   public static int getTypeDetteIdAsInt(TypeCommande value) {
         if (value != null) {
            return value.ordinal() + 1; // Ou une autre logique pour obtenir l'ID
         } else {
            throw new IllegalArgumentException("TypeDette cannot be null");
         }
   }
}
