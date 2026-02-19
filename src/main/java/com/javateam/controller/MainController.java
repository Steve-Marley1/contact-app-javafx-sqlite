package com.javateam.controller;

import com.javateam.model.Person;
import com.javateam.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final PersonService personService = new PersonService();
    private final ObservableList<Person> personList = FXCollections.observableArrayList();

    // ===== TABLE =====
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Integer> idColumn;
    @FXML private TableColumn<Person, String> firstNameColumn;
    @FXML private TableColumn<Person, String> lastNameColumn;
    @FXML private TableColumn<Person, String> nicknameColumn;
    @FXML private TableColumn<Person, String> phoneColumn;
    @FXML private TableColumn<Person, String> emailColumn;
    @FXML private TableColumn<Person, String> addressColumn;
    @FXML private TableColumn<Person, LocalDate> birthDateColumn;

    // ===== FORM FIELDS =====
    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private TextField nicknameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private DatePicker birthDatePicker;

    // ===== BUTTONS =====
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Column Mapping
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idperson"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        loadPersons();

        // Selection Listener
        personTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                firstnameField.setText(newSel.getFirstname());
                lastnameField.setText(newSel.getLastname());
                nicknameField.setText(newSel.getNickname());
                phoneField.setText(newSel.getPhoneNumber());
                emailField.setText(newSel.getEmailAddress());
                addressField.setText(newSel.getAddress());
                birthDatePicker.setValue(newSel.getBirthDate());
            }
        });

        addButton.setOnAction(e -> handleAdd());
        updateButton.setOnAction(e -> handleUpdate());
        deleteButton.setOnAction(e -> handleDelete());
    }

    // ===== LOAD DATA =====
    private void loadPersons() {
        List<Person> persons = personService.getAllPersons();
        personList.setAll(persons);
        personTable.setItems(personList);
    }

    // ===== ADD =====
    private void handleAdd() {

        if (!validateFields()) return;

        Person person = new Person(
                firstnameField.getText(),
                lastnameField.getText(),
                nicknameField.getText(),
                phoneField.getText(),
                addressField.getText(),
                emailField.getText(),
                birthDatePicker.getValue()
        );

        personService.addPerson(person);
        clearFields();
        loadPersons();
    }

    // ===== UPDATE =====
    private void handleUpdate() {

        Person selected = personTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a person to update.");
            return;
        }

        if (!validateFields()) return;

        selected.setFirstname(firstnameField.getText());
        selected.setLastname(lastnameField.getText());
        selected.setNickname(nicknameField.getText());
        selected.setPhoneNumber(phoneField.getText());
        selected.setEmailAddress(emailField.getText());
        selected.setAddress(addressField.getText());
        selected.setBirthDate(birthDatePicker.getValue());

        personService.updatePerson(selected);
        clearFields();
        loadPersons();
    }

    // ===== DELETE =====
    private void handleDelete() {

        Person selected = personTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a person to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Contact");
        confirm.setContentText("Are you sure you want to delete this contact?");

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            personService.deletePerson(selected.getIdperson());
            clearFields();
            loadPersons();
        }
    }

    // ===== VALIDATION =====
    private boolean validateFields() {

    String firstname = firstnameField.getText().trim();
    String lastname = lastnameField.getText().trim();
    String nickname = nicknameField.getText().trim();
    String phone = phoneField.getText().trim();
    String email = emailField.getText().trim();

    // Required fields
    if (firstname.isEmpty() || lastname.isEmpty() || nickname.isEmpty()) {
        showAlert("Validation Error",
                "Firstname, Lastname and Nickname are required.");
        return false;
    }

    // Phone validation (exactly 10 digits)
    if (!phone.matches("\\d{10}")) {
        showAlert("Validation Error",
                "Phone number must contain exactly 10 digits.");
        return false;
    }

    // Email validation
    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        showAlert("Validation Error",
                "Please enter a valid email address.");
        return false;
    }

    // Birth date validation
LocalDate birthDate = birthDatePicker.getValue();

    if (birthDate == null) {
        showAlert("Validation Error", "Please select a birth date.");
        return false;
    }

    if (birthDate.isAfter(LocalDate.now())) {
        showAlert("Validation Error", "Birth date cannot be in the future.");
        return false;
    }

    if (birthDate.getYear() < 1900) {
        showAlert("Validation Error", "Birth year must be after 1900.");
        return false;
    }

    // Name validation
    String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]+$";

    if (!firstnameField.getText().matches(nameRegex)) {
        showAlert("Validation Error", "Firstname must contain only letters.");
        return false;
    }

    if (!lastnameField.getText().matches(nameRegex)) {
        showAlert("Validation Error", "Lastname must contain only letters.");
        return false;
    }

    return true;
    }

    // ===== ALERT =====
    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===== CLEAR =====
    private void clearFields() {
        firstnameField.clear();
        lastnameField.clear();
        nicknameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        birthDatePicker.setValue(null);
    }
}