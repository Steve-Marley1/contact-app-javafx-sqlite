package com.javateam.service;

import com.javateam.dao.PersonDAO;
import com.javateam.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class PersonService {

    private final PersonDAO dao;

    // Lettres (y compris accents) + espaces + - + '
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[\\p{L}]+([\\p{L} '\\-][\\p{L}]+)*$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{10}$");

    // Email plus strict (doit avoir @ + domaine + extension)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern ADDRESS_PATTERN =
            Pattern.compile("^[\\p{L}0-9 ,.'\\-\\/]+$");

    public PersonService() {
        this.dao = new PersonDAO();
    }

    // Injection pour tests unitaires
    public PersonService(PersonDAO dao) {
        this.dao = dao;
    }

    // ================= CRUD =================

    public int addPerson(Person person) {
        validatePerson(person);
        return dao.addPerson(person);
    }

    public void updatePerson(Person person) {
        if (person == null || person.getIdperson() <= 0) {
            throw new IllegalArgumentException("Veuillez sélectionner un contact valide.");
        }
        validatePerson(person);
        dao.updatePerson(person);
    }

    public void deletePerson(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID invalide.");
        dao.deletePerson(id);
    }

    public List<Person> getAllPersons() {
        return dao.getAllPersons();
    }

    // ================= VALIDATION METIER =================

    private void validatePerson(Person p) {
        if (p == null) throw new IllegalArgumentException("Personne invalide.");

        validateFirstname(p.getFirstname());
        validateLastname(p.getLastname());
        validateNickname(p.getNickname());
        validatePhone(p.getPhoneNumber());
        validateEmail(p.getEmailAddress());
        validateAddress(p.getAddress());
        validateBirthDate(p.getBirthDate());
    }

    private void validateFirstname(String firstname) {
        if (isBlank(firstname)) throw new IllegalArgumentException("Firstname est obligatoire.");
        if (!NAME_PATTERN.matcher(firstname.trim()).matches()) {
            throw new IllegalArgumentException("Firstname doit contenir uniquement des lettres (espaces, - et ' autorisés).");
        }
    }

    private void validateLastname(String lastname) {
        if (isBlank(lastname)) throw new IllegalArgumentException("Lastname est obligatoire.");
        if (!NAME_PATTERN.matcher(lastname.trim()).matches()) {
            throw new IllegalArgumentException("Lastname doit contenir uniquement des lettres (espaces, - et ' autorisés).");
        }
    }

    private void validateNickname(String nickname) {
        if (isBlank(nickname)) throw new IllegalArgumentException("Nickname est obligatoire.");
        if (nickname.trim().length() < 2) throw new IllegalArgumentException("Nickname est trop court.");
    }

    private void validatePhone(String phone) {
        if (isBlank(phone)) throw new IllegalArgumentException("Phone est obligatoire.");
        String normalized = phone.trim();
        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Phone doit contenir exactement 10 chiffres (ex: 0612345678).");
        }
    }

    private void validateEmail(String email) {
        if (isBlank(email)) throw new IllegalArgumentException("Email est obligatoire.");

        String normalized = email.trim();

        // Pas d'espace dans un email
        if (normalized.contains(" ")) {
            throw new IllegalArgumentException("Email invalide : pas d'espaces autorisés.");
        }

        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Email invalide. Exemple : nom.prenom@gmail.com");
        }
    }

    private void validateAddress(String address) {
        if (isBlank(address)) throw new IllegalArgumentException("Address est obligatoire.");

        String normalized = address.trim();
        if (normalized.length() < 5) throw new IllegalArgumentException("Address est trop courte.");

        if (!ADDRESS_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Address contient des caractères interdits.");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) throw new IllegalArgumentException("Birth date est obligatoire.");
        if (birthDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Birth date ne peut pas être dans le futur.");
        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) throw new IllegalArgumentException("Birth date invalide (trop ancienne).");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}