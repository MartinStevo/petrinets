package generated;

import Exceptions.DuplicateException;
import Exceptions.MissingObjectException;
import Exceptions.WrongValueException;

public abstract class Transformer<T> {
    public abstract T transform(Document document) throws WrongValueException, MissingObjectException, DuplicateException;
}
