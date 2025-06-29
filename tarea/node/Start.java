/* This file was generated by SableCC (http://www.sablecc.org/). */

package tarea.node;

import tarea.analysis.*;

@SuppressWarnings("nls")
public final class Start extends Node
{
    private PProgram _pProgram_;
    private EOF _eof_;

    public Start()
    {
        // Empty body
    }

    public Start(
        @SuppressWarnings("hiding") PProgram _pProgram_,
        @SuppressWarnings("hiding") EOF _eof_)
    {
        setPProgram(_pProgram_);
        setEOF(_eof_);
    }

    @Override
    public Object clone()
    {
        return new Start(
            cloneNode(this._pProgram_),
            cloneNode(this._eof_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseStart(this);
    }

    public PProgram getPProgram()
    {
        return this._pProgram_;
    }

    public void setPProgram(PProgram node)
    {
        if(this._pProgram_ != null)
        {
            this._pProgram_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pProgram_ = node;
    }

    public EOF getEOF()
    {
        return this._eof_;
    }

    public void setEOF(EOF node)
    {
        if(this._eof_ != null)
        {
            this._eof_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eof_ = node;
    }

    @Override
    void removeChild(Node child)
    {
        if(this._pProgram_ == child)
        {
            this._pProgram_ = null;
            return;
        }

        if(this._eof_ == child)
        {
            this._eof_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        if(this._pProgram_ == oldChild)
        {
            setPProgram((PProgram) newChild);
            return;
        }

        if(this._eof_ == oldChild)
        {
            setEOF((EOF) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    public String toString()
    {
        return "" +
            toString(this._pProgram_) +
            toString(this._eof_);
    }
}
