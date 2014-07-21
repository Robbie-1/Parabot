package org.parabot.environment.scripts.randoms;

import java.util.ArrayList;

/**
 * 
 * @author Everel
 *
 */
public class RandomHandler {
    private ArrayList<Random> randoms;
    
    public RandomHandler() {
    	randoms = new ArrayList<>();
    }

    /**
     * Adds a random to the random list
     * @param random
     */
    public void addRandom(Random random) {
        randoms.add(random);
    }

    /**
     * Checks if random occurs and runs it
     * @return returns <b>true</b> if a random has been executed, otherwise <b>false</b>
     */
    public boolean checkAndRun() {
        for(Random r : randoms) {
            if(r.activate()) {
                r.execute();
                return true;
            }
        }
        return false;
    }
}