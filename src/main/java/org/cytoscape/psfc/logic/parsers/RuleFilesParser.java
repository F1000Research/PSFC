package org.cytoscape.psfc.logic.parsers;

import org.cytoscape.psfc.gui.enums.ExceptionMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * PUBLIC CLASS EdgeTypeRuleNameParser
 */
public class RuleFilesParser {
    private File configFile;
    private BufferedReader reader;
    private HashMap<String, String> edgeTypeRuleNameMap;

    public RuleFilesParser(File configFile) throws FileNotFoundException {
        if (configFile == null)
            throw new NullPointerException(ExceptionMessages.NullConfigFile.getMessage());
        this.configFile = configFile;
        reader = new BufferedReader(new FileReader(configFile));
    }

    public void readConfigFile(){

    }

    public static HashMap<String, String> parseSimpleRules(File edgeTypeRuleNameConfigFile,
                                                            File ruleConfigFile) throws Exception {
        HashMap<String, String> edgeTypeRuleNameMap = parseEdgeTypeRuleNameConfigFile(edgeTypeRuleNameConfigFile);
        HashMap<String, String> ruleNameRuleMap = parseRuleNameRuleConfigFile(ruleConfigFile);
        HashMap<String, String> edgeTypeRuleMap = new HashMap<String, String>();
        for (String edgeType : edgeTypeRuleNameMap.keySet()) {
            String ruleName = edgeTypeRuleNameMap.get(edgeType);
            if (!ruleNameRuleMap.containsKey(ruleName))
                throw new Exception("No rule was specified for rule name " + ruleName);
            edgeTypeRuleMap.put(edgeType, ruleNameRuleMap.get(ruleName));
        }
        return edgeTypeRuleMap;
    }

    public static HashMap<String, String> parseRuleNameRuleConfigFile(File ruleConfigFile) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(ruleConfigFile));
        String line;
        HashMap<String, String> ruleNameRuleMap = new HashMap<String, String>();
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\t");
            if (tokens.length != 2)
                throw new Exception("There should be exactly two tab-delimited columns in the file, found " + tokens.length);
            if (ruleNameRuleMap.containsKey(tokens[0]))
                throw new Exception("Duplicate occurrence of rule name " + tokens[1] + " in file " + ruleConfigFile.getName());
            ruleNameRuleMap.put(tokens[0], tokens[1]);
        }
        if (ruleNameRuleMap.isEmpty())
            throw new Exception("RuleName-Rule configuration file " + ruleConfigFile.getName() + " contained no entries");
        return ruleNameRuleMap;
    }


    public static HashMap<String, String> parseEdgeTypeRuleNameConfigFile
            (File edgeTypeRuleNameConfigFile) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(edgeTypeRuleNameConfigFile));
        String line;
        HashMap<String, String> edgeTypeRuleNameMap = new HashMap<String, String>();
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\t");
            if (tokens.length != 2)
                throw new Exception("There should be exactly two tab-delimited columns in the file, found " + tokens.length);
            if (edgeTypeRuleNameMap.containsKey(tokens[0]))
                throw new Exception("Duplicate occurrence of edge type " + tokens[1] + "in file " + edgeTypeRuleNameConfigFile.getName());
            edgeTypeRuleNameMap.put(tokens[0], tokens[1]);
        }
        if (edgeTypeRuleNameMap.isEmpty())
            throw new Exception("EdgeType-RuleName configuration file " + edgeTypeRuleNameConfigFile.getName()
                    + " contained no entries");
        return edgeTypeRuleNameMap;
    }
}
