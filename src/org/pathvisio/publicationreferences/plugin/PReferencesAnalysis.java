package org.pathvisio.publicationreferences.plugin;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import org.pathvisio.core.Engine;
import org.pathvisio.core.biopax.BiopaxElementManager;
import org.pathvisio.core.biopax.reflect.BiopaxElement;
import org.pathvisio.core.biopax.reflect.PublicationXref;

import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.view.GeneProduct;
import org.pathvisio.core.view.Graphics;
import org.pathvisio.core.view.VPathway;
import org.pathvisio.core.view.VPathwayElement;
import org.pathvisio.gui.SwingEngine;

/**
 * 
 * 
 * 
 * @author Sravanthi Sinha
 * @version 1.0
 */
public class PReferencesAnalysis {

	private Pathway path;

	private Iterator<PublicationXref> it;
	Map<Integer, List<String>> resource = new HashMap<Integer, List<String>>();
	private static PublicationReferencePlugin rPlugin;

	PReferencesAnalysis(PublicationReferencePlugin rPlugin) {
		PReferencesAnalysis.rPlugin = rPlugin;
	}


	public Map<Integer, List<String>> calculateMatrix(Engine eng) {

		int i = 0;

		path = eng.getActivePathway();

		for (PathwayElement pe : path.getDataObjects()) {

			if (pe.getObjectType() == ObjectType.DATANODE) {
				List<String> L = new ArrayList<String>();
				it = pe.getBiopaxReferenceManager().getPublicationXRefs()
						.iterator();

				if (it.hasNext()) {

					PublicationXref pel = it.next();
					L.add(pe.getTextLabel());
					L.add(pel.getPubmedId());

					L.add(pel.getAuthorString());
					L.add(pel.getSource());

					i++;
					resource.put(i, L);

				}

			}

		}

	

		return resource;
	}

	public void printReferences(Engine eng, Map<Integer, List<String>> resource) {

		// reset

		resource.clear();
		if (eng.hasVPathway()) {
			PReferencesAnalysis pa = new PReferencesAnalysis(rPlugin);
			resource = pa.calculateMatrix(eng);
		}

		eng.getActiveVPathway().redraw();

		rPlugin.mytbm.addRow(new Object[] { "DataNode", "PubMedID", "Source",
				"Authors" });
		for (int i = 1; i < resource.size(); i++) {
			rPlugin.mytbm.addRow(new Object[] { resource.get(i).get(0),
					resource.get(i).get(1), resource.get(i).get(3),
					resource.get(i).get(2).replace(';', ',') });
		}
		rPlugin.propertyTable.setEnabled(false);

	}
}
