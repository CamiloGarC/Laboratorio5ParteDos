/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hcadavid
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlueprintNotFoundException extends Exception{

    public BlueprintNotFoundException(String message) {
        super(message);
    }
    public BlueprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
