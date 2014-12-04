package uk.co.revsys.objectology.view.definition.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.rule.ChainRule;
import uk.co.revsys.objectology.view.definition.rule.CopyRule;
import uk.co.revsys.objectology.view.definition.rule.FilterRule;
import uk.co.revsys.objectology.view.definition.rule.FlattenRule;
import uk.co.revsys.objectology.view.definition.rule.TemplateRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class ViewDefinitionParserImpl implements ViewDefinitionParser{

    private ObjectMapper objectMapper = new ObjectMapper();

    public ViewDefinitionParserImpl() {
        SimpleModule module = new SimpleModule();
        Map<String, Class<? extends ViewDefinitionRule>> mappings = new HashMap<String, Class<? extends ViewDefinitionRule>>();
        mappings.put("filter", FilterRule.class);
        mappings.put("template", TemplateRule.class);
        mappings.put("flatten", FlattenRule.class);
        mappings.put("copy", CopyRule.class);
        mappings.put("chain", ChainRule.class);
        module.addDeserializer(ViewDefinitionRule.class, new ViewDefinitionRuleDeserialiser(mappings));
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Override
    public ViewDefinition parse(String jsonString) throws ViewDefinitionParsingException {
        try {
            ViewDefinition viewDefinition = objectMapper.readValue(jsonString, ViewDefinition.class);
            return viewDefinition;
        } catch (IOException ex) {
            throw new ViewDefinitionParsingException(ex);
        }
    }
   
}
