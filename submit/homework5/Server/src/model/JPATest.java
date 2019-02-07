package model;
import javax.persistence.*;

public class JPATest {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServletTest");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = new User("user006","123456",100);
        em.persist(user);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
