 package com.virtusdev.backend_practice.run;

 import jakarta.transaction.Transactional;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.jdbc.core.simple.JdbcClient;
 import org.springframework.stereotype.Repository;
 import org.springframework.util.Assert;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import java.util.List;
 import java.util.Optional;
 
 @Repository
 @Qualifier("jdbcRunRepository")
 public class JdbcRunRepository implements RunRepository {

     private static final Logger logger = LoggerFactory.getLogger(JdbcRunRepository.class);


     private final JdbcClient jdbcClient;
 
     public JdbcRunRepository(JdbcClient jdbcClient) {
         this.jdbcClient = jdbcClient;
     }
 
     public List<Run> findAll() {
         return jdbcClient.sql("select * from run")
                 .query(Run.class)
                 .list();
     }
 
     public Optional<Run> findById(Integer id) {
         return jdbcClient.sql("SELECT id,title,started_on,completed_on,miles,location,version FROM Run WHERE id = :id" )
                 .param("id", id)
                 .query(Run.class)
                 .optional();
     }
 
     public void create(Run run) {
         var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location) values(?,?,?,?,?,?)")
                 .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString()))
                 .update();
 
         Assert.state(updated == 1, "Failed to create run " + run.title());
     }

     public void update(Run run, Integer id) {
         Assert.notNull(run, "Run must not be null");
         Assert.notNull(id, "ID must not be null");

         logger.info("Updating run with ID: {} and title: {}", id, run.title());

         // Check if the run with the given ID exists

         int updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                 .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                 .update();

         logger.info("Rows updated: {}", updated);

         Assert.state(updated == 1, "Failed to update run " + run.title());
     }
 
     public void delete(Integer id) {
         var updated = jdbcClient.sql("delete from run where id = :id")
                 .param("id", id)
                 .update();
 
         Assert.state(updated == 1, "Failed to delete run " + id);
     }
 
     public int count() {
         return jdbcClient.sql("select * from run").query().listOfRows().size();
     }
 
     public void saveAll(List<Run> runs) {
         runs.forEach(this::create);
     }
 
     public List<Run> findByLocation(String location) {
         return jdbcClient.sql("select * from run where location = :location")
                 .param("location", location)
                 .query(Run.class)
                 .list();
     }
 
 }