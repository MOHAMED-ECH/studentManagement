package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EnregistrementController {

    @FXML
    private TableColumn<Etudiant, String> CIN_colonne;

    @FXML
    private TableColumn<Etudiant, String> EMAIL_colonne;

    @FXML
    private TableColumn<Etudiant, String> GSM_colonne;

    @FXML
    private TableColumn<Etudiant, String> NOM_colonne;

    @FXML
    private TableColumn<Etudiant, Integer> N_colonne;

    @FXML
    private TableColumn<Etudiant, String> PRENOM_colonne;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_quitter;

    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_chercher;

    @FXML
    private TableView<Etudiant> table;

    @FXML
    private TextField txt_cin;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_gsm;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;
    @FXML
    private TextField txt_chercher;
    private URL url;
    private ResourceBundle rb;


   /* void ajouter(ActionEvent event) throws SQLException {
        String fullName = "fffff";
        String emailId = "jjjjjj";
        String password = "gggggggg";

        JdbcDao jdbcDao = new JdbcDao();
        jdbcDao.insertRecord(fullName, emailId, password);
        ajouter_etudiant();


    }*/

   /* private void ajouter_etudiant() {

    }*/
  /* @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btn_ajouter){
            insert_record ();
        }
    }*/

    @FXML
    public void initialize() {
        show_etudiant();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    ObservableList<Etudiant> list_etudiants = FXCollections.observableArrayList();


    @FXML
    void modifier(ActionEvent event) {
        Etudiant etudiantSelectionne = table.getSelectionModel().getSelectedItem();
        String nom = etudiantSelectionne.getNom();
        etudiantSelectionne.setNom(txt_nom.getText());
        etudiantSelectionne.setPrenom(txt_prenom.getText());
        etudiantSelectionne.setCin(txt_cin.getText());
        etudiantSelectionne.setEmail(txt_email.getText());
        etudiantSelectionne.setGsm(txt_gsm.getText());
        updateRecord(nom);
        viderChamps();
        show_etudiant();
    }

    @FXML
    /*void ajouter(ActionEvent event) {
        //  if (event.getSource()== btn_ajouter){
        boolean ajout = true;
       boolean champsRemplis = true;
        for (Etudiant e : list_etudiants) {
            if (e.getCin().equals(txt_cin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Echec d'enregistrement");
                alert.setContentText("Cet élève existe déja !!");
                alert.showAndWait();
                ajout = false;
            }
            else if (e.getNom().isEmpty() || e.getCin().isEmpty() || e.getEmail().isEmpty() || e.getGsm().isEmpty() || e.getPrenom().isEmpty()) {
                champsRemplis = false;
                break;  // Sortir de la boucle si un champ est vide
            }
        }
        if (!champsRemplis) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Echec d'enregistrement");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            ajout = false;
        }

        if (ajout == true) {
            insert_record();
            viderChamps();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setContentText("Ajouté avec succé");
            alert.showAndWait();
            txt_nom.requestFocus();
        }


    }
*/

    void ajouter(ActionEvent event) {
        boolean ajout = true;
        boolean champsRemplis = true;

        for (Etudiant e : list_etudiants) {
            if (e.getCin().equals(txt_cin.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Echec d'enregistrement");
                alert.setContentText("Cet élève existe déjà !");
                alert.showAndWait();
                ajout = false;
                break;  // Sortir de la boucle si le CIN existe déjà
            }

        }
        if (txt_nom.getText().isEmpty() || txt_cin.getText().isEmpty() || txt_email.getText().isEmpty() || txt_gsm.getText().isEmpty() || txt_prenom.getText().isEmpty()) {
            champsRemplis = false;
        }
        if (!champsRemplis) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Echec d'enregistrement");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            ajout = false;
        }

        if (ajout) {
            insert_record();
            viderChamps();
            show_etudiant(); // Mettre à jour la table avec la liste mise à jour
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setContentText("Ajouté avec succès");
            alert.showAndWait();
            txt_nom.requestFocus();
        }
    }
    @FXML
    void selectionner() {
        Etudiant etudiantSelectionne = table.getSelectionModel().getSelectedItem();
        txt_nom.setText(etudiantSelectionne.getNom());
        txt_prenom.setText(etudiantSelectionne.getPrenom());
        txt_cin.setText(etudiantSelectionne.getCin());
        txt_email.setText(etudiantSelectionne.getEmail());
        txt_gsm.setText(etudiantSelectionne.getGsm());

    }

    @FXML
    void quitter(ActionEvent event) {
        System.exit(0);


    }

    @FXML
    void chercher(ActionEvent event) {
        Etudiant etudiant_trouve = null;
        boolean trouve = false;
        int x = 0;
        if (txt_chercher.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Recherche");
            alert.setContentText("auccun nom n'est saisi");
            alert.showAndWait();
            txt_chercher.requestFocus();
        } else {
            String nom_cherche = txt_chercher.getText();
            for (Etudiant e : list_etudiants) {
                x++;
                if (nom_cherche.equalsIgnoreCase(e.getNom()) || nom_cherche.equalsIgnoreCase(e.getPrenom()) || nom_cherche.equalsIgnoreCase(e.getCin()) || nom_cherche.equalsIgnoreCase(e.getEmail()) || nom_cherche.equalsIgnoreCase(e.getGsm())) {
                    trouve = true;
                    etudiant_trouve = e;
                    break;
                }

            }
        }
        if (trouve) {
            txt_nom.setText(etudiant_trouve.getNom());
            txt_prenom.setText(etudiant_trouve.getPrenom());
            txt_cin.setText(etudiant_trouve.getCin());
            txt_email.setText(etudiant_trouve.getEmail());
            txt_gsm.setText(etudiant_trouve.getGsm());

            table.getSelectionModel().select(x - 1);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Recherche");
            alert.setContentText("cet etudiant n'existe pas sur la liste");
            alert.showAndWait();
            txt_chercher.requestFocus();
        }

    }

    @FXML
    void supprimer(ActionEvent event) {


        Etudiant etudiantSelectionne = table.getSelectionModel().getSelectedItem();


        if (etudiantSelectionne != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Suppression");
            confirmationAlert.setHeaderText("Supprimer l'étudiant");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet étudiant ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                list_etudiants.remove(etudiantSelectionne);
                table.getSelectionModel().clearSelection();
                removeRecord(etudiantSelectionne);
            } else {
                // Aucune ligne sélectionnée, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Suppression");
                alert.setContentText("Aucune ligne sélectionnée");
                alert.showAndWait();
            }


            // Réinitialisez les champs de texte et la sélection de la table si nécessaire
            viderChamps();
            table.getSelectionModel().clearSelection();
        }

    }

    private void viderChamps() {
        txt_nom.setText("");
        txt_prenom.setText("");
        txt_cin.setText("");
        txt_gsm.setText("");
        txt_email.setText("");
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdb", "root", "med01994");
            return conn;
        } catch (Exception ex) {
            System.out.println("erreur:" + ex.getMessage());
            return null;
        }

    }

    public ObservableList<Etudiant> getEtudiant_list() {
        // ObservableList<Etudiant> list_etudiants = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Etudiant";
        Statement st;
        ResultSet rs;


        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Etudiant etudiant;
            while (rs.next()) {
                etudiant = new Etudiant(rs.getString("CIN"), rs.getString("NOM"), rs.getString("PRENOM"), rs.getString("EMAIL"), rs.getString("GSM"));
                list_etudiants.add(etudiant);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list_etudiants;
    }

    public void show_etudiant() {
        table.getItems().clear();
        ObservableList<Etudiant> liste = getEtudiant_list();
        CIN_colonne.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("cin"));
        NOM_colonne.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));
        PRENOM_colonne.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("prenom"));
        EMAIL_colonne.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("email"));
        GSM_colonne.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("gsm"));
        table.setItems(liste);
    }

    private void insert_record() {
        String query = "INSERT INTO ETUDIANT VALUES ('" + txt_cin.getText() + "','" + txt_nom.getText() + "','" + txt_prenom.getText()
                + "','" + txt_email.getText() + "','" + txt_gsm.getText() + "')";
        executeQuery(query);
       show_etudiant();
    }

    private void removeRecord(Etudiant e) {
        String nom = e.getNom();
        String query = "DELETE FROM ETUDIANT WHERE nom = '" + nom + "'";
        executeQuery(query);
    }

    private void updateRecord(String nom) {
        String newNom = txt_nom.getText();
        String newPrenom = txt_prenom.getText();
        String newCin = txt_cin.getText();
        String newEmail = txt_email.getText();
        String newGsm = txt_gsm.getText();

        String query = "UPDATE ETUDIANT SET NOM ='" + newNom + "', prenom='" + newPrenom + "', cin ='" + newCin + "', email='" + newEmail + "', gsm='" + newGsm + "' WHERE nom ='" + nom + "'";
        executeQuery(query);

    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}