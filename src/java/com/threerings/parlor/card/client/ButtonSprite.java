//
// $Id: ButtonSprite.java,v 1.1 2004/10/29 00:41:50 andrzej Exp $
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2004 Three Rings Design, Inc., All Rights Reserved
// http://www.threerings.net/code/narya/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.parlor.card.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;

import com.samskivert.swing.Label;

import com.threerings.media.sprite.Sprite;

import com.threerings.parlor.card.Log;

/**
 * A sprite that acts as a button.
 */
public class ButtonSprite extends Sprite
{
    /**
     * Constructs a button sprite.
     *
     * @param label the label to render on the button
     * @param backgroundColor the background color of the button
     * @param actionCommand the button's command
     * @param commandArgument the button's command argument
     */
    public ButtonSprite (Label label, Color backgroundColor, 
        String actionCommand, Object commandArgument)
    {
        _label = label;
        _backgroundColor = backgroundColor;
        _actionCommand = actionCommand;
        _commandArgument = commandArgument;
    }

    /**
     * Returns a reference to the label displayed by this sprite.
     */
    public Label getLabel ()
    {
        return _label;
    }

    /**
     * Updates this sprite's bounds after a change to the label.
     */
    public void updateBounds ()
    {
        // size the bounds to fit our label
        Dimension size = _label.getSize();
        _bounds.width = size.width + PADDING*2;
        _bounds.height = size.height + PADDING*2;
    }
    
    /**
     * Sets the background color of this button.
     */
    public void setBackgroundColor (Color backgroundColor)
    {
        _backgroundColor = backgroundColor;
    }
    
    /**
     * Returns the background color of this button.
     */
    public Color getBackgroundColor ()
    {
        return _backgroundColor;
    }
    
    /**
     * Sets the action command generated by this button.
     */
    public void setActionCommand (String actionCommand)
    {
        _actionCommand = actionCommand;
    }
    
    /**
     * Returns the action command generated by this button.
     */
    public String getActionCommand ()
    {
        return _actionCommand;
    }
    
    /**
     * Sets the command argument generated by this button.
     */
    public void setCommandArgument (Object commandArgument)
    {
        _commandArgument = commandArgument;
    }
    
    /**
     * Returns the command argument generated by this button.
     */
    public Object getCommandArgument ()
    {
        return _commandArgument;
    }
    
    /**
     * Sets whether or not this button is enabled.
     */
    public void setEnabled (boolean enabled)
    {
        if (_enabled != enabled) {
            _enabled = enabled;
            invalidate();
        }
    }
    
    /**
     * Checks whether or not this button is enabled.
     */
    public boolean isEnabled ()
    {
        return _enabled;
    }
    
    /**
     * Sets whether or not this button appears pressed
     * (does not fire an event).
     */
    public void setPressed (boolean pressed)
    {
        if (_pressed != pressed) {
            _pressed = pressed;
            invalidate();
        }
    }
    
    /** 
     * Checks whether or not this button appears pressed.
     */
    public boolean isPressed ()
    {
        return _pressed;
    }
    
    // documentation inherited
    protected void init ()
    {
        super.init();

        updateBounds();
    }

    // documentation inherited
    public void paint (Graphics2D gfx)
    {
        gfx.setColor(_enabled ? _backgroundColor : _backgroundColor.darker());
        gfx.fill3DRect(_bounds.x, _bounds.y, _bounds.width, 
            _bounds.height, !_pressed);
            
        _label.render(gfx, _bounds.x + (_pressed ? PADDING : PADDING - 1),
            _bounds.y + (_pressed ? PADDING : PADDING - 1));
    }

    /** The number of pixels to add between the text and the border. */
    protected static final int PADDING = 2;
    
    /** The label associated with this sprite. */
    protected Label _label;
    
    /** The action command generated by this button. */
    protected String _actionCommand;
    
    /** The command argument generated by this button. */
    protected Object _commandArgument;
    
    /** The background color of this sprite. */
    protected Color _backgroundColor;
    
    /** Whether or not the button is currently enabled. */
    protected boolean _enabled = true;
    
    /** Whether or not the button is currently pressed. */
    protected boolean _pressed;
}