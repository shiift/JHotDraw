/*
 * @(#)AboutAction.java  1.0  04 January 2005
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

import org.jhotdraw.util.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.*;

import org.jhotdraw.app.*;
import org.jhotdraw.app.action.AbstractApplicationAction;

/**
 * Displays a dialog showing information about the application.
 *
 * @author  Werner Randelshofer
 * @version 1.0  04 January 2005  Created.
 */
public class DocumentationAction extends AbstractApplicationAction {
	public final static String ID = "Documentation";

	/** Creates a new instance. */
	public DocumentationAction(Application app) {
		super(app);
		ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.app.Labels");
		labels.configureAction(this, ID);
	}

	public void actionPerformed(ActionEvent evt) {
		if(Desktop.isDesktopSupported())
		{
			try {
				Desktop.getDesktop().browse(new URI("http://www.liamd.com/documentation.html"));
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
