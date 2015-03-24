/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 2 (Garage Class)
 *
 *  
 *****************************************************************************/

package test2;

import java.util.Collection;
import java.util.HashMap;

public class Test2 {
    
}
class Garage {
    private static final String[][] orders = new String[5][3];
    private static final HashMap<String, String> services =
            new HashMap<>();
    static {
        services.put("Lube Job", "19.99");
        services.put("Tire Rotation", "48.00");
        services.put("Random other service", "102.80");
    }
    
    public Collection getServices() { return services.values(); }
    public String     getPrice(String service) {
        return services.get(service);
    }
    
}
