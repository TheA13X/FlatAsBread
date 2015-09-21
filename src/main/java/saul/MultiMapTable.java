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
public class MultiMapTable {
	HashMap<String, Class>		cls;
	HashMap<String, ArrayList>	als;
	
	public MultiMapTable(ValuePair<Class>... vps) {
		cls = new HashMap<>();
		als = new HashMap<>();
		for (ValuePair vp : vps) {
			cls.put(vp.name, (Class) vp.value);
			als.put(vp.name, new ArrayList<>());
		}
	}
	
	public ArrayList getAll(String name) {
		return als.get(name);
	}
	
	public void addRow(ValuePair<?>... vars) {
		ArrayList<String> used = new ArrayList<>();
		for (ValuePair var : vars) {
			if (!cls.containsKey(var.name)) {
				throw new IllegalArgumentException("Column " + var.name + " does not exist");
			} else if (var.value != null && !var.value.getClass().equals(cls.get(var.name))) {
				throw new IllegalArgumentException("Value for " + var.name + " has a wrong Type");
			} else {
				als.get(var.name).add(var.value);
				used.add(var.name);
			}
		}
		for (String name : cls.keySet()) {
			if (!used.contains(name)) {
				als.get(name).add(null);
			}
		}
	}
	
	public boolean deleteFirst(String name, Object value) {
		if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (value != null && !value.getClass().equals(cls.get(name))) {
			throw new IllegalArgumentException("Value for " + name + " has a wrong Type");
		} else {
			for (Object val : als.get(name)) {
				if (val == null && value == null) {
					int index = als.get(name).indexOf(val);
					for (String lname : als.keySet()) {
						als.get(lname).remove(index);
					}
					return true;
				} else if (val != null && value != null && val.equals(value)) {
					int index = als.get(name).indexOf(val);
					for (String lname : als.keySet()) {
						als.get(lname).remove(index);
					}
					return true;
				}
			}
			return false;
		}
	}
	
	public int delete(String name, Object value) {
		if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (value != null && !value.getClass().equals(cls.get(name))) {
			throw new IllegalArgumentException("Value for " + name + " has a wrong Type");
		} else {
			int num = 0;
			for (Object val : als.get(name)) {
				if (val == null && value == null) {
					int index = als.get(name).indexOf(val);
					for (String lname : als.keySet()) {
						als.get(lname).remove(index);
					}
					num++;
				} else if (val != null && value != null && val.equals(value)) {
					int index = als.get(name).indexOf(val);
					for (String lname : als.keySet()) {
						als.get(lname).remove(index);
					}
					num++;
				}
			}
			return num;
		}
	}
	
	public boolean changeFirst(String name, Object value, String wherename, Object wherevalue) {
		if (!cls.containsKey(wherename)) {
			throw new IllegalArgumentException("Column " + wherename + " does not exist");
		} else if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (value != null && !value.getClass().equals(cls.get(name))) {
			throw new IllegalArgumentException("Value for " + name + " has a wrong Type");
		} else if (wherevalue != null && !wherevalue.getClass().equals(cls.get(wherename))) {
			throw new IllegalArgumentException("Value for " + wherename + " has a wrong Type");
		} else {
			for (Object val : als.get(wherename)) {
				if (wherevalue == null && val == null) {
					int index = als.get(wherename).indexOf(val);
					als.get(name).set(index, value);
					return true;
				} else if (val.equals(wherevalue)) {
					int index = als.get(wherename).indexOf(val);
					als.get(name).set(index, value);
					return true;
				}
			}
			return false;
		}
	}
	
	public int change(String name, Object value, String wherename, Object wherevalue) {
		if (!cls.containsKey(wherename)) {
			throw new IllegalArgumentException("Column " + wherename + " does not exist");
		} else if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (value != null && !value.getClass().equals(cls.get(name))) {
			throw new IllegalArgumentException("Value for " + name + " has a wrong Type");
		} else if (wherevalue != null && !wherevalue.getClass().equals(cls.get(wherename))) {
			throw new IllegalArgumentException("Value for " + wherename + " has a wrong Type");
		} else {
			int num = 0;
			for (Object val : als.get(wherename)) {
				if (wherevalue == null && val == null) {
					int index = als.get(wherename).indexOf(val);
					als.get(name).set(index, value);
					num++;
				} else if (wherevalue != null && val != null && val.equals(wherevalue)) {
					int index = als.get(wherename).indexOf(val);
					als.get(name).set(index, value);
					num++;
				}
			}
			return num;
		}
	}
	
	public Object getFirst(String name, String wherename, Object wherevalue) {
		if (!cls.containsKey(wherename)) {
			throw new IllegalArgumentException("Column " + wherename + " does not exist");
		} else if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (wherevalue != null && !wherevalue.getClass().equals(cls.get(wherename))) {
			throw new IllegalArgumentException("Value for " + wherename + " has a wrong Type");
		} else {
			for (Object val : als.get(wherename)) {
				if (wherevalue == null && val == null) {
					int index = als.get(wherename).indexOf(val);
					return als.get(name).get(index);
				} else if (wherevalue != null && val != null && val.equals(wherevalue)) {
					int index = als.get(wherename).indexOf(val);
					return als.get(name).get(index);
				}
			}
			return null;
		}
	}
	
	public ArrayList get(String name, String wherename, Object wherevalue) {
		ArrayList vals = new ArrayList();
		if (!cls.containsKey(wherename)) {
			throw new IllegalArgumentException("Column " + wherename + " does not exist");
		} else if (!cls.containsKey(name)) {
			throw new IllegalArgumentException("Column " + name + " does not exist");
		} else if (wherevalue != null && !wherevalue.getClass().equals(cls.get(wherename))) {
			throw new IllegalArgumentException("Value for " + wherename + " has a wrong Type");
		} else {
			for (Object val : als.get(wherename)) {
				if (wherevalue == null && val == null) {
					int index = als.get(wherename).indexOf(val);
					vals.add(als.get(name).get(index));
				} else if (wherevalue != null && val != null && val.equals(wherevalue)) {
					int index = als.get(wherename).indexOf(val);
					vals.add(als.get(name).get(index));
				}
			}
			return vals;
		}
	}
	
	public static class ValuePair<E> {
		public String	name;
		public E		value;
		
		public ValuePair(String n, E c) {
			name = n;
			value = c;
		}
	}
}
