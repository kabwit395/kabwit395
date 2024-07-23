
import java.io.*;
import java.util.*;
class Contact {
    private String nom;
    private String email;
    public Contact(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}

class GestionContacts {
    private Map<String, Contact> contacts;

    public GestionContacts() {
        this.contacts = new HashMap<>();
    }

    public void ajouterContact(String categorie, String nom, String email) {
        contacts.put(categorie + "-" + UUID.randomUUID().toString(), new Contact(nom, email));
    }

    public void supprimerContact(String id) {
        contacts.remove(id);
    }

    public List<Contact> rechercherParNom(String nom) {
        List<Contact> resultats = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (contact.getNom().equals(nom)) {
                resultats.add(contact);
            }
        }
        return resultats;
    }

    public List<Contact> listerParLettre(char lettre) {
        List<Contact> resultats = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (contact.getNom().charAt(0) == lettre) {
                resultats.add(contact);
            }
        }
        return resultats;
    }

    public int nombreDeContacts() {
        return contacts.size();
    }


    public Map<String, List<Contact>> contactsParType() {
        Map<String, List<Contact>> contactsParType = new HashMap<>();
        for (String id : contacts.keySet()) {
            String type = id.split("-")[0];
            if (!contactsParType.containsKey(type)) {
                contactsParType.put(type, new ArrayList<>());
            }
            contactsParType.get(type).add(contacts.get(id));
        }
        return contactsParType;
    }

    public Contact detailsContact(String id) {
        return contacts.get(id);
    }
}

public class GestionContactsApp {
    public static void main(String[] args) {
        GestionContacts gestionContacts = new GestionContacts();
        gestionContacts.ajouterContact("personnel", "SK", "SK@gmail.com");


        System.out.println("Nombre de contacts : " + gestionContacts.nombreDeContacts());

        System.out.println("Recherche par nom 'sylvain' : " + gestionContacts.rechercherParNom("sylvain"));

        System.out.println("Liste des contacts commençant par 'A' : " + gestionContacts.listerParLettre('S'));

        System.out.println("Contacts par type : " + gestionContacts.contactsParType());


        try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : gestionContacts.contacts.values()) {
                writer.println(contact.getNom() + "," + contact.getEmail());


            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des contacts : " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                gestionContacts.ajouterContact("chargé", parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des contacts : " + e.getMessage());
        }


        try {

            throw new CustomException("Ceci est une exception personnalisée.");
        } catch (CustomException e) {
            System.err.println("Exception personnalisée attrapée : " + e.getMessage());
        }
    }
}

class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

