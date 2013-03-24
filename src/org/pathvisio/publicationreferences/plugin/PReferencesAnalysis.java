package org.pathvisio.publicationreferences.plugin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.pathvisio.core.Engine;
import org.pathvisio.core.biopax.PublicationXref;

import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;

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
					L.add(pel.getDOI());

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

		rPlugin.mytbm.addRow(new Object[] { "DataNode", "PubMedID", "Journal source",
				"Authors","DOI" });
		for (int i = 1; i < resource.size(); i++) {
			rPlugin.mytbm.addRow(new Object[] { resource.get(i).get(0),
					resource.get(i).get(1), resource.get(i).get(3),
					resource.get(i).get(2).replace(';', ','),resource.get(i).get(4)});
		}
		rPlugin.propertyTable.setEnabled(false);

	}
}
