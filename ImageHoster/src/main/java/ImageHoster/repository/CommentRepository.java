package ImageHoster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.thymeleaf.engine.CommentStructureHandler;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class CommentRepository {
	 //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;
    
    
    
    
    public void addComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            //persist() method changes the state of the model object from transient state to persistence state
            em.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
    
    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the Comments from the database
    //Returns the list of all the Comments fetched from the database
    public List<Comment> getAllComments() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT c from Comment c", Comment.class);
        List<Comment> resultList = query.getResultList();

        return resultList;
    }
    

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the Comment from the database with corresponding title
    //Returns null if no comment is found in the database
    public List<Comment> getCommentByImage(Image image) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Comment> typedQuery = em.createQuery("SELECT c from Comment c where c.image =:image", Comment.class);
            typedQuery.setParameter("image", image);
            
            List<Comment> resultList = typedQuery.getResultList();
            return resultList;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
