package org.pathvisio.publicationreferences.plugin;

import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import org.pathvisio.core.view.VPathwayEvent;
import org.pathvisio.core.view.VPathwayListener;

/**
 * Contains variables & constants, few common functions, custom classes,
 * Listener required by the plugin.
 */
class VPUtility {

	/**
	 * custom JTable class to override the the method "isCellEditable", in order
	 * to render all its cells un-editable.
	 */
	static class MyTableModel extends DefaultTableModel {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	/**
	 * The listener class to listen to VPathway view related events
	 */
	static class VPWListener implements VPathwayListener {
		private JTable jtb;

		VPWListener(JTable jtb) {
			this.jtb = jtb;
		}

		public void vPathwayEvent(VPathwayEvent e) {
			// TODO Auto-generated method stub
			org.pathvisio.core.view.MouseEvent me;
			if (((me = e.getMouseEvent()) != null)
					&& me.getType() == org.pathvisio.core.view.MouseEvent.MOUSE_DOWN) {
				System.out.println("Pathway area clicked");
				jtb.clearSelection();
			}
		}
	}

}
