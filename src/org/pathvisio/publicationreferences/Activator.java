package org.pathvisio.publicationreferences;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.pathvisio.desktop.plugin.Plugin;
import org.pathvisio.publicationreferences.plugin.PublicationReferencePlugin;

public class Activator implements BundleActivator {

	/*private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}*/

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		//Activator.context = bundleContext;
		PublicationReferencePlugin plugin = new 	PublicationReferencePlugin();
		bundleContext.registerService(Plugin.class.getName(), plugin, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		//Activator.context = null;
	}

}
