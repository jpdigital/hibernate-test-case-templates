package org.hibernate.bugs;

import de.jpdigital.hibernate.entites.BinaryAsset;
import org.hibernate.engine.jdbc.BlobProxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory(
            "templatePU"
        );
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh123Test() throws Exception {
        final Path testFilePath = Path.of("../README.md");
        
        assertThat(Files.exists(testFilePath), is(true));
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        final BinaryAsset asset = new BinaryAsset();
        asset.setFileName("test-file");
        
        final InputStream inputStream = new BufferedInputStream(
            Files.newInputStream(testFilePath)
        );
        // Ensure that the InputStream is resettable
        assertThat(inputStream.markSupported(), is(true));
        
        final Blob fileData = BlobProxy.generateProxy(
            inputStream, 
            Files.size(testFilePath)
        );
        
        asset.setData(fileData);

        entityManager.persist(asset);

        entityManager.getTransaction().commit();

        final BinaryAsset retrieved = entityManager
            .createQuery(
                "SELECT a FROM BinaryAsset a WHERE fileName = :fileName",
                BinaryAsset.class
            )
            .setParameter("fileName", "test-file")
            .getSingleResult();

        assertThat(retrieved.getFileName(), is(equalTo("test-file")));

        entityManager.close();
    }

}
