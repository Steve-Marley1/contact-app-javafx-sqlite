package com.javateam.controller;

import com.javateam.dao.PersonDAO;
import com.javateam.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Integer> colId;
    @FXML private TableColumn<Person, String> colFirstname;
    @FXML private TableColumn<Person, String> colLastname;
    @FXML private TableColumn<Person, String> colNickname;
    @FXML private TableColumn<Person, String> colPhone;

    @FXML private TextField txtFirstname;
    @FXML private TextField txtLastname;
    @FXML private TextField txtNickname;
    @FXML private TextField txtPhone;

    private final PersonDAO dao = new PersonDAO();
    private final ObservableList<Person> personList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getIdperson()).asObject());

        colFirstname.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getFirstname()));

        colLastname.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getLastname()));

        colNickname.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getNickname()));

        colPhone.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPhoneNumber()));

        refreshTable();
    }

    private void refreshTable() {
        personList.setAll(dao.getAllPersons());
        personTable.setItems(personList);
    }

    @FXML
    private void handleAdd() {

        Person p = new Person(
                txtLastname.getText(),
                txtFirstname.getText(),
                txtNickname.getText(),
                txtPhone.getText(),
                "",
                "",
                ""
        );

        dao.addPerson(p);
        refreshTable();
        clearFields();
    }

    @FXML
    private void handleDelete() {
        Person selected = personTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dao.deletePerson(selected.getIdperson());
            refreshTable();
        }
    }

    @FXML
    private void handleUpdate() {

        Person selected = personTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            selected.setFirstname(txtFirstname.getText());
            selected.setLastname(txtLastname.getText());
            selected.setNickname(txtNickname.getText());
            selected.setPhoneNumber(txtPhone.getText());

            dao.updatePerson(selected);
            refreshTable();
        }
    }

    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtNickname.clear();
        txtPhone.clear();
    }
}