package com.g0ng0n.contactmgr;

import com.g0ng0n.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by g0ng0n.
 */
public class Application {
    // holds a reusable reference to aSession Factory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        //create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    public static void main (String[] args){

        Contact contact = new Contact.ContactBuilder("Gonzo", "Gono")
                .withEmail("")
                .withPhone(1231233L)
                .build();

        int idSaved = save(contact);
        System.out.printf("%n%n Before UPDATE %n %n");
        // Display a list of contacts Before the update
        for(Contact c : fetchAllContacts()){
            System.out.println(c);
        }

        // Get persisted contact
        Contact c = findContactById(idSaved);

        // update the contact
        c.setLastName("Gisb");

        // persist the changes

        System.out.printf("%n%n updating.... %n %n");
        update(c);

        System.out.printf("%n%n UPDATE COMPLETE!! %n %n");
        // display a list of contacts after the update
        System.out.printf("%n%n After update %n%n");
        fetchAllContacts().stream().forEach(System.out::println);
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts(){
        // Open session
        Session session = sessionFactory.openSession();

        // Create a criteria Object
        Criteria criteria = session.createCriteria(Contact.class);

        // Get a List of contact objeccts according to the crietria object
        List<Contact> contacts = criteria.list();

        // Close session
        session.close();
        return contacts;
    }

    private static Contact findContactById(int id){
        // Open a session
        Session session = sessionFactory.openSession();
        // Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close a session
        session.close();

        // Return the object
        return contact;

    }

    private static void update(Contact contact){

        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // use the session to update the contact
        session.update(contact);

        // commit the transaction
        session.getTransaction().commit();
        // close the session
        session.close();
    }
    private static int save (Contact contact){

        // Open a Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        session.beginTransaction();

        // Use the session to save the contact
        int id = (int) session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // close the session
        session.close();

        return id;
    }
}
