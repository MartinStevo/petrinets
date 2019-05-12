package gui;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;
import Exceptions.WrongArcException;
import Exceptions.WrongValueException;

public interface NetClickListener {
    default void click(NetUpdateListener netUpdateListener) throws WrongArcException, MissingObjectException, WrongValueException, UnfireableTransitionException {};
}
