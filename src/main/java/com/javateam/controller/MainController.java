package com.javateam.controller;

import com.javateam.model.Person;
import com.javateam.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Integer> idColumn;
    @FXML private TableColumn<Person, String> firstnameColumn;
    @FXML private TableColumn<Person, String> lastnameColumn;
    @FXML private TableColumn<Person, String> nicknameColumn;
    @FXML private TableColumn<Person, String> phoneColumn;
    @FXML private TableColumn<Person, String> emailColumn;
    @FXML private TableColumn<Person, String> addressColumn;
    @FXML private TableColumn<Person, LocalDate> birthDateColumn;

    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private TextField nicknameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private DatePicker birthDatePicker;

    private final PersonService service = new PersonService();
    private final ObservableList<Person> persons = FXCollections.observableArrayList();
    private static final DateTimeFormatter UI_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idperson"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        birthDateColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : UI_DATE_FORMAT.format(item));
            }
        });

        birthDatePicker.setConverter(new StringConverter<>() {
            @Override public String toString(LocalDate date) {
                return date == null ? "" : UI_DATE_FORMAT.format(date);
            }
            @Override public LocalDate fromString(String s) {
                if (s == null || s.isBlank()) return null;
                return LocalDate.parse(s.trim(), UI_DATE_FORMAT);
            }
        });
        birthDatePicker.setPromptText("dd/MM/yyyy");

        installTextFormatters(); 

        refreshTable();

        personTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, p) -> {
            if (p != null) {
                firstnameField.setText(p.getFirstname());
                lastnameField.setText(p.getLastname());
                nicknameField.setText(p.getNickname());
                phoneField.setText(p.getPhoneNumber());
                emailField.setText(p.getEmailAddress());
                addressField.setText(p.getAddress());
                birthDatePicker.setValue(p.getBirthDate());
            }
        });

        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        idColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.05);
        firstnameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.12);
        lastnameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.12);
        nicknameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.12);
        phoneColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.12);
        emailColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.18);
        addressColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.17);
        birthDateColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.12);
    }

    private void installTextFormatters() {


        phoneField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d{0,10}") ? change : null;
        }));

      
        firstnameField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("[\\p{L} '\\-]{0,45}") ? change : null;
        }));

        lastnameField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("[\\p{L} '\\-]{0,45}") ? change : null;
        }));


        emailField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.contains(" ") ? null : change;
        }));
    }


    @FXML
    private void handleAdd() {
        try {
            Person p = buildPersonFromForm();
            service.addPerson(p);
            refreshTable();
            clearForm();
            showInfo("Succès", "Contact ajouté.");
        } catch (Exception ex) {
            showError("Erreur", ex.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Person selected = personTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Erreur", "Veuillez sélectionner un contact à modifier.");
            return;
        }

        try {
            Person p = buildPersonFromForm();
            p.setIdperson(selected.getIdperson());
            service.updatePerson(p);
            refreshTable();
            clearForm();
            showInfo("Succès", "Contact modifié.");
        } catch (Exception ex) {
            showError("Erreur", ex.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Person selected = personTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Erreur", "Veuillez sélectionner un contact à supprimer.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer ce contact ?");
        confirm.setContentText("Cette action est irréversible.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                service.deletePerson(selected.getIdperson());
                refreshTable();
                clearForm();
                showInfo("Succès", "Contact supprimé.");
            } catch (Exception ex) {
                showError("Erreur", ex.getMessage());
            }
        }
    }

    

    private Person buildPersonFromForm() {
        
        return new Person(
                firstnameField.getText(),
                lastnameField.getText(),
                nicknameField.getText(),
                phoneField.getText(),
                emailField.getText(),
                addressField.getText(),
                birthDatePicker.getValue()
        );
    }

    private void refreshTable() {
        persons.setAll(service.getAllPersons());
        personTable.setItems(persons);
    }

    private void clearForm() {
        firstnameField.clear();
        lastnameField.clear();
        nicknameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        birthDatePicker.setValue(null);
        personTable.getSelectionModel().clearSelection();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}