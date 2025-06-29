/* This file was generated by SableCC (http://www.sablecc.org/). */

package tarea.node;

import tarea.analysis.*;

@SuppressWarnings("nls")
public final class TDoubleMinus extends Token
{
    public TDoubleMinus()
    {
        super.setText("--");
    }

    public TDoubleMinus(int line, int pos)
    {
        super.setText("--");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TDoubleMinus(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTDoubleMinus(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TDoubleMinus text.");
    }
}
