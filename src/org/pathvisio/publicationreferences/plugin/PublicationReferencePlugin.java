package org.pathvisio.publicationreferences.plugin;

import java.awt.Color;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.pathvisio.core.ApplicationEvent;
import org.pathvisio.core.Engine;
import org.pathvisio.core.Engine.ApplicationEventListener;
import org.pathvisio.core.view.VPathwayListener;
import org.pathvisio.desktop.PvDesktop;
import org.pathvisio.desktop.plugin.Plugin;



public class PublicationReferencePlugin implements Plugin,
		ApplicationEventListener {
	private PvDesktop desktop;

	private Engine eng;

	private JScrollPane ReferencesScrollPane;
	final JTextField TitleTag = new JTextField("Publication Xrefs");

	final VPUtility.MyTableModel mytbm = new VPUtility.MyTableModel();

	final JTable propertyTable = new JTable(mytbm) {
		public Class<?> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
	};
	private VPathwayListener vpwListener = new VPUtility.VPWListener(
			propertyTable);

	final Map<Integer, List<String>> resource = new HashMap<Integer, List<String>>();

	public void init(PvDesktop desktop) {
		this.desktop = desktop;

		eng = desktop.getSwingEngine().getEngine();
	
		eng.addApplicationEventListener(this);

		
		if (eng.hasVPathway())
			eng.getActiveVPathway().addVPathwayListener(vpwListener);

		createPluginUIAndTheirListeners();
	}

	/**
	 * Almost all the UI related objects are created or assigned with listeners
	 * here.
	 */
	private void createPluginUIAndTheirListeners() {

		TitleTag.setEditable(false);

		// jtable settings
		propertyTable.setTableHeader(null);
		propertyTable.setFont(new Font("Verdana", Font.PLAIN, 15));

		mytbm.addColumn("pathwayelemnt");

		mytbm.addColumn("pubchemid");
		mytbm.addColumn("authors");
		mytbm.addColumn("Source");

		propertyTable.getColumnModel().getColumn(0)
				.setCellRenderer(new TextAreaRenderer(this));
		propertyTable.getColumnModel().getColumn(1)
				.setCellRenderer(new TextAreaRenderer(this));
		propertyTable.getColumnModel().getColumn(2)
				.setCellRenderer(new TextAreaRenderer(this));
		propertyTable.getColumnModel().getColumn(3)
				.setCellRenderer(new TextAreaRenderer(this));

		// create a scrollpane and adding jtable to the pane
		propertyTable.getColumn("pathwayelemnt").setMaxWidth(60);
		propertyTable.getColumn("pubchemid").setMaxWidth(140);
		ReferencesScrollPane = new JScrollPane(propertyTable);

		
		ReferencesScrollPane.getViewport().setBackground(Color.WHITE);

	
		final JPanel mySideBarPanel = new JPanel(new GridBagLayout());
		
		final GridBagConstraints c = new GridBagConstraints();

		c.weighty = 0.0;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		mySideBarPanel.add(TitleTag, c);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		mySideBarPanel.add(ReferencesScrollPane, c);

		final JTabbedPane sidebarTabbedPane = desktop.getSideBarTabbedPane();
		sidebarTabbedPane.add("Publication References", mySideBarPanel);

		sidebarTabbedPane.setSelectedComponent(mySideBarPanel);

	}

	public void done() {
		// do nothing
	}

	public void applicationEvent(ApplicationEvent e) {
		switch (e.getType()) {
		case PATHWAY_NEW:
		case PATHWAY_SAVE:
		case PATHWAY_OPENED:
			resetUI(false);
			eng.getActiveVPathway().addVPathwayListener(vpwListener);
			printItOnTable();
			break;
		}
	}

	private void resetUI(boolean b) {
		// TODO Auto-generated method stub
		clearTableRows();
	}

	void clearTableRows() {
		// TODO Auto-generated method stub
		mytbm.setRowCount(0);

	}

	void printItOnTable() {
		if (eng.hasVPathway()) {
			PReferencesAnalysis pa = new PReferencesAnalysis(this);
			pa.printReferences(eng, resource);

		}
	}

	Engine getengine() {
		return eng;
	}

}
