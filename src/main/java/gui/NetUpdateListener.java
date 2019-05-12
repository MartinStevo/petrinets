package gui;

import Exceptions.MissingObjectException;
import Exceptions.WrongArcException;
import Exceptions.WrongValueException;

public interface NetUpdateListener {
    void updateNet();
    NetClickEventEnu getSelectedEventType();
    void setOnClickedId(Long id);
    void createArcStart(Long id);
    void createResetArcStart(Long id);
    void createArcEnd(Long id) throws WrongArcException, WrongValueException, MissingObjectException;
    void createResetArcEnd(Long id) throws MissingObjectException;
    void deleteDrawable(Long id);
}
