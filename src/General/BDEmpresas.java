/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package General;

import Modelos.Empresa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Actúa como un repositorio en memoria para almacenar y gestionar la lista de 
 * empresas dentro de la aplicación.
 * @author Alejandro
 */
public class BDEmpresas {
    public static ObservableList<Empresa> listaEmpresas = FXCollections.observableArrayList();
}
