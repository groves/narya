//
// $Id: SceneViewPanel.java,v 1.15 2001/10/11 00:41:27 shaper Exp $

package com.threerings.miso.scene;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import com.samskivert.util.Config;
import com.threerings.media.sprite.*;
import com.threerings.miso.util.MisoUtil;

/**
 * The scene view panel is responsible for managing a {@link
 * SceneView}, rendering it to the screen, and handling view-related
 * UI events.
 */
public class SceneViewPanel
    extends JPanel implements AnimatedView
{
    /**
     * Construct the panel and initialize it with a context.
     */
    public SceneViewPanel (Config config, SpriteManager spritemgr)
    {
        // create the data model for the scene view
        _smodel = new IsoSceneViewModel(config);

	// create the scene view
        _view = newSceneView(spritemgr, _smodel);

	// set our attributes for optimal display performance
        setDoubleBuffered(false);
        setOpaque(true);
    }

    /**
     * Constructs the underlying scene view implementation.
     */
    protected IsoSceneView newSceneView (
	SpriteManager smgr, IsoSceneViewModel model)
    {
        return new IsoSceneView(smgr, model);
    }

    /**
     * Set the scene managed by the panel.
     */
    public void setScene (MisoScene scene)
    {
	_view.setScene(scene);
    }

    /**
     * Get the scene managed by the panel.
     */
    public SceneView getSceneView ()
    {
	return _view;
    }

    /**
     * Render the panel and the scene view to the given graphics object.
     */
    public void render (Graphics g)
    {
	if (_offimg == null) {
	    createOffscreen();
	}

	_view.paint(_offg);
	g.drawImage(_offimg, 0, 0, null);
    }

    // documentation inherited
    public void invalidateRects (DirtyRectList rects)
    {
        // pass the invalid rects on to our scene view
        _view.invalidateRects(rects);
    }

    /**
     * Paints this panel immediately. Since we know that we are always
     * opaque and not dependent on Swing's double-buffering, we bypass the
     * antics that <code>JComponent.paintImmediately()</code> performs in
     * the interest of better performance.
     */
    public void paintImmediately ()
    {
        Graphics g = null;

        try {
            Graphics pcg = getGraphics();
            g = pcg.create();
            pcg.dispose();
            paint(g);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public JComponent getComponent ()
    {
	return this;
    }

    public void paintComponent (Graphics g)
    {
	render(g);

//          Rectangle bounds = getBounds();
//          g.drawRect(bounds.x, bounds.y, bounds.width-1, bounds.height-1);
    }

    protected void createOffscreen ()
    {
	Rectangle bounds = getBounds();
	_offimg = createImage(bounds.width, bounds.height);
	_offg = _offimg.getGraphics();
    }

    /**
     * Return the desired size for the panel based on the requested
     * and calculated bounds of the scene view.
     */
    public Dimension getPreferredSize ()
    {
        Dimension psize = (_smodel == null) ?
            super.getPreferredSize() : _smodel.bounds;
	return psize;
    }

    /** The offscreen image used for double-buffering. */
    protected Image _offimg;

    /** The graphics context for the offscreen image. */
    protected Graphics _offg;

    /** The scene view data model. */
    protected IsoSceneViewModel _smodel;

    /** The scene view we're managing. */
    protected SceneView _view;
}
