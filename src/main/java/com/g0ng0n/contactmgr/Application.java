package com.g0ng0n.contactmgr;

import com.g0ng0n.contactmgr.model.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.service.ServiceRegistry;

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

        Contact contact = new Contact.ContactBuilder("Gonzo", "Gono").withEmail("").withPhone(1231233L).build();
    }
}
