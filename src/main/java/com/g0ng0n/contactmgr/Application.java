package com.g0ng0n.contactmgr;

import com.g0ng0n.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
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

        save(contact);

        for(Contact c : fetchAllContacts()){
            System.out.println(c);
        }
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

    private static void save (Contact contact){

        // Open a Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        session.beginTransaction();

        // Use the session to save the contact
        session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // close the session
        session.close();
    }
}
