package com.ism.data.enums;

public enum EtatProduit {
    Disponible,Indisponible;

    public static EtatProduit getEtatArticle(String value) {
        for (EtatProduit etatArticle : EtatProduit.values()) {
            if (etatArticle.name().compareToIgnoreCase(value) == 0) {
                return etatArticle;
            }
        }
        return null;
    }

    public static EtatProduit getEtatArticleId (int id) {
        for (EtatProduit etatArticle : EtatProduit.values()) {
            if (etatArticle.ordinal() == (id - 1)) {
                return etatArticle;
            }
        }
        return null;
    }

    public static int getEtatArticleIdAsInt(EtatProduit etat) {
        if (etat != null) {
            return etat.ordinal() + 1;
        } else {
            throw new IllegalArgumentException("etatArticle cannot be null");
        }
    }
}
