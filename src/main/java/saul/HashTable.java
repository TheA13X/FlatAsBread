/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saul;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexis
 */
public class HashTable {
    HashMap<String,Class> cls;
    HashMap<String,ArrayList> als;
    public HashTable(ValuePairs... vps){
        cls=new HashMap<>();
        als=new HashMap<>();
        for (ValuePairs vp : vps) {
            cls.put(vp.name, vp.value);
            als.put(vp.name, new ArrayList<>());
        }
    }
    public class ValuePairs{
        public String name;
        public Class value;
        public ValuePairs(String n, Class c){
            name=n;
            value=c;
        }
    }
}
