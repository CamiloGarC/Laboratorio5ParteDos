/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    private BlueprintsServices bp;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> obtenerBluePrints() throws BlueprintNotFoundException, ResourceNotFoundException {

        if (bp.getAllBlueprints().isEmpty()) {
            throw new ResourceNotFoundException("Esta vacio");
        } else {
            return new ResponseEntity<>(bp.getAllBlueprints(), HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(path = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> obtenerBluePrintsByAuthor(@PathVariable String author) throws BlueprintNotFoundException, ResourceNotFoundException {
        if (bp.getBlueprintsByAuthor(author).isEmpty()) {
            throw new ResourceNotFoundException("Esta vacio");
        } else {
            return new ResponseEntity<>(bp.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> obtenerGetBlueprint(@PathVariable String author, @PathVariable String bpname) throws ResourceNotFoundException, BlueprintNotFoundException {
        if (bp.getBlueprint(author, bpname) == null) {
            //System.out.println(bp.getBlueprint(author, bpname).getName());
            throw new ResourceNotFoundException("!!!!No se encuentra!!!!");
        } else {
            return new ResponseEntity<>(bp.getBlueprint(author, bpname), HttpStatus.ACCEPTED);
        }

    }
     @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostBluePrint(@RequestBody Blueprint o) {
        try {
            bp.addNewBlueprint(o);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            //Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error POST BluePrint, El plano ya esta registrado\n", HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> manejadorPutBluePrint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint o){
        try {
            bp.RemoveBluePrint(author,bpname);
            bp.addNewBlueprint(o);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException | BlueprintPersistenceException ex) {
            //Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error PUT BluePrint, No se pudo actualizar el BluePrint\n", HttpStatus.FORBIDDEN);
        }   
    }
    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.DELETE)
    public ResponseEntity<?> manejadorDeleteBluePrint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint o){
        try {
            bp.RemoveBluePrint(author,bpname);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException ex) {
            //Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error DELETE BluePrint, El BluePrint no existe\n", HttpStatus.FORBIDDEN);
        }   
    }

}
