/* This file was generated by SableCC (http://www.sablecc.org/). */

package tarea.node;

import tarea.analysis.*;

@SuppressWarnings("nls")
public final class AInitializationStatement extends PStatement
{
    private PInitialization _initialization_;
    private TSemicolon _semicolon_;

    public AInitializationStatement()
    {
        // Constructor
    }

    public AInitializationStatement(
        @SuppressWarnings("hiding") PInitialization _initialization_,
        @SuppressWarnings("hiding") TSemicolon _semicolon_)
    {
        // Constructor
        setInitialization(_initialization_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new AInitializationStatement(
            cloneNode(this._initialization_),
            cloneNode(this._semicolon_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAInitializationStatement(this);
    }

    public PInitialization getInitialization()
    {
        return this._initialization_;
    }

    public void setInitialization(PInitialization node)
    {
        if(this._initialization_ != null)
        {
            this._initialization_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._initialization_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._initialization_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._initialization_ == child)
        {
            this._initialization_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._initialization_ == oldChild)
        {
            setInitialization((PInitialization) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
