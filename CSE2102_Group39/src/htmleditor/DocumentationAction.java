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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.action.AbstractApplicationAction;
import org.jhotdraw.util.ResourceBundleUtil;

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
				e.printStackTrace();
			}
		}
	}
}
