package org.pingclubmanager.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Rest Controller
 * Allow to provide a default response for base API URL
 * @author Tony Boisteux
 *
 */
@RestController
@RequestMapping("/")
public class APIRestController {

    /**
     * Generic response to a successful login
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> get() {
        return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
    }
    
}
