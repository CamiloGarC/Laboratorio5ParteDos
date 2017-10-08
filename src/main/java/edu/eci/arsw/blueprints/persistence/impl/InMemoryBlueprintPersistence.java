/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import static org.springframework.boot.Banner.Mode.LOG;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115), new Point(115, 15), new Point(115, 1), new Point(115, 5), new Point(115, 55)};
         Point[] pts1 = new Point[]{new Point(140, 14), new Point(115, 15), new Point(11, 15), new Point(115, 1), new Point(115, 5), new Point(115, 55)};
        Blueprint bp = new Blueprint("Karen", "bpname", pts);
        Blueprint bp1 = new Blueprint("Karen", "Obra3", pts1);
        Blueprint bp2 = new Blueprint("Camilo", "Obra2", pts);
        Blueprint bp3 = new Blueprint("MariaPaula", "Obra1", pts);
        Blueprint bp4 = new Blueprint("Laura", "Obra", pts);
        
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
   
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    /**
     *
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {

        Set<Blueprint> r = new HashSet<>();
        for (Tuple key : blueprints.keySet()) {
            if (key.getElem1().equals(author) ) {
                //LOG.info("Encontrado "+key.getElem1());
                r.add(blueprints.get(key));
            }
        }
        return r;
    }
    //private static final Logger LOG = Logger.getLogger(InMemoryBlueprintPersistence.class.getName());
    /**
     *
     * @return all the blueprints
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {

        Set<Blueprint> r = new HashSet<>();
        for (Tuple key : blueprints.keySet()) {
            r.add(blueprints.get(key));
        }
        return r;
    }
    /**
     *
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @throws edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException
     */
    @Override
     public void RemoveBluePrint(String author, String bpname) throws BlueprintNotFoundException{
         
        Map<Tuple<String, String>, Blueprint> blueprint = new HashMap<>();
        if(!blueprints.containsKey(new Tuple<>(author, bpname))){
            throw new BlueprintNotFoundException(bpname+"El bluePrint no existe:"+bpname);
        } else {
            blueprints.remove(new Tuple<>(author, bpname));
            //System.out.println("edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence.RemoveBluePrint()");
        }
        
     }

}
