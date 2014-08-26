package uk.co.revsys.objectology.view.definition.parser;

import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRuleSet;
import uk.co.revsys.objectology.view.definition.rule.DelegateRule;
import uk.co.revsys.objectology.view.definition.rule.ReplaceRootRule;
import uk.co.revsys.objectology.view.definition.rule.OneToOneMappingRule;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class ViewDefinitionParserImpl implements ViewDefinitionParser{

    @Override
    public ViewDefinition parse(String jsonString) throws ViewDefinitionParsingException {
        JSONObject json = new JSONObject(jsonString);
        String name = json.getString("name");
        JSONObject templates = json.getJSONObject("templates");
        Map<String, ViewDefinitionRule> rules = new HashMap<String, ViewDefinitionRule>();
        Map<String, JSONObject> ruleSetsJson = new HashMap<String, JSONObject>();
        for(Object key: templates.keySet()){
            String path = (String)key;
            System.out.println("path = " + path);
            Object template = templates.get(path);
            System.out.println("template = " + template);
            ViewDefinitionRule rule;
            if(template instanceof String){
                rule = new ReplaceRootRule((String)template);
            }else if(template instanceof JSONObject){
                ViewDefinitionRuleSet ruleSet = new ViewDefinitionRuleSet();
                ruleSetsJson.put(path, (JSONObject)(template));
                rule = ruleSet;
            }else{
                throw new ViewDefinitionParsingException("Unable to parse " + template);
            }
            rules.put(path, rule);
        }
        for(Entry<String, JSONObject> ruleSetJsonEntry: ruleSetsJson.entrySet()){
            String path = ruleSetJsonEntry.getKey();
            JSONObject ruleJSON = ruleSetJsonEntry.getValue();
            ViewDefinitionRuleSet ruleSet = (ViewDefinitionRuleSet) rules.get(path);
            buildRuleSet(ruleSet, ruleJSON, rules);
        }
        String root = json.optString("root", "$");
        ViewDefinitionRule rootRule = rules.get(root);
        if(rootRule==null){
            throw new ViewDefinitionParsingException("No root template specified");
        }
        ViewDefinition transform = new ViewDefinition(name, root, rootRule);
        return transform;
    }
    
    private void buildRuleSet(ViewDefinitionRuleSet ruleSet, JSONObject json, Map<String, ViewDefinitionRule> rules) throws ViewDefinitionParsingException{
        for(Object key: json.keySet()){
            ViewDefinitionRule rule;
            String label = (String)key;
            Object ruleJSON = json.get(label);
            if(ruleJSON instanceof String){
                String ruleString = (String)ruleJSON;
                if(rules.containsKey(ruleString)){
                    rule = new DelegateRule(label, ruleString, rules.get(ruleString));
                }else{
                    rule = new OneToOneMappingRule(label, ruleString);
                }
            }else if(ruleJSON instanceof JSONObject){
                rule = new ViewDefinitionRuleSet();
                buildRuleSet((ViewDefinitionRuleSet)rule, (JSONObject)ruleJSON, rules);
            }else{
                throw new ViewDefinitionParsingException("Unable to parse " + ruleJSON);
            }
            ruleSet.addRule(rule);
        }
    }
}
