/*
 * @(#)Main.java  1.0  June 10, 2006
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package htmleditor;

import org.jhotdraw.app.*;
/**
 * Main.
 *
 * @author Werner Randelshofer.
 * @version 1.0 June 10, 2006 Created.
 */
public class Main {
    
    /** Creates a new instance. */
    public static void main(String[] args) {
        Application app;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac")) {
            app = new DefaultOSXApplication();
        } else if (os.startsWith("win")) {
          //  app = new DefaultMDIApplication();
            app = new DefaultSDIApplication();
        } else {
            app = new DefaultSDIApplication();
        }
        

        DrawApplicationModel model = new DrawApplicationModel();
        model.setName("JHotDraw HTML Editor");
        model.setVersion("1.0");
        model.setCopyright("Copyright 2014 (c) Group39.");
        model.setProjectClassName("htmleditor.DrawProject");
        app.setModel(model);
        app.launch(args);
    }
    
}
