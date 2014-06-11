package uk.co.revsys.objectology.serialiser.jackson;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

    private final Map<String, ThreadLocal> context = new HashMap<String, ThreadLocal>();
    
    public void set(String key, Object value){
        ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
        threadLocal.set(value);
        context.put(key, threadLocal);
    }
    
    public Object get(String key){
        ThreadLocal threadLocal = context.get(key);
        if(threadLocal != null){
            return threadLocal.get();
        }
        return null;
    }
    
    public void clear(){
        context.clear();
    }
    
}
