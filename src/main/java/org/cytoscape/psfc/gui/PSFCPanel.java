package org.cytoscape.psfc.gui;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.psfc.PSFCActivator;
import org.cytoscape.psfc.gui.actions.CalculateScoreFlowAction;
import org.cytoscape.psfc.gui.actions.SortNetworkAction;
import org.cytoscape.psfc.gui.enums.EColumnNames;
import org.cytoscape.psfc.gui.enums.ENodeDataProps;
import org.cytoscape.psfc.gui.enums.ESortingAlgorithms;
import org.cytoscape.psfc.logic.structures.Node;
import org.cytoscape.psfc.properties.EpsfcProps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * PUBCLI CLASS PSFCPanel
 * <p/>
 * Sets the components of the app panel, located in the WEST CytoPanel group.
 */
public class PSFCPanel extends JPanel implements CytoPanelComponent {
    private String title = "PSFC";
    private String suidSplit = ":SUID";
    private String iconName = "psfc_icon.png";
    private String levelAttr = EColumnNames.Level.getName();
    private File edgeTypeRuleNameConfigFile;
    private File ruleNameRuleConfigFile;

    public PSFCPanel() {
        this.setPreferredSize(new Dimension(450, getHeight()));
        loadProps();
        initComponents();
        setComponentProperties();
        setModels();
        addActionListeners();
        enableButtons();
    }

    public void loadProps() {
        for (EpsfcProps property : EpsfcProps.values()) {
            property.setOldValue(Boolean.parseBoolean((String) PSFCActivator.getPsfcProps().get(property.getName())));
            property.setNewValue(Boolean.parseBoolean((String) PSFCActivator.getPsfcProps().get(property.getName())));
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public CytoPanelName getCytoPanelName() {
        return CytoPanelName.WEST;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Icon getIcon() {
//        ClassLoader classLoader = PSFCActivator.class.getClassLoader();
//        Icon icon = new ImageIcon(classLoader.getResource(iconName));
        return null;
    }

    private javax.swing.JButton jb_calculateFlow;
    private javax.swing.JButton jb_chooseEdgeTypeConfigFile;
    private javax.swing.JButton jb_chooseRuleNameRuleConfigFile;
    private javax.swing.JButton jb_refreshEdgeTypeAttrs;
    private javax.swing.JButton jb_refreshNetworks;
    private javax.swing.JButton jb_refreshNodeDataAttrs;
    private javax.swing.JButton jb_ruleWizard;
    private javax.swing.JButton jb_saveSettings;
    private javax.swing.JButton jb_showEdgeTypes;
    private javax.swing.JButton jb_showNodeData;
    private javax.swing.JButton jb_sortNetwork;
    private javax.swing.JComboBox jcb_edgeTypeAttribute;
    private javax.swing.JComboBox jcb_network;
    private javax.swing.JComboBox jcb_nodeDataAttribute;
    private javax.swing.JComboBox jcb_sortingAlgorithm;
    private javax.swing.JComboBox jcb_weights;
    private javax.swing.JLabel jl_chooseNetwork;
    private javax.swing.JLabel jl_dataMappingRules;
    private javax.swing.JLabel jl_dataType;
    private javax.swing.JLabel jl_defaultValue;
    private javax.swing.JLabel jl_edgeTypeConfigFile;
    private javax.swing.JLabel jl_edgeTypeConfigFileName;
    private javax.swing.JLabel jl_multiInOutRules;
    private javax.swing.JLabel jl_multiSignalProcessing;
    private javax.swing.JLabel jl_multipleDataRule;
    private javax.swing.JLabel jl_ruleConfigFile;
    private javax.swing.JLabel jl_ruleNameRuleConfigFileName;
    private javax.swing.JLabel jl_selectEdgeTypeAttribute;
    private javax.swing.JLabel jl_selectNodeDataAttribute;
    private javax.swing.JLabel jl_signalSplitOn;
    private javax.swing.JLabel jl_signalSplitRule;
    private javax.swing.JLabel jl_simpleRules;
    private javax.swing.JLabel jl_sortingAlgorithm;
    private javax.swing.JPanel jp_Data;
    private javax.swing.JPanel jp_Network;
    private javax.swing.JPanel jp_Rules;
    private javax.swing.JPanel jp_multiInOutRulesPanel;
    private javax.swing.JPanel jp_simpleRules;
    private javax.swing.JRadioButton jrb_FC;
    private javax.swing.JRadioButton jrb_addition;
    private javax.swing.JRadioButton jrb_equal;
    private javax.swing.JRadioButton jrb_incommingEdges;
    private javax.swing.JRadioButton jrb_linear;
    private javax.swing.JRadioButton jrb_log;
    private javax.swing.JRadioButton jrb_logFC;
    private javax.swing.JRadioButton jrb_max;
    private javax.swing.JRadioButton jrb_mean;
    private javax.swing.JRadioButton jrb_min;
    private javax.swing.JRadioButton jrb_multiplication;
    private javax.swing.JRadioButton jrb_none;
    private javax.swing.JRadioButton jrb_outgoingEdges;
    private javax.swing.JRadioButton jrb_proportional;
    private javax.swing.JRadioButton jrb_suppliedWeights;
    private javax.swing.JRadioButton jrb_updatedNodeScores;
    private javax.swing.JTabbedPane jtp_psfc;
    private javax.swing.JTextField jtxt_defaultValue;

    private ButtonGroup jbg_dataType;
    private ButtonGroup jbg_multipleDataOption;
    private ButtonGroup jbg_splitSignalOn;
    private ButtonGroup jbg_signalSplitRule;
    private ButtonGroup jbg_multipleSignalProcessingRule;


    private void initComponents() {


        jtp_psfc = new javax.swing.JTabbedPane();
        jp_Network = new javax.swing.JPanel();
        jcb_sortingAlgorithm = new javax.swing.JComboBox();
        jb_sortNetwork = new javax.swing.JButton();
        jl_chooseNetwork = new javax.swing.JLabel();
        jcb_network = new javax.swing.JComboBox();
        jl_sortingAlgorithm = new javax.swing.JLabel();
        jl_selectEdgeTypeAttribute = new javax.swing.JLabel();
        jcb_edgeTypeAttribute = new javax.swing.JComboBox();
        jb_showEdgeTypes = new javax.swing.JButton();
        jl_selectNodeDataAttribute = new javax.swing.JLabel();
        jcb_nodeDataAttribute = new javax.swing.JComboBox();
        jb_refreshNetworks = new javax.swing.JButton();
        jb_refreshEdgeTypeAttrs = new javax.swing.JButton();
        jb_refreshNodeDataAttrs = new javax.swing.JButton();
        jb_showNodeData = new javax.swing.JButton();
        jp_Data = new javax.swing.JPanel();
        jl_dataMappingRules = new javax.swing.JLabel();
        jl_dataType = new javax.swing.JLabel();
        jrb_linear = new javax.swing.JRadioButton();
        jrb_log = new javax.swing.JRadioButton();
        jrb_logFC = new javax.swing.JRadioButton();
        jrb_FC = new javax.swing.JRadioButton();
        jl_defaultValue = new javax.swing.JLabel();
        jtxt_defaultValue = new javax.swing.JTextField();
        jl_multipleDataRule = new javax.swing.JLabel();
        jrb_min = new javax.swing.JRadioButton();
        jrb_max = new javax.swing.JRadioButton();
        jrb_mean = new javax.swing.JRadioButton();
        jp_Rules = new javax.swing.JPanel();
        jb_ruleWizard = new javax.swing.JButton();
        jp_multiInOutRulesPanel = new javax.swing.JPanel();
        jrb_incommingEdges = new javax.swing.JRadioButton();
        jrb_outgoingEdges = new javax.swing.JRadioButton();
        jl_signalSplitOn = new javax.swing.JLabel();
        jl_multiInOutRules = new javax.swing.JLabel();
        jl_signalSplitRule = new javax.swing.JLabel();
        jrb_equal = new javax.swing.JRadioButton();
        jrb_proportional = new javax.swing.JRadioButton();
        jrb_none = new javax.swing.JRadioButton();
        jrb_suppliedWeights = new javax.swing.JRadioButton();
        jcb_weights = new javax.swing.JComboBox();
        jl_multiSignalProcessing = new javax.swing.JLabel();
        jrb_addition = new javax.swing.JRadioButton();
        jrb_multiplication = new javax.swing.JRadioButton();
        jrb_updatedNodeScores = new javax.swing.JRadioButton();
        jp_simpleRules = new javax.swing.JPanel();
        jl_simpleRules = new javax.swing.JLabel();
        jl_edgeTypeConfigFile = new javax.swing.JLabel();
        jb_chooseEdgeTypeConfigFile = new javax.swing.JButton();
        jl_edgeTypeConfigFileName = new javax.swing.JLabel();
        jb_chooseRuleNameRuleConfigFile = new javax.swing.JButton();
        jl_ruleConfigFile = new javax.swing.JLabel();
        jl_ruleNameRuleConfigFileName = new javax.swing.JLabel();
        jb_calculateFlow = new javax.swing.JButton();
        jb_saveSettings = new javax.swing.JButton();


        jp_Network.setPreferredSize(new java.awt.Dimension(400, 500));

        jcb_sortingAlgorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Hierarchical", "BFS", "DFS", "ShortestPath"}));

        jb_sortNetwork.setText("Sort");
        jb_sortNetwork.setPreferredSize(new java.awt.Dimension(59, 20));
        jb_sortNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_sortNetworkActionPerformed(evt);
            }
        });

        jl_chooseNetwork.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_chooseNetwork.setText("Network");

        jcb_network.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Current Network", "Network1", "Network2", "Network3"}));

        jl_sortingAlgorithm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_sortingAlgorithm.setText("Sorting algorithm");

        jl_selectEdgeTypeAttribute.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_selectEdgeTypeAttribute.setText("Select Edge type attribute");

        jcb_edgeTypeAttribute.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jb_showEdgeTypes.setText("Show");
        jb_showEdgeTypes.setPreferredSize(new java.awt.Dimension(59, 20));
        jb_showEdgeTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_showEdgeTypesActionPerformed(evt);
            }
        });

        jl_selectNodeDataAttribute.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_selectNodeDataAttribute.setText("Select Node data attribute");

        jcb_nodeDataAttribute.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jb_refreshNetworks.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\refresh-green.png")); // NOI18N
        jb_refreshNetworks.setMaximumSize(new java.awt.Dimension(20, 20));
        jb_refreshNetworks.setMinimumSize(new java.awt.Dimension(20, 20));
        jb_refreshNetworks.setPreferredSize(new java.awt.Dimension(20, 20));
        jb_refreshNetworks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_refreshNetworksActionPerformed(evt);
            }
        });

        jb_refreshEdgeTypeAttrs.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\refresh-green.png")); // NOI18N
        jb_refreshEdgeTypeAttrs.setMaximumSize(new java.awt.Dimension(20, 20));
        jb_refreshEdgeTypeAttrs.setMinimumSize(new java.awt.Dimension(20, 20));
        jb_refreshEdgeTypeAttrs.setPreferredSize(new java.awt.Dimension(20, 20));
        jb_refreshEdgeTypeAttrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_refreshEdgeTypeAttrsActionPerformed(evt);
            }
        });

        jb_refreshNodeDataAttrs.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\refresh-green.png")); // NOI18N
        jb_refreshNodeDataAttrs.setMaximumSize(new java.awt.Dimension(20, 20));
        jb_refreshNodeDataAttrs.setMinimumSize(new java.awt.Dimension(20, 20));
        jb_refreshNodeDataAttrs.setPreferredSize(new java.awt.Dimension(20, 20));
        jb_refreshNodeDataAttrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_refreshNodeDataAttrsActionPerformed(evt);
            }
        });

        jb_showNodeData.setText("Show");
        jb_showNodeData.setPreferredSize(new java.awt.Dimension(59, 20));
        jb_showNodeData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_showNodeDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_NetworkLayout = new javax.swing.GroupLayout(jp_Network);
        jp_Network.setLayout(jp_NetworkLayout);
        jp_NetworkLayout.setHorizontalGroup(
                jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jl_chooseNetwork)
                                        .addComponent(jl_selectEdgeTypeAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jl_sortingAlgorithm)
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addComponent(jcb_network, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jb_refreshNetworks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addComponent(jcb_nodeDataAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jb_refreshNodeDataAttrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jb_showNodeData, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                                .addComponent(jcb_edgeTypeAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jb_refreshEdgeTypeAttrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jcb_sortingAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jb_sortNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jb_showEdgeTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jl_selectNodeDataAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(23, Short.MAX_VALUE))
        );
        jp_NetworkLayout.setVerticalGroup(
                jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jl_chooseNetwork)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jcb_network, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jb_refreshNetworks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jl_sortingAlgorithm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jcb_sortingAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jb_sortNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addComponent(jl_selectEdgeTypeAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jcb_edgeTypeAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jb_refreshEdgeTypeAttrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jb_showEdgeTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jl_selectNodeDataAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jb_showNodeData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jp_NetworkLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jp_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jb_refreshNodeDataAttrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jcb_nodeDataAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(202, Short.MAX_VALUE))
        );

        jtp_psfc.addTab("Network", jp_Network);

        jl_dataMappingRules.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_dataMappingRules.setText("Data mapping rules");

        jl_dataType.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_dataType.setText("Data type");

        jrb_linear.setText("Linear");

        jrb_log.setText("Log");
        jrb_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_logActionPerformed(evt);
            }
        });

        jrb_logFC.setText("LogFC");
        jrb_logFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_logFCActionPerformed(evt);
            }
        });

        jrb_FC.setText("FC");
        jrb_FC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_FCActionPerformed(evt);
            }
        });

        jl_defaultValue.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_defaultValue.setText("Default value");

        jtxt_defaultValue.setText("0");
        jtxt_defaultValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_defaultValueActionPerformed(evt);
            }
        });

        jl_multipleDataRule.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_multipleDataRule.setText("Multiple data rule");

        jrb_min.setText("Min");
        jrb_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_minActionPerformed(evt);
            }
        });

        jrb_max.setText("Max");
        jrb_max.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_maxActionPerformed(evt);
            }
        });

        jrb_mean.setText("Mean");
        jrb_mean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_meanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_DataLayout = new javax.swing.GroupLayout(jp_Data);
        jp_Data.setLayout(jp_DataLayout);
        jp_DataLayout.setHorizontalGroup(
                jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_DataLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jl_dataMappingRules)
                                        .addComponent(jl_dataType, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jp_DataLayout.createSequentialGroup()
                                                .addComponent(jl_defaultValue)
                                                .addGap(31, 31, 31)
                                                .addComponent(jtxt_defaultValue, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jl_multipleDataRule)
                                        .addGroup(jp_DataLayout.createSequentialGroup()
                                                .addComponent(jrb_min)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jrb_max)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jrb_mean))
                                        .addGroup(jp_DataLayout.createSequentialGroup()
                                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jrb_linear, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jrb_log))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jrb_logFC, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jrb_FC))))
                                .addContainerGap(164, Short.MAX_VALUE))
        );
        jp_DataLayout.setVerticalGroup(
                jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_DataLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jl_dataMappingRules)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_dataType, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_linear)
                                        .addComponent(jrb_FC))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_log)
                                        .addComponent(jrb_logFC))
                                .addGap(18, 18, 18)
                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jl_defaultValue)
                                        .addComponent(jtxt_defaultValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(jl_multipleDataRule)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jp_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_min)
                                        .addComponent(jrb_max)
                                        .addComponent(jrb_mean))
                                .addContainerGap(212, Short.MAX_VALUE))
        );

        jtp_psfc.addTab("Data", jp_Data);

        jb_ruleWizard.setText("Set up rules with wizard");
        jb_ruleWizard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_ruleWizardActionPerformed(evt);
            }
        });

        jp_multiInOutRulesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jrb_incommingEdges.setText("Incomming edges");

        jrb_outgoingEdges.setText("Outgoing edges");

        jl_signalSplitOn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_signalSplitOn.setForeground(new java.awt.Color(102, 102, 102));
        jl_signalSplitOn.setText("Split signal on ");

        jl_multiInOutRules.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_multiInOutRules.setForeground(new java.awt.Color(51, 153, 0));
        jl_multiInOutRules.setText("Multiple input and output edge rules");

        jl_signalSplitRule.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_signalSplitRule.setForeground(new java.awt.Color(102, 102, 102));
        jl_signalSplitRule.setText("Signal split rule");

        jrb_equal.setText("Equal");

        jrb_proportional.setText("Proportional");
        jrb_proportional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_proportionalActionPerformed(evt);
            }
        });

        jrb_none.setText("None");

        jrb_suppliedWeights.setText("Supplied weights");

        jcb_weights.setMaximumSize(new java.awt.Dimension(14, 20));
        jcb_weights.setMinimumSize(new java.awt.Dimension(14, 20));
        jcb_weights.setName("");
        jcb_weights.setPreferredSize(new java.awt.Dimension(14, 20));

        jl_multiSignalProcessing.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_multiSignalProcessing.setForeground(new java.awt.Color(102, 102, 102));
        jl_multiSignalProcessing.setText("Multiple signal processing rule");

        jrb_addition.setText("Addition");

        jrb_multiplication.setText("Multiplication");
        jrb_multiplication.setMaximumSize(new java.awt.Dimension(100, 23));
        jrb_multiplication.setMinimumSize(new java.awt.Dimension(20, 23));

        jrb_updatedNodeScores.setText("Updated node scores");

        javax.swing.GroupLayout jp_multiInOutRulesPanelLayout = new javax.swing.GroupLayout(jp_multiInOutRulesPanel);
        jp_multiInOutRulesPanel.setLayout(jp_multiInOutRulesPanelLayout);
        jp_multiInOutRulesPanelLayout.setHorizontalGroup(
                jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jrb_multiplication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jrb_none)
                                                        .addComponent(jrb_equal))
                                                .addGap(4, 4, 4)
                                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jrb_proportional)
                                                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                                                .addComponent(jrb_suppliedWeights)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jcb_weights, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(jl_multiInOutRules)
                                        .addComponent(jl_signalSplitOn)
                                        .addComponent(jl_signalSplitRule)
                                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                                .addComponent(jrb_incommingEdges)
                                                .addGap(18, 18, 18)
                                                .addComponent(jrb_outgoingEdges))
                                        .addComponent(jl_multiSignalProcessing, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                                .addComponent(jrb_updatedNodeScores)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jrb_addition)))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        jp_multiInOutRulesPanelLayout.setVerticalGroup(
                jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_multiInOutRulesPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jl_multiInOutRules)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_signalSplitOn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_incommingEdges)
                                        .addComponent(jrb_outgoingEdges))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_signalSplitRule)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_proportional)
                                        .addComponent(jrb_none))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_equal)
                                        .addComponent(jrb_suppliedWeights)
                                        .addComponent(jcb_weights, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_multiSignalProcessing, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jp_multiInOutRulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_addition)
                                        .addComponent(jrb_updatedNodeScores))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jrb_multiplication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jp_simpleRules.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jl_simpleRules.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_simpleRules.setForeground(new java.awt.Color(51, 153, 0));
        jl_simpleRules.setText("Simple rules");

        jl_edgeTypeConfigFile.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_edgeTypeConfigFile.setForeground(new java.awt.Color(102, 102, 102));
        jl_edgeTypeConfigFile.setText("EdgeTypes config file");

        jb_chooseEdgeTypeConfigFile.setText("Choose file");
        jb_chooseEdgeTypeConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_chooseEdgeTypeConfigFileActionPerformed(evt);
            }
        });

        jl_edgeTypeConfigFileName.setText("n/a");

        jb_chooseRuleNameRuleConfigFile.setText("Choose file");
        jb_chooseRuleNameRuleConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_chooseRuleNameRuleConfigFileActionPerformed(evt);
            }
        });

        jl_ruleConfigFile.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jl_ruleConfigFile.setForeground(new java.awt.Color(102, 102, 102));
        jl_ruleConfigFile.setText("Rule config file");

        jl_ruleNameRuleConfigFileName.setText("n/a");

        javax.swing.GroupLayout jp_simpleRulesLayout = new javax.swing.GroupLayout(jp_simpleRules);
        jp_simpleRules.setLayout(jp_simpleRulesLayout);
        jp_simpleRulesLayout.setHorizontalGroup(
                jp_simpleRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_simpleRulesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jp_simpleRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jl_simpleRules)
                                        .addGroup(jp_simpleRulesLayout.createSequentialGroup()
                                                .addComponent(jb_chooseEdgeTypeConfigFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jl_edgeTypeConfigFileName))
                                        .addComponent(jl_ruleConfigFile)
                                        .addGroup(jp_simpleRulesLayout.createSequentialGroup()
                                                .addComponent(jb_chooseRuleNameRuleConfigFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jl_ruleNameRuleConfigFileName))
                                        .addComponent(jl_edgeTypeConfigFile))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_simpleRulesLayout.setVerticalGroup(
                jp_simpleRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_simpleRulesLayout.createSequentialGroup()
                                .addComponent(jl_simpleRules)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jl_edgeTypeConfigFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_simpleRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jb_chooseEdgeTypeConfigFile)
                                        .addComponent(jl_edgeTypeConfigFileName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_ruleConfigFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jp_simpleRulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jb_chooseRuleNameRuleConfigFile)
                                        .addComponent(jl_ruleNameRuleConfigFileName))
                                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_RulesLayout = new javax.swing.GroupLayout(jp_Rules);
        jp_Rules.setLayout(jp_RulesLayout);
        jp_RulesLayout.setHorizontalGroup(
                jp_RulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_RulesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jp_RulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jb_ruleWizard, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jp_multiInOutRulesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jp_simpleRules, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(12, Short.MAX_VALUE))
        );
        jp_RulesLayout.setVerticalGroup(
                jp_RulesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jp_RulesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jb_ruleWizard)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jp_simpleRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jp_multiInOutRulesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jtp_psfc.addTab("Rules", jp_Rules);

        jb_calculateFlow.setText("Calculate flow");

        jb_saveSettings.setText("Save settings");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtp_psfc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 124, Short.MAX_VALUE)
                                                .addComponent(jb_saveSettings)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jb_calculateFlow)
                                                .addGap(16, 16, 16)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jtp_psfc, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jb_calculateFlow)
                                        .addComponent(jb_saveSettings))
                                .addGap(27, 27, 27))
        );

    }

    private void jrb_proportionalActionPerformed(ActionEvent evt) {

    }

    private void jb_showNodeDataActionPerformed(ActionEvent evt) {

    }

    private void jb_refreshNodeDataAttrsActionPerformed(ActionEvent evt) {
        setjcb_nodeDataAttributes();
        enableButtons();
    }

    private void jb_refreshEdgeTypeAttrsActionPerformed(ActionEvent evt) {
        setjcb_edgeTypeAttributes();
        enableButtons();
    }

    private void jb_refreshNetworksActionPerformed(ActionEvent evt) {
        setjcb_networkModel();
        enableButtons();
    }

    private void jb_ruleWizardActionPerformed(ActionEvent evt) {

    }

    private void jb_chooseRuleNameRuleConfigFileActionPerformed(ActionEvent evt) {
        JFrame fileLoadFrame = new JFrame("RuleName-Rule configuration");
        fileLoadFrame.setLocation(400, 250);
        fileLoadFrame.setSize(400, 200);
        JFileChooser fileChooser = new JFileChooser();
        File recentDirectory = PSFCActivator.getRecentDirectory();
        fileChooser.setCurrentDirectory(recentDirectory);


        fileChooser.setDialogTitle("Select RuleName-Rule configuration file");
        fileChooser.showOpenDialog(fileLoadFrame);
        String selectedFilePath = null;

        if (fileChooser.getSelectedFile() != null) {
            selectedFilePath = fileChooser.getSelectedFile().getPath();
            PSFCActivator.writeRecentDirectory(selectedFilePath);
        }

        String noFile = "No file selected";
        String name;
        if (selectedFilePath != null) {
            name = fileChooser.getSelectedFile().getName();
//            int size = noFile.length();
            int size = 40;
            if (name.length() > size)
                name = name.substring(0, size) + "...";
            jl_ruleNameRuleConfigFileName.setText(name);
            this.ruleNameRuleConfigFile = new File(selectedFilePath);
        }
        enableButtons();
    }

    private boolean setRuleNameRuleConfigFile(File file) {
        if (file.exists()) {
            String name = file.getName();
            int size = 40;
            if (name.length() > size)
                name = name.substring(0, size) + "...";
            jl_ruleNameRuleConfigFileName.setText(name);
            ruleNameRuleConfigFile = file;
            enableButtons();
            return true;
        }
        return false;
    }

    private void jb_chooseEdgeTypeConfigFileActionPerformed(ActionEvent evt) {
        JFrame fileLoadFrame = new JFrame("EdgeType-RuleName configuration");
        fileLoadFrame.setLocation(400, 250);
        fileLoadFrame.setSize(400, 200);
        JFileChooser fileChooser = new JFileChooser();
        File recentDirectory = PSFCActivator.getRecentDirectory();
        fileChooser.setCurrentDirectory(recentDirectory);


        fileChooser.setDialogTitle("Select EdgeType-RuleName configuration file");
        fileChooser.showOpenDialog(fileLoadFrame);
        String selectedFilePath = null;

        if (fileChooser.getSelectedFile() != null) {
            selectedFilePath = fileChooser.getSelectedFile().getPath();
            PSFCActivator.writeRecentDirectory(selectedFilePath);

        }

        if (selectedFilePath != null) {
            setEdgeTypeRuleNameConfigFile(new File(selectedFilePath));
        }

        enableButtons();
    }

    private boolean setEdgeTypeRuleNameConfigFile(File file) {
        if (file.exists()) {
            String name = file.getName();
            int size = 40;
            if (name.length() > size)
                name = name.substring(0, size) + "...";
            jl_edgeTypeConfigFileName.setText(name);
            this.edgeTypeRuleNameConfigFile = file;
            enableButtons();
            return true;
        }
        return false;
    }

    private void jrb_meanActionPerformed(ActionEvent evt) {

    }

    private void jrb_maxActionPerformed(ActionEvent evt) {

    }

    private void jrb_minActionPerformed(ActionEvent evt) {

    }

    private void jtxt_defaultValueActionPerformed(ActionEvent evt) {
        String text = jtxt_defaultValue.getText();
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException e) {
            jtxt_defaultValue.setText(Node.getDefaultValue());
        }
    }

    private void jrb_FCActionPerformed(ActionEvent evt) {

    }

    private void jrb_logFCActionPerformed(ActionEvent evt) {

    }

    private void jrb_logActionPerformed(ActionEvent evt) {

    }

    private void jb_showEdgeTypesActionPerformed(ActionEvent evt) {
        if (jcb_edgeTypeAttribute.getSelectedItem() == null)
            return;
        String edgeTypeAttr = jcb_edgeTypeAttribute.getSelectedItem().toString();
        CyNetwork selectedNetwork = getSelectedNetwork();
        if (selectedNetwork == null)
            return;
        TreeSet<String> uniqueValues = new TreeSet<String>();
        for (CyRow row : selectedNetwork.getDefaultEdgeTable().getAllRows()) {
            try {
                uniqueValues.add(row.get(edgeTypeAttr, selectedNetwork.getDefaultEdgeTable().
                        getColumn(edgeTypeAttr).getType()).toString());
            } catch (NullPointerException e) {
                return;
            }
        }
        JFrame frame = new JFrame("Unique values of the attribute " + edgeTypeAttr);
        frame.setName(frame.getTitle());
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (String value : uniqueValues) {
            listModel.addElement(value);
        }

        JList<String> list = new JList<String>(listModel);
        JScrollPane panel = new JScrollPane(list);
        frame.setContentPane(panel);
        frame.setLocation(jb_showEdgeTypes.getLocation());
        frame.pack();
        frame.setVisible(true);
    }

    private void jb_sortNetworkActionPerformed(ActionEvent evt) {
        CyNetwork selectedNetwork = getSelectedNetwork();
        if (selectedNetwork != null) {
            SortNetworkAction sortNetworkAction = new SortNetworkAction(selectedNetwork, getSortingAlgorithm());
            sortNetworkAction.actionPerformed(evt);
        }
    }

    private int getSortingAlgorithm() {
        return ESortingAlgorithms.getNum(jcb_sortingAlgorithm.getSelectedItem().toString());
    }


    private void setModels() {
        setjcb_networkModel();
        setjcb_sortingAlgorithmsModel();
        setjcb_edgeTypeAttributes();
        setjcb_nodeDataAttributes();
    }

    private void addActionListeners() {
        jcb_network.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jcb_networkActionPerformed();
            }
        });
        jb_calculateFlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jb_calculateFlowActionPerformed(e);
            }
        });

        jb_saveSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jb_saveSettingsActionPerformed();
            }
        });
    }

    private void jb_saveSettingsActionPerformed() {
        Properties psfcProps = PSFCActivator.getPsfcProps();
        //columns
        psfcProps.setProperty(EpsfcProps.NodeDataAttribute.getName(), jcb_nodeDataAttribute.getSelectedItem().toString());
        psfcProps.setProperty(EpsfcProps.EdgeTypeAttribute.getName(), jcb_edgeTypeAttribute.getSelectedItem().toString());


        //Node data type
        Enumeration<AbstractButton> buttons = jbg_dataType.getElements();
        while (buttons.hasMoreElements()) {
            JRadioButton button = (JRadioButton) buttons.nextElement();
            if (button.isSelected())
                psfcProps.setProperty(EpsfcProps.NodeDataType.getName(), button.getText());
        }

        //Node default value
        //???????
        //Multiple data rule
        buttons = jbg_multipleDataOption.getElements();
        while (buttons.hasMoreElements()) {
            JRadioButton button = (JRadioButton) buttons.nextElement();
            if (button.isSelected())
                psfcProps.setProperty(EpsfcProps.MultipleDataOption.getName(), button.getText());
        }

        //edgeTypeRuleNameConfigFile
        if (edgeTypeRuleNameConfigFile != null)
            psfcProps.setProperty(EpsfcProps.EdgeTypeRuleNameConfigFile.getName(), edgeTypeRuleNameConfigFile.getAbsolutePath());
        //ruleNameRuleConfigFile
        if (ruleNameRuleConfigFile != null)
            psfcProps.setProperty(EpsfcProps.RuleNameRuleConfigFile.getName(), ruleNameRuleConfigFile.getAbsolutePath());


        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(PSFCActivator.getPsfcPropsFile());
            PSFCActivator.getPsfcProps().store(outputStream, "PSFC property file");
            outputStream.close();
        } catch (FileNotFoundException e) {
            PSFCActivator.getLogger().error("Could not write to psfc.props file. Reason: " + e.getMessage(), e);
        } catch (IOException e) {
            PSFCActivator.getLogger().error("Could not write to psfc.props file. Reason: " + e.getMessage(), e);
        }
    }

    private void jb_calculateFlowActionPerformed(ActionEvent e) {
        CyNetwork network  = getSelectedNetwork();
        if (network == null) {
            JOptionPane.showMessageDialog(this,
                    "Selected network does not exist. \nPlease, refresh the network list and choose a valid network for pathway flow calculation.",
                    "PSFC user message", JOptionPane.OK_OPTION);
            return;
        }
        if (network.getNodeList().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "The network you have chosen contains no nodes.\n " +
                            "Please, choose a valid network for pathway flow calculation",
                    "PSFC user message", JOptionPane.OK_OPTION
            );
            return;
        }
        CyColumn edgeTypeColumn = getEdgeTypeColumn();
        if (edgeTypeColumn == null) {
            JOptionPane.showMessageDialog(this,
                    "Selected EdgeType column does not exist. \nPlease, refresh the column list and choose a valid EdgeType column for pathway flow calculation.",
                    "PSFC user message", JOptionPane.OK_OPTION);
            return;
        }
        boolean isString = true;
        try {
            if (!(edgeTypeColumn.getType().newInstance() instanceof String))
                isString = false;
        } catch (InstantiationException e1) {
            isString = false;
        } catch (IllegalAccessException e1) {
            isString = false;
        }
        if (!isString) {
            JOptionPane.showMessageDialog(this,
                    "Illegal EdgeType column: should be of type String. \nPlease, choose a valid EdgeType column for pathway flow calculation.",
                    "PSFC user message", JOptionPane.OK_OPTION);
            return;
        }
        CyColumn nodeDataColumn = getNodeDataColumn();
        if (nodeDataColumn == null) {
            JOptionPane.showMessageDialog(this,
                    "Selected Node Data column does not exist. \nPlease, refresh the column list and choose a valid Node Data column for pathway flow calculation.",
                    "PSFC user message", JOptionPane.OK_OPTION);
            return;
        }
        boolean isNumber = true;
        if (!nodeDataColumn.getType().getName().equals(Double.class.getName()))
            if (!nodeDataColumn.getType().getName().equals(Integer.class.getName()))
                isNumber = false;
        if (!isNumber) {
            JOptionPane.showMessageDialog(this,
                    "Illegal NodeData column: should be numeric. \nPlease, choose a valid NodeData column for pathway flow calculation.",
                    "PSFC user message", JOptionPane.OK_OPTION);
            return;
        }

        boolean sorted = checkSorted(network);
        SortNetworkAction sortNetworkAction;
        PSFCActivator.getLogger().debug("PSFC flow calculation calling network sorting action.");
        if (!sorted) {
            sortNetworkAction = new SortNetworkAction(network, getSortingAlgorithm());
            sortNetworkAction.actionPerformed(e);
            while (!sortNetworkAction.isPerformed()) {
                try {
                    Thread.sleep(50);
                    System.out.println("Pathway flow calculation waiting for network sorting");
                } catch (InterruptedException e1) {
                    PSFCActivator.getLogger().error("Error while sorting the network: " + e1.getMessage(), e1);
                }
            }
            if (!sortNetworkAction.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                        "An error occured while sortin the network. \n" +
                                "Please, see the PSFC log file at "
                                + PSFCActivator.getPSFCDir() + " directory for details.",
                        "PSFC error message", JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        CyColumn nodeLevelColumn = getNodeLevelColumn();
        Properties nodeDataProperties = getNodeDataProperties();

        CalculateScoreFlowAction calculateFlowAction = new CalculateScoreFlowAction(
                network, getSortingAlgorithm(), edgeTypeColumn, nodeDataColumn, nodeLevelColumn,
                edgeTypeRuleNameConfigFile, ruleNameRuleConfigFile, nodeDataProperties);
        calculateFlowAction.actionPerformed(e);
    }

    private Properties getNodeDataProperties() {
        Properties properties = new Properties();
        properties.setProperty(ENodeDataProps.NODE_DEFAULT_VALUE.getName(), jtxt_defaultValue.getText());
        return properties;
    }

    private boolean checkSorted(CyNetwork network) {
        CyColumn nodeLevelColumn = getNodeLevelColumn();
        if (nodeLevelColumn == null)
            return false;
        try {
            if (nodeLevelColumn.getType().newInstance() instanceof Integer)
                return false;
            for (Object nodeObj : network.getNodeList()) {
                CyNode cyNode = (CyNode) nodeObj;

                if (network.getDefaultNodeTable().getRow(cyNode.getSUID())
                        .get(nodeLevelColumn.getName(), nodeLevelColumn.getType())
                        == null)
                    return false;
            }
        } catch (InstantiationException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }

    private CyColumn getEdgeTypeColumn() {
        try {
            CyNetwork network = getSelectedNetwork();
            String edgeTypeAttr = jcb_edgeTypeAttribute.getSelectedItem().toString();
            return network.getDefaultEdgeTable().getColumn(edgeTypeAttr);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private CyColumn getNodeDataColumn() {
        try {
            CyNetwork network = getSelectedNetwork();
            String attr = jcb_nodeDataAttribute.getSelectedItem().toString();
            return network.getDefaultNodeTable().getColumn(attr);
        } catch (NullPointerException e) {
            return null;
        }

    }

    private CyColumn getNodeLevelColumn() {
        try {
            CyNetwork network = getSelectedNetwork();
            return network.getDefaultNodeTable().getColumn(levelAttr);
        } catch (Exception e) {
            return null;
        }
    }

    private void jcb_networkActionPerformed() {
        setjcb_edgeTypeAttributes();
        setjcb_nodeDataAttributes();
        enableButtons();
    }

    private void setjcb_edgeTypeAttributes() {
        CyNetwork selectedNetwork = getSelectedNetwork();
        if (selectedNetwork == null)
            jcb_edgeTypeAttribute.setModel(new DefaultComboBoxModel());
        else {
            Collection<CyColumn> columns = selectedNetwork.getDefaultEdgeTable().getColumns();
            String[] attributes = new String[columns.size()];
            int i = 0;
            for (CyColumn column : columns) {
                attributes[i++] = column.getName();
            }
            jcb_edgeTypeAttribute.setModel(new DefaultComboBoxModel(attributes));

            //Select item from properties, if valid
            String edgeTypeAttr = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.EdgeTypeAttribute.getName());
            for (i = 0; i < jcb_edgeTypeAttribute.getItemCount(); i++) {
                Object item = jcb_edgeTypeAttribute.getItemAt(i);
                if (item.toString().equals(edgeTypeAttr))
                    jcb_edgeTypeAttribute.setSelectedItem(item);
            }
        }

        enableButtons();
    }

    private void setjcb_nodeDataAttributes() {
        CyNetwork selectedNetwork = null;
        if (jcb_network.getSelectedItem() != null)
            for (CyNetwork network : PSFCActivator.networkManager.getNetworkSet())
                if (network.getRow(network).get(CyNetwork.NAME, String.class).
                        equals(jcb_network.getSelectedItem().toString()))
                    selectedNetwork = network;
        if (selectedNetwork == null)
            jcb_nodeDataAttribute.setModel(new DefaultComboBoxModel());
        else {
            Collection<CyColumn> columns = selectedNetwork.getDefaultNodeTable().getColumns();
            String[] attributes = new String[columns.size()];
            int i = 0;
            for (CyColumn column : columns) {
                attributes[i++] = column.getName();
            }
            jcb_nodeDataAttribute.setModel(new DefaultComboBoxModel(attributes));

            //Select item from properties, if valid
            String edgeTypeAttr = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.NodeDataAttribute.getName());
            for (i = 0; i < jcb_nodeDataAttribute.getItemCount(); i++) {
                Object item = jcb_nodeDataAttribute.getItemAt(i);
                if (item.toString().equals(edgeTypeAttr))
                    jcb_nodeDataAttribute.setSelectedItem(item);
            }
        }

    }

    private CyNetwork getSelectedNetwork() {
        CyNetwork selectedNetwork = null;

        if (jcb_network.getSelectedItem() == null)
            return null;
        String networkSelection = jcb_network.getSelectedItem().toString();
        Long suid = null;
        if (networkSelection.isEmpty())
            return null;
        if (networkSelection.contains(suidSplit)) {
            String[] tokens = networkSelection.split(suidSplit);
            try {
                suid = Long.decode(tokens[tokens.length - 1]);
            } catch (NumberFormatException e) {
                String message = "Could not convert SUID " + suid + " to java.lang.Long";
                PSFCActivator.getLogger().error(message);
            }
        } else {
            String message = "Network selection " + networkSelection + "does not contain SUID";
            PSFCActivator.getLogger().error(message);
        }

        for (CyNetwork network : PSFCActivator.networkManager.getNetworkSet())
//                if (network.getRow(network).get(CyNetwork.NAME, String.class).
//                        equals(jcb_network.getSelectedItem().toString()))
            if (network.getSUID().equals(suid))
                selectedNetwork = network;
        return selectedNetwork;
    }


    private void setjcb_sortingAlgorithmsModel() {
        String[] sortingAlgorithms = ESortingAlgorithms.getAlgorithmNames();
        jcb_sortingAlgorithm.setModel(new DefaultComboBoxModel(sortingAlgorithms));
    }

    private void setjcb_networkModel() {
        Set<CyNetwork> networkSet = PSFCActivator.networkManager.getNetworkSet();
        String[] networkTitles = new String[networkSet.size()];
        int index = 0;
        for (CyNetwork network : networkSet) {
            networkTitles[index++] = network.getRow(network).get("Name", String.class) + suidSplit + network.getSUID();
        }
        jcb_network.setModel(new DefaultComboBoxModel(networkTitles));
        for (int i = 0; i < jcb_network.getItemCount(); i++) {
            Object item = jcb_network.getItemAt(i);
            CyNetwork currentNetwork = PSFCActivator.cyApplicationManager.getCurrentNetwork();
            if (currentNetwork != null)
                if (item.toString().equals(currentNetwork.getRow(currentNetwork).get("Name", String.class)))
                    jcb_network.setSelectedItem(item);
        }
    }

    private void setComponentProperties() {
        //Button groups

        //jbg_dataType
        jbg_dataType = new ButtonGroup();
        jbg_dataType.add(jrb_linear);
        jbg_dataType.add(jrb_log);
        jbg_dataType.add(jrb_FC);
        jbg_dataType.add(jrb_logFC);
        //default selection
        jrb_linear.setSelected(true);

        //Set selectionFromProperties
        String dataType = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.NodeDataType.getName());
        if (dataType != null) {
            setRadioButton(jbg_dataType, dataType);
        }

        //jbg_multipleDataOption

        jbg_multipleDataOption = new ButtonGroup();
        jbg_multipleDataOption.add(jrb_max);
        jbg_multipleDataOption.add(jrb_min);
        jbg_multipleDataOption.add(jrb_mean);

        //default selection
        jrb_mean.setSelected(true);

        //Set selectionFromProperties
        String multipleDataOption = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.MultipleDataOption.getName());
        if (multipleDataOption != null) {
            setRadioButton(jbg_multipleDataOption, multipleDataOption);
        }

        //EdgeTypeRuleNameConfigFile
        String fileName = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.EdgeTypeRuleNameConfigFile.getName());
        File file = new File(fileName);
        if (file.exists())
            setEdgeTypeRuleNameConfigFile(file);

        //RuleNameRuleConfigFile
        fileName = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.RuleNameRuleConfigFile.getName());
        file = new File(fileName);
        if (file.exists())
            setRuleNameRuleConfigFile(file);

        //Split signal on button group
        jbg_splitSignalOn = new ButtonGroup();
        jbg_splitSignalOn.add(jrb_incommingEdges);
        jbg_splitSignalOn.add(jrb_outgoingEdges);

        //default selection
        jrb_outgoingEdges.setSelected(true);

        //Set selectionFromProperties
        String splitSignalOn = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.SplitSignalOn.getName());
        if (splitSignalOn != null)
            setRadioButton(jbg_splitSignalOn, splitSignalOn);

        jbg_signalSplitRule = new ButtonGroup();
        jbg_signalSplitRule.add(jrb_none);
        jbg_signalSplitRule.add(jrb_equal);
        jbg_signalSplitRule.add(jrb_proportional);
        jbg_signalSplitRule.add(jrb_suppliedWeights);

        //default selection
        jrb_none.setSelected(true);

        //Set selectionFromProperties
        String splitSignalRule = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.SplitSignalRule.getName());
        if (splitSignalOn != null)
            setRadioButton(jbg_signalSplitRule, splitSignalRule);

        jbg_multipleSignalProcessingRule = new ButtonGroup();
        jbg_multipleSignalProcessingRule.add(jrb_updatedNodeScores);
        jbg_multipleSignalProcessingRule.add(jrb_addition);
        jbg_multipleSignalProcessingRule.add(jrb_multiplication);

        //default selection
        jrb_updatedNodeScores.setSelected(true);

        //Set selectionFromProperties
        String multipleSignalProcessingRule = PSFCActivator.getPsfcProps().getProperty(EpsfcProps.MultipleSignalProcessingRule.getName());
        if (multipleSignalProcessingRule != null)
            setRadioButton(jbg_multipleSignalProcessingRule, multipleSignalProcessingRule);

    }

    private void setRadioButton(ButtonGroup buttonGroup, String buttonName) {
        Enumeration<AbstractButton> buttonEnumeration = buttonGroup.getElements();
        while (buttonEnumeration.hasMoreElements()) {
            JRadioButton rButton = (JRadioButton) buttonEnumeration.nextElement();
            if (rButton.getText().equals(buttonName)) {
                rButton.setSelected(true);
                break;
            }
        }
    }

    private void enableButtons() {
        jb_calculateFlow.setEnabled(false);
        CyNetwork network = getSelectedNetwork();
        if (network == null) {
            jb_sortNetwork.setEnabled(false);
        } else {
            jb_sortNetwork.setEnabled(true);
            boolean nodeDataColumn = getNodeDataColumn() != null;
            boolean edgeTypeColumn = getEdgeTypeColumn() != null;
            boolean config = (edgeTypeRuleNameConfigFile != null &&
                    edgeTypeRuleNameConfigFile.exists());
            config = (config && ruleNameRuleConfigFile != null && ruleNameRuleConfigFile.exists());
            if (nodeDataColumn && edgeTypeColumn && config)
                jb_calculateFlow.setEnabled(true);
        }
    }
}
