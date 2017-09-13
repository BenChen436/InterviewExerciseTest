package org.example.ws.web.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.ws.model.Course;
import org.example.ws.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;
    private static Map<BigInteger, Course> courseMap;
    

    private static Greeting save(Greeting greeting) {
        if (greetingMap == null) {
            greetingMap = new HashMap<BigInteger, Greeting>();
            
            nextId = BigInteger.ONE;
        }
        // If Update...
        if (greeting.getId() != null) {
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if (oldGreeting == null) {
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(), greeting);
            return greeting;
        }
        // If Create...
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }
    
    private static Course saveC(Course course) {
        if (courseMap == null) {
        	courseMap = new HashMap<BigInteger, Course>();
            
            nextId = BigInteger.ONE;
        }
        // If Update...
        if (course.getId() != null) {
        	Course oldCourse = courseMap.get(course.getId());
            if (oldCourse == null) {
                return null;
            }
            courseMap.remove(course.getId());
            courseMap.put(course.getId(), course);
            return course;
        }
        // If Create...
        course.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        courseMap.put(course.getId(), course);
        return course;
    }

    private static boolean delete(BigInteger id) {
        Greeting deletedGreeting = greetingMap.remove(id);
        if (deletedGreeting == null) {
            return false;
        }
        return true;
    }

    static {
    	
        Greeting g1 = new Greeting();
        g1.setText("Interview1");
        save(g1);

        Greeting g2 = new Greeting();
        g2.setText("Interview2");
        save(g2);
        
        Greeting g3 = new Greeting();
        g3.setText("Interview3");
        save(g3);

        Greeting g4 = new Greeting();
        g4.setText("InterviewPassed");
        save(g4);
        
        Course c1 = new Course();
    	c1.setName("Java");
    	List<Greeting> a1 = new ArrayList<Greeting>();
    	a1.add(g1);
    	a1.add(g2);
    	c1.setGreet(a1);
    	saveC(c1);
    	
    	Course c2 = new Course();
    	c2.setName("C#");
     	List<Greeting> a2 = new ArrayList<Greeting>();
     	a2.add(g3);
     	a2.add(g4);
     	c2.setGreet(a2);
     	saveC(c2);
    }
    
    @RequestMapping(
            value = "/api/courses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Course>> getCourses() {

        Collection<Course> courses = courseMap.values();

        return new ResponseEntity<Collection<Course>>(courses,
                HttpStatus.OK);
    }

    @RequestMapping(
    		value = "/api/courses/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourse(
            @PathVariable("id") BigInteger id) {

    	Course course = courseMap.get(id);
        if (course == null) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/courses/{id}/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {

        Collection<Greeting> greetings = greetingMap.values();

        return new ResponseEntity<Collection<Greeting>>(greetings,
                HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/courses/{id}/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(
            @PathVariable("id") BigInteger id) {

        Greeting greeting = greetingMap.get(id);
        if (greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/courses/{id}/greetings",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(
            @RequestBody Greeting greeting) {

        Greeting savedGreeting = save(greeting);

        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/courses/{id}/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(
            @RequestBody Greeting greeting) {

        Greeting updatedGreeting = save(greeting);
        if (updatedGreeting == null) {
            return new ResponseEntity<Greeting>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/courses/{id}/greetings/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(
            @PathVariable("id") BigInteger id, @RequestBody Greeting greeting) {

        boolean deleted = delete(id);
        if (!deleted) {
            return new ResponseEntity<Greeting>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

}
