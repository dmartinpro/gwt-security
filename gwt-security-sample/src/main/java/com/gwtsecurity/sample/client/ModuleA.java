package com.gwtsecurity.sample.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.gwtsecurity.sample.client.view.SimpleView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ModuleA implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    final Logger logger = Logger.getLogger("MODULEA");
    logger.log(Level.SEVERE, "Module loading...");	  

    RootLayoutPanel.get().add(new SimpleView());
    
  }
}
