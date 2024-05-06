package utils;


import models.Produit.Produit;

import java.sql.SQLException;

public interface MyListener {
    public void onClickListener(Produit produit) throws SQLException;
}
