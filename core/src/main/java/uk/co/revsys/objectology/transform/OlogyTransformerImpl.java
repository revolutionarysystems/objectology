package uk.co.revsys.objectology.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.transform.path.JXPathEvaluator;

public class OlogyTransformerImpl implements OlogyTransformer {

    private final PathEvaluator pathEvaluator = new JXPathEvaluator();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object transform(OlogyInstance instance, ViewDefinition viewDefinition) throws TransformException {
        Object root = instance;
        String rootPath = viewDefinition.getRoot();
        if (rootPath != null) {
            try {
                root = pathEvaluator.evaluate(instance, rootPath);
            } catch (PathEvaluatorException ex) {
                throw new TransformException("Unable to find root " + rootPath, ex);
            }
        }
        Map<String, Object> view = new HashMap<String, Object>();
        ViewDefinitionRule rootRule = viewDefinition.getRules().get("main");
        Object result;
        if(rootRule!=null){
            result = rootRule.evaluate(root, view, viewDefinition, this);
        }else{
            result = view;
        }
        return result;
    }

    public Map<String, Object> getObjectAsMap(Object object) throws TransformException {
        try {
            Map<String, Object> objectAsMap = new HashMap<String, Object>();
            BeanInfo info = Introspector.getBeanInfo(object.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    objectAsMap.put(pd.getName(), reader.invoke(object));
                }
            }
            return objectAsMap;
        } catch (IntrospectionException ex) {
            throw new TransformException(ex);
        } catch (IllegalAccessException ex) {
            throw new TransformException(ex);
        } catch (IllegalArgumentException ex) {
            throw new TransformException(ex);
        } catch (InvocationTargetException ex) {
            throw new TransformException(ex);
        }
    }

    @Override
    public PathEvaluator getPathEvaluator() {
        return pathEvaluator;
    }
}
