/* This file was generated by SableCC (http://www.sablecc.org/). */

package tarea.node;

import tarea.analysis.*;

@SuppressWarnings("nls")
public final class TRBrace extends Token
{
    public TRBrace()
    {
        super.setText("}");
    }

    public TRBrace(int line, int pos)
    {
        super.setText("}");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TRBrace(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTRBrace(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TRBrace text.");
    }
}
